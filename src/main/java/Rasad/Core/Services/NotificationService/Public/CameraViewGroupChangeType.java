package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;

public class CameraViewGroupChangeType
{

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] None = 0,
	public static final CameraViewGroupChangeType None = new CameraViewGroupChangeType(0);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Add = 1,
	public static final CameraViewGroupChangeType Add = new CameraViewGroupChangeType(1);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Edit = 2,
	public static final CameraViewGroupChangeType Edit = new CameraViewGroupChangeType(2);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Delete = 4,
	public static final CameraViewGroupChangeType Delete = new CameraViewGroupChangeType(4);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] ParentChanged = 8,
	public static final CameraViewGroupChangeType ParentChanged = new CameraViewGroupChangeType(8);


	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, CameraViewGroupChangeType> mappings;
	private static java.util.HashMap<Integer, CameraViewGroupChangeType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraViewGroupChangeType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, CameraViewGroupChangeType>();
				}
			}
		}
		return mappings;
	}

	private CameraViewGroupChangeType(int value)
	{
		intValue = value;
		synchronized (CameraViewGroupChangeType.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static CameraViewGroupChangeType forValue(int value)
	{
		synchronized (CameraViewGroupChangeType.class)
		{
			CameraViewGroupChangeType enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new CameraViewGroupChangeType(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}