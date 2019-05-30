package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum AccessPointControlMode : byte
public enum AccessPointControlMode 
{
	Automatic((byte)0),
	UserConfirm((byte)1),
	NoControl((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, AccessPointControlMode> mappings;
	private static java.util.HashMap<Byte, AccessPointControlMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (AccessPointControlMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, AccessPointControlMode>();
				}
			}
		}
		return mappings;
	}

	private AccessPointControlMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static AccessPointControlMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}