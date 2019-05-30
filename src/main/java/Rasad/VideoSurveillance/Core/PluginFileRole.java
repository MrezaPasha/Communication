package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum PluginFileRole : byte
public enum PluginFileRole 
{
	None((byte)0),
	Service((byte)1),
	DatabaseUpgradeFile((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, PluginFileRole> mappings;
	private static java.util.HashMap<Byte, PluginFileRole> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PluginFileRole.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, PluginFileRole>();
				}
			}
		}
		return mappings;
	}

	private PluginFileRole(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static PluginFileRole forValue(byte value)
	{
		return getMappings().get(value);
	}
}