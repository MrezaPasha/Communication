package Rasad.Core.Compression.Zip.Compression.Streams;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;
import Rasad.Core.Compression.Zip.Compression.*;

/** 
 This filter stream is used to decompress data compressed using the "deflate"
 format. The "deflate" format is described in RFC 1951.

 This stream may form the basis for other decompression filters, such
 as the <see cref="Rasad.Core.Compression.GZip.GZipInputStream">GZipInputStream</see>.

 Author of the original java version : John Leuner.
*/
public class InflaterInputStream extends Stream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Create an InflaterInputStream with the default decompressor
	 and a default buffer size of 4KB.
	 
	 @param  baseInputStream
	 The InputStream to read bytes from
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public InflaterInputStream(Stream baseInputStream)
	{
		this(baseInputStream, new Inflater(), 4096);
	}

	/** 
	 Create an InflaterInputStream with the specified decompressor
	 and a default buffer size of 4KB.
	 
	 @param  baseInputStream
	 The source of input data
	 
	 @param  inf
	 The decompressor used to decompress data read from baseInputStream
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public InflaterInputStream(Stream baseInputStream, Inflater inf)
	{
		this(baseInputStream, inf, 4096);
	}

	/** 
	 Create an InflaterInputStream with the specified decompressor
	 and the specified buffer size.
	 
	 @param  baseInputStream
	 The InputStream to read bytes from
	 
	 @param  inflater
	 The decompressor to use
	 
	 @param  bufferSize
	 Size of the buffer to use
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public InflaterInputStream(Stream baseInputStream, Inflater inflater, int bufferSize)
	{
		if (baseInputStream == null)
		{
			throw new NullPointerException("baseInputStream");
		}

		if (inflater == null)
		{
			throw new NullPointerException("inflater");
		}

		if (bufferSize <= 0)
		{
			throw new IndexOutOfBoundsException("bufferSize");
		}

		this.baseInputStream = baseInputStream;
		this.inf = inflater;

		inputBuffer = new InflaterInputBuffer(baseInputStream, bufferSize);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get/set flag indicating ownership of underlying stream.
	 When the flag is true <see cref="Close"/> will close the underlying stream also.
	 
	 
	 The default value is true.
	 
	*/
	public final boolean getIsStreamOwner()
	{
		return isStreamOwner;
	}
	public final void setIsStreamOwner(boolean value)
	{
		isStreamOwner = value;
	}

	/** 
	 Skip specified number of bytes of uncompressed data
	 
	 @param count
	 Number of bytes to skip
	 
	 @return 
	 The number of bytes skipped, zero if the end of 
	 stream has been reached
	 
	 @exception ArgumentOutOfRangeException
	 <paramref name="count">The number of bytes</paramref> to skip is less than or equal to zero.
	 
	*/
	public final long Skip(long count)
	{
		if (count <= 0)
		{
			throw new IndexOutOfBoundsException("count");
		}

		// v0.80 Skip by seeking if underlying stream supports it...
		if (baseInputStream.CanSeek)
		{
			baseInputStream.Seek(count, SeekOrigin.Current);
			return count;
		}
		else
		{
			int length = 2048;
			if (count < length)
			{
				length = (int) count;
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] tmp = new byte[length];
			byte[] tmp = new byte[length];
			int readCount = 1;
			long toSkip = count;

			while ((toSkip > 0) && (readCount > 0))
			{
				if (toSkip < length)
				{
					length = (int)toSkip;
				}

				readCount = baseInputStream.Read(tmp, 0, length);
				toSkip -= readCount;
			}

			return count - toSkip;
		}
	}

	/** 
	 Clear any cryptographic state.
	*/
	protected final void StopDecrypting()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
		inputBuffer.setCryptoTransform(null);
