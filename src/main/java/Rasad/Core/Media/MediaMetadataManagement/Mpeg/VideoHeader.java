package Rasad.Core.Media.MediaMetadataManagement.Mpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// VideoHeader.cs: Provides information about an MPEG video stream.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
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
	This structure implements <see cref="IVideoCodec" /> and provides
	information about an MPEG video stream.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct VideoHeader : IVideoCodec
public final class VideoHeader implements IVideoCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Static Fields

	/** 
		Contains frame rate values.
	*/
	private static final double[] frame_rates = new double[] {0, 24000d / 1001d, 24, 25, 30000d / 1001d, 30, 50, 60000d / 1001d, 60};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the video width.
	*/
	private int width;

	/** 
		Contains the video height.
	*/
	private int height;

	/** 
		Contains the index in <see cref="frame_rates" /> of the
		video frame rate.
	*/
	private int frame_rate_index;

	/** 
		Contains the video bitrate.
	*/
	private int bitrate;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="VideoHeader" /> by reading it from a specified
		location in a specified file.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to read from.
	 
	 @param position
		A <see cref="long" /> value indicating the position in
		<paramref name="file" /> at which the header begins.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		Insufficient data could be read for the header.
	 
	*/
	public VideoHeader()
	{
	}

	public VideoHeader(Rasad.Core.Media.MediaMetadataManagement.File file, long position)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.Seek(position);
		ByteVector data = file.ReadBlock(7);

		if (data.size() < 7)
		{
			throw new CorruptFileException("Insufficient data in header.");
		}

//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		width = data.Mid(0, 2).ToUShort() >>> 4;
		height = data.Mid(1, 2).ToUShort() & 0x0FFF;
		frame_rate_index = data.get(3) & 0x0F;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		bitrate = (int)((data.Mid(4, 3).ToUInt() >>> 6) & 0x3FFFF);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the duration of the media represented by the current
		instance.
	 
	 <value>
		Always <see cref="TimeSpan.Zero" />.
	 </value>
	*/
	public TimeSpan getDuration()
	{
		return TimeSpan.Zero;
	}

	/** 
		Gets the types of media represented by the current
		instance.
	 
	 <value>
		Always <see cref="MediaTypes.Video" />.
	 </value>
	*/
	public MediaTypes getMediaTypes()
	{
		return MediaTypes.Video;
	}

	/** 
		Gets a text description of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the media represented by the current instance.
	 </value>
	*/
	public String getDescription()
	{
		return "MPEG Video";
	}

	/** 
		Gets the width of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the width of the
		video represented by the current instance.
	 </value>
	*/
	public int getVideoWidth()
	{
		return width;
	}

	/** 
		Gets the height of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the height of the
		video represented by the current instance.
	 </value>
	*/
	public int getVideoHeight()
	{
		return height;
	}

	/** 
		Gets the frame rate of the video represented by the
		current instance.
	 
	 <value>
		A <see cref="double" /> value containing the frame rate
		of the video represented by the current instance.
	 </value>
	*/
	public double getVideoFrameRate()
	{
		return frame_rate_index < 9 ? frame_rates [frame_rate_index] : 0;
	}

	/** 
		Gets the bitrate of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing a bitrate of the
		video represented by the current instance.
	 </value>
	*/
	public int getVideoBitrate()
	{
		return bitrate;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public VideoHeader clone()
	{
		VideoHeader varCopy = new VideoHeader();

		varCopy.width = this.width;
		varCopy.height = this.height;
		varCopy.frame_rate_index = this.frame_rate_index;
		varCopy.bitrate = this.bitrate;

		return varCopy;
	}
}