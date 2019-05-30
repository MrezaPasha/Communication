package Rasad.Core.Compression.BZip2;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

// BZip2.cs
//
// Copyright (C) 2010 David Pierson
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

// Suppress this in CF and 1.1, not needed. Static classes introduced in C# version 2.0
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_2_0 && !NET_1_1




/** 
 An example class to demonstrate compression and decompression of BZip2 streams.
*/
public final class BZip2
{
	/** 
	 Decompress the <paramref name="inStream">input</paramref> writing 
	 uncompressed data to the <paramref name="outStream">output stream</paramref>
	 
	 @param inStream The readable stream containing data to decompress.
	 @param outStream The output stream to receive the decompressed data.
	 @param isStreamOwner Both streams are closed on completion if true.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public static void Decompress(Stream inStream, Stream outStream, boolean isStreamOwner)
	{
		if (inStream == null || outStream == null)
		{
			throw new RuntimeException("Null Stream");
		}

		try
		{
			try (BZip2InputStream bzipInput = new BZip2InputStream(inStream))
			{
				bzipInput.setIsStreamOwner(isStreamOwner);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Core.StreamUtils.Copy(bzipInput, outStream, new byte[4096]);
				Core.StreamUtils.Copy(bzipInput, outStream, new byte[4096]);
			}
		}
		finally
		{
			if (isStreamOwner)
			{
				// inStream is closed by the BZip2InputStream if stream owner
				outStream.Close();
			}
		}
	}

	/** 
	 Compress the <paramref name="inStream">input stream</paramref> sending 
	 result data to <paramref name="outStream">output stream</paramref>
	 
	 @param inStream The readable stream to compress.
	 @param outStream The output stream to receive the compressed data.
	 @param isStreamOwner Both streams are closed on completion if true.
	 @param level Block size acts as compression level (1 to 9) with 1 giving 
	 the lowest compression and 9 the highest.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public static void Compress(Stream inStream, Stream outStream, boolean isStreamOwner, int level)
	{
		if (inStream == null || outStream == null)
		{
			throw new RuntimeException("Null Stream");
		}

		try
		{
			try (BZip2OutputStream bzipOutput = new BZip2OutputStream(outStream, level))
			{
				bzipOutput.setIsStreamOwner(isStreamOwner);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Core.StreamUtils.Copy(inStream, bzipOutput, new byte[4096]);
				Core.StreamUtils.Copy(inStream, bzipOutput, new byte[4096]);
			}
		}
		finally
		{
			if (isStreamOwner)
			{
				// outStream is closed by the BZip2OutputStream if stream owner
				inStream.Close();
			}
		}
	}

}
//#endif
