package Rasad.Core.Compression.Tar;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;

// TarInputStream.cs
//
// Copyright (C) 2001 Mike Krueger
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
 The TarInputStream reads a UNIX tar archive as an InputStream.
 methods are provided to position at each successive entry in
 the archive, and the read each entry as a normal input stream
 using read().
*/
public class TarInputStream extends Stream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Construct a TarInputStream with default block factor
	 
	 @param inputStream stream to source data from
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public TarInputStream(Stream inputStream)
	{
		this(inputStream, TarBuffer.DefaultBlockFactor);
	}

	/** 
	 Construct a TarInputStream with user specified block factor
	 
	 @param inputStream stream to source data from
	 @param blockFactor block factor to apply to archive
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public TarInputStream(Stream inputStream, int blockFactor)
	{
		this.inputStream = inputStream;
		tarBuffer = TarBuffer.CreateInputTarBuffer(inputStream, blockFactor);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get/set flag indicating ownership of the underlying stream.
	 When the flag is true <see cref="Close"></see> will close the underlying stream also.
	*/
	public final boolean getIsStreamOwner()
	{
		return tarBuffer.getIsStreamOwner();
	}
	public final void setIsStreamOwner(boolean value)
	{
		tarBuffer.setIsStreamOwner(value);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Stream Overrides
	/** 
	 Gets a value indicating whether the current stream supports reading
	*/
	@Override
	public boolean getCanRead()
	{
		return inputStream.CanRead;
	}

	/** 
	 Gets a value indicating whether the current stream supports seeking
	 This property always returns false.
	*/
	@Override
	public boolean getCanSeek()
	{
		return false;
	}

	/** 
	 Gets a value indicating if the stream supports writing.
	 This property always returns false.
	*/
	@Override
	public boolean getCanWrite()
	{
		return false;
	}

	/** 
	 The length in bytes of the stream
	*/
	@Override
	public long getLength()
	{
		return inputStream.Length;
	}

	/** 
	 Gets or sets the position within the stream. 
	 Setting the Position is not supported and throws a NotSupportedExceptionNotSupportedException
	 
	 @exception NotSupportedException Any attempt to set position
	*/
	@Override
	public long getPosition()
	{
		return inputStream.Position;
	}
	@Override
	public void setPosition(long value)
	{
		throw new UnsupportedOperationException("TarInputStream Seek not supported");
	}

	/** 
	 Flushes the baseInputStream
	*/
	@Override
	public void Flush()
	{
		inputStream.Flush();
	}

	/** 
	 Set the streams position.  This operation is not supported and will throw a NotSupportedException
	 
	 @param offset The offset relative to the origin to seek to.
	 @param origin The <see cref="SeekOrigin"/> to start seeking from.
	 @return The new position in the stream.
	 @exception NotSupportedException Any access
	*/
	@Override
	public long Seek(long offset, SeekOrigin origin)
	{
		throw new UnsupportedOperationException("TarInputStream Seek not supported");
	}

	/** 
	 Sets the length of the stream
	 This operation is not supported and will throw a NotSupportedException
	 
	 @param value The new stream length.
	 @exception NotSupportedException Any access
	*/
	@Override
	public void SetLength(long value)
	{
		throw new UnsupportedOperationException("TarInputStream SetLength not supported");
	}

	/** 
	 Writes a block of bytes to this stream using data from a buffer.
	 This operation is not supported and will throw a NotSupportedException
	 
	 @param buffer The buffer containing bytes to write.
	 @param offset The offset in the buffer of the frist byte to write.
	 @param count The number of bytes to write.
	 @exception NotSupportedException Any access
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
	{
		throw new UnsupportedOperationException("TarInputStream Write not supported");
	}

	/** 
	 Writes a byte to the current position in the file stream.
	 This operation is not supported and will throw a NotSupportedException
	 
	 @param value The byte value to write.
	 @exception NotSupportedException Any access
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void WriteByte(byte value)
	@Override
	public void WriteByte(byte value)
	{
		throw new UnsupportedOperationException("TarInputStream WriteByte not supported");
	}
	/** 
	 Reads a byte from the current tar archive entry.
	 
	 @return A byte cast to an int; -1 if the at the end of the stream.
	*/
	@Override
	public int ReadByte()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] oneByteBuffer = new byte[1];
		byte[] oneByteBuffer = new byte[1];
		int num = Read(oneByteBuffer, 0, 1);
		if (num <= 0)
		{
			// return -1 to indicate that no byte was read.
			return -1;
		}
		return oneByteBuffer[0];
	}

	/** 
	 Reads bytes from the current tar archive entry.
	 
	 This method is aware of the boundaries of the current
	 entry in the archive and will deal with them appropriately
	 
	 @param buffer
	 The buffer into which to place bytes read.
	 
	 @param offset
	 The offset at which to place bytes read.
	 
	 @param count
	 The number of bytes to read.
	 
	 @return 
	 The number of bytes read, or 0 at end of stream/EOF.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
	@Override
	public int Read(byte[] buffer, int offset, int count)
	{
		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		int totalRead = 0;

		if (entryOffset >= entrySize)
		{
			return 0;
		}

		long numToRead = count;

		if ((numToRead + entryOffset) > entrySize)
		{
			numToRead = entrySize - entryOffset;
		}

		if (readBuffer != null)
		{
			int sz = (numToRead > readBuffer.length) ? readBuffer.length : (int)numToRead;

			System.arraycopy(readBuffer, 0, buffer, offset, sz);

			if (sz >= readBuffer.length)
			{
				readBuffer = null;
			}
			else
			{
				int newLen = readBuffer.length - sz;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] newBuf = new byte[newLen];
				byte[] newBuf = new byte[newLen];
				System.arraycopy(readBuffer, sz, newBuf, 0, newLen);
				readBuffer = newBuf;
			}

			totalRead += sz;
			numToRead -= sz;
			offset += sz;
		}

		while (numToRead > 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] rec = tarBuffer.ReadBlock();
			byte[] rec = tarBuffer.ReadBlock();
			if (rec == null)
			{
				// Unexpected EOF!
				throw new TarException("unexpected EOF with " + numToRead + " bytes unread");
			}

			int sz = (int)numToRead;
			int recLen = rec.length;

			if (recLen > sz)
			{
				System.arraycopy(rec, 0, buffer, offset, sz);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: readBuffer = new byte[recLen - sz];
				readBuffer = new byte[recLen - sz];
				System.arraycopy(rec, sz, readBuffer, 0, recLen - sz);
			}
			else
			{
				sz = recLen;
				System.arraycopy(rec, 0, buffer, offset, recLen);
			}

			totalRead += sz;
			numToRead -= sz;
			offset += sz;
		}

		entryOffset += totalRead;

		return totalRead;
	}

	/** 
	 Closes this stream. Calls the TarBuffer's close() method.
	 The underlying stream is closed by the TarBuffer.
	*/
	@Override
	public void Close()
	{
		tarBuffer.Close();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Set the entry factory for this instance.
	 
	 @param factory The factory for creating new entries
	*/
	public final void SetEntryFactory(IEntryFactory factory)
	{
		entryFactory = factory;
	}

	/** 
	 Get the record size being used by this stream's TarBuffer.
	*/
	public final int getRecordSize()
	{
		return tarBuffer.getRecordSize();
	}

	/** 
	 Get the record size being used by this stream's TarBuffer.
	 
	 @return 
	 TarBuffer record size.
	 
	*/
	@Deprecated
	public final int GetRecordSize()
	{
		return tarBuffer.getRecordSize();
	}

	/** 
	 Get the available data that can be read from the current
	 entry in the archive. This does not indicate how much data
	 is left in the entire archive, only in the current entry.
	 This value is determined from the entry's size header field
	 and the amount of data already read from the current entry.
	 
	 @return 
	 The number of available bytes for the current entry.
	 
	*/
	public final long getAvailable()
	{
		return entrySize - entryOffset;
	}

	/** 
	 Skip bytes in the input buffer. This skips bytes in the
	 current entry's data, not the entire archive, and will
	 stop at the end of the current entry's data if the number
	 to skip extends beyond that point.
	 
	 @param skipCount
	 The number of bytes to skip.
	 
	*/
	public final void Skip(long skipCount)
	{
		// TODO: REVIEW efficiency of TarInputStream.Skip
		// This is horribly inefficient, but it ensures that we
		// properly skip over bytes via the TarBuffer...
		//
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] skipBuf = new byte[8 * 1024];
		byte[] skipBuf = new byte[8 * 1024];

		for (long num = skipCount; num > 0;)
		{
			int toRead = num > skipBuf.length ? skipBuf.length : (int)num;
			int numRead = Read(skipBuf, 0, toRead);

			if (numRead == -1)
			{
				break;
			}

			num -= numRead;
		}
	}

	/** 
	 Return a value of true if marking is supported; false otherwise.
	 
	 Currently marking is not supported, the return value is always false.
	*/
	public final boolean getIsMarkSupported()
	{
		return false;
	}

	/** 
	 Since we do not support marking just yet, we do nothing.
	 
	 @param markLimit
	 The limit to mark.
	 
	*/
	public final void Mark(int markLimit)
	{
	}

	/** 
	 Since we do not support marking just yet, we do nothing.
	*/
	public final void Reset()
	{
	}

	/** 
	 Get the next entry in this tar archive. This will skip
	 over any remaining data in the current entry, if there
	 is one, and place the input stream at the header of the
	 next entry, and read the header and instantiate a new
	 TarEntry from the header bytes and return that entry.
	 If there are no more entries in the archive, null will
	 be returned to indicate that the end of the archive has
	 been reached.
	 
	 @return 
	 The next TarEntry in the archive, or null.
	 
	*/
	public final TarEntry GetNextEntry()
	{
		if (hasHitEOF)
		{
			return null;
		}

		if (currentEntry != null)
		{
			SkipToNextEntry();
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] headerBuf = tarBuffer.ReadBlock();
		byte[] headerBuf = tarBuffer.ReadBlock();

		if (headerBuf == null)
		{
			hasHitEOF = true;
		}
		else if (TarBuffer.IsEndOfArchiveBlock(headerBuf))
		{
			hasHitEOF = true;
		}

		if (hasHitEOF)
		{
			currentEntry = null;
		}
		else
		{
			try
			{
				TarHeader header = new TarHeader();
				header.ParseBuffer(headerBuf);
				if (!header.getIsChecksumValid())
				{
					throw new TarException("Header checksum is invalid");
				}
				this.entryOffset = 0;
				this.entrySize = header.getSize();

				StringBuilder longName = null;

				if (header.getTypeFlag() == TarHeader.LF_GNU_LONGNAME)
				{

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] nameBuffer = new byte[TarBuffer.BlockSize];
					byte[] nameBuffer = new byte[TarBuffer.BlockSize];
					long numToRead = this.entrySize;

					longName = new StringBuilder();

					while (numToRead > 0)
					{
						int numRead = this.Read(nameBuffer, 0, (numToRead > nameBuffer.length ? nameBuffer.length : (int)numToRead));

						if (numRead == -1)
						{
							throw new InvalidHeaderException("Failed to read long name entry");
						}

						longName.append(TarHeader.ParseName(nameBuffer, 0, numRead).toString());
						numToRead -= numRead;
					}

					SkipToNextEntry();
					headerBuf = this.tarBuffer.ReadBlock();
				}
				else if (header.getTypeFlag() == TarHeader.LF_GHDR)
				{ // POSIX global extended header
					// Ignore things we dont understand completely for now
					SkipToNextEntry();
					headerBuf = this.tarBuffer.ReadBlock();
				}
				else if (header.getTypeFlag() == TarHeader.LF_XHDR)
				{ // POSIX extended header
					// Ignore things we dont understand completely for now
					SkipToNextEntry();
					headerBuf = this.tarBuffer.ReadBlock();
				}
				else if (header.getTypeFlag() == TarHeader.LF_GNU_VOLHDR)
				{
					// TODO: could show volume name when verbose
					SkipToNextEntry();
					headerBuf = this.tarBuffer.ReadBlock();
				}
				else if (header.getTypeFlag() != TarHeader.LF_NORMAL && header.getTypeFlag() != TarHeader.LF_OLDNORM && header.getTypeFlag() != TarHeader.LF_DIR)
				{
					// Ignore things we dont understand completely for now
					SkipToNextEntry();
					headerBuf = tarBuffer.ReadBlock();
				}

				if (entryFactory == null)
				{
					currentEntry = new TarEntry(headerBuf);
					if (longName != null)
					{
						currentEntry.setName(longName.toString());
					}
				}
				else
				{
					currentEntry = entryFactory.CreateEntry(headerBuf);
				}

				// Magic was checked here for 'ustar' but there are multiple valid possibilities
				// so this is not done anymore.

				entryOffset = 0;

				// TODO: Review How do we resolve this discrepancy?!
				entrySize = this.currentEntry.getSize();
			}
			catch (InvalidHeaderException ex)
			{
				entrySize = 0;
				entryOffset = 0;
				currentEntry = null;
				String errorText = String.format("Bad header in record %1$s block %2$s %3$s", tarBuffer.getCurrentRecord(), tarBuffer.getCurrentBlock(), ex.getMessage());
				throw new InvalidHeaderException(errorText);
			}
		}
		return currentEntry;
	}

	/** 
	 Copies the contents of the current tar archive entry directly into
	 an output stream.
	 
	 @param outputStream
	 The OutputStream into which to write the entry's data.
	 
	*/
	public final void CopyEntryContents(OutputStream outputStream)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] tempBuffer = new byte[32 * 1024];
		byte[] tempBuffer = new byte[32 * 1024];

		while (true)
		{
			int numRead = Read(tempBuffer, 0, tempBuffer.length);
			if (numRead <= 0)
			{
				break;
			}
			outputStream.write(tempBuffer, 0, numRead);
		}
	}

	private void SkipToNextEntry()
	{
		long numToSkip = entrySize - entryOffset;

		if (numToSkip > 0)
		{
			Skip(numToSkip);
		}

		readBuffer = null;
	}

	/** 
	 This interface is provided, along with the method <see cref="SetEntryFactory"/>, to allow
	 the programmer to have their own <see cref="TarEntry"/> subclass instantiated for the
	 entries return from <see cref="GetNextEntry"/>.
	*/
	public interface IEntryFactory
	{
		/** 
		 Create an entry based on name alone
		 
		 @param name
		 Name of the new EntryPointNotFoundException to create
		 
		 @return created TarEntry or descendant class
		*/
		TarEntry CreateEntry(String name);

		/** 
		 Create an instance based on an actual file
		 
		 @param fileName
		 Name of file to represent in the entry
		 
		 @return 
		 Created TarEntry or descendant class
		 
		*/
		TarEntry CreateEntryFromFile(String fileName);

		/** 
		 Create a tar entry based on the header information passed
		 
		 @param headerBuffer
		 Buffer containing header information to create an an entry from.
		 
		 @return 
		 Created TarEntry or descendant class
		 
		*/
		TarEntry CreateEntry(byte[] headerBuffer);
	}

	/** 
	 Standard entry factory class creating instances of the class TarEntry
	*/
	public static class EntryFactoryAdapter implements IEntryFactory
	{
		/** 
		 Create a <see cref="TarEntry"/> based on named
		 
		 @param name The name to use for the entry
		 @return A new <see cref="TarEntry"/>
		*/
		public final TarEntry CreateEntry(String name)
		{
			return TarEntry.CreateTarEntry(name);
		}

		/** 
		 Create a tar entry with details obtained from <paramref name="fileName">file</paramref>
		 
		 @param fileName The name of the file to retrieve details from.
		 @return A new <see cref="TarEntry"/>
		*/
		public final TarEntry CreateEntryFromFile(String fileName)
		{
			return TarEntry.CreateEntryFromFile(fileName);
		}

		/** 
		 Create an entry based on details in <paramref name="headerBuffer">header</paramref>
		 			
		 @param headerBuffer The buffer containing entry details.
		 @return A new <see cref="TarEntry"/>
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TarEntry CreateEntry(byte[] headerBuffer)
		public final TarEntry CreateEntry(byte[] headerBuffer)
		{
			return new TarEntry(headerBuffer);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 Flag set when last block has been read
	*/
	protected boolean hasHitEOF;

	/** 
	 Size of this entry as recorded in header
	*/
	protected long entrySize;

	/** 
	 Number of bytes read for this entry so far
	*/
	protected long entryOffset;

	/** 
	 Buffer used with calls to <code>Read()</code>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected byte[] readBuffer;
	protected byte[] readBuffer;

	/** 
	 Working buffer
	*/
	protected TarBuffer tarBuffer;

	/** 
	 Current entry being read
	*/
	private TarEntry currentEntry;

	/** 
	 Factory used to create TarEntry or descendant class instance
	*/
	protected IEntryFactory entryFactory;

	/** 
	 Stream used as the source of input data.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream inputStream;
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

