package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// Nikon3MakerNoteEntryTag.cs:
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
	Nikon format 3 makernote tags.
	Based on http: //www.exiv2.org/tags-nikon.html and
	http: //park2.wakwak.com/~tsuruzoh/Computer/Digicams/exif-e.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum Nikon3MakerNoteEntryTag : ushort
public enum Nikon3MakerNoteEntryTag 
{
	/** 
		Makernote version. (Hex: 0x0001)
	*/
	Version((short)1),

	/** 
		ISO speed setting. (Hex: 0X0002)
	*/
	ISOSpeed((short)2),

	/** 
		Color mode. (Hex: 0X0003)
	*/
	ColorMode((short)3),

	/** 
		Image quality setting. (Hex: 0X0004)
	*/
	Quality((short)4),

	/** 
		White balance. (Hex: 0X0005)
	*/
	WhiteBalance((short)5),

	/** 
		Image sharpening setting. (Hex: 0X0006)
	*/
	Sharpening((short)6),

	/** 
		Focus mode. (Hex: 0X0007)
	*/
	Focus((short)7),

	/** 
		Flash setting. (Hex: 0X0008)
	*/
	FlashSetting((short)8),

	/** 
		Flash device. (Hex: 0X0009)
	*/
	FlashDevice((short)9),

	/** 
		Unknown. (Hex: 0X000A)
	*/
	Unknown10((short)10),

	/** 
		White balance bias. (Hex: 0X000B)
	*/
	WhiteBalanceBias((short)11),

	/** 
		WB RB levels. (Hex: 0X000C)
	*/
	WB_RBLevels((short)12),

	/** 
		Program shift. (Hex: 0X000D)
	*/
	ProgramShift((short)13),

	/** 
		Exposure difference. (Hex: 0X000E)
	*/
	ExposureDiff((short)14),

	/** 
		ISO selection. (Hex: 0X000F)
	*/
	ISOSelection((short)15),

	/** 
		Data dump. (Hex: 0X0010)
	*/
	DataDump((short)16),

	/** 
		Offset to an IFD containing a preview image. (Hex: 0x0011)
	*/
	Preview((short)17),

	/** 
		Flash compensation setting. (Hex: 0X0012)
	*/
	FlashComp((short)18),

	/** 
		ISO setting. (Hex: 0X0013)
	*/
	ISOSettings((short)19),

	/** 
		Image boundary. (Hex: 0X0016)
	*/
	ImageBoundary((short)22),

	/** 
		Unknown. (Hex: 0X0017)
	*/
	Unknown23((short)23),

	/** 
		Flash bracket compensation applied. (Hex: 0X0018)
	*/
	FlashBracketComp((short)24),

	/** 
		AE bracket compensation applied. (Hex: 0X0019)
	*/
	ExposureBracketComp((short)25),

	/** 
		Image processing. (Hex: 0X001A)
	*/
	ImageProcessing((short)26),

	/** 
		Crop high speed. (Hex: 0X001B)
	*/
	CropHiSpeed((short)27),

	/** 
		Serial Number. (Hex: 0X001D)
	*/
	SerialNumber((short)29),

	/** 
		Color space. (Hex: 0X001E)
	*/
	ColorSpace((short)30),

	/** 
		VR info. (Hex: 0X001F)
	*/
	VRInfo((short)31),

	/** 
		Image authentication. (Hex: 0X0020)
	*/
	ImageAuthentication((short)32),

	/** 
		ActiveD-lighting. (Hex: 0X0022)
	*/
	ActiveDLighting((short)34),

	/** 
		Picture control. (Hex: 0X0023)
	*/
	PictureControl((short)35),

	/** 
		World time. (Hex: 0X0024)
	*/
	WorldTime((short)36),

	/** 
		ISO info. (Hex: 0X0025)
	*/
	ISOInfo((short)37),

	/** 
		Vignette control. (Hex: 0X002A)
	*/
	VignetteControl((short)42),

	/** 
		Image adjustment setting. (Hex: 0X0080)
	*/
	ImageAdjustment((short)128),

	/** 
		Tone compensation. (Hex: 0X0081)
	*/
	ToneComp((short)129),

	/** 
		Auxiliary lens (adapter). (Hex: 0X0082)
	*/
	AuxiliaryLens((short)130),

	/** 
		Lens type. (Hex: 0X0083)
	*/
	LensType((short)131),

	/** 
		Lens. (Hex: 0X0084)
	*/
	Lens((short)132),

	/** 
		Manual focus distance. (Hex: 0X0085)
	*/
	FocusDistance((short)133),

	/** 
		Digital zoom setting. (Hex: 0X0086)
	*/
	DigitalZoom((short)134),

	/** 
		Mode of flash used. (Hex: 0X0087)
	*/
	FlashMode((short)135),

	/** 
		AF info. (Hex: 0X0088)
	*/
	AFInfo((short)136),

	/** 
		Shooting mode. (Hex: 0X0089)
	*/
	ShootingMode((short)137),

