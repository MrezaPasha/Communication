package main.java.Rasad.Core.Services.NotificationService.Streamer;

import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CameraStreamerAddressChangeType : byte
public enum CameraStreamerAddressChangeType 
{
	AddOrUpdate((byte)0),
	Remove((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, CameraStreamerAddressChangeType> mappings;
	private static java.util.HashMap<Byte, CameraStreamerAddressChangeType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraStreamerAddressChangeType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, CameraStreamerAddressChangeType>();
				}
			}
		}
		return mappings;
	}

	private CameraStreamerAddressChangeType(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static CameraStreamerAddressChangeType forValue(byte value)
	{
		return getMappings().get(value);
	}
}