package main.java.Rasad.Core;

public final class RVSSettingKeys
{
	public static final String InstallationKey = "InstallationKey";
	public static final String InstallationRole = "InstallationRole";

	public static final String FailoverInstallation = "FailoverInstallation";
	public static final String FailoverRemoteFailoverServerGroupID = "FailoverRemoteFailoverServerGroupID";
	public static final String FailoverRemoteServerAddress = "FailoverRemoteServerAddress";
	public static final String FailoverRemoteServerUserID = "FailoverRemoteServerUserID";
	public static final String FailoverRemoteServerUsername = "FailoverRemoteServerUsername";
	public static final String FailoverRemoteConnectionString = "FailoverRemoteConnectionString";
	public static final String FailoverServerIsOnline = "FailoverServerIsOnline";
	public static final String FailoverRecordFileTransferEnabled = "FailoverRecordFileTransferEnabled";
	public static final String FailoverRecordFileTransferPolicy = "FailoverRecordFileTransferPolicy";
	public static final String FailoverRecordingFileTransferSpeed = "FailoverRecordingFileTransferSpeed";

	public static final String FailoverOnlineStartTime = "FailoverOnlineStartTime";
	public static final String FailoverOnlineEndTime = "FailoverOnlineEndTime";


	// please (!) add keys as readonly constants to prevent typing errors in source code!
	public static final String CentralLogEnabled = "CentralLogEnabled";
	public static final String LogLevel = "LogLevel";
	public static final String MaxDaysKeepLogFiles = "MaxDaysKeepLogFiles";
	public static final String CameraTooltips = "CameraTooltips";
	public static final String MultiMonitorRun = "MultiMonitorRun";
	public static final String UpdateSourceDir = "UpdateSourceDir";
	public static final String SecurityTokenExpirySeconds = "SecurityTokenExpirySeconds";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Recording
	public static final String EdgeRecordingsSyncEnabled = "EdgeRecordingsSyncEnabled";
	public static final String EdgeRecordingsDeleteAfterSync = "EdgeRecordingsDeleteAfterSync";
	public static final String EdgeRecordingsSyncMode = "EdgeRecordingsSyncMode";

	public static final String RecordingStorageMethod = "RecordingStorageMethod";
	public static final String SingleServiceMode = "SingleServiceMode";
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Web Export
	public static final String WebInterfaceEnabled = "WebInterfaceEnabled";
	public static final String WebLiveVideoMaxHeight = "WebLiveVideoMaxHeight";
	public static final String WebAllCamerasViewEnabled = "WebAllCamerasViewEnabled";
	public static final String WebInvestigationsEnabled = "WebInvestigationsEnabled";
	public static final String InvestigationsStoragePath = "InvestigationsStoragePath";
	public static final String InvestigationsStorageLimitSize = "InvestigationsStorageLimitSize";
	public static final String InvestigationsStorageLimitSizeValue = "InvestigationsStorageLimitSizeValue";

	public static final String WebInterfaceHttpEnabled = "WebInterfaceHttpEnabled";
	public static final String WebInterfaceHttpsEnabled = "WebInterfaceHttpsEnabled";
	public static final String WebInterfaceHttpsForceLive = "WebInterfaceHttpsForceLive";
	public static final String WebInterfaceHttpsForcePlayback = "WebInterfaceHttpsForcePlayback";

