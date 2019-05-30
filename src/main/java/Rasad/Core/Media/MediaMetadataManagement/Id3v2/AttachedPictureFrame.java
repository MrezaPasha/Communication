package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// AttachedPictureFrame.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   attachedpictureframe.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2004 Scott Wheeler (Original Implementation)
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
	ID3v2 Attached Picture (APIC) Frames.
 
 
	<p>A <see cref="AttachedPictureFrame" /> is used for storing
	pictures that complement the media, including the album cover,
	the physical medium, leaflets, file icons, etc. Other file and
	object data can be encapulsated via <see
	cref="GeneralEncapsulatedObjectFrame" />.</p>
	<p>Additionally, <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag.Pictures" /> provides a
	generic way or getting and setting pictures which is preferable
	to format specific code.</p>
 
*/
public class AttachedPictureFrame extends Frame implements IPicture
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Properties

	/** 
		Contains the text encoding to use when rendering.
	*/
	private StringType text_encoding = Tag.getDefaultEncoding();

	/** 
		Contains the mime type of <see cref="data" />.
	*/
	private String mime_type = null;

	/** 
		Contains the type of picture.
	*/
	private PictureType type = PictureType.Other;

	/** 
		Contains the description.
	*/
	private String description = null;

	/** 
		Contains the picture data.
	*/
	private ByteVector data = null;

	/** 
		Contains the raw field data of the current instance as
		sent to <see cref="ParseFields" /> or <see
		langword="null" /> if <see cref="ParseFields" /> has not
		been called or <see cref="ParseRawData" /> has been
		called.
	 
	 
		As this frame takes a while to parse and isn't read in
		all cases, the raw data is stored here until it is
		needed. This speeds up the file read time significantly.
	 
	*/
	private ByteVector raw_data = null;

	/** 
		Contains the ID3v2 version <see cref="raw_data" /> is
		stored in.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte raw_version = 0;
	private byte raw_version = 0;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
		Constructs and initializes a new instance of <see
		cref="AttachedPictureFrame" /> with no contents and the
		default values.
	 
	 
		<p>When a frame is created, it is not automatically
		added to the tag. Consider using <see
		cref="Get(Tag,string,PictureType,bool)" /> for more
		integrated frame creation.</p>
		<p>Additionally, <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag.Pictures" />
		provides a generic way or getting and setting
		pictures which is preferable to format specific
		code.</p>
	 
	*/
	public AttachedPictureFrame()
	{
		super(FrameType.APIC, 4);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="AttachedPictureFrame" /> by populating it with
		the contents of another <see cref="IPicture" /> object.
	 
	 @param picture
		A <see cref="IPicture" /> object containing values to use
		in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="picture" /> is <see langword="null" />.
	 
	 
		<p>When a frame is created, it is not automatically
		added to the tag. Consider using <see
		cref="Get(Tag,string,PictureType,bool)" /> for more
		integrated frame creation.</p>
		<p>Additionally, <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag.Pictures" />
		provides a generic way or getting and setting
		pictures which is preferable to format specific
		code.</p>
	 
	 <example>
		<p>Add a picture to a file.</p>
		<code lang="C#">
	 using Rasad.Core.Media.MediaMetadataManagement;
	 using Rasad.Core.Media.MediaMetadataManagement.Id3v2;
	
	 public static class AddId3v2Picture
	 {
	 	public static void Main (string [] args)
	 	{
	 		if (args.Length != 2)
	 			throw new ApplicationException (
	 				"USAGE: AddId3v2Picture.exe AUDIO_FILE PICTURE_FILE");
	
	 		// Create the file. Can throw file to Rasad.Core.Media.MediaMetadataManagement# exceptions.
	 		File file = File.Create (args [0]);
	
	 		// Get or create the ID3v2 tag.
	 		Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag tag = file.GetTag (TagTypes.Id3v2, true) as Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag;
	 		if (tag == null)
	 			throw new ApplicationException ("File does not support ID3v2 tags.");
	
	 		// Create a picture. Can throw file related exceptions.
			Rasad.Core.Media.MediaMetadataManagement.Picture picture = Rasad.Core.Media.MediaMetadataManagement.Picture.CreateFromPath (path);
	
	 		// Add a new picture frame to the tag.
	 		tag.AddFrame (new AttachedPictureFrame (picture));
	
	 		// Save the file.
	 		file.Save ();
	 	}
	 }
		</code>
	 </example>
	*/
	public AttachedPictureFrame(IPicture picture)
	{
		super(FrameType.APIC, 4);
		if (picture == null)
		{
			throw new NullPointerException("picture");
		}

		mime_type = picture.getMimeType();
		type = picture.getType();
		description = picture.getDescription();
		data = picture.getData();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="AttachedPictureFrame" /> by reading its raw data in
		a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public AttachedPictureFrame(ByteVector data, byte version)
	public AttachedPictureFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="AttachedPictureFrame" /> by reading its raw data
		in a specified ID3v2 version.
	 
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
//ORIGINAL LINE: protected internal AttachedPictureFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected AttachedPictureFrame(ByteVector data, int offset, FrameHeader header, byte version)
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
		ParseRawData();
		return text_encoding;
	}
	public final void setTextEncoding(StringType value)
	{
		text_encoding = value;
	}

	/** 
		Gets and sets the mime-type of the picture stored in the
		current instance.
	 
	 <value>
		A <see cref="string" /> containing the mime-type of the
		picture stored in the current instance.
	 </value>
	*/
	public final String getMimeType()
	{
		ParseRawData();
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
		Gets and sets the picture type stored in the current
		instance.
	 
	 <value>
		A <see cref="string" /> containing the picture type
		stored in the current instance.
	 </value>
	 
		There should only be one frame with a matching
		description and type per tag.
	 
	*/
	public final PictureType getType()
	{
		ParseRawData();
		return type;
	}
	public final void setType(PictureType value)
	{
		type = value;
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
		ParseRawData();
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
		Gets and sets the image data stored in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> containing the image data
		stored in the current instance.
	 </value>
	*/
	public final ByteVector getData()
	{
		ParseRawData();
		return data != null ? data : new ByteVector();
	}
	public final void setData(ByteVector value)
	{
		data = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Gets a string representation of the current instance.
	 
	 @return 
		A <see cref="string" /> representing the current
		instance.
	 
	*/
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		if (tangible.StringHelper.isNullOrEmpty(getDescription()))
		{
			builder.append(getDescription());
			builder.append(" ");
		}

		builder.AppendFormat(System.Globalization.CultureInfo.InvariantCulture, "[{0}] {1} bytes", getMimeType(), getData().size());

		return builder.toString();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Gets a specified picture frame from the specified tag,
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
		A <see cref="AttachedPictureFrame" /> object containing
		the matching frame, or <see langword="null" /> if a match
		wasn't found and <paramref name="create" /> is <see
		langword="false" />.
	 
	*/
	public static AttachedPictureFrame Get(Tag tag, String description, boolean create)
	{
		return Get(tag, description, PictureType.Other, create);
	}

	/** 
		Gets a specified picture frame from the specified tag,
		optionally creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param type
		A <see cref="PictureType" /> specifying the picture type
		to match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="AttachedPictureFrame" /> object containing
		the matching frame, or <see langword="null" /> if a match
		wasn't found and <paramref name="create" /> is <see
		langword="false" />.
	 
	*/
	public static AttachedPictureFrame Get(Tag tag, PictureType type, boolean create)
	{
		return Get(tag, null, type, create);
	}

	/** 
		Gets a specified picture frame from the specified tag,
		optionally creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param description
		A <see cref="string" /> specifying the description to
		match.
	 
	 @param type
		A <see cref="PictureType" /> specifying the picture type
		to match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="AttachedPictureFrame" /> object containing
		the matching frame, or <see langword="null" /> if a match
		wasn't found and <paramref name="create" /> is <see
		langword="false" />.
	 
	 <example>
		<p>Sets a cover image with a description. Because <see
		cref="Get(Tag,string,PictureType,bool)" /> is used, if
		the program is called again with the same audio file and
		desciption, the picture will be overwritten with the new
		one.</p>
		<code lang="C#">
	 using Rasad.Core.Media.MediaMetadataManagement;
	 using Rasad.Core.Media.MediaMetadataManagement.Id3v2;
	
	 public static class SetId3v2Cover
	 {
	 	public static void Main (string [] args)
	 	{
	 		if (args.Length != 3)
	 			throw new ApplicationException (
	 				"USAGE: SetId3v2Cover.exe AUDIO_FILE PICTURE_FILE DESCRIPTION");
	
	 		// Create the file. Can throw file to Rasad.Core.Media.MediaMetadataManagement# exceptions.
	 		File file = File.Create (args [0]);
	
	 		// Get or create the ID3v2 tag.
	 		Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag tag = file.GetTag (TagTypes.Id3v2, true) as Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag;
	 		if (tag == null)
	 			throw new ApplicationException ("File does not support ID3v2 tags.");
	
	 		// Create a picture. Can throw file related exceptions.
			Rasad.Core.Media.MediaMetadataManagement.Picture picture = Rasad.Core.Media.MediaMetadataManagement.Picture.CreateFromPath (args [1]);
	
	 		// Get or create the picture frame.
	 		AttachedPictureFrame frame = AttachedPictureFrame.Get (
	 			tag, args [2], PictureType.FrontCover, true);
	
	 		// Set the data from the picture.
	 		frame.MimeType = picture.MimeType;
	 		frame.Data     = picture.data;
	 		
	 		// Save the file.
	 		file.Save ();
	 	}
	 }
		</code>
	 </example>
	*/
	public static AttachedPictureFrame Get(Tag tag, String description, PictureType type, boolean create)
	{
		AttachedPictureFrame apic;
		for (Frame frame : tag.GetFrames(FrameType.APIC))
		{
			apic = frame instanceof AttachedPictureFrame ? (AttachedPictureFrame)frame : null;

			if (apic == null)
			{
				continue;
			}

			if (description != null && !apic.getDescription().equals(description))
			{
				continue;
			}

			if (type != PictureType.Other && apic.getType() != type)
			{
				continue;
			}

			return apic;
		}

		if (!create)
		{
			return null;
		}

		apic = new AttachedPictureFrame();
		apic.setDescription(description);
		apic.setType(type);

		tag.AddFrame(apic);

		return apic;
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
		if (data.size() < 5)
		{
			throw new CorruptFileException("A picture frame must contain at least 5 bytes.");
		}

		raw_data = data;
		raw_version = version;
	}

	/** 
		Performs the actual parsing of the raw data.
	 
	 
		Because of the high parsing cost and relatively low usage
		of the class, <see cref="ParseFields" /> only stores the
		field data so it can be parsed on demand. Whenever a
		property or method is called which requires the data,
		this method is called, and only on the first call does it
		actually parse the data.
	 
	*/
	protected final void ParseRawData()
	{
		if (raw_data == null)
		{
			return;
		}

		int pos = 0;
		int offset;

		text_encoding = StringType.forValue(raw_data.get(pos++));

		if (raw_version > 2)
		{
			offset = raw_data.Find(ByteVector.TextDelimiter(StringType.Latin1), pos);

			if (offset < pos)
			{
				return;
			}

			mime_type = raw_data.toString(StringType.Latin1, pos, offset - pos);
			pos = offset + 1;
		}
		else
		{
			ByteVector ext = raw_data.Mid(pos, 3);

			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(ext, "JPG"))
			{
				mime_type = "image/jpeg";
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(ext, "PNG"))
			{
				mime_type = "image/png";
			}
			else
			{
				mime_type = "image/unknown";
			}

			pos += 3;
		}

		ByteVector delim = ByteVector.TextDelimiter(text_encoding);

		type = PictureType.forValue(raw_data.get(pos++));

		offset = raw_data.Find(delim, pos, delim.size());

		if (offset < pos)
		{
			return;
		}

		description = raw_data.toString(text_encoding, pos, offset - pos);
		pos = offset + delim.size();
		raw_data.subList(0, pos).clear();
		this.data = raw_data;

		this.raw_data = null;
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
		if (raw_data != null && raw_version == version)
		{
			return raw_data;
		}

		StringType encoding = CorrectEncoding(getTextEncoding(), version);
		ByteVector data = new ByteVector();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add((byte) encoding);
		data.add((byte) encoding.getValue());

		if (version == 2)
		{
			switch (getMimeType())
			{
			case "image/png":
				data.add("PNG");
				break;
			case "image/jpeg":
				data.add("JPG");
				break;
			default:
				data.add("XXX");
				break;
			}
		}
		else
		{
			data.add(ByteVector.FromString(getMimeType(), StringType.Latin1));
			data.add(ByteVector.TextDelimiter(StringType.Latin1));
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add((byte) type);
		data.add((byte) type.getValue());
		data.add(ByteVector.FromString(getDescription(), encoding));
		data.add(ByteVector.TextDelimiter(encoding));
		data.add(this.data);

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
		AttachedPictureFrame frame = new AttachedPictureFrame();
		frame.text_encoding = text_encoding;
		frame.mime_type = mime_type;
		frame.type = type;
		frame.description = description;
		if (data != null)
		{
			frame.data = new ByteVector(data);
		}
		if (raw_data != null)
		{
			frame.data = new ByteVector(raw_data);
		}
		frame.raw_version = raw_version;
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}