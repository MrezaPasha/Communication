package Rasad.Core.Compression.Zip.Compression;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;

// DEFLATE ALGORITHM:
// 
// The uncompressed stream is inserted into the window array.  When
// the window array is full the first half is thrown away and the
// second half is copied to the beginning.
//
// The head array is a hash table.  Three characters build a hash value
// and they the value points to the corresponding index in window of 
// the last string with this hash.  The prev array implements a
// linked list of matches with the same hash: prev[index & WMASK] points
// to the previous index with the same hash.
// 


/** 
 Low level compression engine for deflate algorithm which uses a 32K sliding window
 with secondary compression from Huffman/Shannon-Fano codes.
*/
public class DeflaterEngine extends DeflaterConstants
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constants
	private static final int TooFar = 4096;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Construct instance with pending buffer
	 
	 @param pending
	 Pending buffer to use
	 >
	*/
	public DeflaterEngine(DeflaterPending pending)
	{
		this.pending = pending;
		huffman = new DeflaterHuffman(pending);
		adler = new Adler32();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: window = new byte[2 * WSIZE];
		window = new byte[2 * WSIZE];
		head = new short[HASH_SIZE];
		prev = new short[WSIZE];

		// We start at index 1, to avoid an implementation deficiency, that
		// we cannot build a repeat pattern at index 0.
		blockStart = strstart = 1;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Deflate drives actual compression of data
	 
	 @param flush True to flush input buffers
	 @param finish Finish deflation with the current input.
	 @return Returns true if progress has been made.
	*/
	public final boolean Deflate(boolean flush, boolean finish)
	{
		boolean progress;
		do
		{
			FillWindow();
			boolean canFlush = flush && (inputOff == inputEnd);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
			if (DeflaterConstants.DEBUGGING)
			{
				System.out.println("window: [" + blockStart + "," + strstart + "," + lookahead + "], " + compressionFunction + "," + canFlush);
			}
//#endif
			switch (compressionFunction)
			{
				case DEFLATE_STORED:
					progress = DeflateStored(canFlush, finish);
					break;
				case DEFLATE_FAST:
					progress = DeflateFast(canFlush, finish);
					break;
				case DEFLATE_SLOW:
					progress = DeflateSlow(canFlush, finish);
					break;
				default:
					throw new IllegalStateException("unknown compressionFunction");
			}
		} while (pending.getIsFlushed() && progress); // repeat while we have no pending output and progress was made
		return progress;
	}

	/** 
	 Sets input data to be deflated.  Should only be called when <code>NeedsInput()</code>
	 returns true
	 
	 @param buffer The buffer containing input data.
	 @param offset The offset of the first byte of data.
	 @param count The number of bytes of data to use as input.
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
			throw new IndexOutOfBoundsException("offset");
		}

		if (count < 0)
		{
			throw new IndexOutOfBoundsException("count");
		}

		if (inputOff < inputEnd)
		{
			throw new IllegalStateException("Old input was not completely processed");
		}

		int end = offset + count;

		/* We want to throw an ArrayIndexOutOfBoundsException early.  The
		* check is very tricky: it also handles integer wrap around.
		*/
		if ((offset > end) || (end > buffer.length))
		{
			throw new IndexOutOfBoundsException("count");
		}

		inputBuf = buffer;
		inputOff = offset;
		inputEnd = end;
	}

	/** 
	 Determines if more <see cref="SetInput">input</see> is needed.
	 		
	 @return Return true if input is needed via <see cref="SetInput">SetInput</see>
	*/
	public final boolean NeedsInput()
	{
		return (inputEnd == inputOff);
	}

	/** 
	 Set compression dictionary
	 
	 @param buffer The buffer containing the dictionary data
	 @param offset The offset in the buffer for the first byte of data
	 @param length The length of the dictionary data.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetDictionary(byte[] buffer, int offset, int length)
	public final void SetDictionary(byte[] buffer, int offset, int length)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
		if (DeflaterConstants.DEBUGGING && (strstart != 1))
		{
			throw new IllegalStateException("strstart not 1");
		}
//#endif
		adler.Update(buffer, offset, length);
		if (length < MIN_MATCH)
		{
			return;
		}

		if (length > MAX_DIST)
		{
			offset += length - MAX_DIST;
			length = MAX_DIST;
		}

		System.arraycopy(buffer, offset, window, strstart, length);

		UpdateHash();
		--length;
		while (--length > 0)
		{
			InsertString();
			strstart++;
		}
		strstart += 2;
		blockStart = strstart;
	}

	/** 
	 Reset internal state
	*/
	public final void Reset()
	{
		huffman.Reset();
		adler.Reset();
		blockStart = strstart = 1;
		lookahead = 0;
		totalIn = 0;
		prevAvailable = false;
		matchLen = MIN_MATCH - 1;

		for (int i = 0; i < HASH_SIZE; i++)
		{
			head[i] = 0;
		}

		for (int i = 0; i < WSIZE; i++)
		{
			prev[i] = 0;
		}
	}

	/** 
	 Reset Adler checksum
	*/
	public final void ResetAdler()
	{
		adler.Reset();
	}

	/** 
	 Get current value of Adler checksum
	*/
	public final int getAdler()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: return unchecked((int)adler.Value);
		return (int)adler.getValue();
	}

	/** 
	 Total data processed
	*/
	public final long getTotalIn()
	{
		return totalIn;
	}

	/** 
	 Get/set the <see cref="DeflateStrategy">deflate strategy</see>
	*/
	public final DeflateStrategy getStrategy()
	{
		return strategy;
	}
	public final void setStrategy(DeflateStrategy value)
	{
		strategy = value;
	}

	/** 
	 Set the deflate level (0-9)
	 
	 @param level The value to set the level to.
	*/
	public final void SetLevel(int level)
	{
		if ((level < 0) || (level > 9))
		{
			throw new IndexOutOfBoundsException("level");
		}

		goodLength = DeflaterConstants.GOOD_LENGTH[level];
		max_lazy = DeflaterConstants.MAX_LAZY[level];
		niceLength = DeflaterConstants.NICE_LENGTH[level];
		max_chain = DeflaterConstants.MAX_CHAIN[level];

		if (DeflaterConstants.COMPR_FUNC[level] != compressionFunction)
		{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
			if (DeflaterConstants.DEBUGGING)
			{
			   System.out.println("Change from " + compressionFunction + " to " + DeflaterConstants.COMPR_FUNC[level]);
			}
//#endif
			switch (compressionFunction)
			{
				case DEFLATE_STORED:
					if (strstart > blockStart)
					{
						huffman.FlushStoredBlock(window, blockStart, strstart - blockStart, false);
						blockStart = strstart;
					}
					UpdateHash();
					break;

				case DEFLATE_FAST:
					if (strstart > blockStart)
					{
						huffman.FlushBlock(window, blockStart, strstart - blockStart, false);
						blockStart = strstart;
					}
					break;

				case DEFLATE_SLOW:
					if (prevAvailable)
					{
						huffman.TallyLit(window[strstart - 1] & 0xff);
					}
					if (strstart > blockStart)
					{
						huffman.FlushBlock(window, blockStart, strstart - blockStart, false);
						blockStart = strstart;
					}
					prevAvailable = false;
					matchLen = MIN_MATCH - 1;
					break;
			}
			compressionFunction = COMPR_FUNC[level];
		}
	}

	/** 
	 Fill the window
	*/
	public final void FillWindow()
	{
		/* If the window is almost full and there is insufficient lookahead,
		 * move the upper half to the lower one to make room in the upper half.
		 */
		if (strstart >= WSIZE + MAX_DIST)
		{
			SlideWindow();
		}

		/* If there is not enough lookahead, but still some input left,
		 * read in the input
		 */
		while (lookahead < DeflaterConstants.MIN_LOOKAHEAD && inputOff < inputEnd)
		{
			int more = 2 * WSIZE - lookahead - strstart;

			if (more > inputEnd - inputOff)
			{
				more = inputEnd - inputOff;
			}

			System.arraycopy(inputBuf, inputOff, window, strstart + lookahead, more);
			adler.Update(inputBuf, inputOff, more);

			inputOff += more;
			totalIn += more;
			lookahead += more;
		}

		if (lookahead >= MIN_MATCH)
		{
			UpdateHash();
		}
	}

	private void UpdateHash()
	{
/*
			if (DEBUGGING) {
				Console.WriteLine("updateHash: "+strstart);
			}
*/
		ins_h = (window[strstart] << HASH_SHIFT) ^ window[strstart + 1];
	}

	/** 
	 Inserts the current string in the head hash and returns the previous
	 value for this hash.
	 
	 @return The previous hash value
	*/
	private int InsertString()
	{
		short match;
		int hash = ((ins_h << HASH_SHIFT) ^ window[strstart + (MIN_MATCH - 1)]) & HASH_MASK;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
		if (DeflaterConstants.DEBUGGING)
		{
			if (hash != (((window[strstart] << (2 * HASH_SHIFT)) ^ (window[strstart + 1] << HASH_SHIFT) ^ (window[strstart + 2])) & HASH_MASK))
			{
					throw new SharpZipBaseException("hash inconsistent: " + hash + "/" + window[strstart] + "," + window[strstart + 1] + "," + window[strstart + 2] + "," + HASH_SHIFT);
			}
		}
//#endif
		prev[strstart & WMASK] = match = head[hash];
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: head[hash] = unchecked((short)strstart);
		head[hash] = (short)strstart;
		ins_h = hash;
		return match & 0xffff;
	}

	private void SlideWindow()
	{
		System.arraycopy(window, WSIZE, window, 0, WSIZE);
		matchStart -= WSIZE;
		strstart -= WSIZE;
		blockStart -= WSIZE;

		// Slide the hash table (could be avoided with 32 bit values
		// at the expense of memory usage).
		for (int i = 0; i < HASH_SIZE; ++i)
		{
			int m = head[i] & 0xffff;
			head[i] = (short)(m >= WSIZE ? (m - WSIZE) : 0);
		}

		// Slide the prev table.
		for (int i = 0; i < WSIZE; i++)
		{
			int m = prev[i] & 0xffff;
			prev[i] = (short)(m >= WSIZE ? (m - WSIZE) : 0);
		}
	}

	/** 
	 Find the best (longest) string in the window matching the 
	 string starting at strstart.
	
	 Preconditions:
	 <code>
	 strstart + MAX_MATCH &lt;= window.length.</code>
	 
	 @param curMatch
	 @return True if a match greater than the minimum length is found
	*/
	private boolean FindLongestMatch(int curMatch)
	{
		int chainLength = this.max_chain;
		int niceLength = this.niceLength;
		short[] prev = this.prev;
		int scan = this.strstart;
		int match;
		int best_end = this.strstart + matchLen;
		int best_len = Math.max(matchLen, MIN_MATCH - 1);

		int limit = Math.max(strstart - MAX_DIST, 0);

		int strend = strstart + MAX_MATCH - 1;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte scan_end1 = window[best_end - 1];
		byte scan_end1 = window[best_end - 1];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte scan_end = window[best_end];
		byte scan_end = window[best_end];

		// Do not waste too much time if we already have a good match:
		if (best_len >= this.goodLength)
		{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			chainLength >>= 2;
		}

		/* Do not look for matches beyond the end of the input. This is necessary
		* to make deflate deterministic.
		*/
		if (niceLength > lookahead)
		{
			niceLength = lookahead;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation

		if (DeflaterConstants.DEBUGGING && (strstart > 2 * WSIZE - MIN_LOOKAHEAD))
		{
			throw new IllegalStateException("need lookahead");
		}
//#endif

		do
		{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation

			if (DeflaterConstants.DEBUGGING && (curMatch >= strstart))
			{
				throw new IllegalStateException("no future");
			}
//#endif
			if (window[curMatch + best_len] != scan_end || window[curMatch + best_len - 1] != scan_end1 || window[curMatch] != window[scan] || window[curMatch + 1] != window[scan + 1])
			{
				continue;
			}

			match = curMatch + 2;
			scan += 2;

			/* We check for insufficient lookahead only every 8th comparison;
			* the 256th check will be made at strstart + 258.
			*/
			while (window[++scan] == window[++match] && window[++scan] == window[++match] && window[++scan] == window[++match] && window[++scan] == window[++match] && window[++scan] == window[++match] && window[++scan] == window[++match] && window[++scan] == window[++match] && window[++scan] == window[++match] && (scan < strend))
			{
				// Do nothing
			}

			if (scan > best_end)
			{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
				if (DeflaterConstants.DEBUGGING && (ins_h == 0))
				{
					System.err.println("Found match: " + curMatch + "-" + (scan - strstart));
				}
//#endif
				matchStart = curMatch;
				best_end = scan;
				best_len = scan - strstart;

				if (best_len >= niceLength)
				{
					break;
				}

				scan_end1 = window[best_end - 1];
				scan_end = window[best_end];
			}
			scan = strstart;
		} while ((curMatch = (prev[curMatch & WMASK] & 0xffff)) > limit && --chainLength != 0);

		matchLen = Math.min(best_len, lookahead);
		return matchLen >= MIN_MATCH;
	}

	private boolean DeflateStored(boolean flush, boolean finish)
	{
		if (!flush && (lookahead == 0))
		{
			return false;
		}

		strstart += lookahead;
		lookahead = 0;

		int storedLength = strstart - blockStart;

		if ((storedLength >= DeflaterConstants.MAX_BLOCK_SIZE) || (blockStart < WSIZE && storedLength >= MAX_DIST) || flush)
		{
			boolean lastBlock = finish;
			if (storedLength > DeflaterConstants.MAX_BLOCK_SIZE)
			{
				storedLength = DeflaterConstants.MAX_BLOCK_SIZE;
				lastBlock = false;
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
			if (DeflaterConstants.DEBUGGING)
			{
			   System.out.println("storedBlock[" + storedLength + "," + lastBlock + "]");
			}
//#endif

			huffman.FlushStoredBlock(window, blockStart, storedLength, lastBlock);
			blockStart += storedLength;
			return !lastBlock;
		}
		return true;
	}

	private boolean DeflateFast(boolean flush, boolean finish)
	{
		if (lookahead < MIN_LOOKAHEAD && !flush)
		{
			return false;
		}

		while (lookahead >= MIN_LOOKAHEAD || flush)
		{
			if (lookahead == 0)
			{
				// We are flushing everything
				huffman.FlushBlock(window, blockStart, strstart - blockStart, finish);
				blockStart = strstart;
				return false;
			}

			if (strstart > 2 * WSIZE - MIN_LOOKAHEAD)
			{
				/* slide window, as FindLongestMatch needs this.
				 * This should only happen when flushing and the window
				 * is almost full.
				 */
				SlideWindow();
			}

			int hashHead;
			if (lookahead >= MIN_MATCH && (hashHead = InsertString()) != 0 && strategy != DeflateStrategy.HuffmanOnly && strstart - hashHead <= MAX_DIST && FindLongestMatch(hashHead))
			{
				// longestMatch sets matchStart and matchLen
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
				if (DeflaterConstants.DEBUGGING)
				{
					for (int i = 0 ; i < matchLen; i++)
					{
						if (window[strstart + i] != window[matchStart + i])
						{
							throw new SharpZipBaseException("Match failure");
						}
					}
				}
//#endif

				boolean full = huffman.TallyDist(strstart - matchStart, matchLen);

				lookahead -= matchLen;
				if (matchLen <= max_lazy && lookahead >= MIN_MATCH)
				{
					while (--matchLen > 0)
					{
						++strstart;
						InsertString();
					}
					++strstart;
				}
				else
				{
					strstart += matchLen;
					if (lookahead >= MIN_MATCH - 1)
					{
						UpdateHash();
					}
				}
				matchLen = MIN_MATCH - 1;
				if (!full)
				{
					continue;
				}
			}
			else
			{
				// No match found
				huffman.TallyLit(window[strstart] & 0xff);
				++strstart;
				--lookahead;
			}

			if (huffman.IsFull())
			{
				boolean lastBlock = finish && (lookahead == 0);
				huffman.FlushBlock(window, blockStart, strstart - blockStart, lastBlock);
				blockStart = strstart;
				return !lastBlock;
			}
		}
		return true;
	}

	private boolean DeflateSlow(boolean flush, boolean finish)
	{
		if (lookahead < MIN_LOOKAHEAD && !flush)
		{
			return false;
		}

		while (lookahead >= MIN_LOOKAHEAD || flush)
		{
			if (lookahead == 0)
			{
				if (prevAvailable)
				{
					huffman.TallyLit(window[strstart - 1] & 0xff);
				}
				prevAvailable = false;

				// We are flushing everything
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
				if (DeflaterConstants.DEBUGGING && !flush)
				{
					throw new SharpZipBaseException("Not flushing, but no lookahead");
				}
//#endif
				huffman.FlushBlock(window, blockStart, strstart - blockStart, finish);
				blockStart = strstart;
				return false;
			}

			if (strstart >= 2 * WSIZE - MIN_LOOKAHEAD)
			{
				/* slide window, as FindLongestMatch needs this.
				 * This should only happen when flushing and the window
				 * is almost full.
				 */
				SlideWindow();
			}

			int prevMatch = matchStart;
			int prevLen = matchLen;
			if (lookahead >= MIN_MATCH)
			{

				int hashHead = InsertString();

				if (strategy != DeflateStrategy.HuffmanOnly && hashHead != 0 && strstart - hashHead <= MAX_DIST && FindLongestMatch(hashHead))
				{

					// longestMatch sets matchStart and matchLen

					// Discard match if too small and too far away
					if (matchLen <= 5 && (strategy == DeflateStrategy.Filtered || (matchLen == MIN_MATCH && strstart - matchStart > TooFar)))
					{
						matchLen = MIN_MATCH - 1;
					}
				}
			}

			// previous match was better
			if ((prevLen >= MIN_MATCH) && (matchLen <= prevLen))
			{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if DebugDeflation
				if (DeflaterConstants.DEBUGGING)
				{
				   for (int i = 0 ; i < matchLen; i++)
				   {
					  if (window[strstart - 1 + i] != window[prevMatch + i])
					  {
						 throw new SharpZipBaseException();
					  }
				   }
				}
//#endif
				huffman.TallyDist(strstart - 1 - prevMatch, prevLen);
				prevLen -= 2;
				do
				{
					strstart++;
					lookahead--;
					if (lookahead >= MIN_MATCH)
					{
						InsertString();
					}
				} while (--prevLen > 0);

				strstart++;
				lookahead--;
				prevAvailable = false;
				matchLen = MIN_MATCH - 1;
			}
			else
			{
				if (prevAvailable)
				{
					huffman.TallyLit(window[strstart - 1] & 0xff);
				}
				prevAvailable = true;
				strstart++;
				lookahead--;
			}

			if (huffman.IsFull())
			{
				int len = strstart - blockStart;
				if (prevAvailable)
				{
					len--;
				}
				boolean lastBlock = (finish && (lookahead == 0) && !prevAvailable);
				huffman.FlushBlock(window, blockStart, len, lastBlock);
				blockStart += len;
				return !lastBlock;
			}
		}
		return true;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields

	// Hash index of string to be inserted
	private int ins_h;

	/** 
	 Hashtable, hashing three characters to an index for window, so
	 that window[index]..window[index+2] have this hash code.  
	 Note that the array should really be unsigned short, so you need
	 to and the values with 0xffff.
	*/
	private short[] head;

	/** 
	 <code>prev[index &amp; WMASK]</code> points to the previous index that has the
	 same hash code as the string starting at index.  This way 
	 entries with the same hash code are in a linked list.
	 Note that the array should really be unsigned short, so you need
	 to and the values with 0xffff.
	*/
	private short[] prev;

	private int matchStart;
	// Length of best match
	private int matchLen;
	// Set if previous match exists
	private boolean prevAvailable;
	private int blockStart;

	/** 
	 Points to the current character in the window.
	*/
	private int strstart;

	/** 
	 lookahead is the number of characters starting at strstart in
	 window that are valid.
	 So window[strstart] until window[strstart+lookahead-1] are valid
	 characters.
	*/
	private int lookahead;

	/** 
	 This array contains the part of the uncompressed stream that 
	 is of relevance.  The current character is indexed by strstart.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] window;
	private byte[] window;

	private DeflateStrategy strategy = DeflateStrategy.values()[0];
	private int max_chain, max_lazy, niceLength, goodLength;

	/** 
	 The current compression function.
	*/
	private int compressionFunction;

	/** 
	 The input data for compression.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] inputBuf;
	private byte[] inputBuf;

	/** 
	 The total bytes of input read.
	*/
	private long totalIn;

	/** 
	 The offset into inputBuf, where input data starts.
	*/
	private int inputOff;

	/** 
	 The end offset of the input data.
	*/
	private int inputEnd;

	private DeflaterPending pending;
	private DeflaterHuffman huffman;

	/** 
	 The adler checksum
	*/
	private Adler32 adler;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}