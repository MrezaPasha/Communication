package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] public enum PluginFileSection : byte
public class PluginFileSection 
{
	public static final PluginFileSection All = new PluginFileSection(0);
	public static final PluginFileSection Client = new PluginFileSection(1);
	public static final PluginFileSection Administrator = new PluginFileSection(2);
	public static final PluginFileSection Server = new PluginFileSection(4);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, PluginFileSection> mappings;
	private static java.util.HashMap<Byte, PluginFileSection> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PluginFileSection.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, PluginFileSection>();
				}
			}
		}
		return mappings;
	}

	private PluginFileSection(byte value)
	{
		byteValue = value;
		synchronized (PluginFileSection.class)
		{
			getMappings().put(value, this);
		}
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static PluginFileSection forValue(byte value)
	{
		synchronized (PluginFileSection.class)
		{
			PluginFileSection enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new PluginFileSection(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}