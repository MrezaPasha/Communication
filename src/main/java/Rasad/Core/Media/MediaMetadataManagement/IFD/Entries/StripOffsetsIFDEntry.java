package Rasad.Core.Media.MediaMetadataManagement.IFD.Entries;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// StripOffsetsIFDEntry.cs:
//
// Author:
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009 Mike Gemuende
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
	Contains the offsets to the image data strips.
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public class StripOffsetsIFDEntry : ArrayIFDEntry<uint>
public class StripOffsetsIFDEntry extends ArrayIFDEntry<Integer>
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** <value>
		Store the strip length to read them before writing.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint[] byte_counts;
	private int[] byte_counts;

	/** <value>
		The file the offsets belong to
	 </value>
	*/
	private File file;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructor.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag ID of the entry this instance
		represents
	 
	 @param values
		A <see cref="System.UInt32[]"/> with the strip offsets.
	 
	 @param byte_counts
		The length of the strips.
	 
	 @param file
		The file from which the strips will be read.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public StripOffsetsIFDEntry(ushort tag, uint[] values, uint[] byte_counts, File file)
	public StripOffsetsIFDEntry(short tag, int[] values, int[] byte_counts, File file)
	{
		super(tag);
		setValues(values);
		this.byte_counts = byte_counts;
		this.file = file;

		if (values.length != byte_counts.length)
		{
			throw new RuntimeException("strip offsets and strip byte counts do not have the same length");
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance to a <see cref="ByteVector"/>
	 
	 @param is_bigendian
		A <see cref="System.Boolean"/> indicating the endianess for rendering.
	 
	 @param offset
		A <see cref="System.UInt32"/> with the offset, the data is stored.
	 
	 @param type
		A <see cref="System.UInt16"/> the ID of the type, which is rendered
	 
	 @param count
		A <see cref="System.UInt32"/> with the count of the values which are
		rendered.
	 
	 @return 
		A <see cref="ByteVector"/> with the rendered data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override ByteVector Render(bool is_bigendian, uint offset, out ushort type, out uint count)
	@Override
	public ByteVector Render(boolean is_bigendian, int offset, tangible.OutObject<Short> type, tangible.OutObject<Integer> count)
	{
		// The StripOffsets are an array of offsets, where the image data can be found.
		// We store the offsets and behind the offsets the image data is stored. Therfore,
		// the ByteVector data first collects the image data and the offsets itself are
		// collected by offset_data. Then both are concatenated.
		ByteVector data = new ByteVector();
		ByteVector offset_data = new ByteVector();

		// every offset needs 4 byte, we need to reserve the bytes.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint data_offset = offset + (uint)(4 * Values.Length);
		int data_offset = offset + (int)(4 * getValues().Length);

		for (int i = 0; i < getValues().Length; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint new_offset = (uint)(data_offset + data.Count);
			int new_offset = (int)(data_offset + data.size());

			file.Seek(getValues()[i], SeekOrigin.Begin);
			data.add(file.ReadBlock((int) byte_counts[i]));

			// update strip offset data to new offset
			getValues()[i] = new_offset;

			offset_data.add(ByteVector.FromUInt(new_offset, is_bigendian));
		}

		// If the StripOffsets only consists of one offset, this doesn't work, because this offset
		// should be stored inside the IFD as a value. But, because of the additional image data,
		// it is not stored there. We need to fix this, that the offset is adjusted correctly.
		// Therefore, the offset_data is only added if it contains more than one value.
		// Then, the offset is set correctly. (However, we need to ensure, that the image data
		// consists at least of 4 bytes, which is probably the case every time, but to be sure ...)
		// However, the strip offset in the array must also be adjusted, if the offset_data is ignored.
		if (getValues().Length > 1)
		{
			data.add(0, offset_data);
		}
		else
		{
			getValues()[0] = offset;
		}

		while (data.size() < 4)
		{
			data.add(0x00);
		}

		// the entry is a single long entry where the value is an offset to the data
		// the offset is automatically updated by the renderer.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: type = (ushort) IFDEntryType.Long;
		type.argValue = (short) IFDEntryType.Long.getValue();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: count = (uint) Values.Length;
		count.argValue = (int) getValues().Length;

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}