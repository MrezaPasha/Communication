package Rasad.Core.Media.MediaMetadataManagement.Ogg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
	This structure provides a representation of an Ogg page header.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct PageHeader
public final class PageHeader
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Propertis

	/** 
		Contains the sizes of the packets contained in the
		current instance.
	*/
	private ArrayList<Integer> packet_sizes;

	/** 
		Contains the OGG version.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte version;
	private byte version;

	/** 
		Contains the page flags.
	*/
	private PageFlags flags = PageFlags.values()[0];

	/** 
		Contains the page absolute granular postion.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong absolute_granular_position;
	private long absolute_granular_position;

	/** 
		Contains the stream serial number of the page.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint stream_serial_number;
	private int stream_serial_number;

	/** 
		Contains the page sequence number.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint page_sequence_number;
	private int page_sequence_number;

	/** 
		Contains the header size on disk.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint size;
	private int size;

	/** 
		Contains the data size on disk.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint data_size;
	private int data_size;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="PageHeader" /> with a given serial number, page
		number, and flags.
	 
	 @param streamSerialNumber
		A <see cref="uint" /> value containing the serial number
		for the stream containing the page described by the new
		instance.
	 
	 @param pageNumber
		A <see cref="uint" /> value containing the index of the
		page described by the new instance in the stream.
	 
	 @param flags
		A <see cref="PageFlags" /> object containing the flags
		that apply to the page described by the new instance.
	 
	*/
	public PageHeader()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public PageHeader(uint streamSerialNumber, uint pageNumber, PageFlags flags)
	public PageHeader(int streamSerialNumber, int pageNumber, PageFlags flags)
	{
		version = 0;
		this.flags = flags;
		absolute_granular_position = 0;
		stream_serial_number = streamSerialNumber;
		page_sequence_number = pageNumber;
		size = 0;
		data_size = 0;
		packet_sizes = new ArrayList<Integer> ();

		if (pageNumber == 0 && (flags.getValue() & PageFlags.FirstPacketContinued.getValue()) == 0)
		{
			this.flags = Rasad.Core.Media.MediaMetadataManagement.Ogg.PageFlags.forValue(this.flags.getValue() | PageFlags.FirstPageOfStream.getValue());
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="PageHeader" /> by reading a raw Ogg page header
		from a specified position in a specified file.
	 
	 @param file
		A <see cref="File" /> object containing the file from
		which the contents of the new instance are to be read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	 @exception CorruptFileException
		The Ogg identifier could not be found at the correct
		location.
	 
	*/
	public PageHeader(File file, long position)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (position < 0 || position > file.getLength() - 27)
		{
			throw new IndexOutOfBoundsException("position");
		}

		file.Seek(position);

		// An Ogg page header is at least 27 bytes, so we'll go
		// ahead and read that much and then get the rest when
		// we're ready for it.

		ByteVector data = file.ReadBlock(27);
		if (data.size() < 27 || !data.StartsWith("OggS"))
		{
			throw new CorruptFileException("Error reading page header");
		}

		version = data.get(4);
		this.flags = PageFlags.forValue(data.get(5));
		absolute_granular_position = data.Mid(6, 8).ToULong(false);
		stream_serial_number = data.Mid(14, 4).ToUInt(false);
		page_sequence_number = data.Mid(18, 4).ToUInt(false);

		// Byte number 27 is the number of page segments, which
		// is the only variable length portion of the page
		// header. After reading the number of page segments
		// we'll then read in the coresponding data for this
		// count.
		int page_segment_count = data.get(26);
		ByteVector page_segments = file.ReadBlock(page_segment_count);

		// Another sanity check.
		if (page_segment_count < 1 || page_segments.size() != page_segment_count)
		{
			throw new CorruptFileException("Incorrect number of page segments");
		}

		// The base size of an Ogg page 27 bytes plus the number
		// of lacing values.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: size = (uint)(27 + page_segment_count);
		size = (int)(27 + page_segment_count);
		packet_sizes = new ArrayList<Integer> ();

		int packet_size = 0;
		data_size = 0;

		for (int i = 0; i < page_segment_count; i++)
		{
			data_size += page_segments.get(i);
			packet_size += page_segments.get(i);

			if (page_segments.get(i) < 255)
			{
				packet_sizes.add(packet_size);
				packet_size = 0;
			}
		}

		if (packet_size > 0)
		{
			packet_sizes.add(packet_size);
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="PageHeader" /> by copying the values from another
		instance, offsetting the page number and applying new
		flags.
	 
	 @param original
		A <see cref="PageHeader"/> object to copy the values
		from.
	 
	 @param offset
		A <see cref="uint"/> value specifying how much to offset
		the page sequence number in the new instance.
	 
	 @param flags
		A <see cref="PageFlags"/> value specifying the flags to
		use in the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public PageHeader(PageHeader original, uint offset, PageFlags flags)
	public PageHeader(PageHeader original, int offset, PageFlags flags)
	{
		version = original.version;
		this.flags = flags;
		absolute_granular_position = original.absolute_granular_position;
		stream_serial_number = original.stream_serial_number;
		page_sequence_number = original.page_sequence_number + offset;
		size = original.size;
		data_size = original.data_size;
		packet_sizes = new ArrayList<Integer> ();

		if (page_sequence_number == 0 && (flags.getValue() & PageFlags.FirstPacketContinued.getValue()) == 0)
		{
			this.flags = Rasad.Core.Media.MediaMetadataManagement.Ogg.PageFlags.forValue(this.flags.getValue() | PageFlags.FirstPageOfStream.getValue());
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets and sets the sizes for the packets in the page
		described by the current instance.
	 
	 <value>
		A <see cref="int[]" /> containing the packet sizes.
	 </value>
	*/
	public int[] getPacketSizes()
	{
		return tangible.IntegerLists.toArray(packet_sizes);
	}
	public void setPacketSizes(int[] value)
	{
		packet_sizes.clear();
		tangible.IntegerLists.addPrimitiveArrayToList(value, packet_sizes);
	}

	/** 
		Gets the flags for the page described by the current
		instance.
	 
	 <value>
		A <see cref="PageFlags" /> value containing the page
		flags.
	 </value>
	*/
	public PageFlags getFlags()
	{
		return flags;
	}

	/** 
		Gets the absolute granular position of the page described
		by the current instance.
	 
	 <value>
		A <see cref="long" /> value containing the absolute
		granular position of the page.
	 </value>
	*/
	public long getAbsoluteGranularPosition()
	{
		return (long) absolute_granular_position;
	}

	/** 
		Gets the sequence number of the page described by the
		current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the sequence
		number of the page.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getPageSequenceNumber()
	public int getPageSequenceNumber()
	{
		return page_sequence_number;
	}

	/** 
		Gets the serial number of stream that the page described
		by the current instance belongs to.
	 
	 <value>
		A <see cref="uint" /> value containing the stream serial
		number.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getStreamSerialNumber()
	public int getStreamSerialNumber()
	{
		return stream_serial_number;
	}

	/** 
		Gets the size of the header as it appeared on disk.
	 
	 <value>
		A <see cref="uint" /> value containing the header size.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getSize()
	public int getSize()
	{
		return size;
	}

	/** 
		Gets the size of the data portion of the page described
		by the current instance as it appeared on disk.
	 
	 <value>
		A <see cref="uint" /> value containing the data size.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getDataSize()
	public int getDataSize()
	{
		return data_size;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance as a raw Ogg page header.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	public ByteVector Render()
	{
		ByteVector data = new ByteVector();

		data.add("OggS");
		data.add(version); // stream structure version
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add((byte) flags);
		data.add((byte) flags.getValue());
		data.add(ByteVector.FromULong(absolute_granular_position, false));
		data.add(ByteVector.FromUInt(stream_serial_number, false));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) page_sequence_number, false));
		data.add(ByteVector.FromUInt((int) page_sequence_number, false));
		data.add(new ByteVector(4, 0)); // checksum, to be filled in later.
		ByteVector page_segments = getLacingValues();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add((byte) page_segments.Count);
		data.add((byte) page_segments.size());
		data.add(page_segments);

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Properties

	/** 
		Gets the rendered lacing values for the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the
		rendered lacing values.
	 </value>
	*/
	private ByteVector getLacingValues()
	{
		ByteVector data = new ByteVector();

		int [] sizes = getPacketSizes();

		for (int i = 0; i < sizes.length; i++)
		{
				// The size of a packet in an Ogg page
				// is indicated by a series of "lacing
				// values" where the sum of the values
				// is the packet size in bytes. Each of
				// these values is a byte. A value of
				// less than 255 (0xff) indicates the
				// end of the packet.

			int quot = sizes [i] / 255;
			int rem = sizes [i] % 255;

			for (int j = 0; j < quot; j++)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add((byte) 255);
				data.add((byte) 255);
			}

			if (i < sizes.length - 1 || (packet_sizes.get(i) % 255) != 0)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add((byte) rem);
				data.add((byte) rem);
			}
		}

		return data;
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
			return (int)(getLacingValues().hashCode() ^ version ^  flags.getValue() ^ (int) absolute_granular_position ^ stream_serial_number ^ page_sequence_number ^ size ^ data_size);
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
		if (!(other instanceof PageHeader))
		{
			return false;
		}

		return Equals((PageHeader) other);
	}

	/** 
		Checks whether or not the current instance is equal to
		another instance of <see cref="PageHeader" />.
	 
	 @param other
		A <see cref="PageHeader" /> object to compare to the
		current instance.
	 
	 @return 
		A <see cref="bool" /> value indicating whether or not the
		current instance is equal to <paramref name="other" />.
	 
	 {@link M:System.IEquatable`1.Equals }
	*/
	public boolean equals(PageHeader other)
	{
		return packet_sizes == other.packet_sizes && version == other.version && flags == other.flags && absolute_granular_position == other.absolute_granular_position && stream_serial_number == other.stream_serial_number && page_sequence_number == other.page_sequence_number && size == other.size && data_size == other.data_size;
	}

	/** 
		Gets whether or not two instances of <see
		cref="PageHeader" /> are equal to eachother.
	 
	 @param first
		A <see cref="PageHeader" /> object to compare.
	 
	 @param second
		A <see cref="PageHeader" /> object to compare.
	 
	 @return 
		<see langword="true" /> if <paramref name="first" /> is
		equal to <paramref name="second" />. Otherwise, <see
		langword="false" />.
	 
	*/
	public static boolean OpEquality(PageHeader first, PageHeader second)
	{
		return first.equals(second.clone());
	}

	/** 
		Gets whether or not two instances of <see
		cref="PageHeader" /> differ.
	 
	 @param first
		A <see cref="PageHeader" /> object to compare.
	 
	 @param second
		A <see cref="PageHeader" /> object to compare.
	 
	 @return 
		<see langword="true" /> if <paramref name="first" /> is
		unequal to <paramref name="second" />. Otherwise, <see
		langword="false" />.
	 
	*/
	public static boolean OpInequality(PageHeader first, PageHeader second)
	{
		return !first.equals(second.clone());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

	public PageHeader clone()
	{
		PageHeader varCopy = new PageHeader();

		varCopy.packet_sizes = this.packet_sizes;
		varCopy.version = this.version;
		varCopy.flags = this.flags;
		varCopy.absolute_granular_position = this.absolute_granular_position;
		varCopy.stream_serial_number = this.stream_serial_number;
		varCopy.page_sequence_number = this.page_sequence_number;
		varCopy.size = this.size;
		varCopy.data_size = this.data_size;

		return varCopy;
	}
}