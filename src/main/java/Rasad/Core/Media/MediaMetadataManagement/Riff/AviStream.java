package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// AviStream.cs:
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
	This abstract class provides basic support for parsing a raw AVI
	stream list.
*/
public abstract class AviStream
{
	/** 
		Contains the stream header.
	*/
	private AviStreamHeader header = new AviStreamHeader();

	/** 
		Contains the stream codec information.
	*/
	private ICodec codec;

	/** 
		Constructs and intializes a new instance of <see
		cref="AviStream" /> with a specified stream header.
	 
	 @param header
	   A <see cref="AviStreamHeader"/> object containing the
	   stream's header.
	 
	*/
	protected AviStream(AviStreamHeader header)
	{
		this.header = header.clone();
	}

	/** 
		Parses a stream list item.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the item's
		ID.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the item's
		data.
	 
	 @param start
		A <see cref="uint" /> value specifying the index in
		<paramref name="data" /> at which the item data begins.
	 
	 @param length
		A <see cref="uint" /> value specifying the length of the
		item.
	 
	*/
	public void ParseItem(ByteVector id, ByteVector data, int start, int length)
	{
	}

	/** 
		Gets the stream header.
	 
	 <value>
		A <see cref="AviStreamHeader" /> object containing the
		header information for the stream.
	 </value>
	*/
	public final AviStreamHeader getHeader()
	{
		return header;
	}

	/** 
		Gets the codec information.
	 
	 <value>
		A <see cref="ICodec" /> object containing the codec
		information for the stream.
	 </value>
	*/
	public final ICodec getCodec()
	{
		return codec;
	}
	protected final void setCodec(ICodec value)
	{
		this.codec = value;
	}

	/** 
		Parses a raw AVI stream list and returns the stream
		information.
	 
	 @param data
		A <see cref="ByteVector" /> object containing raw stream
		list.
	 
	 @return 
		A <see cref="AviStream" /> object containing stream
		information.
	 
	*/
	public static AviStream ParseStreamList(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}


		if (!data.StartsWith("strl"))
		{
			return null;
		}

		AviStream stream = null;
		int pos = 4;

		while (pos + 8 < data.size())
		{
			ByteVector id = data.Mid(pos, 4);
			int block_length = (int) data.Mid(pos + 4, 4).ToUInt(false);

			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(id, "strh") && stream == null)
			{
				AviStreamHeader stream_header = new AviStreamHeader(data, pos + 8);
				if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(stream_header.getType(), "vids"))
				{
					stream = new AviVideoStream(stream_header.clone());
				}
				else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(stream_header.getType(), "auds"))
				{
					stream = new AviAudioStream(stream_header.clone());
				}
			}
			else if (stream != null)
			{
				stream.ParseItem(id, data, pos + 8, block_length);
			}

			pos += block_length + 8;
		}

		return stream;
	}
}