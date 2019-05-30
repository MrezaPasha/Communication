package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// StreamPropertiesObject.cs: Provides a representation of an ASF Stream
// Properties object which can be read from and written to disk.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2006-2007 Brian Nickel
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
	This class extends <see cref="Object" /> to provide a
	representation of an ASF Stream Properties object which can be
	read from and written to disk.
*/
public class StreamPropertiesObject extends Object
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the stream type GUID.
	*/
	private UUID stream_type;

	/** 
		Contains the error correction type GUID.
	*/
	private UUID error_correction_type;

	/** 
		Contains the time offset of the stream.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong time_offset;
	private long time_offset;

	/** 
		Contains the stream flags.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort flags;
	private short flags;

	/** 
		Contains the reserved data.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint reserved;
	private int reserved;

	/** 
		Contains the type specific data.
	*/
	private ByteVector type_specific_data;

	/** 
		Contains the error correction data.
	*/
	private ByteVector error_correction_data;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="PaddingObject" /> by reading the contents from a
		specified position in a specified file.
	 
	 @param file
		A <see cref="Asf.File" /> object containing the file from
		which the contents of the new instance are to be read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read the object.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	 @exception CorruptFileException
		The object read from disk does not have the correct GUID
		or smaller than the minimum size.
	 
	*/
	public StreamPropertiesObject(Asf.File file, long position)
	{
		super(file, position);
		if (!UUID.equals(Asf.Guid.AsfStreamPropertiesObject))
		{
			throw new CorruptFileException("Object GUID incorrect.");
		}

		if (getOriginalSize() < 78)
		{
			throw new CorruptFileException("Object size too small.");
		}

		stream_type = file.ReadGuid();
		error_correction_type = file.ReadGuid();
		time_offset = file.ReadQWord();

		int type_specific_data_length = (int) file.ReadDWord();
		int error_correction_data_length = (int) file.ReadDWord();

		flags = file.ReadWord();
		reserved = file.ReadDWord();
		type_specific_data = file.ReadBlock(type_specific_data_length);
		error_correction_data = file.ReadBlock(error_correction_data_length);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the codec information contained in the current
		instance.
	 
	 <value>
		A <see cref="ICodec" /> object containing the codec
		information read from <see cref="TypeSpecificData" /> or
		<see langword="null" /> if the data could not be decoded.
	 </value>
	*/
	public final ICodec getCodec()
	{
		if (stream_type.equals(Asf.Guid.AsfAudioMedia))
		{
			return new Riff.WaveFormatEx(type_specific_data, 0);
		}

		if (stream_type.equals(Asf.Guid.AsfVideoMedia))
		{
			return new Rasad.Core.Media.MediaMetadataManagement.Riff.BitmapInfoHeader(type_specific_data, 11);
		}

		return null;
	}

	/** 
		Gets the stream type GUID of the current instance.
	 
	 
		A <see cref="System.Guid" /> object containing the stream
		type GUID of the current instance.
	*/
	public final UUID getStreamType()
	{
		return stream_type;
	}

	/** 
		Gets the error correction type GUID of the current
		instance.
	 
	 
		A <see cref="System.Guid" /> object containing the error
		correction type GUID of the current instance.
	*/
	public final UUID getErrorCorrectionType()
	{
		return error_correction_type;
	}

	/** 
		Gets the time offset at which the stream described by the
		current instance begins.
	 
	 <value>
		A <see cref="TimeSpan" /> value containing the time
		offset at which the stream described by the current
		instance begins.
	 </value>
	*/
	public final TimeSpan getTimeOffset()
	{
		return new TimeSpan((long)time_offset);
	}

	/** 
		Gets the flags that apply to the current instance.
	 
	 <value>
		A <see cref="ushort" /> value containing the flags that
		apply to the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getFlags()
	public final short getFlags()
	{
		return flags;
	}

	/** 
		Gets the type specific data contained in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the type
		specific data contained in the current instance.
	 </value>
	 
		The contents of this value are dependant on the type
		contained in <see cref="StreamType" />.
	 
	*/
	public final ByteVector getTypeSpecificData()
	{
		return type_specific_data;
	}

	/** 
		Gets the error correction data contained in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the error
		correction data contained in the current instance.
	 </value>
	 
		The contents of this value are dependant on the type
		contained in <see cref="ErrorCorrectionType" />.
	 
	*/
	public final ByteVector getErrorCorrectionData()
	{
		return error_correction_data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Renders the current instance as a raw ASF object.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	@Override
	public ByteVector Render()
	{
		ByteVector output = stream_type.ToByteArray();
		output.add(error_correction_type.ToByteArray());
		output.add(RenderQWord(time_offset));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(RenderDWord((uint) type_specific_data.Count));
		output.add(RenderDWord((int) type_specific_data.size()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(RenderDWord((uint) error_correction_data.Count));
		output.add(RenderDWord((int) error_correction_data.size()));
		output.add(RenderWord(flags));
		output.add(RenderDWord(reserved));
		output.add(type_specific_data);
		output.add(error_correction_data);

		return Render(output);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}