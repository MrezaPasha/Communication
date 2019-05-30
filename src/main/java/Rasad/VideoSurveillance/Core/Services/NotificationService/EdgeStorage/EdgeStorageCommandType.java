package Rasad.VideoSurveillance.Core.Services.NotificationService.EdgeStorage;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.VideoSurveillance.Core.*;

public enum EdgeStorageCommandType
{
	None(0),
	SyncNow(1),
	NewRequestedTime(2);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, EdgeStorageCommandType> mappings;
	private static java.util.HashMap<Integer, EdgeStorageCommandType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (EdgeStorageCommandType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, EdgeStorageCommandType>();
				}
			}
		}
		return mappings;
	}

	private EdgeStorageCommandType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static EdgeStorageCommandType forValue(int value)
	{
		return getMappings().get(value);
	}
}