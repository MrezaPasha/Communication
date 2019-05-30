package Rasad.Core.Compression.Tar;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;

// TarEntry.cs
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



/** 
 This class represents an entry in a Tar archive. It consists
 of the entry's header, as well as the entry's File. Entries
 can be instantiated in one of three ways, depending on how
 they are to be used.
 <p>
 TarEntries that are created from the header bytes read from
 an archive are instantiated with the TarEntry( byte[] )
 constructor. These entries will be used when extracting from
 or listing the contents of an archive. These entries have their
 header filled in using the header bytes. They also set the File
 to null, since they reference an archive entry not a file.</p>
 <p>
 TarEntries that are created from files that are to be written
 into an archive are instantiated with the CreateEntryFromFile(string)
 pseudo constructor. These entries have their header filled in using
 the File's information. They also keep a reference to the File
 for convenience when writing entries.</p>
 <p>
 Finally, TarEntries can be constructed from nothing but a name.
 This allows the programmer to construct the entry by hand, for
 instance when only an InputStream is available for writing to
 the archive, and the header information is constructed from
 other information. In this case the header fields are set to
 defaults and the File is set to null.</p>
 <see cref="TarHeader"/>
*/
public class TarEntry implements Cloneable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a default instance of <see cref="TarEntry"/>.
	*/
	private TarEntry()
	{
		header = new TarHeader();
	}

	/** 
	 Construct an entry from an archive's header bytes. File is set
	 to null.
	 
	 @param  headerBuffer
	 The header bytes from a tar archive entry.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TarEntry(byte[] headerBuffer)
	public TarEntry(byte[] headerBuffer)
	{
		header = new TarHeader();
		header.ParseBuffer(headerBuffer);
	}

	/** 
	 Construct a TarEntry using the <paramref name="header">header</paramref> provided
	 
	 @param header Header details for entry
	*/
	public TarEntry(TarHeader header)
	{
		if (header == null)
		{
			throw new NullPointerException("header");
		}

		this.header = (TarHeader)header.Clone();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICloneable Members
	/** 
	 Clone this tar entry.
	 
	 @return Returns a clone of this entry.
	*/
	public final Object Clone()
	{
		TarEntry entry = new TarEntry();
		entry.file = file;
		entry.header = (TarHeader)header.Clone();
		entry.setName(getName());
		return entry;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Construct an entry with only a <paramref name="name">name</paramref>.
	 This allows the programmer to construct the entry's header "by hand". 
	 
	 @param name The name to use for the entry
	 @return Returns the newly created <see cref="TarEntry"/>
	*/
	public static TarEntry CreateTarEntry(String name)
	{
		TarEntry entry = new TarEntry();
		TarEntry.NameTarHeader(entry.header, name);
		return entry;
	}

	/** 
	 Construct an entry for a file. File is set to file, and the
	 header is constructed from information from the file.
	 
	 @param  fileName The file name that the entry represents.
	 @return Returns the newly created <see cref="TarEntry"/>
	*/
	public static TarEntry CreateEntryFromFile(String fileName)
	{
		TarEntry entry = new TarEntry();
		entry.GetFileTarHeader(entry.header, fileName);
		return entry;
	}

	/** 
	 Determine if the two entries are equal. Equality is determined
	 by the header names being equal.
	 
	 @param obj The <see cref="Object"/> to compare with the current Object.
	 @return 
	 True if the entries are equal; false if not.
	 
	*/
	@Override
	public boolean equals(Object obj)
	{
		TarEntry localEntry = obj instanceof TarEntry ? (TarEntry)obj : null;

		if (localEntry != null)
		{
			return getName().equals(localEntry.getName());
		}
		return false;
	}

	/** 
	 Derive a Hash value for the current <see cref="Object"/>
	 
	 @return A Hash code for the current <see cref="Object"/>
	*/
	@Override
	public int hashCode()
	{
		return getName().hashCode();
	}

	/** 
	 Determine if the given entry is a descendant of this entry.
	 Descendancy is determined by the name of the descendant
	 starting with this entry's name.
	 
	 @param  toTest
	 Entry to be checked as a descendent of this.
	 
	 @return 
	 True if entry is a descendant of this.
	 
	*/
	public final boolean IsDescendent(TarEntry toTest)
	{
		if (toTest == null)
		{
			throw new NullPointerException("toTest");
		}

		return toTest.getName().startsWith(getName());
	}

	/** 
	 Get this entry's header.
	 
	 @return 
	 This entry's TarHeader.
	 
	*/
	public final TarHeader getTarHeader()
	{
		return header;
	}

	/** 
	 Get/Set this entry's name.
	*/
	public final String getName()
	{
		return header.getName();
	}
	public final void setName(String value)
	{
		header.setName(value);
	}

	/** 
	 Get/set this entry's user id.
	*/
	public final int getUserId()
	{
		return header.getUserId();
	}
	public final void setUserId(int value)
	{
		header.setUserId(value);
	}

	/** 
	 Get/set this entry's group id.
	*/
	public final int getGroupId()
	{
		return header.getGroupId();
	}
	public final void setGroupId(int value)
	{
		header.setGroupId(value);
	}

	/** 
	 Get/set this entry's user name.
	*/
	public final String getUserName()
	{
		return header.getUserName();
	}
	public final void setUserName(String value)
	{
		header.setUserName(value);
	}

	/** 
	 Get/set this entry's group name.
	*/
	public final String getGroupName()
	{
		return header.getGroupName();
	}
	public final void setGroupName(String value)
	{
		header.setGroupName(value);
	}

	/** 
	 Convenience method to set this entry's group and user ids.
	 
	 @param userId
	 This entry's new user id.
	 
	 @param groupId
	 This entry's new group id.
	 
	*/
	public final void SetIds(int userId, int groupId)
	{
		setUserId(userId);
		setGroupId(groupId);
	}

	/** 
	 Convenience method to set this entry's group and user names.
	 
	 @param userName
	 This entry's new user name.
	 
	 @param groupName
	 This entry's new group name.
	 
	*/
	public final void SetNames(String userName, String groupName)
	{
		setUserName(userName);
		setGroupName(groupName);
	}

	/** 
	 Get/Set the modification time for this entry
	*/
	public final LocalDateTime getModTime()
	{
		return header.getModTime();
	}
	public final void setModTime(LocalDateTime value)
	{
		header.setModTime(value);
	}

	/** 
	 Get this entry's file.
	 
	 @return 
	 This entry's file.
	 
	*/
	public final String getFile()
	{
		return file;
	}

	/** 
	 Get/set this entry's recorded file size.
	*/
	public final long getSize()
	{
		return header.getSize();
	}
	public final void setSize(long value)
	{
		header.setSize(value);
	}

	/** 
	 Return true if this entry represents a directory, false otherwise
	 
	 @return 
	 True if this entry is a directory.
	 
	*/
	public final boolean getIsDirectory()
	{
		if (file != null)
		{
			return (new File(file)).isDirectory();
		}

		if (header != null)
		{
			if ((header.getTypeFlag() == getTarHeader().LF_DIR) || getName().endsWith("/"))
			{
				return true;
			}
		}
		return false;
	}

	/** 
	 Fill in a TarHeader with information from a File.
	 
	 @param header
	 The TarHeader to fill in.
	 
	 @param file
	 The file from which to get the header information.
	 
	*/
	public final void GetFileTarHeader(TarHeader header, String file)
	{
		if (header == null)
		{
			throw new NullPointerException("header");
		}

		if (file == null)
		{
			throw new NullPointerException("file");
		}

		this.file = file;

		// bugfix from torhovl from #D forum:
		String name = file;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0 && !NETCF_2_0
		// 23-Jan-2004 GnuTar allows device names in path where the name is not local to the current directory
		if (name.indexOf(Paths.get("").toAbsolutePath().toString()) == 0)
		{
			name = name.substring(Paths.get("").toAbsolutePath().toString().Length);
		}
//#endif

/*
			if (Path.DirectorySeparatorChar == '\\') 
			{
				// check if the OS is Windows
				// Strip off drive letters!
				if (name.Length > 2) 
				{
					char ch1 = name[0];
					char ch2 = name[1];
					
					if (ch2 == ':' && Char.IsLetter(ch1)) 
					{
						name = name.Substring(2);
					}
				}
			}
*/

		name = name.replace(File.separatorChar, '/');

		// No absolute pathnames
		// Windows (and Posix?) paths can start with UNC style "\\NetworkDrive\",
		// so we loop on starting /'s.
		while (name.startsWith("/"))
		{
			name = name.substring(1);
		}

		header.setLinkName("");
		header.setName(name);

		if ((new File(file)).isDirectory())
		{
			header.setMode(1003); // Magic number for security access for a UNIX filesystem
			header.setTypeFlag(getTarHeader().LF_DIR);
			if ((header.getName().length() == 0) || header.getName().charAt(header.getName().length() - 1) != '/')
			{
				header.setName(header.getName() + "/");
			}

			header.setSize(0);
		}
		else
		{
			header.setMode(33216); // Magic number for security access for a UNIX filesystem
			header.setTypeFlag(getTarHeader().LF_NORMAL);
			header.setSize((new File(file.replace('/', File.separatorChar))).length());
		}

		header.setModTime(System.IO.File.GetLastWriteTime(file.replace('/', File.separatorChar)).ToUniversalTime());
		header.setDevMajor(0);
		header.setDevMinor(0);
	}

	/** 
	 Get entries for all files present in this entries directory.
	 If this entry doesnt represent a directory zero entries are returned.
	 
	 @return 
	 An array of TarEntry's for this entry's children.
	 
	*/
	public final TarEntry[] GetDirectoryEntries()
	{
		if ((file == null) || !(new File(file)).isDirectory())
		{
			return new TarEntry[0];
		}

		String[] list = Directory.GetFileSystemEntries(file);
		TarEntry[] result = new TarEntry[list.length];

		for (int i = 0; i < list.length; ++i)
		{
			result[i] = TarEntry.CreateEntryFromFile(list[i]);
		}

		return result;
	}

	/** 
	 Write an entry's header information to a header buffer.
	 
	 @param  outBuffer
	 The tar entry header buffer to fill in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void WriteEntryHeader(byte[] outBuffer)
	public final void WriteEntryHeader(byte[] outBuffer)
	{
		header.WriteHeader(outBuffer);
	}

	/** 
	 Convenience method that will modify an entry's name directly
	 in place in an entry header buffer byte array.
	 
	 @param buffer
	 The buffer containing the entry header to modify.
	 
	 @param newName
	 The new name to place into the header buffer.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void AdjustEntryName(byte[] buffer, string newName)
	public static void AdjustEntryName(byte[] buffer, String newName)
	{
		getTarHeader().GetNameBytes(newName, buffer, 0, getTarHeader().NAMELEN);
	}

	/** 
	 Fill in a TarHeader given only the entry's name.
	 
	 @param header
	 The TarHeader to fill in.
	 
	 @param name
	 The tar entry name.
	 
	*/
	public static void NameTarHeader(TarHeader header, String name)
	{
		if (header == null)
		{
			throw new NullPointerException("header");
		}

		if (name == null)
		{
			throw new NullPointerException("name");
		}

		boolean isDir = name.endsWith("/");

		header.setName(name);
		header.setMode(isDir ? 1003 : 33216);
		header.setUserId(0);
		header.setGroupId(0);
		header.setSize(0);

		header.setModTime(LocalDateTime.UtcNow);

		header.setTypeFlag(isDir ? getTarHeader().LF_DIR : getTarHeader().LF_NORMAL);

		header.setLinkName("");
		header.setUserName("");
		header.setGroupName("");

		header.setDevMajor(0);
		header.setDevMinor(0);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 The name of the file this entry represents or null if the entry is not based on a file.
	*/
	private String file;

	/** 
	 The entry's header information.
	*/
	private TarHeader header;
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
