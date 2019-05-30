package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

//
// ByteVectorList.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//   Aaron Bockover (abockover@novell.com)
//
// Original Source:
//   tbytevectorlist.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
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
	This class extends <see cref="T:Rasad.Core.Media.MediaMetadataManagement.ListBase`1"/> to represent
	a collection of <see cref="ByteVector" /> objects.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ComVisible(false)] public class ByteVectorCollection : ListBase<ByteVector>
public class ByteVectorCollection extends ListBase<ByteVector>
{
	/** 
		Constructs and initializes a new instance of <see
		cref="ByteVectorCollection" /> with no contents.
	*/
	public ByteVectorCollection()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ByteVectorCollection" /> with specified contents.
	 
	 @param list
	   A <see cref="T:System.Collections.Generic.IEnumerable`1"
	   /> containing <see cref="ByteVector" /> objects to add to
	   the current instance.
	 
	*/
	public ByteVectorCollection(java.lang.Iterable<ByteVector> list)
	{
		if (list != null)
		{
			Add(list);
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ByteVectorCollection" /> with specified contents.
	 
	 @param list
	   A <see cref="ByteVector[]" /> containing objects to add to
	   the current instance.
	 
	*/
	public ByteVectorCollection(ByteVector... list)
	{
		if (list != null)
		{
			Add(list);
		}
	}

	/** 
		Performs a sorted insert of a <see cref="ByteVector" />
		object into the current instance, optionally only adding
		if the item is unique.
	 
	 @param item
		A <see cref="ByteVector" /> object to add to the current
		instance.
	 
	 @param unique
		If <see langword="true" />, the object will only be added
		if an identical value is not already contained in the
		current instance.
	 
	 @exception ArgumentNullException
		<paramref name="item" /> is <see langword="null" />.
	 
	*/
	@Override
	public void SortedInsert(ByteVector item, boolean unique)
	{
		if (item == null)
		{
			throw new NullPointerException("item");
		}

		// FIXME: This is not used, but if it is a faster
		// method could be used.
		int i = 0;
		for (; i < getCount(); i++)
		{
			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(item, this.get(i)) && unique)
			{
				return;
			}

			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpGreaterThanOrEqual(item, this.get(i)))
			{
				break;
			}
		}

		Insert(i + 1, item);
	}

	/** 
		Converts the current instance to a <see cref="ByteVector"
		/> by joining the contents together with a specified
		separator.
	 
	 @param separator
		A <see cref="ByteVector"/> object to separate the
		combined contents of the current instance.
	 
	 @return 
		A new <see cref="ByteVector"/> object containing the
		joined contents of the current instance.
	 
	 @exception ArgumentNullException
		<paramref name="separator" /> is <see langword="null" />.
	 
	*/
	public final ByteVector ToByteVector(ByteVector separator)
	{
		if (separator == null)
		{
			throw new NullPointerException("separator");
		}

		ByteVector vector = new ByteVector();

		for (int i = 0; i < getCount(); i++)
		{
			if (i != 0 && separator.size() > 0)
			{
				vector.add(separator);
			}

			vector.add(this.get(i));
		}

		return vector;
	}

	/** 
		Splits a <see cref="ByteVector" /> object using a
		pattern.
	 
	 @param vector
		A <see cref="ByteVector"/> object to split.
	 
	 @param pattern
		A <see cref="ByteVector"/> object to use to split
		<paramref name="vector" /> with.
	 
	 @param byteAlign
		A <see cref="int" /> specifying the byte align to use
		when splitting. In order to split when a pattern is
		encountered, the index at which it is found must be
	*/
	//     divisible by <paramref name="byteAlign" />.
	/** 
	 @param max
		A <see cref="int" /> value specifying the maximum number
		of objects to return, or zero to not to limit the number.
		If that that number is reached, the last value will
		contain the remainder of the file even if it contains
		more instances of <paramref name="pattern" />.
	 
	 @return 
		A <see cref="ByteVectorCollection" /> object containing
		the split contents of the current instance.
	 
	 @exception ArgumentNullException
		<paramref name="vector" /> or <paramref name="pattern" />
		is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="byteAlign" /> is less than 1.
	 
	*/
	public static ByteVectorCollection Split(ByteVector vector, ByteVector pattern, int byteAlign, int max)
	{
		if (vector == null)
		{
			throw new NullPointerException("vector");
		}

		if (pattern == null)
		{
			throw new NullPointerException("pattern");
		}

		if (byteAlign < 1)
		{
			throw new IndexOutOfBoundsException("byteAlign", "byteAlign must be at least 1.");
		}

		ByteVectorCollection list = new ByteVectorCollection();
		int previous_offset = 0;

		for (int offset = vector.Find(pattern, 0, byteAlign); offset != -1 && (max < 1 || max > list.size() + 1); offset = vector.Find(pattern, offset + pattern.size(), byteAlign))
		{
			list.add(vector.Mid(previous_offset, offset - previous_offset));
			previous_offset = offset + pattern.size();
		}

		if (previous_offset < vector.size())
		{
			list.add(vector.Mid(previous_offset, vector.size() - previous_offset));
		}

		return list;
	}

	/** 
		Splits a <see cref="ByteVector" /> object using a
		pattern.
	 
	 @param vector
		A <see cref="ByteVector"/> object to split.
	 
	 @param pattern
		A <see cref="ByteVector"/> object to use to split
		<paramref name="vector" /> with.
	 
	 @param byteAlign
		A <see cref="int" /> specifying the byte align to use
		when splitting. In order to split when a pattern is
		encountered, the index at which it is found must be
	*/
	//     divisible by <paramref name="byteAlign" />.
	/** 
	 @return 
		A <see cref="ByteVectorCollection" /> object containing
		the split contents of the current instance.
	 
	 @exception ArgumentNullException
		<paramref name="vector" /> or <paramref name="pattern" />
		is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="byteAlign" /> is less than 1.
	 
	*/
	public static ByteVectorCollection Split(ByteVector vector, ByteVector pattern, int byteAlign)
	{
		return Split(vector, pattern, byteAlign, 0);
	}

	/** 
		Splits a <see cref="ByteVector" /> object using a
		pattern.
	 
	 @param vector
		A <see cref="ByteVector"/> object to split.
	 
	 @param pattern
		A <see cref="ByteVector"/> object to use to split
		<paramref name="vector" /> with.
	 
	 @return 
		A <see cref="ByteVectorCollection" /> object containing
		the split contents of the current instance.
	 
	 @exception ArgumentNullException
		<paramref name="vector" /> or <paramref name="pattern" />
		is <see langword="null" />.
	 
	*/
	public static ByteVectorCollection Split(ByteVector vector, ByteVector pattern)
	{
		return Split(vector, pattern, 1);
	}
}