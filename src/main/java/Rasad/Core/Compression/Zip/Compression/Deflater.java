package Rasad.Core.Compression.Zip.Compression;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;

// Deflater.cs
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
 This is the Deflater class.  The deflater class compresses input
 with the deflate algorithm described in RFC 1951.  It has several
 compression levels and three different strategies described below.

 This class is <i>not</i> thread safe.  This is inherent in the API, due
 to the split of deflate and setInput.
 
 author of the original java version : Jochen Hoenicke
*/
public class Deflater
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Deflater Documentation
	/*
	* The Deflater can do the following state transitions:
	*
	* (1) -> INIT_STATE   ----> INIT_FINISHING_STATE ---.
	*        /  | (2)      (5)                          |
	*       /   v          (5)                          |
	*   (3)| SETDICT_STATE ---> SETDICT_FINISHING_STATE |(3)
	*       \   | (3)                 |        ,--------'
	*        |  |                     | (3)   /
	*        v  v          (5)        v      v
	* (1) -> BUSY_STATE   ----> FINISHING_STATE
	*                                | (6)
	*                                v
	*                           FINISHED_STATE
	*    \_____________________________________/
	*                    | (7)
	*                    v
	*               CLOSED_STATE
	*
	* (1) If we should produce a header we start in INIT_STATE, otherwise
	*     we start in BUSY_STATE.
	* (2) A dictionary may be set only when we are in INIT_STATE, then
	*     we change the state as indicated.
	* (3) Whether a dictionary is set or not, on the first call of deflate
	*     we change to BUSY_STATE.
	* (4) -- intentionally left blank -- :)
	* (5) FINISHING_STATE is entered, when flush() is called to indicate that
	*     there is no more INPUT.  There are also states indicating, that
	*     the header wasn't written yet.
	* (6) FINISHED_STATE is entered, when everything has been flushed to the
	*     internal pending output buffer.
	* (7) At any time (7)
	*
	*/
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Constants
	/** 
	 The best and slowest compression level.  This tries to find very
	 long and distant string repetitions.
	*/
	public static final int BEST_COMPRESSION = 9;

	/** 
	 The worst but fastest compression level.
	*/
	public static final int BEST_SPEED = 1;

	/** 
	 The default compression level.
	*/
	public static final int DEFAULT_COMPRESSION = -1;

	/** 
	 This level won't compress at all but output uncompressed blocks.
	*/
	public static final int NO_COMPRESSION = 0;

	/** 
	 The compression method.  This is the only method supported so far.
	 There is no need to use this constant at all.
	*/
	public static final int DEFLATED = 8;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Local Constants
	private static final int IS_SETDICT = 0x01;
	private static final int IS_FLUSHING = 0x04;
	private static final int IS_FINISHING = 0x08;

	private static final int INIT_STATE = 0x00;
	private static final int SETDICT_STATE = 0x01;
	//		private static  int INIT_FINISHING_STATE    = 0x08;
	//		private static  int SETDICT_FINISHING_STATE = 0x09;
	private static final int BUSY_STATE = 0x10;
	private static final int FLUSHING_STATE = 0x14;
	private static final int FINISHING_STATE = 0x1c;
	private static final int FINISHED_STATE = 0x1e;
	private static final int CLOSED_STATE = 0x7f;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Creates a new deflater with default compression level.
	*/
	public Deflater()
	{
		this(DEFAULT_COMPRESSION, false);

	}

	/** 
	 Creates a new deflater with given compression level.
	 
	 @param level
	 the compression level, a value between NO_COMPRESSION
	 and BEST_COMPRESSION, or DEFAULT_COMPRESSION.
	 
	 @exception System.ArgumentOutOfRangeException if lvl is out of range.
	*/
	public Deflater(int level)
	{
		this(level, false);

	}

	/** 
	 Creates a new deflater with given compression level.
	 
	 @param level
	 the compression level, a value between NO_COMPRESSION
	 and BEST_COMPRESSION.
	 
	 @param noZlibHeaderOrFooter
	 true, if we should suppress the Zlib/RFC1950 header at the
	 beginning and the adler checksum at the end of the output.  This is
	 useful for the GZIP/PKZIP formats.
	 
	 @exception System.ArgumentOutOfRangeException if lvl is out of range.
	*/
	public Deflater(int level, boolean noZlibHeaderOrFooter)
	{
		if (level == DEFAULT_COMPRESSION)
		{
			level = 6;
		}
		else if (level < NO_COMPRESSION || level > BEST_COMPRESSION)
		{
			throw new IndexOutOfBoundsException("level");
		}

		pending = new DeflaterPending();
		engine = new DeflaterEngine(pending);
		this.noZlibHeaderOrFooter = noZlibHeaderOrFooter;
		SetStrategy(DeflateStrategy.Default);
		SetLevel(level);
		Reset();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Resets the deflater.  The deflater acts afterwards as if it was
	 just created with the same compression level and strategy as it
	 had before.
	*/
	public final void Reset()
	{
		state = (noZlibHeaderOrFooter ? BUSY_STATE : INIT_STATE);
		totalOut = 0;
		pending.Reset();
		engine.Reset();
	}

	/** 
	 Gets the current adler checksum of the data that was processed so far.
	*/
	public final int getAdler()
	{
		return engine.getAdler();
	}

	/** 
	 Gets the number of input bytes processed so far.
	*/
	public final long getTotalIn()
	{
		return engine.getTotalIn();
	}

	/** 
	 Gets the number of output bytes so far.
	*/
	public final long getTotalOut()
	{
		return totalOut;
	}

	/** 
	 Flushes the current input block.  Further calls to deflate() will
	 produce enough output to inflate everything in the current input
	 block.  This is not part of Sun's JDK so I have made it package
	 private.  It is used by DeflaterOutputStream to implement
	 flush().
	*/
	public final void Flush()
	{
		state |= IS_FLUSHING;
	}

	/** 
	 Finishes the deflater with the current input block.  It is an error
	 to give more input after this method was called.  This method must
	 be called to force all bytes to be flushed.
	*/
	public final void Finish()
	{
		state |= (IS_FLUSHING | IS_FINISHING);
	}

	/** 
	 Returns true if the stream was finished and no more output bytes
	 are available.
	*/
	public final boolean getIsFinished()
	{
		return (state == FINISHED_STATE) && pending.getIsFlushed();
	}

	/** 
	 Returns true, if the input buffer is empty.
	 You should then call setInput(). 
	 NOTE: This method can also return true when the stream
	 was finished.
	*/
	public final boolean getIsNeedingInput()
	{
		return engine.NeedsInput();
	}

	/** 
	 Sets the data which should be compressed next.  This should be only
	 called when needsInput indicates that more input is needed.
	 If you call setInput when needsInput() returns false, the
	 previous input that is still pending will be thrown away.
	 The given byte array should not be changed, before needsInput() returns
	 true again.
	 This call is equivalent to <code>setInput(input, 0, input.length)</code>.
	 
	 @param input
	 the buffer containing the input data.
	 
	 @exception System.InvalidOperationException
	 if the buffer was finished() or ended().
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetInput(byte[] input)
	public final void SetInput(byte[] input)
	{
		SetInput(input, 0, input.length);
	}

	/** 
	 Sets the data which should be compressed next.  This should be
	 only called when needsInput indicates that more input is needed.
	 The given byte array should not be changed, before needsInput() returns
	 true again.
	 
	 @param input
	 the buffer containing the input data.
	 
	 @param offset
	 the start of the data.
	 
	 @param count
	 the number of data bytes of input.
	 
	 @exception System.InvalidOperationException
	 if the buffer was Finish()ed or if previous input is still pending.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetInput(byte[] input, int offset, int count)
	public final void SetInput(byte[] input, int offset, int count)
	{
		if ((state & IS_FINISHING) != 0)
		{
			throw new IllegalStateException("Finish() already called");
		}
		engine.SetInput(input, offset, count);
	}

	/** 
	 Sets the compression level.  There is no guarantee of the exact
	 position of the change, but if you call this when needsInput is
	 true the change of compression level will occur somewhere near
	 before the end of the so far given input.
	 
	 @param level
	 the new compression level.
	 
	*/
	public final void SetLevel(int level)
	{
		if (level == DEFAULT_COMPRESSION)
		{
			level = 6;
		}
		else if (level < NO_COMPRESSION || level > BEST_COMPRESSION)
		{
			throw new IndexOutOfBoundsException("level");
		}

		if (this.level != level)
		{
			this.level = level;
			engine.SetLevel(level);
		}
	}

	/** 
	 Get current compression level
	 
	 @return Returns the current compression level
	*/
	public final int GetLevel()
	{
		return level;
	}

	/** 
	 Sets the compression strategy. Strategy is one of
	 DEFAULT_STRATEGY, HUFFMAN_ONLY and FILTERED.  For the exact
	 position where the strategy is changed, the same as for
	 SetLevel() applies.
	 
	 @param strategy
	 The new compression strategy.
	 
	*/
	public final void SetStrategy(DeflateStrategy strategy)
	{
		engine.setStrategy(strategy);
	}

	/** 
	 Deflates the current input block with to the given array.
	 
	 @param output
	 The buffer where compressed data is stored
	 
	 @return 
	 The number of compressed bytes added to the output, or 0 if either
	 IsNeedingInput() or IsFinished returns true or length is zero.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int Deflate(byte[] output)
	public final int Deflate(byte[] output)
	{
		return Deflate(output, 0, output.length);
	}

	/** 
	 Deflates the current input block to the given array.
	 
	 @param output
	 Buffer to store the compressed data.
	 
	 @param offset
	 Offset into the output array.
	 
	 @param length
	 The maximum number of bytes that may be stored.
	 
	 @return 
	 The number of compressed bytes added to the output, or 0 if either
	 needsInput() or finished() returns true or length is zero.
	 
	 @exception System.InvalidOperationException
	 If Finish() was previously called.
	 
	 @exception System.ArgumentOutOfRangeException
	 If offset or length don't match the array length.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public int Deflate(byte[] output, int offset, int length)
	public final int Deflate(byte[] output, int offset, int length)
	{
		int origLength = length;

		if (state == CLOSED_STATE)
		{
			throw new IllegalStateException("Deflater closed");
		}

		if (state < BUSY_STATE)
		{
			// output header
			int header = (DEFLATED + ((DeflaterConstants.MAX_WBITS - 8) << 4)) << 8;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			int level_flags = (level - 1) >> 1;
			if (level_flags < 0 || level_flags > 3)
			{
				level_flags = 3;
			}
			header |= level_flags << 6;
			if ((state & IS_SETDICT) != 0)
			{
				// Dictionary was set
				header |= DeflaterConstants.PRESET_DICT;
			}
			header += 31 - (header % 31);

			pending.WriteShortMSB(header);
			if ((state & IS_SETDICT) != 0)
			{
				int chksum = engine.getAdler();
				engine.ResetAdler();
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
				pending.WriteShortMSB(chksum >> 16);
				pending.WriteShortMSB(chksum & 0xffff);
			}

			state = BUSY_STATE | (state & (IS_FLUSHING | IS_FINISHING));
		}

		for (;;)
		{
			int count = pending.Flush(output, offset, length);
			offset += count;
			totalOut += count;
			length -= count;

			if (length == 0 || state == FINISHED_STATE)
			{
				break;
			}

			if (!engine.Deflate((state & IS_FLUSHING) != 0, (state & IS_FINISHING) != 0))
			{
				if (state == BUSY_STATE)
				{
					// We need more input now
					return origLength - length;
				}
				else if (state == FLUSHING_STATE)
				{
					if (level != NO_COMPRESSION)
					{
						/* We have to supply some lookahead.  8 bit lookahead
						 * is needed by the zlib inflater, and we must fill
						 * the next byte, so that all bits are flushed.
						 */
						int neededbits = 8 + ((-pending.getBitCount()) & 7);
						while (neededbits > 0)
						{
							/* write a static tree block consisting solely of
							 * an EOF:
							 */
							pending.WriteBits(2, 10);
							neededbits -= 10;
						}
					}
					state = BUSY_STATE;
				}
				else if (state == FINISHING_STATE)
				{
					pending.AlignToByte();

					// Compressed data is complete.  Write footer information if required.
					if (!noZlibHeaderOrFooter)
					{
						int adler = engine.getAdler();
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
						pending.WriteShortMSB(adler >> 16);
						pending.WriteShortMSB(adler & 0xffff);
					}
					state = FINISHED_STATE;
				}
			}
		}
		return origLength - length;
	}

	/** 
	 Sets the dictionary which should be used in the deflate process.
	 This call is equivalent to <code>setDictionary(dict, 0, dict.Length)</code>.
	 
	 @param dictionary
	 the dictionary.
	 
	 @exception System.InvalidOperationException
	 if SetInput () or Deflate () were already called or another dictionary was already set.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetDictionary(byte[] dictionary)
	public final void SetDictionary(byte[] dictionary)
	{
		SetDictionary(dictionary, 0, dictionary.length);
	}

	/** 
	 Sets the dictionary which should be used in the deflate process.
	 The dictionary is a byte array containing strings that are
	 likely to occur in the data which should be compressed.  The
	 dictionary is not stored in the compressed output, only a
	 checksum.  To decompress the output you need to supply the same
	 dictionary again.
	 
	 @param dictionary
	 The dictionary data
	 
	 @param index
	 The index where dictionary information commences.
	 
	 @param count
	 The number of bytes in the dictionary.
	 
	 @exception System.InvalidOperationException
	 If SetInput () or Deflate() were already called or another dictionary was already set.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetDictionary(byte[] dictionary, int index, int count)
	public final void SetDictionary(byte[] dictionary, int index, int count)
	{
		if (state != INIT_STATE)
		{
			throw new IllegalStateException();
		}

		state = SETDICT_STATE;
		engine.SetDictionary(dictionary, index, count);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 Compression level.
	*/
	private int level;

	/** 
	 If true no Zlib/RFC1950 headers or footers are generated
	*/
	private boolean noZlibHeaderOrFooter;

	/** 
	 The current state.
	*/
	private int state;

	/** 
	 The total bytes of output written.
	*/
	private long totalOut;

	/** 
	 The pending output.
	*/
	private DeflaterPending pending;

	/** 
	 The deflater engine.
	*/
	private DeflaterEngine engine;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}