package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;

public enum ServiceActionTypeEnum
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("فعال")][EnumMember] Activation,
	Activation,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("غیر فعال")][EnumMember] Deactivation,
	Deactivation,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش")][EnumMember] Edit
	Edit;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ServiceActionTypeEnum forValue(int value)
	{
		return values()[value];
	}
}