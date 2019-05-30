package Rasad.Core.Media.MediaMetadataManagement.Flac;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// StreamHeader.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   flagproperties.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2006-2007 Brian Nickel
// Copyright (C) 2003 Allan Sandfeld Jensen (Original Implementation)
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
	This structure implements <see cref="IAudioCodec" /> and provides
	information about a Flac audio stream.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct StreamHeader : IAudioCodec, ILosslessAudioCodec
public final class StreamHeader implements IAudioCodec, ILosslessAudioCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Properties

	/** 
		Contains the flags.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint flags;
	private int flags;

	/** 
		Contains the low portion of the length.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint low_length;
	private int low_length;

	/** 
		Contains the stream length.
	*/
	private long stream_length;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="StreamHeader" /> by reading a raw stream header
		structure and using the stream length.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		stream header.
	 
	 @param streamLength
		A <see cref="long" /> value containing the length of the
		stream.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> contains less than 18 bytes.
	 
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

		if (data.size() < 18)
		{
			throw new CorruptFileException("Not enough data in FLAC header.");
		}

		this.stream_length = streamLength;
		this.flags = data.Mid(10, 4).ToUInt(true);
		low_length = data.Mid(14, 4).ToUInt(true);
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
		return (getAudioSampleRate() > 0 && stream_length > 0) ? TimeSpan.FromSeconds((double) low_length / (double) getAudioSampleRate() + (double) getHighLength()) : TimeSpan.Zero;
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
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return (int)(flags >>> 12);
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
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return (int)(((flags >>> 9) & 7) + 1);
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
		Gets the sample width of the audio represented by the
		current instance.
	 
	 <value>
		A <see cref="int" /> value containing the sample width of
		the audio represented by the current instance.
	 </value>
	*/
	@Deprecated
	public int getAudioSampleWidth()
	{
		return getBitsPerSample();
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
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return (int)(((flags >>> 4) & 31) + 1);
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
		return "Flac Audio";
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Properties

	/** 
		Gets a high portion of the length of the audio
		represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the high portion
		of the length.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint getHighLength()
	private int getHighLength()
	{
			// The last 4 bits are the most significant 4
			// bits for the 36 bit stream length in samples.
			// (Audio files measured in days)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint)(AudioSampleRate > 0 ? (((flags & 0xf) << 28) / AudioSampleRate) << 4 : 0);
		return (int)(getAudioSampleRate() > 0 ? (((flags & 0xf) << 28) / getAudioSampleRate()) << 4 : 0);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

	public StreamHeader clone()
	{
		StreamHeader varCopy = new StreamHeader();

		varCopy.flags = this.flags;
		varCopy.low_length = this.low_length;
		varCopy.stream_length = this.stream_length;

		return varCopy;
	}
}