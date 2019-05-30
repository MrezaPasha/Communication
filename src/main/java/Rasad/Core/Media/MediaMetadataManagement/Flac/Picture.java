package Rasad.Core.Media.MediaMetadataManagement.Flac;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Picture.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
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
	This class implements <see cref="IPicture" /> to provide support
	for reading and writing Flac picture metadata.
*/
public class Picture implements IPicture
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the picture type.
	*/
	private PictureType type = PictureType.values()[0];

	/** 
		Contains the mime-type.
	*/
	private String mime_type;

	/** 
		Contains the description.
	*/
	private String description;

	/** 
		Contains the width.
	*/
	private int width = 0;

	/** 
		Contains the height.
	*/
	private int height = 0;

	/** 
		Contains the color depth.
	*/
	private int color_depth = 0;

	/** 
		Contains the number of indexed colors.
	*/
	private int indexed_colors = 0;

	/** 
		Contains the picture data.
	*/
	private ByteVector picture_data;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Picture" /> by reading the contents of a raw Flac
		image structure.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		Flac image.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> contains less than 32 bytes.
	 
	*/
	public Picture(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (data.size() < 32)
		{
			throw new CorruptFileException("Data must be at least 32 bytes long");
		}

		int pos = 0;
		type = PictureType.forValue(data.Mid(pos, 4).ToUInt());
		pos += 4;

		int mimetype_length = (int) data.Mid(pos, 4).ToUInt();
		pos += 4;

		mime_type = data.toString(StringType.Latin1, pos, mimetype_length);
		pos += mimetype_length;

		int description_length = (int) data.Mid(pos, 4).ToUInt();
		pos += 4;

		description = data.toString(StringType.UTF8, pos, description_length);
		pos += description_length;

		width = (int) data.Mid(pos, 4).ToUInt();
		pos += 4;

		height = (int) data.Mid(pos, 4).ToUInt();
		pos += 4;

		color_depth = (int) data.Mid(pos, 4).ToUInt();
		pos += 4;

		indexed_colors = (int) data.Mid(pos, 4).ToUInt();
		pos += 4;

		int data_length = (int) data.Mid(pos, 4).ToUInt();
		pos += 4;

		picture_data = data.Mid(pos, data_length);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Picture" /> by copying the properties of a <see
		cref="IPicture" /> object.
	 
	 @param picture
		A <see cref="IPicture" /> object to use for the new
		instance.
	 
	 @exception ArgumentNullException
		<paramref name="picture" /> is <see langword="null" />.
	 
	*/
	public Picture(IPicture picture)
	{
		if (picture == null)
		{
			throw new NullPointerException("picture");
		}

		type = picture.getType();
		mime_type = picture.getMimeType();
		description = picture.getDescription();
		picture_data = picture.getData();

		Rasad.Core.Media.MediaMetadataManagement.Flac.Picture flac_picture = picture instanceof Rasad.Core.Media.MediaMetadataManagement.Flac.Picture ? (Rasad.Core.Media.MediaMetadataManagement.Flac.Picture)picture : null;

		if (flac_picture == null)
		{
			return;
		}

		width = flac_picture.getWidth();
		height = flac_picture.getHeight();
		color_depth = flac_picture.getColorDepth();
		indexed_colors = flac_picture.getIndexedColors();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance as a raw Flac picture.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	public final ByteVector Render()
	{
		ByteVector data = new ByteVector();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) Type));
		data.add(ByteVector.FromUInt((int) getType().getValue()));

		ByteVector mime_data = ByteVector.FromString(getMimeType(), StringType.Latin1);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) mime_data.Count));
		data.add(ByteVector.FromUInt((int) mime_data.size()));
		data.add(mime_data);

		ByteVector decription_data = ByteVector.FromString(getDescription(), StringType.UTF8);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) decription_data.Count));
		data.add(ByteVector.FromUInt((int) decription_data.size()));
		data.add(decription_data);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) Width));
		data.add(ByteVector.FromUInt((int) getWidth()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) Height));
		data.add(ByteVector.FromUInt((int) getHeight()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) ColorDepth));
		data.add(ByteVector.FromUInt((int) getColorDepth()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) IndexedColors));
		data.add(ByteVector.FromUInt((int) getIndexedColors()));

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) Data.Count));
		data.add(ByteVector.FromUInt((int) getData().size()));
		data.add(getData());

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets and sets the mime-type of the picture data
		stored in the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the mime-type
		of the picture data stored in the current instance.
	 </value>
	*/
	public final String getMimeType()
	{
		return mime_type;
	}
	public final void setMimeType(String value)
	{
		mime_type = value;
	}

	/** 
		Gets and sets the type of content visible in the picture
		stored in the current instance.
	 
	 <value>
		A <see cref="PictureType" /> containing the type of
		content visible in the picture stored in the current
		instance.
	 </value>
	*/
	public final PictureType getType()
	{
		return type;
	}
	public final void setType(PictureType value)
	{
		type = value;
	}

	/** 
		Gets and sets a description of the picture stored in the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the picture stored in the current instance.
	 </value>
	*/
	public final String getDescription()
	{
		return description;
	}
	public final void setDescription(String value)
	{
		description = value;
	}

	/** 
		Gets and sets the picture data stored in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the picture
		data stored in the current instance.
	 </value>
	*/
	public final ByteVector getData()
	{
		return picture_data;
	}
	public final void setData(ByteVector value)
	{
		picture_data = value;
	}

	/** 
		Gets and sets the width of the picture in the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing width of the
		picture stored in the current instance.
	 </value>
	*/
	public final int getWidth()
	{
		return width;
	}
	public final void setWidth(int value)
	{
		width = value;
	}

	/** 
		Gets and sets the height of the picture in the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing height of the
		picture stored in the current instance.
	 </value>
	*/
	public final int getHeight()
	{
		return height;
	}
	public final void setHeight(int value)
	{
		height = value;
	}

	/** 
		Gets and sets the color depth of the picture in the
		current instance.
	 
	 <value>
		A <see cref="int" /> value containing color depth of the
		picture stored in the current instance.
	 </value>
	*/
	public final int getColorDepth()
	{
		return color_depth;
	}
	public final void setColorDepth(int value)
	{
		color_depth = value;
	}

	/** 
		Gets and sets the number of indexed colors in the picture
		in the current instance.
	 
	 <value>
		A <see cref="int" /> value containing number of indexed
		colors in the picture, or zero if the picture is not
		stored in an indexed format.
	 </value>
	*/
	public final int getIndexedColors()
	{
		return indexed_colors;
	}
	public final void setIndexedColors(int value)
	{
		indexed_colors = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}