package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// NikonPictureControlEntryTag.cs:
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
	Nikon picture control entry tags.
	Based on:
	http: //exiv2.org/tags-nikon.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum NikonPictureControlEntryTag : ushort
public enum NikonPictureControlEntryTag 
{

	/** 
		Version. (Hex: 0X0000)
	*/
	Version((short)0),

	/** 
		Name. (Hex: 0X0004)
	*/
	Name((short)4),

	/** 
		Base. (Hex: 0X0018)
	*/
	Base((short)24),

	/** 
		Adjust. (Hex: 0X0030)
	*/
	Adjust((short)48),

	/** 
		Quick adjust. (Hex: 0X0031)
	*/
	QuickAdjust((short)49),

	/** 
		Sharpness. (Hex: 0X0032)
	*/
	Sharpness((short)50),

	/** 
		Contrast. (Hex: 0X0033)
	*/
	Contrast((short)51),

	/** 
		Brightness. (Hex: 0X0034)
	*/
	Brightness((short)52),

	/** 
		Saturation. (Hex: 0X0035)
	*/
	Saturation((short)53),

	/** 
		Hue adjustment. (Hex: 0X0036)
	*/
	HueAdjustment((short)54),

	/** 
		Filter effect. (Hex: 0X0037)
	*/
	FilterEffect((short)55),

	/** 
		Toning effect. (Hex: 0X0038)
	*/
	ToningEffect((short)56),

	/** 
		Toning saturation. (Hex: 0X0039)
	*/
	ToningSaturation((short)57);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, NikonPictureControlEntryTag> mappings;
	private static java.util.HashMap<Short, NikonPictureControlEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (NikonPictureControlEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, NikonPictureControlEntryTag>();
				}
			}
		}
		return mappings;
	}

	private NikonPictureControlEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static NikonPictureControlEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}