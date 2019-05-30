package main.java.Rasad.Core.Services.NotificationService.Server;



public enum ServerActionType
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش سرور")][EnumMember] EditServer,
	EditServer,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حـذف سرور")][EnumMember] DeleteServer,
	DeleteServer,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("افزودن سرور جدید")][EnumMember] AddServer
	AddServer;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ServerActionType forValue(int value)
	{
		return values()[value];
	}
}