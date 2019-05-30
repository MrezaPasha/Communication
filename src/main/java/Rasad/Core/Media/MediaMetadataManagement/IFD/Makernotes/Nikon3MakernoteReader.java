package Rasad.Core.Media.MediaMetadataManagement.IFD.Makernotes;

import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Tags.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// Nikon3MakernoteReader.cs: Reads Nikon Makernotes.
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//
// Copyright (C) 2009 Ruben Vermeersch
// Copyright (C) 2009 Mike Gemuende
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
	 This class contains Nikon3 makernote specific reading logic.
*/
public class Nikon3MakernoteReader extends IFDReader
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructor. Reads an IFD from given file, using the given endianness.
	 
	 @param file
		A <see cref="File"/> to read from.
	 
	 @param is_bigendian
		 A <see cref="System.Boolean"/>, it must be true, if the data of the IFD should be
		 read as bigendian, otherwise false.
	 
	 @param structure
		A <see cref="IFDStructure"/> that will be populated.
	 
	 @param base_offset
		 A <see cref="System.Int64"/> value describing the base were the IFD offsets
		 refer to. E.g. in Jpegs the IFD are located in an Segment and the offsets
		 inside the IFD refer from the beginning of this segment. So <paramref
		 name="base_offset"/> must contain the beginning of the segment.
	 
	 @param ifd_offset
		 A <see cref="System.UInt32"/> value with the beginning of the IFD relative to
		 <paramref name="base_offset"/>.
	 
	 @param max_offset
	 	A <see cref="System.UInt32"/> value with maximal possible offset. This is to limit
		 the size of the possible data;
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nikon3MakernoteReader(File file, bool is_bigendian, IFDStructure structure, long base_offset, uint ifd_offset, uint max_offset)
	public Nikon3MakernoteReader(File file, boolean is_bigendian, IFDStructure structure, long base_offset, int ifd_offset, int max_offset)
	{
		super(file, is_bigendian, structure, base_offset, ifd_offset, max_offset);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Methods

	/** 
		Try to parse the given IFD entry, used to discover format-specific entries.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag of the entry.
	 
	 @param type
		A <see cref="System.UInt16"/> with the type of the entry.
	 
	 @param count
		A <see cref="System.UInt32"/> with the data count of the entry.
	 
	 @param base_offset
		A <see cref="System.Int64"/> with the base offset which every offsets in the
		IFD are relative to.
	 
	 @param offset
		A <see cref="System.UInt32"/> with the offset of the entry.
	 
	 @return 
		A <see cref="IFDEntry"/> with the given parameters, or null if none was parsed, after
		which the normal TIFF parsing is used.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override IFDEntry ParseIFDEntry(ushort tag, ushort type, uint count, long base_offset, uint offset)
	@Override
	protected IFDEntry ParseIFDEntry(short tag, short type, int count, long base_offset, int offset)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (tag == (ushort) Nikon3MakerNoteEntryTag.Preview)
		if (tag == (short) Nikon3MakerNoteEntryTag.Preview.getValue())
		{
			// SubIFD with Preview Image
			// The entry itself is usually a long
			// TODO: handle JPEGInterchangeFormat and JPEGInterchangeFormatLength correctly

			// The preview field contains a long with an offset to an IFD
			// that contains the preview image. We need to be careful
			// though: this IFD does not contain a valid next-offset
			// pointer. For this reason, we only read the first IFD and
			// ignore the rest (which is preview image data, directly
			// starting after the IFD entries).

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: type = (ushort) IFDEntryType.IFD;
			type = (short) IFDEntryType.IFD.getValue();

			IFDStructure ifd_structure = new IFDStructure();
			IFDReader reader = CreateSubIFDReader(file, is_bigendian, ifd_structure, base_offset, offset, max_offset);

			reader.Read(1);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return new SubIFDEntry(tag, type, (uint) ifd_structure.Directories.Length, ifd_structure);
			return new SubIFDEntry(tag, type, (int) ifd_structure.getDirectories().length, ifd_structure);
		}
		return super.ParseIFDEntry(tag, type, count, base_offset, offset);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}