package Rasad.Core.Compression.Encryption;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;

/** 
 PkzipClassicCryptoBase provides the low level facilities for encryption
 and decryption using the PkzipClassic algorithm.
*/
public class PkzipClassicCryptoBase
{
	/** 
	 Transform a single byte 
	 
	 @return 
	 The transformed value
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected byte TransformByte()
	protected final byte TransformByte()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint temp = ((keys[2] & 0xFFFF) | 2);
		int temp = ((keys[2] & 0xFFFF) | 2);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (byte)((temp * (temp ^ 1)) >> 8);
		return (byte)((temp * (temp ^ 1)) >>> 8);
	}

	/** 
	 Set the key schedule for encryption/decryption.
	 
	 @param keyData The data use to set the keys from.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected void SetKeys(byte[] keyData)
	protected final void SetKeys(byte[] keyData)
	{
		if (keyData == null)
		{
			throw new NullPointerException("keyData");
		}

		if (keyData.length != 12)
		{
			throw new IllegalStateException("Key length is not valid");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: keys = new uint[3];
		keys = new int[3];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: keys[0] = (uint)((keyData[3] << 24) | (keyData[2] << 16) | (keyData[1] << 8) | keyData[0]);
		keys[0] = (int)((keyData[3] << 24) | (keyData[2] << 16) | (keyData[1] << 8) | keyData[0]);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: keys[1] = (uint)((keyData[7] << 24) | (keyData[6] << 16) | (keyData[5] << 8) | keyData[4]);
		keys[1] = (int)((keyData[7] << 24) | (keyData[6] << 16) | (keyData[5] << 8) | keyData[4]);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: keys[2] = (uint)((keyData[11] << 24) | (keyData[10] << 16) | (keyData[9] << 8) | keyData[8]);
		keys[2] = (int)((keyData[11] << 24) | (keyData[10] << 16) | (keyData[9] << 8) | keyData[8]);
	}

	/** 
	 Update encryption keys 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected void UpdateKeys(byte ch)
	protected final void UpdateKeys(byte ch)
	{
		keys[0] = Crc32.ComputeCrc32(keys[0], ch);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: keys[1] = keys[1] + (byte)keys[0];
		keys[1] = keys[1] + (byte)keys[0];
		keys[1] = keys[1] * 134775813 + 1;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: keys[2] = Crc32.ComputeCrc32(keys[2], (byte)(keys[1] >> 24));
		keys[2] = Crc32.ComputeCrc32(keys[2], (byte)(keys[1] >>> 24));
	}

	/** 
	 Reset the internal state.
	*/
	protected final void Reset()
	{
		keys[0] = 0;
		keys[1] = 0;
		keys[2] = 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint[] keys;
	private int[] keys;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}