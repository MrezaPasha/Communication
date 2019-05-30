package Rasad.VideoSurveillance.Core;

public enum SnapshotType
{
	Live(0),
	Playback(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, SnapshotType> mappings;
	private static java.util.HashMap<Integer, SnapshotType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SnapshotType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SnapshotType>();
				}
			}
		}
		return mappings;
	}

	private SnapshotType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SnapshotType forValue(int value)
	{
		return getMappings().get(value);
	}
}