//#endif
	}

	/** 
	 Returns 0 once the end of the stream (EOF) has been reached.
	 Otherwise returns 1.
	*/
	public int getAvailable()
	{
		return inf.getIsFinished() ? 0 : 1;
	}

	/** 
	 Fills the buffer with more data to decompress.
	 
	 @exception SharpZipBaseException
	 Stream ends early
	 
	*/
	protected final void Fill()
	{
		// Protect against redundant calls
		if (inputBuffer.getAvailable() <= 0)
		{
			inputBuffer.Fill();
			if (inputBuffer.getAvailable() <= 0)
			{
				throw new SharpZipBaseException("Unexpected EOF");
			}
		}
		inputBuffer.SetInflaterInput(inf);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Stream Overrides
	/** 
	 Gets a value indicating whether the current stream supports reading
	*/
	@Override
	public boolean getCanRead()
	{
		return baseInputStream.CanRead;
	}

	/** 
	 Gets a value of false indicating seeking is not supported for this stream.
	*/
	@Override
	public boolean getCanSeek()
	{
		return false;
	}

	/** 
	 Gets a value of false indicating that this stream is not writeable.
	*/
	@Override
	public boolean getCanWrite()
	{
		return false;
	}

	/** 
	 A value representing the length of the stream in bytes.
	*/
	@Override
	public long getLength()
	{
		return inputBuffer.getRawLength();
	}

	/** 
	 The current position within the stream.
	 Throws a NotSupportedException when attempting to set the position
	 
	 @exception NotSupportedException Attempting to set the position
	*/
	@Override
	public long getPosition()
	{
		return baseInputStream.Position;
	}
	@Override
	public void setPosition(long value)
	{
		throw new UnsupportedOperationException("InflaterInputStream Position not supported");
	}

	/** 
	 Flushes the baseInputStream
	*/
	@Override
	public void Flush()
	{
		baseInputStream.Flush();
	}

	/** 
	 Sets the position within the current stream
	 Always throws a NotSupportedException
	 
	 @param offset The relative offset to seek to.
	 @param origin The <see cref="SeekOrigin"/> defining where to seek from.
	 @return The new position in the stream.
	 @exception NotSupportedException Any access
	*/
	@Override
	public long Seek(long offset, SeekOrigin origin)
	{
		throw new UnsupportedOperationException("Seek not supported");
	}

	/** 
	 Set the length of the current stream
	 Always throws a NotSupportedException
	 
	 @param value The new length value for the stream.
	 @exception NotSupportedException Any access
	*/
	@Override
	public void SetLength(long value)
	{
		throw new UnsupportedOperationException("InflaterInputStream SetLength not supported");
	}

	/** 
	 Writes a sequence of bytes to stream and advances the current position
	 This method always throws a NotSupportedException
	 
	 @param buffer Thew buffer containing data to write.
	 @param offset The offset of the first byte to write.
	 @param count The number of bytes to write.
	 @exception NotSupportedException Any access
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
	{
		throw new UnsupportedOperationException("InflaterInputStream Write not supported");
	}

	/** 
	 Writes one byte to the current stream and advances the current position
	 Always throws a NotSupportedException
	 
	 @param value The byte to write.
	 @exception NotSupportedException Any access
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void WriteByte(byte value)
	@Override
	public void WriteByte(byte value)
	{
		throw new UnsupportedOperationException("InflaterInputStream WriteByte not supported");
	}

	/** 
	 Entry point to begin an asynchronous write.  Always throws a NotSupportedException.
	 
	 @param buffer The buffer to write data from
	 @param offset Offset of first byte to write
	 @param count The maximum number of bytes to write
	 @param callback The method to be called when the asynchronous write operation is completed
	 @param state A user-provided object that distinguishes this particular asynchronous write request from other requests
	 @return An <see cref="System.IAsyncResult">IAsyncResult</see> that references the asynchronous write
	 @exception NotSupportedException Any access
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override IAsyncResult BeginWrite(byte[] buffer, int offset, int count, AsyncCallback callback, object state)
	@Override
	public IAsyncResult BeginWrite(byte[] buffer, int offset, int count, AsyncCallback callback, Object state)
	{
		throw new UnsupportedOperationException("InflaterInputStream BeginWrite not supported");
	}

	/** 
	 Closes the input stream.  When <see cref="IsStreamOwner"></see>
	 is true the underlying stream is also closed.
	*/
	@Override
	public void Close()
	{
		if (!isClosed)
		{
			isClosed = true;
			if (isStreamOwner)
			{
				baseInputStream.Close();
			}
		}
	}

	/** 
	 Reads decompressed data into the provided buffer byte array
	 
	 @param buffer
	 The array to read and decompress data into
	 
	 @param offset
	 The offset indicating where the data should be placed
	 
	 @param count
	 The number of bytes to decompress
	 
	 @return The number of bytes read.  Zero signals the end of stream
	 @exception SharpZipBaseException
	 Inflater needs a dictionary
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
	@Override
	public int Read(byte[] buffer, int offset, int count)
	{
		if (inf.getIsNeedingDictionary())
		{
			throw new SharpZipBaseException("Need a dictionary");
		}

		int remainingBytes = count;
		while (true)
		{
			int bytesRead = inf.Inflate(buffer, offset, remainingBytes);
			offset += bytesRead;
			remainingBytes -= bytesRead;

			if (remainingBytes == 0 || inf.getIsFinished())
			{
				break;
			}

			if (inf.getIsNeedingInput())
			{
				Fill();
			}
			else if (bytesRead == 0)
			{
				throw new ZipException("Dont know what to do");
			}
		}
		return count - remainingBytes;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 Decompressor for this stream
	*/
	protected Inflater inf;

	/** 
	 <see cref="InflaterInputBuffer">Input buffer</see> for this stream.
	*/
	protected InflaterInputBuffer inputBuffer;

	/** 
	 Base stream the inflater reads from.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream baseInputStream;

	/** 
	 The compressed size
	*/
	protected long csize;

	/** 
	 Flag indicating wether this instance has been closed or not.
	*/
	private boolean isClosed;

	/** 
	 Flag indicating wether this instance is designated the stream owner.
	 When closing if this flag is true the underlying stream is closed.
	*/
	private boolean isStreamOwner = true;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}