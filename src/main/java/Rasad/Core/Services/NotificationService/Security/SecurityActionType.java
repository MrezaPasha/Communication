package main.java.Rasad.Core.Services.NotificationService.Security;


import java.util.*;

public enum SecurityActionType
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("؟")][DataMember] None = 0,
	None(0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("افزودن کاربر")][DataMember] AddNewUser = 1,
	AddNewUser(1),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف کاربر")][DataMember] DeleteUser = 2,
	DeleteUser(2),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش کاربر")][DataMember] EditUser = 3,
	EditUser(3),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("افزودن نقش")][DataMember] AddNewRole = 4,
	AddNewRole(4),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف نقش")][DataMember] DeleteRole = 5,
	DeleteRole(5),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش نقش")][DataMember] EditRole = 6,
	EditRole(6);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, SecurityActionType> mappings;
	private static java.util.HashMap<Integer, SecurityActionType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SecurityActionType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SecurityActionType>();
				}
			}
		}
		return mappings;
	}

	private SecurityActionType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SecurityActionType forValue(int value)
	{
		return getMappings().get(value);
	}
}