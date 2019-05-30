package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum SingleServiceMode : byte
public enum SingleServiceMode 
{
	None((byte)0),
	SingleServiceModeRecStrMotion((byte)1),
	SingleServiceModeRecStr((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, SingleServiceMode> mappings;
	private static java.util.HashMap<Byte, SingleServiceMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SingleServiceMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, SingleServiceMode>();
				}
			}
		}
		return mappings;
	}

	private SingleServiceMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static SingleServiceMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}