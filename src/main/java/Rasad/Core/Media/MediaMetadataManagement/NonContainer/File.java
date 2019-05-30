package Rasad.Core.Media.MediaMetadataManagement.NonContainer;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// File.cs: Provides tagging and properties for files that contain an
// indeterminite  number of tags at their beginning or end.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
// 
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
// 
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//



/** 
	This abstract class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> to provide
	tagging and properties for files that contain an indeterminite
	number of tags at their beginning or end.
 
 
	<p>When extending this class, <see cref="ReadStart" />, <see
	cref="ReadEnd" />, and <see cref="ReadProperties" /> should be
	overrided methods that read the format specific information from
	the file.</p>
	<p>The file is read upon construction in the following
	manner:</p>
	<list type="number">
	   <item><term>The file is opened for reading.</term></item>
	   <item><term>The tags at the start of the file are
	   read.</term></item>
	   <item><term><see cref="ReadStart" /> is called.</term></item>
	   <item><term>The tags at the end of the file are
	   read.</term></item>
	   <item><term><see cref="ReadEnd" /> is called.</term></item>
	   <item><term>If reading with a style other than <see
	   cref="ReadStyle.None" />, <see cref="ReadProperties" /> is
	   called.</term></item>
	   <item><term>The file is closed.</term></item>
	</list>
 
*/
public abstract class File extends Rasad.Core.Media.MediaMetadataManagement.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the tags.
	*/
	private Rasad.Core.Media.MediaMetadataManagement.NonContainer.Tag tag;

	/** 
		Contains the media properties.
	*/
	private Properties properties;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system and specified read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	protected File(String path, ReadStyle propertiesStyle)
	{
		super(path);
		Read(propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system with an average read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	protected File(String path)
	{
		this(path, ReadStyle.Average);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction and
		specified read style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	protected File(File.IFileAbstraction abstraction, ReadStyle propertiesStyle)
	{
		super(abstraction);
		Read(propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction with an
		average read style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	protected File(File.IFileAbstraction abstraction)
	{
		this(abstraction, ReadStyle.Average);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets a abstract representation of all tags stored in the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> object representing all tags
		stored in the current instance.
	 </value>
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag getTag()
	{
		return tag;
	}

	/** 
		Gets the media properties of the file represented by the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Properties" /> object containing the
		media properties of the file represented by the current
		instance.
	 </value>
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Properties getProperties()
	{
		return properties;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Saves the changes made in the current instance to the
		file it represents.
	*/
	@Override
	public void Save()
	{
		long start, end;
		Mode = AccessMode.Write;
		try
		{
			tangible.OutObject<Long> tempOut_start = new tangible.OutObject<Long>();
			tangible.OutObject<Long> tempOut_end = new tangible.OutObject<Long>();
			tag.Write(tempOut_start, tempOut_end);
		end = tempOut_end.argValue;
		start = tempOut_start.argValue;
			InvariantStartPosition = start;
			InvariantEndPosition = end;
			TagTypesOnDisk = TagTypes;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}
	}

	/** 
		Removes a set of tag types from the current instance.
	 
	 @param types
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value
		containing tag types to be removed from the file.
	 
	 
		In order to remove all tags from a file, pass <see
		cref="TagTypes.AllTags" /> as <paramref name="types" />.
	 
	*/
	@Override
	public void RemoveTags(TagTypes types)
	{
		tag.RemoveTags(types);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Properties

	/** 
		Gets the collection of tags appearing at the start of the
		file.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.NonContainer.StartTag" /> storing the
		tags for the start of the file.
	 </value>
	*/
	protected final StartTag getStartTag()
	{
		return tag.getStartTag();
	}

	/** 
		Gets the collection of tags appearing at the end of the
		file.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.NonContainer.EndTag" /> storing the
		tags for the end of the file.
	 </value>
	*/
	protected final EndTag getEndTag()
	{
		return tag.getEndTag();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Reads format specific information at the start of the
		file.
	 
	 @param start
		A <see cref="long" /> value containing the seek position
		at which the tags end and the media data begins.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 
		This method is called by the constructor immediately
		after the tags at the start of the file have been read
		and as such (so the internal seek mechanism is close to
		the start). It should be used for reading any content
		specific information, such as an audio header from the
		start of the file.
	 
	*/
	protected void ReadStart(long start, ReadStyle propertiesStyle)
	{
	}

	/** 
		Reads format specific information at the end of the
		file.
	 
	 @param end
		A <see cref="long" /> value containing the seek position
		at which the media data ends and the tags begin.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 
		This method is called by the constructor immediately
		after the tags at the end of the file have been read
		and as such (so the internal seek mechanism is close to
		the end). It should be used for reading any content
		specific information, such as an audio header from the
		end of the file.
	 
	*/
	protected void ReadEnd(long end, ReadStyle propertiesStyle)
	{
	}

	/** 
		Reads the audio properties from the file represented by
		the current instance.
	 
	 @param start
		A <see cref="long" /> value containing the seek position
		at which the tags end and the media data begins.
	 
	 @param end
		A <see cref="long" /> value containing the seek position
		at which the media data ends and the tags begin.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @return 
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Properties" /> object describing the
		media properties of the file represented by the current
		instance.
	 
	 
		This method is called ONLY IF the file is constructed
		with a read style other than <see cref="ReadStyle.None"
		/>, and as such MUST NOT return <see langword="null" />.
		It is guaranteed that <see cref="ReadStart" /> and <see
		cref="ReadEnd" /> will have been called first and this
		method should be strictly used to perform final
		processing on already read data.
	 
	*/
	protected abstract Properties ReadProperties(long start, long end, ReadStyle propertiesStyle);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Reads the file with a specified read style.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	private void Read(ReadStyle propertiesStyle)
	{
		Mode = AccessMode.Read;
		try
		{
			tag = new Tag(this);

			// Read the tags and property data at the beginning of
			// the file.
			InvariantStartPosition = tag.ReadStart();
			TagTypesOnDisk |= getStartTag().getTagTypes();
			ReadStart(InvariantStartPosition, propertiesStyle);

			// Read the tags and property data at the end of the
			// file.
			InvariantEndPosition = (InvariantStartPosition == Length) ? Length : tag.ReadEnd();
			TagTypesOnDisk |= getEndTag().getTagTypes();
			ReadEnd(InvariantEndPosition, propertiesStyle);

			// Read the audio properties.
			properties = (propertiesStyle != ReadStyle.None) ? ReadProperties(InvariantStartPosition, InvariantEndPosition, propertiesStyle) : null;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}