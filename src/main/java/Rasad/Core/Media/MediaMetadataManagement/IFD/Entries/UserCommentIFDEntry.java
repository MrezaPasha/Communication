package Rasad.Core.Media.MediaMetadataManagement.IFD.Entries;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// UserCommentIFDEntry.cs:
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
	Contains an ASCII STRING value.
*/
public class UserCommentIFDEntry implements IFDEntry
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constant Values

	/** 
	   Marker for an ASCII-encoded UserComment tag.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static readonly ByteVector COMMENT_ASCII_CODE = new byte[] {0x41, 0x53, 0x43, 0x49, 0x49, 0x00, 0x00, 0x00};
	public static final ByteVector COMMENT_ASCII_CODE = new byte[] {0x41, 0x53, 0x43, 0x49, 0x49, 0x00, 0x00, 0x00};

	/** 
	   Marker for a JIS-encoded UserComment tag.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static readonly ByteVector COMMENT_JIS_CODE = new byte[] {0x4A, 0x49, 0x53, 0x00, 0x00, 0x00, 0x00, 0x00};
	public static final ByteVector COMMENT_JIS_CODE = new byte[] {0x4A, 0x49, 0x53, 0x00, 0x00, 0x00, 0x00, 0x00};

	/** 
	   Marker for a UNICODE-encoded UserComment tag.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static readonly ByteVector COMMENT_UNICODE_CODE = new byte[] {0x55, 0x4E, 0x49, 0x43, 0x4F, 0x44, 0x45, 0x00};
	public static final ByteVector COMMENT_UNICODE_CODE = new byte[] {0x55, 0x4E, 0x49, 0x43, 0x4F, 0x44, 0x45, 0x00};

	/** 
	   Corrupt marker that seems to be resembling unicode.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static readonly ByteVector COMMENT_BAD_UNICODE_CODE = new byte[] {0x55, 0x6E, 0x69, 0x63, 0x6F, 0x64, 0x65, 0x00};
	public static final ByteVector COMMENT_BAD_UNICODE_CODE = new byte[] {0x55, 0x6E, 0x69, 0x63, 0x6F, 0x64, 0x65, 0x00};

	/** 
	   Marker for a UserComment tag with undefined encoding.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static readonly ByteVector COMMENT_UNDEFINED_CODE = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	public static final ByteVector COMMENT_UNDEFINED_CODE = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Properties

	/** <value>
		The ID of the tag, the current instance belongs to
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort Tag;
	private short Tag;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getTag()
	public final short getTag()
	{
		return Tag;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setTag(ushort value)
	private void setTag(short value)
	{
		Tag = value;
	}

	/** <value>
		The value which is stored by the current instance
	 </value>
	*/
	private String Value;
	public final String getValue()
	{
		return Value;
	}
	private void setValue(String value)
	{
		Value = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Construcor.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag ID of the entry this instance
		represents
	 
	 @param value
		A <see cref="string"/> to be stored
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public UserCommentIFDEntry(ushort tag, string value)
	public UserCommentIFDEntry(short tag, String value)
	{
		setTag(tag);
		setValue(value);
	}

	/** 
		Construcor.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag ID of the entry this instance
		represents
	 
	 @param data
		A <see cref="ByteVector"/> to be stored
	 
	 @param file
		The file that's currently being parsed, used for reporting corruptions.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public UserCommentIFDEntry(ushort tag, ByteVector data, Rasad.Core.Media.MediaMetadataManagement.File file)
	public UserCommentIFDEntry(short tag, ByteVector data, Rasad.Core.Media.MediaMetadataManagement.File file)
	{
		setTag(tag);

		if (data.StartsWith(COMMENT_ASCII_CODE))
		{
			setValue(TrimNull(data.toString(StringType.Latin1, COMMENT_ASCII_CODE.size(), data.size() - COMMENT_ASCII_CODE.size())));
			return;
		}

		if (data.StartsWith(COMMENT_UNICODE_CODE))
		{
			setValue(TrimNull(data.toString(StringType.UTF8, COMMENT_UNICODE_CODE.size(), data.size() - COMMENT_UNICODE_CODE.size())));
			return;
		}

		String trimmed = data.toString().trim();
		if (trimmed.length() == 0 || trimmed.equals("\0"))
		{
			setValue("");
			return;
		}

		// Some programs like e.g. CanonZoomBrowser inserts just the first 0x00-byte
		// followed by 7-bytes of trash.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (data.StartsWith((byte) 0x00) && data.Count >= 8)
		if (data.StartsWith((byte) 0x00) && data.size() >= 8)
		{

			// And CanonZoomBrowser fills some trailing bytes of the comment field
			// with '\0'. So we return only the characters before the first '\0'.
			int term = data.Find("\0", 8);
			if (term != -1)
			{
				setValue(data.toString(StringType.Latin1, 8, term - 8));
			}
			else
			{
				setValue(data.toString(StringType.Latin1, 8, data.size() - 8));
			}
			return;
		}

		if (data.getData().length == 0)
		{
			setValue("");
			return;
		}

		// Try to parse anyway
		int offset = 0;
		int length = data.size() - offset;

		// Corruption that starts with a Unicode header and a count byte.
		if (data.StartsWith(COMMENT_BAD_UNICODE_CODE))
		{
			offset = COMMENT_BAD_UNICODE_CODE.size();
			length = data.size() - offset;
		}

		file.MarkAsCorrupt("UserComment with other encoding than Latin1 or Unicode");
		setValue(TrimNull(data.toString(StringType.UTF8, offset, length)));
	}

	private String TrimNull(String value)
	{
		int term = value.indexOf('\0');
		if (term > -1)
		{
			value = value.substring(0, term);
		}
		return value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance to a <see cref="ByteVector"/>
	 
	 @param is_bigendian
		A <see cref="System.Boolean"/> indicating the endianess for rendering.
	 
	 @param offset
		A <see cref="System.UInt32"/> with the offset, the data is stored.
	 
	 @param type
		A <see cref="System.UInt16"/> the ID of the type, which is rendered
	 
	 @param count
		A <see cref="System.UInt32"/> with the count of the values which are
		rendered.
	 
	 @return 
		A <see cref="ByteVector"/> with the rendered data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ByteVector Render(bool is_bigendian, uint offset, out ushort type, out uint count)
	public final ByteVector Render(boolean is_bigendian, int offset, tangible.OutObject<Short> type, tangible.OutObject<Integer> count)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: type = (ushort) IFDEntryType.Undefined;
		type.argValue = (short) IFDEntryType.Undefined.getValue();

		ByteVector data = new ByteVector();
		data.add(COMMENT_UNICODE_CODE);
		data.add(ByteVector.FromString(getValue(), StringType.UTF8));

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: count = (uint) data.Count;
		count.argValue = (int) data.size();

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}