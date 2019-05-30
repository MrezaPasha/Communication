package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.Media.MediaMetadataManagement.Id3v2.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// UnsynchronisedLyricsFrame.cs:
//
// Author:
//   Patrick Laplante
//
// Original Source:
//   Rasad.Core.Media.MediaMetadataManagement.Id3v2.CommentsFrame
//
// Copyright (C) 2007 Brian Nickel (Original Implementation)
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
	This class extends <see cref="Frame" />, implementing support for
	ID3v2 Unsynchronised Lyrics (USLT) Frames.
*/
public class UnsynchronisedLyricsFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Properties

	/** 
		Contains the text encoding to use when rendering the
		current instance.
	*/
	private StringType encoding = Tag.getDefaultEncoding();

	/** 
		Contains the ISO-639-2 language code of the current
		instance.
	*/
	private String language = null;

	/** 
		Contains the description of the current instance.
	*/
	private String description = null;

	/** 
		Contains the lyrics text of the current instance.
	*/
	private String text = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="UnsynchronisedLyricsFrame" /> with a specified
		description, ISO-639-2 language code, and text encoding.
	 
	 @param description
		A <see cref="string" /> containing the description of the
		new frame.
	 
	 @param language
		A <see cref="string" /> containing the ISO-639-2 language
		code of the new frame.
	 
	 @param encoding
		A <see cref="StringType" /> containing the text encoding
		to use when rendering the new frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public UnsynchronisedLyricsFrame(String description, String language, StringType encoding)
	{
		super(FrameType.USLT, 4);
		this.encoding = encoding;
		this.language = language;
		this.description = description;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UnsynchronisedLyricsFrame" /> with a specified
		description and ISO-639-2 language code.
	 
	 @param description
		A <see cref="string" /> containing the description of the
		new frame.
	 
	 @param language
		A <see cref="string" /> containing the ISO-639-2 language
		code of the new frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public UnsynchronisedLyricsFrame(String description, String language)
	{
		this(description, language, Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag.getDefaultEncoding());
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UnsynchronisedLyricsFrame" /> with a specified
		description.
	 
	 @param description
		A <see cref="string" /> containing the description of the
		new frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public UnsynchronisedLyricsFrame(String description)
	{
		this(description, null);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UnsynchronisedLyricsFrame" /> by reading its raw
		data in a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public UnsynchronisedLyricsFrame(ByteVector data, byte version)
	public UnsynchronisedLyricsFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UnsynchronisedLyricsFrame" /> by reading its raw
		data in a specified ID3v2 version.
	 
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
//ORIGINAL LINE: protected internal UnsynchronisedLyricsFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected UnsynchronisedLyricsFrame(ByteVector data, int offset, FrameHeader header, byte version)
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
		Gets and sets the ISO-639-2 language code stored in the
		current instance.
	 
	 <value>
		A <see cref="string" /> containing the ISO-639-2 language
		code stored in the current instance.
	 </value>
	 
		There should only be one file with a matching description
		and ISO-639-2 language code per tag.
	 
	*/
	public final String getLanguage()
	{
		if (language != null && language.length() > 2)
		{
			return language.substring(0, 3);
		}

		return "XXX";
	}
	public final void setLanguage(String value)
	{
		language = value;
	}

	/** 
		Gets and sets the description stored in the current
		instance.
	 
	 <value>
		A <see cref="string" /> containing the description
		stored in the current instance.
	 </value>
	 
		There should only be one frame with a matching
		description and ISO-639-2 language code per tag.
	 
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
		Gets and sets the lyrical text stored in the current
		instance.
	 
	 <value>
		A <see cref="string" /> containing the lyrical text
		stored in the current instance.
	 </value>
	*/
	public final String getText()
	{
		if (text != null)
		{
			return text;
		}

		return "";
	}
	public final void setText(String value)
	{
		text = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Gets a string representation of the current instance.
	 
	 @return 
		A <see cref="string" /> containing the lyrical text.
	 
	*/
	@Override
	public String toString()
	{
		return getText();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Gets a specified lyrics frame from the specified tag,
		optionally creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param description
		A <see cref="string" /> specifying the description to
		match.
	 
	 @param language
		A <see cref="string" /> specifying the ISO-639-2 language
	   code to match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="UnsynchronisedLyricsFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found and <paramref name="create" /> is
		<see langword="false" />.
	 
	*/
	public static UnsynchronisedLyricsFrame Get(Tag tag, String description, String language, boolean create)
	{
		UnsynchronisedLyricsFrame uslt;
		for (Frame frame : tag.GetFrames(FrameType.USLT))
		{
			uslt = frame instanceof UnsynchronisedLyricsFrame ? (UnsynchronisedLyricsFrame)frame : null;

			if (uslt == null)
			{
				continue;
			}

			if (!uslt.getDescription().equals(description))
			{
				continue;
			}

			if (language != null && !uslt.getLanguage().equals(language))
			{
				continue;
			}

			return uslt;
		}

		if (!create)
		{
			return null;
		}

		uslt = new UnsynchronisedLyricsFrame(description, language);
		tag.AddFrame(uslt);
		return uslt;
	}

	/** 
		Gets a specified comments frame from the specified tag,
		trying to to match the description and language but
		accepting an incomplete match.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param description
		A <see cref="string" /> specifying the description to
		match.
	 
	 @param language
		A <see cref="string" /> specifying the ISO-639-2 language
	   code to match.
	 
	 @return 
		A <see cref="UnsynchronisedLyricsFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found.
	 
	 
		<p>The method tries matching with the following order
		of precidence:</p>
		<list type="number">
		   <item><term>The first frame with a matching
		   description and language.</term></item>
		   <item><term>The first frame with a matching
		   language.</term></item>
		   <item><term>The first frame with a matching
		   description.</term></item>
		   <item><term>The first frame.</term></item>
		</list>
	 
	*/
	public static UnsynchronisedLyricsFrame GetPreferred(Tag tag, String description, String language)
	{
		// This is weird, so bear with me. The best thing we can
		// have is something straightforward and in our own
		// language. If it has a description, then it is
		// probably used for something other than an actual
		// comment. If that doesn't work, we'd still rather have
		// something in our language than something in another.
		// After that all we have left are things in other
		// languages, so we'd rather have one with actual
		// content, so we try to get one with no description
		// first.

		int best_value = -1;
		UnsynchronisedLyricsFrame best_frame = null;

		for (Frame frame : tag.GetFrames(FrameType.USLT))
		{
			UnsynchronisedLyricsFrame uslt = frame instanceof UnsynchronisedLyricsFrame ? (UnsynchronisedLyricsFrame)frame : null;

			if (uslt == null)
			{
				continue;
			}

			boolean same_name = uslt.getDescription().equals(description);
			boolean same_lang = uslt.getLanguage().equals(language);

			if (same_name && same_lang)
			{
				return uslt;
			}

			int value = same_lang ? 2 : same_name ? 1 : 0;

			if (value <= best_value)
			{
				continue;
			}

			best_value = value;
			best_frame = uslt;
		}

		return best_frame;
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
		if (data.size() < 4)
		{
			throw new CorruptFileException("Not enough bytes in field.");
		}

		encoding = StringType.forValue(data.get(0));
		language = data.toString(StringType.Latin1, 1, 3);

		String [] split = data.ToStrings(encoding, 4, 2);

		if (split.length == 1)
		{
			// Bad lyrics frame. Assume that it lacks a
			// description.
			description = "";
			text = split [0];
		}
		else
		{
			description = split [0];
			text = split [1];
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
		StringType encoding = CorrectEncoding(getTextEncoding(), version);
		ByteVector v = new ByteVector();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add((byte) encoding);
		v.add((byte) encoding.getValue());
		v.add(ByteVector.FromString(getLanguage(), StringType.Latin1));
		v.add(ByteVector.FromString(description, encoding));
		v.add(ByteVector.TextDelimiter(encoding));
		v.add(ByteVector.FromString(text, encoding));

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
		UnsynchronisedLyricsFrame frame = new UnsynchronisedLyricsFrame(description, language, encoding);
		frame.text = text;
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}