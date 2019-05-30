package Rasad.Core.Media.MediaMetadataManagement.IFD;

import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// IFDRenderer.cs: Outputs an IFD structure into TIFF IFD bytes.
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009 Ruben Vermeersch
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
	 This class contains all the IFD rendering code.
*/
public class IFDRenderer
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		The IFD structure that will be rendered.
	*/
	private IFDStructure structure;

	/** 
		If IFD should be encoded in BigEndian or not.
	*/
	private boolean is_bigendian;

	/** 
		A <see cref="System.UInt32"/> value with the offset of the
		current IFD. All offsets inside the IFD must be adjusted
		according to this given offset.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly uint ifd_offset;
	private int ifd_offset;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructor. Will render the given IFD structure.
	 
	 @param is_bigendian
		If IFD should be encoded in BigEndian or not.
	 
	 @param structure
		The IFD structure that will be rendered.
	 
	 @param ifd_offset
		A <see cref="System.UInt32"/> value with the offset of the
		current IFD. All offsets inside the IFD must be adjusted
		according to this given offset.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public IFDRenderer(bool is_bigendian, IFDStructure structure, uint ifd_offset)
	public IFDRenderer(boolean is_bigendian, IFDStructure structure, int ifd_offset)
	{
		this.is_bigendian = is_bigendian;
		this.structure = structure;
		this.ifd_offset = ifd_offset;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance to a <see cref="ByteVector"/>.
	 
	 @return 
		A <see cref="ByteVector"/> containing the rendered IFD.
	 
	*/
	public final ByteVector Render()
	{
		ByteVector ifd_data = new ByteVector();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint current_offset = ifd_offset;
		int current_offset = ifd_offset;
		ArrayList<IFDDirectory> directories = structure.directories;

		for (int index = 0; index < directories.size(); index++)
		{
			ByteVector data = RenderIFD(directories.get(index), current_offset, index == directories.size() - 1);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: current_offset += (uint) data.Count;
			current_offset += (int) data.size();
			ifd_data.add(data);
		}

		return ifd_data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	/** 
		Renders the IFD to an ByteVector where the offset of the IFD
		itself is <paramref name="ifd_offset"/> and all offsets
		contained in the IFD are adjusted accroding it.
	 
	 @param directory
		A <see cref="IFDDirectory"/> with the directory to render.
	 
	 @param ifd_offset
		A <see cref="System.UInt32"/> with the offset of the IFD
	 
	 @param last
		A <see cref="System.Boolean"/> which is true, if the IFD is
		the last one, i.e. the offset to the next IFD, which is
		stored inside the IFD, is 0. If the value is false, the
		offset to the next IFD is set that it starts directly after
		the current one.
	 
	 @return 
		A <see cref="ByteVector"/> with the rendered IFD.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ByteVector RenderIFD(IFDDirectory directory, uint ifd_offset, bool last)
	private ByteVector RenderIFD(IFDDirectory directory, int ifd_offset, boolean last)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (directory.Count > (int)UInt16.MaxValue)
		if (directory.size() > (int)Short.MAX_VALUE)
		{
			throw new RuntimeException(String.format("Directory has too much entries: %1$s", directory.size()));
		}

		// Remove empty SUB ifds.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var tags = new List<ushort> (directory.Keys);
		ArrayList<Short> tags = new ArrayList<Short> (directory.keySet());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (var tag in tags)
		for (short tag : tags)
		{
			unknown entry = directory.get(tag);
			if (entry instanceof SubIFDEntry && (entry instanceof SubIFDEntry ? (SubIFDEntry)entry : null).getChildCount() == 0)
			{
				directory.remove(tag);
			}
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort entry_count = (ushort) directory.Count;
		short entry_count = (short) directory.size();

		// ifd_offset + size of entry_count + entries + next ifd offset
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint data_offset = ifd_offset + 2 + 12 * (uint) entry_count + 4;
		int data_offset = ifd_offset + 2 + 12 * (int) entry_count + 4;

		// store the entries itself
		ByteVector entry_data = new ByteVector();

		// store the data referenced by the entries
		ByteVector offset_data = new ByteVector();

		entry_data.add(ByteVector.FromUShort(entry_count, is_bigendian));

		for (IFDEntry entry : directory.values())
		{
			RenderEntryData(entry, entry_data, offset_data, data_offset);
		}

		if (last)
		{
			entry_data.add("\0\0\0\0");
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: entry_data.Add(ByteVector.FromUInt((uint)(data_offset + offset_data.Count), is_bigendian));
			entry_data.add(ByteVector.FromUInt((int)(data_offset + offset_data.size()), is_bigendian));
		}

		if (data_offset - ifd_offset != entry_data.size())
		{
			throw new RuntimeException(String.format("Expected IFD data size was %1$s but is %2$s", data_offset - ifd_offset, entry_data.size()));
		}

		entry_data.add(offset_data);

		return entry_data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Methods

	/** 
		Adds the data of a single entry to <paramref name="entry_data"/>.
	 
	 @param entry_data
		A <see cref="ByteVector"/> to add the entry to.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag of the entry.
	 
	 @param type
		A <see cref="System.UInt16"/> with the type of the entry.
	 
	 @param count
		A <see cref="System.UInt32"/> with the data count of the entry,
	 
	 @param offset
		A <see cref="System.UInt32"/> with the offset field of the entry.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected void RenderEntry(ByteVector entry_data, ushort tag, ushort type, uint count, uint offset)
	protected final void RenderEntry(ByteVector entry_data, short tag, short type, int count, int offset)
	{
		entry_data.add(ByteVector.FromUShort(tag, is_bigendian));
		entry_data.add(ByteVector.FromUShort(type, is_bigendian));
		entry_data.add(ByteVector.FromUInt(count, is_bigendian));
		entry_data.add(ByteVector.FromUInt(offset, is_bigendian));
	}

	/** 
		Renders a complete entry together with the data. The entry itself
		is stored in <paramref name="entry_data"/> and the data of the
		entry is stored in <paramref name="offset_data"/> if it cannot be
		stored in the offset. This method is called for every <see
		cref="IFDEntry"/> of this IFD and can be overwritten in subclasses
		to provide special behavior.
	 
	 @param entry
		A <see cref="IFDEntry"/> with the entry to render.
	 
	 @param entry_data
		A <see cref="ByteVector"/> to add the entry to.
	 
	 @param offset_data
		A <see cref="ByteVector"/> to add the entry data to if it cannot be
		stored in the offset field.
	 
	 @param data_offset
		A <see cref="System.UInt32"/> with the offset, were the data of the
		entries starts. It is needed to adjust the offsets of the entries
		itself.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected virtual void RenderEntryData(IFDEntry entry, ByteVector entry_data, ByteVector offset_data, uint data_offset)
	protected void RenderEntryData(IFDEntry entry, ByteVector entry_data, ByteVector offset_data, int data_offset)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort tag = (ushort) entry.Tag;
		short tag = (short) entry.getTag();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint offset = (uint)(data_offset + offset_data.Count);
		int offset = (int)(data_offset + offset_data.size());

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort type;
		short type;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint count;
		int count;
		tangible.OutObject<Short> tempOut_type = new tangible.OutObject<Short>();
		tangible.OutObject<Integer> tempOut_count = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector data = entry.Render(is_bigendian, offset, out type, out count);
		ByteVector data = entry.Render(is_bigendian, offset, tempOut_type, tempOut_count);
	count = tempOut_count.argValue;
	type = tempOut_type.argValue;

		// store data in offset, if it is smaller than 4 byte
		if (data.size() <= 4)
		{

			while (data.size() < 4)
			{
				data.add("\0");
			}

			offset = data.ToUInt(is_bigendian);
			data = null;
		}

		// preserve word boundary of offsets
		if (data != null && data.size() % 2 != 0)
		{
			data.add("\0");
		}

		RenderEntry(entry_data, tag, type, count, offset);
		offset_data.add(data);
	}

	/** 
		Constructs a new IFD Renderer used to render a <see cref="SubIFDEntry"/>.
	 
	 @param is_bigendian
		If IFD should be encoded in BigEndian or not.
	 
	 @param structure
		The IFD structure that will be rendered.
	 
	 @param ifd_offset
		A <see cref="System.UInt32"/> value with the offset of the
		current IFD. All offsets inside the IFD must be adjusted
		according to this given offset.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected virtual IFDRenderer CreateSubRenderer(bool is_bigendian, IFDStructure structure, uint ifd_offset)
	protected IFDRenderer CreateSubRenderer(boolean is_bigendian, IFDStructure structure, int ifd_offset)
	{
		return new IFDRenderer(is_bigendian, structure, ifd_offset);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}