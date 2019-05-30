package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	This structure provides a representation of a Microsoft
	AviStreamHeader structure, minus the first 8 bytes.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct AviStreamHeader
public final class AviStreamHeader
{
	/** 
		Contains the stream type.
	*/
	private ByteVector type;

	/** 
		Contains the stream handler.
	*/
	private ByteVector handler;

	/** 
		Contains the flags.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint flags;
	private int flags;

	/** 
		Contains the priority.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint priority;
	private int priority;

	/** 
		Contains the initial frame count.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint initial_frames;
	private int initial_frames;

	/** 
		Contains the scale.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint scale;
	private int scale;

	/** 
		Contains the rate.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint rate;
	private int rate;

	/** 
		Contains the start delay.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint start;
	private int start;

	/** 
		Contains the stream length.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint length;
	private int length;

	/** 
		Contains the suggested buffer size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint suggested_buffer_size;
	private int suggested_buffer_size;

	/** 
		Contains the quality (between 0 and 10,000).
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint quality;
	private int quality;

	/** 
		Contains the sample size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint sample_size;
	private int sample_size;

	/** 
		Contains the position for the left side of the video.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort left;
	private short left;

	/** 
		Contains the position for the top side of the video.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort top;
	private short top;

	/** 
		Contains the position for the right side of the video.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort right;
	private short right;

	/** 
		Contains the position for the bottom side of the video.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort bottom;
	private short bottom;

	/** 
		Constructs and initializes a new instance of <see
		cref="AviStreamHeader" /> by reading the raw structure
		from the beginning of a <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		data structure.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> contains less than 56 bytes.
	 
	*/
	public AviStreamHeader()
	{
	}

	@Deprecated
	public AviStreamHeader(ByteVector data)
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
		<paramref name="data" /> contains less than 56 bytes at
		<paramref name="offset" />.
	 
	*/
	public AviStreamHeader(ByteVector data, int offset)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (offset < 0)
		{
			throw new IndexOutOfBoundsException("offset");
		}

		if (offset + 56 > data.size())
		{
			throw new CorruptFileException("Expected 56 bytes.");
		}

		type = data.Mid(offset, 4);
		handler = data.Mid(offset + 4, 4);
		flags = data.Mid(offset + 8, 4).ToUInt(false);
		priority = data.Mid(offset + 12, 4).ToUInt(false);
		initial_frames = data.Mid(offset + 16, 4).ToUInt(false);
		scale = data.Mid(offset + 20, 4).ToUInt(false);
		rate = data.Mid(offset + 24, 4).ToUInt(false);
		start = data.Mid(offset + 28, 4).ToUInt(false);
		length = data.Mid(offset + 32, 4).ToUInt(false);
		suggested_buffer_size = data.Mid(offset + 36, 4).ToUInt(false);
		quality = data.Mid(offset + 40, 4).ToUInt(false);
		sample_size = data.Mid(offset + 44, 4).ToUInt(false);
		left = data.Mid(offset + 48, 2).ToUShort(false);
		top = data.Mid(offset + 50, 2).ToUShort(false);
		right = data.Mid(offset + 52, 2).ToUShort(false);
		bottom = data.Mid(offset + 54, 2).ToUShort(false);
	}

	/** 
		Gets the stream type.
	 
	 <value>
		A four-byte <see cref="ByteVector" /> object specifying
		stream type.
	 </value>
	*/
	public ByteVector getType()
	{
		return type;
	}

	/** 
		Gets the stream handler (codec) ID.
	 
	 <value>
		A four-byte <see cref="ByteVector" /> object specifying
		stream handler ID.
	 </value>
	*/
	public ByteVector getHandler()
	{
		return handler;
	}

	/** 
		Gets the stream flags.
	 
	 <value>
		A <see cref="uint" /> value specifying stream flags.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getFlags()
	public int getFlags()
	{
		return flags;
	}

	/** 
		Gets the stream priority.
	 
	 <value>
		A <see cref="uint" /> value specifying stream priority.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getPriority()
	public int getPriority()
	{
		return priority;
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
		Gets the scale of the stream.
	 
	 <value>
		A <see cref="uint" /> value specifying the scale of the
		stream.
	 </value>
	 
		Dividing <see cref="Rate"/> by <see cref="Scale" /> gives
		the number of samples per second.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getScale()
	public int getScale()
	{
		return scale;
	}

	/** 
		Gets the rate of the stream.
	 
	 <value>
		A <see cref="uint" /> value specifying the rate of the
		stream.
	 </value>
	 
		Dividing <see cref="Rate"/> by <see cref="Scale" /> gives
		the number of samples per second.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getRate()
	public int getRate()
	{
		return rate;
	}

	/** 
		Gets the start delay of the stream.
	 
	 <value>
		A <see cref="uint" /> value specifying the start delay of
		the stream.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getStart()
	public int getStart()
	{
		return start;
	}

	/** 
		Gets the length of the stream.
	 
	 <value>
		A <see cref="uint" /> value specifying the length of the
		stream.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getLength()
	public int getLength()
	{
		return length;
	}

	/** 
		Gets the suggested buffer size for the stream.
	 
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
		Gets the quality of the stream data.
	 
	 <value>
		A <see cref="uint" /> value specifying the quality of the
		stream data between 0 and 10,000.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getQuality()
	public int getQuality()
	{
		return quality;
	}

	/** 
		Gets the sample size of the stream data.
	 
	 <value>
		A <see cref="uint" /> value specifying the sample size.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getSampleSize()
	public int getSampleSize()
	{
		return sample_size;
	}

	/** 
		Gets the position at which the left of the video is to
		be displayed in the rectangle whose width is given in the
		the file's <see cref="AviHeader"/>.
	 
	 <value>
		A <see cref="ushort" /> value specifying the left
		position.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getLeft()
	public short getLeft()
	{
		return left;
	}

	/** 
		Gets the position at which the top of the video is to be
		displayed in the rectangle whose height is given in the
		the file's <see cref="AviHeader"/>.
	 
	 <value>
		A <see cref="ushort" /> value specifying the top
		position.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getTop()
	public short getTop()
	{
		return top;
	}

	/** 
		Gets the position at which the right of the video is to
		be displayed in the rectangle whose width is given in the
		the file's <see cref="AviHeader"/>.
	 
	 <value>
		A <see cref="ushort" /> value specifying the right
		position.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getRight()
	public short getRight()
	{
		return right;
	}

	/** 
		Gets the position at which the bottom of the video is
		to be displayed in the rectangle whose height is given in
		the file's <see cref="AviHeader"/>.
	 
	 <value>
		A <see cref="ushort" /> value specifying the bottom
		position.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getBottom()
	public short getBottom()
	{
		return bottom;
	}

	public AviStreamHeader clone()
	{
		AviStreamHeader varCopy = new AviStreamHeader();

		varCopy.type = this.type;
		varCopy.handler = this.handler;
		varCopy.flags = this.flags;
		varCopy.priority = this.priority;
		varCopy.initial_frames = this.initial_frames;
		varCopy.scale = this.scale;
		varCopy.rate = this.rate;
		varCopy.start = this.start;
		varCopy.length = this.length;
		varCopy.suggested_buffer_size = this.suggested_buffer_size;
		varCopy.quality = this.quality;
		varCopy.sample_size = this.sample_size;
		varCopy.left = this.left;
		varCopy.top = this.top;
		varCopy.right = this.right;
		varCopy.bottom = this.bottom;

		return varCopy;
	}
}