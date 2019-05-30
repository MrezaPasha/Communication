package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// DivXTag.cs: Provide support for reading and writing DivX tags.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag
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
	This class extends <see cref="Tag" /> to provide support for
	reading and writing tags stored in the DivX format.
*/
public class DivXTag extends Rasad.Core.Media.MediaMetadataManagement.Tag
{
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
		Contains the 4 digit year.
	*/
	private String year;

	/** 
		Contains a comment on track.
	*/
	private String comment;

	/** 
		Contains the genre index.
	*/
	private String genre;

	/** 
		Contains the extra 6 bytes at the end of the tag.
	*/
	private ByteVector extra_data;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion




//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Fields

	/** 
		The size of a DivX tag.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint Size = 128;
	public static final int Size = 128;

	/** 
		The identifier used to recognize a DivX tags.
	 
	 <value>
		"DIVXTAG"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "DIVXTAG";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="DivXTag" /> with no contents.
	*/
	public DivXTag()
	{
		Clear();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="DivXTag" /> by reading the contents from a
		specified position in a specified file.
	 
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
		The file does not contain the file identifier at the
		correct offset from the given position.
	 
	*/
	public DivXTag(File file, long position)
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

		if (!data.EndsWith(FileIdentifier))
		{
			throw new CorruptFileException("DivX tag data does not end with identifier.");
		}

		Parse(data);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="DivXTag" /> by reading the contents raw tag data
		stored in a specified <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector"/> containing a raw DivX tag to
		read into the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		The file does not contain the file identifier at the
		correct offset from the given position.
	 
	*/
	public DivXTag(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (data.size() < Size)
		{
			throw new CorruptFileException("DivX tag data is less than 128 bytes long.");
		}

		if (!data.EndsWith(FileIdentifier))
		{
			throw new CorruptFileException("DivX tag data does not end with identifier.");
		}

		Parse(data);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance as a raw DivX tag.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered tag.
	 
	*/
	public final ByteVector Render()
	{
		ByteVector data = new ByteVector();
		data.add(ByteVector.FromString(title, StringType.Latin1).Resize(32, (byte)0x20));
		data.add(ByteVector.FromString(artist, StringType.Latin1).Resize(28, (byte)0x20));
		data.add(ByteVector.FromString(year, StringType.Latin1).Resize(4, (byte)0x20));
		data.add(ByteVector.FromString(comment, StringType.Latin1).Resize(48, (byte)0x20));
		data.add(ByteVector.FromString(genre, StringType.Latin1).Resize(3, (byte)0x20));
		data.add(extra_data);
		data.add(FileIdentifier);
		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	/** 
		Populates the current instance by parsing the contents of
		a raw DivX tag.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the
		starting with an DivX tag.
	 
	*/
	private void Parse(ByteVector data)
	{
		title = data.toString(StringType.Latin1, 0, 32).trim();
		artist = data.toString(StringType.Latin1, 32, 28).trim();
		year = data.toString(StringType.Latin1, 60, 4).trim();
		comment = data.toString(StringType.Latin1, 64, 48).trim();
		genre = data.toString(StringType.Latin1, 112, 3).trim();
		extra_data = data.Mid(115, 6);
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
		return TagTypes.DivX;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		When stored on disk, only the first 32 bytes of the
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
	 
		When stored on disk, only the first 28 bytes of the
		Latin-1 encoded value will be stored, minus a byte for
		each additionial performer (i.e. two performers will only
		have 27 bytes and three performers will only have 26
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
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		When stored on disk, only the first 48 bytes of the
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
		cref="Rasad.Core.Media.MediaMetadataManagement.Genres.Video" />. All other values will
		result in the property being cleared.
	 
	*/
	@Override
	public String[] getGenres()
	{
		String genre_name = Rasad.Core.Media.MediaMetadataManagement.Genres.IndexToVideo(genre);

		return (genre_name != null) ? new String [] {genre_name} : new String [0];
	}
	@Override
	public void setGenres(String[] value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: genre = (value != null && value.Length > 0) ? Rasad.Core.Media.MediaMetadataManagement.Genres.VideoToIndex(value [0].Trim()).ToString(CultureInfo.InvariantCulture) : string.Empty;
		genre = (value != null && value.length > 0) ? (new Byte(Rasad.Core.Media.MediaMetadataManagement.Genres.VideoToIndex(value [0].trim()))).toString(CultureInfo.InvariantCulture) : "";
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
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		title = artist = genre = year = comment = "";
		extra_data = new ByteVector(6);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}