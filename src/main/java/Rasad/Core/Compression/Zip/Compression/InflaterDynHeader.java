package Rasad.Core.Compression.Zip.Compression;

import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import Rasad.Core.Compression.Zip.*;

// InflaterDynHeader.cs
// Copyright (C) 2001 Mike Krueger
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





public class InflaterDynHeader
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constants
	private static final int LNUM = 0;
	private static final int DNUM = 1;
	private static final int BLNUM = 2;
	private static final int BLLENS = 3;
	private static final int LENS = 4;
	private static final int REPS = 5;

	private static final int[] repMin = {3, 3, 11};
	private static final int[] repBits = {2, 3, 7};

	private static final int[] BL_ORDER = {16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	public InflaterDynHeader()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public final boolean Decode(StreamManipulator input)
	{
		decode_loop:
			for (;;)
			{
				switch (mode)
				{
					case LNUM:
						lnum = input.PeekBits(5);
						if (lnum < 0)
						{
							return false;
						}
						lnum += 257;
						input.DropBits(5);
						//  	    System.err.println("LNUM: "+lnum);
						mode = DNUM;
					case DNUM:
						dnum = input.PeekBits(5);
						if (dnum < 0)
						{
							return false;
						}
						dnum++;
						input.DropBits(5);
						//  	    System.err.println("DNUM: "+dnum);
						num = lnum + dnum;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: litdistLens = new byte[num];
						litdistLens = new byte[num];
						mode = BLNUM;
					case BLNUM:
						blnum = input.PeekBits(4);
						if (blnum < 0)
						{
							return false;
						}
						blnum += 4;
						input.DropBits(4);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: blLens = new byte[19];
						blLens = new byte[19];
						ptr = 0;
						//  	    System.err.println("BLNUM: "+blnum);
						mode = BLLENS;
					case BLLENS:
						while (ptr < blnum)
						{
							int len = input.PeekBits(3);
							if (len < 0)
							{
								return false;
							}
							input.DropBits(3);
							//  		System.err.println("blLens["+BL_ORDER[ptr]+"]: "+len);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: blLens[BL_ORDER[ptr]] = (byte) len;
							blLens[BL_ORDER[ptr]] = (byte) len;
							ptr++;
						}
						blTree = new InflaterHuffmanTree(blLens);
						blLens = null;
						ptr = 0;
						mode = LENS;
					case LENS:
					{
						int symbol;
						while (((symbol = blTree.GetSymbol(input)) & ~15) == 0)
						{
							/* Normal case: symbol in [0..15] */

							//  		  System.err.println("litdistLens["+ptr+"]: "+symbol);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: litdistLens[ptr++] = lastLen = (byte)symbol;
							litdistLens[ptr++] = lastLen = (byte)symbol;

							if (ptr == num)
							{
								/* Finished */
								return true;
							}
						}

						/* need more input ? */
						if (symbol < 0)
						{
							return false;
						}

						/* otherwise repeat code */
						if (symbol >= 17)
						{
							/* repeat zero */
							//  		  System.err.println("repeating zero");
							lastLen = 0;
						}
						else
						{
							if (ptr == 0)
							{
								throw new SharpZipBaseException();
							}
						}
						repSymbol = symbol - 16;
					}
						mode = REPS;
					case REPS:
					{
						int bits = repBits[repSymbol];
						int count = input.PeekBits(bits);
						if (count < 0)
						{
							return false;
						}
						input.DropBits(bits);
						count += repMin[repSymbol];
						//  	      System.err.println("litdistLens repeated: "+count);

						if (ptr + count > num)
						{
							throw new SharpZipBaseException();
						}
						while (count-- > 0)
						{
							litdistLens[ptr++] = lastLen;
						}

						if (ptr == num)
						{
							/* Finished */
							return true;
						}
					}
					mode = LENS;
//C# TO JAVA CONVERTER TODO TASK: There is no 'goto' in Java:
					goto decode_loop;
				}
			}
	}

	public final InflaterHuffmanTree BuildLitLenTree()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] litlenLens = new byte[lnum];
		byte[] litlenLens = new byte[lnum];
		System.arraycopy(litdistLens, 0, litlenLens, 0, lnum);
		return new InflaterHuffmanTree(litlenLens);
	}

	public final InflaterHuffmanTree BuildDistTree()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] distLens = new byte[dnum];
		byte[] distLens = new byte[dnum];
		System.arraycopy(litdistLens, lnum, distLens, 0, dnum);
		return new InflaterHuffmanTree(distLens);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] blLens;
	private byte[] blLens;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] litdistLens;
	private byte[] litdistLens;

	private InflaterHuffmanTree blTree;

	/** 
	 The current decode mode
	*/
	private int mode;
	private int lnum, dnum, blnum, num;
	private int repSymbol;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte lastLen;
	private byte lastLen;
	private int ptr;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}