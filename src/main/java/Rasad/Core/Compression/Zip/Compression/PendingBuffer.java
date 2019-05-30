package Rasad.Core.Compression.Zip.Compression;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;

// PendingBuffer.cs
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
 This class is general purpose class for writing data to a buffer.
 
 It allows you to write bits as well as bytes
 Based on DeflaterPending.java
 
 author of the original java version : Jochen Hoenicke
*/
public class PendingBuffer
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 Internal work buffer
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer_;
	private byte[] buffer_;

	private int start;
	private int end;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint bits;
	private int bits;
	private int bitCount;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 construct instance using default buffer size of 4096
	*/
	public PendingBuffer()
	{
		this(4096);
	}

	/** 
	 construct instance using specified buffer size
	 
	 @param bufferSize
	 size to use for internal buffer
	 
	*/
	public PendingBuffer(int bufferSize)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer_ = new byte[bufferSize];
		buffer_ = new byte[bufferSize];
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Clear internal state/buffers
	*/
	public final void Reset()
	{
		start = end = bitCount = 0;
	}

	/** 
	 Write a byte to buffer
	 
	 @param value
	 The value to write
	 
	*/
	public final void WriteByte(int value)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
		if (DeflaterConstants.DEBUGGING && (start != 0))
		{
			throw new SharpZipBaseException("Debug check: start != 0");
		}
//#endif
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte) value);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		buffer_[end++] = (byte) value;
	}

	/** 
	 Write a short value to buffer LSB first
	 
	 @param value
	 The value to write.
	 
	*/
	public final void WriteShort(int value)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
		if (DeflaterConstants.DEBUGGING && (start != 0))
		{
			throw new SharpZipBaseException("Debug check: start != 0");
		}
//#endif
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte) value);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		buffer_[end++] = (byte) value;
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte)(value >> 8));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		buffer_[end++] = (byte)(value >> 8);
	}

	/** 
	 write an integer LSB first
	 
	 @param value The value to write.
	*/
	public final void WriteInt(int value)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
		if (DeflaterConstants.DEBUGGING && (start != 0))
		{
			throw new SharpZipBaseException("Debug check: start != 0");
		}
//#endif
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte) value);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		buffer_[end++] = (byte) value;
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte)(value >> 8));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		buffer_[end++] = (byte)(value >> 8);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte)(value >> 16));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		buffer_[end++] = (byte)(value >> 16);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte)(value >> 24));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		buffer_[end++] = (byte)(value >> 24);
	}

	/** 
	 Write a block of data to buffer
	 
	 @param block data to write
	 @param offset offset of first byte to write
	 @param length number of bytes to write
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void WriteBlock(byte[] block, int offset, int length)
	public final void WriteBlock(byte[] block, int offset, int length)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
		if (DeflaterConstants.DEBUGGING && (start != 0))
		{
			throw new SharpZipBaseException("Debug check: start != 0");
		}
//#endif
		System.arraycopy(block, offset, buffer_, end, length);
		end += length;
	}

	/** 
	 The number of bits written to the buffer
	*/
	public final int getBitCount()
	{
		return bitCount;
	}

	/** 
	 Align internal buffer on a byte boundary
	*/
	public final void AlignToByte()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
		if (DeflaterConstants.DEBUGGING && (start != 0))
		{
			throw new SharpZipBaseException("Debug check: start != 0");
		}
//#endif
		if (bitCount > 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte) bits);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
			buffer_[end++] = (byte) bits;
			if (bitCount > 8)
			{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte)(bits >> 8));
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
				buffer_[end++] = (byte)(bits >>> 8);
			}
		}
		bits = 0;
		bitCount = 0;
	}

	/** 
	 Write bits to internal buffer
	 
	 @param b source of bits
	 @param count number of bits to write
	*/
	public final void WriteBits(int b, int count)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
		if (DeflaterConstants.DEBUGGING && (start != 0))
		{
			throw new SharpZipBaseException("Debug check: start != 0");
		}

		//			if (DeflaterConstants.DEBUGGING) {
		//				//Console.WriteLine("writeBits("+b+","+count+")");
		//			}
//#endif
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: bits |= (uint)(b << bitCount);
		bits |= (int)(b << bitCount);
		bitCount += count;
		if (bitCount >= 16)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte) bits);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
			buffer_[end++] = (byte) bits;
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte)(bits >> 8));
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
			buffer_[end++] = (byte)(bits >>> 8);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			bits >>>= 16;
			bitCount -= 16;
		}
	}

	/** 
	 Write a short value to internal buffer most significant byte first
	 
	 @param s value to write
	*/
	public final void WriteShortMSB(int s)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
		if (DeflaterConstants.DEBUGGING && (start != 0))
		{
			throw new SharpZipBaseException("Debug check: start != 0");
		}
//#endif
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte)(s >> 8));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		buffer_[end++] = (byte)(s >> 8);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte) s);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		buffer_[end++] = (byte) s;
	}

	/** 
	 Indicates if buffer has been flushed
	*/
	public final boolean getIsFlushed()
	{
		return end == 0;
	}

	/** 
	 Flushes the pending buffer into the given output array.  If the
	 output array is to small, only a partial flush is done.
	 
	 @param output The output array.
	 @param offset The offset into output array.
	 @param length The maximum number of bytes to store.
	 @return The number of bytes flushed.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int Flush(byte[] output, int offset, int length)
	public final int Flush(byte[] output, int offset, int length)
	{
		if (bitCount >= 8)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: buffer_[end++] = unchecked((byte) bits);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
			buffer_[end++] = (byte) bits;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			bits >>>= 8;
			bitCount -= 8;
		}

		if (length > end - start)
		{
			length = end - start;
			System.arraycopy(buffer_, start, output, offset, length);
			start = 0;
			end = 0;
		}
		else
		{
			System.arraycopy(buffer_, start, output, offset, length);
			start += length;
		}
		return length;
	}

	/** 
	 Convert internal buffer to byte array.
	 Buffer is empty on completion
	 
	 @return 
	 The internal buffer contents converted to a byte array.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] ToByteArray()
	public final byte[] ToByteArray()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] result = new byte[end - start];
		byte[] result = new byte[end - start];
		System.arraycopy(buffer_, start, result, 0, result.length);
		start = 0;
		end = 0;
		return result;
	}
}