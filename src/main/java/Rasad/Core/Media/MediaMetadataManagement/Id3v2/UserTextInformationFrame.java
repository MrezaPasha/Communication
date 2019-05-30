package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
	This class extends <see cref="TextInformationFrame" /> to provide
	support for ID3v2 User Text Information (TXXX) Frames.
*/
public class UserTextInformationFrame extends TextInformationFrame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="UserTextInformationFrame" /> with a specified
		description and text encoding.
	 
	 @param description
		A <see cref="string" /> containing the description of the
		new frame.
	 
	 @param encoding
		A <see cref="StringType" /> containing the text encoding
		to use when rendering the new frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see
		cref="Get(Tag,string,StringType,bool)" /> for more
		integrated frame creation.
	 
	*/
	public UserTextInformationFrame(String description, StringType encoding)
	{
		super(FrameType.TXXX, encoding);
		super.setText(new String [] {description});
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UserTextInformationFrame" /> with a specified
		description.
	 
	 @param description
		A <see cref="string" /> containing the description of the
		new frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see
		cref="Get(Tag,string,bool)" /> for more integrated frame
		creation.
	 
	*/
	public UserTextInformationFrame(String description)
	{
		super(FrameType.TXXX);
		super.setText(new String [] {description});
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UserTextInformationFrame" /> by reading its raw
		data in a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public UserTextInformationFrame(ByteVector data, byte version)
	public UserTextInformationFrame(ByteVector data, byte version)
	{
		super(data, version);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UserTextInformationFrame" /> by reading its raw
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
//ORIGINAL LINE: protected internal UserTextInformationFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected UserTextInformationFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(data, offset, header.clone(), version);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets and sets the description stored in the current
		instance.
	 
	 <value>
		A <see cref="string" /> containing the description
		stored in the current instance.
	 </value>
	 
		There should only be one frame with a matching
		description per tag.
	 
	*/
	public final String getDescription()
	{
		String [] text = super.getText();
		return text.length > 0 ? text [0] : null;
	}

	public final void setDescription(String value)
	{
		String [] text = super.getText();
		if (text.length > 0)
		{
			text [0] = value;
		}
		else
		{
			text = new String [] {value};
		}

		super.setText(text);
	}

	/** 
		Gets and sets the text contained in the current
		instance.
	 
	 <value>
		A <see cref="string[]" /> containing the text contained
		in the current instance.
	 </value>
	 
		<p>Modifying the contents of the returned value will
		not modify the contents of the current instance. The
		value must be reassigned for the value to change.</p>
	 
	*/
	@Override
	public String[] getText()
	{
		String [] text = super.getText();
		if (text.length < 2)
		{
			return new String [0];
		}

		String [] new_text = new String [text.length - 1];
		for (int i = 0; i < new_text.length; i++)
		{
			new_text [i] = text [i + 1];
		}

		return new_text;
	}
	@Override
	public void setText(String[] value)
	{
		String [] new_value = new String [value != null ? (value.length + 1) : 1];

		new_value [0] = getDescription();

		for (int i = 1; i < new_value.length; i++)
		{
			new_value [i] = value [i - 1];
		}

		super.setText(new_value);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Gets a string representation of the current instance.
	 
	 @return 
		A <see cref="string" /> containing the joined text.
	 
	*/
	@Override
	public String toString()
	{
		return (new StringBuilder()).append("[").append(getDescription()).append("] ").append(super.toString()).toString();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Gets a specified user text frame from the specified tag,
		optionally creating it if it does not exist and optionally
		searching for the frame case-insensitive.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param description
		A <see cref="string" /> specifying the description to
		match.
	 
	 @param type
		A <see cref="StringType" /> specifying the encoding to
		use if creating a new frame.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @param caseSensitive
		A <see cref="bool" /> specifying whether or not to search
		for the frame case-sensitive.
	 
	 @return 
		A <see cref="UserTextInformationFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found and <paramref name="create" /> is
		<see langword="false" />.
	 
	*/
	public static UserTextInformationFrame Get(Tag tag, String description, StringType type, boolean create, boolean caseSensitive)
	{
		if (tag == null)
		{
			throw new NullPointerException("tag");
		}

		if (description == null)
		{
			throw new NullPointerException("description");
		}

		if (description.length() == 0)
		{
			throw new IllegalArgumentException("Description must not be empty.", "description");
		}

		StringComparison stringComparison = caseSensitive ? StringComparison.InvariantCulture : StringComparison.InvariantCultureIgnoreCase;

		for (UserTextInformationFrame frame : tag.<UserTextInformationFrame>GetFrames (FrameType.TXXX))
		{
			if (frame.getDescription().equals(stringComparison))
			{
				return frame;
			}
		}

		if (!create)
		{
			return null;
		}

		UserTextInformationFrame new_frame = new UserTextInformationFrame(description, type);
		tag.AddFrame(new_frame);
		return new_frame;
	}

	/** 
		Gets a specified user text frame from the specified tag,
		optionally creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param description
		A <see cref="string" /> specifying the description to
		match.
	 
	 @param type
		A <see cref="StringType" /> specifying the encoding to
		use if creating a new frame.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="UserTextInformationFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found and <paramref name="create" /> is
		<see langword="false" />.
	 
	*/
	public static UserTextInformationFrame Get(Tag tag, String description, StringType type, boolean create)
	{
		return Get(tag, description, type, create, true);
	}

	/** 
		Gets a specified user text frame from the specified tag,
		optionally creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param description
		A <see cref="string" /> specifying the description to
		match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="UserTextInformationFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found and <paramref name="create" /> is
		<see langword="false" />.
	 
	*/
	public static UserTextInformationFrame Get(Tag tag, String description, boolean create)
	{
		return Get(tag, description, Tag.getDefaultEncoding(), create);
	}

	/** 
		Gets a specified user text frame from the specified tag.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param description
		A <see cref="string" /> specifying the description to
		match.
	 
	 @return 
		A <see cref="UserTextInformationFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found.
	 
	*/
	@Deprecated
	public static UserTextInformationFrame Get(Tag tag, String description)
	{
		return Get(tag, description, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}