package Rasad.Core.Compression.Tar;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.time.*;

// TarHeader.cs
//
// Copyright (C) 2001 Mike Krueger
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

// HISTORY
//	27-07-2012	Z-1647	Added handling of Tar formats for files over 8GB such as Posix and Pax

/* The tar format and its POSIX successor PAX have a long history which makes for compatability
   issues when creating and reading files.

   This is further complicated by a large number of programs with variations on formats
   One common issue is the handling of names longer than 100 characters.
   GNU style long names are currently supported.

This is the ustar (Posix 1003.1) header.

struct header 
{
	char t_name[100]; //   0 Filename
	char t_mode[8]; // 100 Permissions
	char t_uid[8]; // 108 Numerical User ID
	char t_gid[8]; // 116 Numerical Group ID
	char t_size[12]; // 124 Filesize
	char t_mtime[12]; // 136 st_mtime
	char t_chksum[8]; // 148 Checksum
	char t_typeflag; // 156 Type of File
	char t_linkname[100]; // 157 Target of Links
	char t_magic[6]; // 257 "ustar" or other...
	char t_version[2]; // 263 Version fixed to 00
	char t_uname[32]; // 265 User Name
	char t_gname[32]; // 297 Group Name
	char t_devmajor[8]; // 329 Major for devices
	char t_devminor[8]; // 337 Minor for devices
	char t_prefix[155]; // 345 Prefix for t_name
	char t_mfill[12]; // 500 Filler up to 512
};

*/





