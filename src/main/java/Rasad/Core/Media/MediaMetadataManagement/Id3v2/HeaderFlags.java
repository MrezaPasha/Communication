package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Header.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   id3v2header.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
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
	Indicates the flags applied to a <see cref="Header" /> object.
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] public enum HeaderFlags : byte
public class HeaderFlags 
{
	/** 
		The header contains no flags.
	*/
	public static final HeaderFlags None = new HeaderFlags(0);

	/** 
		The tag described by the header has been unsynchronized.
	*/
	public static final HeaderFlags Unsynchronisation = new HeaderFlags(0x80);

	/** 
		The tag described by the header has contains an extended
		header.
	*/
	public static final HeaderFlags ExtendedHeader = new HeaderFlags(0x40);

	/** 
		The tag described by the header is experimental.
	*/
	public static final HeaderFlags ExperimentalIndicator = new HeaderFlags(0x20);

	/** 
		The tag described by the header contains a footer.
	*/
	public static final HeaderFlags FooterPresent = new HeaderFlags(0x10);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, HeaderFlags> mappings;
	private static java.util.HashMap<Byte, HeaderFlags> getMappings()
	{
		if (mappings == null)
		{
			synchronized (HeaderFlags.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, HeaderFlags>();
				}
			}
		}
		return mappings;
	}

	private HeaderFlags(byte value)
	{
		byteValue = value;
		synchronized (HeaderFlags.class)
		{
			getMappings().put(value, this);
		}
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static HeaderFlags forValue(byte value)
	{
		synchronized (HeaderFlags.class)
		{
			HeaderFlags enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new HeaderFlags(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}