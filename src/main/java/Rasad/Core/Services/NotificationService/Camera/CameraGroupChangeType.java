package main.java.Rasad.Core.Services.NotificationService.Camera;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad._core.GenericProtobuf;

public class CameraGroupChangeType extends GenericProtobuf<CameraGroupChangeType>
{

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] None = 0,
	public static final CameraGroupChangeType None = new CameraGroupChangeType(0);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Add = 1,
	public static final CameraGroupChangeType Add = new CameraGroupChangeType(1);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Edit = 2,
	public static final CameraGroupChangeType Edit = new CameraGroupChangeType(2);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Delete = 4,
	public static final CameraGroupChangeType Delete = new CameraGroupChangeType(4);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] ParentChanged = 8,
	public static final CameraGroupChangeType ParentChanged = new CameraGroupChangeType(8);


	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, CameraGroupChangeType> mappings;
	private static java.util.HashMap<Integer, CameraGroupChangeType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraGroupChangeType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, CameraGroupChangeType>();
				}
			}
		}
		return mappings;
	}

	private CameraGroupChangeType(int value)
	{
		intValue = value;
		synchronized (CameraGroupChangeType.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static CameraGroupChangeType forValue(int value)
	{
		synchronized (CameraGroupChangeType.class)
		{
			CameraGroupChangeType enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new CameraGroupChangeType(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}