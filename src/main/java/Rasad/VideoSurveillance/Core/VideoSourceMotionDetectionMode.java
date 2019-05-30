package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum VideoSourceMotionDetectionMode : byte
public enum VideoSourceMotionDetectionMode 
{
	Disabled((byte)0),
	DetectMotionsWithoutEncoding((byte)1),
	DetectMotionsWithoutEncodingDisplayFrames((byte)2),
	DetectMotionsWithEncoding((byte)3),
	DetectMotionsWithEncodingDisplayFrames((byte)4);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, VideoSourceMotionDetectionMode> mappings;
	private static java.util.HashMap<Byte, VideoSourceMotionDetectionMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (VideoSourceMotionDetectionMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, VideoSourceMotionDetectionMode>();
				}
			}
		}
		return mappings;
	}

	private VideoSourceMotionDetectionMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static VideoSourceMotionDetectionMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}