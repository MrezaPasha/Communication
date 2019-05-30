package Rasad.Core.Media.MediaMetadataManagement.Flac;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// BlockHeader.cs:
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
	Specifies the contents of a Flac block in <see cref="BlockHeader"
	/>.
*/
public enum BlockType
{
	/** 
		The block contains stream information.
	*/
	StreamInfo(0),

	/** 
		The block contains padding.
	*/
	Padding(1),

	/** 
		The block contains application data.
	*/
	Application(2),

	/** 
		The block contains seek table.
	*/
	SeekTable(3),

	/** 
		The block contains a Xipp comment.
	*/
	XiphComment(4),

	/** 
		The block contains a cue sheet.
	*/
	CueSheet(5),

	/** 
		The block contains a picture.
	*/
	Picture(6);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, BlockType> mappings;
	private static java.util.HashMap<Integer, BlockType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (BlockType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, BlockType>();
				}
			}
		}
		return mappings;
	}

	private BlockType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static BlockType forValue(int value)
	{
		return getMappings().get(value);
	}
}