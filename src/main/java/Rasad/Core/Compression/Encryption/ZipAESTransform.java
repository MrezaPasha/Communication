package Rasad.Core.Compression.Encryption;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

//
// ZipAESTransform.cs
//
// Copyright 2009 David Pierson
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// Linking this library statically or dynamically with other modules is
// making a combined work based on this library.  Thus, the terms and
// conditions of the GNU General Public License cover the whole
// combination.
// 
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent
// modules, and to copy and distribute the resulting executable under
// terms of your choice, provided that you also meet, for each linked
// independent module, the terms and conditions of the license of that
// module.  An independent module is a module which is not derived from
// or based on this library.  If you modify this library, you may extend
// this exception to your version of the library, but you are not
// obligated to do so.  If you do not wish to do so, delete this
// exception statement from your version.
//

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_1 && !NETCF_2_0
// Framework version 2.0 required for Rfc2898DeriveBytes 




/** 
 Transforms stream using AES in CTR mode
*/
public class ZipAESTransform implements ICryptoTransform
{

	private static final int PWD_VER_LENGTH = 2;

	// WinZip use iteration count of 1000 for PBKDF2 key generation
	private static final int KEY_ROUNDS = 1000;

	// For 128-bit AES (16 bytes) the encryption is implemented as expected.
	// For 256-bit AES (32 bytes) WinZip do full 256 bit AES of the nonce to create the encryption
	// block but use only the first 16 bytes of it, and discard the second half.
	private static final int ENCRYPT_BLOCK = 16;

	private int _blockSize;
	private ICryptoTransform _encryptor;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly byte[] _counterNonce;
	private byte[] _counterNonce;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] _encryptBuffer;
	private byte[] _encryptBuffer;
	private int _encrPos;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] _pwdVerifier;
	private byte[] _pwdVerifier;
	private HMACSHA1 _hmacsha1;
	private boolean _finalised;

	private boolean _writeMode;

	/** 
	 Constructor.
	 
	 @param key Password string
	 @param saltBytes Random bytes, length depends on encryption strength.
	 128 bits = 8 bytes, 192 bits = 12 bytes, 256 bits = 16 bytes.
	 @param blockSize The encryption strength, in bytes eg 16 for 128 bits.
	 @param writeMode True when creating a zip, false when reading. For the AuthCode.
	
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ZipAESTransform(string key, byte[] saltBytes, int blockSize, bool writeMode)
	public ZipAESTransform(String key, byte[] saltBytes, int blockSize, boolean writeMode)
	{

		if (blockSize != 16 && blockSize != 32) // 24 valid for AES but not supported by Winzip
		{
			throw new RuntimeException("Invalid blocksize " + blockSize + ". Must be 16 or 32.");
		}
		if (saltBytes.length != blockSize / 2)
		{
			throw new RuntimeException("Invalid salt len. Must be " + blockSize / 2 + " for blocksize " + blockSize);
		}
		// initialise the encryption buffer and buffer pos
		_blockSize = blockSize;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _encryptBuffer = new byte[_blockSize];
		_encryptBuffer = new byte[_blockSize];
		_encrPos = ENCRYPT_BLOCK;

		// Performs the equivalent of derive_key in Dr Brian Gladman's pwd2key.c
		Rfc2898DeriveBytes pdb = new Rfc2898DeriveBytes(key, saltBytes, KEY_ROUNDS);
		RijndaelManaged rm = new RijndaelManaged();
		rm.Mode = CipherMode.ECB; // No feedback from cipher for CTR mode
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _counterNonce = new byte[_blockSize];
		_counterNonce = new byte[_blockSize];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] byteKey1 = pdb.GetBytes(_blockSize);
		byte[] byteKey1 = pdb.GetBytes(_blockSize);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] byteKey2 = pdb.GetBytes(_blockSize);
		byte[] byteKey2 = pdb.GetBytes(_blockSize);
		_encryptor = rm.CreateEncryptor(byteKey1, byteKey2);
		_pwdVerifier = pdb.GetBytes(PWD_VER_LENGTH);
		//
		_hmacsha1 = new HMACSHA1(byteKey2);
		_writeMode = writeMode;
	}

	/** 
	 Implement the ICryptoTransform method.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int TransformBlock(byte[] inputBuffer, int inputOffset, int inputCount, byte[] outputBuffer, int outputOffset)
	public final int TransformBlock(byte[] inputBuffer, int inputOffset, int inputCount, byte[] outputBuffer, int outputOffset)
	{

		// Pass the data stream to the hash algorithm for generating the Auth Code.
		// This does not change the inputBuffer. Do this before decryption for read mode.
		if (!_writeMode)
		{
			_hmacsha1.TransformBlock(inputBuffer, inputOffset, inputCount, inputBuffer, inputOffset);
		}
		// Encrypt with AES in CTR mode. Regards to Dr Brian Gladman for this.
		int ix = 0;
		while (ix < inputCount)
		{
			if (_encrPos == ENCRYPT_BLOCK)
			{
				/* increment encryption nonce   */
				int j = 0;
				while (++_counterNonce[j] == 0)
				{
					++j;
				}
				/* encrypt the nonce to form next xor buffer    */
				_encryptor.TransformBlock(_counterNonce, 0, _blockSize, _encryptBuffer, 0);
				_encrPos = 0;
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: outputBuffer[ix + outputOffset] = (byte)(inputBuffer[ix + inputOffset] ^ _encryptBuffer[_encrPos++]);
			outputBuffer[ix + outputOffset] = (byte)(inputBuffer[ix + inputOffset] ^ _encryptBuffer[_encrPos++]);
			//
			ix++;
		}
		if (_writeMode)
		{
			// This does not change the buffer. 
			_hmacsha1.TransformBlock(outputBuffer, outputOffset, inputCount, outputBuffer, outputOffset);
		}
		return inputCount;
	}

	/** 
	 Returns the 2 byte password verifier
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getPwdVerifier()
	public final byte[] getPwdVerifier()
	{
		return _pwdVerifier;
	}

	/** 
	 Returns the 10 byte AUTH CODE to be checked or appended immediately following the AES data stream.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] GetAuthCode()
	public final byte[] GetAuthCode()
	{
		// We usually don't get advance notice of final block. Hash requres a TransformFinal.
		if (!_finalised)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] dummy = new byte[0];
			byte[] dummy = new byte[0];
			_hmacsha1.TransformFinalBlock(dummy, 0, 0);
			_finalised = true;
		}
		return _hmacsha1.Hash;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICryptoTransform Members

	/** 
	 Not implemented.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] TransformFinalBlock(byte[] inputBuffer, int inputOffset, int inputCount)
	public final byte[] TransformFinalBlock(byte[] inputBuffer, int inputOffset, int inputCount)
	{

		throw new UnsupportedOperationException("ZipAESTransform.TransformFinalBlock");
	}

	/** 
	 Gets the size of the input data blocks in bytes.
	*/
	public final int getInputBlockSize()
	{
		return _blockSize;
	}

	/** 
	 Gets the size of the output data blocks in bytes.
	*/
	public final int getOutputBlockSize()
	{
		return _blockSize;
	}

	/** 
	 Gets a value indicating whether multiple blocks can be transformed.
	*/
	public final boolean getCanTransformMultipleBlocks()
	{
		return true;
	}

	/** 
	 Gets a value indicating whether the current transform can be reused.
	*/
	public final boolean getCanReuseTransform()
	{
		return true;
	}

	/** 
	 Cleanup internal state.
	*/
	public final void Dispose()
	{
		_encryptor.Dispose();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}
//#endif