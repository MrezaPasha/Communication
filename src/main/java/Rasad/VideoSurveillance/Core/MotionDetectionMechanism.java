package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum MotionDetectionMechanism : byte
public enum MotionDetectionMechanism 
{
	Software((byte)0),
	Camera((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, MotionDetectionMechanism> mappings;
	private static java.util.HashMap<Byte, MotionDetectionMechanism> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MotionDetectionMechanism.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, MotionDetectionMechanism>();
				}
			}
		}
		return mappings;
	}

	private MotionDetectionMechanism(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static MotionDetectionMechanism forValue(byte value)
	{
		return getMappings().get(value);
	}
}