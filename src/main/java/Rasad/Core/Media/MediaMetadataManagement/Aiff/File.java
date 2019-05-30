package Rasad.Core.Media.MediaMetadataManagement.Aiff;

import Rasad.Core.Media.MediaMetadataManagement.Id3v2.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// File.cs: Provides tagging and properties support for Apple's AIFF 
// files.
//
// Author:
//   Helmut Wahrmann
//
// Copyright (C) 2009 Helmut Wahrmann
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> to provide
	support for reading and writing tags and properties for files
	using the AIFF file format.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/aif", "aif")][SupportedMimeType("audio/x-aiff")][SupportedMimeType("audio/aiff")][SupportedMimeType("sound/aiff")][SupportedMimeType("application/x-aiff")] public class File : Rasad.Core.Media.MediaMetadataManagement.File
public class File extends Rasad.Core.Media.MediaMetadataManagement.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the address of the AIFF header block.
	*/
	private ByteVector header_block = null;

	/** 
	  Contains the Id3v2 tag.
	*/
	private Id3v2.Tag tag = null;

	/** 
	  Contains the media properties.
	*/
	private Properties properties = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Fields

	/** 
		The identifier used to recognize a AIFF files.
	 
	 <value>
		"FORM"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "FORM";

	/** 
		The identifier used to recognize a AIFF Common chunk.
	 
	 <value>
		"COMM"
	 </value>
	*/
	public static final ReadOnlyByteVector CommIdentifier = "COMM";

	/** 
		The identifier used to recognize a AIFF Sound Data Chunk.
	 
	 <value>
		"SSND"
	 </value>
	*/
	public static final ReadOnlyByteVector SoundIdentifier = "SSND";

	/** 
		The identifier used to recognize a AIFF ID3 chunk.
	 
	 <value>
		"ID3 "
	 </value>
	*/
	public static final ReadOnlyByteVector ID3Identifier = "ID3 ";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Constructors

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
		this(new File.LocalFileAbstraction(path), propertiesStyle);
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
		Mode = AccessMode.Read;
		try
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint aiff_size;
			int aiff_size;
			long tag_start, tag_end;
			tangible.OutObject<Integer> tempOut_aiff_size = new tangible.OutObject<Integer>();
			tangible.OutObject<Long> tempOut_tag_start = new tangible.OutObject<Long>();
			tangible.OutObject<Long> tempOut_tag_end = new tangible.OutObject<Long>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Read(true, propertiesStyle, out aiff_size, out tag_start, out tag_end);
			Read(true, propertiesStyle, tempOut_aiff_size, tempOut_tag_start, tempOut_tag_end);
		tag_end = tempOut_tag_end.argValue;
		tag_start = tempOut_tag_start.argValue;
		aiff_size = tempOut_aiff_size.argValue;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}

		TagTypesOnDisk = TagTypes;

		GetTag(TagTypes.Id3v2, true);
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
	public Tag getTag()
	{
		return tag;
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
			ByteVector data = new ByteVector();

			// Add the ID3 chunk and ID32 tag to the vector
			if (tag != null)
			{
				ByteVector tag_data = tag.Render();
				if (tag_data.size() > 10)
				{
					if (tag_data.size() % 2 == 1)
					{
						tag_data.add(0);
					}

					data.add("ID3 ");
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) tag_data.Count, true));
					data.add(ByteVector.FromUInt((int) tag_data.size(), true));
					data.add(tag_data);
				}
			}

			// Read the file to determine the current AIFF
			// size and the area tagging is in.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint aiff_size;
			int aiff_size;
			long tag_start, tag_end;
			tangible.OutObject<Integer> tempOut_aiff_size = new tangible.OutObject<Integer>();
			tangible.OutObject<Long> tempOut_tag_start = new tangible.OutObject<Long>();
			tangible.OutObject<Long> tempOut_tag_end = new tangible.OutObject<Long>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Read(false, ReadStyle.None, out aiff_size, out tag_start, out tag_end);
			Read(false, ReadStyle.None, tempOut_aiff_size, tempOut_tag_start, tempOut_tag_end);
		tag_end = tempOut_tag_end.argValue;
		tag_start = tempOut_tag_start.argValue;
		aiff_size = tempOut_aiff_size.argValue;

			// If tagging info cannot be found, place it at
			// the end of the file.
			if (tag_start < 12 || tag_end < tag_start)
			{
				tag_start = tag_end = Length;
			}

			int length = (int)(tag_end - tag_start + 8);

			// Insert the tagging data.
			Insert(data, tag_start, length);

			// If the data size changed update the aiff size.
			if (data.size() - length != 0 && tag_start <= aiff_size)
			{
				// Depending, if a Tag has been added or removed, 
				// the length needs to be adjusted
				if (tag == null)
				{
					length -= 16;
				}
				else
				{
					length -= 8;
				}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Insert(ByteVector.FromUInt((uint)(aiff_size + data.Count - length), true), 4, 4);
				Insert(ByteVector.FromUInt((int)(aiff_size + data.size() - length), true), 4, 4);
			}
			// Update the tag types.
			TagTypesOnDisk = TagTypes;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}
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
		if (types == Rasad.Core.Media.MediaMetadataManagement.TagTypes.Id3v2 || types == Rasad.Core.Media.MediaMetadataManagement.TagTypes.AllTags)
		{
			tag = null;
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
		Rasad.Core.Media.MediaMetadataManagement.Tag id32_tag = null;

		switch (type)
		{
			case TagTypes.Id3v2:
				if (tag == null && create)
				{
					tag = new Id3v2.Tag();
					tag.setVersion((byte)2);
				}

				id32_tag = tag;
				break;
		}

		return id32_tag;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Reads the contents of the current instance determining
		the size of the riff data, the area the tagging is in,
		and optionally reading in the tags and media properties.
	 
	 @param read_tags
		If <see langword="true" />, any tags found will be read
		into the current instance.
	 
	 @param style
		A <see cref="ReadStyle"/> value specifying how the media
		data is to be read into the current instance.
	 
	 @param aiff_size
		A <see cref="uint"/> value reference to be filled with
		the size of the RIFF data as read from the file.
	 
	 @param tag_start
		A <see cref="long" /> value reference to be filled with
		the absolute seek position at which the tagging data
		starts.
	 
	 @param tag_end
		A <see cref="long" /> value reference to be filled with
		the absolute seek position at which the tagging data
		ends.
	 
	 @exception CorruptFileException
		The file does not begin with <see cref="FileIdentifier"
		/>.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void Read(bool read_tags, ReadStyle style, out uint aiff_size, out long tag_start, out long tag_end)
	private void Read(boolean read_tags, ReadStyle style, tangible.OutObject<Integer> aiff_size, tangible.OutObject<Long> tag_start, tangible.OutObject<Long> tag_end)
	{
		Seek(0);
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpInequality(ReadBlock(4), FileIdentifier))
		{
			throw new CorruptFileException("File does not begin with AIFF identifier");
		}

		aiff_size.argValue = ReadBlock(4).ToUInt(true);
		tag_start.argValue = -1;
		tag_end.argValue = -1;

		// Get the properties of the file
		if (header_block == null && style != ReadStyle.None)
		{
			long common_chunk_pos = Find(CommIdentifier, 0);

			if (common_chunk_pos == -1)
			{
				throw new CorruptFileException("No Common chunk available in AIFF file.");
			}

			Seek(common_chunk_pos);
			header_block = ReadBlock((int) StreamHeader.Size);

			StreamHeader header = new StreamHeader(header_block, aiff_size.argValue);
			properties = new Properties(TimeSpan.Zero, header.clone());
		}

		// Now we search for the ID3 chunk.
		// Normally it appears after the Sound data chunk. But as the order of
		// chunks is free, it might be the case that the ID3 chunk appears before 
		// the sound data chunk.
		// So we search first for the Sound data chunk and see, if an ID3 chunk appears before
		long id3_chunk_pos = -1;
		long sound_chunk_pos = Find(SoundIdentifier, 0, ID3Identifier);
		if (sound_chunk_pos == -1)
		{
			// The ID3 chunk appears before the Sound chunk
			id3_chunk_pos = Find(ID3Identifier, 0);
		}

		// Now let's look for the Sound chunk again
		// Since a previous return value of -1 does mean, that the ID3 chunk was found first
		sound_chunk_pos = Find(SoundIdentifier, 0);
		if (sound_chunk_pos == -1)
		{
			throw new CorruptFileException("No Sound chunk available in AIFF file.");
		}

		// Get the length of the Sound chunk and use this as a start value to look for the ID3 chunk
		Seek(sound_chunk_pos + 4);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong sound_chunk_length = ReadBlock(4).ToULong(true);
		long sound_chunk_length = ReadBlock(4).ToULong(true);
		long start_search_pos = (long) sound_chunk_length + sound_chunk_pos + 4;

		if (id3_chunk_pos == -1)
		{
			id3_chunk_pos = Find(ID3Identifier, start_search_pos);
		}

		if (id3_chunk_pos > -1)
		{
			if (read_tags && tag == null)
			{
				tag = new Id3v2.Tag(this, id3_chunk_pos + 8);
			}

			// Get the length of the tag out of the ID3 chunk
			Seek(id3_chunk_pos + 4);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint tag_size = ReadBlock(4).ToUInt(true) + 8;
			int tag_size = ReadBlock(4).ToUInt(true) + 8;

			tag_start.argValue = InvariantStartPosition = id3_chunk_pos;
			tag_end.argValue = InvariantEndPosition = tag_start.argValue + tag_size;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}