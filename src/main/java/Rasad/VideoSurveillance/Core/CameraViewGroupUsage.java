package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CameraViewGroupUsage : byte
public enum CameraViewGroupUsage 
{
	Live((byte)0),
	Playback((byte)1),
	Both((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, CameraViewGroupUsage> mappings;
	private static java.util.HashMap<Byte, CameraViewGroupUsage> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraViewGroupUsage.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, CameraViewGroupUsage>();
				}
			}
		}
		return mappings;
	}

	private CameraViewGroupUsage(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static CameraViewGroupUsage forValue(byte value)
	{
		return getMappings().get(value);
	}
}