package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

/** 
 Defines the contents of the general bit flags field for an archive entry.
*/
public class GeneralBitFlags 
{
	/** 
	 Bit 0 if set indicates that the file is encrypted
	*/
	public static final GeneralBitFlags Encrypted = new GeneralBitFlags(0x0001);
	/** 
	 Bits 1 and 2 - Two bits defining the compression method (only for Method 6 Imploding and 8,9 Deflating)
	*/
	public static final GeneralBitFlags Method = new GeneralBitFlags(0x0006);
	/** 
	 Bit 3 if set indicates a trailing data desciptor is appended to the entry data
	*/
	public static final GeneralBitFlags Descriptor = new GeneralBitFlags(0x0008);
	/** 
	 Bit 4 is reserved for use with method 8 for enhanced deflation
	*/
	public static final GeneralBitFlags ReservedPKware4 = new GeneralBitFlags(0x0010);
	/** 
	 Bit 5 if set indicates the file contains Pkzip compressed patched data.
	 Requires version 2.7 or greater.
	*/
	public static final GeneralBitFlags Patched = new GeneralBitFlags(0x0020);
	/** 
	 Bit 6 if set indicates strong encryption has been used for this entry.
	*/
	public static final GeneralBitFlags StrongEncryption = new GeneralBitFlags(0x0040);
	/** 
	 Bit 7 is currently unused
	*/
	public static final GeneralBitFlags Unused7 = new GeneralBitFlags(0x0080);
	/** 
	 Bit 8 is currently unused
	*/
	public static final GeneralBitFlags Unused8 = new GeneralBitFlags(0x0100);
	/** 
	 Bit 9 is currently unused
	*/
	public static final GeneralBitFlags Unused9 = new GeneralBitFlags(0x0200);
	/** 
	 Bit 10 is currently unused
	*/
	public static final GeneralBitFlags Unused10 = new GeneralBitFlags(0x0400);
	/** 
	 Bit 11 if set indicates the filename and 
	 comment fields for this file must be encoded using UTF-8.
	*/
	public static final GeneralBitFlags UnicodeText = new GeneralBitFlags(0x0800);
	/** 
	 Bit 12 is documented as being reserved by PKware for enhanced compression.
	*/
	public static final GeneralBitFlags EnhancedCompress = new GeneralBitFlags(0x1000);
	/** 
	 Bit 13 if set indicates that values in the local header are masked to hide
	 their actual values, and the central directory is encrypted.
	 
	 
	 Used when encrypting the central directory contents.
	 
	*/
	public static final GeneralBitFlags HeaderMasked = new GeneralBitFlags(0x2000);
	/** 
	 Bit 14 is documented as being reserved for use by PKware
	*/
	public static final GeneralBitFlags ReservedPkware14 = new GeneralBitFlags(0x4000);
	/** 
	 Bit 15 is documented as being reserved for use by PKware
	*/
	public static final GeneralBitFlags ReservedPkware15 = new GeneralBitFlags(0x8000);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, GeneralBitFlags> mappings;
	private static java.util.HashMap<Integer, GeneralBitFlags> getMappings()
	{
		if (mappings == null)
		{
			synchronized (GeneralBitFlags.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, GeneralBitFlags>();
				}
			}
		}
		return mappings;
	}

	private GeneralBitFlags(int value)
	{
		intValue = value;
		synchronized (GeneralBitFlags.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static GeneralBitFlags forValue(int value)
	{
		synchronized (GeneralBitFlags.class)
		{
			GeneralBitFlags enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new GeneralBitFlags(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}