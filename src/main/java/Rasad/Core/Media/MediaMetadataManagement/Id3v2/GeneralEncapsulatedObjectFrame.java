package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// GeneralEncapsulatedObjectFrame.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   generalencapsulatedobjectframe.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2007 Brian Nickel
// Copyright (C) 2007 Scott Wheeler (Original Implementation)
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
	This class extends <see cref="Frame" />, implementing support for
	ID3v2 General Encapsulated Object (GEOB) Frames.
 
 
	<p>A <see cref="GeneralEncapsulatedObjectFrame" /> should be
	used for storing files and other objects relevant to the file but
	not supported by other frames.</p>
 
*/
public class GeneralEncapsulatedObjectFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the text encoding to use when rendering the
		current instance.
	*/
	private StringType encoding = Tag.getDefaultEncoding();

	/** 
		Contains the mime type of <see cref="data" />.
	*/
	private String mime_type = null;

	/** 
		Contains the original file name.
	*/
	private String file_name = null;

	/** 
		Contains the description.
	*/
	private String description = null;

	/** 
		Contains the data.
	*/
	private ByteVector data = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="GeneralEncapsulatedObjectFrame" /> with no
		contents.
	*/
	public GeneralEncapsulatedObjectFrame()
	{
		super(FrameType.GEOB, 4);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="GeneralEncapsulatedObjectFrame" /> by reading its
		raw data in a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public GeneralEncapsulatedObjectFrame(ByteVector data, byte version)
	public GeneralEncapsulatedObjectFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="GeneralEncapsulatedObjectFrame" /> by reading its
		raw data in a specified ID3v2 version.
	 
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
//ORIGINAL LINE: protected internal GeneralEncapsulatedObjectFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected GeneralEncapsulatedObjectFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(header.clone());
		SetData(data, offset, version, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets and sets the text encoding to use when storing the
		current instance.
	 
	 <value>
		A <see cref="string" /> containing the text encoding to
		use when storing the current instance.
	 </value>
	 
		This encoding is overridden when rendering if <see
		cref="Tag.ForceDefaultEncoding" /> is <see
		langword="true" /> or the render version does not support
		it.
	 
	*/
	public final StringType getTextEncoding()
	{
		return encoding;
	}
	public final void setTextEncoding(StringType value)
	{
		encoding = value;
	}

	/** 
		Gets and sets the mime-type of the object stored in the
		current instance.
	 
	 <value>
		A <see cref="string" /> containing the mime-type of the
		object stored in the current instance.
	 </value>
	*/
	public final String getMimeType()
	{
		if (mime_type != null)
		{
			return mime_type;
		}

		return "";
	}
	public final void setMimeType(String value)
	{
		mime_type = value;
	}

	/** 
		Gets and sets the file name of the object stored in the
		current instance.
	 
	 <value>
		A <see cref="string" /> containing the file name of the
		object stored in the current instance.
	 </value>
	*/
	public final String getFileName()
	{
		if (file_name != null)
		{
			return file_name;
		}

		return "";
	}
	public final void setFileName(String value)
	{
		file_name = value;
	}

	/** 
		Gets and sets the description stored in the current
		instance.
	 
	 <value>
		A <see cref="string" /> containing the description
		stored in the current instance.
	 </value>
	 
		There should only be one frame with a matching
		description and type per tag.
	 
	*/
	public final String getDescription()
	{
		if (description != null)
		{
			return description;
		}

		return "";
	}
	public final void setDescription(String value)
	{
		description = value;
	}

	/** 
		Gets and sets the object data stored in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> containing the object data
		stored in the current instance.
	 </value>
	*/
	public final ByteVector getObject()
	{
		return data != null ? data : new ByteVector();
	}
	public final void setObject(ByteVector value)
	{
		data = value;
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
		StringBuilder builder = new StringBuilder();

		if (getDescription().length() == 0)
		{
			builder.append(getDescription());
			builder.append(" ");
		}

		builder.AppendFormat(System.Globalization.CultureInfo.InvariantCulture, "[{0}] {1} bytes", getMimeType(), getObject().size());

		return builder.toString();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Gets a specified encapsulated object frame from the
		specified tag, optionally creating it if it does not
		exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param description
		A <see cref="string" /> specifying the description to
		match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="GeneralEncapsulatedObjectFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found and <paramref name="create" /> is
		<see langword="false" />.
	 
	*/
	public static GeneralEncapsulatedObjectFrame Get(Tag tag, String description, boolean create)
	{
		GeneralEncapsulatedObjectFrame geob;
		for (Frame frame : tag.GetFrames(FrameType.GEOB))
		{
			geob = frame instanceof GeneralEncapsulatedObjectFrame ? (GeneralEncapsulatedObjectFrame)frame : null;

			if (geob == null)
			{
				continue;
			}

			if (!geob.getDescription().equals(description))
			{
				continue;
			}

			return geob;
		}

		if (!create)
		{
			return null;
		}

		geob = new GeneralEncapsulatedObjectFrame();
		geob.setDescription(description);
		tag.AddFrame(geob);
		return geob;
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
	 
	 @exception CorruptFileException
		<paramref name="data" /> contains less than 5 bytes.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override void ParseFields(ByteVector data, byte version)
	@Override
	protected void ParseFields(ByteVector data, byte version)
	{
		if (data.size() < 4)
		{
			throw new CorruptFileException("An object frame must contain at least 4 bytes.");
		}

		int start = 0;

		encoding = StringType.forValue(data.get(start++));

		int end = data.Find(ByteVector.TextDelimiter(StringType.Latin1), start);

		if (end < start)
		{
			return;
		}

		mime_type = data.toString(StringType.Latin1, start, end - start);

		ByteVector delim = ByteVector.TextDelimiter(encoding);
		start = end + 1;
		end = data.Find(delim, start, delim.size());

		if (end < start)
		{
			return;
		}

		file_name = data.toString(encoding, start, end - start);
		start = end + delim.size();
		end = data.Find(delim, start, delim.size());

		if (end < start)
		{
			return;
		}

		description = data.toString(encoding, start, end - start);
		start = end + delim.size();

		data.subList(0, start).clear();
		this.data = data;
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
		StringType encoding = CorrectEncoding(this.encoding, version);
		ByteVector v = new ByteVector();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add((byte) encoding);
		v.add((byte) encoding.getValue());

		if (getMimeType() != null)
		{
			v.add(ByteVector.FromString(getMimeType(), StringType.Latin1));
		}
		v.add(ByteVector.TextDelimiter(StringType.Latin1));

		if (getFileName() != null)
		{
			v.add(ByteVector.FromString(getFileName(), encoding));
		}
		v.add(ByteVector.TextDelimiter(encoding));

		if (getDescription() != null)
		{
			v.add(ByteVector.FromString(getDescription(), encoding));
		}
		v.add(ByteVector.TextDelimiter(encoding));

		v.add(data);
		return v;
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
		GeneralEncapsulatedObjectFrame frame = new GeneralEncapsulatedObjectFrame();
		frame.encoding = encoding;
		frame.mime_type = mime_type;
		frame.file_name = file_name;
		frame.description = description;
		if (data != null)
		{
			frame.data = new ByteVector(data);
		}
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}