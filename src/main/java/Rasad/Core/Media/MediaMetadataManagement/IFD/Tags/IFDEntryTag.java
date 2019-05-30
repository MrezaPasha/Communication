package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// IFDEntryTag.cs:
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
	Entry tags occuring in a Tiff IFD, or IFD0 for Jpegs. They are mostly
	defined by the TIFF specification:
	http: //partners.adobe.com/public/developer/en/tiff/TIFF6.pdf
	The complete overview can be obtained at:
	http: //www.awaresystems.be/imaging/tiff.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum IFDEntryTag : ushort
public enum IFDEntryTag 
{

	/** 
		 A general indication of the kind of data contained in this subfile. (Hex: 0x00FE)
		 http://www.awaresystems.be/imaging/tiff/tifftags/newsubfiletype.html
	*/
	NewSubfileType((short)254),

	/** 
		 A general indication of the kind of data contained in this subfile. (Hex: 0x00FF)
		 http://www.awaresystems.be/imaging/tiff/tifftags/subfiletype.html
	*/
	SubfileType((short)255),

	/** 
		 The number of columns in the image, i.e., the number of pixels per row. (Hex: 0x0100)
		 http://www.awaresystems.be/imaging/tiff/tifftags/imagewidth.html
	*/
	ImageWidth((short)256),

	/** 
		 The number of rows of pixels in the image. (Hex: 0x0101)
		 http://www.awaresystems.be/imaging/tiff/tifftags/imagelength.html
	*/
	ImageLength((short)257),

	/** 
		 Number of bits per component. (Hex: 0x0102)
		 http://www.awaresystems.be/imaging/tiff/tifftags/bitspersample.html
	*/
	BitsPerSample((short)258),

	/** 
		 Compression scheme used on the image data. (Hex: 0x0103)
		 http://www.awaresystems.be/imaging/tiff/tifftags/compression.html
	*/
	Compression((short)259),

	/** 
		 The color space of the image data. (Hex: 0x0106)
		 http://www.awaresystems.be/imaging/tiff/tifftags/photometricinterpretation.html
	*/
	PhotometricInterpretation((short)262),

	/** 
		 For black and white TIFF files that represent shades of gray, the technique used to convert from gray to black and white pixels. (Hex: 0x0107)
		 http://www.awaresystems.be/imaging/tiff/tifftags/threshholding.html
	*/
	Threshholding((short)263),

	/** 
		 The width of the dithering or halftoning matrix used to create a dithered or halftoned bilevel file. (Hex: 0x0108)
		 http://www.awaresystems.be/imaging/tiff/tifftags/cellwidth.html
	*/
	CellWidth((short)264),

	/** 
		 The length of the dithering or halftoning matrix used to create a dithered or halftoned bilevel file. (Hex: 0x0109)
		 http://www.awaresystems.be/imaging/tiff/tifftags/celllength.html
	*/
	CellLength((short)265),

	/** 
		 The logical order of bits within a byte. (Hex: 0x010A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/fillorder.html
	*/
	FillOrder((short)266),

	/** 
		 The name of the document from which this image was scanned. (Hex: 0x010D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/documentname.html
	*/
	DocumentName((short)269),

	/** 
		 A string that describes the subject of the image. (Hex: 0x010E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/imagedescription.html
	*/
	ImageDescription((short)270),

	/** 
		 The scanner manufacturer. (Hex: 0x010F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/make.html
	*/
	Make((short)271),

	/** 
		 The scanner model name or number. (Hex: 0x0110)
		 http://www.awaresystems.be/imaging/tiff/tifftags/model.html
	*/
	Model((short)272),

	/** 
		 For each strip, the byte offset of that strip. (Hex: 0x0111)
		 http://www.awaresystems.be/imaging/tiff/tifftags/stripoffsets.html
	*/
	StripOffsets((short)273),

	/** 
		 The orientation of the image with respect to the rows and columns. (Hex: 0x0112)
		 http://www.awaresystems.be/imaging/tiff/tifftags/orientation.html
	*/
	Orientation((short)274),

	/** 
		 The number of components per pixel. (Hex: 0x0115)
		 http://www.awaresystems.be/imaging/tiff/tifftags/samplesperpixel.html
	*/
	SamplesPerPixel((short)277),

