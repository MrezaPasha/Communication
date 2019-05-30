package Rasad.Core.Media.MediaMetadataManagement.Aac;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// BitStream.cs: Helper to read bits from a byte array.
//
// Copyright (C) 2009 Patrick Dehne
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
	This class is used to help reading arbitary number of bits from
	a fixed array of bytes
*/
public class BitStream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	private BitSet bits;
	private int bitindex;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Construct a new <see cref="BitStream"/>.
	 
	 @param buffer
		A <see cref="System.Byte[]"/>, must be 7 bytes long.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public BitStream(byte[] buffer)
	public BitStream(byte[] buffer)
	{
		Debug.Assert(buffer.length == 7, "buffer.Length == 7", "buffer size invalid");

		if (buffer.length != 7)
		{
			throw new IllegalArgumentException("Buffer size must be 7 bytes");
		}

		// Reverse bits            
		bits = new BitSet(buffer.length * 8);
		for (int i = 0; i < buffer.length; i++)
		{
			for (int y = 0; y < 8; y++)
			{
				bits.set(i * 8 + y, ((buffer[i] & (1 << (7 - y))) > 0));
			}
		}

		bitindex = 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Reads an Int32 from the bitstream        
	 
	 @param numberOfBits
		A <see cref="int" /> value containing the number
		of bits to read from the bitstream
	 
	*/
	public final int ReadInt32(int numberOfBits)
	{
		Debug.Assert(numberOfBits > 0, "numberOfBits < 1");
		Debug.Assert(numberOfBits <= 32, "numberOfBits <= 32");

		if (numberOfBits <= 0)
		{
			throw new IllegalArgumentException("Number of bits to read must be >= 1");
		}

		if (numberOfBits > 32)
		{
			throw new IllegalArgumentException("Number of bits to read must be <= 32");
		}

		int value = 0;
		int start = bitindex + numberOfBits - 1;
		for (int i = 0; i < numberOfBits; i++)
		{
			value += bits.get(start) ? (1 << i) : 0;
			bitindex++;
			start--;
		}

		return value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}