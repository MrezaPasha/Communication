package Rasad.Core.Media.MediaMetadataManagement.Flac;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
	This class extends <see cref="CombinedTag" /> to provide support
	for reading and writing FLAC metadata boxes.
 
 
	At this point, only Xiph Comments and pictures are supported.
 
*/
public class Metadata extends CombinedTag
{
	/** 
		Contains the pictures.
	*/
	private ArrayList<IPicture> pictures = new ArrayList<IPicture>();

	/** 
		Constructs and initializes a new instance of <see
		cref="Metadata" /> using a collection of blocks.
	 
	 @param blocks
		A <see cref="T:System.Collections.Generic.List`1" /> object containing <see
		cref="Block" /> objects to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="blocks" /> is <see langword="null" />.
	 
	*/
	@Deprecated
	public Metadata(ArrayList<Block> blocks)
	{
		this(blocks instanceof java.lang.Iterable<Block> ? (java.lang.Iterable<Block>)blocks : null);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Metadata" /> using a collection of blocks.
	 
	 @param blocks
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating <see
		cref="Block" /> objects to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="blocks" /> is <see langword="null" />.
	 
	*/
	public Metadata(java.lang.Iterable<Block> blocks)
	{
		if (blocks == null)
		{
			throw new NullPointerException("blocks");
		}

		for (Block block : blocks)
		{
			if (block.getData().isEmpty())
			{
				continue;
			}

			if (block.getType() == BlockType.XiphComment)
			{
				AddTag(new Ogg.XiphComment(block.getData()));
			}
			else if (block.getType() == BlockType.Picture)
			{
				pictures.add(new Picture(block.getData()));
			}
		}
	}

	/** 
		Gets the first Xiph comment stored in the current
		instance, optionally creating one if necessary.
	 
	 @param create
		A <see cref="bool" /> value indicating whether or not a
		comment should be added if one cannot be found.
	 
	 @param copy
		A <see cref="Tag" /> object containing the source tag to
		copy the values from, or <see langword="null" /> to not
		copy values.
	 
	 @return 
		A <see cref="Ogg.XiphComment" /> object containing the
		tag that was found in or added to the current instance.
		If no matching tag was found and none was created, <see
		langword="null" /> is returned.
	 
	*/
	public final Ogg.XiphComment GetComment(boolean create, Tag copy)
	{
		for (Tag t : getTags())
		{
			if (t instanceof Ogg.XiphComment)
			{
				return t instanceof Ogg.XiphComment ? (Ogg.XiphComment)t : null;
			}
		}

		if (!create)
		{
			return null;
		}

		Ogg.XiphComment c = new Ogg.XiphComment();

		if (copy != null)
		{
			copy.CopyTo(c, true);
		}

		AddTag(c);

		return c;
	}

	/** 
		Removes all child Xiph Comments from the current
		instance.
	*/
	public final void RemoveComment()
	{
		Ogg.XiphComment c;

		while ((c = GetComment(false, null)) != null)
		{
			RemoveTag(c);
		}
	}

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value
		containing the tag types stored in the current instance.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.FlacMetadata.getValue() | super.getTagTypes().getValue();
	}

	/** 
		Gets and sets a collection of pictures associated with
		the media represented by the current instance.
	 
	 <value>
		A <see cref="IPicture[]" /> containing a collection of
		pictures associated with the media represented by the
		current instance or an empty array if none are present.
	 </value>
	*/
	@Override
	public IPicture[] getPictures()
	{
		return pictures.toArray(new IPicture[0]);
	}
	@Override
	public void setPictures(IPicture[] value)
	{
		pictures.clear();
		if (value != null)
		{
			pictures.addAll(Arrays.asList(value));
		}
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		pictures.clear();
	}
}