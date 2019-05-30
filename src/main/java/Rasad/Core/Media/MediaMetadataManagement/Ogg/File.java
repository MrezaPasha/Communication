package Rasad.Core.Media.MediaMetadataManagement.Ogg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// File.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   oggfile.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2003 Scott Wheeler (Original Implementation)
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
	and properties support for Ogg files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/ogg", "ogg")][SupportedMimeType("taglib/oga", "oga")][SupportedMimeType("taglib/ogv", "ogv")][SupportedMimeType("application/ogg")][SupportedMimeType("application/x-ogg")][SupportedMimeType("audio/vorbis")][SupportedMimeType("audio/x-vorbis")][SupportedMimeType("audio/x-vorbis+ogg")][SupportedMimeType("audio/ogg")][SupportedMimeType("audio/x-ogg")][SupportedMimeType("video/ogg")][SupportedMimeType("video/x-ogm+ogg")][SupportedMimeType("video/x-theora+ogg")][SupportedMimeType("video/x-theora")] public class File : Rasad.Core.Media.MediaMetadataManagement.File
public class File extends Rasad.Core.Media.MediaMetadataManagement.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
	   Contains the tags for the file.
	*/
	private GroupedComment tag;

	/** 
		Contains the media properties.
	*/
	private Properties properties;

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
			tag = new GroupedComment();
			Read(propertiesStyle);
			TagTypesOnDisk = TagTypes;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}
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
			long end;
			ArrayList<Page> pages = new ArrayList<Page> ();
			tangible.OutObject<Long> tempOut_end = new tangible.OutObject<Long>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Dictionary<uint, Bitstream> streams = ReadStreams(pages, out end);
			HashMap<Integer, Bitstream> streams = ReadStreams(pages, tempOut_end);
		end = tempOut_end.argValue;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Dictionary<uint, Paginator> paginators = new Dictionary<uint, Paginator> ();
			HashMap<Integer, Paginator> paginators = new HashMap<Integer, Paginator> ();
			ArrayList<ArrayList<Page>> new_pages = new ArrayList<ArrayList<Page>> ();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Dictionary<uint, int> shifts = new Dictionary<uint, int> ();
			HashMap<Integer, Integer> shifts = new HashMap<Integer, Integer> ();

			for (Page page : pages)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint id = page.Header.StreamSerialNumber;
				int id = page.getHeader().getStreamSerialNumber();
				if (!paginators.containsKey(id))
				{
					paginators.put(id, new Paginator(streams.get(id).getCodec()));
				}

				paginators.get(id).AddPage(page);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (uint id in paginators.Keys)
			for (int id : paginators.keySet())
			{
				paginators.get(id).SetComment(tag.GetComment(id));
				int shift;
				tangible.OutObject<Integer> tempOut_shift = new tangible.OutObject<Integer>();
				new_pages.add(new ArrayList<Page> (Arrays.asList(paginators.get(id).Paginate(tempOut_shift))));
			shift = tempOut_shift.argValue;
				shifts.put(id, shift);
			}

			ByteVector output = new ByteVector();
			boolean empty;
			do
			{
				empty = true;
				for (ArrayList<Page> stream_pages : new_pages)
				{
					if (stream_pages.isEmpty())
					{
						continue;
					}

				output.add(stream_pages.get(0).Render());
				stream_pages.remove(0);

				if (!stream_pages.isEmpty())
				{
					empty = false;
				}
				}
			} while (!empty);

			Insert(output, 0, end);
			InvariantStartPosition = output.size();
			InvariantEndPosition = Length;

			TagTypesOnDisk = TagTypes;

			Page.OverwriteSequenceNumbers(this, output.size(), shifts);
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
	public void RemoveTags(Rasad.Core.Media.MediaMetadataManagement.TagTypes types)
	{
		if ((types.getValue() & Rasad.Core.Media.MediaMetadataManagement.TagTypes.Xiph.getValue()) != Rasad.Core.Media.MediaMetadataManagement.TagTypes.None.getValue())
		{
			tag.Clear();
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
	public Rasad.Core.Media.MediaMetadataManagement.Tag GetTag(Rasad.Core.Media.MediaMetadataManagement.TagTypes type, boolean create)
	{
		if (type == Rasad.Core.Media.MediaMetadataManagement.TagTypes.Xiph)
		{
			for (XiphComment comment : tag.getComments())
			{
				return comment;
			}
		}

		return null;
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
///#region Private Methods

	/** 
		Reads the file with a specified read style.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	private void Read(ReadStyle propertiesStyle)
	{
		long end;
		tangible.OutObject<Long> tempOut_end = new tangible.OutObject<Long>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Dictionary<uint, Bitstream> streams = ReadStreams(null, out end);
		HashMap<Integer, Bitstream> streams = ReadStreams(null, tempOut_end);
	end = tempOut_end.argValue;
		ArrayList<ICodec> codecs = new ArrayList<ICodec> ();
		InvariantStartPosition = end;
		InvariantEndPosition = Length;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (uint id in streams.Keys)
		for (int id : streams.keySet())
		{
			tag.AddComment(id, streams.get(id).getCodec().getCommentData());
			codecs.add(streams.get(id).getCodec());
		}

		if (propertiesStyle == ReadStyle.None)
		{
			return;
		}

		PageHeader last_header = getLastPageHeader().clone();

		TimeSpan duration = streams.get(last_header.getStreamSerialNumber()).GetDuration(last_header.getAbsoluteGranularPosition());
		properties = new Properties(duration, codecs);
	}

	/** 
		Reads the file until all streams have finished their
		property and tagging data.
	 
	 @param pages
		A <see cref="T:System.Collections.Generic.List`1"/>
		object to be filled with <see cref="Page" /> objects as
		they are read, or <see langword="null"/> if the pages
		are not to be stored.
	 
	 @param end
		A <see cref="long" /> value reference to be updated to
		the postion of the first page not read by the current
		instance.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.Dictionary`2"
		/> object containing stream serial numbers as the keys
		<see cref="Bitstream" /> objects as the values.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private Dictionary<uint, Bitstream> ReadStreams(List<Page> pages, out long end)
	private HashMap<Integer, Bitstream> ReadStreams(ArrayList<Page> pages, tangible.OutObject<Long> end)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Dictionary<uint, Bitstream> streams = new Dictionary<uint, Bitstream> ();
		HashMap<Integer, Bitstream> streams = new HashMap<Integer, Bitstream> ();
		ArrayList<Bitstream> active_streams = new ArrayList<Bitstream> ();

		long position = 0;

		do
		{
			Bitstream stream = null;
			Page page = new Page(this, position);

			if ((page.getHeader().getFlags().getValue() & PageFlags.FirstPageOfStream.getValue()) != 0)
			{
				stream = new Bitstream(page);
				streams.put(page.getHeader().getStreamSerialNumber(), stream);
				active_streams.add(stream);
			}

			if (stream == null)
			{
				stream = streams.get(page.getHeader().getStreamSerialNumber());
			}

			if (active_streams.contains(stream) && stream.ReadPage(page))
			{
				active_streams.remove(stream);
			}

			if (pages != null)
			{
				pages.add(page);
			}

			position += page.getSize();
		} while (!active_streams.isEmpty());

		end.argValue = position;

		return streams;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Properties

	/** 
		Gets the last page header in the file.
	 
	 <value>
		A <see cref="PageHeader" /> object containing the last
		page header in the file.
	 </value>
	 
		The last page header is used to determine the last
		absolute granular position of a stream so the duration
		can be calculated.
	 
	*/
	private PageHeader getLastPageHeader()
	{
		long last_page_header_offset = RFind("OggS");

		if (last_page_header_offset < 0)
		{
			throw new CorruptFileException("Could not find last header.");
		}

		return new PageHeader(this, last_page_header_offset);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}