package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;

 </code>
 </example>
*/
public class ZipOutputStream extends DeflaterOutputStream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Creates a new Zip output stream, writing a zip archive.
	 
	 @param baseOutputStream
	 The output stream to which the archive contents are written.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public ZipOutputStream(Stream baseOutputStream)
	{
		super(baseOutputStream, new Deflater(Deflater.DEFAULT_COMPRESSION, true));
	}

	/** 
	 Creates a new Zip output stream, writing a zip archive.
	 
	 @param baseOutputStream The output stream to which the archive contents are written.
	 @param bufferSize Size of the buffer to use.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public ZipOutputStream(Stream baseOutputStream, int bufferSize)
	{
		super(baseOutputStream, new Deflater(Deflater.DEFAULT_COMPRESSION, true), bufferSize);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Gets a flag value of true if the central header has been added for this archive; false if it has not been added.
	 
	 No further entries can be added once this has been done.
	*/
	public final boolean getIsFinished()
	{
		return entries == null;
	}

	/** 
	 Set the zip file comment.
	 
	 @param comment
	 The comment text for the entire archive.
	 
	 <exception name ="ArgumentOutOfRangeException">
	 The converted comment is longer than 0xffff bytes.
	 
	*/
	public final void SetComment(String comment)
	{
		// TODO: Its not yet clear how to handle unicode comments here.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] commentBytes = ZipConstants.ConvertToArray(comment);
		byte[] commentBytes = ZipConstants.ConvertToArray(comment);
		if (commentBytes.length > 0xffff)
		{
			throw new IndexOutOfBoundsException("comment");
		}
		zipComment = commentBytes;
	}

	/** 
	 Sets the compression level.  The new level will be activated
	 immediately.
	 
	 @param level The new compression level (1 to 9).
	 @exception ArgumentOutOfRangeException
	 Level specified is not supported.
	 
	 <see cref="Rasad.Core.Compression.Zip.Compression.Deflater"/>
	*/
	public final void SetLevel(int level)
	{
		deflater_.SetLevel(level);
		defaultCompressionLevel = level;
	}

	/** 
	 Get the current deflater compression level
	 
	 @return The current compression level
	*/
	public final int GetLevel()
	{
		return deflater_.GetLevel();
	}

	/** 
	 Get / set a value indicating how Zip64 Extension usage is determined when adding entries.
	 
	 Older archivers may not understand Zip64 extensions.
	 If backwards compatability is an issue be careful when adding <see cref="ZipEntry.Size">entries</see> to an archive.
	 Setting this property to off is workable but less desirable as in those circumstances adding a file
	 larger then 4GB will fail.
	*/
	public final UseZip64 getUseZip64()
	{
		return useZip64_;
	}
	public final void setUseZip64(UseZip64 value)
	{
		useZip64_ = value;
	}

	/** 
	 Write an unsigned short in little endian byte order.
	*/
	private void WriteLeShort(int value)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: baseOutputStream_.WriteByte((byte)(value & 0xff));
			baseOutputStream_.WriteByte((byte)(value & 0xff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: baseOutputStream_.WriteByte((byte)((value >> 8) & 0xff));
			baseOutputStream_.WriteByte((byte)((value >> 8) & 0xff));
		}
	}

	/** 
	 Write an int in little endian byte order.
	*/
	private void WriteLeInt(int value)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
			WriteLeShort(value);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			WriteLeShort(value >> 16);
		}
	}

	/** 
	 Write an int in little endian byte order.
	*/
	private void WriteLeLong(long value)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
			WriteLeInt((int)value);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			WriteLeInt((int)(value >> 32));
		}
	}

	/** 
	 Starts a new Zip entry. It automatically closes the previous
	 entry if present.
	 All entry elements bar name are optional, but must be correct if present.
	 If the compression method is stored and the output is not patchable
	 the compression for that entry is automatically changed to deflate level 0
	 
	 @param entry
	 the entry.
	 
	 @exception System.ArgumentNullException
	 if entry passed is null.
	 
	 @exception System.IO.IOException
	 if an I/O error occured.
	 
	 @exception System.InvalidOperationException
	 if stream was finished
	 
	 @exception ZipException
	 Too many entries in the Zip file<br/>
	 Entry name is too long<br/>
	 Finish has already been called<br/>
	 
	*/
	public final void PutNextEntry(ZipEntry entry)
	{
		if (entry == null)
		{
			throw new NullPointerException("entry");
		}

		if (entries == null)
		{
			throw new IllegalStateException("ZipOutputStream was finished");
		}

		if (curEntry != null)
		{
			CloseEntry();
		}

		if (entries.size() == Integer.MAX_VALUE)
		{
			throw new ZipException("Too many entries for Zip file");
		}

		CompressionMethod method = entry.getCompressionMethod();
		int compressionLevel = defaultCompressionLevel;

		// Clear flags that the library manages internally
		entry.setFlags(entry.getFlags() & GeneralBitFlags.UnicodeText.getValue());
		patchEntryHeader = false;

		boolean headerInfoAvailable;

		// No need to compress - definitely no data.
		if (entry.getSize() == 0)
		{
			entry.setCompressedSize(entry.getSize());
			entry.setCrc(0);
			method = CompressionMethod.Stored;
			headerInfoAvailable = true;
		}
		else
		{
			headerInfoAvailable = (entry.getSize() >= 0) && entry.getHasCrc() && entry.getCompressedSize() >= 0;

			// Switch to deflation if storing isnt possible.
			if (method == CompressionMethod.Stored)
			{
				if (!headerInfoAvailable)
				{
					if (!CanPatchEntries)
					{
						// Can't patch entries so storing is not possible.
						method = CompressionMethod.Deflated;
						compressionLevel = 0;
					}
				}
				else // entry.size must be > 0
				{
					entry.setCompressedSize(entry.getSize());
					headerInfoAvailable = entry.getHasCrc();
				}
			}
		}

		if (headerInfoAvailable == false)
		{
			if (CanPatchEntries == false)
			{
				// Only way to record size and compressed size is to append a data descriptor
				// after compressed data.

				// Stored entries of this form have already been converted to deflating.
				entry.setFlags(entry.getFlags() | 8);
			}
			else
			{
				patchEntryHeader = true;
			}
		}

		if (Password != null)
		{
			entry.setIsCrypted(true);
			if (entry.getCrc() < 0)
			{
				// Need to append a data descriptor as the crc isnt available for use
				// with encryption, the date is used instead.  Setting the flag
				// indicates this to the decompressor.
				entry.setFlags(entry.getFlags() | 8);
			}
		}

		entry.setOffset(offset);
		entry.setCompressionMethod(CompressionMethod.forValue(method));

		curMethod = method;
		sizePatchPos = -1;

		if ((useZip64_ == UseZip64.On) || ((entry.getSize() < 0) && (useZip64_ == UseZip64.Dynamic)))
		{
			entry.ForceZip64();
		}

		// Write the local file header
		WriteLeInt(ZipConstants.LocalHeaderSignature);

		WriteLeShort(entry.getVersion());
		WriteLeShort(entry.getFlags());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLeShort((byte)entry.CompressionMethodForHeader);
		WriteLeShort((byte)entry.getCompressionMethodForHeader().getValue());
		WriteLeInt((int)entry.getDosTime());

		// TODO: Refactor header writing.  Its done in several places.
		if (headerInfoAvailable)
		{
			WriteLeInt((int)entry.getCrc());
			if (entry.getLocalHeaderRequiresZip64())
			{
				WriteLeInt(-1);
				WriteLeInt(-1);
			}
			else
			{
				WriteLeInt(entry.getIsCrypted() ? (int)entry.getCompressedSize() + ZipConstants.CryptoHeaderSize : (int)entry.getCompressedSize());
				WriteLeInt((int)entry.getSize());
			}
		}
		else
		{
			if (patchEntryHeader)
			{
				crcPatchPos = baseOutputStream_.Position;
			}
			WriteLeInt(0); // Crc

			if (patchEntryHeader)
			{
				sizePatchPos = baseOutputStream_.Position;
			}

			// For local header both sizes appear in Zip64 Extended Information
			if (entry.getLocalHeaderRequiresZip64() || patchEntryHeader)
			{
				WriteLeInt(-1);
				WriteLeInt(-1);
			}
			else
			{
				WriteLeInt(0); // Compressed size
				WriteLeInt(0); // Uncompressed size
			}
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
			if (headerInfoAvailable)
			{
				ed.AddLeLong(entry.getSize());
				ed.AddLeLong(entry.getCompressedSize());
			}
			else
			{
				ed.AddLeLong(-1);
				ed.AddLeLong(-1);
			}
			ed.AddNewEntry(1);

			if (!ed.Find(1))
			{
				throw new ZipException("Internal error cant find extra data");
			}

			if (patchEntryHeader)
			{
				sizePatchPos = ed.getCurrentReadIndex();
			}
		}
		else
		{
			ed.Delete(1);
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_1 && !NETCF_2_0
		if (entry.AESKeySize > 0)
		{
			AddExtraDataAES(entry, ed);
		}
//#endif
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] extra = ed.GetEntryData();
		byte[] extra = ed.GetEntryData();

		WriteLeShort(name.length);
		WriteLeShort(extra.length);

		if (name.length > 0)
		{
			baseOutputStream_.Write(name, 0, name.length);
		}

		if (entry.getLocalHeaderRequiresZip64() && patchEntryHeader)
		{
			sizePatchPos += baseOutputStream_.Position;
		}

		if (extra.length > 0)
		{
			baseOutputStream_.Write(extra, 0, extra.length);
		}

		offset += ZipConstants.LocalHeaderBaseSize + name.length + extra.length;
		// Fix offsetOfCentraldir for AES
		if (entry.AESKeySize > 0)
		{
			offset += entry.getAESOverheadSize();
		}

		// Activate the entry.
		curEntry = entry;
		crc.Reset();
		if (method == CompressionMethod.Deflated)
		{
			deflater_.Reset();
			deflater_.SetLevel(compressionLevel);
		}
		size = 0;

		if (entry.getIsCrypted())
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_1 && !NETCF_2_0
			if (entry.AESKeySize > 0)
			{
				WriteAESHeader(entry);
			}
			else
//#endif
			{
				if (entry.getCrc() < 0)
				{ // so testing Zip will says its ok
					WriteEncryptionHeader(entry.getDosTime() << 16);
				}
				else
				{
					WriteEncryptionHeader(entry.getCrc());
				}
			}
		}
	}

	/** 
	 Closes the current entry, updating header and footer information as required
	 
	 @exception System.IO.IOException
	 An I/O error occurs.
	 
	 @exception System.InvalidOperationException
	 No entry is active.
	 
	*/
	public final void CloseEntry()
	{
		if (curEntry == null)
		{
			throw new IllegalStateException("No open entry");
		}

		long csize = size;

		// First finish the deflater, if appropriate
		if (curMethod == CompressionMethod.Deflated)
		{
			if (size >= 0)
			{
				super.Finish();
				csize = deflater_.TotalOut;
			}
			else
			{
				deflater_.Reset();
			}
		}

		// Write the AES Authentication Code (a hash of the compressed and encrypted data)
		if (curEntry.AESKeySize > 0)
		{
			baseOutputStream_.Write(AESAuthCode, 0, 10);
		}

		if (curEntry.getSize() < 0)
		{
			curEntry.setSize(size);
		}
		else if (curEntry.getSize() != size)
		{
			throw new ZipException("size was " + size + ", but I expected " + curEntry.getSize());
		}

		if (curEntry.getCompressedSize() < 0)
		{
			curEntry.setCompressedSize(csize);
		}
		else if (curEntry.getCompressedSize() != csize)
		{
			throw new ZipException("compressed size was " + csize + ", but I expected " + curEntry.getCompressedSize());
		}

		if (curEntry.getCrc() < 0)
		{
			curEntry.setCrc(crc.getValue());
		}
		else if (curEntry.getCrc() != crc.getValue())
		{
			throw new ZipException("crc was " + crc.getValue() + ", but I expected " + curEntry.getCrc());
		}

		offset += csize;

		if (curEntry.getIsCrypted())
		{
			if (curEntry.AESKeySize > 0)
			{
				curEntry.setCompressedSize(curEntry.getCompressedSize() + curEntry.getAESOverheadSize());

			}
			else
			{
				curEntry.setCompressedSize(curEntry.getCompressedSize() + ZipConstants.CryptoHeaderSize);
			}
		}

		// Patch the header if possible
		if (patchEntryHeader)
		{
			patchEntryHeader = false;

			long curPos = baseOutputStream_.Position;
			baseOutputStream_.Seek(crcPatchPos, SeekOrigin.Begin);
			WriteLeInt((int)curEntry.getCrc());

			if (curEntry.getLocalHeaderRequiresZip64())
			{

				if (sizePatchPos == -1)
				{
					throw new ZipException("Entry requires zip64 but this has been turned off");
				}

				baseOutputStream_.Seek(sizePatchPos, SeekOrigin.Begin);
				WriteLeLong(curEntry.getSize());
				WriteLeLong(curEntry.getCompressedSize());
			}
			else
			{
				WriteLeInt((int)curEntry.getCompressedSize());
				WriteLeInt((int)curEntry.getSize());
			}
			baseOutputStream_.Seek(curPos, SeekOrigin.Begin);
		}

		// Add data descriptor if flagged as required
		if ((curEntry.getFlags() & 8) != 0)
		{
			WriteLeInt(ZipConstants.DataDescriptorSignature);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: WriteLeInt(unchecked((int)curEntry.Crc));
			WriteLeInt((int)curEntry.getCrc());

			if (curEntry.getLocalHeaderRequiresZip64())
			{
				WriteLeLong(curEntry.getCompressedSize());
				WriteLeLong(curEntry.getSize());
				offset += ZipConstants.Zip64DataDescriptorSize;
			}
			else
			{
				WriteLeInt((int)curEntry.getCompressedSize());
				WriteLeInt((int)curEntry.getSize());
				offset += ZipConstants.DataDescriptorSize;
			}
		}

		entries.add(curEntry);
		curEntry = null;
	}

	private void WriteEncryptionHeader(long crcValue)
	{
		offset += ZipConstants.CryptoHeaderSize;

		InitializePassword(Password);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] cryptBuffer = new byte[ZipConstants.CryptoHeaderSize];
		byte[] cryptBuffer = new byte[ZipConstants.CryptoHeaderSize];
		Random rnd = new Random();
		rnd.nextBytes(cryptBuffer);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: cryptBuffer[11] = (byte)(crcValue >> 24);
		cryptBuffer[11] = (byte)(crcValue >> 24);

		EncryptBlock(cryptBuffer, 0, cryptBuffer.length);
		baseOutputStream_.Write(cryptBuffer, 0, cryptBuffer.length);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_1 && !NETCF_2_0
	private static void AddExtraDataAES(ZipEntry entry, ZipExtraData extraData)
	{

		// Vendor Version: AE-1 IS 1. AE-2 is 2. With AE-2 no CRC is required and 0 is stored.
		final int VENDOR_VERSION = 2;
		// Vendor ID is the two ASCII characters "AE".
		final int VENDOR_ID = 0x4541; //not 6965;
		extraData.StartNewEntry();
		// Pack AES extra data field see http://www.winzip.com/aes_info.htm
		//extraData.AddLeShort(7);							// Data size (currently 7)
		extraData.AddLeShort(VENDOR_VERSION); // 2 = AE-2
		extraData.AddLeShort(VENDOR_ID); // "AE"
		extraData.AddData(entry.getAESEncryptionStrength()); //  1 = 128, 2 = 192, 3 = 256
		extraData.AddLeShort(entry.getCompressionMethod().getValue()); // The actual compression method used to compress the file
		extraData.AddNewEntry(0x9901);
	}

	// Replaces WriteEncryptionHeader for AES
	//
	private void WriteAESHeader(ZipEntry entry)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] salt;
		byte[] salt;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] pwdVerifier;
		byte[] pwdVerifier;
		tangible.OutObject<Byte> tempOut_salt = new tangible.OutObject<Byte>();
		tangible.OutObject<Byte> tempOut_pwdVerifier = new tangible.OutObject<Byte>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: InitializeAESPassword(entry, Password, out salt, out pwdVerifier);
		InitializeAESPassword(entry, Password, tempOut_salt, tempOut_pwdVerifier);
	pwdVerifier = tempOut_pwdVerifier.argValue;
	salt = tempOut_salt.argValue;
		// File format for AES:
		// Size (bytes)   Content
		// ------------   -------
		// Variable       Salt value
		// 2              Password verification value
		// Variable       Encrypted file data
		// 10             Authentication code
		//
		// Value in the "compressed size" fields of the local file header and the central directory entry
		// is the total size of all the items listed above. In other words, it is the total size of the
		// salt value, password verification value, encrypted data, and authentication code.
		baseOutputStream_.Write(salt, 0, salt.length);
		baseOutputStream_.Write(pwdVerifier, 0, pwdVerifier.length);
	}
