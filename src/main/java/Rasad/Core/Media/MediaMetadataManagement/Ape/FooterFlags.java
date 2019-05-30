package Rasad.Core.Media.MediaMetadataManagement.Ape;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Footer.cs: Provides a representation of an APEv2 tag footer which can be read
// from and written to disk.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   apefooter.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2004 Allan Sandfeld Jensen (Original Implementation)
// copyright (C) 2002, 2003 Scott Wheeler (Original Implementation)
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



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Enums

/** 
	Indicates the flags applied to a <see cref="Footer" /> object.
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] public enum FooterFlags : uint
public class FooterFlags 
{
	/** 
		The tag lacks a footer object.
	*/
	public static final FooterFlags FooterAbsent = new FooterFlags(0x40000000);

	/** 
		The footer is actually a header.
	*/
	public static final FooterFlags IsHeader = new FooterFlags(0x20000000);

	/** 
		The tag contains a header.
	*/
	public static final FooterFlags HeaderPresent = new FooterFlags(0x80000000);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, FooterFlags> mappings;
	private static java.util.HashMap<Integer, FooterFlags> getMappings()
	{
		if (mappings == null)
		{
			synchronized (FooterFlags.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, FooterFlags>();
				}
			}
		}
		return mappings;
	}

	private FooterFlags(int value)
	{
		intValue = value;
		synchronized (FooterFlags.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static FooterFlags forValue(int value)
	{
		synchronized (FooterFlags.class)
		{
			FooterFlags enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new FooterFlags(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}