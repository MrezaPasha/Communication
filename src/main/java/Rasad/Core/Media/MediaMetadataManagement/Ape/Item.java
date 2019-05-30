package Rasad.Core.Media.MediaMetadataManagement.Ape;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	This class provides a representation of an APEv2 tag item which
	can be read from and written to disk.
*/
public class Item implements Cloneable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the type of data stored in the item.
	*/
	private ItemType type = ItemType.Text;

	/** 
		Contains the item key.
	*/
	private String key = null;

	/** 
		Contains the item value.
	*/
	private ReadOnlyByteVector data = null;

	/** 
		Contains the item text.
	*/
	private String [] text = null;

	/** 
		Indicates whether or not the item is read only.
	*/
	private boolean read_only = false;

	/** 
		Contains the size of the item on disk.
	*/
	private int size_on_disk;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Item" />  by reading in a raw APEv2 item.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the item to
		read.
	 
	 @param offset
		A <see cref="int" /> value specifying the offset in
		<paramref name="data" /> at which the item data begins.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="offset" /> is less than zero.
	 
	 @exception CorruptFileException
		A complete item could not be read.
	 
	*/
	public Item(ByteVector data, int offset)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		Parse(data, offset);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Item" /> with a specified key and value.
	 
	 @param key
		A <see cref="string" /> object containing the key to use
		for the current instance.
	 
	 @param value
		A <see cref="string" /> object containing the value to
		store in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> or <paramref name="value" /> is
		<see langword="null" />.
	 
	*/
	public Item(String key, String value)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (value == null)
		{
			throw new NullPointerException("value");
		}

		this.key = key;
		this.text = new String [] {value};
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Item" /> with a specified key and collection of
		values.
	 
	 @param key
		A <see cref="string" /> object containing the key to use
		for the current instance.
	 
	 @param value
		A <see cref="string[]" /> containing the values to store
		in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> or <paramref name="value" /> is
		<see langword="null" />.
	 
	*/
	public Item(String key, String... value)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (value == null)
		{
			throw new NullPointerException("value");
		}

		this.key = key;
		this.text = (String[]) value.clone();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Item" /> with a specified key and collection of
		values.
	 
	 @param key
		A <see cref="string" /> object containing the key to use
		for the current instance.
	 
	 @param value
		A <see cref="StringCollection" /> object containing the
		values to store in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> or <paramref name="value" /> is
		<see langword="null" />.
	 
	 {@link Item(string,string[]) }
	*/
	@Deprecated
	public Item(String key, StringCollection value)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (value == null)
		{
			throw new NullPointerException("value");
		}

		this.key = key;
		this.text = value.toArray(new Object[0]);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Item" /> with a specified key and raw data.
	 
	 @param key
		A <see cref="string" /> object containing the key to use
		for the current instance.
	 
	 @param value
		A <see cref="StringCollection" /> object containing the
		values to store in the new instance.
	 
	 
		This constructor automatically marks the new instance as
		<see cref="ItemType.Binary" />.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> or <paramref name="value" /> is
		<see langword="null" />.
	 
	 {@link Item(string,string[]) }
	*/
	public Item(String key, ByteVector value)
	{
		this.key = key;
		this.type = ItemType.Binary;

		data = value instanceof ReadOnlyByteVector ? (ReadOnlyByteVector)value : null;
		if (data == null)
		{
			data = new ReadOnlyByteVector(value);
		}
	}

	private Item(Item item)
	{
		type = item.type;
		key = item.key;
		if (item.data != null)
		{
			data = new ReadOnlyByteVector(item.data);
		}
		if (item.text != null)
		{
			text = (String[]) item.text.clone();
		}
		read_only = item.read_only;
		size_on_disk = item.size_on_disk;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the key used to identify the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the key used to
		identify the current instance.
	 </value>
	 
		This value is used for specifying the contents of the
		item in a common and consistant fashion. For example,
		<c>"TITLE"</c> specifies that the item contains the title
		of the track.
	 
	*/
	public final String getKey()
	{
		return key;
	}

	/** 
		Gets the binary value stored in the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the binary
		value stored in the current instance, or <see
		langword="null" /> if the item contains text.
	 </value>
	*/
	public final ByteVector getValue()
	{
		return (type == ItemType.Binary) ? data : null;
	}

	/** 
		Gets the size of the current instance as it last appeared
		on disk.
	 
	 <value>
		A <see cref="int" /> value containing the size of the
		current instance as it last appeared on disk.
	 </value>
	*/
	public final int getSize()
	{
		return size_on_disk;
	}

	/** 
		Gets and sets the type of value contained in the
		current instance.
	 
	 <value>
		A <see cref="ItemType" /> value indicating the type of
		value contained in the current instance.
	 </value>
	*/
	public final ItemType getType()
	{
		return type;
	}
	public final void setType(ItemType value)
	{
		type = value;
	}

	/** 
		Gets and sets whether or not the current instance is
		flagged as read-only on disk.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		current instance is flagged as read-only on disk.
	 </value>
	*/
	public final boolean getReadOnly()
	{
		return read_only;
	}
	public final void setReadOnly(boolean value)
	{
		read_only = value;
	}

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		current instance contains no value.
	 </value>
	*/
	public final boolean getIsEmpty()
	{
		if (type != ItemType.Binary)
		{
			return text == null || text.length == 0;
		}
		else
		{
			return data == null || data.getIsEmpty();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Gets the contents of the current instance as a <see
		cref="string" />.
	 
	 @return 
		<p>A <see cref="string" /> object containing the text
		stored in the current instance, or <see langword="null"
		/> if the item is empty of contains binary data.</p>
		<p>If the current instance contains multiple string
		values, they will be returned as a comma separated
		value.</p>
	 
	*/
	@Override
	public String toString()
	{
		if (type == ItemType.Binary || text == null)
		{
			return null;
		}
		else
		{
			return tangible.StringHelper.join(", ", text);
		}
	}

	/** 
		Gets the contents of the current instance as a <see
		cref="string" /> array.
	 
	 @return 
		A <see cref="string[]" /> containing the text stored in
		the current instance, or an empty array if the item
		contains binary data.
	 
	*/
	public final String[] ToStringArray()
	{
		if (type == ItemType.Binary || text == null)
		{
			return new String [0];
		}

		return text;
	}

	/** 
		Renders the current instance as an APEv2 item.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	public final ByteVector Render()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint flags = (uint)((ReadOnly) ? 1 : 0) | ((uint) Type << 1);
		int flags = (int)((getReadOnly()) ? 1 : 0) | ((int) getType().getValue() << 1);

		if (getIsEmpty())
		{
			return new ByteVector();
		}

		ByteVector result = null;

		if (type == ItemType.Binary)
		{
			if (text == null && data != null)
			{
				result = data;
			}
		}

		if (result == null && text != null)
		{
			result = new ByteVector();

			for (int i = 0; i < text.length; i++)
			{
				if (i != 0)
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: result.Add((byte) 0);
					result.add((byte) 0);
				}

				result.add(ByteVector.FromString(text [i], StringType.UTF8));
			}
		}

		// If no data is stored, don't write the item.
		if (result == null || result.size() == 0)
		{
			return new ByteVector();
		}

		ByteVector output = new ByteVector();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add(ByteVector.FromUInt((uint) result.Count, false));
		output.add(ByteVector.FromUInt((int) result.size(), false));
		output.add(ByteVector.FromUInt(flags, false));
		output.add(ByteVector.FromString(key, StringType.UTF8));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Add((byte) 0);
		output.add((byte) 0);
		output.add(result);

		size_on_disk = output.size();

		return output;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Populates the current instance by reading in a raw APEv2
		item.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the item to
		read.
	 
	 @param offset
		A <see cref="int" /> value specifying the offset in
		<paramref name="data" /> at which the item data begins.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="offset" /> is less than zero.
	 
	 @exception CorruptFileException
		A complete item could not be read.
	 
	*/
	protected final void Parse(ByteVector data, int offset)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (offset < 0)
		{
			throw new IndexOutOfBoundsException("offset");
		}


		// 11 bytes is the minimum size for an APE item
		if (data.size() < offset + 11)
		{
			throw new CorruptFileException("Not enough data for APE Item");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value_length = data.Mid(offset, 4).ToUInt(false);
		int value_length = data.Mid(offset, 4).ToUInt(false);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint flags = data.Mid(offset + 4, 4).ToUInt(false);
		int flags = data.Mid(offset + 4, 4).ToUInt(false);

		setReadOnly((flags & 1) == 1);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		setType(ItemType.forValue((flags >>> 1) & 3));

		int pos = data.Find(ByteVector.TextDelimiter(StringType.UTF8), offset + 8);

		key = data.toString(StringType.UTF8, offset + 8, pos - offset - 8);

		if (value_length > data.size() - pos - 1)
		{
			throw new CorruptFileException("Invalid data length.");
		}

		size_on_disk = pos + 1 + (int) value_length - offset;

		if (getType() == ItemType.Binary)
		{
			this.data = new ReadOnlyByteVector(data.Mid(pos + 1, (int) value_length));
		}
		else
		{
			this.text = data.Mid(pos + 1, (int) value_length).ToStrings(StringType.UTF8, 0);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region ICloneable

	/** 
		Creates a deep copy of the current instance.
	 
	 @return 
		A new <see cref="Item"/> object identical to the current
		instance.
	 
	*/
	public final Item Clone()
	{
		return new Item(this);
	}

	public final Object Clone()
	{
		return Clone();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}