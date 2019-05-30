package Rasad.VideoSurveillance.Core;

public enum DisplayProfilePolicy
{
	AsDefined(0),
	AlwaysSmall(1),
	AlwaysMedium(2);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, DisplayProfilePolicy> mappings;
	private static java.util.HashMap<Integer, DisplayProfilePolicy> getMappings()
	{
		if (mappings == null)
		{
			synchronized (DisplayProfilePolicy.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, DisplayProfilePolicy>();
				}
			}
		}
		return mappings;
	}

	private DisplayProfilePolicy(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static DisplayProfilePolicy forValue(int value)
	{
		return getMappings().get(value);
	}
}