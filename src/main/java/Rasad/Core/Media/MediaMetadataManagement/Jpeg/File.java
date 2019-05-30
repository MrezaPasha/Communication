package Rasad.Core.Media.MediaMetadataManagement.Jpeg;

import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.Media.MediaMetadataManagement.Xmp.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// File.cs: Provides tagging for Jpeg files
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//   Stephane Delcroix (stephane@delcroix.org)
//
// Copyright (C) 2009 Ruben Vermeersch
// Copyright (C) 2009 Mike Gemuende
// Copyright (c) 2009 Stephane Delcroix
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Image.ImageBlockFile" /> to provide tagging
	and properties support for Jpeg files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/jpg", "jpg")][SupportedMimeType("taglib/jpeg", "jpeg")][SupportedMimeType("taglib/jpe", "jpe")][SupportedMimeType("taglib/jif", "jif")][SupportedMimeType("taglib/jfif", "jfif")][SupportedMimeType("taglib/jfi", "jfi")][SupportedMimeType("image/jpeg")] public class File : Rasad.Core.Media.MediaMetadataManagement.Image.ImageBlockFile
public class File extends Rasad.Core.Media.MediaMetadataManagement.Image.ImageBlockFile
{

	/** 
		The magic bits used to recognize an Exif segment
	*/
	private static final String EXIF_IDENTIFIER = "Exif\0\0";

	/** 
	 The magic strings used to identifiy an IPTC-IIM section
	*/
	private static final String IPTC_IIM_IDENTIFIER = "Photoshop 3.0\u00008BIM\u0004\u0004";

	/** 
		Standard (empty) JFIF header to add, if no one is contained
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static readonly byte [] BASIC_JFIF_HEADER = new byte [] { 0xFF, (byte) Marker.APP0, 0x00, 0x10, 0x4A, 0x46, 0x49, 0x46, 0x00, 0x01, 0x01, 0x01, 0x00, 0x48, 0x00, 0x48, 0x00, 0x00 };
	private static final byte [] BASIC_JFIF_HEADER = new byte [] {(byte)0xFF, (byte) Marker.APP0.getValue(), 0x00, 0x10, 0x4A, 0x46, 0x49, 0x46, 0x00, 0x01, 0x01, 0x01, 0x00, 0x48, 0x00, 0x48, 0x00, 0x00};


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the media properties.
	*/
	private Properties properties;

	/** 
		For now, we do not allow to change the jfif header. As long as this is
		the case, the header is kept as it is.
	*/
	private ByteVector jfif_header = null;

