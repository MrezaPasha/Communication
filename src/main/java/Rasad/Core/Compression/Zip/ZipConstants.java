package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

/** 
 This class contains constants used for Zip format files
*/
public final class ZipConstants
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Versions
	/** 
	 The version made by field for entries in the central header when created by this library
	 
	 
	 This is also the Zip version for the library when comparing against the version required to extract
	 for an entry.  See <see cref="ZipEntry.CanDecompress"/>.
	 
	*/
	public static final int VersionMadeBy = 51; // was 45 before AES

	/** 
	 The version made by field for entries in the central header when created by this library
	 
	 
	 This is also the Zip version for the library when comparing against the version required to extract
	 for an entry.  See <see cref="ZipInputStream.CanDecompressEntry">ZipInputStream.CanDecompressEntry</see>.
	 
	*/
	@Deprecated
	public static final int VERSION_MADE_BY = 51;

	/** 
	 The minimum version required to support strong encryption
	*/
	public static final int VersionStrongEncryption = 50;

	/** 
	 The minimum version required to support strong encryption
	*/
	@Deprecated
	public static final int VERSION_STRONG_ENCRYPTION = 50;

	/** 
	 Version indicating AES encryption
	*/
	public static final int VERSION_AES = 51;

	/** 
	 The version required for Zip64 extensions (4.5 or higher)
	*/
	public static final int VersionZip64 = 45;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Header Sizes
	/** 
	 Size of local entry header (excluding variable length fields at end)
	*/
	public static final int LocalHeaderBaseSize = 30;

	/** 
	 Size of local entry header (excluding variable length fields at end)
	*/
	@Deprecated
	public static final int LOCHDR = 30;

	/** 
	 Size of Zip64 data descriptor
	*/
	public static final int Zip64DataDescriptorSize = 24;

	/** 
	 Size of data descriptor
	*/
	public static final int DataDescriptorSize = 16;

	/** 
	 Size of data descriptor
	*/
	@Deprecated
	public static final int EXTHDR = 16;

	/** 
	 Size of central header entry (excluding variable fields)
	*/
	public static final int CentralHeaderBaseSize = 46;

	/** 
	 Size of central header entry
	*/
	@Deprecated
	public static final int CENHDR = 46;

	/** 
	 Size of end of central record (excluding variable fields)
	*/
	public static final int EndOfCentralRecordBaseSize = 22;

	/** 
	 Size of end of central record (excluding variable fields)
	*/
	@Deprecated
	public static final int ENDHDR = 22;

	/** 
	 Size of 'classic' cryptographic header stored before any entry data
	*/
	public static final int CryptoHeaderSize = 12;

	/** 
	 Size of cryptographic header stored before entry data
	*/
	@Deprecated
	public static final int CRYPTO_HEADER_SIZE = 12;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Header Signatures

	/** 
	 Signature for local entry header
	*/
	public static final int LocalHeaderSignature = 'P' | ('K' << 8) | (3 << 16) | (4 << 24);

	/** 
	 Signature for local entry header
	*/
	@Deprecated
	public static final int LOCSIG = 'P' | ('K' << 8) | (3 << 16) | (4 << 24);

	/** 
	 Signature for spanning entry
	*/
	public static final int SpanningSignature = 'P' | ('K' << 8) | (7 << 16) | (8 << 24);

	/** 
	 Signature for spanning entry
	*/
	@Deprecated
	public static final int SPANNINGSIG = 'P' | ('K' << 8) | (7 << 16) | (8 << 24);

	/** 
	 Signature for temporary spanning entry
	*/
	public static final int SpanningTempSignature = 'P' | ('K' << 8) | ('0' << 16) | ('0' << 24);

	/** 
	 Signature for temporary spanning entry
	*/
	@Deprecated
	public static final int SPANTEMPSIG = 'P' | ('K' << 8) | ('0' << 16) | ('0' << 24);

	/** 
	 Signature for data descriptor
	 
	 
	 This is only used where the length, Crc, or compressed size isnt known when the
	 entry is created and the output stream doesnt support seeking.
	 The local entry cannot be 'patched' with the correct values in this case
	 so the values are recorded after the data prefixed by this header, as well as in the central directory.
	 
	*/
	public static final int DataDescriptorSignature = 'P' | ('K' << 8) | (7 << 16) | (8 << 24);

	/** 
	 Signature for data descriptor
	 
	 
	 This is only used where the length, Crc, or compressed size isnt known when the
	 entry is created and the output stream doesnt support seeking.
	 The local entry cannot be 'patched' with the correct values in this case
	 so the values are recorded after the data prefixed by this header, as well as in the central directory.
	 
	*/
	@Deprecated
	public static final int EXTSIG = 'P' | ('K' << 8) | (7 << 16) | (8 << 24);

	/** 
	 Signature for central header
	*/
	@Deprecated
	public static final int CENSIG = 'P' | ('K' << 8) | (1 << 16) | (2 << 24);

	/** 
	 Signature for central header
	*/
	public static final int CentralHeaderSignature = 'P' | ('K' << 8) | (1 << 16) | (2 << 24);

	/** 
	 Signature for Zip64 central file header
	*/
	public static final int Zip64CentralFileHeaderSignature = 'P' | ('K' << 8) | (6 << 16) | (6 << 24);

	/** 
	 Signature for Zip64 central file header
	*/
	@Deprecated
	public static final int CENSIG64 = 'P' | ('K' << 8) | (6 << 16) | (6 << 24);

	/** 
	 Signature for Zip64 central directory locator
	*/
	public static final int Zip64CentralDirLocatorSignature = 'P' | ('K' << 8) | (6 << 16) | (7 << 24);

	/** 
	 Signature for archive extra data signature (were headers are encrypted).
	*/
	public static final int ArchiveExtraDataSignature = 'P' | ('K' << 8) | (6 << 16) | (7 << 24);

	/** 
	 Central header digitial signature
	*/
	public static final int CentralHeaderDigitalSignature = 'P' | ('K' << 8) | (5 << 16) | (5 << 24);

	/** 
	 Central header digitial signature
	*/
	@Deprecated
	public static final int CENDIGITALSIG = 'P' | ('K' << 8) | (5 << 16) | (5 << 24);

	/** 
	 End of central directory record signature
	*/
	public static final int EndOfCentralDirectorySignature = 'P' | ('K' << 8) | (5 << 16) | (6 << 24);

	/** 
	 End of central directory record signature
	*/
	@Deprecated
	public static final int ENDSIG = 'P' | ('K' << 8) | (5 << 16) | (6 << 24);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0 || NETCF_2_0
	// This isnt so great but is better than nothing.
	// Trying to work out an appropriate OEM code page would be good.
	// 850 is a good default for english speakers particularly in Europe.
	private static int defaultCodePage = CultureInfo.CurrentCulture.TextInfo.ANSICodePage;
