package Rasad.Core.Media.MediaMetadataManagement.Ogg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// PageHeader.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   oggpageheader.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2003 Scott Wheeler (Original Implementation)
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
	Indicates the special properties of a <see cref="Page" />.
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] public enum PageFlags : byte
public class PageFlags 
{
	/** 
		The page is a normal page.
	*/
	public static final PageFlags None = new PageFlags(0);

	/** 
		The first packet of the page is continued from the
		previous page.
	*/
	public static final PageFlags FirstPacketContinued = new PageFlags(1);

	/** 
		The page is the first page of the stream.
	*/
	public static final PageFlags FirstPageOfStream = new PageFlags(2);

	/** 
		The page is the last page of the stream.
	*/
	public static final PageFlags LastPageOfStream = new PageFlags(4);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, PageFlags> mappings;
	private static java.util.HashMap<Byte, PageFlags> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PageFlags.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, PageFlags>();
				}
			}
		}
		return mappings;
	}

	private PageFlags(byte value)
	{
		byteValue = value;
		synchronized (PageFlags.class)
		{
			getMappings().put(value, this);
		}
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static PageFlags forValue(byte value)
	{
		synchronized (PageFlags.class)
		{
			PageFlags enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new PageFlags(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}