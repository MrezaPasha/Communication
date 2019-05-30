package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// File.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
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
	using the RIFF file format such as AVI and Wave files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/avi", "avi")][SupportedMimeType("taglib/wav", "wav")][SupportedMimeType("taglib/divx", "divx")][SupportedMimeType("video/avi")][SupportedMimeType("video/msvideo")][SupportedMimeType("video/x-msvideo")][SupportedMimeType("image/avi")][SupportedMimeType("application/x-troff-msvideo")][SupportedMimeType("audio/avi")][SupportedMimeType("audio/wav")][SupportedMimeType("audio/wave")][SupportedMimeType("audio/x-wav")] public class File : Rasad.Core.Media.MediaMetadataManagement.File
public class File extends Rasad.Core.Media.MediaMetadataManagement.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
	  Contains all the tags of the file.
	*/
	private CombinedTag tag = new CombinedTag();

	/** 
	  Contains the INFO tag.
	*/
	private InfoTag info_tag = null;

	/** 
	  Contains the MovieID tag.
	*/
	private MovieIdTag mid_tag = null;

	/** 
	  Contains the DivX tag.
	*/
	private DivXTag divx_tag = null;

	/** 
	  Contains the Id3v2 tag.
	*/
	private Id3v2.Tag id32_tag = null;

	/** 
	  Contains the media properties.
	*/
	private Properties properties = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Fields

	/** 
		The identifier used to recognize a RIFF files.
	 
	 <value>
		"RIFF"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "RIFF";

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
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint riff_size;
		int riff_size;
		long tag_start, tag_end;

		Mode = AccessMode.Read;
		try
		{
			tangible.OutObject<Integer> tempOut_riff_size = new tangible.OutObject<Integer>();
			tangible.OutObject<Long> tempOut_tag_start = new tangible.OutObject<Long>();
			tangible.OutObject<Long> tempOut_tag_end = new tangible.OutObject<Long>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Read(true, propertiesStyle, out riff_size, out tag_start, out tag_end);
			Read(true, propertiesStyle, tempOut_riff_size, tempOut_tag_start, tempOut_tag_end);
		tag_end = tempOut_tag_end.argValue;
		tag_start = tempOut_tag_start.argValue;
		riff_size = tempOut_riff_size.argValue;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}

		TagTypesOnDisk = TagTypes;

		GetTag(TagTypes.Id3v2, true);
		GetTag(TagTypes.RiffInfo, true);
		GetTag(TagTypes.MovieId, true);
		GetTag(TagTypes.DivX, true);
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

			// Enclose the Id3v2 tag in an "ID32" item and
			// embed it as the first tag.
			if (id32_tag != null)
			{
				ByteVector tag_data = id32_tag.Render();
				if (tag_data.size() > 10)
				{
					if (tag_data.size() % 2 == 1)
					{
						tag_data.add(0);
					}
					data.add("ID32");
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) tag_data.Count, false));
					data.add(ByteVector.FromUInt((int) tag_data.size(), false));
					data.add(tag_data);
				}
			}

			// Embed "INFO" as the second tag.
			if (info_tag != null)
			{
				data.add(info_tag.RenderEnclosed());
			}

			// Embed "MID " as the third tag.
			if (mid_tag != null)
			{
				data.add(mid_tag.RenderEnclosed());
			}

			// Embed the DivX tag in "IDVX and embed it as
			// the fourth tag.
			if (divx_tag != null && !divx_tag.getIsEmpty())
			{
				ByteVector tag_data = divx_tag.Render();
				data.add("IDVX");
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) tag_data.Count, false));
				data.add(ByteVector.FromUInt((int) tag_data.size(), false));
				data.add(tag_data);
			}

			// Read the file to determine the current RIFF
			// size and the area tagging does in.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint riff_size;
			int riff_size;
			long tag_start, tag_end;
			tangible.OutObject<Integer> tempOut_riff_size = new tangible.OutObject<Integer>();
			tangible.OutObject<Long> tempOut_tag_start = new tangible.OutObject<Long>();
			tangible.OutObject<Long> tempOut_tag_end = new tangible.OutObject<Long>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Read(false, ReadStyle.None, out riff_size, out tag_start, out tag_end);
			Read(false, ReadStyle.None, tempOut_riff_size, tempOut_tag_start, tempOut_tag_end);
		tag_end = tempOut_tag_end.argValue;
		tag_start = tempOut_tag_start.argValue;
		riff_size = tempOut_riff_size.argValue;

			// If tagging info cannot be found, place it at
			// the end of the file.
			if (tag_start < 12 || tag_end < tag_start)
			{
				tag_start = tag_end = Length;
			}

			int length = (int)(tag_end - tag_start);

			// If the tag isn't at the end of the file,
			// try appending using padding to improve
			// write time now or for subsequent writes.
			if (tag_end != Length)
			{
				int padding_size = length - data.size() - 8;
				if (padding_size < 0)
				{
					padding_size = 1024;
				}


				data.add("JUNK");
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint)padding_size, false));
				data.add(ByteVector.FromUInt((int)padding_size, false));
				data.add(new ByteVector(padding_size));
			}

			// Insert the tagging data.
			Insert(data, tag_start, length);

			// If the data size changed, and the tagging
			// data is within the RIFF portion of the file,
			// update the riff size.
			if (data.size() - length != 0 && tag_start <= riff_size)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Insert(ByteVector.FromUInt((uint)(riff_size + data.Count - length), false), 4, 4);
				Insert(ByteVector.FromUInt((int)(riff_size + data.size() - length), false), 4, 4);
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
		if ((types.getValue() & Rasad.Core.Media.MediaMetadataManagement.TagTypes.Id3v2.getValue()) != Rasad.Core.Media.MediaMetadataManagement.TagTypes.None.getValue())
		{
			id32_tag = null;
		}
		if ((types.getValue() & Rasad.Core.Media.MediaMetadataManagement.TagTypes.RiffInfo.getValue()) != Rasad.Core.Media.MediaMetadataManagement.TagTypes.None.getValue())
		{
			info_tag = null;
		}
		if ((types.getValue() & Rasad.Core.Media.MediaMetadataManagement.TagTypes.MovieId.getValue()) != Rasad.Core.Media.MediaMetadataManagement.TagTypes.None.getValue())
		{
			mid_tag = null;
		}
		if ((types.getValue() & Rasad.Core.Media.MediaMetadataManagement.TagTypes.DivX.getValue()) != Rasad.Core.Media.MediaMetadataManagement.TagTypes.None.getValue())
		{
			divx_tag = null;
		}

		tag.SetTags(id32_tag, info_tag, mid_tag, divx_tag);
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
		Rasad.Core.Media.MediaMetadataManagement.Tag tag = null;

		switch (type)
		{
		case TagTypes.Id3v2:
			if (id32_tag == null && create)
			{
				id32_tag = new Id3v2.Tag();
				id32_tag.setVersion((byte)4);
				id32_tag.setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.HeaderFlags.forValue(id32_tag.getFlags().getValue() | Id3v2.HeaderFlags.FooterPresent.getValue()));
				this.tag.CopyTo(id32_tag, true);
			}

			tag = id32_tag;
			break;

		case TagTypes.RiffInfo:
			if (info_tag == null && create)
			{
				info_tag = new InfoTag();
				this.tag.CopyTo(info_tag, true);
			}

			tag = info_tag;
			break;

		case TagTypes.MovieId:
			if (mid_tag == null && create)
			{
				mid_tag = new MovieIdTag();
				this.tag.CopyTo(mid_tag, true);
			}

			tag = mid_tag;
			break;

		case TagTypes.DivX:
			if (divx_tag == null && create)
			{
				divx_tag = new DivXTag();
				this.tag.CopyTo(divx_tag, true);
			}

			tag = divx_tag;
			break;
		}

		this.tag.SetTags(id32_tag, info_tag, mid_tag, divx_tag);
		return tag;
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
	 
	 @param riff_size
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
//ORIGINAL LINE: private void Read(bool read_tags, ReadStyle style, out uint riff_size, out long tag_start, out long tag_end)
	private void Read(boolean read_tags, ReadStyle style, tangible.OutObject<Integer> riff_size, tangible.OutObject<Long> tag_start, tangible.OutObject<Long> tag_end)
	{
		Seek(0);
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpInequality(ReadBlock(4), FileIdentifier))
		{
			throw new CorruptFileException("File does not begin with RIFF identifier");
		}

		riff_size.argValue = ReadBlock(4).ToUInt(false);
		ByteVector stream_format = ReadBlock(4);
		tag_start.argValue = -1;
		tag_end.argValue = -1;

		long position = 12;
		long length = Length;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint size = 0;
		int size = 0;
		TimeSpan duration = TimeSpan.Zero;
		ICodec [] codecs = new ICodec [0];

		// Read until there are less than 8 bytes to read.
		do
		{
			boolean tag_found = false;

			Seek(position);
			String fourcc = ReadBlock(4).toString(StringType.UTF8);
			size = ReadBlock(4).ToUInt(false);

			switch (fourcc)
			{

			// "fmt " is used by Wave files to hold the
			// WaveFormatEx structure.
			case "fmt ":
				if (style == ReadStyle.None || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(stream_format, "WAVE"))
				{
					break;
				}

				Seek(position + 8);
				codecs = new ICodec [] {new WaveFormatEx(ReadBlock(18), 0)};
				break;

			// "data" contains the audio data for wave
			// files. It's contents represent the invariant
			// portion of the file and is used to determine
			// the duration of a file. It should always
			// appear after "fmt ".
			case "data":
				if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(stream_format, "WAVE"))
				{
					break;
				}

				InvariantStartPosition = position;
				InvariantEndPosition = position + size;

				if (style == ReadStyle.None || codecs.length != 1 || !(codecs [0] instanceof WaveFormatEx))
				{
					break;
				}

				duration += TimeSpan.FromSeconds((double) size / (double)((WaveFormatEx) codecs [0]).getAverageBytesPerSecond());

				break;

			// Lists are used to store a variety of data
			// collections. Read the type and act on it.
			case "LIST":
			{
				switch (ReadBlock(4).toString(StringType.UTF8))
				{

				// "hdlr" is used by AVI files to hold
				// a media header and BitmapInfoHeader
				// and WaveFormatEx structures.
				case "hdrl":
					if (style == ReadStyle.None || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(stream_format, "AVI "))
					{
						continue;
					}

					AviHeaderList header_list = new AviHeaderList(this, position + 12, (int)(size - 4));
					duration = header_list.getHeader().getDuration();
					codecs = header_list.getCodecs();
					break;

				// "INFO" is a tagging format handled by
				// the InfoTag class.
				case "INFO":
					if (read_tags && info_tag == null)
					{
						info_tag = new InfoTag(this, position + 12, (int)(size - 4));
					}

					tag_found = true;
					break;

				// "MID " is a tagging format handled by
				// the MovieIdTag class.
				case "MID ":
					if (read_tags && mid_tag == null)
					{
						mid_tag = new MovieIdTag(this, position + 12, (int)(size - 4));
					}

					tag_found = true;
					break;

				// "movi" contains the media data for
				// and AVI and its contents represent
				// the invariant portion of the file.
				case "movi":
					if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(stream_format, "AVI "))
					{
						break;
					}

					InvariantStartPosition = position;
					InvariantEndPosition = position + size;
					break;
				}
				break;
			}

			// "ID32" is a custom box for this format that
			// contains an ID3v2 tag.
			case "ID32":
				if (read_tags && id32_tag == null)
				{
					id32_tag = new Id3v2.Tag(this, position + 8);
				}

				tag_found = true;
				break;

			// "IDVX" is used by DivX and holds an ID3v1-
			// style tag.
			case "IDVX":
				if (read_tags && divx_tag == null)
				{
					divx_tag = new DivXTag(this, position + 8);
				}

				tag_found = true;
				break;

			// "JUNK" is a padding element that could be
			// associated with tag data.
			case "JUNK":
				if (tag_end.argValue == position)
				{
					tag_end.argValue = position + 8 + size;
				}
				break;
			}

			// Determine the region of the file that
			// contains tags.
			if (tag_found)
			{
				if (tag_start.argValue == -1)
				{
					tag_start.argValue = position;
					tag_end.argValue = position + 8 + size;
				}
				else if (tag_end.argValue == position)
				{
					tag_end.argValue = position + 8 + size;
				}
			}

			// Move to the next item.
		} while ((position += 8 + size) + 8 < length);

		// If we're reading properties, and one were found,
		// throw an exception. Otherwise, create the Properties
		// object.
		if (style != ReadStyle.None)
		{
			if (codecs.length == 0)
			{
				throw new UnsupportedFormatException("Unsupported RIFF type.");
			}

			properties = new Properties(duration, codecs);
		}

		// If we're reading tags, update the combined tag.
		if (read_tags)
		{
			tag.SetTags(id32_tag, info_tag, mid_tag, divx_tag);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}