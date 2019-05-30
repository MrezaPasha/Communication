package main.java.Rasad.Core.Services.NotificationService.Recording;


import java.util.*;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum RecordingStorageChangeType : byte
public enum RecordingStorageChangeType 
{
	Insert((byte)0),
	Edit((byte)1),
	Delete((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, RecordingStorageChangeType> mappings;
	private static java.util.HashMap<Byte, RecordingStorageChangeType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RecordingStorageChangeType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, RecordingStorageChangeType>();
				}
			}
		}
		return mappings;
	}

	private RecordingStorageChangeType(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static RecordingStorageChangeType forValue(byte value)
	{
		return getMappings().get(value);
	}
}