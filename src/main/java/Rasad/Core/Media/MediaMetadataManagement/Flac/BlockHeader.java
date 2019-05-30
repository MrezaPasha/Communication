package Rasad.Core.Media.MediaMetadataManagement.Flac;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	This structure provides a representation of a Flac metadata block
	header structure.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct BlockHeader
public final class BlockHeader
{
	/** 
		Contains the block type.
	*/
	private BlockType block_type = getBlockType().values()[0];

	/** 
		Indicates whether or not this is the last metadata block.
	*/
	private boolean is_last_block;

	/** 
		Contains the block size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint block_size;
	private int block_size;

	/** 
		The size of a block header.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint Size = 4;
	public static final int Size = 4;

	/** 
		Constructs and initializes a new instance of <see
		cref="BlockHeader" /> by reading a raw header from a <see
		cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing a raw
		block header.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> contains less than 4 bytes.
	 
	*/
	public BlockHeader()
	{
	}

	public BlockHeader(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (data.size() < Size)
		{
			throw new CorruptFileException("Not enough data in Flac header.");
		}

		block_type = BlockType.forValue(data.get(0) & 0x7f);
		is_last_block = (data.get(0) & 0x80) != 0;
		block_size = data.Mid(1, 3).ToUInt();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="BlockHeader" /> for a specified block type and size.
	 
	 @param type
		A <see cref="BlockType" /> value describing the contents
		of the block.
	 
	 @param blockSize
		A <see cref="uint" /> value containing the block data
		size minus the size of the header.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public BlockHeader(BlockType type, uint blockSize)
	public BlockHeader(BlockType type, int blockSize)
	{
		block_type = type;
		is_last_block = false;
		block_size = blockSize;
	}

	/** 
		Renderes the current instance as a raw Flac block header.
	 
	 @param isLastBlock
		A <see cref="bool" /> value specifying whether or not the
		header is the last header of the file.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered header.
	 
	*/
	public ByteVector Render(boolean isLastBlock)
	{
		ByteVector data = ByteVector.FromUInt(block_size);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data [0] = (byte)(block_type + (isLastBlock ? 0x80 : 0));
		data.set(0, (byte)(block_type + (isLastBlock ? 0x80 : 0)));
		return data;
	}

	/** 
		Gets the type of block described by the current instance.
	 
	 <value>
		A <see cref="BlockType" /> value describing the block
		type.
	 </value>
	*/
	public BlockType getBlockType()
	{
		return block_type;
	}

	/** 
		Gets whether or not the block is the last in the file.
	 
	 <value>
		<see langword="true" /> if the block is the last in the
		file; otherwise <see langword="false" />.
	 </value>
	*/
	public boolean getIsLastBlock()
	{
		return is_last_block;
	}

	/** 
		Gets the size of the block described by the current
		instance, minus the block header.
	 
	 <value>
		A <see cref="uint" /> value containing the size of the
		block, minus the header.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getBlockSize()
	public int getBlockSize()
	{
		return block_size;
	}

	public BlockHeader clone()
	{
		BlockHeader varCopy = new BlockHeader();

		varCopy.block_type = this.block_type;
		varCopy.is_last_block = this.is_last_block;
		varCopy.block_size = this.block_size;

		return varCopy;
	}
}