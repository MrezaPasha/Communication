package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum PluginPlatform : byte
public enum PluginPlatform 
{
	AnyCPU((byte)0),
	X86((byte)1),
	X64((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, PluginPlatform> mappings;
	private static java.util.HashMap<Byte, PluginPlatform> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PluginPlatform.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, PluginPlatform>();
				}
			}
		}
		return mappings;
	}

	private PluginPlatform(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static PluginPlatform forValue(byte value)
	{
		return getMappings().get(value);
	}
}