package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// ContentDescriptionObject.cs: Provides a representation of an ASF Content
// Description object which can be read from and written to disk.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2006-2007 Brian Nickel
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
	This class extends <see cref="Object" /> to provide a
	representation of an ASF Content Description object which can be
	read from and written to disk.
*/
public class ContentDescriptionObject extends Object
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the media title.
	*/
	private String title = "";

	/** 
		Contains the author/performer.
	*/
	private String author = "";

	/** 
		Contains the copyright information.
	*/
	private String copyright = "";

	/** 
		Contains the description of the media.
	*/
	private String description = "";

	/** 
		Contains the rating of the media.
	*/
	private String rating = "";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="ContentDescriptionObject" /> by reading the
		contents from a specified position in a specified file.
	 
	 @param file
		A <see cref="Asf.File" /> object containing the file from
		which the contents of the new instance are to be read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read the object.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	 @exception CorruptFileException
		The object read from disk does not have the correct GUID
		or smaller than the minimum size.
	 
	*/
	public ContentDescriptionObject(Asf.File file, long position)
	{
		super(file, position);
		if (!Asf.Guid.AsfContentDescriptionObject.equals(System.Guid))
		{
			throw new CorruptFileException("Object GUID incorrect.");
		}

		if (getOriginalSize() < 34)
		{
			throw new CorruptFileException("Object size too small.");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort title_length = file.ReadWord();
		short title_length = file.ReadWord();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort author_length = file.ReadWord();
		short author_length = file.ReadWord();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort copyright_length = file.ReadWord();
		short copyright_length = file.ReadWord();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort description_length = file.ReadWord();
		short description_length = file.ReadWord();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort rating_length = file.ReadWord();
		short rating_length = file.ReadWord();

		title = file.ReadUnicode(title_length);
		author = file.ReadUnicode(author_length);
		copyright = file.ReadUnicode(copyright_length);
		description = file.ReadUnicode(description_length);
		rating = file.ReadUnicode(rating_length);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ContentDescriptionObject" /> with no contents.
	*/
	public ContentDescriptionObject()
	{
		super(Asf.Guid.AsfContentDescriptionObject);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Region

	/** 
		Gets and sets the title of the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title of
		the media or <see langword="null" /> if it is not set.
	 </value>
	*/
	public final String getTitle()
	{
		return title.length() == 0 ? null : title;
	}
	public final void setTitle(String value)
	{
		title = tangible.StringHelper.isNullOrEmpty(value) ? "" : value;
	}

	/** 
		Gets and sets the author or performer of the media
		described by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the author of
		the media or <see langword="null" /> if it is not set.
	 </value>
	*/
	public final String getAuthor()
	{
		return author.length() == 0 ? null : author;
	}
	public final void setAuthor(String value)
	{
		author = tangible.StringHelper.isNullOrEmpty(value) ? "" : value;
	}

	/** 
		Gets and sets the copyright information for the media
		described by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media or <see langword="null" /> if
		it is not set.
	 </value>
	*/
	public final String getCopyright()
	{
		return copyright.length() == 0 ? null : copyright;
	}
	public final void setCopyright(String value)
	{
		copyright = tangible.StringHelper.isNullOrEmpty(value) ? "" : value;
	}

	/** 
		Gets and sets the description of the media described by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the media or <see langword="null" /> if it is not set.
	 </value>
	*/
	public final String getDescription()
	{
		return description.length() == 0 ? null : description;
	}
	public final void setDescription(String value)
	{
		description = tangible.StringHelper.isNullOrEmpty(value) ? "" : value;
	}

	/** 
		Gets and sets the rating of the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a rating of the
		media or <see langword="null" /> if it is not set.
	 </value>
	*/
	public final String getRating()
	{
		return rating.length() == 0 ? null : rating;
	}
	public final void setRating(String value)
	{
		rating = tangible.StringHelper.isNullOrEmpty(value) ? "" : value;
	}

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if all the values are cleared.
		Otherwise <see langword="false" />.
	 </value>
	*/
	public final boolean getIsEmpty()
	{
		return title.length() == 0 && author.length() == 0 && copyright.length() == 0 && description.length() == 0 && rating.length() == 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Region

	/** 
		Renders the current instance as a raw ASF object.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	@Override
	public ByteVector Render()
	{
		ByteVector title_bytes = RenderUnicode(title);
		ByteVector author_bytes = RenderUnicode(author);
		ByteVector copyright_bytes = RenderUnicode(copyright);
		ByteVector description_bytes = RenderUnicode(description);
		ByteVector rating_bytes = RenderUnicode(rating);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector output = RenderWord((ushort) title_bytes.Count);
		ByteVector output = RenderWord((short) title_bytes.size());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(RenderWord((ushort) author_bytes.Count));
		output.add(RenderWord((short) author_bytes.size()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(RenderWord((ushort) copyright_bytes.Count));
		output.add(RenderWord((short) copyright_bytes.size()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(RenderWord((ushort) description_bytes.Count));
		output.add(RenderWord((short) description_bytes.size()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(RenderWord((ushort) rating_bytes.Count));
		output.add(RenderWord((short) rating_bytes.size()));
		output.add(title_bytes);
		output.add(author_bytes);
		output.add(copyright_bytes);
		output.add(description_bytes);
		output.add(rating_bytes);

		return Render(output);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}