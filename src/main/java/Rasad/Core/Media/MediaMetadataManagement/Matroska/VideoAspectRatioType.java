package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// VideoTrack.cs:
//
// Author:
//   Julien Moutte <julien@fluendo.com>
//
// Copyright (C) 2011 FLUENDO S.A.
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
 Enumeration describing supported Video Aspect Ratio types.
*/
public enum VideoAspectRatioType
{
	/** 
	 Free Aspect Ratio.
	*/
	AspectRatioModeFree(0x0),
	/** 
	 Keep Aspect Ratio.
	*/
	AspectRatioModeKeep(0x1),
	/** 
	 Fixed Aspect Ratio.
	*/
	AspectRatioModeFixed(0x2);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, VideoAspectRatioType> mappings;
	private static java.util.HashMap<Integer, VideoAspectRatioType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (VideoAspectRatioType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, VideoAspectRatioType>();
				}
			}
		}
		return mappings;
	}

	private VideoAspectRatioType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static VideoAspectRatioType forValue(int value)
	{
		return getMappings().get(value);
	}
}