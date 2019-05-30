package Rasad.VideoSurveillance.Core;

public enum RecordFileTransferPolicy 
{
	NewestRecordingsPriority(0),
	OldestRecordingsPriority(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, RecordFileTransferPolicy> mappings;
	private static java.util.HashMap<Integer, RecordFileTransferPolicy> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RecordFileTransferPolicy.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, RecordFileTransferPolicy>();
				}
			}
		}
		return mappings;
	}

	private RecordFileTransferPolicy(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static RecordFileTransferPolicy forValue(int value)
	{
		return getMappings().get(value);
	}
}