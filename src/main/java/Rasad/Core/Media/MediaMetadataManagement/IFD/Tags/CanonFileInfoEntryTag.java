package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// CanonFileInfoEntryTag.cs:
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
	Label tags for Canon File Info.
	Based on http: //www.exiv2.org/tags-canon.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CanonFileInfoEntryTag : ushort
public enum CanonFileInfoEntryTag 
{
	/** 
		File Number. (Hex: 0X0001)
	*/
	FileNumber((short)1),

	/** 
		Bracket Mode. (Hex: 0X0003)
	*/
	BracketMode((short)3),

	/** 
		Bracket Value. (Hex: 0X0004)
	*/
	BracketValue((short)4),

	/** 
		Bracket Shot Number. (Hex: 0X0005)
	*/
	BracketShotNumber((short)5),

	/** 
		Raw Jpg Quality. (Hex: 0X0006)
	*/
	RawJpgQuality((short)6),

	/** 
		Raw Jpg Size. (Hex: 0X0007)
	*/
	RawJpgSize((short)7),

	/** 
		Noise Reduction. (Hex: 0X0008)
	*/
	NoiseReduction((short)8),

	/** 
		WB Bracket Mode. (Hex: 0X0009)
	*/
	WBBracketMode((short)9),

	/** 
		WB Bracket Value AB. (Hex: 0X000C)
	*/
	WBBracketValueAB((short)12),

	/** 
		WB Bracket Value GM. (Hex: 0X000D)
	*/
	WBBracketValueGM((short)13),

	/** 
		Filter Effect. (Hex: 0X000E)
	*/
	FilterEffect((short)14),

	/** 
		Toning Effect. (Hex: 0X000F)
	*/
	ToningEffect((short)15);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, CanonFileInfoEntryTag> mappings;
	private static java.util.HashMap<Short, CanonFileInfoEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CanonFileInfoEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, CanonFileInfoEntryTag>();
				}
			}
		}
		return mappings;
	}

	private CanonFileInfoEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static CanonFileInfoEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}