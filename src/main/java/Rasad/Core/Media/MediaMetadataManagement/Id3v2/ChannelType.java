package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// RelativeVolumeFrame.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   textidentificationframe.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2004 Scott Wheeler (Original Implementation)
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
	Specified the type of channel data to get from or set to a
	<see cref="RelativeVolumeFrame" /> object.
*/
public enum ChannelType
{
	/** 
		The channel data is for some other speaker.
	*/
	Other(0x00),

	/** 
		The channel data is for the master volume.
	*/
	MasterVolume(0x01),

	/** 
		The channel data is for the front right speaker.
	*/
	FrontRight(0x02),

	/** 
		The channel data is for the front left speaker.
	*/
	FrontLeft(0x03),

	/** 
		The channel data is for the back right speaker.
	*/
	BackRight(0x04),

	/** 
		The channel data is for the back left speaker.
	*/
	BackLeft(0x05),

	/** 
		The channel data is for the front center speaker.
	*/
	FrontCentre(0x06),

	/** 
		The channel data is for the back center speaker.
	*/
	BackCentre(0x07),

	/** 
		The channel data is for the subwoofer.
	*/
	Subwoofer(0x08);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ChannelType> mappings;
	private static java.util.HashMap<Integer, ChannelType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ChannelType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ChannelType>();
				}
			}
		}
		return mappings;
	}

	private ChannelType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ChannelType forValue(int value)
	{
		return getMappings().get(value);
	}
}