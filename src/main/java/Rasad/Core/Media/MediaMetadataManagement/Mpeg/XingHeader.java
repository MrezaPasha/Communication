package Rasad.Core.Media.MediaMetadataManagement.Mpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// XingHeader.cs: Provides information about a variable bitrate MPEG audio
// stream.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   xingheader.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2003 by Ismael Orenstein (Original Implementation)
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
	This structure provides information about a variable bitrate MPEG
	audio stream.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct XingHeader
public final class XingHeader
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the frame count.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint frames;
	private int frames;

	/** 
		Contains the stream size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint size;
	private int size;

	/** 
		Indicates that a physical Xing header is present.
	*/
	private boolean present;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Fields

	/** 
		Contains te Xing identifier.
	 
	 <value>
		"Xing"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "Xing";

	/** 
		An empty and unset Xing header.
	*/
	public static final XingHeader Unknown = new XingHeader(0, 0);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="XingHeader" /> with a specified frame count and
		size.
	 
	 @param frame
		A <see cref="uint" /> value specifying the frame count of
		the audio represented by the new instance.
	 
	 @param size
		A <see cref="uint" /> value specifying the stream size of
		the audio represented by the new instance.
	 
	*/
	public XingHeader()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private XingHeader(uint frame, uint size)
	private XingHeader(int frame, int size)
	{
		this.frames = frame;
		this.size = size;
		this.present = false;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="XingHeader" /> by reading its raw contents.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		Xing header.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> does not start with <see
		cref="FileIdentifier" />.
	 
	*/
	public XingHeader(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		// Check to see if a valid Xing header is available.
		if (!data.StartsWith(FileIdentifier))
		{
			throw new CorruptFileException("Not a valid Xing header");
		}

		int position = 8;

		if ((data.get(7) & 0x01) != 0)
		{
			frames = data.Mid(position, 4).ToUInt();
			position += 4;
		}
		else
		{
			frames = 0;
		}

		if ((data.get(7) & 0x02) != 0)
		{
			size = data.Mid(position, 4).ToUInt();
			position += 4;
		}
		else
		{
			size = 0;
		}

		present = true;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the total number of frames in the file, as indicated
		by the current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the number of
		frames in the file, or <c>0</c> if not specified.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getTotalFrames()
	public int getTotalFrames()
	{
		return frames;
	}

	/** 
		Gets the total size of the file, as indicated by the
		current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the total size of
		the file, or <c>0</c> if not specified.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getTotalSize()
	public int getTotalSize()
	{
		return size;
	}

	/** 
		Gets whether or not a physical Xing header is present in
		the file.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		current instance represents a physical Xing header.
	 </value>
	*/
	public boolean getPresent()
	{
		return present;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Gets the offset at which a Xing header would appear in an
		MPEG audio packet based on the version and channel mode.
	 
	 @param version
		A <see cref="Version" /> value specifying the version of
		the MPEG audio packet.
	 
	 @param channelMode
		A <see cref="ChannelMode" /> value specifying the channel
		mode of the MPEG audio packet.
	 
	 @return 
		A <see cref="int" /> value indicating the offset in an
		MPEG audio packet at which the Xing header would appear.
	 
	*/
	public static int XingHeaderOffset(Version version, ChannelMode channelMode)
	{
		boolean single_channel = channelMode == ChannelMode.SingleChannel;

		if (version == Version.Version1)
		{
			return single_channel ? 0x15 : 0x24;
		}
		else
		{
			return single_channel ? 0x0D : 0x15;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public XingHeader clone()
	{
		XingHeader varCopy = new XingHeader();

		varCopy.frames = this.frames;
		varCopy.size = this.size;
		varCopy.present = this.present;

		return varCopy;
	}
}