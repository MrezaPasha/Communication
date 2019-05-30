package Rasad.VideoSurveillance.Core;

public enum RVSErrorCode 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Successful")] Successful = 0,
	Successful((short)0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Unknown error occurred.")] Unknown = -1,
	Unknown((short)-1),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("This operation is not valid when a web service is running. Stop all web services and retry again.")] OperationNotValidWhenWebServiceIsRunning = -91,
	OperationNotValidWhenWebServiceIsRunning((short)-91),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("The requested web service is not available in the system.")] RequestedWebServiceNotAvailable = -77,
	RequestedWebServiceNotAvailable((short)-77);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, RVSErrorCode> mappings;
	private static java.util.HashMap<Short, RVSErrorCode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RVSErrorCode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, RVSErrorCode>();
				}
			}
		}
		return mappings;
	}

	private RVSErrorCode(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static RVSErrorCode forValue(short value)
	{
		return getMappings().get(value);
	}
}