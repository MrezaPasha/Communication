package main.java.Rasad.Core.Services.NotificationService.Map;


import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;

public enum MapActionType
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("هر تغییری")][EnumMember] Any,
	Any;

	//[Description("ویرایش نقشه")]
	//[EnumMember]
	//EditMap,

	//[Description("حـذف نقشه")]
	//[EnumMember]
	//DeleteMap,

	//[Description("افزودن نقشه جدید")]
	//[EnumMember]
	//AddMap

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static MapActionType forValue(int value)
	{
		return values()[value];
	}
}