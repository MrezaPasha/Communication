package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Tag.cs:
//
// Author:
//   Julien Moutte <julien@fluendo.com>
//
// Copyright (C) 2011 FLUENDO S.A.
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
 Describes a Matroska Tag.
*/
public class Tag extends Rasad.Core.Media.MediaMetadataManagement.Tag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private fields

	private String title;
	private String author;
	private String album;
	private String comments;
	private String genres;
	private String copyright;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Taglib.Tag


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
	 
		This property is implemented using the title stored in
		the ASF Content Description Object.
	 
	*/
	@Override
	public String getTitle()
	{
		return title;
	}
	@Override
	public void setTitle(String value)
	{
		title = value;
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
		return null;
	}
	@Override
	public void setTitleSort(String value)
	{
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
		the MKV Tag element.
	 
	*/
	@Override
	public String[] getPerformers()
	{
		return new String [] {author};
	}
	@Override
	public void setPerformers(String[] value)
	{
		author = tangible.StringHelper.join("; ", value);
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
	*/
	@Override
	public String[] getPerformersSort()
	{
		return new String [] {};
	}
	@Override
	public void setPerformersSort(String[] value)
	{
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
	*/
	@Override
	public String[] getAlbumArtists()
	{
		return new String [] {};
	}
	@Override
	public void setAlbumArtists(String[] value)
	{
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
	*/
	@Override
	public String[] getAlbumArtistsSort()
	{
		return new String [] { };
	}
	@Override
	public void setAlbumArtistsSort(String[] value)
	{
	}

	/** 
		Gets and sets the composers of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the composers of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	*/
	@Override
	public String[] getComposers()
	{
		return new String [] { };
	}
	@Override
	public void setComposers(String[] value)
	{
	}

	/** 
		Gets and sets the album of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the album of
		the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "ALBUM" Tag.
	 
	*/
	@Override
	public String getAlbum()
	{
		return album;
	}
	@Override
	public void setAlbum(String value)
	{
		album = value;
	}

	/** 
		Gets and sets the sort names for the Album Title of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort name of 
		the Album Title of the media described by the current
		instance or null if no value is present.
	 </value>
	*/
	@Override
	public String getAlbumSort()
	{
		return null;
	}
	@Override
	public void setAlbumSort(String value)
	{
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "COMMENTS" Tag.
	 
	*/
	@Override
	public String getComment()
	{
		return comments;
	}
	@Override
	public void setComment(String value)
	{
		comments = value;
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "GENRE" Tag.
	 
	*/
	@Override
	public String[] getGenres()
	{
		String value = genres;

		if (value == null || value.trim().length() == 0)
		{
			return new String [] { };
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
//ORIGINAL LINE: if (closing > 0 && genre [0] == '(' && byte.TryParse(genre.Substring(1, closing - 1), out genre_id))
			if (closing > 0 && genre.charAt (0) == '(' && tangible.TryParseHelper.tryParseByte(genre.substring(1, closing), tempOut_genre_id))
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
		genres = tangible.StringHelper.join("; ", value);
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setYear(uint value)
	@Override
	public void setYear(int value)
	{
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
	}

	/** 
		Gets and sets the number of tracks in the album
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of tracks in
		the album containing the media represented by the current
		instance or zero if not specified.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrackCount(uint value)
	@Override
	public void setTrackCount(int value)
	{
	}

	/** 
		Gets and sets the number of the disc containing the media
		represented by the current instance in the boxed set.
	 
	 <value>
		A <see cref="uint" /> containing the number of the disc
		containing the media represented by the current instance
		in the boxed set.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDisc()
	@Override
	public int getDisc()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDisc(uint value)
	@Override
	public void setDisc(int value)
	{
	}

	/** 
		Gets and sets the number of discs in the boxed set
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of discs in
		the boxed set containing the media represented by the
		current instance or zero if not specified.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDiscCount()
	@Override
	public int getDiscCount()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDiscCount(uint value)
	@Override
	public void setDiscCount(int value)
	{
	}

	/** 
		Gets and sets the lyrics or script of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the lyrics or
		script of the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	*/
	@Override
	public String getLyrics()
	{
		return null;
	}
	@Override
	public void setLyrics(String value)
	{
	}

	/** 
		Gets and sets the grouping on the album which the media
		in the current instance belongs to.
	 
	 <value>
		A <see cref="string" /> object containing the grouping on
		the album which the media in the current instance belongs
		to or <see langword="null" /> if no value is present.
	 </value>
	*/
	@Override
	public String getGrouping()
	{
		return null;
	}
	@Override
	public void setGrouping(String value)
	{
	}

	/** 
		Gets and sets the number of beats per minute in the audio
		of the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of beats per
		minute in the audio of the media represented by the
		current instance, or zero if not specified.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getBeatsPerMinute()
	@Override
	public int getBeatsPerMinute()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setBeatsPerMinute(uint value)
	@Override
	public void setBeatsPerMinute(int value)
	{
	}

	/** 
		Gets and sets the conductor or director of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the conductor
		or director of the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	*/
	@Override
	public String getConductor()
	{
		return null;
	}
	@Override
	public void setConductor(String value)
	{
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "COPYRIGHT" Tag.
	 
	*/
	@Override
	public String getCopyright()
	{
		return copyright;
	}
	@Override
	public void setCopyright(String value)
	{
		copyright = value;
	}

	/** 
		Gets and sets the MusicBrainz Artist ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ArtistID for the media described by the current
		instance or null if no value is present.
	 </value>
	*/
	@Override
	public String getMusicBrainzArtistId()
	{
		return null;
	}
	@Override
	public void setMusicBrainzArtistId(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Release ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ReleaseID for the media described by the current
		instance or null if no value is present.
	 </value>
	*/
	@Override
	public String getMusicBrainzReleaseId()
	{
		return null;
	}
	@Override
	public void setMusicBrainzReleaseId(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Release Artist ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ReleaseArtistID for the media described by the current
		instance or null if no value is present.
	 </value>
	*/
	@Override
	public String getMusicBrainzReleaseArtistId()
	{
		return null;
	}
	@Override
	public void setMusicBrainzReleaseArtistId(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Track ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		TrackID for the media described by the current
		instance or null if no value is present.
	 </value>
	*/
	@Override
	public String getMusicBrainzTrackId()
	{
		return null;
	}
	@Override
	public void setMusicBrainzTrackId(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Disc ID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		DiscID for the media described by the current
		instance or null if no value is present.
	 </value>
	*/
	@Override
	public String getMusicBrainzDiscId()
	{
		return null;
	}
	@Override
	public void setMusicBrainzDiscId(String value)
	{
	}

	/** 
		Gets and sets the MusicIP PUID of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicIPPUID 
		for the media described by the current instance or
		null if no value is present.
	 </value>
	*/
	@Override
	public String getMusicIpId()
	{
		return null;
	}
	@Override
	public void setMusicIpId(String value)
	{
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
	*/
	@Override
	public String getMusicBrainzReleaseStatus()
	{
		return null;
	}
	@Override
	public void setMusicBrainzReleaseStatus(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Release Type of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ReleaseType for the media described by the current
		instance or null if no value is present.
	 </value>
	*/
	@Override
	public String getMusicBrainzReleaseType()
	{
		return null;
	}
	@Override
	public void setMusicBrainzReleaseType(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Release Country of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz 
		ReleaseCountry for the media described by the current
		instance or null if no value is present.
	 </value>
	*/
	@Override
	public String getMusicBrainzReleaseCountry()
	{
		return null;
	}
	@Override
	public void setMusicBrainzReleaseCountry(String value)
	{
	}

	/** 
		Gets and sets a collection of pictures associated with
		the media represented by the current instance.
	 
	 <value>
		A <see cref="IPicture[]" /> containing a collection of
		pictures associated with the media represented by the
		current instance or an empty array if none are present.
	 </value>
	*/
	@Override
	public IPicture[] getPictures()
	{
		ArrayList<IPicture> l = new ArrayList<IPicture> ();


		return l.toArray(new IPicture[0]);
	}
	@Override
	public void setPictures(IPicture[] value)
	{
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
		return false;
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}