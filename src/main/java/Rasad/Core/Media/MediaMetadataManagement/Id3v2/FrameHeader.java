package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	This structure provides a representation of an ID3v2 frame header
	which can be read from and written to disk.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct FrameHeader
public final class FrameHeader
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains frame's ID.
	*/
	private ReadOnlyByteVector frame_id;

	/** 
		Contains frame's size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint frame_size;
	private int frame_size;

	/** 
		Contains frame's flags.
	*/
	private FrameFlags flags = FrameFlags.values()[0];

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="FrameHeader" /> by reading it from raw header data
		of a specified version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		data to build the new instance from.
	 
	 @param version
		A <see cref="byte" /> value containing the ID3v2 version
		with which the data in <paramref name="data" /> was
		encoded.
	 
	 
		If the data size is smaller than the size of a full
		header, the data is just treated as a frame identifier 
		and the remaining values are zeroed.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> is smaller than the size of a
		frame identifier or <paramref name="version" /> is less
		than 2 or more than 4.
	 
	*/
	public FrameHeader()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public FrameHeader(ByteVector data, byte version)
	public FrameHeader(ByteVector data, byte version)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		flags = Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(0);
		frame_size = 0;

		if (version < 2 || version > 4)
		{
			throw new CorruptFileException("Unsupported tag version.");
		}

		if (data.size() < (version == 2 ? 3 : 4))
		{
			throw new CorruptFileException("Data must contain at least a frame ID.");
		}

		switch (version)
		{
		case 2:
			// Set the frame ID -- the first three bytes
			frame_id = ConvertId(data.Mid(0, 3), version, false);

			// If the full header information was not passed
			// in, do not continue to the steps to parse the
			// frame size and flags.
			if (data.size() < 6)
			{
				return;
			}

			frame_size = data.Mid(3, 3).ToUInt();
			return;

		case 3:
			// Set the frame ID -- the first four bytes
			frame_id = ConvertId(data.Mid(0, 4), version, false);

			// If the full header information was not passed
			// in, do not continue to the steps to parse the
			// frame size and flags.
			if (data.size() < 10)
			{
				return;
			}

			// Store the flags internally as version 2.4.
			frame_size = data.Mid(4, 4).ToUInt();
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			flags = FrameFlags.forValue((short)(((data.get(8) << 7) & 0x7000) | ((data.get(9) >> 4) & 0x000C) | ((data.get(9) << 1) & 0x0040)));

			return;

		case 4:
			// Set the frame ID -- the first four bytes
			frame_id = new ReadOnlyByteVector(data.Mid(0, 4));

			// If the full header information was not passed
			// in, do not continue to the steps to parse the
			// frame size and flags.
			if (data.size() < 10)
			{
				return;
			}

			frame_size = SynchData.ToUInt(data.Mid(4, 4));
			flags = FrameFlags.forValue(data.Mid(8, 2).ToUShort());

			return;

		default:
			throw new CorruptFileException("Unsupported tag version.");
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets and sets the identifier of the frame described by
		the current instance.
	 
	 <value>
		A <see cref="ReadOnlyByteVector" /> object containing the
		identifier of the frame described by the current
		instance.
	 </value>
	 @exception ArgumentNullException
		<paramref name="value" /> is <see langword="null" />.
	 
	*/
	public ReadOnlyByteVector getFrameId()
	{
		return frame_id;
	}
	public void setFrameId(ReadOnlyByteVector value)
	{
		if (value == null)
		{
			throw new NullPointerException("value");
		}

		frame_id = value.size() == 4 ? value : new ReadOnlyByteVector(value.Mid(0, 4));
	}

	/** 
		Gets and sets the size of the frame described by the
		current instance, minus the header.
	 
	 <value>
		A <see cref="uint" /> value containing the size of the
		frame described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getFrameSize()
	public int getFrameSize()
	{
		return frame_size;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setFrameSize(uint value)
	public void setFrameSize(int value)
	{
		frame_size = value;
	}

	/** 
		Gets and sets the flags applied to the current instance.
	 
	 <value>
		A bitwise combined <see cref="HeaderFlags" /> value
		containing the flags applied to the current instance.
	 </value>
	 @exception ArgumentException
		<paramref name="value" /> contains a either compression
		or encryption, neither of which are supported by the
		library.
	 
	*/
	public FrameFlags getFlags()
	{
		return flags;
	}
	public void setFlags(FrameFlags value)
	{
		if ((value.getValue() & (FrameFlags.Compression.getValue() | FrameFlags.Encryption.getValue()).getValue()) != 0)
		{
			throw new IllegalArgumentException("Encryption and compression are not supported.", "value");
		}

		flags = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Renders the current instance, encoded in a specified
		ID3v2 version.
	 
	 @param version
		A <see cref="byte" /> value specifying the version of
		ID3v2 to use when encoding the current instance.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	 @exception NotImplementedException
		The version specified in the current instance is
		unsupported.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ByteVector Render(byte version)
	public ByteVector Render(byte version)
	{
		ByteVector data = new ByteVector();
		ByteVector id = ConvertId(frame_id, version, true);

		if (id == null)
		{
			throw new UnsupportedOperationException();
		}

		switch (version)
		{
		case 2:
			data.add(id);
			data.add(ByteVector.FromUInt(frame_size).Mid(1, 3));

			return data;

		case 3:
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort new_flags = (ushort)((((ushort)flags << 1) & 0xE000) | (((ushort)flags << 4) & 0x00C0) | (((ushort)flags >> 1) & 0x0020));
			short new_flags = (short)((((short)flags.getValue() << 1) & 0xE000) | (((short)flags.getValue() << 4) & 0x00C0) | (((short)flags.getValue() >>> 1) & 0x0020));

			data.add(id);
			data.add(ByteVector.FromUInt(frame_size));
			data.add(ByteVector.FromUShort(new_flags));

			return data;

		case 4:
			data.add(id);
			data.add(SynchData.FromUInt(frame_size));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUShort((ushort) flags));
			data.add(ByteVector.FromUShort((short) flags.getValue()));

			return data;

		default:
			throw new UnsupportedOperationException("Unsupported tag version.");
		}
	}

	/** 
		Gets the size of a header for a specified ID3v2 version.
	 
	 @param version
		A <see cref="byte" /> value specifying the version of
		ID3v2 to get the size for.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static uint Size(byte version)
	public static int Size(byte version)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint)(version < 3 ? 6 : 10);
		return (int)(version < 3 ? 6 : 10);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static ReadOnlyByteVector ConvertId(ByteVector id, byte version, bool toVersion)
	private static ReadOnlyByteVector ConvertId(ByteVector id, byte version, boolean toVersion)
	{
		if (version >= 4)
		{
			ReadOnlyByteVector outid = id instanceof ReadOnlyByteVector ? (ReadOnlyByteVector)id : null;

			return outid != null ? outid : new ReadOnlyByteVector(id);
		}

		if (id == null || version < 2)
		{
			return null;
		}

		if (!toVersion && (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(id, FrameType.EQUA) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(id, FrameType.RVAD) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(id, FrameType.TRDA) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(id, FrameType.TSIZ)))
		{
			return null;
		}

		if (version == 2)
		{
			for (int i = 0; i < version2_frames.length; i++)
			{
				if (!version2_frames [i][toVersion ? 1 : 0].equals(id))
				{
					continue;
				}

				return version2_frames [i][toVersion ? 0 : 1];
			}
		}

		if (version == 3)
		{
			for (int i = 0; i < version3_frames.length; i++)
			{
				if (!version3_frames [i][toVersion ? 1 : 0].equals(id))
				{
					continue;
				}

				return version3_frames [i][toVersion ? 0 : 1];
			}
		}

		if ((id.size() != 4 && version > 2) || (id.size() != 3 && version == 2))
		{
			return null;
		}

		return id instanceof ReadOnlyByteVector ? id instanceof ReadOnlyByteVector ? (ReadOnlyByteVector)id : null : new ReadOnlyByteVector(id);
	}

	private static final ReadOnlyByteVector [][] version2_frames = new ReadOnlyByteVector [][]
	{
		{"BUF", "RBUF"},
		{"CNT", "PCNT"},
		{"COM", "COMM"},
		{"CRA", "AENC"},
		{"ETC", "ETCO"},
		{"GEO", "GEOB"},
		{"IPL", "TIPL"},
		{"MCI", "MCDI"},
		{"MLL", "MLLT"},
		{"PIC", "APIC"},
		{"POP", "POPM"},
		{"REV", "RVRB"},
		{"SLT", "SYLT"},
		{"STC", "SYTC"},
		{"TAL", "TALB"},
		{"TBP", "TBPM"},
		{"TCM", "TCOM"},
		{"TCO", "TCON"},
		{"TCP", "TCMP"},
		{"TCR", "TCOP"},
		{"TDA", "TDAT"},
		{"TIM", "TIME"},
		{"TDY", "TDLY"},
		{"TEN", "TENC"},
		{"TFT", "TFLT"},
		{"TKE", "TKEY"},
		{"TLA", "TLAN"},
		{"TLE", "TLEN"},
		{"TMT", "TMED"},
		{"TOA", "TOAL"},
		{"TOF", "TOFN"},
		{"TOL", "TOLY"},
		{"TOR", "TDOR"},
		{"TOT", "TOAL"},
		{"TP1", "TPE1"},
		{"TP2", "TPE2"},
		{"TP3", "TPE3"},
		{"TP4", "TPE4"},
		{"TPA", "TPOS"},
		{"TPB", "TPUB"},
		{"TRC", "TSRC"},
		{"TRK", "TRCK"},
		{"TSS", "TSSE"},
		{"TT1", "TIT1"},
		{"TT2", "TIT2"},
		{"TT3", "TIT3"},
		{"TXT", "TOLY"},
		{"TXX", "TXXX"},
		{"TYE", "TDRC"},
		{"UFI", "UFID"},
		{"ULT", "USLT"},
		{"WAF", "WOAF"},
		{"WAR", "WOAR"},
		{"WAS", "WOAS"},
		{"WCM", "WCOM"},
		{"WCP", "WCOP"},
		{"WPB", "WPUB"},
		{"WXX", "WXXX"},
		{"XRV", "RVA2"}
	};

	private static final ReadOnlyByteVector [][] version3_frames = new ReadOnlyByteVector [][]
	{
		{"TORY", "TDOR"},
		{"TYER", "TDRC"},
		{"XRVA", "RVA2"}
	};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public FrameHeader clone()
	{
		FrameHeader varCopy = new FrameHeader();

		varCopy.frame_id = this.frame_id;
		varCopy.frame_size = this.frame_size;
		varCopy.flags = this.flags;

		return varCopy;
	}
}