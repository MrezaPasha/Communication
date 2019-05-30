package Rasad.Core.Media.MediaMetadataManagement.Ogg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// XiphComment.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   xiphcomment.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2003 Scott Wheeler (Original Implementation)
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
	cref="T:System.Collections.Generic.IEnumerable`1" /> to provide
	support for reading and writing Xiph comments.
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class XiphComment extends Rasad.Core.Media.MediaMetadataManagement.Tag implements java.lang.Iterable<String>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the comment fields.
	*/
	private HashMap<String,String[]> field_list = new HashMap<String,String[]> ();

	/** 
		Contains the ventor ID.
	*/
	private String vendor_id;

	/** 
		Contains the field identifier to use for <see
		cref="Comment" />.
	*/
	private String comment_field = "DESCRIPTION";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="XiphComment" /> with no contents.
	*/
	public XiphComment()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="XiphComment" /> by reading the contents of a raw
		Xiph Comment from a <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing a raw Xiph
		comment.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
	public XiphComment(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		Parse(data);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Gets the field data for a given field identifier.
	 
	 @param key
		A <see cref="string"/> object containing the field
		identifier.
	 
	 @return 
		A <see cref="string[]"/> containing the field data or an
		empty array if the field was not found.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
	public final String[] GetField(String key)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		key = key.toUpperCase(Locale.ROOT);

		if (!field_list.containsKey(key))
		{
			return new String [0];
		}

		return (String []) field_list.get(key).clone();
	}

	/** 
		Gets the first field for a given field identifier.
	 
	 @param key
		A <see cref="string"/> object containing the field
		identifier.
	 
	 @return 
		A <see cref="string"/> containing the field data or <see
		langword="null" /> if the field was not found.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
	public final String GetFirstField(String key)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		String [] values = GetField(key);
		return (values.length > 0) ? values [0] : null;
	}

	/** 
		Sets the contents of a specified field to a number.
	 
	 @param key
		A <see cref="string"/> object containing the field
		identifier.
	 
	 @param number
		A <see cref="uint" /> value to set the field to.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetField(string key, uint number)
	public final void SetField(String key, int number)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (number == 0)
		{
			RemoveField(key);
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetField(key, number.ToString(CultureInfo.InvariantCulture));
			SetField(key, (new Integer(number)).toString(CultureInfo.InvariantCulture));
		}
	}

	/** 
		Sets the contents of a specified field to the contents of
		a <see cref="string[]" />.
	 
	 @param key
		A <see cref="string"/> object containing the field
		identifier.
	 
	 @param values
		A <see cref="string[]"/> containing the values to store
		in the current instance.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
	public final void SetField(String key, String... values)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		key = key.toUpperCase(Locale.ROOT);

		if (values == null || values.length == 0)
		{
			RemoveField(key);
			return;
		}

		ArrayList <String> result = new ArrayList<String> ();
		for (String text : values)
		{
			if (text != null && text.trim().length() != 0)
			{
				result.add(text);
			}
		}

		if (result.isEmpty())
		{
			RemoveField(key);
		}
		else if (field_list.containsKey(key))
		{
			field_list.put(key, result.toArray(new String[0]));
		}
		else
		{
			field_list.put(key, result.toArray(new String[0]));
		}
	}

	/** 
		Removes a field and all its values from the current
		instance.
	 
	 @param key
		A <see cref="string"/> object containing the field
		identifier.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
	public final void RemoveField(String key)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		key = key.toUpperCase(Locale.ROOT);

		field_list.remove(key);
	}

	/** 
		Renders the current instance as a raw Xiph comment,
		optionally adding a framing bit.
	 
	 @param addFramingBit
		If <see langword="true" />, a framing bit will be added to
		the end of the content.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the rendered
		version of the current instance.
	 
	*/
	public final ByteVector Render(boolean addFramingBit)
	{
		ByteVector data = new ByteVector();

		// Add the vendor ID length and the vendor ID.  It's
		// important to use the length of the data(String::UTF8)
		// rather than the lenght of the the string since this
		// is UTF8 text and there may be more characters in the
		// data than in the UTF16 string.

		ByteVector vendor_data = ByteVector.FromString(vendor_id, StringType.UTF8);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) vendor_data.Count, false));
		data.add(ByteVector.FromUInt((int) vendor_data.size(), false));
		data.add(vendor_data);

		// Add the number of fields.

		data.add(ByteVector.FromUInt(getFieldCount(), false));

		for (Map.Entry<String,String[]> entry : field_list.entrySet())
		{
			// And now iterate over the values of the
			// current list.

			for (String value : entry.getValue())
			{
				ByteVector field_data = ByteVector.FromString(entry.getKey(), StringType.UTF8);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: field_data.Add((byte) '=');
				field_data.add((byte) '=');
				field_data.add(ByteVector.FromString(value, StringType.UTF8));

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) field_data.Count, false));
				data.add(ByteVector.FromUInt((int) field_data.size(), false));
				data.add(field_data);
			}
		}

		// Append the "framing bit".
		if (addFramingBit)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add((byte) 1);
			data.add((byte) 1);
		}

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets the number of fields contained in the current
		instance.
	 
	 <value>
		A <see cref="uint" /> value containing the number of
		fields in the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getFieldCount()
	public final int getFieldCount()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint count = 0;
		int count = 0;
		for (String [] values : field_list.values())
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: count += (uint) values.Length;
			count += (int) values.length;
		}

		return count;
	}

	/** 
		Gets the vendor ID for the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the vendor ID
		for current instance.
	 </value>
	*/
	public final String getVendorId()
	{
		return vendor_id;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Methods

	/** 
		Populates and initializes a new instance of <see
		cref="XiphComment" /> by reading the contents of a raw
		Xiph Comment from a <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing a raw Xiph
		comment.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
	protected final void Parse(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		// The first thing in the comment data is the vendor ID
		// length, followed by a UTF8 string with the vendor ID.
		int pos = 0;
		int vendor_length = (int) data.Mid(pos, 4).ToUInt(false);
		pos += 4;

		vendor_id = data.toString(StringType.UTF8, pos, vendor_length);
		pos += vendor_length;

		// Next the number of fields in the comment vector.

		int comment_fields = (int) data.Mid(pos, 4).ToUInt(false);
		pos += 4;

		for (int i = 0; i < comment_fields; i++)
		{
			// Each comment field is in the format
			// "KEY=value" in a UTF8 string and has 4 bytes
			// before the text starts that gives the length.

			int comment_length = (int) data.Mid(pos, 4).ToUInt(false);
			pos += 4;

			String comment = data.toString(StringType.UTF8, pos, comment_length);
			pos += comment_length;

			int comment_separator_position = comment.indexOf('=');

			if (comment_separator_position < 0)
			{
				continue;
			}

			String key = comment.substring(0, comment_separator_position).toUpperCase(Locale.ROOT);
			String value = comment.substring(comment_separator_position + 1);
			String [] values;

			if (field_list.containsKey(key) ? (values = field_list.get(key)) == values : false)
			{
				tangible.RefObject<String> tempRef_values = new tangible.RefObject<String>(values);
				Array.<String>Resize  (tempRef_values, values.length + 1);
			values = tempRef_values.argValue;
				values [values.length - 1] = value;
				field_list.put(key, values);
			}
			else
			{
				SetField(key, value);
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IEnumerable

	/** 
		Gets an enumerator for enumerating through the the field
		identifiers.
	 
	 @return 
		A <see cref="T:System.Collections.IEnumerator`1" /> for
		enumerating through the field identifiers.
	 
	*/
	public final Iterator<String> iterator()
	{
		return field_list.keySet().iterator();
	}

	public final Iterator GetEnumerator()
	{
		return field_list.keySet().iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.Xiph" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.Xiph;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "TITLE" field.
	 
	*/
	@Override
	public String getTitle()
	{
		return GetFirstField("TITLE");
	}
	@Override
	public void setTitle(String value)
	{
		SetField("TITLE", value);
	}

	/** 
		Gets and sets the sort names for the Track Title of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort name of 
		the Track Title of the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "TITLESORT"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getTitleSort()
	{
		return GetFirstField("TITLESORT");
	}
	@Override
	public void setTitleSort(String value)
	{
		SetField("TITLESORT", value);
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
	 
		This property is implemented using the "ARTIST" field.
	 
	*/
	@Override
	public String[] getPerformers()
	{
		return GetField("ARTIST");
	}
	@Override
	public void setPerformers(String[] value)
	{
		SetField("ARTIST", value);
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
	 
		This property is implemented using the "ARTISTSORT" field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String[] getPerformersSort()
	{
		return GetField("ARTISTSORT");
	}
	@Override
	public void setPerformersSort(String[] value)
	{
		SetField("ARTISTSORT", value);
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
	 
		This property is implemented using the "ALBUMARTIST"
		field.
	 
	*/
	@Override
	public String[] getAlbumArtists()
	{
			// First try to get AlbumArtist, if that comment is not present try: 
			// ENSEMBLE: set by TAG & RENAME
			// ALBUM ARTIST: set by The GodFather
		String[] value = GetField("ALBUMARTIST");
		if (value != null && value.length > 0)
		{
		  return value;
		}

		value = GetField("ALBUM ARTIST");
		if (value != null && value.length > 0)
		{
		  return value;
		}

		return GetField("ENSEMBLE");
	}
	@Override
	public void setAlbumArtists(String[] value)
	{
		SetField("ALBUMARTIST", value);
	}

	/** 
		Gets and sets the sort names for the band or artist who
		is credited in the creation of the entire album or
		collection containing the media described by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names
		for the band or artist who is credited in the creation
		of the entire album or collection containing the media
		described by the current instance or an empty array if
		no value is present.
	 </value>
	 
		This property is implemented using the "ALBUMARTISTSORT"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String[] getAlbumArtistsSort()
	{
		return GetField("ALBUMARTISTSORT");
	}
	@Override
	public void setAlbumArtistsSort(String[] value)
	{
		SetField("ALBUMARTISTSORT", value);
	}

	/** 
		Gets and sets the composers of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the composers of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "COMPOSER" field.
	 
	*/
	@Override
	public String[] getComposers()
	{
		return GetField("COMPOSER");
	}
	@Override
	public void setComposers(String[] value)
	{
		SetField("COMPOSER", value);
	}

	/** 
		Gets and sets the sort names for the composers of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names
		for the composer of the media described by the current
		instance or an empty array if no value is present.
	 </value>
	 
		This property is implemented using the "COMPOSERSORT"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String[] getComposersSort()
	{
		return GetField("COMPOSERSORT");
	}
	@Override
	public void setComposersSort(String[] value)
	{
		SetField("COMPOSERSORT", value);
	}

	/** 
		Gets and sets the album of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the album of
		the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "ALBUM" field.
	 
	*/
	@Override
	public String getAlbum()
	{
		return GetFirstField("ALBUM");
	}
	@Override
	public void setAlbum(String value)
	{
		SetField("ALBUM", value);
	}

	/** 
		Gets and sets the sort names for the Album Title of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort name of 
		the Album Title of the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "ALBUMSORT"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getAlbumSort()
	{
		return GetFirstField("ALBUMSORT");
	}
	@Override
	public void setAlbumSort(String value)
	{
		SetField("ALBUMSORT", value);
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "COMMENT" or
		"DESCRIPTION" field, preferring "DESCRIPTION" but using
		"COMMENT" if that is the field used by the comment.
	 
	*/
	@Override
	public String getComment()
	{
		String value = GetFirstField(comment_field);
		if (value != null || comment_field.equals("COMMENT"))
		{
			return value;
		}

		comment_field = "COMMENT";
		return GetFirstField(comment_field);
	}
	@Override
	public void setComment(String value)
	{
		SetField(comment_field, value);
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "GENRE" field.
	 
	*/
	@Override
	public String[] getGenres()
	{
		return GetField("GENRE");
	}
	@Override
	public void setGenres(String[] value)
	{
		SetField("GENRE", value);
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		This property is implemented using the "DATE" field. If a
		value greater than 9999 is set, this property will be
		cleared.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
		String text = GetFirstField("DATE");
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;
		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (text != null && uint.TryParse(text.Length > 4 ? text.Substring(0, 4) : text, out value)) ? value : 0;
		int tempVar = (text != null && tangible.TryParseHelper.tryParseInt(text.length() > 4 ? text.substring(0, 4) : text, tempOut_value)) ? value : 0;
	value = tempOut_value.argValue;
	return tempVar;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setYear(uint value)
	@Override
	public void setYear(int value)
	{
		SetField("DATE", value);
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	 
		This property is implemented using the "TRACKNUMER"
		field.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		String text = GetFirstField("TRACKNUMBER");
		String [] values;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;

		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (text != null && (values = text.Split('/')).Length > 0 && uint.TryParse(values [0], out value))
		if (text != null && (values = text.split("[/]", -1)).length > 0 && tangible.TryParseHelper.tryParseInt(values [0], tempOut_value))
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
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
		SetField("TRACKTOTAL", getTrackCount());
		SetField("TRACKNUMBER", value);
	}

	/** 
		Gets and sets the number of tracks in the album
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of tracks in
		the album containing the media represented by the current
		instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "TRACKTOTAL" field
		but is capable of reading from "TRACKNUMBER" if the total
		is stored in {track}/{count} format.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		String text;
		String [] values;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;

		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((text = GetFirstField("TRACKTOTAL")) != null && uint.TryParse(text, out value))
		if ((text = GetFirstField("TRACKTOTAL")) != null && tangible.TryParseHelper.tryParseInt(text, tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}

		tangible.OutObject<Integer> tempOut_value2 = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((text = GetFirstField("TRACKNUMBER")) != null && (values = text.Split('/')).Length > 1 && uint.TryParse(values [1], out value))
		if ((text = GetFirstField("TRACKNUMBER")) != null && (values = text.split("[/]", -1)).length > 1 && tangible.TryParseHelper.tryParseInt(values [1], tempOut_value2))
		{
		value = tempOut_value2.argValue;
			return value;
		}
	else
	{
		value = tempOut_value2.argValue;
	}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrackCount(uint value)
	@Override
	public void setTrackCount(int value)
	{
		SetField("TRACKTOTAL", value);
	}

	/** 
		Gets and sets the number of the disc containing the media
		represented by the current instance in the boxed set.
	 
	 <value>
		A <see cref="uint" /> containing the number of the disc
		containing the media represented by the current instance
		in the boxed set.
	 </value>
	 
		This property is implemented using the "DISCNUMBER"
		field.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDisc()
	@Override
	public int getDisc()
	{
		String text = GetFirstField("DISCNUMBER");
		String [] values;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;

		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (text != null && (values = text.Split('/')).Length > 0 && uint.TryParse(values [0], out value))
		if (text != null && (values = text.split("[/]", -1)).length > 0 && tangible.TryParseHelper.tryParseInt(values [0], tempOut_value))
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
//ORIGINAL LINE: public override void setDisc(uint value)
	@Override
	public void setDisc(int value)
	{
		SetField("DISCTOTAL", getDiscCount());
		SetField("DISCNUMBER", value);
	}

	/** 
		Gets and sets the number of discs in the boxed set
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of discs in
		the boxed set containing the media represented by the
		current instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "DISCTOTAL" field
		but is capable of reading from "DISCNUMBER" if the total
		is stored in {disc}/{count} format.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDiscCount()
	@Override
	public int getDiscCount()
	{
		String text;
		String [] values;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;

		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((text = GetFirstField("DISCTOTAL")) != null && uint.TryParse(text, out value))
		if ((text = GetFirstField("DISCTOTAL")) != null && tangible.TryParseHelper.tryParseInt(text, tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}

		tangible.OutObject<Integer> tempOut_value2 = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((text = GetFirstField("DISCNUMBER")) != null && (values = text.Split('/')).Length > 1 && uint.TryParse(values [1], out value))
		if ((text = GetFirstField("DISCNUMBER")) != null && (values = text.split("[/]", -1)).length > 1 && tangible.TryParseHelper.tryParseInt(values [1], tempOut_value2))
		{
		value = tempOut_value2.argValue;
			return value;
		}
	else
	{
		value = tempOut_value2.argValue;
	}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDiscCount(uint value)
	@Override
	public void setDiscCount(int value)
	{
		SetField("DISCTOTAL", value);
	}

	/** 
		Gets and sets the lyrics or script of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the lyrics or
		script of the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "LYRICS" field.
	 
	*/
	@Override
	public String getLyrics()
	{
		return GetFirstField("LYRICS");
	}
	@Override
	public void setLyrics(String value)
	{
		SetField("LYRICS", value);
	}

	/** 
		Gets and sets the grouping on the album which the media
		in the current instance belongs to.
	 
	 <value>
		A <see cref="string" /> object containing the grouping on
		the album which the media in the current instance belongs
		to or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "GROUPING" field.
	 
	*/
	@Override
	public String getGrouping()
	{
		return GetFirstField("GROUPING");
	}
	@Override
	public void setGrouping(String value)
	{
		SetField("GROUPING", value);
	}

	/** 
		Gets and sets the number of beats per minute in the audio
		of the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of beats per
		minute in the audio of the media represented by the
		current instance, or zero if not specified.
	 </value>
	 
		This property is implemented using the "TEMPO" field.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getBeatsPerMinute()
	@Override
	public int getBeatsPerMinute()
	{
		String text = GetFirstField("TEMPO");
		double value;
		tangible.OutObject<Double> tempOut_value = new tangible.OutObject<Double>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (text != null && double.TryParse(text, out value) && value > 0) ? (uint) Math.Round(value) : 0;
		int tempVar = (text != null && tangible.TryParseHelper.tryParseDouble(text, tempOut_value) && value > 0) ? (int) Math.rint(value) : 0;
	value = tempOut_value.argValue;
	return tempVar;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setBeatsPerMinute(uint value)
	@Override
	public void setBeatsPerMinute(int value)
	{
		SetField("TEMPO", value);
	}

	/** 
		Gets and sets the conductor or director of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the conductor
		or director of the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "CONDUCTOR" field.
	 
	*/
	@Override
	public String getConductor()
	{
		return GetFirstField("CONDUCTOR");
	}
	@Override
	public void setConductor(String value)
	{
		SetField("CONDUCTOR", value);
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "COPYRIGHT" field.
	 
	*/
	@Override
	public String getCopyright()
	{
		return GetFirstField("COPYRIGHT");
	}
	@Override
	public void setCopyright(String value)
	{
		SetField("COPYRIGHT", value);
	}

	/** 
		Gets and sets the MusicBrainz Artist ID for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ArtistID for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ARTISTID" field.
	 
	*/
	@Override
	public String getMusicBrainzArtistId()
	{
		return GetFirstField("MUSICBRAINZ_ARTISTID");
	}
	@Override
	public void setMusicBrainzArtistId(String value)
	{
		SetField("MUSICBRAINZ_ARTISTID", value);
	}

	/** 
		Gets and sets the MusicBrainz Release ID for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseID for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ALBUMID" field.
	 
	*/
	@Override
	public String getMusicBrainzReleaseId()
	{
		return GetFirstField("MUSICBRAINZ_ALBUMID");
	}
	@Override
	public void setMusicBrainzReleaseId(String value)
	{
		SetField("MUSICBRAINZ_ALBUMID", value);
	}

	/** 
		Gets and sets the MusicBrainz Release Artist ID for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseArtistID for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ALBUMARTISTID" field.
	 
	*/
	@Override
	public String getMusicBrainzReleaseArtistId()
	{
		return GetFirstField("MUSICBRAINZ_ALBUMARTISTID");
	}
	@Override
	public void setMusicBrainzReleaseArtistId(String value)
	{
		SetField("MUSICBRAINZ_ALBUMARTISTID", value);
	}

	/** 
		Gets and sets the MusicBrainz Track ID for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		TrackID for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_TRACKID" field.
	 
	*/
	@Override
	public String getMusicBrainzTrackId()
	{
		return GetFirstField("MUSICBRAINZ_TRACKID");
	}
	@Override
	public void setMusicBrainzTrackId(String value)
	{
		SetField("MUSICBRAINZ_TRACKID", value);
	}

	/** 
		Gets and sets the MusicBrainz Disc ID for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		DiscID for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_DISCID" field.
	 
	*/
	@Override
	public String getMusicBrainzDiscId()
	{
		return GetFirstField("MUSICBRAINZ_DISCID");
	}
	@Override
	public void setMusicBrainzDiscId(String value)
	{
		SetField("MUSICBRAINZ_DISCID", value);
	}

	/** 
		Gets and sets the MusicIP PUID for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicIP PUID
		for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "MUSICIP_PUID" field.
	 
	*/
	@Override
	public String getMusicIpId()
	{
		return GetFirstField("MUSICIP_PUID");
	}
	@Override
	public void setMusicIpId(String value)
	{
		SetField("MUSICIP_PUID", value);
	}

	/** 
		Gets and sets the Amazon ID for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the AmazonID
		for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "ASIN" field.
	 
	*/
	@Override
	public String getAmazonId()
	{
		return GetFirstField("ASIN");
	}
	@Override
	public void setAmazonId(String value)
	{
		SetField("ASIN", value);
	}

	/** 
		Gets and sets the MusicBrainz Release Status for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseStatus for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ALBUMSTATUS" field.
	 
	*/
	@Override
	public String getMusicBrainzReleaseStatus()
	{
		return GetFirstField("MUSICBRAINZ_ALBUMSTATUS");
	}
	@Override
	public void setMusicBrainzReleaseStatus(String value)
	{
		SetField("MUSICBRAINZ_ALBUMSTATUS", value);
	}

	/** 
		Gets and sets the MusicBrainz Release Type for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseType for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ALBUMTYPE" field.
	 
	*/
	@Override
	public String getMusicBrainzReleaseType()
	{
		return GetFirstField("MUSICBRAINZ_ALBUMTYPE");
	}
	@Override
	public void setMusicBrainzReleaseType(String value)
	{
		SetField("MUSICBRAINZ_ALBUMTYPE", value);
	}

	/** 
		Gets and sets the MusicBrainz Release Country for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseCountry for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "RELEASECOUNTRY" field.
	 
	*/
	@Override
	public String getMusicBrainzReleaseCountry()
	{
		return GetFirstField("RELEASECOUNTRY");
	}
	@Override
	public void setMusicBrainzReleaseCountry(String value)
	{
		SetField("RELEASECOUNTRY", value);
	}

	/** 
		Gets and sets a collection of pictures associated with
		the media represented by the current instance.
	 
	 <value>
		A <see cref="IPicture[]" /> containing a collection of
		pictures associated with the media represented by the
		current instance or an empty array if none are present.
	 </value>
	 
		<p>This property is implemented using the COVERART
		field.</p>
	 
	*/
	@Override
	public IPicture[] getPictures()
	{
		String[] covers = GetField("COVERART");
		IPicture[] pictures = new Picture[covers.length];
		for (int ii = 0; ii < covers.length; ii++)
		{
			ByteVector data = new ByteVector(Convert.FromBase64String(covers[ii]));
			pictures[ii] = new Picture(data);
		}
		return pictures;
	}
	@Override
	public void setPictures(IPicture[] value)
	{
		String[] covers = new String[value.length];
		for (int ii = 0; ii < value.length; ii++)
		{
			IPicture old = value[ii];
			covers[ii] = Convert.ToBase64String(old.getData().getData());
		}
		SetField("COVERART", covers);
	}

	/** 
		Gets and sets whether or not the album described by the
		current instance is a compilation.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		album described by the current instance is a compilation.
	 </value>
	 
		This property is implemented using the "COMPILATION" field.
	 
	*/
	public final boolean getIsCompilation()
	{
		String text;
		int value;

		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
		if ((text = GetFirstField("COMPILATION")) != null && tangible.TryParseHelper.tryParseInt(text, tempOut_value))
		{
		value = tempOut_value.argValue;
				return value == 1;
		}
	else
	{
		value = tempOut_value.argValue;
	}
		return false;
	}
	public final void setIsCompilation(boolean value)
	{
		if (value)
		{
			SetField("COMPILATION", "1");
		}
		else
		{
			RemoveField("COMPILATION");
		}
	}

	/** 
		Gets and sets the ReplayGain track gain in dB.
	 
	 <value>
		A <see cref="bool" /> value in dB for the track gain as
		per the ReplayGain specification.
	 </value>
	 
		This property is implemented using the 
		"REPLAYGAIN_TRACK_GAIN" field. Set the value to double.NaN
		to clear the field.
	 
	*/
	@Override
	public double getReplayGainTrackGain()
	{
		String text = GetFirstField("REPLAYGAIN_TRACK_GAIN");
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
			RemoveField("REPLAYGAIN_TRACK_GAIN");
		}
		else
		{
			String text = (new Double(value)).toString("0.00 dB", CultureInfo.InvariantCulture);
			SetField("REPLAYGAIN_TRACK_GAIN", text);
		}
	}

	/** 
		Gets and sets the ReplayGain track peak sample.
	 
	 <value>
		A <see cref="bool" /> value for the track peak as per the
		ReplayGain specification.
	 </value>
	 
		This property is implemented using the 
		"REPLAYGAIN_TRACK_PEAK" field. Set the value to double.NaN
		to clear the field.
	 
	*/
	@Override
	public double getReplayGainTrackPeak()
	{
		String text;
		double value;

		tangible.OutObject<Double> tempOut_value = new tangible.OutObject<Double>();
		if ((text = GetFirstField("REPLAYGAIN_TRACK_PEAK")) != null && tangible.TryParseHelper.tryParseDouble(text, NumberStyles.Float, CultureInfo.InvariantCulture, tempOut_value))
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
			RemoveField("REPLAYGAIN_TRACK_PEAK");
		}
		else
		{
			String text = (new Double(value)).toString("0.000000", CultureInfo.InvariantCulture);
			SetField("REPLAYGAIN_TRACK_PEAK", text);
		}
	}

	/** 
		Gets and sets the ReplayGain album gain in dB.
	 
	 <value>
		A <see cref="bool" /> value in dB for the album gain as
		per the ReplayGain specification.
	 </value>
	 
		This property is implemented using the 
		"REPLAYGAIN_ALBUM_GAIN" field. Set the value to double.NaN
		to clear the field.
	 
	*/
	@Override
	public double getReplayGainAlbumGain()
	{
		String text = GetFirstField("REPLAYGAIN_ALBUM_GAIN");
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
			RemoveField("REPLAYGAIN_ALBUM_GAIN");
		}
		else
		{
			String text = (new Double(value)).toString("0.00 dB", CultureInfo.InvariantCulture);
			SetField("REPLAYGAIN_ALBUM_GAIN", text);
		}
	}

	/** 
		Gets and sets the ReplayGain album peak sample.
	 
	 <value>
		A <see cref="bool" /> value for the album peak as per the
		ReplayGain specification.
	 </value>
	 
		This property is implemented using the 
		"REPLAYGAIN_ALBUM_PEAK" field. Set the value to double.NaN
		to clear the field.
	 
	*/
	@Override
	public double getReplayGainAlbumPeak()
	{
		String text;
		double value;

		tangible.OutObject<Double> tempOut_value = new tangible.OutObject<Double>();
		if ((text = GetFirstField("REPLAYGAIN_ALBUM_PEAK")) != null && tangible.TryParseHelper.tryParseDouble(text, NumberStyles.Float, CultureInfo.InvariantCulture, tempOut_value))
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
			RemoveField("REPLAYGAIN_ALBUM_PEAK");
		}
		else
		{
			String text = (new Double(value)).toString("0.000000", CultureInfo.InvariantCulture);
			SetField("REPLAYGAIN_ALBUM_PEAK", text);
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
		for (String [] values : field_list.values())
		{
			if (values.length != 0)
			{
				return false;
			}
		}

		return true;
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		field_list.clear();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}