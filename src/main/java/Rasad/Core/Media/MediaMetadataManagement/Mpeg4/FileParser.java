package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// FileParser.cs: Provides methods for reading important information from an
// MPEG-4 file.
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
	This class provides methods for reading important information
	from an MPEG-4 file.
*/
public class FileParser
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the file to read from.
	*/
	private Rasad.Core.Media.MediaMetadataManagement.File file;

	/** 
		Contains the first header found in the file.
	*/
	private BoxHeader first_header = new BoxHeader();

	/** 
		Contains the ISO movie header box.
	*/
	private IsoMovieHeaderBox mvhd_box;

	/** 
		Contains the ISO user data boxes.
	*/
	private ArrayList<IsoUserDataBox> udta_boxes = new ArrayList<IsoUserDataBox> ();

	/** 
		Contains the box headers from the top of the file to the
		"moov" box.
	*/
	private BoxHeader [] moov_tree;

	/** 
		Contains the box headers from the top of the file to the
		"udta" box.
	*/
	private BoxHeader [] udta_tree;

	/** 
		Contains the "stco" boxes found in the file.
	*/
	private ArrayList<Box> stco_boxes = new ArrayList<Box> ();

	/** 
		Contains the "stsd" boxes found in the file.
	*/
	private ArrayList<Box> stsd_boxes = new ArrayList<Box> ();

	/** 
		Contains the position at which the "mdat" box starts.
	*/
	private long mdat_start = -1;

	/** 
		Contains the position at which the "mdat" box ends.
	*/
	private long mdat_end = -1;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="FileParser" /> for a specified file.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to perform operations
		on.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="file" /> does not start with a
		"<c>ftyp</c>" box.
	 
	*/
	public FileParser(Rasad.Core.Media.MediaMetadataManagement.File file)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		this.file = file;
		first_header = new BoxHeader(file, 0);

		if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(first_header.getBoxType(), "ftyp"))
		{
			throw new CorruptFileException("File does not start with 'ftyp' box.");
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the movie header box read by the current instance.
	 
	 <value>
		A <see cref="IsoMovieHeaderBox" /> object read by the
		current instance, or <see langword="null" /> if not found.
	 </value>
	 
		This value will only be set by calling <see
		cref="ParseTagAndProperties()" />.
	 
	*/
	public final IsoMovieHeaderBox getMovieHeaderBox()
	{
		return mvhd_box;
	}

	/** 
		Gets all user data boxes read by the current instance.
	 
	 <value>
		A <see cref="IsoUserDataBox" /> array read by the
		current instance.
	 </value>
	 
		This value will only be set by calling <see
		cref="ParseTag()" /> and <see
		cref="ParseTagAndProperties()" />.
	 
	*/
	public final IsoUserDataBox[] getUserDataBoxes()
	{
		return udta_boxes.toArray(new IsoUserDataBox[0]);
	}

	public final IsoUserDataBox getUserDataBox()
	{
		return getUserDataBoxes().length == 0 ? null : getUserDataBoxes()[0];
	}

	/** 
		Gets the audio sample entry read by the current instance.
	 
	 <value>
		A <see cref="IsoAudioSampleEntry" /> object read by the
		current instance, or <see langword="null" /> if not found.
	 </value>
	 
		This value will only be set by calling <see
		cref="ParseTagAndProperties()" />.
	 
	*/
	public final IsoAudioSampleEntry getAudioSampleEntry()
	{
		for (IsoSampleDescriptionBox box : stsd_boxes)
		{
			for (Box sub : box.getChildren())
			{
				IsoAudioSampleEntry entry = sub instanceof IsoAudioSampleEntry ? (IsoAudioSampleEntry)sub : null;

				if (entry != null)
				{
					return entry;
				}
			}
		}
		return null;
	}

	/** 
		Gets the visual sample entry read by the current
		instance.
	 
	 <value>
		A <see cref="IsoVisualSampleEntry" /> object read by the
		current instance, or <see langword="null" /> if not found.
	 </value>
	 
		This value will only be set by calling <see
		cref="ParseTagAndProperties()" />.
	 
	*/
	public final IsoVisualSampleEntry getVisualSampleEntry()
	{
		for (IsoSampleDescriptionBox box : stsd_boxes)
		{
			for (Box sub : box.getChildren())
			{
				IsoVisualSampleEntry entry = sub instanceof IsoVisualSampleEntry ? (IsoVisualSampleEntry)sub : null;

				if (entry != null)
				{
					return entry;
				}
			}
		}
		return null;
	}

	/** 
		Gets the box headers for the first "<c>moov</c>" box and
		all parent boxes up to the top of the file as read by the
		current instance.
	 
	 <value>
		A <see cref="BoxHeader[]" /> containing the headers for
		the first "<c>moov</c>" box and its parent boxes up to
		the top of the file, in the order they appear, or <see
		langword="null" /> if none is present.
	 </value>
	 
		This value is useful for overwriting box headers, and is
		only be set by calling <see cref="ParseBoxHeaders()" />.
	 
	*/
	public final BoxHeader[] getMoovTree()
	{
		return moov_tree;
	}

	/** 
		Gets the box headers for the first "<c>udta</c>" box and
		all parent boxes up to the top of the file as read by the
		current instance.
	 
	 <value>
		A <see cref="BoxHeader[]" /> containing the headers for
		the first "<c>udta</c>" box and its parent boxes up to
		the top of the file, in the order they appear, or <see
		langword="null" /> if none is present.
	 </value>
	 
		This value is useful for overwriting box headers, and is
		only be set by calling <see cref="ParseBoxHeaders()" />.
	 
	*/
	public final BoxHeader[] getUdtaTree()
	{
		return udta_tree;
	}

	/** 
		Gets all chunk offset boxes read by the current instance.
	 
	 <value>
		A <see cref="Box[]" /> containing all chunk offset boxes
		read by the current instance.
	 </value>
	 
		These boxes contain offset information for media data in
		the current instance and can be devalidated by size
		change operations, in which case they need to be
		corrected. This value will only be set by calling <see
		cref="ParseChunkOffsets()" />.
	 
	*/
	public final Box[] getChunkOffsetBoxes()
	{
		return stco_boxes.toArray(new Box[0]);
	}

	/** 
		Gets the position at which the "<c>mdat</c>" box starts.
	 
	 <value>
		A <see cref="long" /> value containing the seek position
		at which the "<c>mdat</c>" box starts.
	 </value>
	 
		The "<c>mdat</c>" box contains the media data for the
		file and is used for estimating the invariant data
		portion of the file.
	 
	*/
	public final long getMdatStartPosition()
	{
		return mdat_start;
	}

	/** 
		Gets the position at which the "<c>mdat</c>" box ends.
	 
	 <value>
		A <see cref="long" /> value containing the seek position
		at which the "<c>mdat</c>" box ends.
	 </value>
	 
		The "<c>mdat</c>" box contains the media data for the
		file and is used for estimating the invariant data
		portion of the file.
	 
	*/
	public final long getMdatEndPosition()
	{
		return mdat_end;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Parses the file referenced by the current instance,
		searching for box headers that will be useful in saving
		the file.
	*/
	public final void ParseBoxHeaders()
	{
		try
		{
			ResetFields();
			ParseBoxHeaders(first_header.getTotalBoxSize(), file.getLength(), null);
		}
		catch (CorruptFileException e)
		{
			file.MarkAsCorrupt(e.getMessage());
		}
	}

	/** 
		Parses the file referenced by the current instance,
		searching for tags.
	*/
	public final void ParseTag()
	{
		try
		{
			ResetFields();
			ParseTag(first_header.getTotalBoxSize(), file.getLength(), null);
		}
		catch (CorruptFileException e)
		{
			file.MarkAsCorrupt(e.getMessage());
		}
	}

	/** 
		Parses the file referenced by the current instance,
		searching for tags and properties.
	*/
	public final void ParseTagAndProperties()
	{
		try
		{
			ResetFields();
			ParseTagAndProperties(first_header.getTotalBoxSize(), file.getLength(), null, null);
		}
		catch (CorruptFileException e)
		{
			file.MarkAsCorrupt(e.getMessage());
		}
	}

	/** 
		Parses the file referenced by the current instance,
		searching for chunk offset boxes.
	*/
	public final void ParseChunkOffsets()
	{
		try
		{
			ResetFields();
			ParseChunkOffsets(first_header.getTotalBoxSize(), file.getLength());
		}
		catch (CorruptFileException e)
		{
			file.MarkAsCorrupt(e.getMessage());
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Parses boxes for a specified range, looking for headers.
	 
	 @param start
		A <see cref="long" /> value specifying the seek position
		at which to start reading.
	 
	 @param end
		A <see cref="long" /> value specifying the seek position
		at which to stop reading.
	 
	 @param parents
		A <see cref="T:System.Collections.Generic.List`1" /> object containing all the parent
		handlers that apply to the range.
	 
	*/
	private void ParseBoxHeaders(long start, long end, ArrayList<BoxHeader> parents)
	{
		BoxHeader header = new BoxHeader();

		for (long position = start; position < end; position += header.getTotalBoxSize())
		{
			header = new BoxHeader(file, position);

			if (moov_tree == null && Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Moov))
			{
				ArrayList<BoxHeader> new_parents = AddParent(parents, header.clone());
				moov_tree = new_parents.toArray(new BoxHeader[0]);
				ParseBoxHeaders(header.getHeaderSize() + position, header.getTotalBoxSize() + position, new_parents);
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Mdia) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Minf) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Stbl) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Trak))
			{
				ParseBoxHeaders(header.getHeaderSize() + position, header.getTotalBoxSize() + position, AddParent(parents, header.clone()));
			}
			else if (udta_tree == null && Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Udta))
			{
				// For compatibility, we still store the tree to the first udta
				// block. The proper way to get this info is from the individual
				// IsoUserDataBox.ParentTree member.
				udta_tree = AddParent(parents, header.clone()).toArray(new BoxHeader[0]);
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Mdat))
			{
				mdat_start = position;
				mdat_end = position + header.getTotalBoxSize();
			}

			if (header.getTotalBoxSize() == 0)
			{
				break;
			}
		}
	}

	/** 
		Parses boxes for a specified range, looking for tags.
	 
	 @param start
		A <see cref="long" /> value specifying the seek position
		at which to start reading.
	 
	 @param end
		A <see cref="long" /> value specifying the seek position
		at which to stop reading.
	 
	*/
	private void ParseTag(long start, long end, ArrayList<BoxHeader> parents)
	{
		BoxHeader header = new BoxHeader();

		for (long position = start; position < end; position += header.getTotalBoxSize())
		{
			header = new BoxHeader(file, position);

			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Moov))
			{
				ParseTag(header.getHeaderSize() + position, header.getTotalBoxSize() + position, AddParent(parents, header.clone()));
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Mdia) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Minf) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Stbl) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Trak))
			{
				ParseTag(header.getHeaderSize() + position, header.getTotalBoxSize() + position, AddParent(parents, header.clone()));
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Udta))
			{
				Rasad.Core.Media.MediaMetadataManagement.Mpeg4.Box tempVar = BoxFactory.CreateBox(file, header.clone());
				IsoUserDataBox udtaBox = tempVar instanceof IsoUserDataBox ? (IsoUserDataBox)tempVar : null;

				// Since we can have multiple udta boxes, save the parent for each one
				ArrayList<BoxHeader> new_parents = AddParent(parents, header.clone());
				udtaBox.setParentTree(new_parents.toArray(new BoxHeader[0]));

				udta_boxes.add(udtaBox);
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Mdat))
			{
				mdat_start = position;
				mdat_end = position + header.getTotalBoxSize();
			}

			if (header.getTotalBoxSize() == 0)
			{
				break;
			}
		}
	}

	/** 
		Parses boxes for a specified range, looking for tags and
		properties.
	 
	 @param start
		A <see cref="long" /> value specifying the seek position
		at which to start reading.
	 
	 @param end
		A <see cref="long" /> value specifying the seek position
		at which to stop reading.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object that applied to the
		range being searched.
	 
	*/
	private void ParseTagAndProperties(long start, long end, IsoHandlerBox handler, ArrayList<BoxHeader> parents)
	{
		BoxHeader header = new BoxHeader();

		for (long position = start; position < end; position += header.getTotalBoxSize())
		{
			header = new BoxHeader(file, position);
			ByteVector type = header.getBoxType();

			if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Moov))
			{
				ParseTagAndProperties(header.getHeaderSize() + position, header.getTotalBoxSize() + position, handler, AddParent(parents, header.clone()));
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Mdia) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Minf) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Stbl) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Trak))
			{
				ParseTagAndProperties(header.getHeaderSize() + position, header.getTotalBoxSize() + position, handler, AddParent(parents, header.clone()));
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Stsd))
			{
				stsd_boxes.add(BoxFactory.CreateBox(file, header.clone(), handler));
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Hdlr))
			{
				Rasad.Core.Media.MediaMetadataManagement.Mpeg4.Box tempVar = BoxFactory.CreateBox(file, header.clone(), handler);
				handler = tempVar instanceof IsoHandlerBox ? (IsoHandlerBox)tempVar : null;
			}
			else if (mvhd_box == null && Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Mvhd))
			{
				Rasad.Core.Media.MediaMetadataManagement.Mpeg4.Box tempVar2 = BoxFactory.CreateBox(file, header.clone(), handler);
				mvhd_box = tempVar2 instanceof IsoMovieHeaderBox ? (IsoMovieHeaderBox)tempVar2 : null;
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Udta))
			{
				Rasad.Core.Media.MediaMetadataManagement.Mpeg4.Box tempVar3 = BoxFactory.CreateBox(file, header.clone(), handler);
				IsoUserDataBox udtaBox = tempVar3 instanceof IsoUserDataBox ? (IsoUserDataBox)tempVar3 : null;

				// Since we can have multiple udta boxes, save the parent for each one
				ArrayList<BoxHeader> new_parents = AddParent(parents, header.clone());
				udtaBox.setParentTree(new_parents.toArray(new BoxHeader[0]));

				udta_boxes.add(udtaBox);
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Mdat))
			{
				mdat_start = position;
				mdat_end = position + header.getTotalBoxSize();
			}

			if (header.getTotalBoxSize() == 0)
			{
				break;
			}
		}
	}

	/** 
		Parses boxes for a specified range, looking for chunk
		offset boxes.
	 
	 @param start
		A <see cref="long" /> value specifying the seek position
		at which to start reading.
	 
	 @param end
		A <see cref="long" /> value specifying the seek position
		at which to stop reading.
	 
	*/
	private void ParseChunkOffsets(long start, long end)
	{
		BoxHeader header = new BoxHeader();

		for (long position = start; position < end; position += header.getTotalBoxSize())
		{
			header = new BoxHeader(file, position);

			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Moov))
			{
				ParseChunkOffsets(header.getHeaderSize() + position, header.getTotalBoxSize() + position);
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Moov) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Mdia) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Minf) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Stbl) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Trak))
			{
				ParseChunkOffsets(header.getHeaderSize() + position, header.getTotalBoxSize() + position);
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Stco) || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Co64))
			{
				stco_boxes.add(BoxFactory.CreateBox(file, header.clone()));
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Mdat))
			{
				mdat_start = position;
				mdat_end = position + header.getTotalBoxSize();
			}

			if (header.getTotalBoxSize() == 0)
			{
				break;
			}
		}
	}

	/** 
		Resets all internal fields.
	*/
	private void ResetFields()
	{
		mvhd_box = null;
		udta_boxes.clear();
		moov_tree = null;
		udta_tree = null;
		stco_boxes.clear();
		stsd_boxes.clear();
		mdat_start = -1;
		mdat_end = -1;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Static Methods

	/** 
		Adds a parent to the end of an existing list of parents.
	 
	 @param parents
		A <see cref="T:System.Collections.Generic.List`1" /> object containing an existing
		list of parents.
	 
	 @param current
		A <see cref="BoxHeader" /> object to add to the list.
	 
	 @return 
		A new <see cref="T:System.Collections.Generic.List`1" /> object containing the list
		of parents, including the added header.
	 
	*/
	private static ArrayList<BoxHeader> AddParent(ArrayList<BoxHeader> parents, BoxHeader current)
	{
		ArrayList<BoxHeader> boxes = new ArrayList<BoxHeader> ();
		if (parents != null)
		{
			boxes.addAll(parents);
		}
		boxes.add(current.clone());
		return boxes;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}