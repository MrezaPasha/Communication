package Rasad.VideoSurveillance.Core;

public enum RecordType
{
	None(0),

	/** 
	*/
	Normal(1),

	/** 
	*/
	OnEvent(2);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, RecordType> mappings;
	private static java.util.HashMap<Integer, RecordType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RecordType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, RecordType>();
				}
			}
		}
		return mappings;
	}

	private RecordType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static RecordType forValue(int value)
	{
		return getMappings().get(value);
	}
}