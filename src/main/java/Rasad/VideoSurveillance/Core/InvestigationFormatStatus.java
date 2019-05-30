package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum InvestigationFormatStatus : byte
public enum InvestigationFormatStatus 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("آماده نشده")] NotReady = 0,
	NotReady((byte)0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("در حال آماده سازی")] InProgress = 1,
	InProgress((byte)1),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("آماده دانلود")] Ready = 2,
	Ready((byte)2),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("خطا در آماده سازی")] Error = 3
	Error((byte)3);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, InvestigationFormatStatus> mappings;
	private static java.util.HashMap<Byte, InvestigationFormatStatus> getMappings()
	{
		if (mappings == null)
		{
			synchronized (InvestigationFormatStatus.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, InvestigationFormatStatus>();
				}
			}
		}
		return mappings;
	}

	private InvestigationFormatStatus(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static InvestigationFormatStatus forValue(byte value)
	{
		return getMappings().get(value);
	}
}