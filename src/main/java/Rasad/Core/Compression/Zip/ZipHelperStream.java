package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

/** 
 This class assists with writing/reading from Zip files.
*/
public class ZipHelperStream extends Stream
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise an instance of this class.
	 
	 @param name The name of the file to open.
	*/
	public ZipHelperStream(String name)
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
		stream_ = new FileStream(name, FileMode.Open, FileAccess.ReadWrite);
		isOwner_ = true;
	}

	/** 
	 Initialise a new instance of <see cref="ZipHelperStream"/>.
	 
	 @param stream The stream to use.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public ZipHelperStream(Stream stream)
	{
		stream_ = stream;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get / set a value indicating wether the the underlying stream is owned or not.
	 
	 If the stream is owned it is closed when this instance is closed.
	*/
	public final boolean getIsStreamOwner()
	{
		return isOwner_;
	}
	public final void setIsStreamOwner(boolean value)
	{
		isOwner_ = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Base Stream Methods
	@Override
	public boolean getCanRead()
	{
		return stream_.CanRead;
	}

	@Override
	public boolean getCanSeek()
	{
		return stream_.CanSeek;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NET_1_0 && !NET_1_1 && !NETCF_1_0
	@Override
	public boolean getCanTimeout()
	{
		return stream_.CanTimeout;
	}
//#endif

	@Override
	public long getLength()
	{
		return stream_.Length;
	}

	@Override
	public long getPosition()
	{
		return stream_.Position;
	}
	@Override
	public void setPosition(long value)
	{
		stream_.Position = value;
	}

	@Override
	public boolean getCanWrite()
	{
		return stream_.CanWrite;
	}

	@Override
	public void Flush()
	{
		stream_.Flush();
	}

	@Override
	public long Seek(long offset, SeekOrigin origin)
	{
		return stream_.Seek(offset, origin);
	}

	@Override
	public void SetLength(long value)
	{
		stream_.SetLength(value);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
	@Override
	public int Read(byte[] buffer, int offset, int count)
	{
		return stream_.Read(buffer, offset, count);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
	{
		stream_.Write(buffer, offset, count);
	}

	/** 
	 Close the stream.
	 
	 
	 The underlying stream is closed only if <see cref="IsStreamOwner"/> is true.
	 
	*/
	@Override
	public void Close()
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		Stream toClose = stream_;
		stream_ = null;
		if (isOwner_ && (toClose != null))
		{
			isOwner_ = false;
			toClose.Close();
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	// Write the local file header
	// TODO: ZipHelperStream.WriteLocalHeader is not yet used and needs checking for ZipFile and ZipOuptutStream usage
	private void WriteLocalHeader(ZipEntry entry, EntryPatchData patchData)
	{
		CompressionMethod method = entry.getCompressionMethod();
		boolean headerInfoAvailable = true; // How to get this?
		boolean patchEntryHeader = false;

		WriteLEInt(ZipConstants.LocalHeaderSignature);

		WriteLEShort(entry.getVersion());
		WriteLEShort(entry.getFlags());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEShort((byte)method);
		WriteLEShort((byte)method.getValue());
		WriteLEInt((int)entry.getDosTime());

		if (headerInfoAvailable == true)
		{
			WriteLEInt((int)entry.getCrc());
			if (entry.getLocalHeaderRequiresZip64())
			{
				WriteLEInt(-1);
				WriteLEInt(-1);
			}
			else
			{
				WriteLEInt(entry.getIsCrypted() ? (int)entry.getCompressedSize() + ZipConstants.CryptoHeaderSize : (int)entry.getCompressedSize());
				WriteLEInt((int)entry.getSize());
			}
		}
		else
		{
			if (patchData != null)
			{
				patchData.setCrcPatchOffset(stream_.Position);
			}
			WriteLEInt(0); // Crc

			if (patchData != null)
			{
				patchData.setSizePatchOffset(stream_.Position);
			}

			// For local header both sizes appear in Zip64 Extended Information
			if (entry.getLocalHeaderRequiresZip64() && patchEntryHeader)
			{
				WriteLEInt(-1);
				WriteLEInt(-1);
			}
			else
			{
				WriteLEInt(0); // Compressed size
				WriteLEInt(0); // Uncompressed size
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

		if (entry.getLocalHeaderRequiresZip64() && (headerInfoAvailable || patchEntryHeader))
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

			if (patchData != null)
			{
				patchData.setSizePatchOffset(ed.getCurrentReadIndex());
			}
		}
		else
		{
			ed.Delete(1);
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] extra = ed.GetEntryData();
		byte[] extra = ed.GetEntryData();

		WriteLEShort(name.length);
		WriteLEShort(extra.length);

		if (name.length > 0)
		{
			stream_.Write(name, 0, name.length);
		}

		if (entry.getLocalHeaderRequiresZip64() && patchEntryHeader)
		{
			patchData.setSizePatchOffset(patchData.getSizePatchOffset() + stream_.Position);
		}

		if (extra.length > 0)
		{
			stream_.Write(extra, 0, extra.length);
		}
	}

	/** 
	 Locates a block with the desired <paramref name="signature"/>.
	 
	 @param signature The signature to find.
	 @param endLocation Location, marking the end of block.
	 @param minimumBlockSize Minimum size of the block.
	 @param maximumVariableData The maximum variable data.
	 @return Eeturns the offset of the first byte after the signature; -1 if not found
	*/
	public final long LocateBlockWithSignature(int signature, long endLocation, int minimumBlockSize, int maximumVariableData)
	{
		long pos = endLocation - minimumBlockSize;
		if (pos < 0)
		{
			return -1;
		}

		long giveUpMarker = Math.max(pos - maximumVariableData, 0);

		// TODO: This loop could be optimised for speed.
		do
		{
			if (pos < giveUpMarker)
			{
				return -1;
			}
			Seek(pos--, SeekOrigin.Begin);
		} while (ReadLEInt() != signature);

		return getPosition();
	}

	/** 
	 Write Zip64 end of central directory records (File header and locator).
	 
	 @param noOfEntries The number of entries in the central directory.
	 @param sizeEntries The size of entries in the central directory.
	 @param centralDirOffset The offset of the dentral directory.
	*/
	public final void WriteZip64EndOfCentralDirectory(long noOfEntries, long sizeEntries, long centralDirOffset)
	{
		long centralSignatureOffset = stream_.Position;
		WriteLEInt(ZipConstants.Zip64CentralFileHeaderSignature);
		WriteLELong(44); // Size of this record (total size of remaining fields in header or full size - 12)
		WriteLEShort(ZipConstants.VersionMadeBy); // Version made by
		WriteLEShort(ZipConstants.VersionZip64); // Version to extract
		WriteLEInt(0); // Number of this disk
		WriteLEInt(0); // number of the disk with the start of the central directory
		WriteLELong(noOfEntries); // No of entries on this disk
		WriteLELong(noOfEntries); // Total No of entries in central directory
		WriteLELong(sizeEntries); // Size of the central directory
		WriteLELong(centralDirOffset); // offset of start of central directory
		// zip64 extensible data sector not catered for here (variable size)

		// Write the Zip64 end of central directory locator
		WriteLEInt(ZipConstants.Zip64CentralDirLocatorSignature);

		// no of the disk with the start of the zip64 end of central directory
		WriteLEInt(0);

		// relative offset of the zip64 end of central directory record
		WriteLELong(centralSignatureOffset);

		// total number of disks
		WriteLEInt(1);
	}

	/** 
	 Write the required records to end the central directory.
	 
	 @param noOfEntries The number of entries in the directory.
	 @param sizeEntries The size of the entries in the directory.
	 @param startOfCentralDirectory The start of the central directory.
	 @param comment The archive comment.  (This can be null).
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void WriteEndOfCentralDirectory(long noOfEntries, long sizeEntries, long startOfCentralDirectory, byte[] comment)
	public final void WriteEndOfCentralDirectory(long noOfEntries, long sizeEntries, long startOfCentralDirectory, byte[] comment)
	{

		if ((noOfEntries >= 0xffffL) || (startOfCentralDirectory >= 0xffffffffL) || (sizeEntries >= 0xffffffffL))
		{
			WriteZip64EndOfCentralDirectory(noOfEntries, sizeEntries, startOfCentralDirectory);
		}

		WriteLEInt(ZipConstants.EndOfCentralDirectorySignature);

		// TODO: ZipFile Multi disk handling not done
		WriteLEShort(0); // number of this disk
		WriteLEShort(0); // no of disk with start of central dir


		// Number of entries
		if (noOfEntries >= 0xffffL)
		{
			WriteLEUshort((short)0xffff); // Zip64 marker
			WriteLEUshort((short)0xffff);
		}
		else
		{
			WriteLEShort((short)noOfEntries); // entries in central dir for this disk
			WriteLEShort((short)noOfEntries); // total entries in central directory
		}

		// Size of the central directory
		if (sizeEntries >= 0xffffffffL)
		{
			WriteLEUint((int)0xffffffff); // Zip64 marker
		}
		else
		{
			WriteLEInt((int)sizeEntries);
		}


		// offset of start of central directory
		if (startOfCentralDirectory >= 0xffffffffL)
		{
			WriteLEUint((int)0xffffffff); // Zip64 marker
		}
		else
		{
			WriteLEInt((int)startOfCentralDirectory);
		}

		int commentLength = (comment != null) ? comment.length : 0;

		if (commentLength > 0xffff)
		{
			throw new ZipException(String.format("Comment length(%1$s) is too long can only be 64K", commentLength));
		}

		WriteLEShort(commentLength);

		if (commentLength > 0)
		{
			Write(comment, 0, comment.length);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region LE value reading/writing
	/** 
	 Read an unsigned short in little endian byte order.
	 
	 @return Returns the value read.
	 @exception IOException
	 An i/o error occurs.
	 
	 @exception EndOfStreamException
	 The file ends prematurely
	 
	*/
	public final int ReadLEShort()
	{
		int byteValue1 = stream_.ReadByte();

		if (byteValue1 < 0)
		{
			throw new EndOfStreamException();
		}

		int byteValue2 = stream_.ReadByte();
		if (byteValue2 < 0)
		{
			throw new EndOfStreamException();
		}

		return byteValue1 | (byteValue2 << 8);
	}

	/** 
	 Read an int in little endian byte order.
	 
	 @return Returns the value read.
	 @exception IOException
	 An i/o error occurs.
	 
	 @exception System.IO.EndOfStreamException
	 The file ends prematurely
	 
	*/
	public final int ReadLEInt()
	{
		return ReadLEShort() | (ReadLEShort() << 16);
	}

	/** 
	 Read a long in little endian byte order.
	 
	 @return The value read.
	*/
	public final long ReadLELong()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint)ReadLEInt() | ((long)ReadLEInt() << 32);
		return (int)ReadLEInt() | ((long)ReadLEInt() << 32);
	}

	/** 
	 Write an unsigned short in little endian byte order.
	 
	 @param value The value to write.
	*/
	public final void WriteLEShort(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: stream_.WriteByte((byte)(value & 0xff));
		stream_.WriteByte((byte)(value & 0xff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: stream_.WriteByte((byte)((value >> 8) & 0xff));
		stream_.WriteByte((byte)((value >> 8) & 0xff));
	}

	/** 
	 Write a ushort in little endian byte order.
	 
	 @param value The value to write.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void WriteLEUshort(ushort value)
	public final void WriteLEUshort(short value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: stream_.WriteByte((byte)(value & 0xff));
		stream_.WriteByte((byte)(value & 0xff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: stream_.WriteByte((byte)(value >> 8));
		stream_.WriteByte((byte)(value >>> 8));
	}

	/** 
	 Write an int in little endian byte order.
	 
	 @param value The value to write.
	*/
	public final void WriteLEInt(int value)
	{
		WriteLEShort(value);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		WriteLEShort(value >> 16);
	}

	/** 
	 Write a uint in little endian byte order.
	 
	 @param value The value to write.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void WriteLEUint(uint value)
	public final void WriteLEUint(int value)
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
	 
	 @param value The value to write.
	*/
	public final void WriteLELong(long value)
	{
		WriteLEInt((int)value);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		WriteLEInt((int)(value >> 32));
	}

	/** 
	 Write a ulong in little endian byte order.
	 
	 @param value The value to write.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void WriteLEUlong(ulong value)
	public final void WriteLEUlong(long value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEUint((uint)(value & 0xffffffff));
		WriteLEUint((int)(value & 0xffffffff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: WriteLEUint((uint)(value >> 32));
		WriteLEUint((int)(value >>> 32));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Write a data descriptor.
	 
	 @param entry The entry to write a descriptor for.
	 @return Returns the number of descriptor bytes written.
	*/
	public final int WriteDataDescriptor(ZipEntry entry)
	{
		if (entry == null)
		{
			throw new NullPointerException("entry");
		}

		int result = 0;

		// Add data descriptor if flagged as required
		if ((entry.getFlags() & GeneralBitFlags.Descriptor.getValue()) != 0)
		{
			// The signature is not PKZIP originally but is now described as optional
			// in the PKZIP Appnote documenting trhe format.
			WriteLEInt(ZipConstants.DataDescriptorSignature);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'unchecked' in this context:
//ORIGINAL LINE: WriteLEInt(unchecked((int)(entry.Crc)));
			WriteLEInt((int)(entry.getCrc()));

			result += 8;

			if (entry.getLocalHeaderRequiresZip64())
			{
				WriteLELong(entry.getCompressedSize());
				WriteLELong(entry.getSize());
				result += 16;
			}
			else
			{
				WriteLEInt((int)entry.getCompressedSize());
				WriteLEInt((int)entry.getSize());
				result += 8;
			}
		}

		return result;
	}

	/** 
	 Read data descriptor at the end of compressed data.
	 
	 @param zip64 if set to <c>true</c> [zip64].
	 @param data The data to fill in.
	 @return Returns the number of bytes read in the descriptor.
	*/
	public final void ReadDataDescriptor(boolean zip64, DescriptorData data)
	{
		int intValue = ReadLEInt();

		// In theory this may not be a descriptor according to PKZIP appnote.
		// In practise its always there.
		if (intValue != ZipConstants.DataDescriptorSignature)
		{
			throw new ZipException("Data descriptor signature not found");
		}

		data.setCrc(ReadLEInt());

		if (zip64)
		{
			data.setCompressedSize(ReadLELong());
			data.setSize(ReadLELong());
		}
		else
		{
			data.setCompressedSize(ReadLEInt());
			data.setSize(ReadLEInt());
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private boolean isOwner_;
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream stream_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}