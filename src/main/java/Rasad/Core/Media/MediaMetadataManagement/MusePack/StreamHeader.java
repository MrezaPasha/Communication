package Rasad.Core.Media.MediaMetadataManagement.MusePack;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// StreamHeader.cs: Provides support for reading MusePack audio properties.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   mpcproperties.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2006-2007 Brian Nickel
// Copyright (C) 2004 by Allan Sandfeld Jensen (Original Implementation)
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
	This struct implements <see cref="IAudioCodec" /> to provide
	support for reading MusePack audio properties.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct StreamHeader : IAudioCodec
public final class StreamHeader implements IAudioCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constants

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static ushort [] sftable = {44100, 48000, 37800, 32000};
	private static short [] sftable = {(short)44100, (short)48000, (short)37800, 32000};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the number of bytes in the stream.
	*/
	private long stream_length;

	/** 
		Contains the MusePack version.
	*/
	private int version;

	/** 
		Contains additional header information.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint header_data;
	private int header_data;

	/** 
		Contains the sample rate of the stream.
	*/
	private int sample_rate;

	/** 
		Contains the number of frames in the stream.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint frames;
	private int frames;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Fields

	/** 
		The size of a MusePack header.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint Size = 56;
	public static final int Size = 56;

	/** 
		The identifier used to recognize a WavPack file.
	 
	 <value>
		"MP+"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "MP+";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="StreamHeader" /> for a specified header block and
		stream length.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the stream
		header data.
	 
	 @param streamLength
		A <see cref="long" /> value containing the length of the
		MusePAck stream in bytes.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> does not begin with <see
		cref="FileIdentifier" /> or is less than <see cref="Size"
		/> bytes long.
	 
	*/
	public StreamHeader()
	{
	}

	public StreamHeader(ByteVector data, long streamLength)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (!data.StartsWith(FileIdentifier))
		{
			throw new CorruptFileException("Data does not begin with identifier.");
		}

		if (data.size() < Size)
		{
			throw new CorruptFileException("Insufficient data in stream header");
		}

		stream_length = streamLength;
		version = data.get(3) & 15;

		if (version >= 7)
		{
			frames = data.Mid(4, 4).ToUInt(false);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint flags = data.Mid(8, 4).ToUInt(false);
			int flags = data.Mid(8, 4).ToUInt(false);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			sample_rate = sftable [(int)(((flags >>> 17) & 1) * 2 + ((flags >>> 16) & 1))];
			header_data = 0;
		}
		else
		{
			header_data = data.Mid(0, 4).ToUInt(false);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			version = (int)((header_data >>> 11) & 0x03ff);
			sample_rate = 44100;
			frames = data.Mid(4, version >= 5 ? 4 : 2).ToUInt(false);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the duration of the media represented by the current
		instance.
	 
	 <value>
		A <see cref="TimeSpan" /> containing the duration of the
		media represented by the current instance.
	 </value>
	*/

	public TimeSpan getDuration()
	{
		if (sample_rate <= 0 && stream_length <= 0)
		{
			return TimeSpan.Zero;
		}

		return TimeSpan.FromSeconds((double)(frames * 1152 - 576) / (double) sample_rate + 0.5);
	}

	/** 
		Gets the types of media represented by the current
		instance.
	 
	 <value>
		Always <see cref="MediaTypes.Audio" />.
	 </value>
	*/
	public MediaTypes getMediaTypes()
	{
		return MediaTypes.Audio;
	}

	/** 
		Gets a text description of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the media represented by the current instance.
	 </value>
	*/
	public String getDescription()
	{
		return String.format(System.Globalization.CultureInfo.InvariantCulture, "MusePack Version %1$s Audio", getVersion());
	}

	/** 
		Gets the bitrate of the audio represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing a bitrate of the
		audio represented by the current instance.
	 </value>
	*/
	public int getAudioBitrate()
	{
		if (header_data != 0)
		{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			return (int)((header_data >>> 23) & 0x01ff);
		}

		return (int)(getDuration() > TimeSpan.Zero ? ((stream_length * 8L) / getDuration().TotalSeconds) / 1000 : 0);
	}

	/** 
		Gets the sample rate of the audio represented by the
		current instance.
	 
	 <value>
		A <see cref="int" /> value containing the sample rate of
		the audio represented by the current instance.
	 </value>
	*/
	public int getAudioSampleRate()
	{
		return sample_rate;
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
	public int getAudioChannels()
	{
		return 2;
	}

	/** 
		Gets the WavPack version of the audio represented by the
		current instance.
	 
	 <value>
		A <see cref="int" /> value containing the WavPack version
		of the audio represented by the current instance.
	 </value>
	*/
	public int getVersion()
	{
		return version;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IEquatable

	/** 
		Generates a hash code for the current instance.
	 
	 @return 
		A <see cref="int" /> value containing the hash code for
		the current instance.
	 
	*/
	@Override
	public int hashCode()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
			return (int)(header_data ^ sample_rate ^ frames ^ version);
		}
	}

	/** 
		Checks whether or not the current instance is equal to
		another object.
	 
	 @param other
		A <see cref="object" /> to compare to the current
		instance.
	 
	 @return 
		A <see cref="bool" /> value indicating whether or not the
		current instance is equal to <paramref name="other" />.
	 
	 {@link M:System.IEquatable`1.Equals }
	*/
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof StreamHeader))
		{
			return false;
		}

		return Equals((StreamHeader) other);
	}

	/** 
		Checks whether or not the current instance is equal to
		another instance of <see cref="StreamHeader" />.
	 
	 @param other
		A <see cref="StreamHeader" /> object to compare to the
		current instance.
	 
	 @return 
		A <see cref="bool" /> value indicating whether or not the
		current instance is equal to <paramref name="other" />.
	 
	 {@link M:System.IEquatable`1.Equals }
	*/
	public boolean equals(StreamHeader other)
	{
		return header_data == other.header_data && sample_rate == other.sample_rate && version == other.version && frames == other.frames;
	}

	/** 
		Gets whether or not two instances of <see
		cref="StreamHeader" /> are equal to eachother.
	 
	 @param first
		A <see cref="StreamHeader" /> object to compare.
	 
	 @param second
		A <see cref="StreamHeader" /> object to compare.
	 
	 @return 
		<see langword="true" /> if <paramref name="first" /> is
		equal to <paramref name="second" />. Otherwise, <see
		langword="false" />.
	 
	*/
	public static boolean OpEquality(StreamHeader first, StreamHeader second)
	{
		return first.equals(second.clone());
	}

	/** 
		Gets whether or not two instances of <see
		cref="StreamHeader" /> differ.
	 
	 @param first
		A <see cref="StreamHeader" /> object to compare.
	 
	 @param second
		A <see cref="StreamHeader" /> object to compare.
	 
	 @return 
		<see langword="true" /> if <paramref name="first" /> is
		unequal to <paramref name="second" />. Otherwise, <see
		langword="false" />.
	 
	*/
	public static boolean OpInequality(StreamHeader first, StreamHeader second)
	{
		return !first.equals(second.clone());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public StreamHeader clone()
	{
		StreamHeader varCopy = new StreamHeader();

		varCopy.stream_length = this.stream_length;
		varCopy.version = this.version;
		varCopy.header_data = this.header_data;
		varCopy.sample_rate = this.sample_rate;
		varCopy.frames = this.frames;

		return varCopy;
	}
}