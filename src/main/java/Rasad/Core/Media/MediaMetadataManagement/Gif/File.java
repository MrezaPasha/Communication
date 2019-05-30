package Rasad.Core.Media.MediaMetadataManagement.Gif;

import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.Xmp.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// File.cs: Provides tagging for GIF files
//
// Author:
//   Mike Gemuende (mike@gemuende.be)
//
// Copyright (C) 2010 Mike Gemuende
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
	and property support for Gif files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/gif", "gif")][SupportedMimeType("image/gif")] public class File : Rasad.Core.Media.MediaMetadataManagement.Image.ImageBlockFile
public class File extends Rasad.Core.Media.MediaMetadataManagement.Image.ImageBlockFile
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region GIF specific constants

	/** 
		Gif file signature which occurs at the begin of the file
	*/
	protected static final String SIGNATURE = "GIF";

	/** 
		String which is used to indicate version the gif file format version 87a
	*/
	protected static final String VERSION_87A = "87a";

	/** 
		String which is used to indicate version the gif file format version 89a
	*/
	protected static final String VERSION_89A = "89a";

	/** 
		Application Extension Identifier for an XMP Block
	*/
	private static final String XMP_IDENTIFIER = "XMP Data";

	/** 
		Application Authentication Extension Code for an XMP Block
	*/
	private static final String XMP_AUTH_CODE = "XMP";

	/** 
		The Magic Trailer for XMP Data
	 
	 
		The storage of XMP data in GIF does not follow the GIF specification. According to the
		specification, extension data is stored in so-called sub-blocks, which start with a length
		byte which specifies the number of data bytes contained in the sub block. So a block can at
		most contain 256 data bytes. After a sub-block, the next sub-block begins. The sequence ends,
		when a sub-block starts with 0. So readers, which are not aware of the XMP data not following
		this scheme, will get confused by the XMP data. To fix this, this trailer is added to the end.
		It has a length of 258 bytes, so that it is ensured that a reader which tries to skip the
		XMP data reads one of this bytes as length of a sub-block. But, each byte points with its length
		to the last one. Therefoe, independent of the byte, the reader reads as sub-block length, it is
		redirected to the last byte of the trailer and therfore to the end of the XMP data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static readonly byte [] XMP_MAGIC_TRAILER = new byte [] { 0x01, 0xFF, 0xFE, 0xFD, 0xFC, 0xFB, 0xFA, 0xF9, 0xF8, 0xF7, 0xF6, 0xF5, 0xF4, 0xF3, 0xF2, 0xF1, 0xF0, 0xEF, 0xEE, 0xED, 0xEC, 0xEB, 0xEA, 0xE9, 0xE8, 0xE7, 0xE6, 0xE5, 0xE4, 0xE3, 0xE2, 0xE1, 0xE0, 0xDF, 0xDE, 0xDD, 0xDC, 0xDB, 0xDA, 0xD9, 0xD8, 0xD7, 0xD6, 0xD5, 0xD4, 0xD3, 0xD2, 0xD1, 0xD0, 0xCF, 0xCE, 0xCD, 0xCC, 0xCB, 0xCA, 0xC9, 0xC8, 0xC7, 0xC6, 0xC5, 0xC4, 0xC3, 0xC2, 0xC1, 0xC0, 0xBF, 0xBE, 0xBD, 0xBC, 0xBB, 0xBA, 0xB9, 0xB8, 0xB7, 0xB6, 0xB5, 0xB4, 0xB3, 0xB2, 0xB1, 0xB0, 0xAF, 0xAE, 0xAD, 0xAC, 0xAB, 0xAA, 0xA9, 0xA8, 0xA7, 0xA6, 0xA5, 0xA4, 0xA3, 0xA2, 0xA1, 0xA0, 0x9F, 0x9E, 0x9D, 0x9C, 0x9B, 0x9A, 0x99, 0x98, 0x97, 0x96, 0x95, 0x94, 0x93, 0x92, 0x91, 0x90, 0x8F, 0x8E, 0x8D, 0x8C, 0x8B, 0x8A, 0x89, 0x88, 0x87, 0x86, 0x85, 0x84, 0x83, 0x82, 0x81, 0x80, 0x7F, 0x7E, 0x7D, 0x7C, 0x7B, 0x7A, 0x79, 0x78, 0x77, 0x76, 0x75, 0x74, 0x73, 0x72, 0x71, 0x70, 0x6F, 0x6E, 0x6D, 0x6C, 0x6B, 0x6A, 0x69, 0x68, 0x67, 0x66, 0x65, 0x64, 0x63, 0x62, 0x61, 0x60, 0x5F, 0x5E, 0x5D, 0x5C, 0x5B, 0x5A, 0x59, 0x58, 0x57, 0x56, 0x55, 0x54, 0x53, 0x52, 0x51, 0x50, 0x4F, 0x4E, 0x4D, 0x4C, 0x4B, 0x4A, 0x49, 0x48, 0x47, 0x46, 0x45, 0x44, 0x43, 0x42, 0x41, 0x40, 0x3F, 0x3E, 0x3D, 0x3C, 0x3B, 0x3A, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x2F, 0x2E, 0x2D, 0x2C, 0x2B, 0x2A, 0x29, 0x28, 0x27, 0x26, 0x25, 0x24, 0x23, 0x22, 0x21, 0x20, 0x1F, 0x1E, 0x1D, 0x1C, 0x1B, 0x1A, 0x19, 0x18, 0x17, 0x16, 0x15, 0x14, 0x13, 0x12, 0x11, 0x10, 0x0F, 0x0E, 0x0D, 0x0C, 0x0B, 0x0A, 0x09, 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00, 0x00 };
	private static final byte [] XMP_MAGIC_TRAILER = new byte [] {0x01, (byte)0xFF, (byte)0xFE, (byte)0xFD, (byte)0xFC, (byte)0xFB, (byte)0xFA, (byte)0xF9, (byte)0xF8, (byte)0xF7, (byte)0xF6, (byte)0xF5, (byte)0xF4, (byte)0xF3, (byte)0xF2, (byte)0xF1, (byte)0xF0, (byte)0xEF, (byte)0xEE, (byte)0xED, (byte)0xEC, (byte)0xEB, (byte)0xEA, (byte)0xE9, (byte)0xE8, (byte)0xE7, (byte)0xE6, (byte)0xE5, (byte)0xE4, (byte)0xE3, (byte)0xE2, (byte)0xE1, (byte)0xE0, (byte)0xDF, (byte)0xDE, (byte)0xDD, (byte)0xDC, (byte)0xDB, (byte)0xDA, (byte)0xD9, (byte)0xD8, (byte)0xD7, (byte)0xD6, (byte)0xD5, (byte)0xD4, (byte)0xD3, (byte)0xD2, (byte)0xD1, (byte)0xD0, (byte)0xCF, (byte)0xCE, (byte)0xCD, (byte)0xCC, (byte)0xCB, (byte)0xCA, (byte)0xC9, (byte)0xC8, (byte)0xC7, (byte)0xC6, (byte)0xC5, (byte)0xC4, (byte)0xC3, (byte)0xC2, (byte)0xC1, (byte)0xC0, (byte)0xBF, (byte)0xBE, (byte)0xBD, (byte)0xBC, (byte)0xBB, (byte)0xBA, (byte)0xB9, (byte)0xB8, (byte)0xB7, (byte)0xB6, (byte)0xB5, (byte)0xB4, (byte)0xB3, (byte)0xB2, (byte)0xB1, (byte)0xB0, (byte)0xAF, (byte)0xAE, (byte)0xAD, (byte)0xAC, (byte)0xAB, (byte)0xAA, (byte)0xA9, (byte)0xA8, (byte)0xA7, (byte)0xA6, (byte)0xA5, (byte)0xA4, (byte)0xA3, (byte)0xA2, (byte)0xA1, (byte)0xA0, (byte)0x9F, (byte)0x9E, (byte)0x9D, (byte)0x9C, (byte)0x9B, (byte)0x9A, (byte)0x99, (byte)0x98, (byte)0x97, (byte)0x96, (byte)0x95, (byte)0x94, (byte)0x93, (byte)0x92, (byte)0x91, (byte)0x90, (byte)0x8F, (byte)0x8E, (byte)0x8D, (byte)0x8C, (byte)0x8B, (byte)0x8A, (byte)0x89, (byte)0x88, (byte)0x87, (byte)0x86, (byte)0x85, (byte)0x84, (byte)0x83, (byte)0x82, (byte)0x81, (byte)0x80, 0x7F, 0x7E, 0x7D, 0x7C, 0x7B, 0x7A, 0x79, 0x78, 0x77, 0x76, 0x75, 0x74, 0x73, 0x72, 0x71, 0x70, 0x6F, 0x6E, 0x6D, 0x6C, 0x6B, 0x6A, 0x69, 0x68, 0x67, 0x66, 0x65, 0x64, 0x63, 0x62, 0x61, 0x60, 0x5F, 0x5E, 0x5D, 0x5C, 0x5B, 0x5A, 0x59, 0x58, 0x57, 0x56, 0x55, 0x54, 0x53, 0x52, 0x51, 0x50, 0x4F, 0x4E, 0x4D, 0x4C, 0x4B, 0x4A, 0x49, 0x48, 0x47, 0x46, 0x45, 0x44, 0x43, 0x42, 0x41, 0x40, 0x3F, 0x3E, 0x3D, 0x3C, 0x3B, 0x3A, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x2F, 0x2E, 0x2D, 0x2C, 0x2B, 0x2A, 0x29, 0x28, 0x27, 0x26, 0x25, 0x24, 0x23, 0x22, 0x21, 0x20, 0x1F, 0x1E, 0x1D, 0x1C, 0x1B, 0x1A, 0x19, 0x18, 0x17, 0x16, 0x15, 0x14, 0x13, 0x12, 0x11, 0x10, 0x0F, 0x0E, 0x0D, 0x0C, 0x0B, 0x0A, 0x09, 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00, 0x00};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region private fields

	/** 
		The width of the image
	*/
	private int width;

	/** 
		The height of the image
	*/
	private int height;

	/** 
		The Properties of the image
	*/
	private Properties properties;

	/** 
		The version of the file format
	*/
	private String version;

	/** 
		The start of the first block in file after the header.
	*/
	private long start_of_blocks = -1;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region public Properties

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
///#region constructors

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
///#region Public Methods

	/** 
		Saves the changes made in the current instance to the
		file it represents.
	*/
	@Override
	public void Save()
	{
		Mode = AccessMode.Write;
		try
		{
			SaveMetadata();

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
			setImageTag(new CombinedImageTag(TagTypes.XMP.getValue() | TagTypes.GifComment.getValue()));

			ReadHeader();
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
	   Reads a single byte form file. This is needed often for Gif files.
	 
	 @return 
	   A <see cref="System.Byte"/> with the read data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte ReadByte()
	private byte ReadByte()
	{
		ByteVector data = ReadBlock(1);

		if (data.size() != 1)
		{
			throw new CorruptFileException("Unexpected end of file");
		}

		return data.get(0);
	}


	/** 
		Reads the Header and the Logical Screen Descriptor of the GIF file and,
		if there is one, skips the global color table. It also extracts the
		image width and height from it.
	*/
	private void ReadHeader()
	{
		// The header consists of:
		//
		// 3 Bytes        Signature
		// 3 Bytes        Version
		//
		// The Logical Screen Descriptor of:
		//
		// 2 Bytes        Width  (little endian)
		// 2 Bytes        Height (little endian)
		// 1 Byte         Screen and Color Map (packed field)
		// 1 Byte         Background Color
		// 1 Byte         Aspect Ratio
		//
		// Whereas the bits of the packed field contains some special information.
		ByteVector data = ReadBlock(13);

		if (data.size() != 13)
		{
			throw new CorruptFileException("Unexpected end of Header");
		}

		if (!data.Mid(0, 3).toString().equals(SIGNATURE))
		{
			throw new CorruptFileException(String.format("Expected a GIF signature at start of file, but found: %1$s", data.Mid(0, 3).toString()));
		}

		// We do not care about the version here, because we can read both versions in the same way.
		// We just care when writing metadata, that, if necessary, the version is increased to 89a.
		String read_version = data.Mid(3, 3).toString();
		if (VERSION_87A.equals(read_version) || VERSION_89A.equals(read_version))
		{
			version = read_version;
		}
		else
		{
			throw new UnsupportedFormatException(String.format("Only GIF versions 87a and 89a are currently supported, but not: %1$s", read_version));
		}

		// Read Image Size (little endian)
		width = data.Mid(6, 2).ToUShort(false);
		height = data.Mid(8, 2).ToUShort(false);

		// Skip optional global color table
		SkipColorTable(data.get(10));
	}


	/** 
		Reads the metadata from file. The current position must point to the
		start of the first block after the Header and Logical Screen
		Descriptor (and, if there is one, the Global Color Table)
	*/
	private void ReadMetadata()
	{
		start_of_blocks = Tell;

		// Read Blocks until end of file is reached.
		while (true)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte identifier = ReadByte();
			byte identifier = ReadByte();

			switch (identifier)
			{
			case 0x2c:
				SkipImage();
				break;

			case 0x21:
				ReadExtensionBlock();
				break;

			case 0x3B:
				return;

			default:
				throw new CorruptFileException(String.format("Do not know what to do with byte 0x%02X at the beginning of a block (%2$s).", identifier, Tell - 1));
			}
		}
	}

	/** 
		Reads an Extension Block at the current position. The current position must
		point to the 2nd byte of the comment block. (The other byte is usually
		read before to identify the comment block)
	*/
	private void ReadExtensionBlock()
	{
		// Extension Block
		//
		// 1 Byte       Extension Introducer (0x21)
		// 1 Byte       Extension Identifier
		// ....
		//
		// Note, the Extension Introducer was read before to
		// identify the Extension Block. Therefore, it has not
		// to be handled here.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte identifier = ReadByte();
		byte identifier = ReadByte();

		switch (identifier)
		{
		case 0xFE:
			ReadCommentBlock();
			break;

		case 0xFF:
			ReadApplicationExtensionBlock();
			break;

		// Control Extension Block, ...
		case 0xF9:
		// ... Plain Text Extension ...
		case 0x01:
		// ... and all other unknown blocks can be skipped by just
		// reading sub-blocks.
		default:
			SkipSubBlocks();
			break;
		}
	}


	/** 
		Reads an Application Extension Block at the current position. The current
		position must point to the 3rd byte of the comment block. (The other 2 bytes
		are usually read before to identify the comment block)
	*/
	private void ReadApplicationExtensionBlock()
	{
		// Application Extension Block
		//
		// 1 Byte       Extension Introducer (0x21)
		// 1 Byte       Application Extension Label (0xFF)
		// 1 Byte       Block Size (0x0B - 11)
		// 8 Bytes      Application Identifier
		// 3 Bytes      Application Auth. Code
		// N Bytes      Application Data (sub blocks)
		// 1 Byte       Block Terminator (0x00)
		//
		// Note, the first 2 bytes are still read to identify the Comment Block.
		// Therefore, we only need to read the sub blocks and extract the data.
		long position = Tell;
		ByteVector data = ReadBlock(12);

		if (data.size() != 12)
		{
			throw new CorruptFileException("");
		}

		// Contains XMP data
		if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(data.Mid(1, 8), XMP_IDENTIFIER) && Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(data.Mid(9, 3), XMP_AUTH_CODE))
		{
			// XMP Data is not organized in sub-blocks

			// start of xmp data
			long data_start = Tell;

			// start of trailer start
			// FIXME: Since File.Find is still buggy, the following call does not work to find the end of the
			// XMP data. Therfore, we use here a different way for now.
			//long xmp_trailer_start = Find (new ByteVector (0x00), data_start);

			// Since searching just one byte is save, we search for the end of the xmp trailer which
			// consists of two 0x00 bytes and compute the expected start.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: long xmp_trailer_start = Find(new byte [] {0x00}, data_start) - XMP_MAGIC_TRAILER.Length + 2;
			long xmp_trailer_start = Find(new byte [] {0x00}, data_start) - XMP_MAGIC_TRAILER.length + 2;

			Seek(data_start, SeekOrigin.Begin);

			if (xmp_trailer_start <= data_start)
			{
				throw new CorruptFileException("No End of XMP data found");
			}

			// length of xmp data
			int data_length = (int)(xmp_trailer_start - data_start);

			ByteVector xmp_data = ReadBlock(data_length);
			getImageTag().AddTag(new XmpTag(xmp_data.toString(StringType.UTF8), this));

			// 2 bytes where read before
			AddMetadataBlock(position - 2, 14 + data_length + XMP_MAGIC_TRAILER.length);

			// set position behind the XMP block
			Seek(xmp_trailer_start + XMP_MAGIC_TRAILER.length, SeekOrigin.Begin);

		}
		else
		{
			SkipSubBlocks();
		}
	}


	/** 
		Reads a Comment Block at the current position. The current position must
		point to the 3rd byte of the comment block. (The other 2 bytes are usually
		read before to identify the comment block)
	*/
	private void ReadCommentBlock()
	{
		long position = Tell;

		// Comment Extension
		//
		// 1 Byte       Extension Introducer (0x21)
		// 1 Byte       Comment Label (0xFE)
		// N Bytes      Comment Data (Sub Blocks)
		// 1 Byte       Block Terminator (0x00)
		//
		// Note, the first 2 bytes are still read to identify the Comment Block.
		// Therefore, we only need to read the sub blocks and extract the data.

		String comment = ReadSubBlocks();

		// Only add the tag, if no one is still contained.
		if ((TagTypes.getValue() & TagTypes.GifComment.getValue()) == 0x00)
		{
			getImageTag().AddTag(new GifCommentTag(comment));

			// 2 bytes where read before
			AddMetadataBlock(position - 2, Tell - position + 2);
		}
	}


	/** 
		Skips the color table if there is one
	 
	 @param packed_data
		A <see cref="System.Byte"/> with the packed data which is
		contained Logical Screen Descriptor or in the Image Descriptor.
	 
	 
		The data contained in the packed data is different for the Logical
		Screen Descriptor and for the Image Descriptor. But fortunately,
		the bits which are used do identifying the exitstance and the size
		of the color table are at the same position.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void SkipColorTable(byte packed_data)
	private void SkipColorTable(byte packed_data)
	{
		// Packed Field (Information with Bit 0 is  LSB)
		//
		// Bit 0-2      Size of Color Table
		// Bit 3-6      Other stuff
		// Bit 7        (Local|Global) Color Table Flag
		//
		// We are interested in Bit 7 which indicates if a global color table is
		// present or not and the Bits 0-2 which indicate the size of the color
		// table.
		if ((packed_data & 0x80) == 0x80)
		{
			// 2^(size + 1) for each color.
			int table_size = 3 * (1 << ((packed_data & 0x07) + 1));

			// and simply skip the color table
			ByteVector color_table = ReadBlock(table_size);

			if (color_table.size() != table_size)
			{
				throw new CorruptFileException("Unexpected end of Color Table");
			}

		}
	}


	/** 
		Skip over the image data at the current position. The current position must
		point to 2nd byte of the Image Descriptor. (First byte is usually read before
		to identify the image descriptor.)
	*/
	private void SkipImage()
	{
		// Image Descriptor
		//
		// 1 Byte         Separator (0x2C)
		// 2 Bytes        Image Left Position   (little endian)
		// 2 Bytes        Image Right Position  (little endian)
		// 2 Bytes        Image Witdh           (little endian)
		// 2 Bytes        Image Height          (little endian)
		// 1 Byte         Packed Data
		//
		// Note, the Separator was read before to identify the Image Block
		// Therefore, we only need to read 9 bytes here.
		ByteVector data = ReadBlock(9);

		if (data.size() != 9)
		{
			throw new CorruptFileException("Unexpected end of Image Descriptor");
		}

		// Skip an optional local color table
		SkipColorTable(data.get(8));


		// Image Data
		//
		// 1 Byte         LZW Minimum Code Size
		// N Bytes        Image Data (Sub-Blocks)
		//
		// Before the image data, one byte for LZW encoding information is used.
		// This byte is read first, then the sub-blocks are skipped.
		ReadBlock(1);
		SkipSubBlocks();
	}


	/** 
		Reads a sequence of sub-blocks from the current position and concatenates the data
		from the sub-blocks to a string. The current position must point to the size-byte
		of the first subblock to skip.
	 
	 @return 
		A <see cref="System.String"/> with the data contained in the sub-blocks.
	 
	*/
	private String ReadSubBlocks()
	{
		// Sub Block
		// Starts with one byte with the number of data bytes
		// following. The last sub block is terminated by length 0
		StringBuilder builder = new StringBuilder();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte length = 0;
		byte length = 0;

		do
		{

			if (length >= 0)
			{
				builder.append(ReadBlock(length).toString());
			}

			// read new length byte
			length = ReadByte();

		// The sub-blocks are terminated with 0
		} while (length != 0);

		return builder.toString();
	}


	/** 
		Skips over a sequence of sub-blocks from the current position in the file.
		The current position must point to the size-byte of the first subblock to skip.
	*/
	private void SkipSubBlocks()
	{
		// Sub Block
		// Starts with one byte with the number of data bytes
		// following. The last sub block is terminated by length 0
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte length = 0;
		byte length = 0;

		do
		{

			if (Tell + length >= Length)
			{
				throw new CorruptFileException("Unexpected end of Sub-Block");
			}

			// Seek to end of sub-block and update the position
			Seek(Tell + length, SeekOrigin.Begin);


			// read new length byte
			length = ReadByte();

		// The sub-blocks are terminated with 0
		} while (length != 0);
	}


	/** 
		Save the metadata to file.
	*/
	private void SaveMetadata()
	{
		ByteVector comment_block = RenderGifCommentBlock();
		ByteVector xmp_block = RenderXMPBlock();

		// If we write metadata and the version is not 89a, bump the format version
		// because application extension blocks and comment extension blocks are
		// specified in 89a.
		// If we do not write metadata or if metadata is deleted, we do not care
		// about the version, because it may be wrong before.
		if (comment_block != null && xmp_block != null && !VERSION_89A.equals(version))
		{
			Insert(VERSION_89A, 3, VERSION_89A.length());
		}

		// now, only metadata is stored at the beginning of the file, and we can overwrite it.
		ByteVector metadata_blocks = new ByteVector();
		metadata_blocks.add(comment_block);
		metadata_blocks.add(xmp_block);

		SaveMetadata(metadata_blocks, start_of_blocks);
	}


	/** 
		Renders the XMP data to a Application Extension Block which can be
		embedded in a Gif file.
	 
	 @return 
		A <see cref="ByteVector"/> with the Application Extension Block for the
		XMP data, or <see langword="null" /> if the file does not have XMP data.
	 
	*/
	private ByteVector RenderXMPBlock()
	{
		// Check, if XmpTag is contained
		XmpTag xmp = getImageTag().getXmp();
		if (xmp == null)
		{
			return null;
		}

		ByteVector xmp_data = new ByteVector();

		// Add Extension Introducer (0x21), Application Extension Label (0xFF) and
		// the Block Size (0x0B
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: xmp_data.Add(new byte [] {0x21, 0xFF, 0x0B});
		xmp_data.add(new byte [] {0x21, (byte)0xFF, 0x0B});

		// Application Identifier and Appl. Auth. Code
		xmp_data.add(XMP_IDENTIFIER);
		xmp_data.add(XMP_AUTH_CODE);

		// Add XMP data and Magic Trailer
		// For XMP, we do not need to store the data in sub-blocks, therfore we
		// can just add the whole rendered data. (The trailer fixes this)
		xmp_data.add(xmp.Render());
		xmp_data.add(XMP_MAGIC_TRAILER);

		return xmp_data;
	}


	/** 
		Renders the Gif Comment to a Comment Extension Block which can be
		embedded in a Gif file.
	 
	 @return 
		A <see cref="ByteVector"/> with the Comment Extension Block for the
		Gif Comment, or <see langword="null" /> if the file does not have
		a Gif Comment.
	 
	*/
	private ByteVector RenderGifCommentBlock()
	{
		// Check, if GifCommentTag is contained
		Object tempVar = GetTag(TagTypes.GifComment);
		GifCommentTag comment_tag = tempVar instanceof GifCommentTag ? (GifCommentTag)tempVar : null;
		if (comment_tag == null)
		{
			return null;
		}

		String comment = comment_tag.getComment();
		if (comment == null)
		{
			return null;
		}

		ByteVector comment_data = new ByteVector();

		// Add Extension Introducer (0x21) and Comment Label (0xFE)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: comment_data.Add(new byte [] {0x21, 0xFE});
		comment_data.add(new byte [] {0x21, (byte)0xFE});

		// Add data of comment in sub-blocks of max length 256.
		ByteVector comment_bytes = new ByteVector(comment);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte block_max = 255;
		byte block_max = (byte)255;
		for (int start = 0; start < comment_bytes.size(); start += block_max)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte block_length = (byte) Math.Min(comment_bytes.Count - start, block_max);
			byte block_length = (byte) Math.min(comment_bytes.size() - start, block_max);

			comment_data.add(block_length);
			comment_data.add(comment_bytes.Mid(start, block_length));
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: comment_data.Add(new byte [] {0x00});
		comment_data.add(new byte [] {0x00});

		return comment_data;
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
			return new Properties(TimeSpan.Zero, new Codec(width, height));
		}

		return null;

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}