package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
	This class extends <see cref="Frame" />, implementing support for
	ID3v2 Synchronised Lyrics and Text (SYLT) Frames.
*/
public class SynchronisedLyricsFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Properties

	/** 
		Contains the text encoding to use when rendering the
		current instance.
	*/
	private StringType encoding = Tag.getDefaultEncoding();

	/** 
		Contains the ISO-639-2 language code.
	*/
	private String language = null;

	/** 
		Contains the description.
	*/
	private String description = null;

	/** 
		Contains the timestamp format.
	*/
	private TimestampFormat timestamp_format = TimestampFormat.Unknown;

	/** 
		Contains the text type.
	*/
	private SynchedTextType lyrics_type = SynchedTextType.Other;

	/** 
		Contains the text.
	*/
	private SynchedText [] text = new SynchedText [0];

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="SynchronisedLyricsFrame" /> with a specified
		description, ISO-639-2 language code, text type, and text
		encoding.
	 
	 @param description
		A <see cref="string" /> object containing the description
		of the new instnace.
	 
	 @param language
		A <see cref="string" /> object containing the ISO-639-2
		language code of the new instance.
	 
	 @param type
		A <see cref="SynchedTextType" /> containing the type of
		text to be stored in the new instance.
	 
	 @param encoding
		A <see cref="StringType" /> containing the text encoding
		to use when rendering the new instance.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public SynchronisedLyricsFrame(String description, String language, SynchedTextType type, StringType encoding)
	{
		super(FrameType.SYLT, 4);
		this.encoding = encoding;
		this.language = language;
		this.description = description;
		this.lyrics_type = type;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="SynchronisedLyricsFrame" /> with a specified
		description, ISO-639-2 language code, and text type.
	 
	 @param description
		A <see cref="string" /> object containing the description
		of the new instnace.
	 
	 @param language
		A <see cref="string" /> object containing the ISO-639-2
		language code of the new instance.
	 
	 @param type
		A <see cref="SynchedTextType" /> containing the type of
		text to be stored in the new instance.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public SynchronisedLyricsFrame(String description, String language, SynchedTextType type)
	{
		this(description, language, type, Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag.getDefaultEncoding());
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="SynchronisedLyricsFrame" /> by reading its raw data
		in a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new instance.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public SynchronisedLyricsFrame(ByteVector data, byte version)
	public SynchronisedLyricsFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="SynchronisedLyricsFrame" /> by reading its raw data
		in a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		representation of the new instance.
	 
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
//ORIGINAL LINE: protected internal SynchronisedLyricsFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected SynchronisedLyricsFrame(ByteVector data, int offset, FrameHeader header, byte version)
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
	 
		There should only be one frame with a matching
		description, type, and ISO-639-2 language code per tag.
	 
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
		Gets and sets the description stored in the current
		instance.
	 
	 <value>
		A <see cref="string" /> containing the description
		stored in the current instance.
	 </value>
	 
		There should only be one frame with a matching
		description, type, and ISO-639-2 language code per tag.
	 
	*/
	public final String getDescription()
	{
		return description;
	}
	public final void setDescription(String value)
	{
		description = value;
	}

	/** 
		Gets and sets the timestamp format used by the current
		instance.
	 
	 <value>
		A <see cref="TimestampFormat" /> value describing the
		timestamp format used by the current instance.
	 </value>
	*/
	public final TimestampFormat getFormat()
	{
		return timestamp_format;
	}
	public final void setFormat(TimestampFormat value)
	{
		timestamp_format = value;
	}

	/** 
		Gets and sets the type of text contained in the current
		instance.
	 
	 <value>
		A <see cref="TimestampFormat" /> value describing the
		type of text contained in the current instance.
	 </value>
	*/
	public final SynchedTextType getType()
	{
		return lyrics_type;
	}
	public final void setType(SynchedTextType value)
	{
		lyrics_type = value;
	}

	/** 
		Gets and sets the text contained in the current
		instance.
	 
	 <value>
		A <see cref="SynchedText[]" /> containing the text
		contained in the current instance.
	 </value>
	*/
	public final SynchedText[] getText()
	{
		return text;
	}
	public final void setText(SynchedText[] value)
	{
		text = value == null ? new SynchedText [0] : value;
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
		A <see cref="string" /> object specifying the description
		to match.
	 
	 @param language
		A <see cref="string" /> object specifying the ISO-639-2
		language code to match.
	 
	 @param type
		A <see cref="SynchedTextType" /> value specifying the
		text type to match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="SynchronisedLyricsFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found and <paramref name="create" /> is
		<see langword="false" />.
	 
	*/
	public static SynchronisedLyricsFrame Get(Tag tag, String description, String language, SynchedTextType type, boolean create)
	{
		for (Frame f : tag)
		{
			SynchronisedLyricsFrame lyr = f instanceof SynchronisedLyricsFrame ? (SynchronisedLyricsFrame)f : null;

			if (lyr == null)
			{
				continue;
			}

			if (lyr.getDescription().equals(description) && (language == null || lyr.getLanguage().equals(language)) && type == lyr.getType())
			{
				return lyr;
			}
		}

		if (!create)
		{
			return null;
		}

		SynchronisedLyricsFrame frame = new SynchronisedLyricsFrame(description, language, type);
		tag.AddFrame(frame);
		return frame;
	}

	/** 
		Gets a specified lyrics frame from the specified tag,
		trying to to match the description and language but
		accepting an incomplete match.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param description
		A <see cref="string" /> object specifying the description
		to match.
	 
	 @param language
		A <see cref="string" /> object specifying the ISO-639-2
		language code to match.
	 
	 @param type
		A <see cref="SynchedTextType" /> value specifying the
		text type to match.
	 
	 @return 
		A <see cref="SynchronisedLyricsFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found.
	 
	 
		<p>The method tries matching with the following order
		of precidence:</p>
		<list type="number">
		   <item><term>The first frame with a matching
		   description, language, and type.</term></item>
		   <item><term>The first frame with a matching
		   description and language.</term></item>
		   <item><term>The first frame with a matching
		   language.</term></item>
		   <item><term>The first frame with a matching
		   description.</term></item>
		   <item><term>The first frame with a matching
		   type.</term></item>
		   <item><term>The first frame.</term></item>
		</list>
	 
	*/
	public static SynchronisedLyricsFrame GetPreferred(Tag tag, String description, String language, SynchedTextType type)
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
		SynchronisedLyricsFrame best_frame = null;

		for (Frame f : tag)
		{
			SynchronisedLyricsFrame cf = f instanceof SynchronisedLyricsFrame ? (SynchronisedLyricsFrame)f : null;

			if (cf == null)
			{
				continue;
			}

			int value = 0;
			if (cf.getLanguage().equals(language))
			{
				value += 4;
			}
			if (cf.getDescription().equals(description))
			{
				value += 2;
			}
			if (cf.getType() == type)
			{
				value += 1;
			}

			if (value == 7)
			{
				return cf;
			}

			if (value <= best_value)
			{
				continue;
			}

			best_value = value;
			best_frame = cf;
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
		if (data.size() < 6)
		{
			throw new CorruptFileException("Not enough bytes in field.");
		}

		encoding = StringType.forValue(data.get(0));
		language = data.toString(StringType.Latin1, 1, 3);
		timestamp_format = TimestampFormat.forValue(data.get(4));
		lyrics_type = SynchedTextType.forValue(data.get(5));

		ByteVector delim = ByteVector.TextDelimiter(encoding);
		int delim_index = data.Find(delim, 6, delim.size());

		if (delim_index < 0)
		{
			throw new CorruptFileException("Text delimiter expected.");
		}

		description = data.toString(encoding, 6, delim_index - 6);

		int offset = delim_index + delim.size();
		ArrayList<SynchedText> l = new ArrayList<SynchedText> ();

		while (offset + delim.size() + 4 < data.size())
		{
			delim_index = data.Find(delim, offset, delim.size());

			if (delim_index < offset)
			{
				throw new CorruptFileException("Text delimiter expected.");
			}

			String text = data.toString(encoding, offset, delim_index - offset);
			offset = delim_index + delim.size();

			if (offset + 4 > data.size())
			{
				break;
			}

			l.add(new SynchedText(data.Mid(offset, 4).ToUInt(), text));
			offset += 4;
		}

		this.text = l.toArray(new SynchedText[0]);
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
		ByteVector delim = ByteVector.TextDelimiter(encoding);
		ByteVector v = new ByteVector();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add((byte) encoding);
		v.add((byte) encoding.getValue());
		v.add(ByteVector.FromString(getLanguage(), StringType.Latin1));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add((byte) timestamp_format);
		v.add((byte) timestamp_format.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add((byte) lyrics_type);
		v.add((byte) lyrics_type.getValue());
		v.add(ByteVector.FromString(description, encoding));
		v.add(delim);

		for (SynchedText t : text)
		{
			v.add(ByteVector.FromString(t.getText(), encoding));
			v.add(delim);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(ByteVector.FromUInt((uint)t.Time));
			v.add(ByteVector.FromUInt((int)t.getTime()));
		}

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
		SynchronisedLyricsFrame frame = new SynchronisedLyricsFrame(description, language, lyrics_type, encoding);
		frame.timestamp_format = timestamp_format;
		frame.text = (SynchedText[]) text.clone();
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}