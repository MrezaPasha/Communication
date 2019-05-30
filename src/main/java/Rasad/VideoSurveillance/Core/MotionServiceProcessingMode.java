package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum MotionServiceProcessingMode : byte
public enum MotionServiceProcessingMode 
{
	MultiProcess((byte)0),
	SingleProcess((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, MotionServiceProcessingMode> mappings;
	private static java.util.HashMap<Byte, MotionServiceProcessingMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MotionServiceProcessingMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, MotionServiceProcessingMode>();
				}
			}
		}
		return mappings;
	}

	private MotionServiceProcessingMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static MotionServiceProcessingMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}