package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum EdgeRequestedTimeStatus : byte
public enum EdgeRequestedTimeStatus 
{
	NotProcessed((byte)0),
	DoneWithoutSync((byte)1),
	DoneWithSync((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, EdgeRequestedTimeStatus> mappings;
	private static java.util.HashMap<Byte, EdgeRequestedTimeStatus> getMappings()
	{
		if (mappings == null)
		{
			synchronized (EdgeRequestedTimeStatus.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, EdgeRequestedTimeStatus>();
				}
			}
		}
		return mappings;
	}

	private EdgeRequestedTimeStatus(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static EdgeRequestedTimeStatus forValue(byte value)
	{
		return getMappings().get(value);
	}
}