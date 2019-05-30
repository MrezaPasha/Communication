package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
	This structure provides a representation of a Microsoft
	AviMainHeader structure, minus the first 8 bytes.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct AviHeader
public final class AviHeader
{
	/** 
		Contains the number of microseconds per frame.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint microseconds_per_frame;
	private int microseconds_per_frame;

	/** 
		Contains the maximum number of bytes per second.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint max_bytes_per_second;
	private int max_bytes_per_second;

	/** 
		Contains the flags.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint flags;
	private int flags;

	/** 
		Contains the total number of frames.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint total_frames;
	private int total_frames;

	/** 
		Contains the number of initial frames.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint initial_frames;
	private int initial_frames;

	/** 
		Contains the number of streams.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint streams;
	private int streams;

	/** 
		Contains the suggested buffer size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint suggested_buffer_size;
	private int suggested_buffer_size;

	/** 
		Contains the video width.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint width;
	private int width;

	/** 
		Contains the video height.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint height;
	private int height;

	/** 
		Constructs and initializes a new instance of <see
		cref="AviHeader" /> by reading the raw structure from the
		beginning of a <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		data structure.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> contains less than 40 bytes.
	 
	*/
	public AviHeader()
	{
	}

	@Deprecated
	public AviHeader(ByteVector data)
	{
		this(data, 0);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="AviStreamHeader" /> by reading the raw structure
		from a specified position in a <see cref="ByteVector" />
		object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		data structure.
	 
	 @param offset
		A <see cref="int" /> value specifying the index in
		<paramref name="data"/> at which the structure begins.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="offset" /> is less than zero.
	 
	 @exception CorruptFileException
		<paramref name="data" /> contains less than 40 bytes at
		<paramref name="offset" />.
	 
	*/
	public AviHeader(ByteVector data, int offset)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (offset < 0)
		{
			throw new IndexOutOfBoundsException("offset");
		}

		if (offset + 40 > data.size())
		{
			throw new CorruptFileException("Expected 40 bytes.");
		}

		microseconds_per_frame = data.Mid(offset, 4).ToUInt(false);
		max_bytes_per_second = data.Mid(offset + 4, 4).ToUInt(false);
		flags = data.Mid(offset + 12, 4).ToUInt(false);
		total_frames = data.Mid(offset + 16, 4).ToUInt(false);
		initial_frames = data.Mid(offset + 20, 4).ToUInt(false);
		streams = data.Mid(offset + 24, 4).ToUInt(false);
		suggested_buffer_size = data.Mid(offset + 28, 4).ToUInt(false);
		width = data.Mid(offset + 32, 4).ToUInt(false);
		height = data.Mid(offset + 36, 4).ToUInt(false);
	}

	/** 
		Gets the number of microseconds per frame.
	 
	 <value>
		A <see cref="uint" /> value specifying number of
		microseconds per frame.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getMicrosecondsPerFrame()
	public int getMicrosecondsPerFrame()
	{
		return microseconds_per_frame;
	}

	/** 
		Gets the maximum number of bytes per second.
	 
	 <value>
		A <see cref="uint" /> value specifying maximum number of
		bytes per second.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getMaxBytesPerSecond()
	public int getMaxBytesPerSecond()
	{
		return max_bytes_per_second;
	}

	/** 
		Gets the file flags.
	 
	 <value>
		A <see cref="uint" /> value specifying file flags.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getFlags()
	public int getFlags()
	{
		return flags;
	}

	/** 
		Gets the number of frames in the file.
	 
	 <value>
		A <see cref="uint" /> value specifying the number of
		frames in the file.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getTotalFrames()
	public int getTotalFrames()
	{
		return total_frames;
	}

	/** 
		Gets how far ahead audio is from video.
	 
	 <value>
		A <see cref="uint" /> value specifying how far ahead
		audio is from video.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getInitialFrames()
	public int getInitialFrames()
	{
		return initial_frames;
	}

	/** 
		Gets the number of streams in the file.
	 
	 <value>
		A <see cref="uint" /> value specifying the number of
		streams in the file.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getStreams()
	public int getStreams()
	{
		return streams;
	}

	/** 
		Gets the suggested buffer size for the file.
	 
	 <value>
		A <see cref="uint" /> value specifying the buffer size.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getSuggestedBufferSize()
	public int getSuggestedBufferSize()
	{
		return suggested_buffer_size;
	}

	/** 
		Gets the width of the video in the file.
	 
	 <value>
		A <see cref="uint" /> value containing the width of the
		video.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getWidth()
	public int getWidth()
	{
		return width;
	}

	/** 
		Gets the height of the video in the file.
	 
	 <value>
		A <see cref="uint" /> value containing the height of the
		video.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getHeight()
	public int getHeight()
	{
		return height;
	}

	/** 
		Gets the duration of the media in the file.
	 
	 <value>
		A <see cref="TimeSpan" /> value containing the duration
		of the file.
	 </value>
	*/
	public TimeSpan getDuration()
	{
		return TimeSpan.FromMilliseconds((double) getTotalFrames() * (double) getMicrosecondsPerFrame() / 1000.0);
	}

	public AviHeader clone()
	{
		AviHeader varCopy = new AviHeader();

		varCopy.microseconds_per_frame = this.microseconds_per_frame;
		varCopy.max_bytes_per_second = this.max_bytes_per_second;
		varCopy.flags = this.flags;
		varCopy.total_frames = this.total_frames;
		varCopy.initial_frames = this.initial_frames;
		varCopy.streams = this.streams;
		varCopy.suggested_buffer_size = this.suggested_buffer_size;
		varCopy.width = this.width;
		varCopy.height = this.height;

		return varCopy;
	}
}