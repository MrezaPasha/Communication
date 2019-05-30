package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	This class provides a representation of an ASF Content
	Descriptor to be used in combination with <see
	cref="ExtendedContentDescriptionObject" />.
*/
public class ContentDescriptor
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the data type.
	*/
	private DataType type = DataType.Unicode;

	/** 
		Contains the descriptor name.
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

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="ContentDescriptor" /> with a specified name and
		and value.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="string" /> object containing the value for
		the new instance.
	 
	*/
	public ContentDescriptor(String name, String value)
	{
		this.name = name;
		this.strValue = value;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ContentDescriptor" /> with a specified name and
		and value.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="ByteVector" /> object containing the value
		for the new instance.
	 
	*/
	public ContentDescriptor(String name, ByteVector value)
	{
		this.name = name;
		this.type = DataType.Bytes;
		this.byteValue = new ByteVector(value);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ContentDescriptor" /> with a specified name and
		and value.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="uint" /> value containing the value
		for the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ContentDescriptor(string name, uint value)
	public ContentDescriptor(String name, int value)
	{
		this.name = name;
		this.type = DataType.DWord;
		this.longValue = value;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ContentDescriptor" /> with a specified name and
		and value.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="ulong" /> value containing the value
		for the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ContentDescriptor(string name, ulong value)
	public ContentDescriptor(String name, long value)
	{
		this.name = name;
		this.type = DataType.QWord;
		this.longValue = value;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ContentDescriptor" /> with a specified name and
		and value.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="ushort" /> value containing the value
		for the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ContentDescriptor(string name, ushort value)
	public ContentDescriptor(String name, short value)
	{
		this.name = name;
		this.type = DataType.Word;
		this.longValue = value;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ContentDescriptor" /> with a specified name and
		and value.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		new instance.
	 
	 @param value
		A <see cref="bool" /> value containing the value
		for the new instance.
	 
	*/
	public ContentDescriptor(String name, boolean value)
	{
		this.name = name;
		this.type = DataType.Bool;
		this.longValue = value ? 1 : 0;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ContentDescriptor" /> by reading its contents from
		a file.
	 
	 @param file
		A <see cref="Asf.File" /> object to read the raw ASF
		Description Record from.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		A valid descriptor could not be read.
	 
	 
		<paramref name="file" /> must be at a seek position at
		which the descriptor can be read.
	 
	*/
	protected ContentDescriptor(Asf.File file)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (!Parse(file))
		{
			throw new CorruptFileException("Failed to parse content descriptor.");
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

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
		default:
			return null;
		}

		ByteVector name = Object.RenderUnicode(this.name);

		ByteVector output = new ByteVector();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(Object.RenderWord((ushort) name.Count));
		output.add(Object.RenderWord((short) name.size()));
		output.add(name);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(Object.RenderWord((ushort) type));
		output.add(Object.RenderWord((short) type.getValue()));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(Object.RenderWord((ushort) value.Count));
		output.add(Object.RenderWord((short) value.size()));
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
		Content Descriptor from.
	 
	 @return 
		<see langword="true" /> if the data was read correctly.
		Otherwise <see langword="false" />.
	 
	*/
	protected final boolean Parse(Asf.File file)
	{
		int name_count = file.ReadWord();
		name = file.ReadUnicode(name_count);

		type = DataType.forValue(file.ReadWord());

		int value_count = file.ReadWord();
		switch (type)
		{
		case Word:
			longValue = file.ReadWord();
			break;

		case Bool:
			longValue = file.ReadDWord();
			break;

		case DWord:
			longValue = file.ReadDWord();
			break;

		case QWord:
			longValue = file.ReadQWord();
			break;

		case Unicode:
			strValue = file.ReadUnicode(value_count);
			break;

		case Bytes:
			byteValue = file.ReadBlock(value_count);
			break;

		default:
			return false;
		}

		return true;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}