package Rasad.Core.Compression.GZip;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.time.*;

 }	
 </code>
 </example>
*/
public class GZipOutputStream extends DeflaterOutputStream
{
	private enum OutputState
	{
		Header,
		Footer,
		Finished,
		Closed;

		public static final int SIZE = java.lang.Integer.SIZE;

		public int getValue()
		{
			return this.ordinal();
		}

		public static OutputState forValue(int value)
		{
			return values()[value];
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 CRC-32 value for uncompressed data
	*/
	protected Crc32 crc = new Crc32();
	private OutputState state_ = OutputState.Header;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Creates a GzipOutputStream with the default buffer size
	 
	 @param baseOutputStream
	 The stream to read data (to be compressed) from
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public GZipOutputStream(Stream baseOutputStream)
	{
		this(baseOutputStream, 4096);
	}

	/** 
	 Creates a GZipOutputStream with the specified buffer size
	 
	 @param baseOutputStream
	 The stream to read data (to be compressed) from
	 
	 @param size
	 Size of the buffer to use
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public GZipOutputStream(Stream baseOutputStream, int size)
	{
		super(baseOutputStream, new Deflater(Deflater.DEFAULT_COMPRESSION, true), size);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public API
	/** 
	 Sets the active compression level (1-9).  The new level will be activated
	 immediately.
	 
	 @param level The compression level to set.
	 @exception ArgumentOutOfRangeException
	 Level specified is not supported.
	 
	 <see cref="Deflater"/>
	*/
	public final void SetLevel(int level)
	{
		if (level < Deflater.BEST_SPEED)
		{
			throw new IndexOutOfBoundsException("level");
		}
		deflater_.SetLevel(level);
	}

	/** 
	 Get the current compression level.
	 
	 @return The current compression level.
	*/
	public final int GetLevel()
	{
		return deflater_.GetLevel();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Stream overrides
	/** 
	 Write given buffer to output updating crc
	 
	 @param buffer Buffer to write
	 @param offset Offset of first byte in buf to write
	 @param count Number of bytes to write
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
	{
		if (state_ == OutputState.Header)
		{
			WriteHeader();
		}

		if (state_ != OutputState.Footer)
		{
			throw new IllegalStateException("Write not permitted in current state");
		}

		crc.Update(buffer, offset, count);
		super.Write(buffer, offset, count);
	}

	/** 
	 Writes remaining compressed output data to the output stream
	 and closes it.
	*/
	@Override
	public void Close()
	{
		try
		{
			Finish();
		}
		finally
		{
			if (state_ != OutputState.Closed)
			{
				state_ = OutputState.Closed;
				if (IsStreamOwner)
				{
					baseOutputStream_.Close();
				}
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region DeflaterOutputStream overrides
	/** 
	 Finish compression and write any footer information required to stream
	*/
	@Override
	public void Finish()
	{
		// If no data has been written a header should be added.
		if (state_ == OutputState.Header)
		{
			WriteHeader();
		}

		if (state_ == OutputState.Footer)
		{
			state_ = OutputState.Finished;
			super.Finish();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint totalin=(uint)(deflater_.TotalIn&0xffffffff);
			int totalin = (int)(deflater_.TotalIn & 0xffffffff);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint crcval=(uint)(crc.Value&0xffffffff);
			int crcval = (int)(crc.getValue() & 0xffffffff);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] gzipFooter;
			byte[] gzipFooter;

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
			unchecked
			{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gzipFooter = new byte[] { (byte) crcval, (byte)(crcval >> 8), (byte)(crcval >> 16), (byte)(crcval >> 24), (byte) totalin, (byte)(totalin >> 8), (byte)(totalin >> 16), (byte)(totalin >> 24) };
				gzipFooter = new byte[] {(byte) crcval, (byte)(crcval >>> 8), (byte)(crcval >>> 16), (byte)(crcval >>> 24), (byte) totalin, (byte)(totalin >>> 8), (byte)(totalin >>> 16), (byte)(totalin >>> 24)};
			}

			baseOutputStream_.Write(gzipFooter, 0, gzipFooter.length);
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Support Routines
	private void WriteHeader()
	{
		if (state_ == OutputState.Header)
		{
			state_ = OutputState.Footer;

			int mod_time = (int)((LocalDateTime.now().getTime() - (LocalDateTime.of(1970, 1, 1)).getTime()) / 10000000L); // Ticks give back 100ns intervals
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] gzipHeader = { (byte)(GZipConstants.GZIP_MAGIC >> 8), (byte)(GZipConstants.GZIP_MAGIC & 0xff), (byte) Deflater.DEFLATED, 0, (byte) mod_time, (byte)(mod_time >> 8), (byte)(mod_time >> 16), (byte)(mod_time >> 24), 0, (byte) 255 };
			byte[] gzipHeader = {(byte)(GZipConstants.GZIP_MAGIC >> 8), (byte)(GZipConstants.GZIP_MAGIC & 0xff), (byte) Deflater.DEFLATED, 0, (byte) mod_time, (byte)(mod_time >> 8), (byte)(mod_time >> 16), (byte)(mod_time >> 24), 0, (byte) 255};
			baseOutputStream_.Write(gzipHeader, 0, gzipHeader.length);
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}