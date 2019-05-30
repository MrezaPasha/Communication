package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum StreamerMethod : byte
public enum StreamerMethod 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("استریمر 1")] OldStreamer = 0,
	OldStreamer((byte)0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("استریمر 2")] NewStreamer = 1
	NewStreamer((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, StreamerMethod> mappings;
	private static java.util.HashMap<Byte, StreamerMethod> getMappings()
	{
		if (mappings == null)
		{
			synchronized (StreamerMethod.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, StreamerMethod>();
				}
			}
		}
		return mappings;
	}

	private StreamerMethod(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static StreamerMethod forValue(byte value)
	{
		return getMappings().get(value);
	}
}