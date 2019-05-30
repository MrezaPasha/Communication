package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// ContentDescriptor.cs: Provides a representation of an ASF Content Descriptor
// to be used in combination with ExtendedContentDescriptionObject.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2006-2007 Brian Nickel
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
	Indicates the type of data stored in a <see
	cref="ContentDescriptor" /> or <see cref="DescriptionRecord" />
	object.
*/
public enum DataType
{
	/** 
		The descriptor contains Unicode (UTF-16LE) text.
	*/
	Unicode(0),

	/** 
		The descriptor contains binary data.
	*/
	Bytes(1),

	/** 
		The descriptor contains a boolean value.
	*/
	Bool(2),

	/** 
		The descriptor contains a 4-byte DWORD value.
	*/
	DWord(3),

	/** 
		The descriptor contains a 8-byte QWORD value.
	*/
	QWord(4),

	/** 
		The descriptor contains a 2-byte WORD value.
	*/
	Word(5),

	/** 
		The descriptor contains a 16-byte GUID value.
	*/
	Guid(6);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, DataType> mappings;
	private static java.util.HashMap<Integer, DataType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (DataType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, DataType>();
				}
			}
		}
		return mappings;
	}

	private DataType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static DataType forValue(int value)
	{
		return getMappings().get(value);
	}
}