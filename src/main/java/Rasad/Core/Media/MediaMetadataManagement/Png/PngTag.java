package Rasad.Core.Media.MediaMetadataManagement.Png;

import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;
import java.time.*;

//
// PngTag.cs:
//
// Author:
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2010 Mike Gemuende
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
	Native Png Keywords
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class PngTag extends ImageTag implements java.lang.Iterable
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region defined PNG keywords

	/** 
		Short (one line) title or caption for image
	*/
	public static final String TITLE = "Title";

	/** 
		Name of image's creator
	*/
	public static final String AUTHOR = "Author";

	/** 
		Description of image (possibly long)
	*/
	public static final String DESCRIPTION = "Description";

	/** 
		Copyright notice
	*/
	public static final String COPYRIGHT = "Copyright";

	/** 
		Time of original image creation
	*/
	public static final String CREATION_TIME = "Creation Time";

	/** 
		Software used to create the image
	*/
	public static final String SOFTWARE = "Software";

	/** 
		Legal disclaimer
	*/
	public static final String DISCLAIMER = "Disclaimer";

	/** 
		Warning of nature of content
	*/
	public static final String WARNING = "Warning";

	/** 
		Device used to create the image
	*/
	public static final String SOURCE = "Source";

	/** 
		Miscellaneous comment
	*/
	public static final String COMMENT = "Comment";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fieds

	/** 
		Store the keywords with their values
	*/
	private HashMap<String, String> keyword_store = new HashMap<String, String> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructor.
	*/
	public PngTag()
	{
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets or sets the comment for the image described
		by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the comment of the
		current instace.
	 </value>
	 
		We use here both keywords Description and Comment of the
		PNG specification to store the comment.
	 
	*/
	@Override
	public String getComment()
	{
		String description = GetKeyword(DESCRIPTION);

		if (!tangible.StringHelper.isNullOrEmpty(description))
		{
			return description;
		}

		return GetKeyword(COMMENT);
	}
	@Override
	public void setComment(String value)
	{
		SetKeyword(DESCRIPTION, value);
		SetKeyword(COMMENT, value);
	}

	/** 
		Gets and sets the title for the media described by the
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
		return GetKeyword(TITLE);
	}
	@Override
	public void setTitle(String value)
	{
		SetKeyword(TITLE, value);
	}

	/** 
		Gets or sets the creator of the image.
	 
	 <value>
		A <see cref="string" /> with the name of the creator.
	 </value>
	*/
	@Override
	public String getCreator()
	{
		return GetKeyword(AUTHOR);
	}
	@Override
	public void setCreator(String value)
	{
		SetKeyword(AUTHOR, value);
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	*/
	@Override
	public String getCopyright()
	{
		return GetKeyword(COPYRIGHT);
	}
	@Override
	public void setCopyright(String value)
	{
		SetKeyword(COPYRIGHT, value);
	}

	/** 
		Gets or sets the software the image, the current instance
		belongs to, was created with.
	 
	 <value>
		A <see cref="string" /> containing the name of the
		software the current instace was created with.
	 </value>
	*/
	@Override
	public String getSoftware()
	{
		return GetKeyword(SOFTWARE);
	}
	@Override
	public void setSoftware(String value)
	{
		SetKeyword(SOFTWARE, value);
	}

	/** 
		Gets or sets the time when the image, the current instance
		belongs to, was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the time the image was taken.
	 </value>
	*/
	@Override
	public DateTime getDateTime()
	{
		LocalDateTime ret = LocalDateTime.MIN;
		String date = GetKeyword(CREATION_TIME);

		tangible.OutObject<LocalDateTime> tempOut_ret = new tangible.OutObject<LocalDateTime>();
		if (getDateTime().TryParse(date, tempOut_ret))
		{
		ret = tempOut_ret.argValue;
			return ret;
		}
	else
	{
		ret = tempOut_ret.argValue;
	}

		return null;
	}
	@Override
	public void setDateTime(DateTime value)
	{
		String date = null;

		if (value != null)
		{
				// Creation Date is stored in RFC 822 for PNG
			date = value.getValue().toString("R");
		}

		SetKeyword(CREATION_TIME, date);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Sets a keyword of to the given value.
	 
	 @param keyword
		A <see cref="System.String"/> with the keyword to set.
	 
	 @param value
		A <see cref="System.String"/> with the value.
	 
	*/
	public final void SetKeyword(String keyword, String value)
	{
		if (tangible.StringHelper.isNullOrEmpty(keyword))
		{
			throw new IllegalArgumentException("keyword is null or empty");
		}

		keyword_store.remove(keyword);

		if (value != null)
		{
			keyword_store.put(keyword, value);
		}
	}


	/** 
		Gets a value of a keyword.
	 
	 @param keyword
		A <see cref="System.String"/> with the keyword to get the value for.
	 
	 @return 
		A <see cref="System.String"/> with the value or  <see langword="null" />
		if the keyword is not contained.
	 
	*/
	public final String GetKeyword(String keyword)
	{
		String ret = null;

		ret = keyword_store.get(keyword);

		return ret;
	}


	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.Png" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.Png;
	}


	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		keyword_store.clear();
	}


	/** 
		Returns an enumerator to enumerate all keywords.
	 
	 @return 
		A <see cref="System.Collections.IEnumerator"/> to enumerate
		the keywords.
	 
	*/
	public final Iterator iterator()
	{
		return keyword_store.entrySet().iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}