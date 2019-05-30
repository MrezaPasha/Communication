package Rasad.Core.Compression.Encryption;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;

/** 
 Defines a wrapper object to access the Pkzip algorithm. 
 This class cannot be inherited.
*/
public final class PkzipClassicManaged extends PkzipClassic
{
	/** 
	 Get / set the applicable block size in bits.
	 
	 The only valid block size is 8.
	*/
	@Override
	public int getBlockSize()
	{
		return 8;
	}

	@Override
	public void setBlockSize(int value)
	{
		if (value != 8)
		{
			throw new CryptographicException("Block size is invalid");
		}
	}

	/** 
	 Get an array of legal <see cref="KeySizes">key sizes.</see>
	*/
	@Override
	public KeySizes[] getLegalKeySizes()
	{
		KeySizes[] keySizes = new KeySizes[1];
		keySizes[0] = new KeySizes(12 * 8, 12 * 8, 0);
		return keySizes;
	}

	/** 
	 Generate an initial vector.
	*/
	@Override
	public void GenerateIV()
	{
		// Do nothing.
	}

	/** 
	 Get an array of legal <see cref="KeySizes">block sizes</see>.
	*/
	@Override
	public KeySizes[] getLegalBlockSizes()
	{
		KeySizes[] keySizes = new KeySizes[1];
		keySizes[0] = new KeySizes(1 * 8, 1 * 8, 0);
		return keySizes;
	}

	/** 
	 Get / set the key value applicable.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override byte[] getKey()
	@Override
	public byte[] getKey()
	{
		if (key_ == null)
		{
			GenerateKey();
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (byte[]) key_.Clone();
		return (byte[]) key_.clone();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setKey(byte[] value)
	@Override
	public void setKey(byte[] value)
	{
		if (value == null)
		{
			throw new NullPointerException("value");
		}

		if (value.length != 12)
		{
			throw new CryptographicException("Key size is illegal");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: key_ = (byte[]) value.Clone();
		key_ = (byte[]) value.clone();
	}

	/** 
	 Generate a new random key.
	*/
	@Override
	public void GenerateKey()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: key_ = new byte[12];
		key_ = new byte[12];
		Random rnd = new Random();
		rnd.nextBytes(key_);
	}

	/** 
	 Create an encryptor.
	 
	 @param rgbKey The key to use for this encryptor.
	 @param rgbIV Initialisation vector for the new encryptor.
	 @return Returns a new PkzipClassic encryptor
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override ICryptoTransform CreateEncryptor(byte[] rgbKey, byte[] rgbIV)
	@Override
	public ICryptoTransform CreateEncryptor(byte[] rgbKey, byte[] rgbIV)
	{
		key_ = rgbKey;
		return new PkzipClassicEncryptCryptoTransform(getKey());
	}

	/** 
	 Create a decryptor.
	 
	 @param rgbKey Keys to use for this new decryptor.
	 @param rgbIV Initialisation vector for the new decryptor.
	 @return Returns a new decryptor.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override ICryptoTransform CreateDecryptor(byte[] rgbKey, byte[] rgbIV)
	@Override
	public ICryptoTransform CreateDecryptor(byte[] rgbKey, byte[] rgbIV)
	{
		key_ = rgbKey;
		return new PkzipClassicDecryptCryptoTransform(getKey());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] key_;
	private byte[] key_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
//#endif
