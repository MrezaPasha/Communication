package Rasad.Core.Media.MediaMetadataManagement.Ape;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	This struct implements <see cref="IAudioCodec" /> to provide
	support for reading Monkey's Audio APE stream properties.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct StreamHeader : IAudioCodec, ILosslessAudioCodec
public final class StreamHeader implements IAudioCodec, ILosslessAudioCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the APE version.
	 
	 
		This value is stored in bytes (4,5) of the file and is
		1000 times the actual version number, so 3810 indicates
		version 3.81.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort version;
	private short version;

	// Ape Header (24 bytes) starting at Offest 52 into the file
	/** 
		Contains the compression level.
	 
	 
		This value is stored in bytes (51,52).
	 
	*/
	private CompressionLevel compression_level = CompressionLevel.values()[0];

	/*
	/// <summary>
	///    Contains the format flags.
	/// </summary>
	/// <remarks>
	///    This value is stored in bytes (53,54).
	/// </remarks>
	private ushort format_flags;
	*/

	/** 
		Contains the number of audio blocks in one frame.
	 
	 
		This value is stored in bytes (55-58).
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint blocks_per_frame;
	private int blocks_per_frame;

	/** 
		Contains the number of audio blocks in the final frame.
	 
	 
		This value is stored in bytes (59-62).
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint final_frame_blocks;
	private int final_frame_blocks;

	/** 
		Contains the total number of frames.
	 
	 
		This value is stored in bytes (63-66).
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint total_frames;
	private int total_frames;

	/** 
		Contains the number of bits per sample.
	 
	 
		This value is stored in bytes (67,68) and is typically
		16.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort bits_per_sample;
	private short bits_per_sample;

	/** 
		Contains the number of channels.
	 
	 
		This value is stored in bytes (69,70) and is typically
		1 or 2.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort channels;
	private short channels;

	/** 
		Contains the sample rate.
	 
	 
		This value is stored in bytes (71-74) and is typically
		44100.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint sample_rate;
	private int sample_rate;

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
		The size of a Monkey Audio header.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint Size = 76;
	public static final int Size = 76;

	/** 
		The identifier used to recognize a WavPack file.
	 
	 <value>
		"MAC "
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "MAC ";

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
		Monkey Audio stream in bytes.
	 
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
		version = data.Mid(4, 2).ToUShort(false);
		compression_level = CompressionLevel.forValue(data.Mid(52, 2).ToUShort(false));
		// format_flags = data.Mid(54, 2).ToUShort(false);
		blocks_per_frame = data.Mid(56, 4).ToUInt(false);
		final_frame_blocks = data.Mid(60, 4).ToUInt(false);
		total_frames = data.Mid(64, 4).ToUInt(false);
		bits_per_sample = data.Mid(68, 2).ToUShort(false);
		channels = data.Mid(70, 2).ToUShort(false);
		sample_rate = data.Mid(72, 4).ToUInt(false);
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

		return TimeSpan.FromSeconds((double)((total_frames - 1) * blocks_per_frame + final_frame_blocks) / (double) sample_rate);
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
//C# TO JAVA CONVERTER TODO TASK: The '0:0.000' format specifier is not converted to Java:
		return String.format(CultureInfo.InvariantCulture, "Monkey's Audio APE Version {0:0.000}", getVersion());
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
		return (int)sample_rate;
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
		Gets the APE version of the audio represented by the
		current instance.
	 
	 <value>
		A <see cref="double" /> value containing the APE version
		of the audio represented by the current instance.
	 </value>
	*/
	public double getVersion()
	{
		return (double) version / (double) 1000;
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

	/** 
		Gets the level of compression used when encoding the
		audio represented by the current instance.
	 
	 <value>
		A <see cref="CompressionLevel" /> value indicating the
		level of compression used when encoding the audio
		represented by the current instance.
	 </value>
	*/
	public CompressionLevel getCompression()
	{
		return compression_level;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public StreamHeader clone()
	{
		StreamHeader varCopy = new StreamHeader();

		varCopy.version = this.version;
		varCopy.compression_level = this.compression_level;
		varCopy.blocks_per_frame = this.blocks_per_frame;
		varCopy.final_frame_blocks = this.final_frame_blocks;
		varCopy.total_frames = this.total_frames;
		varCopy.bits_per_sample = this.bits_per_sample;
		varCopy.channels = this.channels;
		varCopy.sample_rate = this.sample_rate;
		varCopy.stream_length = this.stream_length;

		return varCopy;
	}
}