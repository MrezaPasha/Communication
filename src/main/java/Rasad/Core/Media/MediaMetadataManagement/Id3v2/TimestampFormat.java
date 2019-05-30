package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// SynchronizedLyricsFrame.cs:
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
	Specifies the timestamp format used by a <see
	cref="SynchronisedLyricsFrame" />.
*/
public enum TimestampFormat
{
	/** 
		The timestamp is of unknown format.
	*/
	Unknown(0x00),

	/** 
		The timestamp represents the number of MPEG frames since
		the beginning of the audio stream.
	*/
	AbsoluteMpegFrames(0x01),

	/** 
		The timestamp represents the number of milliseconds since
		the beginning of the audio stream.
	*/
	AbsoluteMilliseconds(0x02);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, TimestampFormat> mappings;
	private static java.util.HashMap<Integer, TimestampFormat> getMappings()
	{
		if (mappings == null)
		{
			synchronized (TimestampFormat.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, TimestampFormat>();
				}
			}
		}
		return mappings;
	}

	private TimestampFormat(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static TimestampFormat forValue(int value)
	{
		return getMappings().get(value);
	}
}