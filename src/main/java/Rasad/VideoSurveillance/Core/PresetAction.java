package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum PresetAction : byte
public enum PresetAction 
{
	Add((byte)0),
	Edit((byte)1),
	Delete((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, PresetAction> mappings;
	private static java.util.HashMap<Byte, PresetAction> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PresetAction.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, PresetAction>();
				}
			}
		}
		return mappings;
	}

	private PresetAction(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static PresetAction forValue(byte value)
	{
		return getMappings().get(value);
	}
}