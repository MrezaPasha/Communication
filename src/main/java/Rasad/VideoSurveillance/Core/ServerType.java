package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum ServerType : byte
public enum ServerType 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سرور محلی")] Local = 0,
	Local((byte)0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سرور مجتمع")] Federated = 1,
	Federated((byte)1),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سرور Failover")] Failover = 2,
	Failover((byte)2),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سرور Failover")] Redundant = 3,
	Redundant((byte)3);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, ServerType> mappings;
	private static java.util.HashMap<Byte, ServerType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ServerType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, ServerType>();
				}
			}
		}
		return mappings;
	}

	private ServerType(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static ServerType forValue(byte value)
	{
		return getMappings().get(value);
	}
}