package Rasad.Core.Media.MediaMetadataManagement.Aiff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// StreamHeader.cs: Provides support for reading Apple's AIFF stream
// properties.
//
// Author:
//   Helmut Wahrmann
//
// Copyright (C) 2009 Helmut Wahrmann
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
	support for reading Apple's AIFF stream properties.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct StreamHeader : IAudioCodec, ILosslessAudioCodec
public final class StreamHeader implements IAudioCodec, ILosslessAudioCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the number of channels.
	 
	 
		This value is stored in bytes (9,10).
		1 is monophonic, 2 is stereo, 4 means 4 channels, etc..
		any number of audio channels may be represented
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort channels;
	private short channels;

	/** 
		Contains the number of sample frames in the Sound Data chunk.
	 
	 
		This value is stored in bytes (11-14).
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong total_frames;
	private long total_frames;

	/** 
		Contains the number of bits per sample.
	 
	 
		This value is stored in bytes (15,16).
		It can be any number from 1 to 32.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort bits_per_sample;
	private short bits_per_sample;

	/** 
		Contains the sample rate.
	 
	 
		This value is stored in bytes (17-26).
		the sample rate at which the sound is to be played back, 
		in sample frames per second
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong sample_rate;
	private long sample_rate;

	/** 
		Contains the length of the audio stream.
	 
	 
		This value is provided by the constructor.
	 
	*/
	private long stream_length;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Fields

	/** 
		The size of an AIFF Common chunk
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint Size = 26;
	public static final int Size = 26;

	/** 
		The identifier used to recognize a AIFF file.
		Altough an AIFF file start with "FORM2, we're interested
		in the Common chunk only, which contains the properties we need.
	 
	 <value>
		"COMM"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "COMM";

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
		AIFF Audio stream in bytes.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> does not begin with <see
		cref="FileIdentifier" /> 
	 
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

		stream_length = streamLength;

		// The first 8 bytes contain the Common chunk identifier "COMM"
		// And the size of the common chunk, which is always 18
		channels = data.Mid(8, 2).ToUShort(true);
		total_frames = data.Mid(10, 4).ToULong(true);
		bits_per_sample = data.Mid(14, 2).ToUShort(true);

		ByteVector sample_rate_indicator = data.Mid(17, 1);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong sample_rate_tmp = data.Mid(18, 2).ToULong(true);
		long sample_rate_tmp = data.Mid(18, 2).ToULong(true);
		sample_rate = 44100; // Set 44100 as default sample rate

		// The following are combinations that iTunes 8 encodes to.
		// There may be other combinations in the field, but i couldn't test them.
		switch (sample_rate_tmp)
		{
			case 44100:
				if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0E))
				{
					sample_rate = 44100;
				}
				else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0D))
				{
					sample_rate = 22050;
				}
				else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0C))
				{
					sample_rate = 11025;
				}
				break;

			case 48000:
				if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0E))
				{
					sample_rate = 48000;
				}
				else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0D))
				{
					sample_rate = 24000;
				}
				break;

			case 64000:
				if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0D))
				{
					sample_rate = 32000;
				}
				else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0C))
				{
					sample_rate = 16000;
				}
				else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0B))
				{
					sample_rate = 8000;
				}
				break;

			case 44510:
				if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0D))
				{
					sample_rate = 22255;
				}
				break;

			case 44508:
				if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(sample_rate_indicator, 0x0C))
				{
					sample_rate = 11127;
				}
				break;
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
		if (sample_rate <= 0 || total_frames <= 0)
		{
			return TimeSpan.Zero;
		}

		return TimeSpan.FromSeconds((double) total_frames / (double) sample_rate);
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
		return "AIFF Audio";
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
		TimeSpan d = getDuration();
		if (d <= TimeSpan.Zero)
		{
			return 0;
		}

		return (int)((stream_length * 8L) / d.TotalSeconds) / 1000;
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
		return (int) sample_rate;
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
		return channels;
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
		return bits_per_sample;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public StreamHeader clone()
	{
		StreamHeader varCopy = new StreamHeader();

		varCopy.channels = this.channels;
		varCopy.total_frames = this.total_frames;
		varCopy.bits_per_sample = this.bits_per_sample;
		varCopy.sample_rate = this.sample_rate;
		varCopy.stream_length = this.stream_length;

		return varCopy;
	}
}