package Rasad.Core.Media.MediaMetadataManagement.IFD.Entries;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// LongIFDEntry.cs:
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
	Contains a LONG value.
*/
public class LongIFDEntry implements IFDEntry
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Properties

	/** <value>
		The ID of the tag, the current instance belongs to
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort Tag;
	private short Tag;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getTag()
	public final short getTag()
	{
		return Tag;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setTag(ushort value)
	private void setTag(short value)
	{
		Tag = value;
	}

	/** <value>
		The value which is stored by the current instance
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint Value;
	private int Value;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getValue()
	public final int getValue()
	{
		return Value;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setValue(uint value)
	private void setValue(int value)
	{
		Value = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Construcor.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag ID of the entry this instance
		represents
	 
	 @param value
		A <see cref="System.UInt32"/> to be stored
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public LongIFDEntry(ushort tag, uint value)
	public LongIFDEntry(short tag, int value)
	{
		setTag(tag);
		setValue(value);
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
//ORIGINAL LINE: public ByteVector Render(bool is_bigendian, uint offset, out ushort type, out uint count)
	public final ByteVector Render(boolean is_bigendian, int offset, tangible.OutObject<Short> type, tangible.OutObject<Integer> count)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: type = (ushort) IFDEntryType.Long;
		type.argValue = (short) IFDEntryType.Long.getValue();
		count.argValue = 1;

		return ByteVector.FromUInt(getValue(), is_bigendian);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}