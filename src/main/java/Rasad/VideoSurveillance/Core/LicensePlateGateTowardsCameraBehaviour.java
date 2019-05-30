package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum LicensePlateGateTowardsCameraBehaviour : byte
public enum LicensePlateGateTowardsCameraBehaviour 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ورودی")] Entrance = 0,
	Entrance((byte)0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("خروجی")] Exit = 2
	Exit((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, LicensePlateGateTowardsCameraBehaviour> mappings;
	private static java.util.HashMap<Byte, LicensePlateGateTowardsCameraBehaviour> getMappings()
	{
		if (mappings == null)
		{
			synchronized (LicensePlateGateTowardsCameraBehaviour.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, LicensePlateGateTowardsCameraBehaviour>();
				}
			}
		}
		return mappings;
	}

	private LicensePlateGateTowardsCameraBehaviour(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static LicensePlateGateTowardsCameraBehaviour forValue(byte value)
	{
		return getMappings().get(value);
	}
}