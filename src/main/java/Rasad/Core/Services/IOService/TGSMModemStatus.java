package Rasad.Core.Services.IOService;

import Rasad.Core.*;
import Rasad.Core.Services.*;

public enum TGSMModemStatus
{
	/** 
	 متصل به مودم
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("متصل به مودم")] Connected = 1,
	Connected(1),
	/** 
	 عدم اتصال به مودم
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("عدم اتصال به مودم")] Disconnected = 2,
	Disconnected(2),
	/** 
	 پاسخ از سرویس ارتباطات دریافت نشده است
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("پاسخ از سرویس ارتباطات دریافت نشده است")] NotResponsiveService = 3,
	NotResponsiveService(3);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, TGSMModemStatus> mappings;
	private static java.util.HashMap<Integer, TGSMModemStatus> getMappings()
	{
		if (mappings == null)
		{
			synchronized (TGSMModemStatus.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, TGSMModemStatus>();
				}
			}
		}
		return mappings;
	}

	private TGSMModemStatus(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static TGSMModemStatus forValue(int value)
	{
		return getMappings().get(value);
	}
}