package Rasad.Core.Media.MediaMetadataManagement.Gif;

import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// GifCommentTag.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009 Ruben Vermeersch
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
	Contains the Gif comment.
*/
public class GifCommentTag extends ImageTag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructor.
	 
	 @param value
		The value of the comment.
	 
	*/
	public GifCommentTag(String value)
	{
		setValue(value);
	}

	/** 
		Constructor. Creates a new empty comment.
	*/
	public GifCommentTag()
	{
		setValue(null);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		The value of the comment represented by the current instance.
	*/
	private String Value;
	public final String getValue()
	{
		return Value;
	}
	public final void setValue(String value)
	{
		Value = value;
	}

	/** 
		Gets or sets the comment for the image described
		by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the comment of the
		current instace.
	 </value>
	*/
	@Override
	public String getComment()
	{
		return getValue();
	}
	@Override
	public void setComment(String value)
	{
		setValue(value);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.GifComment" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.GifComment;
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		setValue(null);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}