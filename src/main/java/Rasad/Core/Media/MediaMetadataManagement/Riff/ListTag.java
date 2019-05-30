package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// ListTag.cs:
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
	This abstract class extends <see cref="Tag" /> to provide support
	for reading and writing tags stored in the RIFF list format.
*/
public abstract class ListTag extends Tag
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the <see cref="List" /> object.
	*/
	private List fields;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="ListTag" /> with no contents.
	*/
	protected ListTag()
	{
		fields = new List();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="MovieIdTag" /> using a specified RIFF list.
	 
	 @param fields
		A <see cref="List"/> object to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="fields" /> is <see langword="null" />.
	 
	*/
	protected ListTag(List fields)
	{
		if (fields == null)
		{
			throw new NullPointerException("fields");
		}

		this.fields = fields;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ListTag" /> by reading the contents of a raw
		RIFF list stored in a <see cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector"/> containing a raw RIFF list to
		read into the new instance.
	 
	*/
	protected ListTag(ByteVector data)
	{
		fields = new List(data);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ListTag" /> by reading the contents of a raw RIFF
		list from a specified position in a <see
		cref="Rasad.Core.Media.MediaMetadataManagement.File" />.
	 
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
	protected ListTag(Rasad.Core.Media.MediaMetadataManagement.File file, long position, int length)
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

		fields = new List(file, position, length);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance enclosed in the appropriate
		item.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the rendered
		version of the current instance.
	 
	*/
	public abstract ByteVector RenderEnclosed();

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
	protected final ByteVector RenderEnclosed(ByteVector id)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		return fields.RenderEnclosed(id);
	}

	/** 
		Renders the current instance as a raw RIFF list.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the rendered
		version of the current instance.
	 
	*/
	public final ByteVector Render()
	{
		return fields.Render();
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

		return fields.GetValues(id);
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

		return fields.GetValuesAsStrings(id);
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
		return new StringCollection(fields.GetValuesAsStrings(id));
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

		return fields.GetValueAsUInt(id);
	}

	/** 
		Sets the value for a specified item in the current
		instance to the contents of a <see cref="ByteVector[]"
		/>.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @param value
		A <see cref="ByteVector[]" /> containing the values to
		store in the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final void SetValue(ByteVector id, ByteVector... value)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		fields.SetValue(id, value);
	}

	/** 
		Sets the value for a specified item in the current
		instance to the contents of a <see
		cref="ByteVectorCollection" />.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @param value
		A <see cref="ByteVectorCollection" /> object containing
		the values to store in the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final void SetValue(ByteVector id, ByteVectorCollection value)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		fields.SetValue(id, value);
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
		fields.SetValue(id, value);
	}

	/** 
		Sets the value for a specified item in the current
		instance to the contents of a <see
		cref="StringCollection" />.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @param value
		A <see cref="StringCollection" /> object containing the
		values to store in the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	@Deprecated
	public final void SetValue(ByteVector id, StringCollection value)
	{
		fields.SetValue(id, value);
	}

	/** 
		Sets the value for a specified item in the current
		instance to the contents of a <see cref="string[]" />.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the ID of
		the item to set.
	 
	 @param value
		A <see cref="string[]" /> containing the values to store
		in the specified item.
	 
	 @exception ArgumentNullException
		<paramref name="id" /> is <see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="id" /> isn't exactly four bytes long.
	 
	*/
	public final void SetValue(ByteVector id, String... value)
	{
		if (id == null)
		{
			throw new NullPointerException("id");
		}

		if (id.size() != 4)
		{
			throw new IllegalArgumentException("ID must be 4 bytes long.", "id");
		}

		fields.SetValue(id, value);
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

		fields.RemoveValue(id);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if the current instance does not
		any values. Otherwise <see langword="false" />.
	 </value>
	*/
	@Override
	public boolean getIsEmpty()
	{
		return fields.isEmpty();
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		fields.clear();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}