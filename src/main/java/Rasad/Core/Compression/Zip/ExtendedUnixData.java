package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

/** 
 Class representing extended unix date time values.
*/
public class ExtendedUnixData implements ITaggedData
{
	/** 
	 Flags indicate which values are included in this instance.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] public enum Flags : byte
	public class Flags 
	{
		/** 
		 The modification time is included
		*/
		public static final Flags ModificationTime = new Flags(0x01);

		/** 
		 The access time is included
		*/
		public static final Flags AccessTime = new Flags(0x02);

		/** 
		 The create time is included.
		*/
		public static final Flags CreateTime = new Flags(0x04);

		public static final int SIZE = java.lang.Byte.SIZE;

		private byte byteValue;
		private static java.util.HashMap<Byte, Flags> mappings;
		private static java.util.HashMap<Byte, Flags> getMappings()
		{
			if (mappings == null)
			{
				synchronized (Flags.class)
				{
					if (mappings == null)
					{
						mappings = new java.util.HashMap<Byte, Flags>();
					}
				}
			}
			return mappings;
		}

		private Flags(byte value)
		{
			byteValue = value;
			synchronized (Flags.class)
			{
				getMappings().put(value, this);
			}
		}

		public byte getValue()
		{
			return byteValue;
		}

		public static Flags forValue(byte value)
		{
			synchronized (Flags.class)
			{
				Flags enumObj = getMappings().get(value);
				if (enumObj == null)
				{
					return new Flags(value);
				}
				else
				{
					return enumObj;
				}
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ITaggedData Members

	/** 
	 Get the ID
	*/
	public final short getTagID()
	{
		return 0x5455;
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
			// bit 0           if set, modification time is present
			// bit 1           if set, access time is present
			// bit 2           if set, creation time is present

			_flags = Flags.forValue((byte)helperStream.ReadByte());
			if (((_flags.getValue() & Flags.ModificationTime.getValue()) != 0) && (count >= 5))
			{
				int iTime = helperStream.ReadLEInt();

				_modificationTime = ((LocalDateTime.of(1970, 1, 1, 0, 0, 0)).ToUniversalTime() + new TimeSpan(0, 0, 0, iTime, 0)).ToLocalTime();
			}

			if ((_flags.getValue() & Flags.AccessTime.getValue()) != 0)
			{
				int iTime = helperStream.ReadLEInt();

				_lastAccessTime = ((LocalDateTime.of(1970, 1, 1, 0, 0, 0)).ToUniversalTime() + new TimeSpan(0, 0, 0, iTime, 0)).ToLocalTime();
			}

			if ((_flags.getValue() & Flags.CreateTime.getValue()) != 0)
			{
				int iTime = helperStream.ReadLEInt();

				_createTime = ((LocalDateTime.of(1970, 1, 1, 0, 0, 0)).ToUniversalTime() + new TimeSpan(0, 0, 0, iTime, 0)).ToLocalTime();
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
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: helperStream.WriteByte((byte)_flags);
			helperStream.WriteByte((byte)_flags.getValue()); // Flags
			if ((_flags.getValue() & Flags.ModificationTime.getValue()) != 0)
			{
				TimeSpan span = _modificationTime.ToUniversalTime() - (LocalDateTime.of(1970, 1, 1, 0, 0, 0)).ToUniversalTime();
				int seconds = (int)span.TotalSeconds;
				helperStream.WriteLEInt(seconds);
			}
			if ((_flags.getValue() & Flags.AccessTime.getValue()) != 0)
			{
				TimeSpan span = _lastAccessTime.ToUniversalTime() - (LocalDateTime.of(1970, 1, 1, 0, 0, 0)).ToUniversalTime();
				int seconds = (int)span.TotalSeconds;
				helperStream.WriteLEInt(seconds);
			}
			if ((_flags.getValue() & Flags.CreateTime.getValue()) != 0)
			{
				TimeSpan span = _createTime.ToUniversalTime() - (LocalDateTime.of(1970, 1, 1, 0, 0, 0)).ToUniversalTime();
				int seconds = (int)span.TotalSeconds;
				helperStream.WriteLEInt(seconds);
			}
			return ms.ToArray();
		}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Test a <see cref="DateTime"> value to see if is valid and can be represented here.</see>
	 
	 @param value The <see cref="DateTime">value</see> to test.
	 @return Returns true if the value is valid and can be represented; false if not.
	 The standard Unix time is a signed integer data type, directly encoding the Unix time number,
	 which is the number of seconds since 1970-01-01.
	 Being 32 bits means the values here cover a range of about 136 years.
	 The minimum representable time is 1901-12-13 20:45:52,
	 and the maximum representable time is 2038-01-19 03:14:07.
	 
	*/
	public static boolean IsValidValue(LocalDateTime value)
	{
		return ((value.compareTo(LocalDateTime.of(1901, 12, 13, 20, 45, 52)) >= 0) || (value.compareTo(LocalDateTime.of(2038, 1, 19, 03, 14, 07)) <= 0));
	}

	/** 
	 Get /set the Modification Time
	 
	 @exception ArgumentOutOfRangeException
	 @see IsValidValue
	*/
	public final LocalDateTime getModificationTime()
	{
		return _modificationTime;
	}
	public final void setModificationTime(LocalDateTime value)
	{
		if (!IsValidValue(value))
		{
			throw new IndexOutOfBoundsException("value");
		}

		_flags = Rasad.Core.Compression.Zip.ExtendedUnixData.Flags.forValue(_flags.getValue() | Flags.ModificationTime.getValue());
		_modificationTime = value;
	}

	/** 
	 Get / set the Access Time
	 
	 @exception ArgumentOutOfRangeException
	 @see IsValidValue
	*/
	public final LocalDateTime getAccessTime()
	{
		return _lastAccessTime;
	}
	public final void setAccessTime(LocalDateTime value)
	{
		if (!IsValidValue(value))
		{
			throw new IndexOutOfBoundsException("value");
		}

		_flags = Rasad.Core.Compression.Zip.ExtendedUnixData.Flags.forValue(_flags.getValue() | Flags.AccessTime.getValue());
		_lastAccessTime = value;
	}

	/** 
	 Get / Set the Create Time
	 
	 @exception ArgumentOutOfRangeException
	 @see IsValidValue
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

		_flags = Rasad.Core.Compression.Zip.ExtendedUnixData.Flags.forValue(_flags.getValue() | Flags.CreateTime.getValue());
		_createTime = value;
	}

	/** 
	 Get/set the <see cref="Flags">values</see> to include.
	*/
	private Flags getInclude()
	{
		return _flags;
	}
	private void setInclude(Flags value)
	{
		_flags = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private Flags _flags = Flags.values()[0];
	private LocalDateTime _modificationTime = LocalDateTime.of(1970,1,1);
	private LocalDateTime _lastAccessTime = LocalDateTime.of(1970, 1, 1);
	private LocalDateTime _createTime = LocalDateTime.of(1970, 1, 1);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}