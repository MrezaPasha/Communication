package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// EBMLElement.cs:
//
// Author:
//   Julien Moutte <julien@fluendo.com>
//
// Copyright (C) 2011 FLUENDO S.A.
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
 Describes a generic EBML Element.
*/
public class EBMLElement
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong offset = 0;
	private long offset = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong data_offset = 0;
	private long data_offset = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint ebml_id = 0;
	private int ebml_id = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong ebml_size = 0;
	private long ebml_size = 0;
	private Matroska.File file = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
	 Constructs a <see cref="EBMLElement" /> parsing from provided
	 file data.
	 
	 @param _file <see cref="File" /> instance to read from.
	 @param position Position to start reading from.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public EBMLElement(Matroska.File _file, ulong position)
	public EBMLElement(Matroska.File _file, long position)
	{
		if (_file == null)
		{
			throw new NullPointerException("file");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (position > (ulong)(_file.Length - 4))
		if (position > (long)(_file.Length - 4))
		{
			throw new IndexOutOfBoundsException("position");
		}

		// Keep a reference to the file
		file = _file;

		file.Seek((long) position);

		// Get the header byte
		ByteVector vector = file.ReadBlock(1);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Byte header_byte = vector [0];
		byte header_byte = vector.get(0);
		// Define a mask
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Byte mask = 0x80, id_length = 1;
		byte mask = (byte)0x80, id_length = 1;
		// Figure out the size in bytes
		while (id_length <= 4 && (header_byte & mask) == 0)
		{
			id_length++;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			mask >>>= 1;
		}

		if (id_length > 4)
		{
			throw new CorruptFileException("invalid EBML id size");
		}

		// Now read the rest of the EBML ID
		if (id_length > 1)
		{
			vector.add(file.ReadBlock(id_length - 1));
		}

		ebml_id = vector.ToUInt();

		vector.clear();

		// Get the size length
		vector = file.ReadBlock(1);
		header_byte = vector.get(0);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: mask = 0x80;
		mask = (byte)0x80;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Byte size_length = 1;
		byte size_length = 1;

		// Iterate through various possibilities
		while (size_length <= 8 && (header_byte & mask) == 0)
		{
			size_length++;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			mask >>>= 1;
		}

		if (size_length > 8)
		{
			throw new CorruptFileException("invalid EBML element size");
		}

		// Clear the marker bit
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: vector [0] &= (Byte)(mask - 1);
		vector.set(0, vector.get(0) & (byte)(mask - 1));

		// Now read the rest of the EBML element size
		if (size_length > 1)
		{
			vector.add(file.ReadBlock(size_length - 1));
		}

		ebml_size = vector.ToULong();

		offset = position;
		data_offset = offset + id_length + size_length;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
	 EBML Element Identifier.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getID()
	public final int getID()
	{
		return ebml_id;
	}

	/** 
	 EBML Element size in bytes.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getSize()
	public final long getSize()
	{
		return (data_offset - offset) + ebml_size;
	}

	/** 
	 EBML Element data size in bytes.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getDataSize()
	public final long getDataSize()
	{
		return ebml_size;
	}

	/** 
	 EBML Element data offset in bytes.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getDataOffset()
	public final long getDataOffset()
	{
		return data_offset;
	}

	/** 
	 EBML Element offset in bytes.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getOffset()
	public final long getOffset()
	{
		return offset;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
	 Reads a string from EBML Element's data section.
	 
	 @return a string object containing the parsed value.
	*/
	public final String ReadString()
	{
		if (file == null)
		{
			return null;
		}

		file.Seek((long) data_offset);

		ByteVector vector = file.ReadBlock((int) ebml_size);

		return vector.toString();
	}

	/** 
	 Reads a boolean from EBML Element's data section.
	 
	 @return a bool containing the parsed value.
	*/
	public final boolean ReadBool()
	{
		if (file == null)
		{
			return false;
		}

		file.Seek((long) data_offset);

		ByteVector vector = file.ReadBlock((int) ebml_size);

		if (vector.ToUInt() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/** 
	 Reads a double from EBML Element's data section.
	 
	 @return a double containing the parsed value.
	*/
	public final double ReadDouble()
	{
		if (file == null)
		{
			return 0;
		}

		if (ebml_size != 4 && ebml_size != 8)
		{
			throw new UnsupportedFormatException("Can not read a Double with sizes differing from 4 or 8");
		}

		file.Seek((long) data_offset);

		ByteVector vector = file.ReadBlock((int) ebml_size);

		double result = 0.0;

		if (ebml_size == 4)
		{
			result = (double) vector.ToFloat();
		}
		else if (ebml_size == 8)
		{
			result = vector.ToDouble();
		}

		return result;
	}

	/** 
	 Reads an unsigned 32 bits integer from EBML Element's data section.
	 
	 @return a uint containing the parsed value.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint ReadUInt()
	public final int ReadUInt()
	{
		if (file == null)
		{
			return 0;
		}

		file.Seek((long) data_offset);

		ByteVector vector = file.ReadBlock((int) ebml_size);

		return vector.ToUInt();
	}

	/** 
	 Reads a bytes vector from EBML Element's data section.
	 
	 @return a <see cref="ByteVector" /> containing the parsed value.
	*/
	public final ByteVector ReadBytes()
	{
		if (file == null)
		{
			return null;
		}

		file.Seek((long) data_offset);

		ByteVector vector = file.ReadBlock((int) ebml_size);

		return vector;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}