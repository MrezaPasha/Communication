package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// SonyMakerNoteEntryTag.cs:
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
	Label tags for Sony Makernote.
	Based on http: //www.exiv2.org/tags-sony.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum SonyMakerNoteEntryTag : ushort
public enum SonyMakerNoteEntryTag 
{
	/** 
		Image quality. (Hex: 0X0102)
	*/
	Quality((short)258),

	/** 
		Flash exposure compensation in EV. (Hex: 0X0104)
	*/
	FlashExposureComp((short)260),

	/** 
		Teleconverter Model. (Hex: 0X0105)
	*/
	Teleconverter((short)261),

	/** 
		White Balance Fine Tune Value. (Hex: 0X0112)
	*/
	WhiteBalanceFineTune((short)274),

	/** 
		Camera Settings. (Hex: 0X0114)
	*/
	CameraSettings((short)276),

	/** 
		White balance. (Hex: 0X0115)
	*/
	WhiteBalance((short)277),

	/** 
		PrintIM information. (Hex: 0X0E00)
	*/
	PrintIM((short)3584),

	/** 
		Multi Burst Mode. (Hex: 0X1000)
	*/
	MultiBurstMode((short)4096),

	/** 
		Multi Burst Image Width. (Hex: 0X1001)
	*/
	MultiBurstImageWidth((short)4097),

	/** 
		Multi Burst Image Height. (Hex: 0X1002)
	*/
	MultiBurstImageHeight((short)4098),

	/** 
		Panorama. (Hex: 0X1003)
	*/
	Panorama((short)4099),

	/** 
		Preview Image. (Hex: 0X2001)
	*/
	PreviewImage((short)8193),

	/** 
		Auto High Definition Range. (Hex: 0X200A)
	*/
	AutoHDR((short)8202),

	/** 
		Shot Information. (Hex: 0X3000)
	*/
	ShotInfo((short)12288),

	/** 
		File Format. (Hex: 0XB000)
	*/
	FileFormat((short)45056),

	/** 
		Sony Model ID. (Hex: 0XB001)
	*/
	SonyModelID((short)45057),

	/** 
		Color Reproduction. (Hex: 0XB020)
	*/
	ColorReproduction((short)45088),

	/** 
		Color Temperature. (Hex: 0XB021)
	*/
	ColorTemperature((short)45089),

	/** 
		Color Compensation Filter: negative is green, positive is magenta. (Hex: 0XB022)
	*/
	ColorCompensationFilter((short)45090),

	/** 
		Scene Mode. (Hex: 0XB023)
	*/
	SceneMode((short)45091),

	/** 
		Zone Matching. (Hex: 0XB024)
	*/
	ZoneMatching((short)45092),

	/** 
		Dynamic Range Optimizer. (Hex: 0XB025)
	*/
	DynamicRangeOptimizer((short)45093),

	/** 
		Image stabilization. (Hex: 0XB026)
	*/
	ImageStabilization((short)45094),

	/** 
		Lens identifier. (Hex: 0XB027)
	*/
	LensID((short)45095),

	/** 
		Minolta MakerNote. (Hex: 0XB028)
	*/
	MinoltaMakerNote((short)45096),

	/** 
		Color Mode. (Hex: 0XB029)
	*/
	ColorMode((short)45097),

	/** 
		Full Image Size. (Hex: 0XB02B)
	*/
	FullImageSize((short)45099),

	/** 
		Preview Image Size. (Hex: 0XB02C)
	*/
	PreviewImageSize((short)45100),

	/** 
		Macro. (Hex: 0XB040)
	*/
	Macro((short)45120),

	/** 
		Exposure Mode. (Hex: 0XB041)
	*/
	ExposureMode((short)45121),

	/** 
		Focus mode. (Hex: 0XB042)
	*/
	FocusMode((short)45122),

	/** 
		AF Mode. (Hex: 0XB043)
	*/
	AFMode((short)45123),

	/** 
		AF Illuminator. (Hex: 0XB044)
	*/
	AFIlluminator((short)45124),

	/** 
		Quality. (Hex: 0XB047)
	*/
	Quality2((short)45127),

	/** 
		Flash Level. (Hex: 0XB048)
	*/
	FlashLevel((short)45128),

	/** 
		Release Mode. (Hex: 0XB049)
	*/
	ReleaseMode((short)45129),

	/** 
		Shot number in continous burst mode. (Hex: 0XB04A)
	*/
	SequenceNumber((short)45130),

	/** 
		Anti-Blur. (Hex: 0XB04B)
	*/
	AntiBlur((short)45131),

	/** 
		Long Exposure Noise Reduction. (Hex: 0XB04E)
	*/
	LongExposureNoiseReduction((short)45134),

	/** 
		Dynamic Range Optimizer. (Hex: 0XB04F)
	*/
	DynamicRangeOptimizer2((short)45135),

	/** 
		Intelligent Auto. (Hex: 0XB052)
	*/
	IntelligentAuto((short)45138),

	/** 
		White Balance. (Hex: 0XB054)
	*/
	WhiteBalance2((short)45140);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, SonyMakerNoteEntryTag> mappings;
	private static java.util.HashMap<Short, SonyMakerNoteEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SonyMakerNoteEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, SonyMakerNoteEntryTag>();
				}
			}
		}
		return mappings;
	}

	private SonyMakerNoteEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static SonyMakerNoteEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}