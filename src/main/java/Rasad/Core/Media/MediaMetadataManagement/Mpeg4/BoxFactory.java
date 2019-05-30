package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// BoxFactory.cs: Provides support for reading boxes from a file.
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
	This static class provides support for reading boxes from a file.
*/
public final class BoxFactory
{
	/** 
		Creates a box by reading it from a file given its header,
		parent header, handler, and index in its parent.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		to read from.
	 
	 @param header
		A <see cref="BoxHeader" /> object containing the header
		of the box to create.
	 
	 @param parent
		A <see cref="BoxHeader" /> object containing the header
		of the parent box.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new box.
	 
	 @param index
		A <see cref="int" /> value containing the index of the
		new box in its parent.
	 
	 @return 
		A newly created <see cref="Box" /> object.
	 
	*/
	private static Box CreateBox(Rasad.Core.Media.MediaMetadataManagement.File file, BoxHeader header, BoxHeader parent, IsoHandlerBox handler, int index)
	{
		// The first few children of an "stsd" are sample
		// entries.
		Rasad.Core.Media.MediaMetadataManagement.Mpeg4.Box tempVar = parent.getBox();
		if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(parent.getBoxType(), BoxType.Stsd) && parent.getBox() instanceof IsoSampleDescriptionBox && index < (tempVar instanceof IsoSampleDescriptionBox ? (IsoSampleDescriptionBox)tempVar : null).getEntryCount())
		{
			if (handler != null && Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(handler.getHandlerType(), BoxType.Soun))
			{
				return new IsoAudioSampleEntry(header.clone(), file, handler);
			}
			else if (handler != null && Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(handler.getHandlerType(), BoxType.Vide))
			{
				return new IsoVisualSampleEntry(header.clone(), file, handler);
			}
			else if (handler != null && Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(handler.getHandlerType(), BoxType.Alis))
			{
				if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Text))
				{
					return new TextBox(header.clone(), file, handler);
				}
				else if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(header.getBoxType(), BoxType.Url))
				{
					return new UrlBox(header.clone(), file, handler);
				}
				// This could be anything, so just parse it
				return new UnknownBox(header.clone(), file, handler);
			}
			else
			{
				return new IsoSampleEntry(header.clone(), file, handler);
			}
		}

		// Standard items...
		ByteVector type = header.getBoxType();

		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Mvhd))
		{
			return new IsoMovieHeaderBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Stbl))
		{
			return new IsoSampleTableBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Stsd))
		{
			return new IsoSampleDescriptionBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Stco))
		{
			return new IsoChunkOffsetBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Co64))
		{
			return new IsoChunkLargeOffsetBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Hdlr))
		{
			return new IsoHandlerBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Udta))
		{
			return new IsoUserDataBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Meta))
		{
			return new IsoMetaBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Ilst))
		{
			return new AppleItemListBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Data))
		{
			return new AppleDataBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Esds))
		{
			return new AppleElementaryStreamDescriptor(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Free) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Skip))
		{
			return new IsoFreeSpaceBox(header.clone(), file, handler);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Mean) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(type, BoxType.Name))
		{
			return new AppleAdditionalInfoBox(header.clone(), file, handler);
		}

		// If we still don't have a tag, and we're inside an
		// ItemListBox, load the box as an AnnotationBox
		// (Apple tag item).
		if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(parent.getBoxType(), BoxType.Ilst))
		{
			return new AppleAnnotationBox(header.clone(), file, handler);
		}

		// Nothing good. Go generic.
		return new UnknownBox(header.clone(), file, handler);
	}

	/** 
		Creates a box by reading it from a file given its
		position in the file, parent header, handler, and index
		in its parent.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		to read from.
	 
	 @param position
		A <see cref="long" /> value specifying at what seek
		position in <paramref name="file" /> to start reading.
	 
	 @param parent
		A <see cref="BoxHeader" /> object containing the header
		of the parent box.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new box.
	 
	 @param index
		A <see cref="int" /> value containing the index of the
		new box in its parent.
	 
	 @return 
		A newly created <see cref="Box" /> object.
	 
	*/
	public static Box CreateBox(Rasad.Core.Media.MediaMetadataManagement.File file, long position, BoxHeader parent, IsoHandlerBox handler, int index)
	{
		BoxHeader header = new BoxHeader(file, position);
		return CreateBox(file, header.clone(), parent.clone(), handler, index);
	}

	/** 
		Creates a box by reading it from a file given its
		position in the file and handler.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		to read from.
	 
	 @param position
		A <see cref="long" /> value specifying at what seek
		position in <paramref name="file" /> to start reading.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new box.
	 
	 @return 
		A newly created <see cref="Box" /> object.
	 
	*/
	public static Box CreateBox(Rasad.Core.Media.MediaMetadataManagement.File file, long position, IsoHandlerBox handler)
	{
		return CreateBox(file, position, BoxHeader.Empty, handler, -1);
	}

	/** 
		Creates a box by reading it from a file given its
		position in the file.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		to read from.
	 
	 @param position
		A <see cref="long" /> value specifying at what seek
		position in <paramref name="file" /> to start reading.
	 
	 @return 
		A newly created <see cref="Box" /> object.
	 
	*/
	public static Box CreateBox(Rasad.Core.Media.MediaMetadataManagement.File file, long position)
	{
		return CreateBox(file, position, null);
	}

	/** 
		Creates a box by reading it from a file given its header
		and handler.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		to read from.
	 
	 @param header
		A <see cref="BoxHeader" /> object containing the header
		of the box to create.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new box.
	 
	 @return 
		A newly created <see cref="Box" /> object.
	 
	*/
	public static Box CreateBox(Rasad.Core.Media.MediaMetadataManagement.File file, BoxHeader header, IsoHandlerBox handler)
	{
		return CreateBox(file, header.clone(), BoxHeader.Empty, handler, -1);
	}

	/** 
		Creates a box by reading it from a file given its header
		and handler.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		to read from.
	 
	 @param header
		A <see cref="BoxHeader" /> object containing the header
		of the box to create.
	 
	 @return 
		A newly created <see cref="Box" /> object.
	 
	*/
	public static Box CreateBox(Rasad.Core.Media.MediaMetadataManagement.File file, BoxHeader header)
	{
		return CreateBox(file, header.clone(), null);
	}
}