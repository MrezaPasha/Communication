package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum RecordingStorageMethod : byte
public enum RecordingStorageMethod 
{
	LegacyMethod((byte)0),
	NewMethod((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, RecordingStorageMethod> mappings;
	private static java.util.HashMap<Byte, RecordingStorageMethod> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RecordingStorageMethod.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, RecordingStorageMethod>();
				}
			}
		}
		return mappings;
	}

	private RecordingStorageMethod(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static RecordingStorageMethod forValue(byte value)
	{
		return getMappings().get(value);
	}
}