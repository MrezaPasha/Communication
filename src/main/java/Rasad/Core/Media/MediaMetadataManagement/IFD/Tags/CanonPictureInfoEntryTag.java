package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// CanonPictureInfoEntryTag.cs:
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
	Label tags for Canon Picture Info.
	Based on http: //www.exiv2.org/tags-canon.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CanonPictureInfoEntryTag : ushort
public enum CanonPictureInfoEntryTag 
{
	/** 
		Image width. (Hex: 0X0002)
	*/
	ImageWidth((short)2),

	/** 
		Image height. (Hex: 0X0003)
	*/
	ImageHeight((short)3),

	/** 
		Image width (as shot). (Hex: 0X0004)
	*/
	ImageWidthAsShot((short)4),

	/** 
		Image height (as shot). (Hex: 0X0005)
	*/
	ImageHeightAsShot((short)5),

	/** 
		AF points used. (Hex: 0X0016)
	*/
	AFPointsUsed((short)22),

	/** 
		AF points used (20D). (Hex: 0X001A)
	*/
	AFPointsUsed20D((short)26);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, CanonPictureInfoEntryTag> mappings;
	private static java.util.HashMap<Short, CanonPictureInfoEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CanonPictureInfoEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, CanonPictureInfoEntryTag>();
				}
			}
		}
		return mappings;
	}

	private CanonPictureInfoEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static CanonPictureInfoEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}