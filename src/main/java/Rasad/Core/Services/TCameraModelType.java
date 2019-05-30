package main.java.Rasad.Core.Services;

import Rasad.Core.*;

public enum TCameraModelType
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Dome")] Dome = 1,
	Dome(1),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Bullet")] Bullet = 2,
	Bullet(2),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Box")] Box = 3,
	Box(3),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("SpeedDome")] SpeedDome = 4
	SpeedDome(4);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, TCameraModelType> mappings;
	private static java.util.HashMap<Integer, TCameraModelType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (TCameraModelType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, TCameraModelType>();
				}
			}
		}
		return mappings;
	}

	private TCameraModelType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static TCameraModelType forValue(int value)
	{
		return getMappings().get(value);
	}
}