package Rasad.Core.Media.MediaMetadataManagement.Tiff.Rw2;

import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Tags.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Tiff.*;

//
// File.cs: Provides tagging for Panasonic Rw2 files
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Tiff.BaseTiffFile" /> to provide tagging
	for RW2 image files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/rw2", "rw2")][SupportedMimeType("image/rw2")][SupportedMimeType("taglib/raw", "raw")][SupportedMimeType("image/raw")][SupportedMimeType("image/x-raw")][SupportedMimeType("image/x-panasonic-raw")] public class File : Rasad.Core.Media.MediaMetadataManagement.Tiff.BaseTiffFile
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

	/** 
		 The JPEG file that's embedded in the RAW file.
	*/
	private Jpeg.File JpgFromRaw;
	public final Jpeg.File getJpgFromRaw()
	{
		return JpgFromRaw;
	}
	public final void setJpgFromRaw(Jpeg.File value)
	{
		JpgFromRaw = value;
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
		setMagic((short)85); // Panasonic uses 0x55
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

	/** 
		Gets a tag of a specified type from the current instance,
		optionally creating a new tag if possible.
	 
	 @param type
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value indicating the
		type of tag to read.
	 
	 @param create
		A <see cref="bool" /> value specifying whether or not to
		try and create the tag if one is not found.
	 
	 @return 
		A <see cref="Tag" /> object containing the tag that was
		found in or added to the current instance. If no
		matching tag was found and none was created, <see
		langword="null" /> is returned.
	 
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag GetTag(Rasad.Core.Media.MediaMetadataManagement.TagTypes type, boolean create)
	{
		Rasad.Core.Media.MediaMetadataManagement.Tag tag = super.GetTag(type, false);
		if (tag != null)
		{
			return tag;
		}

		if (!create || (type.getValue() & getImageTag().getAllowedTypes().getValue()) == 0)
		{
			return null;
		}

		if (type != TagTypes.TiffIFD)
		{
			return super.GetTag(type, create);
		}

		ImageTag new_tag = new IFDTag(this);
		getImageTag().AddTag(new_tag);
		return new_tag;
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
		Parses the RW2 file
	*/
	private void ReadFile()
	{
		// A RW2 file starts with a Tiff header followed by a RW2 header
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint first_ifd_offset = ReadHeader();
		int first_ifd_offset = ReadHeader();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint raw_ifd_offset = ReadAdditionalRW2Header();
		int raw_ifd_offset = ReadAdditionalRW2Header();

		ReadIFD(first_ifd_offset, 3);
		ReadIFD(raw_ifd_offset, 1);
	}

	/** 
	   Reads and validates the RW2 header started at the current position.
	 
	 @return 
		A <see cref="System.UInt32"/> with the offset to the IFD with the RAW data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint ReadAdditionalRW2Header()
	private int ReadAdditionalRW2Header()
	{
		// RW2 Header
		//
		// Seems to be 16 bytes, no idea on the meaning of these.

		ByteVector header = ReadBlock(16);

		if (header.size() != 16)
		{
			throw new CorruptFileException("Unexpected end of RW2 header");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint) Tell;
		return (int) Tell;
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
		IFDStructure structure = tag.Structure;

		Nullable<Integer> tempVar2 = structure.GetLongValue(0, (short)0x07);
		width = (int)((tempVar2 != null) ? tempVar2 : 0);
		Nullable<Integer> tempVar3 = structure.GetLongValue(0, (short)0x06);
		height = (int)((tempVar3 != null) ? tempVar3 : 0);

		String vendor = getImageTag().getMake();
		if (vendor.equals("LEICA"))
		{
			vendor = "Leica";
		}
		String desc = String.format("%1$s RAW File", vendor);

		if (width > 0 && height > 0)
		{
			return new Properties(TimeSpan.Zero, new Codec(width, height, desc));
		}

		return null;
	}

	/** 
		Creates an IFD reader to parse the file.
	 
	 @param file
		A <see cref="File"/> to read from.
	 
	 @param is_bigendian
		 A <see cref="System.Boolean"/>, it must be true, if the data of the IFD should be
		 read as bigendian, otherwise false.
	 
	 @param structure
		A <see cref="IFDStructure"/> that will be populated.
	 
	 @param base_offset
		 A <see cref="System.Int64"/> value describing the base were the IFD offsets
		 refer to. E.g. in Jpegs the IFD are located in an Segment and the offsets
		 inside the IFD refer from the beginning of this segment. So <paramref
		 name="base_offset"/> must contain the beginning of the segment.
	 
	 @param ifd_offset
		 A <see cref="System.UInt32"/> value with the beginning of the IFD relative to
		 <paramref name="base_offset"/>.
	 
	 @param max_offset
	 	A <see cref="System.UInt32"/> value with maximal possible offset. This is to limit
		 the size of the possible data;
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override Rasad.Core.Media.MediaMetadataManagement.IFD.IFDReader CreateIFDReader(BaseTiffFile file, bool is_bigendian, IFDStructure structure, long base_offset, uint ifd_offset, uint max_offset)
	@Override
	protected Rasad.Core.Media.MediaMetadataManagement.IFD.IFDReader CreateIFDReader(BaseTiffFile file, boolean is_bigendian, IFDStructure structure, long base_offset, int ifd_offset, int max_offset)
	{
		return new IFDReader(file, is_bigendian, structure, base_offset, ifd_offset, max_offset);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}