package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.time.*;

//
// IsoMovieHeaderBox.cs: Provides an implementation of a ISO/IEC 14496-12
// MovieHeaderBox.
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
	This class extends <see cref="FullBox" /> to provide an
	implementation of a ISO/IEC 14496-12 MovieHeaderBox.
*/
public class IsoMovieHeaderBox extends FullBox
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the creation time of the movie.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong creation_time;
	private long creation_time;

	/** 
		Contains the modification time of the movie.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong modification_time;
	private long modification_time;

	/** 
		Contains the timescale.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint timescale;
	private int timescale;

	/** 
		Contains the duration.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong duration;
	private long duration;

	/** 
		Contains the rate.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint rate;
	private int rate;

	/** 
		Contains the volume.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort volume;
	private short volume;

	/** 
		Contains the next track ID.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint next_track_id;
	private int next_track_id;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="IsoMovieHeaderBox" /> with a provided header and
		handler by reading the contents from a specified file.
	 
	 @param header
		A <see cref="BoxHeader" /> object containing the header
		to use for the new instance.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to read the contents
		of the box from.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	*/
	public IsoMovieHeaderBox(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), file, handler);
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		int bytes_remaining = getDataSize();
		ByteVector data;

		if (getVersion() == 1)
		{
			// Read version one (large integers).
			data = file.ReadBlock(Math.min(28, bytes_remaining));
			if (data.size() >= 8)
			{
				creation_time = data.Mid(0, 8).ToULong();
			}
			if (data.size() >= 16)
			{
				modification_time = data.Mid(8, 8).ToULong();
			}
			if (data.size() >= 20)
			{
				timescale = data.Mid(16, 4).ToUInt();
			}
			if (data.size() >= 28)
			{
				duration = data.Mid(20, 8).ToULong();
			}
			bytes_remaining -= 28;
		}
		else
		{
			// Read version zero (normal integers).
			data = file.ReadBlock(Math.min(16, bytes_remaining));
			if (data.size() >= 4)
			{
				creation_time = data.Mid(0, 4).ToUInt();
			}
			if (data.size() >= 8)
			{
				modification_time = data.Mid(4, 4).ToUInt();
			}
			if (data.size() >= 12)
			{
				timescale = data.Mid(8, 4).ToUInt();
			}
			if (data.size() >= 16)
			{
				duration = data.Mid(12, 4).ToUInt();
			}
			bytes_remaining -= 16;
		}

		data = file.ReadBlock(Math.min(6, bytes_remaining));
		if (data.size() >= 4)
		{
			rate = data.Mid(0, 4).ToUInt();
		}
		if (data.size() >= 6)
		{
			volume = data.Mid(4, 2).ToUShort();
		}
		file.Seek(file.getTell() + 70);
		bytes_remaining -= 76;

		data = file.ReadBlock(Math.min(4, bytes_remaining));

		if (data.size() >= 4)
		{
			next_track_id = data.Mid(0, 4).ToUInt();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the creation time of movie represented by the
		current instance.
	 
	 <value>
		A <see cref="DateTime" /> value containing the creation
		time of the movie represented by the current instance.
	 </value>
	*/
	public final LocalDateTime getCreationTime()
	{
		return (LocalDateTime.of(1904, 1, 1, 0, 0, 0)).AddTicks((long)(10000000 * creation_time));
	}

	/** 
		Gets the modification time of movie represented by the
		current instance.
	 
	 <value>
		A <see cref="DateTime" /> value containing the
		modification time of the movie represented by the current
		instance.
	 </value>
	*/
	public final LocalDateTime getModificationTime()
	{
		return (LocalDateTime.of(1904, 1, 1, 0, 0, 0)).AddTicks((long)(10000000 * modification_time));
	}

	/** 
		Gets the duration of the movie represented by the current
		instance.
	 
	 <value>
		A <see cref="TimeSpan" /> value containing the duration
		of the movie represented by the current instance.
	 </value>
	*/
	public final TimeSpan getDuration()
	{
			// The length is the number of ticks divided by
			// ticks per second.
		return TimeSpan.FromSeconds((double) duration / (double) timescale);
	}

	/** 
		Gets the playback rate of the movie represented by the
		current instance.
	 
	 <value>
		A <see cref="double" /> value containing the playback
		rate of the movie represented by the current instance.
	 </value>
	*/
	public final double getRate()
	{
		return ((double) rate) / ((double) 0x10000);
	}

	/** 
		Gets the playback volume of the movie represented by the
		current instance.
	 
	 <value>
		A <see cref="double" /> value containing the playback
		volume of the movie represented by the current instance.
	 </value>
	*/
	public final double getVolume()
	{
		return ((double) volume) / ((double) 0x100);
	}

	/** 
		Gets the ID of the next track in the movie represented by
		the current instance.
	 
	 <value>
	   A <see cref="uint" /> value containing the ID of the next
	   track in the movie represented by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getNextTrackId()
	public final int getNextTrackId()
	{
		return next_track_id;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}