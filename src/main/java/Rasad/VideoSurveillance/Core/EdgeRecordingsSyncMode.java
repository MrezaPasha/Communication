package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum EdgeRecordingsSyncMode : byte
public enum EdgeRecordingsSyncMode 
{
	OnlyDisconnectedTimes((byte)0),
	AllTimes((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, EdgeRecordingsSyncMode> mappings;
	private static java.util.HashMap<Byte, EdgeRecordingsSyncMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (EdgeRecordingsSyncMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, EdgeRecordingsSyncMode>();
				}
			}
		}
		return mappings;
	}

	private EdgeRecordingsSyncMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static EdgeRecordingsSyncMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}