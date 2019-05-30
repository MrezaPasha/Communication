package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// SynchData.cs: Provides support for encoding and decoding unsynchronized data
// and numbers.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   id3v2synchdata.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
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
	This static class provides support for encoding and decoding
	unsynchronized data and numbers.
 
 
	Unsynchronization is designed so that portions of the tag won't
	be misinterpreted as MPEG audio stream headers by removing the
	possibility of the synch bytes occuring in the tag.
 
*/
public final class SynchData
{
	/** 
		Decodes synchronized integer data into a <see
		cref="uint" /> value.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the number
		to decode. Only the first 4 bytes of this value will be
		used.
	 
	 @return 
		A <see cref="uint" /> value containing the decoded
		number.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static uint ToUInt(ByteVector data)
	public static int ToUInt(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint sum = 0;
		int sum = 0;
		int last = data.size() > 4 ? 3 : data.size() - 1;

		for (int i = 0; i <= last; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: sum |= (uint)(data [i] & 0x7f) << ((last - i) * 7);
			sum |= (int)(data.get(i) & 0x7f) << ((last - i) * 7);
		}

		return sum;
	}

	/** 
		Encodes a <see cref="uint" /> value as synchronized
		integer data.
	 
	 @param value
		A <see cref="uint" /> value containing the number to
		encode.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the encoded
		number.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="value" /> is greater than 268435455.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector FromUInt(uint value)
	public static ByteVector FromUInt(int value)
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		if ((value >>> 28) != 0)
		{
			throw new IndexOutOfBoundsException("value", "value must be less than 268435456.");
		}

		ByteVector v = new ByteVector(4, 0);

		for (int i = 0; i < 4; i++)
		{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v [i] = (byte)(value >> ((3 - i) * 7) & 0x7f);
			v.set(i, (byte)(value >>> ((3 - i) * 7) & 0x7f));
		}

		return v;
	}

	/** 
		Unsynchronizes a <see cref="ByteVector" /> object by
		inserting empty bytes where necessary.
	 
	 @param data
		A <see cref="ByteVector" /> object to unsynchronize.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
	public static void UnsynchByteVector(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		for (int i = data.size() - 2; i >= 0; i--)
		{
			if (data.get(i) == 0xFF && (data.get(i + 1) == 0 || (data.get(i + 1) & 0xE0) != 0))
			{
				data.add(i + 1, 0);
			}
		}
	}

	/** 
		Resynchronizes a <see cref="ByteVector" /> object by
		removing the added bytes.
	 
	 @param data
		A <see cref="ByteVector" /> object to resynchronize.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
	public static void ResynchByteVector(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		int i = 0, j = 0;
		while (i < data.size() - 1)
		{
			if (i != j)
			{
				data.set(j, data.get(i));
			}
			i += data.get(i) == 0xFF && data.get(i + 1) == 0 ? 2 : 1;
			j++;
		}
		if (i < data.size())
		{
			data.set(j++, data.get(i++));
		}
		data.Resize(j);
	}
}