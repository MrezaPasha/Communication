package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum InvestigationFormat : byte
public enum InvestigationFormat 
{
	NativeFormat((byte)0),
	CustomFormat((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, InvestigationFormat> mappings;
	private static java.util.HashMap<Byte, InvestigationFormat> getMappings()
	{
		if (mappings == null)
		{
			synchronized (InvestigationFormat.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, InvestigationFormat>();
				}
			}
		}
		return mappings;
	}

	private InvestigationFormat(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static InvestigationFormat forValue(byte value)
	{
		return getMappings().get(value);
	}
}