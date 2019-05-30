package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;
import java.io.*;

//
// ByteVector.cs: represents and performs operations on variable length list of
// bytes.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//   Aaron Bockover (abockover@novell.com)
//
// Original Source:
//   tbytevector.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2006 Novell, Inc.
// Copyright (C) 2002-2004 Scott Wheeler (Original Implementation)
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
	Specifies the text encoding used when converting between a <see
	cref="string" /> and a <see cref="ByteVector" />.
 
 
	This enumeration is used by <see
	cref="ByteVector.FromString(string,StringType)" /> and <see
	cref="ByteVector.ToString(StringType)" />.
 
*/
public enum StringType
{
	/** 
		The string is to be Latin-1 encoded.
	*/
	Latin1(0),

	/** 
		The string is to be UTF-16 encoded.
	*/
	UTF16(1),

	/** 
		The string is to be UTF-16BE encoded.
	*/
	UTF16BE(2),

	/** 
		The string is to be UTF-8 encoded.
	*/
	UTF8(3),

	/** 
		The string is to be UTF-16LE encoded.
	*/
	UTF16LE(4);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, StringType> mappings;
	private static java.util.HashMap<Integer, StringType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (StringType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, StringType>();
				}
			}
		}
		return mappings;
	}

	private StringType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static StringType forValue(int value)
	{
		return getMappings().get(value);
	}
}