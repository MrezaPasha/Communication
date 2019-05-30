package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// BoxHeader.cs: Provides support for reading and writing headers for ISO/IEC
// 14496-12 boxes.
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
	This structure provides support for reading and writing headers
	for ISO/IEC 14496-12 boxes.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct BoxHeader
public final class BoxHeader
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the box type.
	*/
	private ByteVector box_type;

	/** 
		Contains the extended type.
	*/
	private ByteVector extended_type;

	/** 
		Contains the box size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong box_size;
	private long box_size;

	/** 
		Contains the header size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint header_size;
	private int header_size;

	/** 
		Contains the position of the header.
	*/
	private long position;

	/** 
		Contains the box (temporarily).
	*/
	private Box box;

	/** 
		Indicated that the header was read from a file.
	*/
	private boolean from_disk;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Fields

	/** 
		An empty box header.
	*/
	public static final BoxHeader Empty = new BoxHeader("xxxx");

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="BoxHeader" /> by reading it from a specified seek
		position in a specified file.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to read the new
		instance from.
	 
	 @param position
		A <see cref="long" /> value specifiying the seek position
		in <paramref name="file" /> at which to start reading.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		There isn't enough data in the file to read the complete
		header.
	 
	*/
	public BoxHeader()
	{
	}

	public BoxHeader(Rasad.Core.Media.MediaMetadataManagement.File file, long position)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		this.box = null;
		this.from_disk = true;
		this.position = position;
		file.Seek(position);

		ByteVector data = file.ReadBlock(32);
		int offset = 0;

		if (data.size() < 8 + offset)
		{
			throw new CorruptFileException("Not enough data in box header.");
		}

		header_size = 8;
		box_size = data.Mid(offset, 4).ToUInt();
		box_type = data.Mid(offset + 4, 4);

		// If the size is 1, that just tells us we have a
		// massive ULONG size waiting for us in the next 8
		// bytes.
		if (box_size == 1)
		{
			if (data.size() < 8 + offset)
			{
				throw new CorruptFileException("Not enough data in box header.");
			}

			header_size += 8;
			box_size = data.Mid(offset, 8).ToULong();
			offset += 8;
		}

		// UUID has a special header with 16 extra bytes.
		if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(box_type, Mpeg4.BoxType.Uuid))
		{
			if (data.size() < 16 + offset)
			{
				throw new CorruptFileException("Not enough data in box header.");
			}

			header_size += 16;
			extended_type = data.Mid(offset, 16);
		}
		else
		{
			extended_type = null;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (box_size > (ulong)(file.Length - position))
		if (box_size > (long)(file.getLength() - position))
		{
			throw new CorruptFileException("Box header specified a size of {0} bytes but only {1} bytes left in the file");
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="BoxHeader" /> with a specified box type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the four
		byte box type.
	 
	 
		<see cref="BoxHeader(ByteVector,ByteVector)" /> must be
		used to create a header of type "<c>uuid</c>".
	 
	 @exception ArgumentNullException
		<paramref name="type" /> is <see langword="null" /> or is
		equal to "<c>uuid</c>".
	 
	 @exception ArgumentException
		<paramref name="type" /> isn't exactly 4 bytes long.
	 
	*/
	public BoxHeader(ByteVector type)
	{
		this(type, null);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="BoxHeader" /> with a specified box type and
		optionally extended type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the four
		byte box type.
	 
	 @param extendedType
		A <see cref="ByteVector" /> object containing the four
		byte box type.
	 
	 @exception ArgumentNullException
		<paramref name="type" /> is <see langword="null" /> - or -
		<paramref name="type" /> is equal to "<c>uuid</c>" and
		<paramref name="extendedType" /> is <see langword="null"
		/>.
	 
	 @exception ArgumentException
		<paramref name="type" /> isn't exactly 4 bytes long - or
		- <paramref name="type" /> isn't "<c>uuid</c>" but
		<paramref name="extendedType" /> isn't <see
		langword="null" /> - or - paramref name="type" /> is
		"<c>uuid</c>" but <paramref name="extendedType" /> isn't
		exactly 16 bytes long.
	 
	*/
	public BoxHeader(ByteVector type, ByteVector extendedType)
	{
		position = -1;
		box = null;
		from_disk = false;
		box_type = type;

		if (type == null)
		{
			throw new NullPointerException("type");
		}

		if (type.size() != 4)
		{
			throw new IllegalArgumentException("Box type must be 4 bytes in length.", "type");
		}

		box_size = header_size = 8;

		if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(type, "uuid"))
		{
			if (extendedType != null)
			{
				throw new IllegalArgumentException("Extended type only permitted for 'uuid'.", "extendedType");
			}

			this.extended_type = extendedType;
			return;
		}

		if (extendedType == null)
		{
			throw new NullPointerException("extendedType");
		}

		if (extendedType.size() != 16)
		{
			throw new IllegalArgumentException("Extended type must be 16 bytes in length.", "extendedType");
		}

		box_size = header_size = 24;
		this.extended_type = extendedType;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the type of box represented by the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the 4 byte
		box type.
	 </value>
	*/
	public ByteVector getBoxType()
	{
		return box_type;
	}

	/** 
		Gets the extended type of the box represented by the
		current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the 16 byte
		extended type, or <see langword="null" /> if <see
		cref="BoxType" /> is not "<c>uuid</c>".
	 </value>
	*/
	public ByteVector getExtendedType()
	{
		return extended_type;
	}

	/** 
		Gets the size of the header represented by the current
		instance.
	 
	 <value>
		A <see cref="long" /> value containing the size of the
		header represented by the current instance.
	 </value>
	*/
	public long getHeaderSize()
	{
		return header_size;
	}

	/** 
		Gets and sets the size of the data in the box described
		by the current instance.
	 
	 <value>
		A <see cref="long" /> value containing the size of the
		data in the box described by the current instance.
	 </value>
	*/
	public long getDataSize()
	{
		return (long)(box_size - header_size);
	}
	public void setDataSize(long value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: box_size = (ulong) value + header_size;
		box_size = (long) value + header_size;
	}

	/** 
		Gets the offset of the box data from the position of the
		header.
	 
	 <value>
		A <see cref="long" /> value containing the offset of the
		box data from the position of the header.
	 </value>
	*/
	@Deprecated
	public long getDataOffset()
	{
		return header_size;
	}

	/** 
		Gets the total size of the box described by the current
		instance.
	 
	 <value>
		A <see cref="long" /> value containing the total size of
		the box described by the current instance.
	 </value>
	*/
	public long getTotalBoxSize()
	{
		return (long)box_size;
	}

	/** 
		Gets the position box represented by the current instance
		in the file it comes from.
	 
	 <value>
		A <see cref="long" /> value containing the position box
		represented by the current instance in the file it comes
		from.
	 </value>
	*/
	public long getPosition()
	{
		return from_disk ? position : -1;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Overwrites the header on disk, updating it to include a
		change in the size of the box.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		from which the box originates.
	 
	 @param sizeChange
		A <see cref="long" /> value indicating the change in the
		size of the box described by the current instance.
	 
	 @return 
		The size change encountered by the box that parents the
		box described the the current instance, equal to the
		size change of the box plus any size change that should
		happen in the header.
	 
	*/
	public long Overwrite(Rasad.Core.Media.MediaMetadataManagement.File file, long sizeChange)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (!from_disk)
		{
			throw new IllegalStateException("Cannot overwrite headers not on disk.");
		}

		long old_header_size = getHeaderSize();
		setDataSize(getDataSize() + sizeChange);
		file.Insert(Render(), position, old_header_size);
		return sizeChange + getHeaderSize() - old_header_size;
	}

	/** 
		Renders the header represented by the current instance.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	public ByteVector Render()
	{
		// Enlarge for size if necessary.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((header_size == 8 || header_size == 24) && box_size > uint.MaxValue)
		if ((header_size == 8 || header_size == 24) && box_size > Integer.MAX_VALUE)
		{
			header_size += 8;
			box_size += 8;
		}

		// Add the box size and type to the output.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector output = ByteVector.FromUInt((header_size == 8 || header_size == 24) ? (uint) box_size : 1);
		ByteVector output = ByteVector.FromUInt((header_size == 8 || header_size == 24) ? (int) box_size : 1);
		output.add(box_type);

		// If the box size is 16 or 32, we must have more a
		// large header to append.
		if (header_size == 16 || header_size == 32)
		{
			output.add(ByteVector.FromULong(box_size));
		}

		// The only reason for such a big size is an extended
		// type. Extend!!!
		if (header_size >= 24)
		{
			output.add(extended_type);
		}

		return output;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Internal Properties

	/** 
		Gets and sets the box represented by the current instance
		as a means of temporary storage for internal uses.
	*/
	public Box getBox()
	{
		return box;
	}
	public void setBox(Box value)
	{
		box = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public BoxHeader clone()
	{
		BoxHeader varCopy = new BoxHeader();

		varCopy.box_type = this.box_type;
		varCopy.extended_type = this.extended_type;
		varCopy.box_size = this.box_size;
		varCopy.header_size = this.header_size;
		varCopy.position = this.position;
		varCopy.box = this.box;
		varCopy.from_disk = this.from_disk;

		return varCopy;
	}
}