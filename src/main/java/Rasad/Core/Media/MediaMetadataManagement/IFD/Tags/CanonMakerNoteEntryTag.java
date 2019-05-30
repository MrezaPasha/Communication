package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// CanonMakerNoteEntryTag.cs:
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
	Label tags for Canon Makernote.
	Based on http: //www.burren.cx/david/canon.html and http://www.exiv2.org/tags-canon.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CanonMakerNoteEntryTag : ushort
public enum CanonMakerNoteEntryTag 
{
	/** 
		Unknown field at tag 0x0000. (Hex: 0x0000)
	*/
	Unknown0((short)0),

	/** 
		Camera Settings. (Hex: 0x0001)
	*/
	CameraSettings((short)1),

	/** 
		Focal Length. (Hex: 0x0002)
	*/
	FocalLength((short)2),

	/** 
		Unknown field at tag 0x0000. (Hex: 0x0003)
	*/
	Unknown3((short)3),

	/** 
		Shot Information. (Hex: 0x0004)
	*/
	ShotInfo((short)4),

	/** 
		Panorama. (Hex: 0x0005)
	*/
	Panorama((short)5),

	/** 
		Image Type. (Hex: 0x0006)
	*/
	ImageType((short)6),

	/** 
		Firmware Version. (Hex: 0x0007)
	*/
	FirmwareVersion((short)7),

	/** 
		Image Number. (Hex: 0x0008)
	*/
	ImageNumber((short)8),

	/** 
		Owner Name. (Hex: 0x0009)
	*/
	OwnerName((short)9),

	/** 
		Serial Number. (Hex: 0x000C)
	*/
	SerialNumber((short)12),

	/** 
		Unknown field at tag 0x0000. (Hex: 0x000D)
	*/
	Unknown13((short)13),

	/** 
		Custom Functions. (Hex: 0x000F)
	*/
	CustomFunctions((short)15),

	/** 
		Model ID. (Hex: 0x0010)
	*/
	ModelID((short)16),

	/** 
		Picture Info. (Hex: 0x0012)
	*/
	PictureInfo((short)18),

	/** 
		Serial Number Format. (Hex: 0x0015)
	*/
	SerialNumberFormat((short)21),

	/** 
		Canon File Info. (Hex: 0x0093)
	*/
	CanonFileInfo((short)147),

	/** 
		Lens Model. (Hex: 0x0095)
	*/
	LensModel((short)149),

	/** 
		Serial Info. (Hex: 0x0096)
	*/
	SerialInfo((short)150),

	/** 
		Processing Info. (Hex: 0x00A0)
	*/
	ProcessingInfo((short)160),

	/** 
		White Balance Table. (Hex: 0x00A9)
	*/
	WhiteBalanceTable((short)169),

	/** 
		Measured Color. (Hex: 0x00AA)
	*/
	MeasuredColor((short)170),

	/** 
		Color Space. (Hex: 0x00B4)
	*/
	ColorSpace((short)180),

	/** 
		Sensor Info. (Hex: 0x00E0)
	*/
	SensorInfo((short)224),

	/** 
		Black Level. (Hex: 0x4008)
	*/
	BlackLevel((short)16392);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, CanonMakerNoteEntryTag> mappings;
	private static java.util.HashMap<Short, CanonMakerNoteEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CanonMakerNoteEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, CanonMakerNoteEntryTag>();
				}
			}
		}
		return mappings;
	}

	private CanonMakerNoteEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static CanonMakerNoteEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}