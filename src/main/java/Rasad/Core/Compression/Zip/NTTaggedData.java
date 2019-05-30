package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

/** 
 Class handling NT date time values.
*/
public class NTTaggedData implements ITaggedData
{
	/** 
	 Get the ID for this tagged data value.
	*/
	public final short getTagID()
	{
		return 10;
	}

	/** 
	 Set the data from the raw values provided.
	 
	 @param data The raw data to extract values from.
	 @param index The index to start extracting values from.
	 @param count The number of bytes available.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetData(byte[] data, int index, int count)
	public final void SetData(byte[] data, int index, int count)
	{
		try (MemoryStream ms = new MemoryStream(data, index, count, false))
		{
		try (ZipHelperStream helperStream = new ZipHelperStream(ms))
		{
			helperStream.ReadLEInt(); // Reserved
			while (helperStream.getPosition() < helperStream.getLength())
			{
				int ntfsTag = helperStream.ReadLEShort();
				int ntfsLength = helperStream.ReadLEShort();
				if (ntfsTag == 1)
				{
					if (ntfsLength >= 24)
					{
						long lastModificationTicks = helperStream.ReadLELong();
						_lastModificationTime = LocalDateTime.FromFileTime(lastModificationTicks);

						long lastAccessTicks = helperStream.ReadLELong();
						_lastAccessTime = LocalDateTime.FromFileTime(lastAccessTicks);

						long createTimeTicks = helperStream.ReadLELong();
						_createTime = LocalDateTime.FromFileTime(createTimeTicks);
					}
					break;
				}
				else
				{
					// An unknown NTFS tag so simply skip it.
					helperStream.Seek(ntfsLength, SeekOrigin.Current);
				}
			}
		}
		}
	}

	/** 
	 Get the binary data representing this instance.
	 
	 @return The raw binary data representing this instance.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] GetData()
	public final byte[] GetData()
	{
		try (MemoryStream ms = new MemoryStream())
		{
		try (ZipHelperStream helperStream = new ZipHelperStream(ms))
		{
			helperStream.setIsStreamOwner(false);
			helperStream.WriteLEInt(0); // Reserved
			helperStream.WriteLEShort(1); // Tag
			helperStream.WriteLEShort(24); // Length = 3 x 8.
			helperStream.WriteLELong(_lastModificationTime.ToFileTime());
			helperStream.WriteLELong(_lastAccessTime.ToFileTime());
			helperStream.WriteLELong(_createTime.ToFileTime());
			return ms.ToArray();
		}
		}
	}

	/** 
	 Test a <see cref="DateTime"> valuie to see if is valid and can be represented here.</see>
	 
	 @param value The <see cref="DateTime">value</see> to test.
	 @return Returns true if the value is valid and can be represented; false if not.
	 
	 NTFS filetimes are 64-bit unsigned integers, stored in Intel
	 (least significant byte first) byte order. They determine the
	 number of 1.0E-07 seconds (1/10th microseconds!) past WinNT "epoch",
	 which is "01-Jan-1601 00:00:00 UTC". 28 May 60056 is the upper limit
	 
	*/
	public static boolean IsValidValue(LocalDateTime value)
	{
		boolean result = true;
		try
		{
			value.ToFileTimeUtc();
		}
		catch (java.lang.Exception e)
		{
			result = false;
		}
		return result;
	}

	/** 
	 Get/set the <see cref="DateTime">last modification time</see>.
	*/
	public final LocalDateTime getLastModificationTime()
	{
		return _lastModificationTime;
	}
	public final void setLastModificationTime(LocalDateTime value)
	{
		if (!IsValidValue(value))
		{
			throw new IndexOutOfBoundsException("value");
		}
		_lastModificationTime = value;
	}

	/** 
	 Get /set the <see cref="DateTime">create time</see>
	*/
	public final LocalDateTime getCreateTime()
	{
		return _createTime;
	}
	public final void setCreateTime(LocalDateTime value)
	{
		if (!IsValidValue(value))
		{
			throw new IndexOutOfBoundsException("value");
		}
		_createTime = value;
	}

	/** 
	 Get /set the <see cref="DateTime">last access time</see>.
	*/
	public final LocalDateTime getLastAccessTime()
	{
		return _lastAccessTime;
	}
	public final void setLastAccessTime(LocalDateTime value)
	{
		if (!IsValidValue(value))
		{
			throw new IndexOutOfBoundsException("value");
		}
		_lastAccessTime = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private LocalDateTime _lastAccessTime = LocalDateTime.FromFileTime(0);
	private LocalDateTime _lastModificationTime = LocalDateTime.FromFileTime(0);
	private LocalDateTime _createTime = LocalDateTime.FromFileTime(0);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}