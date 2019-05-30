package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// ExifEntryTag.cs:
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
	Entry tags occuring in the Exif IFD
	The complete overview can be obtained at:
	http: //www.awaresystems.be/imaging/tiff.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum ExifEntryTag : ushort
public enum ExifEntryTag 
{
	/** 
		 Contains two values representing the minimum rows and columns
		 to define the repeating patterns of the color filter array.
		 (Hex: 0x828D)
	*/
	CFARepeatPatternDim((short)33421),

	/** 
		 Contains two values representing the minimum rows and columns
		 to define the repeating patterns of the color filter array.
		 (Hex: 0x828E)
	*/
	CFAPattern((short)33422),

	/** 
		 Exposure time, given in seconds. (Hex: 0x829A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/exposuretime.html
	*/
	ExposureTime((short)33434),

	/** 
		 The F number. (Hex: 0x829D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/fnumber.html
	*/
	FNumber((short)33437),

	/** 
		 The class of the program used by the camera to set exposure when the picture is taken. (Hex: 0x8822)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/exposureprogram.html
	*/
	ExposureProgram((short)34850),

	/** 
		 Indicates the spectral sensitivity of each channel of the camera used. (Hex: 0x8824)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/spectralsensitivity.html
	*/
	SpectralSensitivity((short)34852),

	/** 
		 Indicates the ISO Speed and ISO Latitude of the camera or input device as specified in ISO 12232. (Hex: 0x8827)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/isospeedratings.html
	*/
	ISOSpeedRatings((short)34855),

	/** 
		 Indicates the Opto-Electric Conversion Function (OECF) specified in ISO 14524. (Hex: 0x8828)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/oecf.html
	*/
	OECF((short)34856),

	/** 
		 The version of the supported Exif standard. (Hex: 0x9000)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/exifversion.html
	*/
	ExifVersion((short)36864),

	/** 
		 The date and time when the original image data was generated. (Hex: 0x9003)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/datetimeoriginal.html
	*/
	DateTimeOriginal((short)36867),

	/** 
		 The date and time when the image was stored as digital data. (Hex: 0x9004)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/datetimedigitized.html
	*/
	DateTimeDigitized((short)36868),

	/** 
		 Specific to compressed data; specifies the channels and complements PhotometricInterpretation (Hex: 0x9101)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/componentsconfiguration.html
	*/
	ComponentsConfiguration((short)37121),

	/** 
		 Specific to compressed data; states the compressed bits per pixel. (Hex: 0x9102)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/compressedbitsperpixel.html
	*/
	CompressedBitsPerPixel((short)37122),

	/** 
		 Shutter speed. (Hex: 0x9201)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/shutterspeedvalue.html
	*/
	ShutterSpeedValue((short)37377),

	/** 
		 The lens aperture. (Hex: 0x9202)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/aperturevalue.html
	*/
	ApertureValue((short)37378),

	/** 
		 The value of brightness. (Hex: 0x9203)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/brightnessvalue.html
	*/
	BrightnessValue((short)37379),

	/** 
		 The exposure bias. (Hex: 0x9204)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/exposurebiasvalue.html
	*/
	ExposureBiasValue((short)37380),

	/** 
		 The smallest F number of the lens. (Hex: 0x9205)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/maxaperturevalue.html
	*/
	MaxApertureValue((short)37381),

	/** 
		 The distance to the subject, given in meters. (Hex: 0x9206)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/subjectdistance.html
	*/
	SubjectDistance((short)37382),

	/** 
		 The metering mode. (Hex: 0x9207)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/meteringmode.html
	*/
	MeteringMode((short)37383),

	/** 
		 The kind of light source. (Hex: 0x9208)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/lightsource.html
	*/
	LightSource((short)37384),

	/** 
		 Indicates the status of flash when the image was shot. (Hex: 0x9209)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/flash.html
	*/
	Flash((short)37385),

	/** 
		 The actual focal length of the lens, in mm. (Hex: 0x920A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/focallength.html
	*/
	FocalLength((short)37386),

	/** 
		 Indicates the location and area of the main subject in the overall scene. (Hex: 0x9214)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/subjectarea.html
	*/
	SubjectArea((short)37396),

	/** 
		 Manufacturer specific information. (Hex: 0x927C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/makernote.html
	*/
	MakerNote((short)37500),

	/** 
		 Keywords or comments on the image; complements ImageDescription. (Hex: 0x9286)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/usercomment.html
	*/
	UserComment((short)37510),

	/** 
		 A tag used to record fractions of seconds for the DateTime tag. (Hex: 0x9290)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/subsectime.html
	*/
	SubsecTime((short)37520),

	/** 
		 A tag used to record fractions of seconds for the DateTimeOriginal tag. (Hex: 0x9291)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/subsectimeoriginal.html
	*/
	SubsecTimeOriginal((short)37521),

	/** 
		 A tag used to record fractions of seconds for the DateTimeDigitized tag. (Hex: 0x9292)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/subsectimedigitized.html
	*/
	SubsecTimeDigitized((short)37522),

	/** 
		 The Flashpix format version supported by a FPXR file. (Hex: 0xA000)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/flashpixversion.html
	*/
	FlashpixVersion((short)40960),

	/** 
		 The color space information tag is always recorded as the color space specifier. (Hex: 0xA001)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/colorspace.html
	*/
	ColorSpace((short)40961),

