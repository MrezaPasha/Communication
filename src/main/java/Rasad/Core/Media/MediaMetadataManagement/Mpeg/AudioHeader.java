package Rasad.Core.Media.MediaMetadataManagement.Mpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

/** 
	This structure implements <see cref="IAudioCodec" /> and provides
	information about an MPEG audio stream.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct AudioHeader : IAudioCodec
public final class AudioHeader implements IAudioCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Static Value Arrays

	/** 
		Contains a sample rate table for MPEG audio.
	*/
	private static final int [][] sample_rates = new int [][]
	{
		{44100, 48000, 32000, 0},
		{22050, 24000, 16000, 0},
		{11025, 12000, 8000, 0}
	};

	/** 
		Contains a block size table for MPEG audio.
	*/
	private static final int [][] block_size = new int [][]
	{
		{0, 384, 1152, 1152},
		{0, 384, 1152, 576},
		{0, 384, 1152, 576}
	};

	/** 
		Contains a bitrate table for MPEG audio.
	*/
	private static final int [][][] bitrates = new int [][][]
	{
		{
			{0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448, -1},
			{0, 32, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 384, -1},
			{0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, -1}
		},
		{
			{0, 32, 48, 56, 64, 80, 96, 112, 128, 144, 160, 176, 192, 224, 256, -1},
			{0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160, -1},
			{0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160, -1}
		}
	};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Properties

	/** 
		Contains the header flags.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint flags;
	private int flags;

	/** 
		Contains the audio stream length.
	*/
	private long stream_length;

	/** 
		Contains the associated Xing header.
	*/
	private XingHeader xing_header = new XingHeader();

	/** 
		Contains the associated VBRI header.
	*/
	private VBRIHeader vbri_header = new VBRIHeader();

	/** 
		Contains the audio stream duration.
	*/
	private TimeSpan duration = new TimeSpan();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Fields

	/** 
		An empty and unset header.
	*/
	public static final AudioHeader Unknown = new AudioHeader(0, 0, getXingHeader().Unknown, getVBRIHeader().Unknown);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="AudioHeader" /> by populating it with specified
		values.
	 
	 @param flags
		A <see cref="uint" /> value specifying flags for the new
		instance.
	 
	 @param streamLength
		A <see cref="long" /> value specifying the stream length
		of the new instance.
	 
	 @param xingHeader
		A <see cref="XingHeader" /> object representing the Xing
		header associated with the new instance.
	 
	 @param vbriHeader
		A <see cref="VBRIHeader" /> object representing the VBRI
		header associated with the new instance.
	 
	*/
	public AudioHeader()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private AudioHeader(uint flags, long streamLength, XingHeader xingHeader, VBRIHeader vbriHeader)
	private AudioHeader(int flags, long streamLength, XingHeader xingHeader, VBRIHeader vbriHeader)
	{
		this.flags = flags;
		this.stream_length = streamLength;
		this.xing_header = xingHeader.clone();
		this.vbri_header = vbriHeader.clone();
		this.duration = TimeSpan.Zero;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="AudioHeader" /> by reading its contents from a
		<see cref="ByteVector" /> object and its Xing Header from
		the appropriate location in the specified file.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the header
		to read.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to read the Xing
		header from.
	 
	 @param position
		A <see cref="long" /> value indicating the position in
		<paramref name="file" /> at which the header begins.
	 
	 @exception CorruptFileException
		<paramref name="data" /> is less than 4 bytes long,
		does not begin with a MPEG audio synch, has a negative
		bitrate, or has a sample rate of zero.
	 
	*/
	private AudioHeader(ByteVector data, Rasad.Core.Media.MediaMetadataManagement.File file, long position)
	{
		this.duration = TimeSpan.Zero;
		stream_length = 0;

		String error = GetHeaderError(data);
		if (error != null)
		{
			throw new CorruptFileException(error);
		}

		flags = data.ToUInt();

		xing_header = getXingHeader().Unknown;

		vbri_header = getVBRIHeader().Unknown;

		// Check for a Xing header that will help us in
		// gathering information about a VBR stream.
		file.Seek(position + getXingHeader().XingHeaderOffset(getVersion(), getChannelMode()));

		ByteVector xing_data = file.ReadBlock(16);
		if (xing_data.size() == 16 && xing_data.StartsWith(getXingHeader().FileIdentifier))
		{
			xing_header = new XingHeader(xing_data);
		}

		if (xing_header.getPresent())
		{
			return;
		}

		// A Xing header could not be found, next chec for a
		// Fraunhofer VBRI header.
		file.Seek(position + getVBRIHeader().VBRIHeaderOffset());

		// Only get the first 24 bytes of the Header.
		// We're not interested in the TOC entries.
		ByteVector vbri_data = file.ReadBlock(24);
		if (vbri_data.size() == 24 && vbri_data.StartsWith(getVBRIHeader().FileIdentifier))
		{
		vbri_header = new VBRIHeader(vbri_data);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the MPEG version used to encode the audio
		represented by the current instance.
	 
	 <value>
		A <see cref="Version" /> value indicating the MPEG
		version used to encode the audio represented by the
		current instance.
	 </value>
	*/
	public Version getVersion()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		switch ((flags >>> 19) & 0x03)
		{
		case 0:
			return Version.Version25;
		case 2:
			return Version.Version2;
		default:
			return Version.Version1;
		}
	}

	/** 
		Gets the MPEG audio layer used to encode the audio
		represented by the current instance.
	 
	 <value>
		A <see cref="int" /> value indicating the MPEG audio
		layer used to encode the audio represented by the current
		instance.
	 </value>
	*/
	public int getAudioLayer()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		switch ((flags >>> 17) & 0x03)
		{
		case 1:
			return 3;
		case 2:
			return 2;
		default:
			return 1;
		}
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
		if (xing_header.getTotalSize() > 0 && duration > TimeSpan.Zero)
		{
			return (int) Math.rint((((getXingHeader().getTotalSize() * 8L) / duration.TotalSeconds) / 1000.0));
		}

		if (vbri_header.getTotalSize() > 0 && duration > TimeSpan.Zero)
		{
			return (int)Math.rint((((getVBRIHeader().getTotalSize() * 8L) / duration.TotalSeconds) / 1000.0));
		}

//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return bitrates [getVersion() == Version.Version1 ? 0 : 1][getAudioLayer() > 0 ? getAudioLayer() - 1 : 0][(int)(flags >>> 12) & 0x0F];
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
		return sample_rates [getVersion().getValue()][(int)(flags >>> 10) & 0x03];
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
		return getChannelMode() == ChannelMode.SingleChannel ? 1 : 2;
	}

	/** 
		Gets the length of the frames in the audio represented by
		the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the length of the
		frames in the audio represented by the current instance.
	 </value>
	*/
	public int getAudioFrameLength()
	{
		switch (getAudioLayer())
		{
			case 1:
				return 48000 * getAudioBitrate() / getAudioSampleRate() + (getIsPadded() ? 4 : 0);
			case 2:
				return 144000 * getAudioBitrate() / getAudioSampleRate() + (getIsPadded() ? 1 : 0);
			case 3:
				if (getVersion() == Version.Version1)
				{
//C# TO JAVA CONVERTER TODO TASK: There is no 'goto' in Java:
					goto case 2;
				}

				return 72000 * getAudioBitrate() / getAudioSampleRate() + (getIsPadded() ? 1 : 0);
			default:
				return 0;
		}
	}

	/** 
		Gets the duration of the media represented by the current
		instance.
	 
	 <value>
		A <see cref="TimeSpan" /> containing the duration of the
		media represented by the current instance.
	 </value>
	 
		If <see cref="XingHeader" /> is equal to <see
		cref="XingHeader.Unknown" /> and <see
		cref="SetStreamLength" /> has not been called, this value
		will not be correct.
		If <see cref="VBRIHeader" /> is equal to <see
		cref="VBRIHeader.Unknown" /> and <see
		cref="SetStreamLength" /> has not been called, this value
		will not be correct.
	 
	*/
	public TimeSpan getDuration()
	{
		if (duration > TimeSpan.Zero)
		{
			return duration;
		}

		if (xing_header.getTotalFrames() > 0)
		{
				// Read the length and the bitrate from
				// the Xing header.

			double time_per_frame = (double) block_size [getVersion().getValue()][getAudioLayer()] / (double) getAudioSampleRate();

			duration = TimeSpan.FromSeconds(time_per_frame * getXingHeader().getTotalFrames());
		}
		else if (vbri_header.getTotalFrames() > 0)
		{
				// Read the length and the bitrate from
				// the VBRI header.

			double time_per_frame = (double) block_size [getVersion().getValue()][getAudioLayer()] / (double) getAudioSampleRate();

			duration = TimeSpan.FromSeconds(Math.rint(time_per_frame * getVBRIHeader().getTotalFrames()));
		}
		else if (getAudioFrameLength() > 0 && getAudioBitrate() > 0)
		{
				// Since there was no valid Xing or VBRI
				// header found, we hope that we're in a
				// constant bitrate file.

			int frames = (int)(stream_length / getAudioFrameLength() + 1);

			duration = TimeSpan.FromSeconds((double)(getAudioFrameLength() * frames) / (double)(getAudioBitrate() * 125) + 0.5);
		}

		return duration;
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
		StringBuilder builder = new StringBuilder();

		builder.append("MPEG Version ");
		switch (getVersion())
		{
		case Version1:
			builder.append("1");
			break;
		case Version2:
			builder.append("2");
			break;
		case Version25:
			builder.append("2.5");
			break;
		}
		builder.append(" Audio, Layer ");
		builder.append(getAudioLayer());

		if (xing_header.getPresent() || vbri_header.getPresent())
		{
			builder.append(" VBR");
		}

		return builder.toString();
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
		Gets whether or not the audio represented by the current
		instance is protected.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		audio represented by the current instance is protected.
	 </value>
	*/
	public boolean getIsProtected()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return ((flags >>>16) & 1) == 0;
	}

	/** 
		Gets whether or not the audio represented by the current
		instance is padded.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		audio represented by the current instance is padded.
	 </value>
	*/
	public boolean getIsPadded()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return ((flags >>> 9) & 1) == 1;
	}

	/** 
		Gets whether or not the audio represented by the current
		instance is copyrighted.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		audio represented by the current instance is copyrighted.
	 </value>
	*/
	public boolean getIsCopyrighted()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return ((flags >>> 3) & 1) == 1;
	}

	/** 
		Gets whether or not the audio represented by the current
		instance is original.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		audio represented by the current instance is original.
	 </value>
	*/
	public boolean getIsOriginal()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return ((flags >>> 2) & 1) == 1;
	}

	/** 
		Gets the MPEG audio channel mode of the audio represented
		by the current instance.
	 
	 <value>
		A <see cref="ChannelMode" /> value indicating the MPEG
		audio channel mode of the audio represented by the
		current instance.
	 </value>
	*/
	public ChannelMode getChannelMode()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return ChannelMode.forValue((flags >>> 14) & 0x03);
	}

	/** 
		Gets the Xing header found in the audio represented by
		the current instance.
	 
	 <value>
		A <see cref="XingHeader" /> object containing the Xing
		header found in the audio represented by the current
		instance, or <see cref="XingHeader.Unknown" /> if no
		header was found.
	 </value>
	*/
	public XingHeader getXingHeader()
	{
		return xing_header;
	}

	/** 
		Gets the VBRI header found in the audio represented by
		the current instance.
	 
	 <value>
		A <see cref="VBRIHeader" /> object containing the VBRI
		header found in the audio represented by the current
		instance, or <see cref="VBRIHeader.Unknown" /> if no
		header was found.
	 </value>
	*/
	public VBRIHeader getVBRIHeader()
	{
		return vbri_header;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Sets the length of the audio stream represented by the
		current instance.
	 
	 @param streamLength
		A <see cref="long" /> value specifying the length in
		bytes of the audio stream represented by the current
		instance.
	 
	 
		The this value has been set, <see cref="Duration" /> will
		return an incorrect value.
	 
	*/
	public void SetStreamLength(long streamLength)
	{
		this.stream_length = streamLength;

		// Force the recalculation of duration if it depends on
		// the stream length.
		if (xing_header.getTotalFrames() == 0 || vbri_header.getTotalFrames() == 0)
		{
			duration = TimeSpan.Zero;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Searches for an audio header in a <see cref="Rasad.Core.Media.MediaMetadataManagement.File"
		/> starting at a specified position and searching through
		a specified number of bytes.
	 
	 @param header
		A <see cref="AudioHeader" /> object in which the found
		header will be stored.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to search.
	 
	 @param position
		A <see cref="long" /> value specifying the seek position
		in <paramref name="file" /> at which to start searching.
	 
	 @param length
		A <see cref="int" /> value specifying the maximum number
		of bytes to search before aborting.
	 
	 @return 
		A <see cref="bool" /> value indicating whether or not a
		header was found.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	*/
	public static boolean Find(tangible.OutObject<AudioHeader> header, Rasad.Core.Media.MediaMetadataManagement.File file, long position, int length)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		long end = position + length;
		header.argValue = AudioHeader.Unknown;

		file.Seek(position);

		ByteVector buffer = file.ReadBlock(3);

		if (buffer.size() < 3)
		{
			return false;
		}

		do
		{
			file.Seek(position + 3);
			buffer = buffer.Mid(buffer.size() - 3);
			buffer.add(file.ReadBlock((int) File.getBufferSize()));

			for (int i = 0; i < buffer.size() - 3 && (length < 0 || position + i < end); i++)
			{
				if (buffer.get(i) == 0xFF && buffer.get(i + 1) > 0xE0)
				{
					ByteVector data = buffer.Mid(i, 4);
					if (GetHeaderError(data) == null)
					{
						try
						{
							header.argValue = new AudioHeader(data, file, position + i);
							return true;
						}
						catch (CorruptFileException e)
						{
						}
					}
				}
			}

			position += File.getBufferSize();
		} while (buffer.size() > 3 && (length < 0 || position < end));

		return false;
	}

	/** 
		Searches for an audio header in a <see cref="Rasad.Core.Media.MediaMetadataManagement.File"
		/> starting at a specified position and searching to the
		end of the file.
	 
	 @param header
		A <see cref="AudioHeader" /> object in which the found
		header will be stored.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to search.
	 
	 @param position
		A <see cref="long" /> value specifying the seek position
		in <paramref name="file" /> at which to start searching.
	 
	 @return 
		A <see cref="bool" /> value indicating whether or not a
		header was found.
	 
	 
		Searching to the end of the file can be very, very slow
		especially for corrupt or non-MPEG files. It is
		recommended to use <see
		cref="Find(AudioHeader,Rasad.Core.Media.MediaMetadataManagement.File,long,int)" />
		instead.
	 
	*/
	public static boolean Find(tangible.OutObject<AudioHeader> header, Rasad.Core.Media.MediaMetadataManagement.File file, long position)
	{
		return Find(header.clone(), file, position, -1);
	}

	private static String GetHeaderError(ByteVector data)
	{
		if (data.size() < 4)
		{
			return "Insufficient header length.";
		}

		if (data.get(0) != 0xFF)
		{
			return "First byte did not match MPEG synch.";
		}

		// Checking bits from high to low:
		//
		// First 3 bits MUST be set. Bits 4 and 5 can
		// be 00, 10, or 11 but not 01. One or more of
		// bits 6 and 7 must be set. Bit 8 can be
		// anything.
		if ((data.get(1) & 0xE6) <= 0xE0 || (data.get(1) & 0x18) == 0x08)
		{
			return "Second byte did not match MPEG synch.";
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint flags = data.ToUInt();
		int flags = data.ToUInt();

//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		if (((flags >>> 12) & 0x0F) == 0x0F)
		{
			return "Header uses invalid bitrate index.";
		}

//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		if (((flags >>> 10) & 0x03) == 0x03)
		{
			return "Invalid sample rate.";
		}

		return null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public AudioHeader clone()
	{
		AudioHeader varCopy = new AudioHeader();

		varCopy.flags = this.flags;
		varCopy.stream_length = this.stream_length;
		varCopy.xing_header = this.xing_header.clone();
		varCopy.vbri_header = this.vbri_header.clone();
		varCopy.duration = this.duration;

		return varCopy;
	}
}