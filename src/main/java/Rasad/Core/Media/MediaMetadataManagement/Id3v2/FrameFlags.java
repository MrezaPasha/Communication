package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// FrameHeader.cs:
//
// Authors:
//   Brian Nickel (brian.nickel@gmail.com)
//   Gabriel BUrt (gabriel.burt@gmail.com)
//
// Original Source:
//   id3v2frame.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2010 Novell, Inc.
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
	Indicates the flags applied to a <see cref="FrameHeader" />
	object.
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] public enum FrameFlags : ushort
public class FrameFlags 
{
	/** 
		The header contains no flags.
	*/
	public static final FrameFlags None = new FrameFlags(0);

	/** 
		Indicates that the frame is to be deleted if the tag is
		altered.
	*/
	public static final FrameFlags TagAlterPreservation = new FrameFlags(0x4000);

	/** 
		Indicates that the frame is to be deleted if the file is
		altered.
	*/
	public static final FrameFlags FileAlterPreservation = new FrameFlags(0x2000);

	/** 
		Indicates that the frame is read-only and should not be
		altered.
	*/
	public static final FrameFlags ReadOnly = new FrameFlags(0x1000);

	/** 
		Indicates that the frame has a grouping identity.
	*/
	public static final FrameFlags GroupingIdentity = new FrameFlags(0x0040);

	/** 
		Indicates that the frame data is compressed.
	*/
	public static final FrameFlags Compression = new FrameFlags(0x0008);

	/** 
		Indicates that the frame data is encrypted.
	*/
	public static final FrameFlags Encryption = new FrameFlags(0x0004);

	/** 
		Indicates that the frame data has been unsynchronized.
	*/
	public static final FrameFlags Unsynchronisation = new FrameFlags(0x0002);

	/** 
		Indicates that the frame has a data length indicator.
	*/
	public static final FrameFlags DataLengthIndicator = new FrameFlags(0x0001);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, FrameFlags> mappings;
	private static java.util.HashMap<Short, FrameFlags> getMappings()
	{
		if (mappings == null)
		{
			synchronized (FrameFlags.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, FrameFlags>();
				}
			}
		}
		return mappings;
	}

	private FrameFlags(short value)
	{
		shortValue = value;
		synchronized (FrameFlags.class)
		{
			getMappings().put(value, this);
		}
	}

	public short getValue()
	{
		return shortValue;
	}

	public static FrameFlags forValue(short value)
	{
		synchronized (FrameFlags.class)
		{
			FrameFlags enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new FrameFlags(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}