package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum RecordFileSource : byte
public enum RecordFileSource 
{
	LocalRecording((byte)0),
	Failover((byte)1),
	MobileDeviceTransfer((byte)2),
	EdgeRecording((byte)3);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, RecordFileSource> mappings;
	private static java.util.HashMap<Byte, RecordFileSource> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RecordFileSource.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, RecordFileSource>();
				}
			}
		}
		return mappings;
	}

	private RecordFileSource(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static RecordFileSource forValue(byte value)
	{
		return getMappings().get(value);
	}
}