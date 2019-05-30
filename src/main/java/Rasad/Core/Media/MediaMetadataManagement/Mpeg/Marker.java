package Rasad.Core.Media.MediaMetadataManagement.Mpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// File.cs: Provides tagging and properties support for MPEG-1, MPEG-2, and
// MPEG-2.5 audio files.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
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
	Indicates the type of marker found in a MPEG file.
*/
public enum Marker
{
	/** 
		An invalid marker.
	*/
	Corrupt(-1),

	/** 
		A zero value marker.
	*/
	Zero(0),

	/** 
	   A marker indicating a system sync packet.
	*/
	SystemSyncPacket(0xBA),

	/** 
	   A marker indicating a video sync packet.
	*/
	VideoSyncPacket(0xB3),

	/** 
	   A marker indicating a system packet.
	*/
	SystemPacket(0xBB),

	/** 
	   A marker indicating a padding packet.
	*/
	PaddingPacket(0xBE),

	/** 
	   A marker indicating a audio packet.
	*/
	AudioPacket(0xC0),

	/** 
	   A marker indicating a video packet.
	*/
	VideoPacket(0xE0),

	/** 
	   A marker indicating the end of a stream.
	*/
	EndOfStream(0xB9);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, Marker> mappings;
	private static java.util.HashMap<Integer, Marker> getMappings()
	{
		if (mappings == null)
		{
			synchronized (Marker.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, Marker>();
				}
			}
		}
		return mappings;
	}

	private Marker(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static Marker forValue(int value)
	{
		return getMappings().get(value);
	}
}