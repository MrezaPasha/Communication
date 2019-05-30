package Rasad.Core.Media.MediaMetadataManagement.NonContainer;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Tag.cs: Combines StartTag and EndTag in such a way as their children appear
// as its children.
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
	This class extends <see cref="CombinedTag" />, combining <see
	cref="StartTag" /> and <see cref="EndTag" /> in such a way as
	their children appear as its children.
*/
public class Tag extends CombinedTag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the tags appearing at the start of the file.
	*/
	private StartTag start_tag;

	/** 
		Contains the tags appearing at the end of the file.
	*/
	private EndTag end_tag;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Tag" /> for a specified <see cref="Rasad.Core.Media.MediaMetadataManagement.File" />.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object on which the new
		instance will perform its operations.
	 
	 
		Constructing a new instance does not automatically read
		the contents from the disk. <see cref="Read" /> must be
		called to read the tags.
	 
	*/
	public Tag(File file)
	{
		super();
		start_tag = new StartTag(file);
		end_tag = new EndTag(file);
		AddTag(start_tag);
		AddTag(end_tag);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the collection of tags appearing at the start of the
		file.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.NonContainer.StartTag" /> storing the
		tags for the start of the file.
	 </value>
	*/
	public final StartTag getStartTag()
	{
		return start_tag;
	}

	/** 
		Gets the collection of tags appearing at the end of the
		file.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.NonContainer.EndTag" /> storing the
		tags for the end of the file.
	 </value>
	*/
	public final EndTag getEndTag()
	{
		return end_tag;
	}

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" />
		containing the tag types contained in the current
		instance.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return start_tag.getTagTypes().getValue() | end_tag.getTagTypes().getValue();
	}

	/** 
		Gets the tags combined in the current instance.
	 
	 <value>
		A <see cref="Tag[]" /> containing the tags combined in
		the current instance.
	 </value>
	 
		This contains the combined children of <see
		cref="Tag.StartTag" /> and <see cref="Tag.EndTag" />.
	 
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag[] getTags()
	{
		ArrayList<Rasad.Core.Media.MediaMetadataManagement.Tag> tags = new ArrayList<Rasad.Core.Media.MediaMetadataManagement.Tag> ();
		tags.addAll(Arrays.asList(start_tag.getTags()));
		tags.addAll(Arrays.asList(end_tag.getTags()));
		return tags.toArray(new Rasad.Core.Media.MediaMetadataManagement.Tag[0]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Gets a tag of a specified type from the current instance.
	 
	 @param type
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value indicating the
		type of tag to read.
	 
	 @return 
		A <see cref="Tag" /> object containing the tag that was
		found in the current instance. If no
		matching tag was found and none was created, <see
		langword="null" /> is returned.
	 
	*/
	public final Rasad.Core.Media.MediaMetadataManagement.Tag GetTag(TagTypes type)
	{
		for (Rasad.Core.Media.MediaMetadataManagement.Tag t : getTags())
		{
			if (type == TagTypes.Id3v1 && t instanceof Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag)
			{
				return t;
			}

			if (type == TagTypes.Id3v2 && t instanceof Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag)
			{
				return t;
			}

			if (type == TagTypes.Ape && t instanceof Rasad.Core.Media.MediaMetadataManagement.Ape.Tag)
			{
				return t;
			}
		}

		return null;
	}

	/** 
		Removes a set of tag types from the current instance.
	 
	 @param types
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value
		containing tag types to be removed from the file.
	 
	 
		In order to remove all tags from a file, pass <see
		cref="TagTypes.AllTags" /> as <paramref name="types" />.
	 
	*/
	public final void RemoveTags(TagTypes types)
	{
		start_tag.RemoveTags(types);
		end_tag.RemoveTags(types);
	}

	/** 
		Reads the tags at the start and end of the file.
	 
	 @param start
		A <see cref="long" /> value reference which will be set
		to contain the seek position in the file at which the
		tags at the start end. This also marks the seek position
		at which the media begins.
	 
	 @param end
		A <see cref="long" /> value reference which will be set
		to contain the seek position in the file at which the
		tags at the end begin. This also marks the seek position
		at which the media ends.
	 
	*/
	public final void Read(tangible.OutObject<Long> start, tangible.OutObject<Long> end)
	{
		start.argValue = ReadStart();
		end.argValue = ReadEnd();
	}

	/** 
		Reads the tags stored at the start of the file into the
		current instance.
	 
	 @return 
		A <see cref="long" /> value indicating the seek position
		in the file at which the read tags end. This also
		marks the seek position at which the media begins.
	 
	*/
	public final long ReadStart()
	{
		return start_tag.Read();
	}

	/** 
		Reads the tags stored at the end of the file into the
		current instance.
	 
	 @return 
		A <see cref="long" /> value indicating the seek position
		in the file at which the read tags begin. This also
		marks the seek position at which the media ends.
	 
	*/
	public final long ReadEnd()
	{
		return end_tag.Read();
	}

	/** 
		Writes the tags to the start and end of the file.
	 
	 @param start
		A <see cref="long" /> value reference which will be set
		to contain the new seek position in the file at which the
		tags at the start end. This also marks the seek position
		at which the media begins.
	 
	 @param end
		A <see cref="long" /> value reference which will be set
		to contain the new seek position in the file at which the
		tags at the end begin. This also marks the seek position
		at which the media ends.
	 
	*/
	public final void Write(tangible.OutObject<Long> start, tangible.OutObject<Long> end)
	{
		start.argValue = start_tag.Write();
		end.argValue = end_tag.Write();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}