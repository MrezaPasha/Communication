package Rasad.VideoSurveillance.Core;

public enum ConfigReportType
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دوربین های محلی")] Cameras,
	Cameras,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دوربین های قطع")] NotAvailableCameras,
	NotAvailableCameras,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سرورهای محلی")] LocalServers,
	LocalServers,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سرورهای مجتمع")] FederatedServers,
	FederatedServers,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سرورهای Failover")] FailoverServers,
	FailoverServers,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("افزونه ها")] Plugins,
	Plugins,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("کاربران سیستم")] Users,
	Users,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نقش های امنیتی")] Roles,
	Roles,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گروه نمایه ها")] ViewGroups,
	ViewGroups,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گواهینامه های نرم افزار")] License,
	License,
	//[Description("اطلاعات سایت")]
	//SiteInformation,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نقشه ها")] Maps,
	Maps;
	//[Description("سایر تنظیمات")]
	//OtherSettings

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ConfigReportType forValue(int value)
	{
		return values()[value];
	}
}