package Rasad.VideoSurveillance.Core;

public enum RecordFileTransferSpeed 
{
	Low(0),
	Medium(1),
	High(2),
	NoLimit(3);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, RecordFileTransferSpeed> mappings;
	private static java.util.HashMap<Integer, RecordFileTransferSpeed> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RecordFileTransferSpeed.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, RecordFileTransferSpeed>();
				}
			}
		}
		return mappings;
	}

	private RecordFileTransferSpeed(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static RecordFileTransferSpeed forValue(int value)
	{
		return getMappings().get(value);
	}
}