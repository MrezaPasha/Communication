package Rasad.Core.Compression.Zip.Compression;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;

// Inflater.cs
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
 Inflater is used to decompress data that has been compressed according
 to the "deflate" standard described in rfc1951.
 
 By default Zlib (rfc1950) headers and footers are expected in the input.
 You can use constructor <code> public Inflater(bool noHeader)</code> passing true
 if there is no Zlib header information

 The usage is as following.  First you have to set some input with
 <code>SetInput()</code>, then Inflate() it.  If inflate doesn't
 inflate any bytes there may be three reasons:
 <ul>
 <li>IsNeedingInput() returns true because the input buffer is empty.
 You have to provide more input with <code>SetInput()</code>.
 NOTE: IsNeedingInput() also returns true when, the stream is finished.
 </li>
 <li>IsNeedingDictionary() returns true, you have to provide a preset
	dictionary with <code>SetDictionary()</code>.</li>
 <li>IsFinished returns true, the inflater has finished.</li>
 </ul>
 Once the first output byte is produced, a dictionary will not be
 needed at a later stage.

 author of the original java version : John Leuner, Jochen Hoenicke
*/
public class Inflater
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constants/Readonly
	/** 
	 Copy lengths for literal codes 257..285
	*/
	private static final int[] CPLENS = {3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258};

	/** 
	 Extra bits for literal codes 257..285
	*/
	private static final int[] CPLEXT = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0};

	/** 
	 Copy offsets for distance codes 0..29
	*/
	private static final int[] CPDIST = {1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385, 24577};

	/** 
	 Extra bits for distance codes
	*/
	private static final int[] CPDEXT = {0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13};

	/** 
	 These are the possible states for an inflater
	*/
	private static final int DECODE_HEADER = 0;
	private static final int DECODE_DICT = 1;
	private static final int DECODE_BLOCKS = 2;
	private static final int DECODE_STORED_LEN1 = 3;
	private static final int DECODE_STORED_LEN2 = 4;
	private static final int DECODE_STORED = 5;
	private static final int DECODE_DYN_HEADER = 6;
	private static final int DECODE_HUFFMAN = 7;
	private static final int DECODE_HUFFMAN_LENBITS = 8;
	private static final int DECODE_HUFFMAN_DIST = 9;
	private static final int DECODE_HUFFMAN_DISTBITS = 10;
	private static final int DECODE_CHKSUM = 11;
	private static final int FINISHED = 12;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 This variable contains the current state.
	*/
	private int mode;

	/** 
	 The adler checksum of the dictionary or of the decompressed
	 stream, as it is written in the header resp. footer of the
	 compressed stream. 
	 Only valid if mode is DECODE_DICT or DECODE_CHKSUM.
	*/
	private int readAdler;

	/** 
	 The number of bits needed to complete the current state.  This
	 is valid, if mode is DECODE_DICT, DECODE_CHKSUM,
	 DECODE_HUFFMAN_LENBITS or DECODE_HUFFMAN_DISTBITS.
	*/
	private int neededBits;
	private int repLength;
	private int repDist;
	private int uncomprLen;

	/** 
	 True, if the last block flag was set in the last block of the
	 inflated stream.  This means that the stream ends after the
	 current block.
	*/
	private boolean isLastBlock;

	/** 
	 The total number of inflated bytes.
	*/
	private long totalOut;

	/** 
	 The total number of bytes set with setInput().  This is not the
	 value returned by the TotalIn property, since this also includes the
	 unprocessed input.
	*/
	private long totalIn;

	/** 
	 This variable stores the noHeader flag that was given to the constructor.
	 True means, that the inflated stream doesn't contain a Zlib header or 
	 footer.
	*/
	private boolean noHeader;

	private StreamManipulator input;
	private OutputWindow outputWindow;
	private InflaterDynHeader dynHeader;
	private InflaterHuffmanTree litlenTree, distTree;
	private Adler32 adler;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Creates a new inflater or RFC1951 decompressor
	 RFC1950/Zlib headers and footers will be expected in the input data
	*/
	public Inflater()
	{
		this(false);
	}

	/** 
	 Creates a new inflater.
	 
	 @param noHeader
	 True if no RFC1950/Zlib header and footer fields are expected in the input data
	 
	 This is used for GZIPed/Zipped input.
	 
	 For compatibility with
	 Sun JDK you should provide one byte of input more than needed in
	 this case.
	 
	*/
	public Inflater(boolean noHeader)
	{
		this.noHeader = noHeader;
		this.adler = new Adler32();
		input = new StreamManipulator();
		outputWindow = new OutputWindow();
		mode = noHeader ? DECODE_BLOCKS : DECODE_HEADER;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Resets the inflater so that a new stream can be decompressed.  All
	 pending input and output will be discarded.
	*/
	public final void Reset()
	{
		mode = noHeader ? DECODE_BLOCKS : DECODE_HEADER;
		totalIn = 0;
		totalOut = 0;
		input.Reset();
		outputWindow.Reset();
		dynHeader = null;
		litlenTree = null;
		distTree = null;
		isLastBlock = false;
		adler.Reset();
	}

	/** 
	 Decodes a zlib/RFC1950 header.
	 
	 @return 
	 False if more input is needed.
	 
	 @exception SharpZipBaseException
	 The header is invalid.
	 
	*/
	private boolean DecodeHeader()
	{
		int header = input.PeekBits(16);
		if (header < 0)
		{
			return false;
		}
		input.DropBits(16);

		// The header is written in "wrong" byte order
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		header = ((header << 8) | (header >> 8)) & 0xffff;
		if (header % 31 != 0)
		{
			throw new SharpZipBaseException("Header checksum illegal");
		}

		if ((header & 0x0f00) != (Deflater.DEFLATED << 8))
		{
			throw new SharpZipBaseException("Compression Method unknown");
		}

		/* Maximum size of the backwards window in bits.
		* We currently ignore this, but we could use it to make the
		* inflater window more space efficient. On the other hand the
		* full window (15 bits) is needed most times, anyway.
		int max_wbits = ((header & 0x7000) >> 12) + 8;
		*/

		if ((header & 0x0020) == 0)
		{ // Dictionary flag?
			mode = DECODE_BLOCKS;
		}
		else
		{
			mode = DECODE_DICT;
			neededBits = 32;
		}
		return true;
	}

	/** 
	 Decodes the dictionary checksum after the deflate header.
	 
	 @return 
	 False if more input is needed.
	 
	*/
	private boolean DecodeDict()
	{
		while (neededBits > 0)
		{
			int dictByte = input.PeekBits(8);
			if (dictByte < 0)
			{
				return false;
			}
			input.DropBits(8);
			readAdler = (readAdler << 8) | dictByte;
			neededBits -= 8;
		}
		return false;
	}

	/** 
	 Decodes the huffman encoded symbols in the input stream.
	 
	 @return 
	 false if more input is needed, true if output window is
	 full or the current block ends.
	 
	 @exception SharpZipBaseException
	 if deflated stream is invalid.
	 
	*/
	private boolean DecodeHuffman()
	{
		int free = outputWindow.GetFreeSpace();
		while (free >= 258)
		{
			int symbol;
			switch (mode)
			{
				case DECODE_HUFFMAN:
					// This is the inner loop so it is optimized a bit
					while (((symbol = litlenTree.GetSymbol(input)) & ~0xff) == 0)
					{
						outputWindow.Write(symbol);
						if (--free < 258)
						{
							return true;
						}
					}

					if (symbol < 257)
					{
						if (symbol < 0)
						{
							return false;
						}
						else
						{
							// symbol == 256: end of block
							distTree = null;
							litlenTree = null;
							mode = DECODE_BLOCKS;
							return true;
						}
					}

					try
					{
						repLength = CPLENS[symbol - 257];
						neededBits = CPLEXT[symbol - 257];
					}
					catch (RuntimeException e)
					{
						throw new SharpZipBaseException("Illegal rep length code");
					}

				case DECODE_HUFFMAN_LENBITS:
					if (neededBits > 0)
					{
						mode = DECODE_HUFFMAN_LENBITS;
						int i = input.PeekBits(neededBits);
						if (i < 0)
						{
							return false;
						}
						input.DropBits(neededBits);
						repLength += i;
					}
					mode = DECODE_HUFFMAN_DIST;

				case DECODE_HUFFMAN_DIST:
					symbol = distTree.GetSymbol(input);
					if (symbol < 0)
					{
						return false;
					}

					try
					{
						repDist = CPDIST[symbol];
						neededBits = CPDEXT[symbol];
					}
					catch (RuntimeException e2)
					{
						throw new SharpZipBaseException("Illegal rep dist code");
					}


				case DECODE_HUFFMAN_DISTBITS:
					if (neededBits > 0)
					{
						mode = DECODE_HUFFMAN_DISTBITS;
						int i = input.PeekBits(neededBits);
						if (i < 0)
						{
							return false;
						}
						input.DropBits(neededBits);
						repDist += i;
					}

					outputWindow.Repeat(repLength, repDist);
					free -= repLength;
					mode = DECODE_HUFFMAN;
					break;

				default:
					throw new SharpZipBaseException("Inflater unknown mode");
			}
		}
		return true;
	}

	/** 
	 Decodes the adler checksum after the deflate stream.
	 
	 @return 
	 false if more input is needed.
	 
	 @exception SharpZipBaseException
	 If checksum doesn't match.
	 
	*/
	private boolean DecodeChksum()
	{
		while (neededBits > 0)
		{
			int chkByte = input.PeekBits(8);
			if (chkByte < 0)
			{
				return false;
			}
			input.DropBits(8);
			readAdler = (readAdler << 8) | chkByte;
			neededBits -= 8;
		}

		if ((int) adler.getValue() != readAdler)
		{
			throw new SharpZipBaseException("Adler chksum doesn't match: " + (int)adler.getValue() + " vs. " + readAdler);
		}

		mode = FINISHED;
		return false;
	}

	/** 
	 Decodes the deflated stream.
	 
	 @return 
	 false if more input is needed, or if finished.
	 
	 @exception SharpZipBaseException
	 if deflated stream is invalid.
	 
	*/
	private boolean Decode()
	{
		switch (mode)
		{
			case DECODE_HEADER:
				return DecodeHeader();

			case DECODE_DICT:
				return DecodeDict();

			case DECODE_CHKSUM:
				return DecodeChksum();

			case DECODE_BLOCKS:
				if (isLastBlock)
				{
					if (noHeader)
					{
						mode = FINISHED;
						return false;
					}
					else
					{
						input.SkipToByteBoundary();
						neededBits = 32;
						mode = DECODE_CHKSUM;
						return true;
					}
				}

				int type = input.PeekBits(3);
				if (type < 0)
				{
					return false;
				}
				input.DropBits(3);

				if ((type & 1) != 0)
				{
					isLastBlock = true;
				}
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
				switch (type >> 1)
				{
					case DeflaterConstants.STORED_BLOCK:
						input.SkipToByteBoundary();
						mode = DECODE_STORED_LEN1;
						break;
					case DeflaterConstants.STATIC_TREES:
						litlenTree = InflaterHuffmanTree.defLitLenTree;
						distTree = InflaterHuffmanTree.defDistTree;
						mode = DECODE_HUFFMAN;
						break;
					case DeflaterConstants.DYN_TREES:
						dynHeader = new InflaterDynHeader();
						mode = DECODE_DYN_HEADER;
						break;
					default:
						throw new SharpZipBaseException("Unknown block type " + type);
				}
				return true;

			case DECODE_STORED_LEN1:
			{
				if ((uncomprLen = input.PeekBits(16)) < 0)
				{
					return false;
				}
				input.DropBits(16);
				mode = DECODE_STORED_LEN2;
			}

			case DECODE_STORED_LEN2:
			{
				int nlen = input.PeekBits(16);
				if (nlen < 0)
				{
					return false;
				}
				input.DropBits(16);
				if (nlen != (uncomprLen ^ 0xffff))
				{
					throw new SharpZipBaseException("broken uncompressed block");
				}
				mode = DECODE_STORED;
			}

			case DECODE_STORED:
			{
				int more = outputWindow.CopyStored(input, uncomprLen);
				uncomprLen -= more;
				if (uncomprLen == 0)
				{
					mode = DECODE_BLOCKS;
					return true;
				}
				return !input.getIsNeedingInput();
			}

			case DECODE_DYN_HEADER:
				if (!dynHeader.Decode(input))
				{
					return false;
				}

				litlenTree = dynHeader.BuildLitLenTree();
				distTree = dynHeader.BuildDistTree();
				mode = DECODE_HUFFMAN;

			case DECODE_HUFFMAN:
			case DECODE_HUFFMAN_LENBITS:
			case DECODE_HUFFMAN_DIST:
			case DECODE_HUFFMAN_DISTBITS:
				return DecodeHuffman();

			case FINISHED:
				return false;

			default:
				throw new SharpZipBaseException("Inflater.Decode unknown mode");
		}
	}

	/** 
	 Sets the preset dictionary.  This should only be called, if
	 needsDictionary() returns true and it should set the same
	 dictionary, that was used for deflating.  The getAdler()
	 function returns the checksum of the dictionary needed.
	 
	 @param buffer
	 The dictionary.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetDictionary(byte[] buffer)
	public final void SetDictionary(byte[] buffer)
	{
		SetDictionary(buffer, 0, buffer.length);
	}

	/** 
	 Sets the preset dictionary.  This should only be called, if
	 needsDictionary() returns true and it should set the same
	 dictionary, that was used for deflating.  The getAdler()
	 function returns the checksum of the dictionary needed.
	 
	 @param buffer
	 The dictionary.
	 
	 @param index
	 The index into buffer where the dictionary starts.
	 
	 @param count
	 The number of bytes in the dictionary.
	 
	 @exception System.InvalidOperationException
	 No dictionary is needed.
	 
	 @exception SharpZipBaseException
	 The adler checksum for the buffer is invalid
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetDictionary(byte[] buffer, int index, int count)
	public final void SetDictionary(byte[] buffer, int index, int count)
	{
		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		if (index < 0)
		{
			throw new IndexOutOfBoundsException("index");
		}

		if (count < 0)
		{
			throw new IndexOutOfBoundsException("count");
		}

		if (!getIsNeedingDictionary())
		{
			throw new IllegalStateException("Dictionary is not needed");
		}

		adler.Update(buffer, index, count);

		if ((int)adler.getValue() != readAdler)
		{
			throw new SharpZipBaseException("Wrong adler checksum");
		}
		adler.Reset();
		outputWindow.CopyDict(buffer, index, count);
		mode = DECODE_BLOCKS;
	}

	/** 
	 Sets the input.  This should only be called, if needsInput()
	 returns true.
	 
	 @param buffer
	 the input.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetInput(byte[] buffer)
	public final void SetInput(byte[] buffer)
	{
		SetInput(buffer, 0, buffer.length);
	}

	/** 
	 Sets the input.  This should only be called, if needsInput()
	 returns true.
	 
	 @param buffer
	 The source of input data
	 
	 @param index
	 The index into buffer where the input starts.
	 
	 @param count
	 The number of bytes of input to use.
	 
	 @exception System.InvalidOperationException
	 No input is needed.
	 
	 @exception System.ArgumentOutOfRangeException
	 The index and/or count are wrong.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetInput(byte[] buffer, int index, int count)
	public final void SetInput(byte[] buffer, int index, int count)
	{
		input.SetInput(buffer, index, count);
		totalIn += (long)count;
	}

	/** 
	 Inflates the compressed stream to the output buffer.  If this
	 returns 0, you should check, whether IsNeedingDictionary(),
	 IsNeedingInput() or IsFinished() returns true, to determine why no
	 further output is produced.
	 
	 @param buffer
	 the output buffer.
	 
	 @return 
	 The number of bytes written to the buffer, 0 if no further
	 output can be produced.
	 
	 @exception System.ArgumentOutOfRangeException
	 if buffer has length 0.
	 
	 @exception System.FormatException
	 if deflated stream is invalid.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int Inflate(byte[] buffer)
	public final int Inflate(byte[] buffer)
	{
		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		return Inflate(buffer, 0, buffer.length);
	}

	/** 
	 Inflates the compressed stream to the output buffer.  If this
	 returns 0, you should check, whether needsDictionary(),
	 needsInput() or finished() returns true, to determine why no
	 further output is produced.
	 
	 @param buffer
	 the output buffer.
	 
	 @param offset
	 the offset in buffer where storing starts.
	 
	 @param count
	 the maximum number of bytes to output.
	 
	 @return 
	 the number of bytes written to the buffer, 0 if no further output can be produced.
	 
	 @exception System.ArgumentOutOfRangeException
	 if count is less than 0.
	 
	 @exception System.ArgumentOutOfRangeException
	 if the index and / or count are wrong.
	 
	 @exception System.FormatException
	 if deflated stream is invalid.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int Inflate(byte[] buffer, int offset, int count)
	public final int Inflate(byte[] buffer, int offset, int count)
	{
		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		if (count < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("count");
//#else
			throw new IndexOutOfBoundsException("count", "count cannot be negative");
//#endif
		}

		if (offset < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("offset");
//#else
			throw new IndexOutOfBoundsException("offset", "offset cannot be negative");
//#endif
		}

		if (offset + count > buffer.length)
		{
			throw new IllegalArgumentException("count exceeds buffer bounds");
		}

		// Special case: count may be zero
		if (count == 0)
		{
			if (!getIsFinished())
			{ // -jr- 08-Nov-2003 INFLATE_BUG fix..
				Decode();
			}
			return 0;
		}

		int bytesCopied = 0;

		do
		{
			if (mode != DECODE_CHKSUM)
			{
				/* Don't give away any output, if we are waiting for the
				* checksum in the input stream.
				*
				* With this trick we have always:
				*   IsNeedingInput() and not IsFinished()
				*   implies more output can be produced.
				*/
				int more = outputWindow.CopyOutput(buffer, offset, count);
				if (more > 0)
				{
					adler.Update(buffer, offset, more);
					offset += more;
					bytesCopied += more;
					totalOut += (long)more;
					count -= more;
					if (count == 0)
					{
						return bytesCopied;
					}
				}
			}
		} while (Decode() || ((outputWindow.GetAvailable() > 0) && (mode != DECODE_CHKSUM)));
		return bytesCopied;
	}

	/** 
	 Returns true, if the input buffer is empty.
	 You should then call setInput(). 
	 NOTE: This method also returns true when the stream is finished.
	*/
	public final boolean getIsNeedingInput()
	{
		return input.getIsNeedingInput();
	}

	/** 
	 Returns true, if a preset dictionary is needed to inflate the input.
	*/
	public final boolean getIsNeedingDictionary()
	{
		return mode == DECODE_DICT && neededBits == 0;
	}

	/** 
	 Returns true, if the inflater has finished.  This means, that no
	 input is needed and no output can be produced.
	*/
	public final boolean getIsFinished()
	{
		return mode == FINISHED && outputWindow.GetAvailable() == 0;
	}

	/** 
	 Gets the adler checksum.  This is either the checksum of all
	 uncompressed bytes returned by inflate(), or if needsDictionary()
	 returns true (and thus no output was yet produced) this is the
	 adler checksum of the expected dictionary.
	 
	 @return 
	 the adler checksum.
	 
	*/
	public final int getAdler()
	{
		return getIsNeedingDictionary() ? readAdler : (int) adler.getValue();
	}

	/** 
	 Gets the total number of output bytes returned by Inflate().
	 
	 @return 
	 the total number of output bytes.
	 
	*/
	public final long getTotalOut()
	{
		return totalOut;
	}

	/** 
	 Gets the total number of processed compressed input bytes.
	 
	 @return 
	 The total number of bytes of processed input bytes.
	 
	*/
	public final long getTotalIn()
	{
		return totalIn - (long)getRemainingInput();
	}

	/** 
	 Gets the number of unprocessed input bytes.  Useful, if the end of the
	 stream is reached and you want to further process the bytes after
	 the deflate stream.
	 
	 @return 
	 The number of bytes of the input which have not been processed.
	 
	*/
		// TODO: This should be a long?
	public final int getRemainingInput()
	{
		return input.getAvailableBytes();
	}
}