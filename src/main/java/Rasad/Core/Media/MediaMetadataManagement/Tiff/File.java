package Rasad.Core.Media.MediaMetadataManagement.Tiff;

import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Tags.*;
import Rasad.Core.Media.MediaMetadataManagement.Xmp.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// File.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//
// Copyright (C) 2009 Ruben Vermeersch
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
	and properties support for Tiff files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/tiff", "tiff")][SupportedMimeType("taglib/tif", "tif")][SupportedMimeType("image/tiff")] public class File : BaseTiffFile
public class File extends BaseTiffFile
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the media properties.
	*/
	private Properties properties;

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
		super(path);
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
		setImageTag(new CombinedImageTag(TagTypes.TiffIFD.getValue() | TagTypes.XMP.getValue()));

		Mode = AccessMode.Read;
		try
		{
			Read(propertiesStyle);
			TagTypesOnDisk = TagTypes;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}
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
		super(abstraction);
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
		Saves the changes made in the current instance to the
		file it represents.
	*/
	@Override
	public void Save()
	{
		Mode = AccessMode.Write;
		try
		{
			WriteFile();

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
		Render the whole file and write it back.
	*/
	private void WriteFile()
	{
		// Check, if IFD0 is contained
		IFDTag exif = getImageTag().getExif();
		if (exif == null)
		{
			throw new RuntimeException("Tiff file without tags");
		}

		UpdateTags(exif);

		// first IFD starts at 8
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint first_ifd_offset = 8;
		int first_ifd_offset = 8;
		ByteVector data = RenderHeader(first_ifd_offset);

		IFDRenderer renderer = new IFDRenderer(getIsBigEndian(), exif.getStructure(), first_ifd_offset);

		data.add(renderer.Render());

		Insert(data, 0, Length);
	}

	/** 
		Update the XMP stored in the Tiff IFD
	 
	 @param exif
		A <see cref="IFDTag"/> The Tiff IFD to update the entries
	 
	*/
	private void UpdateTags(IFDTag exif)
	{
		// update the XMP entry
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: exif.Structure.RemoveTag(0, (ushort) IFDEntryTag.XMP);
		exif.getStructure().RemoveTag(0, (short) IFDEntryTag.XMP.getValue());

		XmpTag xmp = getImageTag().getXmp();
		if (xmp != null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: exif.Structure.AddEntry(0, new ByteVectorIFDEntry((ushort) IFDEntryTag.XMP, xmp.Render()));
			exif.getStructure().AddEntry(0, new ByteVectorIFDEntry((short) IFDEntryTag.XMP.getValue(), xmp.Render()));
		}
	}

	/** 
		Reads the file with a specified read style.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	protected final void Read(ReadStyle propertiesStyle)
	{
		Mode = AccessMode.Read;
		try
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint first_ifd_offset = ReadHeader();
			int first_ifd_offset = ReadHeader();
			ReadIFD(first_ifd_offset);

			// Find XMP data
			Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry tempVar = getImageTag().getExif().getStructure().GetEntry(0, (short) IFDEntryTag.XMP.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ReadIFD(first_ifd_offset);
			ByteVectorIFDEntry xmp_entry = tempVar instanceof ByteVectorIFDEntry ? (ByteVectorIFDEntry)tempVar : null;
			if (xmp_entry != null)
			{
				getImageTag().AddTag(new XmpTag(xmp_entry.getData().toString(), this));
			}

			if (propertiesStyle == ReadStyle.None)
			{
				return;
			}

			properties = ExtractProperties();
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
	protected Properties ExtractProperties()
	{
		int width = 0, height = 0;

		Object tempVar = GetTag(TagTypes.TiffIFD);
		IFDTag tag = tempVar instanceof IFDTag ? (IFDTag)tempVar : null;
		IFDStructure structure = tag.getStructure();

		Nullable<Integer> tempVar2 = structure.GetLongValue(0, (short) IFDEntryTag.ImageWidth.getValue());
		width = (int)((tempVar2 != null) ? tempVar2 : 0);
		Nullable<Integer> tempVar3 = structure.GetLongValue(0, (short) IFDEntryTag.ImageLength.getValue());
		height = (int)((tempVar3 != null) ? tempVar3 : 0);

		if (width > 0 && height > 0)
		{
			return new Properties(TimeSpan.Zero, CreateCodec(width, height));
		}

		return null;
	}

	/** 
		Create a codec that describes the photo properties.
	 
	 @return 
		A <see cref="Codec" /> object.
	 
	*/
	protected Codec CreateCodec(int width, int height)
	{
		return new Codec(width, height);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}