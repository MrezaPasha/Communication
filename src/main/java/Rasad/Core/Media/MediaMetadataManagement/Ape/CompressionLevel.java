package Rasad.Core.Media.MediaMetadataManagement.Ape;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// StreamHeader.cs: Provides support for reading Monkey's Audio APE stream
// properties.
//
// Author:
//   Helmut Wahrmann
//
// Copyright (C) 2007 Helmut Wahrmann
// Copyright (C) 2007 Brian Nickel
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
	Indicates the compression level used when encoding a Monkey's
	Audio APE file.
*/
public enum CompressionLevel
{
	/** 
		The audio is not compressed.
	*/
	None(0),

	/** 
		The audio is mildly compressed.
	*/
	Fast(1000),

	/** 
		The audio is compressed at a normal level.
	*/
	Normal(2000),

	/** 
		The audio is highly compressed.
	*/
	High(3000),

	/** 
		The audio is extremely highly compressed.
	*/
	ExtraHigh(4000),

	/** 
		The audio is compressed to an insane level.
	*/
	Insane(4001);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, CompressionLevel> mappings;
	private static java.util.HashMap<Integer, CompressionLevel> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CompressionLevel.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, CompressionLevel>();
				}
			}
		}
		return mappings;
	}

	private CompressionLevel(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static CompressionLevel forValue(int value)
	{
		return getMappings().get(value);
	}
}