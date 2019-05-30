package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// PopularimeterFrame.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
// 
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
// 
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//


/** 
	This class extends <see cref="Frame" />, implementing support for
	ID3v2 Popularimeter (POPM) Frames.
*/
public class PopularimeterFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Properties

	/** 
		Contains the email of the user this frame belongs to.
	*/
	private String user = "";

	/** 
		Contains the rating of the files from 0 to 255.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte rating = 0;
	private byte rating = 0;

	/** 
		Contains the number of times this file has been played.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong play_count = 0;
	private long play_count = 0;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="PopularimeterFrame" /> for a specified user with a
		rating and play count of zero.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public PopularimeterFrame(String user)
	{
		super(FrameType.POPM, 4);
		setUser(user);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="PopularimeterFrame" /> by reading its raw data in a
		specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public PopularimeterFrame(ByteVector data, byte version)
	public PopularimeterFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="PopularimeterFrame" /> by reading its raw data in a
		specified ID3v2 version.
	 
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
//ORIGINAL LINE: protected internal PopularimeterFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected PopularimeterFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(header.clone());
		SetData(data, offset, version, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets and sets the user to whom the current instance
		belongs.
	 
	 <value>
		A <see cref="string" /> containing the user to whom the
		current instance belongs.
	 </value>
	*/
	public final String getUser()
	{
		return user;
	}
	public final void setUser(String value)
	{
		user = value != null ? value : "";
	}

	/** 
		Gets and sets the rating of the current instance.
	 
	 <value>
		A <see cref="byte" /> containing the rating of the
		current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getRating()
	public final byte getRating()
	{
		return rating;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setRating(byte value)
	public final void setRating(byte value)
	{
		rating = value;
	}

	/** 
		Gets and sets the play count of the current instance.
	 
	 <value>
		A <see cref="ulong" /> containing the play count of the
		current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getPlayCount()
	public final long getPlayCount()
	{
		return play_count;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setPlayCount(ulong value)
	public final void setPlayCount(long value)
	{
		play_count = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Gets a popularimeter frame from a specified tag,
		optionally creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param user
		A <see cref="string" /> containing the user to search for
		in the current instance.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="PopularimeterFrame" /> object containing the
		matching frame, or <see langword="null" /> if a match
		wasn't found and <paramref name="create" /> is <see
		langword="false" />.
	 
	*/
	public static PopularimeterFrame Get(Tag tag, String user, boolean create)
	{
		PopularimeterFrame popm;
		for (Frame frame : tag)
		{
			popm = frame instanceof PopularimeterFrame ? (PopularimeterFrame)frame : null;

			if (popm != null && popm.user.equals(user))
			{
				return popm;
			}
		}

		if (!create)
		{
			return null;
		}

		popm = new PopularimeterFrame(user);
		tag.AddFrame(popm);
		return popm;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

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
//ORIGINAL LINE: protected override void ParseFields(ByteVector data, byte version)
	@Override
	protected void ParseFields(ByteVector data, byte version)
	{
		ByteVector delim = ByteVector.TextDelimiter(StringType.Latin1);

		int index = tangible.ListHelper.find(data, delim);
		if (index < 0)
		{
			throw new CorruptFileException("Popularimeter frame does not contain a text delimiter");
		}
		if (index + 2 > data.size())
		{
			throw new CorruptFileException("Popularimeter is too short");
		}

		user = data.toString(StringType.Latin1, 0, index);
		rating = data.get(index + 1);
		play_count = data.Mid(index + 2).ToULong();
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
		ByteVector data = ByteVector.FromULong(play_count);
		while (data.size() > 0 && data.get(0) == 0)
		{
			data.remove(0);
		}

		data.add(0, rating);
		data.add(0, 0);
		data.add(0, ByteVector.FromString(user, StringType.Latin1));
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
		PopularimeterFrame frame = new PopularimeterFrame(user);
		frame.play_count = play_count;
		frame.rating = rating;
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}