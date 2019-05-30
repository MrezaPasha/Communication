package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum WebServerTransportMode : byte
public enum WebServerTransportMode 
{
	Http((byte)0),
	Https((byte)1),
	HttpHttps((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, WebServerTransportMode> mappings;
	private static java.util.HashMap<Byte, WebServerTransportMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (WebServerTransportMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, WebServerTransportMode>();
				}
			}
		}
		return mappings;
	}

	private WebServerTransportMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static WebServerTransportMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}