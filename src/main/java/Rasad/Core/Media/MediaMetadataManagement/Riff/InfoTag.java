package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// InfoTag.cs:
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
	This class extends <see cref="ListTag" /> to provide support for
	reading and writing standard INFO tags.
*/
public class InfoTag extends ListTag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="InfoTag" /> with no contents.
	*/
	public InfoTag()
	{
		super();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="InfoTag" /> by reading the contents of a raw
		RIFF list stored in a <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector"/> containing a raw RIFF list to
		read into the new instance.
	 
	*/
	public InfoTag(ByteVector data)
	{
		super(data);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="InfoTag" /> by reading the contents of a raw RIFF
		list from a specified position in a <see
		cref="Rasad.Core.Media.MediaMetadataManagement.File"/>.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		from which the contents of the new instance is to be
		read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read the list.
	 
	 @param length
		A <see cref="int" /> value specifying the number of bytes
		to read.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	*/
	public InfoTag(Rasad.Core.Media.MediaMetadataManagement.File file, long position, int length)
	{
		super(file, position, length);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance enclosed in a "INFO" item.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the rendered
		version of the current instance.
	 
	*/
	@Override
	public ByteVector RenderEnclosed()
	{
	return RenderEnclosed("INFO");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.RiffInfo" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.RiffInfo;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "INAM" item.
	 
	*/
	@Override
	public String getTitle()
	{
		for (String s : GetValuesAsStrings("INAM"))
		{
			if (!tangible.StringHelper.isNullOrEmpty(s))
			{
				return s;
			}
		}

		return null;
	}
	@Override
	public void setTitle(String value)
	{
		SetValue("INAM", value);
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
	 
		This property is implemented using the "ISTR" item.
	 
	*/
	@Override
	public String[] getPerformers()
	{
		return GetValuesAsStrings("ISTR");
	}
	@Override
	public void setPerformers(String[] value)
	{
		SetValue("ISTR", value);
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
	 
		This property is implemented using the "IART" item.
	 
	*/
	@Override
	public String[] getAlbumArtists()
	{
		return GetValuesAsStrings("IART");
	}
	@Override
	public void setAlbumArtists(String[] value)
	{
		SetValue("IART", value);
	}

	/** 
		Gets and sets the composers of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the composers of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "IWRI" item.
	 
	*/
	@Override
	public String[] getComposers()
	{
		return GetValuesAsStrings("IWRI");
	}
	@Override
	public void setComposers(String[] value)
	{
		SetValue("IWRI", value);
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "ICMT" item.
	 
	*/
	@Override
	public String getComment()
	{
		for (String s : GetValuesAsStrings("ICMT"))
		{
			if (!tangible.StringHelper.isNullOrEmpty(s))
			{
				return s;
			}
		}

		return null;
	}
	@Override
	public void setComment(String value)
	{
		SetValue("ICMT", value);
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "IGNR" item.
	 
	*/
	@Override
	public String[] getGenres()
	{
		return GetValuesAsStrings("IGNR");
	}
	@Override
	public void setGenres(String[] value)
	{
		SetValue("IGNR", value);
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		This property is implemented using the "ICRD" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
		return GetValueAsUInt("ICRD");
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setYear(uint value)
	@Override
	public void setYear(int value)
	{
		SetValue("ICRD", value);
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	 
		This property is implemented using the "IPRT" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		return GetValueAsUInt("IPRT");
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
		SetValue("IPRT", value);
	}

	/** 
		Gets and sets the number of tracks in the album
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of tracks in
		the album containing the media represented by the current
		instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "IFRM" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		return GetValueAsUInt("IFRM");
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrackCount(uint value)
	@Override
	public void setTrackCount(int value)
	{
		SetValue("IFRM", value);
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "ICOP" item.
	 
	*/
	@Override
	public String getCopyright()
	{
		for (String s : GetValuesAsStrings("ICOP"))
		{
			if (!tangible.StringHelper.isNullOrEmpty(s))
			{
				return s;
			}
		}

		return null;
	}
	@Override
	public void setCopyright(String value)
	{
		SetValue("ICOP", value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}