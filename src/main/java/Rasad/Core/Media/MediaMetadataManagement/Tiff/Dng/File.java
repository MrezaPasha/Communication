package Rasad.Core.Media.MediaMetadataManagement.Tiff.Dng;

import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Tags.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Tiff.*;

//
// File.cs: Provides tagging for DNG files
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Tiff.File" /> to provide tagging
	for DNG image files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/dng", "dng")][SupportedMimeType("image/dng")][SupportedMimeType("image/x-adobe-dng")] public class File : Rasad.Core.Media.MediaMetadataManagement.Tiff.File
public class File extends Rasad.Core.Media.MediaMetadataManagement.Tiff.File
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region public Properties

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
		super(abstraction, propertiesStyle);
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

	/** 
		Attempts to extract the media properties of the main
		photo.
	 
	 @return 
		A <see cref="Properties" /> object with a best effort guess
		at the right values. When no guess at all can be made,
		<see langword="null" /> is returned.
	 
	*/
	@Override
	protected Properties ExtractProperties()
	{
		int width = 0, height = 0;

		Object tempVar = GetTag(TagTypes.TiffIFD);
		IFDTag tag = tempVar instanceof IFDTag ? (IFDTag)tempVar : null;
		IFDStructure structure = tag.getStructure();

		// DNG uses SubIFDs for images, the one with SubfileType = 0 is the RAW data.
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry tempVar2 = structure.GetEntry(0, (short) IFDEntryTag.SubIFDs.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: IFDStructure structure = tag.Structure;
		SubIFDArrayEntry sub_ifds = tempVar2 instanceof SubIFDArrayEntry ? (SubIFDArrayEntry)tempVar2 : null;
		if (sub_ifds == null)
		{
			return super.ExtractProperties();
		}

		for (Rasad.Core.Media.MediaMetadataManagement.IFD.IFDStructure entry : sub_ifds.getEntries())
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var type = entry.GetLongValue(0, (ushort) IFDEntryTag.NewSubfileType);
			Integer type = entry.GetLongValue(0, (short) IFDEntryTag.NewSubfileType.getValue());
			if (type.equals(0))
			{
				Nullable<Integer> tempVar3 = entry.GetLongValue(0, (short) IFDEntryTag.ImageWidth.getValue());
				width = (int)((tempVar3 != null) ? tempVar3 : 0);
				Nullable<Integer> tempVar4 = entry.GetLongValue(0, (short) IFDEntryTag.ImageLength.getValue());
				height = (int)((tempVar4 != null) ? tempVar4 : 0);
				break; // No need to iterate the other SubIFDs
			}
		}

		if (width > 0 && height > 0)
		{
			return new Properties(TimeSpan.Zero, CreateCodec(width, height));
		}

		// Fall back to normal detection.
		return super.ExtractProperties();
	}

	/** 
		Create a codec that describes the photo properties.
	 
	 @return 
		A <see cref="Codec" /> object.
	 
	*/
	@Override
	protected Codec CreateCodec(int width, int height)
	{
		return new Codec(width, height, "Adobe Digital Negative File");
	}
}