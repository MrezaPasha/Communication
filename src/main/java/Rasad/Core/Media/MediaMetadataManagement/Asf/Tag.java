package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Tag.cs: Provides a representation of an ASF tag which can be read from and
// written to disk.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2005-2007 Brian Nickel
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> to provide a
	representation of an ASF tag which can be read from and written
	to disk.
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class Tag extends Rasad.Core.Media.MediaMetadataManagement.Tag implements java.lang.Iterable<ContentDescriptor>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the content description object.
	*/
	private ContentDescriptionObject description = new ContentDescriptionObject();

	/** 
		Contains the extended content description object.
	*/
	private ExtendedContentDescriptionObject ext_description = new ExtendedContentDescriptionObject();

	/** 
		Contains the metadata library object.
	*/
	private MetadataLibraryObject metadata_library = new MetadataLibraryObject();

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
		cref="Tag" /> using the children of a <see
		cref="HeaderObject" /> object.
	 
	 @param header
		A <see cref="HeaderObject" /> object whose children are
		are to be used by the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="header" /> is <see langword="null" />.
	 
	*/
	public Tag(HeaderObject header)
	{
		if (header == null)
		{
			throw new NullPointerException("header");
		}

		for (Object child : header.getChildren())
		{
			if (child instanceof getContentDescriptionObject())
			{
				description = child instanceof ContentDescriptionObject ? (ContentDescriptionObject)child : null;
			}

			if (child instanceof getExtendedContentDescriptionObject())
			{
				ext_description = child instanceof ExtendedContentDescriptionObject ? (ExtendedContentDescriptionObject)child : null;
			}
		}

		for (Object child : header.getExtension().getChildren())
		{
			if (child instanceof getMetadataLibraryObject())
			{
				metadata_library = child instanceof MetadataLibraryObject ? (MetadataLibraryObject)child : null;
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the ASF Content Description object used by the
		current instance.
	 
	 <value>
		A <see cref="ContentDescriptionObject" /> object
		containing the ASF Content Description object used by the
		current instance.
	 </value>
	*/
	public final ContentDescriptionObject getContentDescriptionObject()
	{
		return description;
	}

	/** 
		Gets the ASF Extended Content Description object used by
		the current instance.
	 
	 <value>
		A <see cref="ExtendedContentDescriptionObject" /> object
		containing the ASF Extended Content Description object
		used by the current instance.
	 </value>
	*/
	public final ExtendedContentDescriptionObject getExtendedContentDescriptionObject()
	{
		return ext_description;
	}

	/** 
		Gets the ASF Metadata Library object used by the current
		instance.
	 
	 <value>
		A <see cref="MetadataLibraryObject" /> object containing
		the ASF Metadata Library object used by the current
		instance.
	 </value>
	*/
	public final MetadataLibraryObject getMetadataLibraryObject()
	{
		return metadata_library;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Gets the string contained in a specific descriptor in the
		current instance.
	 
	 @param names
		A <see cref="string[]" /> containing the names of the
		descriptors to look for the value in.
	 
	 @exception ArgumentNullException
		<paramref name="names" /> is <see langword="null" />.
	 
	 @return 
		A <see cref="string" /> object containing the contents of
		the first descriptor found in the current instance.
	 
	*/
	public final String GetDescriptorString(String... names)
	{
		if (names == null)
		{
			throw new NullPointerException("names");
		}

		for (ContentDescriptor desc : GetDescriptors(names))
		{
			if (desc == null || desc.getType() != DataType.Unicode)
			{
				continue;
			}

			String value = desc.toString();
			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	/** 
		Gets the strings contained in a specific descriptor in
		the current instance.
	 
	 @param names
		A <see cref="string[]" /> containing the names of the
		descriptors to look for the value in.
	 
	 @exception ArgumentNullException
		<paramref name="names" /> is <see langword="null" />.
	 
	 @return 
		A <see cref="string" /> object containing the contents of
		the first descriptor found in the current instance as
		split by ';'.
	 
	*/
	public final String[] GetDescriptorStrings(String... names)
	{
		if (names == null)
		{
			throw new NullPointerException("names");
		}

		return SplitAndClean(GetDescriptorString(names));
	}

	/** 
		Sets the string for a collection of descriptors in the
		current instance.
	 
	 @param value
		A <see cref="string" /> object containing the value to
		store, or <see langword="null" /> to clear the value.
	 
	 @param names
		A <see cref="string[]" /> containing the names in which
		the value would be expected. For example, "WM/AlbumTitle"
		and "Album".
	 
	 @exception ArgumentNullException
		<paramref name="names" /> is <see langword="null" />.
	 
	 
		The value will be stored in the first value in <paramref
		name="names" /> and the rest will be cleared.
	 
	*/
	public final void SetDescriptorString(String value, String... names)
	{
		if (names == null)
		{
			throw new NullPointerException("names");
		}

		int index = 0;

		if (value != null && value.trim().length() != 0)
		{
			SetDescriptors(names [0], new ContentDescriptor(names [0], value));

			index++;
		}

		for (; index < names.length; index++)
		{
			RemoveDescriptors(names [index]);
		}
	}

	/** 
		Sets the strings for a collection of descriptors in the
		current instance.
	 
	 @param value
		A <see cref="string[]" /> containing the value to store,
		or <see langword="null" /> to clear the value.
	 
	 @param names
		A <see cref="string[]" /> containing the names in which
		the value would be expected. For example, "WM/AlbumTitle"
		and "Album".
	 
	 @exception ArgumentNullException
		<paramref name="names" /> is <see langword="null" />.
	 
	 
		The value will be stored in the first value in <paramref
		name="names" /> and the rest will be cleared.
	 
	*/
	public final void SetDescriptorStrings(String[] value, String... names)
	{
		if (names == null)
		{
			throw new NullPointerException("names");
		}

		SetDescriptorString(tangible.StringHelper.join("; ", value), names);
	}

	/** 
		Removes all descriptors with a specified name from the
		current instance.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		descriptor to remove from the current instance.
	 
	 @exception ArgumentNullException
		<paramref name="name" /> is <see langword="null" />.
	 
	*/
	public final void RemoveDescriptors(String name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		ext_description.RemoveDescriptors(name);
	}

	/** 
		Gets all descriptors with any of a collection of names
		from the current instance.
	 
	 @param names
		A <see cref="string[]" /> containing the names of the
		descriptors to be retrieved.
	 
	 @exception ArgumentNullException
		<paramref name="names" /> is <see langword="null" />.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating
		through the <see cref="ContentDescriptor" /> objects
		retrieved from the current instance.
	 
	*/
	public final java.lang.Iterable<ContentDescriptor> GetDescriptors(String... names)
	{
		if (names == null)
		{
			throw new NullPointerException("names");
		}

		return ext_description.GetDescriptors(names);
	}

	/** 
		Sets the a collection of desciptors for a given name,
		removing the existing matching records.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		descriptors to be added.
	 
	 @param descriptors
		A <see cref="ContentDescriptor[]" /> containing
		descriptors to add to the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="name" /> is <see langword="null" />.
	 
	 
		All added entries in <paramref name="descriptors" />
		should match <paramref name="name" /> but it is not
		verified by the method. The descriptors will be added
		with their own names and not the one provided in this
		method, which are used for removing existing values and
		determining where to position the new objects.
	 
	*/
	public final void SetDescriptors(String name, ContentDescriptor... descriptors)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		ext_description.SetDescriptors(name, descriptors);
	}

	/** 
		Adds a descriptor to the current instance.
	 
	 @param descriptor
		A <see cref="ContentDescriptor" /> object to add to the
		current instance.
	 
	 @exception ArgumentNullException
		<paramref name="descriptor" /> is <see langword="null"
		/>.
	 
	*/
	public final void AddDescriptor(ContentDescriptor descriptor)
	{
		if (descriptor == null)
		{
			throw new NullPointerException("descriptor");
		}

		ext_description.AddDescriptor(descriptor);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Static Methods

	/** 
		Converts a raw ASF picture into an <see cref="IPicture"
		/> object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing raw ASF
		picture data.
	 
	 @return 
		A <see cref="IPicture" /> object to read from the raw
		data.
	 
	*/
	private static IPicture PictureFromData(ByteVector data)
	{
		if (data.size() < 9)
		{
			return null;
		}

		int offset = 0;
		Picture p = new Picture();

		// Get the picture type:

		p.setType(PictureType.forValue(data.get(offset)));
		offset += 1;

		// Get the picture size:

		int size = (int) data.Mid(offset, 4).ToUInt(false);
		offset += 4;

		// Get the mime-type:

		int found = data.Find(ByteVector.TextDelimiter(StringType.UTF16LE), offset, 2);
		if (found < 0)
		{
			return null;
		}

		p.setMimeType(data.toString(StringType.UTF16LE, offset, found - offset));
		offset = found + 2;

		// Get the description:

		found = data.Find(ByteVector.TextDelimiter(StringType.UTF16LE), offset, 2);
		if (found < 0)
		{
			return null;
		}

		p.setDescription(data.toString(StringType.UTF16LE, offset, found - offset));
		offset = found + 2;

		p.setData(data.Mid(offset, size));

		return p;
	}

	/** 
		Converts a <see cref="IPicture" /> object into raw ASF
		picture data.
	 
	 @param picture
		A <see cref="IPicture" /> object to convert.
	 
	 @return 
		A <see cref="ByteVector" /> object containing raw ASF
		picture data.
	 
	*/
	private static ByteVector PictureToData(IPicture picture)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector v = new ByteVector((byte) picture.Type);
		ByteVector v = new ByteVector((byte) picture.getType().getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(Object.RenderDWord((uint) picture.Data.Count));
		v.add(Object.RenderDWord((int) picture.getData().size()));
		v.add(Object.RenderUnicode(picture.getMimeType()));
		v.add(Object.RenderUnicode(picture.getDescription()));
		v.add(picture.getData());
		return v;
	}

	/** 
		Splits a string into a collection of strings by ';'.
	 
	 @param s
		A <see cref="string" /> object containing the text to
		split.
	 
	 @return 
		A <see cref="string[]" /> containing the split text.
	 
	*/
	private static String[] SplitAndClean(String s)
	{
		if (s == null || s.trim().length() == 0)
		{
			return new String [0];
		}

		String [] result = s.split("[;]", -1);

		for (int i = 0; i < result.length; i++)
		{
			result [i] = result [i].trim();
		}

		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IEnumerable

	/** 
		Gets an enumerator for enumerating through the content
		descriptors.
	 
	 @return 
		A <see cref="T:System.Collections.IEnumerator`1" /> for
		enumerating through the content descriptors.
	 
	*/
	public final Iterator<ContentDescriptor> iterator()
	{
		return ext_description.iterator();
	}

	public final Iterator GetEnumerator()
	{
		return ext_description.iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.Asf" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.Asf;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the title stored in
		the ASF Content Description Object.
	 
	*/
	@Override
	public String getTitle()
	{
		return description.getTitle();
	}
	@Override
	public void setTitle(String value)
	{
		description.setTitle(value);
	}

	/** 
		Gets and sets the sort names for the Track Title of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort name of 
		the Track Title of the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "WM/TitleSortOrder"
		field.
		http://msdn.microsoft.com/en-us/library/aa386866(VS.85).aspx
	 
	*/
	@Override
	public String getTitleSort()
	{
		return GetDescriptorString("WM/TitleSortOrder");
	}
	@Override
	public void setTitleSort(String value)
	{
		SetDescriptorString(value, "WM/TitleSortOrder");
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
	 
		This property is implemented using the author stored in
		the ASF Content Description Object.
	 
	*/
	@Override
	public String[] getPerformers()
	{
		return SplitAndClean(description.getAuthor());
	}
	@Override
	public void setPerformers(String[] value)
	{
		description.setAuthor(tangible.StringHelper.join("; ", value));
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
	 
		This property is implemented using the "WM/ArtistSortOrder" field.
		http://msdn.microsoft.com/en-us/library/aa386866(VS.85).aspx
	 
	*/
	@Override
	public String[] getPerformersSort()
	{
		return GetDescriptorStrings("WM/ArtistSortOrder");
	}
	@Override
	public void setPerformersSort(String[] value)
	{
		SetDescriptorStrings(value, "WM/ArtistSortOrder");
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
	 
		This property is implemented using the "WM/AlbumArtist"
		and "AlbumArtist" Content Descriptors.
	 
	*/
	@Override
	public String[] getAlbumArtists()
	{
		return GetDescriptorStrings("WM/AlbumArtist", "AlbumArtist");
	}
	@Override
	public void setAlbumArtists(String[] value)
	{
		SetDescriptorStrings(value, "WM/AlbumArtist", "AlbumArtist");
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
	 
		This property is implemented using the "WM/AlbumArtistSortOrder"
		field.
		http://msdn.microsoft.com/en-us/library/aa386866(VS.85).aspx
	 
	*/
	@Override
	public String[] getAlbumArtistsSort()
	{
		return GetDescriptorStrings("WM/AlbumArtistSortOrder");
	}
	@Override
	public void setAlbumArtistsSort(String[] value)
	{
		SetDescriptorStrings(value, "WM/AlbumArtistSortOrder");
	}

	/** 
		Gets and sets the composers of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the composers of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "WM/Composer"
		and "Composer" Content Descriptors.
	 
	*/
	@Override
	public String[] getComposers()
	{
		return GetDescriptorStrings("WM/Composer", "Composer");
	}
	@Override
	public void setComposers(String[] value)
	{
		SetDescriptorStrings(value, "WM/Composer", "Composer");
	}

	/** 
		Gets and sets the album of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the album of
		the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "WM/AlbumTitle"
		and "Album" Content Descriptors.
	 
	*/
	@Override
	public String getAlbum()
	{
		return GetDescriptorString("WM/AlbumTitle", "Album");
	}
	@Override
	public void setAlbum(String value)
	{
		SetDescriptorString(value, "WM/AlbumTitle", "Album");
	}

	/** 
		Gets and sets the sort names for the Album Title of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort name of 
		the Album Title of the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "WM/AlbumSortOrder"
		field.
		http://msdn.microsoft.com/en-us/library/aa386866(VS.85).aspx
	 
	*/
	@Override
	public String getAlbumSort()
	{
		return GetDescriptorString("WM/AlbumSortOrder");
	}
	@Override
	public void setAlbumSort(String value)
	{
		SetDescriptorString(value, "WM/AlbumSortOrder");
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the description stored
		in the ASF Content Description Object.
	 
	*/
	@Override
	public String getComment()
	{
		return description.getDescription();
	}
	@Override
	public void setComment(String value)
	{
		description.setDescription(value);
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "WM/Genre",
		"WM/GenreID", and "Genre" Content Descriptors.
	 
	*/
	@Override
	public String[] getGenres()
	{
		String value = GetDescriptorString("WM/Genre", "WM/GenreID", "Genre");

		if (value == null || value.trim().length() == 0)
		{
			return new String [] {};
		}

		String [] result = value.split("[;]", -1);

		for (int i = 0; i < result.length; i++)
		{
			String genre = result [i].trim();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte genre_id;
			byte genre_id;
			int closing = genre.indexOf(')');
			tangible.OutObject<Byte> tempOut_genre_id = new tangible.OutObject<Byte>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (closing > 0 && genre[0] == '(' && byte.TryParse(genre.Substring(1, closing - 1), out genre_id))
			if (closing > 0 && genre.charAt(0) == '(' && tangible.TryParseHelper.tryParseByte(genre.substring(1, closing), tempOut_genre_id))
			{
			genre_id = tempOut_genre_id.argValue;
				genre = Rasad.Core.Media.MediaMetadataManagement.Genres.IndexToAudio(genre_id);
			}
		else
		{
			genre_id = tempOut_genre_id.argValue;
		}

			result [i] = genre;
		}

		return result;
	}
	@Override
	public void setGenres(String[] value)
	{
		SetDescriptorString(tangible.StringHelper.join("; ", value), "WM/Genre", "Genre", "WM/GenreID");
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		This property is implemented using the "WM/Year" Content
		Descriptor.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
		String text = GetDescriptorString("WM/Year");

		if (text == null || text.length() < 4)
		{
			return 0;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;
		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (uint.TryParse(text.Substring(0, 4), NumberStyles.Integer, CultureInfo.InvariantCulture, out value))
		if (tangible.TryParseHelper.tryParseInt(text.substring(0, 4), NumberStyles.Integer, CultureInfo.InvariantCulture, tempOut_value))
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
		if (value == 0)
		{
			RemoveDescriptors("WM/Year");
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetDescriptorString(value.ToString(CultureInfo.InvariantCulture), "WM/Year");
		SetDescriptorString((new Integer(value)).toString(CultureInfo.InvariantCulture), "WM/Year");
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	 
		This property is implemented using the "WM/TrackNumber"
		Content Descriptor.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		for (ContentDescriptor desc : GetDescriptors("WM/TrackNumber"))
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = desc.ToDWord();
				int value = desc.ToDWord();
				if (value != 0)
				{
					return value;
				}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
		if (value == 0)
		{
			RemoveDescriptors("WM/TrackNumber");
		}
		else
		{
			SetDescriptors("WM/TrackNumber", new ContentDescriptor("WM/TrackNumber", value));
		}
	}

	/** 
		Gets and sets the number of tracks in the album
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of tracks in
		the album containing the media represented by the current
		instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "TrackTotal"
		Content Descriptor.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		for (ContentDescriptor desc : GetDescriptors("TrackTotal"))
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = desc.ToDWord();
				int value = desc.ToDWord();
				if (value != 0)
				{
					return value;
				}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrackCount(uint value)
	@Override
	public void setTrackCount(int value)
	{
		if (value == 0)
		{
			RemoveDescriptors("TrackTotal");
		}
		else
		{
			SetDescriptors("TrackTotal", new ContentDescriptor("TrackTotal", value));
		}
	}

	/** 
		Gets and sets the number of the disc containing the media
		represented by the current instance in the boxed set.
	 
	 <value>
		A <see cref="uint" /> containing the number of the disc
		containing the media represented by the current instance
		in the boxed set.
	 </value>
	 
		This property is implemented using the "WM/PartOfSet"
		Content Descriptor.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDisc()
	@Override
	public int getDisc()
	{
		String text = GetDescriptorString("WM/PartOfSet");

		if (text == null)
		{
			return 0;
		}

		String [] texts = text.split("[/]", -1);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;

		if (texts.length < 1)
		{
			return 0;
		}

		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return uint.TryParse(texts [0], NumberStyles.Integer, CultureInfo.InvariantCulture, out value) ? value : 0;
		int tempVar = tangible.TryParseHelper.tryParseInt(texts [0], NumberStyles.Integer, CultureInfo.InvariantCulture, tempOut_value) ? value : 0;
	value = tempOut_value.argValue;
	return tempVar;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDisc(uint value)
	@Override
	public void setDisc(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint count = DiscCount;
		int count = getDiscCount();
		if (value == 0 && count == 0)
		{
			RemoveDescriptors("WM/PartOfSet");
			return;
		}

		if (count != 0)
		{
			SetDescriptorString(String.format(CultureInfo.InvariantCulture, "%1$s/%2$s", value, count), "WM/PartOfSet");
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetDescriptorString(value.ToString(CultureInfo.InvariantCulture), "WM/PartOfSet");
		SetDescriptorString((new Integer(value)).toString(CultureInfo.InvariantCulture), "WM/PartOfSet");
	}

	/** 
		Gets and sets the number of discs in the boxed set
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of discs in
		the boxed set containing the media represented by the
		current instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "WM/PartOfSet"
		Content Descriptor.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDiscCount()
	@Override
	public int getDiscCount()
	{
		String text = GetDescriptorString("WM/PartOfSet");

		if (text == null)
		{
			return 0;
		}

		String [] texts = text.split("[/]", -1);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;

		if (texts.length < 2)
		{
			return 0;
		}

		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return uint.TryParse(texts [1], NumberStyles.Integer, CultureInfo.InvariantCulture, out value) ? value : 0;
		int tempVar = tangible.TryParseHelper.tryParseInt(texts [1], NumberStyles.Integer, CultureInfo.InvariantCulture, tempOut_value) ? value : 0;
	value = tempOut_value.argValue;
	return tempVar;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDiscCount(uint value)
	@Override
	public void setDiscCount(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint disc = Disc;
		int disc = getDisc();
		if (disc == 0 && value == 0)
		{
			RemoveDescriptors("WM/PartOfSet");
			return;
		}

		if (value != 0)
		{
			SetDescriptorString(String.format(CultureInfo.InvariantCulture, "%1$s/%2$s", disc, value), "WM/PartOfSet");
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetDescriptorString(disc.ToString(CultureInfo.InvariantCulture), "WM/PartOfSet");
		SetDescriptorString((new Integer(disc)).toString(CultureInfo.InvariantCulture), "WM/PartOfSet");
	}

	/** 
		Gets and sets the lyrics or script of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the lyrics or
		script of the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "WM/Lyrics"
		Content Descriptor.
	 
	*/
	@Override
	public String getLyrics()
	{
		return GetDescriptorString("WM/Lyrics");
	}
	@Override
	public void setLyrics(String value)
	{
		SetDescriptorString(value, "WM/Lyrics");
	}

	/** 
		Gets and sets the grouping on the album which the media
		in the current instance belongs to.
	 
	 <value>
		A <see cref="string" /> object containing the grouping on
		the album which the media in the current instance belongs
		to or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the
		"WM/ContentGroupDescription" Content Descriptor.
	 
	*/
	@Override
	public String getGrouping()
	{
		return GetDescriptorString("WM/ContentGroupDescription");
	}
	@Override
	public void setGrouping(String value)
	{
		SetDescriptorString(value, "WM/ContentGroupDescription");
	}

	/** 
		Gets and sets the number of beats per minute in the audio
		of the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of beats per
		minute in the audio of the media represented by the
		current instance, or zero if not specified.
	 </value>
	 
		This property is implemented using the
		"WM/BeatsPerMinute" Content Descriptor.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getBeatsPerMinute()
	@Override
	public int getBeatsPerMinute()
	{
		for (ContentDescriptor desc : GetDescriptors("WM/BeatsPerMinute"))
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = desc.ToDWord();
				int value = desc.ToDWord();
				if (value != 0)
				{
					return value;
				}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setBeatsPerMinute(uint value)
	@Override
	public void setBeatsPerMinute(int value)
	{
		if (value == 0)
		{
			RemoveDescriptors("WM/BeatsPerMinute");
			return;
		}

		SetDescriptors("WM/BeatsPerMinute", new ContentDescriptor("WM/BeatsPerMinute", value));
	}

	/** 
		Gets and sets the conductor or director of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the conductor
		or director of the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "WM/Conductor"
		Content Descriptor.
	 
	*/
	@Override
	public String getConductor()
	{
		return GetDescriptorString("WM/Conductor");
	}
	@Override
	public void setConductor(String value)
	{
		SetDescriptorString(value, "WM/Conductor");
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the copyright stored
		in the ASF Content Description Object.
	 
	*/
	@Override
	public String getCopyright()
	{
		return description.getCopyright();
	}
	@Override
	public void setCopyright(String value)
	{
		description.setCopyright(value);
	}

	/** 
		Gets and sets the MusicBrainz Artist ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ArtistID for the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "MusicBrainz/Artist Id"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzArtistId()
	{
		return GetDescriptorString("MusicBrainz/Artist Id");
	}
	@Override
	public void setMusicBrainzArtistId(String value)
	{
		SetDescriptorString(value, "MusicBrainz/Artist Id");
	}

	/** 
		Gets and sets the MusicBrainz Release ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ReleaseID for the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "MusicBrainz/Album Id"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseId()
	{
		return GetDescriptorString("MusicBrainz/Album Id");
	}
	@Override
	public void setMusicBrainzReleaseId(String value)
	{
		SetDescriptorString(value, "MusicBrainz/Album Id");
	}

	/** 
		Gets and sets the MusicBrainz Release Artist ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ReleaseArtistID for the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "MusicBrainz/Album Artist Id"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseArtistId()
	{
		return GetDescriptorString("MusicBrainz/Album Artist Id");
	}
	@Override
	public void setMusicBrainzReleaseArtistId(String value)
	{
		SetDescriptorString(value, "MusicBrainz/Album Artist Id");
	}

	/** 
		Gets and sets the MusicBrainz Track ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		TrackID for the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "MusicBrainz/Track Id"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzTrackId()
	{
		return GetDescriptorString("MusicBrainz/Track Id");
	}
	@Override
	public void setMusicBrainzTrackId(String value)
	{
		SetDescriptorString(value, "MusicBrainz/Track Id");
	}

	/** 
		Gets and sets the MusicBrainz Disc ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		DiscID for the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "MusicBrainz/Disc Id"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzDiscId()
	{
		return GetDescriptorString("MusicBrainz/Disc Id");
	}
	@Override
	public void setMusicBrainzDiscId(String value)
	{
		SetDescriptorString(value, "MusicBrainz/Disc Id");
	}

	/** 
		Gets and sets the MusicIP PUID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicIPPUID 
		for the media described by the current instance or
		null if no value is present.
	 </value>
	 
		This property is implemented using the "MusicIP/PUID"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicIpId()
	{
		return GetDescriptorString("MusicIP/PUID");
	}
	@Override
	public void setMusicIpId(String value)
	{
		SetDescriptorString(value, "MusicIP/PUID");
	}

	// <summary>
	//    Gets and sets the AmazonID of
	//    the media described by the current instance.
	// </summary>
	// <value>
	//    A <see cref="string" /> containing the AmazonID 
	//    for the media described by the current instance or
	//    null if no value is present.  
	// </value>
	// <remarks>
	//    A definition on where to store the ASIN for
	//    Windows Media is not currently defined
	// </remarks>
	//public override string AmazonId {
	//    get { return null; }
	//    set {}
	//}

	/** 
		Gets and sets the MusicBrainz Release Status of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ReleaseStatus for the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "MusicBrainz/Album Status"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseStatus()
	{
		return GetDescriptorString("MusicBrainz/Album Status");
	}
	@Override
	public void setMusicBrainzReleaseStatus(String value)
	{
		SetDescriptorString(value, "MusicBrainz/Album Status");
	}

	/** 
		Gets and sets the MusicBrainz Release Type of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ReleaseType for the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "MusicBrainz/Album Type"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseType()
	{
		return GetDescriptorString("MusicBrainz/Album Type");
	}
	@Override
	public void setMusicBrainzReleaseType(String value)
	{
		SetDescriptorString(value, "MusicBrainz/Album Type");
	}

	/** 
		Gets and sets the MusicBrainz Release Country of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ReleaseCountry for the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "MusicBrainz/Album Release Country"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseCountry()
	{
		return GetDescriptorString("MusicBrainz/Album Release Country");
	}
	@Override
	public void setMusicBrainzReleaseCountry(String value)
	{
		SetDescriptorString(value, "MusicBrainz/Album Release Country");
	}

	/** 
		Gets and sets a collection of pictures associated with
		the media represented by the current instance.
	 
	 <value>
		A <see cref="IPicture[]" /> containing a collection of
		pictures associated with the media represented by the
		current instance or an empty array if none are present.
	 </value>
	 
		This property is implemented using the "WM/Picture"
		Content Descriptor and Description Record.
	 
	*/
	@Override
	public IPicture[] getPictures()
	{
		ArrayList<IPicture> l = new ArrayList<IPicture> ();

		for (ContentDescriptor descriptor : GetDescriptors("WM/Picture"))
		{
			IPicture p = PictureFromData(descriptor.ToByteVector());
			if (p != null)
			{
				l.add(p);
			}
		}

		for (DescriptionRecord record : metadata_library.GetRecords((short)0, (short)0, "WM/Picture"))
		{
			IPicture p = PictureFromData(record.ToByteVector());
			if (p != null)
			{
				l.add(p);
			}
		}

		return l.toArray(new IPicture[0]);
	}
	@Override
	public void setPictures(IPicture[] value)
	{
		if (value == null || value.length == 0)
		{
			RemoveDescriptors("WM/Picture");
			metadata_library.RemoveRecords((short)0, (short)0, "WM/Picture");
			return;
		}

		ArrayList<ByteVector> pics = new ArrayList<ByteVector> ();

		boolean big_pics = false;

		for (IPicture pic : value)
		{
			ByteVector data = PictureToData(pic);
			pics.add(data);
			if (data.size() > 0xFFFF)
			{
				big_pics = true;
			}
		}

		if (big_pics)
		{
			DescriptionRecord [] records = new DescriptionRecord [pics.size()];
			for (int i = 0; i < pics.size(); i++)
			{
				records [i] = new DescriptionRecord(0, 0, "WM/Picture", pics.get(i));
			}
			RemoveDescriptors("WM/Picture");
			metadata_library.SetRecords((short)0, (short)0, "WM/Picture", records);
		}
		else
		{
			ContentDescriptor [] descs = new ContentDescriptor [pics.size()];
			for (int i = 0; i < pics.size(); i++)
			{
				descs [i] = new ContentDescriptor("WM/Picture", pics.get(i));
			}
			metadata_library.RemoveRecords((short)0, (short)0, "WM/Picture");
			SetDescriptors("WM/Picture", descs);
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
		return description.getIsEmpty() && ext_description.getIsEmpty();
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		description = new ContentDescriptionObject();
		ext_description = new ExtendedContentDescriptionObject();
		metadata_library.RemoveRecords((short)0, (short)0, "WM/Picture");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}