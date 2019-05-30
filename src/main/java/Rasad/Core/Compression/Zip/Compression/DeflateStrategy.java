package Rasad.Core.Compression.Zip.Compression;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;

// DeflaterEngine.cs
//
// Copyright (C) 2001 Mike Krueger
// Copyright (C) 2004 John Reilly
//
// This file was translated from java, it was part of the GNU Classpath
// Copyright (C) 2001 Free Software Foundation, Inc.
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// Linking this library statically or dynamically with other modules is
// making a combined work based on this library.  Thus, the terms and
// conditions of the GNU General Public License cover the whole
// combination.
// 
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent
// modules, and to copy and distribute the resulting executable under
// terms of your choice, provided that you also meet, for each linked
// independent module, the terms and conditions of the license of that
// module.  An independent module is a module which is not derived from
// or based on this library.  If you modify this library, you may extend
// this exception to your version of the library, but you are not
// obligated to do so.  If you do not wish to do so, delete this
// exception statement from your version.





/** 
 Strategies for deflater
*/
public enum DeflateStrategy
{
	/** 
	 The default strategy
	*/
	Default(0),

	/** 
	 This strategy will only allow longer string repetitions.  It is
	 useful for random data with a small character set.
	*/
	Filtered(1),


	/** 
	 This strategy will not look for string repetitions at all.  It
	 only encodes with Huffman trees (which means, that more common
	 characters get a smaller encoding.
	*/
	HuffmanOnly(2);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, DeflateStrategy> mappings;
	private static java.util.HashMap<Integer, DeflateStrategy> getMappings()
	{
		if (mappings == null)
		{
			synchronized (DeflateStrategy.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, DeflateStrategy>();
				}
			}
		}
		return mappings;
	}

	private DeflateStrategy(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static DeflateStrategy forValue(int value)
	{
		return getMappings().get(value);
	}
}