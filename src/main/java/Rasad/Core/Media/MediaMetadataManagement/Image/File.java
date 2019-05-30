package Rasad.Core.Media.MediaMetadataManagement.Image;

import Rasad.Core.Media.MediaMetadataManagement.Jpeg.*;
import Rasad.Core.Media.MediaMetadataManagement.Gif.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;
import Rasad.Core.Media.MediaMetadataManagement.Xmp.*;
import Rasad.Core.Media.MediaMetadataManagement.Png.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// File.cs: Base class for Image types.
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> to provide basic
	functionality common to all image types.
*/
public abstract class File extends Rasad.Core.Media.MediaMetadataManagement.File
{
	private CombinedImageTag image_tag;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

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
	protected File(String path)
	{
		super(path);
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
		Gets a abstract representation of all tags stored in the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> object representing all tags
		stored in the current instance.
	 </value>
	*/
	@Override
	public Tag getTag()
	{
		return getImageTag();
	}

	/** 
		Gets a abstract representation of all tags stored in the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Image.CombinedImageTag" /> object
		representing all image tags stored in the current instance.
	 </value>
	*/
	public final CombinedImageTag getImageTag()
	{
		return image_tag;
	}
	protected final void setImageTag(CombinedImageTag value)
	{
		image_tag = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		The method creates all tags which are allowed for the current
		instance of the image file. This method can be used to ensure,
		that all tags are in place and properties can be safely used
		to set values.
	*/
	public final void EnsureAvailableTags()
	{
		for (TagTypes type : TagTypes.values())
		{
			if ((type.getValue() & getImageTag().getAllowedTypes().getValue()) != 0x00 && type != TagTypes.AllTags)
			{
				GetTag(type, true);
			}
		}
	}

	/** 
		Removes a set of tag types from the current instance.
	 
	 @param types
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value
		containing tag types to be removed from the file.
	 
	 
		In order to remove all tags from a file, pass <see
		cref="TagTypes.AllTags" /> as <paramref name="types" />.
	 
	*/
	@Override
	public void RemoveTags(Rasad.Core.Media.MediaMetadataManagement.TagTypes types)
	{
		ArrayList<ImageTag> to_delete = new ArrayList<ImageTag> ();

		for (ImageTag tag : getImageTag().getAllTags())
		{
			if ((tag.getTagTypes().getValue() & types.getValue()) == tag.getTagTypes().getValue())
			{
				to_delete.add(tag);
			}
		}

		for (ImageTag tag : to_delete)
		{
			getImageTag().RemoveTag(tag);
		}
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
		for (Tag tag : getImageTag().getAllTags())
		{
			if ((tag.getTagTypes().getValue() & type.getValue()) == type.getValue())
			{
				return tag;
			}
		}

		if (!create || (type.getValue() & getImageTag().getAllowedTypes().getValue()) == 0)
		{
			return null;
		}

		ImageTag new_tag = null;
		switch (type)
		{
		case TagTypes.JpegComment:
			new_tag = new JpegCommentTag();
			break;

		case TagTypes.GifComment:
			new_tag = new GifCommentTag();
			break;

		case TagTypes.Png:
			new_tag = new PngTag();
			break;

		case TagTypes.TiffIFD:
			new_tag = new IFDTag();
			break;

		case TagTypes.XMP:
			new_tag = new XmpTag();
			break;
		}

		if (new_tag != null)
		{
			getImageTag().AddTag(new_tag);
			return new_tag;
		}

		throw new UnsupportedOperationException(String.format("Adding tag of type %1$s not supported!", type));
	}

	/** 
	 	Copies metadata from the given file..
	 
	 <param name='file'>
	 	File to copy metadata from.
	 
	*/
	public final void CopyFrom(Rasad.Core.Media.MediaMetadataManagement.Image.File file)
	{
		EnsureAvailableTags();
		Rasad.Core.Media.MediaMetadataManagement.Image.CombinedImageTag from_tag = file.getImageTag();
		Rasad.Core.Media.MediaMetadataManagement.Image.CombinedImageTag to_tag = getImageTag();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		for (var prop : Rasad.Core.Media.MediaMetadataManagement.Image.ImageTag.class.GetProperties())
		{
			if (!prop.CanWrite || prop.Name.equals("TagTypes"))
			{
				continue;
			}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var value = prop.GetValue(from_tag, null);
			prop.SetValue(to_tag, value, null);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}