package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Tag.cs: Provide support for reading and writing ID3v2 tags.
//
// Authors:
//   Brian Nickel (brian.nickel@gmail.com)
//   Gabriel BUrt (gabriel.burt@gmail.com)
//
// Original Source:
//   id3v2tag.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2010 Novell, Inc.
// Copyright (C) 2005-2007 Brian Nickel
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> and implements <see
	cref="T:System.Collections.Generic.IEnumerable`1" /> to provide support for reading and
	writing ID3v2 tags.
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class Tag extends Rasad.Core.Media.MediaMetadataManagement.Tag implements java.lang.Iterable<Frame>, Cloneable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Static Fields

	/** 
		Contains the language to use for language specific
		fields.
	*/
	private static String language = CultureInfo.CurrentCulture.ThreeLetterISOLanguageName;

	/** 
		Contains the field to use for new tags.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static byte default_version = 3;
	private static byte default_version = 3;

	/** 
		Indicates whether or not all tags should be saved in
		<see cref="default_version" />.
	*/
	private static boolean force_default_version = false;

	/** 
		Specifies the default string type to use for new frames.
	*/
	private static StringType default_string_type = StringType.UTF8;

	/** 
		Specifies whether or not all frames shoudl be saved in
		<see cref="default_string_type" />.
	*/
	private static boolean force_default_string_type = false;

	/** 
		Specifies whether or not numeric genres should be used
		when available.
	*/
	private static boolean use_numeric_genres = true;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the tag's header.
	*/
	private Header header = new Header();

	/** 
		Contains the tag's extended header.
	*/
	private ExtendedHeader extended_header = null;

	/** 
		Contains the tag's frames.
	*/
	private ArrayList<Frame> frame_list = new ArrayList<Frame> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Tag" /> with no contents.
	*/
	public Tag()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Tag" /> by reading the contents from a specified
		position in a specified file.
	 
	 @param file
		A <see cref="File" /> object containing the file from
		which the contents of the new instance is to be read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read the tag.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	*/
	public Tag(File file, long position)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.setMode(Rasad.Core.Media.MediaMetadataManagement.File.AccessMode.Read);

		if (position < 0 || position > file.getLength() - Header.Size)
		{
			throw new IndexOutOfBoundsException("position");
		}

		Read(file, position);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Tag" /> by reading the contents from a specified
		<see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector" /> object to read the tag from.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> does not contain enough data.
	 
	*/
	public Tag(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (data.size() < Header.Size)
		{
			throw new CorruptFileException("Does not contain enough header data.");
		}

		header = new Header(data);

		// If the tag size is 0, then this is an invalid tag.
		// Tags must contain at least one frame.

		if (header.getTagSize() == 0)
		{
			return;
		}

		if (data.size() - Header.Size < header.getTagSize())
		{
			throw new CorruptFileException("Does not contain enough tag data.");
		}

		Parse(data.Mid((int) Header.Size, (int) header.getTagSize()));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Gets all frames contained in the current instance.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating
		through the frames.
	 
	*/
	public final java.lang.Iterable<Frame> GetFrames()
	{
		return frame_list;
	}

	/** 
		Gets all frames with a specified identifier contained in
		the current instance.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the
		identifier of the frames to return.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating
		through the frames.
	 
	 @exception ArgumentNullException
		<paramref name="ident" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="ident" /> is not exactly four bytes long.
	 
	*/
	public final java.lang.Iterable<Frame> GetFrames(ByteVector ident)
	{
		if (ident == null)
		{
			throw new NullPointerException("ident");
		}

		if (ident.size() != 4)
		{
			throw new IllegalArgumentException("Identifier must be four bytes long.", "ident");
		}

		for (Frame f : frame_list)
		{
			if (f.getFrameId().equals(ident))
			{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
				yield return f;
			}
		}
	}

	/** 
		Gets all frames with of a specified type contained in
		the current instance.
	 
	 <typeparam name="T">
		The type of object, derived from <see cref="Frame" />,
		to return from in the current instance.
	 </typeparam>
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating
		through the frames.
	 
	*/
	public final <T extends Frame> java.lang.Iterable<T> GetFrames()
	{
		for (Frame f : frame_list)
		{
			T tf = (T)f;
			if (tf != null)
			{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
				yield return tf;
			}
		}
	}

	/** 
		Gets all frames with a of type <typeparamref name="T" />
		with a specified identifier contained in the current
		instance.
	 
	 <typeparam name="T">
		The type of object, derived from <see cref="Frame" />,
		to return from in the current instance.
	 </typeparam>
	 @param ident
		A <see cref="ByteVector" /> object containing the
		identifier of the frames to return.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating
		through the frames.
	 
	 @exception ArgumentNullException
		<paramref name="ident" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="ident" /> is not exactly four bytes long.
	 
	*/
	public final <T extends Frame> java.lang.Iterable<T> GetFrames(ByteVector ident)
	{
		if (ident == null)
		{
			throw new NullPointerException("ident");
		}

		if (ident.size() != 4)
		{
			throw new IllegalArgumentException("Identifier must be four bytes long.", "ident");
		}

		for (Frame f : frame_list)
		{
			T tf = (T)f;
			if (tf != null && f.getFrameId().equals(ident))
			{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
				yield return tf;
			}
		}
	}

	/** 
		Adds a frame to the current instance.
	 
	 @param frame
		A <see cref="Frame" /> object to add to the current
		instance.
	 
	 @exception ArgumentNullException
		<paramref name="frame" /> is <see langword="null" />.
	 
	*/
	public final void AddFrame(Frame frame)
	{
		if (frame == null)
		{
			throw new NullPointerException("frame");
		}

		frame_list.add(frame);
	}

	/** 
		Replaces an existing frame with a new one in the list
		contained in the current instance, or adds a new one if
		the existing one is not contained.
	 
	 @param oldFrame
		A <see cref="Frame" /> object to be replaced.
	 
	 @param newFrame
		A <see cref="Frame" /> object to add to the current
		instance.
	 
	 @exception ArgumentNullException
		<paramref name="oldFrame" /> or <paramref name="newFrame"
		/> is <see langword="null" />.
	 
	*/
	public final void ReplaceFrame(Frame oldFrame, Frame newFrame)
	{
		if (oldFrame == null)
		{
			throw new NullPointerException("oldFrame");
		}

		if (newFrame == null)
		{
			throw new NullPointerException("newFrame");
		}

		if (oldFrame == newFrame)
		{
			return;
		}

		int i = frame_list.indexOf(oldFrame);
		if (i >= 0)
		{
			frame_list.set(i, newFrame);
		}
		else
		{
			frame_list.add(newFrame);
		}
	}

	/** 
		Removes a specified frame from the current instance.
	 
	 @param frame
		A <see cref="Frame" /> object to remove from the current
		instance.
	 
	 @exception ArgumentNullException
		<paramref name="frame" /> is <see langword="null" />.
	 
	*/
	public final void RemoveFrame(Frame frame)
	{
		if (frame == null)
		{
			throw new NullPointerException("frame");
		}

		if (frame_list.contains(frame))
		{
			frame_list.remove(frame);
		}
	}

	/** 
		Removes all frames with a specified identifier from the
		current instance.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the
		identifier of the frames to remove.
	 
	 @exception ArgumentNullException
		<paramref name="ident" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="ident" /> is not exactly four bytes long.
	 
	*/
	public final void RemoveFrames(ByteVector ident)
	{
		if (ident == null)
		{
			throw new NullPointerException("ident");
		}

		if (ident.size() != 4)
		{
			throw new IllegalArgumentException("Identifier must be four bytes long.", "ident");
		}

		for (int i = frame_list.size() - 1; i >= 0; i--)
		{
			if (frame_list.get(i).getFrameId().equals(ident))
			{
				frame_list.remove(i);
			}
		}
	}

	/** 
		Sets the text for a specified Text Information Frame.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the
		identifier of the frame to set the data for.
	 
	 @param text
		A <see cref="string[]" /> containing the text to set for
		the specified frame, or <see langword="null" /> to unset
		the value.
	 
	 @exception ArgumentNullException
		<paramref name="ident" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="ident" /> is not exactly four bytes long.
	 
	*/
	public final void SetTextFrame(ByteVector ident, String... text)
	{
		if (ident == null)
		{
			throw new NullPointerException("ident");
		}

		if (ident.size() != 4)
		{
			throw new IllegalArgumentException("Identifier must be four bytes long.", "ident");
		}

		boolean empty = true;

		if (text != null)
		{
			for (int i = 0; empty && i < text.length; i++)
			{
				if (!tangible.StringHelper.isNullOrEmpty(text [i]))
				{
					empty = false;
				}
			}
		}

		if (empty)
		{
			RemoveFrames(ident);
			return;
		}

		TextInformationFrame frame = TextInformationFrame.Get(this, ident, true);

		frame.setText(text);
		frame.setTextEncoding(getDefaultEncoding());
	}

	/** 
		Sets the text for a specified Text Information Frame.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the
		identifier of the frame to set the data for.
	 
	 @param text
		A <see cref="StringCollection" /> object containing the
		text to set for the specified frame, or <see
		langword="null" /> to unset the value.
	 
	 @exception ArgumentNullException
		<paramref name="ident" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="ident" /> is not exactly four bytes long.
	 
	*/
	@Deprecated
	public final void SetTextFrame(ByteVector ident, StringCollection text)
	{
		if (text == null || text.size() == 0)
		{
			RemoveFrames(ident);
		}
		else
		{
			SetTextFrame(ident, text.toArray(new Object[0]));
		}
	}

	/** 
		Sets the numeric values for a specified Text Information
		Frame.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the
		identifier of the frame to set the data for.
	 
	 @param number
		A <see cref="uint" /> value containing the number to
		store.
	 
	 @param count
		A <see cref="uint" /> value representing a total which
		<paramref name="number" /> is a part of, or zero if
		<paramref name="number" /> is not part of a set.
	 
	 
		If both <paramref name="number" /> and <paramref
		name="count" /> are equal to zero, the value will be
		cleared. If <paramref name="count" /> is zero, <paramref
		name="number" /> by itself will be stored. Otherwise, the
		values will be stored as "<paramref name="number"
		/>/<paramref name="count" />".
	 
	 @exception ArgumentNullException
		<paramref name="ident" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="ident" /> is not exactly four bytes long.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetNumberFrame(ByteVector ident, uint number, uint count)
	public final void SetNumberFrame(ByteVector ident, int number, int count)
	{
		if (ident == null)
		{
			throw new NullPointerException("ident");
		}

		if (ident.size() != 4)
		{
			throw new IllegalArgumentException("Identifier must be four bytes long.", "ident");
		}

		if (number == 0 && count == 0)
		{
			RemoveFrames(ident);
		}
		else if (count != 0)
		{
			SetTextFrame(ident, String.format(CultureInfo.InvariantCulture, "%1$s/%2$s", number, count));
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetTextFrame(ident, number.ToString(CultureInfo.InvariantCulture));
			SetTextFrame(ident, (new Integer(number)).toString(CultureInfo.InvariantCulture));
		}
	}

	/** 
		Renders the current instance as a raw ID3v2 tag.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered tag.
	 
	 
		By default, tags will be rendered in the version they
		were loaded in, and new tags using the version specified
		by <see cref="DefaultVersion" />. If <see
		cref="ForceDefaultVersion" /> is <see langword="true" />,
		all tags will be rendered in using the version specified
		by <see cref="DefaultVersion" />, except for tags with
		footers, which must be in version 4.
	 
	*/
	public final ByteVector Render()
	{
		// We need to render the "tag data" first so that we
		// have to correct size to render in the tag's header.
		// The "tag data" (everything that is included in
		// Header.TagSize) includes the extended header, frames
		// and padding, but does not include the tag's header or
		// footer.

		boolean has_footer = (header.getFlags().getValue() & HeaderFlags.FooterPresent.getValue()) != 0;
		boolean unsynchAtFrameLevel = (header.getFlags().getValue() & HeaderFlags.Unsynchronisation.getValue()) != 0 && getVersion() >= 4;
		boolean unsynchAtTagLevel = (header.getFlags().getValue() & HeaderFlags.Unsynchronisation.getValue()) != 0 && getVersion() < 4;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: header.MajorVersion = has_footer ? (byte) 4 : Version;
		header.setMajorVersion(has_footer ? (byte) 4 : getVersion());

		ByteVector tag_data = new ByteVector();

		// TODO: Render the extended header.
		header.setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.HeaderFlags.forValue(header.getFlags().getValue() & ~HeaderFlags.ExtendedHeader.getValue()));

		// Loop through the frames rendering them and adding
		// them to the tag_data.
		for (Frame frame : frame_list)
		{
			if (unsynchAtFrameLevel)
			{
				frame.setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(frame.getFlags().getValue() | FrameFlags.Unsynchronisation.getValue()));
			}

			if ((frame.getFlags().getValue() & FrameFlags.TagAlterPreservation.getValue()) != 0)
			{
				continue;
			}

			try
			{
				tag_data.add(frame.Render(header.getMajorVersion()));
			}
			catch (UnsupportedOperationException e)
			{
			}
		}

		// Add unsyncronization bytes if necessary.
		if (unsynchAtTagLevel)
		{
			SynchData.UnsynchByteVector(tag_data);
		}

		// Compute the amount of padding, and append that to
		// tag_data.


		if (!has_footer)
		{
			tag_data.add(new ByteVector((int)((tag_data.size() < header.getTagSize()) ? (header.getTagSize() - tag_data.size()) : 1024)));
		}

		// Set the tag size.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: header.TagSize = (uint) tag_data.Count;
		header.setTagSize((int) tag_data.size());

		tag_data.add(0, header.Render());
		if (has_footer)
		{
			tag_data.add((new Footer(header.clone())).Render());
		}

		return tag_data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets and sets the header flags applied to the current
		instance.
	 
	 <value>
		A bitwise combined <see cref="HeaderFlags" /> value
		containing flags applied to the current instance.
	 </value>
	*/
	public final HeaderFlags getFlags()
	{
		return header.getFlags();
	}
	public final void setFlags(HeaderFlags value)
	{
		header.setFlags(value);
	}

	/** 
		Gets and sets the ID3v2 version of the current instance.
	 
	 <value>
		A <see cref="byte" /> value specifying the ID3v2 version
		of the current instance.
	 </value>
	 @exception ArgumentOutOfRangeException
		<paramref name="value" /> is less than 2 or more than 4.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getVersion()
	public final byte getVersion()
	{
		return getForceDefaultVersion() ? getDefaultVersion() : header.getMajorVersion();
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setVersion(byte value)
	public final void setVersion(byte value)
	{
		if (value < 2 || value > 4)
		{
			throw new IndexOutOfBoundsException("value", "Version must be 2, 3, or 4");
		}

		header.setMajorVersion(value);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Properties

	/** 
		Gets and sets the ISO-639-2 language code to use when
		searching for and storing language specific values.
	 
	 <value>
		A <see cref="string" /> object containing an ISO-639-2
		language code fto use when searching for and storing
		language specific values.
	 </value>
	 
		If the language is unknown, "   " is the appropriate
		filler.
	 
	*/
	public static String getLanguage()
	{
		return language;
	}
	public static void setLanguage(String value)
	{
		language = (value == null || value.length() < 3) ? "   " : value.substring(0,3);
	}

	/** 
		Gets and sets the the default version to use when
		creating new tags.
	 
	 <value>
		A <see cref="byte" /> value specifying the default ID3v2
		version. The default version for this library is 3.
	 </value>
	 
		If <see cref="ForceDefaultVersion" /> is <see
		langword="true" />, all tags will be rendered with this
		version.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="value" /> is less than 2 or more than 4.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte getDefaultVersion()
	public static byte getDefaultVersion()
	{
		return default_version;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void setDefaultVersion(byte value)
	public static void setDefaultVersion(byte value)
	{
		if (value < 2 || value > 4)
		{
			throw new IndexOutOfBoundsException("value", "Version must be 2, 3, or 4");
		}

		default_version = value;
	}

	/** 
		Gets and sets whether or not to save all tags in the
		default version rather than their original version.
	 
	 <value>
		If <see langword="true"/>, tags will be saved in
		<see cref="DefaultVersion" /> rather than their original
		format, with the exception of tags with footers, which
		will be saved in version 4.
	 </value>
	*/
	public static boolean getForceDefaultVersion()
	{
		return force_default_version;
	}
	public static void setForceDefaultVersion(boolean value)
	{
		force_default_version = value;
	}

	/** 
		Gets and sets the encoding to use when creating new
		frames.
	 
	 <value>
		A <see cref="StringType" /> value specifying the encoding
		to use when creating new frames.
	 </value>
	*/
	public static StringType getDefaultEncoding()
	{
		return default_string_type;
	}
	public static void setDefaultEncoding(StringType value)
	{
		default_string_type = value;
	}

	/** 
		Gets and sets whether or not to render all frames with
		the default encoding rather than their original encoding.
	 
	 <value>
		If <see langword="true"/>, fames will be rendered in
		<see cref="DefaultEncoding" /> rather than their original
		encoding.
	 </value>
	*/
	public static boolean getForceDefaultEncoding()
	{
		return force_default_string_type;
	}
	public static void setForceDefaultEncoding(boolean value)
	{
		force_default_string_type = value;
	}

	/** 
		Gets and sets whether or not to use ID3v1 style numeric
		genres when possible.
	 
	 <value>
		A <see cref="bool" /> value specifying whether or not to
		use genres with numeric values when possible.
	 </value>
	 
		If <see langword="true" />, Rasad.Core.Media.MediaMetadataManagement# will try looking up
		the numeric genre code when storing the value. For
		ID3v2.2 and ID3v2.3, "Rock" would be stored as "(17)" and
		for ID3v2.4 it would be stored as "17".
	 
	*/
	public static boolean getUseNumericGenres()
	{
		return use_numeric_genres;
	}
	public static void setUseNumericGenres(boolean value)
	{
		use_numeric_genres = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Methods

	/** 
		Populates the current instance be reading in a tag from
		a specified position in a specified file.
	 
	 @param file
		A <see cref="File" /> object to read the tag from.
	 
	 @param position
		A <see cref="long" /> value specifying the seek position
		at which to read the tag.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than 0 or greater
		than the size of the file.
	 
	*/
	protected final void Read(File file, long position)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.setMode(File.AccessMode.Read);

		if (position < 0 || position > file.getLength() - Header.Size)
		{
			throw new IndexOutOfBoundsException("position");
		}

		file.Seek(position);

		header = new Header(file.ReadBlock((int) Header.Size));

		// If the tag size is 0, then this is an invalid tag.
		// Tags must contain at least one frame.

		if (header.getTagSize() == 0)
		{
			return;
		}

		Parse(file.ReadBlock((int) header.getTagSize()));
	}

	/** 
		Populates the current instance by parsing the contents of
		a raw ID3v2 tag, minus the header.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the content
		of an ID3v2 tag, minus the header.
	 
	 
		This method must only be called after the internal
		header has been read from the file, otherwise the data
		cannot be parsed correctly.
	 
	*/
	protected final void Parse(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		// If the entire tag is marked as unsynchronized, and this tag
		// is version id3v2.3 or lower, resynchronize it.
		boolean fullTagUnsynch = (header.getMajorVersion() < 4) && ((header.getFlags().getValue() & HeaderFlags.Unsynchronisation.getValue()) != 0);
		if (fullTagUnsynch)
		{
			SynchData.ResynchByteVector(data);
		}

		int frame_data_position = 0;
		int frame_data_length = data.size();

		// Check for the extended header.

		if ((header.getFlags().getValue() & HeaderFlags.ExtendedHeader.getValue()) != 0)
		{
			extended_header = new ExtendedHeader(data, header.getMajorVersion());

			if (extended_header.getSize() <= data.size())
			{
				frame_data_position += (int) extended_header.getSize();
				frame_data_length -= (int) extended_header.getSize();
			}
		}

		// Parse the frames. TDRC, TDAT, and TIME will be needed
		// for post-processing, so check for them as they are
		// loaded.
		TextInformationFrame tdrc = null;
		TextInformationFrame tyer = null;
		TextInformationFrame tdat = null;
		TextInformationFrame time = null;

		while (frame_data_position < frame_data_length - FrameHeader.Size(header.getMajorVersion()))
		{

			// If the next data is position is 0, assume
			// that we've hit the padding portion of the
			// frame data.
			if (data.get(frame_data_position) == 0)
			{
				break;
			}

			Frame frame = null;

			try
			{
				tangible.RefObject<Integer> tempRef_frame_data_position = new tangible.RefObject<Integer>(frame_data_position);
				frame = FrameFactory.CreateFrame(data, tempRef_frame_data_position, header.getMajorVersion(), fullTagUnsynch);
			frame_data_position = tempRef_frame_data_position.argValue;
			}
			catch (UnsupportedOperationException e)
			{
				continue;
			}
			catch (CorruptFileException e2)
			{
				continue;
			}

			if (frame == null)
			{
				break;
			}

			// Only add frames that contain data.
			if (frame.getSize() == 0)
			{
				continue;
			}

			AddFrame(frame);

			// If the tag is version 4, no post-processing
			// is needed.
			if (header.getMajorVersion() == 4)
			{
				continue;
			}

			// Load up the first instance of each, for
			// post-processing.

			if (tdrc == null && frame.getFrameId().equals(FrameType.TDRC))
			{
				tdrc = frame instanceof TextInformationFrame ? (TextInformationFrame)frame : null;
			}
			else if (tyer == null && frame.getFrameId().equals(FrameType.TYER))
			{
				tyer = frame instanceof TextInformationFrame ? (TextInformationFrame)frame : null;
			}
			else if (tdat == null && frame.getFrameId().equals(FrameType.TDAT))
			{
				tdat = frame instanceof TextInformationFrame ? (TextInformationFrame)frame : null;
			}
			else if (time == null && frame.getFrameId().equals(FrameType.TIME))
			{
				time = frame instanceof TextInformationFrame ? (TextInformationFrame)frame : null;
			}
		}

		// Try to fill out the date/time of the TDRC frame.  Can't do that if no TDRC
		// frame exists, or if there is no TDAT frame, or if TDRC already has the date.
		if (tdrc == null || tdat == null || tdrc.toString().length() > 4)
		{
			return;
		}

		String year = tdrc.toString();
		if (year.length() != 4)
		{
			return;
		}

		// Start with the year already in TDRC, then add the TDAT and TIME if available
		StringBuilder tdrc_text = new StringBuilder();
		tdrc_text.append(year);

		// Add the date
		if (tdat != null)
		{
			String tdat_text = tdat.toString();
			if (tdat_text.length() == 4)
			{
				tdrc_text.append("-").append(tdat_text, 0, 2).append("-").append(tdat_text, 2, 2);

				// Add the time
				if (time != null)
				{
					String time_text = time.toString();

					if (time_text.length() == 4)
					{
						tdrc_text.append("T").append(time_text, 0, 2).append(":").append(time_text, 2, 2);
					}

					RemoveFrames(FrameType.TIME);
				}
			}

			RemoveFrames(FrameType.TDAT);
		}

		tdrc.setText(new String [] {tdrc_text.toString()});
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	// TODO: These should become public some day.

	/** 
		Gets the text value from a specified Text Information
		Frame.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the frame
		identifier of the Text Information Frame to get the value
		from.
	 
	 @return 
		A <see cref="string" /> object containing the text of the
		specified frame, or <see langword="null" /> if no value
		was found.
	 
	*/
	private String GetTextAsString(ByteVector ident)
	{
		TextInformationFrame frame = TextInformationFrame.Get(this, ident, false);

		String result = frame == null ? null : frame.toString();
		return tangible.StringHelper.isNullOrEmpty(result) ? null : result;
	}

	/** 
		Gets the text values from a specified Text Information
		Frame.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the frame
		identifier of the Text Information Frame to get the value
		from.
	 
	 @return 
		A <see cref="string[]" /> containing the text of the
		specified frame, or an empty array if no values were
		found.
	 
	*/
	private String[] GetTextAsArray(ByteVector ident)
	{
		TextInformationFrame frame = TextInformationFrame.Get(this, ident, false);
		return frame == null ? new String [0] : frame.getText();
	}

	/** 
		Gets an integer value from a "/" delimited list in a
		specified Text Information Frame.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the frame
		identifier of the Text Information Frame to read from.
	 
	 @param index
		A <see cref="int" /> value specifying the index in the
		integer list of the value to return.
	 
	 @return 
		A <see cref="uint" /> value read from the list in the
		frame, or 0 if the value wasn't found.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint GetTextAsUInt32(ByteVector ident, int index)
	private int GetTextAsUInt32(ByteVector ident, int index)
	{
		String text = GetTextAsString(ident);

		if (text == null)
		{
			return 0;
		}

		String [] values = text.split("[/]", index + 2);

		if (values.length < index + 1)
		{
			return 0;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint result;
		int result;
		tangible.OutObject<Integer> tempOut_result = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (uint.TryParse(values [index], out result))
		if (tangible.TryParseHelper.tryParseInt(values [index], tempOut_result))
		{
		result = tempOut_result.argValue;
			return result;
		}
	else
	{
		result = tempOut_result.argValue;
	}

		return 0;
	}

	/** 
	 Gets a TXXX frame via reference of the description field, optionally searching for the
	 frame in a case-sensitive manner.
	 
	 @param description String containing the description field
	 @return UserTextInformationFrame (TXXX) that corresponds to the description
	*/
	private String GetUserTextAsString(String description, boolean caseSensitive)
	{

		//Gets the TXXX frame, frame will be null if nonexistant
		UserTextInformationFrame frame = UserTextInformationFrame.Get(this, description, Tag.getDefaultEncoding(), false, caseSensitive);

		//TXXX frames support multivalue strings, join them up and return
		//only the text from the frame.
		String result = frame == null ? null : tangible.StringHelper.join(";",frame.getText());
		return tangible.StringHelper.isNullOrEmpty(result) ? null : result;

	}

	/** 
	 Gets a TXXX frame via reference of the description field.
	 
	 @param description String containing the description field
	 @return UserTextInformationFrame (TXXX) that corresponds to the description
	*/
	private String GetUserTextAsString(String description)
	{
		return GetUserTextAsString(description, true);
	}

	/** 
	 Creates and/or sets a UserTextInformationFrame (TXXX)  with the given
	 description and text, optionally searching for the frame in a case-sensitive manner.
	 
	 @param description String containing the Description field for the
	 TXXX frame
	 @param text String containing the Text field for the TXXX frame
	*/
	private void SetUserTextAsString(String description, String text, boolean caseSensitive)
	{
		//Get the TXXX frame, create a new one if needed
		UserTextInformationFrame frame = UserTextInformationFrame.Get(this, description, Tag.getDefaultEncoding(), true, caseSensitive);

		if (!tangible.StringHelper.isNullOrEmpty(text))
		{
			frame.setText(text.split("[;]", -1));
		}
		else
		{
			//Text string is null or empty, delete the frame, prevent empties
			RemoveFrame(frame);
		}
	}

	/** 
	 Creates and/or sets a UserTextInformationFrame (TXXX)  with the given
	 description and text.
	 
	 @param description String containing the Description field for the
	 TXXX frame
	 @param text String containing the Text field for the TXXX frame
	*/
	private void SetUserTextAsString(String description, String text)
	{
		SetUserTextAsString(description, text, true);
	}

	/** 
	 Gets the text from a particular UFID frame, referenced by the owner field
	 
	 @param owner String containing the "Owner" data
	 @return String containing the text from the UFID frame, or null
	*/
	private String GetUfidText(String owner)
	{

		//Get the UFID frame, frame will be null if nonexistant
		UniqueFileIdentifierFrame frame = UniqueFileIdentifierFrame.Get(this, owner, false);

		//If the frame existed: frame.Identifier is a bytevector, get a string
		String result = frame == null ? null : frame.getIdentifier().toString();
		return tangible.StringHelper.isNullOrEmpty(result) ? null : result;
	}

	/** 
	 Creates and/or sets the text for a UFID frame, referenced by owner
	 
	 @param owner String containing the Owner field
	 @param text String containing the text to set for the frame
	*/
	private void SetUfidText(String owner, String text)
	{

		//Get a UFID frame, create if necessary
		UniqueFileIdentifierFrame frame = UniqueFileIdentifierFrame.Get(this, owner, true);

		//If we have a real string, convert to ByteVector and apply to frame
		if (!tangible.StringHelper.isNullOrEmpty(text))
		{
			ByteVector identifier = ByteVector.FromString(text, StringType.UTF8);
			frame.setIdentifier(identifier);
		}
		else
		{
			//String was null or empty, remove the frame to prevent empties
			RemoveFrame(frame);
		}
	}

	/** 
		Moves a specified frame so it is the first of its type in
		the tag.
	 
	 @param frame
		A <see cref="Frame" /> object to make the first of its
		type.
	 
	*/
	private void MakeFirstOfType(Frame frame)
	{
		ByteVector type = frame.getFrameId();
		Frame swapping = null;
		for (int i = 0; i < frame_list.size(); i++)
		{
			if (swapping == null)
			{
				if (frame_list.get(i).getFrameId().equals(type))
				{
					swapping = frame;
				}
				else
				{
					continue;
				}
			}

			Frame tmp = frame_list.get(i);
			frame_list.set(i, swapping);
			swapping = tmp;

			if (swapping == frame)
			{
				return;
			}
		}

		if (swapping != null)
		{
			frame_list.add(swapping);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IEnumerable

	/** 
		Gets an enumerator for enumerating through the frames.
	 
	 @return 
		A <see cref="T:System.Collections.IEnumerator`1" /> for
		enumerating through the frames.
	 
	*/
	public final Iterator<Frame> iterator()
	{
		return frame_list.iterator();
	}

	public final Iterator GetEnumerator()
	{
		return frame_list.iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.Id3v2" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.Id3v2;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "TIT2" Text
		Information Frame.
	 
	*/
	@Override
	public String getTitle()
	{
		return GetTextAsString(FrameType.TIT2);
	}
	@Override
	public void setTitle(String value)
	{
		SetTextFrame(FrameType.TIT2, value);
	}

	/** 
		Gets and sets the sort names of the Title of the
		media represented by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort names for
		the Title of the media described by the current instance,
		or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TSOT" Text
		Information Frame.
	 
	*/
	@Override
	public String getTitleSort()
	{
		return GetTextAsString(FrameType.TSOT);
	}
	@Override
	public void setTitleSort(String value)
	{
		SetTextFrame(FrameType.TSOT, value);
	}

	/** 
		Gets and sets the performers or artists who performed in
		the media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the performers or
		artists who performed in the media described by the
		current instance or an empty array if no value is
		present.
	 </value>
	 
		This property is implemented using the "TPE1" Text
		Information Frame.
	 
	*/
	@Override
	public String[] getPerformers()
	{
		return GetTextAsArray(FrameType.TPE1);
	}
	@Override
	public void setPerformers(String[] value)
	{
		SetTextFrame(FrameType.TPE1, value);
	}

	/** 
		Gets and sets the sort names of the performers or artists
		who performed in the media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names for
		the performers or artists who performed in the media
		described by the current instance, or an empty array if
		no value is present. 
	 </value>
	 
		This property is implemented using the "TSOP" Text
		Information Frame. http://www.id3.org/id3v2.4.0-frames
	 
	*/
	@Override
	public String[] getPerformersSort()
	{
		return GetTextAsArray(FrameType.TSOP);
	}
	@Override
	public void setPerformersSort(String[] value)
	{
		SetTextFrame(FrameType.TSOP, value);
	}

	/** 
		Gets and sets the sort names of the band or artist who is 
		credited in the creation of the entire album or collection
		containing the media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names for
		the performers or artists who performed in the media
		described by the current instance, or an empty array if
		no value is present. 
	 </value>
	 
		This property is implemented using the "TSO2" Text
		Information Frame. http://www.id3.org/iTunes
	 
	*/
	@Override
	public String[] getAlbumArtistsSort()
	{
		return GetTextAsArray(FrameType.TSO2);
	}
	@Override
	public void setAlbumArtistsSort(String[] value)
	{
		SetTextFrame(FrameType.TSO2, value);
	}

	/** 
		Gets and sets the band or artist who is credited in the
		creation of the entire album or collection containing the
		media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the band or artist
		who is credited in the creation of the entire album or
		collection containing the media described by the current
		instance or an empty array if no value is present.
	 </value>
	 
		This property is implemented using the "TPE2" Text
		Information Frame.
	 
	*/
	@Override
	public String[] getAlbumArtists()
	{
		return GetTextAsArray(FrameType.TPE2);
	}
	@Override
	public void setAlbumArtists(String[] value)
	{
		SetTextFrame(FrameType.TPE2, value);
	}

	/** 
		Gets and sets the composers of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the composers of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "TCOM" Text
		Information Frame.
	 
	*/
	@Override
	public String[] getComposers()
	{
		return GetTextAsArray(FrameType.TCOM);
	}
	@Override
	public void setComposers(String[] value)
	{
		SetTextFrame(FrameType.TCOM, value);
	}

	/** 
		Gets and sets the sort names of the composers of the
		media represented by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names for
		the performers or artists who performed in the media
		described by the current instance, or an empty array if
		no value is present. 
	 </value>
	 
		This property is implemented using the "TSOC" Text
		Information Frame. http://www.id3.org/id3v2.4.0-frames
	 
	*/
	@Override
	public String[] getComposersSort()
	{
		return GetTextAsArray(FrameType.TSOC);
	}
	@Override
	public void setComposersSort(String[] value)
	{
		SetTextFrame(FrameType.TSOC, value);
	}

	/** 
		Gets and sets the album of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the album of
		the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "TALB" Text
		Information Frame.
	 
	*/
	@Override
	public String getAlbum()
	{
		return GetTextAsString(FrameType.TALB);
	}
	@Override
	public void setAlbum(String value)
	{
		SetTextFrame(FrameType.TALB, value);
	}

	/** 
		Gets and sets the sort names of the Album title of the
		media represented by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort names for
		the Title in the media described by the current instance,
		or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TSOA" Text
		Information Frame. http://www.id3.org/id3v2.4.0-frames
	 
	*/
	@Override
	public String getAlbumSort()
	{
		return GetTextAsString(FrameType.TSOA);
	}
	@Override
	public void setAlbumSort(String value)
	{
		SetTextFrame(FrameType.TSOA, value);
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "COMM" Comments
		Frame with an empty description and the language
		specified by <see cref="Language" />.
	 
	*/
	@Override
	public String getComment()
	{
		CommentsFrame f = CommentsFrame.GetPreferred(this, "", getLanguage());
		return f != null ? f.toString() : null;
	}
	@Override
	public void setComment(String value)
	{
		CommentsFrame frame;

		if (tangible.StringHelper.isNullOrEmpty(value))
		{
			while ((frame = CommentsFrame.GetPreferred(this, "", getLanguage())) != null)
			{
				RemoveFrame(frame);
			}

			return;
		}

		frame = CommentsFrame.Get(this, "", getLanguage(), true);

		frame.setText(value);
		frame.setTextEncoding(getDefaultEncoding());
		MakeFirstOfType(frame);
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "TCON" Text
		Information Frame.
	 
	*/
	@Override
	public String[] getGenres()
	{
		String [] text = GetTextAsArray(FrameType.TCON);

		if (text.length == 0)
		{
			return text;
		}

		ArrayList<String> list = new ArrayList<String> ();

		for (String genre : text)
		{
			if (tangible.StringHelper.isNullOrEmpty(genre))
			{
				continue;
			}

				// The string may just be a genre
				// number.

			String genre_from_index = Rasad.Core.Media.MediaMetadataManagement.Genres.IndexToAudio(genre);

			if (genre_from_index != null)
			{
				list.add(genre_from_index);
			}
			else
			{
				list.add(genre);
			}
		}

		return list.toArray(new String[0]);
	}
	@Override
	public void setGenres(String[] value)
	{
		if (value == null || !use_numeric_genres)
		{
			SetTextFrame(FrameType.TCON, value);
			return;
		}

			// Clone the array so changes made won't effect
			// the passed array.
		value = (String []) value.clone();

		for (int i = 0; i < value.length; i++)
		{
			int index = Rasad.Core.Media.MediaMetadataManagement.Genres.AudioToIndex(value [i]);

			if (index != 255)
			{
				value [i] = (new Integer(index)).toString(CultureInfo.InvariantCulture);
			}
		}

		SetTextFrame(FrameType.TCON, value);
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		This property is implemented using the "TDRC" Text
		Information Frame. If a value greater than 9999 is set,
		this property will be cleared.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
		String text = GetTextAsString(FrameType.TDRC);

		if (text == null || text.length() < 4)
		{
			return 0;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;
		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (uint.TryParse(text.Substring(0, 4), out value))
		if (tangible.TryParseHelper.tryParseInt(text.substring(0, 4), tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setYear(uint value)
	@Override
	public void setYear(int value)
	{
		if (value > 9999)
		{
			value = 0;
		}

		SetNumberFrame(FrameType.TDRC, value, 0);
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	 
		This property is implemented using the "TRCK" Text
		Information Frame.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		return GetTextAsUInt32(FrameType.TRCK, 0);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
		SetNumberFrame(FrameType.TRCK, value, getTrackCount());
	}

	/** 
		Gets and sets the number of tracks in the album
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of tracks in
		the album containing the media represented by the current
		instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "TRCK" Text
		Information Frame.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		return GetTextAsUInt32(FrameType.TRCK, 1);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrackCount(uint value)
	@Override
	public void setTrackCount(int value)
	{
		SetNumberFrame(FrameType.TRCK, getTrack(), value);
	}

	/** 
		Gets and sets the number of the disc containing the media
		represented by the current instance in the boxed set.
	 
	 <value>
		A <see cref="uint" /> containing the number of the disc
		containing the media represented by the current instance
		in the boxed set.
	 </value>
	 
		This property is implemented using the "TPOS" Text
		Information Frame.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDisc()
	@Override
	public int getDisc()
	{
		return GetTextAsUInt32(FrameType.TPOS, 0);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDisc(uint value)
	@Override
	public void setDisc(int value)
	{
		SetNumberFrame(FrameType.TPOS, value, getDiscCount());
	}

	/** 
		Gets and sets the number of discs in the boxed set
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of discs in
		the boxed set containing the media represented by the
		current instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "TPOS" Text
		Information Frame.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDiscCount()
	@Override
	public int getDiscCount()
	{
		return GetTextAsUInt32(FrameType.TPOS, 1);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDiscCount(uint value)
	@Override
	public void setDiscCount(int value)
	{
		SetNumberFrame(FrameType.TPOS, getDisc(), value);
	}

	/** 
		Gets and sets the lyrics or script of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the lyrics or
		script of the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "USLT"
		Unsynchronized Lyrics Frame with an empty description and
		the language specified by <see cref="Language" />.
	 
	*/
	@Override
	public String getLyrics()
	{
		UnsynchronisedLyricsFrame f = UnsynchronisedLyricsFrame.GetPreferred(this, "", getLanguage());

		return f != null ? f.toString() : null;
	}
	@Override
	public void setLyrics(String value)
	{
		UnsynchronisedLyricsFrame frame;

		if (tangible.StringHelper.isNullOrEmpty(value))
		{
			while ((frame = UnsynchronisedLyricsFrame.GetPreferred(this, "", getLanguage())) != null)
			{
				RemoveFrame(frame);
			}

			return;
		}

		frame = UnsynchronisedLyricsFrame.Get(this, "", getLanguage(), true);

		frame.setText(value);
		frame.setTextEncoding(getDefaultEncoding());
	}

	/** 
		Gets and sets the grouping on the album which the media
		in the current instance belongs to.
	 
	 <value>
		A <see cref="string" /> object containing the grouping on
		the album which the media in the current instance belongs
		to or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "TIT1" Text
		Information Frame.
	 
	*/
	@Override
	public String getGrouping()
	{
		return GetTextAsString(FrameType.TIT1);
	}
	@Override
	public void setGrouping(String value)
	{
		SetTextFrame(FrameType.TIT1, value);
	}

	/** 
		Gets and sets the number of beats per minute in the audio
		of the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of beats per
		minute in the audio of the media represented by the
		current instance, or zero if not specified.
	 </value>
	 
		This property is implemented using the "TBPM" Text
		Information Frame.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getBeatsPerMinute()
	@Override
	public int getBeatsPerMinute()
	{
		String text = GetTextAsString(FrameType.TBPM);

		if (text == null)
		{
			return 0;
		}

		double result;
		tangible.OutObject<Double> tempOut_result = new tangible.OutObject<Double>();
		if (tangible.TryParseHelper.tryParseDouble(text, tempOut_result) && result >= 0.0)
		{
		result = tempOut_result.argValue;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint) Math.Round(result);
			return (int) Math.rint(result);
		}
	else
	{
		result = tempOut_result.argValue;
	}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setBeatsPerMinute(uint value)
	@Override
	public void setBeatsPerMinute(int value)
	{
		SetNumberFrame(FrameType.TBPM, value, 0);
	}

	/** 
		Gets and sets the conductor or director of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the conductor
		or director of the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "TPE3" Text
		Information Frame.
	 
	*/
	@Override
	public String getConductor()
	{
		return GetTextAsString(FrameType.TPE3);
	}
	@Override
	public void setConductor(String value)
	{
		SetTextFrame(FrameType.TPE3, value);
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "TCOP" Text
		Information Frame.
	 
	*/
	@Override
	public String getCopyright()
	{
		return GetTextAsString(FrameType.TCOP);
	}
	@Override
	public void setCopyright(String value)
	{
		SetTextFrame(FrameType.TCOP, value);
	}

	/** 
		Gets and sets the MusicBrainz ArtistID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ArtistID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TXXX:MusicBrainz Artist Id" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzArtistId()
	{
		return GetUserTextAsString("MusicBrainz Artist Id");
	}
	@Override
	public void setMusicBrainzArtistId(String value)
	{
		SetUserTextAsString("MusicBrainz Artist Id", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TXXX:MusicBrainz Album Id" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseId()
	{
		return GetUserTextAsString("MusicBrainz Album Id");
	}
	@Override
	public void setMusicBrainzReleaseId(String value)
	{
		SetUserTextAsString("MusicBrainz Album Id", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseArtistID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseArtistID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TXXX:MusicBrainz Album Artist Id" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseArtistId()
	{
		return GetUserTextAsString("MusicBrainz Album Artist Id");
	}
	@Override
	public void setMusicBrainzReleaseArtistId(String value)
	{
		SetUserTextAsString("MusicBrainz Album Artist Id", value);
	}

	/** 
		Gets and sets the MusicBrainz TrackID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		TrackID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "UFID:http://musicbrainz.org" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzTrackId()
	{
		return GetUfidText("http://musicbrainz.org");
	}
	@Override
	public void setMusicBrainzTrackId(String value)
	{
		SetUfidText("http://musicbrainz.org", value);
	}

	/** 
		Gets and sets the MusicBrainz DiscID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		DiscID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TXXX:MusicBrainz Disc Id" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzDiscId()
	{
		return GetUserTextAsString("MusicBrainz Disc Id");
	}
	@Override
	public void setMusicBrainzDiscId(String value)
	{
		SetUserTextAsString("MusicBrainz Disc Id", value);
	}

	/** 
		Gets and sets the MusicIP PUID
	 
	 <value>
		A <see cref="string" /> containing the MusicIP PUID
		for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TXXX:MusicIP PUID" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicIpId()
	{
		return GetUserTextAsString("MusicIP PUID");
	}
	@Override
	public void setMusicIpId(String value)
	{
		SetUserTextAsString("MusicIP PUID", value);
	}

	/** 
		Gets and sets the Amazon ID (ASIN)
	 
	 <value>
		A <see cref="string" /> containing the Amazon Id
		for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TXXX:ASIN" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getAmazonId()
	{
		return GetUserTextAsString("ASIN");
	}
	@Override
	public void setAmazonId(String value)
	{
		SetUserTextAsString("ASIN", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseStatus
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseStatus for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TXXX:MusicBrainz Album Status" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseStatus()
	{
		return GetUserTextAsString("MusicBrainz Album Status");
	}
	@Override
	public void setMusicBrainzReleaseStatus(String value)
	{
		SetUserTextAsString("MusicBrainz Album Status", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseType
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseType for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TXXX:MusicBrainz Album Type" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseType()
	{
		return GetUserTextAsString("MusicBrainz Album Type");
	}
	@Override
	public void setMusicBrainzReleaseType(String value)
	{
		SetUserTextAsString("MusicBrainz Album Type", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseCountry
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseCountry for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TXXX:MusicBrainz Album Release Country" frame.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseCountry()
	{
		return GetUserTextAsString("MusicBrainz Album Release Country");
	}
	@Override
	public void setMusicBrainzReleaseCountry(String value)
	{
		SetUserTextAsString("MusicBrainz Album Release Country", value);
	}

	/** 
		Gets and sets the ReplayGain track gain in dB.
	 
	 <value>
		A <see cref="bool" /> value in dB for the track gain as
		per the ReplayGain specification.
	 </value>
	 
		This property is implemented using the "TXXX:REPLAYGAIN_TRACK_GAIN" frame.
		http://wiki.hydrogenaudio.org/index.php?title=ReplayGain_specification#ID3v2
	 
	*/
	@Override
	public double getReplayGainTrackGain()
	{
		String text = GetUserTextAsString("REPLAYGAIN_TRACK_GAIN", false);
		double value;

		if (text == null)
		{
			return Double.NaN;
		}
		if (text.toLowerCase(Locale.ROOT).endsWith("db"))
		{
			text = text.substring(0, text.length() - 2).trim();
		}

		tangible.OutObject<Double> tempOut_value = new tangible.OutObject<Double>();
		if (tangible.TryParseHelper.tryParseDouble(text, NumberStyles.Float, CultureInfo.InvariantCulture, tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}
		return Double.NaN;
	}
	@Override
	public void setReplayGainTrackGain(double value)
	{
		if (Double.isNaN(value))
		{
			SetUserTextAsString("REPLAYGAIN_TRACK_GAIN", null, false);
		}
		else
		{
			String text = (new Double(value)).toString("0.00 dB", CultureInfo.InvariantCulture);
			SetUserTextAsString("REPLAYGAIN_TRACK_GAIN", text, false);
		}
	}

	/** 
		Gets and sets the ReplayGain track peak sample.
	 
	 <value>
		A <see cref="bool" /> value for the track peak as per the
		ReplayGain specification.
	 </value>
	 
		This property is implemented using the "TXXX:REPLAYGAIN_TRACK_PEAK" frame.
		http://wiki.hydrogenaudio.org/index.php?title=ReplayGain_specification#ID3v2
	 
	*/
	@Override
	public double getReplayGainTrackPeak()
	{
		String text;
		double value;

		tangible.OutObject<Double> tempOut_value = new tangible.OutObject<Double>();
		if ((text = GetUserTextAsString("REPLAYGAIN_TRACK_PEAK", false)) != null && tangible.TryParseHelper.tryParseDouble(text, NumberStyles.Float, CultureInfo.InvariantCulture, tempOut_value))
		{
		value = tempOut_value.argValue;
				return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}
		return Double.NaN;
	}
	@Override
	public void setReplayGainTrackPeak(double value)
	{
		if (Double.isNaN(value))
		{
			SetUserTextAsString("REPLAYGAIN_TRACK_PEAK", null, false);
		}
		else
		{
			String text = (new Double(value)).toString("0.000000", CultureInfo.InvariantCulture);
			SetUserTextAsString("REPLAYGAIN_TRACK_PEAK", text, false);
		}
	}

	/** 
		Gets and sets the ReplayGain album gain in dB.
	 
	 <value>
		A <see cref="bool" /> value in dB for the album gain as
		per the ReplayGain specification.
	 </value>
	 
		This property is implemented using the "TXXX:REPLAYGAIN_ALBUM_GAIN" frame.
		http://wiki.hydrogenaudio.org/index.php?title=ReplayGain_specification#ID3v2
	 
	*/
	@Override
	public double getReplayGainAlbumGain()
	{
		String text = GetUserTextAsString("REPLAYGAIN_ALBUM_GAIN", false);
		double value;

		if (text == null)
		{
			return Double.NaN;
		}
		if (text.toLowerCase(Locale.ROOT).endsWith("db"))
		{
			text = text.substring(0, text.length() - 2).trim();
		}

		tangible.OutObject<Double> tempOut_value = new tangible.OutObject<Double>();
		if (tangible.TryParseHelper.tryParseDouble(text, NumberStyles.Float, CultureInfo.InvariantCulture, tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}
		return Double.NaN;
	}
	@Override
	public void setReplayGainAlbumGain(double value)
	{
		if (Double.isNaN(value))
		{
			SetUserTextAsString("REPLAYGAIN_ALBUM_GAIN", null, false);
		}
		else
		{
			String text = (new Double(value)).toString("0.00 dB", CultureInfo.InvariantCulture);
			SetUserTextAsString("REPLAYGAIN_ALBUM_GAIN", text, false);
		}
	}

	/** 
		Gets and sets the ReplayGain album peak sample.
	 
	 <value>
		A <see cref="bool" /> value for the album peak as per the
		ReplayGain specification.
	 </value>
	 
		This property is implemented using the "TXXX:REPLAYGAIN_ALBUM_PEAK" frame.
		http://wiki.hydrogenaudio.org/index.php?title=ReplayGain_specification#ID3v2
	 
	*/
	@Override
	public double getReplayGainAlbumPeak()
	{
		String text;
		double value;

		tangible.OutObject<Double> tempOut_value = new tangible.OutObject<Double>();
		if ((text = GetUserTextAsString("REPLAYGAIN_ALBUM_PEAK", false)) != null && tangible.TryParseHelper.tryParseDouble(text, NumberStyles.Float, CultureInfo.InvariantCulture, tempOut_value))
		{
		value = tempOut_value.argValue;
				return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}
		return Double.NaN;
	}
	@Override
	public void setReplayGainAlbumPeak(double value)
	{
		if (Double.isNaN(value))
		{
			SetUserTextAsString("REPLAYGAIN_ALBUM_PEAK", null, false);
		}
		else
		{
			String text = (new Double(value)).toString("0.000000", CultureInfo.InvariantCulture);
			SetUserTextAsString("REPLAYGAIN_ALBUM_PEAK", text, false);
		}
	}

	/** 
		Gets and sets a collection of pictures associated with
		the media represented by the current instance.
	 
	 <value>
		A <see cref="IPicture[]" /> containing a collection of
		pictures associated with the media represented by the
		current instance or an empty array if none are present.
	 </value>
	 
		This property is implemented using the "APIC" Attached
		Picture Frame.
	 
	*/
	@Override
	public IPicture[] getPictures()
	{
		return (new ArrayList<AttachedPictureFrame> (this.<AttachedPictureFrame>GetFrames  (FrameType.APIC))).toArray(new AttachedPictureFrame[0]);
	}
	@Override
	public void setPictures(IPicture[] value)
	{
		RemoveFrames(FrameType.APIC);

		if (value == null || value.length == 0)
		{
			return;
		}

		for (IPicture picture : value)
		{
			AttachedPictureFrame frame = picture instanceof AttachedPictureFrame ? (AttachedPictureFrame)picture : null;

			if (frame == null)
			{
				frame = new AttachedPictureFrame(picture);
			}

			AddFrame(frame);
		}
	}

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if the current instance does not
		any values. Otherwise <see langword="false" />.
	 </value>
	*/
	@Override
	public boolean getIsEmpty()
	{
		return frame_list.isEmpty();
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		frame_list.clear();
	}

	/** 
		Gets and sets whether or not the album described by the
		current instance is a compilation.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		album described by the current instance is a compilation.
	 </value>
	 
		This property is implemented using the "TCMP" Text
		Information Frame to provide support for a feature of the
		Apple iPod and iTunes products.
	 
	*/
	public final boolean getIsCompilation()
	{
		String val = GetTextAsString(FrameType.TCMP);
		return !tangible.StringHelper.isNullOrEmpty(val) && !val.equals("0");
	}
	public final void setIsCompilation(boolean value)
	{
		SetTextFrame(FrameType.TCMP, value ? "1" : null);
	}

	/** 
		Copies the values from the current instance to another
		<see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" />, optionally overwriting
		existing values.
	 
	 @param target
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> object containing the target
		tag to copy values to.
	 
	 @param overwrite
		A <see cref="bool" /> specifying whether or not to copy
		values over existing one.
	 
	 
		<p>If <paramref name="target" /> is of type <see
		cref="Rasad.Core.Media.MediaMetadataManagement.Ape.Tag" /> a complete copy of all values
		will be performed. Otherwise, only standard values will
		be copied.</p>
	 
	 @exception ArgumentNullException
		<paramref name="target" /> is <see langword="null" />.
	 
	*/
	@Override
	public void CopyTo(Rasad.Core.Media.MediaMetadataManagement.Tag target, boolean overwrite)
	{
		if (target == null)
		{
			throw new NullPointerException("target");
		}

		Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag match = target instanceof Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag ? (Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag)target : null;

		if (match == null)
		{
			super.CopyTo(target, overwrite);
			return;
		}

		ArrayList<Frame> frames = new ArrayList<Frame> (frame_list);
		while (!frames.isEmpty())
		{
			ByteVector ident = frames.get(0).getFrameId();
			boolean copy = true;
			if (overwrite)
			{
				match.RemoveFrames(ident);
			}
			else
			{
				for (Frame f : match.frame_list)
				{
					if (f.getFrameId().equals(ident))
					{
						copy = false;
						break;
					}
				}
			}

			for (int i = 0; i < frames.size();)
			{
				if (frames.get(i).getFrameId().equals(ident))
				{
					if (copy)
					{
						match.frame_list.add(frames.get(i).Clone());
					}

					frames.remove(i);
				}
				else
				{
					i++;
				}
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region ICloneable

	/** 
		Creates a deep copy of the current instance.
	 
	 @return 
		A new <see cref="Tag" /> object identical to the current
		instance.
	 
	*/
	public final Tag Clone()
	{
		Tag tag = new Tag();
		tag.header = header.clone();
		if (tag.extended_header != null)
		{
			tag.extended_header = extended_header.Clone();
		}

		for (Frame frame : frame_list)
		{
			tag.frame_list.Add(frame.Clone());
		}

		return tag;
	}

	public final Object Clone()
	{
		return Clone();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}