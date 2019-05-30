package Rasad.Core.Compression.LZW;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

 </code>
 </example>
*/
public class LzwInputStream extends Stream
{
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
	 Creates a LzwInputStream
	 
	 @param baseInputStream
	 The stream to read compressed data from (baseInputStream LZW format)
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public LzwInputStream(Stream baseInputStream)
	{
		this.baseInputStream = baseInputStream;
	}

	/** 
	 See <see cref="System.IO.Stream.ReadByte"/>
	 
	 @return 
	*/
	@Override
	public int ReadByte()
	{
		int b = Read(one, 0, 1);
		if (b == 1)
		{
			return (one[0] & 0xff);
		}
		return -1;
	}

	/** 
	 Reads decompressed data into the provided buffer byte array
	 
	 @param buffer
	 The array to read and decompress data into
	 
	 @param offset
	 The offset indicating where the data should be placed
	 
	 @param count
	 The number of bytes to decompress
	 
	 @return The number of bytes read. Zero signals the end of stream
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
	@Override
	public int Read(byte[] buffer, int offset, int count)
	{
		if (!headerParsed)
		{
			ParseHeader();
		}

		if (eof)
		{
			return -1;
		}
		int start = offset;

		/* Using local copies of various variables speeds things up by as
		 * much as 30% in Java! Performance not tested in C#.
		 */
		int[] lTabPrefix = tabPrefix;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] lTabSuffix = tabSuffix;
		byte[] lTabSuffix = tabSuffix;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] lStack = stack;
		byte[] lStack = stack;
		int lNBits = nBits;
		int lMaxCode = maxCode;
		int lMaxMaxCode = maxMaxCode;
		int lBitMask = bitMask;
		int lOldCode = oldCode;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte lFinChar = finChar;
		byte lFinChar = finChar;
		int lStackP = stackP;
		int lFreeEnt = freeEnt;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] lData = data;
		byte[] lData = data;
		int lBitPos = bitPos;


		// empty stack if stuff still left
		int sSize = lStack.length - lStackP;
		if (sSize > 0)
		{
			int num = (sSize >= count) ? count : sSize;
			System.arraycopy(lStack, lStackP, buffer, offset, num);
			offset += num;
			count -= num;
			lStackP += num;
		}

		if (count == 0)
		{
			stackP = lStackP;
			return offset - start;
		}


		// loop, filling local buffer until enough data has been decompressed
		MainLoop:
		do
		{
			if (end < EXTRA)
			{
				Fill();
			}

			int bitIn = (got > 0) ? (end - end % lNBits) << 3 : (end << 3) - (lNBits - 1);

			while (lBitPos < bitIn)
			{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region A
				// handle 1-byte reads correctly
				if (count == 0)
				{
					nBits = lNBits;
					maxCode = lMaxCode;
					maxMaxCode = lMaxMaxCode;
					bitMask = lBitMask;
					oldCode = lOldCode;
					finChar = lFinChar;
					stackP = lStackP;
					freeEnt = lFreeEnt;
					bitPos = lBitPos;

					return offset - start;
				}

				// check for code-width expansion
				if (lFreeEnt > lMaxCode)
				{
					int nBytes = lNBits << 3;
					lBitPos = (lBitPos - 1) + nBytes - (lBitPos - 1 + nBytes) % nBytes;

					lNBits++;
					lMaxCode = (lNBits == maxBits) ? lMaxMaxCode : (1 << lNBits) - 1;

					lBitMask = (1 << lNBits) - 1;
					lBitPos = ResetBuf(lBitPos);
//C# TO JAVA CONVERTER TODO TASK: There is no 'goto' in Java:
					goto MainLoop;
				}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region B
				// read next code
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
				int pos = lBitPos >> 3;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
				int code = (((lData[pos] & 0xFF) | ((lData[pos + 1] & 0xFF) << 8) | ((lData[pos + 2] & 0xFF) << 16)) >> (lBitPos & 0x7)) & lBitMask;

					lBitPos += lNBits;

				// handle first iteration
				if (lOldCode == -1)
				{
					if (code >= 256)
					{
						throw new LzwException("corrupt input: " + code + " > 255");
					}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: lFinChar = (byte)(lOldCode = code);
					lFinChar = (byte)(lOldCode = code);
					buffer[offset++] = lFinChar;
					count--;
					continue;
				}

				// handle CLEAR code
				if (code == TBL_CLEAR && blockMode)
				{
					System.arraycopy(zeros, 0, lTabPrefix, 0, zeros.length);
					lFreeEnt = TBL_FIRST - 1;

					int nBytes = lNBits << 3;
					lBitPos = (lBitPos - 1) + nBytes - (lBitPos - 1 + nBytes) % nBytes;
					lNBits = LzwConstants.INIT_BITS;
					lMaxCode = (1 << lNBits) - 1;
					lBitMask = lMaxCode;

					// Code tables reset

					lBitPos = ResetBuf(lBitPos);
//C# TO JAVA CONVERTER TODO TASK: There is no 'goto' in Java:
					goto MainLoop;
				}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region C
				// setup
				int inCode = code;
				lStackP = lStack.length;

				// Handle KwK case
				if (code >= lFreeEnt)
				{
					if (code > lFreeEnt)
					{
						throw new LzwException("corrupt input: code=" + code + ", freeEnt=" + lFreeEnt);
					}

					lStack[--lStackP] = lFinChar;
					code = lOldCode;
				}

				// Generate output characters in reverse order
				while (code >= 256)
				{
					lStack[--lStackP] = lTabSuffix[code];
					code = lTabPrefix[code];
				}

				lFinChar = lTabSuffix[code];
				buffer[offset++] = lFinChar;
				count--;

				// And put them out in forward order
				sSize = lStack.length - lStackP;
				int num = (sSize >= count) ? count : sSize;
				System.arraycopy(lStack, lStackP, buffer, offset, num);
				offset += num;
				count -= num;
				lStackP += num;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region D
				// generate new entry in table
				if (lFreeEnt < lMaxMaxCode)
				{
					lTabPrefix[lFreeEnt] = lOldCode;
					lTabSuffix[lFreeEnt] = lFinChar;
					lFreeEnt++;
				}

				// Remember previous code
				lOldCode = inCode;

				// if output buffer full, then return
				if (count == 0)
				{
					nBits = lNBits;
					maxCode = lMaxCode;
					bitMask = lBitMask;
					oldCode = lOldCode;
					finChar = lFinChar;
					stackP = lStackP;
					freeEnt = lFreeEnt;
					bitPos = lBitPos;

					return offset - start;
				}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion
			} // while

			lBitPos = ResetBuf(lBitPos);

		} while (got > 0); // do..while

		nBits = lNBits;
		maxCode = lMaxCode;
		bitMask = lBitMask;
		oldCode = lOldCode;
		finChar = lFinChar;
		stackP = lStackP;
		freeEnt = lFreeEnt;
		bitPos = lBitPos;

		eof = true;
		return offset - start;
	}

	/** 
	 Moves the unread data in the buffer to the beginning and resets
	 the pointers.
	 
	 @param bitPosition
	 @return 
	*/
	private int ResetBuf(int bitPosition)
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		int pos = bitPosition >> 3;
		System.arraycopy(data, pos, data, 0, end - pos);
		end -= pos;
		return 0;
	}


	private void Fill()
	{
		got = baseInputStream.Read(data, end, data.length - 1 - end);
		if (got > 0)
		{
			end += got;
		}
	}


	private void ParseHeader()
	{
		headerParsed = true;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] hdr = new byte[LzwConstants.HDR_SIZE];
		byte[] hdr = new byte[LzwConstants.HDR_SIZE];

		int result = baseInputStream.Read(hdr, 0, hdr.length);

		// Check the magic marker
		if (result < 0)
		{
			throw new LzwException("Failed to read LZW header");
		}

