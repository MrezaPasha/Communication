package main.java.Rasad.Core.Services;

import Rasad.Core.*;

public enum SecurityOperationEnum
{
	None(0),

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region تب های اصلی

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region نمایش زنده
	/** 
	 نمایش زنده
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایش زنده")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewNBITabView)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewNBITabControl)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewCommunicateMember)] CanViewMainLive = 1000,
	CanViewMainLive(1000),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region بازبینی
	/** 
	 بخش بازبینی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("بخش بازبینی")][TRoleChildNodeAttributes(SecurityOperationEnum.Playback_CanViewViews)] CanViewMainPlayBack = 6008,
	CanViewMainPlayBack(6008),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region پیکربندی
	/** 
	 پیکربندی
	 تغییر نام تنظیمات به پیکربندی
	*/
	//[TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_ManageServers)]
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("پیکربندی")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Users)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Devices)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Maps)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_License)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Plugins)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_ConfigurationReports)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_GSM)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Events)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_PresetTours)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Setting)] CanViewMainTanzimSystem = 3000,
	CanViewMainTanzimSystem(3000),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region گزارشات تحلیلی
	/** 
	 گزارشات تحلیلی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گزارشات تحلیلی")] CanViewReports = 2000,
	CanViewReports(2000),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
	/** 
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("")] CanViewMainRecord = 4000,
	CanViewMainRecord(4000),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ساختارهای مربوط به نمایش زنده
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ابزارهای نمایش
	/** 
	 نمایش زنده ابزارهای نمایش
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ابزارهای نمایش")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Views_Cameras)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_VideoWall_Map)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewViews)] CanViewNBITabView = 1001,
	CanViewNBITabView(1001),
	/** 
	 نمایش زنده گروه کنترل
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("کنترل")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewNBIEvents)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Control_Camera)] CanViewNBITabControl = 1002,
	CanViewNBITabControl(1002),

	/** 
	 نمایش ارتباط با کاربران
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ارتباط با کاربران")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewUserOnline)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewPublicChat)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewRemoteDesktop)] CanViewCommunicateMember = 5000,
	CanViewCommunicateMember(5000),
	/** 
	 نمایش ارتباط با کاربران - کاربران آنلاین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("کاربران آنلاین")] CanViewUserOnline = 5001,
	CanViewUserOnline(5001),
	/** 
	 نمایش ارتباط با کاربران - گفتگوی عمومی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گفتگوی عمومی")] CanViewPublicChat = 5002,
	CanViewPublicChat(5002),
	/** 
	 امکان نمایش میز کار کاربران
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("امکان نمایش میز کار کاربران")] CanViewRemoteDesktop = 3209,
	CanViewRemoteDesktop(3209),

	/** 
	 نمایه
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایه")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewViews)] CanViewNBIView = 1100,
	CanViewNBIView(1100),
	/** 
	 نمایش دوربینها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("لیست دوربین ها")] CanViewNBICameras = 1200,
	CanViewNBICameras(1200),

	/** 
	 نمایش رویدادها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایش رویدادها")] CanViewNBIEvents = 1300,
	CanViewNBIEvents(1300),


	/** 
	 کنترل - کنترل دوربین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("کنترل دوربین")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewNBIPTZ)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewNBIControl)] CanViewTab_Control_Camera = 1410,
	CanViewTab_Control_Camera(1410),
	/** 
	 حرکت دوربین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حرکت دوربین")] CanViewNBIPTZ = 1500,
	CanViewNBIPTZ(1500),
	/** 
	 کنترل دوربین - دکمه های کنترلی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دکمه های کنترلی")] CanViewNBIControl = 1400,
	CanViewNBIControl(1400),

	/** 
	 نمایش صفحات گسترده - نقشه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایش صفحات گسترده - نقشه ها")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewVideoWall)] CanViewTab_VideoWall_Map = 1600,
	CanViewTab_VideoWall_Map(1600),
	/** 
	 صفحات گسترده
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("صفحات گسترده")][TRoleChildNodeAttributes(SecurityOperationEnum.CanAddNewVideoWall)][TRoleChildNodeAttributes(SecurityOperationEnum.CanEditVideoWall)][TRoleChildNodeAttributes(SecurityOperationEnum.CanDeleteVideoWall)][TRoleChildNodeAttributes(SecurityOperationEnum.CanUseVideoWalls)] CanViewVideoWall = 1601,
	CanViewVideoWall(1601),
	/** 
	 امکان افزودن صفحه گسترده جدید
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("افزودن صفحه گسترده")] CanAddNewVideoWall = 1602,
	CanAddNewVideoWall(1602),
	/** 
	 امکان ویرایش صفحه گسترده
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش صفحه گسترده")] CanEditVideoWall = 1603,
	CanEditVideoWall(1603),
	/** 
	 حذف صفحه گسترده
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف صفحه گسترده")] CanDeleteVideoWall = 1604,
	CanDeleteVideoWall(1604),
	/** 
	 صفحات گسترده - نقشه ها - نقشه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نقشه ها")] CanViewLiveTools_Map = 1605,
	CanViewLiveTools_Map(1605),
	/** 
	 صفحات گسترده - نقشه ها - نقشه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مشاهده صفحات گسترده")] CanUseVideoWalls = 1606,
	CanUseVideoWalls(1606),

	/** 
	 نمایش نمایه ها - دوربین ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایه ها - دوربین ها")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewNBIView)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewNBICameras)] CanViewTab_Views_Cameras = 1106,
	CanViewTab_Views_Cameras(1106),
	/** 
	 نمایش نمایه ها
	*/
	//[TRoleChildNodeAttributes(SecurityOperationEnum.CanManagePublicViews)]
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [TRoleChildNodeAttributes(SecurityOperationEnum.CanAddNewView)][TRoleChildNodeAttributes(SecurityOperationEnum.CanEditView)][TRoleChildNodeAttributes(SecurityOperationEnum.CanDeleteView)][TRoleChildNodeAttributes(SecurityOperationEnum.CanRenameView)][TRoleChildNodeAttributes(SecurityOperationEnum.CanShowViewInAnotherMonitor)][TRoleChildNodeAttributes(SecurityOperationEnum.CanUseViews)][TRoleChildNodeAttributes(SecurityOperationEnum.CanManagePrivateViews)] CanViewViews = 1107,
	CanViewViews(1107),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اضافه کردن نمای جدید")] CanAddNewView = 1101,
	CanAddNewView(1101),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش نمایه")] CanEditView = 1102,
	CanEditView(1102),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف نمایه")] CanDeleteView = 1103,
	CanDeleteView(1103),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تغییر نام نمایه")] CanRenameView = 1104,
	CanRenameView(1104),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایش نمایه در نمایشگر")] CanShowViewInAnotherMonitor = 1105,
	CanShowViewInAnotherMonitor(1105),
	/** 
	 مشاهده نمایه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مشاهده نمایه ها")] CanUseViews = 1108,
	CanUseViews(1108),
	/** 
	 مدیریت نمایه های عمومی
	*/
	//[Description("مدیریت نمایه های عمومی")]
	//CanManagePublicViews = 1109,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دسترسی به نمایه های خصوصی")] CanManagePrivateViews = 1110,
	CanManagePrivateViews(1110),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ابزارهای روی دوربین
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region گزینه های مربوط به راست کلیک بر روی دوربین ها
	/** 
	 راست کلیک - حذف دوربین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف دوربین")] CanView_DeleteCamera_RightClick = 4100,
	CanView_DeleteCamera_RightClick(4100),

	/** 
	 راست کلیک - بازبینی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("بازبینی")] CanView_Review_RightClick = 4101,
	CanView_Review_RightClick(4101),

	/** 
	 راست کلیک - گرفتن عکس از دوربین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گرفتن عکس از دوربین")] CanView_TakeSnapshotFromCamera_RightClick = 4102,
	CanView_TakeSnapshotFromCamera_RightClick(4102),

	/** 
	 راست کلیک - نمایش دوربین برای کاربران سیستم
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایش دوربین برای کاربران سیستم")] CanView_ViewCameraForUserSystem_RightClick = 4103,
	CanView_ViewCameraForUserSystem_RightClick(4103),

	/** 
	 راست کلیک - گردش دوربین ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گردش دوربین ها")] CanView_CameraTour_RightClick = 4104,
	CanView_CameraTour_RightClick(4104),

	/** 
	 راست کلیک - ایجاد نشانک
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ایجاد نشانک")] CanView_CreateBookmarks_RightClick = 4105,
	CanView_CreateBookmarks_RightClick(4105),

	/** 
	 راست کلیک - نمایش در
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایش در")] CanView_ViewIn_RightClick = 4106,
	CanView_ViewIn_RightClick(4106),

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region سایر
	/** 
	 قابلیت حرکت
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("قابلیت حرکت")] CanView_MoveCamera = 4200,
	CanView_MoveCamera(4200),

	/** 
	 باز کردن پوشه تصاویر
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("باز کردن پوشه تصاویر")] CanViewOpenPhotoFolder = 4300,
	CanViewOpenPhotoFolder(4300),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ساختارهای مربوط به بازبینی
	/** 
	 نمایش نمایه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [TRoleChildNodeAttributes(SecurityOperationEnum.Playback_CanAddNewView)][TRoleChildNodeAttributes(SecurityOperationEnum.Playback_CanEditView)][TRoleChildNodeAttributes(SecurityOperationEnum.Playback_CanDeleteView)][TRoleChildNodeAttributes(SecurityOperationEnum.Playback_CanRenameView)][TRoleChildNodeAttributes(SecurityOperationEnum.Playback_CanUseViews)][TRoleChildNodeAttributes(SecurityOperationEnum.Playback_CanManagePrivateViews)] Playback_CanViewViews = 6006,
	Playback_CanViewViews(6006),
	/** 
	 نمایش نمایه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [TRoleChildNodeAttributes(SecurityOperationEnum.Playback_CanAddNewView)][TRoleChildNodeAttributes(SecurityOperationEnum.Playback_CanEditView)] Playback_CanViewCameraList = 6009,
	Playback_CanViewCameraList(6009),
	/** 
	 مشاهده نمایه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مشاهده نمایه ها")] Playback_CanUseViews = 6001,
	Playback_CanUseViews(6001),
	/** 
	 اضافه کردن نمای جدید
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اضافه کردن نمای جدید")] Playback_CanAddNewView = 6002,
	Playback_CanAddNewView(6002),
	/** 
	 ویرایش نمایه
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش نمایه")] Playback_CanEditView = 6003,
	Playback_CanEditView(6003),
	/** 
	 حذف نما
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف نمایه")] Playback_CanDeleteView = 6004,
	Playback_CanDeleteView(6004),
	/** 
	 تغییر نام نما
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تغییر نام نمایه")] Playback_CanRenameView = 6005,
	Playback_CanRenameView(6005),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دسترسی به نمایه های خصوصی")] Playback_CanManagePrivateViews = 6007,
	Playback_CanManagePrivateViews(6007),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف نشانک")] Playback_CanDeleteBookmark = 6010,
	Playback_CanDeleteBookmark(6010),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مشاهده دوربین")] CanViewCamera = 1401,
	CanViewCamera(1401),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حرکت دوربین")] CanPtzCamera = 1402,
	CanPtzCamera(1402),
	//[Description("ضبط دوربین")]
	//CanRecordingCamera = 1403,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دسترسی به دوربین در بازبینی")] Playback_CanViewCamera = 1404,
	Playback_CanViewCamera(1404),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دسترسی به گروه")] ViewGroup_CanRead = 1405,
	ViewGroup_CanRead(1405),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دسترسی به گروه")] ViewGroup_CanAddView = 1406,
	ViewGroup_CanAddView(1406),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش داخل گروه")] ViewGroup_CanEditView = 1407,
	ViewGroup_CanEditView(1407),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف داخل گروه")] ViewGroup_CanDeleteView = 1408,
	ViewGroup_CanDeleteView(1408),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تغییر نام داخل گروه")] ViewGroup_CanRenameView = 1409,
	ViewGroup_CanRenameView(1409),





//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region رزرو شده های قبلی
	/** 
	 ضبط تصاویر
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ضبط تصاویر")] CanViewTab_Storage = 3300,
	CanViewTab_Storage(3300),
	/** 
	 زمانبندی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("زمانبندی")] CanViewTab_Schedule = 3400,
	CanViewTab_Schedule(3400),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region تب های مربوط به پیکربندی

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region رویدادها
	/** 
	 رویدادها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("رویدادها")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_SystemEvents)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_ManagementActionEvents)] CanViewTab_Events = 3500,
	CanViewTab_Events(3500),
	/** 
	 رویدادهای سیستمی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایش رویدادهارویدادهای سیستمی")] CanViewTab_SystemEvents = 1301,
	CanViewTab_SystemEvents(1301),
	/** 
	 مدیریت رویداد - رفتارها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مدیریت رویداد - رفتارها")] CanViewTab_ManagementActionEvents = 1302,
	CanViewTab_ManagementActionEvents(1302),

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region سایر تنظیمات
	/** 
	 سایر تنظیمات
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سایر تنظیمات")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTabSetting_SettingSystem)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTabSetting_WorkstationSetting)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTabSetting_UserSetting)] CanViewTab_Setting = 3700,
	CanViewTab_Setting(3700),
	/** 
	 سایر تنظیمات - تنظیمات سیستم
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تنظیمات سیستمی")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTabSetting_SysPublicSetting)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTabSetting_SysStoreRestore)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTabSetting_SysBookmark)] CanViewTabSetting_SettingSystem = 3710,
	CanViewTabSetting_SettingSystem(3710),
	/** 
	 تنظیمات سیستم - تنظیمات عمومی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تنظیمات عمومی")] CanViewTabSetting_SysPublicSetting = 3711,
	CanViewTabSetting_SysPublicSetting(3711),
	/** 
	 تنظیمات سیستم - ذخیره و بازیابی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ذخیره و بازیابی")] CanViewTabSetting_SysStoreRestore = 3712,
	CanViewTabSetting_SysStoreRestore(3712),
	/** 
	 تنظیمات سیستم - انواع نشانک
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("انواع نشانک")] CanViewTabSetting_SysBookmark = 3713,
	CanViewTabSetting_SysBookmark(3713),

	/** 
	 سایر تنظیمات - تنظیمات ایستگاه کاری
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تنظیمات ایستگاه کاری")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTabSetting_WSPublicSetting)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTabSetting_WSController)] CanViewTabSetting_WorkstationSetting = 3730,
	CanViewTabSetting_WorkstationSetting(3730),
	/** 
	 تنظیمات ایستگاه کاری - تنظیمات عمومی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تنظیمات عمومی")] CanViewTabSetting_WSPublicSetting = 3731,
	CanViewTabSetting_WSPublicSetting(3731),
	/** 
	 تنظیمات ایستگاه کاری - کنترل کننده
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("کنترل کننده")] CanViewTabSetting_WSController = 3732,
	CanViewTabSetting_WSController(3732),

	/** 
	 سایر تنظیمات - تنظیمات کاربری
	*/
	//[TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTabSetting_UserPublicSetting)]
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تنظیمات کاربری")] CanViewTabSetting_UserSetting = 3750,
	CanViewTabSetting_UserSetting(3750),
	/** 
	 تنظیمات عمومی - تنظیمات کاربری
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تنظیمات عمومی")] CanViewTabSetting_UserPublicSetting = 3751,
	CanViewTabSetting_UserPublicSetting(3751),

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region گردش
	/** 
	 گردش
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گردش")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_CameraPostion_PresetTours)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_MoveCameraTimeline_PresetTours)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_RecordedMovement_PresetTours)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_ModifyViewsTimeline_PresetTours)] CanViewTab_PresetTours = 3800,
	CanViewTab_PresetTours(3800),
	/** 
	 موقعیت دوربین ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("موقعیت دوربین ها")] CanViewTab_CameraPostion_PresetTours = 3801,
	CanViewTab_CameraPostion_PresetTours(3801),
	/** 
	 برنامه حرکت دوربین ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("برنامه حرکت دوربین ها")] CanViewTab_MoveCameraTimeline_PresetTours = 3802,
	CanViewTab_MoveCameraTimeline_PresetTours(3802),
	/** 
	 برنامه حرکت دوربین ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حرکات ضبط شده")] CanViewTab_RecordedMovement_PresetTours = 3803,
	CanViewTab_RecordedMovement_PresetTours(3803),
	/** 
	 برنامه تغییر نمایه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("برنامه تغییر نمایه ها")] CanViewTab_ModifyViewsTimeline_PresetTours = 3804,
	CanViewTab_ModifyViewsTimeline_PresetTours(3804),

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region تنظیمات امنیتی
	/** 
	 تنظیمات امنیتی
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تنظیمات امنیتی")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Users_Role)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Users_Users)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Users_LimitLoginByAddress)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Users_AccessLoginByData)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewTab_Users_ActiveDirectorySettings)] CanViewTab_Users = 3100,
	CanViewTab_Users(3100),
	/** 
	 نقش ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نقش ها")][TRoleChildNodeAttributes(SecurityOperationEnum.CanAddNewUserGroup)][TRoleChildNodeAttributes(SecurityOperationEnum.CanEditUserGroup)][TRoleChildNodeAttributes(SecurityOperationEnum.CanDeleteUserGroup)] CanViewTab_Users_Role = 3110,
	CanViewTab_Users_Role(3110),
	/** 
	 محدودیت ورود بر اساس آدرس
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("محدودیت ورود بر اساس آدرس")] CanViewTab_Users_LimitLoginByAddress = 3130,
	CanViewTab_Users_LimitLoginByAddress(3130),
	/** 
	 مجوز ورود بر اساس زمان
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مجوز ورود بر اساس زمان")] CanViewTab_Users_AccessLoginByData = 3140,
	CanViewTab_Users_AccessLoginByData(3140),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تنظیمات Active Directory")] CanViewTab_Users_ActiveDirectorySettings = 3150,
	CanViewTab_Users_ActiveDirectorySettings(3150),

	/** 
	 اضافه کردن نقش کاربری
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اضافه کردن نقش کاربری")] CanAddNewUserGroup = 3101,
	CanAddNewUserGroup(3101),
	/** 
	 ویرایش نقش کاربری
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش نقش کاربری")] CanEditUserGroup = 3102,
	CanEditUserGroup(3102),
	/** 
	 حذف نقش کاربری
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف نقش کاربری")] CanDeleteUserGroup = 3103,
	CanDeleteUserGroup(3103),

	/** 
	 کاربران
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("کاربران")][TRoleChildNodeAttributes(SecurityOperationEnum.CanAddNewUser)][TRoleChildNodeAttributes(SecurityOperationEnum.CanEditUser)][TRoleChildNodeAttributes(SecurityOperationEnum.CanDeleteUser)] CanViewTab_Users_Users = 3120,
	CanViewTab_Users_Users(3120),
	/** 
	 اضافه کردن کاربر
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اضافه کردن کاربر")] CanAddNewUser = 3104,
	CanAddNewUser(3104),
	/** 
	 ویرایش کاربر
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش کاربر")] CanEditUser = 3105,
	CanEditUser(3105),
	/** 
	 حذف کاربر
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف کاربر")] CanDeleteUser = 3106,
	CanDeleteUser(3106),

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region دوربین ها
	/** 
	 دوربین ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نمایش دوربین ها")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewActivDeactive_Camera)][TRoleChildNodeAttributes(SecurityOperationEnum.CanAddNewCamera)][TRoleChildNodeAttributes(SecurityOperationEnum.CanDetectCamera)][TRoleChildNodeAttributes(SecurityOperationEnum.CanDeleteCamera)][TRoleChildNodeAttributes(SecurityOperationEnum.CanEditCamera)] CanViewTab_Devices = 3200,
	CanViewTab_Devices(3200),

	/** 
	 فعال و غیرفعال سازی دوربین ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("فعال و غیرفعال سازی دوربین ها")] CanViewActivDeactive_Camera = 3221,
	CanViewActivDeactive_Camera(3221),
	/** 
	 اضافه کردن دوربین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اضافه کردن دوربین")] CanAddNewCamera = 3201,
	CanAddNewCamera(3201),
	/** 
	 جستجوی دوربین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("جستجوی دوربین")] CanDetectCamera = 3202,
	CanDetectCamera(3202),
	/** 
	 حذف دوربین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف دوربین")] CanDeleteCamera = 3203,
	CanDeleteCamera(3203),
	/** 
	 ویرایش دوربین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش دوربین")] CanEditCamera = 3204,
	CanEditCamera(3204),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region پیامک
	/** 
	 پیامک
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("پیامک")][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewValidNumber_GSM)][TRoleChildNodeAttributes(SecurityOperationEnum.CanViewCommands_GSM)] CanViewTab_GSM = 3900,
	CanViewTab_GSM(3900),
	/** 
	 شماره های معتبر
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("شماره های معتبر")] CanViewValidNumber_GSM = 3901,
	CanViewValidNumber_GSM(3901),
	/** 
	 فرامین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("فرامین")] CanViewCommands_GSM = 3902,
	CanViewCommands_GSM(3902),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region مدیریت سرورها
	///// <summary>
	///// مدیریت سرورها
	///// </summary>
	//[Description("مدیریت سرورها")]
	//CanViewTab_ManageServers = 3205,

	/** 
	 سرورها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مدیریت سرورها")][TRoleChildNodeAttributes(SecurityOperationEnum.CanManageLocalServers)][TRoleChildNodeAttributes(SecurityOperationEnum.CanFederate)] CanViewTab_Servers = 3301,
	CanViewTab_Servers(3301),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مدیریت سرورها و سرویس های محلی")] CanManageLocalServers = 3302,
	CanManageLocalServers(3302),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مجتمع سازی سرور جدید")] CanFederate = 3303,
	CanFederate(3303),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ارائه سرور برای مجتمع سازی")] CanGiveFederatationService = 3304,
	CanGiveFederatationService(3304),
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 نقشه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("نقشه ها")] CanViewTab_Maps = 3206,
	CanViewTab_Maps(3206),

	/** 
	 افزونه ها
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("افزونه ها")] CanViewTab_Plugins = 3207,
	CanViewTab_Plugins(3207),

	/** 
	 مدیریت گواهینامه های نرم افزار
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("مدیریت گواهینامه های نرم افزار")] CanViewTab_License = 3210,
	CanViewTab_License(3210),

	/** 
	 تشخیص پلاک
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تشخیص پلاک")] CanViewTab_CarLicensePlateRecognition = 3211,
	CanViewTab_CarLicensePlateRecognition(3211),

	/** 
	 گزارشات سیستم
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("گزارشات سیستم")] CanViewTab_ConfigurationReports = 3212,
	CanViewTab_ConfigurationReports(3212),

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 راهبری مستقیم دوربین
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("راهبری مستقیم دوربین")] CanDirectPTZ = 3208,
	CanDirectPTZ(3208),

	//CanEnableStopAllRecording = 1401,
	//CanEnableStartAllRecording = 1402,
	//CanEnablePlaySound = 1403,
	//CanEnableExecuteAction1 = 1404,
	//CanEnableStartRecordCurrent = 1405,
	//CanEnableMuteSound = 1406,
	//CanEnableExecuteAction2 = 1407,
	//CanEnableTakeSnapshot = 1408,
	//CanEnablePlayCamera = 1409,

	//CanEnableExecuteZoomIn = 1501,
	//CanEnableExecuteZoomOut = 1502,
	//CanEnableExecuteStopZoom = 1503,
	//CanEnableExecuteStopMove = 1504,
	//CanEnableExecuteTree = 1505,
	//CanEnableExecuteMacro = 1506,
	//CanEnableExecuteOpenShater = 1507,
	//CanEnableExecuteCloseShater = 1508,
	//CanEnableExecuteTour = 1509,
	//CanEnableExecutePreset = 1510,


	//CanEnablePlayerPlay = 2201,
	//CanEnablePlayerPause = 2202,
	//CanEnablePlayBackOpenSavingFolder = 2203,
	//CanEnablePlayBackSearch = 2204,
	//CanEnablePlayBackCustom = 2205,
	//CanEnablePlayBackCustom1 = 2206,
	//CanEnablePlayBackCustom2 = 2207,
	//CanEnablePlayBackCmbEdit = 2208,

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Mobile Permission

	/** 
	 موبایل
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [TRoleChildNodeAttributes(SecurityOperationEnum.CanMobilePushCamera)][Description("دسترسی به سامانه از طریق تلفن همراه")] CanAccessMobile = 7000,
	CanAccessMobile(7000),

	/** 
	 ارسال تصویر دوربین از موبایل
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ارسال تصویر دوربین موبایل")] CanMobilePushCamera = 7010,
	CanMobilePushCamera(7010),

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion //End Of Mobile Permission

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Web Permission

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دسترسی به سامانه از طریق وب")] CanAccessWeb = 8000,
	CanAccessWeb(8000),

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion //End Of Mobile Permission

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Access Control Permissions
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("تنظیمات کنترل تردد رصد")][TRoleChildNodeAttributes(SecurityOperationEnum.CanAddAccessPoint)][TRoleChildNodeAttributes(SecurityOperationEnum.CanDeleteAccessPoint)][TRoleChildNodeAttributes(SecurityOperationEnum.CanEditAccessPoint)] CanViewMainAccessControlSettings = 9000,
	CanViewMainAccessControlSettings(9000),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("اضافه کردن نقطه دسترسی")] CanAddAccessPoint = 9001,
	CanAddAccessPoint(9001),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حذف نقطه دسترسی")] CanDeleteAccessPoint = 9002,
	CanDeleteAccessPoint(9002),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش نقطه دسترسی")] CanEditAccessPoint = 9003,
	CanEditAccessPoint(9003);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, SecurityOperationEnum> mappings;
	private static java.util.HashMap<Integer, SecurityOperationEnum> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SecurityOperationEnum.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SecurityOperationEnum>();
				}
			}
		}
		return mappings;
	}

	private SecurityOperationEnum(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SecurityOperationEnum forValue(int value)
	{
		return getMappings().get(value);
	}
}