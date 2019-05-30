package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

//
// Tag.cs: This abstract class provides generic access to standard tag
// features. All tag types will extend this class.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   tag.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2003 Scott Wheeler
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
	Indicates the tag types used by a file.
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] public enum TagTypes : uint
public class TagTypes 
{
	/** 
		No tag types.
	*/
	public static final TagTypes None = new TagTypes(0x00000000);

	/** 
		Xiph's Vorbis Comment
	*/
	public static final TagTypes Xiph = new TagTypes(0x00000001);

	/** 
		ID3v1 Tag
	*/
	public static final TagTypes Id3v1 = new TagTypes(0x00000002);

	/** 
		ID3v2 Tag
	*/
	public static final TagTypes Id3v2 = new TagTypes(0x00000004);

	/** 
		APE Tag
	*/
	public static final TagTypes Ape = new TagTypes(0x00000008);

	/** 
		Apple's ILST Tag Format
	*/
	public static final TagTypes Apple = new TagTypes(0x00000010);

	/** 
		ASF Tag
	*/
	public static final TagTypes Asf = new TagTypes(0x00000020);

	/** 
		Standard RIFF INFO List Tag
	*/
	public static final TagTypes RiffInfo = new TagTypes(0x00000040);

	/** 
		RIFF Movie ID List Tag
	*/
	public static final TagTypes MovieId = new TagTypes(0x00000080);

	/** 
		DivX Tag
	*/
	public static final TagTypes DivX = new TagTypes(0x00000100);

	/** 
		FLAC Metadata Blocks Tag
	*/
	public static final TagTypes FlacMetadata = new TagTypes(0x00000200);

	/** 
		TIFF IFD Tag
	*/
	public static final TagTypes TiffIFD = new TagTypes(0x00000400);

	/** 
		XMP Tag
	*/
	public static final TagTypes XMP = new TagTypes(0x00000800);

	/** 
		Jpeg Comment Tag
	*/
	public static final TagTypes JpegComment = new TagTypes(0x00001000);

	/** 
		Gif Comment Tag
	*/
	public static final TagTypes GifComment = new TagTypes(0x00002000);

	/** 
		native PNG keywords
	*/
	public static final TagTypes Png = new TagTypes(0x00004000);

	/** 
	 IPTC-IIM tag
	*/
	public static final TagTypes IPTCIIM = new TagTypes(0x00008000);

	/** 
		Audible Metadata Blocks Tag
	*/
	public static final TagTypes AudibleMetadata = new TagTypes(0x00000400);

	/** 
		All tag types.
	*/
	public static final TagTypes AllTags = new TagTypes(0xFFFFFFFF);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, TagTypes> mappings;
	private static java.util.HashMap<Integer, TagTypes> getMappings()
	{
		if (mappings == null)
		{
			synchronized (TagTypes.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, TagTypes>();
				}
			}
		}
		return mappings;
	}

	private TagTypes(int value)
	{
		intValue = value;
		synchronized (TagTypes.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static TagTypes forValue(int value)
	{
		synchronized (TagTypes.class)
		{
			TagTypes enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new TagTypes(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}