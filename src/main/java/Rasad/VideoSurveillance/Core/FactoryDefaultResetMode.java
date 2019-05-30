package Rasad.VideoSurveillance.Core;

public enum FactoryDefaultResetMode
{
	Soft(0),
	Hard(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, FactoryDefaultResetMode> mappings;
	private static java.util.HashMap<Integer, FactoryDefaultResetMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (FactoryDefaultResetMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, FactoryDefaultResetMode>();
				}
			}
		}
		return mappings;
	}

	private FactoryDefaultResetMode(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static FactoryDefaultResetMode forValue(int value)
	{
		return getMappings().get(value);
	}
}