//#else
	private static int defaultCodePage = Thread.currentThread().CurrentCulture.TextInfo.OEMCodePage;
//#endif

	/** 
	 Default encoding used for string conversion.  0 gives the default system OEM code page.
	 Dont use unicode encodings if you want to be Zip compatible!
	 Using the default code page isnt the full solution neccessarily
	 there are many variable factors, codepage 850 is often a good choice for
	 European users, however be careful about compatability.
	*/
	public static int getDefaultCodePage()
	{
		return defaultCodePage;
	}
	public static void setDefaultCodePage(int value)
	{
		defaultCodePage = value;
	}

	/** 
	 Convert a portion of a byte array to a string.
	 		
	 @param data
	 Data to convert to string
	 
	 @param count
	 Number of bytes to convert starting from index 0
	 
	 @return 
	 data[0]..data[length - 1] converted to a string
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string ConvertToString(byte[] data, int count)
	public static String ConvertToString(byte[] data, int count)
	{
		if (data == null)
		{
			return "";
		}

		return Encoding.GetEncoding(getDefaultCodePage()).GetString(data, 0, count);
	}

	/** 
	 Convert a byte array to string
	 
	 @param data
	 Byte array to convert
	 
	 @return 
	 <paramref name="data">data</paramref>converted to a string
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string ConvertToString(byte[] data)
	public static String ConvertToString(byte[] data)
	{
		if (data == null)
		{
			return "";
		}
		return ConvertToString(data, data.length);
	}

	/** 
	 Convert a byte array to string
	 
	 @param flags The applicable general purpose bits flags
	 @param data
	 Byte array to convert
	 
	 @param count The number of bytes to convert.
	 @return 
	 <paramref name="data">data</paramref>converted to a string
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string ConvertToStringExt(int flags, byte[] data, int count)
	public static String ConvertToStringExt(int flags, byte[] data, int count)
	{
		if (data == null)
		{
			return "";
		}

		if ((flags & GeneralBitFlags.UnicodeText.getValue()) != 0)
		{
			return Encoding.UTF8.GetString(data, 0, count);
		}
		else
		{
			return ConvertToString(data, count);
		}
	}

	/** 
	 Convert a byte array to string
	 
	 @param data
	 Byte array to convert
	 
	 @param flags The applicable general purpose bits flags
	 @return 
	 <paramref name="data">data</paramref>converted to a string
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string ConvertToStringExt(int flags, byte[] data)
	public static String ConvertToStringExt(int flags, byte[] data)
	{
		if (data == null)
		{
			return "";
		}

		if ((flags & GeneralBitFlags.UnicodeText.getValue()) != 0)
		{
			return Encoding.UTF8.GetString(data, 0, data.length);
		}
		else
		{
			return ConvertToString(data, data.length);
		}
	}

	/** 
	 Convert a string to a byte array
	 
	 @param str
	 String to convert to an array
	 
	 @return Converted array
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] ConvertToArray(string str)
	public static byte[] ConvertToArray(String str)
	{
		if (str == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return new byte[0];
			return new byte[0];
		}

		return Encoding.GetEncoding(getDefaultCodePage()).GetBytes(str);
	}

	/** 
	 Convert a string to a byte array
	 
	 @param flags The applicable <see cref="GeneralBitFlags">general purpose bits flags</see>
	 @param str
	 String to convert to an array
	 
	 @return Converted array
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] ConvertToArray(int flags, string str)
	public static byte[] ConvertToArray(int flags, String str)
	{
		if (str == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return new byte[0];
			return new byte[0];
		}

		if ((flags & GeneralBitFlags.UnicodeText.getValue()) != 0)
		{
			return str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
		}
		else
		{
			return ConvertToArray(str);
		}
	}


	/** 
	 Initialise default instance of <see cref="ZipConstants">ZipConstants</see>
	 
	 
	 Private to prevent instances being created.
	 
	*/
	private ZipConstants()
	{
		// Do nothing
	}
}