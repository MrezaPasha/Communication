package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// PanasonicMakerNoteEntryTag.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//
// Copyright (C) 2010 Ruben Vermeersch
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
	Panasonic makernote tags.
	Based on http: //www.exiv2.org/tags-panasonic.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum PanasonicMakerNoteEntryTag : ushort
public enum PanasonicMakerNoteEntryTag 
{
	/** 
		Image Quality. (Hex: 0x0001)
	*/
	Quality((short)1),

	/** 
		Firmware version. (Hex: 0X0002)
	*/
	FirmwareVersion((short)2),

	/** 
		White balance setting. (Hex: 0X0003)
	*/
	WhiteBalance((short)3),

	/** 
		Unknown. (Hex: 0X0004)
	*/
	Unknown4((short)4),

	/** 
		Focus mode. (Hex: 0X0007)
	*/
	FocusMode((short)7),

	/** 
		AF mode. (Hex: 0X000F)
	*/
	AFMode((short)15),

	/** 
		ISO Speed. (Hex: 0X0017)
	*/
	ISO((short)23),

	/** 
		Image stabilization. (Hex: 0X001A)
	*/
	ImageStabilization((short)26),

	/** 
		Macro mode. (Hex: 0X001C)
	*/
	Macro((short)28),

	/** 
		Shooting mode. (Hex: 0X001F)
	*/
	ShootingMode((short)31),

	/** 
		Audio. (Hex: 0X0020)
	*/
	Audio((short)32),

	/** 
		Data dump. (Hex: 0X0021)
	*/
	DataDump((short)33),

	/** 
		Unknown. (Hex: 0X0022)
	*/
	Unknown34((short)34),

	/** 
		White balance adjustment. (Hex: 0X0023)
	*/
	WhiteBalanceBias((short)35),

	/** 
		Flash bias. (Hex: 0X0024)
	*/
	FlashBias((short)36),

	/** 
		This number is unique, and contains the date of manufacture, but
		is not the same as the number printed on the camera body.
		(Hex: 0X0025)
	*/
	InternalSerialNumber((short)37),

	/** 
		Exif version. (Hex: 0X0026)
	*/
	ExifVersion((short)38),

	/** 
		Unknown. (Hex: 0X0027)
	*/
	Unknown39((short)39),

	/** 
		Color effect. (Hex: 0X0028)
	*/
	ColorEffect((short)40),

	/** 
		Time in 1/100s from when the camera was powered on to when the
		image is written to memory card. (Hex: 0X0029)
	*/
	TimeSincePowerOn((short)41),

	/** 
		Burst mode. (Hex: 0X002A)
	*/
	BurstMode((short)42),

	/** 
		Sequence number. (Hex: 0X002B)
	*/
	SequenceNumber((short)43),

	/** 
		Contrast setting. (Hex: 0X002C)
	*/
	Contrast((short)44),

	/** 
		Noise reduction. (Hex: 0X002D)
	*/
	NoiseReduction((short)45),

	/** 
		Self timer. (Hex: 0X002E)
	*/
	SelfTimer((short)46),

	/** 
		Unknown. (Hex: 0X002F)
	*/
	Unknown47((short)47),

	/** 
		Rotation. (Hex: 0X0030)
	*/
	Rotation((short)48),

	/** 
		Unknown. (Hex: 0X0031)
	*/
	Unknown49((short)49),

	/** 
		Color mode. (Hex: 0X0032)
	*/
	ColorMode((short)50),

	/** 
		Baby (or pet) age. (Hex: 0X0033)
	*/
	BabyAge((short)51),

	/** 
		Optical zoom mode. (Hex: 0X0034)
	*/
	OpticalZoomMode((short)52),

	/** 
		Conversion lens. (Hex: 0X0035)
	*/
	ConversionLens((short)53),

	/** 
		Travel day. (Hex: 0X0036)
	*/
	TravelDay((short)54),

	/** 
		Contrast. (Hex: 0X0039)
	*/
	Contrast2((short)57),

	/** 
		World time location. (Hex: 0X003A)
	*/
	WorldTimeLocation((short)58),

	/** 
		Program ISO. (Hex: 0X003C)
	*/
	ProgramISO((short)60),

	/** 
		Saturation. (Hex: 0X0040)
	*/
	Saturation((short)64),

	/** 
		Sharpness. (Hex: 0X0041)
	*/
	Sharpness((short)65),

	/** 
		Film mode. (Hex: 0X0042)
	*/
	FilmMode((short)66),

	/** 
		WB adjust AB. Positive is a shift toward blue. (Hex: 0X0046)
	*/
	WBAdjustAB((short)70),

	/** 
		WBAdjustGM. Positive is a shift toward green. (Hex: 0X0047)
	*/
	WBAdjustGM((short)71),

	/** 
		Lens type. (Hex: 0X0051)
	*/
	LensType((short)81),

	/** 
		Lens serial number. (Hex: 0X0052)
	*/
	LensSerialNumber((short)82),

	/** 
		Accessory type. (Hex: 0X0053)
	*/
	AccessoryType((short)83),

	/** 
		PrintIM information. (Hex: 0X0E00)
	*/
	PrintIM((short)3584),

	/** 
		Unknown. (Hex: 0X4449)
	*/
	Unknown17481((short)17481),

	/** 
		MakerNote version. (Hex: 0X8000)
	*/
	MakerNoteVersion((short)32768),

	/** 
		Scene mode. (Hex: 0X8001)
	*/
	SceneMode((short)32769),

	/** 
		WB red level. (Hex: 0X8004)
	*/
	WBRedLevel((short)32772),

	/** 
		WB green level. (Hex: 0X8005)
	*/
	WBGreenLevel((short)32773),

	/** 
		WB blue level. (Hex: 0X8006)
	*/
	WBBlueLevel((short)32774),

	/** 
		Baby (or pet) age. (Hex: 0X8010)
	*/
	BabyAge2((short)32784);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, PanasonicMakerNoteEntryTag> mappings;
	private static java.util.HashMap<Short, PanasonicMakerNoteEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PanasonicMakerNoteEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, PanasonicMakerNoteEntryTag>();
				}
			}
		}
		return mappings;
	}

	private PanasonicMakerNoteEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static PanasonicMakerNoteEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}