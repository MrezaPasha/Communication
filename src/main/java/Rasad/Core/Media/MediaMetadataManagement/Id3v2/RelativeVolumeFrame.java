package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
	This class extends <see cref="Frame" />, implementing support for
	ID3v2 Relative Volume (RVA2) Frames.
*/
public class RelativeVolumeFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the frame identification.
	*/
	private String identification = null;

	/** 
		Contains the channel data.
	*/
	private ChannelData [] channels = new ChannelData [9];

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="RelativeVolumeFrame" /> with a specified
		identifier.
	 
	 @param identification
		A <see cref="string" /> object containing the
		identification to use for the new frame.
	 
	*/
	public RelativeVolumeFrame(String identification)
	{
		super(FrameType.RVA2, 4);
		this.identification = identification;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="RelativeVolumeFrame" /> by reading its raw data in
		a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public RelativeVolumeFrame(ByteVector data, byte version)
	public RelativeVolumeFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="RelativeVolumeFrame" /> by reading its raw data in
		a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		representation of the new frame.
	 
	 @param offset
		A <see cref="int" /> indicating at what offset in
		<paramref name="data" /> the frame actually begins.
	 
	 @param header
		A <see cref="FrameHeader" /> containing the header of the
		frame found at <paramref name="offset" /> in the data.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected internal RelativeVolumeFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected RelativeVolumeFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(header.clone());
		SetData(data, offset, version, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets the identification used for the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the
		identification used for the current instance.
	 </value>
	*/
	public final String getIdentification()
	{
		return identification;
	}

	/** 
		Gets a list of the channels in the current instance that
		contain a value.
	 
	 <value>
		A <see cref="ChannelType[]" /> containing the channels
		which have a value set in the current instance.
	 </value>
	*/
	public final ChannelType[] getChannels()
	{
		ArrayList<ChannelType> types = new ArrayList<ChannelType> ();
		for (int i = 0; i < 9; i++)
		{
			if (channels [i].getIsSet())
			{
				types.add(ChannelType.forValue(i));
			}
		}
		return types.toArray(new ChannelType[0]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Creates a text description of the current instance.
	 
	 @return 
		A <see cref="string" /> object containing a description
		of the current instance.
	 
	*/
	@Override
	public String toString()
	{
		return identification;
	}

	/** 
		Gets the volume adjustment index for a specified channel.
	 
	 @param type
		A <see cref="ChannelType" /> value specifying which
		channel to get the value for.
	 
	 @return 
		A <see cref="short" /> value containing the volume
		adjustment index.
	 
	 
		The volume adjustment index is simply the volume
		adjustment multiplied by 512.
	 
	 {@link SetVolumeAdjustmentIndex}
	 {@link GetVolumeAdjustment}
	*/
	public final short GetVolumeAdjustmentIndex(ChannelType type)
	{
		return channels [type.getValue()].VolumeAdjustmentIndex;
	}

	/** 
		Sets the volume adjustment index for a specified channel.
	 
	 @param type
		A <see cref="ChannelType" /> value specifying which
		channel to set the value for.
	 
	 @param index
		A <see cref="short" /> value containing the volume
		adjustment index.
	 
	 {@link GetVolumeAdjustmentIndex}
	 {@link SetVolumeAdjustment}
	*/
	public final void SetVolumeAdjustmentIndex(ChannelType type, short index)
	{
		channels [type.getValue()].VolumeAdjustmentIndex = index;
	}

	/** 
		Gets the volume adjustment for a specified channel.
	 
	 @param type
		A <see cref="ChannelType" /> value specifying which
		channel to get the value for.
	 
	 @return 
		A <see cref="float" /> value containing the volume
		adjustment in decibles.
	 
	 
		The value can be between -64dB and +64dB.
	 
	 {@link SetVolumeAdjustment}
	 {@link GetVolumeAdjustmentIndex}
	*/
	public final float GetVolumeAdjustment(ChannelType type)
	{
		return channels [type.getValue()].getVolumeAdjustment();
	}

	/** 
		Sets the volume adjustment for a specified channel.
	 
	 @param type
		A <see cref="ChannelType" /> value specifying which
		channel to set the value for.
	 
	 @param adjustment
		A <see cref="float" /> value containing the volume
		adjustment in decibles.
	 
	 
		The value can be between -64dB and +64dB.
	 
	 {@link GetVolumeAdjustment}
	 {@link SetVolumeAdjustmentIndex}
	*/
	public final void SetVolumeAdjustment(ChannelType type, float adjustment)
	{
		channels [type.getValue()].setVolumeAdjustment(adjustment);
	}

	/** 
		Gets the peak volume index for a specified channel.
	 
	 @param type
		A <see cref="ChannelType" /> value specifying which
		channel to get the value for.
	 
	 @return 
		A <see cref="ulong" /> value containing the peak volume
		index.
	 
	 
		The peak volume index is simply the peak volume
		multiplied by 512.
	 
	 {@link SetPeakVolumeIndex}
	 {@link GetPeakVolume}
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong GetPeakVolumeIndex(ChannelType type)
	public final long GetPeakVolumeIndex(ChannelType type)
	{
		return channels [type.getValue()].PeakVolumeIndex;
	}

	/** 
		Sets the peak volume index for a specified channel.
	 
	 @param type
		A <see cref="ChannelType" /> value specifying which
		channel to set the value for.
	 
	 @param index
		A <see cref="ulong" /> value containing the peak volume
		index.
	 
	 
		The peak volume index is simply the peak volume
		multiplied by 512.
	 
	 {@link GetPeakVolumeIndex}
	 {@link SetPeakVolume}
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetPeakVolumeIndex(ChannelType type, ulong index)
	public final void SetPeakVolumeIndex(ChannelType type, long index)
	{
		channels [type.getValue()].PeakVolumeIndex = index;
	}

	/** 
		Gets the peak volume for a specified channel.
	 
	 @param type
		A <see cref="ChannelType" /> value specifying which
		channel to get the value for.
	 
	 @return 
		A <see cref="double" /> value containing the peak volume.
	 
	 {@link SetPeakVolume}
	 {@link GetPeakVolumeIndex}
	*/
	public final double GetPeakVolume(ChannelType type)
	{
		return channels [type.getValue()].getPeakVolume();
	}

	/** 
		Sets the peak volume for a specified channel.
	 
	 @param type
		A <see cref="ChannelType" /> value specifying which
		channel to set the value for.
	 
	 @param peak
		A <see cref="double" /> value containing the peak volume.
	 
	 {@link GetPeakVolume}
	 {@link SetPeakVolumeIndex}
	*/
	public final void SetPeakVolume(ChannelType type, double peak)
	{
		channels [type.getValue()].setPeakVolume(peak);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Gets a specified volume adjustment frame from the
		specified tag, optionally creating it if it does not
		exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param identification
		A <see cref="string" /> specifying the identification to
		match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="RelativeVolumeFrame" /> object containing
		the matching frame, or <see langword="null" /> if a match
		wasn't found and <paramref name="create" /> is <see
		langword="false" />.
	 
	*/
	public static RelativeVolumeFrame Get(Tag tag, String identification, boolean create)
	{
		RelativeVolumeFrame rva2;
		for (Frame frame : tag.GetFrames(FrameType.RVA2))
		{
			rva2 = frame instanceof RelativeVolumeFrame ? (RelativeVolumeFrame)frame : null;

			if (rva2 == null)
			{
				continue;
			}

			if (!rva2.getIdentification().equals(identification))
			{
				continue;
			}

			return rva2;
		}

		if (!create)
		{
			return null;
		}

		rva2 = new RelativeVolumeFrame(identification);
		tag.AddFrame(rva2);
		return rva2;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Properties

	/** 
		Populates the values in the current instance by parsing
		its field data in a specified version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the
		extracted field data.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		field data is encoded in.
	 
	 @exception CorruptFileException
		<paramref name="data" /> contains less than 5 bytes.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override void ParseFields(ByteVector data, byte version)
	@Override
	protected void ParseFields(ByteVector data, byte version)
	{
		int pos = tangible.ListHelper.find(data, ByteVector.TextDelimiter(StringType.Latin1));
		if (pos < 0)
		{
			return;
		}

		identification = data.toString(StringType.Latin1, 0, pos++);

		// Each channel is at least 4 bytes.

		while (pos <= data.size() - 4)
		{
			int type = data.get(pos++);

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
			unchecked
			{
				channels [type].VolumeAdjustmentIndex = (short) data.Mid(pos, 2).ToUShort();
			}
			pos += 2;

			int bytes = BitsToBytes(data.get(pos++));

			if (data.size() < pos + bytes)
			{
				break;
			}

			channels [type].PeakVolumeIndex = data.Mid(pos, bytes).ToULong();
			pos += bytes;
		}
	}

	/** 
		Renders the values in the current instance into field
		data for a specified version.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		field data is to be encoded in.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered field data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override ByteVector RenderFields(byte version)
	@Override
	protected ByteVector RenderFields(byte version)
	{
		ByteVector data = new ByteVector();
		data.add(ByteVector.FromString(identification, StringType.Latin1));
		data.add(ByteVector.TextDelimiter(StringType.Latin1));

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: for (byte i = 0; i < 9; i ++)
		for (byte i = 0; i < 9; i++)
		{
			if (!channels [i].getIsSet())
			{
				continue;
			}

			data.add(i);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
			unchecked
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUShort((ushort) channels [i].VolumeAdjustmentIndex));
				data.add(ByteVector.FromUShort((short) channels [i].VolumeAdjustmentIndex));
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte bits = 0;
			byte bits = 0;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: for (byte j = 0; j < 64; j ++)
			for (byte j = 0; j < 64; j++)
			{
				if ((channels [i].PeakVolumeIndex & (1 << j)) != 0)
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: bits = (byte)(j + 1);
					bits = (byte)(j + 1);
				}
			}

			data.add(bits);

			if (bits > 0)
			{
				data.add(ByteVector.FromULong(channels [i].PeakVolumeIndex).Mid(8 - BitsToBytes(bits)));
			}
		}

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region ICloneable

	/** 
		Creates a deep copy of the current instance.
	 
	 @return 
		A new <see cref="Frame" /> object identical to the
		current instance.
	 
	*/
	@Override
	public Frame Clone()
	{
		RelativeVolumeFrame frame = new RelativeVolumeFrame(identification);
		for (int i = 0; i < 9; i++)
		{
			frame.channels [i] = channels [i].clone();
		}
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Static Methods

	private static int BitsToBytes(int i)
	{
		return i % 8 == 0 ? i / 8 : (i - i % 8) / 8 + 1;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Classes

//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: private struct ChannelData
	private final static class ChannelData
	{
		public short VolumeAdjustmentIndex;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong PeakVolumeIndex;
		public long PeakVolumeIndex;

		public boolean getIsSet()
		{
			return VolumeAdjustmentIndex != 0 || PeakVolumeIndex != 0;
		}

		public float getVolumeAdjustment()
		{
			return VolumeAdjustmentIndex / 512f;
		}
		public void setVolumeAdjustment(float value)
		{
			VolumeAdjustmentIndex = (short)(value * 512f);
		}

		public double getPeakVolume()
		{
			return PeakVolumeIndex / 512.0;
		}
		public void setPeakVolume(double value)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: PeakVolumeIndex = (ulong)(value * 512.0);
			PeakVolumeIndex = (long)(value * 512.0);
		}

		public ChannelData clone()
		{
			ChannelData varCopy = new ChannelData();

			varCopy.VolumeAdjustmentIndex = this.VolumeAdjustmentIndex;
			varCopy.PeakVolumeIndex = this.PeakVolumeIndex;

			return varCopy;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}