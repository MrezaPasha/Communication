package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;

//
// CombinedTag.cs: Combines a collection of tags so that they behave as one.
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
	This class combines a collection of tags so that they behave as
	one.
*/
public class CombinedTag extends Tag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains tags to be combined.
	*/
	private ArrayList<Tag> tags;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="CombinedTag" /> with no internal tags.
	 
	 
		You can set the tags in the new instance later using
		<see cref="SetTags" />.
	 
	*/
	public CombinedTag()
	{
		this.tags = new ArrayList<Tag> ();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="CombinedTag" /> with a specified collection of
		tags.
	 
	 @param tags
		A <see cref="Tag[]" /> containing a collection of tags to
		combine in the new instance.
	 
	*/
	public CombinedTag(Tag... tags)
	{
		this.tags = new ArrayList<Tag> (Arrays.asList(tags));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the tags combined in the current instance.
	 
	 <value>
		A <see cref="Tag[]" /> containing the tags combined in
		the current instance.
	 </value>
	*/
	public Tag[] getTags()
	{
		return tags.toArray(new Tag[0]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Sets the child tags to combine in the current instance.
	 
	 @param tags
		A <see cref="Tag[]" /> containing the tags to combine.
	 
	*/
	public final void SetTags(Tag... tags)
	{
		this.tags.clear();
		this.tags.addAll(Arrays.asList(tags));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Inserts a tag into the collection of tags in the current
		instance.
	 
	 @param index
		A <see cref="int" /> value specifying the index at which
		to insert the tag.
	 
	 @param tag
		A <see cref="Tag" /> object to insert into the collection
		of tags.
	 
	 @exception System.ArgumentOutOfRangeException
		<paramref name="index" /> is less than zero or greater
		than the count.
	 
	*/
	protected final void InsertTag(int index, Tag tag)
	{
		this.tags.add(index, tag);
	}

	/** 
		Adds a tag at the end of the collection of tags in the
		current instance.
	 
	 @param tag
		A <see cref="Tag" /> object to add to the collection of
		tags.
	 
	*/
	protected final void AddTag(Tag tag)
	{
		this.tags.add(tag);
	}

	/** 
		Removes a specified tag from the collection in the
		current instance.
	 
	 @param tag
		A <see cref="Tag" /> object to remove from the
		collection.
	 
	*/
	protected final void RemoveTag(Tag tag)
	{
		this.tags.remove(tag);
	}

	/** 
		Clears the tag collection in the current instance.
	*/
	protected final void ClearTags()
	{
		this.tags.clear();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Overrides

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
		for (Tag tag : tags)
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Title }
	*/
	@Override
	public String getTitle()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getTitle();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setTitle(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setTitle(value);
			}
		}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Performers }
	*/
	@Override
	public String[] getPerformers()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String [] value = tag.getPerformers();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String [] {};
	}

	@Override
	public void setPerformers(String[] value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setPerformers(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.PerformersSort }
	*/
	@Override
	public String[] getPerformersSort()
	{
		for (Tag tag : tags)
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
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setPerformersSort(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.AlbumArtistsSort }
	*/
	@Override
	public String[] getAlbumArtistsSort()
	{
		for (Tag tag : tags)
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
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setAlbumArtistsSort(value);
			}
		}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.AlbumArtists }
	*/
	@Override
	public String[] getAlbumArtists()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String [] value = tag.getAlbumArtists();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String [] {};
	}

	@Override
	public void setAlbumArtists(String[] value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setAlbumArtists(value);
			}
		}
	}

	/** 
		Gets and sets the composers of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the composers of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Composers }
	*/
	@Override
	public String[] getComposers()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String [] value = tag.getComposers();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String [] {};
	}

	@Override
	public void setComposers(String[] value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setComposers(value);
			}
		}
	}

	/** 
		Gets and sets the sort names for the composer of the 
		media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names
		for the composers of the media described by the 
		current instance or an empty array if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.ComposersSort }
	*/
	@Override
	public String[] getComposersSort()
	{
		for (Tag tag : tags)
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
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setComposersSort(value);
			}
		}
	}

	/** 
		Gets and sets the sort names for the Track Title of the 
		media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort names
		for the Track Title of the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.TitleSort }
	*/
	@Override
	public String getTitleSort()
	{
		for (Tag tag : tags)
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
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setTitleSort(value);
			}
		}
	}

	/** 
		Gets and sets the sort names for the Album Title of the 
		media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort names
		for the Title of the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.AlbumSort }
	*/
	@Override
	public String getAlbumSort()
	{
		for (Tag tag : tags)
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
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setAlbumSort(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Album }
	*/
	@Override
	public String getAlbum()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getAlbum();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setAlbum(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setAlbum(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Comment }
	*/
	@Override
	public String getComment()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getComment();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setComment(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setComment(value);
			}
		}
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Genres }
	*/
	@Override
	public String[] getGenres()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String [] value = tag.getGenres();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String [] {};
	}

	@Override
	public void setGenres(String[] value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setGenres(value);
			}
		}
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Year }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = tag.Year;
			int value = tag.getYear();

			if (value != 0)
			{
				return value;
			}
		}

		return 0;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setYear(uint value)
	@Override
	public void setYear(int value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setYear(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Track }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = tag.Track;
			int value = tag.getTrack();

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
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setTrack(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.TrackCount }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = tag.TrackCount;
			int value = tag.getTrackCount();

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
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setTrackCount(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Disc }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDisc()
	@Override
	public int getDisc()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = tag.Disc;
			int value = tag.getDisc();

			if (value != 0)
			{
				return value;
			}
		}

		return 0;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDisc(uint value)
	@Override
	public void setDisc(int value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setDisc(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.DiscCount }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDiscCount()
	@Override
	public int getDiscCount()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = tag.DiscCount;
			int value = tag.getDiscCount();

			if (value != 0)
			{
				return value;
			}
		}

		return 0;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDiscCount(uint value)
	@Override
	public void setDiscCount(int value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setDiscCount(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Lyrics }
	*/
	@Override
	public String getLyrics()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getLyrics();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setLyrics(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setLyrics(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Grouping }
	*/
	@Override
	public String getGrouping()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getGrouping();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setGrouping(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setGrouping(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.BeatsPerMinute }
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getBeatsPerMinute()
	@Override
	public int getBeatsPerMinute()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = tag.BeatsPerMinute;
			int value = tag.getBeatsPerMinute();

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
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setBeatsPerMinute(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Conductor }
	*/
	@Override
	public String getConductor()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getConductor();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setConductor(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setConductor(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Copyright }
	*/
	@Override
	public String getCopyright()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getCopyright();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setCopyright(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setCopyright(value);
			}
		}
	}

	/** 
		Gets and sets the MusicBrainz Artist ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ArtistID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.MusicBrainzArtistId }
	*/
	@Override
	public String getMusicBrainzArtistId()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzArtistId();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setMusicBrainzArtistId(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setMusicBrainzArtistId(value);
			}
		}
	}

	/** 
		Gets and sets the MusicBrainz Release ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.MusicBrainzReleaseId }
	*/
	@Override
	public String getMusicBrainzReleaseId()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseId();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setMusicBrainzReleaseId(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setMusicBrainzReleaseId(value);
			}
		}
	}

	/** 
		Gets and sets the MusicBrainz Release Artist ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseArtistID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.MusicBrainzReleaseArtistId }
	*/
	@Override
	public String getMusicBrainzReleaseArtistId()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseArtistId();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setMusicBrainzReleaseArtistId(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setMusicBrainzReleaseArtistId(value);
			}
		}
	}

	/** 
		Gets and sets the MusicBrainz Track ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		TrackID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.MusicBrainzTrackId }
	*/
	@Override
	public String getMusicBrainzTrackId()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzTrackId();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setMusicBrainzTrackId(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setMusicBrainzTrackId(value);
			}
		}
	}

	/** 
		Gets and sets the MusicBrainz Disc ID.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		DiscID for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.MusicBrainzDiscId }
	*/
	@Override
	public String getMusicBrainzDiscId()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzDiscId();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setMusicBrainzDiscId(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setMusicBrainzDiscId(value);
			}
		}
	}

	/** 
		Gets and sets the MusicIP PUID.
	 
	 <value>
		A <see cref="string" /> containing the MusicIP PUID
		for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.MusicIpId }
	*/
	@Override
	public String getMusicIpId()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicIpId();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setMusicIpId(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setMusicIpId(value);
			}
		}
	}

	/** 
		Gets and sets the Amazon ID.
	 
	 <value>
		A <see cref="string" /> containing the Amazon Id
		for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.AmazonId }
	*/
	@Override
	public String getAmazonId()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getAmazonId();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setAmazonId(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setAmazonId(value);
			}
		}
	}

	/** 
		Gets and sets the MusicBrainz Release Status.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseStatus for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.MusicBrainzReleaseStatus }
	*/
	@Override
	public String getMusicBrainzReleaseStatus()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseStatus();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setMusicBrainzReleaseStatus(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setMusicBrainzReleaseStatus(value);
			}
		}
	}

	/** 
		Gets and sets the MusicBrainz Release Type.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseType for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.MusicBrainzReleaseType }
	*/
	@Override
	public String getMusicBrainzReleaseType()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseType();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setMusicBrainzReleaseType(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setMusicBrainzReleaseType(value);
			}
		}
	}

	/** 
		Gets and sets the MusicBrainz Release Country.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseCountry for the media described by the 
		current instance or null if no value is present.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.MusicBrainzReleaseCountry }
	*/
	@Override
	public String getMusicBrainzReleaseCountry()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			String value = tag.getMusicBrainzReleaseCountry();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}

	@Override
	public void setMusicBrainzReleaseCountry(String value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setMusicBrainzReleaseCountry(value);
			}
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
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-<see langword="null" />
		and non-empty value is returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.Pictures }
	*/
	@Override
	public IPicture[] getPictures()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			IPicture [] value = tag.getPictures();

			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return super.getPictures();
	}

	@Override
	public void setPictures(IPicture[] value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setPictures(value);
			}
		}
	}

	/** 
		Gets and sets the ReplayGain track gain in dB.
	 
	 <value>
		A <see cref="bool" /> value in dB for the track gain as
		per the ReplayGain specification.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.ReplayGainTrackGain }
	*/
	@Override
	public double getReplayGainTrackGain()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			double value = tag.getReplayGainTrackGain();

			if (!Double.isNaN(value))
			{
				return value;
			}
		}

		return Double.NaN;
	}

	@Override
	public void setReplayGainTrackGain(double value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setReplayGainTrackGain(value);
			}
		}
	}

	/** 
		Gets and sets the ReplayGain track peak sample.
	 
	 <value>
		A <see cref="bool" /> value for the track peak as per the
		ReplayGain specification.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.ReplayGainTrackPeak }
	*/
	@Override
	public double getReplayGainTrackPeak()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			double value = tag.getReplayGainTrackPeak();

			if (!Double.isNaN(value))
			{
				return value;
			}
		}

		return Double.NaN;
	}

	@Override
	public void setReplayGainTrackPeak(double value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setReplayGainTrackPeak(value);
			}
		}
	}

	/** 
		Gets and sets the ReplayGain album gain in dB.
	 
	 <value>
		A <see cref="bool" /> value in dB for the album gain as
		per the ReplayGain specification.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.ReplayGainAlbumGain }
	*/
	@Override
	public double getReplayGainAlbumGain()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			double value = tag.getReplayGainAlbumGain();

			if (!Double.isNaN(value))
			{
				return value;
			}
		}

		return Double.NaN;
	}

	@Override
	public void setReplayGainAlbumGain(double value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setReplayGainAlbumGain(value);
			}
		}
	}

	/** 
		Gets and sets the ReplayGain album peak sample.
	 
	 <value>
		A <see cref="bool" /> value for the album peak as per the
		ReplayGain specification.
	 </value>
	 
		<p>When getting the value, the child tags are looped
		through in order and the first non-zero value is
		returned.</p>
		<p>When setting the value, it is stored in each child
		tag.</p>
	 
	 {@link Tag.ReplayGainAlbumPeak }
	*/
	@Override
	public double getReplayGainAlbumPeak()
	{
		for (Tag tag : tags)
		{
			if (tag == null)
			{
				continue;
			}

			double value = tag.getReplayGainAlbumPeak();

			if (!Double.isNaN(value))
			{
				return value;
			}
		}

		return Double.NaN;
	}

	@Override
	public void setReplayGainAlbumPeak(double value)
	{
		for (Tag tag : tags)
		{
			if (tag != null)
			{
				tag.setReplayGainAlbumPeak(value);
			}
		}
	}

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if all the child tags are empty.
		Otherwise <see langword="false" />.
	 </value>
	 {@link Tag.IsEmpty }
	*/
	@Override
	public boolean getIsEmpty()
	{
		for (Tag tag : tags)
		{
			if (tag.getIsEmpty())
			{
				return true;
			}
		}

		return false;
	}

	/** 
		Clears all of the child tags.
	*/
	@Override
	public void Clear()
	{
		for (Tag tag : tags)
		{
			tag.Clear();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}