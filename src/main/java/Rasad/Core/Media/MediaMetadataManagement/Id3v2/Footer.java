package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Footer.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   id3v2header.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2002,2003 Scott Wheeler (Original Implementation)
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
	This structure provides a representation of an ID3v2 tag footer
	which can be read from and written to disk.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct Footer
public final class Footer
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the tag's major version.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte major_version;
	private byte major_version;

	/** 
		Contains the tag's version revision.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte revision_number;
	private byte revision_number;

	/** 
		Contains tag's flags.
	*/
	private HeaderFlags flags = HeaderFlags.values()[0];

	/** 
		Contains the tag size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint tag_size;
	private int tag_size;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Fields

	/** 
		The size of a ID3v2 footer.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint Size = 10;
	public static final int Size = 10;

	/** 
		The identifier used to recognize a ID3v2 footer.
	 
	 <value>
		"3DI"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "3DI";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Footer" /> by reading it from raw footer data.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		data to build the new instance from.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> is smaller than <see
		cref="Size" />, does not begin with <see
		cref="FileIdentifier" />, contains invalid flag data,
		or contains invalid size data.
	 
	*/
	public Footer()
	{
	}

	public Footer(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (data.size() < Size)
		{
			throw new CorruptFileException("Provided data is smaller than object size.");
		}

		if (!data.StartsWith(FileIdentifier))
		{
			throw new CorruptFileException("Provided data does not start with the file identifier");
		}

		major_version = data.get(3);
		revision_number = data.get(4);
		flags = HeaderFlags.forValue(data.get(5));

		if (major_version == 2 && (flags.getValue() & 127) != 0)
		{
			throw new CorruptFileException("Invalid flags set on version 2 tag.");
		}

		if (major_version == 3 && (flags.getValue() & 15) != 0)
		{
			throw new CorruptFileException("Invalid flags set on version 3 tag.");
		}

		if (major_version == 4 && (flags.getValue() & 7) != 0)
		{
			throw new CorruptFileException("Invalid flags set on version 4 tag.");
		}

		for (int i = 6; i < 10; i++)
		{
			if (data.get(i) >= 128)
			{
				throw new CorruptFileException("One of the bytes in the header was greater than the allowed 128.");
			}
		}

		tag_size = SynchData.ToUInt(data.Mid(6, 4));
	}

	/** 
		Constructs and intializes a new instance of <see
		cref="Footer" /> by reading in the contents of the header
		object used for the same tag.
	 
	 @param header
		A <see cref="Header" /> object to base the new instance
		off of.
	 
	*/
	public Footer(Header header)
	{
		major_version = header.getMajorVersion();
		revision_number = header.getRevisionNumber();
		flags = Rasad.Core.Media.MediaMetadataManagement.Id3v2.HeaderFlags.forValue(header.getFlags().getValue() | HeaderFlags.FooterPresent.getValue());
		tag_size = header.getTagSize();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets and sets the major version of the tag described by
		the current instance.
	 
	 <value>
		A <see cref="byte" /> value specifying the ID3v2 version
		of tag described by the current instance.
	 </value>
	 
		When the version is set, unsupported header flags will
		automatically be removed from the tag.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="value" /> is not 4.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getMajorVersion()
	public byte getMajorVersion()
	{
		return major_version == 0 ? Tag.getDefaultVersion() : major_version;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setMajorVersion(byte value)
	public void setMajorVersion(byte value)
	{
		if (value != 4)
		{
			throw new IllegalArgumentException("Version unsupported.");
		}

		major_version = value;
	}

	/** 
		Gets and sets the version revision number of the tag
		represented by the current instance.
	 
	 <value>
		A <see cref="byte" /> value containing the version
		revision number of the tag represented by the current
		instance.
	 </value>
	 
		This value should always be zeroed. A non-zero value
		indicates an experimental or new version of the format
		which may not be completely understood by the current
		implementation. Some software may refuse to read tags
		with a non-zero value.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getRevisionNumber()
	public byte getRevisionNumber()
	{
		return revision_number;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setRevisionNumber(byte value)
	public void setRevisionNumber(byte value)
	{
		revision_number = value;
	}

	/** 
		Gets and sets the flags applied to the current instance.
	 
	 <value>
		A bitwise combined <see cref="HeaderFlags" /> value
		containing the flags applied to the current instance.
	 </value>
	 @exception ArgumentException
		<paramref name="value" /> contains a flag not supported
		by the the ID3v2 version of the current instance.
	 
	*/
	public HeaderFlags getFlags()
	{
		return flags;
	}
	public void setFlags(HeaderFlags value)
	{
		if (0.getValue() != (value.getValue() & (HeaderFlags.ExtendedHeader.getValue() | HeaderFlags.ExperimentalIndicator.getValue()).getValue()) && getMajorVersion() < 3)
		{
			throw new IllegalArgumentException("Feature only supported in version 2.3+", "value");
		}

		if (0.getValue() != (value.getValue() & HeaderFlags.FooterPresent.getValue()) && getMajorVersion() < 3)
		{
			throw new IllegalArgumentException("Feature only supported in version 2.4+", "value");
		}

		flags = value;
	}

	/** 
		Gets and sets the size of the tag described by the
		current instance, minus the header and footer.
	 
	 <value>
		A <see cref="uint" /> value containing the size of the
		tag described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getTagSize()
	public int getTagSize()
	{
		return tag_size;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setTagSize(uint value)
	public void setTagSize(int value)
	{
		tag_size = value;
	}

	/** 
		Gets the complete size of the tag described by the
		current instance, including the header and footer.
	 
	 <value>
		A <see cref="uint" /> value containing the complete size
		of the tag described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getCompleteTagSize()
	public int getCompleteTagSize()
	{
		return getTagSize() + Header.Size + Size;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance as a raw ID3v2 header.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered header.
	 
	*/
	public ByteVector Render()
	{
		ByteVector v = new ByteVector();
		v.add(FileIdentifier);
		v.add(getMajorVersion());
		v.add(getRevisionNumber());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add((byte)flags);
		v.add((byte)flags.getValue());
		v.add(SynchData.FromUInt(getTagSize()));
		return v;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

	public Footer clone()
	{
		Footer varCopy = new Footer();

		varCopy.major_version = this.major_version;
		varCopy.revision_number = this.revision_number;
		varCopy.flags = this.flags;
		varCopy.tag_size = this.tag_size;

		return varCopy;
	}
}