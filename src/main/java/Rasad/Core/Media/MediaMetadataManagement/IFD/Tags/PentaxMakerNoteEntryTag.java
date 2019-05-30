package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// PentaxMakerNoteEntryTag.cs:
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
	Pentax makernote tags.
	Based on http: //www.exiv2.org/tags-pentax.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum PentaxMakerNoteEntryTag : ushort
public enum PentaxMakerNoteEntryTag 
{
	/** 
		Pentax Makernote version. (Hex: 0X0000)
	*/
	Version((short)0),

	/** 
		Camera shooting mode. (Hex: 0X0001)
	*/
	Mode((short)1),

	/** 
		Resolution of a preview image. (Hex: 0X0002)
	*/
	PreviewResolution((short)2),

	/** 
		Size of an IFD containing a preview image. (Hex: 0X0003)
	*/
	PreviewLength((short)3),

	/** 
		Offset to an IFD containing a preview image. (Hex: 0X0004)
	*/
	PreviewOffset((short)4),

	/** 
		Pentax model idenfication. (Hex: 0X0005)
	*/
	ModelID((short)5),

	/** 
		Date. (Hex: 0X0006)
	*/
	Date((short)6),

	/** 
		Time. (Hex: 0X0007)
	*/
	Time((short)7),

	/** 
		Image quality settings. (Hex: 0X0008)
	*/
	Quality((short)8),

	/** 
		Image size settings. (Hex: 0X0009)
	*/
	Size((short)9),

	/** 
		Flash mode settings. (Hex: 0X000C)
	*/
	Flash((short)12),

	/** 
		Focus mode settings. (Hex: 0X000D)
	*/
	Focus((short)13),

	/** 
		Selected AF point. (Hex: 0X000E)
	*/
	AFPoint((short)14),

	/** 
		AF point in focus. (Hex: 0X000F)
	*/
	AFPointInFocus((short)15),

	/** 
		Exposure time. (Hex: 0X0012)
	*/
	ExposureTime((short)18),

	/** 
		F-Number. (Hex: 0X0013)
	*/
	FNumber((short)19),

	/** 
		ISO sensitivity settings. (Hex: 0X0014)
	*/
	ISO((short)20),

	/** 
		Exposure compensation. (Hex: 0X0016)
	*/
	ExposureCompensation((short)22),

	/** 
		MeteringMode. (Hex: 0X0017)
	*/
	MeteringMode((short)23),

	/** 
		AutoBracketing. (Hex: 0X0018)
	*/
	AutoBracketing((short)24),

	/** 
		White ballance. (Hex: 0X0019)
	*/
	WhiteBallance((short)25),

	/** 
		White ballance mode. (Hex: 0X001A)
	*/
	WhiteBallanceMode((short)26),

	/** 
		Blue color balance. (Hex: 0X001B)
	*/
	BlueBalance((short)27),

	/** 
		Red color balance. (Hex: 0X001C)
	*/
	RedBalance((short)28),

	/** 
		FocalLength. (Hex: 0X001D)
	*/
	FocalLength((short)29),

	/** 
		Digital zoom. (Hex: 0X001E)
	*/
	DigitalZoom((short)30),

	/** 
		Saturation. (Hex: 0X001F)
	*/
	Saturation((short)31),

	/** 
		Contrast. (Hex: 0X0020)
	*/
	Contrast((short)32),

	/** 
		Sharpness. (Hex: 0X0021)
	*/
	Sharpness((short)33),

	/** 
		Location. (Hex: 0X0022)
	*/
	Location((short)34),

	/** 
		Home town. (Hex: 0X0023)
	*/
	Hometown((short)35),

	/** 
		Destination. (Hex: 0X0024)
	*/
	Destination((short)36),

	/** 
		Whether day saving time is active in home town. (Hex: 0X0025)
	*/
	HometownDST((short)37),

	/** 
		Whether day saving time is active in destination. (Hex: 0X0026)
	*/
	DestinationDST((short)38),

	/** 
		DSPFirmwareVersion. (Hex: 0X0027)
	*/
	DSPFirmwareVersion((short)39),

	/** 
		CPUFirmwareVersion. (Hex: 0X0028)
	*/
	CPUFirmwareVersion((short)40),

	/** 
		Frame number. (Hex: 0X0029)
	*/
	FrameNumber((short)41),

	/** 
		Camera calculated light value, includes exposure compensation. (Hex: 0X002D)
	*/
	EffectiveLV((short)45),

	/** 
		Image processing. (Hex: 0X0032)
	*/
	ImageProcessing((short)50),

	/** 
		Picture mode. (Hex: 0X0033)
	*/
	PictureMode((short)51),

	/** 
		Drive mode. (Hex: 0X0034)
	*/
	DriveMode((short)52),

	/** 
		Color space. (Hex: 0X0037)
	*/
	ColorSpace((short)55),

