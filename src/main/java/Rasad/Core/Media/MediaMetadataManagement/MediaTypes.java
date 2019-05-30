package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

//
// ICodec.cs: Provides ICodec, IAudioCodec, and IVideoCodec interfaces.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
// 
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
// 
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//



/** 
	Indicates the types of media represented by a <see cref="ICodec"
	/> or <see cref="Properties" /> object.
 
 
	These values can be bitwise combined to represent multiple media
	types.
 
*/
public class MediaTypes
{
	/** 
		No media is present.
	*/
	public static final MediaTypes None = new MediaTypes(0);

	/** 
		Audio is present.
	*/
	public static final MediaTypes Audio = new MediaTypes(1);

	/** 
		Video is present.
	*/
	public static final MediaTypes Video = new MediaTypes(2);

	/** 
		A Photo is present.
	*/
	public static final MediaTypes Photo = new MediaTypes(4);

	/** 
		Text is present.
	*/
	public static final MediaTypes Text = new MediaTypes(8);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, MediaTypes> mappings;
	private static java.util.HashMap<Integer, MediaTypes> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MediaTypes.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, MediaTypes>();
				}
			}
		}
		return mappings;
	}

	private MediaTypes(int value)
	{
		intValue = value;
		synchronized (MediaTypes.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static MediaTypes forValue(int value)
	{
		synchronized (MediaTypes.class)
		{
			MediaTypes enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new MediaTypes(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}