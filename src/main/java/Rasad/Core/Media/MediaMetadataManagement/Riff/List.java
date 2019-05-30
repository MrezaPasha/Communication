package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;
import java.io.*;

//
// List.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
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
	This class extends <see
	cref="T:System.Collections.Generic.Dictionary`2" /> to provide
	support for reading and writing RIFF lists.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Serializable][ComVisible(false)] public class List : Dictionary <ByteVector,ByteVectorCollection>
public class List extends HashMap<ByteVector,ByteVectorCollection> implements Serializable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="List" /> with no contents.
	*/
	public List()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="List" /> by reading the contents of a raw RIFF
		list stored in a <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector"/> containing a raw RIFF list to
		read into the new instance.
	 
	*/
	public List(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		Parse(data);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="List" /> by reading the contents of a raw RIFF list
		from a specified position in a <see cref="Rasad.Core.Media.MediaMetadataManagement.File"/>.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		from which the contents of the new instance is to be
		read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read the list.
	 
	 @param length
		A <see cref="int" /> value specifying the number of bytes
		to read.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	*/
	public List(Rasad.Core.Media.MediaMetadataManagement.File file, long position, int length)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (length < 0)
		{
			throw new IndexOutOfBoundsException("length");
		}

		if (position < 0 || position > file.getLength() - length)
		{
			throw new IndexOutOfBoundsException("position");
		}

		file.Seek(position);
		Parse(file.ReadBlock(length));
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="List" /> from a specified serialization info and
		streaming context.
	 
	 @param info
		A <see cref="SerializationInfo" /> object containing the
		serialized data to be used for the new instance.
	 
	 @param context
		A <see cref="StreamingContext" /> object containing the
		streaming context information for the new instance.
	 
	 
		This constructor is implemented because <see
		cref="List" /> implements the <see cref="ISerializable"
		/> interface.
	 
	*/
	protected List(SerializationInfo info, StreamingContext context)
	{
		super(info, context);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance as a raw RIFF list.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the rendered
		version of the current instance.
	 
	*/
	public final ByteVector Render()
	{
		ByteVector data = new ByteVector();

		for (ByteVector id : this.keySet())
		{
			for (ByteVector value : this.get(id))
			{
				if (value.size() == 0)
				{
					continue;
				}

				data.add(id);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(ByteVector.FromUInt((uint) value.Count, false));
				data.add(ByteVector.FromUInt((int) value.size(), false));
				data.add(value);

				if (value.size() % 2 == 1)
				{
					data.add(0);
				}
			}
		}

		return data;
	}

	/** 
		Renders the current instance enclosed in an item with a
		specified ID.
	 
	 @param id
		A <see cref="ByteVector"/> object containing the ID of
		the item to enclose the current instance in.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the rendered
		version of the current instance.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final ByteVector RenderEnclosed(ByteVector id)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		ByteVector data = Render();

		if (data.size() <= 8)
		{
			return new ByteVector();
		}

		ByteVector header = new ByteVector("LIST");
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: header.Add(ByteVector.FromUInt((uint)(data.Count + 4), false));
		header.add(ByteVector.FromUInt((int)(data.size() + 4), false));
		header.add(id);
		data.add(0, header);
		return data;
	}

	/** 
		Gets the values for a specified item in the current
		instance as a <see cref="ByteVectorCollection" />.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @return 
		A <see cref="ByteVectorCollection" /> object containing
		the values of the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final ByteVectorCollection GetValues(ByteVector id)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		ByteVectorCollection value;

		tangible.OutObject<ByteVectorCollection> tempOut_value = new tangible.OutObject<ByteVectorCollection>();
		ByteVectorCollection tempVar = this.TryGetValue(id, tempOut_value) ? value : new ByteVectorCollection();
	value = tempOut_value.argValue;
	return tempVar;
	}

	/** 
		Gets the values for a specified item in the current
		instance as a <see cref="string[]" />.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @return 
		A <see cref="string[]" /> containing the values of the
		specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final String[] GetValuesAsStrings(ByteVector id)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		ByteVectorCollection values = GetValues(id);

		String [] result = new String [values.size()];

		for (int i = 0; i < result.length; i++)
		{
			ByteVector data = values.get(i);

			if (data == null)
			{
				result [i] = "";
				continue;
			}

			int length = data.size();
			while (length > 0 && data.get(length - 1) == 0)
			{
				length--;
			}

			result [i] = data.toString(StringType.UTF8, 0, length);
		}

		return result;
	}

	/** 
		Gets the values for a specified item in the current
		instance as a <see cref="StringCollection" />.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @return 
		A <see cref="StringCollection" /> object containing the
		values of the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	@Deprecated
	public final StringCollection GetValuesAsStringCollection(ByteVector id)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		return new StringCollection(GetValuesAsStrings(id));
	}

	/** 
		Gets the value for a specified item in the current
		instance as a <see cref="uint"/>.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @return 
		A <see cref="uint" /> value containing the first value
		with the specified ID that could be converted to an
		integer, or zero if none could be found.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint GetValueAsUInt(ByteVector id)
	public final int GetValueAsUInt(ByteVector id)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		for (String text : GetValuesAsStrings(id))
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
			int value;
			tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (uint.TryParse(text, out value))
			if (tangible.TryParseHelper.tryParseInt(text, tempOut_value))
			{
			value = tempOut_value.argValue;
				return value;
			}
		else
		{
			value = tempOut_value.argValue;
		}
		}

		return 0;
	}

	/** 
		Sets the value for a specified item in the current
		instance to the contents of a <see
		cref="T:System.Collections.Generic.IEnumerable`1" />.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @param values
		A <see cref="T:System.Collections.Generic.IEnumerable`1"
		/> containing the <see cref="ByteVector"/> objects to
		store in the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final void SetValue(ByteVector id, java.lang.Iterable<ByteVector> values)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		if (values == null)
		{
			RemoveValue(id);
		}
		else if (this.containsKey(id))
		{
			this.put(id, new ByteVectorCollection(values));
		}
		else
		{
			this.put(id, new ByteVectorCollection(values));
		}
	}

	/** 
		Sets the value for a specified item in the current
		instance to the contents of a <see cref="ByteVector[]"
		/>.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @param values
		A <see cref="ByteVector[]" /> containing the values to
		store in the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final void SetValue(ByteVector id, ByteVector... values)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		if (values == null || values.length == 0)
		{
			RemoveValue(id);
		}
		else
		{
			SetValue(id, values instanceof java.lang.Iterable<ByteVector> ? (java.lang.Iterable<ByteVector>)values : null);
		}
	}

	/** 
		Sets the value for a specified item in the current
		instance to the value of a <see cref="uint"/>.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @param value
		A <see cref="uint" /> value to store in the specified
		item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetValue(ByteVector id, uint value)
	public final void SetValue(ByteVector id, int value)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		if (value == 0)
		{
			RemoveValue(id);
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetValue(id, value.ToString(CultureInfo.InvariantCulture));
			SetValue(id, (new Integer(value)).toString(CultureInfo.InvariantCulture));
		}
	}

	/** 
		Sets the value for a specified item in the current
		instance to the contents of a <see
		cref="T:System.Collections.Generic.IEnumerable`1" />.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @param values
		A <see cref="T:System.Collections.Generic.IEnumerable`1"
		/> containing the <see cref="string"/> objects to store
		in the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final void SetValue(ByteVector id, java.lang.Iterable<String> values)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		if (values == null)
		{
			RemoveValue(id);
			return;
		}

		ByteVectorCollection l = new ByteVectorCollection();
		for (String value : values)
		{
			if (tangible.StringHelper.isNullOrEmpty(value))
			{
				continue;
			}

			ByteVector data = ByteVector.FromString(value, StringType.UTF8);
			data.add(0);
			l.add(data);
		}

		if (l.size() == 0)
		{
			RemoveValue(id);
		}
		else
		{
			SetValue(id, l);
		}
	}

	/** 
		Sets the value for a specified item in the current
		instance to the contents of a <see cref="string[]" />.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @param values
		A <see cref="string[]" /> containing the values to store
		in the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final void SetValue(ByteVector id, String... values)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		if (values == null || values.length == 0)
		{
			RemoveValue(id);
		}
		else
		{
			SetValue(id, values instanceof java.lang.Iterable<String> ? (java.lang.Iterable<String>)values : null);
		}
	}

	/** 
		Removes the item with the specified ID from the current
		instance.
	 
	 @param id
		A <see cref="ByteVector"/> object containing the ID of
		the item to remove from the current instance.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final void RemoveValue(ByteVector id)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		if (this.containsKey(id))
		{
			this.remove(id);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	/** 
		Populates the current instance by reading in the contents
		of a raw RIFF list stored in a <see cref="ByteVector" />
		object.
	 
	 @param data
		A <see cref="ByteVector"/> containing a raw RIFF list to
		read into the current instance.
	 
	*/
	private void Parse(ByteVector data)
	{
		int offset = 0;
		while (offset + 8 < data.size())
		{
			ByteVector id = data.Mid(offset, 4);
			int length = (int) data.Mid(offset + 4, 4).ToUInt(false);

			if (!this.containsKey(id))
			{
				this.put(id, new ByteVectorCollection());
			}

			this.get(id).Add(data.Mid(offset + 8, length));

			if (length % 2 == 1)
			{
				length++;
			}

			offset += 8 + length;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}