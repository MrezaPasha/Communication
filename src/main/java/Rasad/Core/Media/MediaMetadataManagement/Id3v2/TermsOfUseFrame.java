package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// TermsOfUseFrame.cs:
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
	This class extends <see cref="Frame" />, implementing support for
	ID3v2 Terms of Use (USER) Frames.
 
 
	This frame contains license text or restrictions on the use of a
	media file.
 
*/
public class TermsOfUseFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

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
		Contains the text in the current instance.
	*/
	private String text = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and intializes a new instance of <see
		cref="TermsOfUseFrame" /> with a specified language and
		encoding.
	 
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
	public TermsOfUseFrame(String language, StringType encoding)
	{
		super(FrameType.USER, 4);
		this.encoding = encoding;
		this.language = language;
	}

	/** 
		Constructs and intializes a new instance of <see
		cref="TermsOfUseFrame" /> with a specified language.
	 
	 @param language
		A <see cref="string" /> containing the ISO-639-2 language
		code of the new frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public TermsOfUseFrame(String language)
	{
		super(FrameType.USER, 4);
		this.language = language;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="TermsOfUseFrame" /> by reading its raw data in a
		specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TermsOfUseFrame(ByteVector data, byte version)
	public TermsOfUseFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="TermsOfUseFrame" /> by reading its raw data in a
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
//ORIGINAL LINE: protected internal TermsOfUseFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected TermsOfUseFrame(ByteVector data, int offset, FrameHeader header, byte version)
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
	 
		There should only be one file with a matching 
		ISO-639-2 language code per tag.
	 
	*/
	public final String getLanguage()
	{
		return (language != null && language.length() > 2) ? language.substring(0, 3) : "XXX";
	}
	public final void setLanguage(String value)
	{
		language = value;
	}

	/** 
		Gets and sets the terms of use stored in the current
		instance.
	 
	 <value>
		A <see cref="string" /> object containing the terms of
		use.
	 </value>
	*/
	public final String getText()
	{
		return text;
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
		A <see cref="string" /> containing the terms of use.
	 
	*/
	@Override
	public String toString()
	{
		return text;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Gets a specified terms of use frame from the specified
		tag, optionally creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param language
		A <see cref="string" /> specifying the ISO-639-2 language
	   code to match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="TermsOfUseFrame" /> object containing the
		matching frame, or <see langword="null" /> if a match
		wasn't found and <paramref name="create" /> is <see
		langword="false" />.
	 
	*/
	public static TermsOfUseFrame Get(Tag tag, String language, boolean create)
	{
		for (Frame f : tag.GetFrames(FrameType.USER))
		{
			TermsOfUseFrame cf = f instanceof TermsOfUseFrame ? (TermsOfUseFrame)f : null;

			if (cf != null && (language == null || cf.getLanguage().equals(language)))
			{
				return cf;
			}
		}

		if (!create)
		{
			return null;
		}

		TermsOfUseFrame frame = new TermsOfUseFrame(language);
		tag.AddFrame(frame);
		return frame;
	}

	/** 
		Gets a specified terms of use frame from the specified
		tag, trying to to match the language but accepting one
		with a different language if a match was not found.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param language
		A <see cref="string" /> specifying the ISO-639-2 language
	   code to match.
	 
	 @return 
		A <see cref="TermsOfUseFrame" /> object containing the
		matching frame, or <see langword="null" /> if a match
		wasn't found.
	 
	*/
	public static TermsOfUseFrame GetPreferred(Tag tag, String language)
	{
		TermsOfUseFrame best = null;
		for (Frame f : tag.GetFrames(FrameType.USER))
		{
			TermsOfUseFrame cf = f instanceof TermsOfUseFrame ? (TermsOfUseFrame)f : null;
			if (cf == null)
			{
				continue;
			}

			if (cf.getLanguage().equals(language))
			{
				return cf;
			}

			if (best == null)
			{
				best = cf;
			}
		}

		return best;
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
		text = data.toString(encoding, 4, data.size() - 4);
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
		TermsOfUseFrame frame = new TermsOfUseFrame(language, encoding);
		frame.text = text;
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}