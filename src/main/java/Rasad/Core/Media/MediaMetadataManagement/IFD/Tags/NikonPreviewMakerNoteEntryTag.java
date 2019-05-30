package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// NikonPreviewMakerNoteEntryTag.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009-2010 Ruben Vermeersch
// Copyright (C) 2009 Mike Gemuende
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
	Nikon makernote preview image tags
	The preview image is contained in a sub-IFD stored by the tag
	Nikon3MakerNoteEntryTag.Preview.
	Based on:
	http: //www.sno.phy.queensu.ca/~phil/exiftool/TagNames/Nikon.html#PreviewImage
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum NikonPreviewMakerNoteEntryTag : ushort
public enum NikonPreviewMakerNoteEntryTag 
{

	/** 
		 Compression scheme used on the image data. (Hex: 0x0103)
		 http://www.awaresystems.be/imaging/tiff/tifftags/compression.html
	*/
	Compression((short)259),

	/** 
		 The number of pixels per ResolutionUnit in the ImageWidth direction. (Hex: 0x011A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/xresolution.html
	*/
	XResolution((short)282),

	/** 
		 The number of pixels per ResolutionUnit in the ImageLength direction. (Hex: 0x011B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/yresolution.html
	*/
	YResolution((short)283),

	/** 
		 The unit of measurement for XResolution and YResolution. (Hex: 0x0128)
		 http://www.awaresystems.be/imaging/tiff/tifftags/resolutionunit.html
	*/
	ResolutionUnit((short)296),

	/** 
		 Start of the preview image data. (Hex: 0x0201)
		 http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/Nikon.html#PreviewImage
	*/
	PreviewImageStart((short)513),

	/** 
		 Length of the preview image data. (Hex: 0x0202)
		 http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/Nikon.html#PreviewImage
	*/
	PreviewImageLength((short)514),

	/** 
		 Specifies the positioning of subsampled chrominance components relative to luminance samples. (Hex: 0x0213)
		 http://www.awaresystems.be/imaging/tiff/tifftags/ycbcrpositioning.html
	*/
	YCbCrPositioning((short)531);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, NikonPreviewMakerNoteEntryTag> mappings;
	private static java.util.HashMap<Short, NikonPreviewMakerNoteEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (NikonPreviewMakerNoteEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, NikonPreviewMakerNoteEntryTag>();
				}
			}
		}
		return mappings;
	}

	private NikonPreviewMakerNoteEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static NikonPreviewMakerNoteEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}