package Rasad.Core.Compression.Zip.Compression.Streams;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;
import Rasad.Core.Compression.Zip.Compression.*;

// OutputWindow.cs
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
 Contains the output from the Inflation process.
 We need to have a window so that we can refer backwards into the output stream
 to repeat stuff.<br/>
 Author of the original java version : John Leuner
*/
public class OutputWindow
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constants
	private static final int WindowSize = 1 << 15;
	private static final int WindowMask = WindowSize - 1;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] window = new byte[WindowSize];
	private byte[] window = new byte[WindowSize]; //The window is 2^15 bytes
	private int windowEnd;
	private int windowFilled;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Write a byte to this output window
	 
	 @param value value to write
	 @exception InvalidOperationException
	 if window is full
	 
	*/
	public final void Write(int value)
	{
		if (windowFilled++ == WindowSize)
		{
			throw new IllegalStateException("Window full");
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: window[windowEnd++] = (byte) value;
		window[windowEnd++] = (byte) value;
		windowEnd &= WindowMask;
	}


	private void SlowRepeat(int repStart, int length, int distance)
	{
		while (length-- > 0)
		{
			window[windowEnd++] = window[repStart++];
			windowEnd &= WindowMask;
			repStart &= WindowMask;
		}
	}

	/** 
	 Append a byte pattern already in the window itself
	 
	 @param length length of pattern to copy
	 @param distance distance from end of window pattern occurs
	 @exception InvalidOperationException
	 If the repeated data overflows the window
	 
	*/
	public final void Repeat(int length, int distance)
	{
		if ((windowFilled += length) > WindowSize)
		{
			throw new IllegalStateException("Window full");
		}

		int repStart = (windowEnd - distance) & WindowMask;
		int border = WindowSize - length;
		if ((repStart <= border) && (windowEnd < border))
		{
			if (length <= distance)
			{
				System.arraycopy(window, repStart, window, windowEnd, length);
				windowEnd += length;
			}
			else
			{
				// We have to copy manually, since the repeat pattern overlaps.
				while (length-- > 0)
				{
					window[windowEnd++] = window[repStart++];
				}
			}
		}
		else
		{
			SlowRepeat(repStart, length, distance);
		}
	}

	/** 
	 Copy from input manipulator to internal window
	 
	 @param input source of data
	 @param length length of data to copy
	 @return the number of bytes copied
	*/
	public final int CopyStored(StreamManipulator input, int length)
	{
		length = Math.min(Math.min(length, WindowSize - windowFilled), input.getAvailableBytes());
		int copied;

		int tailLen = WindowSize - windowEnd;
		if (length > tailLen)
		{
			copied = input.CopyBytes(window, windowEnd, tailLen);
			if (copied == tailLen)
			{
				copied += input.CopyBytes(window, 0, length - tailLen);
			}
		}
		else
		{
			copied = input.CopyBytes(window, windowEnd, length);
		}

		windowEnd = (windowEnd + copied) & WindowMask;
		windowFilled += copied;
		return copied;
	}

	/** 
	 Copy dictionary to window
	 
	 @param dictionary source dictionary
	 @param offset offset of start in source dictionary
	 @param length length of dictionary
	 @exception InvalidOperationException
	 If window isnt empty
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void CopyDict(byte[] dictionary, int offset, int length)
	public final void CopyDict(byte[] dictionary, int offset, int length)
	{
		if (dictionary == null)
		{
			throw new NullPointerException("dictionary");
		}

		if (windowFilled > 0)
		{
			throw new IllegalStateException();
		}

		if (length > WindowSize)
		{
			offset += length - WindowSize;
			length = WindowSize;
		}
		System.arraycopy(dictionary, offset, window, 0, length);
		windowEnd = length & WindowMask;
	}

	/** 
	 Get remaining unfilled space in window
	 
	 @return Number of bytes left in window
	*/
	public final int GetFreeSpace()
	{
		return WindowSize - windowFilled;
	}

	/** 
	 Get bytes available for output in window
	 
	 @return Number of bytes filled
	*/
	public final int GetAvailable()
	{
		return windowFilled;
	}

	/** 
	 Copy contents of window to output
	 
	 @param output buffer to copy to
	 @param offset offset to start at
	 @param len number of bytes to count
	 @return The number of bytes copied
	 @exception InvalidOperationException
	 If a window underflow occurs
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int CopyOutput(byte[] output, int offset, int len)
	public final int CopyOutput(byte[] output, int offset, int len)
	{
		int copyEnd = windowEnd;
		if (len > windowFilled)
		{
			len = windowFilled;
		}
		else
		{
			copyEnd = (windowEnd - windowFilled + len) & WindowMask;
		}

		int copied = len;
		int tailLen = len - copyEnd;

		if (tailLen > 0)
		{
			System.arraycopy(window, WindowSize - tailLen, output, offset, tailLen);
			offset += tailLen;
			len = copyEnd;
		}
		System.arraycopy(window, copyEnd - len, output, offset, len);
		windowFilled -= copied;
		if (windowFilled < 0)
		{
			throw new IllegalStateException();
		}
		return copied;
	}

	/** 
	 Reset by clearing window so <see cref="GetAvailable">GetAvailable</see> returns 0
	*/
	public final void Reset()
	{
		windowFilled = windowEnd = 0;
	}
}