package Rasad.Core.Media.MediaMetadataManagement.Ape;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Tag.cs: Provides a representation of an APEv2 tag which can be read from and
// written to disk.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   apetag.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2004 Allan Sandfeld Jensen (Original Implementation)
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> and implements <see
	cref="T:System.Collections.Generic.IEnumerable`1" /> to provide a representation of an APEv2
	tag which can be read from and written to disk.
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class Tag extends Rasad.Core.Media.MediaMetadataManagement.Tag implements java.lang.Iterable<String>
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Static Fields

	/** 
		Contains names of picture fields, indexed to correspond
		to their picture item names.
	*/
	private static String [] picture_item_names = new String [] {"Cover Art (other)", "Cover Art (icon)", "Cover Art (other icon)", "Cover Art (front)", "Cover Art (back)", "Cover Art (leaflet)", "Cover Art (media)", "Cover Art (lead)", "Cover Art (artist)", "Cover Art (conductor)", "Cover Art (band)", "Cover Art (composer)", "Cover Art (lyricist)", "Cover Art (studio)", "Cover Art (recording)", "Cover Art (performance)", "Cover Art (movie scene)", "Cover Art (colored fish)", "Cover Art (illustration)", "Cover Art (band logo)", "Cover Art (publisher logo)"};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the tag footer.
	*/
	private Footer footer = new Footer();

	/** 
		Contains the items in the tag.
	*/
	private ArrayList<Item> items = new ArrayList<Item> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Properties

	/** 
		Specifies the identifier used find an APEv2 tag in a
		file.
	 
	 <value>
		"<c>APETAGEX</c>"
	 </value>
	*/
	@Deprecated
	public static final ReadOnlyByteVector FileIdentifier = Footer.FileIdentifier;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Tag" /> with no contents.
	*/
	public Tag()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Tag" /> by reading the contents from a specified
		position in a specified file.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object containing the file
		from which the contents of the new instance is to be
		read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read the tag.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	*/
	public Tag(Rasad.Core.Media.MediaMetadataManagement.File file, long position)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (position < 0 || position > file.getLength() - Footer.Size)
		{
			throw new IndexOutOfBoundsException("position");
		}

		Read(file, position);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Tag" /> by reading the contents of a raw tag in a
		specified <see cref="ByteVector"/> object.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		tag.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null"/>.
	 
	 @exception CorruptFileException
		<paramref name="data" /> is too small to contain a tag,
		has a header where the footer should be, or is smaller
		than the tag it is supposed to contain.
	 
	*/
	public Tag(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (data.size() < Footer.Size)
		{
			throw new CorruptFileException("Does not contain enough footer data.");
		}

		footer = new Footer(data.Mid((int)(data.size() - Footer.Size)));

		if (footer.getTagSize() == 0)
		{
			throw new CorruptFileException("Tag size out of bounds.");
		}

		// If we've read a header at the end of the block, the
		// block is invalid.
		if ((footer.getFlags().getValue() & FooterFlags.IsHeader.getValue()) != 0)
		{
			throw new CorruptFileException("Footer was actually header.");
		}

		if (data.size() < footer.getTagSize())
		{
			throw new CorruptFileException("Does not contain enough tag data.");
		}

		Parse(data.Mid((int)(data.size() - footer.getTagSize()), (int)(footer.getTagSize() - Footer.Size)));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets and sets whether or not the current instance has a
		header when rendered.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		current instance has a header when rendered.
	 </value>
	*/
	public final boolean getHeaderPresent()
	{
		return (footer.getFlags().getValue() & FooterFlags.HeaderPresent.getValue()) != 0;
	}
	public final void setHeaderPresent(boolean value)
	{
		if (value)
		{
			footer.setFlags(Rasad.Core.Media.MediaMetadataManagement.Ape.FooterFlags.forValue(footer.getFlags().getValue() | FooterFlags.HeaderPresent.getValue()));
		}
		else
		{
			footer.setFlags(Rasad.Core.Media.MediaMetadataManagement.Ape.FooterFlags.forValue(footer.getFlags().getValue() & ~FooterFlags.HeaderPresent.getValue()));
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Adds a number to the value stored in a specified item.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to store the value in.
	 
	 @param number
		A <see cref="uint" /> value containing the number to
		store.
	 
	 @param count
		A <see cref="uint" /> value representing a total which
		<paramref name="number" /> is a part of, or zero if
		<paramref name="number" /> is not part of a set.
	 
	 
		If both <paramref name="number" /> and <paramref
		name="count" /> are equal to zero, the value will not be
		added. If <paramref name="count" /> is zero, <paramref
		name="number" /> by itself will be stored. Otherwise, the
		values will be stored as "<paramref name="number"
		/>/<paramref name="count" />".
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void AddValue(string key, uint number, uint count)
	public final void AddValue(String key, int number, int count)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (number == 0 && count == 0)
		{
			return;
		}
		else if (count != 0)
		{
			AddValue(key, String.format(CultureInfo.InvariantCulture, "%1$s/%2$s", number, count));
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: AddValue(key, number.ToString(CultureInfo.InvariantCulture));
			AddValue(key, (new Integer(number)).toString(CultureInfo.InvariantCulture));
		}
	}

	/** 
		Stores a number in a specified item.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to store the value in.
	 
	 @param number
		A <see cref="uint" /> value containing the number to
		store.
	 
	 @param count
		A <see cref="uint" /> value representing a total which
		<paramref name="number" /> is a part of, or zero if
		<paramref name="number" /> is not part of a set.
	 
	 
		If both <paramref name="number" /> and <paramref
		name="count" /> are equal to zero, the value will be
		cleared. If <paramref name="count" /> is zero, <paramref
		name="number" /> by itself will be stored. Otherwise, the
		values will be stored as "<paramref name="number"
		/>/<paramref name="count" />".
	 
	 @exception ArgumentNullException
		<paramref name="ident" /> is <see langword="null" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetValue(string key, uint number, uint count)
	public final void SetValue(String key, int number, int count)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (number == 0 && count == 0)
		{
			RemoveItem(key);
		}
		else if (count != 0)
		{
			SetValue(key, String.format(CultureInfo.InvariantCulture, "%1$s/%2$s", number, count));
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetValue(key, number.ToString(CultureInfo.InvariantCulture));
			SetValue(key, (new Integer(number)).toString(CultureInfo.InvariantCulture));
		}
	}

	/** 
		Adds the contents of a <see cref="string" /> to the value
		stored in a specified item.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to store the value in.
	 
	 @param value
		A <see cref="string" /> object containing the text to
		add.
	 
	 
		If <paramref name="value" /> is <see langword="null" />
		or empty, the value will not be added.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
	public final void AddValue(String key, String value)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (tangible.StringHelper.isNullOrEmpty(value))
		{
			return;
		}

		AddValue(key, new String [] {value});
	}

	/** 
		Stores the contents of a <see cref="string" /> in a
		specified item.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to store the value in.
	 
	 @param value
		A <see cref="string" /> object containing the text to
		store.
	 
	 
		If <paramref name="value" /> is <see langword="null" />
		or empty, the value will be cleared.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
	public final void SetValue(String key, String value)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (tangible.StringHelper.isNullOrEmpty(value))
		{
			RemoveItem(key);
		}
		else
		{
			SetValue(key, new String [] {value});
		}
	}

	/** 
		Adds the contents of a <see cref="string[]" /> to the
		value stored in a specified item.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to store the value in.
	 
	 @param value
		A <see cref="string[]" /> containing the text to add.
	 
	 
		If <paramref name="value" /> is <see langword="null" />
		or empty, the value will not be added.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
	public final void AddValue(String key, String[] value)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (value == null || value.length == 0)
		{
			return;
		}

		int index = GetItemIndex(key);

		ArrayList<String> values = new ArrayList<String> ();

		if (index >= 0)
		{
			values.addAll(Arrays.asList(items.get(index).ToStringArray()));
		}

		values.addAll(Arrays.asList(value));

		Item item = new Item(key, values.toArray(new String[0]));

		if (index >= 0)
		{
			items.set(index, item);
		}
		else
		{
			items.add(item);
		}
	}

	/** 
		Stores the contents of a <see cref="string[]" /> in a
		specified item.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to store the value in.
	 
	 @param value
		A <see cref="string[]" /> containing the text to store.
	 
	 
		If <paramref name="value" /> is <see langword="null" />
		or empty, the value will be cleared.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
	public final void SetValue(String key, String[] value)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		if (value == null || value.length == 0)
		{
			RemoveItem(key);
			return;
		}

		Item item = new Item(key, value);

		int index = GetItemIndex(key);
		if (index >= 0)
		{
			items.set(index, item);
		}
		else
		{
			items.add(item);
		}

	}

	/** 
		Gets a specified item from the current instance.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to get from the current instance.
	 
	 @return 
		The item with the matching name contained in the current
		instance, or <see langword="null" /> if a matching object
		was not found.
	 
	*/
	public final Item GetItem(String key)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		StringComparison comparison = StringComparison.InvariantCultureIgnoreCase;

		for (Item item : items)
		{
			if (item.getKey().equals(comparison))
			{
				return item;
			}
		}

		return null;
	}

	/** 
		Adds an item to the current instance, replacing the
		existing one of the same name.
	 
	 @param item
		A <see cref="Item" /> object to add to the current
		instance.
	 
	*/
	public final void SetItem(Item item)
	{
		if (item == null)
		{
			throw new NullPointerException("item");
		}

		int index = GetItemIndex(item.getKey());
		if (index >= 0)
		{
			items.set(index, item);
		}
		else
		{
			items.add(item);
		}
	}

	/** 
		Removes the item with a specified key from the current
		instance.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to remove from the current instance.
	 
	*/
	public final void RemoveItem(String key)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		StringComparison comparison = StringComparison.InvariantCultureIgnoreCase;

		for (int i = items.size() - 1; i >= 0; i--)
		{
			if (items.get(i).getKey().equals(comparison))
			{
				items.remove(i);
			}
		}
	}

	/** 
	 Checks if an item exists.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to check.
	 
	 @return 
		Returns <see langword="true"/> if the <paramref name="key"/>
		exists - else <see langword="false"/> is returned.
	 
	 @exception ArgumentNullException
		<paramref name="key" /> is <see langword="null" />.
	 
	*/
	public final boolean HasItem(String key)
	{
		if (key == null)
		{
			throw new NullPointerException("key");
		}

		return GetItemIndex(key) >= 0;
	}

	/** 
		Renders the current instance as a raw APEv2 tag.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered tag.
	 
	*/
	public final ByteVector Render()
	{
		ByteVector data = new ByteVector();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint item_count = 0;
		int item_count = 0;

		for (Item item : items)
		{
			data.add(item.Render());
			item_count++;
		}

		footer.setItemCount(item_count);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: footer.TagSize = (uint)(data.Count + Footer.Size);
		footer.setTagSize((int)(data.size() + Footer.Size));
		setHeaderPresent(true);

		data.add(0, footer.RenderHeader());
		data.add(footer.RenderFooter());
		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Populates the current instance be reading in a tag from
		a specified position in a specified file.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to read the tag from.
	 
	 @param position
		A <see cref="long" /> value specifying the seek position
		at which to read the tag.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than 0 or greater
		than the size of the file.
	 
	*/
	protected final void Read(Rasad.Core.Media.MediaMetadataManagement.File file, long position)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.setMode(File.AccessMode.Read);

		if (position < 0 || position > file.getLength() - Footer.Size)
		{
			throw new IndexOutOfBoundsException("position");
		}

		file.Seek(position);
		footer = new Footer(file.ReadBlock((int)Footer.Size));

		if (footer.getTagSize() == 0)
		{
			throw new CorruptFileException("Tag size out of bounds.");
		}

		// If we've read a header, we don't have to seek to read
		// the content. If we've read a footer, we need to move
		// back to the start of the tag.
		if ((footer.getFlags().getValue() & FooterFlags.IsHeader.getValue()) == 0)
		{
			file.Seek(position + Footer.Size - footer.getTagSize());
		}

		Parse(file.ReadBlock((int)(footer.getTagSize() - Footer.Size)));
	}

	/** 
		Populates the current instance by parsing the contents of
		a raw APEv2 tag, minus the header and footer.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the content
		of an APEv2 tag, minus the header and footer.
	 
	 
		This method must only be called after the internal
		footer has been read from the file, otherwise the data
		cannot be parsed correctly.
	 
	*/
	protected final void Parse(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		int pos = 0;

		try
		{
			// 11 bytes is the minimum size for an APE item
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: for (uint i = 0; i < footer.ItemCount && pos <= data.Count - 11; i++)
			for (int i = 0; i < footer.getItemCount() && pos <= data.size() - 11; i++)
			{
				Item item = new Item(data, pos);
				SetItem(item);
				pos += item.getSize();
			}
		}
		catch (CorruptFileException e)
		{
			// A corrupt item was encountered, considered
			// the tag finished with what has been read.
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Gets the index of an item in the current instance.
	 
	 @param key
		A <see cref="string" /> object containing the key to look
		for in the current instance.
	 
	 @return 
		A <see cref="int" /> value containing the index in <see
		cref="items" /> at which the item appears, or -1 if the
		item was not found.
	 
	 
		Keys are compared in a case insensitive manner.
	 
	*/
	private int GetItemIndex(String key)
	{
		StringComparison comparison = StringComparison.InvariantCultureIgnoreCase;

		for (int i = 0; i < items.size(); i++)
		{
			if (items.get(i).getKey().equals(comparison))
			{
				return i;
			}
		}

		return -1;
	}

	/** 
		Gets the text value from a specified item.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to get the value from.
	 
	 @return 
		A <see cref="string" /> object containing the text of the
		specified frame, or <see langword="null" /> if no value
		was found.
	 
	*/
	private String GetItemAsString(String key)
	{
			Item item = GetItem(key);
			return item != null ? item.toString() : null;
	}

	/** 
		Gets the text values from a specified item.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to get the value from.
	 
	 @return 
		A <see cref="string[]" /> containing the text of the
		specified frame, or an empty array if no values were
		found.
	 
	*/
	private String[] GetItemAsStrings(String key)
	{
		Item item = GetItem(key);
		return item != null ? item.ToStringArray() : new String [0];
	}

	/** 
		Gets an integer value from a "/" delimited list in a
		specified item.
	 
	 @param key
		A <see cref="string" /> object containing the key of the
		item to get the value from.
	 
	 @param index
		A <see cref="int" /> value specifying the index in the
		integer list of the value to return.
	 
	 @return 
		A <see cref="uint" /> value read from the list in the
		frame, or 0 if the value wasn't found.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint GetItemAsUInt32(string key, int index)
	private int GetItemAsUInt32(String key, int index)
	{
		String text = GetItemAsString(key);

		if (text == null)
		{
			return 0;
		}

		String [] values = text.split("[/]", index + 2);

		if (values.length < index + 1)
		{
			return 0;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint result;
		int result;
		tangible.OutObject<Integer> tempOut_result = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (uint.TryParse(values [index], out result))
		if (tangible.TryParseHelper.tryParseInt(values [index], tempOut_result))
		{
		result = tempOut_result.argValue;
			return result;
		}
	else
	{
		result = tempOut_result.argValue;
	}

		return 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IEnumerable

	/** 
		Gets the enumerator for the current instance.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerator`1" /> object enumerating through
		the item keys stored in the current instance.
	 
	*/
	public final Iterator<String> iterator()
	{
		for (Item item : items)
		{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
			yield return item.getKey();
		}
	}

	/** 
		Gets the enumerator for the current instance.
	 
	 @return 
		A <see cref="IEnumerator" /> object enumerating through
		the item keys stored in the current instance.
	 
	*/
	public final Iterator GetEnumerator()
	{
		return GetEnumerator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.Ape" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.Ape;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "Title" item.
	 
	*/
	@Override
	public String getTitle()
	{
		return GetItemAsString("Title");
	}
	@Override
	public void setTitle(String value)
	{
		SetValue("Title", value);
	}

	/** 
		Gets and sets the sort names of the Title of the
		media represented by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort names for
		the Title of the media described by the current instance,
		or null if no value is present. 
	 </value>
	 
		This property is implemented using the "TitleSort" item.
	 
	*/
	@Override
	public String getTitleSort()
	{
		return GetItemAsString("TitleSort");
	}
	@Override
	public void setTitleSort(String value)
	{
		SetValue("TitleSort", value);
	}

	/** 
		Gets and sets the performers or artists who performed in
		the media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the performers or
		artists who performed in the media described by the
		current instance or an empty array if no value is
		present.
	 </value>
	 
		This property is implemented using the "Artist" item.
	 
	*/
	@Override
	public String[] getPerformers()
	{
		return GetItemAsStrings("Artist");
	}
	@Override
	public void setPerformers(String[] value)
	{
		SetValue("Artist", value);
	}

	/** 
		Gets and sets the sort names of the performers or artists
		who performed in the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> array containing the sort names for
		the performers or artists who performed in the media
		described by the current instance, or an empty array if
		no value is present. 
	 </value>
	 
		This property is implemented using the "ArtistSort" field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String[] getPerformersSort()
	{
		return GetItemAsStrings("ArtistSort");
	}
	@Override
	public void setPerformersSort(String[] value)
	{
		SetValue("ArtistSort", value);
	}

	/** 
		Gets and sets the band or artist who is credited in the
		creation of the entire album or collection containing the
		media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the band or artist
		who is credited in the creation of the entire album or
		collection containing the media described by the current
		instance or an empty array if no value is present.
	 </value>
	 
		This property is implemented using the "Album Artist"
		item, and "AlbumArtist" as a backup property if it exists.
	 
	*/
	@Override
	public String[] getAlbumArtists()
	{
		String[] list = GetItemAsStrings("Album Artist");
		if (list.length == 0)
		{
			list = GetItemAsStrings("AlbumArtist");
		}
		return list;
	}
	@Override
	public void setAlbumArtists(String[] value)
	{
		SetValue("Album Artist", value);
			// compatibility
		if (HasItem("AlbumArtist"))
		{
			SetValue("AlbumArtist", value);
		}
	}

	/** 
		Gets and sets the sort names for the band or artist who
		is credited in the creation of the entire album or
		collection containing the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> array containing the sort names
		for the band or artist who is credited in the creation
		of the entire album or collection containing the media
		described by the current instance or an empty array if
		no value is present.
	 </value>
	 
		This property is implemented using the "AlbumArtistSort"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String[] getAlbumArtistsSort()
	{
		return GetItemAsStrings("AlbumArtistSort");
	}
	@Override
	public void setAlbumArtistsSort(String[] value)
	{
		SetValue("AlbumArtistSort", value);
	}

	/** 
		Gets and sets the composers of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the composers of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "Composer" item.
	 
	*/
	@Override
	public String[] getComposers()
	{
		return GetItemAsStrings("Composer");
	}
	@Override
	public void setComposers(String[] value)
	{
		SetValue("Composer", value);
	}

	/** 
		Gets and sets the sort names for the composers of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> array containing the sort names
		for the composer of the media described by the current
		instance or an empty array if no value is present.
	 </value>
	 
		This property is implemented using the "ComposerSort"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String[] getComposersSort()
	{
		return GetItemAsStrings("ComposerSort");
	}
	@Override
	public void setComposersSort(String[] value)
	{
		SetValue("ComposerSort", value);
	}

	/** 
		Gets and sets the album of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the album of
		the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "Album" item.
	 
	*/
	@Override
	public String getAlbum()
	{
		return GetItemAsString("Album");
	}
	@Override
	public void setAlbum(String value)
	{
		SetValue("Album", value);
	}

	/** 
		Gets and sets the sort names for the Album Title of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort name of 
		the Album Title of the media described by the current
		instance or null if no value is present.
	 </value>
	 
		This property is implemented using the "AlbumSort"
		field.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getAlbumSort()
	{
		return GetItemAsString("AlbumSort");
	}
	@Override
	public void setAlbumSort(String value)
	{
		SetValue("AlbumSort", value);
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "Comment" item.
	 
	*/
	@Override
	public String getComment()
	{
		return GetItemAsString("Comment");
	}
	@Override
	public void setComment(String value)
	{
		SetValue("Comment", value);
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "Genre" item.
	 
	*/
	@Override
	public String[] getGenres()
	{
		return GetItemAsStrings("Genre");
	}
	@Override
	public void setGenres(String[] value)
	{
		SetValue("Genre", value);
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		This property is implemented using the "Year" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
		String text = GetItemAsString("Year");

		if (text == null || text.length() == 0)
		{
			return 0;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;
		tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
		tangible.OutObject<Integer> tempOut_value2 = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (uint.TryParse(text, out value) || (text.Length >= 4 && uint.TryParse(text.Substring(0, 4), out value)))
		if (tangible.TryParseHelper.tryParseInt(text, tempOut_value) || (text.length() >= 4 && tangible.TryParseHelper.tryParseInt(text.substring(0, 4), tempOut_value2)))
		{
		value = tempOut_value2.argValue;
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value2.argValue;
		value = tempOut_value.argValue;
	}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setYear(uint value)
	@Override
	public void setYear(int value)
	{
		SetValue("Year", value, 0);
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	 
		This property is implemented using the "Track" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		return GetItemAsUInt32("Track", 0);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
		SetValue("Track", value, getTrackCount());
	}

	/** 
		Gets and sets the number of tracks in the album
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of tracks in
		the album containing the media represented by the current
		instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "Track" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		return GetItemAsUInt32("Track", 1);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrackCount(uint value)
	@Override
	public void setTrackCount(int value)
	{
		SetValue("Track", getTrack(), value);
	}

	/** 
		Gets and sets the number of the disc containing the media
		represented by the current instance in the boxed set.
	 
	 <value>
		A <see cref="uint" /> containing the number of the disc
		containing the media represented by the current instance
		in the boxed set.
	 </value>
	 
		This property is implemented using the "Disc" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDisc()
	@Override
	public int getDisc()
	{
		return GetItemAsUInt32("Disc", 0);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDisc(uint value)
	@Override
	public void setDisc(int value)
	{
		SetValue("Disc", value, getDiscCount());
	}

	/** 
		Gets and sets the number of discs in the boxed set
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of discs in
		the boxed set containing the media represented by the
		current instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "Disc" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDiscCount()
	@Override
	public int getDiscCount()
	{
		return GetItemAsUInt32("Disc", 1);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDiscCount(uint value)
	@Override
	public void setDiscCount(int value)
	{
		SetValue("Disc", getDisc(), value);
	}

	/** 
		Gets and sets the lyrics or script of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the lyrics or
		script of the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "Lyrics" item.
	 
	*/
	@Override
	public String getLyrics()
	{
		return GetItemAsString("Lyrics");
	}
	@Override
	public void setLyrics(String value)
	{
		SetValue("Lyrics", value);
	}

	/** 
		Gets and sets the grouping on the album which the media
		in the current instance belongs to.
	 
	 <value>
		A <see cref="string" /> object containing the grouping on
		the album which the media in the current instance belongs
		to or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "Grouping" item.
	 
	*/
	@Override
	public String getGrouping()
	{
		return GetItemAsString("Grouping");
	}
	@Override
	public void setGrouping(String value)
	{
		SetValue("Grouping", value);
	}

	/** 
		Gets and sets the number of beats per minute in the audio
		of the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of beats per
		minute in the audio of the media represented by the
		current instance, or zero if not specified.
	 </value>
	 
		This property is implemented using the "BPM" item.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getBeatsPerMinute()
	@Override
	public int getBeatsPerMinute()
	{
		String text = GetItemAsString("BPM");

		if (text == null)
		{
			return 0;
		}

		double value;

		tangible.OutObject<Double> tempOut_value = new tangible.OutObject<Double>();
		if (tangible.TryParseHelper.tryParseDouble(text, tempOut_value))
		{
		value = tempOut_value.argValue;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint) Math.Round(value);
			return (int) Math.rint(value);
		}
	else
	{
		value = tempOut_value.argValue;
	}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setBeatsPerMinute(uint value)
	@Override
	public void setBeatsPerMinute(int value)
	{
		SetValue("BPM", value, 0);
	}

	/** 
		Gets and sets the conductor or director of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the conductor
		or director of the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "Conductor" item.
	 
	*/
	@Override
	public String getConductor()
	{
		return GetItemAsString("Conductor");
	}
	@Override
	public void setConductor(String value)
	{
		SetValue("Conductor", value);
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "Copyright" item.
	 
	*/
	@Override
	public String getCopyright()
	{
		return GetItemAsString("Copyright");
	}
	@Override
	public void setCopyright(String value)
	{
		SetValue("Copyright", value);
	}

	/** 
		Gets and sets the MusicBrainz Artist ID of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ArtistID for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ARTISTID" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzArtistId()
	{
		return GetItemAsString("MUSICBRAINZ_ARTISTID");
	}
	@Override
	public void setMusicBrainzArtistId(String value)
	{
		SetValue("MUSICBRAINZ_ARTISTID", value);
	}

	/** 
		Gets and sets the MusicBrainz Release ID of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseID for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ALBUMID" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseId()
	{
		return GetItemAsString("MUSICBRAINZ_ALBUMID");
	}
	@Override
	public void setMusicBrainzReleaseId(String value)
	{
		SetValue("MUSICBRAINZ_ALBUMID", value);
	}

	/** 
		Gets and sets the MusicBrainz Release Artist ID of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseArtistID for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ALBUMARTISTID" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseArtistId()
	{
		return GetItemAsString("MUSICBRAINZ_ALBUMARTISTID");
	}
	@Override
	public void setMusicBrainzReleaseArtistId(String value)
	{
		SetValue("MUSICBRAINZ_ALBUMARTISTID", value);
	}

	/** 
		Gets and sets the MusicBrainz Track ID of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		TrackID for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_TRACKID" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzTrackId()
	{
		return GetItemAsString("MUSICBRAINZ_TRACKID");
	}
	@Override
	public void setMusicBrainzTrackId(String value)
	{
		SetValue("MUSICBRAINZ_TRACKID", value);
	}

	/** 
		Gets and sets the MusicBrainz Disc ID of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		DiscID for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_DISCID" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzDiscId()
	{
		return GetItemAsString("MUSICBRAINZ_DISCID");
	}
	@Override
	public void setMusicBrainzDiscId(String value)
	{
		SetValue("MUSICBRAINZ_DISCID", value);
	}

	/** 
		Gets and sets the MusicIP PUID of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicIPPUID
		for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "MUSICIP_PUID" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicIpId()
	{
		return GetItemAsString("MUSICIP_PUID");
	}
	@Override
	public void setMusicIpId(String value)
	{
		SetValue("MUSICIP_PUID", value);
	}

	/** 
		Gets and sets the Amazon ID of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the AmazonID
		for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "ASIN" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getAmazonId()
	{
		return GetItemAsString("ASIN");
	}
	@Override
	public void setAmazonId(String value)
	{
		SetValue("ASIN", value);
	}

	/** 
		Gets and sets the MusicBrainz Release Status of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseStatus for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ALBUMSTATUS" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseStatus()
	{
		return GetItemAsString("MUSICBRAINZ_ALBUMSTATUS");
	}
	@Override
	public void setMusicBrainzReleaseStatus(String value)
	{
		SetValue("MUSICBRAINZ_ALBUMSTATUS", value);
	}

	/** 
		Gets and sets the MusicBrainz Release Type of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseType for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "MUSICBRAINZ_ALBUMTYPE" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseType()
	{
		return GetItemAsString("MUSICBRAINZ_ALBUMTYPE");
	}
	@Override
	public void setMusicBrainzReleaseType(String value)
	{
		SetValue("MUSICBRAINZ_ALBUMTYPE", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseCountry of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the MusicBrainz
		ReleaseCountry for the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "RELEASECOUNTRY" item.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseCountry()
	{
		return GetItemAsString("RELEASECOUNTRY");
	}
	@Override
	public void setMusicBrainzReleaseCountry(String value)
	{
		SetValue("RELEASECOUNTRY", value);
	}

	/** 
		Gets and sets a collection of pictures associated with
		the media represented by the current instance.
	 
	 <value>
		A <see cref="IPicture[]" /> containing a collection of
		pictures associated with the media represented by the
		current instance or an empty array if none are present.
	 </value>
	 
		This property is implemented using the "Cover Art" items
		and supports only one picture per type.
	 
	*/
	@Override
	public IPicture[] getPictures()
	{
		ArrayList<IPicture> pictures = new ArrayList<IPicture> ();

		for (int i = 0; i < picture_item_names.length; i++)
		{
			Item item = GetItem(picture_item_names [i]);

			if (item == null || item.getType() != ItemType.Binary)
			{
				continue;
			}

			int index = tangible.ListHelper.find(item.getValue(), ByteVector.TextDelimiter(StringType.UTF8));

			if (index < 0)
			{
				continue;
			}

			Picture pic = new Picture(item.getValue().Mid(index + 1));

			pic.setDescription(item.getValue().toString(StringType.UTF8, 0, index));

			pic.setType(PictureType.forValue(i));

			pictures.add(pic);
		}

		return pictures.toArray(new IPicture[0]);
	}
	@Override
	public void setPictures(IPicture[] value)
	{
		for (String item_name : picture_item_names)
		{
			RemoveItem(item_name);
		}

		if (value == null || value.length == 0)
		{
			return;
		}

		for (IPicture pic : value)
		{
			int type =  pic.getType().getValue();

			if (type >= picture_item_names.length)
			{
				type = 0;
			}

			String name = picture_item_names [type];

			if (GetItem(name) != null)
			{
				continue;
			}

			ByteVector data = ByteVector.FromString(pic.getDescription(), StringType.UTF8);
			data.add(ByteVector.TextDelimiter(StringType.UTF8));
			data.add(pic.getData());

			SetItem(new Item(name, data));
		}
	}

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
		return items.isEmpty();
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		items.clear();
	}

	/** 
		Copies the values from the current instance to another
		<see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" />, optionally overwriting
		existing values.
	 
	 @param target
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> object containing the target
		tag to copy values to.
	 
	 @param overwrite
		A <see cref="bool" /> specifying whether or not to copy
		values over existing one.
	 
	 
		<p>If <paramref name="target" /> is of type <see
		cref="Rasad.Core.Media.MediaMetadataManagement.Ape.Tag" /> a complete copy of all values
		will be performed. Otherwise, only standard values will
		be copied.</p>
	 
	 @exception ArgumentNullException
		<paramref name="target" /> is <see langword="null" />.
	 
	*/
	@Override
	public void CopyTo(Rasad.Core.Media.MediaMetadataManagement.Tag target, boolean overwrite)
	{
		if (target == null)
		{
			throw new NullPointerException("target");
		}

		Rasad.Core.Media.MediaMetadataManagement.Ape.Tag match = target instanceof Rasad.Core.Media.MediaMetadataManagement.Ape.Tag ? (Rasad.Core.Media.MediaMetadataManagement.Ape.Tag)target : null;

		if (match == null)
		{
			super.CopyTo(target, overwrite);
			return;
		}

		for (Item item : items)
		{
			if (!overwrite && match.GetItem(item.getKey()) != null)
			{
				continue;
			}

			match.items.add(item.Clone());
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}