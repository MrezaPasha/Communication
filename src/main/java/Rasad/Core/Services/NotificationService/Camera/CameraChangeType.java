package main.java.Rasad.Core.Services.NotificationService.Camera;

import Rasad.Core.*;
import Rasad.Core.Services.*;

public class CameraChangeType
{

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] None = 0,
	public static final CameraChangeType None = new CameraChangeType(0);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Add = 1,
	public static final CameraChangeType Add = new CameraChangeType(1);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Edit = 2,
	public static final CameraChangeType Edit = new CameraChangeType(2);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Delete = 4,
	public static final CameraChangeType Delete = new CameraChangeType(4);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] CameraActiveState = 8,
	public static final CameraChangeType CameraActiveState = new CameraChangeType(8);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] CameraDeactiveState = 16,
	public static final CameraChangeType CameraDeactiveState = new CameraChangeType(16);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] RecordingProgram = 32,
	public static final CameraChangeType RecordingProgram = new CameraChangeType(32);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] GuardTour = 64,
	public static final CameraChangeType GuardTour = new CameraChangeType(64);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] MotionDetectionSettings = 128,
	public static final CameraChangeType MotionDetectionSettings = new CameraChangeType(128);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] PluginAssignment = 256,
	public static final CameraChangeType PluginAssignment = new CameraChangeType(256);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] StreamingAssignment = 512,
	public static final CameraChangeType StreamingAssignment = new CameraChangeType(512);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] MulticastSettingsChanged = 1024,
	public static final CameraChangeType MulticastSettingsChanged = new CameraChangeType(1024);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, CameraChangeType> mappings;
	private static java.util.HashMap<Integer, CameraChangeType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraChangeType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, CameraChangeType>();
				}
			}
		}
		return mappings;
	}

	private CameraChangeType(int value)
	{
		intValue = value;
		synchronized (CameraChangeType.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static CameraChangeType forValue(int value)
	{
		synchronized (CameraChangeType.class)
		{
			CameraChangeType enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new CameraChangeType(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}