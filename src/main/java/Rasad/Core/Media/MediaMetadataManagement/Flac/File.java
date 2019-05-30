package Rasad.Core.Media.MediaMetadataManagement.Flac;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// File.cs: Provides tagging and properties support for Xiph's Flac audio files.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   flacfile.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2006-2007 Brian Nickel
// Copyright (C) 2003-2004 Allan Sandfeld Jensen (Original Implementation)
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.NonContainer.File" /> to
	provide tagging and properties support for Xiph's Flac audio
	files.
 
 
	A <see cref="Rasad.Core.Media.MediaMetadataManagement.Ogg.XiphComment" /> will be added
	automatically to any file that doesn't contain one. This change
	does not effect the physical file until <see cref="Save" /> is
	called and can be reversed using the following method:
	<code>file.RemoveTags (file.TagTypes &amp; ~file.TagTypesOnDisk);</code>
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/flac", "flac")][SupportedMimeType("audio/x-flac")][SupportedMimeType("application/x-flac")][SupportedMimeType("audio/flac")] public class File : Rasad.Core.Media.MediaMetadataManagement.NonContainer.File
public class File extends Rasad.Core.Media.MediaMetadataManagement.NonContainer.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the Flac metadata tag.
	*/
	private Metadata metadata = null;

	/** 
		Contains the combination of all file tags.
	*/
	private CombinedTag tag = null;

	/** 
		Contains the Flac header block.
	*/
	private ByteVector header_block = null;

	/** 
		Contains the stream start position.
	*/
	private long stream_start = 0;

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
		super(path, propertiesStyle);
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
		super(path);
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
		super(abstraction, propertiesStyle);
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
		super(abstraction);
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
		return tag;
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
			// Update the tags at the beginning of the file.
			long metadata_start = StartTag.Write();
			long metadata_end;

			// Get all the blocks, but don't read the data for ones
			// we're filling with stored data.
			tangible.RefObject<Long> tempRef_metadata_start = new tangible.RefObject<Long>(metadata_start);
			tangible.OutObject<Long> tempOut_metadata_end = new tangible.OutObject<Long>();
			List<Block> old_blocks = ReadBlocks(tempRef_metadata_start, tempOut_metadata_end, BlockMode.Blacklist, BlockType.XiphComment, BlockType.Picture);
		metadata_end = tempOut_metadata_end.argValue;
		metadata_start = tempRef_metadata_start.argValue;

			// Create new vorbis comments is they don't exist.
			GetTag(TagTypes.Xiph, true);

			// Create new blocks and add the basics.
			ArrayList<Block> new_blocks = new ArrayList<Block> ();
			new_blocks.add(old_blocks.get(0));

			// Add blocks we don't deal with from the file.
			for (Block block : old_blocks)
			{
				if (block.getType() != BlockType.StreamInfo && block.getType() != BlockType.XiphComment && block.getType() != BlockType.Picture && block.getType() != BlockType.Padding)
				{
					new_blocks.add(block);
				}
			}

			Rasad.Core.Media.MediaMetadataManagement.Tag tempVar = GetTag(TagTypes.Xiph, true);
			new_blocks.add(new Block(BlockType.XiphComment, (tempVar instanceof Ogg.XiphComment ? (Ogg.XiphComment)tempVar : null).Render(false)));

			for (IPicture picture : metadata.getPictures())
			{
				if (picture == null)
				{
					continue;
				}

				new_blocks.add(new Block(BlockType.Picture, (new Picture(picture)).Render()));
			}

			// Get the length of the blocks.
			long length = 0;
			for (Block block : new_blocks)
			{
				length += block.getTotalSize();
			}

			// Find the padding size to avoid trouble. If that fails
			// make some.
			long padding_size = metadata_end - metadata_start - BlockHeader.Size - length;
			if (padding_size < 0)
			{
				padding_size = 1024 * 4;
			}

			// Add a padding block.
			if (padding_size != 0)
			{
				new_blocks.add(new Block(BlockType.Padding, new ByteVector((int) padding_size)));
			}

			// Render the blocks.
			ByteVector block_data = new ByteVector();
			for (int i = 0; i < new_blocks.size(); i++)
			{
				block_data.add(new_blocks.get(i).Render(i == new_blocks.size() - 1));
			}

			// Update the blocks.
			Insert(block_data, metadata_start, metadata_end - metadata_start);

			// Update the tags at the end of the file.
			EndTag.Write();

			TagTypesOnDisk = TagTypes;
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
		switch (type)
		{
		case TagTypes.Xiph:
			return metadata.GetComment(create, tag);

		case TagTypes.FlacMetadata:
			return metadata;
		}

		Rasad.Core.Media.MediaMetadataManagement.Tag tempVar = super.getTag();
		Tag t = (tempVar instanceof Rasad.Core.Media.MediaMetadataManagement.NonContainer.Tag ? (Rasad.Core.Media.MediaMetadataManagement.NonContainer.Tag)tempVar : null).GetTag(type);

		if (t != null || !create)
		{
			return t;
		}

		switch (type)
		{
		case TagTypes.Id3v1:
			return EndTag.AddTag(type, getTag());

		case TagTypes.Id3v2:
			return StartTag.AddTag(type, getTag());

		case TagTypes.Ape:
			return EndTag.AddTag(type, getTag());

		default:
			return null;
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
		if ((types.getValue() & TagTypes.Xiph.getValue()) != 0)
		{
			metadata.RemoveComment();
		}

		if ((types.getValue() & TagTypes.FlacMetadata.getValue()) != 0)
		{
			metadata.Clear();
		}

		super.RemoveTags(types);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Reads format specific information at the start of the
		file.
	 
	 @param start
		A <see cref="long" /> value containing the seek position
		at which the tags end and the media data begins.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	@Override
	protected void ReadStart(long start, ReadStyle propertiesStyle)
	{
		long end;
		tangible.RefObject<Long> tempRef_start = new tangible.RefObject<Long>(start);
		tangible.OutObject<Long> tempOut_end = new tangible.OutObject<Long>();
		List<Block> blocks = ReadBlocks(tempRef_start, tempOut_end, BlockMode.Whitelist, BlockType.StreamInfo, BlockType.XiphComment, BlockType.Picture);
	end = tempOut_end.argValue;
	start = tempRef_start.argValue;
		metadata = new Metadata(blocks);

		TagTypesOnDisk |= metadata.getTagTypes();

		if (propertiesStyle != ReadStyle.None)
		{
			// Check that the first block is a
			// METADATA_BLOCK_STREAMINFO.
			if (blocks.isEmpty() || blocks.get(0).getType() != BlockType.StreamInfo)
			{
				throw new CorruptFileException("FLAC stream does not begin with StreamInfo.");
			}

			// The stream exists from the end of the last
			// block to the end of the file.
			stream_start = end;
			header_block = blocks.get(0).getData();
		}
	}

	/** 
		Reads format specific information at the end of the
		file.
	 
	 @param end
		A <see cref="long" /> value containing the seek position
		at which the media data ends and the tags begin.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	@Override
	protected void ReadEnd(long end, ReadStyle propertiesStyle)
	{
		tag = new CombinedTag(metadata, super.getTag());
		GetTag(TagTypes.Xiph, true);
	}

	/** 
		Reads the audio properties from the file represented by
		the current instance.
	 
	 @param start
		A <see cref="long" /> value containing the seek position
		at which the tags end and the media data begins.
	 
	 @param end
		A <see cref="long" /> value containing the seek position
		at which the media data ends and the tags begin.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @return 
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Properties" /> object describing the
		media properties of the file represented by the current
		instance.
	 
	*/
	@Override
	protected Properties ReadProperties(long start, long end, ReadStyle propertiesStyle)
	{
		StreamHeader header = new StreamHeader(header_block, end - stream_start);
		return new Properties(TimeSpan.Zero, header.clone());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Indicates whether or not the block types passed into
		<see cref="ReadBlocks" /> are to be white-listed or
		black-listed.
	*/
	private enum BlockMode
	{
		/** 
			All block types except those provided are to be
			returned.
		*/
		Blacklist,

		/** 
			Only those block types provides should be
			returned.
		*/
		Whitelist;

		public static final int SIZE = java.lang.Integer.SIZE;

		public int getValue()
		{
			return this.ordinal();
		}

		public static BlockMode forValue(int value)
		{
			return values()[value];
		}
	}

	/** 
		Reads all metadata blocks starting from the current
		instance, starting at a specified position.
	 
	 @param start
		A <see cref="long" /> value reference specifying the
		position at which to start searching for the blocks. This
		will be updated to the position of the first block.
	 
	 @param end
		A <see cref="long" /> value reference updated to the
		position at which the last block ends.
	 
	 @param mode
		A <see cref="BlockMode" /> value indicating whether to
		white-list or black-list the contents of <paramref
		name="types" />.
	 
	 @param types
		A <see cref="BlockType[]" /> containing the types to look
		for or not look for as specified by <paramref name="mode"
		/>.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IList`1" /> object containing the blocks
		read from the current instance.
	 
	 @exception CorruptFileException
		"<c>fLaC</c>" could not be found.
	 
	*/
	private List<Block> ReadBlocks(tangible.RefObject<Long> start, tangible.OutObject<Long> end, BlockMode mode, BlockType... types)
	{
		ArrayList<Block> blocks = new ArrayList<Block> ();

		long start_position = Find("fLaC", start.argValue);

		if (start_position < 0)
		{
			throw new CorruptFileException("FLAC stream not found at starting position.");
		}

		end.argValue = start.argValue = start_position + 4;

		Seek(start.argValue);

		BlockHeader header = new BlockHeader();

		do
		{
			header = new BlockHeader(ReadBlock((int) BlockHeader.Size));

			boolean found = false;
			for (BlockType type : types)
			{
				if (header.getBlockType() == type)
				{
					found = true;
					break;
				}
			}

			if ((mode == BlockMode.Whitelist && found) || (mode == BlockMode.Blacklist && !found))
			{
				blocks.add(new Block(header.clone(), ReadBlock((int) header.getBlockSize())));
			}
			else
			{
				Seek(header.getBlockSize(), System.IO.SeekOrigin.Current);
			}

			end.argValue += header.getBlockSize() + BlockHeader.Size;
		} while (!header.getIsLastBlock());

		return blocks;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}