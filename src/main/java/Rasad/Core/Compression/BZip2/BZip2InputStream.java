package Rasad.Core.Compression.BZip2;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;

// BZip2InputStream.cs
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
 An input stream that decompresses files in the BZip2 format 
*/
public class BZip2InputStream extends Stream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constants
	private static final int START_BLOCK_STATE = 1;
	private static final int RAND_PART_A_STATE = 2;
	private static final int RAND_PART_B_STATE = 3;
	private static final int RAND_PART_C_STATE = 4;
	private static final int NO_RAND_PART_A_STATE = 5;
	private static final int NO_RAND_PART_B_STATE = 6;
	private static final int NO_RAND_PART_C_STATE = 7;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Construct instance for reading from stream
	 
	 @param stream Data source
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public BZip2InputStream(Stream stream)
	{
		// init arrays
		for (int i = 0; i < BZip2Constants.GroupCount; ++i)
		{
			limit[i] = new int[BZip2Constants.MaximumAlphaSize];
			baseArray[i] = new int[BZip2Constants.MaximumAlphaSize];
			perm[i] = new int[BZip2Constants.MaximumAlphaSize];
		}

		BsSetStream(stream);
		Initialize();
		InitBlock();
		SetupBlock();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get/set flag indicating ownership of underlying stream.
	 When the flag is true <see cref="Close"></see> will close the underlying stream also.
	*/
	public final boolean getIsStreamOwner()
	{
		return isStreamOwner;
	}
	public final void setIsStreamOwner(boolean value)
	{
		isStreamOwner = value;
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Stream Overrides
	/** 
	 Gets a value indicating if the stream supports reading
	*/
	@Override
	public boolean getCanRead()
	{
		return baseStream.CanRead;
	}

	/** 
	 Gets a value indicating whether the current stream supports seeking.
	*/
	@Override
	public boolean getCanSeek()
	{
		return baseStream.CanSeek;
	}

	/** 
	 Gets a value indicating whether the current stream supports writing.
	 This property always returns false
	*/
	@Override
	public boolean getCanWrite()
	{
		return false;
	}

	/** 
	 Gets the length in bytes of the stream.
	*/
	@Override
	public long getLength()
	{
		return baseStream.Length;
	}

	/** 
	 Gets or sets the streams position.
	 Setting the position is not supported and will throw a NotSupportException
	 
	 @exception NotSupportedException Any attempt to set the position
	*/
	@Override
	public long getPosition()
	{
		return baseStream.Position;
	}
	@Override
	public void setPosition(long value)
	{
		throw new UnsupportedOperationException("BZip2InputStream position cannot be set");
	}

	/** 
	 Flushes the stream.
	*/
	@Override
	public void Flush()
	{
		if (baseStream != null)
		{
			baseStream.Flush();
		}
	}

	/** 
	 Set the streams position.  This operation is not supported and will throw a NotSupportedException
	 
	 @param offset A byte offset relative to the <paramref name="origin"/> parameter.
	 @param origin A value of type <see cref="SeekOrigin"/> indicating the reference point used to obtain the new position.
	 @return The new position of the stream.
	 @exception NotSupportedException Any access
	*/
	@Override
	public long Seek(long offset, SeekOrigin origin)
	{
		throw new UnsupportedOperationException("BZip2InputStream Seek not supported");
	}

	/** 
	 Sets the length of this stream to the given value.
	 This operation is not supported and will throw a NotSupportedExceptionortedException
	 
	 @param value The new length for the stream.
	 @exception NotSupportedException Any access
	*/
	@Override
	public void SetLength(long value)
	{
		throw new UnsupportedOperationException("BZip2InputStream SetLength not supported");
	}

	/** 
	 Writes a block of bytes to this stream using data from a buffer.
	 This operation is not supported and will throw a NotSupportedException
	 
	 @param buffer The buffer to source data from.
	 @param offset The offset to start obtaining data from.
	 @param count The number of bytes of data to write.
	 @exception NotSupportedException Any access
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
	{
		throw new UnsupportedOperationException("BZip2InputStream Write not supported");
	}

	/** 
	 Writes a byte to the current position in the file stream.
	 This operation is not supported and will throw a NotSupportedException
	 
	 @param value The value to write.
	 @exception NotSupportedException Any access
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void WriteByte(byte value)
	@Override
	public void WriteByte(byte value)
	{
		throw new UnsupportedOperationException("BZip2InputStream WriteByte not supported");
	}

	/** 
	 Read a sequence of bytes and advances the read position by one byte.
	 
	 @param buffer Array of bytes to store values in
	 @param offset Offset in array to begin storing data
	 @param count The maximum number of bytes to read
	 @return The total number of bytes read into the buffer. This might be less
	 than the number of bytes requested if that number of bytes are not 
	 currently available or zero if the end of the stream is reached.
	 
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

		for (int i = 0; i < count; ++i)
		{
			int rb = ReadByte();
			if (rb == -1)
			{
				return i;
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer[offset + i] = (byte)rb;
			buffer[offset + i] = (byte)rb;
		}
		return count;
	}

	/** 
	 Closes the stream, releasing any associated resources.
	*/
	@Override
	public void Close()
	{
		if (getIsStreamOwner() && (baseStream != null))
		{
			baseStream.Close();
		}
	}
	/** 
	 Read a byte from stream advancing position
	 
	 @return byte read or -1 on end of stream
	*/
	@Override
	public int ReadByte()
	{
		if (streamEnd)
		{
			return -1; // ok
		}

		int retChar = currentChar;
		switch (currentState)
		{
			case RAND_PART_B_STATE:
				SetupRandPartB();
				break;
			case RAND_PART_C_STATE:
				SetupRandPartC();
				break;
			case NO_RAND_PART_B_STATE:
				SetupNoRandPartB();
				break;
			case NO_RAND_PART_C_STATE:
				SetupNoRandPartC();
				break;
			case START_BLOCK_STATE:
			case NO_RAND_PART_A_STATE:
			case RAND_PART_A_STATE:
				break;
			default:
				break;
		}
		return retChar;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private void MakeMaps()
	{
		nInUse = 0;
		for (int i = 0; i < 256; ++i)
		{
			if (inUse[i])
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: seqToUnseq[nInUse] = (byte)i;
				seqToUnseq[nInUse] = (byte)i;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: unseqToSeq[i] = (byte)nInUse;
				unseqToSeq[i] = (byte)nInUse;
				nInUse++;
			}
		}
	}

	private void Initialize()
	{
		char magic1 = BsGetUChar();
		char magic2 = BsGetUChar();

		char magic3 = BsGetUChar();
		char magic4 = BsGetUChar();

		if (magic1 != 'B' || magic2 != 'Z' || magic3 != 'h' || magic4 < '1' || magic4 > '9')
		{
			streamEnd = true;
			return;
		}

		SetDecompressStructureSizes(magic4 - '0');
		computedCombinedCRC = 0;
	}

	private void InitBlock()
	{
		char magic1 = BsGetUChar();
		char magic2 = BsGetUChar();
		char magic3 = BsGetUChar();
		char magic4 = BsGetUChar();
		char magic5 = BsGetUChar();
		char magic6 = BsGetUChar();

		if (magic1 == 0x17 && magic2 == 0x72 && magic3 == 0x45 && magic4 == 0x38 && magic5 == 0x50 && magic6 == 0x90)
		{
			Complete();
			return;
		}

		if (magic1 != 0x31 || magic2 != 0x41 || magic3 != 0x59 || magic4 != 0x26 || magic5 != 0x53 || magic6 != 0x59)
		{
			BadBlockHeader();
			streamEnd = true;
			return;
		}

		storedBlockCRC = BsGetInt32();

		blockRandomised = (BsR(1) == 1);

		GetAndMoveToFrontDecode();

		mCrc.Reset();
		currentState = START_BLOCK_STATE;
	}

	private void EndBlock()
	{
		computedBlockCRC = (int)mCrc.getValue();

		// -- A bad CRC is considered a fatal error. --
		if (storedBlockCRC != computedBlockCRC)
		{
			CrcError();
		}

		// 1528150659
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		computedCombinedCRC = ((computedCombinedCRC << 1) & 0xFFFFFFFF) | (computedCombinedCRC >>> 31);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: computedCombinedCRC = computedCombinedCRC ^ (uint)computedBlockCRC;
		computedCombinedCRC = computedCombinedCRC ^ (int)computedBlockCRC;
	}

	private void Complete()
	{
		storedCombinedCRC = BsGetInt32();
		if (storedCombinedCRC != (int)computedCombinedCRC)
		{
			CrcError();
		}

		streamEnd = true;
	}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private void BsSetStream(Stream stream)
	{
		baseStream = stream;
		bsLive = 0;
		bsBuff = 0;
	}

	private void FillBuffer()
	{
		int thech = 0;

		try
		{
			thech = baseStream.ReadByte();
		}
		catch (RuntimeException e)
		{
			CompressedStreamEOF();
		}

		if (thech == -1)
		{
			CompressedStreamEOF();
		}

		bsBuff = (bsBuff << 8) | (thech & 0xFF);
		bsLive += 8;
	}

	private int BsR(int n)
	{
		while (bsLive < n)
		{
			FillBuffer();
		}

//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		int v = (bsBuff >> (bsLive - n)) & ((1 << n) - 1);
		bsLive -= n;
		return v;
	}

	private char BsGetUChar()
	{
		return (char)BsR(8);
	}

	private int BsGetIntVS(int numBits)
	{
		return BsR(numBits);
	}

	private int BsGetInt32()
	{
		int result = BsR(8);
		result = (result << 8) | BsR(8);
		result = (result << 8) | BsR(8);
		result = (result << 8) | BsR(8);
		return result;
	}

	private void RecvDecodingTables()
	{
		char[][] len = new char[BZip2Constants.GroupCount][];
		for (int i = 0; i < BZip2Constants.GroupCount; ++i)
		{
			len[i] = new char[BZip2Constants.MaximumAlphaSize];
		}

		boolean[] inUse16 = new boolean[16];

		//--- Receive the mapping table ---
		for (int i = 0; i < 16; i++)
		{
			inUse16[i] = (BsR(1) == 1);
		}

		for (int i = 0; i < 16; i++)
		{
			if (inUse16[i])
			{
				for (int j = 0; j < 16; j++)
				{
					inUse[i * 16 + j] = (BsR(1) == 1);
				}
			}
			else
			{
				for (int j = 0; j < 16; j++)
				{
					inUse[i * 16 + j] = false;
				}
			}
		}

		MakeMaps();
		int alphaSize = nInUse + 2;

		//--- Now the selectors ---
		int nGroups = BsR(3);
		int nSelectors = BsR(15);

		for (int i = 0; i < nSelectors; i++)
		{
			int j = 0;
			while (BsR(1) == 1)
			{
				j++;
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: selectorMtf[i] = (byte)j;
			selectorMtf[i] = (byte)j;
		}

		//--- Undo the MTF values for the selectors. ---
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] pos = new byte[BZip2Constants.GroupCount];
		byte[] pos = new byte[BZip2Constants.GroupCount];
		for (int v = 0; v < nGroups; v++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: pos[v] = (byte)v;
			pos[v] = (byte)v;
		}

		for (int i = 0; i < nSelectors; i++)
		{
			int v = selectorMtf[i];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte tmp = pos[v];
			byte tmp = pos[v];
			while (v > 0)
			{
				pos[v] = pos[v - 1];
				v--;
			}
			pos[0] = tmp;
			selector[i] = tmp;
		}

		//--- Now the coding tables ---
		for (int t = 0; t < nGroups; t++)
		{
			int curr = BsR(5);
			for (int i = 0; i < alphaSize; i++)
			{
				while (BsR(1) == 1)
				{
					if (BsR(1) == 0)
					{
						curr++;
					}
					else
					{
						curr--;
					}
				}
				len[t][i] = (char)curr;
			}
		}

		//--- Create the Huffman decoding tables ---
		for (int t = 0; t < nGroups; t++)
		{
			int minLen = 32;
			int maxLen = 0;
			for (int i = 0; i < alphaSize; i++)
			{
				maxLen = Math.max(maxLen, len[t][i]);
				minLen = Math.min(minLen, len[t][i]);
			}
			HbCreateDecodeTables(limit[t], baseArray[t], perm[t], len[t], minLen, maxLen, alphaSize);
			minLens[t] = minLen;
		}
	}

	private void GetAndMoveToFrontDecode()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] yy = new byte[256];
		byte[] yy = new byte[256];
		int nextSym;

		int limitLast = BZip2Constants.BaseBlockSize * blockSize100k;
		origPtr = BsGetIntVS(24);

		RecvDecodingTables();
		int EOB = nInUse+1;
		int groupNo = -1;
		int groupPos = 0;

		/*--
		Setting up the unzftab entries here is not strictly
		necessary, but it does save having to do it later
		in a separate pass, and so saves a block's worth of
		cache misses.
		--*/
		for (int i = 0; i <= 255; i++)
		{
			unzftab[i] = 0;
		}

		for (int i = 0; i <= 255; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: yy[i] = (byte)i;
			yy[i] = (byte)i;
		}

		last = -1;

		if (groupPos == 0)
		{
			groupNo++;
			groupPos = BZip2Constants.GroupSize;
		}

		groupPos--;
		int zt = selector[groupNo];
		int zn = minLens[zt];
		int zvec = BsR(zn);
		int zj;

		while (zvec > limit[zt][zn])
		{
			if (zn > 20)
			{ // the longest code
				throw new BZip2Exception("Bzip data error");
			}
			zn++;
			while (bsLive < 1)
			{
				FillBuffer();
			}
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			zj = (bsBuff >> (bsLive-1)) & 1;
			bsLive--;
			zvec = (zvec << 1) | zj;
		}
		if (zvec - baseArray[zt][zn] < 0 || zvec - baseArray[zt][zn] >= BZip2Constants.MaximumAlphaSize)
		{
			throw new BZip2Exception("Bzip data error");
		}
		nextSym = perm[zt][zvec - baseArray[zt][zn]];

		while (true)
		{
			if (nextSym == EOB)
			{
				break;
			}

			if (nextSym == BZip2Constants.RunA || nextSym == BZip2Constants.RunB)
			{
				int s = -1;
				int n = 1;
				do
				{
					if (nextSym == BZip2Constants.RunA)
					{
						s += (0 + 1) * n;
					}
					else if (nextSym == BZip2Constants.RunB)
					{
						s += (1 + 1) * n;
					}

					n <<= 1;

					if (groupPos == 0)
					{
						groupNo++;
						groupPos = BZip2Constants.GroupSize;
					}

					groupPos--;

					zt = selector[groupNo];
					zn = minLens[zt];
					zvec = BsR(zn);

					while (zvec > limit[zt][zn])
					{
						zn++;
						while (bsLive < 1)
						{
							FillBuffer();
						}
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
						zj = (bsBuff >> (bsLive - 1)) & 1;
						bsLive--;
						zvec = (zvec << 1) | zj;
					}
					nextSym = perm[zt][zvec - baseArray[zt][zn]];
				} while (nextSym == BZip2Constants.RunA || nextSym == BZip2Constants.RunB);

				s++;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte ch = seqToUnseq[yy[0]];
				byte ch = seqToUnseq[yy[0]];
				unzftab[ch] += s;

				while (s > 0)
				{
					last++;
					ll8[last] = ch;
					s--;
				}

				if (last >= limitLast)
				{
					BlockOverrun();
				}
				continue;
			}
			else
			{
				last++;
				if (last >= limitLast)
				{
					BlockOverrun();
				}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte tmp = yy[nextSym - 1];
				byte tmp = yy[nextSym - 1];
				unzftab[seqToUnseq[tmp]]++;
				ll8[last] = seqToUnseq[tmp];

				for (int j = nextSym - 1; j > 0; --j)
				{
					yy[j] = yy[j - 1];
				}
				yy[0] = tmp;

				if (groupPos == 0)
				{
					groupNo++;
					groupPos = BZip2Constants.GroupSize;
				}

				groupPos--;
				zt = selector[groupNo];
				zn = minLens[zt];
				zvec = BsR(zn);
				while (zvec > limit[zt][zn])
				{
					zn++;
					while (bsLive < 1)
					{
						FillBuffer();
					}
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
					zj = (bsBuff >> (bsLive-1)) & 1;
					bsLive--;
					zvec = (zvec << 1) | zj;
				}
				nextSym = perm[zt][zvec - baseArray[zt][zn]];
				continue;
			}
		}
	}

	private void SetupBlock()
	{
		int[] cftab = new int[257];

		cftab[0] = 0;
		System.arraycopy(unzftab, 0, cftab, 1, 256);

		for (int i = 1; i <= 256; i++)
		{
			cftab[i] += cftab[i - 1];
		}

		for (int i = 0; i <= last; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte ch = ll8[i];
			byte ch = ll8[i];
			tt[cftab[ch]] = i;
			cftab[ch]++;
		}

		cftab = null;

		tPos = tt[origPtr];

		count = 0;
		i2 = 0;
		ch2 = 256; //-- not a char and not EOF --

		if (blockRandomised)
		{
			rNToGo = 0;
			rTPos = 0;
			SetupRandPartA();
		}
		else
		{
			SetupNoRandPartA();
		}
	}

	private void SetupRandPartA()
	{
		if (i2 <= last)
		{
			chPrev = ch2;
			ch2 = ll8[tPos];
			tPos = tt[tPos];
			if (rNToGo == 0)
			{
				rNToGo = BZip2Constants.RandomNumbers[rTPos];
				rTPos++;
				if (rTPos == 512)
				{
					rTPos = 0;
				}
			}
			rNToGo--;
			ch2 ^= (int)((rNToGo == 1) ? 1 : 0);
			i2++;

			currentChar = ch2;
			currentState = RAND_PART_B_STATE;
			mCrc.Update(ch2);
		}
		else
		{
			EndBlock();
			InitBlock();
			SetupBlock();
		}
	}

	private void SetupNoRandPartA()
	{
		if (i2 <= last)
		{
			chPrev = ch2;
			ch2 = ll8[tPos];
			tPos = tt[tPos];
			i2++;

			currentChar = ch2;
			currentState = NO_RAND_PART_B_STATE;
			mCrc.Update(ch2);
		}
		else
		{
			EndBlock();
			InitBlock();
			SetupBlock();
		}
	}

	private void SetupRandPartB()
	{
		if (ch2 != chPrev)
		{
			currentState = RAND_PART_A_STATE;
			count = 1;
			SetupRandPartA();
		}
		else
		{
			count++;
			if (count >= 4)
			{
				z = ll8[tPos];
				tPos = tt[tPos];
				if (rNToGo == 0)
				{
					rNToGo = BZip2Constants.RandomNumbers[rTPos];
					rTPos++;
					if (rTPos == 512)
					{
						rTPos = 0;
					}
				}
				rNToGo--;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: z ^= (byte)((rNToGo == 1) ? 1 : 0);
				z ^= (byte)((rNToGo == 1) ? 1 : 0);
				j2 = 0;
				currentState = RAND_PART_C_STATE;
				SetupRandPartC();
			}
			else
			{
				currentState = RAND_PART_A_STATE;
				SetupRandPartA();
			}
		}
	}

	private void SetupRandPartC()
	{
		if (j2 < (int)z)
		{
			currentChar = ch2;
			mCrc.Update(ch2);
			j2++;
		}
		else
		{
			currentState = RAND_PART_A_STATE;
			i2++;
			count = 0;
			SetupRandPartA();
		}
	}

	private void SetupNoRandPartB()
	{
		if (ch2 != chPrev)
		{
			currentState = NO_RAND_PART_A_STATE;
			count = 1;
			SetupNoRandPartA();
		}
		else
		{
			count++;
			if (count >= 4)
			{
				z = ll8[tPos];
				tPos = tt[tPos];
				currentState = NO_RAND_PART_C_STATE;
				j2 = 0;
				SetupNoRandPartC();
			}
			else
			{
				currentState = NO_RAND_PART_A_STATE;
				SetupNoRandPartA();
			}
		}
	}

	private void SetupNoRandPartC()
	{
		if (j2 < (int)z)
		{
			currentChar = ch2;
			mCrc.Update(ch2);
			j2++;
		}
		else
		{
			currentState = NO_RAND_PART_A_STATE;
			i2++;
			count = 0;
			SetupNoRandPartA();
		}
	}

	private void SetDecompressStructureSizes(int newSize100k)
	{
		if (!(0 <= newSize100k && newSize100k <= 9 && 0 <= blockSize100k && blockSize100k <= 9))
		{
			throw new BZip2Exception("Invalid block size");
		}

		blockSize100k = newSize100k;

		if (newSize100k == 0)
		{
			return;
		}

		int n = BZip2Constants.BaseBlockSize * newSize100k;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ll8 = new byte[n];
		ll8 = new byte[n];
		tt = new int[n];
	}

	private static void CompressedStreamEOF()
	{
		throw new EndOfStreamException("BZip2 input stream end of compressed stream");
	}

	private static void BlockOverrun()
	{
		throw new BZip2Exception("BZip2 input stream block overrun");
	}

	private static void BadBlockHeader()
	{
		throw new BZip2Exception("BZip2 input stream bad block header");
	}

	private static void CrcError()
	{
		throw new BZip2Exception("BZip2 input stream crc error");
	}

	private static void HbCreateDecodeTables(int[] limit, int[] baseArray, int[] perm, char[] length, int minLen, int maxLen, int alphaSize)
	{
		int pp = 0;

		for (int i = minLen; i <= maxLen; ++i)
		{
			for (int j = 0; j < alphaSize; ++j)
			{
				if (length[j] == i)
				{
					perm[pp] = j;
					++pp;
				}
			}
		}

		for (int i = 0; i < BZip2Constants.MaximumCodeLength; i++)
		{
			baseArray[i] = 0;
		}

		for (int i = 0; i < alphaSize; i++)
		{
			++baseArray[length[i] + 1];
		}

		for (int i = 1; i < BZip2Constants.MaximumCodeLength; i++)
		{
			baseArray[i] += baseArray[i - 1];
		}

		for (int i = 0; i < BZip2Constants.MaximumCodeLength; i++)
		{
			limit[i] = 0;
		}

		int vec = 0;

		for (int i = minLen; i <= maxLen; i++)
		{
			vec += (baseArray[i + 1] - baseArray[i]);
			limit[i] = vec - 1;
			vec <<= 1;
		}

		for (int i = minLen + 1; i <= maxLen; i++)
		{
			baseArray[i] = ((limit[i - 1] + 1) << 1) - baseArray[i];
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/*--
	index of the last char in the block, so
	the block size == last + 1.
	--*/
	private int last;

	/*--
	index in zptr[] of original string after sorting.
	--*/
	private int origPtr;

	/*--
	always: in the range 0 .. 9.
	The current block size is 100000 * this number.
	--*/
	private int blockSize100k;

	private boolean blockRandomised;

	private int bsBuff;
	private int bsLive;
	private IChecksum mCrc = new StrangeCRC();

	private boolean[] inUse = new boolean[256];
	private int nInUse;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] seqToUnseq = new byte[256];
	private byte[] seqToUnseq = new byte[256];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] unseqToSeq = new byte[256];
	private byte[] unseqToSeq = new byte[256];

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] selector = new byte[BZip2Constants.MaximumSelectors];
	private byte[] selector = new byte[BZip2Constants.MaximumSelectors];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] selectorMtf = new byte[BZip2Constants.MaximumSelectors];
	private byte[] selectorMtf = new byte[BZip2Constants.MaximumSelectors];

	private int[] tt;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] ll8;
	private byte[] ll8;

	/*--
	freq table collected to save a pass over the data
	during decompression.
	--*/
	private int[] unzftab = new int[256];

	private int[][] limit = new int[BZip2Constants.GroupCount][];
	private int[][] baseArray = new int[BZip2Constants.GroupCount][];
	private int[][] perm = new int[BZip2Constants.GroupCount][];
	private int[] minLens = new int[BZip2Constants.GroupCount];

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream baseStream;
	private boolean streamEnd;

	private int currentChar = -1;

	private int currentState = START_BLOCK_STATE;

	private int storedBlockCRC, storedCombinedCRC;
	private int computedBlockCRC;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint computedCombinedCRC;
	private int computedCombinedCRC;

	private int count, chPrev, ch2;
	private int tPos;
	private int rNToGo;
	private int rTPos;
	private int i2, j2;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte z;
	private byte z;
	private boolean isStreamOwner = true;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
/* This file was derived from a file containing this license:
 * 
 * This file is a part of bzip2 and/or libbzip2, a program and
 * library for lossless, block-sorting data compression.
 * 
 * Copyright (C) 1996-1998 Julian R Seward.  All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * 2. The origin of this software must not be misrepresented; you must 
 * not claim that you wrote the original software.  If you use this 
 * software in a product, an acknowledgment in the product 
 * documentation would be appreciated but is not required.
 * 
 * 3. Altered source versions must be plainly marked as such, and must
 * not be misrepresented as being the original software.
 * 
 * 4. The name of the author may not be used to endorse or promote 
 * products derived from this software without specific prior written 
 * permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Java version ported by Keiron Liddle, Aftex Software <keiron@aftexsw.com> 1999-2001
 */
