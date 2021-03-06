package Rasad.Core.Media.MediaMetadataManagement.Jpeg;

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
	A Jpeg photo codec. Contains basic photo details.
*/
public class Codec extends Image.Codec
{

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
		return "JFIF File";
	}


	/** 
		Constructs a new <see cref="Codec" /> with the given width
		and height.
	 
	 @param width
		The width of the photo.
	 
	 @param height
		The height of the photo.
	 
	 @param quality
		The quality of the photo.
	 
	 @return 
		A new <see cref="Codec" /> instance.
	 
	*/
	public Codec(int width, int height, int quality)
	{
		super(width, height, quality);
	}
}