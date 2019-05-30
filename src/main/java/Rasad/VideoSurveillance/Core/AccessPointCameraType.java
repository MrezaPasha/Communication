package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum AccessPointCameraType : byte
public enum AccessPointCameraType 
{
	Overview((byte)0),
	LicensePlateDetection((byte)1),
	FaceDetection((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, AccessPointCameraType> mappings;
	private static java.util.HashMap<Byte, AccessPointCameraType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (AccessPointCameraType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, AccessPointCameraType>();
				}
			}
		}
		return mappings;
	}

	private AccessPointCameraType(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static AccessPointCameraType forValue(byte value)
	{
		return getMappings().get(value);
	}
}