	public static final String WebInternetAccessEnabled = "WebInternetAccessEnabled";
	public static final String InternetAccessAddress = "InternetAccessAddress";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Web snapshots
	public static final String WebSnapshotsEnabled = "WebSnapshotsEnabled";
	public static final String WebSnapshotsStoragePath = "WebSnapshotsStoragePath";
	public static final String WebSnapshotsStorageLimitSize = "WebSnapshotsStorageLimitSize";
	public static final String WebSnapshotsStorageLimitSizeValue = "WebSnapshotsStorageLimitSizeValue";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Multicast
	public static final String MulticastEnabled = "MulticastEnabled";
	public static final String MulticastAddressStart = "MulticastAddressStart";
	public static final String MulticastAddressEnd = "MulticastAddressEnd";
	public static final String MulticastPortStart = "MulticastPortStart";
	public static final String MulticastPortEnd = "MulticastPortEnd";
	public static final String MulticastMTU = "MulticastMTU";
	public static final String MulticastTTL = "MulticastTTL";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Map
	public static final String MapProviderTypeKey = "MapProviderType";
	public static final String MapProviderNameKey = "MapProviderName";
	public static final String IsImageKey = "MapIsImage";
	public static final String AllowCachingKey = "MapAllowCaching";
	public static final String CustomMapAddressKey = "MapCustomMapAddress";
	public static final String CacheFilesAddressKey = "MapCacheFilesAddress";
	public static final String ImageFileAddressKey = "MapImageFileAddress";
	public static final String CenterPositionLatKey = "MapCenterPositionLat";
	public static final String CenterPositionLngKey = "MapCenterPositionLng";
	public static final String DefaultZoomLevelKey = "MapDefaultZoomLevel";
	public static final String CacheLimitSizeKey = "MapCacheLimitSize";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region SMTP
	public static final String SMTPServer = "SMTPServer";
	public static final String SMTPPort = "SMTPPort";
	public static final String SMTPUsername = "SMTPUsername";
	public static final String SMTPPassword = "SMTPPassword";
	public static final String SMTPSenderName = "SMTPSenderName";
	public static final String SMTPSenderMail = "SMTPSenderMail";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region FTP
	public static final String FTPServer = "FTPServer";
	public static final String FTPPort = "FTPPort";
	public static final String FTPUsername = "FTPUsername";
	public static final String FTPPassword = "FTPPassword";
	public static final String FTPRemoteFolder = "FTPRemoteFolder";
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region OSD Settings
	public static final String TitleFontName = "TitleFontName";
	public static final String TitleFontSize = "TitleFontSize";
	public static final String TitleColor = "TitleColor";
	public static final String TitleStrokeColor = "TitleStrokeColor";
	public static final String TitleStrokeSize = "TitleStrokeSize";
	public static final String TitleBackgroundColor = "TitleBackgroundColor";
	public static final String OSDShowCameraTitle = "OSDShowCameraTitle";
	public static final String TitleHorizontalAlignment = "TitleHorizontalAlignment";
	public static final String TitleVerticalAlignment = "TitleVerticalAlignment";
	public static final String ShowMotionDetectionIcon = "ShowMotionDetectionIcon";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Export Video Settings
	public static final String IsOverlayDate = "IsOverlayDate";
	public static final String IsOverlayCameraTitle = "IsOverlayCameraTitle";
	public static final String IsOverlayPicture = "IsOverlayPicture";
	public static final String IsOverlayText = "IsOverlayText";
	public static final String IsOverlayTime = "IsOverlayTime";
	public static final String IsOverlayLogo = "IsOverlayLogo";
	public static final String OverlayText = "OverlayText";
	public static final String OverlayPicturePath = "OverlayPicturePath";

	public static final String FontColor_Suffix = "FontColor";
	public static final String FontName_Suffix = "FontName";
	public static final String FontSize_Suffix = "FontSize";
	public static final String Position_Suffix = "Position";
	public static final String Opacity_Suffix = "Opacity";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Security
	public static final String RestrictionByIPAllowed = "RestrictionByIPAllowed";
	public static final String RestrictionByMacAllowed = "RestrictionByMacAllowed";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public static final String CameraDesignTimeProfileSelectionPolicy = "CameraDesignTimeProfileSelectionPolicy";
	public static final String CameraProfileAccessPolicy = "CameraProfileAccessPolicy";

	public static final String SaveEventCount = "SaveEventCount";
	public static final String SaveEventDays = "SaveEventDays";
	public static final String DeleteEventsMethod = "DeleteEventsMethod";

	public static final String SelectedViewTourID = "SelectedViewTourID";
	public static final String SelectedPlaybackID = "SelectedPlaybackID";
	public static final String AllowShowPopups = "AllowShowPopups";

	public static final String SelectedViewID = "SelectedViewID";
	public static final String IsNavBarPinned = "IsNavBarPinned";

	public static final String SMSActiveDeactive = "SMSActiveDeactive";
	public static final String ToursSuspensionTimeout = "ToursSuspensionTimeout";
	public static final String UserApplicationTheme = "UserApplicationTheme";
	public static final String IsConsoleFullScreen = "IsConsoleFullScreen";

	public static final String ReportLogoImage = "ReportLogoImage";
	public static final String PTZDirectAccessToCamera = "PTZDirectAccessToCamera";
	public static final String SystemTakeSnapshotTextKey = "SystemTakeSnapshotTextKey";
	public static final String SelectedVideoWallTourID = "SelectedVideoWallTourID";
	public static final String DesktopCollaborationConfirmNotRequired = "DesktopCollaborationConfirmNotRequired";

