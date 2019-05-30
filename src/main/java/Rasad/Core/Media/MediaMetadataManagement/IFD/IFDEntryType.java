package Rasad.Core.Media.MediaMetadataManagement.IFD;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// IFDEntryType.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009 Ruben Vermeersch
// Copyright (C) 2009 Mike Gemuende
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
	A type indicator, which identifies how the corresponding value
	field should be interpreted.
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum IFDEntryType : ushort
public enum IFDEntryType 
{
	/** 
		Unknown (shouldn't occur)
	*/
	Unknown((short)0),

	/** 
		8-bit unsigned integer.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Byte = 1,
	Byte((short)1),

	/** 
		8-bit byte that contains a 7-bit ASCII code; the last byte
		must be NUL (binary zero).
	*/
	Ascii((short)2),

	/** 
		16-bit (2-byte) unsigned integer.
	*/
	Short((short)3),

	/** 
		32-bit (4-byte) unsigned integer.
	*/
	Long((short)4),

	/** 
		Two LONGs: the first represents the numerator of a
		fraction; the second, the denominator.
	*/
	Rational((short)5),

	/** 
		An 8-bit signed (twos-complement) integer.
	*/
	Byte((short)6),

	/** 
		An 8-bit byte that may contain anything, depending on
		the definition of the field.
	*/
	Undefined((short)7),

	/** 
		A 16-bit (2-byte) signed (twos-complement) integer.
	*/
	SShort((short)8),

	/** 
		A 32-bit (4-byte) signed (twos-complement) integer.
	*/
	SLong((short)9),

	/** 
		Two SLONG’s: the first represents the numerator of a
		fraction, the second the denominator.
	*/
	SRational((short)10),

	/** 
		Single precision (4-byte) IEEE format.
	*/
	Float((short)11),

	/** 
		Double precision (8-byte) IEEE format.
	*/
	Double((short)12),

	/** 
		IFD
	*/
	IFD((short)13);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, IFDEntryType> mappings;
	private static java.util.HashMap<Short, IFDEntryType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (IFDEntryType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, IFDEntryType>();
				}
			}
		}
		return mappings;
	}

	private IFDEntryType(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static IFDEntryType forValue(short value)
	{
		return getMappings().get(value);
	}
}