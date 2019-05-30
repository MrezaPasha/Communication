package main.java.Rasad.Core.Services.NotificationService.Services;



//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum ServiceState : byte
public enum ServiceState 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("در حال فعال سازی ...")][EnumMember] Starting = 0,
	Starting((byte)0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("فعال")][EnumMember] Working = 1,
	Working((byte)1),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("درحال متوقف سازی ...")][EnumMember] Stopping = 2,
	Stopping((byte)2),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("متوقف")][EnumMember] Stopped = 3
	Stopped((byte)3);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, ServiceState> mappings;
	private static java.util.HashMap<Byte, ServiceState> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ServiceState.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, ServiceState>();
				}
			}
		}
		return mappings;
	}

	private ServiceState(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static ServiceState forValue(byte value)
	{
		return getMappings().get(value);
	}
}