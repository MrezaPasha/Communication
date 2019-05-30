package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum ServerRole : byte
public enum ServerRole 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مشخص نشده")] Undefined = 0,
	Undefined((byte)0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سرور ریشه")] Root = 1,
	Root((byte)1),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سرور ضبط تصاویر")] Recording = 2,
	Recording((byte)2),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("موبایل سرور")] Mobile = 3,
	Mobile((byte)3);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, ServerRole> mappings;
	private static java.util.HashMap<Byte, ServerRole> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ServerRole.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, ServerRole>();
				}
			}
		}
		return mappings;
	}

	private ServerRole(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static ServerRole forValue(byte value)
	{
		return getMappings().get(value);
	}
}