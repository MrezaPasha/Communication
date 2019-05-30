package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;
import java.io.*;

//
// File.cs: Provides a basic framework for reading from and writing to
// a file, as well as accessing basic tagging and media properties.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//   Aaron Bockover (abockover@novell.com)
//
// Original Source:
//   tfile.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005, 2007 Brian Nickel
// Copyright (C) 2006 Novell, Inc.
// Copyright (C) 2002,2003 Scott Wheeler (Original Implementation)
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
	Specifies the level of intensity to use when reading the media
	properties.
*/
public enum ReadStyle
{
	/** 
		The media properties will not be read.
	*/
	None(0),

	// Fast = 1,

	/** 
		The media properties will be read with average accuracy.
	*/
	Average(2);

	// Accurate = 3

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ReadStyle> mappings;
	private static java.util.HashMap<Integer, ReadStyle> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ReadStyle.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ReadStyle>();
				}
			}
		}
		return mappings;
	}

	private ReadStyle(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ReadStyle forValue(int value)
	{
		return getMappings().get(value);
	}
}