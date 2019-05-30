package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

/** 
	This abstract class provides generic access to standard tag
	features. All tag types will extend this class.
 
 
	Because not every tag type supports the same features, it may be
	useful to check that the value is stored by re-reading the
	property after it is stored.
 
*/
public abstract class Tag
{
	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" />
		containing the tag types contained in the current
		instance.
	 </value>
	 
		For a standard tag, the value should be intuitive. For
		example, <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag" /> objects have a
		value of <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes.Id3v2" />. However,
		for tags of type <see cref="Rasad.Core.Media.MediaMetadataManagement.CombinedTag" /> may
		contain multiple or no types.
	 
	*/
	public abstract TagTypes getTagTypes();

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		The title is most commonly the name of the song or
		episode or a movie title. For example, "Daydream
		Believer" (a song by the Monkies), "Space Seed" (an
		episode of Star Trek), or "Harold and Kumar Go To White
		Castle" (a movie).
	 
	*/
	public String getTitle()
	{
		return null;
	}
	public void setTitle(String value)
	{
	}

	/** 
		Gets and sets the sort name for the title of the media 
		described by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the sort name for
		the title of the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		Possibly used to sort compilations, or episodic content.
	 
	*/
	public String getTitleSort()
	{
		return null;
	}
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
	 
		<p>This field is most commonly called "Artists" in
		media applications and should be used to represent each
		of the artists appearing in the media. It can be simple
		in theform of "The Beatles", or more complicated in the
		form of "John Lennon, Paul McCartney, George Harrison,
		Pete Best", depending on the preferences of the listener
		and the degree to which they organize their media
		collection.</p>
		<p>As the preference of the user may vary,
		applications should not try to limit the user in what
		choice they may make.</p>
	 
	*/
	public String[] getPerformers()
	{
		return new String [] {};
	}
	public void setPerformers(String[] value)
	{
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
	 
		<p>This is used to provide more control over how tracks
		are sorted. Typical uses are to skip common prefixes or
		sort by last name. For example, "The Beatles" might be
		sorted as "Beatles, The".
		</p>
	 
	*/
	public String[] getPerformersSort()
	{
		return new String [] {};
	}
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
	 
		<p>This field is typically optional but aids in the
		sorting of compilations or albums with multiple artists.
		For example, if an album has several artists, sorting by
		artist will split up the album and sorting by album will
		split up albums by the same artist. Having a single album
		artist for an entire album will solve this
		problem.</p>
		<p>As this value is to be used as a sorting key, it
		should be used with less variation than <see
		cref="Performers" />. Where performers can be broken into
		muliple artist it is best to stick with a single band
		name. For example, "The Beatles".</p>
	 
	*/
	public String[] getAlbumArtists()
	{
		return new String [] {};
	}
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
	 
		<p>This field is typically optional but aids in the
		sorting of compilations or albums with multiple artists.
		For example, if an album has several artists, sorting by
		artist will split up the album and sorting by album will
		split up albums by the same artist. Having a single album
		artist for an entire album will solve this
		problem.</p>
		<p>As this value is to be used as a sorting key, it
		should be used with less variation than <see
		cref="Performers" />. Where performers can be broken into
		muliple artist it is best to stick with a single band
		name. For example, "Beatles, The".</p>
	 
	*/
	public String[] getAlbumArtistsSort()
	{
		return new String [] {};
	}
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
	 
		<p>This field represents the composers, song writers,
		script writers, or persons who claim authorship of the
		media.</p>
	 
	*/
	public String[] getComposers()
	{
		return new String [] {};
	}
	public void setComposers(String[] value)
	{
	}

	/** 
		Gets and sets the sort names for the composers of the 
		media represented by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names
		for the composers of the media represented by the 
		current instance or an empty array if no value is present.
	 </value>
	 
		<p>This field is typically optional but aids in the
		sorting of compilations or albums with multiple Composers.
		</p>
		<p>As this value is to be used as a sorting key, it
		should be used with less variation than <see
		cref="Composers" />. Where performers can be broken into
		muliple artist it is best to stick with a single composer.
		For example, "McCartney, Paul".</p>
	 
	*/
	public String[] getComposersSort()
	{
		return new String [] {};
	}
	public void setComposersSort(String[] value)
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
	 
		<p>This field represents the name of the album the
		media belongs to. In the case of a boxed set, it should
		be the name of the entire set rather than the individual
		disc.</p>
		<p>For example, "Rubber Soul" (an album by the
		Beatles), "The Sopranos: Complete First Season" (a boxed
		set of TV episodes), or "Back To The Future Trilogy" (a 
		boxed set of movies).</p>
	 
	*/
	public String getAlbum()
	{
		return null;
	}
	public void setAlbum(String value)
	{
	}

	/** 
		Gets and sets the sort names for the Album Title of the 
		media represented by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names
		for the Album Title of the media represented by the 
		current instance or an empty array if no value is present.
	 </value>
	 
		<p>This field is typically optional but aids in the
		sorting of compilations or albums with Similar Titles.
		</p>
	 
	*/
	public String getAlbumSort()
	{
		return null;
	}
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
	 
		<p>This field should be used to store user notes and
		comments. There is no constraint on what text can be
		stored here, but it should not contain program
		information.</p>
		<p>Because this field contains notes that the user
		might think of while listening to the media, it may be
		useful for an application to make this field easily
		accessible, perhaps even including it in the main
		interface.</p>
	 
	*/
	public String getComment()
	{
		return null;
	}
	public void setComment(String value)
	{
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents genres that apply to the song
		or album. This is often used for filtering media.</p>
		<p>A list of common audio genres as popularized by
		ID3v1, are stored in <see cref="Genres.Audio" />.
		Additionally, <see cref="Genres.Video" /> contains video
		genres as used by DivX.</p>
	 
	*/
	public String[] getGenres()
	{
		return new String [] {};
	}
	public void setGenres(String[] value)
	{
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		<p>Years greater than 9999 cannot be stored by most
		tagging formats and will be cleared if a higher value is
		set.</p>
		<p>Some tagging formats store higher precision dates
		which will be truncated when this property is set. Format
		specific implementations are necessary access the higher
		precision values.</p>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual uint getYear()
	public int getYear()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual void setYear(uint value)
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
	 
		<p>This value should be the same as is listed on the
		album cover and no more than <see cref="TrackCount"
		/> if <see cref="TrackCount" /> is non-zero.</p>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual uint getTrack()
	public int getTrack()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual void setTrack(uint value)
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
	 
		<p>If non-zero, this value should be at least equal to
		<see cref="Track" />. If <see cref="Track" /> is zero,
		this value should also be zero.</p>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual uint getTrackCount()
	public int getTrackCount()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual void setTrackCount(uint value)
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
	 
		<p>This value should be the same as is number that
		appears on the disc. For example, if the disc is the
		first of three, the value should be <c>1</c>. It should
		be no more than <see cref="DiscCount" /> if <see
		cref="DiscCount" /> is non-zero.</p>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual uint getDisc()
	public int getDisc()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual void setDisc(uint value)
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
	 
		<p>If non-zero, this value should be at least equal to
		<see cref="Disc" />. If <see cref="Disc" /> is zero,
		this value should also be zero.</p>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual uint getDiscCount()
	public int getDiscCount()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual void setDiscCount(uint value)
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
	 
		<p>This field contains a plain text representation of
		the lyrics or scripts with line breaks and whitespace
		being the only formatting marks.</p>
		<p>Some formats support more advances lyrics, like
		synchronized lyrics, but those must be accessed using
		format specific implementations.</p>
	 
	*/
	public String getLyrics()
	{
		return null;
	}
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
	 
		<p>This field contains a non-physical grouping to
		which the track belongs. In classical music, this could
		be a movement. It could also be parts of a series like
		"Introduction", "Closing Remarks", etc.</p>
	 
	*/
	public String getGrouping()
	{
		return null;
	}
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
	 
		<p>This field is useful for DJ's who are trying to
		match songs. It should be calculated from the audio or
		pulled from a database.</p>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual uint getBeatsPerMinute()
	public int getBeatsPerMinute()
	{
		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual void setBeatsPerMinute(uint value)
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
	 
		<p>This field is most useful for organizing classical
		music and movies.</p>
	 
	*/
	public String getConductor()
	{
		return null;
	}
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
	 
		<p>This field should be used for storing copyright
		information. It may be useful to show this information
		somewhere in the program while the media is
		playing.</p>
		<p>Players should not support editing this field, but
		media creation tools should definitely allow
		modification.</p>
	 
	*/
	public String getCopyright()
	{
		return null;
	}
	public void setCopyright(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Artist ID of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz ArtistID of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the MusicBrainz ArtistID, and is used
		to uniquely identify a particular Artist of the track.</p>
	 
	*/
	public String getMusicBrainzArtistId()
	{
		return null;
	}
	public void setMusicBrainzArtistId(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Release ID of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz ReleaseID of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the MusicBrainz ReleaseID, and is used
		to uniquely identify a particular Release to which this track belongs.</p>
	 
	*/
	public String getMusicBrainzReleaseId()
	{
		return null;
	}
	public void setMusicBrainzReleaseId(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Release Artist ID of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz ReleaseArtistID of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the MusicBrainz Release ArtistID, and is used
		to uniquely identify a particular Album Artist credited with the Album.</p>
	 
	*/
	public String getMusicBrainzReleaseArtistId()
	{
		return null;
	}
	public void setMusicBrainzReleaseArtistId(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Track ID of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz TrackID of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the MusicBrainz TrackID, and is used
		to uniquely identify a particular track.</p>
	 
	*/
	public String getMusicBrainzTrackId()
	{
		return null;
	}
	public void setMusicBrainzTrackId(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Disc ID of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz DiscID of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the MusicBrainz DiscID, and is used
		to uniquely identify the particular Released Media associated with
		this track.</p>
	 
	*/
	public String getMusicBrainzDiscId()
	{
		return null;
	}
	public void setMusicBrainzDiscId(String value)
	{
	}

	/** 
		Gets and sets the MusicIP PUID of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicIP PUID of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the MusicIP PUID, and is an acoustic
		fingerprint identifier.  It Identifies what this track "Sounds Like".</p>
	 
	*/
	public String getMusicIpId()
	{
		return null;
	}
	public void setMusicIpId(String value)
	{
	}

	/** 
		Gets and sets the Amazon ID of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the AmazonID of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the AmazonID, and is used
		to identify the particular track or album in the Amazon Catalog.</p>
	 
	*/
	public String getAmazonId()
	{
		return null;
	}
	public void setAmazonId(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Release Status of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz ReleaseStatus of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the MusicBrainz ReleaseStatus, and is used
		to describes how 'official' a Release is.  Common Status are: Official, Promotion,
		Bootleg, Pseudo-release.</p>
	 
	*/
	public String getMusicBrainzReleaseStatus()
	{
		return null;
	}
	public void setMusicBrainzReleaseStatus(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Release Type of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz ReleaseType of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the MusicBrainz ReleaseType, that describes
		what kind of release a Release is..  Common Status are: Single, Album,
		EP, Compilation, Soundtrack, SpokenWord, Interview, Audiobook, Live, Remix,
		and Other.  Careful thought must be given when using this field to decide if
		a particular track "Is a Compilation".</p>
	 
	*/
	public String getMusicBrainzReleaseType()
	{
		return null;
	}
	public void setMusicBrainzReleaseType(String value)
	{
	}

	/** 
		Gets and sets the MusicBrainz Release Country of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz ReleaseCountry of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		<p>This field represents the MusicBrainz ReleaseCountry, that describes
		the country in which an album was released.  Note that the ReleaseCountry 
		of an album is not necessarily the country in which it was produced. The 
		label itself will typically be more relevant. eg, a release on "Foo Records UK" 
		that has "Made in Austria" printed on it, will likely be a UK release.</p>
	 
	*/
	public String getMusicBrainzReleaseCountry()
	{
		return null;
	}
	public void setMusicBrainzReleaseCountry(String value)
	{
	}

	/** 
		Gets and sets the ReplayGain track gain in dB.
	 
	 <value>
		A <see cref="bool" /> value in dB for the track gain as
		per the ReplayGain specification.
	 </value>
	*/
	public double getReplayGainTrackGain()
	{
		return Double.NaN;
	}
	public void setReplayGainTrackGain(double value)
	{
	}

	/** 
		Gets and sets the ReplayGain track peak sample.
	 
	 <value>
		A <see cref="bool" /> value for the track peak as per the
		ReplayGain specification.
	 </value>
	*/
	public double getReplayGainTrackPeak()
	{
		return Double.NaN;
	}
	public void setReplayGainTrackPeak(double value)
	{
	}

	/** 
		Gets and sets the ReplayGain album gain in dB.
	 
	 <value>
		A <see cref="bool" /> value in dB for the album gain as
		per the ReplayGain specification.
	 </value>
	*/
	public double getReplayGainAlbumGain()
	{
		return Double.NaN;
	}
	public void setReplayGainAlbumGain(double value)
	{
	}

	/** 
		Gets and sets the ReplayGain album peak sample.
	 
	 <value>
		A <see cref="bool" /> value for the album peak as per the
		ReplayGain specification.
	 </value>
	*/
	public double getReplayGainAlbumPeak()
	{
		return Double.NaN;
	}
	public void setReplayGainAlbumPeak(double value)
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
	 
		<p>Typically, this value is used to store an album
		cover or icon to use for the file, but it is capable of
		holding any type of image, including pictures of the
		band, the recording studio, the concert, etc.</p>
	 
	*/
	public IPicture[] getPictures()
	{
		return new Picture [] {};
	}
	public void setPictures(IPicture[] value)
	{
	}

	/** 
		Gets and sets the same value as <see cref="Performers"
		/>.
	 
	 <value>
		The same value as <see cref="Performers" />.
	 </value>
	 
		This property exists to aleviate confusion. Use <see
		cref="Performers" /> for track artists and <see
		cref="AlbumArtists" /> for album artists.
	 
	*/
	@Deprecated
	public String[] getArtists()
	{
		return getPerformers();
	}
	@Deprecated
	public void setArtists(String[] value)
	{
		setPerformers(value);
	}

	/** 
		Gets the same value as <see cref="FirstPerformer" />.
	 
	 <value>
		The same value as <see cref="FirstPerformer" />.
	 </value>
	 
		This property exists to aleviate confusion. Use <see
		cref="FirstPerformer" /> for track artists and <see
		cref="FirstAlbumArtist" /> for album artists.
	 
	*/
	@Deprecated
	public final String getFirstArtist()
	{
		return getFirstPerformer();
	}

	/** 
		Gets the first value contained in <see
		cref="AlbumArtists" />.
	 
	 <value>
		The first <see cref="string" /> object in <see
		cref="AlbumArtists" />, or <see langword="null" /> is it
		contains no values.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="AlbumArtists" /> to set the value.
	 
	*/
	public final String getFirstAlbumArtist()
	{
		return FirstInGroup(getAlbumArtists());
	}

	/** 
		Gets the first value contained in <see
		cref="AlbumArtistsSort" />.
	 
	 <value>
		The first <see cref="string" /> object in <see
		cref="AlbumArtistsSort" />, or <see langword="null" /> is it
		contains no values.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="AlbumArtistsSort" /> to set the value.
	 
	*/
	public final String getFirstAlbumArtistSort()
	{
		return FirstInGroup(getAlbumArtistsSort());
	}

	/** 
		Gets the first value contained in <see
		cref="Performers" />.
	 
	 <value>
		The first <see cref="string" /> object in <see
		cref="Performers" />, or <see langword="null" /> is it
		contains no values.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="Performers" /> to set the value.
	 
	*/
	public final String getFirstPerformer()
	{
		return FirstInGroup(getPerformers());
	}

	/** 
		Gets the first value contained in <see
		cref="PerformersSort" />.
	 
	 <value>
		The first <see cref="string" /> object in <see
		cref="PerformersSort" />, or <see langword="null" /> is it
		contains no values.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="PerformersSort" /> to set the value.
	 
	*/
	public final String getFirstPerformerSort()
	{
		return FirstInGroup(getPerformersSort());
	}

	/** 
		Gets the first value contained in <see
		cref="ComposersSort" />.
	 
	 <value>
		The first <see cref="string" /> object in <see
		cref="ComposersSort" />, or <see langword="null" /> is it
		contains no values.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="ComposersSort" /> to set the value.
	 
	*/
	public final String getFirstComposerSort()
	{
		return FirstInGroup(getComposersSort());
	}

	/** 
		Gets the first value contained in <see
		cref="Composers" />.
	 
	 <value>
		The first <see cref="string" /> object in <see
		cref="Composers" />, or <see langword="null" /> is it
		contains no values.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="Composers" /> to set the value.
	 
	*/
	public final String getFirstComposer()
	{
		return FirstInGroup(getComposers());
	}

	/** 
		Gets the first value contained in <see cref="Genres" />.
	 
	 <value>
		The first <see cref="string" /> object in <see
		cref="Genres" />, or <see langword="null" /> is it
		contains no values.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="Genres" /> to set the value.
	 
	*/
	public final String getFirstGenre()
	{
		return FirstInGroup(getGenres());
	}

	/** 
		Gets the same value as <see cref="JoinedPerformers" />.
	 
	 <value>
		The same value as <see cref="JoinedPerformers" />.
	 </value>
	 
		This property exists to aleviate confusion. Use <see
		cref="JoinedPerformers" /> for track artists and <see
		cref="JoinedAlbumArtists" /> for album artists.
	 
	*/
	@Deprecated
	public final String getJoinedArtists()
	{
		return getJoinedPerformers();
	}

	/** 
		Gets a semicolon separated string containing the values
		in <see cref="AlbumArtists" />.
	 
	 <value>
		A semicolon separated <see cref="string" /> object
		containing the values in <see cref="AlbumArtists" />.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="AlbumArtists" /> to set the value.
	 
	*/
	public final String getJoinedAlbumArtists()
	{
		return JoinGroup(getAlbumArtists());
	}

	/** 
		Gets a semicolon separated string containing the values
		in <see cref="Performers" />.
	 
	 <value>
		A semicolon separated <see cref="string" /> object
		containing the values in <see cref="Performers" />.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="Performers" /> to set the value.
	 
	*/
	public final String getJoinedPerformers()
	{
		return JoinGroup(getPerformers());
	}

	/** 
		Gets a semicolon separated string containing the values
		in <see cref="PerformersSort" />.
	 
	 <value>
		A semicolon separated <see cref="string" /> object
		containing the values in <see cref="PerformersSort" />.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="PerformersSort" /> to set the value.
	 
	*/
	public final String getJoinedPerformersSort()
	{
		return JoinGroup(getPerformersSort());
	}

	/** 
		Gets a semicolon separated string containing the values
		in <see cref="Composers" />.
	 
	 <value>
		A semicolon separated <see cref="string" /> object
		containing the values in <see cref="Composers" />.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="Composers" /> to set the value.
	 
	*/
	public final String getJoinedComposers()
	{
		return JoinGroup(getComposers());
	}

	/** 
		Gets a semicolon separated string containing the values
		in <see cref="Genres" />.
	 
	 <value>
		A semicolon separated <see cref="string" /> object
		containing the values in <see cref="Genres" />.
	 </value>
	 
		This property is provided for convenience. Use <see
		cref="Genres" /> to set the value.
	 
	*/
	public final String getJoinedGenres()
	{
		return JoinGroup(getGenres());
	}

	/** 
		Gets the first string in an array.
	 
	 @param group
		A <see cref="string[]" /> to get the first string from.
	 
	 @return 
		The first <see cref="string" /> object contained in
		<paramref name="group" />, or <see langword="null" /> if
		the array is <see langword="null" /> or empty.
	 
	*/
	private static String FirstInGroup(String[] group)
	{
		return group == null || group.length == 0 ? null : group [0];
	}

	/** 
		Joins a array of strings into a single, semicolon
		separated, string.
	 
	 @param group
		A <see cref="string[]" /> containing values to combine.
	 
	 @return 
		A semicolon separated <see cref="string" /> object
		containing the values from <paramref name="group" />.
	 
	*/
	private static String JoinGroup(String[] group)
	{
		if (group == null)
		{
			return null;
		}

		return tangible.StringHelper.join("; ", group);
	}

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if the current instance does not
		any values. Otherwise <see langword="false" />.
	 </value>
	 
		In the default implementation, this checks the values
		supported by <see cref="Tag" />, but it may be extended
		by child classes to support other values.
	 
	*/
	public boolean getIsEmpty()
	{
		return IsNullOrLikeEmpty(getTitle()) && IsNullOrLikeEmpty(getGrouping()) && IsNullOrLikeEmpty(getAlbumArtists()) && IsNullOrLikeEmpty(getPerformers()) && IsNullOrLikeEmpty(getComposers()) && IsNullOrLikeEmpty(getConductor()) && IsNullOrLikeEmpty(getCopyright()) && IsNullOrLikeEmpty(getAlbum()) && IsNullOrLikeEmpty(getComment()) && IsNullOrLikeEmpty(getGenres()) && getYear() == 0 && getBeatsPerMinute() == 0 && getTrack() == 0 && getTrackCount() == 0 && getDisc() == 0 && getDiscCount() == 0;
	}

	/** 
		Clears the values stored in the current instance.
	 
	 
		The clearing procedure is format specific and should
		clear all values.
	 
	*/
	public abstract void Clear();

	/** 
		Copies all standard values from one tag to another,
		optionally overwriting existing values.
	 
	 @param source
		A <see cref="Tag" /> object containing the source tag to
		copy the values from.
	 
	 @param target
		A <see cref="Tag" /> object containing the target tag to
		copy values to.
	 
	 @param overwrite
		A <see cref="bool" /> specifying whether or not to copy
		values over existing one.
	 
	 
		<p>This method only copies the most basic values,
		those contained in this class, between tags. To copy
		format specific tags, or additional details, additional
		implementations need to be applied. For example, copying
		from one <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag" /> to another:
		<c>foreach (Rasad.Core.Media.MediaMetadataManagement.Id3v2.Frame frame in old_tag)
		new_tag.AddFrame (frame);</c></p>
	 
	 @exception ArgumentNullException
		<paramref name="source" /> or <paramref name="target" />
		is <see langword="null" />.
	 
	*/
	@Deprecated
	public static void Duplicate(Tag source, Tag target, boolean overwrite)
	{
		if (source == null)
		{
			throw new NullPointerException("source");
		}

		if (target == null)
		{
			throw new NullPointerException("target");
		}

		source.CopyTo(target, overwrite);
	}

	/** 
		Copies the values from the current instance to another
		<see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" />, optionally overwriting
		existing values.
	 
	 @param target
		A <see cref="Tag" /> object containing the target tag to
		copy values to.
	 
	 @param overwrite
		A <see cref="bool" /> specifying whether or not to copy
		values over existing one.
	 
	 
		<p>This method only copies the most basic values when
		copying between different tag formats, however, if
		<paramref name="target" /> is of the same type as the
		current instance, more advanced copying may be done.
		For example, <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag" /> will copy
		all of its frames to another tag.</p>
	 
	 @exception ArgumentNullException
		<paramref name="target" /> is <see langword="null" />.
	 
	*/
	public void CopyTo(Tag target, boolean overwrite)
	{
		if (target == null)
		{
			throw new NullPointerException("target");
		}

		if (overwrite || IsNullOrLikeEmpty(target.getTitle()))
		{
			target.setTitle(getTitle());
		}

		if (overwrite || IsNullOrLikeEmpty(target.getAlbumArtists()))
		{
			target.setAlbumArtists(getAlbumArtists());
		}

		if (overwrite || IsNullOrLikeEmpty(target.getPerformers()))
		{
			target.setPerformers(getPerformers());
		}

		if (overwrite || IsNullOrLikeEmpty(target.getComposers()))
		{
			target.setComposers(getComposers());
		}

		if (overwrite || IsNullOrLikeEmpty(target.getAlbum()))
		{
			target.setAlbum(getAlbum());
		}

		if (overwrite || IsNullOrLikeEmpty(target.getComment()))
		{
			target.setComment(getComment());
		}

		if (overwrite || IsNullOrLikeEmpty(target.getGenres()))
		{
			target.setGenres(getGenres());
		}

		if (overwrite || target.getYear() == 0)
		{
			target.setYear(getYear());
		}

		if (overwrite || target.getTrack() == 0)
		{
			target.setTrack(getTrack());
		}

		if (overwrite || target.getTrackCount() == 0)
		{
			target.setTrackCount(getTrackCount());
		}

		if (overwrite || target.getDisc() == 0)
		{
			target.setDisc(getDisc());
		}

		if (overwrite || target.getDiscCount() == 0)
		{
			target.setDiscCount(getDiscCount());
		}

		if (overwrite || target.getBeatsPerMinute() == 0)
		{
			target.setBeatsPerMinute(getBeatsPerMinute());
		}

		if (overwrite || IsNullOrLikeEmpty(target.getGrouping()))
		{
			target.setGrouping(getGrouping());
		}

		if (overwrite || IsNullOrLikeEmpty(target.getConductor()))
		{
			target.setConductor(getConductor());
		}

		if (overwrite || IsNullOrLikeEmpty(target.getCopyright()))
		{
			target.setCopyright(getCopyright());
		}
	}

	/** 
		Checks if a <see cref="string" /> is <see langword="null"
		/> or contains only whitespace characters.
	 
	 @param value
		A <see cref="string" /> object to check.
	 
	 @return 
		<see langword="true" /> if the string is <see
		langword="null" /> or contains only whitespace
		characters. Otherwise <see langword="false" />.
	 
	*/
	private static boolean IsNullOrLikeEmpty(String value)
	{
		return value == null || value.trim().length() == 0;
	}

	/** 
		Checks if all the strings in the array return <see
		langword="true" /> with <see
		cref="IsNullOrLikeEmpty(string)" /> or if the array is
		<see langword="null" /> or is empty.
	 
	 @param value
		A <see cref="string[]" /> to check the contents of.
	 
	 @return 
		<see langword="true" /> if the array is <see
		langword="null" /> or empty, or all elements return <see
		langword="true" /> for <see
		cref="IsNullOrLikeEmpty(string)" />. Otherwise <see
		langword="false" />.
	 
	*/
	private static boolean IsNullOrLikeEmpty(String[] value)
	{
		if (value == null)
		{
			return true;
		}

		for (String s : value)
		{
			if (!IsNullOrLikeEmpty(s))
			{
				return false;
			}
		}

		return true;
	}
}