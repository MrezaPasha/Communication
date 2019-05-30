package main.java.Rasad.Core.Services;

import Rasad.Core.Services.*;
import Rasad.Core.*;

public enum JoystickActionEnum
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("[نامشخـص]")] None = 0,
	None(0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دوربین قبلی")] PreviousCamera = 1,
	PreviousCamera(1),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دوربین بعدی")] NextCamera = 2,
	NextCamera(2),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("موقعیت قبلی")] PreviousPreset = 3,
	PreviousPreset(3),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("موقعیت بعدی")] NextPreset = 4,
	NextPreset(4),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایش (تمام صفحه / عادی)")] SwapFullScreen = 5,
	SwapFullScreen(5),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("بازپخش")] ShowPlayBack = 6,
	ShowPlayBack(6),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("عکس گرفتن")] TakeSnapshot = 7
	TakeSnapshot(7);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, JoystickActionEnum> mappings;
	private static java.util.HashMap<Integer, JoystickActionEnum> getMappings()
	{
		if (mappings == null)
		{
			synchronized (JoystickActionEnum.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, JoystickActionEnum>();
				}
			}
		}
		return mappings;
	}

	private JoystickActionEnum(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static JoystickActionEnum forValue(int value)
	{
		return getMappings().get(value);
	}
}