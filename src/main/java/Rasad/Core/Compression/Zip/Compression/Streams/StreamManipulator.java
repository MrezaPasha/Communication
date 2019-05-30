package Rasad.Core.Compression.Zip.Compression.Streams;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;
import Rasad.Core.Compression.Zip.Compression.*;

// StreamManipulator.cs
//
// Copyright (C) 2001 Mike Krueger
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
 This class allows us to retrieve a specified number of bits from
 the input buffer, as well as copy big byte blocks.

 It uses an int buffer to store up to 31 bits for direct
 manipulation.  This guarantees that we can get at least 16 bits,
 but we only need at most 15, so this is all safe.

 There are some optimizations in this class, for example, you must
 never peek more than 8 bits more than needed, and you must first
 peek bits before you may drop them.  This is not a general purpose
 class but optimized for the behaviour of the Inflater.

 authors of the original java version : John Leuner, Jochen Hoenicke
*/
public class StreamManipulator
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Constructs a default StreamManipulator with all buffers empty
	*/
	public StreamManipulator()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get the next sequence of bits but don't increase input pointer.  bitCount must be
	 less or equal 16 and if this call succeeds, you must drop
	 at least n - 8 bits in the next call.
	 
	 @param bitCount The number of bits to peek.
	 @return 
	 the value of the bits, or -1 if not enough bits available.  */
	 
	*/
	public final int PeekBits(int bitCount)
	{
		if (bitsInBuffer_ < bitCount)
		{
			if (windowStart_ == windowEnd_)
			{
				return -1; // ok
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer_ |= (uint)((window_[windowStart_++] & 0xff | (window_[windowStart_++] & 0xff) << 8) << bitsInBuffer_);
			buffer_ |= (int)((window_[windowStart_++] & 0xff | (window_[windowStart_++] & 0xff) << 8) << bitsInBuffer_);
			bitsInBuffer_ += 16;
		}
		return (int)(buffer_ & ((1 << bitCount) - 1));
	}

	/** 
	 Drops the next n bits from the input.  You should have called PeekBits
	 with a bigger or equal n before, to make sure that enough bits are in
	 the bit buffer.
	 
	 @param bitCount The number of bits to drop.
	*/
	public final void DropBits(int bitCount)
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		buffer_ >>>= bitCount;
		bitsInBuffer_ -= bitCount;
	}

	/** 
	 Gets the next n bits and increases input pointer.  This is equivalent
	 to <see cref="PeekBits"/> followed by <see cref="DropBits"/>, except for correct error handling.
	 
	 @param bitCount The number of bits to retrieve.
	 @return 
	 the value of the bits, or -1 if not enough bits available.
	 
	*/
	public final int GetBits(int bitCount)
	{
		int bits = PeekBits(bitCount);
		if (bits >= 0)
		{
			DropBits(bitCount);
		}
		return bits;
	}

	/** 
	 Gets the number of bits available in the bit buffer.  This must be
	 only called when a previous PeekBits() returned -1.
	 
	 @return 
	 the number of bits available.
	 
	*/
	public final int getAvailableBits()
	{
		return bitsInBuffer_;
	}

	/** 
	 Gets the number of bytes available.
	 
	 @return 
	 The number of bytes available.
	 
	*/
	public final int getAvailableBytes()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		return windowEnd_ - windowStart_ + (bitsInBuffer_ >> 3);
	}

	/** 
	 Skips to the next byte boundary.
	*/
	public final void SkipToByteBoundary()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		buffer_ >>>= (bitsInBuffer_ & 7);
		bitsInBuffer_ &= ~7;
	}

	/** 
	 Returns true when SetInput can be called
	*/
	public final boolean getIsNeedingInput()
	{
		return windowStart_ == windowEnd_;
	}

	/** 
	 Copies bytes from input buffer to output buffer starting
	 at output[offset].  You have to make sure, that the buffer is
	 byte aligned.  If not enough bytes are available, copies fewer
	 bytes.
	 
	 @param output
	 The buffer to copy bytes to.
	 
	 @param offset
	 The offset in the buffer at which copying starts
	 
	 @param length
	 The length to copy, 0 is allowed.
	 
	 @return 
	 The number of bytes copied, 0 if no bytes were available.
	 
	 @exception ArgumentOutOfRangeException
	 Length is less than zero
	 
	 @exception InvalidOperationException
	 Bit buffer isnt byte aligned
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int CopyBytes(byte[] output, int offset, int length)
	public final int CopyBytes(byte[] output, int offset, int length)
	{
		if (length < 0)
		{
			throw new IndexOutOfBoundsException("length");
		}

		if ((bitsInBuffer_ & 7) != 0)
		{
			// bits_in_buffer may only be 0 or a multiple of 8
			throw new IllegalStateException("Bit buffer is not byte aligned!");
		}

		int count = 0;
		while ((bitsInBuffer_ > 0) && (length > 0))
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output[offset++] = (byte) buffer_;
			output[offset++] = (byte) buffer_;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			buffer_ >>>= 8;
			bitsInBuffer_ -= 8;
			length--;
			count++;
		}

		if (length == 0)
		{
			return count;
		}

		int avail = windowEnd_ - windowStart_;
		if (length > avail)
		{
			length = avail;
		}
		System.arraycopy(window_, windowStart_, output, offset, length);
		windowStart_ += length;

		if (((windowStart_ - windowEnd_) & 1) != 0)
		{
			// We always want an even number of bytes in input, see peekBits
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer_ = (uint)(window_[windowStart_++] & 0xff);
			buffer_ = (int)(window_[windowStart_++] & 0xff);
			bitsInBuffer_ = 8;
		}
		return count + length;
	}

	/** 
	 Resets state and empties internal buffers
	*/
	public final void Reset()
	{
		buffer_ = 0;
		windowStart_ = windowEnd_ = bitsInBuffer_ = 0;
	}

	/** 
	 Add more input for consumption.
	 Only call when IsNeedingInput returns true
	 
	 @param buffer data to be input
	 @param offset offset of first byte of input
	 @param count number of bytes of input to add.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetInput(byte[] buffer, int offset, int count)
	public final void SetInput(byte[] buffer, int offset, int count)
	{
		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		if (offset < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("offset");
//#else
			throw new IndexOutOfBoundsException("offset", "Cannot be negative");
//#endif
		}

		if (count < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("count");
//#else
			throw new IndexOutOfBoundsException("count", "Cannot be negative");
//#endif
		}

		if (windowStart_ < windowEnd_)
		{
			throw new IllegalStateException("Old input was not completely processed");
		}

		int end = offset + count;

		// We want to throw an ArrayIndexOutOfBoundsException early.
		// Note the check also handles integer wrap around.
		if ((offset > end) || (end > buffer.length))
		{
			throw new IndexOutOfBoundsException("count");
		}

		if ((count & 1) != 0)
		{
			// We always want an even number of bytes in input, see PeekBits
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer_ |= (uint)((buffer[offset++] & 0xff) << bitsInBuffer_);
			buffer_ |= (int)((buffer[offset++] & 0xff) << bitsInBuffer_);
			bitsInBuffer_ += 8;
		}

		window_ = buffer;
		windowStart_ = offset;
		windowEnd_ = end;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] window_;
	private byte[] window_;
	private int windowStart_;
	private int windowEnd_;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint buffer_;
	private int buffer_;
	private int bitsInBuffer_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}