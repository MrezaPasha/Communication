package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// NikonShotInfoEntryTag.cs:
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
	Nikon shot info entry tags.
	Based on:
	http: //exiv2.org/tags-nikon.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum NikonShotInfoEntryTag : ushort
public enum NikonShotInfoEntryTag 
{

	/** 
		Version. (Hex: 0X0000)
	*/
	Version((short)0),

	/** 
		Shutter count 1. (Hex: 0X006A)
	*/
	ShutterCount1((short)106),

	/** 
		Deleted image count. (Hex: 0X006E)
	*/
	DeletedImageCount((short)110),

	/** 
		Vibration reduction. (Hex: 0X0075)
	*/
	VibrationReduction((short)117),

	/** 
		. (Hex: 0X0082)
	*/
	VibrationReduction1((short)130),

	/** 
		Shutter count 2. (Hex: 0X0157)
	*/
	ShutterCount2((short)343),

	/** 
		Vibration reduction 2. (Hex: 0X01AE)
	*/
	VibrationReduction2((short)430),

	/** 
		ISO. (Hex: 0X0256)
	*/
	ISO((short)598),

	/** 
		Shutter count. (Hex: 0X0276)
	*/
	ShutterCount((short)630);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, NikonShotInfoEntryTag> mappings;
	private static java.util.HashMap<Short, NikonShotInfoEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (NikonShotInfoEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, NikonShotInfoEntryTag>();
				}
			}
		}
		return mappings;
	}

	private NikonShotInfoEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static NikonShotInfoEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}