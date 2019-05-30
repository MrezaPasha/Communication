package Rasad.Core.Compression.Tar;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

// TarOutputStream.cs
//
// Copyright (C) 2001 Mike Krueger
// Copyright 2005 John Reilly
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
//	2012-06-04	Z-1419	Last char of file name was dropped if path length > 100




/** 
 The TarOutputStream writes a UNIX tar archive as an OutputStream.
 Methods are provided to put entries, and then write their contents
 by writing to this stream using write().
 
 public
*/
public class TarOutputStream extends Stream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Construct TarOutputStream using default block factor
	 
	 @param outputStream stream to write to
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public TarOutputStream(Stream outputStream)
	{
		this(outputStream, TarBuffer.DefaultBlockFactor);
	}

	/** 
	 Construct TarOutputStream with user specified block factor
	 
	 @param outputStream stream to write to
	 @param blockFactor blocking factor
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public TarOutputStream(Stream outputStream, int blockFactor)
	{
		if (outputStream == null)
		{
			throw new NullPointerException("outputStream");
		}

		this.outputStream = outputStream;
		buffer = TarBuffer.CreateOutputTarBuffer(outputStream, blockFactor);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: assemblyBuffer = new byte[TarBuffer.BlockSize];
		assemblyBuffer = new byte[TarBuffer.BlockSize];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: blockBuffer = new byte[TarBuffer.BlockSize];
		blockBuffer = new byte[TarBuffer.BlockSize];
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get/set flag indicating ownership of the underlying stream.
	 When the flag is true <see cref="Close"></see> will close the underlying stream also.
	*/
	public final boolean getIsStreamOwner()
	{
		return buffer.getIsStreamOwner();
	}
	public final void setIsStreamOwner(boolean value)
	{
		buffer.setIsStreamOwner(value);
	}

	/** 
	 true if the stream supports reading; otherwise, false.
	*/
	@Override
	public boolean getCanRead()
	{
		return outputStream.CanRead;
	}

	/** 
	 true if the stream supports seeking; otherwise, false.
	*/
	@Override
	public boolean getCanSeek()
	{
		return outputStream.CanSeek;
	}

	/** 
	 true if stream supports writing; otherwise, false.
	*/
	@Override
	public boolean getCanWrite()
	{
		return outputStream.CanWrite;
	}

	/** 
	 length of stream in bytes
	*/
	@Override
	public long getLength()
	{
		return outputStream.Length;
	}

	/** 
	 gets or sets the position within the current stream.
	*/
	@Override
	public long getPosition()
	{
		return outputStream.Position;
	}
	@Override
	public void setPosition(long value)
	{
		outputStream.Position = value;
	}

	/** 
	 set the position within the current stream
	 
	 @param offset The offset relative to the <paramref name="origin"/> to seek to
	 @param origin The <see cref="SeekOrigin"/> to seek from.
	 @return The new position in the stream.
	*/
	@Override
	public long Seek(long offset, SeekOrigin origin)
	{
		return outputStream.Seek(offset, origin);
	}

	/** 
	 Set the length of the current stream
	 
	 @param value The new stream length.
	*/
	@Override
	public void SetLength(long value)
	{
		outputStream.SetLength(value);
	}

	/** 
	 Read a byte from the stream and advance the position within the stream 
	 by one byte or returns -1 if at the end of the stream.
	 
	 @return The byte value or -1 if at end of stream
	*/
	@Override
	public int ReadByte()
	{
		return outputStream.ReadByte();
	}

	/** 
	 read bytes from the current stream and advance the position within the 
	 stream by the number of bytes read.
	 
	 @param buffer The buffer to store read bytes in.
	 @param offset The index into the buffer to being storing bytes at.
	 @param count The desired number of bytes to read.
	 @return The total number of bytes read, or zero if at the end of the stream.
	 The number of bytes may be less than the <paramref name="count">count</paramref>
	 requested if data is not avialable.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
	@Override
	public int Read(byte[] buffer, int offset, int count)
	{
		return outputStream.Read(buffer, offset, count);
	}

	/** 
	 All buffered data is written to destination
	*/
	@Override
	public void Flush()
	{
		outputStream.Flush();
	}

	/** 
	 Ends the TAR archive without closing the underlying OutputStream.
	 The result is that the EOF block of nulls is written.
	*/
	public final void Finish()
	{
		if (getIsEntryOpen())
		{
			CloseEntry();
		}
		WriteEofBlock();
	}

	/** 
	 Ends the TAR archive and closes the underlying OutputStream.
	 
	 This means that Finish() is called followed by calling the
	 TarBuffer's Close().
	*/
	@Override
	public void Close()
	{
		if (!isClosed)
		{
			isClosed = true;
			Finish();
			buffer.Close();
		}
	}

	/** 
	 Get the record size being used by this stream's TarBuffer.
	*/
	public final int getRecordSize()
	{
		return buffer.getRecordSize();
	}

	/** 
	 Get the record size being used by this stream's TarBuffer.
	 
	 @return 
	 The TarBuffer record size.
	 
	*/
	@Deprecated
	public final int GetRecordSize()
	{
		return buffer.getRecordSize();
	}

	/** 
	 Get a value indicating wether an entry is open, requiring more data to be written.
	*/
	private boolean getIsEntryOpen()
	{
		return (currBytes < currSize);
	}


	/** 
	 Put an entry on the output stream. This writes the entry's
	 header and positions the output stream for writing
	 the contents of the entry. Once this method is called, the
	 stream is ready for calls to write() to write the entry's
	 contents. Once the contents are written, closeEntry()
	 <B>MUST</B> be called to ensure that all buffered data
	 is completely written to the output stream.
	 
	 @param entry
	 The TarEntry to be written to the archive.
	 
	*/
	public final void PutNextEntry(TarEntry entry)
	{
		if (entry == null)
		{
			throw new NullPointerException("entry");
		}

		if (entry.getTarHeader().getName().length() >= TarHeader.NAMELEN)
		{
			TarHeader longHeader = new TarHeader();
			longHeader.setTypeFlag(TarHeader.LF_GNU_LONGNAME);
			longHeader.setName(longHeader.getName() + "././@LongLink");
			longHeader.setUserId(0);
			longHeader.setGroupId(0);
			longHeader.setGroupName("");
			longHeader.setUserName("");
			longHeader.setLinkName("");
			longHeader.setSize(entry.getTarHeader().getName().length() + 1); // Plus one to avoid dropping last char

			longHeader.WriteHeader(blockBuffer);
			buffer.WriteBlock(blockBuffer); // Add special long filename header block

			int nameCharIndex = 0;

			while (nameCharIndex < entry.getTarHeader().getName().length())
			{
				Array.Clear(blockBuffer, 0, blockBuffer.length);
				TarHeader.GetAsciiBytes(entry.getTarHeader().getName(), nameCharIndex, this.blockBuffer, 0, TarBuffer.BlockSize);
				nameCharIndex += TarBuffer.BlockSize;
				buffer.WriteBlock(blockBuffer);
			}
		}

		entry.WriteEntryHeader(blockBuffer);
		buffer.WriteBlock(blockBuffer);

		currBytes = 0;

		currSize = entry.getIsDirectory() ? 0 : entry.getSize();
	}

	/** 
	 Close an entry. This method MUST be called for all file
	 entries that contain data. The reason is that we must
	 buffer data written to the stream in order to satisfy
	 the buffer's block based writes. Thus, there may be
	 data fragments still being assembled that must be written
	 to the output stream before this entry is closed and the
	 next entry written.
	*/
	public final void CloseEntry()
	{
		if (assemblyBufferLength > 0)
		{
			Array.Clear(assemblyBuffer, assemblyBufferLength, assemblyBuffer.length - assemblyBufferLength);

			buffer.WriteBlock(assemblyBuffer);

			currBytes += assemblyBufferLength;
			assemblyBufferLength = 0;
		}

		if (currBytes < currSize)
		{
			String errorText = String.format("Entry closed at '%1$s' before the '%2$s' bytes specified in the header were written", currBytes, currSize);
			throw new TarException(errorText);
		}
	}

	/** 
	 Writes a byte to the current tar archive entry.
	 This method simply calls Write(byte[], int, int).
	 
	 @param value
	 The byte to be written.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void WriteByte(byte value)
	@Override
	public void WriteByte(byte value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Write(new byte[] { value }, 0, 1);
		Write(new byte[] {value}, 0, 1);
	}

	/** 
	 Writes bytes to the current tar archive entry. This method
	 is aware of the current entry and will throw an exception if
	 you attempt to write bytes past the length specified for the
	 current entry. The method is also (painfully) aware of the
	 record buffering required by TarBuffer, and manages buffers
	 that are not a multiple of recordsize in length, including
	 assembling records from small buffers.
	 
	 @param  buffer
	 The buffer to write to the archive.
	 
	 @param  offset
	 The offset in the buffer from which to get bytes.
	 
	 @param  count
	 The number of bytes to write.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
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

		if (buffer.length - offset < count)
		{
			throw new IllegalArgumentException("offset and count combination is invalid");
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

		if ((currBytes + count) > currSize)
		{
			String errorText = String.format("request to write '%1$s' bytes exceeds size in header of '%2$s' bytes", count, this.currSize);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("count");
//#else
			throw new IndexOutOfBoundsException("count", errorText);
//#endif
		}

		//
		// We have to deal with assembly!!!
		// The programmer can be writing little 32 byte chunks for all
		// we know, and we must assemble complete blocks for writing.
		// TODO  REVIEW Maybe this should be in TarBuffer? Could that help to
		//        eliminate some of the buffer copying.
		//
		if (assemblyBufferLength > 0)
		{
			if ((assemblyBufferLength + count) >= blockBuffer.length)
			{
				int aLen = blockBuffer.length - assemblyBufferLength;

				System.arraycopy(assemblyBuffer, 0, blockBuffer, 0, assemblyBufferLength);
				System.arraycopy(buffer, offset, blockBuffer, assemblyBufferLength, aLen);

				this.buffer.WriteBlock(blockBuffer);

				currBytes += blockBuffer.length;

				offset += aLen;
				count -= aLen;

				assemblyBufferLength = 0;
			}
			else
			{
				System.arraycopy(buffer, offset, assemblyBuffer, assemblyBufferLength, count);
				offset += count;
				assemblyBufferLength += count;
				count -= count;
			}
		}

		//
		// When we get here we have EITHER:
		//   o An empty "assembly" buffer.
		//   o No bytes to write (count == 0)
		//
		while (count > 0)
		{
			if (count < blockBuffer.length)
			{
				System.arraycopy(buffer, offset, assemblyBuffer, assemblyBufferLength, count);
				assemblyBufferLength += count;
				break;
			}

			this.buffer.WriteBlock(buffer, offset);

			int bufferLength = blockBuffer.length;
			currBytes += bufferLength;
			count -= bufferLength;
			offset += bufferLength;
		}
	}

	/** 
	 Write an EOF (end of archive) block to the tar archive.
	 An EOF block consists of all zeros.
	*/
	private void WriteEofBlock()
	{
		Array.Clear(blockBuffer, 0, blockBuffer.length);
		buffer.WriteBlock(blockBuffer);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 bytes written for this entry so far
	*/
	private long currBytes;

	/** 
	 current 'Assembly' buffer length
	*/
	private int assemblyBufferLength;

	/** 
	 Flag indicating wether this instance has been closed or not.
	*/
	private boolean isClosed;

	/** 
	 Size for the current entry
	*/
	protected long currSize;

	/** 
	 single block working buffer 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected byte[] blockBuffer;
	protected byte[] blockBuffer;

	/** 
	 'Assembly' buffer used to assemble data before writing
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected byte[] assemblyBuffer;
	protected byte[] assemblyBuffer;

	/** 
	 TarBuffer used to provide correct blocking factor
	*/
	protected TarBuffer buffer;

	/** 
	 the destination stream for the archive contents
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	protected Stream outputStream;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
/* The original Java file had this header:
	** Authored by Timothy Gerard Endres
	** <mailto:time@gjt.org>  <http: //www.trustice.com>
	**
	** This work has been placed into the public domain.
	** You may use this work in any way and for any purpose you wish.
	**
	** THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
	** NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
	** OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
	** CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
	** REDISTRIBUTION OF THIS SOFTWARE.
	**
	*/
