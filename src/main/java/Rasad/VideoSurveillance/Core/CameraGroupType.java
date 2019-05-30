package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CameraGroupType : byte
public enum CameraGroupType 
{
	UserGroup((byte)0),
	SystemGroup((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, CameraGroupType> mappings;
	private static java.util.HashMap<Byte, CameraGroupType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraGroupType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, CameraGroupType>();
				}
			}
		}
		return mappings;
	}

	private CameraGroupType(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static CameraGroupType forValue(byte value)
	{
		return getMappings().get(value);
	}
}