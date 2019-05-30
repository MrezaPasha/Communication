package Rasad.Core.Media.MediaMetadataManagement.Ape;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Item.cs: Provides a representation of an APEv2 tag item which can be read
// from and written to disk.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   apeitem.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2004 by Allan Sandfeld Jensen (Original Implementation)
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
	Indicates the type of data stored in a <see cref="Item" />
	object.
*/
public enum ItemType
{
	/** 
		The item contains Unicode text.
	*/
	Text(0),

	/** 
		The item contains binary data.
	*/
	Binary(1),

	/** 
		The item contains a locator (file path/URL) for external
		information.
	*/
	Locator(2);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ItemType> mappings;
	private static java.util.HashMap<Integer, ItemType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ItemType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ItemType>();
				}
			}
		}
		return mappings;
	}

	private ItemType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ItemType forValue(int value)
	{
		return getMappings().get(value);
	}
}