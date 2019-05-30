package Rasad.Core.Media.MediaMetadataManagement.Id3v1;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Tag.cs: Provide support for reading and writing ID3v1 tags.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   id3v1tag.cpp from Rasad.Core.Media.MediaMetadataManagement
//
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
	This class extends <see cref="Tag" /> to provide support for
	reading and writing tags stored in the ID3v1.1 format.
*/
public class Tag extends Rasad.Core.Media.MediaMetadataManagement.Tag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Static Fields

	private static StringHandler string_handler = new StringHandler();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the title.
	*/
	private String title;

	/** 
		Contains the semicolon separated performers.
	*/
	private String artist;

	/** 
		Contains the album name.
	*/
	private String album;

	/** 
		Contains the 4 digit year.
	*/
	private String year;

	/** 
		Contains a comment on track.
	*/
	private String comment;

	/** 
		Contains the track number in the album.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte track;
	private byte track;

	/** 
		Contains the genre index.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte genre;
	private byte genre;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion




//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Fields

	/** 
		The size of a ID3v1 tag.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint Size = 128;
	public static final int Size = 128;

	/** 
		The identifier used to recognize a ID3v1 tags.
	 
	 <value>
		"TAG"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "TAG";

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
		Clear();
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
	 
	 @exception CorruptFileException
		The file does not contain <see cref="FileIdentifier" />
		at the given position.
	 
	*/
	public Tag(File file, long position)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.setMode(Rasad.Core.Media.MediaMetadataManagement.File.AccessMode.Read);

		if (position < 0 || position > file.getLength() - Size)
		{
			throw new IndexOutOfBoundsException("position");
		}

		file.Seek(position);

		// read the tag -- always 128 bytes

		ByteVector data = file.ReadBlock((int) Size);

		// some initial sanity checking

		if (!data.StartsWith(FileIdentifier))
		{
			throw new CorruptFileException("ID3v1 data does not start with identifier.");
		}

		Parse(data);
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
		<paramref name="data" /> is less than 128 bytes or does
		not start with <see cref="FileIdentifier" />.
	 
	*/
	public Tag(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (data.size() < Size)
		{
			throw new CorruptFileException("ID3v1 data is less than 128 bytes long.");
		}

		if (!data.StartsWith(FileIdentifier))
		{
			throw new CorruptFileException("ID3v1 data does not start with identifier.");
		}

		Parse(data);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance as a raw ID3v1 tag.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered tag.
	 
	*/
	public final ByteVector Render()
	{
		ByteVector data = new ByteVector();

		data.add(FileIdentifier);
		data.add(string_handler.Render(title).Resize(30));
		data.add(string_handler.Render(artist).Resize(30));
		data.add(string_handler.Render(album).Resize(30));
		data.add(string_handler.Render(year).Resize(4));
		data.add(string_handler.Render(comment).Resize(28));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add((byte) 0);
		data.add((byte) 0);
		data.add(track);
		data.add(genre);

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Properties

	/** 
		Gets and sets the <see cref="StringHandler" /> object
		to use when reading and writing ID3v1 fields.
	 
	 <value>
		A <see cref="StringHandler" /> object to use when
		processing fields.
	 </value>
	*/
	public static StringHandler getDefaultStringHandler()
	{
		return string_handler;
	}
	public static void setDefaultStringHandler(StringHandler value)
	{
		string_handler = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	/** 
		Populates the current instance by parsing the contents of
		a raw ID3v1 tag.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the
		starting with an ID3v1 tag.
	 
	*/
	private void Parse(ByteVector data)
	{
		title = string_handler.Parse(data.Mid(3, 30));
		artist = string_handler.Parse(data.Mid(33, 30));
		album = string_handler.Parse(data.Mid(63, 30));
		year = string_handler.Parse(data.Mid(93, 4));

		// Check for ID3v1.1 -- Note that ID3v1 *does not*
		// support "track zero" -- this is not a bug in Rasad.Core.Media.MediaMetadataManagement.
		// Since a zeroed byte is what we would expect to
		// indicate the end of a C-String, specifically the
		// comment string, a value of zero must be assumed to be
		// just that.

		if (data.get(125) == 0 && data.get(126) != 0)
		{
			// ID3v1.1 detected
			comment = string_handler.Parse(data.Mid(97, 28));
			track = data.get(126);
		}
		else
		{
			comment = string_handler.Parse(data.Mid(97, 30));
		}

		genre = data.get(127);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.Id3v1" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.Id3v1;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		When stored on disk, only the first 30 bytes of the
		Latin-1 encoded value will be stored. This may result in
		lost data.
	 
	*/
	@Override
	public String getTitle()
	{
		return tangible.StringHelper.isNullOrEmpty(title) ? null : title;
	}
	@Override
	public void setTitle(String value)
	{
		title = value != null ? value.trim() : "";
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
	 
		When stored on disk, only the first 30 bytes of the
		Latin-1 encoded value will be stored, minus a byte for
		each additionial performer (i.e. two performers will only
		have 29 bytes and three performers will only have 28
		bytes).This may result in lost data.
	 
	*/
	@Override
	public String[] getPerformers()
	{
		return tangible.StringHelper.isNullOrEmpty(artist) ? new String [0] : artist.split("[;]", -1);
	}
	@Override
	public void setPerformers(String[] value)
	{
		artist = value != null ? tangible.StringHelper.join(";", value) : "";
	}

	/** 
		Gets and sets the album of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the album of
		the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		When stored on disk, only the first 30 bytes of the
		Latin-1 encoded value will be stored. This may result in
		lost data.
	 
	*/
	@Override
	public String getAlbum()
	{
		return tangible.StringHelper.isNullOrEmpty(album) ? null : album;
	}
	@Override
	public void setAlbum(String value)
	{
		album = value != null ? value.trim() : "";
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		When stored on disk, only the first 28 bytes of the
		Latin-1 encoded value will be stored. This may result in
		lost data.
	 
	*/
	@Override
	public String getComment()
	{
		return tangible.StringHelper.isNullOrEmpty(comment) ? null : comment;
	}
	@Override
	public void setComment(String value)
	{
		comment = value != null ? value.trim() : "";
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		Only first genre will be stored and only if it is an
		exact match for a value appearing in <see
		cref="Rasad.Core.Media.MediaMetadataManagement.Genres.Audio" />. All other values will
		result in the property being cleared.
	 
	*/
	@Override
	public String[] getGenres()
	{
		String genre_name = Rasad.Core.Media.MediaMetadataManagement.Genres.IndexToAudio(genre);

		return (genre_name != null) ? new String [] {genre_name} : new String [0];
	}
	@Override
	public void setGenres(String[] value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: genre = (value == null || value.Length == 0) ? (byte) 255 : Rasad.Core.Media.MediaMetadataManagement.Genres.AudioToIndex(value [0].Trim());
		genre = (value == null || value.length == 0) ? (byte) 255 : Rasad.Core.Media.MediaMetadataManagement.Genres.AudioToIndex(value [0].trim());
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		Only values between 1 and 9999 will be stored, all other
		values will result in the property being zeroed.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;
		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return uint.TryParse(year, NumberStyles.Integer, CultureInfo.InvariantCulture, out value) ? value : 0;
		int tempVar = tangible.TryParseHelper.tryParseInt(year, NumberStyles.Integer, CultureInfo.InvariantCulture, tempOut_value) ? value : 0;
	value = tempOut_value.argValue;
	return tempVar;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setYear(uint value)
	@Override
	public void setYear(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: year = (value > 0 && value < 10000) ? value.ToString(CultureInfo.InvariantCulture) : String.Empty;
		year = (value > 0 && value < 10000) ? (new Integer(value)).toString(CultureInfo.InvariantCulture) : "";
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	 
		Only values between 1 and 255 will be stored, all other
		values will result in the property being zeroed.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		return track;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: track = (byte)(value < 256 ? value : 0);
		track = (byte)(value < 256 ? value : 0);
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		title = artist = album = year = comment = null;
		track = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: genre = 255;
		genre = (byte)255;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}