	/** 
		Auto bracket release. (Hex: 0X008A)
	*/
	AutoBracketRelease((short)138),

	/** 
		Lens FStops. (Hex: 0X008B)
	*/
	LensFStops((short)139),

	/** 
		Contrast curve. (Hex: 0X008C)
	*/
	ContrastCurve((short)140),

	/** 
		Color hue. (Hex: 0X008D)
	*/
	ColorHue((short)141),

	/** 
		Scene mode. (Hex: 0X008F)
	*/
	SceneMode((short)143),

	/** 
		Light source. (Hex: 0X0090)
	*/
	LightSource((short)144),

	/** 
		Shot info. (Hex: 0X0091)
	*/
	ShotInfo((short)145),

	/** 
		Hue adjustment. (Hex: 0X0092)
	*/
	HueAdjustment((short)146),

	/** 
		NEF compression. (Hex: 0X0093)
	*/
	NEFCompression((short)147),

	/** 
		Saturation. (Hex: 0X0094)
	*/
	Saturation((short)148),

	/** 
		Noise reduction. (Hex: 0X0095)
	*/
	NoiseReduction((short)149),

	/** 
		Linearization table. (Hex: 0X0096)
	*/
	LinearizationTable((short)150),

	/** 
		Color balance. (Hex: 0X0097)
	*/
	ColorBalance((short)151),

	/** 
		Lens data settings. (Hex: 0X0098)
	*/
	LensData((short)152),

	/** 
		Raw image center. (Hex: 0X0099)
	*/
	RawImageCenter((short)153),

	/** 
		Sensor pixel size. (Hex: 0X009A)
	*/
	SensorPixelSize((short)154),

	/** 
		Unknown. (Hex: 0X009B)
	*/
	Unknown155((short)155),

	/** 
		Scene assist. (Hex: 0X009C)
	*/
	SceneAssist((short)156),

	/** 
		Retouch history. (Hex: 0X009E)
	*/
	RetouchHistory((short)158),

	/** 
		Unknown. (Hex: 0X009F)
	*/
	Unknown159((short)159),

	/** 
		Camera serial number, usually starts with "NO= ". (Hex: 0X00A0)
	*/
	SerialNO((short)160),

	/** 
		Image data size. (Hex: 0X00A2)
	*/
	ImageDataSize((short)162),

	/** 
		Unknown. (Hex: 0X00A3)
	*/
	Unknown163((short)163),

	/** 
		Image count. (Hex: 0X00A5)
	*/
	ImageCount((short)165),

	/** 
		Deleted image count. (Hex: 0X00A6)
	*/
	DeletedImageCount((short)166),

	/** 
		Number of shots taken by camera. (Hex: 0X00A7)
	*/
	ShutterCount((short)167),

	/** 
		Flash info. (Hex: 0X00A8)
	*/
	FlashInfo((short)168),

	/** 
		Image optimization. (Hex: 0X00A9)
	*/
	ImageOptimization((short)169),

	/** 
		Saturation. (Hex: 0X00AA)
	*/
	Saturation2((short)170),

	/** 
		Program variation. (Hex: 0X00AB)
	*/
	VariProgram((short)171),

	/** 
		Image stabilization. (Hex: 0X00AC)
	*/
	ImageStabilization((short)172),

	/** 
		AF response. (Hex: 0X00AD)
	*/
	AFResponse((short)173),

	/** 
		Multi exposure. (Hex: 0X00B0)
	*/
	MultiExposure((short)176),

	/** 
		High ISO Noise Reduction. (Hex: 0X00B1)
	*/
	HighISONoiseReduction((short)177),

	/** 
		Toning effect. (Hex: 0X00B3)
	*/
	ToningEffect((short)179),

	/** 
		AF info 2. (Hex: 0X00B7)
	*/
	AFInfo2((short)183),

	/** 
		File info. (Hex: 0X00B8)
	*/
	FileInfo((short)184),

	/** 
		PrintIM information. (Hex: 0X0E00)
	*/
	PrintIM((short)3584),

	/** 
		Capture data. (Hex: 0X0E01)
	*/
	CaptureData((short)3585),

	/** 
		Capture version. (Hex: 0X0E09)
	*/
	CaptureVersion((short)3593),

	/** 
		Capture offsets. (Hex: 0X0E0E)
	*/
	CaptureOffsets((short)3598),

	/** 
		Scan IFD. (Hex: 0X0E10)
	*/
	ScanIFD((short)3600),

	/** 
		ICC profile. (Hex: 0X0E1D)
	*/
	ICCProfile((short)3613),

	/** 
		Capture output. (Hex: 0X0E1E)
	*/
	CaptureOutput((short)3614);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, Nikon3MakerNoteEntryTag> mappings;
	private static java.util.HashMap<Short, Nikon3MakerNoteEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (Nikon3MakerNoteEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, Nikon3MakerNoteEntryTag>();
				}
			}
		}
		return mappings;
	}

	private Nikon3MakerNoteEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static Nikon3MakerNoteEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}