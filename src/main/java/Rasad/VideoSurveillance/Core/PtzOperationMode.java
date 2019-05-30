package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum PtzOperationMode : byte
public enum PtzOperationMode 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Continuous")] ContinuousPreferred = 0,
	ContinuousPreferred((byte)0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Relative")] RelativePreferred = 1
	RelativePreferred((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, PtzOperationMode> mappings;
	private static java.util.HashMap<Byte, PtzOperationMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PtzOperationMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, PtzOperationMode>();
				}
			}
		}
		return mappings;
	}

	private PtzOperationMode(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static PtzOperationMode forValue(byte value)
	{
		return getMappings().get(value);
	}
}