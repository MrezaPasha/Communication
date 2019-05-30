package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CameraStreamUrlMode : byte
public enum CameraStreamUrlMode 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دسترسی به استریم دوربین از طریق آدرس مستقیم")] ByUrl = 0,
	ByUrl((byte)0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دسترسی به استریم دوربین از طریق پروفایل")] ByProfile = 1
	ByProfile((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, CameraStreamUrlMode> mappings;
	private static java.util.HashMap<Byte, CameraStreamUrlMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraStreamUrlMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, CameraStreamUrlMode>();
				}
			}
		}
		return mappings;
	}

	private CameraStreamUrlMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static CameraStreamUrlMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}