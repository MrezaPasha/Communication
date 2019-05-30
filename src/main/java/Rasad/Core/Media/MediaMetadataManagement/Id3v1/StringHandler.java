package Rasad.Core.Media.MediaMetadataManagement.Id3v1;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// StringHandler.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   id3v1tag.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2002,2003 Scott Wheeler (Original Implementation)
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
	This class provides a mechanism for customizing how Id3v1 text
	is read and written.
*/
public class StringHandler
{
	/** 
		Converts raw ID3v1 text data to a <see cref="string" />
		object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing raw Id3v1
		text data.
	 
	 @return 
		A <see cref="string"/> object containing the converted
		text.
	 
	*/
	public String Parse(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		String output = data.toString(StringType.Latin1).trim();
		int i = output.indexOf('\0');
		return (i >= 0) ? output.substring(0, i) : output;
	}

	/** 
		Converts a <see cref="string" /> object to raw ID3v1 text
		data.
	 
	 @param text
		A <see cref="string" /> object to convert.
	 
	 @return 
		A <see cref="ByteVector"/> containing the raw ID3v1 text
		data.
	 
	*/
	public ByteVector Render(String text)
	{
		return ByteVector.FromString(text, StringType.Latin1);
	}
}