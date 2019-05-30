package Rasad.Core.Media.MediaMetadataManagement.Ogg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Bitstream.cs:
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
	This class accepts a sequence of pages belonging to a single
	logical bitstream, processes them, and extracts the tagging and
	media information.
*/
public class Bitstream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the last packet of the previous page in case it
		is continued in the next frame.
	*/
	private ByteVector previous_packet;

	/** 
		Contains the index of the next packet to be processed.
	*/
	private int packet_index;

	/** 
		Contains the codec object used to process pages.
	*/
	private Codec codec;

	/** 
		Contains the absolute granular position of the first
		page.
	*/
	private long first_absolute_granular_position;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Bitstream" /> capable of processing a specified
		page.
	 
	 @param page
		The first <see cref="Page" /> object of the stream to be
		processed by the new instance.
	 
	 
		The constructor only sets the new instance up to read the
		packet, but doesn't actually read it.
	 
	 @exception ArgumentNullException
		<paramref name="page" /> is <see langword="null" />.
	 
	 @exception UnsupportedFormatException
		No registered codec capable of processing <paramref
		name="page" /> could be found.
	 
	*/
	public Bitstream(Page page)
	{
		if (page == null)
		{
			throw new NullPointerException("page");
		}

		// Assume that the first packet is completely enclosed.
		// This should be sufficient for codec recognition.
		codec = getCodec().GetCodec(page.getPackets() [0]);

		first_absolute_granular_position = page.getHeader().getAbsoluteGranularPosition();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Reads the next logical page in the stream.
	 
	 @param page
		The next logical <see cref="Page" /> object in the
		stream.
	 
	 @return 
		<see langword="true" /> if the codec has read all the
		necessary packets for the stream and does not need to be
		called again, typically once the Xiph comment has been
		found. Otherwise <see langword="false" />.
	 
	 @exception ArgumentNullException
		<paramref name="page" /> is <see langword="null" />.
	 
	*/
	public final boolean ReadPage(Page page)
	{
		if (page == null)
		{
			throw new NullPointerException("page");
		}

		ByteVector[] packets = page.getPackets();

		for (int i = 0; i < packets.length; i++)
		{
			if ((page.getHeader().getFlags().getValue() & PageFlags.FirstPacketContinued.getValue()) == 0 && previous_packet != null)
			{
				if (ReadPacket(previous_packet))
				{
					return true;
				}
				previous_packet = null;
			}


			ByteVector packet = packets [i];

			// If we're at the first packet of the page, and
			// we're continuing an old packet, combine the
			// old with the new.
			if (i == 0 && (page.getHeader().getFlags().getValue() & PageFlags.FirstPacketContinued.getValue()) != 0 && previous_packet != null)
			{
				previous_packet.add(packet);
				packet = previous_packet;
			}

			previous_packet = null;

			if (i == packets.length - 1)
			{
				// If we're at the last packet of the
				// page, store it.
				previous_packet = new ByteVector(packet);
			}
			else if (ReadPacket(packet))
			{
				// Otherwise, we need to process it.
				return true;
			}
		}

		return false;
	}

	/** 
		Gets the duration of the stream represented by the
		current instance.
	 
	 @param lastAbsoluteGranularPosition
		A <see cref="long" /> value containing the absolute
		granular position of the last page in the bitstream.
	 
	 @return 
		A <see cref="TimeSpan" /> object containing the duration
		of the stream represented by the current instance.
	 
	*/
	public final TimeSpan GetDuration(long lastAbsoluteGranularPosition)
	{
		return codec.GetDuration(first_absolute_granular_position, lastAbsoluteGranularPosition);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets the codec object used to interpret the stream
		represented by the current instance.
	 
	 <value>
		The <see cref="Codec" /> object used by the current
		instance.
	 </value>
	*/
	public final Codec getCodec()
	{
		return codec;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Sents a packet to the codec processor to read it.
	 
	 @param packet
		A <see cref="ByteVector" /> object containing the next
		packet in the stream.
	 
	 @return 
		<see langword="true" /> if the codec has read all the
		necessary packets for the stream and does not need to be
		called again, typically once the Xiph comment has been
		found. Otherwise <see langword="false" />.
	 
	*/
	private boolean ReadPacket(ByteVector packet)
	{
		return codec.ReadPacket(packet, packet_index++);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}