	/** 
		The image width, as parsed from the Frame
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort width;
	private short width;

	/** 
		The image height, as parsed from the Frame
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort height;
	private short height;

	/** 
		Quality of the image, stored as we parse the file
	*/
	private int quality;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system and specified read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public File(String path, ReadStyle propertiesStyle)
	{
		this(new File.LocalFileAbstraction(path), propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public File(String path)
	{
		this(path, ReadStyle.Average);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction and
		specified read style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public File(File.IFileAbstraction abstraction, ReadStyle propertiesStyle)
	{
		super(abstraction);
		Read(propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	protected File(IFileAbstraction abstraction)
	{
		this(abstraction, ReadStyle.Average);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets the media properties of the file represented by the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Properties" /> object containing the
		media properties of the file represented by the current
		instance.
	 </value>
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Properties getProperties()
	{
		return properties;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
	  Gets a tag of a specified type from the current instance, optionally creating a
	 new tag if possible.
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag GetTag(Rasad.Core.Media.MediaMetadataManagement.TagTypes type, boolean create)
	{
		if (type == TagTypes.XMP)
		{
			for (Tag tag : getImageTag().getAllTags())
			{
				if ((tag.getTagTypes().getValue() & type.getValue()) == type.getValue() || (tag.getTagTypes().getValue() & TagTypes.IPTCIIM.getValue()) != 0)
				{
					return tag;
				}
			}
		}
		if (type == TagTypes.IPTCIIM && create)
		{
			// FIXME: don't know how to create IPTCIIM tags
			return super.GetTag(type, false);
		}

		return super.GetTag(type, create);
	}

	/** 
		Saves the changes made in the current instance to the
		file it represents.
	*/
	@Override
	public void Save()
	{
		if (!Writeable || PossiblyCorrupt)
		{
			throw new IllegalStateException("File not writeable. Corrupt metadata?");
		}

		Mode = AccessMode.Write;
		try
		{
			WriteMetadata();

			TagTypesOnDisk = TagTypes;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	/** 
		Reads the information from file with a specified read style.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	private void Read(ReadStyle propertiesStyle)
	{
		Mode = AccessMode.Read;
		try
		{
			setImageTag(new CombinedImageTag(TagTypes.XMP.getValue() | TagTypes.TiffIFD.getValue() | TagTypes.JpegComment.getValue() | TagTypes.IPTCIIM.getValue()));

			ValidateHeader();
			ReadMetadata();

			TagTypesOnDisk = TagTypes;

			if (propertiesStyle != ReadStyle.None)
			{
				properties = ExtractProperties();
			}

		}
		finally
		{
			Mode = AccessMode.Closed;
		}
	}

	/** 
		Attempts to extract the media properties of the main
		photo.
	 
	 @return 
		A <see cref="Properties" /> object with a best effort guess
		at the right values. When no guess at all can be made,
		<see langword="null" /> is returned.
	 
	*/
	private Properties ExtractProperties()
	{
		if (width > 0 && height > 0)
		{
			return new Properties(TimeSpan.Zero, new Codec(width, height, quality));
		}

		return null;

	}

	/** 
		Validates if the opened file is actually a JPEG.
	*/
	private void ValidateHeader()
	{
		ByteVector segment = ReadBlock(2);
		if (segment.ToUShort() != 0xFFD8)
		{
			throw new CorruptFileException("Expected SOI marker at the start of the file.");
		}
	}


	/** 
		Reads a segment marker for a segment starting at current position.
		The second byte of the marker is returned, since the first is equal
		to 0xFF in every case.
	 
	 @return 
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Jpeg.Marker"/> with the second byte of the segment marker.
	 
	*/
	private Marker ReadSegmentMarker()
	{
		ByteVector segment_header = ReadBlock(2);

		if (segment_header.size() != 2)
		{
			throw new CorruptFileException("Could not read enough bytes for segment maker");
		}

		if (segment_header.get(0) != 0xFF)
		{
			throw new CorruptFileException("Start of Segment expected at " + (Tell - 2));
		}

		return Marker.forValue(segment_header.get(1));
	}


	/** 
		Reads the size of a segment at the current position.
	 
	 @return 
		A <see cref="System.UInt16"/> with the size of the current segment.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort ReadSegmentSize()
	private short ReadSegmentSize()
	{
		long position = Tell;

		ByteVector segment_size_bytes = ReadBlock(2);

		if (segment_size_bytes.size() != 2)
		{
			throw new CorruptFileException("Could not read enough bytes to determine segment size");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort segment_size = segment_size_bytes.ToUShort();
		short segment_size = segment_size_bytes.ToUShort();

		// the size itself must be contained in the segment size
		// so the smallest (theoretically) possible number of bytes if 2
		if (segment_size < 2)
		{
			throw new CorruptFileException(String.format("Invalid segment size (%1$s bytes)", segment_size));
		}

		long length = 0;
		try
		{
			length = Length;
		}
		catch (RuntimeException e)
		{
			// Probably not supported by stream.
		}

		if (length > 0 && position + segment_size >= length)
		{
			throw new CorruptFileException("Segment size exceeds file size");
		}

		return segment_size;
	}


	/** 
		Extracts the metadata from the current file by reading every segment in file.
		Method should be called with read position at first segment marker.
	*/
	private void ReadMetadata()
	{
		// loop while marker is not EOI and not the data segment
		while (true)
		{
			Marker marker = ReadSegmentMarker();

			// we stop parsing when the end of file (EOI) or the begin of the
			// data segment is reached (SOS)
			// the second case is a trade-off between tolerant and fast parsing
			if (marker == Marker.EOI || marker == Marker.SOS)
			{
				break;
			}

			long position = Tell;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort segment_size = ReadSegmentSize();
			short segment_size = ReadSegmentSize();

			// segment size contains 2 bytes of the size itself, so the
			// pure data size is this (and the cast is save)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort data_size = (ushort)(segment_size - 2);
			short data_size = (short)(segment_size - 2);

			switch (marker)
			{
			case APP0: // possibly JFIF header
				ReadJFIFHeader(data_size);
				break;

			case APP1: // possibly Exif or Xmp data found
				ReadAPP1Segment(data_size);
				break;

			case APP13: // possibly IPTC-IIM
				ReadAPP13Segment(data_size);
				break;

			case COM: // Comment segment found
				ReadCOMSegment(data_size);
				break;

			case SOF0:
			case SOF1:
			case SOF2:
			case SOF3:
			case SOF9:
			case SOF10:
			case SOF11:
				ReadSOFSegment(data_size, marker);
				break;

			case DQT: // Quantization table(s), use it to guess quality
				ReadDQTSegment(data_size);
				break;
			}

			// set position to next segment and start with next segment marker
			Seek(position + segment_size, SeekOrigin.Begin);
		}
	}

	/** 
		Reads a JFIF header at current position
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void ReadJFIFHeader(ushort length)
	private void ReadJFIFHeader(short length)
	{
		// JFIF header should be contained as first segment
		// SOI marker + APP0 Marker + segment size = 6 bytes
		if (Tell != 6)
		{
			return;
		}

		if (ReadBlock(5).toString().equals("JFIF\0"))
		{

			// store the JFIF header as it is
			Seek(2, SeekOrigin.Begin);
			jfif_header = ReadBlock(length + 2 + 2);

			AddMetadataBlock(2, length + 2 + 2);
		}

	}

	/** 
		Reads an APP1 segment to find EXIF or XMP metadata.
	 
	 @param length
		The length of the segment that will be read.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void ReadAPP1Segment(ushort length)
	private void ReadAPP1Segment(short length)
	{
		long position = Tell;
		ByteVector data = null;

		// for an Exif segment, the data block consists of 14 bytes of:
		//    * 6 bytes Exif identifier string
		//    * 2 bytes bigendian indication MM (or II)
		//    * 2 bytes Tiff magic number (42)
		//    * 4 bytes offset of the first IFD in this segment
		//
		//    the last two points are alreay encoded according to
		//    big- or littleendian
		int exif_header_length = 14;

		// could be an Exif segment
		if ((getImageTag().getTagTypes().getValue() & Rasad.Core.Media.MediaMetadataManagement.TagTypes.TiffIFD.getValue()) == 0x00 && length >= exif_header_length)
		{

			data = ReadBlock(exif_header_length);

			if (data.size() == exif_header_length && data.Mid(0, 6).toString().equals(EXIF_IDENTIFIER))
			{

				boolean is_bigendian = data.Mid(6, 2).toString().equals("MM");

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort magic = data.Mid(8, 2).ToUShort(is_bigendian);
				short magic = data.Mid(8, 2).ToUShort(is_bigendian);
				if (magic != 42)
				{
					throw new RuntimeException(String.format("Invalid TIFF magic: %1$s", magic));
				}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint ifd_offset = data.Mid(10, 4).ToUInt(is_bigendian);
				int ifd_offset = data.Mid(10, 4).ToUInt(is_bigendian);

				IFDTag exif = new IFDTag();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var reader = new IFDReader(this, is_bigendian, exif.Structure, position + 6, ifd_offset, (uint)(length - 6));
				IFDReader reader = new IFDReader(this, is_bigendian, exif.getStructure(), position + 6, ifd_offset, (int)(length - 6));
				reader.Read();
				getImageTag().AddTag(exif);

				AddMetadataBlock(position - 4, length + 4);

				return;
			}
		}

		int xmp_header_length = XmpTag.XAP_NS.length() + 1;

		// could be an Xmp segment
		if ((getImageTag().getTagTypes().getValue() & Rasad.Core.Media.MediaMetadataManagement.TagTypes.XMP.getValue()) == 0x00 && length >= xmp_header_length)
		{

			// if already data is read for determining the Exif segment,
			// just read the remaining bytes.
			// NOTE: that (exif_header_length < xmp_header_length) holds
			if (data == null)
			{
				data = ReadBlock(xmp_header_length);
			}
			else
			{
				data.add(ReadBlock(xmp_header_length - exif_header_length));
			}

			if (data.toString().equals(XmpTag.XAP_NS + "\0"))
			{
				ByteVector xmp_data = ReadBlock(length - xmp_header_length);

				getImageTag().AddTag(new XmpTag(xmp_data.toString(), this));

				AddMetadataBlock(position - 4, length + 4);
			}
		}
	}

	/** 
		Reads an APP13 segment to find IPTC-IIM metadata.
	 
	 @param length
		The length of the segment that will be read.
	 
	 More info and specs for IPTC-IIM:
	 - Guidelines for Handling Image Metadata (http://www.metadataworkinggroup.org/specs/)
	 - IPTC Standard Photo Metadata (July 2010) (http://www.iptc.org/std/photometadata/specification/IPTC-PhotoMetadata-201007_1.pdf)
	 - Extracting IPTC header information from JPEG images (http://www.codeproject.com/KB/graphics/iptc.aspx?fid=2301&df=90&mpp=25&noise=3&prof=False&sort=Position&view=Quick&fr=51#xx0xx)
	 - Reading IPTC APP14 Segment Header Information from JPEG Images (http://www.codeproject.com/KB/graphics/ReadingIPTCAPP14.aspx?q=iptc)
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void ReadAPP13Segment(ushort length)
	private void ReadAPP13Segment(short length)
	{
		// TODO: if both IPTC-IIM and XMP metadata is contained in a file, we should read
		// a IPTC-IIM checksum and compare that with the checksum built over the IIM block.
		// Depending on the result we should prefer the information from XMP or IIM.
		// Right now we always prefer XMP.

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var data = ReadBlock(length);

		// The APP13 segment consists of:
		// - the string "Photoshop 3.0\u0000"
		// - followed by "8BIM"
		// - and then the section type "\u0004\u0004".
		// There might be multiple 8BIM sections with different types, but we're following
		// YAGNI for now and only deal with the one we're interested in (and hope that it's
		// the first one).
		int iptc_iim_length = IPTC_IIM_IDENTIFIER.length();
		if (length < iptc_iim_length || !IPTC_IIM_IDENTIFIER.equals(data.Mid(0, iptc_iim_length)))
		{
			return;
		}

		// PS6 introduced a new header with variable length text
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var headerInfoLen = data.Mid(iptc_iim_length, 1).ToUShort();
		int lenToSkip;
		if (headerInfoLen > 0)
		{
			// PS6 header: 1 byte headerinfolen + headerinfo + 2 bytes 00 padding (?) + 2 bytes length
			lenToSkip = 1 + headerInfoLen + 4;
		}
		else
		{
			//old style: 4 bytes 00 padding (?) + 2 bytes length
			lenToSkip = 6;
		}
		data.RemoveRange(0, iptc_iim_length + lenToSkip);

		IIM.IIMReader reader = new IIM.IIMReader(data);
		Rasad.Core.Media.MediaMetadataManagement.IIM.IIMTag tag = reader.Process();
		if (tag != null)
		{
			getImageTag().AddTag(tag);
		}
	}

	/** 
		Writes the metadata back to file. All metadata is stored in the first segments
		of the file.
	*/
	private void WriteMetadata()
	{
		// first render all metadata segments to a ByteVector before the
		// file is touched ...
		ByteVector data = new ByteVector();

		// existing jfif header is retained, otherwise a standard one
		// is created
		if (jfif_header != null)
		{
			data.add(jfif_header);
		}
		else
		{
			data.add(BASIC_JFIF_HEADER);
		}

		data.add(RenderExifSegment());
		data.add(RenderXMPSegment());
		data.add(RenderCOMSegment());

		SaveMetadata(data, 2);
	}

	/** 
		Creates a <see cref="ByteVector"/> for the Exif segment of this file
	 
	 @return 
		A <see cref="ByteVector"/> with the whole Exif segment, if exif tags
		exists, otherwise null.
	 
	*/
	private ByteVector RenderExifSegment()
	{
		// Check, if IFD0 is contained
		IFDTag exif = getImageTag().getExif();
		if (exif == null)
		{
			return null;
		}

		// first IFD starts at 8
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint first_ifd_offset = 8;
		int first_ifd_offset = 8;

		// Render IFD0
		// FIXME: store endianess and use it here
		IFDRenderer renderer = new IFDRenderer(true, exif.getStructure(), first_ifd_offset);
		ByteVector exif_data = renderer.Render();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint segment_size = (uint)(first_ifd_offset + exif_data.Count + 2 + 6);
		int segment_size = (int)(first_ifd_offset + exif_data.size() + 2 + 6);

		// do not render data segments, which cannot fit into the possible segment size
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (segment_size > ushort.MaxValue)
		if (segment_size > Short.MAX_VALUE)
		{
			throw new RuntimeException("Exif Segment is too big to render");
		}

		// Create whole segment
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector data = new ByteVector(new byte [] { 0xFF, (byte) Marker.APP1 });
		ByteVector data = new ByteVector(new byte [] {(byte)0xFF, (byte) Marker.APP1.getValue()});
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUShort((ushort) segment_size));
		data.add(ByteVector.FromUShort((short) segment_size));
		data.add("Exif\0\0");
		data.add(ByteVector.FromString("MM", StringType.Latin1));
		data.add(ByteVector.FromUShort((short)42));
		data.add(ByteVector.FromUInt(first_ifd_offset));

		// Add ifd data itself
		data.add(exif_data);

		return data;
	}


	/** 
		Creates a <see cref="ByteVector"/> for the Xmp segment of this file
	 
	 @return 
		A <see cref="ByteVector"/> with the whole Xmp segment, if xmp tags
		exists, otherwise null.
	 
	*/
	private ByteVector RenderXMPSegment()
	{
		// Check, if XmpTag is contained
		XmpTag xmp = getImageTag().getXmp();
		if (xmp == null)
		{
			return null;
		}

		ByteVector xmp_data = XmpTag.XAP_NS + "\0";
		xmp_data.add(xmp.Render());

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint segment_size = (uint)(2 + xmp_data.Count);
		int segment_size = (int)(2 + xmp_data.size());

		// do not render data segments, which cannot fit into the possible segment size
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (segment_size > ushort.MaxValue)
		if (segment_size > Short.MAX_VALUE)
		{
			throw new RuntimeException("XMP Segment is too big to render");
		}

		// Create whole segment
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector data = new ByteVector(new byte [] { 0xFF, (byte) Marker.APP1 });
		ByteVector data = new ByteVector(new byte [] {(byte)0xFF, (byte) Marker.APP1.getValue()});
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUShort((ushort) segment_size));
		data.add(ByteVector.FromUShort((short) segment_size));
		data.add(xmp_data);

		return data;
	}


	/** 
		Reads a COM segment to find the JPEG comment.
	 
	 @param length
		The length of the segment that will be read.
	 
	*/
	private void ReadCOMSegment(int length)
	{
		if ((getImageTag().getTagTypes().getValue() & Rasad.Core.Media.MediaMetadataManagement.TagTypes.JpegComment.getValue()) != 0x00)
		{
			return;
		}

		long position = Tell;

		JpegCommentTag com_tag;

		if (length == 0)
		{
			 com_tag = new JpegCommentTag();
		}
		else
		{
			ByteVector data = ReadBlock(length);

			int terminator = data.Find("\0", 0);

			if (terminator < 0)
			{
				com_tag = new JpegCommentTag(data.toString());
			}
			else
			{
				com_tag = new JpegCommentTag(data.Mid(0, terminator).toString());
			}
		}

		getImageTag().AddTag(com_tag);
		AddMetadataBlock(position - 4, length + 4);
	}

	/** 
		Creates a <see cref="ByteVector"/> for the comment segment of this file
	 
	 @return 
		A <see cref="ByteVector"/> with the whole comment segment, if a comment tag
		exists, otherwise null.
	 
	*/
	private ByteVector RenderCOMSegment()
	{
		// check, if Comment is contained
		Object tempVar = GetTag(TagTypes.JpegComment);
		JpegCommentTag com_tag = tempVar instanceof JpegCommentTag ? (JpegCommentTag)tempVar : null;
		if (com_tag == null)
		{
			return null;
		}

		// create comment data
		ByteVector com_data = ByteVector.FromString(com_tag.getValue() + "\0", StringType.Latin1);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint segment_size = (uint)(2 + com_data.Count);
		int segment_size = (int)(2 + com_data.size());

		// do not render data segments, which cannot fit into the possible segment size
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (segment_size > ushort.MaxValue)
		if (segment_size > Short.MAX_VALUE)
		{
			throw new RuntimeException("Comment Segment is too big to render");
		}

		// create segment
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector data = new ByteVector(new byte [] { 0xFF, (byte) Marker.COM });
		ByteVector data = new ByteVector(new byte [] {(byte)0xFF, (byte) Marker.COM.getValue()});
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUShort((ushort) segment_size));
		data.add(ByteVector.FromUShort((short) segment_size));

		data.add(com_data);

		return data;
	}

	/** 
		Reads and parse a SOF segment
	 
	 @param length
		The length of the segment that will be read.
	 
	 @param marker
		The SOFx marker.
	 
	*/
	private void ReadSOFSegment(int length, Marker marker)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning disable 219 // Assigned, never read
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte p = ReadBlock(1)[0];
		byte p = ReadBlock(1)[0]; //precision
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning restore 219

		//FIXME: according to specs, height could be 0 here, and should be retrieved from the DNL marker
		height = ReadBlock(2).ToUShort();
		width = ReadBlock(2).ToUShort();
	}

	/** 
		Reads the DQT Segment, and Guesstimate the image quality from it
	 
	 @param length
		The length of the segment that will be read
	 
	*/
	private void ReadDQTSegment(int length)
	{
		// See CCITT Rec. T.81 (1992 E), B.2.4.1 (p39) for DQT syntax
		while (length > 0)
		{

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte pqtq = ReadBlock(1)[0];
			byte pqtq = ReadBlock(1)[0];
			length--;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte pq = (byte)(pqtq >> 4);
			byte pq = (byte)(pqtq >>> 4); //0 indicates 8-bit Qk, 1 indicates 16-bit Qk
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte tq = (byte)(pqtq & 0x0f);
			byte tq = (byte)(pqtq & 0x0f); //table index;
			int [] table = null;
			switch (tq)
			{
			case 0:
				table = Table.StandardLuminanceQuantization;
				break;
			case 1:
				table = Table.StandardChrominanceQuantization;
				break;
			}

			boolean allones = true; //check for all-ones tables (q=100)
			double cumsf = 0.0;
			//double cumsf2 = 0.0;
			for (int row = 0; row < 8; row++)
			{
				for (int col = 0; col < 8; col++)
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort val = ReadBlock(pq == 1 ? 2 : 1).ToUShort();
					short val = ReadBlock(pq == 1 ? 2 : 1).ToUShort();
					length -= (pq + 1);
					if (table != null)
					{
						double x = 100.0 * (double)val / (double)table [row * 8 + col]; //Scaling factor in percent
						cumsf += x;
						//cumsf2 += x*x;
						allones = allones && (val == 1);
					}
				}
			}

			if (table != null)
			{
				double local_q;
				cumsf /= 64.0; // mean scale factor
				//cumfs2 /= 64.0;
				//double variance = cumsf2 - (cumsf * cumsf);

				if (allones)
				{
					local_q = 100.0;
				}
				else if (cumsf <= 100.0)
				{
					local_q = (200.0 - cumsf) / 2.0;
				}
				else
				{
					local_q = 5000.0 / cumsf;
				}
				quality = Math.max(quality, (int)local_q);
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}