package Rasad.Core.Media.MediaMetadataManagement.Png;

import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.Xmp.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// File.cs: Provides tagging for PNG files
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
	for PNG image files.
 
 
	This implementation is based on http: //www.w3.org/TR/PNG
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/png", "png")][SupportedMimeType("image/png")] public class File : Rasad.Core.Media.MediaMetadataManagement.Image.ImageBlockFile
public class File extends Rasad.Core.Media.MediaMetadataManagement.Image.ImageBlockFile
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region GIF specific constants

	/** 
		The PNG Header every png file starts with.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly byte [] HEADER = new byte [] {137, 80, 78, 71, 13, 10, 26, 10};
	private final byte [] HEADER = new byte [] {(byte)137, 80, 78, 71, 13, 10, 26, 10};

	/** 
		byte sequence to indicate a IHDR Chunk
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly byte [] IHDR_CHUNK_TYPE = new byte [] {73, 72, 68, 82};
	private final byte [] IHDR_CHUNK_TYPE = new byte [] {73, 72, 68, 82};

	/** 
		byte sequence to indicate a IEND Chunk
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly byte [] IEND_CHUNK_TYPE = new byte [] {73, 69, 78, 68};
	private final byte [] IEND_CHUNK_TYPE = new byte [] {73, 69, 78, 68};

	/** 
		byte sequence to indicate a iTXt Chunk
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly byte [] iTXt_CHUNK_TYPE = new byte [] {105, 84, 88, 116};
	private final byte [] iTXt_CHUNK_TYPE = new byte [] {105, 84, 88, 116};

	/** 
		byte sequence to indicate a tEXt Chunk
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly byte [] tEXt_CHUNK_TYPE = new byte [] {116, 69, 88, 116};
	private final byte [] tEXt_CHUNK_TYPE = new byte [] {116, 69, 88, 116};

	/** 
		byte sequence to indicate a zTXt Chunk
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly byte [] zTXt_CHUNK_TYPE = new byte [] {122, 84, 88, 116};
	private final byte [] zTXt_CHUNK_TYPE = new byte [] {122, 84, 88, 116};

	/** 
		header of a iTXt which contains XMP data.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly byte [] XMP_CHUNK_HEADER = new byte [] { 0x58, 0x4D, 0x4C, 0x3A, 0x63, 0x6F, 0x6D, 0x2E, 0x61, 0x64, 0x6F, 0x62, 0x65, 0x2E, 0x78, 0x6D, 0x70, 0x00, 0x00, 0x00, 0x00, 0x00 };
	private final byte [] XMP_CHUNK_HEADER = new byte [] {0x58, 0x4D, 0x4C, 0x3A, 0x63, 0x6F, 0x6D, 0x2E, 0x61, 0x64, 0x6F, 0x62, 0x65, 0x2E, 0x78, 0x6D, 0x70, 0x00, 0x00, 0x00, 0x00, 0x00};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region private fields

	/** 
		The height of the image
	*/
	private int height;

	/** 
		The width of the image
	*/
	private int width;

	/** 
		The Properties of the image
	*/
	private Properties properties;

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
///#region private methods

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
			setImageTag(new CombinedImageTag(TagTypes.XMP.getValue() | TagTypes.Png.getValue()));

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
		Validates the header of a PNG file. Therfore, the current position to
		read must be the start of the file.
	*/
	private void ValidateHeader()
	{
		ByteVector data = ReadBlock(8);

		if (data.size() != 8)
		{
			throw new CorruptFileException("Unexpected end of header");
		}

		if (!data.equals(new ByteVector(HEADER)))
		{
			throw new CorruptFileException("PNG Header was expected");
		}
	}


	/** 
		Reads the length of data of a chunk from the current position
	 
	 @return 
		A <see cref="System.Int32"/> with the length of data.
	 
	 
		The length is stored in a 4-byte unsigned integer in the file,
		but due to the PNG specification this value does not exceed
		2^31-1 and can therfore be safely returned as an signed integer.
		This prevents unsafe casts for using the length as parameter
		for other methods.
	 
	*/
	private int ReadChunkLength()
	{
		ByteVector data = ReadBlock(4);

		if (data.size() != 4)
		{
			throw new CorruptFileException("Unexpected end of Chunk Length");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint length = data.ToUInt(true);
		int length = data.ToUInt(true);

		if (length > Integer.MAX_VALUE)
		{
			throw new CorruptFileException("PNG limits the Chunk Length to 2^31-1");
		}

		return (int) length;
	}


	/** 
		Reads the type of a chunk from the current position.
	 
	 @return 
		A <see cref="ByteVector"/> with 4 bytes containing the type of
		the Chunk.
	 
	*/
	private ByteVector ReadChunkType()
	{
		ByteVector data = ReadBlock(4);

		if (data.size() != 4)
		{
			throw new CorruptFileException("Unexpected end of Chunk Type");
		}

		return data;
	}


	/** 
		Reads the CRC value for a chunk from the current position.
	 
	 @return 
		A <see cref="ByteVector"/> with 4 bytes with the CRC value.
	 
	*/
	private ByteVector ReadCRC()
	{
		ByteVector data = ReadBlock(4);

		if (data.size() != 4)
		{
			throw new CorruptFileException("Unexpected end of CRC");
		}

		return data;
	}


	/** 
		Reads the whole Chunk data starting from the current position.
	 
	 @param data_length
		A <see cref="System.Int32"/> with the length of the Chunk Data.
	 
	 @return 
		A <see cref="ByteVector"/> with the Chunk Data which is read.
	 
	*/
	private ByteVector ReadChunkData(int data_length)
	{
		ByteVector data = ReadBlock(data_length);

		if (data.size() != data_length)
		{
			throw new CorruptFileException(String.format("Chunk Data of Length %1$s expected", data_length));
		}

		return data;
	}


	/** 
		Reads a null terminated string from the given data from given position.
	 
	 @param data
		A <see cref="ByteVector"/> with teh data to read the string from
	 
	 @param start_index
		A <see cref="System.Int32"/> with the index to start reading
	 
	 @param terminator_index
		A <see cref="System.Int32"/> with the index of the null byte
	 
	 @return 
		A <see cref="System.String"/> with the read string. The null byte
		is not included.
	 
	*/
	private String ReadTerminatedString(ByteVector data, int start_index, tangible.OutObject<Integer> terminator_index)
	{
		if (start_index >= data.size())
		{
			throw new CorruptFileException("Unexpected End of Data");
		}

		terminator_index.argValue = data.Find("\0", start_index);

		if (terminator_index.argValue < 0)
		{
			throw new CorruptFileException("Cannot find string terminator");
		}

		return data.Mid(start_index, terminator_index.argValue - start_index).toString();
	}


	/** 
		Reads a null terminated keyword from he given data from given position.
	 
	 @param data
		A <see cref="ByteVector"/> with teh data to read the string from
	 
	 @param start_index
		A <see cref="System.Int32"/> with the index to start reading
	 
	 @param terminator_index
		A <see cref="System.Int32"/> with the index of the null byte
	 
	 @return 
		A <see cref="System.String"/> with the read keyword. The null byte
		is not included.
	 
	*/
	private String ReadKeyword(ByteVector data, int start_index, tangible.OutObject<Integer> terminator_index)
	{
		String keyword = ReadTerminatedString(data, start_index, terminator_index);

		if (tangible.StringHelper.isNullOrEmpty(keyword))
		{
			throw new CorruptFileException("Keyword cannot be empty");
		}

		return keyword;
	}


	/** 
		Skips the Chunk Data and CRC Data. The read position must be at the
		beginning of the Chunk data.
	 
	 @param data_size
		A <see cref="System.Int32"/> with the length of the chunk data read
		before.
	 
	*/
	private void SkipChunkData(int data_size)
	{
		long position = Tell;

		if (position + data_size >= Length)
		{
			throw new CorruptFileException(String.format("Chunk Data of Length %1$s expected", data_size));
		}

		Seek(Tell + data_size);
		ReadCRC();
	}


	/** 
		Reads the whole metadata from file. The current position must be set to
		the first Chunk which is contained in the file.
	*/
	private void ReadMetadata()
	{
		int data_length = ReadChunkLength();
		ByteVector type = ReadChunkType();

		// File should start with a header chunk
		if (!type.StartsWith(IHDR_CHUNK_TYPE))
		{
			throw new CorruptFileException(String.format("IHDR Chunk was expected, but Chunk %1$s was found", type.toString()));
		}

		ReadIHDRChunk(data_length);

		// Read all following chunks
		while (true)
		{

			data_length = ReadChunkLength();
			type = ReadChunkType();

			if (type.StartsWith(IEND_CHUNK_TYPE))
			{
				return;
			}
			else if (type.StartsWith(iTXt_CHUNK_TYPE))
			{
				ReadiTXtChunk(data_length);
			}
			else if (type.StartsWith(tEXt_CHUNK_TYPE))
			{
				ReadtEXtChunk(data_length);
			}
			else if (type.StartsWith(zTXt_CHUNK_TYPE))
			{
				ReadzTXtChunk(data_length);
			}
			else
			{
				SkipChunkData(data_length);
			}

		}
	}


	/** 
		Reads the IHDR Chunk from file and extracts some image information
		like width and height. The current position must be set to the start
		of the Chunk Data.
	 
	 @param data_length
		 A <see cref="System.Int32"/> with the length of the Chunk Data.
	 
	*/
	private void ReadIHDRChunk(int data_length)
	{
		// IHDR Chunk
		//
		// 4 Bytes     Width
		// 4 Bytes     Height
		// 1 Byte      Bit depth
		// 1 Byte      Colour type
		// 1 Byte      Compression method
		// 1 Byte      Filter method
		// 1 Byte      Interlace method
		//
		// Followed by 4 Bytes CRC data

		if (data_length != 13)
		{
			throw new CorruptFileException("IHDR chunk data length must be 13");
		}

		ByteVector data = ReadChunkData(data_length);

		CheckCRC(IHDR_CHUNK_TYPE, data, ReadCRC());

		// The PNG specification limits the size of 4-byte unsigned integers to 2^31-1.
		// That allows us to safely cast them to an signed integer.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint width = data.Mid(0, 4).ToUInt(true);
		int width = data.Mid(0, 4).ToUInt(true);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint height = data.Mid(4, 4).ToUInt(true);
		int height = data.Mid(4, 4).ToUInt(true);

		if (width > Integer.MAX_VALUE || height > Integer.MAX_VALUE)
		{
			throw new CorruptFileException("PNG limits width and heigth to 2^31-1");
		}

		this.width = (int) width;
		this.height = (int) height;
	}


	/** 
		Reads an iTXt Chunk from file. The current position must be set
		to the start of the Chunk Data. Such a Chunk may contain XMP data
		or translated keywords.
	 
	 @param data_length
		A <see cref="System.Int32"/> with the length of the Chunk Data.
	 
	*/
	private void ReadiTXtChunk(int data_length)
	{
		long position = Tell;

		// iTXt Chunk
		//
		// N Bytes     Keyword
		// 1 Byte      Null Separator
		// 1 Byte      Compression Flag (0 for uncompressed data)
		// 1 Byte      Compression Method
		// N Bytes     Language Tag
		// 1 Byte      Null Separator
		// N Bytes     Translated Keyword
		// 1 Byte      Null Terminator
		// N Bytes     Txt
		//
		// Followed by 4 Bytes CRC data

		ByteVector data = ReadChunkData(data_length);

		CheckCRC(iTXt_CHUNK_TYPE, data, ReadCRC());

		// handle XMP, which has a fixed header
		if (data.StartsWith(XMP_CHUNK_HEADER))
		{
			getImageTag().AddTag(new XmpTag(data.Mid(XMP_CHUNK_HEADER.length).toString(StringType.UTF8), this));

			AddMetadataBlock(position - 8, data_length + 8 + 4);

			return;
		}

		int terminator_index;
		tangible.OutObject<Integer> tempOut_terminator_index = new tangible.OutObject<Integer>();
		String keyword = ReadKeyword(data, 0, tempOut_terminator_index);
	terminator_index = tempOut_terminator_index.argValue;

		if (terminator_index + 2 >= data_length)
		{
			throw new CorruptFileException("Compression Flag and Compression Method byte expected");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte compression_flag = data[terminator_index + 1];
		byte compression_flag = data.get(terminator_index + 1);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte compression_method = data[terminator_index + 2];
		byte compression_method = data.get(terminator_index + 2);

		//string language = ReadTerminatedString (data, terminator_index + 3, out terminator_index);
		//string translated_keyword = ReadTerminatedString (data, terminator_index + 1, out terminator_index);

		ByteVector txt_data = data.Mid(terminator_index + 1);

		if (compression_flag != 0x00)
		{
			txt_data = Decompress(compression_method, txt_data);

			// ignore unknown compression methods
			if (txt_data == null)
			{
				return;
			}
		}

		String value = txt_data.toString();
		Rasad.Core.Media.MediaMetadataManagement.Tag tempVar = GetTag(TagTypes.Png, true);
		PngTag png_tag = tempVar instanceof PngTag ? (PngTag)tempVar : null;

		if (png_tag.GetKeyword(keyword) == null)
		{
			png_tag.SetKeyword(keyword, value);
		}

		AddMetadataBlock(position - 8, data_length + 8 + 4);
	}


	/** 
		Reads an tEXt Chunk from file. The current position must be set
		to the start of the Chunk Data. Such a Chunk contains plain
		keywords.
	 
	 @param data_length
		A <see cref="System.Int32"/> with the length of the Chunk Data.
	 
	*/
	private void ReadtEXtChunk(int data_length)
	{
		long position = Tell;

		// tEXt Chunk
		//
		// N Bytes     Keyword
		// 1 Byte      Null Separator
		// N Bytes     Txt
		//
		// Followed by 4 Bytes CRC data

		ByteVector data = ReadChunkData(data_length);

		CheckCRC(tEXt_CHUNK_TYPE, data, ReadCRC());

		int keyword_terminator;
		tangible.OutObject<Integer> tempOut_keyword_terminator = new tangible.OutObject<Integer>();
		String keyword = ReadKeyword(data, 0, tempOut_keyword_terminator);
	keyword_terminator = tempOut_keyword_terminator.argValue;

		String value = data.Mid(keyword_terminator + 1).toString();

		Rasad.Core.Media.MediaMetadataManagement.Tag tempVar = GetTag(TagTypes.Png, true);
		PngTag png_tag = tempVar instanceof PngTag ? (PngTag)tempVar : null;

		if (png_tag.GetKeyword(keyword) == null)
		{
			png_tag.SetKeyword(keyword, value);
		}

		AddMetadataBlock(position - 8, data_length + 8 + 4);
	}


	/** 
		Reads an zTXt Chunk from file. The current position must be set
		to the start of the Chunk Data. Such a Chunk contains compressed
		keywords.
	 
	 @param data_length
		A <see cref="System.Int32"/> with the length of the Chunk Data.
	 
	 
		The Chunk may also contain compressed Exif data which is written
		by other tools. But, since the PNG specification does not support
		Exif data, we ignore it here.
	 
	*/
	private void ReadzTXtChunk(int data_length)
	{
		long position = Tell;

		// zTXt Chunk
		//
		// N Bytes     Keyword
		// 1 Byte      Null Separator
		// 1 Byte      Compression Method
		// N Bytes     Txt
		//
		// Followed by 4 Bytes CRC data

		ByteVector data = ReadChunkData(data_length);

		CheckCRC(zTXt_CHUNK_TYPE, data, ReadCRC());

		int terminator_index;
		tangible.OutObject<Integer> tempOut_terminator_index = new tangible.OutObject<Integer>();
		String keyword = ReadKeyword(data, 0, tempOut_terminator_index);
	terminator_index = tempOut_terminator_index.argValue;

		if (terminator_index + 1 >= data_length)
		{
			throw new CorruptFileException("Compression Method byte expected");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte compression_method = data[terminator_index + 1];
		byte compression_method = data.get(terminator_index + 1);

		ByteVector plain_data = Decompress(compression_method, data.Mid(terminator_index + 2));

		// ignore unknown compression methods
		if (plain_data == null)
		{
			return;
		}

		String value = plain_data.toString();

		Rasad.Core.Media.MediaMetadataManagement.Tag tempVar = GetTag(TagTypes.Png, true);
		PngTag png_tag = tempVar instanceof PngTag ? (PngTag)tempVar : null;

		if (png_tag.GetKeyword(keyword) == null)
		{
			png_tag.SetKeyword(keyword, value);
		}

		AddMetadataBlock(position - 8, data_length + 8 + 4);
	}


	/** 
		Save the metadata to file.
	*/
	private void SaveMetadata()
	{
		ByteVector metadata_chunks = new ByteVector();

		metadata_chunks.add(RenderXMPChunk());
		metadata_chunks.add(RenderKeywordChunks());

		// Metadata is stored after the PNG header and the IDHR chunk.
		SaveMetadata(metadata_chunks, HEADER.length + 13 + 4 + 4 + 4);
	}


	/** 
		Creates a Chunk containing the XMP data.
	 
	 @return 
		A <see cref="ByteVector"/> with the XMP data chunk
		or <see langword="null" /> if no XMP data is contained.
	 
	*/
	private ByteVector RenderXMPChunk()
	{
		// Check, if XmpTag is contained
		XmpTag xmp = getImageTag().getXmp();
		if (xmp == null)
		{
			return null;
		}

		ByteVector chunk = new ByteVector();

		// render the XMP data itself
		ByteVector xmp_data = xmp.Render();

		// TODO check uint size.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: chunk.Add(ByteVector.FromUInt((uint) xmp_data.Count + (uint) XMP_CHUNK_HEADER.Length));
		chunk.add(ByteVector.FromUInt((int) xmp_data.size() + (int) XMP_CHUNK_HEADER.length));
		chunk.add(iTXt_CHUNK_TYPE);
		chunk.add(XMP_CHUNK_HEADER);
		chunk.add(xmp_data);
		chunk.add(ComputeCRC(iTXt_CHUNK_TYPE, XMP_CHUNK_HEADER, xmp_data));

		return chunk;
	}


	/** 
		Creates a list of Chunks containing the PNG keywords
	 
	 @return 
		A <see cref="ByteVector"/> with the list of chunks, or
		or <see langword="null" /> if no PNG Keywords are contained.
	 
	*/
	private ByteVector RenderKeywordChunks()
	{
		// Check, if PngTag is contained
		Rasad.Core.Media.MediaMetadataManagement.Tag tempVar = GetTag(TagTypes.Png, true);
		PngTag png_tag = tempVar instanceof PngTag ? (PngTag)tempVar : null;
		if (png_tag == null)
		{
			return null;
		}

		ByteVector chunks = new ByteVector();

		for (Map.Entry<String, String> keyword : png_tag.entrySet())
		{
			ByteVector data = new ByteVector();
			data.add(keyword.getKey());
			data.add("\0");
			data.add(keyword.getValue());

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: chunks.Add(ByteVector.FromUInt((uint) data.Count));
			chunks.add(ByteVector.FromUInt((int) data.size()));
			chunks.add(tEXt_CHUNK_TYPE);
			chunks.add(data);
			chunks.add(ComputeCRC(tEXt_CHUNK_TYPE, data));
		}

		return chunks;
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

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Utility Stuff


	/** 
		Checks the CRC for a Chunk.
	 
	 @param chunk_type
		A <see cref="ByteVector"/> whith the Chunk type
	 
	 @param chunk_data
		A <see cref="ByteVector"/> with the Chunk data.
	 
	 @param crc_data
		A <see cref="ByteVector"/> with the read CRC data.
	 
	*/
	private static void CheckCRC(ByteVector chunk_type, ByteVector chunk_data, ByteVector crc_data)
	{
		ByteVector computed_crc = ComputeCRC(chunk_type, chunk_data);

		if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(computed_crc, crc_data))
		{
			throw new CorruptFileException(String.format("CRC check failed for %1$s Chunk (expected: 0x%1.4X, read: 0x%2.4X", chunk_type.toString(), computed_crc.ToUInt(), crc_data.ToUInt()));
		}
	}


	/** 
		Computes a 32bit CRC for the given data.
	 
	 @param datas
		A <see cref="ByteVector[]"/> with data to compute
		the CRC for.
	 
	 @return 
		A <see cref="ByteVector"/> with 4 bytes (32bit) containing the CRC.
	 
	*/
	private static ByteVector ComputeCRC(ByteVector... datas)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint crc = 0xFFFFFFFF;
		int crc = (int)0xFFFFFFFF;

		if (crc_table == null)
		{
			BuildCRCTable();
		}

		for (Rasad.Core.Media.MediaMetadataManagement.ByteVector data : datas)
		{

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (byte b in data)
			for (byte b : data)
			{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
				crc = crc_table[(crc ^ b) & 0xFF] ^ (crc >>> 8);
			}
		}

		// Invert
		return ByteVector.FromUInt(crc ^ 0xFFFFFFFF);
	}


	/** 
		Table for faster computation of CRC.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static uint[] crc_table;
	private static int[] crc_table;


	/** 
		Initializes the CRC Table.
	*/
	private static void BuildCRCTable()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint polynom = 0xEDB88320;
		int polynom = (int)0xEDB88320;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: crc_table = new uint [256];
		crc_table = new int [256];

		for (int i = 0; i < 256; i++)
		{

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint c = (uint) i;
			int c = (int) i;
			for (int k = 0; k < 8; k++)
			{
				if ((c & 0x00000001) != 0x00)
				{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
					c = polynom ^ (c >>> 1);
				}
				else
				{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
					c = c >>> 1;
				}
			}
			crc_table[i] = c;
		}
	}


	private static ByteVector Inflate(ByteVector data)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if HAVE_SHARPZIPLIB
		try (System.IO.MemoryStream out_stream = new System.IO.MemoryStream())
		{

			Rasad.Core.Compression.Zip.Compression.Inflater inflater = new Rasad.Core.Compression.Zip.Compression.Inflater();

			inflater.SetInput(data.getData());

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] buffer = new byte [1024];
			byte [] buffer = new byte [1024];
			int written_bytes;

			while ((written_bytes = inflater.Inflate(buffer)) > 0)
			{
				out_stream.Write(buffer, 0, written_bytes);
			}

			return new ByteVector(out_stream.ToArray());
		}
//#else
		return null;
//#endif
	}


//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static ByteVector Decompress(byte compression_method, ByteVector compressed_data)
	private static ByteVector Decompress(byte compression_method, ByteVector compressed_data)
	{
		// there is currently just one compression method specified
		// for PNG.
		switch (compression_method)
		{
		case 0:
			return Inflate(compressed_data);
		default:
			return null;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}