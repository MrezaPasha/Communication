package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// ExtendedHeader.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   id3v2extendedheader.cpp from Rasad.Core.Media.MediaMetadataManagement
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
	This class is a filler until support for reading and writing the
	ID3v2 extended header is implemented.
*/
public class ExtendedHeader implements Cloneable
{
	/** 
		Contains the size of the read header.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint size;
	private int size;

	/** 
		Constructs and initializes a new instance of <see
		cref="ExtendedHeader"/> with no contents.
	*/
	public ExtendedHeader()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ExtendedHeader" /> by reading the raw contents from
		a <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		extended header structure.
	 
	 @param version
		A <see cref="byte" /> value indicating the ID3v2 version.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ExtendedHeader(ByteVector data, byte version)
	public ExtendedHeader(ByteVector data, byte version)
	{
		Parse(data, version);
	}

	/** 
		Gets the size of the data on disk in bytes.
	 
	 <value>
		A <see cref="uint" /> value containing the size of the
		data on disk.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getSize()
	public final int getSize()
	{
		return size;
	}

	/** 
		Populates the current instance with the contents of the
		raw ID3v2 frame.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		extended header structure.
	 
	 @param version
		A <see cref="byte" /> value indicating the ID3v2 version.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected void Parse(ByteVector data, byte version)
	protected final void Parse(ByteVector data, byte version)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		size = (version == 3 ? 4 : 0) + SynchData.ToUInt(data.Mid(0, 4));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region ICloneable

	/** 
		Creates a deep copy of the current instance.
	 
	 @return 
		A new <see cref="ExtendedHeader" /> object identical to
		the current instance.
	 
	*/
	public final ExtendedHeader Clone()
	{
		ExtendedHeader header = new ExtendedHeader();
		header.size = size;
		return header;
	}

	public final Object Clone()
	{
		return Clone();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}