package Rasad.Core.Media.MediaMetadataManagement.Flac;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Block.cs: Represents a Flac metadata block.
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
	This class represents a Flac metadata block.
*/
public class Block
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the block header.
	*/
	private BlockHeader header = new BlockHeader();

	/** 
		Contains the block data.
	*/
	private ByteVector data;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Block" /> with a specified header and internal
		data.
	 
	 @param header
		A <see cref="BlockHeader" /> object containing the
		header to use for the new instance.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the data
		to be contained in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		The size of <paramref name="data" /> does not match the
		size specified in <paramref name="header" />.
	 
	*/
	public Block(BlockHeader header, ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (header.getBlockSize() != data.size())
		{
			throw new CorruptFileException("Data count not equal to block size.");
		}

		this.header = header.clone();
		this.data = data;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Block" /> with of a specified type and internal
		data.
	 
	 @param type
		A <see cref="BlockType" /> value indicating the type of
		data stored in <paramref name="data" />.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the data
		to be contained in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
	public Block(BlockType type, ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: header = new BlockHeader(type, (uint) data.Count);
		header = new BlockHeader(type, (int) data.size());

		this.data = data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the type of data contained in the current instance.
	 
	 <value>
		A <see cref="BlockType" /> value indicating the type of
		data contained in <see cref="Data" />.
	 </value>
	*/
	public final BlockType getType()
	{
		return header.getBlockType();
	}

	/** 
		Gets whether or not the block represented by the current
		instance is the last metadata block in the Flac stream.
	 
	 <value>
		<see langword="true" /> if the block represented by the
		current instance was the last one to appear in the file
		and is followed immediately by the audio data, or <see
		langword="false" /> if another block appears after the
		current one or the block was not read from disk.
	 </value>
	*/
	public final boolean getIsLastBlock()
	{
		return header.getIsLastBlock();
	}

	/** 
		Gets the size of the data contained in the current
		instance.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getDataSize()
	public final int getDataSize()
	{
		return header.getBlockSize();
	}

	/** 
		Gets the total size of the block represented by the
		current instance as it appears on disk.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getTotalSize()
	public final int getTotalSize()
	{
		return getDataSize() + BlockHeader.Size;
	}

	/** 
		Gets the data contained in the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the data
		stored in the current instance.
	 </value>
	*/
	public final ByteVector getData()
	{
		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Renders the current instance as a raw Flac metadata
		block.
	 
	 @param isLastBlock
		A <see cref="bool" /> value indicating whether or not the
		block is to be marked as the last metadata block.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	public final ByteVector Render(boolean isLastBlock)
	{
		if (this.data == null)
		{
			throw new IllegalStateException("Cannot render empty blocks.");
		}

		ByteVector data = header.Render(isLastBlock);
		data.add(this.data);
		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}