//#endif

	/** 
	 Writes the given buffer to the current entry.
	 
	 @param buffer The buffer containing data to write.
	 @param offset The offset of the first byte to write.
	 @param count The number of bytes to write.
	 @exception ZipException Archive size is invalid
	 @exception System.InvalidOperationException No entry is active.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
	{
		if (curEntry == null)
		{
			throw new IllegalStateException("No open entry.");
		}

		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		if (offset < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("offset");
//#else
			throw new IndexOutOfBoundsException("offset", "Cannot be negative");
//#endif
		}

		if (count < 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("count");
//#else
			throw new IndexOutOfBoundsException("count", "Cannot be negative");
//#endif
		}

		if ((buffer.length - offset) < count)
		{
			throw new IllegalArgumentException("Invalid offset/count combination");
		}

		crc.Update(buffer, offset, count);
		size += count;

		switch (curMethod)
		{
			case Deflated:
				super.Write(buffer, offset, count);
				break;

			case Stored:
				if (Password != null)
				{
					CopyAndEncrypt(buffer, offset, count);
				}
				else
				{
					baseOutputStream_.Write(buffer, offset, count);
				}
				break;
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: void CopyAndEncrypt(byte[] buffer, int offset, int count)
	private void CopyAndEncrypt(byte[] buffer, int offset, int count)
	{
		final int CopyBufferSize = 4096;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] localBuffer = new byte[CopyBufferSize];
		byte[] localBuffer = new byte[CopyBufferSize];
		while (count > 0)
		{
			int bufferCount = (count < CopyBufferSize) ? count : CopyBufferSize;

			System.arraycopy(buffer, offset, localBuffer, 0, bufferCount);
			EncryptBlock(localBuffer, 0, bufferCount);
			baseOutputStream_.Write(localBuffer, 0, bufferCount);
			count -= bufferCount;
			offset += bufferCount;
		}
	}

	/** 
	 Finishes the stream.  This will write the central directory at the
	 end of the zip file and flush the stream.
	 
	 
	 This is automatically called when the stream is closed.
	 
	 @exception System.IO.IOException
	 An I/O error occurs.
	 
	 @exception ZipException
	 Comment exceeds the maximum length<br/>
	 Entry name exceeds the maximum length
	 
	*/
	@Override
	public void Finish()
	{
		if (entries == null)
		{
			return;
		}

		if (curEntry != null)
		{
			CloseEntry();
		}

		long numEntries = entries.size();
		long sizeEntries = 0;

		for (ZipEntry entry : entries)
		{
			WriteLeInt(ZipConstants.CentralHeaderSignature);
			WriteLeShort(ZipConstants.VersionMadeBy);
			WriteLeShort(entry.getVersion());
			WriteLeShort(entry.getFlags());
			WriteLeShort((short)entry.getCompressionMethodForHeader().getValue());
			WriteLeInt((int)entry.getDosTime());
			WriteLeInt((int)entry.getCrc());

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (entry.IsZip64Forced() || (entry.CompressedSize >= uint.MaxValue))
			if (entry.IsZip64Forced() || (entry.getCompressedSize() >= Integer.MAX_VALUE))
			{
				WriteLeInt(-1);
			}
			else
			{
				WriteLeInt((int)entry.getCompressedSize());
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (entry.IsZip64Forced() || (entry.Size >= uint.MaxValue))
			if (entry.IsZip64Forced() || (entry.getSize() >= Integer.MAX_VALUE))
			{
				WriteLeInt(-1);
			}
			else
			{
				WriteLeInt((int)entry.getSize());
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] name = ZipConstants.ConvertToArray(entry.Flags, entry.Name);
			byte[] name = ZipConstants.ConvertToArray(entry.getFlags(), entry.getName());

			if (name.length > 0xffff)
			{
				throw new ZipException("Name too long.");
			}

			ZipExtraData ed = new ZipExtraData(entry.getExtraData());

			if (entry.getCentralHeaderRequiresZip64())
			{
				ed.StartNewEntry();
				if (entry.IsZip64Forced() || (entry.getSize() >= 0xffffffffL))
				{
					ed.AddLeLong(entry.getSize());
				}

				if (entry.IsZip64Forced() || (entry.getCompressedSize() >= 0xffffffffL))
				{
					ed.AddLeLong(entry.getCompressedSize());
				}

				if (entry.getOffset() >= 0xffffffffL)
				{
					ed.AddLeLong(entry.getOffset());
				}

				ed.AddNewEntry(1);
			}
			else
			{
				ed.Delete(1);
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_1 && !NETCF_2_0
			if (entry.AESKeySize > 0)
			{
				AddExtraDataAES(entry, ed);
			}
//#endif
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] extra = ed.GetEntryData();
			byte[] extra = ed.GetEntryData();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] entryComment = (entry.Comment != null) ? ZipConstants.ConvertToArray(entry.Flags, entry.Comment) : new byte[0];
			byte[] entryComment = (entry.getComment() != null) ? ZipConstants.ConvertToArray(entry.getFlags(), entry.getComment()) : new byte[0];

			if (entryComment.length > 0xffff)
			{
				throw new ZipException("Comment too long.");
			}

			WriteLeShort(name.length);
			WriteLeShort(extra.length);
			WriteLeShort(entryComment.length);
			WriteLeShort(0); // disk number
			WriteLeShort(0); // internal file attributes
								// external file attributes

			if (entry.getExternalFileAttributes() != -1)
			{
				WriteLeInt(entry.getExternalFileAttributes());
			}
			else
			{
				if (entry.getIsDirectory())
				{ // mark entry as directory (from nikolam.AT.perfectinfo.com)
					WriteLeInt(16);
				}
				else
				{
					WriteLeInt(0);
				}
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (entry.Offset >= uint.MaxValue)
			if (entry.getOffset() >= Integer.MAX_VALUE)
			{
				WriteLeInt(-1);
			}
			else
			{
				WriteLeInt((int)entry.getOffset());
			}

			if (name.length > 0)
			{
				baseOutputStream_.Write(name, 0, name.length);
			}

			if (extra.length > 0)
			{
				baseOutputStream_.Write(extra, 0, extra.length);
			}

			if (entryComment.length > 0)
			{
				baseOutputStream_.Write(entryComment, 0, entryComment.length);
			}

			sizeEntries += ZipConstants.CentralHeaderBaseSize + name.length + extra.length + entryComment.length;
		}

		try (ZipHelperStream zhs = new ZipHelperStream(baseOutputStream_))
		{
			zhs.WriteEndOfCentralDirectory(numEntries, sizeEntries, offset, zipComment);
		}

		entries = null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 The entries for the archive.
	*/
	private ArrayList entries = new ArrayList();

	/** 
	 Used to track the crc of data added to entries.
	*/
	private Crc32 crc = new Crc32();

	/** 
	 The current entry being added.
	*/
	private ZipEntry curEntry;

	private int defaultCompressionLevel = Deflater.DEFAULT_COMPRESSION;

	private CompressionMethod curMethod = CompressionMethod.Deflated;

	/** 
	 Used to track the size of data for an entry during writing.
	*/
	private long size;

	/** 
	 Offset to be recorded for each entry in the central header.
	*/
	private long offset;

	/** 
	 Comment for the entire archive recorded in central header.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] zipComment = new byte[0];
	private byte[] zipComment = new byte[0];

	/** 
	 Flag indicating that header patching is required for the current entry.
	*/
	private boolean patchEntryHeader;

	/** 
	 Position to patch crc
	*/
	private long crcPatchPos = -1;

	/** 
	 Position to patch size.
	*/
	private long sizePatchPos = -1;

	// Default is dynamic which is not backwards compatible and can cause problems
	// with XP's built in compression which cant read Zip64 archives.
	// However it does avoid the situation were a large file is added and cannot be completed correctly.
	// NOTE: Setting the size for entries before they are added is the best solution!
	private UseZip64 useZip64_ = UseZip64.Dynamic;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}