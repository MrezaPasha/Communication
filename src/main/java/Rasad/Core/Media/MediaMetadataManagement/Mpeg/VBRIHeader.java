package Rasad.Core.Media.MediaMetadataManagement.Mpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// VBRIHeader.cs: Provides information about a variable bitrate MPEG audio
// stream encoded with the Fraunhofer Encoder.
//
// Author:
//   Helmut Wahrmann
//
// Original Source:
//   XingHeader.cs
//
// Copyright (C) 2007 Helmut Wahrmann
// Copyright (C) 2005-2007 Brian Nickel
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
	audio stream encoded by the Fraunhofer Encoder.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct VBRIHeader
public final class VBRIHeader
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
		Indicates that a physical VBRI header is present.
	*/
	private boolean present;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Fields

	/** 
		Contains te VBRI identifier.
	 
	 <value>
		"VBRI"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "VBRI";

	/** 
		An empty and unset VBRI header.
	*/
	public static final VBRIHeader Unknown = new VBRIHeader(0, 0);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="VBRIHeader" /> with a specified frame count and
		size.
	 
	 @param frame
		A <see cref="uint" /> value specifying the frame count of
		the audio represented by the new instance.
	 
	 @param size
		A <see cref="uint" /> value specifying the stream size of
		the audio represented by the new instance.
	 
	*/
	public VBRIHeader()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private VBRIHeader(uint frame, uint size)
	private VBRIHeader(int frame, int size)
	{
		this.frames = frame;
		this.size = size;
		this.present = false;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="VBRIHeader" /> by reading its raw contents.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		VBRI header.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> does not start with <see
		cref="FileIdentifier" />.
	 
	*/
	public VBRIHeader(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		// Check to see if a valid VBRI header is available.
		if (!data.StartsWith(FileIdentifier))
		{
			throw new CorruptFileException("Not a valid VBRI header");
		}

		// Size starts at Position 10
		int position = 10;

		size = data.Mid(position, 4).ToUInt();
		position += 4;

		// The number of Frames are found at Posistion 14
		frames = data.Mid(position, 4).ToUInt();
		position += 4;

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
		Gets whether or not a physical VBRI header is present in
		the file.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		current instance represents a physical VBRI header.
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
		Gets the offset at which a VBRI header would appear in an
		MPEG audio packet.
		Always 32 bytes after the end of the first MPEG Header.
	 
	 @return 
		A <see cref="int" /> value indicating the offset in an
		MPEG audio packet at which the VBRI header would appear.
	 
	*/
	public static int VBRIHeaderOffset()
	{
		// A VBRI header always appears 32 bytes after the end
		// of the first MPEG Header. So it's position 36 (0x24).
		  return 0x24;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

	public VBRIHeader clone()
	{
		VBRIHeader varCopy = new VBRIHeader();

		varCopy.frames = this.frames;
		varCopy.size = this.size;
		varCopy.present = this.present;

		return varCopy;
	}
}