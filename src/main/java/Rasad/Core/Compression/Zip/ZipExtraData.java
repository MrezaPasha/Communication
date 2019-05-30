package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

/** 
 
 A class to handle the extra data field for Zip entries
 
 
 Extra data contains 0 or more values each prefixed by a header tag and length.
 They contain zero or more bytes of actual data.
 The data is held internally using a copy on write strategy.  This is more efficient but
 means that for extra data created by passing in data can have the values modified by the caller
 in some circumstances.
 
*/
public final class ZipExtraData implements Closeable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a default instance.
	*/
	public ZipExtraData()
	{
		Clear();
	}

	/** 
	 Initialise with known extra data.
	 
	 @param data The extra data.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ZipExtraData(byte[] data)
	public ZipExtraData(byte[] data)
	{
		if (data == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _data = new byte[0];
			_data = new byte[0];
		}
		else
		{
			_data = data;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get the raw extra data value
	 
	 @return Returns the raw byte[] extra data this instance represents.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] GetEntryData()
	public byte[] GetEntryData()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (Length > ushort.MaxValue)
		if (getLength() > Short.MAX_VALUE)
		{
			throw new ZipException("Data exceeds maximum length");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (byte[])_data.Clone();
		return (byte[])_data.clone();
	}

	/** 
	 Clear the stored data.
	*/
	public void Clear()
	{
		if ((_data == null) || (_data.length != 0))
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _data = new byte[0];
			_data = new byte[0];
		}
	}

	/** 
	 Gets the current extra data length.
	*/
	public int getLength()
	{
		return _data.length;
	}

	/** 
	 Get a read-only <see cref="Stream"/> for the associated tag.
	 
	 @param tag The tag to locate data for.
	 @return Returns a <see cref="Stream"/> containing tag data or null if no tag was found.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public Stream GetStreamForTag(int tag)
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		Stream result = null;
		if (Find(tag))
		{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
			result = new MemoryStream(_data, _index, _readValueLength, false);
		}
		return result;
	}

	/** 
	 Get the <see cref="ITaggedData">tagged data</see> for a tag.
	 
	 @param tag The tag to search for.
	 @return Returns a <see cref="ITaggedData">tagged value</see> or null if none found.
	*/
	private ITaggedData GetData(short tag)
	{
		ITaggedData result = null;
		if (Find(tag))
		{
			result = Create(tag, _data, _readValueStart, _readValueLength);
		}
		return result;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static ITaggedData Create(short tag, byte[] data, int offset, int count)
	private static ITaggedData Create(short tag, byte[] data, int offset, int count)
	{
		ITaggedData result = null;
		switch (tag)
		{
			case 0x000A:
				result = new NTTaggedData();
				break;
			case 0x5455:
				result = new ExtendedUnixData();
				break;
			default:
				result = new RawTaggedData(tag);
				break;
		}
		result.SetData(data, offset, count);
		return result;
	}

	/** 
	 Get the length of the last value found by <see cref="Find"/>
	 
	 This is only valid if <see cref="Find"/> has previously returned true.
	*/
	public int getValueLength()
	{
		return _readValueLength;
	}

	/** 
	 Get the index for the current read value.
	 
	 This is only valid if <see cref="Find"/> has previously returned true.
	 Initially the result will be the index of the first byte of actual data.  The value is updated after calls to
	 <see cref="ReadInt"/>, <see cref="ReadShort"/> and <see cref="ReadLong"/>. 
	*/
	public int getCurrentReadIndex()
	{
		return _index;
	}

	/** 
	 Get the number of bytes remaining to be read for the current value;
	*/
	public int getUnreadCount()
	{
		if ((_readValueStart > _data.length) || (_readValueStart < 4))
		{
			throw new ZipException("Find must be called before calling a Read method");
		}

		return _readValueStart + _readValueLength - _index;
	}

	/** 
	 Find an extra data value
	 
	 @param headerID The identifier for the value to find.
	 @return Returns true if the value was found; false otherwise.
	*/
	public boolean Find(int headerID)
	{
		_readValueStart = _data.length;
		_readValueLength = 0;
		_index = 0;

		int localLength = _readValueStart;
		int localTag = headerID - 1;

		// Trailing bytes that cant make up an entry (as there arent enough
		// bytes for a tag and length) are ignored!
		while ((localTag != headerID) && (_index < _data.length - 3))
		{
			localTag = ReadShortInternal();
			localLength = ReadShortInternal();
			if (localTag != headerID)
			{
				_index += localLength;
			}
		}

		boolean result = (localTag == headerID) && ((_index + localLength) <= _data.length);

		if (result)
		{
			_readValueStart = _index;
			_readValueLength = localLength;
		}

		return result;
	}

	/** 
	 Add a new entry to extra data.
	 
	 @param taggedData The <see cref="ITaggedData"/> value to add.
	*/
	public void AddEntry(ITaggedData taggedData)
	{
		if (taggedData == null)
		{
			throw new NullPointerException("taggedData");
		}
		AddEntry(taggedData.getTagID(), taggedData.GetData());
	}

	/** 
	 Add a new entry to extra data
	 
	 @param headerID The ID for this entry.
	 @param fieldData The data to add.
	 If the ID already exists its contents are replaced.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void AddEntry(int headerID, byte[] fieldData)
	public void AddEntry(int headerID, byte[] fieldData)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((headerID > ushort.MaxValue) || (headerID < 0))
		if ((headerID > Short.MAX_VALUE) || (headerID < 0))
		{
			throw new IndexOutOfBoundsException("headerID");
		}

		int addLength = (fieldData == null) ? 0 : fieldData.length;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (addLength > ushort.MaxValue)
		if (addLength > Short.MAX_VALUE)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("fieldData");
//#else
			throw new IndexOutOfBoundsException("fieldData", "exceeds maximum length");
//#endif
		}

		// Test for new length before adjusting data.
		int newLength = _data.length + addLength + 4;

		if (Find(headerID))
		{
			newLength -= (getValueLength() + 4);
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (newLength > ushort.MaxValue)
		if (newLength > Short.MAX_VALUE)
		{
			throw new ZipException("Data exceeds maximum length");
		}

		Delete(headerID);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] newData = new byte[newLength];
		byte[] newData = new byte[newLength];
		System.arraycopy(_data, 0, newData, 0, _data.length);
		int index = _data.length;
		_data = newData;
		tangible.RefObject<Integer> tempRef_index = new tangible.RefObject<Integer>(index);
		SetShort(tempRef_index, headerID);
	index = tempRef_index.argValue;
		tangible.RefObject<Integer> tempRef_index2 = new tangible.RefObject<Integer>(index);
		SetShort(tempRef_index2, addLength);
	index = tempRef_index2.argValue;
		if (fieldData != null)
		{
			System.arraycopy(fieldData, 0, newData, index, fieldData.length);
		}
	}

	/** 
	 Start adding a new entry.
	 
	 Add data using <see cref="AddData(byte[])"/>, <see cref="AddLeShort"/>, <see cref="AddLeInt"/>, or <see cref="AddLeLong"/>.
	 The new entry is completed and actually added by calling <see cref="AddNewEntry"/>
	 {@link AddEntry(ITaggedData)}
	*/
	public void StartNewEntry()
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
		_newEntry = new MemoryStream();
	}

	/** 
	 Add entry data added since <see cref="StartNewEntry"/> using the ID passed.
	 
	 @param headerID The identifier to use for this entry.
	*/
	public void AddNewEntry(int headerID)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] newData = _newEntry.ToArray();
		byte[] newData = _newEntry.ToArray();
		_newEntry = null;
		AddEntry(headerID, newData);
	}

	/** 
	 Add a byte of data to the pending new entry.
	 
	 @param data The byte to add.
	 {@link StartNewEntry}
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void AddData(byte data)
	public void AddData(byte data)
	{
		_newEntry.WriteByte(data);
	}

	/** 
	 Add data to a pending new entry.
	 
	 @param data The data to add.
	 {@link StartNewEntry}
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void AddData(byte[] data)
	public void AddData(byte[] data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		_newEntry.Write(data, 0, data.length);
	}

	/** 
	 Add a short value in little endian order to the pending new entry.
	 
	 @param toAdd The data to add.
	 {@link StartNewEntry}
	*/
	public void AddLeShort(int toAdd)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _newEntry.WriteByte((byte)toAdd);
			_newEntry.WriteByte((byte)toAdd);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _newEntry.WriteByte((byte)(toAdd >> 8));
			_newEntry.WriteByte((byte)(toAdd >> 8));
		}
	}

	/** 
	 Add an integer value in little endian order to the pending new entry.
	 
	 @param toAdd The data to add.
	 {@link StartNewEntry}
	*/
	public void AddLeInt(int toAdd)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
			AddLeShort((short)toAdd);
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			AddLeShort((short)(toAdd >> 16));
		}
	}

	/** 
	 Add a long value in little endian order to the pending new entry.
	 
	 @param toAdd The data to add.
	 {@link StartNewEntry}
	*/
	public void AddLeLong(long toAdd)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
			AddLeInt((int)(toAdd & 0xffffffff));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			AddLeInt((int)(toAdd >> 32));
		}
	}

	/** 
	 Delete an extra data field.
	 
	 @param headerID The identifier of the field to delete.
	 @return Returns true if the field was found and deleted.
	*/
	public boolean Delete(int headerID)
	{
		boolean result = false;

		if (Find(headerID))
		{
			result = true;
			int trueStart = _readValueStart - 4;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] newData = new byte[_data.Length - (ValueLength + 4)];
			byte[] newData = new byte[_data.length - (getValueLength() + 4)];
			System.arraycopy(_data, 0, newData, 0, trueStart);

			int trueEnd = trueStart + getValueLength() + 4;
			System.arraycopy(_data, trueEnd, newData, trueStart, _data.length - trueEnd);
			_data = newData;
		}
		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Reading Support
	/** 
	 Read a long in little endian form from the last <see cref="Find">found</see> data value
	 
	 @return Returns the long value read.
	*/
	public long ReadLong()
	{
		ReadCheck(8);
		return (ReadInt() & 0xffffffff) | (((long)ReadInt()) << 32);
	}

	/** 
	 Read an integer in little endian form from the last <see cref="Find">found</see> data value.
	 
	 @return Returns the integer read.
	*/
	public int ReadInt()
	{
		ReadCheck(4);

		int result = _data[_index] + (_data[_index + 1] << 8) + (_data[_index + 2] << 16) + (_data[_index + 3] << 24);
		_index += 4;
		return result;
	}

	/** 
	 Read a short value in little endian form from the last <see cref="Find">found</see> data value.
	 
	 @return Returns the short value read.
	*/
	public int ReadShort()
	{
		ReadCheck(2);
		int result = _data[_index] + (_data[_index + 1] << 8);
		_index += 2;
		return result;
	}

	/** 
	 Read a byte from an extra data
	 
	 @return The byte value read or -1 if the end of data has been reached.
	*/
	public int ReadByte()
	{
		int result = -1;
		if ((_index < _data.length) && (_readValueStart + _readValueLength > _index))
		{
			result = _data[_index];
			_index += 1;
		}
		return result;
	}

	/** 
	 Skip data during reading.
	 
	 @param amount The number of bytes to skip.
	*/
	public void Skip(int amount)
	{
		ReadCheck(amount);
		_index += amount;
	}

	private void ReadCheck(int length)
	{
		if ((_readValueStart > _data.length) || (_readValueStart < 4))
		{
			throw new ZipException("Find must be called before calling a Read method");
		}

		if (_index > _readValueStart + _readValueLength - length)
		{
			throw new ZipException("End of extra data");
		}

		if (_index + length < 4)
		{
			throw new ZipException("Cannot read before start of tag");
		}
	}

	/** 
	 Internal form of <see cref="ReadShort"/> that reads data at any location.
	 
	 @return Returns the short value read.
	*/
	private int ReadShortInternal()
	{
		if (_index > _data.length - 2)
		{
			throw new ZipException("End of extra data");
		}

		int result = _data[_index] + (_data[_index + 1] << 8);
		_index += 2;
		return result;
	}

	private void SetShort(tangible.RefObject<Integer> index, int source)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _data[index] = (byte)source;
		_data[index.argValue] = (byte)source;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _data[index + 1] = (byte)(source >> 8);
		_data[index.argValue + 1] = (byte)(source >> 8);
		index.argValue += 2;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IDisposable Members

	/** 
	 Dispose of this instance.
	*/
	public void close() throws IOException
	{
		if (_newEntry != null)
		{
			_newEntry.Close();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private int _index;
	private int _readValueStart;
	private int _readValueLength;

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
	private MemoryStream _newEntry;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] _data;
	private byte[] _data;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}