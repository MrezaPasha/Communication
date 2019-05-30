package Rasad.Core.Media.MediaMetadataManagement.Ogg.Codecs;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Ogg.*;

//
// Theora.cs:
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
	cref="IVideoCodec" /> to provide support for processing Ogg
	Theora bitstreams.
*/
public class Theora extends Codec implements IVideoCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Static Fields

	/** 
		Contains the file identifier.
	*/
	private static ByteVector id = "theora";

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
		cref="Theora" />.
	*/
	private Theora()
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
		if (type != 0x80 && index == 0)
		{
			throw new CorruptFileException("Stream does not begin with theora header.");
		}

		if (comment_data == null)
		{
			if (type == 0x80)
			{
				header = new HeaderPacket(packet);
			}
			else if (type == 0x81)
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
		return TimeSpan.FromSeconds(header.GranuleTime(lastGranularPosition) - header.GranuleTime(firstGranularPosition));
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
//ORIGINAL LINE: ByteVector data = new ByteVector((byte) 0x81);
		ByteVector data = new ByteVector((byte) 0x81);
		data.add(id);
		data.add(comment.Render(true));

		if (packets.size() > 1 && PacketType(packets.get(1)) == 0x81)
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
		Gets the width of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the width of the
		video represented by the current instance.
	 </value>
	*/
	public final int getVideoWidth()
	{
		return header.width;
	}

	/** 
		Gets the height of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the height of the
		video represented by the current instance.
	 </value>
	*/
	public final int getVideoHeight()
	{
		return header.height;
	}

	/** 
		Gets the types of media represented by the current
		instance.
	 
	 <value>
		Always <see cref="MediaTypes.Video" />.
	 </value>
	*/
	@Override
	public MediaTypes getMediaTypes()
	{
		return MediaTypes.Video;
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
		return String.format("Theora Version %1$s.%2$s Video", header.major_version, header.minor_version);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Implements the <see cref="CodecProvider" /> delegate to
		provide support for recognizing a Theora stream from the
		header packet.
	 
	 @param packet
		A <see cref="ByteVector" /> object containing the stream
		header packet.
	 
	 @return 
		A <see cref="Codec"/> object containing a codec capable
		of parsing the stream of <see langref="null" /> if the
		stream is not a Theora stream.
	 
	*/
	public static Codec FromPacket(ByteVector packet)
	{
		return (PacketType(packet) == 0x80) ? new Theora() : null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Static Methods

	/** 
		Gets the packet type for a specified Theora packet.
	 
	 @param packet
		A <see cref="ByteVector" /> object containing a Theora
		packet.
	 
	 @return 
		A <see cref="int" /> value containing the packet type or
		-1 if the packet is invalid.
	 
	*/
	private static int PacketType(ByteVector packet)
	{
		if (packet.size() <= id.size() || packet.get(0) < 0x80)
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
		This structure represents a Theora header packet.
	*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: private struct HeaderPacket
	private final static class HeaderPacket
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte major_version;
		public byte major_version;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte minor_version;
		public byte minor_version;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte revision_version;
		public byte revision_version;
		public int width;
		public int height;
		public int fps_numerator;
		public int fps_denominator;
		public int keyframe_granule_shift;

		public HeaderPacket()
		{
		}

		public HeaderPacket(ByteVector data)
		{
			major_version = data.get(7);
			minor_version = data.get(8);
			revision_version = data.get(9);
			// width = data.Mid (10, 2).ToShort () << 4;
			// height = data.Mid (12, 2).ToShort () << 4;
			width = (int) data.Mid(14, 3).ToUInt(); // Frame Width.
			height = (int) data.Mid(17, 3).ToUInt(); // Frame Height.
			// Offset X.
			// Offset Y.
			fps_numerator = (int) data.Mid(22, 4).ToUInt();
			fps_denominator = (int) data.Mid(26, 4).ToUInt();
			// Aspect Numerator.
			// Aspect Denominator.
			// Colorspace.
			// Target bitrate.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort last_bits = data.Mid(40, 2).ToUShort();
			short last_bits = data.Mid(40, 2).ToUShort();
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			keyframe_granule_shift = (last_bits >>> 5) & 0x1F;
		}

		/** 
			Converts an absolute granular position into a
			seconds.
		 
		 @param granularPosition
			A <see cref="long" /> value containing the
		   absolute granular position.
		 
		 @return 
			A <see cref="double" /> value containing the time
			at <paramref name="granularPosition" /> in
			seconds.
		 
			Many thanks to the good people at
			irc://irc.freenode.net#theora for making this
			code a reality.
		 
		*/
		public double GranuleTime(long granularPosition)
		{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			long iframe = granularPosition >> keyframe_granule_shift;
			long pframe = granularPosition - (iframe << keyframe_granule_shift);
			return (iframe + pframe) * ((double) fps_denominator / (double) fps_numerator);
		}

		public HeaderPacket clone()
		{
			HeaderPacket varCopy = new HeaderPacket();

			varCopy.major_version = this.major_version;
			varCopy.minor_version = this.minor_version;
			varCopy.revision_version = this.revision_version;
			varCopy.width = this.width;
			varCopy.height = this.height;
			varCopy.fps_numerator = this.fps_numerator;
			varCopy.fps_denominator = this.fps_denominator;
			varCopy.keyframe_granule_shift = this.keyframe_granule_shift;

			return varCopy;
		}
	}
}