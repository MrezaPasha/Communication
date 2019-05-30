package main.java.Rasad.Core.Common;


//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum ServerAccessMethod : byte
public enum ServerAccessMethod 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("آدرس IP")] IPAddress = 0,
	IPAddress((byte)0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نام کامپیوتر")] MachineName = 1
	MachineName((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, ServerAccessMethod> mappings;
	private static java.util.HashMap<Byte, ServerAccessMethod> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ServerAccessMethod.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, ServerAccessMethod>();
				}
			}
		}
		return mappings;
	}

	private ServerAccessMethod(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static ServerAccessMethod forValue(byte value)
	{
		return getMappings().get(value);
	}
}