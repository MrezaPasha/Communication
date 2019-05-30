package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// MovieIdTag.cs:
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
	reading and writing MovieID tags.
*/
public class MovieIdTag extends ListTag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="MovieIdTag" /> with no contents.
	*/
	public MovieIdTag()
	{
		super();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="MovieIdTag" /> by reading the contents of a raw
		RIFF list stored in a <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector"/> containing a raw RIFF list to
		read into the new instance.
	 
	*/
	public MovieIdTag(ByteVector data)
	{
		super(data);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="MovieIdTag" /> by reading the contents of a raw
		RIFF list from a specified position in a <see
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
	public MovieIdTag(Rasad.Core.Media.MediaMetadataManagement.File file, long position, int length)
	{
		super(file, position, length);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance enclosed in a "MID " item.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the rendered
		version of the current instance.
	 
	*/
	@Override
	public ByteVector RenderEnclosed()
	{
		return RenderEnclosed("MID ");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.MovieId" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.MovieId;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "TITL" item.
	 
	*/
	@Override
	public String getTitle()
	{
		for (String s : GetValuesAsStrings("TITL"))
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
		SetValue("TITL", value);
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
	 
		This property is implemented using the "IART" item.
	 
	*/
	@Override
	public String[] getPerformers()
	{
		return GetValuesAsStrings("IART");
	}
	@Override
	public void setPerformers(String[] value)
	{
		SetValue("IART", value);
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "COMM" item.
	 
	*/
	@Override
	public String getComment()
	{
		for (String s : GetValuesAsStrings("COMM"))
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
		SetValue("COMM", value);
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "GENR" item.
	 
	*/
	@Override
	public String[] getGenres()
	{
		return GetValuesAsStrings("GENR");
	}
	@Override
	public void setGenres(String[] value)
	{
		SetValue("GENR", value);
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	 
		This property is implemented using the "PRT1" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		return GetValueAsUInt("PRT1");
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
		SetValue("PRT1", value);
	}

	/** 
		Gets and sets the number of tracks in the album
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of tracks in
		the album containing the media represented by the current
		instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "PRT2" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		return GetValueAsUInt("PRT2");
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrackCount(uint value)
	@Override
	public void setTrackCount(int value)
	{
		SetValue("PRT2", value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}