package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// AviHeaderList.cs:
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
	This class provides support for reading an AVI header list to
	extract stream information.
*/
public class AviHeaderList
{
	/** 
		Contains the AVI header.
	*/
	private AviHeader header = new AviHeader();

	/** 
		Contains the AVI codec information.
	*/
	private ArrayList<ICodec> codecs = new ArrayList<ICodec> ();

	/** 
		Constructs and initializes a new instance of <see
		cref="AviHeaderList" /> by reading the contents of a raw
		RIFF list from a specified position in a <see
		cref="Rasad.Core.Media.MediaMetadataManagement.File"/>.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		from which the contents of the new instance is to be
		read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read the list.
	 
	 @param length
		A <see cref="int" /> value specifying the number of bytes
		to read.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	 @exception CorruptFileException
		The list does not contain an AVI header or the AVI header
		is the wrong length.
	 
	*/
	public AviHeaderList(Rasad.Core.Media.MediaMetadataManagement.File file, long position, int length)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (length < 0)
		{
			throw new IndexOutOfBoundsException("length");
		}

		if (position < 0 || position > file.getLength() - length)
		{
			throw new IndexOutOfBoundsException("position");
		}

		List list = new List(file, position, length);

		if (!list.containsKey("avih"))
		{
			throw new CorruptFileException("Avi header not found.");
		}

		ByteVector header_data = list.get("avih")[0];
		if (header_data.size() != 0x38)
		{
			throw new CorruptFileException("Invalid header length.");
		}

		header = new AviHeader(header_data, 0);

		for (ByteVector list_data : list.get("LIST"))
		{
			if (list_data.StartsWith("strl"))
			{
				codecs.Add(AviStream.ParseStreamList(list_data).getCodec());
			}
		}
	}

	/** 
		Gets the header for the current instance.
	 
	 <value>
		A <see cref="AviHeader" /> object containing the header
		for the current instance.
	 </value>
	*/
	public final AviHeader getHeader()
	{
		return header;
	}

	/** 
		Gets the codecs contained in the current instance.
	 
	 <value>
		A <see cref="ICodec[]" /> containing the codecs contained
		in the current instance.
	 </value>
	*/
	public final ICodec[] getCodecs()
	{
		return codecs.ToArray();
	}
}