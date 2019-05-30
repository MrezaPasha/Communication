package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.Compression.Encryption.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;

 }
 </code>
 </example>
*/
public class ZipInputStream extends InflaterInputStream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields

	/** 
	 Delegate for reading bytes from a stream. 
	*/
	@FunctionalInterface
	private interface ReadDataHandler
	{
		int invoke(byte[] b, int offset, int length);
	}

	/** 
	 The current reader this instance.
	*/
	private ReadDataHandler internalReader;

	private Crc32 crc = new Crc32();
	private ZipEntry entry;

	private long size;
	private int method;
	private int flags;
	private String password;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Creates a new Zip input stream, for reading a zip archive.
	 
	 @param baseInputStream The underlying <see cref="Stream"/> providing data.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public ZipInputStream(Stream baseInputStream)
	{
		super(baseInputStream, new Inflater(true));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internalReader = (byte[] b, int offset, int length) -> ReadingNotAvailable(b, offset, length);
		internalReader = (byte[] b, int offset, int length) -> ReadingNotAvailable(b, offset, length);
	}

	/** 
	 Creates a new Zip input stream, for reading a zip archive.
	 
	 @param baseInputStream The underlying <see cref="Stream"/> providing data.
	 @param bufferSize Size of the buffer.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public ZipInputStream(Stream baseInputStream, int bufferSize)
	{
		super(baseInputStream, new Inflater(true), bufferSize);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internalReader = (byte[] b, int offset, int length) -> ReadingNotAvailable(b, offset, length);
		internalReader = (byte[] b, int offset, int length) -> ReadingNotAvailable(b, offset, length);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Optional password used for encryption when non-null
	 
	 <value>A password for all encrypted <see cref="ZipEntry">entries </see> in this <see cref="ZipInputStream"/></value>
	*/
	public final String getPassword()
	{
		return password;
	}
	public final void setPassword(String value)
	{
		password = value;
	}


	/** 
	 Gets a value indicating if there is a current entry and it can be decompressed
	 
	 
	 The entry can only be decompressed if the library supports the zip features required to extract it.
	 See the <see cref="ZipEntry.Version">ZipEntry Version</see> property for more details.
	 
	*/
	public final boolean getCanDecompressEntry()
	{
		return (entry != null) && entry.getCanDecompress();
	}

	/** 
	 Advances to the next entry in the archive
	 
	 @return 
	 The next <see cref="ZipEntry">entry</see> in the archive or null if there are no more entries.
	 
	 
	 If the previous entry is still open <see cref="CloseEntry">CloseEntry</see> is called.
	 
	 @exception InvalidOperationException
	 Input stream is closed
	 
	 @exception ZipException
	 Password is not set, password is invalid, compression method is invalid,
	 version required to extract is not supported
	 
	*/
	public final ZipEntry GetNextEntry()
	{
		if (crc == null)
		{
			throw new IllegalStateException("Closed.");
		}

		if (entry != null)
		{
			CloseEntry();
		}

		int header = inputBuffer.ReadLeInt();

		if (header == ZipConstants.CentralHeaderSignature || header == ZipConstants.EndOfCentralDirectorySignature || header == ZipConstants.CentralHeaderDigitalSignature || header == ZipConstants.ArchiveExtraDataSignature || header == ZipConstants.Zip64CentralFileHeaderSignature)
		{
			// No more individual entries exist
			Close();
			return null;
		}

		// -jr- 07-Dec-2003 Ignore spanning temporary signatures if found
		// Spanning signature is same as descriptor signature and is untested as yet.
		if ((header == ZipConstants.SpanningTempSignature) || (header == ZipConstants.SpanningSignature))
		{
			header = inputBuffer.ReadLeInt();
		}

		if (header != ZipConstants.LocalHeaderSignature)
		{
			throw new ZipException("Wrong Local header signature: 0x" + String.format("%X", header));
		}

		short versionRequiredToExtract = (short)inputBuffer.ReadLeShort();

		flags = inputBuffer.ReadLeShort();
		method = inputBuffer.ReadLeShort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint dostime = (uint)inputBuffer.ReadLeInt();
		int dostime = (int)inputBuffer.ReadLeInt();
		int crc2 = inputBuffer.ReadLeInt();
		csize = inputBuffer.ReadLeInt();
		size = inputBuffer.ReadLeInt();
		int nameLen = inputBuffer.ReadLeShort();
		int extraLen = inputBuffer.ReadLeShort();

		boolean isCrypted = (flags & 1) == 1;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer = new byte[nameLen];
		byte[] buffer = new byte[nameLen];
		inputBuffer.ReadRawBuffer(buffer);

		String name = ZipConstants.ConvertToStringExt(flags, buffer);

		entry = new ZipEntry(name, versionRequiredToExtract);
		entry.setFlags(flags);

		entry.setCompressionMethod(CompressionMethod.forValue(method));

		if ((flags & 8) == 0)
		{
			entry.setCrc(crc2 & 0xFFFFFFFFL);
			entry.setSize(size & 0xFFFFFFFFL);
			entry.setCompressedSize(csize & 0xFFFFFFFFL);

//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: entry.CryptoCheckValue = (byte)((crc2 >> 24) & 0xff);
			entry.setCryptoCheckValue((byte)((crc2 >> 24) & 0xff));

		}
		else
		{

			// This allows for GNU, WinZip and possibly other archives, the PKZIP spec
			// says these values are zero under these circumstances.
			if (crc2 != 0)
			{
				entry.setCrc(crc2 & 0xFFFFFFFFL);
			}

			if (size != 0)
			{
				entry.setSize(size & 0xFFFFFFFFL);
			}

			if (csize != 0)
			{
				entry.setCompressedSize(csize & 0xFFFFFFFFL);
			}

//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: entry.CryptoCheckValue = (byte)((dostime >> 8) & 0xff);
			entry.setCryptoCheckValue((byte)((dostime >>> 8) & 0xff));
		}

		entry.setDosTime(dostime);

		// If local header requires Zip64 is true then the extended header should contain
		// both values.

		// Handle extra data if present.  This can set/alter some fields of the entry.
		if (extraLen > 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] extra = new byte[extraLen];
			byte[] extra = new byte[extraLen];
			inputBuffer.ReadRawBuffer(extra);
			entry.setExtraData(extra);
		}

		entry.ProcessExtraData(true);
		if (entry.getCompressedSize() >= 0)
		{
			csize = entry.getCompressedSize();
		}

		if (entry.getSize() >= 0)
		{
			size = entry.getSize();
		}

		if (method == CompressionMethod.Stored.getValue() && (!isCrypted && csize != size || (isCrypted && csize - ZipConstants.CryptoHeaderSize != size)))
		{
			throw new ZipException("Stored, but compressed != uncompressed");
		}

		// Determine how to handle reading of data if this is attempted.
		if (entry.IsCompressionMethodSupported())
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internalReader = (byte[] b, int offset, int length) -> InitialRead(b, offset, length);
			internalReader = (byte[] b, int offset, int length) -> InitialRead(b, offset, length);
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internalReader = (byte[] b, int offset, int length) -> ReadingNotSupported(b, offset, length);
			internalReader = (byte[] b, int offset, int length) -> ReadingNotSupported(b, offset, length);
		}

		return entry;
	}

	/** 
	 Read data descriptor at the end of compressed data. 
	*/
	private void ReadDataDescriptor()
	{
		if (inputBuffer.ReadLeInt() != ZipConstants.DataDescriptorSignature)
		{
			throw new ZipException("Data descriptor signature not found");
		}

		entry.setCrc(inputBuffer.ReadLeInt() & 0xFFFFFFFFL);

		if (entry.getLocalHeaderRequiresZip64())
		{
			csize = inputBuffer.ReadLeLong();
			size = inputBuffer.ReadLeLong();
		}
		else
		{
			csize = inputBuffer.ReadLeInt();
			size = inputBuffer.ReadLeInt();
		}
		entry.setCompressedSize(csize);
		entry.setSize(size);
	}

	/** 
	 Complete cleanup as the final part of closing.
	 
	 @param testCrc True if the crc value should be tested
	*/
	private void CompleteCloseEntry(boolean testCrc)
	{
		StopDecrypting();

		if ((flags & 8) != 0)
		{
			ReadDataDescriptor();
		}

		size = 0;

		if (testCrc && ((crc.getValue() & 0xFFFFFFFFL) != entry.getCrc()) && (entry.getCrc() != -1))
		{
			throw new ZipException("CRC mismatch");
		}

		crc.Reset();

		if (method == CompressionMethod.Deflated.getValue())
		{
			inf.Reset();
		}
		entry = null;
	}

	/** 
	 Closes the current zip entry and moves to the next one.
	 
	 @exception InvalidOperationException
	 The stream is closed
	 
	 @exception ZipException
	 The Zip stream ends early
	 
	*/
	public final void CloseEntry()
	{
		if (crc == null)
		{
			throw new IllegalStateException("Closed");
		}

		if (entry == null)
		{
			return;
		}

		if (method == CompressionMethod.Deflated.getValue())
		{
			if ((flags & 8) != 0)
			{
				// We don't know how much we must skip, read until end.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] tmp = new byte[4096];
				byte[] tmp = new byte[4096];

				// Read will close this entry
				while (Read(tmp, 0, tmp.length) > 0)
				{
				}
				return;
			}

			csize -= inf.getTotalIn();
			inputBuffer.setAvailable(inputBuffer.getAvailable() + inf.getRemainingInput());
		}

		if ((inputBuffer.getAvailable() > csize) && (csize >= 0))
		{
			inputBuffer.setAvailable((int)((long)inputBuffer.getAvailable() - csize));
		}
		else
		{
			csize -= inputBuffer.getAvailable();
			inputBuffer.setAvailable(0);
			while (csize != 0)
			{
				long skipped = super.Skip(csize);

				if (skipped <= 0)
				{
					throw new ZipException("Zip archive ends early.");
				}

				csize -= skipped;
			}
		}

		CompleteCloseEntry(false);
	}

	/** 
	 Returns 1 if there is an entry available
	 Otherwise returns 0.
	*/
	@Override
	public int getAvailable()
	{
		return entry != null ? 1 : 0;
	}

	/** 
	 Returns the current size that can be read from the current entry if available
	 
	 @exception ZipException Thrown if the entry size is not known.
	 @exception InvalidOperationException Thrown if no entry is currently available.
	*/
	@Override
	public long getLength()
	{
		if (entry != null)
		{
			if (entry.getSize() >= 0)
			{
				return entry.getSize();
			}
			else
			{
				throw new ZipException("Length not available for the current entry");
			}
		}
		else
		{
			throw new IllegalStateException("No current entry");
		}
	}


	/** 
	 Reads a byte from the current zip entry.
	 
	 @return 
	 The byte or -1 if end of stream is reached.
	 
	*/
	@Override
	public int ReadByte()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] b = new byte[1];
		byte[] b = new byte[1];
		if (Read(b, 0, 1) <= 0)
		{
			return -1;
		}
		return b[0] & 0xff;
	}

	/** 
	 Handle attempts to read by throwing an <see cref="InvalidOperationException"/>.
	 
	 @param destination The destination array to store data in.
	 @param offset The offset at which data read should be stored.
	 @param count The maximum number of bytes to read.
	 @return Returns the number of bytes actually read.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: int ReadingNotAvailable(byte[] destination, int offset, int count)
	private int ReadingNotAvailable(byte[] destination, int offset, int count)
	{
		throw new IllegalStateException("Unable to read from this stream");
	}

	/** 
	 Handle attempts to read from this entry by throwing an exception
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: int ReadingNotSupported(byte[] destination, int offset, int count)
	private int ReadingNotSupported(byte[] destination, int offset, int count)
	{
		throw new ZipException("The compression method for this entry is not supported");
	}

	/** 
	 Perform the initial read on an entry which may include 
	 reading encryption headers and setting up inflation.
	 
	 @param destination The destination to fill with data read.
	 @param offset The offset to start reading at.
	 @param count The maximum number of bytes to read.
	 @return The actual number of bytes read.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: int InitialRead(byte[] destination, int offset, int count)
	private int InitialRead(byte[] destination, int offset, int count)
	{
		if (!getCanDecompressEntry())
		{
			throw new ZipException("Library cannot extract this entry. Version required is (" + String.valueOf(entry.getVersion()) + ")");
		}

		// Handle encryption if required.
		if (entry.getIsCrypted())
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new ZipException("Encryption not supported for Compact Framework 1.0");
//#else
			if (password == null)
			{
				throw new ZipException("No password set.");
			}

			// Generate and set crypto transform...
			PkzipClassicManaged managed = new PkzipClassicManaged();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] key = PkzipClassic.GenerateKeys(ZipConstants.ConvertToArray(password));
			byte[] key = PkzipClassic.GenerateKeys(ZipConstants.ConvertToArray(password));

			inputBuffer.setCryptoTransform(managed.CreateDecryptor(key, null));

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] cryptbuffer = new byte[ZipConstants.CryptoHeaderSize];
			byte[] cryptbuffer = new byte[ZipConstants.CryptoHeaderSize];
			inputBuffer.ReadClearTextBuffer(cryptbuffer, 0, ZipConstants.CryptoHeaderSize);

			if (cryptbuffer[ZipConstants.CryptoHeaderSize - 1] != entry.getCryptoCheckValue())
			{
				throw new ZipException("Invalid password");
			}

			if (csize >= ZipConstants.CryptoHeaderSize)
			{
				csize -= ZipConstants.CryptoHeaderSize;
			}
			else if ((entry.getFlags() & GeneralBitFlags.Descriptor.getValue()) == 0)
			{
				throw new ZipException(String.format("Entry compressed size %1$s too small for encryption", csize));
			}
//#endif
		}
		else
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
			inputBuffer.setCryptoTransform(null);
//#endif
		}

		if ((csize > 0) || ((flags & GeneralBitFlags.Descriptor.getValue()) != 0))
		{
			if ((method == CompressionMethod.Deflated.getValue()) && (inputBuffer.getAvailable() > 0))
			{
				inputBuffer.SetInflaterInput(inf);
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internalReader = (byte[] b, int offset, int length) -> BodyRead(b, offset, length);
			internalReader = (byte[] b, int offset, int length) -> BodyRead(b, offset, length);
			return BodyRead(destination, offset, count);
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internalReader = (byte[] b, int offset, int length) -> ReadingNotAvailable(b, offset, length);
			internalReader = (byte[] b, int offset, int length) -> ReadingNotAvailable(b, offset, length);
			return 0;
		}
	}

	/** 
	 Read a block of bytes from the stream.
	 
	 @param buffer The destination for the bytes.
	 @param offset The index to start storing data.
	 @param count The number of bytes to attempt to read.
	 @return Returns the number of bytes read.
	 Zero bytes read means end of stream.
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

		if ((buffer.length - offset) < count)
		{
			throw new IllegalArgumentException("Invalid offset/count combination");
		}

		return internalReader.invoke(buffer, offset, count);
	}

	/** 
	 Reads a block of bytes from the current zip entry.
	 
	 @return 
	 The number of bytes read (this may be less than the length requested, even before the end of stream), or 0 on end of stream.
	 
	 <exception name="IOException">
	 An i/o error occured.
	 
	 @exception ZipException
	 The deflated stream is corrupted.
	 
	 @exception InvalidOperationException
	 The stream is not open.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: int BodyRead(byte[] buffer, int offset, int count)
	private int BodyRead(byte[] buffer, int offset, int count)
	{
		if (crc == null)
		{
			throw new IllegalStateException("Closed");
		}

		if ((entry == null) || (count <= 0))
		{
			return 0;
		}

		if (offset + count > buffer.length)
		{
			throw new IllegalArgumentException("Offset + count exceeds buffer size");
		}

		boolean finished = false;

		switch (method)
		{
			case CompressionMethod.Deflated.getValue():
				count = super.Read(buffer, offset, count);
				if (count <= 0)
				{
					if (!inf.getIsFinished())
					{
						throw new ZipException("Inflater not finished!");
					}
					inputBuffer.setAvailable(inf.getRemainingInput());

					// A csize of -1 is from an unpatched local header
					if ((flags & 8) == 0 && (inf.getTotalIn() != csize && csize != 0xFFFFFFFFL && csize != -1 || inf.getTotalOut() != size))
					{
						throw new ZipException("Size mismatch: " + csize + ";" + size + " <-> " + inf.getTotalIn() + ";" + inf.getTotalOut());
					}
					inf.Reset();
					finished = true;
				}
				break;

			case CompressionMethod.Stored.getValue():
				if ((count > csize) && (csize >= 0))
				{
					count = (int)csize;
				}

				if (count > 0)
				{
					count = inputBuffer.ReadClearTextBuffer(buffer, offset, count);
					if (count > 0)
					{
						csize -= count;
						size -= count;
					}
				}

				if (csize == 0)
				{
					finished = true;
				}
				else
				{
					if (count < 0)
					{
						throw new ZipException("EOF in stored block");
					}
				}
				break;
		}

		if (count > 0)
		{
			crc.Update(buffer, offset, count);
		}

		if (finished)
		{
			CompleteCloseEntry(true);
		}

		return count;
	}

	/** 
	 Closes the zip input stream
	*/
	@Override
	public void Close()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internalReader = (byte[] b, int offset, int length) -> ReadingNotAvailable(b, offset, length);
		internalReader = (byte[] b, int offset, int length) -> ReadingNotAvailable(b, offset, length);
		crc = null;
		entry = null;

		super.Close();
	}
}