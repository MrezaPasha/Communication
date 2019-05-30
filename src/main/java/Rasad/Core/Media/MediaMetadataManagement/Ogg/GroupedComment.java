package Rasad.Core.Media.MediaMetadataManagement.Ogg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// GroupedComment.cs:
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
	This class combines a collection of <see cref="XiphComment"/>
	objects so that properties can be read from each but are only set
	to the first comment of the file.
*/
public class GroupedComment extends Tag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains a mapping between stream serial numbers and
		comments.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private Dictionary<uint, XiphComment> comment_hash;
	private HashMap<Integer, XiphComment> comment_hash;

	/** 
		Contains comments in the order they are added.
	*/
	private ArrayList<XiphComment> tags;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="GroupedComment" /> with now contents.
	*/
	public GroupedComment()
	{
		super();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: comment_hash = new Dictionary <uint, XiphComment> ();
		comment_hash = new HashMap<Integer, XiphComment> ();
		tags = new ArrayList<XiphComment> ();
	}

	/** 
		Gets an enumeration of the comments in the current
		instance, in the order they were added.
	 
	 <value>
		A <see cref="T:System.Collections.Generic.IEnumerable`1"
		/> object enumerating through the <see cref="XiphComment"
		/> objects contained in the current instance.
	 </value>
	*/
	public final java.lang.Iterable<XiphComment> getComments()
	{
		return tags;
	}

	/** 
		Gets a comment in the current instance for a specified
		stream.
	 
	 @param streamSerialNumber
		A <see cref="uint" /> value containing the serial number
		of the stream of the comment to get.
	 
	 @return 
		A <see cref="XiphComment"/> with the matching serial
		number.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public XiphComment GetComment(uint streamSerialNumber)
	public final XiphComment GetComment(int streamSerialNumber)
	{
		return comment_hash.get(streamSerialNumber);
	}

	/** 
		Adds a Xiph comment to the current instance.
	 
	 @param streamSerialNumber
		A <see cref="uint" /> value containing the serial number
		of the stream containing the comment.
	 
	 @param comment
		A <see cref="XiphComment" /> object to add to the current
		instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void AddComment(uint streamSerialNumber, XiphComment comment)
	public final void AddComment(int streamSerialNumber, XiphComment comment)
	{
		comment_hash.put(streamSerialNumber, comment);
		tags.add(comment);
	}

	/** 
		Adds a Xiph comment to the current instance.
	 
	 @param streamSerialNumber
		A <see cref="uint" /> value containing the serial number
		of the stream containing the comment.
	 
	 @param data
		A <see cref="ByteVector"/> object containing the raw Xiph
		comment to add to the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void AddComment(uint streamSerialNumber, ByteVector data)
	public final void AddComment(int streamSerialNumber, ByteVector data)
	{
		AddComment(streamSerialNumber, new XiphComment(data));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" />
		containing the tag types contained in the current
		instance.
	 </value>
	 
		This value contains a bitwise combined value from all the
		child tags.
	 
	 {@link Tag.TagTypes }
	*/
	@Override
	public TagTypes getTagTypes()
	{
		TagTypes types = TagTypes.None;
		for (XiphComment tag : tags)
		{
			if (tag != null)
			{
				types = Rasad.Core.Media.MediaMetadataManagement.TagTypes.forValue(types.getValue() | tag.getTagTypes().getValue());
			}
		}

		return types;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-<see
		langword="null" /> value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Title }
	*/
	@Override
	public String getTitle()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getTitle();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setTitle(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setTitle(value);
		}
	}

	/** 
		Gets and sets the sort names for the individual track title of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort name
		for the track title of the media described by the current 
		instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.TitleSort }
	*/
	@Override
	public String getTitleSort()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getTitleSort();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setTitleSort(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setTitleSort(value);
		}
	}

	/** 
		Gets and sets the performers or artists who performed in
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> array containing the performers or
		artists who performed in the media described by the
		current instance or an empty array if no value is
		present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Performers }
	*/
	@Override
	public String[] getPerformers()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String[] value = tag.getPerformers();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String[] { };
	}
	@Override
	public void setPerformers(String[] value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setPerformers(value);
		}
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
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.PerformersSort }
	*/
	@Override
	public String[] getPerformersSort()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String[] value = tag.getPerformersSort();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String[] { };
	}
	@Override
	public void setPerformersSort(String[] value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setPerformersSort(value);
		}
	}

	/** 
		Gets and sets the band or artist who is credited in the
		creation of the entire album or collection containing the
		media described by the current instance.
	 
	 <value>
		A <see cref="string" /> array containing the band or artist
		who is credited in the creation of the entire album or
		collection containing the media described by the current
		instance or an empty array if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.AlbumArtists }
	*/
	@Override
	public String[] getAlbumArtists()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String[] value = tag.getAlbumArtists();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String[] { };
	}
	@Override
	public void setAlbumArtists(String[] value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setAlbumArtists(value);
		}
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
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.AlbumArtistsSort }
	*/
	@Override
	public String[] getAlbumArtistsSort()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String[] value = tag.getAlbumArtistsSort();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String[] { };
	}

	@Override
	public void setAlbumArtistsSort(String[] value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setAlbumArtistsSort(value);
		}
	}

	/** 
		Gets and sets the composers of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> array containing the composers of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Composers }
	*/
	@Override
	public String[] getComposers()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String[] value = tag.getComposers();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String[] { };
	}
	@Override
	public void setComposers(String[] value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setComposers(value);
		}
	}

	/** 
		Gets and sets the sort names for the composer of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names
		for the composer of the media described by the current
		instance or an empty array if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.ComposersSort }
	*/
	@Override
	public String[] getComposersSort()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String[] value = tag.getComposersSort();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String[] { };
	}
	@Override
	public void setComposersSort(String[] value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setComposersSort(value);
		}
	}

	/** 
		Gets and sets the album of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the album of
		the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-<see
		langword="null" /> value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Album }
	*/
	@Override
	public String getAlbum()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getAlbum();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setAlbum(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setAlbum(value);
		}
	}

	/** 
		Gets and sets the sort names for the album title of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort names
		for the album title of the media described by the
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.AlbumSort }
	*/
	@Override
	public String getAlbumSort()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getAlbumSort();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setAlbumSort(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setAlbumSort(value);
		}
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-<see
		langword="null" /> value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Comment }
	*/
	@Override
	public String getComment()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getComment();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setComment(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setComment(value);
		}
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> array containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Genres }
	*/
	@Override
	public String[] getGenres()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String[] value = tag.getGenres();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String[] { };
	}
	@Override
	public void setGenres(String[] value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setGenres(value);
		}
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="string" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-<see
		langword="null" /> value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Year }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
		for (XiphComment tag : tags)
		{
			if (tag != null && tag.getYear() != 0)
			{
				return tag.getYear();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setYear(uint value)
	@Override
	public void setYear(int value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setYear(value);
		}
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Track }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		for (XiphComment tag : tags)
		{
			if (tag != null && tag.getTrack() != 0)
			{
				return tag.getTrack();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setTrack(value);
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
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.TrackCount }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		for (XiphComment tag : tags)
		{
			if (tag != null && tag.getTrackCount() != 0)
			{
				return tag.getTrackCount();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrackCount(uint value)
	@Override
	public void setTrackCount(int value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setTrackCount(value);
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
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Disc }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDisc()
	@Override
	public int getDisc()
	{
		for (XiphComment tag : tags)
		{
			if (tag != null && tag.getDisc() != 0)
			{
				return tag.getDisc();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDisc(uint value)
	@Override
	public void setDisc(int value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setDisc(value);
		}
	}

	/** 
		Gets and sets the number of discs in the boxed set
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of discs in
		the boxed set containing the media represented by the
		current instance or zero if not specified.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.DiscCount }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDiscCount()
	@Override
	public int getDiscCount()
	{
		for (XiphComment tag : tags)
		{
			if (tag != null && tag.getDiscCount() != 0)
			{
				return tag.getDiscCount();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDiscCount(uint value)
	@Override
	public void setDiscCount(int value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setDiscCount(value);
		}
	}

	/** 
		Gets and sets the lyrics or script of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the lyrics or
		script of the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-<see
		langword="null" /> value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Lyrics }
	*/
	@Override
	public String getLyrics()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getLyrics();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setLyrics(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setLyrics(value);
		}
	}

	/** 
		Gets and sets the grouping on the album which the media
		in the current instance belongs to.
	 
	 <value>
		A <see cref="string" /> object containing the grouping on
		the album which the media in the current instance belongs
		to or <see langword="null" /> if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-<see
		langword="null" /> value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Grouping }
	*/
	@Override
	public String getGrouping()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getGrouping();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setGrouping(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setGrouping(value);
		}
	}

	/** 
		Gets and sets the number of beats per minute in the audio
		of the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of beats per
		minute in the audio of the media represented by the
		current instance, or zero if not specified.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.BeatsPerMinute }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getBeatsPerMinute()
	@Override
	public int getBeatsPerMinute()
	{
		for (XiphComment tag : tags)
		{
			if (tag != null && tag.getBeatsPerMinute() != 0)
			{
				return tag.getBeatsPerMinute();
			}
		}

		return 0;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setBeatsPerMinute(uint value)
	@Override
	public void setBeatsPerMinute(int value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setBeatsPerMinute(value);
		}
	}

	/** 
		Gets and sets the conductor or director of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the conductor
		or director of the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-<see
		langword="null" /> value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Conductor }
	*/
	@Override
	public String getConductor()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getConductor();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setConductor(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setConductor(value);
		}
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-<see
		langword="null" /> value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Copyright }
	*/
	@Override
	public String getCopyright()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getCopyright();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setCopyright(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setCopyright(value);
		}
	}

	/** 
		Gets and sets the MusicBrainz Artist ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ArtistID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.MusicBrainzArtistId }
	*/
	@Override
	public String getMusicBrainzArtistId()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzArtistId();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMusicBrainzArtistId(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setMusicBrainzArtistId(value);
		}
	}

	/** 
		Gets and sets the MusicBrainz Release ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.MusicBrainzReleaseId }
	*/
	@Override
	public String getMusicBrainzReleaseId()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseId();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMusicBrainzReleaseId(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setMusicBrainzReleaseId(value);
		}
	}

	/** 
		Gets and sets the MusicBrainz Release Artist ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseArtistID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.MusicBrainzReleaseArtistId }
	*/
	@Override
	public String getMusicBrainzReleaseArtistId()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseArtistId();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMusicBrainzReleaseArtistId(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setMusicBrainzReleaseArtistId(value);
		}
	}

	/** 
		Gets and sets the MusicBrainz Track ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		TrackID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.MusicBrainzTrackId }
	*/
	@Override
	public String getMusicBrainzTrackId()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzTrackId();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMusicBrainzTrackId(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setMusicBrainzTrackId(value);
		}
	}

	/** 
		Gets and sets the MusicBrainz Disc ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		DiscID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.MusicBrainzDiscId }
	*/
	@Override
	public String getMusicBrainzDiscId()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzDiscId();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMusicBrainzDiscId(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setMusicBrainzDiscId(value);
		}
	}

	/** 
		Gets and sets the MusicIP PUID.
	 
	 <value>
		A <see cref="string" /> containing the MusicIP PUID
		for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.MusicIpId }
	*/
	@Override
	public String getMusicIpId()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicIpId();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMusicIpId(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setMusicIpId(value);
		}
	}

	/** 
		Gets and sets the Amazon ID.
	 
	 <value>
		A <see cref="string" /> containing the Amazon ID
		for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.AmazonId }
	*/
	@Override
	public String getAmazonId()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getAmazonId();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setAmazonId(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setAmazonId(value);
		}
	}

	/** 
		Gets and sets the MusicBrainz Release Status.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		Release Status for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.MusicBrainzReleaseStatus }
	*/
	@Override
	public String getMusicBrainzReleaseStatus()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseStatus();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMusicBrainzReleaseStatus(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setMusicBrainzReleaseStatus(value);
		}
	}

	/** 
		Gets and sets the MusicBrainz Release Type.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		Release Type for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.MusicBrainzReleaseType }
	*/
	@Override
	public String getMusicBrainzReleaseType()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseType();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMusicBrainzReleaseType(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setMusicBrainzReleaseType(value);
		}
	}

	/** 
		Gets and sets the MusicBrainz Release Country.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		Release Country for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child comments are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.MusicBrainzReleaseCountry }
	*/
	@Override
	public String getMusicBrainzReleaseCountry()
	{
		for (XiphComment tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseCountry();

			if (value != null && value.length() > 0)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMusicBrainzReleaseCountry(String value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setMusicBrainzReleaseCountry(value);
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
	 
		<p>When getting the value, the child comments are
		looped through in order and the first non-empty value is
		returned.</p>
		<p>When setting the value, it is stored in the first
		comment.</p>
	 
	 {@link Tag.Pictures }
	*/
	@Override
	public IPicture[] getPictures()
	{
		IPicture [] output = new IPicture [0];
		for (XiphComment tag : tags)
		{
			if (tag != null && output.length == 0)
			{
				output = tag.getPictures();
			}
		}

		return output;
	}
	@Override
	public void setPictures(IPicture[] value)
	{
		if (!tags.isEmpty())
		{
			tags.get(0).setPictures(value);
		}
	}

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if all the comments tags are
		 empty; otherwise <see langword="false" />.
	 </value>
	 {@link Tag.IsEmpty }
	*/
	@Override
	public boolean getIsEmpty()
	{
		for (XiphComment tag : tags)
		{
			if (!tag.getIsEmpty())
			{
				return false;
			}
		}

		return true;
	}

	/** 
		Clears all of the child tags.
	*/
	@Override
	public void Clear()
	{
		for (XiphComment tag : tags)
		{
			tag.Clear();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}