package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum RecordingStorageEncryptionMethod : byte
public enum RecordingStorageEncryptionMethod 
{
	None((byte)0);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, RecordingStorageEncryptionMethod> mappings;
	private static java.util.HashMap<Byte, RecordingStorageEncryptionMethod> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RecordingStorageEncryptionMethod.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, RecordingStorageEncryptionMethod>();
				}
			}
		}
		return mappings;
	}

	private RecordingStorageEncryptionMethod(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static RecordingStorageEncryptionMethod forValue(byte value)
	{
		return getMappings().get(value);
	}
}