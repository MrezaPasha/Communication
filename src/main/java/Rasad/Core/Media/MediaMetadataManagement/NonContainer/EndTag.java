package Rasad.Core.Media.MediaMetadataManagement.NonContainer;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// EndTag.cs: Provides support for accessing and modifying a collection of tags
// appearing at the end of a file.
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
	end of a file.
 
 
	<p>This class is used by <see cref="Rasad.Core.Media.MediaMetadataManagement.NonContainer.File"
	/> to read all the tags appearing at the end of the file but
	could be used by other classes. It currently supports ID3v1,
	ID3v2, and APE tags.</p>
 
*/
public class EndTag extends CombinedTag
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
	private static int read_size = (int) Math.max(Math.max(Rasad.Core.Media.MediaMetadataManagement.Ape.Footer.Size, Rasad.Core.Media.MediaMetadataManagement.Id3v2.Footer.Size), Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag.Size);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="EndTag" /> for a specified <see cref="Rasad.Core.Media.MediaMetadataManagement.File"
		/>.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object on which the new
		instance will perform its operations.
	 
	 
		Constructing a new instance does not automatically read
		the contents from the disk. <see cref="Read" /> must be
		called to read the tags.
	 
	*/
	public EndTag(Rasad.Core.Media.MediaMetadataManagement.File file)
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
		long start = file.getLength();

		tangible.RefObject<Long> tempRef_start = new tangible.RefObject<Long>(start);
		while (ReadTagInfo(tempRef_start) != TagTypes.None)
		{
		start = tempRef_start.argValue;
			;
		}
	start = tempRef_start.argValue;

		return file.getLength() - start;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Reads the tags stored at the end of the file into the
		current instance.
	 
	 @return 
		A <see cref="long" /> value indicating the seek position
		in the file at which the read tags begin. This also
		marks the seek position at which the media ends.
	 
	*/
	public final long Read()
	{
		Rasad.Core.Media.MediaMetadataManagement.Tag tag;
		ClearTags();
		long start = file.getLength();

		tangible.RefObject<Long> tempRef_start = new tangible.RefObject<Long>(start);
		while ((tag = ReadTag(tempRef_start)) != null)
		{
		start = tempRef_start.argValue;
			InsertTag(0, tag);
		}
	start = tempRef_start.argValue;

		return start;
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
			else if (t instanceof Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag)
			{
				data.add((t instanceof Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag ? (Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag)t : null).Render());
			}
		}

		return data;
	}

	/** 
		Writes the tags contained in the current instance to the
		end of the file that created it, overwriting the existing
		tags.
	 
	 @return 
		A <see cref="long" /> value indicating the seek position
		in the file at which the written tags begin. This also
		marks the seek position at which the media ends.
	 
	*/
	public final long Write()
	{
		long total_size = getTotalSize();
		ByteVector data = Render();
		file.Insert(data, file.getLength() - total_size, total_size);
		return file.getLength() - data.size();
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
		writing, this is limited to <see cref="TagTypes.Ape" />,
		<see cref="TagTypes.Id3v1" />, and <see
		cref="TagTypes.Id3v2" />.
	 
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

		if (type == TagTypes.Id3v1)
		{
			tag = new Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag();
		}
		else if (type == TagTypes.Id3v2)
		{
			Id3v2.Tag tag32 = new Id3v2.Tag();
			tag32.setVersion((byte)4);
			tag32.setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.HeaderFlags.forValue(tag32.getFlags().getValue() | Id3v2.HeaderFlags.FooterPresent.getValue()));
			tag = tag32;
		}
		else if (type == TagTypes.Ape)
		{
			tag = new Rasad.Core.Media.MediaMetadataManagement.Ape.Tag();
		}

		if (tag != null)
		{
			if (copy != null)
			{
				copy.CopyTo(tag, true);
			}

			if (type == TagTypes.Id3v1)
			{
				AddTag(tag);
			}
			else
			{
				InsertTag(0, tag);
			}
		}

		return tag;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Reads a tag ending at a specified position and moves the
		cursor to its start position.
	 
	 @param end
		A <see cref="long" /> value reference specifying at what
		position the potential tag ends at. If a tag is found,
		this value will be updated to the position at which the
		found tag starts.
	 
	 @return 
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> object representing the tag
		found at the specified position, or <see langword="null"
		/> if no tag was found.
	 
	*/
	private Rasad.Core.Media.MediaMetadataManagement.Tag ReadTag(tangible.RefObject<Long> end)
	{
		long start = end.argValue;
		tangible.RefObject<Long> tempRef_start = new tangible.RefObject<Long>(start);
		TagTypes type = ReadTagInfo(tempRef_start);
	start = tempRef_start.argValue;
		Rasad.Core.Media.MediaMetadataManagement.Tag tag = null;

		try
		{
			switch (type)
			{
			case TagTypes.Ape:
				tag = new Rasad.Core.Media.MediaMetadataManagement.Ape.Tag(file, end.argValue - Rasad.Core.Media.MediaMetadataManagement.Ape.Footer.Size);
				break;
			case TagTypes.Id3v2:
				tag = new Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag(file, start);
				break;
			case TagTypes.Id3v1:
				tag = new Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag(file, start);
				break;
			}

			end.argValue = start;
		}
		catch (CorruptFileException e)
		{
		}

		return tag;
	}

	/** 
		Looks for a tag ending at a specified position and moves
		the cursor to its start position.
	 
	 @param position
		A <see cref="long" /> value reference specifying at what
		position the potential tag ends. If a tag is found,
		this value will be updated to the position at which the
		found tag starts.
	 
	 @return 
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value specifying the
		type of tag found at the specified position, or <see
		cref="TagTypes.None" /> if no tag was found.
	 
	*/
	private TagTypes ReadTagInfo(tangible.RefObject<Long> position)
	{
		if (position.argValue - read_size < 0)
		{
			return TagTypes.None;
		}

		file.Seek(position.argValue - read_size);
		ByteVector data = file.ReadBlock(read_size);

		try
		{
			int offset = (int)(data.size() - Rasad.Core.Media.MediaMetadataManagement.Ape.Footer.Size);
			if (data.ContainsAt(Rasad.Core.Media.MediaMetadataManagement.Ape.Footer.FileIdentifier, offset))
			{
				Rasad.Core.Media.MediaMetadataManagement.Ape.Footer footer = new Rasad.Core.Media.MediaMetadataManagement.Ape.Footer(data.Mid(offset));

				// If the complete tag size is zero or
				// the tag is a header, this indicates
				// some sort of corruption.
				if (footer.getCompleteTagSize() == 0 || (footer.getFlags().getValue() & Rasad.Core.Media.MediaMetadataManagement.Ape.FooterFlags.IsHeader.getValue()) != 0)
				{
					return TagTypes.None;
				}

				position.argValue -= footer.getCompleteTagSize();
				return TagTypes.Ape;
			}

			offset = (int)(data.size() - Rasad.Core.Media.MediaMetadataManagement.Id3v2.Footer.Size);
			if (data.ContainsAt(Rasad.Core.Media.MediaMetadataManagement.Id3v2.Footer.FileIdentifier, offset))
			{
				Rasad.Core.Media.MediaMetadataManagement.Id3v2.Footer footer = new Rasad.Core.Media.MediaMetadataManagement.Id3v2.Footer(data.Mid(offset));

				position.argValue -= footer.getCompleteTagSize();
				return TagTypes.Id3v2;
			}

			if (data.StartsWith(Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag.FileIdentifier))
			{
				position.argValue -= Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag.Size;
				return TagTypes.Id3v1;
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