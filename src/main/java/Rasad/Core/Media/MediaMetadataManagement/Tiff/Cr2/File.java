package Rasad.Core.Media.MediaMetadataManagement.Tiff.Cr2;

import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Tags.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Tiff.*;

//
// File.cs: Provides tagging for Canon CR2 files
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Tiff.BaseTiffFile" /> to provide tagging
	for CR2 image files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/cr2", "cr2")][SupportedMimeType("image/cr2")][SupportedMimeType("image/x-canon-cr2")] public class File : Rasad.Core.Media.MediaMetadataManagement.Tiff.BaseTiffFile
public class File extends Rasad.Core.Media.MediaMetadataManagement.Tiff.BaseTiffFile
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region private fields

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

	/** 
		Indicates if tags can be written back to the current file or not
	 
	 <value>
		A <see cref="bool" /> which is true if tags can be written to the
		current file, otherwise false.
	 </value>
	*/
	@Override
	public boolean getWriteable()
	{
		return false;
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
		throw new UnsupportedOperationException();
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
			setImageTag(new CombinedImageTag(TagTypes.TiffIFD));

			ReadFile();

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
		Parses the CR2 file
	*/
	private void ReadFile()
	{
		// A CR2 file starts with a Tiff header followed by a CR2 header
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint first_ifd_offset = ReadHeader();
		int first_ifd_offset = ReadHeader();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint raw_ifd_offset = ReadAdditionalCR2Header();
		int raw_ifd_offset = ReadAdditionalCR2Header();

		ReadIFD(first_ifd_offset, 3);
		ReadIFD(raw_ifd_offset, 1);
	}

	/** 
	   Reads and validates the CR2 header started at the current position.
	 
	 @return 
		A <see cref="System.UInt32"/> with the offset to the IFD with the RAW data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint ReadAdditionalCR2Header()
	private int ReadAdditionalCR2Header()
	{
		// CR2 Header
		//
		// CR2 Information:
		//
		// 2 bytes         CR2 Magic word (CR)
		// 1 byte          CR2 major version (2)
		// 1 byte          CR2 minor version (0)
		// 4 bytes         Offset to RAW IFD
		//

		ByteVector header = ReadBlock(8);

		if (header.size() != 8)
		{
			throw new CorruptFileException("Unexpected end of CR2 header");
		}

		if (!header.Mid(0, 2).toString().equals("CR"))
		{
			throw new CorruptFileException("CR2 Magic (CR) expected");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte major_version = header [2];
		byte major_version = header.get(2);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte minor_version = header [3];
		byte minor_version = header.get(3);

		if (major_version != 2 || minor_version != 0)
		{
			throw new UnsupportedFormatException("Only major version 2 and minor version 0 are supported");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint raw_ifd_offset = header.Mid(4, 4).ToUInt(IsBigEndian);
		int raw_ifd_offset = header.Mid(4, 4).ToUInt(getIsBigEndian());

		return raw_ifd_offset;
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
		int width = 0, height = 0;

		Object tempVar = GetTag(TagTypes.TiffIFD);
		IFDTag tag = tempVar instanceof IFDTag ? (IFDTag)tempVar : null;

		Nullable<Integer> tempVar2 = tag.getExifIFD().GetLongValue(0, (short) ExifEntryTag.PixelXDimension.getValue());
		width = (int)((tempVar2 != null) ? tempVar2 : 0);
		Nullable<Integer> tempVar3 = tag.getExifIFD().GetLongValue(0, (short) ExifEntryTag.PixelYDimension.getValue());
		height = (int)((tempVar3 != null) ? tempVar3 : 0);

		if (width > 0 && height > 0)
		{
			return new Properties(TimeSpan.Zero, new Codec(width, height, "Canon RAW File"));
		}

		return null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion


}