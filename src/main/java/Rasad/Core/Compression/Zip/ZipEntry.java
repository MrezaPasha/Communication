package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.nio.file.*;
import java.time.*;

/** 
 This class represents an entry in a zip archive.  This can be a file
 or a directory
 ZipFile and ZipInputStream will give you instances of this class as 
 information about the members in an archive.  ZipOutputStream
 uses an instance of this class when creating an entry in a Zip file.
 <br/>
 <br/>Author of the original java version : Jochen Hoenicke
*/
public class ZipEntry implements Cloneable
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] enum Known : byte
	private class Known 
	{
		public static final Known None = new Known(0);
		public static final Known Size = new Known(0x01);
		public static final Known CompressedSize = new Known(0x02);
		public static final Known Crc = new Known(0x04);
		public static final Known Time = new Known(0x08);
		public static final Known ExternalAttributes = new Known(0x10);

		public static final int SIZE = java.lang.Byte.SIZE;

		private byte byteValue;
		private static java.util.HashMap<Byte, Known> mappings;
		private static java.util.HashMap<Byte, Known> getMappings()
		{
			if (mappings == null)
			{
				synchronized (Known.class)
				{
					if (mappings == null)
					{
						mappings = new java.util.HashMap<Byte, Known>();
					}
				}
			}
			return mappings;
		}

		private Known(byte value)
		{
			byteValue = value;
			synchronized (Known.class)
			{
				getMappings().put(value, this);
			}
		}

		public byte getValue()
		{
			return byteValue;
		}

		public static Known forValue(byte value)
		{
			synchronized (Known.class)
			{
				Known enumObj = getMappings().get(value);
				if (enumObj == null)
				{
					return new Known(value);
				}
				else
				{
					return enumObj;
				}
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Creates a zip entry with the given name.
	 
	 @param name
	 The name for this entry. Can include directory components.
	 The convention for names is 'unix' style paths with relative names only.
	 There are with no device names and path elements are separated by '/' characters.
	 
	 @exception ArgumentNullException
	 The name passed is null
	 
	*/
	public ZipEntry(String name)
	{
		this(name, 0, ZipConstants.VersionMadeBy, CompressionMethod.Deflated);
	}

	/** 
	 Creates a zip entry with the given name and version required to extract
	 
	 @param name
	 The name for this entry. Can include directory components.
	 The convention for names is 'unix'  style paths with no device names and 
	 path elements separated by '/' characters.  This is not enforced see <see cref="CleanName(string)">CleanName</see>
	 on how to ensure names are valid if this is desired.
	 
	 @param versionRequiredToExtract
	 The minimum 'feature version' required this entry
	 
	 @exception ArgumentNullException
	 The name passed is null
	 
	*/
	public ZipEntry(String name, int versionRequiredToExtract)
	{
		this(name, versionRequiredToExtract, ZipConstants.VersionMadeBy, CompressionMethod.Deflated);
	}

	/** 
	 Initializes an entry with the given name and made by information
	 
	 @param name Name for this entry
	 @param madeByInfo Version and HostSystem Information
	 @param versionRequiredToExtract Minimum required zip feature version required to extract this entry
	 @param method Compression method for this entry.
	 @exception ArgumentNullException
	 The name passed is null
	 
	 @exception ArgumentOutOfRangeException
	 versionRequiredToExtract should be 0 (auto-calculate) or > 10
	 
	 
	 This constructor is used by the ZipFile class when reading from the central header
	 It is not generally useful, use the constructor specifying the name only.
	 
	*/
	public ZipEntry(String name, int versionRequiredToExtract, int madeByInfo, CompressionMethod method)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		if (name.length() > 0xffff)
		{
			throw new IllegalArgumentException("Name is too long", "name");
		}

		if ((versionRequiredToExtract != 0) && (versionRequiredToExtract < 10))
		{
			throw new IndexOutOfBoundsException("versionRequiredToExtract");
		}

		this.setDateTime(getDateTime().Now);
		this.name = CleanName(name);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: this.versionMadeBy = (ushort)madeByInfo;
		this.versionMadeBy = (short)madeByInfo;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: this.versionToExtract = (ushort)versionRequiredToExtract;
		this.versionToExtract = (short)versionRequiredToExtract;
		this.method = method;
	}

	/** 
	 Creates a deep copy of the given zip entry.
	 
	 @param entry
	 The entry to copy.
	 
	*/
	@Deprecated
	public ZipEntry(ZipEntry entry)
	{
		if (entry == null)
		{
			throw new NullPointerException("entry");
		}

		known = entry.known;
		name = entry.name;
		size = entry.size;
		compressedSize = entry.compressedSize;
		crc = entry.crc;
		dosTime = entry.dosTime;
		method = entry.method;
		comment = entry.comment;
		versionToExtract = entry.versionToExtract;
		versionMadeBy = entry.versionMadeBy;
		externalFileAttributes = entry.externalFileAttributes;
		flags = entry.flags;

		zipFileIndex = entry.zipFileIndex;
		offset = entry.offset;

		forceZip64_ = entry.forceZip64_;

		if (entry.extra != null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: extra = new byte[entry.extra.Length];
			extra = new byte[entry.extra.length];
			System.arraycopy(entry.extra, 0, extra, 0, entry.extra.length);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get a value indicating wether the entry has a CRC value available.
	*/
	public final boolean getHasCrc()
	{
		return (known.getValue() & Known.Crc.getValue()) != 0;
	}

	/** 
	 Get/Set flag indicating if entry is encrypted.
	 A simple helper routine to aid interpretation of <see cref="Flags">flags</see>
	 
	 This is an assistant that interprets the <see cref="Flags">flags</see> property.
	*/
	public final boolean getIsCrypted()
	{
		return (flags & 1) != 0;
	}
	public final void setIsCrypted(boolean value)
	{
		if (value)
		{
			flags |= 1;
		}
		else
		{
			flags &= ~1;
		}
	}

	/** 
	 Get / set a flag indicating wether entry name and comment text are
	 encoded in <a href="http://www.unicode.org">unicode UTF8</a>.
	 
	 This is an assistant that interprets the <see cref="Flags">flags</see> property.
	*/
	public final boolean getIsUnicodeText()
	{
		return (flags & GeneralBitFlags.UnicodeText.getValue()) != 0;
	}
	public final void setIsUnicodeText(boolean value)
	{
		if (value)
		{
			flags |= GeneralBitFlags.UnicodeText.getValue();
		}
		else
		{
			flags &= ~GeneralBitFlags.UnicodeText.getValue();
		}
	}

	/** 
	 Value used during password checking for PKZIP 2.0 / 'classic' encryption.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internal byte getCryptoCheckValue()
	public final byte getCryptoCheckValue()
	{
		return cryptoCheckValue_;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internal void setCryptoCheckValue(byte value)
	public final void setCryptoCheckValue(byte value)
	{
		cryptoCheckValue_ = value;
	}

	/** 
	 Get/Set general purpose bit flag for entry
	 
	 
	 General purpose bit flag<br/>
	 <br/>
	 Bit 0: If set, indicates the file is encrypted<br/>
	 Bit 1-2 Only used for compression type 6 Imploding, and 8, 9 deflating<br/>
	 Imploding:<br/>
	 Bit 1 if set indicates an 8K sliding dictionary was used.  If clear a 4k dictionary was used<br/>
	 Bit 2 if set indicates 3 Shannon-Fanno trees were used to encode the sliding dictionary, 2 otherwise<br/>
	 <br/>
	 Deflating:<br/>
	   Bit 2    Bit 1<br/>
		 0        0       Normal compression was used<br/>
		 0        1       Maximum compression was used<br/>
		 1        0       Fast compression was used<br/>
		 1        1       Super fast compression was used<br/>
	 <br/>
	 Bit 3: If set, the fields crc-32, compressed size
	 and uncompressed size are were not able to be written during zip file creation
	 The correct values are held in a data descriptor immediately following the compressed data. <br/>
	 Bit 4: Reserved for use by PKZIP for enhanced deflating<br/>
	 Bit 5: If set indicates the file contains compressed patch data<br/>
	 Bit 6: If set indicates strong encryption was used.<br/>
	 Bit 7-10: Unused or reserved<br/>
	 Bit 11: If set the name and comments for this entry are in <a href="http://www.unicode.org">unicode</a>.<br/>
	 Bit 12-15: Unused or reserved<br/>
	 
	 @see IsUnicodeText
	 @see IsCrypted
	*/
	public final int getFlags()
	{
		return flags;
	}
	public final void setFlags(int value)
	{
		flags = value;
	}

	/** 
	 Get/Set index of this entry in Zip file
	 
	 This is only valid when the entry is part of a <see cref="ZipFile"></see>
	*/
	public final long getZipFileIndex()
	{
		return zipFileIndex;
	}
	public final void setZipFileIndex(long value)
	{
		zipFileIndex = value;
	}

	/** 
	 Get/set offset for use in central header
	*/
	public final long getOffset()
	{
		return offset;
	}
	public final void setOffset(long value)
	{
		offset = value;
	}

	/** 
	 Get/Set external file attributes as an integer.
	 The values of this are operating system dependant see
	 <see cref="HostSystem">HostSystem</see> for details
	*/
	public final int getExternalFileAttributes()
	{
		if ((known.getValue() & Known.ExternalAttributes.getValue()) == 0)
		{
			return -1;
		}
		else
		{
			return externalFileAttributes;
		}
	}

	public final void setExternalFileAttributes(int value)
	{
		externalFileAttributes = value;
		known = Rasad.Core.Compression.Zip.ZipEntry.Known.forValue(known.getValue() | Known.ExternalAttributes.getValue());
	}

	/** 
	 Get the version made by for this entry or zero if unknown.
	 The value / 10 indicates the major version number, and 
	 the value mod 10 is the minor version number
	*/
	public final int getVersionMadeBy()
	{
		return (versionMadeBy & 0xff);
	}

	/** 
	 Get a value indicating this entry is for a DOS/Windows system.
	*/
	public final boolean getIsDOSEntry()
	{
		return ((getHostSystem() == HostSystemID.Msdos.getValue()) || (getHostSystem() == HostSystemID.WindowsNT.getValue()));
	}

	/** 
	 Test the external attributes for this <see cref="ZipEntry"/> to
	 see if the external attributes are Dos based (including WINNT and variants)
	 and match the values
	 
	 @param attributes The attributes to test.
	 @return Returns true if the external attributes are known to be DOS/Windows 
	 based and have the same attributes set as the value passed.
	*/
	private boolean HasDosAttributes(int attributes)
	{
		boolean result = false;
		if ((known.getValue() & Known.ExternalAttributes.getValue()) != 0)
		{
			if (((getHostSystem() == HostSystemID.Msdos.getValue()) || (getHostSystem() == HostSystemID.WindowsNT.getValue())) && (getExternalFileAttributes() & attributes) == attributes)
			{
				result = true;
			}
		}
		return result;
	}

	/** 
	 Gets the compatability information for the <see cref="ExternalFileAttributes">external file attribute</see>
	 If the external file attributes are compatible with MS-DOS and can be read
	 by PKZIP for DOS version 2.04g then this value will be zero.  Otherwise the value
	 will be non-zero and identify the host system on which the attributes are compatible.
	 
	 		
	 
	 The values for this as defined in the Zip File format and by others are shown below.  The values are somewhat
	 misleading in some cases as they are not all used as shown.  You should consult the relevant documentation
	 to obtain up to date and correct information.  The modified appnote by the infozip group is
	 particularly helpful as it documents a lot of peculiarities.  The document is however a little dated.
	 <list type="table">
	 <item>0 - MS-DOS and OS/2 (FAT / VFAT / FAT32 file systems)</item>
	 <item>1 - Amiga</item>
	 <item>2 - OpenVMS</item>
	 <item>3 - Unix</item>
	 <item>4 - VM/CMS</item>
	 <item>5 - Atari ST</item>
	 <item>6 - OS/2 HPFS</item>
	 <item>7 - Macintosh</item>
	 <item>8 - Z-System</item>
	 <item>9 - CP/M</item>
	 <item>10 - Windows NTFS</item>
	 <item>11 - MVS (OS/390 - Z/OS)</item>
	 <item>12 - VSE</item>
	 <item>13 - Acorn Risc</item>
	 <item>14 - VFAT</item>
	 <item>15 - Alternate MVS</item>
	 <item>16 - BeOS</item>
	 <item>17 - Tandem</item>
	 <item>18 - OS/400</item>
	 <item>19 - OS/X (Darwin)</item>
	 <item>99 - WinZip AES</item>
	 <item>remainder - unused</item>
	 </list>
	 
	*/
	public final int getHostSystem()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return (versionMadeBy >>> 8) & 0xff;
	}

	public final void setHostSystem(int value)
	{
		versionMadeBy &= 0xff;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: versionMadeBy |= (ushort)((value & 0xff) << 8);
		versionMadeBy |= (short)((value & 0xff) << 8);
	}

	/** 
	 Get minimum Zip feature version required to extract this entry
	 		
	 
	 Minimum features are defined as:<br/>
	 1.0 - Default value<br/>
	 1.1 - File is a volume label<br/>
	 2.0 - File is a folder/directory<br/>
	 2.0 - File is compressed using Deflate compression<br/>
	 2.0 - File is encrypted using traditional encryption<br/>
	 2.1 - File is compressed using Deflate64<br/>
	 2.5 - File is compressed using PKWARE DCL Implode<br/>
	 2.7 - File is a patch data set<br/>
	 4.5 - File uses Zip64 format extensions<br/>
	 4.6 - File is compressed using BZIP2 compression<br/>
	 5.0 - File is encrypted using DES<br/>
	 5.0 - File is encrypted using 3DES<br/>
	 5.0 - File is encrypted using original RC2 encryption<br/>
	 5.0 - File is encrypted using RC4 encryption<br/>
	 5.1 - File is encrypted using AES encryption<br/>
	 5.1 - File is encrypted using corrected RC2 encryption<br/>
	 5.1 - File is encrypted using corrected RC2-64 encryption<br/>
	 6.1 - File is encrypted using non-OAEP key wrapping<br/>
	 6.2 - Central directory encryption (not confirmed yet)<br/>
	 6.3 - File is compressed using LZMA<br/>
	 6.3 - File is compressed using PPMD+<br/>
	 6.3 - File is encrypted using Blowfish<br/>
	 6.3 - File is encrypted using Twofish<br/>
	 
	 @see CanDecompress
	*/
	public final int getVersion()
	{
			// Return recorded version if known.
		if (versionToExtract != 0)
		{
			return versionToExtract & 0x00ff; // Only lower order byte. High order is O/S file system.
		}
		else
		{
			int result = 10;
			if (AESKeySize > 0)
			{
				result = ZipConstants.VERSION_AES; // Ver 5.1 = AES
			}
			else if (getCentralHeaderRequiresZip64())
			{
				result = ZipConstants.VersionZip64;
			}
			else if (CompressionMethod.Deflated == method)
			{
				result = 20;
			}
			else if (getIsDirectory() == true)
			{
				result = 20;
			}
			else if (getIsCrypted() == true)
			{
				result = 20;
			}
			else if (HasDosAttributes(0x08))
			{
				result = 11;
			}
			return result;
		}
	}

	/** 
	 Get a value indicating whether this entry can be decompressed by the library.
	 
	 This is based on the <see cref="Version"></see> and 
	 wether the <see cref="IsCompressionMethodSupported()">compression method</see> is supported.
	*/
	public final boolean getCanDecompress()
	{
		return (getVersion() <= ZipConstants.VersionMadeBy) && ((getVersion() == 10) || (getVersion() == 11) || (getVersion() == 20) || (getVersion() == 45) || (getVersion() == 51)) && IsCompressionMethodSupported();
	}

	/** 
	 Force this entry to be recorded using Zip64 extensions.
	*/
	public final void ForceZip64()
	{
		forceZip64_ = true;
	}

	/** 
	 Get a value indicating wether Zip64 extensions were forced.
	 
	 @return A <see cref="bool"/> value of true if Zip64 extensions have been forced on; false if not.
	*/
	public final boolean IsZip64Forced()
	{
		return forceZip64_;
	}

	/** 
	 Gets a value indicating if the entry requires Zip64 extensions 
	 to store the full entry values.
	 
	 <value>A <see cref="bool"/> value of true if a local header requires Zip64 extensions; false if not.</value>
	*/
	public final boolean getLocalHeaderRequiresZip64()
	{
		boolean result = forceZip64_;

		if (!result)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong trueCompressedSize = compressedSize;
			long trueCompressedSize = compressedSize;

			if ((versionToExtract == 0) && getIsCrypted())
			{
				trueCompressedSize += ZipConstants.CryptoHeaderSize;
			}

				// TODO: A better estimation of the true limit based on compression overhead should be used
				// to determine when an entry should use Zip64.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: result = ((this.size >= uint.MaxValue) || (trueCompressedSize >= uint.MaxValue)) && ((versionToExtract == 0) || (versionToExtract >= ZipConstants.VersionZip64));
			result = ((this.size >= Integer.MAX_VALUE) || (trueCompressedSize >= Integer.MAX_VALUE)) && ((versionToExtract == 0) || (versionToExtract >= ZipConstants.VersionZip64));
		}

		return result;
	}

	/** 
	 Get a value indicating wether the central directory entry requires Zip64 extensions to be stored.
	*/
	public final boolean getCentralHeaderRequiresZip64()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return LocalHeaderRequiresZip64 || (offset >= uint.MaxValue);
		return getLocalHeaderRequiresZip64() || (offset >= Integer.MAX_VALUE);
	}

	/** 
	 Get/Set DosTime value.
	 
	 
	 The MS-DOS date format can only represent dates between 1/1/1980 and 12/31/2107.
	 
	*/
	public final long getDosTime()
	{
		if ((known.getValue() & Known.Time.getValue()) == 0)
		{
			return 0;
		}
		else
		{
			return dosTime;
		}
	}

	public final void setDosTime(long value)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: dosTime = (uint)value;
			dosTime = (int)value;
		}

		known = Rasad.Core.Compression.Zip.ZipEntry.Known.forValue(known.getValue() | Known.Time.getValue());
	}

	/** 
	 Gets/Sets the time of last modification of the entry.
	 
	 
	 The <see cref="DosTime"></see> property is updated to match this as far as possible.
	 
	*/
	public final LocalDateTime getDateTime()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint sec = Math.Min(59, 2 * (dosTime & 0x1f));
		int sec = Math.min(59, 2 * (dosTime & 0x1f));
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint min = Math.Min(59, (dosTime >> 5) & 0x3f);
		int min = Math.min(59, (dosTime >>> 5) & 0x3f);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint hrs = Math.Min(23, (dosTime >> 11) & 0x1f);
		int hrs = Math.min(23, (dosTime >>> 11) & 0x1f);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint mon = Math.Max(1, Math.Min(12, ((dosTime >> 21) & 0xf)));
		int mon = Math.max(1, Math.min(12, ((dosTime >>> 21) & 0xf)));
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint year = ((dosTime >> 25) & 0x7f) + 1980;
		int year = ((dosTime >>> 25) & 0x7f) + 1980;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		int day = Math.max(1, Math.min(getDateTime().DaysInMonth((int)year, (int)mon), (int)((dosTime >>> 16) & 0x1f)));
		return new DateTime((int)year, (int)mon, day, (int)hrs, (int)min, (int)sec);
	}

	public final void setDateTime(LocalDateTime value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint year = (uint) value.Year;
		int year = (int) value.getYear();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint month = (uint) value.Month;
		int month = (int) value.getMonthValue();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint day = (uint) value.Day;
		int day = (int) value.getDayOfMonth();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint hour = (uint) value.Hour;
		int hour = (int) value.getHour();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint minute = (uint) value.Minute;
		int minute = (int) value.getMinute();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint second = (uint) value.Second;
		int second = (int) value.getSecond();

		if (year < 1980)
		{
			year = 1980;
			month = 1;
			day = 1;
			hour = 0;
			minute = 0;
			second = 0;
		}
		else if (year > 2107)
		{
			year = 2107;
			month = 12;
			day = 31;
			hour = 23;
			minute = 59;
			second = 59;
		}

//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		setDosTime(((year - 1980) & 0x7f) << 25 | (month << 21) | (day << 16) | (hour << 11) | (minute << 5) | (second >>> 1));
	}

	/** 
	 Returns the entry name.
	 
	 
	 The unix naming convention is followed.
	 Path components in the entry should always separated by forward slashes ('/').
	 Dos device names like C: should also be removed.
	 See the <see cref="ZipNameTransform"/> class, or <see cref="CleanName(string)"/>
	
	*/
	public final String getName()
	{
		return name;
	}

	/** 
	 Gets/Sets the size of the uncompressed data.
	 
	 @return 
	 The size or -1 if unknown.
	 
	 Setting the size before adding an entry to an archive can help
	 avoid compatability problems with some archivers which dont understand Zip64 extensions.
	*/
	public final long getSize()
	{
		return (known.getValue() & Known.Size.getValue()) != 0 ? (long)size : -1L;
	}
	public final void setSize(long value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: this.size = (ulong)value;
		this.size = (long)value;
		this.known = Rasad.Core.Compression.Zip.ZipEntry.Known.forValue(this.known.getValue() | Known.Size.getValue());
	}

	/** 
	 Gets/Sets the size of the compressed data.
	 
	 @return 
	 The compressed entry size or -1 if unknown.
	 
	*/
	public final long getCompressedSize()
	{
		return (known.getValue() & Known.CompressedSize.getValue()) != 0 ? (long)compressedSize : -1L;
	}
	public final void setCompressedSize(long value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: this.compressedSize = (ulong)value;
		this.compressedSize = (long)value;
		this.known = Rasad.Core.Compression.Zip.ZipEntry.Known.forValue(this.known.getValue() | Known.CompressedSize.getValue());
	}

	/** 
	 Gets/Sets the crc of the uncompressed data.
	 
	 @exception System.ArgumentOutOfRangeException
	 Crc is not in the range 0..0xffffffffL
	 
	 @return 
	 The crc value or -1 if unknown.
	 
	*/
	public final long getCrc()
	{
		return (known.getValue() & Known.Crc.getValue()) != 0 ? crc & 0xffffffffL : -1L;
	}
	public final void setCrc(long value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (((ulong)crc & 0xffffffff00000000L) != 0)
		if (((long)crc & 0xffffffff00000000L) != 0)
		{
			throw new IndexOutOfBoundsException("value");
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: this.crc = (uint)value;
		this.crc = (int)value;
		this.known = Rasad.Core.Compression.Zip.ZipEntry.Known.forValue(this.known.getValue() | Known.Crc.getValue());
	}

	/** 
	 Gets/Sets the compression method. Only Deflated and Stored are supported.
	 
	 @return 
	 The compression method for this entry
	 
	 <see cref="Rasad.Core.Compression.Zip.CompressionMethod.Deflated"/>
	 <see cref="Rasad.Core.Compression.Zip.CompressionMethod.Stored"/>
	*/
	public final CompressionMethod getCompressionMethod()
	{
		return method;
	}

	public final void setCompressionMethod(CompressionMethod value)
	{
		if (!IsCompressionMethodSupported(value))
		{
			throw new UnsupportedOperationException("Compression method not supported");
		}
		this.method = value;
	}

	/** 
	 Gets the compression method for outputting to the local or central header.
	 Returns same value as CompressionMethod except when AES encrypting, which
	 places 99 in the method and places the real method in the extra data.
	*/
	public final CompressionMethod getCompressionMethodForHeader()
	{
		return (AESKeySize > 0) ? CompressionMethod.WinZipAES : method;
	}

	/** 
	 Gets/Sets the extra data.
	 
	 @exception System.ArgumentOutOfRangeException
	 Extra data is longer than 64KB (0xffff) bytes.
	 
	 @return 
	 Extra data or null if not set.
	 
	*/

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getExtraData()
	public final byte[] getExtraData()
	{
// TODO: This is slightly safer but less efficient.  Think about wether it should change.
//				return (byte[]) extra.Clone();
		return extra;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setExtraData(byte[] value)
	public final void setExtraData(byte[] value)
	{
		if (value == null)
		{
			extra = null;
		}
		else
		{
			if (value.length > 0xffff)
			{
				throw new IndexOutOfBoundsException("value");
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: extra = new byte[value.Length];
			extra = new byte[value.length];
			System.arraycopy(value, 0, extra, 0, value.length);
		}
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_1 && !NETCF_2_0
	/** 
	 For AES encrypted files returns or sets the number of bits of encryption (128, 192 or 256).
	 When setting, only 0 (off), 128 or 256 is supported.
	*/
	public final int getAESKeySize()
	{
			// the strength (1 or 3) is in the entry header
		switch (_aesEncryptionStrength)
		{
			case 0:
				return 0; // Not AES
			case 1:
				return 128;
			case 2:
				return 192; // Not used by WinZip
			case 3:
				return 256;
			default:
				throw new ZipException("Invalid AESEncryptionStrength " + _aesEncryptionStrength);
		}
	}
	public final void setAESKeySize(int value)
	{
		switch (value)
		{
			case 0:
				_aesEncryptionStrength = 0;
				break;
			case 128:
				_aesEncryptionStrength = 1;
				break;
			case 256:
				_aesEncryptionStrength = 3;
				break;
			default:
				throw new ZipException("AESKeySize must be 0, 128 or 256: " + value);
		}
	}

	/** 
	 AES Encryption strength for storage in extra data in entry header.
	 1 is 128 bit, 2 is 192 bit, 3 is 256 bit.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internal byte getAESEncryptionStrength()
	public final byte getAESEncryptionStrength()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (byte)_aesEncryptionStrength;
		return (byte)_aesEncryptionStrength;
	}
//#else
	/** 
	 AES unsupported prior to .NET 2.0
	*/
	public int AESKeySize;
//#endif

	/** 
	 Returns the length of the salt, in bytes 
	*/
	public final int getAESSaltLen()
	{
			// Key size -> Salt length: 128 bits = 8 bytes, 192 bits = 12 bytes, 256 bits = 16 bytes.
		return AESKeySize / 16;
	}

	/** 
	 Number of extra bytes required to hold the AES Header fields (Salt, Pwd verify, AuthCode)
	*/
	public final int getAESOverheadSize()
	{
			// File format:
			//   Bytes		Content
			// Variable		Salt value
			//     2		Password verification value
			// Variable		Encrypted file data
			//    10		Authentication code
		return 12 + getAESSaltLen();
	}

	/** 
	 Process extra data fields updating the entry based on the contents.
	 
	 @param localHeader True if the extra data fields should be handled
	 for a local header, rather than for a central header.
	 
	*/
	public final void ProcessExtraData(boolean localHeader)
	{
		ZipExtraData extraData = new ZipExtraData(this.extra);

		if (extraData.Find(0x0001))
		{
			// Version required to extract is ignored here as some archivers dont set it correctly
			// in theory it should be version 45 or higher

			// The recorded size will change but remember that this is zip64.
			forceZip64_ = true;

			if (extraData.getValueLength() < 4)
			{
				throw new ZipException("Extra data extended Zip64 information length is invalid");
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (localHeader || (size == uint.MaxValue))
			if (localHeader || (size == Integer.MAX_VALUE))
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: size = (ulong)extraData.ReadLong();
				size = (long)extraData.ReadLong();
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (localHeader || (compressedSize == uint.MaxValue))
			if (localHeader || (compressedSize == Integer.MAX_VALUE))
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: compressedSize = (ulong)extraData.ReadLong();
				compressedSize = (long)extraData.ReadLong();
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (!localHeader && (offset == uint.MaxValue))
			if (!localHeader && (offset == Integer.MAX_VALUE))
			{
				offset = extraData.ReadLong();
			}

			// Disk number on which file starts is ignored
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (((versionToExtract & 0xff) >= ZipConstants.VersionZip64) && ((size == uint.MaxValue) || (compressedSize == uint.MaxValue)))
			if (((versionToExtract & 0xff) >= ZipConstants.VersionZip64) && ((size == Integer.MAX_VALUE) || (compressedSize == Integer.MAX_VALUE)))
			{
				throw new ZipException("Zip64 Extended information required but is missing.");
			}
		}

		if (extraData.Find(10))
		{
			// No room for any tags.
			if (extraData.getValueLength() < 4)
			{
				throw new ZipException("NTFS Extra data invalid");
			}

			extraData.ReadInt(); // Reserved

			while (extraData.getUnreadCount() >= 4)
			{
				int ntfsTag = extraData.ReadShort();
				int ntfsLength = extraData.ReadShort();
				if (ntfsTag == 1)
				{
					if (ntfsLength >= 24)
					{
						long lastModification = extraData.ReadLong();
						long lastAccess = extraData.ReadLong();
						long createTime = extraData.ReadLong();

						setDateTime(getDateTime().FromFileTime(lastModification));
					}
					break;
				}
				else
				{
					// An unknown NTFS tag so simply skip it.
					extraData.Skip(ntfsLength);
				}
			}
		}
		else if (extraData.Find(0x5455))
		{
			int length = extraData.getValueLength();
			int flags = extraData.ReadByte();

			// Can include other times but these are ignored.  Length of data should
			// actually be 1 + 4 * no of bits in flags.
			if (((flags & 1) != 0) && (length >= 5))
			{
				int iTime = extraData.ReadInt();

				setDateTime(((new DateTime(1970, 1, 1, 0, 0, 0)).ToUniversalTime() + new TimeSpan(0, 0, 0, iTime, 0)).ToLocalTime());
			}
		}
		if (method == CompressionMethod.WinZipAES)
		{
			ProcessAESExtraData(extraData);
		}
	}

	// For AES the method in the entry is 99, and the real compression method is in the extradata
	//
	private void ProcessAESExtraData(ZipExtraData extraData)
	{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_1 && !NETCF_2_0
		if (extraData.Find(0x9901))
		{
			// Set version and flag for Zipfile.CreateAndInitDecryptionStream
			versionToExtract = (short)ZipConstants.VERSION_AES; // Ver 5.1 = AES see "Version" getter
			// Set StrongEncryption flag for ZipFile.CreateAndInitDecryptionStream
			setFlags(getFlags() | GeneralBitFlags.StrongEncryption.getValue());
			//
			// Unpack AES extra data field see http://www.winzip.com/aes_info.htm
			int length = extraData.getValueLength(); // Data size currently 7
			if (length < 7)
			{
				throw new ZipException("AES Extra Data Length " + length + " invalid.");
			}
			int ver = extraData.ReadShort(); // Version number (1=AE-1 2=AE-2)
			int vendorId = extraData.ReadShort(); // 2-character vendor ID 0x4541 = "AE"
			int encrStrength = extraData.ReadByte(); // encryption strength 1 = 128 2 = 192 3 = 256
			int actualCompress = extraData.ReadShort(); // The actual compression method used to compress the file
			_aesVer = ver;
			_aesEncryptionStrength = encrStrength;
			method = CompressionMethod.forValue(actualCompress);
		}
		else
		{
			throw new ZipException("AES Extra Data missing");
		}
//#else
			throw new ZipException("AES unsupported");
//#endif
	}

	/** 
	 Gets/Sets the entry comment.
	 
	 @exception System.ArgumentOutOfRangeException
	 If comment is longer than 0xffff.
	 
	 @return 
	 The comment or null if not set.
	 
	 
	 A comment is only available for entries when read via the <see cref="ZipFile"/> class.
	 The <see cref="ZipInputStream"/> class doesnt have the comment data available.
	 
	*/
	public final String getComment()
	{
		return comment;
	}
	public final void setComment(String value)
	{
			// This test is strictly incorrect as the length is in characters
			// while the storage limit is in bytes.
			// While the test is partially correct in that a comment of this length or greater 
			// is definitely invalid, shorter comments may also have an invalid length
			// where there are multi-byte characters
			// The full test is not possible here however as the code page to apply conversions with
			// isnt available.
		if ((value != null) && (value.length() > 0xffff))
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("value");
//#else
			throw new IndexOutOfBoundsException("value", "cannot exceed 65535");
//#endif
		}

		comment = value;
	}

	/** 
	 Gets a value indicating if the entry is a directory.
	 however.
	 
	 
	 A directory is determined by an entry name with a trailing slash '/'.
	 The external file attributes can also indicate an entry is for a directory.
	 Currently only dos/windows attributes are tested in this manner.
	 The trailing slash convention should always be followed.
	 
	*/
	public final boolean getIsDirectory()
	{
		int nameLength = name.length();
		boolean result = ((nameLength > 0) && ((name.charAt(nameLength - 1) == '/') || (name.charAt(nameLength - 1) == '\\'))) || HasDosAttributes(16);
		return result;
	}

	/** 
	 Get a value of true if the entry appears to be a file; false otherwise
	 
	 
	 This only takes account of DOS/Windows attributes.  Other operating systems are ignored.
	 For linux and others the result may be incorrect.
	 
	*/
	public final boolean getIsFile()
	{
		return !getIsDirectory() && !HasDosAttributes(8);
	}

	/** 
	 Test entry to see if data can be extracted.
	 
	 @return Returns true if data can be extracted for this entry; false otherwise.
	*/
	public final boolean IsCompressionMethodSupported()
	{
		return IsCompressionMethodSupported(getCompressionMethod());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICloneable Members
	/** 
	 Creates a copy of this zip entry.
	 
	 @return An <see cref="Object"/> that is a copy of the current instance.
	*/
	public final Object Clone()
	{
		ZipEntry result = (ZipEntry)this.clone();

		// Ensure extra data is unique if it exists.
		if (extra != null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: result.extra = new byte[extra.Length];
			result.extra = new byte[extra.length];
			System.arraycopy(extra, 0, result.extra, 0, extra.length);
		}

		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Gets a string representation of this ZipEntry.
	 
	 @return A readable textual representation of this <see cref="ZipEntry"/>
	*/
	@Override
	public String toString()
	{
		return name;
	}

	/** 
	 Test a <see cref="CompressionMethod">compression method</see> to see if this library
	 supports extracting data compressed with that method
	 
	 @param method The compression method to test.
	 @return Returns true if the compression method is supported; false otherwise
	*/
	public static boolean IsCompressionMethodSupported(CompressionMethod method)
	{
		return (method == CompressionMethod.Deflated) || (method == CompressionMethod.Stored);
	}

	/** 
	 Cleans a name making it conform to Zip file conventions.
	 Devices names ('c:\') and UNC share names ('\\server\share') are removed
	 and forward slashes ('\') are converted to back slashes ('/').
	 Names are made relative by trimming leading slashes which is compatible
	 with the ZIP naming convention.
	 
	 @param name The name to clean
	 @return The 'cleaned' name.
	 
	 The @see ZipNameTransform Zip name transform class is more flexible.
	 
	*/
	public static String CleanName(String name)
	{
		if (name == null)
		{
			return "";
		}

		if (Paths.get(name).getParent() != null)
		{
			// NOTE:
			// for UNC names...  \\machine\share\zoom\beet.txt gives \zoom\beet.txt
			name = name.substring(Path.GetPathRoot(name).length());
		}

		name = name.replace("\\", "/");

		while ((name.length() > 0) && (name.charAt(0) == '/'))
		{
			name = tangible.StringHelper.remove(name, 0, 1);
		}
		return name;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private Known known = Known.values()[0];
	private int externalFileAttributes = -1; // contains external attributes (O/S dependant)

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort versionMadeBy;
	private short versionMadeBy; // Contains host system and version information
											// only relevant for central header entries

	private String name;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong size;
	private long size;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong compressedSize;
	private long compressedSize;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort versionToExtract;
	private short versionToExtract; // Version required to extract (library handles <= 2.0)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint crc;
	private int crc;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint dosTime;
	private int dosTime;

	private CompressionMethod method = CompressionMethod.Deflated;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] extra;
	private byte[] extra;
	private String comment;

	private int flags; // general purpose bit flags

	private long zipFileIndex = -1; // used by ZipFile
	private long offset; // used by ZipFile and ZipOutputStream

	private boolean forceZip64_;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte cryptoCheckValue_;
	private byte cryptoCheckValue_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_1 && !NETCF_2_0
	private int _aesVer; // Version number (2 = AE-2 ?). Assigned but not used.
	private int _aesEncryptionStrength; // Encryption strength 1 = 128 2 = 192 3 = 256
//#endif
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}