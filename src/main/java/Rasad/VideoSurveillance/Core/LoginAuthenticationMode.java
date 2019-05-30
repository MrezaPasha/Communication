package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum LoginAuthenticationMode : byte
public enum LoginAuthenticationMode 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("کاربر سیستم نظارت تصویری")] System = 0,
	System((byte)0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("احراز هویت توسط ویندوز (کاربر جاری)")] WindowsCurrentUser = 1,
	WindowsCurrentUser((byte)1),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("احراز هویت توسط ویندوز")] WindowsSpecificUser = 2
	WindowsSpecificUser((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, LoginAuthenticationMode> mappings;
	private static java.util.HashMap<Byte, LoginAuthenticationMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (LoginAuthenticationMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, LoginAuthenticationMode>();
				}
			}
		}
		return mappings;
	}

	private LoginAuthenticationMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static LoginAuthenticationMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}