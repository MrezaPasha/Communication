package Rasad.Core.Media.MediaMetadataManagement.IFD.Entries;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// ShortArrayIFDEntry.cs:
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
	Contains a SHORT value with a count > 1
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public class ShortArrayIFDEntry : ArrayIFDEntry<ushort>
public class ShortArrayIFDEntry extends ArrayIFDEntry<Short>
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Construcor.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag ID of the entry this instance
		represents
	 
	 @param values
		A <see cref="System.UInt16[]"/> to be stored
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ShortArrayIFDEntry(ushort tag, ushort [] values)
	public ShortArrayIFDEntry(short tag, short[] values)
	{
		super(tag);
		setValues(values);
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
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: type = (ushort) IFDEntryType.Short;
		type.argValue = (short) IFDEntryType.Short.getValue();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: count = (uint) Values.Length;
		count.argValue = (int) getValues().Length;

		ByteVector data = new ByteVector();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (ushort value in Values)
		for (short value : getValues())
		{
			data.add(ByteVector.FromUShort(value, is_bigendian));
		}

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}