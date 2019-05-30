package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;

//
// ListBase.cs:
//
// Author:
//   Aaron Bockover (abockover@novell.com)
//
// Original Source:
//   tbytevectorlist.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2006 Novell, Inc.
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
	This class implements <see cref="T:System.Collections.Generic`1"/>
	for objects that implement <see cref="T:System.IComparable`1"/>,
	providing extra features used in lists in Rasad.Core.Media.MediaMetadataManagement#.
*/
public class ListBase<T extends java.lang.Comparable<T>> implements List<T>
{
	/** 
		Contains the internal list.
	*/
	private ArrayList<T> data = new ArrayList<T> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="T:Rasad.Core.Media.MediaMetadataManagement.ListBase`1" /> with no contents.
	*/
	public ListBase()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="T:Rasad.Core.Media.MediaMetadataManagement.ListBase`1" /> with specified contents.
	 
	 @param list
	   A <see cref="T:System.Collections.Generic.IEnumerable`1"
	   /> containing objects to add to the current instance.
	 
	*/
	public ListBase(ListBase<T> list)
	{
		if (list != null)
		{
			Add(list);
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="T:Rasad.Core.Media.MediaMetadataManagement.ListBase`1" /> with specified contents.
	 
	 @param list
	   A <see cref="System.Array" /> containing objects to add to
	   the current instance.
	 
	*/
	public ListBase(T... list)
	{
		if (list != null)
		{
			Add(list);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if the current instance is empty;
		otherwise <see langword="false" />.
	 </value>
	*/
	public final boolean getIsEmpty()
	{
		return getCount() == 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods

	/** 
		Adds a collection of elements to the current instance.
	 
	 @param list
		A <see cref="T:Rasad.Core.Media.MediaMetadataManagement.ListBase`1"/> object containing
		elements to add to the current instance.
	 
	*/
	public final void Add(ListBase<T> list)
	{
		if (list != null)
		{
			data.addAll(list);
		}
	}

	/** 
		Adds a collection of elements to the current instance.
	 
	 @param list
		A <see cref="T:System.Collections.Generic.IEnumerable`1"/> object containing
		elements to add to the current instance.
	 
	*/
	public final void Add(java.lang.Iterable<T> list)
	{
		if (list != null)
		{
			data.addAll(list);
		}
	}

	/** 
		Adds a collection of elements to the current instance.
	 
	 @param list
		An array containing elements to add to the current
		instance.
	 
	*/
	public final void Add(T[] list)
	{
		if (list != null)
		{
			data.addAll(Arrays.asList(list));
		}
	}

	/** 
		Performs a sorted insert of an object into the current
		instance, optionally only adding if the item is unique.
	 
	 @param item
		An object to add to the current instance.
	 
	 @param unique
		If <see langword="true" />, the object will only be added
		if an identical value is not already contained in the
		current instance.
	 
	 @exception ArgumentNullException
		<paramref name="item" /> is <see langword="null" />.
	 
	*/
	public void SortedInsert(T item, boolean unique)
	{
		if (item == null)
		{
			throw new NullPointerException("item");
		}

		int i = 0;
		for (; i < data.size(); i++)
		{
			if (item.CompareTo(data.get(i)) == 0 && unique)
			{
				return;
			}

			if (item.CompareTo(data.get(i)) <= 0)
			{
				break;
			}
		}

		Insert(i, item);
	}

	/** 
		Performs a sorted insert of an object into the current
		instance.
	 
	 @param item
		An object to add to the current instance.
	 
	 @exception ArgumentNullException
		<paramref name="item" /> is <see langword="null" />.
	 
	*/
	public final void SortedInsert(T item)
	{
		if (item == null)
		{
			throw new NullPointerException("item");
		}

		SortedInsert(item, false);
	}

	/** 
		Converts the current instance to an array.
	 
	 @return 
		A <see cref="System.Array" /> containing the contents of
		the current instance.
	 
	*/
	public final T[] ToArray()
	{
//C# TO JAVA CONVERTER WARNING: Java does not allow direct instantiation of arrays of generic type parameters:
//ORIGINAL LINE: return data.ToArray();
		return data.toArray((T[])new Object[0]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IList<T>

	/** 
		Gets whether or not the current instance is read-only.
	 
	 <value>
		Always <see langword="false" />.
	 </value>
	*/
	public final boolean getIsReadOnly()
	{
		return false;
	}

	/** 
		Gets whether or not the current instance has a fixed
		size.
	 
	 <value>
		Always <see langword="false" />.
	 </value>
	*/
	public final boolean getIsFixedSize()
	{
		return false;
	}

	/** 
		Gets and sets the value as a specified index.
	*/
	public final T get(int index)
	{
		return data.get(index);
	}
	public final void set(int index, T value)
	{
		data.set(index, value);
	}

	/** 
		Adds a single item to end of the current instance.
	 
	 @param item
		An object to add to the end of the current instance.
	 
	*/
	public final void Add(T item)
	{
		data.add(item);
	}

	/** 
		Clears the contents of the current instance.
	*/
	public final void clear()
	{
		data.clear();
	}

	/** 
		Gets whether or not the current instance contains a
		specified object.
	 
	 @param item
		An object to look for in the current instance.
	 
	 @return 
		<see langword="true" /> if the item could be found;
		otherwise <see langword="false" />.
	 
	*/
	public final boolean contains(Object objectValue)
	{
		T item = (T)objectValue;
		return data.contains(item);
	}

	/** 
		Gets the index of the first occurance of a value.
	 
	 @param item
		A object to find in the current instance.
	 
	 @return 
		A <see cref="int" /> value containing the first index
		at which the value was found, or -1 if it was not found.
	 
	*/
	public final int indexOf(Object objectValue)
	{
		T item = (T)objectValue;
		return data.indexOf(item);
	}

	/** 
		Inserts a single value into the current instance at a
	*/
	//     specified index.
	/** 
	 @param index
		A <see cref="int" /> value specifying the position at
		which to insert the value.
	 
	 @param item
		An object to insert into the current instance.
	 
	*/
	public final void add(int index, T item)
	{
		data.add(index, item);
	}

	/** 
		Removes the first occurance of an object from the current
		instance.
	 
	 @param item
		An object to remove from the current instance.
	 
	 @return 
		<see langword="true" /> if the value was removed;
		otherwise the value did not appear in the current
		instance and <see langword="false" /> is returned.
	 
	*/
	public final boolean remove(Object objectValue)
	{
		T item = (T)objectValue;
		return data.remove(item);
	}

	/** 
		Removes the item at the specified index.
	 
	 @param index
		A <see cref="int" /> value specifying the position at
		which to remove an item.
	 
	*/
	public final void remove(int index)
	{
		data.remove(index);
	}

	/** 
		Gets a string representation of the contents of the
		current instance, joined by a separator.
	 
	 @param separator
		A <see cref="string" /> object to separate the items
		with.
	 
	 @return 
		A <see cref="string" /> object containing the contents
		of the current instance.
	 
	*/
	public final String toString(String separator)
	{
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < getCount(); i++)
		{
			if (i != 0)
			{
				builder.append(separator);
			}

			builder.append(this.get(i).toString());
		}

		return builder.toString();
	}

	/** 
		Gets a string representation of the contents of the
		current instance, joined by commas.
	 
	 @return 
		A <see cref="string" /> object containing the contents
		of the current instance.
	 
	*/
	@Override
	public String toString()
	{
		return toString(", ");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region ICollection<T>

	/** 
		Gets the number of elements in the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the number of
		elements in the current instance.
	 </value>
	*/
	public final int size()
	{
		return data.size();
	}

	/** 
		Gets whether or not the current instance is synchronized.
	 
	 <value>
		Always <see langword="false" />.
	 </value>
	*/
	public final boolean getIsSynchronized()
	{
		return false;
	}

	/** 
		Gets the object that can be used to synchronize the
		current instance.
	 
	 <value>
		A <see cref="object" /> that can be used to synchronize
		the current instance.
	 </value>
	*/
	public final Object getSyncRoot()
	{
		return this;
	}

	/** 
		Copies the current instance to an array, starting at a
		specified index.
	 
	 @param array
		An array to copy to.
	 
	 @param arrayIndex
		A <see cref="int" /> value indicating the index in
		<paramref name="array" /> at which to start copying.
	 
	*/
	public final void CopyTo(T[] array, int arrayIndex)
	{
		data.CopyTo(array, arrayIndex);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion




//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IEnumerable<T>

	/** 
		Gets an enumerator for enumerating through the elements
		in the current instance.
	 
	 @return 
		A <see cref="T:System.Collections.IEnumerator`1" /> for
		enumerating through the tag's data boxes.
	 
	*/
	public final Iterator<T> iterator()
	{
		return data.iterator();
	}

	public final Iterator GetEnumerator()
	{
		return data.iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}