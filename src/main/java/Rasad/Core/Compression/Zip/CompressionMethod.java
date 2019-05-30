package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

/** 
 The kind of compression used for an entry in an archive
*/
public enum CompressionMethod
{
	/** 
	 A direct copy of the file contents is held in the archive
	*/
	Stored(0),

	/** 
	 Common Zip compression method using a sliding dictionary 
	 of up to 32KB and secondary compression from Huffman/Shannon-Fano trees
	*/
	Deflated(8),

	/** 
	 An extension to deflate with a 64KB window. Not supported by #Zip currently
	*/
	Deflate64(9),

	/** 
	 BZip2 compression. Not supported by #Zip.
	*/
	BZip2(11),

	/** 
	 WinZip special for AES encryption, Now supported by #Zip.
	*/
	WinZipAES(99);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, CompressionMethod> mappings;
	private static java.util.HashMap<Integer, CompressionMethod> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CompressionMethod.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, CompressionMethod>();
				}
			}
		}
		return mappings;
	}

	private CompressionMethod(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static CompressionMethod forValue(int value)
	{
		return getMappings().get(value);
	}
}