	/** 
		 The number of rows per strip. (Hex: 0x0116)
		 http://www.awaresystems.be/imaging/tiff/tifftags/rowsperstrip.html
	*/
	RowsPerStrip((short)278),

	/** 
		 For each strip, the number of bytes in the strip after compression. (Hex: 0x0117)
		 http://www.awaresystems.be/imaging/tiff/tifftags/stripbytecounts.html
	*/
	StripByteCounts((short)279),

	/** 
		 The minimum component value used. (Hex: 0x0118)
		 http://www.awaresystems.be/imaging/tiff/tifftags/minsamplevalue.html
	*/
	MinSampleValue((short)280),

	/** 
		 The maximum component value used. (Hex: 0x0119)
		 http://www.awaresystems.be/imaging/tiff/tifftags/maxsamplevalue.html
	*/
	MaxSampleValue((short)281),

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
		 How the components of each pixel are stored. (Hex: 0x011C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/planarconfiguration.html
	*/
	PlanarConfiguration((short)284),

	/** 
		 The name of the page from which this image was scanned. (Hex: 0x011D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/pagename.html
	*/
	PageName((short)285),

	/** 
		 X position of the image. (Hex: 0x011E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/xposition.html
	*/
	XPosition((short)286),

	/** 
		 Y position of the image. (Hex: 0x011F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/yposition.html
	*/
	YPosition((short)287),

	/** 
		 For each string of contiguous unused bytes in a TIFF file, the byte offset of the string. (Hex: 0x0120)
		 http://www.awaresystems.be/imaging/tiff/tifftags/freeoffsets.html
	*/
	FreeOffsets((short)288),

	/** 
		 For each string of contiguous unused bytes in a TIFF file, the number of bytes in the string. (Hex: 0x0121)
		 http://www.awaresystems.be/imaging/tiff/tifftags/freebytecounts.html
	*/
	FreeByteCounts((short)289),

	/** 
		 The precision of the information contained in the GrayResponseCurve. (Hex: 0x0122)
		 http://www.awaresystems.be/imaging/tiff/tifftags/grayresponseunit.html
	*/
	GrayResponseUnit((short)290),

	/** 
		 For grayscale data, the optical density of each possible pixel value. (Hex: 0x0123)
		 http://www.awaresystems.be/imaging/tiff/tifftags/grayresponsecurve.html
	*/
	GrayResponseCurve((short)291),

	/** 
		 Options for Group 3 Fax compression (Hex: 0x0124)
		 http://www.awaresystems.be/imaging/tiff/tifftags/t4options.html
	*/
	T4Options((short)292),

	/** 
		 Options for Group 4 Fax compression (Hex: 0x0125)
		 http://www.awaresystems.be/imaging/tiff/tifftags/t6options.html
	*/
	T6Options((short)293),

	/** 
		 The unit of measurement for XResolution and YResolution. (Hex: 0x0128)
		 http://www.awaresystems.be/imaging/tiff/tifftags/resolutionunit.html
	*/
	ResolutionUnit((short)296),

	/** 
		 The page number of the page from which this image was scanned. (Hex: 0x0129)
		 http://www.awaresystems.be/imaging/tiff/tifftags/pagenumber.html
	*/
	PageNumber((short)297),

	/** 
		 Describes a transfer function for the image in tabular style. (Hex: 0x012D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/transferfunction.html
	*/
	TransferFunction((short)301),

	/** 
		 Name and version number of the software package(s) used to create the image. (Hex: 0x0131)
		 http://www.awaresystems.be/imaging/tiff/tifftags/software.html
	*/
	Software((short)305),

	/** 
		 Date and time of image creation. (Hex: 0x0132)
		 http://www.awaresystems.be/imaging/tiff/tifftags/datetime.html
	*/
	DateTime((short)306),

	/** 
		 Person who created the image. (Hex: 0x013B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/artist.html
	*/
	Artist((short)315),

	/** 
		 The computer and/or operating system in use at the time of image creation. (Hex: 0x013C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/hostcomputer.html
	*/
	HostComputer((short)316),

	/** 
		 A mathematical operator that is applied to the image data before an encoding scheme is applied. (Hex: 0x013D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/predictor.html
	*/
	Predictor((short)317),

	/** 
		 The chromaticity of the white point of the image. (Hex: 0x013E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/whitepoint.html
	*/
	WhitePoint((short)318),

	/** 
		 The chromaticities of the primaries of the image. (Hex: 0x013F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/primarychromaticities.html
	*/
	PrimaryChromaticities((short)319),

	/** 
		 A color map for palette color images. (Hex: 0x0140)
		 http://www.awaresystems.be/imaging/tiff/tifftags/colormap.html
	*/
	ColorMap((short)320),

	/** 
		 Conveys to the halftone function the range of gray levels within a colorimetrically-specified image that should retain tonal detail. (Hex: 0x0141)
		 http://www.awaresystems.be/imaging/tiff/tifftags/halftonehints.html
	*/
	HalftoneHints((short)321),

	/** 
		 The tile width in pixels. This is the number of columns in each tile. (Hex: 0x0142)
		 http://www.awaresystems.be/imaging/tiff/tifftags/tilewidth.html
	*/
	TileWidth((short)322),

	/** 
		 The tile length (height) in pixels. This is the number of rows in each tile. (Hex: 0x0143)
		 http://www.awaresystems.be/imaging/tiff/tifftags/tilelength.html
	*/
	TileLength((short)323),

	/** 
		 For each tile, the byte offset of that tile, as compressed and stored on disk. (Hex: 0x0144)
		 http://www.awaresystems.be/imaging/tiff/tifftags/tileoffsets.html
	*/
	TileOffsets((short)324),

	/** 
		 For each tile, the number of (compressed) bytes in that tile. (Hex: 0x0145)
		 http://www.awaresystems.be/imaging/tiff/tifftags/tilebytecounts.html
	*/
	TileByteCounts((short)325),

	/** 
		 Used in the TIFF-F standard, denotes the number of 'bad' scan lines encountered by the facsimile device. (Hex: 0x0146)
		 http://www.awaresystems.be/imaging/tiff/tifftags/badfaxlines.html
	*/
	BadFaxLines((short)326),

	/** 
		 Used in the TIFF-F standard, indicates if 'bad' lines encountered during reception are stored in the data, or if 'bad' lines have been replaced by the receiver. (Hex: 0x0147)
		 http://www.awaresystems.be/imaging/tiff/tifftags/cleanfaxdata.html
	*/
	CleanFaxData((short)327),

	/** 
		 Used in the TIFF-F standard, denotes the maximum number of consecutive 'bad' scanlines received. (Hex: 0x0148)
		 http://www.awaresystems.be/imaging/tiff/tifftags/consecutivebadfaxlines.html
	*/
	ConsecutiveBadFaxLines((short)328),

	/** 
		 Offset to child IFDs. (Hex: 0x014A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/subifds.html
	*/
	SubIFDs((short)330),

	/** 
		 The set of inks used in a separated (PhotometricInterpretation=5) image. (Hex: 0x014C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/inkset.html
	*/
	InkSet((short)332),

	/** 
		 The name of each ink used in a separated image. (Hex: 0x014D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/inknames.html
	*/
	InkNames((short)333),

	/** 
		 The number of inks. (Hex: 0x014E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/numberofinks.html
	*/
	NumberOfInks((short)334),

	/** 
		 The component values that correspond to a 0% dot and 100% dot. (Hex: 0x0150)
		 http://www.awaresystems.be/imaging/tiff/tifftags/dotrange.html
	*/
	DotRange((short)336),

	/** 
		 A description of the printing environment for which this separation is intended. (Hex: 0x0151)
		 http://www.awaresystems.be/imaging/tiff/tifftags/targetprinter.html
	*/
	TargetPrinter((short)337),

	/** 
		 Description of extra components. (Hex: 0x0152)
		 http://www.awaresystems.be/imaging/tiff/tifftags/extrasamples.html
	*/
	ExtraSamples((short)338),

	/** 
		 Specifies how to interpret each data sample in a pixel. (Hex: 0x0153)
		 http://www.awaresystems.be/imaging/tiff/tifftags/sampleformat.html
	*/
	SampleFormat((short)339),

	/** 
		 Specifies the minimum sample value. (Hex: 0x0154)
		 http://www.awaresystems.be/imaging/tiff/tifftags/sminsamplevalue.html
	*/
	SMinSampleValue((short)340),

	/** 
		 Specifies the maximum sample value. (Hex: 0x0155)
		 http://www.awaresystems.be/imaging/tiff/tifftags/smaxsamplevalue.html
	*/
	SMaxSampleValue((short)341),

	/** 
		 Expands the range of the TransferFunction. (Hex: 0x0156)
		 http://www.awaresystems.be/imaging/tiff/tifftags/transferrange.html
	*/
	TransferRange((short)342),

	/** 
		 Mirrors the essentials of PostScript's path creation functionality. (Hex: 0x0157)
		 http://www.awaresystems.be/imaging/tiff/tifftags/clippath.html
	*/
	ClipPath((short)343),

	/** 
		 The number of units that span the width of the image, in terms of integer ClipPath coordinates. (Hex: 0x0158)
		 http://www.awaresystems.be/imaging/tiff/tifftags/xclippathunits.html
	*/
	XClipPathUnits((short)344),

	/** 
		 The number of units that span the height of the image, in terms of integer ClipPath coordinates. (Hex: 0x0159)
		 http://www.awaresystems.be/imaging/tiff/tifftags/yclippathunits.html
	*/
	YClipPathUnits((short)345),

	/** 
		 Aims to broaden the support for indexed images to include support for any color space. (Hex: 0x015A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/indexed.html
	*/
	Indexed((short)346),

	/** 
		 JPEG quantization and/or Huffman tables. (Hex: 0x015B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpegtables.html
	*/
	JPEGTables((short)347),

	/** 
		 OPI-related. (Hex: 0x015F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/opiproxy.html
	*/
	OPIProxy((short)351),

	/** 
		 Used in the TIFF-FX standard to point to an IFD containing tags that are globally applicable to the complete TIFF file. (Hex: 0x0190)
		 http://www.awaresystems.be/imaging/tiff/tifftags/globalparametersifd.html
	*/
	GlobalParametersIFD((short)400),

	/** 
		 Used in the TIFF-FX standard, denotes the type of data stored in this file or IFD. (Hex: 0x0191)
		 http://www.awaresystems.be/imaging/tiff/tifftags/profiletype.html
	*/
	ProfileType((short)401),

	/** 
		 Used in the TIFF-FX standard, denotes the 'profile' that applies to this file. (Hex: 0x0192)
		 http://www.awaresystems.be/imaging/tiff/tifftags/faxprofile.html
	*/
	FaxProfile((short)402),

	/** 
		 Used in the TIFF-FX standard, indicates which coding methods are used in the file. (Hex: 0x0193)
		 http://www.awaresystems.be/imaging/tiff/tifftags/codingmethods.html
	*/
	CodingMethods((short)403),

	/** 
		 Used in the TIFF-FX standard, denotes the year of the standard specified by the FaxProfile field. (Hex: 0x0194)
		 http://www.awaresystems.be/imaging/tiff/tifftags/versionyear.html
	*/
	VersionYear((short)404),

	/** 
		 Used in the TIFF-FX standard, denotes the mode of the standard specified by the FaxProfile field. (Hex: 0x0195)
		 http://www.awaresystems.be/imaging/tiff/tifftags/modenumber.html
	*/
	ModeNumber((short)405),

	/** 
		 Used in the TIFF-F and TIFF-FX standards, holds information about the ITULAB (PhotometricInterpretation = 10) encoding. (Hex: 0x01B1)
		 http://www.awaresystems.be/imaging/tiff/tifftags/decode.html
	*/
	Decode((short)433),

	/** 
		 Defined in the Mixed Raster Content part of RFC 2301, is the default color needed in areas where no image is available. (Hex: 0x01B2)
		 http://www.awaresystems.be/imaging/tiff/tifftags/defaultimagecolor.html
	*/
	DefaultImageColor((short)434),

	/** 
		 Old-style JPEG compression field. TechNote2 invalidates this part of the specification. (Hex: 0x0200)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpegproc.html
	*/
	JPEGProc((short)512),

	/** 
		 Old-style JPEG compression field. TechNote2 invalidates this part of the specification. (Hex: 0x0201)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpeginterchangeformat.html
	*/
	JPEGInterchangeFormat((short)513),

	/** 
		 Old-style JPEG compression field. TechNote2 invalidates this part of the specification. (Hex: 0x0202)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpeginterchangeformatlength.html
	*/
	JPEGInterchangeFormatLength((short)514),

	/** 
		 Old-style JPEG compression field. TechNote2 invalidates this part of the specification. (Hex: 0x0203)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpegrestartinterval.html
	*/
	JPEGRestartInterval((short)515),

	/** 
		 Old-style JPEG compression field. TechNote2 invalidates this part of the specification. (Hex: 0x0205)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpeglosslesspredictors.html
	*/
	JPEGLosslessPredictors((short)517),

	/** 
		 Old-style JPEG compression field. TechNote2 invalidates this part of the specification. (Hex: 0x0206)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpegpointtransforms.html
	*/
	JPEGPointTransforms((short)518),

	/** 
		 Old-style JPEG compression field. TechNote2 invalidates this part of the specification. (Hex: 0x0207)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpegqtables.html
	*/
	JPEGQTables((short)519),

	/** 
		 Old-style JPEG compression field. TechNote2 invalidates this part of the specification. (Hex: 0x0208)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpegdctables.html
	*/
	JPEGDCTables((short)520),

	/** 
		 Old-style JPEG compression field. TechNote2 invalidates this part of the specification. (Hex: 0x0209)
		 http://www.awaresystems.be/imaging/tiff/tifftags/jpegactables.html
	*/
	JPEGACTables((short)521),

	/** 
		 The transformation from RGB to YCbCr image data. (Hex: 0x0211)
		 http://www.awaresystems.be/imaging/tiff/tifftags/ycbcrcoefficients.html
	*/
	YCbCrCoefficients((short)529),

	/** 
		 Specifies the subsampling factors used for the chrominance components of a YCbCr image. (Hex: 0x0212)
		 http://www.awaresystems.be/imaging/tiff/tifftags/ycbcrsubsampling.html
	*/
	YCbCrSubSampling((short)530),

	/** 
		 Specifies the positioning of subsampled chrominance components relative to luminance samples. (Hex: 0x0213)
		 http://www.awaresystems.be/imaging/tiff/tifftags/ycbcrpositioning.html
	*/
	YCbCrPositioning((short)531),

	/** 
		 Specifies a pair of headroom and footroom image data values (codes) for each pixel component. (Hex: 0x0214)
		 http://www.awaresystems.be/imaging/tiff/tifftags/referenceblackwhite.html
	*/
	ReferenceBlackWhite((short)532),

	/** 
		 Defined in the Mixed Raster Content part of RFC 2301, used to replace RowsPerStrip for IFDs with variable-sized strips. (Hex: 0x022F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/striprowcounts.html
	*/
	StripRowCounts((short)559),

	/** 
		 XML packet containing XMP metadata (Hex: 0x02BC)
		 http://www.awaresystems.be/imaging/tiff/tifftags/xmp.html
	*/
	XMP((short)700),

	/** 
		 Rating tag used by Windows (Hex: 0x4746)
	*/
	Rating((short)18246),

	/** 
		 Rating tag used by Windows, value in percent (Hex: 0x4749)
	*/
	RatingPercent((short)18249),

	/** 
		 OPI-related. (Hex: 0x800D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/imageid.html
	*/
	ImageID((short)32781),

	/** 
		 Annotation data, as used in 'Imaging for Windows'. (Hex: 0x80A4)
		 http://www.awaresystems.be/imaging/tiff/tifftags/wangannotation.html
	*/
	WangAnnotation((short)32932),

	/** 
		 Copyright notice. (Hex: 0x8298)
		 http://www.awaresystems.be/imaging/tiff/tifftags/copyright.html
	*/
	Copyright((short)33432),

	/** 
		 Specifies the pixel data format encoding in the Molecular Dynamics GEL file format. (Hex: 0x82A5)
		 http://www.awaresystems.be/imaging/tiff/tifftags/mdfiletag.html
	*/
	MDFileTag((short)33445),

	/** 
		 Specifies a scale factor in the Molecular Dynamics GEL file format. (Hex: 0x82A6)
		 http://www.awaresystems.be/imaging/tiff/tifftags/mdscalepixel.html
	*/
	MDScalePixel((short)33446),

	/** 
		 Used to specify the conversion from 16bit to 8bit in the Molecular Dynamics GEL file format. (Hex: 0x82A7)
		 http://www.awaresystems.be/imaging/tiff/tifftags/mdcolortable.html
	*/
	MDColorTable((short)33447),

	/** 
		 Name of the lab that scanned this file, as used in the Molecular Dynamics GEL file format. (Hex: 0x82A8)
		 http://www.awaresystems.be/imaging/tiff/tifftags/mdlabname.html
	*/
	MDLabName((short)33448),

	/** 
		 Information about the sample, as used in the Molecular Dynamics GEL file format. (Hex: 0x82A9)
		 http://www.awaresystems.be/imaging/tiff/tifftags/mdsampleinfo.html
	*/
	MDSampleInfo((short)33449),

	/** 
		 Date the sample was prepared, as used in the Molecular Dynamics GEL file format. (Hex: 0x82AA)
		 http://www.awaresystems.be/imaging/tiff/tifftags/mdprepdate.html
	*/
	MDPrepDate((short)33450),

	/** 
		 Time the sample was prepared, as used in the Molecular Dynamics GEL file format. (Hex: 0x82AB)
		 http://www.awaresystems.be/imaging/tiff/tifftags/mdpreptime.html
	*/
	MDPrepTime((short)33451),

	/** 
		 Units for data in this file, as used in the Molecular Dynamics GEL file format. (Hex: 0x82AC)
		 http://www.awaresystems.be/imaging/tiff/tifftags/mdfileunits.html
	*/
	MDFileUnits((short)33452),

	/** 
		 Used in interchangeable GeoTIFF files. (Hex: 0x830E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/modelpixelscaletag.html
	*/
	ModelPixelScaleTag((short)33550),

	/** 
		 IPTC (International Press Telecommunications Council) metadata. (Hex: 0x83BB)
		 http://www.awaresystems.be/imaging/tiff/tifftags/iptc.html
	*/
	IPTC((short)33723),

	/** 
		 Intergraph Application specific storage. (Hex: 0x847E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/ingrpacketdatatag.html
	*/
	INGRPacketDataTag((short)33918),

	/** 
		 Intergraph Application specific flags. (Hex: 0x847F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/ingrflagregisters.html
	*/
	INGRFlagRegisters((short)33919),

	/** 
		 Originally part of Intergraph's GeoTIFF tags, but likely understood by IrasB only. (Hex: 0x8480)
		 http://www.awaresystems.be/imaging/tiff/tifftags/irasbtransformationmatrix.html
	*/
	IrasBTransformationMatrix((short)33920),

	/** 
		 Originally part of Intergraph's GeoTIFF tags, but now used in interchangeable GeoTIFF files. (Hex: 0x8482)
		 http://www.awaresystems.be/imaging/tiff/tifftags/modeltiepointtag.html
	*/
	ModelTiepointTag((short)33922),

	/** 
		 Used in interchangeable GeoTIFF files. (Hex: 0x85D8)
		 http://www.awaresystems.be/imaging/tiff/tifftags/modeltransformationtag.html
	*/
	ModelTransformationTag((short)34264),

	/** 
		 Collection of Photoshop 'Image Resource Blocks'. (Hex: 0x8649)
		 http://www.awaresystems.be/imaging/tiff/tifftags/photoshop.html
	*/
	Photoshop((short)34377),

	/** 
		 A pointer to the Exif IFD. (Hex: 0x8769)
		 http://www.awaresystems.be/imaging/tiff/tifftags/exififd.html
	*/
	ExifIFD((short)34665),

	/** 
		 ICC profile data. (Hex: 0x8773)
		 http://www.awaresystems.be/imaging/tiff/tifftags/iccprofile.html
	*/
	ICCProfile((short)34675),

	/** 
		 Defined in the Mixed Raster Content part of RFC 2301, used to denote the particular function of this Image in the mixed raster scheme. (Hex: 0x87AC)
		 http://www.awaresystems.be/imaging/tiff/tifftags/imagelayer.html
	*/
	ImageLayer((short)34732),

	/** 
		 Used in interchangeable GeoTIFF files. (Hex: 0x87AF)
		 http://www.awaresystems.be/imaging/tiff/tifftags/geokeydirectorytag.html
	*/
	GeoKeyDirectoryTag((short)34735),

	/** 
		 Used in interchangeable GeoTIFF files. (Hex: 0x87B0)
		 http://www.awaresystems.be/imaging/tiff/tifftags/geodoubleparamstag.html
	*/
	GeoDoubleParamsTag((short)34736),

	/** 
		 Used in interchangeable GeoTIFF files. (Hex: 0x87B1)
		 http://www.awaresystems.be/imaging/tiff/tifftags/geoasciiparamstag.html
	*/
	GeoAsciiParamsTag((short)34737),

	/** 
		 A pointer to the Exif-related GPS Info IFD. (Hex: 0x8825)
		 http://www.awaresystems.be/imaging/tiff/tifftags/gpsifd.html
	*/
	GPSIFD((short)34853),

	/** 
		 Used by HylaFAX. (Hex: 0x885C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/hylafaxfaxrecvparams.html
	*/
	HylaFAXFaxRecvParams((short)34908),

	/** 
		 Used by HylaFAX. (Hex: 0x885D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/hylafaxfaxsubaddress.html
	*/
	HylaFAXFaxSubAddress((short)34909),

	/** 
		 Used by HylaFAX. (Hex: 0x885E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/hylafaxfaxrecvtime.html
	*/
	HylaFAXFaxRecvTime((short)34910),

	/** 
		 Used by Adobe Photoshop. (Hex: 0x935C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/imagesourcedata.html
	*/
	ImageSourceData((short)37724),

	/** 
		 A pointer to the Exif-related Interoperability IFD. (Hex: 0xA005)
		 http://www.awaresystems.be/imaging/tiff/tifftags/interoperabilityifd.html
	*/
	InteroperabilityIFD((short)40965),

	/** 
		 Used by the GDAL library, holds an XML list of name=value 'metadata' values about the image as a whole, and about specific samples. (Hex: 0xA480)
		 http://www.awaresystems.be/imaging/tiff/tifftags/gdal_metadata.html
	*/
	GDAL_METADATA((short)42112),

	/** 
		 Used by the GDAL library, contains an ASCII encoded nodata or background pixel value. (Hex: 0xA481)
		 http://www.awaresystems.be/imaging/tiff/tifftags/gdal_nodata.html
	*/
	GDAL_NODATA((short)42113),

	/** 
		 Used in the Oce scanning process. (Hex: 0xC427)
		 http://www.awaresystems.be/imaging/tiff/tifftags/ocescanjobdescription.html
	*/
	OceScanjobDescription((short)50215),

	/** 
		 Used in the Oce scanning process. (Hex: 0xC428)
		 http://www.awaresystems.be/imaging/tiff/tifftags/oceapplicationselector.html
	*/
	OceApplicationSelector((short)50216),

	/** 
		 Used in the Oce scanning process. (Hex: 0xC429)
		 http://www.awaresystems.be/imaging/tiff/tifftags/oceidentificationnumber.html
	*/
	OceIdentificationNumber((short)50217),

	/** 
		 Used in the Oce scanning process. (Hex: 0xC42A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/oceimagelogiccharacteristics.html
	*/
	OceImageLogicCharacteristics((short)50218),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC612)
		 http://www.awaresystems.be/imaging/tiff/tifftags/dngversion.html
	*/
	DNGVersion((short)50706),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC613)
		 http://www.awaresystems.be/imaging/tiff/tifftags/dngbackwardversion.html
	*/
	DNGBackwardVersion((short)50707),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC614)
		 http://www.awaresystems.be/imaging/tiff/tifftags/uniquecameramodel.html
	*/
	UniqueCameraModel((short)50708),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC615)
		 http://www.awaresystems.be/imaging/tiff/tifftags/localizedcameramodel.html
	*/
	LocalizedCameraModel((short)50709),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC616)
		 http://www.awaresystems.be/imaging/tiff/tifftags/cfaplanecolor.html
	*/
	CFAPlaneColor((short)50710),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC617)
		 http://www.awaresystems.be/imaging/tiff/tifftags/cfalayout.html
	*/
	CFALayout((short)50711),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC618)
		 http://www.awaresystems.be/imaging/tiff/tifftags/linearizationtable.html
	*/
	LinearizationTable((short)50712),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC619)
		 http://www.awaresystems.be/imaging/tiff/tifftags/blacklevelrepeatdim.html
	*/
	BlackLevelRepeatDim((short)50713),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC61A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/blacklevel.html
	*/
	BlackLevel((short)50714),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC61B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/blackleveldeltah.html
	*/
	BlackLevelDeltaH((short)50715),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC61C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/blackleveldeltav.html
	*/
	BlackLevelDeltaV((short)50716),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC61D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/whitelevel.html
	*/
	WhiteLevel((short)50717),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC61E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/defaultscale.html
	*/
	DefaultScale((short)50718),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC61F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/defaultcroporigin.html
	*/
	DefaultCropOrigin((short)50719),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC620)
		 http://www.awaresystems.be/imaging/tiff/tifftags/defaultcropsize.html
	*/
	DefaultCropSize((short)50720),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC621)
		 http://www.awaresystems.be/imaging/tiff/tifftags/colormatrix1.html
	*/
	ColorMatrix1((short)50721),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC622)
		 http://www.awaresystems.be/imaging/tiff/tifftags/colormatrix2.html
	*/
	ColorMatrix2((short)50722),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC623)
		 http://www.awaresystems.be/imaging/tiff/tifftags/cameracalibration1.html
	*/
	CameraCalibration1((short)50723),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC624)
		 http://www.awaresystems.be/imaging/tiff/tifftags/cameracalibration2.html
	*/
	CameraCalibration2((short)50724),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC625)
		 http://www.awaresystems.be/imaging/tiff/tifftags/reductionmatrix1.html
	*/
	ReductionMatrix1((short)50725),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC626)
		 http://www.awaresystems.be/imaging/tiff/tifftags/reductionmatrix2.html
	*/
	ReductionMatrix2((short)50726),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC627)
		 http://www.awaresystems.be/imaging/tiff/tifftags/analogbalance.html
	*/
	AnalogBalance((short)50727),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC628)
		 http://www.awaresystems.be/imaging/tiff/tifftags/asshotneutral.html
	*/
	AsShotNeutral((short)50728),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC629)
		 http://www.awaresystems.be/imaging/tiff/tifftags/asshotwhitexy.html
	*/
	AsShotWhiteXY((short)50729),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC62A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/baselineexposure.html
	*/
	BaselineExposure((short)50730),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC62B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/baselinenoise.html
	*/
	BaselineNoise((short)50731),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC62C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/baselinesharpness.html
	*/
	BaselineSharpness((short)50732),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC62D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/bayergreensplit.html
	*/
	BayerGreenSplit((short)50733),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC62E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/linearresponselimit.html
	*/
	LinearResponseLimit((short)50734),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC62F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/cameraserialnumber.html
	*/
	CameraSerialNumber((short)50735),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC630)
		 http://www.awaresystems.be/imaging/tiff/tifftags/lensinfo.html
	*/
	LensInfo((short)50736),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC631)
		 http://www.awaresystems.be/imaging/tiff/tifftags/chromablurradius.html
	*/
	ChromaBlurRadius((short)50737),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC632)
		 http://www.awaresystems.be/imaging/tiff/tifftags/antialiasstrength.html
	*/
	AntiAliasStrength((short)50738),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC634)
		 http://www.awaresystems.be/imaging/tiff/tifftags/dngprivatedata.html
	*/
	DNGPrivateData((short)50740),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC635)
		 http://www.awaresystems.be/imaging/tiff/tifftags/makernotesafety.html
	*/
	MakerNoteSafety((short)50741),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC65A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/calibrationilluminant1.html
	*/
	CalibrationIlluminant1((short)50778),

	/** 
		 Used in IFD 0 of DNG files. (Hex: 0xC65B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/calibrationilluminant2.html
	*/
	CalibrationIlluminant2((short)50779),

	/** 
		 Used in Raw IFD of DNG files. (Hex: 0xC65C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/bestqualityscale.html
	*/
	BestQualityScale((short)50780),

	/** 
		 Alias Sketchbook Pro layer usage description. (Hex: 0xC660)
		 http://www.awaresystems.be/imaging/tiff/tifftags/aliaslayermetadata.html
	*/
	AliasLayerMetadata((short)50784);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, IFDEntryTag> mappings;
	private static java.util.HashMap<Short, IFDEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (IFDEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, IFDEntryTag>();
				}
			}
		}
		return mappings;
	}

	private IFDEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static IFDEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}