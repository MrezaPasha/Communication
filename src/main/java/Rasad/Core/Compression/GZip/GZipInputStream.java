package Rasad.Core.Compression.GZip;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;

 </code>
 </example>
*/
public class GZipInputStream extends InflaterInputStream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 CRC-32 value for uncompressed data
	*/
	protected Crc32 crc;

	/** 
	 Flag to indicate if we've read the GZIP header yet for the current member (block of compressed data).
	 This is tracked per-block as the file is parsed.
	*/
	private boolean readGZIPHeader;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Creates a GZipInputStream with the default buffer size
	 
	 @param baseInputStream
	 The stream to read compressed data from (baseInputStream GZIP format)
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public GZipInputStream(Stream baseInputStream)
	{
		this(baseInputStream, 4096);
	}

	/** 
	 Creates a GZIPInputStream with the specified buffer size
	 
	 @param baseInputStream
	 The stream to read compressed data from (baseInputStream GZIP format)
	 
	 @param size
	 Size of the buffer to use
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public GZipInputStream(Stream baseInputStream, int size)
	{
		super(baseInputStream, new Inflater(true), size);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Stream overrides
	/** 
	 Reads uncompressed data into an array of bytes
	 
	 @param buffer
	 The buffer to read uncompressed data into
	 
	 @param offset
	 The offset indicating where the data should be placed
	 
	 @param count
	 The number of uncompressed bytes to be read
	 
	 @return Returns the number of bytes actually read.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
	@Override
	public int Read(byte[] buffer, int offset, int count)
	{
		// A GZIP file can contain multiple blocks of compressed data, although this is quite rare.
		// A compressed block could potentially be empty, so we need to loop until we reach EOF or
		// we find data.
		while (true)
		{

			// If we haven't read the header for this block, read it
			if (!readGZIPHeader)
			{

				// Try to read header. If there is no header (0 bytes available), this is EOF. If there is
				// an incomplete header, this will throw an exception.
				if (!ReadHeader())
				{
					return 0;
				}
			}

			// Try to read compressed data
			int bytesRead = super.Read(buffer, offset, count);
			if (bytesRead > 0)
			{
				crc.Update(buffer, offset, bytesRead);
			}

			// If this is the end of stream, read the footer
			if (inf.getIsFinished())
			{
				ReadFooter();
			}

			if (bytesRead > 0)
			{
				return bytesRead;
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Support routines
	private boolean ReadHeader()
	{
		// Initialize CRC for this block
		crc = new Crc32();

		// Make sure there is data in file. We can't rely on ReadLeByte() to fill the buffer, as this could be EOF,
		// which is fine, but ReadLeByte() throws an exception if it doesn't find data, so we do this part ourselves.
		if (inputBuffer.getAvailable() <= 0)
		{
			inputBuffer.Fill();
			if (inputBuffer.getAvailable() <= 0)
			{
				// No header, EOF.
				return false;
			}
		}

		// 1. Check the two magic bytes
		Crc32 headCRC = new Crc32();
		int magic = inputBuffer.ReadLeByte();

		if (magic < 0)
		{
			throw new EndOfStreamException("EOS reading GZIP header");
		}

		headCRC.Update(magic);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		if (magic != (GZipConstants.GZIP_MAGIC >> 8))
		{
			throw new GZipException("Error GZIP header, first magic byte doesn't match");
		}

		//magic = baseInputStream.ReadByte();
		magic = inputBuffer.ReadLeByte();

		if (magic < 0)
		{
			throw new EndOfStreamException("EOS reading GZIP header");
		}

		if (magic != (GZipConstants.GZIP_MAGIC & 0xFF))
		{
			throw new GZipException("Error GZIP header,  second magic byte doesn't match");
		}

		headCRC.Update(magic);

		// 2. Check the compression type (must be 8)
		int compressionType = inputBuffer.ReadLeByte();

		if (compressionType < 0)
		{
			throw new EndOfStreamException("EOS reading GZIP header");
		}

		if (compressionType != 8)
		{
			throw new GZipException("Error GZIP header, data not in deflate format");
		}
		headCRC.Update(compressionType);

		// 3. Check the flags
		int flags = inputBuffer.ReadLeByte();
		if (flags < 0)
		{
			throw new EndOfStreamException("EOS reading GZIP header");
		}
		headCRC.Update(flags);

		/*    This flag byte is divided into individual bits as follows:

		bit 0   FTEXT
		bit 1   FHCRC
		bit 2   FEXTRA
		bit 3   FNAME
		bit 4   FCOMMENT
		bit 5   reserved
		bit 6   reserved
		bit 7   reserved
		*/

		// 3.1 Check the reserved bits are zero

		if ((flags & 0xE0) != 0)
		{
			throw new GZipException("Reserved flag bits in GZIP header != 0");
		}

		// 4.-6. Skip the modification time, extra flags, and OS type
		for (int i = 0; i < 6; i++)
		{
			int readByte = inputBuffer.ReadLeByte();
			if (readByte < 0)
			{
				throw new EndOfStreamException("EOS reading GZIP header");
			}
			headCRC.Update(readByte);
		}

		// 7. Read extra field
		if ((flags & GZipConstants.FEXTRA) != 0)
		{

			// XLEN is total length of extra subfields, we will skip them all
			int len1, len2;
			len1 = inputBuffer.ReadLeByte();
			len2 = inputBuffer.ReadLeByte();
			if ((len1 < 0) || (len2 < 0))
			{
				throw new EndOfStreamException("EOS reading GZIP header");
			}
			headCRC.Update(len1);
			headCRC.Update(len2);

			int extraLen = (len2 << 8) | len1; // gzip is LSB first
			for (int i = 0; i < extraLen;i++)
			{
				int readByte = inputBuffer.ReadLeByte();
				if (readByte < 0)
				{
					throw new EndOfStreamException("EOS reading GZIP header");
				}
				headCRC.Update(readByte);
			}
		}

		// 8. Read file name
		if ((flags & GZipConstants.FNAME) != 0)
		{
			int readByte;
			while ((readByte = inputBuffer.ReadLeByte()) > 0)
			{
				headCRC.Update(readByte);
			}

			if (readByte < 0)
			{
				throw new EndOfStreamException("EOS reading GZIP header");
			}
			headCRC.Update(readByte);
		}

		// 9. Read comment
		if ((flags & GZipConstants.FCOMMENT) != 0)
		{
			int readByte;
			while ((readByte = inputBuffer.ReadLeByte()) > 0)
			{
				headCRC.Update(readByte);
			}

			if (readByte < 0)
			{
				throw new EndOfStreamException("EOS reading GZIP header");
			}

			headCRC.Update(readByte);
		}

		// 10. Read header CRC
		if ((flags & GZipConstants.FHCRC) != 0)
		{
			int tempByte;
			int crcval = inputBuffer.ReadLeByte();
			if (crcval < 0)
			{
				throw new EndOfStreamException("EOS reading GZIP header");
			}

			tempByte = inputBuffer.ReadLeByte();
			if (tempByte < 0)
			{
				throw new EndOfStreamException("EOS reading GZIP header");
			}

			crcval = (crcval << 8) | tempByte;
			if (crcval != ((int) headCRC.getValue() & 0xffff))
			{
				throw new GZipException("Header CRC value mismatch");
			}
		}

		readGZIPHeader = true;
		return true;
	}

	private void ReadFooter()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] footer = new byte[8];
		byte[] footer = new byte[8];

		// End of stream; reclaim all bytes from inf, read the final byte count, and reset the inflator
		long bytesRead = inf.getTotalOut() & 0xffffffff;
		inputBuffer.setAvailable(inputBuffer.getAvailable() + inf.getRemainingInput());
		inf.Reset();

		// Read footer from inputBuffer
		int needed = 8;
		while (needed > 0)
		{
			int count = inputBuffer.ReadClearTextBuffer(footer, 8 - needed, needed);
			if (count <= 0)
			{
				throw new EndOfStreamException("EOS reading GZIP footer");
			}
			needed -= count; // Jewel Jan 16
		}

		// Calculate CRC
		int crcval = (footer[0] & 0xff) | ((footer[1] & 0xff) << 8) | ((footer[2] & 0xff) << 16) | (footer[3] << 24);
		if (crcval != (int) crc.getValue())
		{
			throw new GZipException("GZIP crc sum mismatch, theirs \"" + crcval + "\" and ours \"" + (int) crc.getValue());
		}

		// NOTE The total here is the original total modulo 2 ^ 32.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint total = (uint)((uint)footer[4] & 0xff) | (uint)(((uint)footer[5] & 0xff) << 8) | (uint)(((uint)footer[6] & 0xff) << 16) | (uint)((uint)footer[7] << 24);
		int total = (int)((int)footer[4] & 0xff) | (int)(((int)footer[5] & 0xff) << 8) | (int)(((int)footer[6] & 0xff) << 16) | (int)((int)footer[7] << 24);

		if (bytesRead != total)
		{
			throw new GZipException("Number of bytes mismatch in footer");
		}

		// Mark header read as false so if another header exists, we'll continue reading through the file
		readGZIPHeader = false;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}