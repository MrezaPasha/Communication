package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

/** 
 Identifies the encryption algorithm used for an entry
*/
public enum EncryptionAlgorithm
{
	/** 
	 No encryption has been used.
	*/
	None(0),
	/** 
	 Encrypted using PKZIP 2.0 or 'classic' encryption.
	*/
	PkzipClassic(1),
	/** 
	 DES encryption has been used.
	*/
	Des(0x6601),
	/** 
	 RCS encryption has been used for encryption.
	*/
	RC2(0x6602),
	/** 
	 Triple DES encryption with 168 bit keys has been used for this entry.
	*/
	TripleDes168(0x6603),
	/** 
	 Triple DES with 112 bit keys has been used for this entry.
	*/
	TripleDes112(0x6609),
	/** 
	 AES 128 has been used for encryption.
	*/
	Aes128(0x660e),
	/** 
	 AES 192 has been used for encryption.
	*/
	Aes192(0x660f),
	/** 
	 AES 256 has been used for encryption.
	*/
	Aes256(0x6610),
	/** 
	 RC2 corrected has been used for encryption.
	*/
	RC2Corrected(0x6702),
	/** 
	 Blowfish has been used for encryption.
	*/
	Blowfish(0x6720),
	/** 
	 Twofish has been used for encryption.
	*/
	Twofish(0x6721),
	/** 
	 RC4 has been used for encryption.
	*/
	RC4(0x6801),
	/** 
	 An unknown algorithm has been used for encryption.
	*/
	Unknown(0xffff);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, EncryptionAlgorithm> mappings;
	private static java.util.HashMap<Integer, EncryptionAlgorithm> getMappings()
	{
		if (mappings == null)
		{
			synchronized (EncryptionAlgorithm.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, EncryptionAlgorithm>();
				}
			}
		}
		return mappings;
	}

	private EncryptionAlgorithm(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static EncryptionAlgorithm forValue(int value)
	{
		return getMappings().get(value);
	}
}