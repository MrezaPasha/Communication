package Rasad.Core.Media.MediaMetadataManagement.NonContainer;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// StartTag.cs: Provides support for accessing and modifying a collection of
// tags appearing at the start of a file.
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
	This class extends <see cref="CombinedTag" />, providing support
	for accessing and modifying a collection of tags appearing at the
	start of a file.
 
 
	<p>This class is used by <see cref="Rasad.Core.Media.MediaMetadataManagement.NonContainer.File"
	/> to read all the tags appearing at the start of the file but
	could be used by other classes. It currently supports ID3v2
	and APE tags.</p>
 
*/
public class StartTag extends CombinedTag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the file to operate on.
	*/
	private Rasad.Core.Media.MediaMetadataManagement.File file;

	/** 
		Contains the number of bytes that must be read to
		hold all applicable indicators.
	*/
	private int read_size = (int) Math.max(Rasad.Core.Media.MediaMetadataManagement.Ape.Footer.Size, Rasad.Core.Media.MediaMetadataManagement.Id3v2.Header.Size);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="StartTag" /> for a specified <see
		cref="Rasad.Core.Media.MediaMetadataManagement.File" />.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object on which the new
		instance will perform its operations.
	 
	 
		Constructing a new instance does not automatically read
		the contents from the disk. <see cref="Read" /> must be
		called to read the tags.
	 
	*/
	public StartTag(Rasad.Core.Media.MediaMetadataManagement.File file)
	{
		super();
		this.file = file;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the total size of the tags located at the end of the
		file by reading from the file.
	*/
	public final long getTotalSize()
	{
		long size = 0;

		tangible.RefObject<Long> tempRef_size = new tangible.RefObject<Long>(size);
		while (ReadTagInfo(tempRef_size) != TagTypes.None)
		{
		size = tempRef_size.argValue;
			;
		}
	size = tempRef_size.argValue;

		return size;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Reads the tags stored at the start of the file into the
		current instance.
	 
	 @return 
		A <see cref="long" /> value indicating the seek position
		in the file at which the read tags end. This also
		marks the seek position at which the media begins.
	 
	*/
	public final long Read()
	{
		Rasad.Core.Media.MediaMetadataManagement.Tag tag;
		ClearTags();
		long end = 0;

		tangible.RefObject<Long> tempRef_end = new tangible.RefObject<Long>(end);
		while ((tag = ReadTag(tempRef_end)) != null)
		{
		end = tempRef_end.argValue;
			AddTag(tag);
		}
	end = tempRef_end.argValue;

		return end;
	}

	/** 
		Renders the tags contained in the current instance.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		physical representation of the tags stored in the current
		instance.
	 
	 
		The tags are rendered in the order that they are stored
		in the current instance.
	 
	*/
	public final ByteVector Render()
	{
		ByteVector data = new ByteVector();
		for (Rasad.Core.Media.MediaMetadataManagement.Tag t : getTags())
		{
			if (t instanceof Rasad.Core.Media.MediaMetadataManagement.Ape.Tag)
			{
				data.add((t instanceof Rasad.Core.Media.MediaMetadataManagement.Ape.Tag ? (Rasad.Core.Media.MediaMetadataManagement.Ape.Tag)t : null).Render());
			}
			else if (t instanceof Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag)
			{
				data.add((t instanceof Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag ? (Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag)t : null).Render());
			}
		}

		return data;
	}

	/** 
		Writes the tags contained in the current instance to the
		beginning of the file that created it, overwriting the
		existing tags.
	 
	 @return 
		A <see cref="long" /> value indicating the seek position
		in the file at which the written tags end. This also
		marks the seek position at which the media begins.
	 
	*/
	public final long Write()
	{
		ByteVector data = Render();
		file.Insert(data, 0, getTotalSize());
		return data.size();
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
		for (int i = getTags().length - 1; i >= 0; i--)
		{
			Rasad.Core.Media.MediaMetadataManagement.Tag tag = getTags()[i];
			if (types == TagTypes.AllTags || (tag.getTagTypes().getValue() & types.getValue()) == tag.getTagTypes().getValue())
			{
				RemoveTag(tag);
			}
		}
	}

	/** 
		Adds a tag of a specified type to the current instance,
		optionally copying values from an existing type.
	 
	 @param type
		A <see cref="TagTypes" /> value specifying the type of
		tag to add to the current instance. At the time of this
		writing, this is limited to <see cref="TagTypes.Ape" />
		and <see cref="TagTypes.Id3v2" />.
	 
	 @param copy
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> to copy values from using
		<see cref="Rasad.Core.Media.MediaMetadataManagement.Tag.CopyTo" />, or <see
		langword="null" /> if no tag is to be copied.
	 
	 @return 
		The <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> object added to the current
		instance, or <see langword="null" /> if it couldn't be
		created.
	 
	 
		ID3v2 tags are added at the end of the current instance,
		while other tags are added to the beginning.
	 
	*/
	public final Rasad.Core.Media.MediaMetadataManagement.Tag AddTag(TagTypes type, Rasad.Core.Media.MediaMetadataManagement.Tag copy)
	{
		Rasad.Core.Media.MediaMetadataManagement.Tag tag = null;

		if (type == TagTypes.Id3v2)
		{
			tag = new Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag();
		}
		else if (type == TagTypes.Ape)
		{
			tag = new Rasad.Core.Media.MediaMetadataManagement.Ape.Tag();
			(tag instanceof Ape.Tag ? (Ape.Tag)tag : null).setHeaderPresent(true);
		}

		if (tag != null)
		{
			if (copy != null)
			{
				copy.CopyTo(tag, true);
			}

			AddTag(tag);
		}

		return tag;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Reads a tag starting at a specified position and moves the
		cursor to its start position.
	 
	 @param start
		A <see cref="long" /> value reference specifying at what
		position the potential tag starts. If a tag is found,
		this value will be updated to the position at which the
		found tag ends.
	 
	 @return 
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> object representing the tag
		found at the specified position, or <see langword="null"
		/> if no tag was found.
	 
	*/
	private Rasad.Core.Media.MediaMetadataManagement.Tag ReadTag(tangible.RefObject<Long> start)
	{
		long end = start.argValue;
		tangible.RefObject<Long> tempRef_end = new tangible.RefObject<Long>(end);
		TagTypes type = ReadTagInfo(tempRef_end);
	end = tempRef_end.argValue;
		Rasad.Core.Media.MediaMetadataManagement.Tag tag = null;

		switch (type)
		{
			case TagTypes.Ape:
				tag = new Rasad.Core.Media.MediaMetadataManagement.Ape.Tag(file, start.argValue);
				break;
			case TagTypes.Id3v2:
				tag = new Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag(file, start.argValue);
				break;
		}

		start.argValue = end;
		return tag;
	}

	/** 
		Looks for a tag starting at a specified position and moves
		the cursor to its start position.
	 
	 @param position
		A <see cref="long" /> value reference specifying at what
		position the potential tag starts. If a tag is found,
		this value will be updated to the position at which the
		found tag ends.
	 
	 @return 
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value specifying the
		type of tag found at the specified position, or <see
		cref="TagTypes.None" /> if no tag was found.
	 
	*/
	private TagTypes ReadTagInfo(tangible.RefObject<Long> position)
	{
		file.Seek(position.argValue);
		ByteVector data = file.ReadBlock(read_size);

		try
		{
			if (data.StartsWith(Rasad.Core.Media.MediaMetadataManagement.Ape.Footer.FileIdentifier))
			{
				Rasad.Core.Media.MediaMetadataManagement.Ape.Footer footer = new Rasad.Core.Media.MediaMetadataManagement.Ape.Footer(data);

				position.argValue += footer.getCompleteTagSize();
				return TagTypes.Ape;
			}

			if (data.StartsWith(Rasad.Core.Media.MediaMetadataManagement.Id3v2.Header.FileIdentifier))
			{
				Rasad.Core.Media.MediaMetadataManagement.Id3v2.Header header = new Rasad.Core.Media.MediaMetadataManagement.Id3v2.Header(data);

				position.argValue += header.getCompleteTagSize();
				return TagTypes.Id3v2;
			}
		}
		catch (CorruptFileException e)
		{
		}

		return TagTypes.None;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}