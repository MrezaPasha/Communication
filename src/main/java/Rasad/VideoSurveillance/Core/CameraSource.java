package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CameraSource : byte
public enum CameraSource 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دوربین IP")] IPCamera = 0,
	IPCamera((byte)0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دوربین موبایل")] MobilePushCamera = 1
	MobilePushCamera((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, CameraSource> mappings;
	private static java.util.HashMap<Byte, CameraSource> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraSource.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, CameraSource>();
				}
			}
		}
		return mappings;
	}

	private CameraSource(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static CameraSource forValue(byte value)
	{
		return getMappings().get(value);
	}
}