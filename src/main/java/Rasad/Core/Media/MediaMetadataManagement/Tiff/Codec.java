package Rasad.Core.Media.MediaMetadataManagement.Tiff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Codec.cs:
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
	A TIFF photo codec. Contains basic photo details.
*/
public class Codec extends Image.Codec
{
	private String description = "TIFF File";

	/** 
		Gets a text description of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the media represented by the current instance.
	 </value>
	*/
	@Override
	public String getDescription()
	{
		return description;
	}


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
		super(width, height);
	}

	/** 
		Constructs a new <see cref="Codec" /> with the given width
		and height.
	 
	 @param width
		The width of the photo.
	 
	 @param height
		The height of the photo.
	 
	 @param description
		The description of the photo type.
	 
	 @return 
		A new <see cref="Codec" /> instance.
	 
	*/
	public Codec(int width, int height, String description)
	{
		super(width, height);
		this.description = description;
	}
}