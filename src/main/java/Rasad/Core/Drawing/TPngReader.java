package Rasad.Core.Drawing;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class TPngReader implements java.lang.Iterable, IFrameSource
{

	/** 
	 List of chunks in the APNG
	*/
	private ArrayList<APNGChunk> _Chunks;
	/** 
	 List of PNGs embedded in the APNG
	*/
	private ArrayList<PNG> _Pngs;
	/** 
	 The APNG's MHDRChunk
	*/
	private acTLChunk _HeaderChunk;

	private IHDRChunk _IhdrChunk;


	/** 
	 Gets the number of embedded PNGs within the APNG
	*/
	public final int getPngCount()
	{
		return _Pngs.size();
	}

	public final java.lang.Iterable<Bitmap> getBitmaps()
	{
		return _Pngs.Select(t -> t.RelatedBitmap);
	}

	public final java.lang.Iterable<BitmapSource> getFrames()
	{
		return this._Pngs.Select(t -> t.RelatedBitmapImage);
	}

	public final Bitmap get(int Index)
	{
		return GetBitmap(Index);
	}

	/** 
	 Creates a Bitmap object containing the embedded PNG at the specified
	 index in the APNG's list of embedded PNGs
	 
	 @param index The embedded PNG index
	 @return Bitmap
	*/
	public final Bitmap GetBitmap(int index)
	{
		// Verify the index
		if (index > getPngCount())
		{
			throw new IllegalArgumentException(String.format("Embedded PNG index must be between 0 and %1$s", getPngCount() - 1));
		}
		return _Pngs.get(index).getRelatedBitmap();
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if (DEBUG)
	public final void SaveFile(int index, String FileName)
	{
		// Verify the index
		if (index > getPngCount())
		{
			throw new IllegalArgumentException(String.format("Embedded PNG index must be between 0 and %1$s", getPngCount() - 1));
		}
		_Pngs.get(index).SaveFile(FileName);
	}
//#endif

	/** 
	 Creates a string containing the names of all the chunks in the APNG
	 
	 @return String
	*/
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (APNGChunk chunk : _Chunks)
		{
			sb.append(chunk.getChunkType() + "\r\n");
		}
		return sb.toString();
	}

	/** 
	 Attempts to load an APNG from the specified file name
	 
	 @param filename Name of the APNG file to load
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final void Load(Stream stream)
	{
		_Chunks = new ArrayList<APNGChunk>();
		_Pngs = new ArrayList<PNG>();
		// Create a new header (should be 1 per file) and
		// read it from the stream
		APNGHeader header = new APNGHeader();
		try
		{
			header.Read(stream);
		}
		catch (RuntimeException e)
		{
			stream.Close();
			throw e;
		}

		APNGChunk chunk;
		PNG png = null;

		// Read chunks from the stream until we reach the MEND chunk
		do
		{
			// Read a generic Chunk
			chunk = new APNGChunk();
			try
			{
				chunk.Read(stream);
			}
			catch (RuntimeException e2)
			{
				stream.Close();
				throw e2;
			}

			// Take a look at the chunk type and decide what derived class to
			// use to create a specific chunk
			switch (chunk.getChunkType())
			{
				case acTLChunk.NAME:
					if (_HeaderChunk != null)
					{
						throw new RuntimeException(String.format("Only one chunk of type %1$s is allowed", chunk.getChunkType()));
					}
					chunk = _HeaderChunk = new acTLChunk(chunk);
					break;
				case MENDChunk.NAME:
					chunk = new MENDChunk(chunk);
					break;
				case TERMChunk.NAME:
					chunk = new TERMChunk(chunk);
					break;
				case BACKChunk.NAME:
					chunk = new BACKChunk(chunk);
					break;
				case BKGDChunk.NAME:
					chunk = new BKGDChunk(chunk);
					break;
				case fcTLChunk.NAME:
					// This is the beginning of a new embedded PNG
					chunk = new fcTLChunk(chunk);
					png = new PNG();
					png.setFCTL(chunk instanceof fcTLChunk ? (fcTLChunk)chunk : null);
					png.setIHDR(_IhdrChunk);
					_Pngs.add(png);
					break;
				case IHDRChunk.NAME:
					chunk = new IHDRChunk(chunk);
					_IhdrChunk = chunk instanceof IHDRChunk ? (IHDRChunk)chunk : null;
					break;
				case IDATChunk.NAME:
					chunk = new IDATChunk(chunk);
					if (png != null)
					{
						png.setIDAT(chunk instanceof IDATChunk ? (IDATChunk)chunk : null);
					}
					break;
				case fdATChunk.NAME:
					chunk = new fdATChunk(chunk);
					if (png != null)
					{
						chunk.setChunkType(IDATChunk.NAME);
						png.setIDAT(new IDATChunk(chunk));
					}
					break;
				case IENDChunk.NAME:
					chunk = new IENDChunk(chunk);
					for (int i = 0; i < _Pngs.size(); i++)
					{
						_Pngs.get(i).setIEND(chunk instanceof IENDChunk ? (IENDChunk)chunk : null);
					}
					break;
				default:
					break;
			}
			// Add the chunk to our list of chunks
			_Chunks.add(chunk);
		} while (!chunk.getChunkType().equals(IENDChunk.NAME));
	}

	public final void Load(String filename)
	{
		// Open the file for reading
		Load(File.OpenRead(filename));
	}
	public final void Load(Uri uri)
	{
		System.Windows.Resources.StreamResourceInfo SRI = Application.GetResourceStream(uri);
		Load(SRI.Stream);
	}


	public final Iterator iterator()
	{
		return getBitmaps().iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Internal Classes


	public static class Utils
	{
		/** 
		 Attempts to read count bytes of data from the supplied stream.
		 
		 @param stream The stream to read from
		 @param count The number of bytes to read
		 @return A byte[] containing the data or null if an error occurred
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] ReadStream(Stream stream, uint count)
		public static byte[] ReadStream(InputStream stream, int count)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] bytes = new byte[count];
			byte[] bytes = new byte[count];
			try
			{
				stream.read(bytes, 0, (int)count);
			}
			catch (IOException e)
			{
				throw e;
			}
			return bytes;
		}

		/** 
		 Attempts to parse an unsigned integer value from the array of bytes
		 provided.  The most significant byte of the unsigned integer is
		 parsed from the first element in the array.
		 
		 @param buffer An array of bytes from which the value is to be extracted
		 @param uintLengthInBytes The number of bytes to parse (must be <= sizeof(uint))
		 @return The extracted unsigned integer returned in a uint
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static uint ParseUint(byte[] buffer, int uintLengthInBytes)
		public static int ParseUint(byte[] buffer, int uintLengthInBytes)
		{
			int offset = 0;
			tangible.RefObject<Integer> tempRef_offset = new tangible.RefObject<Integer>(offset);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ParseUint(buffer, uintLengthInBytes, ref offset);
			int tempVar = ParseUint(buffer, uintLengthInBytes, tempRef_offset);
		offset = tempRef_offset.argValue;
		return tempVar;
		}

		/** 
		 Attempts to parse an unsigned integer value from the array of bytes
		 provided.  The most significant byte of the unsigned integer is
		 parsed from the specified offset in the array.
		 
		 @param buffer An array of bytes from which the value is to be extracted
		 @param uintLengthInBytes The number of bytes to parse (must be <= sizeof(uint))
		 @param offset The offset in the array of bytes where parsing shall begin
		 @return The extracted unsigned integer returned in a uint
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static uint ParseUint(byte[] buffer, int uintLengthInBytes, ref int offset)
		public static int ParseUint(byte[] buffer, int uintLengthInBytes, tangible.RefObject<Integer> offset)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value = 0;
			int value = 0;
			if (uintLengthInBytes > (Integer.SIZE / Byte.SIZE))
			{
				throw new IllegalArgumentException(String.format("Function can only be used to parse up to %1$s bytes from the buffer", (Integer.SIZE / Byte.SIZE)));
			}
			if (buffer.length - offset.argValue < uintLengthInBytes)
			{
				throw new IllegalArgumentException(String.format("buffer is not long enough to extract %1$s bytes at offset %2$s", (Integer.SIZE / Byte.SIZE), offset.argValue));
			}
			int i, j;
			for (i = offset.argValue + uintLengthInBytes - 1, j = 0; i >= offset.argValue; i--, j++)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: value |= (uint)(buffer[i] << (8 * j));
				value |= (int)(buffer[i] << (8 * j));
			}
			offset.argValue += uintLengthInBytes;
			return value;
		}
	}

	public static class APNGHeader
	{
		/** 
		 The first 8 bytes of an APNG encoding
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static byte[] expectedSignature = { 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a };
		private static byte[] expectedSignature = {(byte)0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a};
		/** 
		 The signature parsed from the input stream
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] signature;
		private byte[] signature;

		/** 
		 Default constructor
		*/
		public APNGHeader()
		{
			signature = null;
		}

		/** 
		 Attempts to read an APNG Header chunk from the supplied stream.
		 
		 @param stream The stream containing the APNG Header
		*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		public final void Read(Stream stream)
		{
			// Stream must be readable
			if (!stream.CanRead)
			{
				throw new IllegalArgumentException("Stream is not readable");
			}

			// Read the signature
			try
			{
				signature = Utils.ReadStream(stream, 8);
			}
			catch (RuntimeException e)
			{
				// Re-throw any exceptions
				throw e;
			}

			// Test signature for validity
			if (signature.length == expectedSignature.length)
			{
				for (int i = 0; i < expectedSignature.length; i++)
				{
					// Invalid signature
					if (expectedSignature[i] != signature[i])
					{
						throw new RuntimeException("APNG signature not found.");
					}
				}
			}
			else
			{
				// Invalid signature
				throw new RuntimeException("APNG signature not found.");
			}
		}
	}

	public static class MENDChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "MEND";

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public MENDChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}
	}

	public static class TERMChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "TERM";

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint terminationAction;
		private int terminationAction;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint actionAfterTermination;
		private int actionAfterTermination;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint delay;
		private int delay;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint iterationMax;
		private int iterationMax;

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public TERMChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}

		/** 
		 Extracts various fields specific to this chunk from the APNG's
		 data field
		 
		 @param chunkData An array of bytes representing the APNG's data field
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override void ParseData(byte[] chunkData)
		@Override
		protected void ParseData(byte[] chunkData)
		{
			int offset = 0;
			tangible.RefObject<Integer> tempRef_offset = new tangible.RefObject<Integer>(offset);
			terminationAction = Utils.ParseUint(chunkData, 1, tempRef_offset);
		offset = tempRef_offset.argValue;
			// If the data length is > 1 then read 9 more bytes
			if (chunkData.length > 1)
			{
				tangible.RefObject<Integer> tempRef_offset2 = new tangible.RefObject<Integer>(offset);
				actionAfterTermination = Utils.ParseUint(chunkData, 1, tempRef_offset2);
			offset = tempRef_offset2.argValue;
				tangible.RefObject<Integer> tempRef_offset3 = new tangible.RefObject<Integer>(offset);
				delay = Utils.ParseUint(chunkData, 4, tempRef_offset3);
			offset = tempRef_offset3.argValue;
				tangible.RefObject<Integer> tempRef_offset4 = new tangible.RefObject<Integer>(offset);
				iterationMax = Utils.ParseUint(chunkData, 4, tempRef_offset4);
			offset = tempRef_offset4.argValue;
			}
		}
	}

	public static class BKGDChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "bKGD";

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public BKGDChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}
	}

	public static class BACKChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "BACK";

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint redBackground;
		private int redBackground;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint greenBackground;
		private int greenBackground;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint blueBackground;
		private int blueBackground;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint mandatoryBackground;
		private int mandatoryBackground;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint backgroundImageId;
		private int backgroundImageId;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint backgroundTiling;
		private int backgroundTiling;

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public BACKChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}

		/** 
		 Extracts various fields specific to this chunk from the APNG's
		 data field
		 
		 @param chunkData An array of bytes representing the APNG's data field
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override void ParseData(byte[] chunkData)
		@Override
		protected void ParseData(byte[] chunkData)
		{
			int offset = 0;
			tangible.RefObject<Integer> tempRef_offset = new tangible.RefObject<Integer>(offset);
			redBackground = Utils.ParseUint(chunkData, 2, tempRef_offset);
		offset = tempRef_offset.argValue;
			tangible.RefObject<Integer> tempRef_offset2 = new tangible.RefObject<Integer>(offset);
			greenBackground = Utils.ParseUint(chunkData, 2, tempRef_offset2);
		offset = tempRef_offset2.argValue;
			tangible.RefObject<Integer> tempRef_offset3 = new tangible.RefObject<Integer>(offset);
			blueBackground = Utils.ParseUint(chunkData, 2, tempRef_offset3);
		offset = tempRef_offset3.argValue;

			// If the data length is > 6 then read 1 more byte
			if (chunkData.length > 6)
			{
				tangible.RefObject<Integer> tempRef_offset4 = new tangible.RefObject<Integer>(offset);
				mandatoryBackground = Utils.ParseUint(chunkData, 1, tempRef_offset4);
			offset = tempRef_offset4.argValue;
			}
			// If the data length is > 7 then read 2 more bytes
			if (chunkData.length > 7)
			{
				tangible.RefObject<Integer> tempRef_offset5 = new tangible.RefObject<Integer>(offset);
				backgroundImageId = Utils.ParseUint(chunkData, 2, tempRef_offset5);
			offset = tempRef_offset5.argValue;
			}
			// If the data length is > 9 then read 1 more byte
			if (chunkData.length > 9)
			{
				tangible.RefObject<Integer> tempRef_offset6 = new tangible.RefObject<Integer>(offset);
				backgroundTiling = Utils.ParseUint(chunkData, 1, tempRef_offset6);
			offset = tempRef_offset6.argValue;
			}
		}
	}

	public static class IHDRChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "IHDR";

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint width;
		private int width;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint height;
		private int height;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint bitDepth;
		private int bitDepth;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint colorType;
		private int colorType;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint compressionMethod;
		private int compressionMethod;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint filterMethod;
		private int filterMethod;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint interlaceMethod;
		private int interlaceMethod;

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public IHDRChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}

		/** 
		 Extracts various fields specific to this chunk from the APNG's
		 data field
		 
		 @param chunkData An array of bytes representing the APNG's data field
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override void ParseData(byte[] chunkData)
		@Override
		protected void ParseData(byte[] chunkData)
		{
			int offset = 0;
			tangible.RefObject<Integer> tempRef_offset = new tangible.RefObject<Integer>(offset);
			width = Utils.ParseUint(chunkData, 4, tempRef_offset);
		offset = tempRef_offset.argValue;
			tangible.RefObject<Integer> tempRef_offset2 = new tangible.RefObject<Integer>(offset);
			height = Utils.ParseUint(chunkData, 4, tempRef_offset2);
		offset = tempRef_offset2.argValue;
			tangible.RefObject<Integer> tempRef_offset3 = new tangible.RefObject<Integer>(offset);
			bitDepth = Utils.ParseUint(chunkData, 1, tempRef_offset3);
		offset = tempRef_offset3.argValue;
			tangible.RefObject<Integer> tempRef_offset4 = new tangible.RefObject<Integer>(offset);
			colorType = Utils.ParseUint(chunkData, 1, tempRef_offset4);
		offset = tempRef_offset4.argValue;
			tangible.RefObject<Integer> tempRef_offset5 = new tangible.RefObject<Integer>(offset);
			compressionMethod = Utils.ParseUint(chunkData, 1, tempRef_offset5);
		offset = tempRef_offset5.argValue;
			tangible.RefObject<Integer> tempRef_offset6 = new tangible.RefObject<Integer>(offset);
			filterMethod = Utils.ParseUint(chunkData, 1, tempRef_offset6);
		offset = tempRef_offset6.argValue;
			tangible.RefObject<Integer> tempRef_offset7 = new tangible.RefObject<Integer>(offset);
			interlaceMethod = Utils.ParseUint(chunkData, 1, tempRef_offset7);
		offset = tempRef_offset7.argValue;
		}
	}

	public static class fcTLChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "fcTL";

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public fcTLChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}

		public final boolean IsEmpty()
		{
			return getChunkLength() == 0;
		}
	}

	public static class IENDChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "IEND";

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public IENDChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}
	}

	public static class IDATChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "IDAT";

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public IDATChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}
	}

	public static class fdATChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "fdAT";

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public fdATChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}
	}

	public static class acTLChunk extends APNGChunk
	{
		/** 
		 The ASCII name of the APNG chunk
		*/
		public static final String NAME = "acTL";

		/** 
		 The APNG frame width in pixels
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint frameWidth;
		private int frameWidth;
		/** 
		 The APNG frame height in pixels
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint frameHeight;
		private int frameHeight;
		///// <summary>
		///// The APNG frame rate
		///// </summary>
		//private uint ticksPerSecond;
		///// <summary>
		///// The APNG layer count
		///// </summary>
		//private uint nominalLayerCount;
		///// <summary>
		///// The APNG frame count
		///// </summary>
		//private uint nominalFrameCount;
		///// <summary>
		///// The APNG play time
		///// </summary>
		//private uint nominalPlayTime;
		///// <summary>
		///// The APNG simplicity profile
		///// </summary>
		//private uint simplicityProfile;

		/** 
		 Constructor
		 
		 @param chunk The APNG chunk containing the data for this specific chunk
		*/
		public acTLChunk(APNGChunk chunk)
		{
			super(chunk, NAME);
		}

		/** 
		 The APNG width in pixels
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getFrameWidth()
		public final int getFrameWidth()
		{
			return frameWidth;
		}

		/** 
		 The APNG height in pixels
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getFrameHeight()
		public final int getFrameHeight()
		{
			return frameHeight;
		}

		/** 
		 Extracts various fields specific to this chunk from the APNG's
		 data field
		 
		 @param chunkData An array of bytes representing the APNG's data field
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override void ParseData(byte[] chunkData)
		@Override
		protected void ParseData(byte[] chunkData)
		{
			int offset = 0;
			tangible.RefObject<Integer> tempRef_offset = new tangible.RefObject<Integer>(offset);
			frameWidth = Utils.ParseUint(chunkData, 4, tempRef_offset);
		offset = tempRef_offset.argValue;
			tangible.RefObject<Integer> tempRef_offset2 = new tangible.RefObject<Integer>(offset);
			frameHeight = Utils.ParseUint(chunkData, 4, tempRef_offset2);
		offset = tempRef_offset2.argValue;
			//ticksPerSecond = Utils.ParseUint(chunkData, 4, ref offset);
			//nominalLayerCount = Utils.ParseUint(chunkData, 4, ref offset);
			//nominalFrameCount = Utils.ParseUint(chunkData, 4, ref offset);
			//nominalPlayTime = Utils.ParseUint(chunkData, 4, ref offset);
			//simplicityProfile = Utils.ParseUint(chunkData, 4, ref offset);
		}
	}

	public static class APNGChunk
	{
		protected String error;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected byte[] chunkLength;
		protected byte[] chunkLength;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected byte[] chunkType;
		protected byte[] chunkType;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected byte[] chunkData;
		protected byte[] chunkData;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected byte[] chunkCRC;
		protected byte[] chunkCRC;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected uint calculatedCRC;
		protected int calculatedCRC;

		/** 
		 Default constructor
		*/
		public APNGChunk()
		{
			chunkLength = chunkType = chunkData = chunkCRC = null;
			error = "No Error";
		}

		/** 
		 Constructor which takes an existing APNGChunk object and
		 verifies that its type matches that which is expected
		 
		 @param chunk The APNGChunk to copy
		 @param expectedType The input APNGChunk expected type
		*/
		public APNGChunk(APNGChunk chunk, String expectedType)
		{
			// Copy the existing chunks members
			chunkLength = chunk.chunkLength;
			chunkType = chunk.chunkType;
			chunkData = chunk.chunkData;
			chunkCRC = chunk.chunkCRC;

			// Verify the chunk type is as expected
			if (!getChunkType().equals(expectedType))
			{
				throw new IllegalArgumentException(String.format("Specified chunk type is not %1$s as expected", expectedType));
			}

			// Parse the chunk's data
			ParseData(chunkData);
		}

		/** 
		 Extracts various fields specific to this chunk from the APNG's
		 data field
		 
		 @param chunkData An array of bytes representing the APNG's data field
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected virtual void ParseData(byte[] chunkData)
		protected void ParseData(byte[] chunkData)
		{
			// Nothing specific to do here.  Derived classes can override this
			// to do specific field parsing.
		}

		/** 
		 Gets the array of bytes which make up the APNG chunk.  This includes:
		 o 4 bytes of the chunk's length
		 o 4 bytes of the chunk's type
		 o N bytes of the chunk's data
		 o 4 bytes of the chunk's CRC
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getChunk()
		public final byte[] getChunk()
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] ba = new byte[chunkLength.Length + chunkType.Length + chunkData.Length + chunkCRC.Length];
			byte[] ba = new byte[chunkLength.length + chunkType.length + chunkData.length + chunkCRC.length];
			System.arraycopy(chunkLength, 0, ba, 0, chunkLength.length);
			System.arraycopy(chunkType, 0, ba, chunkLength.length, chunkType.length);
			System.arraycopy(chunkData, 0, ba, chunkLength.length + chunkType.length, chunkData.length);
			System.arraycopy(chunkCRC, 0, ba, chunkLength.length + chunkType.length + chunkData.length, chunkCRC.length);
			return ba;
		}

		/** 
		 Gets the array of bytes which make up the chunk's data field
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getChunkData()
		public final byte[] getChunkData()
		{
			return chunkData;
		}

		/** 
		 Gets chunk's type field as an string
		*/
		public final String getChunkType()
		{
			return new String(new char[] {(char)chunkType[0], (char)chunkType[1], (char)chunkType[2], (char)chunkType[3]});
		}
		public final void setChunkType(String value)
		{
			chunkType = value.getBytes(java.nio.charset.StandardCharsets.US_ASCII);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] newChunkData = new byte[chunkData.Length - 4];
			byte[] newChunkData = new byte[chunkData.length - 4];
			System.arraycopy(chunkData, 4, newChunkData, 0, newChunkData.length);
			chunkData = newChunkData;

			chunkLength = BitConverter.GetBytes(chunkData.length);
			Array.Reverse(chunkLength);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint crc = CRC.INITIAL_CRC;
			int crc = CRC.INITIAL_CRC;
			crc = CRC.UpdateCRC(crc, chunkType);
			crc = CRC.UpdateCRC(crc, chunkData);
				// CRC is inverted
			crc = ~crc;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] array = BitConverter.GetBytes(crc);
			byte[] array = BitConverter.GetBytes(crc);
			Array.Reverse(array);
			chunkCRC = array;
		}

		/** 
		 Gets the length field of the chunk
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getChunkLength()
		public final int getChunkLength()
		{
			return Utils.ParseUint(chunkLength, chunkLength.length);
		}

		/** 
		 Gets the CRC field of the chunk
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getChunkCRC()
		public final int getChunkCRC()
		{
			return Utils.ParseUint(chunkCRC, chunkCRC.length);
		}

		/** 
		 Attempts to parse an APNGChunk for the specified stream
		 
		 @param stream The stream containing the APNG Chunk
		*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		public final void Read(Stream stream)
		{
			if (!stream.CanRead)
			{
				throw new IllegalArgumentException("Stream is not readable");
			}

			calculatedCRC = CRC.INITIAL_CRC;

			long chunkStart = stream.Position;

			// Read the data Length
			chunkLength = Utils.ReadStream(stream, 4);

			// Read the chunk type
			chunkType = Utils.ReadStream(stream, 4);
			calculatedCRC = CRC.UpdateCRC(calculatedCRC, chunkType);

			// Read the data
			chunkData = Utils.ReadStream(stream, getChunkLength());
			calculatedCRC = CRC.UpdateCRC(calculatedCRC, chunkData);

			// Read the CRC
			chunkCRC = Utils.ReadStream(stream, 4);

			// CRC is inverted
			calculatedCRC = ~calculatedCRC;

			// Verify the CRC
			if (getChunkCRC() != calculatedCRC)
			{
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("APNG Chunk CRC Mismatch.  Chunk CRC = %1$s, Calculated CRC = %2$s.", getChunkCRC(), calculatedCRC) + "\r\n");
				sb.append(String.format("This occurred while parsing the chunk at position %1$s (0x%1.8X) in the stream.", chunkStart, chunkStart) + "\r\n");
				throw new RuntimeException(sb.toString());
			}
		}
	}

	public static class PNG
	{
		/** 
		 The PNG file signature
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static byte[] header = new byte[] { 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };
		private static byte[] header = new byte[] {(byte)0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
		/** 
		 The PNG file's IHDR chunk
		*/
		private IHDRChunk ihdr;
		/** 
		 The PNG file's PLTE chunk (optional)
		*/
		private fcTLChunk plte;
		/** 
		 The PNG file's IDAT chunks
		*/
		private ArrayList<IDATChunk> idats;
		/** 
		 The PNG file's IEND chunk
		*/
		private IENDChunk iend;

		/** 
		 Default constructor
		*/
		public PNG()
		{
			idats = new ArrayList<IDATChunk>();
		}

		/** 
		 Converts the chunks making up the PNG into a single MemoryStream which
		 is suitable for writing to a PNG file or creating a Image object using
		 Bitmap.FromStream
		 
		 @return MemoryStream
		*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
		public final MemoryStream ToStream()
		{
			ByteArrayOutputStream ms = new ByteArrayOutputStream();
			ms.write(header, 0, header.length);
			ms.write(ihdr.getChunk(), 0, ihdr.getChunk().length);
			for (IDATChunk chunk : idats)
			{
				ms.write(chunk.getChunk(), 0, chunk.getChunk().length);
			}
			ms.write(iend.getChunk(), 0, iend.getChunk().length);
			return ms;
		}

		public final void SaveFile(String FileName)
		{
			try (FileStream writer = new FileStream(FileName, FileMode.Create))
			{
				ByteArrayInputStream mem = ToStream();
				mem.Position = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] content = new byte[mem.Length];
				byte[] content = new byte[mem.Length];
				mem.read(content, 0, content.length);
				writer.Write(content, 0, content.length);
				writer.Close();
			}
		}

		/** 
		 Gets or Sets the PNG's IHDR chunk
		*/
		public final IHDRChunk getIHDR()
		{
			return ihdr;
		}
		public final void setIHDR(IHDRChunk value)
		{
			ihdr = value;
		}

		/** 
		 Gets or Sets the PNG's PLTE chunk
		*/
		public final fcTLChunk getFCTL()
		{
			return plte;
		}
		public final void setFCTL(fcTLChunk value)
		{
			plte = value;
		}

		/** 
		 Gets or Sets the PNG's IEND chunk
		*/
		public final IENDChunk getIEND()
		{
			return iend;
		}
		public final void setIEND(IENDChunk value)
		{
			iend = value;
		}

		/** 
		 Gets the list of IDAT chunk's making up the PNG
		*/
		public final ArrayList<IDATChunk> getIDATS()
		{
			return idats;
		}

		/** 
		 Adds the assigned IDAT chunk to the end of the PNG's list of IDAT chunks
		*/
		public final void setIDAT(IDATChunk value)
		{
			idats.add(value);
		}

		private Bitmap _RelatedBitmap;
		public final Bitmap getRelatedBitmap()
		{
			if (_RelatedBitmap == null)
			{
				_RelatedBitmap = (Bitmap)Bitmap.FromStream(this.ToStream());
			}
			return _RelatedBitmap;
		}
		private BitmapImage _RelatedBitmapImage;
		public final BitmapImage getRelatedBitmapImage()
		{
			if (_RelatedBitmapImage == null)
			{
				_RelatedBitmapImage = new BitmapImage();
				_RelatedBitmapImage.BeginInit();
				_RelatedBitmapImage.StreamSource = this.ToStream();
				_RelatedBitmapImage.EndInit();
			}
			return _RelatedBitmapImage;
		}
	}

	public static class CRC
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static uint[] crcTable;
		private static int[] crcTable;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint INITIAL_CRC = 0xFFFFFFFF;
		public static final int INITIAL_CRC = (int)0xFFFFFFFF;

		private static void MakeCRCTable()
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: crcTable = new uint[256];
			crcTable = new int[256];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint c, n, k;
			int c, n, k;

			for (n = 0; n < crcTable.length; n++)
			{
				c = n;
				for (k = 0; k < 8; k++)
				{
					if ((c & 1) != 0)
					{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
						c = 0xedb88320 ^ (c >>> 1);
					}
					else
					{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
						c = c >>> 1;
					}
				}
				crcTable[n] = c;
			}
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static uint UpdateCRC(uint crc, byte[] bytes)
		public static int UpdateCRC(int crc, byte[] bytes)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint c = crc;
			int c = crc;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint n;
			int n;
			if (crcTable == null)
			{
				MakeCRCTable();
			}
			for (n = 0; n < bytes.length; n++)
			{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
				c = crcTable[(c ^ bytes[n]) & 0xff] ^ (c >>> 8);
			}
			return c;


		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static uint Calculate(byte[] bytes)
		public static int Calculate(byte[] bytes)
		{
			return UpdateCRC(INITIAL_CRC, bytes);
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



}