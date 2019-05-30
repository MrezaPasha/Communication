package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum RecordingStorageState : byte
public enum RecordingStorageState 
{
	NotInitialized((byte)0),
	FirstTimePrepared((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, RecordingStorageState> mappings;
	private static java.util.HashMap<Byte, RecordingStorageState> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RecordingStorageState.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, RecordingStorageState>();
				}
			}
		}
		return mappings;
	}

	private RecordingStorageState(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static RecordingStorageState forValue(byte value)
	{
		return getMappings().get(value);
	}
}