package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Encryption.*;
import Rasad.Core.Compression.Core.*;
import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.Compression.Zip.Compression.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;

 </code>
 </example>
*/
public class ZipFile implements java.lang.Iterable, Closeable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region KeyHandling

	/** 
	 Delegate for handling keys/password setting during compresion/decompression.
	*/
	@FunctionalInterface
	public interface KeysRequiredEventHandler
	{
		void invoke(Object sender, KeysRequiredEventArgs e);
	}

	/** 
	 Event handler for handling encryption keys.
	*/
	public KeysRequiredEventHandler KeysRequired;

	/** 
	 Handles getting of encryption keys when required.
	 
	 @param fileName The file for which encryption keys are required.
	*/
	private void OnKeysRequired(String fileName)
	{
		if (KeysRequired != null)
		{
			KeysRequiredEventArgs krea = new KeysRequiredEventArgs(fileName, key);
			KeysRequired.invoke(this, krea);
			key = krea.getKey();
		}
	}

	/** 
	 Get/set the encryption key value.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] getKey()
	private byte[] getKey()
	{
		return key;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setKey(byte[] value)
	private void setKey(byte[] value)
	{
		key = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
	/** 
	 Password to be used for encrypting/decrypting files.
	 
	 Set to null if no password is required.
	*/
	public final void setPassword(String value)
	{
		if ((value == null) || (value.length() == 0))
		{
			key = null;
		}
		else
		{
			rawPassword_ = value;
			key = PkzipClassic.GenerateKeys(ZipConstants.ConvertToArray(value));
		}
	}
