package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// DescriptionRecord.cs: Provides a representation of an ASF Description Record
// to be used in combination with MetadataLibaryObject.
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
	This class provides a representation of an ASF Description Record
	to be used in combination with <see cref="MetadataLibraryObject"
	/>.
*/
public class DescriptionRecord
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the data type.
	*/
	private DataType type = DataType.Unicode;

	/** 
		Contains the language list index.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort lang_list_index = 0;
	private short lang_list_index = 0;

	/** 
		Contains the stream number.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort stream_number = 0;
	private short stream_number = 0;

	/** 
		Contains the record name.
	*/
	private String name = null;

	/** 
		Contains the string value.
	*/
	private String strValue = null;

	/** 
		Contains the byte value.
	*/
	private ByteVector byteValue = null;

	/** 
		Contains the long value.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong longValue = 0;
	private long longValue = 0;

	/** 
		Contains the GUID value.
	*/
	private UUID guidValue = UUID.Empty;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="DescriptionRecord" /> with a specified language,
		stream, name, and value.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the new instance.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the new instance.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="string" /> object containing the value for
		the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public DescriptionRecord(ushort languageListIndex, ushort streamNumber, string name, string value)
	public DescriptionRecord(short languageListIndex, short streamNumber, String name, String value)
	{
		this.lang_list_index = languageListIndex;
		this.stream_number = streamNumber;
		this.name = name;
		this.strValue = value;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="DescriptionRecord" /> with a specified language,
		stream, name, and value.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the new instance.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the new instance.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="ByteVector" /> object containing the value
		for the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public DescriptionRecord(ushort languageListIndex, ushort streamNumber, string name, ByteVector value)
	public DescriptionRecord(short languageListIndex, short streamNumber, String name, ByteVector value)
	{
		this.lang_list_index = languageListIndex;
		this.stream_number = streamNumber;
		this.name = name;
		this.type = DataType.Bytes;
		this.byteValue = new ByteVector(value);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="DescriptionRecord" /> with a specified language,
		stream, name, and value.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the new instance.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the new instance.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="uint" /> value containing the value
		for the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public DescriptionRecord(ushort languageListIndex, ushort streamNumber, string name, uint value)
	public DescriptionRecord(short languageListIndex, short streamNumber, String name, int value)
	{
		this.lang_list_index = languageListIndex;
		this.stream_number = streamNumber;
		this.name = name;
		this.type = DataType.DWord;
		this.longValue = value;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="DescriptionRecord" /> with a specified language,
		stream, name, and value.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the new instance.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the new instance.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="ulong" /> value containing the value
		for the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public DescriptionRecord(ushort languageListIndex, ushort streamNumber, string name, ulong value)
	public DescriptionRecord(short languageListIndex, short streamNumber, String name, long value)
	{
		this.lang_list_index = languageListIndex;
		this.stream_number = streamNumber;
		this.name = name;
		this.type = DataType.QWord;
		this.longValue = value;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="DescriptionRecord" /> with a specified language,
		stream, name, and value.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the new instance.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the new instance.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="ushort" /> value containing the value
		for the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public DescriptionRecord(ushort languageListIndex, ushort streamNumber, string name, ushort value)
	public DescriptionRecord(short languageListIndex, short streamNumber, String name, short value)
	{
		this.lang_list_index = languageListIndex;
		this.stream_number = streamNumber;
		this.name = name;
		this.type = DataType.Word;
		this.longValue = value;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="DescriptionRecord" /> with a specified language,
		stream, name, and value.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the new instance.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the new instance.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="bool" /> value containing the value
		for the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public DescriptionRecord(ushort languageListIndex, ushort streamNumber, string name, bool value)
	public DescriptionRecord(short languageListIndex, short streamNumber, String name, boolean value)
	{
		this.lang_list_index = languageListIndex;
		this.stream_number = streamNumber;
		this.name = name;
		this.type = DataType.Bool;
		this.longValue = value ? 1 : 0;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="DescriptionRecord" /> with a specified language,
		stream, name, and value.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the new instance.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the new instance.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="System.Guid" /> value containing the value
		for the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public DescriptionRecord(ushort languageListIndex, ushort streamNumber, string name, System.Guid value)
	public DescriptionRecord(short languageListIndex, short streamNumber, String name, UUID value)
	{
		this.lang_list_index = languageListIndex;
		this.stream_number = streamNumber;
		this.name = name;
		this.type = DataType.Guid;
		this.guidValue = value;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="DescriptionRecord" /> by reading its contents from
		a file.
	 
	 @param file
		A <see cref="Asf.File" /> object to read the raw ASF
		Description Record from.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		A valid record could not be read.
	 
	 
		<paramref name="file" /> must be at a seek position at
		which the record can be read.
	 
	*/
	protected DescriptionRecord(Asf.File file)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (!Parse(file))
		{
			throw new CorruptFileException("Failed to parse description record.");
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the index of the language associated with the
		current instance.
	 
	 <value>
		A <see cref="ushort" /> value containing the index of the
		language associated with the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getLanguageListIndex()
	public final short getLanguageListIndex()
	{
		return lang_list_index;
	}

	/** 
		Gets the index of the stream associated with the current
		instance.
	 
	 <value>
		A <see cref="ushort" /> value containing the index of the
		stream associated with the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getStreamNumber()
	public final short getStreamNumber()
	{
		return stream_number;
	}

	/** 
		Gets the name of the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the name of the
		current instance.
	 </value>
	*/
	public final String getName()
	{
		return name;
	}

	/** 
		Gets the type of data contained in the current instance.
	 
	 <value>
		A <see cref="DataType" /> value indicating type of data
		contained in the current instance.
	 </value>
	*/
	public final DataType getType()
	{
		return type;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Gets a string representation of the current instance.
	 
	 @return 
		A <see cref="string" /> object containing the value of
		the current instance.
	 
	*/
	@Override
	public String toString()
	{
		if (type == DataType.Unicode)
		{
			return strValue;
		}

		if (type == DataType.Bytes)
		{
			return byteValue.toString(StringType.UTF16LE);
		}

		return String.valueOf(longValue);
	}

	/** 
		Gets the binary contents of the current instance.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		contents of the current instance, or <see langword="null"
		/> if <see cref="Type" /> is unequal to <see
		cref="DataType.Bytes" />.
	 
	*/
	public final ByteVector ToByteVector()
	{
		return byteValue;
	}

	/** 
		Gets the boolean value contained in the current instance.
	 
	 @return 
		A <see cref="bool" /> value containing the value of the
		current instance.
	 
	*/
	public final boolean ToBool()
	{
		return longValue != 0;
	}

	/** 
		Gets the DWORD value contained in the current instance.
	 
	 @return 
		A <see cref="uint" /> value containing the value of the
		current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint ToDWord()
	public final int ToDWord()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;
		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == DataType.Unicode && strValue != null && uint.TryParse(strValue, out value))
		if (type == DataType.Unicode && strValue != null && tangible.TryParseHelper.tryParseInt(strValue, tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint) longValue;
		return (int) longValue;
	}

	/** 
		Gets the QWORD value contained in the current instance.
	 
	 @return 
		A <see cref="ulong" /> value containing the value of the
		current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong ToQWord()
	public final long ToQWord()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong value;
		long value;
		tangible.OutObject<Long> tempOut_value = new tangible.OutObject<Long>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == DataType.Unicode && strValue != null && ulong.TryParse(strValue, out value))
		if (type == DataType.Unicode && strValue != null && tangible.TryParseHelper.tryParseLong(strValue, tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}

		return longValue;
	}

	/** 
		Gets the WORD value contained in the current instance.
	 
	 @return 
		A <see cref="ushort" /> value containing the value of the
		current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort ToWord()
	public final short ToWord()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort value;
		short value;
		tangible.OutObject<Short> tempOut_value = new tangible.OutObject<Short>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (type == DataType.Unicode && strValue != null && ushort.TryParse(strValue, out value))
		if (type == DataType.Unicode && strValue != null && tangible.TryParseHelper.tryParseShort(strValue, tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (ushort) longValue;
		return (short) longValue;
	}

	/** 
		Gets the GUID value contained in the current instance.
	 
	 @return 
		A <see cref="System.Guid" /> value containing the value
		of the current instance.
	 
	*/
	public final UUID ToGuid()
	{
		return guidValue;
	}

	/** 
		Renders the current instance as a raw ASF Description
		Record.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	public final ByteVector Render()
	{
		ByteVector value = null;

		switch (type)
		{
		case Unicode:
			value = Object.RenderUnicode(strValue);
			break;
		case Bytes:
			value = byteValue;
			break;
		case Bool:
		case DWord:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: value = Object.RenderDWord((uint) longValue);
			value = Object.RenderDWord((int) longValue);
			break;
		case QWord:
			value = Object.RenderQWord(longValue);
			break;
		case Word:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: value = Object.RenderWord((ushort) longValue);
			value = Object.RenderWord((short) longValue);
			break;
		case Guid:
			value = guidValue.ToByteArray();
			break;
		default:
			return null;
		}

		ByteVector name = Object.RenderUnicode(this.name);

		ByteVector output = new ByteVector();
		output.add(Object.RenderWord(lang_list_index));
		output.add(Object.RenderWord(stream_number));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(Object.RenderWord((ushort) name.Count));
		output.add(Object.RenderWord((short) name.size()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(Object.RenderWord((ushort) type));
		output.add(Object.RenderWord((short) type.getValue()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(Object.RenderDWord((uint) value.Count));
		output.add(Object.RenderDWord((int) value.size()));
		output.add(name);
		output.add(value);

		return output;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Populates the current instance by reading in the contents
		from a file.
	 
	 @param file
		A <see cref="Asf.File" /> object to read the raw ASF
		Description Record from.
	 
	 @return 
		<see langword="true" /> if the data was read correctly.
		Otherwise <see langword="false" />.
	 
	*/
	protected final boolean Parse(Asf.File file)
	{
		// Field name          Field type Size (bits)
		// Language List Index WORD       16
		// Stream Number       WORD       16
		// Name Length         WORD       16
		// Data Type           WORD       16
		// Data Length         DWORD      32
		// Name                WCHAR      varies
		// Data                See below  varies

		lang_list_index = file.ReadWord();
		stream_number = file.ReadWord();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort name_length = file.ReadWord();
		short name_length = file.ReadWord();
		type = DataType.forValue(file.ReadWord());
		int data_length = (int) file.ReadDWord();
		name = file.ReadUnicode(name_length);

		switch (type)
		{
		case Word:
			longValue = file.ReadWord();
			break;
		case Bool:
		case DWord:
			longValue = file.ReadDWord();
			break;
		case QWord:
			longValue = file.ReadQWord();
			break;
		case Unicode:
			strValue = file.ReadUnicode(data_length);
			break;
		case Bytes:
			byteValue = file.ReadBlock(data_length);
			break;
		case Guid:
			guidValue = file.ReadGuid();
			break;
		default:
			return false;
		}

		return true;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}