	/** 
		 Specific to compressed data; the valid width of the meaningful image. (Hex: 0xA002)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/pixelxdimension.html
	*/
	PixelXDimension((short)40962),

	/** 
		 Specific to compressed data; the valid height of the meaningful image. (Hex: 0xA003)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/pixelydimension.html
	*/
	PixelYDimension((short)40963),

	/** 
		 Used to record the name of an audio file related to the image data. (Hex: 0xA004)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/relatedsoundfile.html
	*/
	RelatedSoundFile((short)40964),

	/** 
		 Indicates the strobe energy at the time the image is captured, as measured in Beam Candle Power Seconds (Hex: 0xA20B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/flashenergy.html
	*/
	FlashEnergy((short)41483),

	/** 
		 Records the camera or input device spatial frequency table and SFR values in the direction of image width, image height, and diagonal direction, as specified in ISO 12233. (Hex: 0xA20C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/spatialfrequencyresponse.html
	*/
	SpatialFrequencyResponse((short)41484),

	/** 
		 Indicates the number of pixels in the image width (X) direction per FocalPlaneResolutionUnit on the camera focal plane. (Hex: 0xA20E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/focalplanexresolution.html
	*/
	FocalPlaneXResolution((short)41486),

	/** 
		 Indicates the number of pixels in the image height (Y) direction per FocalPlaneResolutionUnit on the camera focal plane. (Hex: 0xA20F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/focalplaneyresolution.html
	*/
	FocalPlaneYResolution((short)41487),

	/** 
		 Indicates the unit for measuring FocalPlaneXResolution and FocalPlaneYResolution. (Hex: 0xA210)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/focalplaneresolutionunit.html
	*/
	FocalPlaneResolutionUnit((short)41488),

	/** 
		 Indicates the location of the main subject in the scene. (Hex: 0xA214)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/subjectlocation.html
	*/
	SubjectLocation((short)41492),

	/** 
		 Indicates the exposure index selected on the camera or input device at the time the image is captured. (Hex: 0xA215)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/exposureindex.html
	*/
	ExposureIndex((short)41493),

	/** 
		 Indicates the image sensor type on the camera or input device. (Hex: 0xA217)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/sensingmethod.html
	*/
	SensingMethod((short)41495),

	/** 
		 Indicates the image source. (Hex: 0xA300)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/filesource.html
	*/
	FileSource((short)41728),

	/** 
		 Indicates the type of scene. (Hex: 0xA301)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/scenetype.html
	*/
	SceneType((short)41729),

	/** 
		 Indicates the color filter array (CFA) geometric pattern of the image sensor when a one-chip color area sensor is used. (Hex: 0xA302)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/cfapattern.html
	*/
	CFAPattern2((short)41730),

	/** 
		 Indicates the use of special processing on image data, such as rendering geared to output. (Hex: 0xA401)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/customrendered.html
	*/
	CustomRendered((short)41985),

	/** 
		 Indicates the exposure mode set when the image was shot. (Hex: 0xA402)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/exposuremode.html
	*/
	ExposureMode((short)41986),

	/** 
		 Indicates the white balance mode set when the image was shot. (Hex: 0xA403)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/whitebalance.html
	*/
	WhiteBalance((short)41987),

	/** 
		 Indicates the digital zoom ratio when the image was shot. (Hex: 0xA404)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/digitalzoomratio.html
	*/
	DigitalZoomRatio((short)41988),

	/** 
		 Indicates the equivalent focal length assuming a 35mm film camera, in mm. (Hex: 0xA405)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/focallengthin35mmfilm.html
	*/
	FocalLengthIn35mmFilm((short)41989),

	/** 
		 Indicates the type of scene that was shot. (Hex: 0xA406)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/scenecapturetype.html
	*/
	SceneCaptureType((short)41990),

	/** 
		 Indicates the degree of overall image gain adjustment. (Hex: 0xA407)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/gaincontrol.html
	*/
	GainControl((short)41991),

	/** 
		 Indicates the direction of contrast processing applied by the camera when the image was shot. (Hex: 0xA408)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/contrast.html
	*/
	Contrast((short)41992),

	/** 
		 Indicates the direction of saturation processing applied by the camera when the image was shot. (Hex: 0xA409)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/saturation.html
	*/
	Saturation((short)41993),

	/** 
		 Indicates the direction of sharpness processing applied by the camera when the image was shot. (Hex: 0xA40A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/sharpness.html
	*/
	Sharpness((short)41994),

	/** 
		 This tag indicates information on the picture-taking conditions of a particular camera model. (Hex: 0xA40B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/devicesettingdescription.html
	*/
	DeviceSettingDescription((short)41995),

	/** 
		 Indicates the distance to the subject. (Hex: 0xA40C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/subjectdistancerange.html
	*/
	SubjectDistanceRange((short)41996),

	/** 
		 Indicates an identifier assigned uniquely to each image. (Hex: 0xA420)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/exif/imageuniqueid.html
	*/
	ImageUniqueID((short)42016);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, ExifEntryTag> mappings;
	private static java.util.HashMap<Short, ExifEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ExifEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, ExifEntryTag>();
				}
			}
		}
		return mappings;
	}

	private ExifEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static ExifEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}