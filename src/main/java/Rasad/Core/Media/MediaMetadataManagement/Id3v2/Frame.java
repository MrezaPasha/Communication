package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Frame.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   id3v2frame.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2002,2003 Scott Wheeler (Original Implementation)
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
	This abstract class provides a basic framework for representing
	ID3v2.4 frames.
*/
public abstract class Frame implements Cloneable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the frame's header.
	*/
	private FrameHeader header = new FrameHeader();

	/** 
		Contains the frame's grouping ID.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte group_id;
	private byte group_id;

	/** 
		Contains the frame's encryption ID.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte encryption_id;
	private byte encryption_id;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Frame" /> by reading the raw header encoded in the
		specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the
		identifier or header data to use for the new instance.
	 
	 @param version
		A <see cref="byte" /> value indicating the ID3v2 version
		which <paramref name="data" /> is encoded in.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="data" /> does not contain a complete
		identifier.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected Frame(ByteVector data, byte version)
	protected Frame(ByteVector data, byte version)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (data.size() < ((version < 3) ? 3 : 4))
		{
			throw new IllegalArgumentException("Data contains an incomplete identifier.", "data");
		}

		header = new FrameHeader(data, version);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Frame" /> with a specified header.
	 
	 @param header
		A <see cref="FrameHeader" /> value containing the header
		to use for the new instance.
	 
	*/
	protected Frame(FrameHeader header)
	{
		this.header = header.clone();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the frame ID for the current instance.
	 
	 <value>
		A <see cref="ReadOnlyByteVector" /> object containing the
		four-byte ID3v2.4 frame header for the current instance.
	 </value>
	*/
	public final ReadOnlyByteVector getFrameId()
	{
		return header.getFrameId();
	}

	/** 
		Gets the size of the current instance as it was last
		stored on disk.
	 
	 <value>
		A <see cref="uint" /> value containing the size of the
		current instance as it was last stored on disk.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getSize()
	public final int getSize()
	{
		return header.getFrameSize();
	}

	/** 
		Gets and sets the frame flags applied to the current
		instance.
	 
	 <value>
		A bitwise combined <see cref="FrameFlags" /> value
		containing the frame flags applied to the current
		instance.
	 </value>
	 
		If the value includes either <see
		cref="FrameFlags.Encryption" /> or <see
		cref="FrameFlags.Compression" />, <see cref="Render" />
		will throw a <see cref="NotImplementedException" />.
	 
	*/
	public final FrameFlags getFlags()
	{
		return header.getFlags();
	}
	public final void setFlags(FrameFlags value)
	{
		header.setFlags(value);
	}

	/** 
		Gets and sets the grouping ID applied to the current
		instance.
	 
	 <value>
		A <see cref="short" /> value containing the grouping
		identifier for the current instance, or -1 if not set.
	 </value>
	 
		Grouping identifiers can be between 0 and 255. Setting
		any other value will unset the grouping identity and set
		the value to -1.
	 
	*/
	public final short getGroupId()
	{
		return (getFlags().getValue() & FrameFlags.GroupingIdentity.getValue()) != 0 ? group_id : (short) -1;
	}
	public final void setGroupId(short value)
	{
		if (value >= 0x00 && value <= 0xFF)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: group_id = (byte) value;
			group_id = (byte) value;
			setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(getFlags().getValue() | FrameFlags.GroupingIdentity.getValue()));
		}
		else
		{
			setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(getFlags().getValue() & ~FrameFlags.GroupingIdentity.getValue()));
		}
	}

	/** 
		Gets and sets the encryption ID applied to the current
		instance.
	 
	 <value>
		A <see cref="short" /> value containing the encryption
		identifier for the current instance, or -1 if not set.
	 </value>
	 
		<p>Encryption identifiers can be between 0 and 255.
		Setting any other value will unset the grouping identity
		and set the value to -1.</p>
		<p>If set, <see cref="Render" /> will throw a <see
		cref="NotImplementedException" />.</p>
	 
	*/
	public final short getEncryptionId()
	{
		return (getFlags().getValue() & FrameFlags.Encryption.getValue()) != 0 ? encryption_id : (short) -1;
	}
	public final void setEncryptionId(short value)
	{
		if (value >= 0x00 && value <= 0xFF)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: encryption_id = (byte) value;
			encryption_id = (byte) value;
			setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(getFlags().getValue() | FrameFlags.Encryption.getValue()));
		}
		else
		{
			setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(getFlags().getValue() & ~FrameFlags.Encryption.getValue()));
		}
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
		The current instance uses some feature that cannot be
		implemented in the specified ID3v2 version, or uses a
		feature, such as encryption or compression, which is not
		yet implemented in the library.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual ByteVector Render(byte version)
	public ByteVector Render(byte version)
	{
		// Remove flags that are not supported by older versions
		// of ID3v2.
		if (version < 4)
		{
			setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(getFlags().getValue() & ~(FrameFlags.DataLengthIndicator.getValue() | FrameFlags.Unsynchronisation.getValue()).getValue()));
		}

		if (version < 3)
		{
			setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(getFlags().getValue() & ~(FrameFlags.Compression.getValue() | FrameFlags.Encryption.getValue() | FrameFlags.FileAlterPreservation.getValue() | FrameFlags.GroupingIdentity.getValue() | FrameFlags.ReadOnly.getValue() | FrameFlags.TagAlterPreservation.getValue()).getValue()));
		}

		ByteVector field_data = RenderFields(version);

		// If we don't have any content, don't render anything.
		// This will cause the frame to not be rendered.
		if (field_data.size() == 0)
		{
			return new ByteVector();
		}

		ByteVector front_data = new ByteVector();

		if ((getFlags().getValue() & (FrameFlags.Compression.getValue() | FrameFlags.DataLengthIndicator.getValue()).getValue()) != 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: front_data.Add(ByteVector.FromUInt((uint) field_data.Count));
			front_data.add(ByteVector.FromUInt((int) field_data.size()));
		}

		if ((getFlags().getValue() & FrameFlags.GroupingIdentity.getValue()) != 0)
		{
			front_data.add(group_id);
		}

		if ((getFlags().getValue() & FrameFlags.Encryption.getValue()) != 0)
		{
			front_data.add(encryption_id);
		}

		// FIXME: Implement compression.
		if ((getFlags().getValue() & FrameFlags.Compression.getValue()) != 0)
		{
			throw new UnsupportedOperationException("Compression not yet supported");
		}

		// FIXME: Implement encryption.
		if ((getFlags().getValue() & FrameFlags.Encryption.getValue()) != 0)
		{
			throw new UnsupportedOperationException("Encryption not yet supported");
		}

		if ((getFlags().getValue() & FrameFlags.Unsynchronisation.getValue()) != 0)
		{
			SynchData.UnsynchByteVector(field_data);
		}

		if (front_data.size() > 0)
		{
			field_data.add(0, front_data);
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: header.FrameSize = (uint) field_data.Count;
		header.setFrameSize((int) field_data.size());
		ByteVector header_data = header.Render(version);
		header_data.add(field_data);

		return header_data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Gets the text delimiter for a specified encoding.
	 
	 @param type
		A <see cref="StringType" /> value specifying the encoding
		to get the delimiter for.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		delimiter for the specified encoding.
	 
	*/
	@Deprecated
	public static ByteVector TextDelimiter(StringType type)
	{
		return ByteVector.TextDelimiter(type);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Converts an encoding to be a supported encoding for a
		specified tag version.
	 
	 @param type
		A <see cref="StringType" /> value containing the original
		encoding.
	 
	 @param version
		A <see cref="byte" /> value containing the ID3v2 version
		to be encoded for.
	 
	 @return 
		A <see cref="StringType" /> value containing the correct
		encoding to use, based on <see
		cref="Tag.ForceDefaultEncoding" /> and what is supported
		by <paramref name="version" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected static StringType CorrectEncoding(StringType type, byte version)
	protected static StringType CorrectEncoding(StringType type, byte version)
	{
		if (Tag.getForceDefaultEncoding())
		{
			type = Tag.getDefaultEncoding();
		}

		return (version < 4 && type == StringType.UTF8) ? StringType.UTF16 : type;
	}

	/** 
		Populates the current instance by reading the raw frame
		from disk, optionally reading the header.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		ID3v2 frame.
	 
	 @param offset
		A <see cref="int" /> value containing the offset in
		<paramref name="data" /> at which the frame begins.
	 
	 @param version
		A <see cref="byte" /> value containing the ID3v2 version
		of the raw frame contained in <paramref name="data" />.
	 
	 @param readHeader
		A <see cref="bool" /> value indicating whether or not to
		read the header into current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected void SetData(ByteVector data, int offset, byte version, bool readHeader)
	protected final void SetData(ByteVector data, int offset, byte version, boolean readHeader)
	{
		if (readHeader)
		{
			header = new FrameHeader(data, version);
		}
		ParseFields(FieldData(data, offset, version), version);
	}

	/** 
		Populates the values in the current instance by parsing
		its field data in a specified version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the
		extracted field data.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		field data is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected abstract void ParseFields(ByteVector data, byte version);
	protected abstract void ParseFields(ByteVector data, byte version);

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
//ORIGINAL LINE: protected abstract ByteVector RenderFields(byte version);
	protected abstract ByteVector RenderFields(byte version);

	/** 
		Extracts the field data from the raw data portion of an
		ID3v2 frame.
	 
	 @param frameData
		A <see cref="ByteVector" /> object containing fraw frame
		data.
	 
	 @param offset
		A <see cref="int" /> value containing the index at which
		the data is contained.
	 
	 @param version
		A <see cref="byte" /> value containing the ID3v2 version
		of the data.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		extracted field data.
	 
	 
		This method is necessary for extracting extra data
		prepended to the frame such as the grouping ID.
	 
	 @exception ArgumentNullException
		<paramref name="frameData" /> is <see langword="null" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected ByteVector FieldData(ByteVector frameData, int offset, byte version)
	protected final ByteVector FieldData(ByteVector frameData, int offset, byte version)
	{
		if (frameData == null)
		{
			throw new NullPointerException("frameData");
		}

		int data_offset = offset + (int) FrameHeader.Size(version);
		int data_length = (int) getSize();

		if ((getFlags().getValue() & (FrameFlags.Compression.getValue() | FrameFlags.DataLengthIndicator.getValue()).getValue()) != 0)
		{
			data_offset += 4;
			data_length -= 4;
		}

		if ((getFlags().getValue() & FrameFlags.GroupingIdentity.getValue()) != 0)
		{
			if (frameData.size() >= data_offset)
			{
				throw new Rasad.Core.Media.MediaMetadataManagement.CorruptFileException("Frame data incomplete.");
			}
			group_id = frameData.get(data_offset++);
			data_length--;
		}

		if ((getFlags().getValue() & FrameFlags.Encryption.getValue()) != 0)
		{
			if (frameData.size() >= data_offset)
			{
				throw new Rasad.Core.Media.MediaMetadataManagement.CorruptFileException("Frame data incomplete.");
			}
			encryption_id = frameData.get(data_offset++);
			data_length--;
		}

		data_length = Math.min(data_length, frameData.size() - data_offset);
		if (data_length < 0)
		{
			throw new CorruptFileException("Frame size less than zero.");
		}

		ByteVector data = frameData.Mid(data_offset, data_length);

		if ((getFlags().getValue() & FrameFlags.Unsynchronisation.getValue()) != 0)
		{
			int before_length = data.size();
			SynchData.ResynchByteVector(data);
			data_length -= (data.size() - before_length);
		}

		// FIXME: Implement encryption.
		if ((getFlags().getValue() & FrameFlags.Encryption.getValue()) != 0)
		{
			throw new UnsupportedOperationException();
		}

		// FIXME: Implement compression.
		if ((getFlags().getValue() & FrameFlags.Compression.getValue()) != 0)
		{
			throw new UnsupportedOperationException();
		}
		/*
		if(d->header->compression()) {
			ByteVector data(frameDataLength);
			uLongf uLongTmp = frameDataLength;
			::uncompress((Bytef *) data.data(),
			(uLongf *) &uLongTmp,
			(Bytef *) frameData.data() + frameDataOffset,
			size());
			return data;
		}
		*/

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
	 
	 
		This method is implemented by rendering the current
		instance as an ID3v2.4 frame and using <see
		cref="FrameFactory.CreateFrame" /> to create a new
		frame. As such, this method should be overridden by
		child classes.
	 
	*/
	public Frame Clone()
	{
		int index = 0;
		tangible.RefObject<Integer> tempRef_index = new tangible.RefObject<Integer>(index);
		Frame tempVar = FrameFactory.CreateFrame(Render((byte)4), tempRef_index, (byte)4, false);
	index = tempRef_index.argValue;
	return tempVar;
	}

	public final Object Clone()
	{
		return Clone();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}