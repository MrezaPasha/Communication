package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CameraViewGroupType : byte
public enum CameraViewGroupType 
{
	UserGroup((byte)0),
	SystemGroupPublicRoot((byte)1),
	SystemGroupPrivateRoot((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, CameraViewGroupType> mappings;
	private static java.util.HashMap<Byte, CameraViewGroupType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraViewGroupType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, CameraViewGroupType>();
				}
			}
		}
		return mappings;
	}

	private CameraViewGroupType(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static CameraViewGroupType forValue(byte value)
	{
		return getMappings().get(value);
	}
}