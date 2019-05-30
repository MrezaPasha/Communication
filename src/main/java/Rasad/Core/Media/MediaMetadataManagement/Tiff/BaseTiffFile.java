package Rasad.Core.Media.MediaMetadataManagement.Tiff;

import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// BaseTiffFile.cs:
//
// Author:
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2010 Mike Gemuende
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Image.File" /> to provide some basic behavior
	for Tiff based file formats.
*/
public abstract class BaseTiffFile extends Rasad.Core.Media.MediaMetadataManagement.Image.File
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Indicates if the current file is in big endian or little endian format.
	 
	 
		The method <see cref="ReadHeader()"/> must be called from a subclass to
		properly initialize this property.
	 
	*/
	private boolean IsBigEndian;
	public final boolean getIsBigEndian()
	{
		return IsBigEndian;
	}
	private void setIsBigEndian(boolean value)
	{
		IsBigEndian = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Properties

	/** 
		The identifier used to recognize the file. This is 42 for most TIFF files.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort Magic;
	private short Magic;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected ushort getMagic()
	protected final short getMagic()
	{
		return Magic;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected void setMagic(ushort value)
	protected final void setMagic(short value)
	{
		Magic = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	protected BaseTiffFile(String path)
	{
		super(path);
		setMagic((short)42);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	protected BaseTiffFile(IFileAbstraction abstraction)
	{
		super(abstraction);
		setMagic((short)42);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Methods

	/** 
		Reads and validates the TIFF header at the current position.
	 
	 @return 
		A <see cref="System.UInt32"/> with the offset value to the first
		IFD contained in the file.
	 
	 
		This method should only be called, when the current read position is
		the beginning of the file.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected uint ReadHeader()
	protected final int ReadHeader()
	{
		// TIFF header:
		//
		// 2 bytes         Indicating the endianess (II or MM)
		// 2 bytes         Tiff Magic word (usually 42)
		// 4 bytes         Offset to first IFD

		ByteVector header = ReadBlock(8);

		if (header.size() != 8)
		{
			throw new CorruptFileException("Unexpected end of header");
		}

		String order = header.Mid(0, 2).toString();

		if (order.equals("II"))
		{
			setIsBigEndian(false);
		}
		else if (order.equals("MM"))
		{
			setIsBigEndian(true);
		}
		else
		{
			throw new CorruptFileException("Unknown Byte Order");
		}

		if (header.Mid(2, 2).ToUShort(getIsBigEndian()) != getMagic())
		{
			throw new CorruptFileException(String.format("TIFF Magic (%1$s) expected", getMagic()));
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint first_ifd_offset = header.Mid(4, 4).ToUInt(IsBigEndian);
		int first_ifd_offset = header.Mid(4, 4).ToUInt(getIsBigEndian());

		return first_ifd_offset;
	}


	/** 
		Reads IFDs starting from the given offset.
	 
	 @param offset
		A <see cref="System.UInt32"/> with the IFD offset to start
		reading from.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected void ReadIFD(uint offset)
	protected final void ReadIFD(int offset)
	{
		ReadIFD(offset, -1);
	}


	/** 
		Reads a certain number of IFDs starting from the given offset.
	 
	 @param offset
		A <see cref="System.UInt32"/> with the IFD offset to start
		reading from.
	 
	 @param ifd_count
		A <see cref="System.Int32"/> with the number of IFDs to read.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected void ReadIFD(uint offset, int ifd_count)
	protected final void ReadIFD(int offset, int ifd_count)
	{
		long length = 0;
		try
		{
			length = Length;
		}
		catch (RuntimeException e)
		{
			// Use a safety-value of 4 gigabyte.
			length = 1073741824L * 4;
		}
		Rasad.Core.Media.MediaMetadataManagement.Tag tempVar = GetTag(TagTypes.TiffIFD, true);
		IFDTag ifd_tag = tempVar instanceof IFDTag ? (IFDTag)tempVar : null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var reader = CreateIFDReader(this, IsBigEndian, ifd_tag.Structure, 0, offset, (uint) length);
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDReader reader = CreateIFDReader(this, getIsBigEndian(), ifd_tag.getStructure(), 0, offset, (int) length);

		reader.Read(ifd_count);
	}

	/** 
		Creates an IFD reader to parse the file.
	 
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
//ORIGINAL LINE: protected virtual IFDReader CreateIFDReader(BaseTiffFile file, bool is_bigendian, IFDStructure structure, long base_offset, uint ifd_offset, uint max_offset)
	protected IFDReader CreateIFDReader(BaseTiffFile file, boolean is_bigendian, IFDStructure structure, long base_offset, int ifd_offset, int max_offset)
	{
		return new IFDReader(file, is_bigendian, structure, base_offset, ifd_offset, max_offset);

	}

	/** 
		Renders a TIFF header with the given offset to the first IFD.
		The returned data has length 8.
	 
	 @param first_ifd_offset
		A <see cref="System.UInt32"/> with the offset to the first IFD
		to be included in the header.
	 
	 @return 
		A <see cref="ByteVector"/> with the rendered header of length 8.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected ByteVector RenderHeader(uint first_ifd_offset)
	protected final ByteVector RenderHeader(int first_ifd_offset)
	{
		ByteVector data = new ByteVector();

		if (getIsBigEndian())
		{
			data.add("MM");
		}
		else
		{
			data.add("II");
		}

		data.add(ByteVector.FromUShort(getMagic(), getIsBigEndian()));
		data.add(ByteVector.FromUInt(first_ifd_offset, getIsBigEndian()));

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}