/** 
 This class encapsulates the Tar Entry Header used in Tar Archives.
 The class also holds a number of tar constants, used mostly in headers.
*/
public class TarHeader implements Cloneable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constants
	/** 
	 The length of the name field in a header buffer.
	*/
	public static final int NAMELEN = 100;

	/** 
	 The length of the mode field in a header buffer.
	*/
	public static final int MODELEN = 8;

	/** 
	 The length of the user id field in a header buffer.
	*/
	public static final int UIDLEN = 8;

	/** 
	 The length of the group id field in a header buffer.
	*/
	public static final int GIDLEN = 8;

	/** 
	 The length of the checksum field in a header buffer.
	*/
	public static final int CHKSUMLEN = 8;

	/** 
	 Offset of checksum in a header buffer.
	*/
	public static final int CHKSUMOFS = 148;

	/** 
	 The length of the size field in a header buffer.
	*/
	public static final int SIZELEN = 12;

	/** 
	 The length of the magic field in a header buffer.
	*/
	public static final int MAGICLEN = 6;

	/** 
	 The length of the version field in a header buffer.
	*/
	public static final int VERSIONLEN = 2;

	/** 
	 The length of the modification time field in a header buffer.
	*/
	public static final int MODTIMELEN = 12;

	/** 
	 The length of the user name field in a header buffer.
	*/
	public static final int UNAMELEN = 32;

	/** 
	 The length of the group name field in a header buffer.
	*/
	public static final int GNAMELEN = 32;

	/** 
	 The length of the devices field in a header buffer.
	*/
	public static final int DEVLEN = 8;

	//
	// LF_ constants represent the "type" of an entry
	//

	/** 
	  The "old way" of indicating a normal file.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_OLDNORM = 0;
	public static final byte LF_OLDNORM = 0;

	/** 
	 Normal file type.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_NORMAL = (byte) '0';
	public static final byte LF_NORMAL = (byte) '0';

	/** 
	 Link file type.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_LINK = (byte) '1';
	public static final byte LF_LINK = (byte) '1';

	/** 
	 Symbolic link file type.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_SYMLINK = (byte) '2';
	public static final byte LF_SYMLINK = (byte) '2';

	/** 
	 Character device file type.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_CHR = (byte) '3';
	public static final byte LF_CHR = (byte) '3';

	/** 
	 Block device file type.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_BLK = (byte) '4';
	public static final byte LF_BLK = (byte) '4';

	/** 
	 Directory file type.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_DIR = (byte) '5';
	public static final byte LF_DIR = (byte) '5';

	/** 
	 FIFO (pipe) file type.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_FIFO = (byte) '6';
	public static final byte LF_FIFO = (byte) '6';

	/** 
	 Contiguous file type.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_CONTIG = (byte) '7';
	public static final byte LF_CONTIG = (byte) '7';

	/** 
	 Posix.1 2001 global extended header
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_GHDR = (byte) 'g';
	public static final byte LF_GHDR = (byte) 'g';

	/** 
	 Posix.1 2001 extended header
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_XHDR = (byte) 'x';
	public static final byte LF_XHDR = (byte) 'x';

	// POSIX allows for upper case ascii type as extensions

	/** 
	 Solaris access control list file type
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_ACL = (byte) 'A';
	public static final byte LF_ACL = (byte) 'A';

	/** 
	 GNU dir dump file type
	 This is a dir entry that contains the names of files that were in the
	 dir at the time the dump was made
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_GNU_DUMPDIR = (byte) 'D';
	public static final byte LF_GNU_DUMPDIR = (byte) 'D';

	/** 
	 Solaris Extended Attribute File
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_EXTATTR = (byte) 'E';
	public static final byte LF_EXTATTR = (byte) 'E';

	/** 
	 Inode (metadata only) no file content
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_META = (byte) 'I';
	public static final byte LF_META = (byte) 'I';

	/** 
	 Identifies the next file on the tape as having a long link name
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_GNU_LONGLINK = (byte) 'K';
	public static final byte LF_GNU_LONGLINK = (byte) 'K';

	/** 
	 Identifies the next file on the tape as having a long name
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_GNU_LONGNAME = (byte) 'L';
	public static final byte LF_GNU_LONGNAME = (byte) 'L';

	/** 
	 Continuation of a file that began on another volume
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_GNU_MULTIVOL = (byte) 'M';
	public static final byte LF_GNU_MULTIVOL = (byte) 'M';

	/** 
	 For storing filenames that dont fit in the main header (old GNU)
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_GNU_NAMES = (byte) 'N';
	public static final byte LF_GNU_NAMES = (byte) 'N';

	/** 
	 GNU Sparse file
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_GNU_SPARSE = (byte) 'S';
	public static final byte LF_GNU_SPARSE = (byte) 'S';

	/** 
	 GNU Tape/volume header ignore on extraction
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const byte LF_GNU_VOLHDR = (byte) 'V';
	public static final byte LF_GNU_VOLHDR = (byte) 'V';

	/** 
	 The magic tag representing a POSIX tar archive.  (includes trailing NULL)
	*/
	public static final String TMAGIC = "ustar ";

	/** 
	 The magic tag representing an old GNU tar archive where version is included in magic and overwrites it
	*/
	public static final String GNU_TMAGIC = "ustar  ";

	private static final long timeConversionFactor = 10000000L; // 1 tick == 100 nanoseconds
	private final static LocalDateTime dateTime1970 = LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
	 Initialise a default TarHeader instance
	*/
	public TarHeader()
	{
		setMagic(TMAGIC);
		setVersion(" ");

		setName("");
		setLinkName("");

		setUserId(defaultUserId);
		setGroupId(defaultGroupId);
		setUserName(defaultUser);
		setGroupName(defaultGroupName);
		setSize(0);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	/** 
	 Get/set the name for this tar entry.
	 
	 @exception ArgumentNullException Thrown when attempting to set the property to null.
	*/
	public final String getName()
	{
		return name;
	}
	public final void setName(String value)
	{
		if (value == null)
		{
			throw new NullPointerException("value");
		}
		name = value;
	}

	/** 
	 Get the name of this entry.
	 
	 @return The entry's name.
	*/
	@Deprecated
	public final String GetName()
	{
		return name;
	}

	/** 
	 Get/set the entry's Unix style permission mode.
	*/
	public final int getMode()
	{
		return mode;
	}
	public final void setMode(int value)
	{
		mode = value;
	}


	/** 
	 The entry's user id.
	 
	 
	 This is only directly relevant to unix systems.
	 The default is zero.
	 
	*/
	public final int getUserId()
	{
		return userId;
	}
	public final void setUserId(int value)
	{
		userId = value;
	}


	/** 
	 Get/set the entry's group id.
	 
	 
	 This is only directly relevant to linux/unix systems.
	 The default value is zero.
	 
	*/
	public final int getGroupId()
	{
		return groupId;
	}
	public final void setGroupId(int value)
	{
		groupId = value;
	}


	/** 
	 Get/set the entry's size.
	 
	 @exception ArgumentOutOfRangeException Thrown when setting the size to less than zero.
	*/
	public final long getSize()
	{
		return size;
	}
	public final void setSize(long value)
	{
		if (value < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("value");
//#else
			throw new IndexOutOfBoundsException("value", "Cannot be less than zero");
//#endif
		}
		size = value;
	}


	/** 
	 Get/set the entry's modification time.
	 
	 
	 The modification time is only accurate to within a second.
	 
	 @exception ArgumentOutOfRangeException Thrown when setting the date time to less than 1/1/1970.
	*/
	public final LocalDateTime getModTime()
	{
		return modTime;
	}
	public final void setModTime(LocalDateTime value)
	{
		if (value.compareTo(dateTime1970) < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("value");
//#else
			throw new IndexOutOfBoundsException("value", "ModTime cannot be before Jan 1st 1970");
//#endif
		}
		modTime = LocalDateTime.of(value.getYear(), value.getMonthValue(), value.getDayOfMonth(), value.getHour(), value.getMinute(), value.getSecond());
	}


	/** 
	 Get the entry's checksum.  This is only valid/updated after writing or reading an entry.
	*/
	public final int getChecksum()
	{
		return checksum;
	}


	/** 
	 Get value of true if the header checksum is valid, false otherwise.
	*/
	public final boolean getIsChecksumValid()
	{
		return isChecksumValid;
	}


	/** 
	 Get/set the entry's type flag.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getTypeFlag()
	public final byte getTypeFlag()
	{
		return typeFlag;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setTypeFlag(byte value)
	public final void setTypeFlag(byte value)
	{
		typeFlag = value;
	}


	/** 
	 The entry's link name.
	 
	 @exception ArgumentNullException Thrown when attempting to set LinkName to null.
	*/
	public final String getLinkName()
	{
		return linkName;
	}
	public final void setLinkName(String value)
	{
		if (value == null)
		{
			throw new NullPointerException("value");
		}
		linkName = value;
	}


	/** 
	 Get/set the entry's magic tag.
	 
	 @exception ArgumentNullException Thrown when attempting to set Magic to null.
	*/
	public final String getMagic()
	{
		return magic;
	}
	public final void setMagic(String value)
	{
		if (value == null)
		{
			throw new NullPointerException("value");
		}
		magic = value;
	}


	/** 
	 The entry's version.
	 
	 @exception ArgumentNullException Thrown when attempting to set Version to null.
	*/
	public final String getVersion()
	{
		return version;
	}

	public final void setVersion(String value)
	{
		if (value == null)
		{
			throw new NullPointerException("value");
		}
		version = value;
	}


	/** 
	 The entry's user name.
	*/
	public final String getUserName()
	{
		return userName;
	}
	public final void setUserName(String value)
	{
		if (value != null)
		{
			userName = value.substring(0, Math.min(UNAMELEN, value.length()));
		}
		else
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0 || NETCF_2_0
			String currentUser = "PocketPC";
//#else
			String currentUser = Environment.UserName;
//#endif
			if (currentUser.length() > UNAMELEN)
			{
				currentUser = currentUser.substring(0, UNAMELEN);
			}
			userName = currentUser;
		}
	}


	/** 
	 Get/set the entry's group name.
	 
	 
	 This is only directly relevant to unix systems.
	 
	*/
	public final String getGroupName()
	{
		return groupName;
	}
	public final void setGroupName(String value)
	{
		if (value == null)
		{
			groupName = "None";
		}
		else
		{
			groupName = value;
		}
	}


	/** 
	 Get/set the entry's major device number.
	*/
	public final int getDevMajor()
	{
		return devMajor;
	}
	public final void setDevMajor(int value)
	{
		devMajor = value;
	}


	/** 
	 Get/set the entry's minor device number.
	*/
	public final int getDevMinor()
	{
		return devMinor;
	}
	public final void setDevMinor(int value)
	{
		devMinor = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICloneable Members
	/** 
	 Create a new <see cref="TarHeader"/> that is a copy of the current instance.
	 
	 @return A new <see cref="Object"/> that is a copy of the current instance.
	*/
	public final Object Clone()
	{
		return clone();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Parse TarHeader information from a header buffer.
	 
	 @param  header
	 The tar entry header buffer to get information from.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void ParseBuffer(byte[] header)
	public final void ParseBuffer(byte[] header)
	{
		if (header == null)
		{
			throw new NullPointerException("header");
		}

		int offset = 0;

		name = ParseName(header, offset, NAMELEN).toString();
		offset += NAMELEN;

		mode = (int)ParseOctal(header, offset, MODELEN);
		offset += MODELEN;

		setUserId((int)ParseOctal(header, offset, UIDLEN));
		offset += UIDLEN;

		setGroupId((int)ParseOctal(header, offset, GIDLEN));
		offset += GIDLEN;

		setSize(ParseBinaryOrOctal(header, offset, SIZELEN));
		offset += SIZELEN;

		setModTime(GetDateTimeFromCTime(ParseOctal(header, offset, MODTIMELEN)));
		offset += MODTIMELEN;

		checksum = (int)ParseOctal(header, offset, CHKSUMLEN);
		offset += CHKSUMLEN;

		setTypeFlag(header[offset++]);

		setLinkName(ParseName(header, offset, NAMELEN).toString());
		offset += NAMELEN;

		setMagic(ParseName(header, offset, MAGICLEN).toString());
		offset += MAGICLEN;

		setVersion(ParseName(header, offset, VERSIONLEN).toString());
		offset += VERSIONLEN;

		setUserName(ParseName(header, offset, UNAMELEN).toString());
		offset += UNAMELEN;

		setGroupName(ParseName(header, offset, GNAMELEN).toString());
		offset += GNAMELEN;

		setDevMajor((int)ParseOctal(header, offset, DEVLEN));
		offset += DEVLEN;

		setDevMinor((int)ParseOctal(header, offset, DEVLEN));

		// Fields past this point not currently parsed or used...

		isChecksumValid = getChecksum() == TarHeader.MakeCheckSum(header);
	}

	/** 
	 'Write' header information to buffer provided, updating the <see cref="Checksum">check sum</see>.
	 
	 @param outBuffer output buffer for header information
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void WriteHeader(byte[] outBuffer)
	public final void WriteHeader(byte[] outBuffer)
	{
		if (outBuffer == null)
		{
			throw new NullPointerException("outBuffer");
		}

		int offset = 0;

		offset = GetNameBytes(getName(), outBuffer, offset, NAMELEN);
		offset = GetOctalBytes(mode, outBuffer, offset, MODELEN);
		offset = GetOctalBytes(getUserId(), outBuffer, offset, UIDLEN);
		offset = GetOctalBytes(getGroupId(), outBuffer, offset, GIDLEN);

		offset = GetBinaryOrOctalBytes(getSize(), outBuffer, offset, SIZELEN);
		offset = GetOctalBytes(GetCTime(getModTime()), outBuffer, offset, MODTIMELEN);

		int csOffset = offset;
		for (int c = 0; c < CHKSUMLEN; ++c)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: outBuffer[offset++] = (byte)' ';
			outBuffer[offset++] = (byte)' ';
		}

		outBuffer[offset++] = getTypeFlag();

		offset = GetNameBytes(getLinkName(), outBuffer, offset, NAMELEN);
		offset = GetAsciiBytes(getMagic(), 0, outBuffer, offset, MAGICLEN);
		offset = GetNameBytes(getVersion(), outBuffer, offset, VERSIONLEN);
		offset = GetNameBytes(getUserName(), outBuffer, offset, UNAMELEN);
		offset = GetNameBytes(getGroupName(), outBuffer, offset, GNAMELEN);

		if ((getTypeFlag() == LF_CHR) || (getTypeFlag() == LF_BLK))
		{
			offset = GetOctalBytes(getDevMajor(), outBuffer, offset, DEVLEN);
			offset = GetOctalBytes(getDevMinor(), outBuffer, offset, DEVLEN);
		}

		for (; offset < outBuffer.length;)
		{
			outBuffer[offset++] = 0;
		}

		checksum = ComputeCheckSum(outBuffer);

		GetCheckSumOctalBytes(checksum, outBuffer, csOffset, CHKSUMLEN);
		isChecksumValid = true;
	}

	/** 
	 Get a hash code for the current object.
	 
	 @return A hash code for the current object.
	*/
	@Override
	public int hashCode()
	{
		return getName().hashCode();
	}

	/** 
	 Determines if this instance is equal to the specified object.
	 
	 @param obj The object to compare with.
	 @return true if the objects are equal, false otherwise.
	*/
	@Override
	public boolean equals(Object obj)
	{
		TarHeader localHeader = obj instanceof TarHeader ? (TarHeader)obj : null;

		boolean result;
		if (localHeader != null)
		{
			result = (name.equals(localHeader.name)) && (mode == localHeader.mode) && (getUserId() == localHeader.getUserId()) && (getGroupId() == localHeader.getGroupId()) && (getSize() == localHeader.getSize()) && (getModTime().equals(localHeader.getModTime())) && (getChecksum() == localHeader.getChecksum()) && (getTypeFlag() == localHeader.getTypeFlag()) && (getLinkName().equals(localHeader.getLinkName())) && (getMagic().equals(localHeader.getMagic())) && (getVersion().equals(localHeader.getVersion())) && (getUserName().equals(localHeader.getUserName())) && (getGroupName().equals(localHeader.getGroupName())) && (getDevMajor() == localHeader.getDevMajor()) && (getDevMinor() == localHeader.getDevMinor());
		}
		else
		{
			result = false;
		}
		return result;
	}

	/** 
	 Set defaults for values used when constructing a TarHeader instance.
	 
	 @param userId Value to apply as a default for userId.
	 @param userName Value to apply as a default for userName.
	 @param groupId Value to apply as a default for groupId.
	 @param groupName Value to apply as a default for groupName.
	*/
	public static void SetValueDefaults(int userId, String userName, int groupId, String groupName)
	{
		defaultUserId = userIdAsSet = userId;
		defaultUser = userNameAsSet = userName;
		defaultGroupId = groupIdAsSet = groupId;
		defaultGroupName = groupNameAsSet = groupName;
	}

	public static void RestoreSetValues()
	{
		defaultUserId = userIdAsSet;
		defaultUser = userNameAsSet;
		defaultGroupId = groupIdAsSet;
		defaultGroupName = groupNameAsSet;
	}

	// Return value that may be stored in octal or binary. Length must exceed 8.
	//
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static long ParseBinaryOrOctal(byte[] header, int offset, int length)
	private static long ParseBinaryOrOctal(byte[] header, int offset, int length)
	{
		if (header[offset] >= 0x80)
		{
			// File sizes over 8GB are stored in 8 right-justified bytes of binary indicated by setting the high-order bit of the leftmost byte of a numeric field.
			long result = 0;
			for (int pos = length - 8; pos < length; pos++)
			{
				result = result << 8 | header[offset + pos];
			}
			return result;
		}
		return ParseOctal(header, offset, length);
	}

	/** 
	 Parse an octal string from a header buffer.
	 
	 @param  header The header buffer from which to parse.
	 @param  offset The offset into the buffer from which to parse.
	 @param  length The number of header bytes to parse.
	 @return The long equivalent of the octal string.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static long ParseOctal(byte[] header, int offset, int length)
	public static long ParseOctal(byte[] header, int offset, int length)
	{
		if (header == null)
		{
			throw new NullPointerException("header");
		}

		long result = 0;
		boolean stillPadding = true;

		int end = offset + length;
		for (int i = offset; i < end ; ++i)
		{
			if (header[i] == 0)
			{
				break;
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (header[i] == (byte)' ' || header[i] == '0')
			if (header[i] == (byte)' ' || header[i] == '0')
			{
				if (stillPadding)
				{
					continue;
				}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (header[i] == (byte)' ')
				if (header[i] == (byte)' ')
				{
					break;
				}
			}

			stillPadding = false;

			result = (result << 3) + (header[i] - '0');
		}

		return result;
	}

	/** 
	 Parse a name from a header buffer.
	 
	 @param header
	 The header buffer from which to parse.
	 
	 @param offset
	 The offset into the buffer from which to parse.
	 
	 @param length
	 The number of header bytes to parse.
	 
	 @return 
	 The name parsed.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static StringBuilder ParseName(byte[] header, int offset, int length)
	public static StringBuilder ParseName(byte[] header, int offset, int length)
	{
		if (header == null)
		{
			throw new NullPointerException("header");
		}

		if (offset < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("offset");
//#else
			throw new IndexOutOfBoundsException("offset", "Cannot be less than zero");
//#endif
		}

		if (length < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("length");
//#else
			throw new IndexOutOfBoundsException("length", "Cannot be less than zero");
//#endif
		}

		if (offset + length > header.length)
		{
			throw new IllegalArgumentException("Exceeds header size", "length");
		}

		StringBuilder result = new StringBuilder(length);

		for (int i = offset; i < offset + length; ++i)
		{
			if (header[i] == 0)
			{
				break;
			}
			result.append((char)header[i]);
		}

		return result;
	}

	/** 
	 Add <paramref name="name">name</paramref> to the buffer as a collection of bytes
	 
	 @param name The name to add
	 @param nameOffset The offset of the first character
	 @param buffer The buffer to add to
	 @param bufferOffset The index of the first byte to add
	 @param length The number of characters/bytes to add
	 @return The next free index in the <paramref name="buffer"/>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static int GetNameBytes(StringBuilder name, int nameOffset, byte[] buffer, int bufferOffset, int length)
	public static int GetNameBytes(StringBuilder name, int nameOffset, byte[] buffer, int bufferOffset, int length)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		return GetNameBytes(name.toString(), nameOffset, buffer, bufferOffset, length);
	}

	/** 
	 Add <paramref name="name">name</paramref> to the buffer as a collection of bytes
	 
	 @param name The name to add
	 @param nameOffset The offset of the first character
	 @param buffer The buffer to add to
	 @param bufferOffset The index of the first byte to add
	 @param length The number of characters/bytes to add
	 @return The next free index in the <paramref name="buffer"/>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static int GetNameBytes(string name, int nameOffset, byte[] buffer, int bufferOffset, int length)
	public static int GetNameBytes(String name, int nameOffset, byte[] buffer, int bufferOffset, int length)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		int i;

		for (i = 0 ; i < length - 1 && nameOffset + i < name.length(); ++i)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer[bufferOffset + i] = (byte)name[nameOffset + i];
			buffer[bufferOffset + i] = (byte)name.charAt(nameOffset + i);
		}

		for (; i < length ; ++i)
		{
			buffer[bufferOffset + i] = 0;
		}

		return bufferOffset + length;
	}

	/** 
	 Add an entry name to the buffer
	 
	 @param name
	 The name to add
	 
	 @param buffer
	 The buffer to add to
	 
	 @param offset
	 The offset into the buffer from which to start adding
	 
	 @param length
	 The number of header bytes to add
	 
	 @return 
	 The index of the next free byte in the buffer
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static int GetNameBytes(StringBuilder name, byte[] buffer, int offset, int length)
	public static int GetNameBytes(StringBuilder name, byte[] buffer, int offset, int length)
	{

		if (name == null)
		{
			throw new NullPointerException("name");
		}

		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		return GetNameBytes(name.toString(), 0, buffer, offset, length);
	}

	/** 
	 Add an entry name to the buffer
	 
	 @param name The name to add
	 @param buffer The buffer to add to
	 @param offset The offset into the buffer from which to start adding
	 @param length The number of header bytes to add
	 @return The index of the next free byte in the buffer
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static int GetNameBytes(string name, byte[] buffer, int offset, int length)
	public static int GetNameBytes(String name, byte[] buffer, int offset, int length)
	{

		if (name == null)
		{
			throw new NullPointerException("name");
		}

		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		return GetNameBytes(name, 0, buffer, offset, length);
	}

	/** 
	 Add a string to a buffer as a collection of ascii bytes.
	 
	 @param toAdd The string to add
	 @param nameOffset The offset of the first character to add.
	 @param buffer The buffer to add to.
	 @param bufferOffset The offset to start adding at.
	 @param length The number of ascii characters to add.
	 @return The next free index in the buffer.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static int GetAsciiBytes(string toAdd, int nameOffset, byte[] buffer, int bufferOffset, int length)
	public static int GetAsciiBytes(String toAdd, int nameOffset, byte[] buffer, int bufferOffset, int length)
	{
		if (toAdd == null)
		{
			throw new NullPointerException("toAdd");
		}

		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		for (int i = 0 ; i < length && nameOffset + i < toAdd.length(); ++i)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer[bufferOffset + i] = (byte)toAdd[nameOffset + i];
			buffer[bufferOffset + i] = (byte)toAdd.charAt(nameOffset + i);
		}
		return bufferOffset + length;
	}

	/** 
	 Put an octal representation of a value into a buffer
	 
	 @param  value
	 the value to be converted to octal
	 
	 @param  buffer
	 buffer to store the octal string
	 
	 @param  offset
	 The offset into the buffer where the value starts
	 
	 @param  length
	 The length of the octal string to create
	 
	 @return 
	 The offset of the character next byte after the octal string
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static int GetOctalBytes(long value, byte[] buffer, int offset, int length)
	public static int GetOctalBytes(long value, byte[] buffer, int offset, int length)
	{
		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		int localIndex = length - 1;

		// Either a space or null is valid here.  We use NULL as per GNUTar
		buffer[offset + localIndex] = 0;
		--localIndex;

		if (value > 0)
		{
			for (long v = value; (localIndex >= 0) && (v > 0); --localIndex)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer[offset + localIndex] = (byte)((byte)'0' + (byte)(v & 7));
				buffer[offset + localIndex] = (byte)((byte)'0' + (byte)(v & 7));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
				v >>= 3;
			}
		}

		for (; localIndex >= 0; --localIndex)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer[offset + localIndex] = (byte)'0';
			buffer[offset + localIndex] = (byte)'0';
		}

		return offset + length;
	}

	/** 
	 Put an octal or binary representation of a value into a buffer
	 
	 @param  value Value to be convert to octal
	 @param  buffer The buffer to update
	 @param  offset The offset into the buffer to store the value
	 @param  length The length of the octal string. Must be 12.
	 @return Index of next byte
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static int GetBinaryOrOctalBytes(long value, byte[] buffer, int offset, int length)
	private static int GetBinaryOrOctalBytes(long value, byte[] buffer, int offset, int length)
	{
		if (value > 0x1FFFFFFFFL)
		{ // Octal 77777777777 (11 digits)
			// Put value as binary, right-justified into the buffer. Set high order bit of left-most byte.
			for (int pos = length - 1; pos > 0; pos--)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer[offset + pos] = (byte)value;
				buffer[offset + pos] = (byte)value;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
				value = value >> 8;
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer[offset] = 0x80;
			buffer[offset] = (byte)0x80;
			return offset + length;
		}
		return GetOctalBytes(value, buffer, offset, length);
	}

	/** 
	 Add the checksum integer to header buffer.
	 
	 @param  value
	 @param  buffer The header buffer to set the checksum for
	 @param  offset The offset into the buffer for the checksum
	 @param  length The number of header bytes to update.
	 It's formatted differently from the other fields: it has 6 digits, a
	 null, then a space -- rather than digits, a space, then a null.
	 The final space is already there, from checksumming
	 
	 @return The modified buffer offset
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static void GetCheckSumOctalBytes(long value, byte[] buffer, int offset, int length)
	private static void GetCheckSumOctalBytes(long value, byte[] buffer, int offset, int length)
	{
		GetOctalBytes(value, buffer, offset, length - 1);
	}

	/** 
	 Compute the checksum for a tar entry header.  
	 The checksum field must be all spaces prior to this happening
	 
	 @param  buffer The tar entry's header buffer.
	 @return The computed checksum.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static int ComputeCheckSum(byte[] buffer)
	private static int ComputeCheckSum(byte[] buffer)
	{
		int sum = 0;
		for (int i = 0; i < buffer.length; ++i)
		{
			sum += buffer[i];
		}
		return sum;
	}

	/** 
	 Make a checksum for a tar entry ignoring the checksum contents.
	 
	 @param  buffer The tar entry's header buffer.
	 @return The checksum for the buffer
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static int MakeCheckSum(byte[] buffer)
	private static int MakeCheckSum(byte[] buffer)
	{
		int sum = 0;
		for (int i = 0; i < CHKSUMOFS; ++i)
		{
			sum += buffer[i];
		}

		for (int i = 0; i < CHKSUMLEN; ++i)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: sum += (byte)' ';
			sum += (byte)' ';
		}

		for (int i = CHKSUMOFS + CHKSUMLEN; i < buffer.length; ++i)
		{
			sum += buffer[i];
		}
		return sum;
	}

	private static int GetCTime(LocalDateTime dateTime)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: return unchecked((int)((dateTime.Ticks - dateTime1970.Ticks) / timeConversionFactor));
		return (int)((dateTime.getTime() - dateTime1970.getTime()) / timeConversionFactor);
	}

	private static LocalDateTime GetDateTimeFromCTime(long ticks)
	{
		LocalDateTime result = LocalDateTime.MIN;

		try
		{
			result = LocalDateTime.of(dateTime1970.getTime() + ticks * timeConversionFactor);
		}
		catch (IndexOutOfBoundsException e)
		{
			result = dateTime1970;
		}
		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private String name;
	private int mode;
	private int userId;
	private int groupId;
	private long size;
	private LocalDateTime modTime = LocalDateTime.MIN;
	private int checksum;
	private boolean isChecksumValid;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte typeFlag;
	private byte typeFlag;
	private String linkName;
	private String magic;
	private String version;
	private String userName;
	private String groupName;
	private int devMajor;
	private int devMinor;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Class Fields
	// Values used during recursive operations.
	public static int userIdAsSet;
	public static int groupIdAsSet;
	public static String userNameAsSet;
	public static String groupNameAsSet = "None";

	public static int defaultUserId;
	public static int defaultGroupId;
	public static String defaultGroupName = "None";
	public static String defaultUser;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
/* The original Java file had this header:
 * 
** Authored by Timothy Gerard Endres
** <mailto:time@gjt.org>  <http: //www.trustice.com>
** 
** This work has been placed into the public domain.
** You may use this work in any way and for any purpose you wish.
**
** THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
** NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
** OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
** CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
** REDISTRIBUTION OF THIS SOFTWARE. 
** 
*/