//#endif

	/** 
	 Get a value indicating wether encryption keys are currently available.
	*/
	private boolean getHaveKeys()
	{
		return key != null;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Opens a Zip file with the given name for reading.
	 
	 @param name The name of the file to open.
	 @exception ArgumentNullException The argument supplied is null.
	 @exception IOException
	 An i/o error occurs
	 
	 @exception ZipException
	 The file doesn't contain a valid zip archive.
	 
	*/
	public ZipFile(String name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		name_ = name;

		baseStream_ = File.Open(name, FileMode.Open, FileAccess.Read, FileShare.Read);
		isStreamOwner = true;

		try
		{
			ReadEntries();
		}
		catch (java.lang.Exception e)
		{
			DisposeInternal(true);
			throw e;
		}
	}

	/** 
	 Opens a Zip file reading the given <see cref="FileStream"/>.
	 
	 @param file The <see cref="FileStream"/> to read archive data from.
	 @exception ArgumentNullException The supplied argument is null.
	 @exception IOException
	 An i/o error occurs.
	 
	 @exception ZipException
	 The file doesn't contain a valid zip archive.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
	public ZipFile(FileStream file)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (!file.CanSeek)
		{
			throw new IllegalArgumentException("Stream is not seekable", "file");
		}

		baseStream_ = file;
		name_ = file.Name;
		isStreamOwner = true;

		try
		{
			ReadEntries();
		}
		catch (java.lang.Exception e)
		{
			DisposeInternal(true);
			throw e;
		}
	}

	/** 
	 Opens a Zip file reading the given <see cref="Stream"/>.
	 
	 @param stream The <see cref="Stream"/> to read archive data from.
	 @exception IOException
	 An i/o error occurs
	 
	 @exception ZipException
	 The stream doesn't contain a valid zip archive.<br/>
	 
	 @exception ArgumentException
	 The <see cref="Stream">stream</see> doesnt support seeking.
	 
	 @exception ArgumentNullException
	 The <see cref="Stream">stream</see> argument is null.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public ZipFile(Stream stream)
	{
		if (stream == null)
		{
			throw new NullPointerException("stream");
		}

		if (!stream.CanSeek)
		{
			throw new IllegalArgumentException("Stream is not seekable", "stream");
		}

		baseStream_ = stream;
		isStreamOwner = true;

		if (baseStream_.Length > 0)
		{
			try
			{
				ReadEntries();
			}
			catch (java.lang.Exception e)
			{
				DisposeInternal(true);
				throw e;
			}
		}
		else
		{
			entries_ = new ZipEntry[0];
			isNewArchive_ = true;
		}
	}

	/** 
	 Initialises a default <see cref="ZipFile"/> instance with no entries and no file storage.
	*/
	public ZipFile()
	{
		entries_ = new ZipEntry[0];
		isNewArchive_ = true;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Destructors and Closing
	/** 
	 Finalize this instance.
	*/
	protected void finalize() throws Throwable
	{
		Dispose(false);
	}

	/** 
	 Closes the ZipFile.  If the stream is <see cref="IsStreamOwner">owned</see> then this also closes the underlying input stream.
	 Once closed, no further instance methods should be called.
	 
	 @exception System.IO.IOException
	 An i/o error occurs.
	 
	*/
	public final void Close()
	{
		DisposeInternal(true);
		GC.SuppressFinalize(this);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Creators
	/** 
	 Create a new <see cref="ZipFile"/> whose data will be stored in a file.
	 
	 @param fileName The name of the archive to create.
	 @return Returns the newly created <see cref="ZipFile"/>
	 @exception ArgumentNullException <paramref name="fileName"></paramref> is null
	*/
	public static ZipFile Create(String fileName)
	{
		if (fileName == null)
		{
			throw new NullPointerException("fileName");
		}

		FileOutputStream fs = File.Create(fileName);

		ZipFile result = new ZipFile();
		result.name_ = fileName;
		result.baseStream_ = fs;
		result.isStreamOwner = true;
		return result;
	}

	/** 
	 Create a new <see cref="ZipFile"/> whose data will be stored on a stream.
	 
	 @param outStream The stream providing data storage.
	 @return Returns the newly created <see cref="ZipFile"/>
	 @exception ArgumentNullException <paramref name="outStream"> is null</paramref>
	 @exception ArgumentException <paramref name="outStream"> doesnt support writing.</paramref>
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public static ZipFile Create(Stream outStream)
	{
		if (outStream == null)
		{
			throw new NullPointerException("outStream");
		}

		if (!outStream.CanWrite)
		{
			throw new IllegalArgumentException("Stream is not writeable", "outStream");
		}

		if (!outStream.CanSeek)
		{
			throw new IllegalArgumentException("Stream is not seekable", "outStream");
		}

		ZipFile result = new ZipFile();
		result.baseStream_ = outStream;
		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	/** 
	 Get/set a flag indicating if the underlying stream is owned by the ZipFile instance.
	 If the flag is true then the stream will be closed when <see cref="Close">Close</see> is called.
	 
	 
	 The default value is true in all cases.
	 
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
	 Get a value indicating wether
	 this archive is embedded in another file or not.
	*/
		// Not strictly correct in all circumstances currently
	public final boolean getIsEmbeddedArchive()
	{
		return offsetOfFirstEntry > 0;
	}

	/** 
	 Get a value indicating that this archive is a new one.
	*/
	public final boolean getIsNewArchive()
	{
		return isNewArchive_;
	}

	/** 
	 Gets the comment for the zip file.
	*/
	public final String getZipFileComment()
	{
		return comment_;
	}

	/** 
	 Gets the name of this zip file.
	*/
	public final String getName()
	{
		return name_;
	}

	/** 
	 Gets the number of entries in this zip file.
	 
	 @exception InvalidOperationException
	 The Zip file has been closed.
	 
	*/
	@Deprecated
	public final int getSize()
	{
		return entries_.length;
	}

	/** 
	 Get the number of entries contained in this <see cref="ZipFile"/>.
	*/
	public final long getCount()
	{
		return entries_.length;
	}

	/** 
	 Indexer property for ZipEntries
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Runtime.CompilerServices.IndexerNameAttribute("EntryByIndex")] public ZipEntry this[int index]
	public final ZipEntry get(int index)
	{
		return (ZipEntry) entries_[index].Clone();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Input Handling
	/** 
	 Gets an enumerator for the Zip entries in this Zip file.
	 
	 @return Returns an <see cref="IEnumerator"/> for this archive.
	 @exception ObjectDisposedException
	 The Zip file has been closed.
	 
	*/
	public final Iterator iterator()
	{
		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		return new ZipEntryEnumerator(entries_);
	}

	/** 
	 Return the index of the entry with a matching name
	 
	 @param name Entry name to find
	 @param ignoreCase If true the comparison is case insensitive
	 @return The index position of the matching entry or -1 if not found
	 @exception ObjectDisposedException
	 The Zip file has been closed.
	 
	*/
	public final int FindEntry(String name, boolean ignoreCase)
	{
		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		// TODO: This will be slow as the next ice age for huge archives!
		for (int i = 0; i < entries_.length; i++)
		{
			if (String.Compare(name, entries_[i].getName(), ignoreCase, CultureInfo.InvariantCulture) == 0)
			{
				return i;
			}
		}
		return -1;
	}

	/** 
	 Searches for a zip entry in this archive with the given name.
	 String comparisons are case insensitive
	 
	 @param name
	 The name to find. May contain directory components separated by slashes ('/').
	 
	 @return 
	 A clone of the zip entry, or null if no entry with that name exists.
	 
	 @exception ObjectDisposedException
	 The Zip file has been closed.
	 
	*/
	public final ZipEntry GetEntry(String name)
	{
		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		int index = FindEntry(name, true);
		return (index >= 0) ? (ZipEntry) entries_[index].Clone() : null;
	}

	/** 
	 Gets an input stream for reading the given zip entry data in an uncompressed form.
	 Normally the <see cref="ZipEntry"/> should be an entry returned by GetEntry().
	 
	 @param entry The <see cref="ZipEntry"/> to obtain a data <see cref="Stream"/> for
	 @return An input <see cref="Stream"/> containing data for this <see cref="ZipEntry"/>
	 @exception ObjectDisposedException
	 The ZipFile has already been closed
	 
	 @exception Rasad.Core.Compression.Zip.ZipException
	 The compression method for the entry is unknown
	 
	 @exception IndexOutOfRangeException
	 The entry is not found in the ZipFile
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final Stream GetInputStream(ZipEntry entry)
	{
		if (entry == null)
		{
			throw new NullPointerException("entry");
		}

		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		long index = entry.getZipFileIndex();
		if ((index < 0) || (index >= entries_.length) || (!entries_[index].getName().equals(entry.getName())))
		{
			index = FindEntry(entry.getName(), true);
			if (index < 0)
			{
				throw new ZipException("Entry cannot be found");
			}
		}
		return GetInputStream(index);
	}

	/** 
	 Creates an input stream reading a zip entry
	 
	 @param entryIndex The index of the entry to obtain an input stream for.
	 @return 
	 An input <see cref="Stream"/> containing data for this <paramref name="entryIndex"/>
	 
	 @exception ObjectDisposedException
	 The ZipFile has already been closed
	 
	 @exception Rasad.Core.Compression.Zip.ZipException
	 The compression method for the entry is unknown
	 
	 @exception IndexOutOfRangeException
	 The entry is not found in the ZipFile
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final Stream GetInputStream(long entryIndex)
	{
		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		long start = LocateEntry(entries_[entryIndex]);
		CompressionMethod method = entries_[entryIndex].getCompressionMethod();
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		Stream result = new PartialInputStream(this, start, entries_[entryIndex].getCompressedSize());

		if (entries_[entryIndex].getIsCrypted() == true)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new ZipException("decryption not supported for Compact Framework 1.0");
//#else
			result = CreateAndInitDecryptionStream(result, entries_[entryIndex]);
			if (result == null)
			{
				throw new ZipException("Unable to decrypt this entry");
			}
//#endif
		}

		switch (method)
		{
			case Stored:
				// read as is.
				break;

			case Deflated:
				// No need to worry about ownership and closing as underlying stream close does nothing.
				result = new InflaterInputStream(result, new Inflater(true));
				break;

			default:
				throw new ZipException("Unsupported compression method " + method);
		}

		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Archive Testing
	/** 
	 Test an archive for integrity/validity
	 
	 @param testData Perform low level data Crc check
	 @return true if all tests pass, false otherwise
	 Testing will terminate on the first error found.
	*/
	public final boolean TestArchive(boolean testData)
	{
		return TestArchive(testData, TestStrategy.FindFirstError, null);
	}

	/** 
	 Test an archive for integrity/validity
	 
	 @param testData Perform low level data Crc check
	 @param strategy The <see cref="TestStrategy"></see> to apply.
	 @param resultHandler The <see cref="ZipTestResultHandler"></see> handler to call during testing.
	 @return true if all tests pass, false otherwise
	 @exception ObjectDisposedException The object has already been closed.
	*/
	public final boolean TestArchive(boolean testData, TestStrategy strategy, ZipTestResultHandler resultHandler)
	{
		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		TestStatus status = new TestStatus(this);

		if (resultHandler != null)
		{
			resultHandler.invoke(status, null);
		}

		HeaderTest test = testData ? (HeaderTest.Header.getValue() | HeaderTest.Extract.getValue()) : HeaderTest.Header;

		boolean testing = true;

		try
		{
			int entryIndex = 0;

			while (testing && (entryIndex < getCount()))
			{
				if (resultHandler != null)
				{
					status.SetEntry(this.get(entryIndex));
					status.SetOperation(TestOperation.EntryHeader);
					resultHandler.invoke(status, null);
				}

				try
				{
					TestLocalHeader(this.get(entryIndex), test);
				}
				catch (ZipException ex)
				{
					status.AddError();

					if (resultHandler != null)
					{
						resultHandler.invoke(status, String.format("Exception during test - '%1$s'", ex.getMessage()));
					}

					if (strategy == TestStrategy.FindFirstError)
					{
						testing = false;
					}
				}

				if (testing && testData && this.get(entryIndex).getIsFile())
				{
					if (resultHandler != null)
					{
						status.SetOperation(TestOperation.EntryData);
						resultHandler.invoke(status, null);
					}

					Crc32 crc = new Crc32();

					try (Stream entryStream = this.GetInputStream(this.get(entryIndex)))
					{

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer = new byte[4096];
						byte[] buffer = new byte[4096];
						long totalBytes = 0;
						int bytesRead;
						while ((bytesRead = entryStream.Read(buffer, 0, buffer.length)) > 0)
						{
							crc.Update(buffer, 0, bytesRead);

							if (resultHandler != null)
							{
								totalBytes += bytesRead;
								status.SetBytesTested(totalBytes);
								resultHandler.invoke(status, null);
							}
						}
					}

					if (this.get(entryIndex).getCrc() != crc.getValue())
					{
						status.AddError();

						if (resultHandler != null)
						{
							resultHandler.invoke(status, "CRC mismatch");
						}

						if (strategy == TestStrategy.FindFirstError)
						{
							testing = false;
						}
					}

					if ((this.get(entryIndex).getFlags() & GeneralBitFlags.Descriptor.getValue()) != 0)
					{
						ZipHelperStream helper = new ZipHelperStream(baseStream_);
						DescriptorData data = new DescriptorData();
						helper.ReadDataDescriptor(this.get(entryIndex).getLocalHeaderRequiresZip64(), data);
						if (this.get(entryIndex).getCrc() != data.getCrc())
						{
							status.AddError();
						}

						if (this.get(entryIndex).getCompressedSize() != data.getCompressedSize())
						{
							status.AddError();
						}

						if (this.get(entryIndex).getSize() != data.getSize())
						{
							status.AddError();
						}
					}
				}

				if (resultHandler != null)
				{
					status.SetOperation(TestOperation.EntryComplete);
					resultHandler.invoke(status, null);
				}

				entryIndex += 1;
			}

			if (resultHandler != null)
			{
				status.SetOperation(TestOperation.MiscellaneousTests);
				resultHandler.invoke(status, null);
			}

			// TODO: the 'Corrina Johns' test where local headers are missing from
			// the central directory.  They are therefore invisible to many archivers.
		}
		catch (RuntimeException ex)
		{
			status.AddError();

			if (resultHandler != null)
			{
				resultHandler.invoke(status, String.format("Exception during test - '%1$s'", ex.getMessage()));
			}
		}

		if (resultHandler != null)
		{
			status.SetOperation(TestOperation.Complete);
			status.SetEntry(null);
			resultHandler.invoke(status, null);
		}

		return (status.getErrorCount() == 0);
	}

	private class HeaderTest
	{
		public static final HeaderTest Extract = new HeaderTest(0x01); // Check that this header represents an entry whose data can be extracted
		public static final HeaderTest Header = new HeaderTest(0x02); // Check that this header contents are valid

		public static final int SIZE = java.lang.Integer.SIZE;

		private int intValue;
		private static java.util.HashMap<Integer, HeaderTest> mappings;
		private static java.util.HashMap<Integer, HeaderTest> getMappings()
		{
			if (mappings == null)
			{
				synchronized (HeaderTest.class)
				{
					if (mappings == null)
					{
						mappings = new java.util.HashMap<Integer, HeaderTest>();
					}
				}
			}
			return mappings;
		}

		private HeaderTest(int value)
		{
			intValue = value;
			synchronized (HeaderTest.class)
			{
				getMappings().put(value, this);
			}
		}

		public int getValue()
		{
			return intValue;
		}

		public static HeaderTest forValue(int value)
		{
			synchronized (HeaderTest.class)
			{
				HeaderTest enumObj = getMappings().get(value);
				if (enumObj == null)
				{
					return new HeaderTest(value);
				}
				else
				{
					return enumObj;
				}
			}
		}
	}

	/** 
	 Test a local header against that provided from the central directory
	 
	 @param entry
	 The entry to test against
	 
	 @param tests The type of <see cref="HeaderTest">tests</see> to carry out.
	 @return The offset of the entries data in the file
	*/
	private long TestLocalHeader(ZipEntry entry, HeaderTest tests)
	{
		synchronized (baseStream_)
		{
			boolean testHeader = (tests.getValue() & HeaderTest.Header.getValue()) != 0;
			boolean testData = (tests.getValue() & HeaderTest.Extract.getValue()) != 0;

			baseStream_.Seek(offsetOfFirstEntry + entry.getOffset(), SeekOrigin.Begin);
			if ((int)ReadLEUint() != ZipConstants.LocalHeaderSignature)
			{
				throw new ZipException(String.format("Wrong local header signature @%X", offsetOfFirstEntry + entry.getOffset()));
			}

			short extractVersion = (short)ReadLEUshort();
			short localFlags = (short)ReadLEUshort();
			short compressionMethod = (short)ReadLEUshort();
			short fileTime = (short)ReadLEUshort();
			short fileDate = (short)ReadLEUshort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint crcValue = ReadLEUint();
			int crcValue = ReadLEUint();
			long compressedSize = ReadLEUint();
			long size = ReadLEUint();
			int storedNameLength = ReadLEUshort();
			int extraDataLength = ReadLEUshort();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] nameData = new byte[storedNameLength];
			byte[] nameData = new byte[storedNameLength];
			StreamUtils.ReadFully(baseStream_, nameData);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] extraData = new byte[extraDataLength];
			byte[] extraData = new byte[extraDataLength];
			StreamUtils.ReadFully(baseStream_, extraData);

			ZipExtraData localExtraData = new ZipExtraData(extraData);

			// Extra data / zip64 checks
			if (localExtraData.Find(1))
			{
				// 2010-03-04 Forum 10512: removed checks for version >= ZipConstants.VersionZip64
				// and size or compressedSize = MaxValue, due to rogue creators.

				size = localExtraData.ReadLong();
				compressedSize = localExtraData.ReadLong();

				if ((localFlags & GeneralBitFlags.Descriptor.getValue()) != 0)
				{
					// These may be valid if patched later
					if ((size != -1) && (size != entry.getSize()))
					{
						throw new ZipException("Size invalid for descriptor");
					}

					if ((compressedSize != -1) && (compressedSize != entry.getCompressedSize()))
					{
						throw new ZipException("Compressed size invalid for descriptor");
					}
				}
			}
			else
			{
				// No zip64 extra data but entry requires it.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((extractVersion >= ZipConstants.VersionZip64) && (((uint)size == uint.MaxValue) || ((uint)compressedSize == uint.MaxValue)))
				if ((extractVersion >= ZipConstants.VersionZip64) && (((int)size == Integer.MAX_VALUE) || ((int)compressedSize == Integer.MAX_VALUE)))
				{
					throw new ZipException("Required Zip64 extended information missing");
				}
			}

			if (testData)
			{
				if (entry.getIsFile())
				{
					if (!entry.IsCompressionMethodSupported())
					{
						throw new ZipException("Compression method not supported");
					}

					if ((extractVersion > ZipConstants.VersionMadeBy) || ((extractVersion > 20) && (extractVersion < ZipConstants.VersionZip64)))
					{
						throw new ZipException(String.format("Version required to extract this entry not supported (%1$s)", extractVersion));
					}

					if ((localFlags & GeneralBitFlags.Patched.getValue() | GeneralBitFlags.StrongEncryption.getValue() | GeneralBitFlags.EnhancedCompress.getValue() | GeneralBitFlags.HeaderMasked.getValue()) != 0)
					{
						throw new ZipException("The library does not support the zip version required to extract this entry");
					}
				}
			}

			if (testHeader)
			{
				if ((extractVersion <= 63) && (extractVersion != 10) && (extractVersion != 11) && (extractVersion != 20) && (extractVersion != 21) && (extractVersion != 25) && (extractVersion != 27) && (extractVersion != 45) && (extractVersion != 46) && (extractVersion != 50) && (extractVersion != 51) && (extractVersion != 52) && (extractVersion != 61) && (extractVersion != 62) && (extractVersion != 63))
				{
					throw new ZipException(String.format("Version required to extract this entry is invalid (%1$s)", extractVersion));
				}

				// Local entry flags dont have reserved bit set on.
				if ((localFlags & GeneralBitFlags.ReservedPKware4.getValue() | GeneralBitFlags.ReservedPkware14.getValue() | GeneralBitFlags.ReservedPkware15.getValue()) != 0)
				{
					throw new ZipException("Reserved bit flags cannot be set.");
				}

				// Encryption requires extract version >= 20
				if (((localFlags & GeneralBitFlags.Encrypted.getValue()) != 0) && (extractVersion < 20))
				{
					throw new ZipException(String.format("Version required to extract this entry is too low for encryption (%1$s)", extractVersion));
				}

				// Strong encryption requires encryption flag to be set and extract version >= 50.
				if ((localFlags & GeneralBitFlags.StrongEncryption.getValue()) != 0)
				{
					if ((localFlags & GeneralBitFlags.Encrypted.getValue()) == 0)
					{
						throw new ZipException("Strong encryption flag set but encryption flag is not set");
					}

					if (extractVersion < 50)
					{
						throw new ZipException(String.format("Version required to extract this entry is too low for encryption (%1$s)", extractVersion));
					}
				}

				// Patched entries require extract version >= 27
				if (((localFlags & GeneralBitFlags.Patched.getValue()) != 0) && (extractVersion < 27))
				{
					throw new ZipException(String.format("Patched data requires higher version than (%1$s)", extractVersion));
				}

				// Central header flags match local entry flags.
				if (localFlags != entry.getFlags())
				{
					throw new ZipException("Central header/local header flags mismatch");
				}

				// Central header compression method matches local entry
				if (entry.getCompressionMethod() != CompressionMethod.forValue(compressionMethod))
				{
					throw new ZipException("Central header/local header compression method mismatch");
				}

				if (entry.getVersion() != extractVersion)
				{
					throw new ZipException("Extract version mismatch");
				}

				// Strong encryption and extract version match
				if ((localFlags & GeneralBitFlags.StrongEncryption.getValue()) != 0)
				{
					if (extractVersion < 62)
					{
						throw new ZipException("Strong encryption flag set but version not high enough");
					}
				}

				if ((localFlags & GeneralBitFlags.HeaderMasked.getValue()) != 0)
				{
					if ((fileTime != 0) || (fileDate != 0))
					{
						throw new ZipException("Header masked set but date/time values non-zero");
					}
				}

				if ((localFlags & GeneralBitFlags.Descriptor.getValue()) == 0)
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (crcValue != (uint)entry.Crc)
					if (crcValue != (int)entry.getCrc())
					{
						throw new ZipException("Central header/local header crc mismatch");
					}
				}

				// Crc valid for empty entry.
				// This will also apply to streamed entries where size isnt known and the header cant be patched
				if ((size == 0) && (compressedSize == 0))
				{
					if (crcValue != 0)
					{
						throw new ZipException("Invalid CRC for empty entry");
					}
				}

				// TODO: make test more correct...  can't compare lengths as was done originally as this can fail for MBCS strings
				// Assuming a code page at this point is not valid?  Best is to store the name length in the ZipEntry probably
				if (entry.getName().length() > storedNameLength)
				{
					throw new ZipException("File name length mismatch");
				}

				// Name data has already been read convert it and compare.
				String localName = ZipConstants.ConvertToStringExt(localFlags, nameData);

				// Central directory and local entry name match
				if (!entry.getName().equals(localName))
				{
					throw new ZipException("Central header and local header file name mismatch");
				}

				// Directories have zero actual size but can have compressed size
				if (entry.getIsDirectory())
				{
					if (size > 0)
					{
						throw new ZipException("Directory cannot have size");
					}

					// There may be other cases where the compressed size can be greater than this?
					// If so until details are known we will be strict.
					if (entry.getIsCrypted())
					{
						if (compressedSize > ZipConstants.CryptoHeaderSize + 2)
						{
							throw new ZipException("Directory compressed size invalid");
						}
					}
					else if (compressedSize > 2)
					{
						// When not compressed the directory size can validly be 2 bytes
						// if the true size wasnt known when data was originally being written.
						// NOTE: Versions of the library 0.85.4 and earlier always added 2 bytes
						throw new ZipException("Directory compressed size invalid");
					}
				}

				if (!ZipNameTransform.IsValidName(localName, true))
				{
					throw new ZipException("Name is invalid");
				}
			}

			// Tests that apply to both data and header.

			// Size can be verified only if it is known in the local header.
			// it will always be known in the central header.
			if (((localFlags & GeneralBitFlags.Descriptor.getValue()) == 0) || ((size > 0) || (compressedSize > 0)))
			{

				if (size != entry.getSize())
				{
					throw new ZipException(String.format("Size mismatch between central header(%1$s) and local header(%2$s)", entry.getSize(), size));
				}

				if (compressedSize != entry.getCompressedSize() && compressedSize != 0xFFFFFFFFL && compressedSize != -1)
				{
					throw new ZipException(String.format("Compressed size mismatch between central header(%1$s) and local header(%2$s)", entry.getCompressedSize(), compressedSize));
				}
			}

			int extraLength = storedNameLength + extraDataLength;
			return offsetOfFirstEntry + entry.getOffset() + ZipConstants.LocalHeaderBaseSize + extraLength;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Updating

	private static final int DefaultBufferSize = 4096;

	/** 
	 The kind of update to apply.
	*/
	private enum UpdateCommand
	{
		Copy, // Copy original file contents.
		Modify, // Change encryption, compression, attributes, name, time etc, of an existing file.
		Add; // Add a new file to the archive.

		public static final int SIZE = java.lang.Integer.SIZE;

		public int getValue()
		{
			return this.ordinal();
		}

		public static UpdateCommand forValue(int value)
		{
			return values()[value];
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	/** 
	 Get / set the <see cref="INameTransform"/> to apply to names when updating.
	*/
	public final INameTransform getNameTransform()
	{
		return updateEntryFactory_.getNameTransform();
	}

	public final void setNameTransform(INameTransform value)
	{
		updateEntryFactory_.setNameTransform(value);
	}

	/** 
	 Get/set the <see cref="IEntryFactory"/> used to generate <see cref="ZipEntry"/> values
	 during updates.
	*/
	public final IEntryFactory getEntryFactory()
	{
		return updateEntryFactory_;
	}

	public final void setEntryFactory(IEntryFactory value)
	{
		if (value == null)
		{
			updateEntryFactory_ = new ZipEntryFactory();
		}
		else
		{
			updateEntryFactory_ = value;
		}
	}

	/** 
	 Get /set the buffer size to be used when updating this zip file.
	*/
	public final int getBufferSize()
	{
		return bufferSize_;
	}
	public final void setBufferSize(int value)
	{
		if (value < 1024)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("value");
//#else
			throw new IndexOutOfBoundsException("value", "cannot be below 1024");
//#endif
		}

		if (bufferSize_ != value)
		{
			bufferSize_ = value;
			copyBuffer_ = null;
		}
	}

	/** 
	 Get a value indicating an update has <see cref="BeginUpdate()">been started</see>.
	*/
	public final boolean getIsUpdating()
	{
		return updates_ != null;
	}

	/** 
	 Get / set a value indicating how Zip64 Extension usage is determined when adding entries.
	*/
	public final UseZip64 getUseZip64()
	{
		return useZip64_;
	}
	public final void setUseZip64(UseZip64 value)
	{
		useZip64_ = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Immediate updating
//		TBD: Direct form of updating
// 
//		public void Update(IEntryMatcher deleteMatcher)
//		{
//		}
//
//		public void Update(IScanner addScanner)
//		{
//		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Deferred Updating
	/** 
	 Begin updating this <see cref="ZipFile"/> archive.
	 
	 @param archiveStorage The <see cref="IArchiveStorage">archive storage</see> for use during the update.
	 @param dataSource The <see cref="IDynamicDataSource">data source</see> to utilise during updating.
	 @exception ObjectDisposedException ZipFile has been closed.
	 @exception ArgumentNullException One of the arguments provided is null
	 @exception ObjectDisposedException ZipFile has been closed.
	*/
	public final void BeginUpdate(IArchiveStorage archiveStorage, IDynamicDataSource dataSource)
	{
		if (archiveStorage == null)
		{
			throw new NullPointerException("archiveStorage");
		}

		if (dataSource == null)
		{
			throw new NullPointerException("dataSource");
		}

		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		if (getIsEmbeddedArchive())
		{
			throw new ZipException("Cannot update embedded/SFX archives");
		}

		archiveStorage_ = archiveStorage;
		updateDataSource_ = dataSource;

		// NOTE: the baseStream_ may not currently support writing or seeking.

		updateIndex_ = new Hashtable();

		updates_ = new ArrayList(entries_.length);
		for (ZipEntry entry : entries_)
		{
			updates_.add(new ZipUpdate(entry));
			int index = updates_.size() - 1;
			updateIndex_.put(entry.getName(), index);
		}

		// We must sort by offset before using offset's calculated sizes
		Collections.sort(updates_, new UpdateComparer());

		int idx = 0;
		for (ZipUpdate update : updates_)
		{
			//If last entry, there is no next entry offset to use
			if (idx == updates_.size() - 1)
			{
				break;
			}

			update.setOffsetBasedSize(((ZipUpdate)updates_.get(idx + 1)).getEntry().getOffset() - update.getEntry().getOffset());
			idx++;
		}
		updateCount_ = updates_.size();

		contentsEdited_ = false;
		commentEdited_ = false;
		newComment_ = null;
	}

	/** 
	 Begin updating to this <see cref="ZipFile"/> archive.
	 
	 @param archiveStorage The storage to use during the update.
	*/
	public final void BeginUpdate(IArchiveStorage archiveStorage)
	{
		BeginUpdate(archiveStorage, new DynamicDiskDataSource());
	}

	/** 
	 Begin updating this <see cref="ZipFile"/> archive.
	 
	 {@link BeginUpdate(IArchiveStorage)}
	 @see CommitUpdate
	 @see AbortUpdate
	*/
	public final void BeginUpdate()
	{
		if (getName() == null)
		{
			BeginUpdate(new MemoryArchiveStorage(), new DynamicDiskDataSource());
		}
		else
		{
			BeginUpdate(new DiskArchiveStorage(this), new DynamicDiskDataSource());
		}
	}

	/** 
	 Commit current updates, updating this archive.
	 
	 @see BeginUpdate()
	 @see AbortUpdate
	 @exception ObjectDisposedException ZipFile has been closed.
	*/
	public final void CommitUpdate()
	{
		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		CheckUpdating();

		try
		{
			updateIndex_.clear();
			updateIndex_ = null;

			if (contentsEdited_)
			{
				RunUpdates();
			}
			else if (commentEdited_)
			{
				UpdateCommentOnly();
			}
			else
			{
				// Create an empty archive if none existed originally.
				if (entries_.length == 0)
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] theComment=(newComment_ != null)?newComment_.RawComment:ZipConstants.ConvertToArray(comment_);
					byte[] theComment = (newComment_ != null)?newComment_.getRawComment():ZipConstants.ConvertToArray(comment_);
					try (ZipHelperStream zhs = new ZipHelperStream(baseStream_))
					{
						zhs.WriteEndOfCentralDirectory(0, 0, 0, theComment);
					}
				}
			}

		}
		finally
		{
			PostUpdateCleanup();
		}
	}

	/** 
	 Abort updating leaving the archive unchanged.
	 
	 @see BeginUpdate()
	 @see CommitUpdate
	*/
	public final void AbortUpdate()
	{
		PostUpdateCleanup();
	}

	/** 
	 Set the file comment to be recorded when the current update is <see cref="CommitUpdate">commited</see>.
	 
	 @param comment The comment to record.
	 @exception ObjectDisposedException ZipFile has been closed.
	*/
	public final void SetComment(String comment)
	{
		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		CheckUpdating();

		newComment_ = new ZipString(comment);

		if (newComment_.getRawLength() > 0xffff)
		{
			newComment_ = null;
			throw new ZipException("Comment length exceeds maximum - 65535");
		}

		// We dont take account of the original and current comment appearing to be the same
		// as encoding may be different.
		commentEdited_ = true;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Adding Entries

	private void AddUpdate(ZipUpdate update)
	{
		contentsEdited_ = true;

		int index = FindExistingUpdate(update.getEntry().getName());

		if (index >= 0)
		{
			if (updates_.get(index) == null)
			{
				updateCount_ += 1;
			}

			// Direct replacement is faster than delete and add.
			updates_.set(index, update);
		}
		else
		{
			updates_.add(update);
			index = updates_.size() - 1;
			updateCount_ += 1;
			updateIndex_.put(update.getEntry().getName(), index);
		}
	}

	/** 
	 Add a new entry to the archive.
	 
	 @param fileName The name of the file to add.
	 @param compressionMethod The compression method to use.
	 @param useUnicodeText Ensure Unicode text is used for name and comment for this entry.
	 @exception ArgumentNullException Argument supplied is null.
	 @exception ObjectDisposedException ZipFile has been closed.
	 @exception ArgumentOutOfRangeException Compression method is not supported.
	*/
	public final void Add(String fileName, CompressionMethod compressionMethod, boolean useUnicodeText)
	{
		if (fileName == null)
		{
			throw new NullPointerException("fileName");
		}

		if (isDisposed_)
		{
			throw new ObjectDisposedException("ZipFile");
		}

		if (!ZipEntry.IsCompressionMethodSupported(compressionMethod))
		{
			throw new IndexOutOfBoundsException("compressionMethod");
		}

		CheckUpdating();
		contentsEdited_ = true;

		ZipEntry entry = getEntryFactory().MakeFileEntry(fileName);
		entry.setIsUnicodeText(useUnicodeText);
		entry.setCompressionMethod(compressionMethod);

		AddUpdate(new ZipUpdate(fileName, entry));
	}

	/** 
	 Add a new entry to the archive.
	 
	 @param fileName The name of the file to add.
	 @param compressionMethod The compression method to use.
	 @exception ArgumentNullException ZipFile has been closed.
	 @exception ArgumentOutOfRangeException The compression method is not supported.
	*/
	public final void Add(String fileName, CompressionMethod compressionMethod)
	{
		if (fileName == null)
		{
			throw new NullPointerException("fileName");
		}

		if (!ZipEntry.IsCompressionMethodSupported(compressionMethod))
		{
			throw new IndexOutOfBoundsException("compressionMethod");
		}

		CheckUpdating();
		contentsEdited_ = true;

		ZipEntry entry = getEntryFactory().MakeFileEntry(fileName);
		entry.setCompressionMethod(compressionMethod);
		AddUpdate(new ZipUpdate(fileName, entry));
	}

	/** 
	 Add a file to the archive.
	 
	 @param fileName The name of the file to add.
	 @exception ArgumentNullException Argument supplied is null.
	*/
	public final void Add(String fileName)
	{
		if (fileName == null)
		{
			throw new NullPointerException("fileName");
		}

		CheckUpdating();
		AddUpdate(new ZipUpdate(fileName, getEntryFactory().MakeFileEntry(fileName)));
	}

	/** 
	 Add a file to the archive.
	 
	 @param fileName The name of the file to add.
	 @param entryName The name to use for the <see cref="ZipEntry"/> on the Zip file created.
	 @exception ArgumentNullException Argument supplied is null.
	*/
	public final void Add(String fileName, String entryName)
	{
		if (fileName == null)
		{
			throw new NullPointerException("fileName");
		}

		if (entryName == null)
		{
			throw new NullPointerException("entryName");
		}

		CheckUpdating();
		AddUpdate(new ZipUpdate(fileName, getEntryFactory().MakeFileEntry(fileName, entryName, true)));
	}


	/** 
	 Add a file entry with data.
	 
	 @param dataSource The source of the data for this entry.
	 @param entryName The name to give to the entry.
	*/
	public final void Add(IStaticDataSource dataSource, String entryName)
	{
		if (dataSource == null)
		{
			throw new NullPointerException("dataSource");
		}

		if (entryName == null)
		{
			throw new NullPointerException("entryName");
		}

		CheckUpdating();
		AddUpdate(new ZipUpdate(dataSource, getEntryFactory().MakeFileEntry(entryName, false)));
	}

	/** 
	 Add a file entry with data.
	 
	 @param dataSource The source of the data for this entry.
	 @param entryName The name to give to the entry.
	 @param compressionMethod The compression method to use.
	*/
	public final void Add(IStaticDataSource dataSource, String entryName, CompressionMethod compressionMethod)
	{
		if (dataSource == null)
		{
			throw new NullPointerException("dataSource");
		}

		if (entryName == null)
		{
			throw new NullPointerException("entryName");
		}

		CheckUpdating();

		ZipEntry entry = getEntryFactory().MakeFileEntry(entryName, false);
		entry.setCompressionMethod(compressionMethod);

		AddUpdate(new ZipUpdate(dataSource, entry));
	}

	/** 
	 Add a file entry with data.
	 
	 @param dataSource The source of the data for this entry.
	 @param entryName The name to give to the entry.
	 @param compressionMethod The compression method to use.
	 @param useUnicodeText Ensure Unicode text is used for name and comments for this entry.
	*/
	public final void Add(IStaticDataSource dataSource, String entryName, CompressionMethod compressionMethod, boolean useUnicodeText)
	{
		if (dataSource == null)
		{
			throw new NullPointerException("dataSource");
		}

		if (entryName == null)
		{
			throw new NullPointerException("entryName");
		}

		CheckUpdating();

		ZipEntry entry = getEntryFactory().MakeFileEntry(entryName, false);
		entry.setIsUnicodeText(useUnicodeText);
		entry.setCompressionMethod(compressionMethod);

		AddUpdate(new ZipUpdate(dataSource, entry));
	}

	/** 
	 Add a <see cref="ZipEntry"/> that contains no data.
	 
	 @param entry The entry to add.
	 This can be used to add directories, volume labels, or empty file entries.
	*/
	public final void Add(ZipEntry entry)
	{
		if (entry == null)
		{
			throw new NullPointerException("entry");
		}

		CheckUpdating();

		if ((entry.getSize() != 0) || (entry.getCompressedSize() != 0))
		{
			throw new ZipException("Entry cannot have any data");
		}

		AddUpdate(new ZipUpdate(UpdateCommand.Add, entry));
	}

	/** 
	 Add a directory entry to the archive.
	 
	 @param directoryName The directory to add.
	*/
	public final void AddDirectory(String directoryName)
	{
		if (directoryName == null)
		{
			throw new NullPointerException("directoryName");
		}

		CheckUpdating();

		ZipEntry dirEntry = getEntryFactory().MakeDirectoryEntry(directoryName);
		AddUpdate(new ZipUpdate(UpdateCommand.Add, dirEntry));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Modifying Entries
/* Modify not yet ready for public consumption.
   Direct modification of an entry should not overwrite original data before its read.
   Safe mode is trivial in this sense.
		public void Modify(ZipEntry original, ZipEntry updated)
		{
			if ( original == null ) {
				throw new ArgumentNullException("original");
			}

			if ( updated == null ) {
				throw new ArgumentNullException("updated");
			}

			CheckUpdating();
			contentsEdited_ = true;
			updates_.Add(new ZipUpdate(original, updated));
		}
*/
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Deleting Entries
	/** 
	 Delete an entry by name
	 
	 @param fileName The filename to delete
	 @return True if the entry was found and deleted; false otherwise.
	*/
	public final boolean Delete(String fileName)
	{
		if (fileName == null)
		{
			throw new NullPointerException("fileName");
		}

		CheckUpdating();

		boolean result = false;
		int index = FindExistingUpdate(fileName);
		if ((index >= 0) && (updates_.get(index) != null))
		{
			result = true;
			contentsEdited_ = true;
			updates_.set(index, null);
			updateCount_ -= 1;
		}
		else
		{
			throw new ZipException("Cannot find entry to delete");
		}
		return result;
	}

	/** 
	 Delete a <see cref="ZipEntry"/> from the archive.
	 
	 @param entry The entry to delete.
	*/
	public final void Delete(ZipEntry entry)
	{
		if (entry == null)
		{
			throw new NullPointerException("entry");
		}

		CheckUpdating();

		int index = FindExistingUpdate(entry);
		if (index >= 0)
		{
			contentsEdited_ = true;
			updates_.set(index, null);
			updateCount_ -= 1;
		}
		else
		{
			throw new ZipException("Cannot find entry to delete");
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Update Support

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Writing Values/Headers
	private void WriteLEShort(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: baseStream_.WriteByte((byte)(value & 0xff));
		baseStream_.WriteByte((byte)(value & 0xff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: baseStream_.WriteByte((byte)((value >> 8) & 0xff));
		baseStream_.WriteByte((byte)((value >> 8) & 0xff));
	}

	/** 
	 Write an unsigned short in little endian byte order.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: void WriteLEUshort(ushort value)
	private void WriteLEUshort(short value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: baseStream_.WriteByte((byte)(value & 0xff));
		baseStream_.WriteByte((byte)(value & 0xff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: baseStream_.WriteByte((byte)(value >> 8));
		baseStream_.WriteByte((byte)(value >>> 8));
	}

	/** 
	 Write an int in little endian byte order.
	*/
	private void WriteLEInt(int value)
	{
		WriteLEShort(value & 0xffff);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		WriteLEShort(value >> 16);
	}

	/** 
	 Write an unsigned int in little endian byte order.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: void WriteLEUint(uint value)
	private void WriteLEUint(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEUshort((ushort)(value & 0xffff));
		WriteLEUshort((short)(value & 0xffff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEUshort((ushort)(value >> 16));
		WriteLEUshort((short)(value >>> 16));
	}

	/** 
	 Write a long in little endian byte order.
	*/
	private void WriteLeLong(long value)
	{
		WriteLEInt((int)(value & 0xffffffff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		WriteLEInt((int)(value >> 32));
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: void WriteLEUlong(ulong value)
	private void WriteLEUlong(long value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEUint((uint)(value & 0xffffffff));
		WriteLEUint((int)(value & 0xffffffff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEUint((uint)(value >> 32));
		WriteLEUint((int)(value >>> 32));
	}

	private void WriteLocalEntryHeader(ZipUpdate update)
	{
		ZipEntry entry = update.getOutEntry();

		// TODO: Local offset will require adjusting for multi-disk zip files.
		entry.setOffset(baseStream_.Position);

		// TODO: Need to clear any entry flags that dont make sense or throw an exception here.
		if (update.getCommand() != UpdateCommand.Copy)
		{
			if (entry.getCompressionMethod() == CompressionMethod.Deflated)
			{
				if (entry.getSize() == 0)
				{
					// No need to compress - no data.
					entry.setCompressedSize(entry.getSize());
					entry.setCrc(0);
					entry.setCompressionMethod(CompressionMethod.Stored);
				}
			}
			else if (entry.getCompressionMethod() == CompressionMethod.Stored)
			{
				entry.setFlags(entry.getFlags() & ~GeneralBitFlags.Descriptor.getValue());
			}

			if (getHaveKeys())
			{
				entry.setIsCrypted(true);
				if (entry.getCrc() < 0)
				{
					entry.setFlags(entry.getFlags() | GeneralBitFlags.Descriptor.getValue());
				}
			}
			else
			{
				entry.setIsCrypted(false);
			}

			switch (useZip64_)
			{
				case Dynamic:
					if (entry.getSize() < 0)
					{
						entry.ForceZip64();
					}
					break;

				case On:
					entry.ForceZip64();
					break;

				case Off:
					// Do nothing.  The entry itself may be using Zip64 independantly.
					break;
			}
		}

		// Write the local file header
		WriteLEInt(ZipConstants.LocalHeaderSignature);

		WriteLEShort(entry.getVersion());
		WriteLEShort(entry.getFlags());

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEShort((byte)entry.CompressionMethod);
		WriteLEShort((byte)entry.getCompressionMethod().getValue());
		WriteLEInt((int)entry.getDosTime());

		if (!entry.getHasCrc())
		{
			// Note patch address for updating CRC later.
			update.setCrcPatchOffset(baseStream_.Position);
			WriteLEInt((int)0);
		}
		else
		{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: WriteLEInt(unchecked((int)entry.Crc));
			WriteLEInt((int)entry.getCrc());
		}

		if (entry.getLocalHeaderRequiresZip64())
		{
			WriteLEInt(-1);
			WriteLEInt(-1);
		}
		else
		{
			if ((entry.getCompressedSize() < 0) || (entry.getSize() < 0))
			{
				update.setSizePatchOffset(baseStream_.Position);
			}

			WriteLEInt((int)entry.getCompressedSize());
			WriteLEInt((int)entry.getSize());
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] name = ZipConstants.ConvertToArray(entry.Flags, entry.Name);
		byte[] name = ZipConstants.ConvertToArray(entry.getFlags(), entry.getName());

		if (name.length > 0xFFFF)
		{
			throw new ZipException("Entry name too long.");
		}

		ZipExtraData ed = new ZipExtraData(entry.getExtraData());

		if (entry.getLocalHeaderRequiresZip64())
		{
			ed.StartNewEntry();

			// Local entry header always includes size and compressed size.
			// NOTE the order of these fields is reversed when compared to the normal headers!
			ed.AddLeLong(entry.getSize());
			ed.AddLeLong(entry.getCompressedSize());
			ed.AddNewEntry(1);
		}
		else
		{
			ed.Delete(1);
		}

		entry.setExtraData(ed.GetEntryData());

		WriteLEShort(name.length);
		WriteLEShort(entry.getExtraData().length);

		if (name.length > 0)
		{
			baseStream_.Write(name, 0, name.length);
		}

		if (entry.getLocalHeaderRequiresZip64())
		{
			if (!ed.Find(1))
			{
				throw new ZipException("Internal error cannot find extra data");
			}

			update.setSizePatchOffset(baseStream_.Position + ed.getCurrentReadIndex());
		}

		if (entry.getExtraData().length > 0)
		{
			baseStream_.Write(entry.getExtraData(), 0, entry.getExtraData().length);
		}
	}

	private int WriteCentralDirectoryHeader(ZipEntry entry)
	{
		if (entry.getCompressedSize() < 0)
		{
			throw new ZipException("Attempt to write central directory entry with unknown csize");
		}

		if (entry.getSize() < 0)
		{
			throw new ZipException("Attempt to write central directory entry with unknown size");
		}

		if (entry.getCrc() < 0)
		{
			throw new ZipException("Attempt to write central directory entry with unknown crc");
		}

		// Write the central file header
		WriteLEInt(ZipConstants.CentralHeaderSignature);

		// Version made by
		WriteLEShort(ZipConstants.VersionMadeBy);

		// Version required to extract
		WriteLEShort(entry.getVersion());

		WriteLEShort(entry.getFlags());

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEShort((byte)entry.CompressionMethod);
			WriteLEShort((byte)entry.getCompressionMethod().getValue());
			WriteLEInt((int)entry.getDosTime());
			WriteLEInt((int)entry.getCrc());
		}

		if ((entry.IsZip64Forced()) || (entry.getCompressedSize() >= 0xffffffffL))
		{
			WriteLEInt(-1);
		}
		else
		{
			WriteLEInt((int)(entry.getCompressedSize() & 0xffffffff));
		}

		if ((entry.IsZip64Forced()) || (entry.getSize() >= 0xffffffffL))
		{
			WriteLEInt(-1);
		}
		else
		{
			WriteLEInt((int)entry.getSize());
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] name = ZipConstants.ConvertToArray(entry.Flags, entry.Name);
		byte[] name = ZipConstants.ConvertToArray(entry.getFlags(), entry.getName());

		if (name.length > 0xFFFF)
		{
			throw new ZipException("Entry name is too long.");
		}

		WriteLEShort(name.length);

		// Central header extra data is different to local header version so regenerate.
		ZipExtraData ed = new ZipExtraData(entry.getExtraData());

		if (entry.getCentralHeaderRequiresZip64())
		{
			ed.StartNewEntry();

			if ((entry.getSize() >= 0xffffffffL) || (useZip64_ == UseZip64.On))
			{
				ed.AddLeLong(entry.getSize());
			}

			if ((entry.getCompressedSize() >= 0xffffffffL) || (useZip64_ == UseZip64.On))
			{
				ed.AddLeLong(entry.getCompressedSize());
			}

			if (entry.getOffset() >= 0xffffffffL)
			{
				ed.AddLeLong(entry.getOffset());
			}

			// Number of disk on which this file starts isnt supported and is never written here.
			ed.AddNewEntry(1);
		}
		else
		{
			// Should have already be done when local header was added.
			ed.Delete(1);
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] centralExtraData = ed.GetEntryData();
		byte[] centralExtraData = ed.GetEntryData();

		WriteLEShort(centralExtraData.length);
		WriteLEShort(entry.getComment() != null ? entry.getComment().length() : 0);

		WriteLEShort(0); // disk number
		WriteLEShort(0); // internal file attributes

		// External file attributes...
		if (entry.getExternalFileAttributes() != -1)
		{
			WriteLEInt(entry.getExternalFileAttributes());
		}
		else
		{
			if (entry.getIsDirectory())
			{
				WriteLEUint(16);
			}
			else
			{
				WriteLEUint(0);
			}
		}

		if (entry.getOffset() >= 0xffffffffL)
		{
			WriteLEUint((int)0xffffffff);
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEUint((uint)(int)entry.Offset);
			WriteLEUint((int)(int)entry.Offset);
		}

		if (name.length > 0)
		{
			baseStream_.Write(name, 0, name.length);
		}

		if (centralExtraData.length > 0)
		{
			baseStream_.Write(centralExtraData, 0, centralExtraData.length);
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] rawComment = (entry.Comment != null) ? Encoding.ASCII.GetBytes(entry.Comment) : new byte[0];
		byte[] rawComment = (entry.getComment() != null) ? entry.getComment().getBytes(java.nio.charset.StandardCharsets.US_ASCII) : new byte[0];

		if (rawComment.length > 0)
		{
			baseStream_.Write(rawComment, 0, rawComment.length);
		}

		return ZipConstants.CentralHeaderBaseSize + name.length + centralExtraData.length + rawComment.length;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private void PostUpdateCleanup()
	{
		updateDataSource_ = null;
		updates_ = null;
		updateIndex_ = null;

		if (archiveStorage_ != null)
		{
			archiveStorage_.Dispose();
			archiveStorage_ = null;
		}
	}

	private String GetTransformedFileName(String name)
	{
		INameTransform transform = getNameTransform();
		return (transform != null) ? transform.TransformFile(name) : name;
	}

	private String GetTransformedDirectoryName(String name)
	{
		INameTransform transform = getNameTransform();
		return (transform != null) ? transform.TransformDirectory(name) : name;
	}

	/** 
	 Get a raw memory buffer.
	 
	 @return Returns a raw memory buffer.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] GetBuffer()
	private byte[] GetBuffer()
	{
		if (copyBuffer_ == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: copyBuffer_ = new byte[bufferSize_];
			copyBuffer_ = new byte[bufferSize_];
		}
		return copyBuffer_;
	}

	private void CopyDescriptorBytes(ZipUpdate update, OutputStream dest, InputStream source)
	{
		int bytesToCopy = GetDescriptorSize(update);

		if (bytesToCopy > 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer = GetBuffer();
			byte[] buffer = GetBuffer();

			while (bytesToCopy > 0)
			{
				int readSize = Math.min(buffer.length, bytesToCopy);

				int bytesRead = source.read(buffer, 0, readSize);
				if (bytesRead > 0)
				{
					dest.write(buffer, 0, bytesRead);
					bytesToCopy -= bytesRead;
				}
				else
				{
					throw new ZipException("Unxpected end of stream");
				}
			}
		}
	}

	private void CopyBytes(ZipUpdate update, OutputStream destination, InputStream source, long bytesToCopy, boolean updateCrc)
	{
		if (destination == source)
		{
			throw new IllegalStateException("Destination and source are the same");
		}

		// NOTE: Compressed size is updated elsewhere.
		Crc32 crc = new Crc32();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer = GetBuffer();
		byte[] buffer = GetBuffer();

		long targetBytes = bytesToCopy;
		long totalBytesRead = 0;

		int bytesRead;
		do
		{
			int readSize = buffer.length;

			if (bytesToCopy < readSize)
			{
				readSize = (int)bytesToCopy;
			}

			bytesRead = source.read(buffer, 0, readSize);
			if (bytesRead > 0)
			{
				if (updateCrc)
				{
					crc.Update(buffer, 0, bytesRead);
				}
				destination.write(buffer, 0, bytesRead);
				bytesToCopy -= bytesRead;
				totalBytesRead += bytesRead;
			}
		} while ((bytesRead > 0) && (bytesToCopy > 0));

		if (totalBytesRead != targetBytes)
		{
			throw new ZipException(String.format("Failed to copy bytes expected %1$s read %2$s", targetBytes, totalBytesRead));
		}

		if (updateCrc)
		{
			update.getOutEntry().setCrc(crc.getValue());
		}
	}

	/** 
	 Get the size of the source descriptor for a <see cref="ZipUpdate"/>.
	 
	 @param update The update to get the size for.
	 @return The descriptor size, zero if there isnt one.
	*/
	private int GetDescriptorSize(ZipUpdate update)
	{
		int result = 0;
		if ((update.getEntry().getFlags() & GeneralBitFlags.Descriptor.getValue()) != 0)
		{
			result = ZipConstants.DataDescriptorSize - 4;
			if (update.getEntry().getLocalHeaderRequiresZip64())
			{
				result = ZipConstants.Zip64DataDescriptorSize - 4;
			}
		}
		return result;
	}

	private void CopyDescriptorBytesDirect(ZipUpdate update, InputStream stream, tangible.RefObject<Long> destinationPosition, long sourcePosition)
	{
		int bytesToCopy = GetDescriptorSize(update);

		while (bytesToCopy > 0)
		{
			int readSize = (int)bytesToCopy;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer = GetBuffer();
			byte[] buffer = GetBuffer();

			stream.Position = sourcePosition;
			int bytesRead = stream.read(buffer, 0, readSize);
			if (bytesRead > 0)
			{
				stream.Position = destinationPosition.argValue;
				stream.Write(buffer, 0, bytesRead);
				bytesToCopy -= bytesRead;
				destinationPosition.argValue += bytesRead;
				sourcePosition += bytesRead;
			}
			else
			{
				throw new ZipException("Unxpected end of stream");
			}
		}
	}

	private void CopyEntryDataDirect(ZipUpdate update, InputStream stream, boolean updateCrc, tangible.RefObject<Long> destinationPosition, tangible.RefObject<Long> sourcePosition)
	{
		long bytesToCopy = update.getEntry().getCompressedSize();

		// NOTE: Compressed size is updated elsewhere.
		Crc32 crc = new Crc32();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer = GetBuffer();
		byte[] buffer = GetBuffer();

		long targetBytes = bytesToCopy;
		long totalBytesRead = 0;

		int bytesRead;
		do
		{
			int readSize = buffer.length;

			if (bytesToCopy < readSize)
			{
				readSize = (int)bytesToCopy;
			}

			stream.Position = sourcePosition.argValue;
			bytesRead = stream.read(buffer, 0, readSize);
			if (bytesRead > 0)
			{
				if (updateCrc)
				{
					crc.Update(buffer, 0, bytesRead);
				}
				stream.Position = destinationPosition.argValue;
				stream.Write(buffer, 0, bytesRead);

				destinationPosition.argValue += bytesRead;
				sourcePosition.argValue += bytesRead;
				bytesToCopy -= bytesRead;
				totalBytesRead += bytesRead;
			}
		} while ((bytesRead > 0) && (bytesToCopy > 0));

		if (totalBytesRead != targetBytes)
		{
			throw new ZipException(String.format("Failed to copy bytes expected %1$s read %2$s", targetBytes, totalBytesRead));
		}

		if (updateCrc)
		{
			update.getOutEntry().setCrc(crc.getValue());
		}
	}

	private int FindExistingUpdate(ZipEntry entry)
	{
		int result = -1;
		String convertedName = GetTransformedFileName(entry.getName());

		if (updateIndex_.containsKey(convertedName))
		{
			result = (int)updateIndex_.get(convertedName);
		}
/*
			// This is slow like the coming of the next ice age but takes less storage and may be useful
			// for CF?
			for (int index = 0; index < updates_.Count; ++index)
			{
				ZipUpdate zu = ( ZipUpdate )updates_[index];
				if ( (zu.Entry.ZipFileIndex == entry.ZipFileIndex) &&
					(string.Compare(convertedName, zu.Entry.Name, true, CultureInfo.InvariantCulture) == 0) ) {
					result = index;
					break;
				}
			}
 */
		return result;
	}

	private int FindExistingUpdate(String fileName)
	{
		int result = -1;

		String convertedName = GetTransformedFileName(fileName);

		if (updateIndex_.containsKey(convertedName))
		{
			result = (int)updateIndex_.get(convertedName);
		}

/*
			// This is slow like the coming of the next ice age but takes less storage and may be useful
			// for CF?
			for ( int index = 0; index < updates_.Count; ++index ) {
				if ( string.Compare(convertedName, (( ZipUpdate )updates_[index]).Entry.Name,
					true, CultureInfo.InvariantCulture) == 0 ) {
					result = index;
					break;
				}
			}
 */

		return result;
	}

	/** 
	 Get an output stream for the specified <see cref="ZipEntry"/>
	 
	 @param entry The entry to get an output stream for.
	 @return The output stream obtained for the entry.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream GetOutputStream(ZipEntry entry)
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		Stream result = baseStream_;

		if (entry.getIsCrypted() == true)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new ZipException("Encryption not supported for Compact Framework 1.0");
//#else
			result = CreateAndInitEncryptionStream(result, entry);
//#endif
		}

		switch (entry.getCompressionMethod())
		{
			case Stored:
				result = new UncompressedStream(result);
				break;

			case Deflated:
				DeflaterOutputStream dos = new DeflaterOutputStream(result, new Deflater(9, true));
				dos.IsStreamOwner = false;
				result = dos;
				break;

			default:
				throw new ZipException("Unknown compression method " + entry.getCompressionMethod());
		}
		return result;
	}

	private void AddEntry(ZipFile workFile, ZipUpdate update)
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		Stream source = null;

		if (update.getEntry().getIsFile())
		{
			source = update.GetSource();

			if (source == null)
			{
				source = updateDataSource_.GetSource(update.getEntry(), update.getFilename());
			}
		}

		if (source != null)
		{
			try (source)
			{
				long sourceStreamLength = source.Length;
				if (update.getOutEntry().getSize() < 0)
				{
					update.getOutEntry().setSize(sourceStreamLength);
				}
				else
				{
					// Check for errant entries.
					if (update.getOutEntry().getSize() != sourceStreamLength)
					{
						throw new ZipException("Entry size/stream size mismatch");
					}
				}

				workFile.WriteLocalEntryHeader(update);

				long dataStart = workFile.baseStream_.Position;

				try (Stream output = workFile.GetOutputStream(update.getOutEntry()))
				{
					CopyBytes(update, output, source, sourceStreamLength, true);
				}

				long dataEnd = workFile.baseStream_.Position;
				update.getOutEntry().setCompressedSize(dataEnd - dataStart);

				if ((update.getOutEntry().getFlags() & GeneralBitFlags.Descriptor.getValue()) == GeneralBitFlags.Descriptor.getValue())
				{
					ZipHelperStream helper = new ZipHelperStream(workFile.baseStream_);
					helper.WriteDataDescriptor(update.getOutEntry());
				}
			}
		}
		else
		{
			workFile.WriteLocalEntryHeader(update);
			update.getOutEntry().setCompressedSize(0);
		}

	}

	private void ModifyEntry(ZipFile workFile, ZipUpdate update)
	{
		workFile.WriteLocalEntryHeader(update);
		long dataStart = workFile.baseStream_.Position;

		// TODO: This is slow if the changes don't effect the data!!
		if (update.getEntry().getIsFile() && (update.getFilename() != null))
		{
			try (Stream output = workFile.GetOutputStream(update.getOutEntry()))
			{
				try (Stream source = this.GetInputStream(update.getEntry()))
				{
					CopyBytes(update, output, source, source.Length, true);
				}
			}
		}

		long dataEnd = workFile.baseStream_.Position;
		update.getEntry().setCompressedSize(dataEnd - dataStart);
	}

	private void CopyEntryDirect(ZipFile workFile, ZipUpdate update, tangible.RefObject<Long> destinationPosition)
	{
		boolean skipOver = false;
		if (update.getEntry().getOffset() == destinationPosition.argValue)
		{
			skipOver = true;
		}

		if (!skipOver)
		{
			baseStream_.Position = destinationPosition.argValue;
			workFile.WriteLocalEntryHeader(update);
			destinationPosition.argValue = baseStream_.Position;
		}

		long sourcePosition = 0;

		final int NameLengthOffset = 26;

		// TODO: Add base for SFX friendly handling
		long entryDataOffset = update.getEntry().getOffset() + NameLengthOffset;

		baseStream_.Seek(entryDataOffset, SeekOrigin.Begin);

		// Clumsy way of handling retrieving the original name and extra data length for now.
		// TODO: Stop re-reading name and data length in CopyEntryDirect.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint nameLength = ReadLEUshort();
		int nameLength = ReadLEUshort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint extraLength = ReadLEUshort();
		int extraLength = ReadLEUshort();

		sourcePosition = baseStream_.Position + nameLength + extraLength;

		if (skipOver)
		{
			if (update.getOffsetBasedSize() != -1)
			{
				destinationPosition.argValue += update.getOffsetBasedSize();
			}
			else
			{
				// TODO: Find out why this calculation comes up 4 bytes short on some entries in ODT (Office Document Text) archives.
				// WinZip produces a warning on these entries:
				// "caution: value of lrec.csize (compressed size) changed from ..."
				destinationPosition.argValue += (sourcePosition - entryDataOffset) + NameLengthOffset + update.getEntry().getCompressedSize() + GetDescriptorSize(update);
			}
		}
		else
		{
			if (update.getEntry().getCompressedSize() > 0)
			{
				tangible.RefObject<Long> tempRef_sourcePosition = new tangible.RefObject<Long>(sourcePosition);
				CopyEntryDataDirect(update, baseStream_, false, destinationPosition, tempRef_sourcePosition);
			sourcePosition = tempRef_sourcePosition.argValue;
			}
			CopyDescriptorBytesDirect(update, baseStream_, destinationPosition, sourcePosition);
		}
	}

	private void CopyEntry(ZipFile workFile, ZipUpdate update)
	{
		workFile.WriteLocalEntryHeader(update);

		if (update.getEntry().getCompressedSize() > 0)
		{
			final int NameLengthOffset = 26;

			long entryDataOffset = update.getEntry().getOffset() + NameLengthOffset;

			// TODO: This wont work for SFX files!
			baseStream_.Seek(entryDataOffset, SeekOrigin.Begin);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint nameLength = ReadLEUshort();
			int nameLength = ReadLEUshort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint extraLength = ReadLEUshort();
			int extraLength = ReadLEUshort();

			baseStream_.Seek(nameLength + extraLength, SeekOrigin.Current);

			CopyBytes(update, workFile.baseStream_, baseStream_, update.getEntry().getCompressedSize(), false);
		}
		CopyDescriptorBytes(update, workFile.baseStream_, baseStream_);
	}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private void Reopen(Stream source)
	{
		if (source == null)
		{
			throw new ZipException("Failed to reopen archive - no source");
		}

		isNewArchive_ = false;
		baseStream_ = source;
		ReadEntries();
	}

	private void Reopen()
	{
		if (getName() == null)
		{
			throw new IllegalStateException("Name is not known cannot Reopen");
		}

		Reopen(File.Open(getName(), FileMode.Open, FileAccess.Read, FileShare.Read));
	}

	private void UpdateCommentOnly()
	{
		long baseLength = baseStream_.Length;

		ZipHelperStream updateFile = null;

		if (archiveStorage_.getUpdateMode() == FileUpdateMode.Safe)
		{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
			Stream copyStream = archiveStorage_.MakeTemporaryCopy(baseStream_);
			updateFile = new ZipHelperStream(copyStream);
			updateFile.setIsStreamOwner(true);

			baseStream_.Close();
			baseStream_ = null;
		}
		else
		{
			if (archiveStorage_.getUpdateMode() == FileUpdateMode.Direct)
			{
				// TODO: archiveStorage wasnt originally intended for this use.
				// Need to revisit this to tidy up handling as archive storage currently doesnt 
				// handle the original stream well.
				// The problem is when using an existing zip archive with an in memory archive storage.
				// The open stream wont support writing but the memory storage should open the same file not an in memory one.

				// Need to tidy up the archive storage interface and contract basically.
				baseStream_ = archiveStorage_.OpenForDirectUpdate(baseStream_);
				updateFile = new ZipHelperStream(baseStream_);
			}
			else
			{
				baseStream_.Close();
				baseStream_ = null;
				updateFile = new ZipHelperStream(getName());
			}
		}

		try (updateFile)
		{
			long locatedCentralDirOffset = updateFile.LocateBlockWithSignature(ZipConstants.EndOfCentralDirectorySignature, baseLength, ZipConstants.EndOfCentralRecordBaseSize, 0xffff);
			if (locatedCentralDirOffset < 0)
			{
				throw new ZipException("Cannot find central directory");
			}

			final int CentralHeaderCommentSizeOffset = 16;
			updateFile.setPosition(updateFile.getPosition() + CentralHeaderCommentSizeOffset);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] rawComment = newComment_.RawComment;
			byte[] rawComment = newComment_.getRawComment();

			updateFile.WriteLEShort(rawComment.length);
			updateFile.Write(rawComment, 0, rawComment.length);
			updateFile.SetLength(updateFile.getPosition());
		}

		if (archiveStorage_.getUpdateMode() == FileUpdateMode.Safe)
		{
			Reopen(archiveStorage_.ConvertTemporaryToFinal());
		}
		else
		{
			ReadEntries();
		}
	}

	/** 
	 Class used to sort updates.
	*/
	private static class UpdateComparer implements Comparator
	{
		/** 
		 Compares two objects and returns a value indicating whether one is 
		 less than, equal to or greater than the other.
		 
		 @param x First object to compare
		 @param y Second object to compare.
		 @return Compare result.
		*/
		public final int compare(Object x, Object y)
		{
			ZipUpdate zx = x instanceof ZipUpdate ? (ZipUpdate)x : null;
			ZipUpdate zy = y instanceof ZipUpdate ? (ZipUpdate)y : null;

			int result;

			if (zx == null)
			{
				if (zy == null)
				{
					result = 0;
				}
				else
				{
					result = -1;
				}
			}
			else if (zy == null)
			{
				result = 1;
			}
			else
			{
				int xCmdValue = ((zx.getCommand() == UpdateCommand.Copy) || (zx.getCommand() == UpdateCommand.Modify)) ? 0 : 1;
				int yCmdValue = ((zy.getCommand() == UpdateCommand.Copy) || (zy.getCommand() == UpdateCommand.Modify)) ? 0 : 1;

				result = xCmdValue - yCmdValue;
				if (result == 0)
				{
					long offsetDiff = zx.getEntry().getOffset() - zy.getEntry().getOffset();
					if (offsetDiff < 0)
					{
						result = -1;
					}
					else if (offsetDiff == 0)
					{
						result = 0;
					}
					else
					{
						result = 1;
					}
				}
			}
			return result;
		}
	}

	private void RunUpdates()
	{
		long sizeEntries = 0;
		long endOfStream = 0;
		boolean directUpdate = false;
		long destinationPosition = 0; // NOT SFX friendly

		ZipFile workFile;

		if (getIsNewArchive())
		{
			workFile = this;
			workFile.baseStream_.Position = 0;
			directUpdate = true;
		}
		else if (archiveStorage_.getUpdateMode() == FileUpdateMode.Direct)
		{
			workFile = this;
			workFile.baseStream_.Position = 0;
			directUpdate = true;

			// Sort the updates by offset within copies/modifies, then adds.
			// This ensures that data required by copies will not be overwritten.
			Collections.sort(updates_, new UpdateComparer());
		}
		else
		{
			workFile = ZipFile.Create(archiveStorage_.GetTemporaryOutput());
			workFile.setUseZip64(getUseZip64());

			if (key != null)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: workFile.key = (byte[])key.Clone();
				workFile.key = (byte[])key.clone();
			}
		}

		try
		{
			for (ZipUpdate update : updates_)
			{
				if (update != null)
				{
					switch (update.getCommand())
					{
						case Copy:
							if (directUpdate)
							{
								tangible.RefObject<Long> tempRef_destinationPosition = new tangible.RefObject<Long>(destinationPosition);
								CopyEntryDirect(workFile, update, tempRef_destinationPosition);
							destinationPosition = tempRef_destinationPosition.argValue;
							}
							else
							{
								CopyEntry(workFile, update);
							}
							break;

						case Modify:
							// TODO: Direct modifying of an entry will take some legwork.
							ModifyEntry(workFile, update);
							break;

						case Add:
							if (!getIsNewArchive() && directUpdate)
							{
								workFile.baseStream_.Position = destinationPosition;
							}

							AddEntry(workFile, update);

							if (directUpdate)
							{
								destinationPosition = workFile.baseStream_.Position;
							}
							break;
					}
				}
			}

			if (!getIsNewArchive() && directUpdate)
			{
				workFile.baseStream_.Position = destinationPosition;
			}

			long centralDirOffset = workFile.baseStream_.Position;

			for (ZipUpdate update : updates_)
			{
				if (update != null)
				{
					sizeEntries += workFile.WriteCentralDirectoryHeader(update.getOutEntry());
				}
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] theComment = (newComment_ != null) ? newComment_.RawComment : ZipConstants.ConvertToArray(comment_);
			byte[] theComment = (newComment_ != null) ? newComment_.getRawComment() : ZipConstants.ConvertToArray(comment_);
			try (ZipHelperStream zhs = new ZipHelperStream(workFile.baseStream_))
			{
				zhs.WriteEndOfCentralDirectory(updateCount_, sizeEntries, centralDirOffset, theComment);
			}

			endOfStream = workFile.baseStream_.Position;

			// And now patch entries...
			for (ZipUpdate update : updates_)
			{
				if (update != null)
				{
					// If the size of the entry is zero leave the crc as 0 as well.
					// The calculated crc will be all bits on...
					if ((update.getCrcPatchOffset() > 0) && (update.getOutEntry().getCompressedSize() > 0))
					{
						workFile.baseStream_.Position = update.getCrcPatchOffset();
						workFile.WriteLEInt((int)update.getOutEntry().getCrc());
					}

					if (update.getSizePatchOffset() > 0)
					{
						workFile.baseStream_.Position = update.getSizePatchOffset();
						if (update.getOutEntry().getLocalHeaderRequiresZip64())
						{
							workFile.WriteLeLong(update.getOutEntry().getSize());
							workFile.WriteLeLong(update.getOutEntry().getCompressedSize());
						}
						else
						{
							workFile.WriteLEInt((int)update.getOutEntry().getCompressedSize());
							workFile.WriteLEInt((int)update.getOutEntry().getSize());
						}
					}
				}
			}
		}
		catch (java.lang.Exception e)
		{
			workFile.Close();
			if (!directUpdate && (workFile.getName() != null))
			{
				(new File(workFile.getName())).delete();
			}
			throw e;
		}

		if (directUpdate)
		{
			workFile.baseStream_.SetLength(endOfStream);
			workFile.baseStream_.Flush();
			isNewArchive_ = false;
			ReadEntries();
		}
		else
		{
			baseStream_.Close();
			Reopen(archiveStorage_.ConvertTemporaryToFinal());
		}
	}

	private void CheckUpdating()
	{
		if (updates_ == null)
		{
			throw new IllegalStateException("BeginUpdate has not been called");
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ZipUpdate class
	/** 
	 Represents a pending update to a Zip file.
	*/
	private static class ZipUpdate
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Constructors
		public ZipUpdate(String fileName, ZipEntry entry)
		{
			command_ = UpdateCommand.Add;
			entry_ = entry;
			filename_ = fileName;
		}

		@Deprecated
		public ZipUpdate(String fileName, String entryName, CompressionMethod compressionMethod)
		{
			command_ = UpdateCommand.Add;
			entry_ = new ZipEntry(entryName);
			entry_.setCompressionMethod(compressionMethod);
			filename_ = fileName;
		}

		@Deprecated
		public ZipUpdate(String fileName, String entryName)
		{
			this(fileName, entryName, CompressionMethod.Deflated);
			// Do nothing.
		}

		@Deprecated
		public ZipUpdate(IStaticDataSource dataSource, String entryName, CompressionMethod compressionMethod)
		{
			command_ = UpdateCommand.Add;
			entry_ = new ZipEntry(entryName);
			entry_.setCompressionMethod(compressionMethod);
			dataSource_ = dataSource;
		}

		public ZipUpdate(IStaticDataSource dataSource, ZipEntry entry)
		{
			command_ = UpdateCommand.Add;
			entry_ = entry;
			dataSource_ = dataSource;
		}

		public ZipUpdate(ZipEntry original, ZipEntry updated)
		{
			throw new ZipException("Modify not currently supported");
		/*
			command_ = UpdateCommand.Modify;
			entry_ = ( ZipEntry )original.Clone();
			outEntry_ = ( ZipEntry )updated.Clone();
		*/
		}

		public ZipUpdate(UpdateCommand command, ZipEntry entry)
		{
			command_ = command;
			entry_ = (ZipEntry)entry.Clone();
		}


		/** 
		 Copy an existing entry.
		 
		 @param entry The existing entry to copy.
		*/
		public ZipUpdate(ZipEntry entry)
		{
			this(UpdateCommand.Copy, entry);
			// Do nothing.
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

		/** 
		 Get the <see cref="ZipEntry"/> for this update.
		 
		 This is the source or original entry.
		*/
		public final ZipEntry getEntry()
		{
			return entry_;
		}

		/** 
		 Get the <see cref="ZipEntry"/> that will be written to the updated/new file.
		*/
		public final ZipEntry getOutEntry()
		{
			if (outEntry_ == null)
			{
				outEntry_ = (ZipEntry)entry_.Clone();
			}

			return outEntry_;
		}

		/** 
		 Get the command for this update.
		*/
		public final UpdateCommand getCommand()
		{
			return command_;
		}

		/** 
		 Get the filename if any for this update.  Null if none exists.
		*/
		public final String getFilename()
		{
			return filename_;
		}

		/** 
		 Get/set the location of the size patch for this update.
		*/
		public final long getSizePatchOffset()
		{
			return sizePatchOffset_;
		}
		public final void setSizePatchOffset(long value)
		{
			sizePatchOffset_ = value;
		}

		/** 
		 Get /set the location of the crc patch for this update.
		*/
		public final long getCrcPatchOffset()
		{
			return crcPatchOffset_;
		}
		public final void setCrcPatchOffset(long value)
		{
			crcPatchOffset_ = value;
		}

		/** 
		 Get/set the size calculated by offset.
		 Specifically, the difference between this and next entry's starting offset.
		*/
		public final long getOffsetBasedSize()
		{
			return _offsetBasedSize;
		}
		public final void setOffsetBasedSize(long value)
		{
			_offsetBasedSize = value;
		}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		public final Stream GetSource()
		{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
			Stream result = null;
			if (dataSource_ != null)
			{
				result = dataSource_.GetSource();
			}

			return result;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Instance Fields
		private ZipEntry entry_;
		private ZipEntry outEntry_;
		private UpdateCommand command_ = UpdateCommand.values()[0];
		private IStaticDataSource dataSource_;
		private String filename_;
		private long sizePatchOffset_ = -1;
		private long crcPatchOffset_ = -1;
		private long _offsetBasedSize = -1;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Disposing

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IDisposable Members
	public final void close() throws IOException
	{
		Close();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private void DisposeInternal(boolean disposing)
	{
		if (!isDisposed_)
		{
			isDisposed_ = true;
			entries_ = new ZipEntry[0];

			if (getIsStreamOwner() && (baseStream_ != null))
			{
				synchronized (baseStream_)
				{
					baseStream_.Close();
				}
			}

			PostUpdateCleanup();
		}
	}

	/** 
	 Releases the unmanaged resources used by the this instance and optionally releases the managed resources.
	 
	 @param disposing true to release both managed and unmanaged resources;
	 false to release only unmanaged resources.
	*/
	protected void Dispose(boolean disposing)
	{
		DisposeInternal(disposing);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Internal routines
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Reading
	/** 
	 Read an unsigned short in little endian byte order.
	 
	 @return Returns the value read.
	 @exception EndOfStreamException
	 The stream ends prematurely
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort ReadLEUshort()
	private short ReadLEUshort()
	{
		int data1 = baseStream_.ReadByte();

		if (data1 < 0)
		{
			throw new EndOfStreamException("End of stream");
		}

		int data2 = baseStream_.ReadByte();

		if (data2 < 0)
		{
			throw new EndOfStreamException("End of stream");
		}


//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: return unchecked((ushort)((ushort)data1 | (ushort)(data2 << 8)));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		return (short)((short)data1 | (short)(data2 << 8));
	}

	/** 
	 Read a uint in little endian byte order.
	 
	 @return Returns the value read.
	 @exception IOException
	 An i/o error occurs.
	 
	 @exception System.IO.EndOfStreamException
	 The file ends prematurely
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint ReadLEUint()
	private int ReadLEUint()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint)(ReadLEUshort() | (ReadLEUshort() << 16));
		return (int)(ReadLEUshort() | (ReadLEUshort() << 16));
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong ReadLEUlong()
	private long ReadLEUlong()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ReadLEUint() | ((ulong)ReadLEUint() << 32);
		return ReadLEUint() | ((long)ReadLEUint() << 32);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
	// NOTE this returns the offset of the first byte after the signature.
	private long LocateBlockWithSignature(int signature, long endLocation, int minimumBlockSize, int maximumVariableData)
	{
		try (ZipHelperStream les = new ZipHelperStream(baseStream_))
		{
			return les.LocateBlockWithSignature(signature, endLocation, minimumBlockSize, maximumVariableData);
		}
	}

	/** 
	 Search for and read the central directory of a zip file filling the entries array.
	 
	 @exception System.IO.IOException
	 An i/o error occurs.
	 
	 @exception Rasad.Core.Compression.Zip.ZipException
	 The central directory is malformed or cannot be found
	 
	*/
	private void ReadEntries()
	{
		// Search for the End Of Central Directory.  When a zip comment is
		// present the directory will start earlier
		// 
		// The search is limited to 64K which is the maximum size of a trailing comment field to aid speed.
		// This should be compatible with both SFX and ZIP files but has only been tested for Zip files
		// If a SFX file has the Zip data attached as a resource and there are other resources occuring later then
		// this could be invalid.
		// Could also speed this up by reading memory in larger blocks.			

		if (baseStream_.CanSeek == false)
		{
			throw new ZipException("ZipFile stream must be seekable");
		}

		long locatedEndOfCentralDir = LocateBlockWithSignature(ZipConstants.EndOfCentralDirectorySignature, baseStream_.Length, ZipConstants.EndOfCentralRecordBaseSize, 0xffff);

		if (locatedEndOfCentralDir < 0)
		{
			throw new ZipException("Cannot find central directory");
		}

		// Read end of central directory record
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort thisDiskNumber = ReadLEUshort();
		short thisDiskNumber = ReadLEUshort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort startCentralDirDisk = ReadLEUshort();
		short startCentralDirDisk = ReadLEUshort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong entriesForThisDisk = ReadLEUshort();
		long entriesForThisDisk = ReadLEUshort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong entriesForWholeCentralDir = ReadLEUshort();
		long entriesForWholeCentralDir = ReadLEUshort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong centralDirSize = ReadLEUint();
		long centralDirSize = ReadLEUint();
		long offsetOfCentralDir = ReadLEUint();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint commentSize = ReadLEUshort();
		int commentSize = ReadLEUshort();

		if (commentSize > 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] comment = new byte[commentSize];
			byte[] comment = new byte[commentSize];

			StreamUtils.ReadFully(baseStream_, comment);
			comment_ = ZipConstants.ConvertToString(comment);
		}
		else
		{
			comment_ = "";
		}

		boolean isZip64 = false;

		// Check if zip64 header information is required.
		if ((thisDiskNumber == 0xffff) || (startCentralDirDisk == 0xffff) || (entriesForThisDisk == 0xffffL) || (entriesForWholeCentralDir == 0xffffL) || (centralDirSize == 0xffffffffL) || (offsetOfCentralDir == 0xffffffffL))
		{
			isZip64 = true;

			long offset = LocateBlockWithSignature(ZipConstants.Zip64CentralDirLocatorSignature, locatedEndOfCentralDir, 0, 0x1000);
			if (offset < 0)
			{
				throw new ZipException("Cannot find Zip64 locator");
			}

			// number of the disk with the start of the zip64 end of central directory 4 bytes 
			// relative offset of the zip64 end of central directory record 8 bytes 
			// total number of disks 4 bytes 
			ReadLEUint(); // startDisk64 is not currently used
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong offset64 = ReadLEUlong();
			long offset64 = ReadLEUlong();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint totalDisks = ReadLEUint();
			int totalDisks = ReadLEUint();

			baseStream_.Position = (long)offset64;
			long sig64 = ReadLEUint();

			if (sig64 != ZipConstants.Zip64CentralFileHeaderSignature)
			{
				throw new ZipException(String.format("Invalid Zip64 Central directory signature at %X", offset64));
			}

			// NOTE: Record size = SizeOfFixedFields + SizeOfVariableData - 12.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong recordSize = ReadLEUlong();
			long recordSize = ReadLEUlong();
			int versionMadeBy = ReadLEUshort();
			int versionToExtract = ReadLEUshort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint thisDisk = ReadLEUint();
			int thisDisk = ReadLEUint();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint centralDirDisk = ReadLEUint();
			int centralDirDisk = ReadLEUint();
			entriesForThisDisk = ReadLEUlong();
			entriesForWholeCentralDir = ReadLEUlong();
			centralDirSize = ReadLEUlong();
			offsetOfCentralDir = (long)ReadLEUlong();

			// NOTE: zip64 extensible data sector (variable size) is ignored.
		}

		entries_ = new ZipEntry[entriesForThisDisk];

		// SFX/embedded support, find the offset of the first entry vis the start of the stream
		// This applies to Zip files that are appended to the end of an SFX stub.
		// Or are appended as a resource to an executable.
		// Zip files created by some archivers have the offsets altered to reflect the true offsets
		// and so dont require any adjustment here...
		// TODO: Difficulty with Zip64 and SFX offset handling needs resolution - maths?
		if (!isZip64 && (offsetOfCentralDir < locatedEndOfCentralDir - (4 + (long)centralDirSize)))
		{
			offsetOfFirstEntry = locatedEndOfCentralDir - (4 + (long)centralDirSize + offsetOfCentralDir);
			if (offsetOfFirstEntry <= 0)
			{
				throw new ZipException("Invalid embedded zip archive");
			}
		}

		baseStream_.Seek(offsetOfFirstEntry + offsetOfCentralDir, SeekOrigin.Begin);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: for (ulong i = 0; i < entriesForThisDisk; i++)
		for (long i = 0; i < entriesForThisDisk; i++)
		{
			if (ReadLEUint() != ZipConstants.CentralHeaderSignature)
			{
				throw new ZipException("Wrong Central Directory signature");
			}

			int versionMadeBy = ReadLEUshort();
			int versionToExtract = ReadLEUshort();
			int bitFlags = ReadLEUshort();
			int method = ReadLEUshort();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint dostime = ReadLEUint();
			int dostime = ReadLEUint();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint crc = ReadLEUint();
			int crc = ReadLEUint();
			long csize = (long)ReadLEUint();
			long size = (long)ReadLEUint();
			int nameLen = ReadLEUshort();
			int extraLen = ReadLEUshort();
			int commentLen = ReadLEUshort();

			int diskStartNo = ReadLEUshort(); // Not currently used
			int internalAttributes = ReadLEUshort(); // Not currently used

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint externalAttributes = ReadLEUint();
			int externalAttributes = ReadLEUint();
			long offset = ReadLEUint();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer = new byte[Math.Max(nameLen, commentLen)];
			byte[] buffer = new byte[Math.max(nameLen, commentLen)];

			StreamUtils.ReadFully(baseStream_, buffer, 0, nameLen);
			String name = ZipConstants.ConvertToStringExt(bitFlags, buffer, nameLen);

			ZipEntry entry = new ZipEntry(name, versionToExtract, versionMadeBy, CompressionMethod.forValue(method));
			entry.setCrc(crc & 0xffffffffL);
			entry.setSize(size & 0xffffffffL);
			entry.setCompressedSize(csize & 0xffffffffL);
			entry.setFlags(bitFlags);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: entry.DosTime = (uint)dostime;
			entry.setDosTime((int)dostime);
			entry.setZipFileIndex((long)i);
			entry.setOffset(offset);
			entry.setExternalFileAttributes((int)externalAttributes);

			if ((bitFlags & 8) == 0)
			{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: entry.CryptoCheckValue = (byte)(crc >> 24);
				entry.setCryptoCheckValue((byte)(crc >>> 24));
			}
			else
			{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: entry.CryptoCheckValue = (byte)((dostime >> 8) & 0xff);
				entry.setCryptoCheckValue((byte)((dostime >>> 8) & 0xff));
			}

			if (extraLen > 0)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] extra = new byte[extraLen];
				byte[] extra = new byte[extraLen];
				StreamUtils.ReadFully(baseStream_, extra);
				entry.setExtraData(extra);
			}

			entry.ProcessExtraData(false);

			if (commentLen > 0)
			{
				StreamUtils.ReadFully(baseStream_, buffer, 0, commentLen);
				entry.setComment(ZipConstants.ConvertToStringExt(bitFlags, buffer, commentLen));
			}

			entries_[i] = entry;
		}
	}

	/** 
	 Locate the data for a given entry.
	 
	 @return 
	 The start offset of the data.
	 
	 @exception System.IO.EndOfStreamException
	 The stream ends prematurely
	 
	 @exception Rasad.Core.Compression.Zip.ZipException
	 The local header signature is invalid, the entry and central header file name lengths are different
	 or the local and entry compression methods dont match
	 
	*/
	private long LocateEntry(ZipEntry entry)
	{
		return TestLocalHeader(entry, HeaderTest.Extract);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream CreateAndInitDecryptionStream(InputStream baseStream, ZipEntry entry)
	{
		CryptoStream result = null;

		if ((entry.getVersion() < ZipConstants.VersionStrongEncryption) || (entry.getFlags() & GeneralBitFlags.StrongEncryption.getValue()) == 0)
		{
			PkzipClassicManaged classicManaged = new PkzipClassicManaged();

			OnKeysRequired(entry.getName());
			if (getHaveKeys() == false)
			{
				throw new ZipException("No password available for encrypted stream");
			}

			result = new CryptoStream(baseStream, classicManaged.CreateDecryptor(key, null), CryptoStreamMode.Read);
			CheckClassicPassword(result, entry);
		}
		else
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_1 && !NETCF_2_0
			if (entry.getVersion() == ZipConstants.VERSION_AES)
			{
				//
				OnKeysRequired(entry.getName());
				if (getHaveKeys() == false)
				{
					throw new ZipException("No password available for AES encrypted stream");
				}
				int saltLen = entry.getAESSaltLen();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] saltBytes = new byte[saltLen];
				byte[] saltBytes = new byte[saltLen];
				int saltIn = baseStream.read(saltBytes, 0, saltLen);
				if (saltIn != saltLen)
				{
					throw new ZipException("AES Salt expected " + saltLen + " got " + saltIn);
				}
				//
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] pwdVerifyRead = new byte[2];
				byte[] pwdVerifyRead = new byte[2];
				baseStream.read(pwdVerifyRead, 0, 2);
				int blockSize = entry.AESKeySize / 8; // bits to bytes

				ZipAESTransform decryptor = new ZipAESTransform(rawPassword_, saltBytes, blockSize, false);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] pwdVerifyCalc = decryptor.PwdVerifier;
				byte[] pwdVerifyCalc = decryptor.getPwdVerifier();
				if (pwdVerifyCalc[0] != pwdVerifyRead[0] || pwdVerifyCalc[1] != pwdVerifyRead[1])
				{
					throw new RuntimeException("Invalid password for AES");
				}
				result = new ZipAESStream(baseStream, decryptor, CryptoStreamMode.Read);
			}
			else
//#endif
			{
				throw new ZipException("Decryption method not supported");
			}
		}

		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream CreateAndInitEncryptionStream(Stream baseStream, ZipEntry entry)
	{
		CryptoStream result = null;
		if ((entry.getVersion() < ZipConstants.VersionStrongEncryption) || (entry.getFlags() & GeneralBitFlags.StrongEncryption.getValue()) == 0)
		{
			PkzipClassicManaged classicManaged = new PkzipClassicManaged();

			OnKeysRequired(entry.getName());
			if (getHaveKeys() == false)
			{
				throw new ZipException("No password available for encrypted stream");
			}

			// Closing a CryptoStream will close the base stream as well so wrap it in an UncompressedStream
			// which doesnt do this.
			result = new CryptoStream(new UncompressedStream(baseStream), classicManaged.CreateEncryptor(key, null), CryptoStreamMode.Write);

			if ((entry.getCrc() < 0) || (entry.getFlags() & 8) != 0)
			{
				WriteEncryptionHeader(result, entry.getDosTime() << 16);
			}
			else
			{
				WriteEncryptionHeader(result, entry.getCrc());
			}
		}
		return result;
	}

	private static void CheckClassicPassword(CryptoStream classicCryptoStream, ZipEntry entry)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] cryptbuffer = new byte[ZipConstants.CryptoHeaderSize];
		byte[] cryptbuffer = new byte[ZipConstants.CryptoHeaderSize];
		StreamUtils.ReadFully(classicCryptoStream, cryptbuffer);
		if (cryptbuffer[ZipConstants.CryptoHeaderSize - 1] != entry.getCryptoCheckValue())
		{
			throw new ZipException("Invalid password");
		}
	}
//#endif

	private static void WriteEncryptionHeader(OutputStream stream, long crcValue)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] cryptBuffer = new byte[ZipConstants.CryptoHeaderSize];
		byte[] cryptBuffer = new byte[ZipConstants.CryptoHeaderSize];
		Random rnd = new Random();
		rnd.nextBytes(cryptBuffer);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: cryptBuffer[11] = (byte)(crcValue >> 24);
		cryptBuffer[11] = (byte)(crcValue >> 24);
		stream.write(cryptBuffer, 0, cryptBuffer.length);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private boolean isDisposed_;
	private String name_;
	private String comment_;
	private String rawPassword_;
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream baseStream_;
	private boolean isStreamOwner;
	private long offsetOfFirstEntry;
	private ZipEntry[] entries_;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] key;
	private byte[] key;
	private boolean isNewArchive_;

	// Default is dynamic which is not backwards compatible and can cause problems
	// with XP's built in compression which cant read Zip64 archives.
	// However it does avoid the situation were a large file is added and cannot be completed correctly.
	// Hint: Set always ZipEntry size before they are added to an archive and this setting isnt needed.
	private UseZip64 useZip64_ = UseZip64.Dynamic;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Zip Update Instance Fields
	private ArrayList updates_;
	private long updateCount_; // Count is managed manually as updates_ can contain nulls!
	private Hashtable updateIndex_;
	private IArchiveStorage archiveStorage_;
	private IDynamicDataSource updateDataSource_;
	private boolean contentsEdited_;
	private int bufferSize_ = DefaultBufferSize;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] copyBuffer_;
	private byte[] copyBuffer_;
	private ZipString newComment_;
	private boolean commentEdited_;
	private IEntryFactory updateEntryFactory_ = new ZipEntryFactory();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Support Classes
	/** 
	 Represents a string from a <see cref="ZipFile"/> which is stored as an array of bytes.
	*/
	private static class ZipString
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Constructors
		/** 
		 Initialise a <see cref="ZipString"/> with a string.
		 
		 @param comment The textual string form.
		*/
		public ZipString(String comment)
		{
			comment_ = comment;
			isSourceString_ = true;
		}

		/** 
		 Initialise a <see cref="ZipString"/> using a string in its binary 'raw' form.
		 
		 @param rawString
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ZipString(byte[] rawString)
		public ZipString(byte[] rawString)
		{
			rawComment_ = rawString;
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

		/** 
		 Get a value indicating the original source of data for this instance.
		 True if the source was a string; false if the source was binary data.
		*/
		public final boolean getIsSourceString()
		{
			return isSourceString_;
		}

		/** 
		 Get the length of the comment when represented as raw bytes.
		*/
		public final int getRawLength()
		{
			MakeBytesAvailable();
			return rawComment_.length;
		}

		/** 
		 Get the comment in its 'raw' form as plain bytes.
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getRawComment()
		public final byte[] getRawComment()
		{
			MakeBytesAvailable();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (byte[])rawComment_.Clone();
			return (byte[])rawComment_.clone();
		}

		/** 
		 Reset the comment to its initial state.
		*/
		public final void Reset()
		{
			if (isSourceString_)
			{
				rawComment_ = null;
			}
			else
			{
				comment_ = null;
			}
		}

		private void MakeTextAvailable()
		{
			if (comment_ == null)
			{
				comment_ = ZipConstants.ConvertToString(rawComment_);
			}
		}

		private void MakeBytesAvailable()
		{
			if (rawComment_ == null)
			{
				rawComment_ = ZipConstants.ConvertToArray(comment_);
			}
		}

		/** 
		 Implicit conversion of comment to a string.
		 
		 @param zipString The <see cref="ZipString"/> to convert to a string.
		 @return The textual equivalent for the input value.
		*/
//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
		public static implicit operator String(ZipString zipString)
		{
			zipString.MakeTextAvailable();
			return zipString.comment_;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Instance Fields
		private String comment_;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] rawComment_;
		private byte[] rawComment_;
		private boolean isSourceString_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}

	/** 
	 An <see cref="IEnumerator">enumerator</see> for <see cref="ZipEntry">Zip entries</see>
	*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
	private static class ZipEntryEnumerator implements Iterator
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Constructors
		public ZipEntryEnumerator(ZipEntry[] entries)
		{
			array = entries;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region IEnumerator Members
		public final Object getCurrent()
		{
			return array[index];
		}

		public final void Reset()
		{
			index = -1;
		}

		public final boolean MoveNext()
		{
			return (++index < array.length);
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Instance Fields
		private ZipEntry[] array;
		private int index = -1;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}

	/** 
	 An <see cref="UncompressedStream"/> is a stream that you can write uncompressed data
	 to and flush, but cannot read, seek or do anything else to.
	*/
	private static class UncompressedStream extends Stream
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Constructors
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		public UncompressedStream(Stream baseStream)
		{
			baseStream_ = baseStream;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

		/** 
		 Close this stream instance.
		*/
		@Override
		public void Close()
		{
			// Do nothing
		}

		/** 
		 Gets a value indicating whether the current stream supports reading.
		*/
		@Override
		public boolean getCanRead()
		{
			return false;
		}

		/** 
		 Write any buffered data to underlying storage.
		*/
		@Override
		public void Flush()
		{
			baseStream_.Flush();
		}

		/** 
		 Gets a value indicating whether the current stream supports writing.
		*/
		@Override
		public boolean getCanWrite()
		{
			return baseStream_.CanWrite;
		}

		/** 
		 Gets a value indicating whether the current stream supports seeking.
		*/
		@Override
		public boolean getCanSeek()
		{
			return false;
		}

		/** 
		 Get the length in bytes of the stream.
		*/
		@Override
		public long getLength()
		{
			return 0;
		}

		/** 
		 Gets or sets the position within the current stream.
		*/
		@Override
		public long getPosition()
		{
			return baseStream_.Position;
		}

		@Override
		public void setPosition(long value)
		{
		}

		/** 
		 Reads a sequence of bytes from the current stream and advances the position within the stream by the number of bytes read.
		 
		 @param buffer An array of bytes. When this method returns, the buffer contains the specified byte array with the values between offset and (offset + count - 1) replaced by the bytes read from the current source.
		 @param offset The zero-based byte offset in buffer at which to begin storing the data read from the current stream.
		 @param count The maximum number of bytes to be read from the current stream.
		 @return 
		 The total number of bytes read into the buffer. This can be less than the number of bytes requested if that many bytes are not currently available, or zero (0) if the end of the stream has been reached.
		 
		 @exception T:System.ArgumentException The sum of offset and count is larger than the buffer length. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		 @exception T:System.NotSupportedException The stream does not support reading. 
		 @exception T:System.ArgumentNullException buffer is null. 
		 @exception T:System.IO.IOException An I/O error occurs. 
		 @exception T:System.ArgumentOutOfRangeException offset or count is negative. 
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
		@Override
		public int Read(byte[] buffer, int offset, int count)
		{
			return 0;
		}

		/** 
		 Sets the position within the current stream.
		 
		 @param offset A byte offset relative to the origin parameter.
		 @param origin A value of type <see cref="T:System.IO.SeekOrigin"></see> indicating the reference point used to obtain the new position.
		 @return 
		 The new position within the current stream.
		 
		 @exception T:System.IO.IOException An I/O error occurs. 
		 @exception T:System.NotSupportedException The stream does not support seeking, such as if the stream is constructed from a pipe or console output. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		*/
		@Override
		public long Seek(long offset, SeekOrigin origin)
		{
			return 0;
		}

		/** 
		 Sets the length of the current stream.
		 
		 @param value The desired length of the current stream in bytes.
		 @exception T:System.NotSupportedException The stream does not support both writing and seeking, such as if the stream is constructed from a pipe or console output. 
		 @exception T:System.IO.IOException An I/O error occurs. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		*/
		@Override
		public void SetLength(long value)
		{
		}

		/** 
		 Writes a sequence of bytes to the current stream and advances the current position within this stream by the number of bytes written.
		 
		 @param buffer An array of bytes. This method copies count bytes from buffer to the current stream.
		 @param offset The zero-based byte offset in buffer at which to begin copying bytes to the current stream.
		 @param count The number of bytes to be written to the current stream.
		 @exception T:System.IO.IOException An I/O error occurs. 
		 @exception T:System.NotSupportedException The stream does not support writing. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		 @exception T:System.ArgumentNullException buffer is null. 
		 @exception T:System.ArgumentException The sum of offset and count is greater than the buffer length. 
		 @exception T:System.ArgumentOutOfRangeException offset or count is negative. 
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
		@Override
		public void Write(byte[] buffer, int offset, int count)
		{
			baseStream_.Write(buffer, offset, count);
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Instance Fields
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		private Stream baseStream_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}

	/** 
	 A <see cref="PartialInputStream"/> is an <see cref="InflaterInputStream"/>
	 whose data is only a part or subsection of a file.
	*/
	private static class PartialInputStream extends Stream
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Constructors
		/** 
		 Initialise a new instance of the <see cref="PartialInputStream"/> class.
		 
		 @param zipFile The <see cref="ZipFile"/> containing the underlying stream to use for IO.
		 @param start The start of the partial data.
		 @param length The length of the partial data.
		*/
		public PartialInputStream(ZipFile zipFile, long start, long length)
		{
			start_ = start;
			length_ = length;

			// Although this is the only time the zipfile is used
			// keeping a reference here prevents premature closure of
			// this zip file and thus the baseStream_.

			// Code like this will cause apparently random failures depending
			// on the size of the files and when garbage is collected.
			//
			// ZipFile z = new ZipFile (stream);
			// Stream reader = z.GetInputStream(0);
			// uses reader here....
			zipFile_ = zipFile;
			baseStream_ = zipFile_.baseStream_;
			readPos_ = start;
			end_ = start + length;
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

		/** 
		 Read a byte from this stream.
		 
		 @return Returns the byte read or -1 on end of stream.
		*/
		@Override
		public int ReadByte()
		{
			if (readPos_ >= end_)
			{
				 // -1 is the correct value at end of stream.
				return -1;
			}

			synchronized (baseStream_)
			{
				baseStream_.Seek(readPos_++, SeekOrigin.Begin);
				return baseStream_.ReadByte();
			}
		}

		/** 
		 Close this <see cref="PartialInputStream">partial input stream</see>.
		 
		 
		 The underlying stream is not closed.  Close the parent ZipFile class to do that.
		 
		*/
		@Override
		public void Close()
		{
			// Do nothing at all!
		}

		/** 
		 Reads a sequence of bytes from the current stream and advances the position within the stream by the number of bytes read.
		 
		 @param buffer An array of bytes. When this method returns, the buffer contains the specified byte array with the values between offset and (offset + count - 1) replaced by the bytes read from the current source.
		 @param offset The zero-based byte offset in buffer at which to begin storing the data read from the current stream.
		 @param count The maximum number of bytes to be read from the current stream.
		 @return 
		 The total number of bytes read into the buffer. This can be less than the number of bytes requested if that many bytes are not currently available, or zero (0) if the end of the stream has been reached.
		 
		 @exception T:System.ArgumentException The sum of offset and count is larger than the buffer length. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		 @exception T:System.NotSupportedException The stream does not support reading. 
		 @exception T:System.ArgumentNullException buffer is null. 
		 @exception T:System.IO.IOException An I/O error occurs. 
		 @exception T:System.ArgumentOutOfRangeException offset or count is negative. 
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
		@Override
		public int Read(byte[] buffer, int offset, int count)
		{
			synchronized (baseStream_)
			{
				if (count > end_ - readPos_)
				{
					count = (int)(end_ - readPos_);
					if (count == 0)
					{
						return 0;
					}
				}

				baseStream_.Seek(readPos_, SeekOrigin.Begin);
				int readCount = baseStream_.Read(buffer, offset, count);
				if (readCount > 0)
				{
					readPos_ += readCount;
				}
				return readCount;
			}
		}

		/** 
		 Writes a sequence of bytes to the current stream and advances the current position within this stream by the number of bytes written.
		 
		 @param buffer An array of bytes. This method copies count bytes from buffer to the current stream.
		 @param offset The zero-based byte offset in buffer at which to begin copying bytes to the current stream.
		 @param count The number of bytes to be written to the current stream.
		 @exception T:System.IO.IOException An I/O error occurs. 
		 @exception T:System.NotSupportedException The stream does not support writing. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		 @exception T:System.ArgumentNullException buffer is null. 
		 @exception T:System.ArgumentException The sum of offset and count is greater than the buffer length. 
		 @exception T:System.ArgumentOutOfRangeException offset or count is negative. 
		*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
		@Override
		public void Write(byte[] buffer, int offset, int count)
		{
			throw new UnsupportedOperationException();
		}

		/** 
		 When overridden in a derived class, sets the length of the current stream.
		 
		 @param value The desired length of the current stream in bytes.
		 @exception T:System.NotSupportedException The stream does not support both writing and seeking, such as if the stream is constructed from a pipe or console output. 
		 @exception T:System.IO.IOException An I/O error occurs. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		*/
		@Override
		public void SetLength(long value)
		{
			throw new UnsupportedOperationException();
		}

		/** 
		 When overridden in a derived class, sets the position within the current stream.
		 
		 @param offset A byte offset relative to the origin parameter.
		 @param origin A value of type <see cref="T:System.IO.SeekOrigin"></see> indicating the reference point used to obtain the new position.
		 @return 
		 The new position within the current stream.
		 
		 @exception T:System.IO.IOException An I/O error occurs. 
		 @exception T:System.NotSupportedException The stream does not support seeking, such as if the stream is constructed from a pipe or console output. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		*/
		@Override
		public long Seek(long offset, SeekOrigin origin)
		{
			long newPos = readPos_;

			switch (origin)
			{
				case Begin:
					newPos = start_ + offset;
					break;

				case Current:
					newPos = readPos_ + offset;
					break;

				case End:
					newPos = end_ + offset;
					break;
			}

			if (newPos < start_)
			{
				throw new IllegalArgumentException("Negative position is invalid");
			}

			if (newPos >= end_)
			{
				throw new IOException("Cannot seek past end");
			}
			readPos_ = newPos;
			return readPos_;
		}

		/** 
		 Clears all buffers for this stream and causes any buffered data to be written to the underlying device.
		 
		 @exception T:System.IO.IOException An I/O error occurs. 
		*/
		@Override
		public void Flush()
		{
			// Nothing to do.
		}

		/** 
		 Gets or sets the position within the current stream.
		 
		 <value></value>
		 @return The current position within the stream.
		 @exception T:System.IO.IOException An I/O error occurs. 
		 @exception T:System.NotSupportedException The stream does not support seeking. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		*/
		@Override
		public long getPosition()
		{
			return readPos_ - start_;
		}
		@Override
		public void setPosition(long value)
		{
			long newPos = start_ + value;

			if (newPos < start_)
			{
				throw new IllegalArgumentException("Negative position is invalid");
			}

			if (newPos >= end_)
			{
				throw new IllegalStateException("Cannot seek past end");
			}
			readPos_ = newPos;
		}

		/** 
		 Gets the length in bytes of the stream.
		 
		 <value></value>
		 @return A long value representing the length of the stream in bytes.
		 @exception T:System.NotSupportedException A class derived from Stream does not support seeking. 
		 @exception T:System.ObjectDisposedException Methods were called after the stream was closed. 
		*/
		@Override
		public long getLength()
		{
			return length_;
		}

		/** 
		 Gets a value indicating whether the current stream supports writing.
		 
		 <value>false</value>
		 @return true if the stream supports writing; otherwise, false.
		*/
		@Override
		public boolean getCanWrite()
		{
			return false;
		}

		/** 
		 Gets a value indicating whether the current stream supports seeking.
		 
		 <value>true</value>
		 @return true if the stream supports seeking; otherwise, false.
		*/
		@Override
		public boolean getCanSeek()
		{
			return true;
		}

		/** 
		 Gets a value indicating whether the current stream supports reading.
		 
		 <value>true.</value>
		 @return true if the stream supports reading; otherwise, false.
		*/
		@Override
		public boolean getCanRead()
		{
			return true;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_0 && !NET_1_1 && !NETCF_1_0
		/** 
		 Gets a value that determines whether the current stream can time out.
		 
		 <value></value>
		 @return A value that determines whether the current stream can time out.
		*/
		@Override
		public boolean getCanTimeout()
		{
			return baseStream_.CanTimeout;
		}
//#endif
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Instance Fields
		private ZipFile zipFile_;
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		private Stream baseStream_;
		private long start_;
		private long length_;
		private long readPos_;
		private long end_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}