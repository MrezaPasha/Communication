package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// OlympusMakerNoteEntryTag.cs:
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
	Olympus makernote tags.
	Based on http: //www.exiv2.org/tags-olympus.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum OlympusMakerNoteEntryTag : ushort
public enum OlympusMakerNoteEntryTag 
{
	/** 
		Thumbnail image. (Hex: 0X0100)
	*/
	ThumbnailImage((short)256),

	/** 
		Picture taking mode. (Hex: 0X0200)
	*/
	SpecialMode((short)512),

	/** 
		Image quality setting. (Hex: 0X0201)
	*/
	Quality((short)513),

	/** 
		Macro mode. (Hex: 0X0202)
	*/
	Macro((short)514),

	/** 
		Black and white mode. (Hex: 0X0203)
	*/
	BWMode((short)515),

	/** 
		Digital zoom ratio. (Hex: 0X0204)
	*/
	DigitalZoom((short)516),

	/** 
		Focal plane diagonal. (Hex: 0X0205)
	*/
	FocalPlaneDiagonal((short)517),

	/** 
		Lens distortion parameters. (Hex: 0X0206)
	*/
	LensDistortionParams((short)518),

	/** 
		Software firmware version. (Hex: 0X0207)
	*/
	FirmwareVersion((short)519),

	/** 
		ASCII format data such as [PictureInfo]. (Hex: 0X0208)
	*/
	PictureInfo((short)520),

	/** 
		Camera ID data. (Hex: 0X0209)
	*/
	CameraID((short)521),

	/** 
		Pre-capture frames. (Hex: 0X0300)
	*/
	PreCaptureFrames((short)768),

	/** 
		One touch white balance. (Hex: 0X0302)
	*/
	OneTouchWB((short)770),

	/** 
		Serial number. (Hex: 0X0404)
	*/
	SerialNumber((short)1028),

	/** 
		PrintIM information. (Hex: 0X0E00)
	*/
	PrintIM((short)3584),

	/** 
		Various camera settings 1. (Hex: 0X0F00)
	*/
	DataDump1((short)3840),

	/** 
		Various camera settings 2. (Hex: 0X0F01)
	*/
	DataDump2((short)3841),

	/** 
		Shutter speed value. (Hex: 0X1000)
	*/
	ShutterSpeed((short)4096),

	/** 
		ISO speed value. (Hex: 0X1001)
	*/
	ISOSpeed((short)4097),

	/** 
		Aperture value. (Hex: 0X1002)
	*/
	ApertureValue((short)4098),

	/** 
		Brightness value. (Hex: 0X1003)
	*/
	Brightness((short)4099),

	/** 
		Flash mode. (Hex: 0X1004)
	*/
	FlashMode((short)4100),

	/** 
		Flash device. (Hex: 0X1005)
	*/
	FlashDevice((short)4101),

	/** 
		Exposure compensation value. (Hex: 0X1006)
	*/
	Bracket((short)4102),

	/** 
		Sensor temperature. (Hex: 0X1007)
	*/
	SensorTemperature((short)4103),

	/** 
		Lens temperature. (Hex: 0X1008)
	*/
	LensTemperature((short)4104),

	/** 
		Focus mode. (Hex: 0X100B)
	*/
	FocusMode((short)4107),

	/** 
		Manual focus distance. (Hex: 0X100C)
	*/
	FocusDistance((short)4108),

	/** 
		Zoom step count. (Hex: 0X100D)
	*/
	Zoom((short)4109),

	/** 
		Macro focus step count. (Hex: 0X100E)
	*/
	MacroFocus((short)4110),

	/** 
		Sharpness factor. (Hex: 0X100F)
	*/
	SharpnessFactor((short)4111),

	/** 
		Flash charge level. (Hex: 0X1010)
	*/
	FlashChargeLevel((short)4112),

	/** 
		Color matrix. (Hex: 0X1011)
	*/
	ColorMatrix((short)4113),

	/** 
		Black level. (Hex: 0X1012)
	*/
	BlackLevel((short)4114),

	/** 
		White balance mode. (Hex: 0X1015)
	*/
	WhiteBalance((short)4117),

	/** 
		Red balance. (Hex: 0X1017)
	*/
	RedBalance((short)4119),

	/** 
		Blue balance. (Hex: 0X1018)
	*/
	BlueBalance((short)4120),

	/** 
		Serial number 2. (Hex: 0X101A)
	*/
	SerialNumber2((short)4122),

	/** 
		Flash exposure compensation. (Hex: 0X1023)
	*/
	FlashBias((short)4131),

	/** 
		External flash bounce. (Hex: 0X1026)
	*/
	ExternalFlashBounce((short)4134),

	/** 
		External flash zoom. (Hex: 0X1027)
	*/
	ExternalFlashZoom((short)4135),

	/** 
		External flash mode. (Hex: 0X1028)
	*/
	ExternalFlashMode((short)4136),

	/** 
		Contrast setting. (Hex: 0X1029)
	*/
	Contrast((short)4137),

	/** 
		Sharpness factor. (Hex: 0X102A)
	*/
	SharpnessFactor2((short)4138),

	/** 
		Color control. (Hex: 0X102B)
	*/
	ColorControl((short)4139),

	/** 
		Valid bits. (Hex: 0X102C)
	*/
	ValidBits((short)4140),

	/** 
		Coring filter. (Hex: 0X102D)
	*/
	CoringFilter((short)4141),

	/** 
		Image width. (Hex: 0X102E)
	*/
	ImageWidth((short)4142),

	/** 
		Image height. (Hex: 0X102F)
	*/
	ImageHeight((short)4143),

	/** 
		Compression ratio. (Hex: 0X1034)
	*/
	CompressionRatio((short)4148),

	/** 
		Preview image embedded. (Hex: 0X1035)
	*/
	Thumbnail((short)4149),

	/** 
		Offset of the preview image. (Hex: 0X1036)
	*/
	ThumbnailOffset((short)4150),

	/** 
		Size of the preview image. (Hex: 0X1037)
	*/
	ThumbnailLength((short)4151),

	/** 
		CCD scan mode. (Hex: 0X1039)
	*/
	CCDScanMode((short)4153),

	/** 
		Noise reduction. (Hex: 0X103A)
	*/
	NoiseReduction((short)4154),

	/** 
		Infinity lens step. (Hex: 0X103B)
	*/
	InfinityLensStep((short)4155),

	/** 
		Near lens step. (Hex: 0X103C)
	*/
	NearLensStep((short)4156),

	/** 
		Camera equipment sub-IFD. (Hex: 0X2010)
	*/
	Equipment((short)8208),

	/** 
		Camera Settings sub-IFD. (Hex: 0X2020)
	*/
	CameraSettings((short)8224),

	/** 
		Raw development sub-IFD. (Hex: 0X2030)
	*/
	RawDevelopment((short)8240),

	/** 
		Raw development 2 sub-IFD. (Hex: 0X2031)
	*/
	RawDevelopment2((short)8241),

	/** 
		Image processing sub-IFD. (Hex: 0X2040)
	*/
	ImageProcessing((short)8256),

	/** 
		Focus sub-IFD. (Hex: 0X2050)
	*/
	FocusInfo((short)8272),

	/** 
		Raw sub-IFD. (Hex: 0X3000)
	*/
	RawInfo((short)12288);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, OlympusMakerNoteEntryTag> mappings;
	private static java.util.HashMap<Short, OlympusMakerNoteEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (OlympusMakerNoteEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, OlympusMakerNoteEntryTag>();
				}
			}
		}
		return mappings;
	}

	private OlympusMakerNoteEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static OlympusMakerNoteEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}