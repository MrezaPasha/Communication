package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum UserAuthenticationType : byte
public enum UserAuthenticationType 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سیستم")] System = 0,
	System((byte)0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویندوز")] LDAP = 1
	LDAP((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, UserAuthenticationType> mappings;
	private static java.util.HashMap<Byte, UserAuthenticationType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (UserAuthenticationType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, UserAuthenticationType>();
				}
			}
		}
		return mappings;
	}

	private UserAuthenticationType(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static UserAuthenticationType forValue(byte value)
	{
		return getMappings().get(value);
	}
}