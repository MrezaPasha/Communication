package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum ProfileStreamerMode : byte
public enum ProfileStreamerMode 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("بنا به تقاضا")] OnDemand = 0,
	OnDemand((byte)0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("همیشه")] Always = 1
	Always((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, ProfileStreamerMode> mappings;
	private static java.util.HashMap<Byte, ProfileStreamerMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ProfileStreamerMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, ProfileStreamerMode>();
				}
			}
		}
		return mappings;
	}

	private ProfileStreamerMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static ProfileStreamerMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}