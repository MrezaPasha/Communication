package Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public;

import ProtoBuf.*;
import Rasad.VideoSurveillance.Core.*;
import Rasad.VideoSurveillance.Core.Services.MobileNotificationService.*;

public enum MobileSettingsChangeType 
{
	None((short)0),
	GSMSettings((short)1),
	Camera((short)2),
	CameraPresets((short)3),
	MobileTemplates((short)4),
	ServerSettings((short)5);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, MobileSettingsChangeType> mappings;
	private static java.util.HashMap<Short, MobileSettingsChangeType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MobileSettingsChangeType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, MobileSettingsChangeType>();
				}
			}
		}
		return mappings;
	}

	private MobileSettingsChangeType(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static MobileSettingsChangeType forValue(short value)
	{
		return getMappings().get(value);
	}
}