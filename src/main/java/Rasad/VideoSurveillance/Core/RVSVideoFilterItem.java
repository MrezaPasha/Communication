package Rasad.VideoSurveillance.Core;

public enum RVSVideoFilterItem
{
	None(0),
	NoFilter(1),
	FishEye(2);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, RVSVideoFilterItem> mappings;
	private static java.util.HashMap<Integer, RVSVideoFilterItem> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RVSVideoFilterItem.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, RVSVideoFilterItem>();
				}
			}
		}
		return mappings;
	}

	private RVSVideoFilterItem(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static RVSVideoFilterItem forValue(int value)
	{
		return getMappings().get(value);
	}
}