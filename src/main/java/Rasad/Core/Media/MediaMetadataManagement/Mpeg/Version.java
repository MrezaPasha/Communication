package Rasad.Core.Media.MediaMetadataManagement.Mpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// AudioHeader.cs: Provides information about an MPEG audio stream.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   mpegheader.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2003 by Scott Wheeler (Original Implementation)
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




//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Enums

/** 
	Indicates the MPEG version of a file or stream.
*/
public enum Version
{
	/** 
		Unknown version.
	*/
	Unknown(-1),

	/** 
		MPEG-1
	*/
	Version1(0),

	/** 
		MPEG-2
	*/
	Version2(1),

	/** 
		MPEG-2.5
	*/
	Version25(2);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, Version> mappings;
	private static java.util.HashMap<Integer, Version> getMappings()
	{
		if (mappings == null)
		{
			synchronized (Version.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, Version>();
				}
			}
		}
		return mappings;
	}

	private Version(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static Version forValue(int value)
	{
		return getMappings().get(value);
	}
}