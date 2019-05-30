package Rasad.VideoSurveillance.Core;

public enum MapObjectType 
{
	Camera(0),
	ChildMap(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, MapObjectType> mappings;
	private static java.util.HashMap<Integer, MapObjectType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MapObjectType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, MapObjectType>();
				}
			}
		}
		return mappings;
	}

	private MapObjectType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static MapObjectType forValue(int value)
	{
		return getMappings().get(value);
	}
}