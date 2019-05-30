package Rasad.Core.Compression.Zip.Compression.Streams;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;
import Rasad.Core.Compression.Zip.Compression.*;

// InflaterInputStream.cs
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

// HISTORY
//	11-08-2009	GeoffHart	T9121	Added Multi-member gzip support


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
//#endif



/** 
 An input buffer customised for use by <see cref="InflaterInputStream"/>
 
 
 The buffer supports decryption of incoming data.
 
*/
public class InflaterInputBuffer
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a new instance of <see cref="InflaterInputBuffer"/> with a default buffer size
	 
	 @param stream The stream to buffer.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public InflaterInputBuffer(Stream stream)
	{
		this(stream, 4096);
	}

	/** 
	 Initialise a new instance of <see cref="InflaterInputBuffer"/>
	 
	 @param stream The stream to buffer.
	 @param bufferSize The size to use for the buffer
	 A minimum buffer size of 1KB is permitted.  Lower sizes are treated as 1KB.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public InflaterInputBuffer(Stream stream, int bufferSize)
	{
		inputStream = stream;
		if (bufferSize < 1024)
		{
			bufferSize = 1024;
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: rawData = new byte[bufferSize];
		rawData = new byte[bufferSize];
		clearText = rawData;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get the length of bytes bytes in the <see cref="RawData"/>
	*/
	public final int getRawLength()
	{
		return rawLength;
	}

	/** 
	 Get the contents of the raw data buffer.
	 
	 This may contain encrypted data.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getRawData()
	public final byte[] getRawData()
	{
		return rawData;
	}

	/** 
	 Get the number of useable bytes in <see cref="ClearText"/>
	*/
	public final int getClearTextLength()
	{
		return clearTextLength;
	}

	/** 
	 Get the contents of the clear text buffer.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getClearText()
	public final byte[] getClearText()
	{
		return clearText;
	}

	/** 
	 Get/set the number of bytes available
	*/
	public final int getAvailable()
	{
		return available;
	}
	public final void setAvailable(int value)
	{
		available = value;
	}

	/** 
	 Call <see cref="Inflater.SetInput(byte[], int, int)"/> passing the current clear text buffer contents.
	 
	 @param inflater The inflater to set input for.
	*/
	public final void SetInflaterInput(Inflater inflater)
	{
		if (available > 0)
		{
			inflater.SetInput(clearText, clearTextLength - available, available);
			available = 0;
		}
	}

	/** 
	 Fill the buffer from the underlying input stream.
	*/
	public final void Fill()
	{
		rawLength = 0;
		int toRead = rawData.length;

		while (toRead > 0)
		{
			int count = inputStream.Read(rawData, rawLength, toRead);
			if (count <= 0)
			{
				break;
			}
			rawLength += count;
			toRead -= count;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
		if (cryptoTransform != null)
		{
			clearTextLength = cryptoTransform.TransformBlock(rawData, 0, rawLength, clearText, 0);
		}
		else
//#endif
		{
			clearTextLength = rawLength;
		}

		available = clearTextLength;
	}

	/** 
	 Read a buffer directly from the input stream
	 
	 @param buffer The buffer to fill
	 @return Returns the number of bytes read.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int ReadRawBuffer(byte[] buffer)
	public final int ReadRawBuffer(byte[] buffer)
	{
		return ReadRawBuffer(buffer, 0, buffer.length);
	}

	/** 
	 Read a buffer directly from the input stream
	 
	 @param outBuffer The buffer to read into
	 @param offset The offset to start reading data into.
	 @param length The number of bytes to read.
	 @return Returns the number of bytes read.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int ReadRawBuffer(byte[] outBuffer, int offset, int length)
	public final int ReadRawBuffer(byte[] outBuffer, int offset, int length)
	{
		if (length < 0)
		{
			throw new IndexOutOfBoundsException("length");
		}

		int currentOffset = offset;
		int currentLength = length;

		while (currentLength > 0)
		{
			if (available <= 0)
			{
				Fill();
				if (available <= 0)
				{
					return 0;
				}
			}
			int toCopy = Math.min(currentLength, available);
			System.arraycopy(rawData, rawLength - (int)available, outBuffer, currentOffset, toCopy);
			currentOffset += toCopy;
			currentLength -= toCopy;
			available -= toCopy;
		}
		return length;
	}

	/** 
	 Read clear text data from the input stream.
	 
	 @param outBuffer The buffer to add data to.
	 @param offset The offset to start adding data at.
	 @param length The number of bytes to read.
	 @return Returns the number of bytes actually read.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int ReadClearTextBuffer(byte[] outBuffer, int offset, int length)
	public final int ReadClearTextBuffer(byte[] outBuffer, int offset, int length)
	{
		if (length < 0)
		{
			throw new IndexOutOfBoundsException("length");
		}

		int currentOffset = offset;
		int currentLength = length;

		while (currentLength > 0)
		{
			if (available <= 0)
			{
				Fill();
				if (available <= 0)
				{
					return 0;
				}
			}

			int toCopy = Math.min(currentLength, available);
			System.arraycopy(clearText, clearTextLength - (int)available, outBuffer, currentOffset, toCopy);
			currentOffset += toCopy;
			currentLength -= toCopy;
			available -= toCopy;
		}
		return length;
	}

	/** 
	 Read a <see cref="byte"/> from the input stream.
	 
	 @return Returns the byte read.
	*/
	public final int ReadLeByte()
	{
		if (available <= 0)
		{
			Fill();
			if (available <= 0)
			{
				throw new ZipException("EOF in header");
			}
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte result = rawData[rawLength - available];
		byte result = rawData[rawLength - available];
		available -= 1;
		return result;
	}

	/** 
	 Read an <see cref="short"/> in little endian byte order.
	 
	 @return The short value read case to an int.
	*/
	public final int ReadLeShort()
	{
		return ReadLeByte() | (ReadLeByte() << 8);
	}

	/** 
	 Read an <see cref="int"/> in little endian byte order.
	 
	 @return The int value read.
	*/
	public final int ReadLeInt()
	{
		return ReadLeShort() | (ReadLeShort() << 16);
	}

	/** 
	 Read a <see cref="long"/> in little endian byte order.
	 
	 @return The long value read.
	*/
	public final long ReadLeLong()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint)ReadLeInt() | ((long)ReadLeInt() << 32);
		return (int)ReadLeInt() | ((long)ReadLeInt() << 32);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
	/** 
	 Get/set the <see cref="ICryptoTransform"/> to apply to any data.
	 
	 Set this value to null to have no transform applied.
	*/
	public final void setCryptoTransform(ICryptoTransform value)
	{
		cryptoTransform = value;
		if (cryptoTransform != null)
		{
			if (rawData == clearText)
			{
				if (internalClearText == null)
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internalClearText = new byte[rawData.Length];
					internalClearText = new byte[rawData.length];
				}
				clearText = internalClearText;
			}
			clearTextLength = rawLength;
			if (available > 0)
			{
				cryptoTransform.TransformBlock(rawData, rawLength - available, available, clearText, rawLength - available);
			}
		}
		else
		{
			clearText = rawData;
			clearTextLength = rawLength;
		}
	}
//#endif

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private int rawLength;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] rawData;
	private byte[] rawData;

	private int clearTextLength;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] clearText;
	private byte[] clearText;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] internalClearText;
	private byte[] internalClearText;
//#endif

	private int available;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
	private ICryptoTransform cryptoTransform;
//#endif
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream inputStream;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}