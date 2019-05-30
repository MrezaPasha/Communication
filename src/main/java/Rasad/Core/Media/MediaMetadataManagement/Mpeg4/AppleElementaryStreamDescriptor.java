package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// AppleElementaryStreamDescriptor.cs: Provides an implementation of an Apple
// ItemListBox.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2006-2007 Brian Nickel
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
	This class extends <see cref="FullBox" /> to provide an
	implementation of an Apple ElementaryStreamDescriptor.
 
 
	This box may appear as a child of a <see
	cref="IsoAudioSampleEntry" /> and provided further information
	about an audio stream.
 
*/
public class AppleElementaryStreamDescriptor extends FullBox
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the stream ID.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort es_id;
	private short es_id;

	/** 
		Contains the stream priority.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte stream_priority;
	private byte stream_priority;

	/** 
		Contains the object type ID.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte object_type_id;
	private byte object_type_id;

	/** 
		Contains the stream type.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte stream_type;
	private byte stream_type;

	/** 
		Contains the bugger size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint buffer_size_db;
	private int buffer_size_db;

	/** 
		Contains the maximum bitrate.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint max_bitrate;
	private int max_bitrate;

	/** 
		Contains the average bitrate.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint average_bitrate;
	private int average_bitrate;

	/** 
		Contains the decoder config.
	*/
	private ByteVector decoder_config;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="AppleElementaryStreamDescriptor" /> with a provided
		header and handler by reading the contents from a
		specified file.
	 
	 @param header
		A <see cref="BoxHeader" /> object containing the header
		to use for the new instance.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to read the contents
		of the box from.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		Valid data could not be read.
	 
	*/
	public AppleElementaryStreamDescriptor(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), file, handler);
		int offset = 0;
		ByteVector box_data = file.ReadBlock(getDataSize());
		decoder_config = new ByteVector();

		// Elementary Stream Descriptor Tag
		if (box_data.get(offset++) == 3)
		{
			// We have a descriptor tag. Check that it's at
			// least 20 long.
			tangible.RefObject<Integer> tempRef_offset = new tangible.RefObject<Integer>(offset);
			if (ReadLength(box_data, tempRef_offset) < 20)
			{
			offset = tempRef_offset.argValue;
				throw new CorruptFileException("Insufficient data present.");
			}
		else
		{
			offset = tempRef_offset.argValue;
		}

			es_id = box_data.Mid(offset, 2).ToUShort();
			offset += 2;
			stream_priority = box_data.get(offset++);
		}
		else
		{
			// The tag wasn't found, so the next two byte
			// are the ID, and after that, business as
			// usual.
			es_id = box_data.Mid(offset, 2).ToUShort();
			offset += 2;
		}

		// Verify that the next data is the Decoder
		// Configuration Descriptor Tag and escape if it won't
		// work out.
		if (box_data.get(offset++) != 4)
		{
			throw new CorruptFileException("Could not identify decoder configuration descriptor.");
		}

		// Check that it's at least 15 long.
		tangible.RefObject<Integer> tempRef_offset2 = new tangible.RefObject<Integer>(offset);
		if (ReadLength(box_data, tempRef_offset2) < 15)
		{
		offset = tempRef_offset2.argValue;
			throw new CorruptFileException("Could not read data. Too small.");
		}
	else
	{
		offset = tempRef_offset2.argValue;
	}

		// Read a lot of good info.
		object_type_id = box_data.get(offset++);
		stream_type = box_data.get(offset++);
		buffer_size_db = box_data.Mid(offset, 3).ToUInt();
		offset += 3;
		max_bitrate = box_data.Mid(offset, 4).ToUInt();
		offset += 4;
		average_bitrate = box_data.Mid(offset, 4).ToUInt();
		offset += 4;

		// Verify that the next data is the Decoder Specific
		// Descriptor Tag and escape if it won't work out.
		if (box_data.get(offset++) != 5)
		{
			throw new CorruptFileException("Could not identify decoder specific descriptor.");
		}

		// The rest of the info is decoder specific.
		tangible.RefObject<Integer> tempRef_offset3 = new tangible.RefObject<Integer>(offset);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint length = ReadLength(box_data, ref offset);
		int length = ReadLength(box_data, tempRef_offset3);
	offset = tempRef_offset3.argValue;
		decoder_config = box_data.Mid(offset, (int) length);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the ID of the stream described by the current
		instance.
	 
	 <value>
		A <see cref="ushort" /> value containing the ID of the
		stream described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getStreamId()
	public final short getStreamId()
	{
		return es_id;
	}

	/** 
		Gets the priority of the stream described by the current
		instance.
	 
	 <value>
		A <see cref="byte" /> value containing the priority of
		the stream described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getStreamPriority()
	public final byte getStreamPriority()
	{
		return stream_priority;
	}

	/** 
		Gets the object type ID of the stream described by the
		current instance.
	 
	 <value>
		A <see cref="byte" /> value containing the object type ID
		of the stream described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getObjectTypeId()
	public final byte getObjectTypeId()
	{
		return object_type_id;
	}

	/** 
		Gets the type the stream described by the current
		instance.
	 
	 <value>
		A <see cref="byte" /> value containing the type the
		stream described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getStreamType()
	public final byte getStreamType()
	{
		return stream_type;
	}

	/** 
		Gets the buffer size DB value the stream described by the
		current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the buffer size DB
		value the stream described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getBufferSizeDB()
	public final int getBufferSizeDB()
	{
		return buffer_size_db;
	}

	/** 
		Gets the maximum bitrate the stream described by the
		current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the maximum
		bitrate the stream described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getMaximumBitrate()
	public final int getMaximumBitrate()
	{
		return max_bitrate / 1000;
	}

	/** 
		Gets the maximum average the stream described by the
		current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the average
		bitrate the stream described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getAverageBitrate()
	public final int getAverageBitrate()
	{
		return average_bitrate / 1000;
	}

	/** 
		Gets the decoder config data of stream described by the
		current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the decoder
		config data of the stream described by the current
		instance.
	 </value>
	*/
	public final ByteVector getDecoderConfig()
	{
		return decoder_config;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Reads a section length and updates the offset to the end
		of of the length block.
	 
	 @param data
		A <see cref="ByteVector" /> object to read from.
	 
	 @param offset
		A <see cref="int" /> value reference specifying the
		offset at which to read. This value gets updated to the
		position following the size data.
	 
	 @return 
		A <see cref="uint" /> value containing the length that
		was read.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static uint ReadLength(ByteVector data, ref int offset)
	private static int ReadLength(ByteVector data, tangible.RefObject<Integer> offset)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte b;
		byte b;
		int end = offset.argValue + 4;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint length = 0;
		int length = 0;

		do
		{
			b = data.get(offset.argValue++);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: length = (uint)(length << 7) | (uint)(b & 0x7f);
			length = (int)(length << 7) | (int)(b & 0x7f);
		} while ((b & 0x80) != 0 && offset.argValue <= end);

		return length;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}