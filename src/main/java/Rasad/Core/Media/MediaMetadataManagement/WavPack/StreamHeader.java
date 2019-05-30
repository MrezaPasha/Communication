package Rasad.Core.Media.MediaMetadataManagement.WavPack;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// StreamHeader.cs: Provides support for reading WavPack audio properties.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   wvproperties.cpp from libtunepimp
//
// Copyright (C) 2006-2007 Brian Nickel
// Copyright (C) 2006 by Lukáš Lalinský (Original Implementation)
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
	support for reading WavPack audio properties.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct StreamHeader : IAudioCodec, ILosslessAudioCodec, IEquatable<StreamHeader>
public final class StreamHeader implements IAudioCodec, ILosslessAudioCodec, IEquatable<StreamHeader>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constants

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static readonly uint [] sample_rates = new uint [] { 6000, 8000, 9600, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000, 64000, 88200, 96000, 192000};
	private static final int [] sample_rates = new int [] {6000, 8000, 9600, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000, 64000, 88200, 96000, 192000};

	private static final int BYTES_STORED = 3;
	private static final int MONO_FLAG = 4;
	private static final int SHIFT_LSB = 13;
	private static final long SHIFT_MASK = (0x1fL << SHIFT_LSB);
	private static final int SRATE_LSB = 23;
	private static final long SRATE_MASK = (0xfL << SRATE_LSB);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the number of bytes in the stream.
	*/
	private long stream_length;

	/** 
		Contains the WavPack version.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort version;
	private short version;

	/** 
		Contains the flags.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint flags;
	private int flags;

	/** 
		Contains the sample count.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint samples;
	private int samples;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Fields

	/** 
		The size of a WavPack header.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint Size = 32;
	public static final int Size = 32;

	/** 
		The identifier used to recognize a WavPack file.
	 
	 <value>
		"wvpk"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "wvpk";

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
		WavPack stream in bytes.
	 
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
		version = data.Mid(8, 2).ToUShort(false);
		flags = data.Mid(24, 4).ToUInt(false);
		samples = data.Mid(12, 4).ToUInt(false);
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
		return getAudioSampleRate() > 0 ? TimeSpan.FromSeconds((double) samples / (double) getAudioSampleRate() + 0.5) : TimeSpan.Zero;
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
		return String.format(System.Globalization.CultureInfo.InvariantCulture, "WavPack Version %1$s Audio", getVersion());
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
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		return (int)(sample_rates [(flags & SRATE_MASK) >> SRATE_LSB]);
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
		return ((flags & MONO_FLAG) != 0) ? 1 : 2;
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

	/** 
		Gets the number of bits per sample in the audio
		represented by the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the number of bits
		per sample in the audio represented by the current
		instance.
	 </value>
	*/
	public int getBitsPerSample()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		return (int)(((flags & BYTES_STORED) + 1) * 8 - ((flags & SHIFT_MASK) >> SHIFT_LSB));
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
			return (int)(flags ^ samples ^ version);
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
		return flags == other.flags && samples == other.samples && version == other.version;
	}

	/** 
		Gets whether or not two instances of <see
		cref="StreamHeader" /> are equal to eachother.
	 
	 @param first
		The first <see cref="StreamHeader" /> object to compare.
	 
	 @param second
		The second <see cref="StreamHeader" /> object to compare.
	 
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
		cref="StreamHeader" /> are unequal to eachother.
	 
	 @param first
		The first <see cref="StreamHeader" /> object to compare.
	 
	 @param second
		The second <see cref="StreamHeader" /> object to compare.
	 
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
		varCopy.flags = this.flags;
		varCopy.samples = this.samples;

		return varCopy;
	}
}