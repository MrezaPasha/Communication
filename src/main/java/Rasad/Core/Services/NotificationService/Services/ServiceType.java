package main.java.Rasad.Core.Services.NotificationService.Services;


import java.util.*;
import java.nio.file.*;

public enum ServiceType
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اصلی")][ServiceInformation("{7627D0BA-7EBB-4919-9D9E-E2BBCACA5772}", "Rasad.MainService.exe", "Rasad Main Service")][EnumMember] Main,
	Main,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ضبط تصاویر")][ServiceInformation("{D9565EE0-D449-45A3-8D30-60F4B292AABF}", "Rasad.RecordingService.exe", "Rasad Recording Service")][EnumMember] Recording,
	Recording,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("بازبینی تصاویر")][ServiceInformation("{39902EC6-350A-41C9-A64E-73ECF9BB688C}", "Rasad.PlaybackService.exe", "Rasad Playback Service")][EnumMember] Playback,
	Playback,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گردش دوربینها")][ServiceInformation("{9AFFE992-747A-47BA-A24A-B7F2F4BD27E7}", "Rasad.PresetTourService.exe", "Rasad Preset Tour Service")][EnumMember] PresetTour,
	PresetTour,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تشخیص حرکت")][ServiceInformation("{409CBF29-D49B-4252-A14E-D8693BE0DDF4}", "Rasad.MotionDetectionService.exe", "Rasad Motion Detection Service")][EnumMember] MotionDetection,
	MotionDetection,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ارتباطات")][ServiceInformation("{37C5B103-9511-46D4-B92A-3A114DA68029}", "Rasad.CommunicationService.exe", "Rasad Communication Service")][EnumMember] CommunicationService,
	CommunicationService,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ناظر دوربینها")][ServiceInformation("{B72D1704-5C09-42A4-8D9B-6F167BEEDCDD}", "Rasad.CameraMonitoringService.exe", "Rasad Camera Monitoring Service")][EnumMember] CameraMonitoring,
	CameraMonitoring,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مدیریت رویدادها")][ServiceInformation("{DB4769A0-544B-448A-B090-E3CE8E44DB9F}", "Rasad.EventTriggerService.exe", "Rasad Event Trigger Service")][EnumMember] EventTriggerService,
	EventTriggerService,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گواهینامه")][ServiceInformation("{8EA8D96C-EB12-4DB6-BF96-E162C4B92C84}", "Rasad.LicenseService.exe", "Rasad License Service")][EnumMember] LicenseService,
	LicenseService,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("استریمر")][ServiceInformation("{910A50B6-52DD-4B53-B140-2C9A0EAFFD2F}", "Rasad.StreamingService.exe", "Rasad Streaming Service")][EnumMember] StreamingService,
	StreamingService,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("افزونه")][EnumMember] Plugin,
	Plugin,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ارتباطات I/O")][ServiceInformation("{5ADE3F60-29AD-4ABC-83B2-5A27E7DDCBC9}", "Rasad.IOService.exe", "Rasad IO Service")][EnumMember] IO,
	IO,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("لاگ")][ServiceInformation("{2D018D63-A045-48AD-9A08-5410FAC92009}", "Rasad.LogService.exe", "Rasad Log Service")][EnumMember] Log,
	Log,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("موبایل")][ServiceInformation("{154C2539-FB2A-4D26-A793-6F6EE8DE6AE3}", RVSConstants.MobileServiceDir + "\\Rasad.MobileService.exe", "Rasad Mobile Service")][EnumMember] Mobile,
	Mobile,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دوربین")][ServiceInformation("{C20FA65C-D2F8-47A4-A232-1C5109EB5008}", "Rasad.CameraService.exe", "Rasad Camera Service")][EnumMember] CameraService,
	CameraService,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Failover")][ServiceInformation("{4BFDC869-E3A0-43E1-9201-D0DFE56934CC}", "Rasad.FailoverService.exe", "Rasad Failover Service")][EnumMember] FailoverService,
	FailoverService,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نقشه")][ServiceInformation("{D322664C-FA2B-46F2-9242-0FB4C4901304}", "Rasad.MapService.exe", "Rasad Map Service")][EnumMember] MapService,
	MapService,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("پروکسی داده")][ServiceInformation("{31AE978F-62D3-4F6A-8364-3DA1D3D1C01C}", "Rasad.DataProxyService.exe", "Rasad Data Proxy Service")][EnumMember] DataProxy,
	DataProxy,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("کنترل تردد رصد")][ServiceInformation("{A0BEFD21-8ADE-4329-8389-137EFACBC3BC}", RVSConstants.AccessControlServiceDir + "\\Rasad.AccessControlService.exe", "Rasad Access Control Service")][EnumMember] AccessControl,
	AccessControl;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ServiceType forValue(int value)
	{
		return values()[value];
	}
}