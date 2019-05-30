package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.time.*;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum EventCategory : byte
public enum EventCategory 
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اطلاعات")][TEnumImage("Ico_Information.ico")] Information = 0,
	Information((byte)0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اخطارها")][TEnumImage("Ico_Warning.ico")] Warning = 1,
	Warning((byte)1),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("خطاها")][TEnumImage("Ico_Banned.ico")] Error = 2,
	Error((byte)2),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("رویدادهای حیاتی")][TEnumImage("Ico_Critical.ico")] Critical = 3,
	Critical((byte)3);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, EventCategory> mappings;
	private static java.util.HashMap<Byte, EventCategory> getMappings()
	{
		if (mappings == null)
		{
			synchronized (EventCategory.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, EventCategory>();
				}
			}
		}
		return mappings;
	}

	private EventCategory(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static EventCategory forValue(byte value)
	{
		return getMappings().get(value);
	}
}