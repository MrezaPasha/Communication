package Rasad.Core.Media.MediaMetadataManagement.Image;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Codec.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009 Ruben Vermeersch
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
	A photo codec. Contains basic photo details.
*/
public abstract class Codec implements IPhotoCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Properties

	/** 
		Gets the duration of the media represented by the current
		instance.
	 
	 <value>
		A <see cref="TimeSpan" /> containing the duration of the
		media represented by the current instance.
	 </value>
	*/
	public final TimeSpan getDuration()
	{
		return TimeSpan.Zero;
	}

	/** 
		Gets the types of media represented by the current
		instance.
	 
	 <value>
		A bitwise combined <see cref="MediaTypes" /> containing
		the types of media represented by the current instance.
	 </value>
	*/
	public final MediaTypes getMediaTypes()
	{
		return MediaTypes.Photo;
	}

	/** 
		Gets a text description of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the media represented by the current instance.
	 </value>
	*/
	public abstract String getDescription();

	/** 
		Gets the width of the photo represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the width of the
		photo represented by the current instance.
	 </value>
	*/
	private int PhotoWidth;
	public final int getPhotoWidth()
	{
		return PhotoWidth;
	}
	protected final void setPhotoWidth(int value)
	{
		PhotoWidth = value;
	}

	/** 
		Gets the height of the photo represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the height of the
		photo represented by the current instance.
	 </value>
	*/
	private int PhotoHeight;
	public final int getPhotoHeight()
	{
		return PhotoHeight;
	}
	protected final void setPhotoHeight(int value)
	{
		PhotoHeight = value;
	}

	/** 
		Gets the (format specific) quality indicator of the photo
		represented by the current instance.
	 
	 <value>
		A <see cref="int" /> value indicating the quality. A value
		0 means that there was no quality indicator for the format
		or the file.
	 </value>
	*/
	private int PhotoQuality;
	public final int getPhotoQuality()
	{
		return PhotoQuality;
	}
	protected final void setPhotoQuality(int value)
	{
		PhotoQuality = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs a new <see cref="Codec" /> with the given width
		and height.
	 
	 @param width
		The width of the photo.
	 
	 @param height
		The height of the photo.
	 
	 @return 
		A new <see cref="Codec" /> instance.
	 
	*/
	public Codec(int width, int height)
	{
		this(width, height, 0);
	}

	/** 
		Constructs a new <see cref="Codec" /> with the given width
		and height.
	 
	 @param width
		The width of the photo.
	 
	 @param height
		The height of the photo.
	 
	 @param quality
		The quality indicator for the photo, if the format supports it.
	 
	 @return 
		A new <see cref="Codec" /> instance.
	 
	*/
	public Codec(int width, int height, int quality)
	{
		setPhotoWidth(width);
		setPhotoHeight(height);
		setPhotoQuality(quality);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}