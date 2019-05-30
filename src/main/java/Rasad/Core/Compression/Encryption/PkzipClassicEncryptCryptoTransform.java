package Rasad.Core.Compression.Encryption;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;

/** 
 PkzipClassic CryptoTransform for encryption.
*/
public class PkzipClassicEncryptCryptoTransform extends PkzipClassicCryptoBase implements ICryptoTransform
{
	/** 
	 Initialise a new instance of <see cref="PkzipClassicEncryptCryptoTransform"></see>
	 
	 @param keyBlock The key block to use.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internal PkzipClassicEncryptCryptoTransform(byte[] keyBlock)
	public PkzipClassicEncryptCryptoTransform(byte[] keyBlock)
	{
		SetKeys(keyBlock);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICryptoTransform Members

	/** 
	 Transforms the specified region of the specified byte array.
	 
	 @param inputBuffer The input for which to compute the transform.
	 @param inputOffset The offset into the byte array from which to begin using data.
	 @param inputCount The number of bytes in the byte array to use as data.
	 @return The computed transform.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] TransformFinalBlock(byte[] inputBuffer, int inputOffset, int inputCount)
	public final byte[] TransformFinalBlock(byte[] inputBuffer, int inputOffset, int inputCount)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] result = new byte[inputCount];
		byte[] result = new byte[inputCount];
		TransformBlock(inputBuffer, inputOffset, inputCount, result, 0);
		return result;
	}

	/** 
	 Transforms the specified region of the input byte array and copies 
	 the resulting transform to the specified region of the output byte array.
	 
	 @param inputBuffer The input for which to compute the transform.
	 @param inputOffset The offset into the input byte array from which to begin using data.
	 @param inputCount The number of bytes in the input byte array to use as data.
	 @param outputBuffer The output to which to write the transform.
	 @param outputOffset The offset into the output byte array from which to begin writing data.
	 @return The number of bytes written.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int TransformBlock(byte[] inputBuffer, int inputOffset, int inputCount, byte[] outputBuffer, int outputOffset)
	public final int TransformBlock(byte[] inputBuffer, int inputOffset, int inputCount, byte[] outputBuffer, int outputOffset)
	{
		for (int i = inputOffset; i < inputOffset + inputCount; ++i)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte oldbyte = inputBuffer[i];
			byte oldbyte = inputBuffer[i];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: outputBuffer[outputOffset++] = (byte)(inputBuffer[i] ^ TransformByte());
			outputBuffer[outputOffset++] = (byte)(inputBuffer[i] ^ TransformByte());
			UpdateKeys(oldbyte);
		}
		return inputCount;
	}

	/** 
	 Gets a value indicating whether the current transform can be reused.
	*/
	public final boolean getCanReuseTransform()
	{
		return true;
	}

	/** 
	 Gets the size of the input data blocks in bytes.
	*/
	public final int getInputBlockSize()
	{
		return 1;
	}

	/** 
	 Gets the size of the output data blocks in bytes.
	*/
	public final int getOutputBlockSize()
	{
		return 1;
	}

	/** 
	 Gets a value indicating whether multiple blocks can be transformed.
	*/
	public final boolean getCanTransformMultipleBlocks()
	{
		return true;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IDisposable Members

	/** 
	 Cleanup internal state.
	*/
	public final void Dispose()
	{
		Reset();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}