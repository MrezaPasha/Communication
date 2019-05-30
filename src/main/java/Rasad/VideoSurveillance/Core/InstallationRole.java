package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum InstallationRole : byte
public enum InstallationRole 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اصلی")] Main = 0,
	Main((byte)0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Failover")] Failover = 1,
	Failover((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, InstallationRole> mappings;
	private static java.util.HashMap<Byte, InstallationRole> getMappings()
	{
		if (mappings == null)
		{
			synchronized (InstallationRole.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, InstallationRole>();
				}
			}
		}
		return mappings;
	}

	private InstallationRole(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static InstallationRole forValue(byte value)
	{
		return getMappings().get(value);
	}
}