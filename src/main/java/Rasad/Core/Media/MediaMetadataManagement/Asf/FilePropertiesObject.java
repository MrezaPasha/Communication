package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;
import java.time.*;

//
// FilePropertiesObject.cs: Provides a representation of an ASF File Properties
// object which can be read from and written to disk.
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
	representation of an ASF File Properties object which can be read
	from and written to disk.
*/
public class FilePropertiesObject extends Object
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the GUID for the file.
	*/
	private UUID file_id;

	/** 
		Contains the file size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong file_size;
	private long file_size;

	/** 
		Contains the creation date.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong creation_date;
	private long creation_date;

	/** 
		Contains the packet count.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong data_packets_count;
	private long data_packets_count;

	/** 
		Contains the play duration.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong play_duration;
	private long play_duration;

	/** 
		Contains the send duration.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong send_duration;
	private long send_duration;

	/** 
		Contains the preroll.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong preroll;
	private long preroll;

	/** 
		Contains the file flags.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint flags;
	private int flags;

	/** 
		Contains the minimum packet size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint minimum_data_packet_size;
	private int minimum_data_packet_size;

	/** 
		Contains the maxximum packet size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint maximum_data_packet_size;
	private int maximum_data_packet_size;

	/** 
		Contains the maximum bitrate of the file.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint maximum_bitrate;
	private int maximum_bitrate;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="FilePropertiesObject" /> by reading the contents
		from a specified position in a specified file.
	 
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
	public FilePropertiesObject(Asf.File file, long position)
	{
		super(file, position);
		if (!UUID.equals(Asf.Guid.AsfFilePropertiesObject))
		{
			throw new CorruptFileException("Object GUID incorrect.");
		}

		if (getOriginalSize() < 104)
		{
			throw new CorruptFileException("Object size too small.");
		}

		file_id = file.ReadGuid();
		file_size = file.ReadQWord();
		creation_date = file.ReadQWord();
		data_packets_count = file.ReadQWord();
		send_duration = file.ReadQWord();
		play_duration = file.ReadQWord();
		preroll = file.ReadQWord();
		flags = file.ReadDWord();
		minimum_data_packet_size = file.ReadDWord();
		maximum_data_packet_size = file.ReadDWord();
		maximum_bitrate = file.ReadDWord();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the GUID for the file described by the current
		instance.
	 
	 <value>
		A <see cref="System.Guid" /> value containing the GUID
		for the file described by the current instance.
	 </value>
	*/
	public final UUID getFileId()
	{
		return file_id;
	}

	/** 
		Gets the size of the file described by the current
		instance.
	 
	 <value>
		A <see cref="ulong" /> value containing the size of the
		file described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getFileSize()
	public final long getFileSize()
	{
		return file_size;
	}

	/** 
		Gets the creation date of the file described by the
		current instance.
	 
	 <value>
		A <see cref="DateTime" /> value containing the creation
		date of the file described by the current instance.
	 </value>
	*/
	public final LocalDateTime getCreationDate()
	{
		return LocalDateTime.of((long)creation_date);
	}

	/** 
		Gets the number of data packets in the file described by
		the current instance.
	 
	 <value>
		A <see cref="ulong" /> value containing the number of
		data packets in the file described by the current
		instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getDataPacketsCount()
	public final long getDataPacketsCount()
	{
		return data_packets_count;
	}

	/** 
		Gets the play duration of the file described by the
		current instance.
	 
	 <value>
		A <see cref="TimeSpan" /> value containing the play
		duration of the file described by the current instance.
	 </value>
	*/
	public final TimeSpan getPlayDuration()
	{
		return new TimeSpan((long)play_duration);
	}

	/** 
		Gets the send duration of the file described by the
		current instance.
	 
	 <value>
		A <see cref="TimeSpan" /> value containing the send
		duration of the file described by the current instance.
	 </value>
	*/
	public final TimeSpan getSendDuration()
	{
		return new TimeSpan((long)send_duration);
	}

	/** 
		Gets the pre-roll of the file described by the current
		instance.
	 
	 <value>
		A <see cref="ulong" /> value containing the pre-roll of
		the file described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getPreroll()
	public final long getPreroll()
	{
		return preroll;
	}

	/** 
		Gets the flags of the file described by the current
		instance.
	 
	 <value>
		A <see cref="uint" /> value containing the flags of the
		file described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getFlags()
	public final int getFlags()
	{
		return flags;
	}

	/** 
		Gets the minimum data packet size of the file described
		by the current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the minimum data
		packet size of the file described by the current
		instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getMinimumDataPacketSize()
	public final int getMinimumDataPacketSize()
	{
		return minimum_data_packet_size;
	}

	/** 
		Gets the maximum data packet size of the file described
		by the current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the maximum data
		packet size of the file described by the current
		instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getMaximumDataPacketSize()
	public final int getMaximumDataPacketSize()
	{
		return maximum_data_packet_size;
	}

	/** 
		Gets the maximum bitrate of the file described by the
		current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the maximum
		bitrate of the file described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getMaximumBitrate()
	public final int getMaximumBitrate()
	{
		return maximum_bitrate;
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
		ByteVector output = file_id.ToByteArray();
		output.add(RenderQWord(file_size));
		output.add(RenderQWord(creation_date));
		output.add(RenderQWord(data_packets_count));
		output.add(RenderQWord(send_duration));
		output.add(RenderQWord(play_duration));
		output.add(RenderQWord(preroll));
		output.add(RenderDWord(flags));
		output.add(RenderDWord(minimum_data_packet_size));
		output.add(RenderDWord(maximum_data_packet_size));
		output.add(RenderDWord(maximum_bitrate));

		return Render(output);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}