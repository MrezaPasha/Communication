package Rasad.Core.Media.MediaMetadataManagement.Audible;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Tag.cs:
//
// Author:
//   Guy Taylor (s0700260@sms.ed.ac.uk) (thebigguy.co.uk@gmail.com)
//
// Original Source:
//   Id3v1/Tag.cs from Rasad.Core.Media.MediaMetadataManagement-sharp
//
// Copyright (C) 2009 Guy Taylor (Original Implementation)
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
	reading tags stored in the Audible Metadata format.
*/
public class Tag extends Rasad.Core.Media.MediaMetadataManagement.Tag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	private ArrayList<Map.Entry<String, String>> tags;

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
		// TODO: can we read from file
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

		Clear();
		Parse(data);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Populates the current instance by parsing the contents of
		a raw AudibleMetadata tag.
	 
	 @param data
			A <see cref="ByteVector" /> object containing the whole tag
	 	object
	 
	 @exception CorruptFileException
		<paramref name="data" /> is less than 128 bytes or does
		not start with <see cref="FileIdentifier" />.
	 
	*/
	private void Parse(ByteVector data)
	{
		String currentKey, currentValue;
		int keyLen, valueLen;

		try
		{
			do
			{
				keyLen = (int) data.ToUInt(true);
				data.subList(0, 4).clear();
				valueLen = (int) data.ToUInt(true);
				data.subList(0, 4).clear();
				currentKey = data.toString(Rasad.Core.Media.MediaMetadataManagement.StringType.UTF8, 0, keyLen);
				data.subList(0, keyLen).clear();
				currentValue = data.toString(Rasad.Core.Media.MediaMetadataManagement.StringType.UTF8, 0, valueLen);
				data.subList(0, valueLen).clear();

				tags.add(new Map.Entry<String, String>(currentKey, currentValue));

				//StringHandle (currentKey, currentValue);

				// if it is not the last item remove the end byte (null terminated)
				if (data.size() != 0)
				{
					data.subList(0, 1).clear();
				}
			} while (data.size() >= 4);
		}
		catch (RuntimeException e)
		{
			//
		}

		if (data.size() != 0)
		{
			throw new CorruptFileException();
		}
	}

	private void setTag(String tagName, String value)
	{
		for (int i = 0; i < tags.size(); i++)
		{
			if (tagName.equals(tags.get(i).getKey()))
			{
				tags.set(i, new Map.Entry<String, String> (tags.get(i).getKey(), value));
			}
		}
	}

	private String getTag(String tagName)
	{
		for (Map.Entry<String, String> tag : tags.entrySet())
		{
			if (tagName.equals(tag.getKey()))
			{
				return tag.getValue();
			}
		}
		return null;
	}

	/*
	/// <summary>
	///		Given a key and value pair it will update the
	///		present metadata.
	/// </summary>
	/// <param name="key">
	///    A <see cref="String" /> containing the key.
	/// </param>
	/// <param name="strValue">
	///    A <see cref="String" /> containing the value.
	/// </param>
	private void StringHandle (String key, String strValue)
	{
		switch (key)
		{
		case "title":
			title = strValue;
			break;
		case "author":
			artist = strValue;
			break;
		case "provider":
			album = strValue;
			break;
		}
		
	}
	*/

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.AudibleMetadata" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.AudibleMetadata;
	}

	public final String getAuthor()
	{
		return getTag("author");
	}

	@Override
	public String getCopyright()
	{
		return getTag("copyright");
	}
	@Override
	public void setCopyright(String value)
	{
		setTag("copyright", value);
	}

	public final String getDescription()
	{
		return getTag("description");
	}

	public final String getNarrator()
	{
		return getTag("narrator");
	}

	/** 
		Gets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	*/
	@Override
	public String getTitle()
	{
		return getTag("title");
	}

	/** 
		Gets the album for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the album for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	*/
	@Override
	public String getAlbum()
	{
		return getTag("provider");
			//return string.IsNullOrEmpty (album) ?
			//	null : album;
	}

	/** 
		Gets the album artist for the media described by the
		current instance.
	 
	 <value>
			A <see cref="string[]" /> object containing a single 
	 	artist described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	*/
	@Override
	public String[] getAlbumArtists()
	{
		String artist = getTag("provider");

		return tangible.StringHelper.isNullOrEmpty(artist) ? null : new String[] {artist};
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		tags = new ArrayList<Map.Entry<String, String>>();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}