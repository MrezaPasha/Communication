package Rasad.Core.Media.MediaMetadataManagement.Image;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// ImageBlockFile.cs: Base class for Images files which are organized
//                    which are organized as blocks.
//
// Author:
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2010 Mike Gemuende
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
	Some image file formats are organized as a sequence of mostly
	independent data blocks whose order can be changed. Metadata is
	stored in some of those blocks and when metadata is saved, often the
	same task remains: Delete some blocks which contain metadata and
	overwrite some blocks with other metadata.
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Image.File" /> to provide this
	functionality. Blocks can be marked as metadata and when metadata is
	saved their space is used or they are deleted.
*/
public abstract class ImageBlockFile extends Rasad.Core.Media.MediaMetadataManagement.Image.File
{

	/** 
		This class represents a metadata block to overwrite.
	*/
	private static class MetadataBlock
	{

		/** 
			The start index
		*/
		private long Start;
		public final long getStart()
		{
			return Start;
		}
		public final void setStart(long value)
		{
			Start = value;
		}

		/** 
			The length of the block
		*/
		private long Length;
		public final long getLength()
		{
			return Length;
		}
		public final void setLength(long value)
		{
			Length = value;
		}


		/** 
			Constructor
		 
		 @param start
			A <see cref="System.Int64"/> with the start of the block
		 
		 @param length
			A <see cref="System.Int64"/> with the length of the block
		 
		*/
		public MetadataBlock(long start, long length)
		{
			if (start < 0)
			{
				throw new IndexOutOfBoundsException("start");
			}

			if (length < 0)
			{
				throw new IndexOutOfBoundsException("length");
			}

			setStart(start);
			setLength(length);
		}

		/** 
			Constructor. Creates a new instance with an empty block
		*/
		public MetadataBlock()
		{
			this(0, 0);
		}


		/** 
			Checks if the given block overlaps with this instance.
		 
		 @param block
			A <see cref="MetadataBlock"/> with the block to check
			overlapping.
		 
		 @return 
			A <see cref="System.Boolean"/> which is true, if the given
			block overlapps with the current instance.
		 
		 
			Overlapping means here also that blocks directly follow.
		 
		*/
		public final boolean OverlapsWith(MetadataBlock block)
		{
			if (block.getStart() >= getStart() && block.getStart() <= getStart() + getLength())
			{
				return true;
			}

			if (getStart() >= block.getStart() && getStart() <= block.getStart() + block.getLength())
			{
				return true;
			}

			return false;
		}

		/** 
			Adds the given block to the current instance, if this is possible.
		 
		 @param block
			A <see cref="MetadataBlock"/> with the block to add.
		 
		*/
		public final void Add(MetadataBlock block)
		{
			if (block.getStart() >= getStart() && block.getStart() <= getStart() + getLength())
			{
				setLength(Math.max(getLength(), block.getStart() + block.getLength() - getStart()));
				return;
			}

			if (getStart() >= block.getStart() && getStart() <= block.getStart() + block.getLength())
			{
				setLength(Math.max(block.getLength(), getStart() + getLength() - block.getStart()));
				setStart(block.getStart());
				return;
			}

			throw new IllegalArgumentException(String.format("blocks do not overlap: %1$s and %2$s", this, block));
		}


		/** 
			Checks, if the one block is before the other. That means,
			if the current instance ends before the given block starts.
		 
		 @param block
			A <see cref="MetadataBlock"/> to compare with.
		 
		 @return 
			A <see cref="System.Boolean"/> which is true if the current
			instance is before the given block.
		 
		*/
		public final boolean Before(MetadataBlock block)
		{
			return (getStart() + getLength() < block.getStart());
		}


		/** 
			Provides a readable <see cref="System.String"/> for
			the current instance.
		 
		 @return 
			A <see cref="System.String"/> representing the current
			instance.
		 
		*/
		@Override
		public String toString()
		{
			return String.format("[MetadataBlock: Start=%1$s, Length=%2$s]", getStart(), getLength());
		}
	}

	/** 
		An odered list of the metadata blocks. The blocks do not overlap.
	*/
	private ArrayList<MetadataBlock> metadata_blocks = new ArrayList<MetadataBlock> ();


	/** 
		Adds a range to be treated as metadata.
	 
	 @param start
		A <see cref="System.Int64"/> with the start index of the metadata block
	 
	 @param length
		A <see cref="System.Int64"/> with the length of the metadata block
	 
	*/
	protected final void AddMetadataBlock(long start, long length)
	{
		MetadataBlock new_block = new MetadataBlock(start, length);

		// We keep the list sorted and unique. Therefore, we add the new block to
		// the list and join overlapping blocks if necessary.

		// iterate through all existing blocks.
		for (int i = 0; i < metadata_blocks.size(); i++)
		{

			MetadataBlock block = metadata_blocks.get(i);

			// if one block overlaps with the new one, join them.
			if (new_block.OverlapsWith(block))
			{
				block.Add(new_block);

				// Since we joined two blocks, they may overlap with
				// other blocks which follows in the list. Therfore,
				// we iterate through the tail of the list and join
				// blocks which are now contained.
				i++;
				while (i < metadata_blocks.size())
				{
					MetadataBlock next_block = metadata_blocks.get(i);

					if (block.OverlapsWith(next_block))
					{
						block.Add(next_block);
						metadata_blocks.remove(next_block);
					}
					else
					{
						return;
					}

				}

				return;

				// if the new block is 'smaller' than the one in the list,
				// just add it to the list.
			}
			else if (new_block.Before(block))
			{
				metadata_blocks.add(i, new_block);
				return;
			}
		}

		// if the new block is 'bigger' than all other blocks, at it to the end.
		metadata_blocks.add(new_block);
	}


	/** 
		Saves the given data at the given position. All metadata blocks are
		either deleted or overwritten.
	 
	 @param data
		A <see cref="ByteVector"/> with the metadata to write.
	 
	 @param start
		A <see cref="System.Int64"/> with the index to save the metadata at.
	 
	*/
	protected final void SaveMetadata(ByteVector data, long start)
	{
		long new_start = 0;

		// this ensures that the block with the start index is contained.
		AddMetadataBlock(start, 0);

		// start iterating through the metadata block from the end,
		// because deleting such blocks do not affect the smaller indices.
		for (int i = metadata_blocks.size() - 1; i >= 0; i--)
		{
			MetadataBlock block = metadata_blocks.get(i);

			// this is the block to save the metadata in
			if (block.getStart() <= start && block.getStart() + block.getLength() >= start)
			{

				// the metadata is saved starting at the beginning of the block,
				// because the bytes will be removed.
				Insert(data, block.getStart(), block.getLength());
				new_start = block.getStart();

			}
			else
			{

				// remove block
				Insert("", block.getStart(), block.getLength());

				// update start of the metadata block, if metadata was written
				// before, i.e. we have removed a block which is before the saved
				// metadata
				if (block.getStart() < start)
				{
					new_start -= block.getLength();
				}

			}
		}

		// and reset the metadata blocks
		// (there is now just one block contained)
		metadata_blocks.clear();
		AddMetadataBlock(new_start, data.size());
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance for a specified
		path in the local file system.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	protected ImageBlockFile(String path)
	{
		super(path);
	}

	/** 
		Constructs and initializes a new instance for a specified
		file abstraction.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	protected ImageBlockFile(IFileAbstraction abstraction)
	{
		super(abstraction);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}