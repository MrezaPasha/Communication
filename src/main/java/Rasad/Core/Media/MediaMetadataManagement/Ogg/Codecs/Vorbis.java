package Rasad.Core.Media.MediaMetadataManagement.Ogg.Codecs;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Ogg.*;

//
// Vorbis.cs:
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
	This class extends <see cref="Codec" /> and implements <see
	cref="IAudioCodec" /> to provide support for processing Ogg
	Vorbis bitstreams.
*/
public class Vorbis extends Codec implements IAudioCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Static Fields

	/** 
		Contains the file identifier.
	*/
	private static ByteVector id = "vorbis";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the header packet.
	*/
	private HeaderPacket header = new HeaderPacket();

	/** 
		Contains the comment data.
	*/
	private ByteVector comment_data;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Vorbis" />.
	*/
	private Vorbis()
	{
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Reads a Ogg packet that has been encountered in the
		stream.
	 
	 @param packet
		A <see cref="ByteVector" /> object containing a packet to
		be read by the current instance.
	 
	 @param index
		A <see cref="int" /> value containing the index of the
		packet in the stream.
	 
	 @return 
		<see langword="true" /> if the codec has read all the
		necessary packets for the stream and does not need to be
		called again, typically once the Xiph comment has been
		found. Otherwise <see langword="false" />.
	 
	 @exception ArgumentNullException
		<paramref name="packet" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="index" /> is less than zero.
	 
	 @exception CorruptFileException
		The data does not conform to the specificiation for the
		codec represented by the current instance.
	 
	*/
	@Override
	public boolean ReadPacket(ByteVector packet, int index)
	{
		if (packet == null)
		{
			throw new NullPointerException("packet");
		}

		if (index < 0)
		{
			throw new IndexOutOfBoundsException("index", "index must be at least zero.");
		}

		int type = PacketType(packet);
		if (type != 1 && index == 0)
		{
			throw new CorruptFileException("Stream does not begin with vorbis header.");
		}

		if (comment_data == null)
		{
			if (type == 1)
			{
				header = new HeaderPacket(packet);
			}
			else if (type == 3)
			{
				comment_data = packet.Mid(7);
			}
			else
			{
				return true;
			}
		}

		return comment_data != null;
	}

	/** 
		Computes the duration of the stream using the first and
		last granular positions of the stream.
	 
	 @param firstGranularPosition
		A <see cref="long" /> value containing the first granular
		position of the stream.
	 
	 @param lastGranularPosition
		A <see cref="long" /> value containing the last granular
		position of the stream.
	 
	 @return 
		A <see cref="TimeSpan" /> value containing the duration
		of the stream.
	 
	*/
	@Override
	public TimeSpan GetDuration(long firstGranularPosition, long lastGranularPosition)
	{
		return header.sample_rate == 0 ? TimeSpan.Zero : TimeSpan.FromSeconds((double)(lastGranularPosition - firstGranularPosition) / (double) header.sample_rate);
	}

	/** 
		Replaces the comment packet in a collection of packets
		with the rendered version of a Xiph comment or inserts a
		comment packet if the stream lacks one.
	 
	 @param packets
		A <see cref="ByteVectorCollection" /> object containing
		a collection of packets.
	 
	 @param comment
		A <see cref="XiphComment" /> object to store the rendered
		version of in <paramref name="packets" />.
	 
	 @exception ArgumentNullException
		<paramref name="packets" /> or <paramref name="comment"
		/> is <see langword="null" />.
	 
	*/
	@Override
	public void SetCommentPacket(ByteVectorCollection packets, XiphComment comment)
	{
		if (packets == null)
		{
			throw new NullPointerException("packets");
		}

		if (comment == null)
		{
			throw new NullPointerException("comment");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector data = new ByteVector((byte) 0x03);
		ByteVector data = new ByteVector((byte) 0x03);
		data.add(id);
		data.add(comment.Render(true));
		if (packets.size() > 1 && PacketType(packets.get(1)) == 0x03)
		{
			packets.set(1, data);
		}
		else
		{
			packets.add(1, data);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets the bitrate of the audio represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing a bitrate of the
		audio represented by the current instance.
	 </value>
	*/
	public final int getAudioBitrate()
	{
		return (int)((float)header.bitrate_nominal / 1000f + 0.5);
	}

	/** 
		Gets the sample rate of the audio represented by the
		current instance.
	 
	 <value>
		A <see cref="int" /> value containing the sample rate of
		the audio represented by the current instance.
	 </value>
	*/
	public final int getAudioSampleRate()
	{
		return (int) header.sample_rate;
	}

	/** 
		Gets the number of channels in the audio represented by
		the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the number of
		channels in the audio represented by the current
		instance.
	 </value>
	*/
	public final int getAudioChannels()
	{
		return (int) header.channels;
	}

	/** 
		Gets the types of media represented by the current
		instance.
	 
	 <value>
		Always <see cref="MediaTypes.Audio" />.
	 </value>
	*/
	@Override
	public MediaTypes getMediaTypes()
	{
		return MediaTypes.Audio;
	}

	/** 
		Gets the raw Xiph comment data contained in the codec.
	 
	 <value>
		A <see cref="ByteVector" /> object containing a raw Xiph
		comment or <see langword="null"/> if none was found.
	 </value>
	*/
	@Override
	public ByteVector getCommentData()
	{
		return comment_data;
	}

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
		return String.format("Vorbis Version %1$s Audio", header.vorbis_version);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Implements the <see cref="CodecProvider" /> delegate to
		provide support for recognizing a Vorbis stream from the
		header packet.
	 
	 @param packet
		A <see cref="ByteVector" /> object containing the stream
		header packet.
	 
	 @return 
		A <see cref="Codec"/> object containing a codec capable
		of parsing the stream of <see langref="null" /> if the
		stream is not a Vorbis stream.
	 
	*/
	public static Codec FromPacket(ByteVector packet)
	{
		return (PacketType(packet) == 1) ? new Vorbis() : null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Static Methods

	/** 
		Gets the packet type for a specified Vorbis packet.
	 
	 @param packet
		A <see cref="ByteVector" /> object containing a Vorbis
		packet.
	 
	 @return 
		A <see cref="int" /> value containing the packet type or
		-1 if the packet is invalid.
	 
	*/
	private static int PacketType(ByteVector packet)
	{
		if (packet.size() <= id.size())
		{
			return -1;
		}

		for (int i = 0; i < id.size(); i++)
		{
			if (packet.get(i + 1) != id.get(i))
			{
				return -1;
			}
		}

		return packet.get(0);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

	/** 
		This structure represents a Vorbis header packet.
	*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: private struct HeaderPacket
	private final static class HeaderPacket
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint sample_rate;
		public int sample_rate;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint channels;
		public int channels;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint vorbis_version;
		public int vorbis_version;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint bitrate_maximum;
		public int bitrate_maximum;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint bitrate_nominal;
		public int bitrate_nominal;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint bitrate_minimum;
		public int bitrate_minimum;

		public HeaderPacket()
		{
		}

		public HeaderPacket(ByteVector data)
		{
			vorbis_version = data.Mid(7, 4).ToUInt(false);
			channels = data.get(11);
			sample_rate = data.Mid(12, 4).ToUInt(false);
			bitrate_maximum = data.Mid(16, 4).ToUInt(false);
			bitrate_nominal = data.Mid(20, 4).ToUInt(false);
			bitrate_minimum = data.Mid(24, 4).ToUInt(false);
		}

		public HeaderPacket clone()
		{
			HeaderPacket varCopy = new HeaderPacket();

			varCopy.sample_rate = this.sample_rate;
			varCopy.channels = this.channels;
			varCopy.vorbis_version = this.vorbis_version;
			varCopy.bitrate_maximum = this.bitrate_maximum;
			varCopy.bitrate_nominal = this.bitrate_nominal;
			varCopy.bitrate_minimum = this.bitrate_minimum;

			return varCopy;
		}
	}
}