	public static final String LDAPEnabled = "ActiveDirectoryEnabled";
	//public const String LDAPDomainName = "LDAPDomainName";
	//public const String LDAPDomainController = "LDAPDomainController";
	//public const String LDAPAccessUsername = "LDAPAccessUsername";
	//public const String LDAPAccessPassword = "LDAPAccessPassword";


	public static String[] KeysForFailoverSync = new String[] {MaxDaysKeepLogFiles, CameraTooltips, MultiMonitorRun, "PhotoPath", MapProviderTypeKey, MapProviderNameKey, IsImageKey, AllowCachingKey, CustomMapAddressKey, CacheFilesAddressKey, ImageFileAddressKey, CenterPositionLatKey, CenterPositionLngKey, DefaultZoomLevelKey, CacheLimitSizeKey, SMTPServer, SMTPPort, SMTPUsername, SMTPPassword, SMTPSenderName, SMTPSenderMail, "ViewingCamerasDecreaseSpeed", "CpuBusyDecreaseSpeed", "CpuBusyTopLimit", "ViewingCamerasTopLimit", FTPServer, FTPPort, FTPUsername, FTPPassword, FTPRemoteFolder, TitleFontName, TitleFontSize, TitleColor, TitleStrokeColor, TitleStrokeSize, TitleBackgroundColor, OSDShowCameraTitle, TitleHorizontalAlignment, TitleVerticalAlignment, ShowMotionDetectionIcon, IsOverlayDate, IsOverlayCameraTitle, IsOverlayPicture, IsOverlayText, IsOverlayTime, IsOverlayLogo, OverlayText, OverlayPicturePath, RestrictionByIPAllowed, RestrictionByMacAllowed, CameraDesignTimeProfileSelectionPolicy, CameraProfileAccessPolicy, SaveEventCount, SaveEventDays, DeleteEventsMethod, SelectedViewTourID, SelectedPlaybackID, AllowShowPopups, SelectedViewID, IsNavBarPinned, SMSActiveDeactive, ToursSuspensionTimeout, UserApplicationTheme, IsConsoleFullScreen, ReportLogoImage, PTZDirectAccessToCamera, SystemTakeSnapshotTextKey, SelectedVideoWallTourID, DesktopCollaborationConfirmNotRequired, "WindowIsPinned", "PTZIsCollapsed", "ControlingIsCollapsed", "EventsIsCollapsed", "ViewIsCollapsed", "CamerasIsCollapsed", "VideoWallIsCollapsed", "MapsIsCollapsed", "SettinkKeys_TakenPhotoes", "SettinkKeys_DisplayPerformance_Setting", "SettinkKeys_TOSDSetting2", "SettinkKeys_SMTP_Setting", "SettinkKeys_Event_Setting", "SettinkKeys_Event_Setting", "SettinkKeys_ExportVideo_Setting", "SettinkKeys_ExportVideo_Setting", "SettinkKeys_FTP_Setting", "SettinkKeys_TOSDSetting", "SettinkKeys_TOSDSetting1", "SettinkKeys_Other_Setting", "SettinkKeys_StreamSettings", "SettinkKeys_ExportVideo_Setting", "SettinkKeys_TakenPhotoes", "SettinkKeys_Event_Setting", "UsersOnlineIsCollapsed", "PublicChatIsCollapsed", LDAPEnabled, SecurityTokenExpirySeconds};

	public static String[] KeysWithSuffixForFailoverSync = new String[] {FontColor_Suffix, FontName_Suffix, FontSize_Suffix, Position_Suffix, Opacity_Suffix};

	public static String[] KeysForServerSettingsFailoverSync = new String[] {MulticastEnabled, MulticastAddressStart, MulticastAddressEnd, MulticastPortStart, MulticastPortEnd, MulticastMTU, MulticastTTL, WebInterfaceEnabled, WebLiveVideoMaxHeight, WebAllCamerasViewEnabled, WebInvestigationsEnabled, InvestigationsStoragePath, InvestigationsStorageLimitSize, InvestigationsStorageLimitSizeValue, WebInterfaceHttpEnabled, WebInterfaceHttpsEnabled, WebInterfaceHttpsForceLive, WebInterfaceHttpsForcePlayback, WebInternetAccessEnabled, InternetAccessAddress, WebSnapshotsEnabled, WebSnapshotsStoragePath, WebSnapshotsStorageLimitSize, WebSnapshotsStorageLimitSizeValue, EdgeRecordingsSyncEnabled, EdgeRecordingsDeleteAfterSync, EdgeRecordingsSyncMode, SingleServiceMode};
}