//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		if (hdr[0] != (LzwConstants.MAGIC >> 8) || hdr[1] != (LzwConstants.MAGIC & 0xff))
		{
			throw new LzwException(String.format("Wrong LZW header. Magic bytes don't match. 0x%02x 0x%1.2x", hdr[0], hdr[1]));
		}

		// Check the 3rd header byte
		blockMode = (hdr[2] & LzwConstants.BLOCK_MODE_MASK) > 0;
		maxBits = hdr[2] & LzwConstants.BIT_MASK;

		if (maxBits > LzwConstants.MAX_BITS)
		{
			throw new LzwException("Stream compressed with " + maxBits + " bits, but decompression can only handle " + LzwConstants.MAX_BITS + " bits.");
		}

		if ((hdr[2] & LzwConstants.RESERVED_MASK) > 0)
		{
			throw new LzwException("Unsupported bits set in the header.");
		}

		// Initialize variables
		maxMaxCode = 1 << maxBits;
		nBits = LzwConstants.INIT_BITS;
		maxCode = (1 << nBits) - 1;
		bitMask = maxCode;
		oldCode = -1;
		finChar = 0;
		freeEnt = blockMode ? TBL_FIRST : 256;

		tabPrefix = new int[1 << maxBits];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: tabSuffix = new byte[1 << maxBits];
		tabSuffix = new byte[1 << maxBits];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: stack = new byte[1 << maxBits];
		stack = new byte[1 << maxBits];
		stackP = stack.length;

		for (int idx = 255; idx >= 0; idx--)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: tabSuffix[idx] = (byte)idx;
			tabSuffix[idx] = (byte)idx;
		}
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
		return got;
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

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream baseInputStream;

	/** 
	 Flag indicating wether this instance is designated the stream owner.
	 When closing if this flag is true the underlying stream is closed.
	*/
	private boolean isStreamOwner = true;

	/** 
	 Flag indicating wether this instance has been closed or not.
	*/
	private boolean isClosed;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: readonly byte[] one = new byte[1];
	private final byte[] one = new byte[1];
	private boolean headerParsed;

	// string table stuff
	private static final int TBL_CLEAR = 0x100;
	private static final int TBL_FIRST = TBL_CLEAR + 1;

	private int[] tabPrefix;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] tabSuffix;
	private byte[] tabSuffix;
	private final int[] zeros = new int[256];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] stack;
	private byte[] stack;

	// various state
	private boolean blockMode;
	private int nBits;
	private int maxBits;
	private int maxMaxCode;
	private int maxCode;
	private int bitMask;
	private int oldCode;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte finChar;
	private byte finChar;
	private int stackP;
	private int freeEnt;

	// input buffer
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private readonly byte[] data = new byte[1024 * 8];
	private final byte[] data = new byte[1024 * 8];
	private int bitPos;
	private int end;
	private int got;
	private boolean eof;
	private static final int EXTRA = 64;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}