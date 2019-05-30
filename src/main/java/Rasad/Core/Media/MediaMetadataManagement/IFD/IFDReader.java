package Rasad.Core.Media.MediaMetadataManagement.IFD;

import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Makernotes.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Tags.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// IFDReader.cs: Parses TIFF IFDs and populates an IFD structure.
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
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
	 This class contains all the IFD reading and parsing code.
*/
public class IFDReader
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Constants

	private static final String PANASONIC_HEADER = "Panasonic\0\0\0";
	private static final String PENTAX_HEADER = "AOC\0";
	private static final String NIKON_HEADER = "Nikon\0";
	private static final String OLYMPUS1_HEADER = "OLYMP\0";
	private static final String OLYMPUS2_HEADER = "OLYMPUS\0";
	private static final String SONY_HEADER = "SONY DSC \0\0\0";
	private static final String LEICA_HEADER = "LEICA\0\0\0";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Fields

	/** 
		The <see cref="File" /> where this IFD is found in.
	*/
	protected File file;

	/** 
		If IFD is encoded in BigEndian or not
	*/
	protected boolean is_bigendian;

	/** 
		The IFD structure that will be populated
	*/
	protected IFDStructure structure;

	/** 
		 A <see cref="System.Int64"/> value describing the base were the IFD offsets
		 refer to. E.g. in Jpegs the IFD are located in an Segment and the offsets
		 inside the IFD refer from the beginning of this segment. So base_offset must
		 contain the beginning of the segment.
	*/
	protected long base_offset;

	/** 
		 A <see cref="System.UInt32"/> value with the beginning of the IFD relative to
		 base_offset.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected readonly uint ifd_offset;
	protected int ifd_offset;

	/** 
		A <see cref="System.UInt32"/> with the maximal offset, which should occur in the
		IFD. Greater offsets, would reference beyond the considered data.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected readonly uint max_offset;
	protected int max_offset;

	/** 
		Whether or not the makernote should be parsed.
	*/
	protected boolean parse_makernote = true;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

	/** 
		Whether or not the makernote should be parsed.
	*/
	public final boolean getShouldParseMakernote()
	{
		return parse_makernote;
	}
	public final void setShouldParseMakernote(boolean value)
	{
		parse_makernote = value;
	}

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
//ORIGINAL LINE: public IFDReader(File file, bool is_bigendian, IFDStructure structure, long base_offset, uint ifd_offset, uint max_offset)
	public IFDReader(File file, boolean is_bigendian, IFDStructure structure, long base_offset, int ifd_offset, int max_offset)
	{
		this.file = file;
		this.is_bigendian = is_bigendian;
		this.structure = structure;
		this.base_offset = base_offset;
		this.ifd_offset = ifd_offset;
		this.max_offset = max_offset;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Read all IFD segments from the file.
	*/
	public final void Read()
	{
		Read(-1);
	}

	/** 
		Read IFD segments from the file.
	 
	 <p>
		The number of IFDs that may be read can be restricted using the count
		parameter. This might be needed for fiels that have invalid next-ifd
		pointers (such as some IFDs in the Nikon Makernote). This condition is
		tested in the Nikon2 unit test, which contains such a file.
	 </p>
	 @param count
		 A <see cref="System.Int32"/> with the maximal number of IFDs to read.
		 Passing -1 means unlimited.
	 
	*/
	public final void Read(int count)
	{
		if (count == 0)
		{
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint next_offset = ifd_offset;
		int next_offset = ifd_offset;
		int i = 0;

		synchronized (file)
		{
			StartIFDLoopDetect();
			do
			{
				if (DetectIFDLoop(base_offset + next_offset))
				{
					file.MarkAsCorrupt("IFD loop detected");
					break;
				}
				next_offset = ReadIFD(base_offset, next_offset, max_offset);
			} while (next_offset > 0 && (count == -1 || ++i < count));

			StopIFDLoopDetect();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	/** 
		Add to the reference count for the IFD loop detection.
	*/
	private void StartIFDLoopDetect()
	{
		if (!ifd_offsets.containsKey(file))
		{
			ifd_offsets.put(file, new ArrayList<Long> ());
			ifd_loopdetect_refs.put(file, 1);
		}
		else
		{
			ifd_loopdetect_refs.get(file)++;
		}
	}

	/** 
		Attempts to detect whether or not this file has an endless IFD loop.
	 
	 @param offset
		A <see cref="System.UInt32"/> with the offset at which the next IFD
		can be found.
	 
	 @return 
		True if we have gone into a loop, false otherwise.
	 
	*/
	private boolean DetectIFDLoop(long offset)
	{
		if (offset == 0)
		{
			return false;
		}
		if (ifd_offsets.get(file).contains(offset))
		{
			return true;
		}
		ifd_offsets.get(file).add(offset);
		return false;
	}

	/** 
		End the IFD loop detection, cleanup if we're the last.
	*/
	private void StopIFDLoopDetect()
	{
		ifd_loopdetect_refs.get(file)--;
		if (ifd_loopdetect_refs.get(file).equals(0))
		{
			ifd_offsets.remove(file);
			ifd_loopdetect_refs.remove(file);
		}
	}

	private static HashMap<File, ArrayList<Long>> ifd_offsets = new HashMap<File, ArrayList<Long>> ();
	private static HashMap<File, Integer> ifd_loopdetect_refs = new HashMap<File, Integer> ();

	/** 
		Reads an IFD from file at position <paramref name="offset"/> relative
		to <paramref name="base_offset"/>.
	 
	 @param base_offset
		A <see cref="System.Int64"/> with the base offset which every offset
		in IFD is relative to.
	 
	 @param offset
		A <see cref="System.UInt32"/> with the offset of the IFD relative to
		<paramref name="base_offset"/>
	 
	 @param max_offset
		A <see cref="System.UInt32"/> with the maximal offset to consider for
		the IFD.
	 
	 @return 
		A <see cref="System.UInt32"/> with the offset of the next IFD, the
		offset is also relative to <paramref name="base_offset"/>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint ReadIFD(long base_offset, uint offset, uint max_offset)
	private int ReadIFD(long base_offset, int offset, int max_offset)
	{
		long length = 0;
		try
		{
			length = file.getLength();
		}
		catch (RuntimeException e)
		{
			// Use a safety-value of 4 gigabyte.
			length = 1073741824L * 4;
		}

		if (base_offset + offset > length)
		{
			file.MarkAsCorrupt("Invalid IFD offset");
			return 0;
		}

		IFDDirectory directory = new IFDDirectory();

		file.Seek(base_offset + offset, SeekOrigin.Begin);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort entry_count = ReadUShort();
		short entry_count = ReadUShort();

		if (file.getTell() + 12 * entry_count > base_offset + max_offset)
		{
			file.MarkAsCorrupt("Size of entries exceeds possible data size");
			return 0;
		}

		ByteVector entry_datas = file.ReadBlock(12 * entry_count);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint next_offset = ReadUInt();
		int next_offset = ReadUInt();

		for (int i = 0; i < entry_count; i++)
		{
			ByteVector entry_data = entry_datas.Mid(i * 12, 12);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort entry_tag = entry_data.Mid(0, 2).ToUShort(is_bigendian);
			short entry_tag = entry_data.Mid(0, 2).ToUShort(is_bigendian);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort type = entry_data.Mid(2, 2).ToUShort(is_bigendian);
			short type = entry_data.Mid(2, 2).ToUShort(is_bigendian);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value_count = entry_data.Mid(4, 4).ToUInt(is_bigendian);
			int value_count = entry_data.Mid(4, 4).ToUInt(is_bigendian);
			ByteVector offset_data = entry_data.Mid(8, 4);

			IFDEntry entry = CreateIFDEntry(entry_tag, type, value_count, base_offset, offset_data, max_offset);

			if (entry == null)
			{
				continue;
			}

			if (directory.containsKey(entry.getTag()))
			{
				directory.remove(entry.getTag());
			}

			directory.put(entry.getTag(), entry);
		}

		FixupDirectory(base_offset, directory);

		structure.directories.add(directory);
		return next_offset;
	}

	/** 
		Creates an IFDEntry from the given values. This method is used for
		every entry. Custom parsing can be hooked in by overriding the
		<see cref="ParseIFDEntry(ushort,ushort,uint,long,uint)"/> method.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag of the entry.
	 
	 @param type
		A <see cref="System.UInt16"/> with the type of the entry.
	 
	 @param count
		A <see cref="System.UInt32"/> with the data count of the entry.
	 
	 @param base_offset
		A <see cref="System.Int64"/> with the base offset which every
		offsets in the IFD are relative to.
	 
	 @param offset_data
		A <see cref="ByteVector"/> containing exactly 4 byte with the data
		of the offset of the entry. Since this field isn't interpreted as
		an offset if the data can be directly stored in the 4 byte, we
		pass the <see cref="ByteVector"/> to easier interpret it.
	 
	 @param max_offset
		A <see cref="System.UInt32"/> with the maximal offset to consider for
		the IFD.
	 
	 @return 
		A <see cref="IFDEntry"/> with the given parameter.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private IFDEntry CreateIFDEntry(ushort tag, ushort type, uint count, long base_offset, ByteVector offset_data, uint max_offset)
	private IFDEntry CreateIFDEntry(short tag, short type, int count, long base_offset, ByteVector offset_data, int max_offset)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint offset = offset_data.ToUInt(is_bigendian);
		int offset = offset_data.ToUInt(is_bigendian);

		// Fix the type for the IPTC tag.
		// From http://www.awaresystems.be/imaging/tiff/tifftags/iptc.html
		// "Often times, the datatype is incorrectly specified as LONG. "
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (tag == (ushort) IFDEntryTag.IPTC && type == (ushort) IFDEntryType.Long)
		if (tag == (short) IFDEntryTag.IPTC.getValue() && type == (short) IFDEntryType.Long.getValue())
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: type = (ushort) IFDEntryType.Byte;
			type = (short) IFDEntryType.Byte.getValue();
		}

		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry ifd_entry = ParseIFDEntry(tag, type, count, base_offset, offset);
		if (ifd_entry != null)
		{
			return ifd_entry;
		}

		if (count > 0x10000000)
		{
			// Some Nikon files are known to exhibit this corruption (or "feature").
			file.MarkAsCorrupt("Impossibly large item count");
			return null;
		}

		// then handle the values stored in the offset data itself
		if (count == 1)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Byte)
			if (type == (short) IFDEntryType.Byte.getValue())
			{
				return new ByteIFDEntry(tag, offset_data.get(0));
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.SByte)
			if (type == (short) IFDEntryType.SByte.getValue())
			{
				return new SByteIFDEntry(tag, (byte)offset_data.get(0));
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Short)
			if (type == (short) IFDEntryType.Short.getValue())
			{
				return new ShortIFDEntry(tag, offset_data.Mid(0, 2).ToUShort(is_bigendian));
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.SShort)
			if (type == (short) IFDEntryType.SShort.getValue())
			{
				return new SShortIFDEntry(tag, (short) offset_data.Mid(0, 2).ToUShort(is_bigendian));
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Long)
			if (type == (short) IFDEntryType.Long.getValue())
			{
				return new LongIFDEntry(tag, offset_data.ToUInt(is_bigendian));
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.SLong)
			if (type == (short) IFDEntryType.SLong.getValue())
			{
				return new SLongIFDEntry(tag, offset_data.ToInt(is_bigendian));
			}

		}

		if (count == 2)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Short)
			if (type == (short) IFDEntryType.Short.getValue())
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort [] data = new ushort [] { offset_data.Mid(0, 2).ToUShort(is_bigendian), offset_data.Mid(2, 2).ToUShort(is_bigendian) };
				short [] data = new short [] {offset_data.Mid(0, 2).ToUShort(is_bigendian), offset_data.Mid(2, 2).ToUShort(is_bigendian)};

				return new ShortArrayIFDEntry(tag, data);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.SShort)
			if (type == (short) IFDEntryType.SShort.getValue())
			{
				short [] data = new short [] {(short) offset_data.Mid(0, 2).ToUShort(is_bigendian), (short) offset_data.Mid(2, 2).ToUShort(is_bigendian)};

				return new SShortArrayIFDEntry(tag, data);
			}
		}

		if (count <= 4)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Undefined)
			if (type == (short) IFDEntryType.Undefined.getValue())
			{
				return new UndefinedIFDEntry(tag, offset_data.Mid(0, (int)count));
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Ascii)
			if (type == (short) IFDEntryType.Ascii.getValue())
			{
				String data = offset_data.Mid(0, (int)count).toString();
				int term = data.indexOf('\0');

				if (term > -1)
				{
					data = data.substring(0, term);
				}

				return new StringIFDEntry(tag, data);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Byte)
			if (type == (short) IFDEntryType.Byte.getValue())
			{
				return new ByteVectorIFDEntry(tag, offset_data.Mid(0, (int)count));
			}
		}


		// FIXME: create correct type.
		if (offset > max_offset)
		{
			return new UndefinedIFDEntry(tag, new ByteVector());
		}

		// then handle data referenced by the offset
		file.Seek(base_offset + offset, SeekOrigin.Begin);

		if (count == 1)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Rational)
			if (type == (short) IFDEntryType.Rational.getValue())
			{
				return new RationalIFDEntry(tag, ReadRational().clone());
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.SRational)
			if (type == (short) IFDEntryType.SRational.getValue())
			{
				return new SRationalIFDEntry(tag, ReadSRational().clone());
			}
		}

		if (count > 1)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Long)
			if (type == (short) IFDEntryType.Long.getValue())
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint [] data = ReadUIntArray(count);
				int [] data = ReadUIntArray(count);

				return new LongArrayIFDEntry(tag, data);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.SLong)
			if (type == (short) IFDEntryType.SLong.getValue())
			{
				int [] data = ReadIntArray(count);

				return new SLongArrayIFDEntry(tag, data);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Rational)
			if (type == (short) IFDEntryType.Rational.getValue())
			{
				Rational[] entries = new Rational [count];

				for (int i = 0; i < count; i++)
				{
					entries[i] = ReadRational().clone();
				}

				return new RationalArrayIFDEntry(tag, entries);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.SRational)
			if (type == (short) IFDEntryType.SRational.getValue())
			{
				SRational[] entries = new SRational [count];

				for (int i = 0; i < count; i++)
				{
					entries[i] = ReadSRational().clone();
				}

				return new SRationalArrayIFDEntry(tag, entries);
			}
		}

		if (count > 2)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Short)
			if (type == (short) IFDEntryType.Short.getValue())
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort [] data = ReadUShortArray(count);
				short [] data = ReadUShortArray(count);

				return new ShortArrayIFDEntry(tag, data);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.SShort)
			if (type == (short) IFDEntryType.SShort.getValue())
			{
				short [] data = ReadShortArray(count);

				return new SShortArrayIFDEntry(tag, data);
			}
		}

		if (count > 4)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Long)
			if (type == (short) IFDEntryType.Long.getValue())
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint [] data = ReadUIntArray(count);
				int [] data = ReadUIntArray(count);

				return new LongArrayIFDEntry(tag, data);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Byte)
			if (type == (short) IFDEntryType.Byte.getValue())
			{
				ByteVector data = file.ReadBlock((int) count);

				return new ByteVectorIFDEntry(tag, data);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Ascii)
			if (type == (short) IFDEntryType.Ascii.getValue())
			{
				String data = ReadAsciiString((int) count);

				return new StringIFDEntry(tag, data);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (tag == (ushort) ExifEntryTag.UserComment)
			if (tag == (short) ExifEntryTag.UserComment.getValue())
			{
				ByteVector data = file.ReadBlock((int) count);

				return new UserCommentIFDEntry(tag, data, file);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Undefined)
			if (type == (short) IFDEntryType.Undefined.getValue())
			{
				ByteVector data = file.ReadBlock((int) count);

				return new UndefinedIFDEntry(tag, data);
			}
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.Float)
		if (type == (short) IFDEntryType.Float.getValue())
		{
			return null;
		}

		if (type == 0 || type > 12)
		{
			// Invalid type
			file.MarkAsCorrupt("Invalid item type");
			return null;
		}

		// TODO: We should ignore unreadable values, erroring for now until we have sufficient coverage.
		throw new UnsupportedOperationException(String.format("Unknown type/count %1$s/%2$s (%3$s)", type, count, offset));
	}

	/** 
		Reads a 2-byte signed short from the current file.
	 
	 @return 
		A <see cref="short" /> value containing the short read
		from the current instance.
	 
	*/
	private short ReadShort()
	{
		return (short) file.ReadBlock(2).ToUShort(is_bigendian);
	}

	/** 
		Reads a 2-byte unsigned short from the current file.
	 
	 @return 
		A <see cref="ushort" /> value containing the short read
		from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort ReadUShort()
	private short ReadUShort()
	{
		return file.ReadBlock(2).ToUShort(is_bigendian);
	}

	/** 
		Reads a 4-byte int from the current file.
	 
	 @return 
		A <see cref="uint" /> value containing the int read
		from the current instance.
	 
	*/
	private int ReadInt()
	{
		return file.ReadBlock(4).ToInt(is_bigendian);
	}

	/** 
		Reads a 4-byte unsigned int from the current file.
	 
	 @return 
		A <see cref="uint" /> value containing the int read
		from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint ReadUInt()
	private int ReadUInt()
	{
		return file.ReadBlock(4).ToUInt(is_bigendian);
	}

	/** 
		Reads a <see cref="Rational"/> by two following unsigned
		int from the current file.
	 
	 @return 
		A <see cref="Rational"/> value created by the read values.
	 
	*/
	private Rational ReadRational()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint numerator = ReadUInt();
		int numerator = ReadUInt();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint denominator = ReadUInt();
		int denominator = ReadUInt();

		// correct illegal value
		if (denominator == 0)
		{
			numerator = 0;
			denominator = 1;
		}

		return new Rational(numerator, denominator);
	}

	/** 
		Reads a <see cref="SRational"/> by two following unsigned
		int from the current file.
	 
	 @return 
		A <see cref="SRational"/> value created by the read values.
	 
	*/
	private SRational ReadSRational()
	{
		int numerator = ReadInt();
		int denominator = ReadInt();

		// correct illegal value
		if (denominator == 0)
		{
			numerator = 0;
			denominator = 1;
		}

		return new SRational(numerator, denominator);
	}

	/** 
		Reads an array of 2-byte shorts from the current file.
	 
	 @return 
		An array of <see cref="ushort" /> values containing the
		shorts read from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort [] ReadUShortArray(uint count)
	private short[] ReadUShortArray(int count)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort [] data = new ushort [count];
		short [] data = new short [count];
		for (int i = 0; i < count; i++)
		{
			data [i] = ReadUShort();
		}
		return data;
	}

	/** 
		Reads an array of 2-byte signed shorts from the current file.
	 
	 @return 
		An array of <see cref="short" /> values containing the
		shorts read from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private short [] ReadShortArray(uint count)
	private short[] ReadShortArray(int count)
	{
		short [] data = new short [count];
		for (int i = 0; i < count; i++)
		{
			data [i] = ReadShort();
		}
		return data;
	}

	/** 
		Reads an array of 4-byte int from the current file.
	 
	 @return 
		An array of <see cref="int" /> values containing the
		shorts read from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private int [] ReadIntArray(uint count)
	private int[] ReadIntArray(int count)
	{
		int [] data = new int [count];
		for (int i = 0; i < count; i++)
		{
			data [i] = ReadInt();
		}
		return data;
	}

	/** 
		Reads an array of 4-byte unsigned int from the current file.
	 
	 @return 
		An array of <see cref="uint" /> values containing the
		shorts read from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint [] ReadUIntArray(uint count)
	private int[] ReadUIntArray(int count)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint [] data = new uint [count];
		int [] data = new int [count];
		for (int i = 0; i < count; i++)
		{
			data [i] = ReadUInt();
		}
		return data;
	}

	/** 
		Reads an ASCII string from the current file.
	 
	 @return 
		A <see cref="string" /> read from the current instance.
	 
	 
		The exif standard allows to store multiple string separated
		by '\0' in one ASCII-field. On the other hand some programs
		(e.g. CanonZoomBrowser) fill some ASCII fields by trailing
		'\0's.
		We follow the Adobe practice as described in XMP Specification
		Part 3 (Storeage in Files), and process the ASCII string only
		to the first '\0'.
	 
	*/
	private String ReadAsciiString(int count)
	{
		String str = file.ReadBlock(count).toString();
		int term = str.indexOf('\0');

		if (term > -1)
		{
			str = str.substring(0, term);
		}

		return str;
	}

	/** 
		Performs some fixups to a read <see cref="IFDDirectory"/>. For some
		special cases multiple <see cref="IFDEntry"/> instances contained
		in the directory are needed. Therfore, we do the fixups after reading the
		whole directory to be sure, all entries are present.
	 
	 @param base_offset
		A <see cref="System.Int64"/> value with the base offset, all offsets in the
		directory refers to.
	 
	 @param directory
		A <see cref="IFDDirectory"/> instance which was read and needs fixes.
	 
	*/
	private void FixupDirectory(long base_offset, IFDDirectory directory)
	{
		// The following two entries refer to thumbnail data, where one is  the offset
		// to the data and the other is the length. Unnaturally both are used to describe
		// the data. So it is needed to keep both entries in sync and keep the thumbnail data
		// for writing it back.
		// We determine the position of the data, read it and store it in an ThumbnailDataIFDEntry
		// which replaces the offset-entry to thumbnail data.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort offset_tag = (ushort) IFDEntryTag.JPEGInterchangeFormat;
		short offset_tag = (short) IFDEntryTag.JPEGInterchangeFormat.getValue();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort length_tag = (ushort) IFDEntryTag.JPEGInterchangeFormatLength;
		short length_tag = (short) IFDEntryTag.JPEGInterchangeFormatLength.getValue();
		if (directory.containsKey(offset_tag) && directory.containsKey(length_tag))
		{

			LongIFDEntry offset_entry = directory.get(offset_tag) instanceof LongIFDEntry ? (LongIFDEntry)directory.get(offset_tag) : null;
			LongIFDEntry length_entry = directory.get(length_tag) instanceof LongIFDEntry ? (LongIFDEntry)directory.get(length_tag) : null;

			if (offset_entry != null && length_entry != null)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint offset = offset_entry.Value;
				int offset = offset_entry.getValue();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint length = length_entry.Value;
				int length = length_entry.getValue();

				file.Seek(base_offset + offset, SeekOrigin.Begin);
				ByteVector data = file.ReadBlock((int) length);

				directory.remove(offset_tag);
				directory.put(offset_tag, new ThumbnailDataIFDEntry(offset_tag, data));
			}
		}


		// create a StripOffsetIFDEntry if necessary
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort strip_offsets_tag = (ushort) IFDEntryTag.StripOffsets;
		short strip_offsets_tag = (short) IFDEntryTag.StripOffsets.getValue();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort strip_byte_counts_tag = (ushort) IFDEntryTag.StripByteCounts;
		short strip_byte_counts_tag = (short) IFDEntryTag.StripByteCounts.getValue();
		if (directory.containsKey(strip_offsets_tag) && directory.containsKey(strip_byte_counts_tag))
		{

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint [] strip_offsets = null;
			int [] strip_offsets = null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint [] strip_byte_counts = null;
			int [] strip_byte_counts = null;

			unknown strip_offsets_entry = directory.get(strip_offsets_tag);
			unknown strip_byte_counts_entry = directory.get(strip_byte_counts_tag);

			if (strip_offsets_entry instanceof LongIFDEntry)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: strip_offsets = new uint[] {(strip_offsets_entry instanceof LongIFDEntry ? (LongIFDEntry)strip_offsets_entry : null).Value};
				strip_offsets = new int[] {(strip_offsets_entry instanceof LongIFDEntry ? (LongIFDEntry)strip_offsets_entry : null).getValue()};
			}
			else if (strip_offsets_entry instanceof LongArrayIFDEntry)
			{
				strip_offsets = (strip_offsets_entry instanceof LongArrayIFDEntry ? (LongArrayIFDEntry)strip_offsets_entry : null).getValues();
			}

			if (strip_offsets == null)
			{
				return;
			}

			if (strip_byte_counts_entry instanceof LongIFDEntry)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: strip_byte_counts = new uint[] {(strip_byte_counts_entry instanceof LongIFDEntry ? (LongIFDEntry)strip_byte_counts_entry : null).Value};
				strip_byte_counts = new int[] {(strip_byte_counts_entry instanceof LongIFDEntry ? (LongIFDEntry)strip_byte_counts_entry : null).getValue()};
			}
			else if (strip_byte_counts_entry instanceof LongArrayIFDEntry)
			{
				strip_byte_counts = (strip_byte_counts_entry instanceof LongArrayIFDEntry ? (LongArrayIFDEntry)strip_byte_counts_entry : null).getValues();
			}

			if (strip_byte_counts == null)
			{
				return;
			}

			directory.remove(strip_offsets_tag);
			directory.put(strip_offsets_tag, new StripOffsetsIFDEntry(strip_offsets_tag, strip_offsets, strip_byte_counts, file));
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private IFDEntry ParseMakernote(ushort tag, ushort type, uint count, long base_offset, uint offset)
	private IFDEntry ParseMakernote(short tag, short type, int count, long base_offset, int offset)
	{
		long makernote_offset = base_offset + offset;
		IFDStructure ifd_structure = new IFDStructure();

		// This is the minimum size a makernote should have
		// The shortest header is PENTAX_HEADER (4)
		// + IFD entry count (2)
		// + at least one IFD etry (12)
		// + next IFD pointer (4)
		// = 22 ....
		// we use this number to read a header which is big used
		// to identify the makernote types
		int header_size = 18;

		long length = 0;
		try
		{
			length = file.getLength();
		}
		catch (RuntimeException e)
		{
			// Use a safety-value of 4 gigabyte.
			length = 1073741824L * 4;
		}

		if (makernote_offset > length)
		{
			file.MarkAsCorrupt("offset to makernote is beyond file size");
			return null;
		}

		if (makernote_offset + header_size > length)
		{
			file.MarkAsCorrupt("data is to short to contain a maker note ifd");
			return null;
		}

		// read header
		file.Seek(makernote_offset, SeekOrigin.Begin);
		ByteVector header = file.ReadBlock(header_size);

		if (header.StartsWith(PANASONIC_HEADER))
		{
			IFDReader reader = new IFDReader(file, is_bigendian, ifd_structure, base_offset, offset + 12, max_offset);

			reader.ReadIFD(base_offset, offset + 12, max_offset);
			return new MakernoteIFDEntry(tag, ifd_structure, MakernoteType.Panasonic, PANASONIC_HEADER, 12, true, null);
		}

		if (header.StartsWith(PENTAX_HEADER))
		{
			IFDReader reader = new IFDReader(file, is_bigendian, ifd_structure, base_offset, offset + 6, max_offset);

			reader.ReadIFD(base_offset, offset + 6, max_offset);
			return new MakernoteIFDEntry(tag, ifd_structure, MakernoteType.Pentax, header.Mid(0, 6), 6, true, null);
		}

		if (header.StartsWith(OLYMPUS1_HEADER))
		{
			IFDReader reader = new IFDReader(file, is_bigendian, ifd_structure, base_offset, offset + 8, max_offset);

			reader.Read();
			return new MakernoteIFDEntry(tag, ifd_structure, MakernoteType.Olympus1, header.Mid(0, 8), 8, true, null);
		}

		if (header.StartsWith(OLYMPUS2_HEADER))
		{
			IFDReader reader = new IFDReader(file, is_bigendian, ifd_structure, makernote_offset, 12, count);

			reader.Read();
			return new MakernoteIFDEntry(tag, ifd_structure, MakernoteType.Olympus2, header.Mid(0, 12), 12, false, null);
		}

		if (header.StartsWith(SONY_HEADER))
		{
			IFDReader reader = new IFDReader(file, is_bigendian, ifd_structure, base_offset, offset + 12, max_offset);

			reader.ReadIFD(base_offset, offset + 12, max_offset);
			return new MakernoteIFDEntry(tag, ifd_structure, MakernoteType.Sony, SONY_HEADER, 12, true, null);
		}

		if (header.StartsWith(NIKON_HEADER))
		{

			ByteVector endian_bytes = header.Mid(10, 2);

			if (endian_bytes.toString().equals("II") || endian_bytes.toString().equals("MM"))
			{

				boolean makernote_endian = endian_bytes.toString().equals("MM");
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort magic = header.Mid(12, 2).ToUShort(is_bigendian);
				short magic = header.Mid(12, 2).ToUShort(is_bigendian);

				if (magic == 42)
				{

					// TODO: the max_offset value is not correct here. However, some nikon files have offsets to a sub-ifd
					// (preview image) which are not stored with the other makernote data. Therfore, we keep the max_offset
					// for now. (It is just an upper bound for some checks. So if it is too big, it doesn't matter)
					Nikon3MakernoteReader reader = new Nikon3MakernoteReader(file, makernote_endian, ifd_structure, makernote_offset + 10, 8, max_offset - offset - 10);

					reader.Read();
					return new MakernoteIFDEntry(tag, ifd_structure, MakernoteType.Nikon3, header.Mid(0, 18), 8, false, makernote_endian);
				}
			}
		}

		if (header.StartsWith(LEICA_HEADER))
		{
			IFDReader reader = new IFDReader(file, is_bigendian, ifd_structure, makernote_offset, 8, count);

			reader.Read();
			return new MakernoteIFDEntry(tag, ifd_structure, MakernoteType.Leica, header.Mid(0, 8), 10, false, null);
		}

		try
		{
			IFDReader reader = new IFDReader(file, is_bigendian, ifd_structure, base_offset, offset, max_offset);

			reader.Read();
			return new MakernoteIFDEntry(tag, ifd_structure, MakernoteType.Canon);
		}
		catch (java.lang.Exception e2)
		{
			return null;
		}
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
//ORIGINAL LINE: protected virtual IFDEntry ParseIFDEntry(ushort tag, ushort type, uint count, long base_offset, uint offset)
	protected IFDEntry ParseIFDEntry(short tag, short type, int count, long base_offset, int offset)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (tag == (ushort) ExifEntryTag.MakerNote && parse_makernote)
		if (tag == (short) ExifEntryTag.MakerNote.getValue() && parse_makernote)
		{
			return ParseMakernote(tag, type, count, base_offset, offset);
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (tag == (ushort) IFDEntryTag.SubIFDs)
		if (tag == (short) IFDEntryTag.SubIFDs.getValue())
		{
			ArrayList<IFDStructure> entries = new ArrayList<IFDStructure> ();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint [] data;
			int [] data;
			if (count >= 2)
			{

				// This is impossible right?
				if (base_offset + offset > file.getLength())
				{
					file.MarkAsCorrupt("Length of SubIFD is too long");
					return null;
				}

				file.Seek(base_offset + offset, SeekOrigin.Begin);
				data = ReadUIntArray(count);
			}
			else
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data = new uint [] { offset };
				data = new int [] {offset};
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (var sub_offset in data)
			for (int sub_offset : data)
			{
				IFDStructure sub_structure = new IFDStructure();
				Rasad.Core.Media.MediaMetadataManagement.IFD.IFDReader sub_reader = CreateSubIFDReader(file, is_bigendian, sub_structure, base_offset, sub_offset, max_offset);
				sub_reader.Read();

				entries.add(sub_structure);
			}
			return new SubIFDArrayEntry(tag, entries);
		}


		IFDStructure ifd_structure = new IFDStructure();
		IFDReader reader = CreateSubIFDReader(file, is_bigendian, ifd_structure, base_offset, offset, max_offset);

		// Sub IFDs are either identified by the IFD-type ...
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == (ushort) IFDEntryType.IFD)
		if (type == (short) IFDEntryType.IFD.getValue())
		{
			reader.Read();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return new SubIFDEntry(tag, type, (uint) ifd_structure.Directories.Length, ifd_structure);
			return new SubIFDEntry(tag, type, (int) ifd_structure.getDirectories().length, ifd_structure);
		}

		// ... or by one of the following tags
		switch (tag)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: case (ushort) IFDEntryTag.ExifIFD:
		case (short) IFDEntryTag.ExifIFD.getValue():
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: case (ushort) IFDEntryTag.InteroperabilityIFD:
		case (short) IFDEntryTag.InteroperabilityIFD.getValue():
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: case (ushort) IFDEntryTag.GPSIFD:
		case (short) IFDEntryTag.GPSIFD.getValue():
			reader.Read();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return new SubIFDEntry(tag, (ushort) IFDEntryType.Long, 1, ifd_structure);
			return new SubIFDEntry(tag, (short) IFDEntryType.Long.getValue(), 1, ifd_structure);

		default:
			return null;
		}
	}

	/** 
		Create a reader for Sub IFD entries.
	 
	 @param file
		A <see cref="File"/> to read from.
	 
	 @param is_bigendian
		 A <see cref="System.Boolean"/>, it must be true, if the data of the IFD should be
		 read as bigendian, otherwise false.
	 
	 @param structure
		A <see cref="IFDStructure"/> that will be populated.
	 
	 @param base_offset
		A <see cref="System.Int64"/> with the base offset which every offsets in the
		IFD are relative to.
	 
	 @param offset
		A <see cref="System.UInt32"/> with the offset of the entry.
	 
	 @param max_offset
		A <see cref="System.UInt32"/> with the maximal offset to consider for
		the IFD.
	 
	 @return 
		A <see cref="IFDReader"/> which can be used to read the specified sub IFD.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected virtual IFDReader CreateSubIFDReader(File file, bool is_bigendian, IFDStructure structure, long base_offset, uint offset, uint max_offset)
	protected IFDReader CreateSubIFDReader(File file, boolean is_bigendian, IFDStructure structure, long base_offset, int offset, int max_offset)
	{
		return new IFDReader(file, is_bigendian, structure, base_offset, offset, max_offset);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}