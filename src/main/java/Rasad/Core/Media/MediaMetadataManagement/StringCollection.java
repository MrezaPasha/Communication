package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

//
// StringList.cs: This class extends ListBase<string> for a collection
// of string objects.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//   Aaron Bockover (abockover@novell.com)
//
// Copyright (C) 2006 Novell, Inc.
// Copyright (C) 2005-2007 Brian Nickel
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
	This class extends <see cref="T:Rasad.Core.Media.MediaMetadataManagement.ListBase`1" /> for a collection of
	<see cref="string" /> objects.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ComVisible(false)] public class StringCollection : ListBase<string>
public class StringCollection extends ListBase<String>
{
	/** 
		Constructs and initializes a new instance of <see
		cref="StringCollection" /> with no contents.
	*/
	public StringCollection()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="StringCollection" /> with the contents of another
		instance.
	 
	 @param values
		A <see cref="StringCollection" /> object whose values are
		to be added to the new instance.
	 
	*/
	public StringCollection(StringCollection values)
	{
		Add(values);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="StringCollection" /> with the contents of a
		specified array.
	 
	 @param values
		A <see cref="string[]" /> whose values are to be added to
		the new instance.
	 
	*/
	public StringCollection(String... values)
	{
		Add(values);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="StringCollection" /> by converting a collection of
		<see cref="ByteVector" /> objects to strings with a
		specified encoding.
	 
	 @param vectorList
		A <see cref="ByteVectorCollection" /> object containing
		values to convert and add to the new instance.
	 
	 @param type
		A <see cref="StringType" /> specifying what encoding to
		use when converting the data to strings.
	 
	*/
	public StringCollection(ByteVectorCollection vectorList, StringType type)
	{
		for (ByteVector vector : vectorList)
		{
			Add(vector.toString(type));
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="StringCollection" /> by converting a collection of
		<see cref="ByteVector" /> objects to strings using the
		UTF-8 encoding.
	 
	 @param vectorList
		A <see cref="ByteVectorCollection" /> object containing
		values to convert and add to the new instance.
	 
	*/
	public StringCollection(ByteVectorCollection vectorList)
	{
		this(vectorList, StringType.UTF8);
	}

	/** 
		Splits a single <see cref="string" /> into a <see
		cref="StringCollection" /> using a pattern.
	 
	 @param value
		A <see cref="string" /> object to split.
	 
	 @param pattern
		A <see cref="string" /> object containing a pattern to
		use to split <paramref name="value" />.
	 
	 @return 
		A <see cref="StringCollection" /> object containing the
		split values.
	 
	 @exception ArgumentNullException
		<paramref name="value" /> or <paramref name="pattern" />
		is <see langword="null" />.
	 
	*/
	public static StringCollection Split(String value, String pattern)
	{
		if (value == null)
		{
			throw new NullPointerException("value");
		}

		if (pattern == null)
		{
			throw new NullPointerException("pattern");
		}

		StringCollection list = new StringCollection();

		int previous_position = 0;
		int position = value.indexOf(pattern, 0);
		int pattern_length = pattern.length();

		while (position != -1)
		{
			list.add(value.substring(previous_position, position));
			previous_position = position + pattern_length;
			position = value.indexOf(pattern, previous_position);
		}

		list.add(value.substring(previous_position));

		return list;
	}
}