package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// NikonWorldTimeEntryTag.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//
// Copyright (C) 2010 Ruben Vermeersch
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
	Nikon world time entry tags.
	Based on:
	http: //exiv2.org/tags-nikon.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum NikonWorldTimeEntryTag : ushort
public enum NikonWorldTimeEntryTag 
{

	/** 
		Timezone. (Hex: 0X0000)
	*/
	Timezone((short)0),

	/** 
		Daylight savings. (Hex: 0X0002)
	*/
	DaylightSavings((short)2),

	/** 
		Date display format. (Hex: 0X0003)
	*/
	DateDisplayFormat((short)3);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, NikonWorldTimeEntryTag> mappings;
	private static java.util.HashMap<Short, NikonWorldTimeEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (NikonWorldTimeEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, NikonWorldTimeEntryTag>();
				}
			}
		}
		return mappings;
	}

	private NikonWorldTimeEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static NikonWorldTimeEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}