package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// File.cs: Provides tagging and properties support for Microsoft's ASF files.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2006-2007 Brian Nickel
//
// This library is free software; you can redistribute it and/or modify
// it  under the terms of the GNU Lesser General Public License version
// 2.1 as published by the Free Software Foundation.
//
// This library is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
// USA
//



/** 
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> to provide tagging
	and properties support for Microsoft's ASF files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/wma", "wma")][SupportedMimeType("taglib/wmv", "wmv")][SupportedMimeType("taglib/asf", "asf")][SupportedMimeType("audio/x-ms-wma")][SupportedMimeType("audio/x-ms-asf")][SupportedMimeType("video/x-ms-asf")] public class File : Rasad.Core.Media.MediaMetadataManagement.File
public class File extends Rasad.Core.Media.MediaMetadataManagement.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the file's tag.
	*/
	private Asf.Tag asf_tag = null;

	/** 
		Contains the file's properties.
	*/
	private Properties properties = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system and specified read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public File(String path, ReadStyle propertiesStyle)
	{
		super(path);
		Read(propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system with an average read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public File(String path)
	{
		this(path, ReadStyle.Average);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction and
		specified read style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public File(File.IFileAbstraction abstraction, ReadStyle propertiesStyle)
	{
		super(abstraction);
		Read(propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction with an
		average read style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public File(File.IFileAbstraction abstraction)
	{
		this(abstraction, ReadStyle.Average);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets a abstract representation of all tags stored in the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> object representing all tags
		stored in the current instance.
	 </value>
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag getTag()
	{
		return asf_tag;
	}

	/** 
		Gets the media properties of the file represented by the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Properties" /> object containing the
		media properties of the file represented by the current
		instance.
	 </value>
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Properties getProperties()
	{
		return properties;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Saves the changes made in the current instance to the
		file it represents.
	*/
	@Override
	public void Save()
	{
		Mode = AccessMode.Write;
		try
		{
			HeaderObject header = new HeaderObject(this, 0);

			if (asf_tag == null)
			{
				header.RemoveContentDescriptors();
				TagTypesOnDisk &= ~TagTypes.Asf;
			}
			else
			{
				TagTypesOnDisk |= TagTypes.Asf;
				header.AddUniqueObject(asf_tag.getContentDescriptionObject());
				header.AddUniqueObject(asf_tag.getExtendedContentDescriptionObject());
				header.getExtension().AddUniqueObject(asf_tag.getMetadataLibraryObject());
			}

			ByteVector output = header.Render();
			long diff = output.size() - (long) header.getOriginalSize();
			Insert(output, 0, (long) header.getOriginalSize());

			InvariantStartPosition += diff;
			InvariantEndPosition += diff;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}
	}

	/** 
		Gets a tag of a specified type from the current instance,
		optionally creating a new tag if possible.
	 
	 @param type
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value indicating the
		type of tag to read.
	 
	 @param create
		A <see cref="bool" /> value specifying whether or not to
		try and create the tag if one is not found.
	 
	 @return 
		A <see cref="Tag" /> object containing the tag that was
		found in or added to the current instance. If no
		matching tag was found and none was created, <see
		langword="null" /> is returned.
	 
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag GetTag(TagTypes type, boolean create)
	{
		if (type == TagTypes.Asf)
		{
			return asf_tag;
		}

		return null;
	}

	/** 
		Removes a set of tag types from the current instance.
	 
	 @param types
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value
		containing tag types to be removed from the file.
	 
	 
		In order to remove all tags from a file, pass <see
		cref="TagTypes.AllTags" /> as <paramref name="types" />.
	 
	*/
	@Override
	public void RemoveTags(TagTypes types)
	{
		if ((types.getValue() & TagTypes.Asf.getValue()) == TagTypes.Asf.getValue())
		{
			asf_tag.Clear();
		}
	}

	/** 
		Reads a 2-byte WORD from the current instance.
	 
	 @return 
		A <see cref="ushort" /> value containing the WORD read
		from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort ReadWord()
	public final short ReadWord()
	{
		return ReadBlock(2).ToUShort(false);
	}

	/** 
		Reads a 4-byte DWORD from the current instance.
	 
	 @return 
		A <see cref="uint" /> value containing the DWORD read
		from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint ReadDWord()
	public final int ReadDWord()
	{
		return ReadBlock(4).ToUInt(false);
	}

	/** 
		Reads a 8-byte QWORD from the current instance.
	 
	 @return 
		A <see cref="ulong" /> value containing the QWORD read
		from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong ReadQWord()
	public final long ReadQWord()
	{
		return ReadBlock(8).ToULong(false);
	}

	/** 
		Reads a 16-byte GUID from the current instance.
	 
	 @return 
		A <see cref="System.Guid" /> value containing the GUID
		read from the current instance.
	 
	*/
	public final UUID ReadGuid()
	{
		return new UUID(ReadBlock(16).Data);
	}

	/** 
		Reads a Unicode (UTF-16LE) string of specified length
		from the current instance.
	 
	 @param length
		A <see cref="int" /> value specifying the number of bytes
		to read. This should always be an even number.
	 
	 @return 
		A <see cref="string" /> object containing the Unicode
		string read from the current instance.
	 
	*/
	public final String ReadUnicode(int length)
	{
		ByteVector data = ReadBlock(length);
		String output = data.toString(StringType.UTF16LE);
		int i = output.indexOf('\0');
		return (i >= 0) ? output.substring(0, i) : output;
	}

	/** 
		Reads a collection of objects from the current instance.
	 
	 @param count
		A <see cref="uint" /> value specifying the number of
		objects to read.
	 
	 @param position
		A <see cref="long" /> value specifying the seek position
		at which to start reading.
	 
	 @return 
		A new <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating
		through the <see cref="Object" /> objects read from the
		current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public IEnumerable<Object> ReadObjects(uint count, long position)
	public final java.lang.Iterable<Object> ReadObjects(int count, long position)
	{
		for (int i = 0; i < (int) count; i++)
		{
			Object obj = ReadObject(position);
			position += (Long) obj.getOriginalSize();
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
			yield return obj;
		}
	}

	/** 
		Reads a <see cref="Object" /> from the current instance.
	 
	 @param position
		A <see cref="long" /> value specifying the seek position
		at which to start reading.
	 
	 @return 
		A new <see cref="Object" /> object of appropriate type as
		read from the current instance.
	 
	*/
	public final Object ReadObject(long position)
	{
		Seek(position);
		UUID id = ReadGuid();

		if (id.equals(UUID.AsfFilePropertiesObject))
		{
			return new FilePropertiesObject(this, position);
		}

		if (id.equals(UUID.AsfStreamPropertiesObject))
		{
			return new StreamPropertiesObject(this, position);
		}

		if (id.equals(UUID.AsfContentDescriptionObject))
		{
			return new ContentDescriptionObject(this, position);
		}

		if (id.equals(UUID.AsfExtendedContentDescriptionObject))
		{
			return new ExtendedContentDescriptionObject(this, position);
		}

		if (id.equals(UUID.AsfPaddingObject))
		{
			return new PaddingObject(this, position);
		}

		if (id.equals(UUID.AsfHeaderExtensionObject))
		{
			return new HeaderExtensionObject(this, position);
		}

		if (id.equals(UUID.AsfMetadataLibraryObject))
		{
			return new MetadataLibraryObject(this, position);
		}

		return new UnknownObject(this, position);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Reads the contents of the current instance.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	private void Read(ReadStyle propertiesStyle)
	{
		Mode = AccessMode.Read;
		try
		{
			HeaderObject header = new HeaderObject(this, 0);

			if (header.getHasContentDescriptors())
			{
				TagTypesOnDisk |= TagTypes.Asf;
			}

			asf_tag = new Asf.Tag(header);

			InvariantStartPosition = (long) header.getOriginalSize();
			InvariantEndPosition = Length;

			if (propertiesStyle != ReadStyle.None)
			{
				properties = header.getProperties();
			}
		}
		finally
		{
			Mode = AccessMode.Closed;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}