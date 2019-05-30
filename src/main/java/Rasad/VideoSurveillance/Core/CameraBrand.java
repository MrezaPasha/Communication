package Rasad.VideoSurveillance.Core;

public enum CameraBrand 
{
	Unknown(0),
	Axis(1),
	Onvif(2),
	Bosch(3),
	Sony(4);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, CameraBrand> mappings;
	private static java.util.HashMap<Integer, CameraBrand> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraBrand.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, CameraBrand>();
				}
			}
		}
		return mappings;
	}

	private CameraBrand(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static CameraBrand forValue(int value)
	{
		return getMappings().get(value);
	}
}