	/** 
		Image area offset. (Hex: 0X0038)
	*/
	ImageAreaOffset((short)56),

	/** 
		Raw image size. (Hex: 0X0039)
	*/
	RawImageSize((short)57),

	/** 
		Preview image borders. (Hex: 0X003E)
	*/
	PreviewImageBorders((short)62),

	/** 
		Lens type. (Hex: 0X003F)
	*/
	LensType((short)63),

	/** 
		Sensitivity adjust. (Hex: 0X0040)
	*/
	SensitivityAdjust((short)64),

	/** 
		Digital filter. (Hex: 0X0041)
	*/
	DigitalFilter((short)65),

	/** 
		Camera temperature. (Hex: 0X0047)
	*/
	Temperature((short)71),

	/** 
		AE lock. (Hex: 0X0048)
	*/
	AELock((short)72),

	/** 
		Noise reduction. (Hex: 0X0049)
	*/
	NoiseReduction((short)73),

	/** 
		Flash exposure compensation. (Hex: 0X004D)
	*/
	FlashExposureCompensation((short)77),

	/** 
		Image tone. (Hex: 0X004F)
	*/
	ImageTone((short)79),

	/** 
		Colort temperature. (Hex: 0X0050)
	*/
	ColorTemperature((short)80),

	/** 
		Shake reduction information. (Hex: 0X005C)
	*/
	ShakeReduction((short)92),

	/** 
		Shutter count. (Hex: 0X005D)
	*/
	ShutterCount((short)93),

	/** 
		Dynamic range expansion. (Hex: 0X0069)
	*/
	DynamicRangeExpansion((short)105),

	/** 
		High ISO noise reduction. (Hex: 0X0071)
	*/
	HighISONoiseReduction((short)113),

	/** 
		AF Adjustment. (Hex: 0X0072)
	*/
	AFAdjustment((short)114),

	/** 
		Black point. (Hex: 0X0200)
	*/
	BlackPoint((short)512),

	/** 
		White point. (Hex: 0X0201)
	*/
	WhitePoint((short)513),

	/** 
		ShotInfo. (Hex: 0X0205)
	*/
	ShotInfo((short)517),

	/** 
		AEInfo. (Hex: 0X0206)
	*/
	AEInfo((short)518),

	/** 
		LensInfo. (Hex: 0X0207)
	*/
	LensInfo((short)519),

	/** 
		FlashInfo. (Hex: 0X0208)
	*/
	FlashInfo((short)520),

	/** 
		AEMeteringSegments. (Hex: 0X0209)
	*/
	AEMeteringSegments((short)521),

	/** 
		FlashADump. (Hex: 0X020A)
	*/
	FlashADump((short)522),

	/** 
		FlashBDump. (Hex: 0X020B)
	*/
	FlashBDump((short)523),

	/** 
		WB_RGGBLevelsDaylight. (Hex: 0X020D)
	*/
	WB_RGGBLevelsDaylight((short)525),

	/** 
		WB_RGGBLevelsShade. (Hex: 0X020E)
	*/
	WB_RGGBLevelsShade((short)526),

	/** 
		WB_RGGBLevelsCloudy. (Hex: 0X020F)
	*/
	WB_RGGBLevelsCloudy((short)527),

	/** 
		WB_RGGBLevelsTungsten. (Hex: 0X0210)
	*/
	WB_RGGBLevelsTungsten((short)528),

	/** 
		WB_RGGBLevelsFluorescentD. (Hex: 0X0211)
	*/
	WB_RGGBLevelsFluorescentD((short)529),

	/** 
		WB_RGGBLevelsFluorescentN. (Hex: 0X0212)
	*/
	WB_RGGBLevelsFluorescentN((short)530),

	/** 
		WB_RGGBLevelsFluorescentW. (Hex: 0X0213)
	*/
	WB_RGGBLevelsFluorescentW((short)531),

	/** 
		WB_RGGBLevelsFlash. (Hex: 0X0214)
	*/
	WB_RGGBLevelsFlash((short)532),

	/** 
		CameraInfo. (Hex: 0X0215)
	*/
	CameraInfo((short)533),

	/** 
		BatteryInfo. (Hex: 0X0216)
	*/
	BatteryInfo((short)534),

	/** 
		AFInfo. (Hex: 0X021F)
	*/
	AFInfo((short)543),

	/** 
		ColorInfo. (Hex: 0X0222)
	*/
	ColorInfo((short)546),

	/** 
		Serial Number. (Hex: 0X0229)
	*/
	SerialNumber((short)553);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, PentaxMakerNoteEntryTag> mappings;
	private static java.util.HashMap<Short, PentaxMakerNoteEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PentaxMakerNoteEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, PentaxMakerNoteEntryTag>();
				}
			}
		}
		return mappings;
	}

	private PentaxMakerNoteEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static PentaxMakerNoteEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}