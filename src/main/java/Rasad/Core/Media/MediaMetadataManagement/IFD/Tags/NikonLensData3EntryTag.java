package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// NikonLensData3EntryTag.cs:
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
	Nikon lens data entry tags.
	Based on:
	http: //exiv2.org/tags-nikon.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum NikonLensData3EntryTag : ushort
public enum NikonLensData3EntryTag 
{

	/** 
		Version. (Hex: 0X0000)
	*/
	Version((short)0),

	/** 
		Exit pupil position. (Hex: 0X0004)
	*/
	ExitPupilPosition((short)4),

	/** 
		AF aperture. (Hex: 0X0005)
	*/
	AFAperture((short)5),

	/** 
		Focus position. (Hex: 0X0008)
	*/
	FocusPosition((short)8),

	/** 
		Focus distance. (Hex: 0X000A)
	*/
	FocusDistance((short)10),

	/** 
		Focal length. (Hex: 0X000B)
	*/
	FocalLength((short)11),

	/** 
		Lens ID number. (Hex: 0X000C)
	*/
	LensIDNumber((short)12),

	/** 
		Lens F-stops. (Hex: 0X000D)
	*/
	LensFStops((short)13),

	/** 
		Min focal length. (Hex: 0X000E)
	*/
	MinFocalLength((short)14),

	/** 
		Max focal length. (Hex: 0X000F)
	*/
	MaxFocalLength((short)15),

	/** 
		Max aperture at min focal length. (Hex: 0X0010)
	*/
	MaxApertureAtMinFocal((short)16),

	/** 
		Max aperture at max focal length. (Hex: 0X0011)
	*/
	MaxApertureAtMaxFocal((short)17),

	/** 
		MCU version. (Hex: 0X0012)
	*/
	MCUVersion((short)18),

	/** 
		Effective max aperture. (Hex: 0X0013)
	*/
	EffectiveMaxAperture((short)19);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, NikonLensData3EntryTag> mappings;
	private static java.util.HashMap<Short, NikonLensData3EntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (NikonLensData3EntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, NikonLensData3EntryTag>();
				}
			}
		}
		return mappings;
	}

	private NikonLensData3EntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static NikonLensData3EntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}