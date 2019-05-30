package Rasad.Core.Media.MediaMetadataManagement.Image;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// ImageOrientation.cs: Enum for the orientation of an image
//
// Author:
//   Paul Lange (palango@gmx.de)
//
// Copyright (C) 2009 Paul Lange
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



/**

  1        2       3      4         5            6           7          8

888888  888888      88  88      8888888888  88                  88  8888888888
88          88      88  88      88  88      88  88          88  88      88  88
8888      8888    8888  8888    88          8888888888  8888888888          88
88          88      88  88
88          88  888888  888888

t-l     t-r     b-r     b-l     l-t         r-t         r-b             l-b

**/

/** 
 Describes the orientation of an image.
 Values are viewed in terms of rows and columns.
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum ImageOrientation : uint
public enum ImageOrientation 
{
	/** 
	 No value is known.
	*/
	None(0),

	/** 
	 No need to do any transformations.
	*/
	TopLeft(1),

	/** 
	 Mirror image vertically.
	*/
	TopRight(2),

	/** 
	 Rotate image 180 degrees.
	*/
	BottomRight(3),

	/** 
	 Mirror image horizontally
	*/
	BottomLeft(4),

	/** 
	 Mirror image horizontally and rotate 90 degrees clockwise.
	*/
	LeftTop(5),

	/** 
	 Rotate image 90 degrees clockwise.
	*/
	RightTop(6),

	/** 
	 Mirror image vertically and rotate 90 degrees clockwise.
	*/
	RightBottom(7),

	/** 
	 Rotate image 270 degrees clockwise.
	*/
	LeftBottom(8);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ImageOrientation> mappings;
	private static java.util.HashMap<Integer, ImageOrientation> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ImageOrientation.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ImageOrientation>();
				}
			}
		}
		return mappings;
	}

	private ImageOrientation(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ImageOrientation forValue(int value)
	{
		return getMappings().get(value);
	}
}