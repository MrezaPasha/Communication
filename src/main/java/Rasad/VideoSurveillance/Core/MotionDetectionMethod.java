package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum MotionDetectionMethod : byte
public enum MotionDetectionMethod 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Basic")] Basic = 0,
	Basic((byte)0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Motion Vector")] MotionVector = 1,
	MotionVector((byte)1),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Basic Resize")] BasicResize = 2,
	BasicResize((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, MotionDetectionMethod> mappings;
	private static java.util.HashMap<Byte, MotionDetectionMethod> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MotionDetectionMethod.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, MotionDetectionMethod>();
				}
			}
		}
		return mappings;
	}

	private MotionDetectionMethod(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static MotionDetectionMethod forValue(byte value)
	{
		return getMappings().get(value);
	}
}