package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum AccessPointType : byte
public enum AccessPointType 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ورودی")] Enter = 0,
	Enter((byte)0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("خروجی")] Exit = 1,
	Exit((byte)1),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ورودی و خروجی")] EnterAndExit = 2,
	EnterAndExit((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, AccessPointType> mappings;
	private static java.util.HashMap<Byte, AccessPointType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (AccessPointType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, AccessPointType>();
				}
			}
		}
		return mappings;
	}

	private AccessPointType(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static AccessPointType forValue(byte value)
	{
		return getMappings().get(value);
	}
}