package Rasad.Core.Media.MediaMetadataManagement.Mpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.NonContainer.File" /> to
	provide tagging and properties support for MPEG-1, MPEG-2, and
	MPEG-2.5 video files.
 
 
	A <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag" /> and <see
	cref="Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag" /> will be added automatically to any
	file that doesn't contain one. This change does not effect the
	file until it is saved and can be reversed using the following
	method:
	<code>file.RemoveTags (file.TagTypes &amp; ~file.TagTypesOnDisk);</code>
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/mpg", "mpg")][SupportedMimeType("taglib/mpeg", "mpeg")][SupportedMimeType("taglib/mpe", "mpe")][SupportedMimeType("taglib/mpv2", "mpv2")][SupportedMimeType("taglib/m2v", "m2v")][SupportedMimeType("video/x-mpg")][SupportedMimeType("video/mpeg")] public class File : Rasad.Core.Media.MediaMetadataManagement.NonContainer.File
public class File extends Rasad.Core.Media.MediaMetadataManagement.NonContainer.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Static Fields

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static readonly ByteVector MarkerStart = new byte [] {0, 0, 1};
	private static final ByteVector MarkerStart = new byte [] {0, 0, 1};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the MPEG version.
	*/
	private Version version = Version.values()[0];

	/** 
		Contains the first audio header.
	*/
	private AudioHeader audio_header = new AudioHeader();

	/** 
		Contains the first video header.
	*/
	private VideoHeader video_header = new VideoHeader();

	/** 
		Indicates whether or not audio was found.
	*/
	private boolean video_found = false;

	/** 
		Indicates whether or not video was found.
	*/
	private boolean audio_found = false;

	/** 
		Contains the start time of the file.
	*/
	private Double start_time = null;

	/** 
		Contains the end time of the file.
	*/
	private double end_time;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system and specified read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public File(String path, ReadStyle propertiesStyle)
	{
		super(path, propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system with an average read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public File(String path)
	{
		super(path);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction and
		specified read style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public File(File.IFileAbstraction abstraction, ReadStyle propertiesStyle)
	{
		super(abstraction, propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction with an
		average read style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public File(File.IFileAbstraction abstraction)
	{
		super(abstraction);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Gets a tag of a specified type from the current instance,
		optionally creating a new tag if possible.
	 
	 @param type
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value indicating the
		type of tag to read.
	 
	 @param create
		A <see cref="bool" /> value specifying whether or not to
		try and create the tag if one is not found.
	 
	 @return 
		A <see cref="Tag" /> object containing the tag that was
		found in or added to the current instance. If no
		matching tag was found and none was created, <see
		langword="null" /> is returned.
	 
	 
		If a <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag" /> is added to the
		current instance, it will be placed at the start of the
		file. On the other hand, <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag" />
		<see cref="Rasad.Core.Media.MediaMetadataManagement.Ape.Tag" /> will be added to the end of
		the file. All other tag types will be ignored.
	 
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag GetTag(TagTypes type, boolean create)
	{
		Tag t = (Tag instanceof Rasad.Core.Media.MediaMetadataManagement.NonContainer.Tag ? (Rasad.Core.Media.MediaMetadataManagement.NonContainer.Tag)Tag : null).GetTag(type);

		if (t != null || !create)
		{
			return t;
		}

		switch (type)
		{
		case TagTypes.Id3v1:
			return EndTag.AddTag(type, Tag);

		case TagTypes.Id3v2:
			return EndTag.AddTag(type, Tag);

		case TagTypes.Ape:
			return EndTag.AddTag(type, Tag);

		default:
			return null;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Reads format specific information at the start of the
		file.
	 
	 @param start
		A <see cref="long" /> value containing the seek position
		at which the tags end and the media data begins.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	@Override
	protected void ReadStart(long start, ReadStyle propertiesStyle)
	{
		if (propertiesStyle == ReadStyle.None)
		{
			return;
		}

		tangible.RefObject<Long> tempRef_start = new tangible.RefObject<Long>(start);
		FindMarker(tempRef_start, Marker.SystemSyncPacket);
	start = tempRef_start.argValue;
		ReadSystemFile(start);
	}

	/** 
		Reads format specific information at the end of the
		file.
	 
	 @param end
		A <see cref="long" /> value containing the seek position
		at which the media data ends and the tags begin.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	@Override
	protected void ReadEnd(long end, ReadStyle propertiesStyle)
	{
		// Make sure we have ID3v1 and ID3v2 tags.
		GetTag(TagTypes.Id3v1, true);
		GetTag(TagTypes.Id3v2, true);

		if (propertiesStyle == ReadStyle.None || start_time == null)
		{
			return;
		}

		tangible.RefObject<Long> tempRef_end = new tangible.RefObject<Long>(end);
		RFindMarker(tempRef_end, Marker.SystemSyncPacket);
	end = tempRef_end.argValue;

		end_time = ReadTimestamp(end + 4);
	}

	/** 
		Reads the audio properties from the file represented by
		the current instance.
	 
	 @param start
		A <see cref="long" /> value containing the seek position
		at which the tags end and the media data begins.
	 
	 @param end
		A <see cref="long" /> value containing the seek position
		at which the media data ends and the tags begin.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @return 
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Properties" /> object describing the
		media properties of the file represented by the current
		instance.
	 
	*/
	@Override
	protected Properties ReadProperties(long start, long end, ReadStyle propertiesStyle)
	{
		TimeSpan duration = start_time == null ? TimeSpan.Zero : TimeSpan.FromSeconds(end_time - (double) start_time);

		return new Properties(duration, video_header.clone(), audio_header.clone());
	}

	/** 
		Gets the marker at a specified position.
	 
	 @param position
		A <see cref="long" /> value specifying the postion in the
		file represented by the current instance at which to
		read.
	 
	 @return 
		A <see cref="Marker" /> value containing the type of
		marker found at the specified position.
	 
	 @exception CorruptFileException
		A valid marker does not exist at the specified position.
	 
	*/
	protected final Marker GetMarker(long position)
	{
		Seek(position);
		ByteVector identifier = ReadBlock(4);

		if (identifier.size() == 4 && identifier.StartsWith(MarkerStart))
		{
			return Marker.forValue(identifier.get(3));
		}

		throw new CorruptFileException("Invalid marker at position " + position);
	}

	/** 
		Finds the next marker starting at a specified position.
	 
	 @param position
		A <see cref="long" /> value reference specifying the
		position at which to start searching. This value
		is updated to the position of the found marker.
	 
	 @return 
		A <see cref="Marker" /> value containing the type of
		marker found at the specified position.
	 
	 @exception CorruptFileException
		A valid marker could not be found.
	 
	*/
	protected final Marker FindMarker(tangible.RefObject<Long> position)
	{
		position.argValue = Find(MarkerStart, position.argValue);
		if (position.argValue < 0)
		{
			throw new CorruptFileException("Marker not found");
		}

		return GetMarker(position.argValue);
	}

	/** 
		Finds the next marker of a specified type, starting at a
		specified position.
	 
	 @param position
		A <see cref="long" /> value reference specifying the
		position at which to start searching. This value
		is updated to the position of the found marker.
	 
	 @param marker
		A <see cref="Marker" /> value specifying the type of
		marker to search for.
	 
	 @return 
		A <see cref="Marker" /> value containing the type of
		marker found at the specified position. This value will
		be identical to <paramref name="marker" />.
	 
	 @exception CorruptFileException
		A valid marker could not be found.
	 
	*/
	protected final Marker FindMarker(tangible.RefObject<Long> position, Marker marker)
	{
		ByteVector packet = new ByteVector(MarkerStart);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: packet.Add((byte) marker);
		packet.add((byte) marker.getValue());
		position.argValue = Find(packet, position.argValue);

		if (position.argValue < 0)
		{
			throw new CorruptFileException("Marker not found");
		}

		return GetMarker(position.argValue);
	}

	/** 
		Finds the previous marker of a specified type, starting
		at a specified position.
	 
	 @param position
		A <see cref="long" /> value reference specifying the
		position at which to start searching. This value
		is updated to the position of the found marker.
	 
	 @param marker
		A <see cref="Marker" /> value specifying the type of
		marker to search for.
	 
	 @return 
		A <see cref="Marker" /> value containing the type of
		marker found at the specified position. This value will
		be identical to <paramref name="marker" />.
	 
	 @exception CorruptFileException
		A valid marker could not be found.
	 
	*/
	protected final Marker RFindMarker(tangible.RefObject<Long> position, Marker marker)
	{
		ByteVector packet = new ByteVector(MarkerStart);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: packet.Add((byte) marker);
		packet.add((byte) marker.getValue());
		position.argValue = RFind(packet, position.argValue);

		if (position.argValue < 0)
		{
			throw new CorruptFileException("Marker not found");
		}

		return GetMarker(position.argValue);
	}

	/** 
		Reads the contents of the file as a system file, starting
		at a specified position.
	 
	 @param position
		A <see cref="long" /> value specifying the postion in the
		file represented by the current instance at which to
		start reading.
	 
	 
		This method will stop when it has read both an audio and
		a video header, or once it's read 100 packets. This is to
		prevent the entire file from being read if it lacks one
		type of stream.
	 
	*/
	protected final void ReadSystemFile(long position)
	{
		int sanity_limit = 100;

		for (int i = 0; i < sanity_limit && (start_time == null || !audio_found || !video_found); i++)
		{

			tangible.RefObject<Long> tempRef_position = new tangible.RefObject<Long>(position);
			Marker marker = FindMarker(tempRef_position);
		position = tempRef_position.argValue;

			switch (marker)
			{
			case SystemSyncPacket:
				tangible.RefObject<Long> tempRef_position2 = new tangible.RefObject<Long>(position);
				ReadSystemSyncPacket(tempRef_position2);
			position = tempRef_position2.argValue;
				break;

			case SystemPacket:
			case PaddingPacket:
				Seek(position + 4);
				position += ReadBlock(2).ToUShort() + 6;
				break;

			case VideoPacket:
				tangible.RefObject<Long> tempRef_position3 = new tangible.RefObject<Long>(position);
				ReadVideoPacket(tempRef_position3);
			position = tempRef_position3.argValue;
				break;

			case AudioPacket:
				tangible.RefObject<Long> tempRef_position4 = new tangible.RefObject<Long>(position);
				ReadAudioPacket(tempRef_position4);
			position = tempRef_position4.argValue;
				break;

			case EndOfStream:
				return;

			default:
				position += 4;
				break;
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Reads an audio packet, assigning the audio header and
		advancing the position to the next packet position.
	 
	 @param position
		A <see cref="long" /> value reference specifying the
		position at which to start reading the packet. This value
		is updated to the position of the next packet.
	 
	*/
	private void ReadAudioPacket(tangible.RefObject<Long> position)
	{
		Seek(position.argValue + 4);
		int length = ReadBlock(2).ToUShort();

		if (!audio_found)
		{
			tangible.OutObject<Rasad.Core.Media.MediaMetadataManagement.Mpeg.AudioHeader> tempOut_audio_header = new tangible.OutObject<Rasad.Core.Media.MediaMetadataManagement.Mpeg.AudioHeader>();
			audio_found = AudioHeader.Find(tempOut_audio_header, this, position.argValue + 15, length - 9);
		audio_header = tempOut_audio_header.argValue;
		}
		position.argValue += length;
	}

	/** 
		Reads a video packet, assigning the video header and
		advancing the position to the next packet position.
	 
	 @param position
		A <see cref="long" /> value reference specifying the
		position at which to start reading the packet. This value
		is updated to the position of the next packet.
	 
	*/
	private void ReadVideoPacket(tangible.RefObject<Long> position)
	{
		Seek(position.argValue + 4);
		int length = ReadBlock(2).ToUShort();
		long offset = position.argValue + 6;

		while (!video_found && offset < position.argValue + length)
		{
			tangible.RefObject<Long> tempRef_offset = new tangible.RefObject<Long>(offset);
			if (FindMarker(tempRef_offset) == Marker.VideoSyncPacket)
			{
			offset = tempRef_offset.argValue;
				video_header = new VideoHeader(this, offset + 4);
				video_found = true;
			}
			else
			{
			offset = tempRef_offset.argValue;
				// advance the offset by 6 bytes, so the next iteration of the
				// loop won't find the same marker and get stuck.  6 bytes because findMarker is a
				// generic find that could get both PES packets and Stream packets, the smallest
				// posible pes packet with a size =0 would be 6 bytes.
				offset += 6;
			}
		}

		position.argValue += length;
	}

	/** 
		Reads a system sync packet, filling in version
		information and the first timestamp value, advancing the
		position to the next packet position.
	 
	 @param position
		A <see cref="long" /> value reference specifying the
		position at which to start reading the packet. If the
		method is called without exception, this is updated to
		the position of the next packet.
	 
	 @exception UnsupportedFormatException
		The MPEG version contained in the packet is unknown.
	 
	*/
	private void ReadSystemSyncPacket(tangible.RefObject<Long> position)
	{
		int packet_size = 0;
		Seek(position.argValue + 4);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte version_info = ReadBlock(1)[0];
		byte version_info = ReadBlock(1)[0];

		if ((version_info & 0xF0) == 0x20)
		{
			version = Version.Version1;
			packet_size = 12;
		}
		else if ((version_info & 0xC0) == 0x40)
		{
			version = Version.Version2;
			Seek(position.argValue + 13);
			packet_size = 14 + (ReadBlock(1)[0] & 0x07);
		}
		else
		{
			throw new UnsupportedFormatException("Unknown MPEG version.");
		}

		if (start_time == null)
		{
			start_time = ReadTimestamp(position.argValue + 4);
		}

		position.argValue += packet_size;
	}

	/** 
		Reads an MPEG timestamp from a specified position in the
		file represented by the current instance.
	 
	 @param position
		A <see cref="long" /> value containing the position in
		the file at which to read. This should be immediately
		following a system sync packet marker.
	 
	 @return 
		A <see cref="double" /> value containing the read time in
		seconds.
	 
	*/
	private double ReadTimestamp(long position)
	{
		double high;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint low;
		int low;

		Seek(position);

		if (version == Version.Version1)
		{
			ByteVector data = ReadBlock(5);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			high = (double)((data.get(0) >> 3) & 0x01);

//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: low = ((uint)((data [0] >> 1) & 0x03) << 30) | (uint)(data [1] << 22) | (uint)((data [2] >> 1) << 15) | (uint)(data [3] << 7) | (uint)(data [4] << 1);
			low = ((int)((data.get(0) >> 1) & 0x03) << 30) | (int)(data.get(1) << 22) | (int)((data.get(2) >> 1) << 15) | (int)(data.get(3) << 7) | (int)(data.get(4) << 1);
		}
		else
		{
			ByteVector data = ReadBlock(6);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			high = (double)((data.get(0) & 0x20) >> 5);

//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: low = ((uint)((data [0] & 0x18) >> 3) << 30) | (uint)((data [0] & 0x03) << 28) | (uint)(data [1] << 20) | (uint)((data [2] & 0xF8) << 12) | (uint)((data [2] & 0x03) << 13) | (uint)(data [3] << 5) | (uint)(data [4] >> 3);
			low = ((int)((data.get(0) & 0x18) >> 3) << 30) | (int)((data.get(0) & 0x03) << 28) | (int)(data.get(1) << 20) | (int)((data.get(2) & 0xF8) << 12) | (int)((data.get(2) & 0x03) << 13) | (int)(data.get(3) << 5) | (int)(data.get(4) >> 3);
		}

		return (((high * 0x10000) * 0x10000) + low) / 90000.0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}