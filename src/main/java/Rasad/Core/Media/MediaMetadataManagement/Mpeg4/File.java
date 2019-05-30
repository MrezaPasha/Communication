package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// File.cs: Provides tagging and properties support for MPEG-4 files.
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> to provide tagging
	and properties support for MPEG-4 files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/m4a", "m4a")][SupportedMimeType("taglib/m4b", "m4b")][SupportedMimeType("taglib/m4v", "m4v")][SupportedMimeType("taglib/m4p", "m4p")][SupportedMimeType("taglib/mp4", "mp4")][SupportedMimeType("audio/mp4")][SupportedMimeType("audio/x-m4a")][SupportedMimeType("video/mp4")][SupportedMimeType("video/x-m4v")] public class File : Rasad.Core.Media.MediaMetadataManagement.File
public class File extends Rasad.Core.Media.MediaMetadataManagement.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the Apple tag.
	*/
	private AppleTag apple_tag;

	/** 
		Contains the combined tag.
	 
	 
		TODO: Add support for ID3v2 tags.
	 
	*/
	private CombinedTag tag;

	/** 
		Contains the media properties.
	*/
	private Properties properties;

	/** 
		Contains the ISO user data boxes.
	*/
	private ArrayList<IsoUserDataBox> udta_boxes = new ArrayList<IsoUserDataBox> ();

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
	public File(String path, ReadStyle propertiesStyle)
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
	public File(String path)
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
	public File(File.IFileAbstraction abstraction, ReadStyle propertiesStyle)
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
	public File(File.IFileAbstraction abstraction)
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

	protected final ArrayList<IsoUserDataBox> getUdtaBoxes()
	{
		return udta_boxes;
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
		if (udta_boxes.isEmpty())
		{
			IsoUserDataBox udtaBox = new IsoUserDataBox();
			udta_boxes.add(udtaBox);
		}

		// Try to get into write mode.
		Mode = File.AccessMode.Write;
		try
		{
			FileParser parser = new FileParser(this);
			parser.ParseBoxHeaders();

			InvariantStartPosition = parser.getMdatStartPosition();
			InvariantEndPosition = parser.getMdatEndPosition();

			long size_change = 0;
			long write_position = 0;

			// To avoid rewriting udta blocks which might not have been modified,
			// the code here will work correctly if:
			// 1. There is a single udta for the entire file
			//   - OR -
			// 2. There are multiple utdtas, but only 1 of them contains the Apple ILST box.
			// We should be OK in the vast majority of cases
			IsoUserDataBox udtaBox = FindAppleTagUdta();
			if (null == udtaBox)
			{
				udtaBox = new IsoUserDataBox();
			}
			ByteVector tag_data = udtaBox.Render();

			// If we don't have a "udta" box to overwrite...
			if (udtaBox.getParentTree() == null || udtaBox.getParentTree().length == 0)
			{

				// Stick the box at the end of the moov box.
				BoxHeader moov_header = parser.getMoovTree() [parser.getMoovTree().length - 1].clone();
				size_change = tag_data.size();
				write_position = moov_header.getPosition() + moov_header.getTotalBoxSize();
				Insert(tag_data, write_position, 0);

				// Overwrite the parent box sizes.
				for (int i = parser.getMoovTree().length - 1; i >= 0; i--)
				{
					size_change = parser.getMoovTree() [i].Overwrite(this, size_change);
				}
			}
			else
			{
				// Overwrite the old box.
				BoxHeader udta_header = udtaBox.getParentTree()[udtaBox.getParentTree().length - 1].clone();
				size_change = tag_data.size() - udta_header.getTotalBoxSize();
				write_position = udta_header.getPosition();
				Insert(tag_data, write_position, udta_header.getTotalBoxSize());

				// Overwrite the parent box sizes.
				for (int i = udtaBox.getParentTree().length - 2; i >= 0; i--)
				{
					size_change = udtaBox.getParentTree() [i].Overwrite(this, size_change);
				}
			}

			// If we've had a size change, we may need to adjust
			// chunk offsets.
			if (size_change != 0)
			{
				// We may have moved the offset boxes, so we
				// need to reread.
				parser.ParseChunkOffsets();
				InvariantStartPosition = parser.getMdatStartPosition();
				InvariantEndPosition = parser.getMdatEndPosition();

				for (Box box : parser.getChunkOffsetBoxes())
				{
					IsoChunkLargeOffsetBox co64 = box instanceof IsoChunkLargeOffsetBox ? (IsoChunkLargeOffsetBox)box : null;

					if (co64 != null)
					{
						co64.Overwrite(this, size_change, write_position);
						continue;
					}

					IsoChunkOffsetBox stco = box instanceof IsoChunkOffsetBox ? (IsoChunkOffsetBox)box : null;

					if (stco != null)
					{
						stco.Overwrite(this, size_change, write_position);
						continue;
					}
				}
			}

			TagTypesOnDisk = TagTypes;
		}
		finally
		{
			Mode = File.AccessMode.Closed;
		}
	}

	/** 
		Gets a tag of a specified type from the current instance,
		optionally creating a new tag if possible.
	 
	 @param type
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value indicating the
		type of tag to read.
	 
	 @param create
		A <see cref="bool" /> value specifying whether or not to
		try and create the tag if one is not found.
	 
	 @return 
		A <see cref="Tag" /> object containing the tag that was
		found in or added to the current instance. If no
		matching tag was found and none was created, <see
		langword="null" /> is returned.
	 
	 
		At the time of this writing, only <see cref="AppleTag" />
		is supported. All other tag types will be ignored.
	 
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag GetTag(TagTypes type, boolean create)
	{
		if (type == TagTypes.Apple)
		{
			if (apple_tag == null && create)
			{
				IsoUserDataBox udtaBox = FindAppleTagUdta();
				if (null == udtaBox)
				{
					udtaBox = new IsoUserDataBox();
				}
				apple_tag = new AppleTag(udtaBox);
				tag.SetTags(apple_tag);
			}

			return apple_tag;
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
	@Override
	public void RemoveTags(TagTypes types)
	{
		if ((types.getValue() & TagTypes.Apple.getValue()) != TagTypes.Apple.getValue() || apple_tag == null)
		{
			return;
		}

		apple_tag.DetachIlst();
		apple_tag = null;
		tag.SetTags();
	}

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
		// TODO: Support Id3v2 boxes!!!
		tag = new CombinedTag();
		Mode = AccessMode.Read;
		try
		{
			FileParser parser = new FileParser(this);

			if (propertiesStyle == ReadStyle.None)
			{
				parser.ParseTag();
			}
			else
			{
				parser.ParseTagAndProperties();
			}

			InvariantStartPosition = parser.getMdatStartPosition();
			InvariantEndPosition = parser.getMdatEndPosition();

			udta_boxes.addAll(Arrays.asList(parser.getUserDataBoxes()));

			// Ensure our collection contains at least a single empty box
			if (udta_boxes.isEmpty())
			{
				IsoUserDataBox dummy = new IsoUserDataBox();
				udta_boxes.add(dummy);
			}

			// Check if a udta with ILST actually exists
			if (IsAppleTagUdtaPresent())
			{
				TagTypesOnDisk |= TagTypes.Apple; //There is an udta present with ILST info
			}

			// Find the udta box with the Apple Tag ILST
			IsoUserDataBox udtaBox = FindAppleTagUdta();
			if (null == udtaBox)
			{
				udtaBox = new IsoUserDataBox();
			}
			apple_tag = new AppleTag(udtaBox);
			tag.SetTags(apple_tag);

			// If we're not reading properties, we're done.
			if (propertiesStyle == ReadStyle.None)
			{
				Mode = AccessMode.Closed;
				return;
			}

			// Get the movie header box.
			IsoMovieHeaderBox mvhd_box = parser.getMovieHeaderBox();
			if (mvhd_box == null)
			{
				Mode = AccessMode.Closed;
				throw new CorruptFileException("mvhd box not found.");
			}

			IsoAudioSampleEntry audio_sample_entry = parser.getAudioSampleEntry();
			IsoVisualSampleEntry visual_sample_entry = parser.getVisualSampleEntry();

			// Read the properties.
			properties = new Properties(mvhd_box.getDuration(), audio_sample_entry, visual_sample_entry);
		}
		finally
		{
			Mode = AccessMode.Closed;
		}
	}

	/** 
		Find the udta box within our collection that contains the Apple ILST data.
	 
	 
			If there is a single udta in a file, we return that.
			If there are multiple udtas, we search for the one that contains the ILST box.
	 
	*/
	private IsoUserDataBox FindAppleTagUdta()
	{
		if (udta_boxes.size() == 1)
		{
			return udta_boxes.get(0); //Single udta - just return it
		}

		// multiple udta : pick out the shallowest node which has an ILst tag
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		var udtaBox = udta_boxes.Where(box -> box.GetChildRecursively(BoxType.Ilst) != null).OrderBy(box -> box.ParentTree.Length).FirstOrDefault();

		return udtaBox;
	}

	/** 
		Returns true if there is a udta with ILST present in our collection
	*/
	private boolean IsAppleTagUdtaPresent()
	{
		for (IsoUserDataBox udtaBox : udta_boxes)
		{
			if (udtaBox.GetChild(BoxType.Meta) != null && udtaBox.GetChild(BoxType.Meta).GetChild(BoxType.Ilst) != null)
			{

				return true;
			}
		}

		return false;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}