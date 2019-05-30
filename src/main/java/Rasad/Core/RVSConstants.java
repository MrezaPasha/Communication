package main.java.Rasad.Core;

import Rasad.Core.Services.NotificationService.Public.*;
import Rasad.Core.VersionTarget;
import Rasad.VideoSurveillance.Core.*;
import java.util.*;

public final class RVSConstants
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.NoOptimization)] static RVSConstants()
	static
	{
		setKeepCameraStreamAliveTimeoutMilliseconds(TimeSpan.FromSeconds(1).TotalMilliseconds);
		SetupVersionVars();
		TLogManager.MinDiskFreeSpaceLogFilesMB = RVSRootDriveMinDiskFreeSpaceMB;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public static final String LPR_Plugin_Key = "{5adc8141-cb64-4d01-8a1b-0ddba40a9ab1}";
	public static final String FishEye_Plugin_Key = "{6c752b69-09ae-4cfb-bb41-40d0311a7699}";
	public static final String FaceDetection_Plugin_Key = "{7BBE16E3-EE26-4ECC-8176-0EFA48CCA56A}";
	public static final String VideoSynopsis_Plugin_Key = "{2564D0DD-4133-4AF0-B320-2B5FDB922DCE}";
	public static final String AccessControl_Plugin_Key = "{77D53D5A-3366-4D0E-AE90-37599EB8B73C}";

	public static final String ClientProcessModuleName = "Rasad.Console.WPF.exe";
	public static final String AdministratorClientProcessModuleName = "Rasad.Console.Administrator.exe";

	public static final int MPAManagerMaxOverallStartAllDurationSeconds = 2 * 60;
	public static final int MPAManagerMaxStartDelayMilliseconds = 3 * 1000;
	public static final int MPAManagerFullReloadFromDBIntervalMinutes = 30; // every half hour
	public static final int MPAManager_ElementProcessCreateTimeMaxSeconds = 60;
	public static final int MPAManager_ElementStartupTimeMaxSeconds = 30;
	public static final int MPAManager_ElementRestartDelayMilliseconds = 1000;

	public static final int MPAManager_ElementWorkingStatusCheckMinStepMilliseconds = 1000;
	public static final int MPAManager_ElementWorkingStatusCheckPreferredStepMilliseconds = 30 * 1000;
	public static final int MPAManager_ElementWorkingStatusCheckAllCheckTotalMilliseconds = 5 * 60 * 1000;

	public static final int CommunicationPingIntervalMilliseconds = 5000;
	public static final double MPAElementActivityTimeoutMinutes = 1.5;

	public static final int NormalFrameRate = 8; // changed to 8 to have default of 8 fps ->25;
	public static final int LowFrameRate = 3;
	public static final int RecordingFrameRate = 10;
	public static final int RecordingSelectionFrameRateMin = 10;
	public static final int RecordingSelectionFrameRateMax = 15;

	public static final int LicenseRequeryIntervalWhenLicenseIsReceivedMinutes = 3; // this is used both in client ans services
	public static final int LicenseRequeryIntervalWhenLicenseNotReceivedSeconds = 30;

	public static final int ClientReadLicenseTryCount = 10;
	public static final int ClientCheckLicenseTryCount = 10;
	public static final int ClientCheckLicenseWarningSkipCount = 3;
	public static final String CheckLicenseFailedMessage = "قفل سخت افزاری متصل نیست یا دوره آزمایشی این نسخه به اتمام رسیده است!";
	public static final int ServicesLicenseReloadTryCountBeforeInvalidate = 10;
	public static final int ServicesStartupDelayForLicenseReadyMilliseconds = 10000;
	public static final int MainServerCheckLicenseTryCount = 30;
	public static final int MinutesBeforeEmptyLicenseHonor = 5;

	public static final String StreamerProfileTitlePrefix = "STR_";
	public static final String StreamerProfileTokenPrefix = "STR_";

	public static final int ServiceSettingsAutoReloadIntervalMinutes = 5;
	public static final int ClientSettingsAutoReloadIntervalMinutes = 2;

	public static final int MinODMPlayTimeMilliseconds = 4000;
	//public const int ODMMaxPlaybackConnectionWaitMilliseconds = 6000;
	public static final ConnectionWaitConfigItem[] ODMMaxPlaybackConnectionWaits = new ConnectionWaitConfigItem[]
	{
		new ConnectionWaitConfigItem(1, 1500),
		new ConnectionWaitConfigItem(2, 2000),
		new ConnectionWaitConfigItem(2, 6000),
		new ConnectionWaitConfigItem(2, 5000),
		new ConnectionWaitConfigItem(0, 6000)
	};

	public static final int PresetManagerServerSuspendTimeSeconds = 60;

	public static final boolean PTZDirectAccessToCameraDefault = true;

	public static final int MaxMotionDurationAndReportMilliseconds = 30 * 1000;

	public static final int RecordHistoryKeepMinutes = 20;
	public static final int RecordHistoryMinDelayBetweenChecksMilliseconds = 5000;
	public static final int RecordKeepPreviousOrNextWhenInBoundaryTimeSeconds = 5;

	public static final int CommunicationServerMessageCacheTimeMilliseconds = 30 * 60 * 1000;

	public static final int StreamerMonitorAllUrlsCheckIntervalMinutes = 5;
	public static final int StreamerMonitorWaitForFirstFrameMaxSeconds = 40;
	public static final int StreamerMonitorUrlIfAccessedInLastMinutes = 60;

	public static String SystemTakeSnapshotDefaultText = "نرم افزار نظارت تصویری";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region SETUP SPECIFIC CONSTANTS
	// ========================= SETUP SPECIFIC CONSTANTS ========================= //
	// ** CAUTION: DON'T CHANGE THESE VALUES TO CONSTANT - THEY MUST BE VARIABLES ** //
	public static final int MaxDevices = Integer.MAX_VALUE - 1; // ** set to any value greater than 0

	public static boolean AllowThemeChange;
	public static String DefaultThemeName;

	public static String ProductTitle;
	public static String ApplicationTitle;
	public static String ApplicationTitleAdministrator;
	public static String MessageBoxTitle;
	public static String getSystemVersionTitle()
	{
		return String.Concat("(نگارش : ", RVSConstants.SystemVersion, ")");
	}

	// For Hafezan use these titles
	//public const String ApplicationTitle = "سیستم نظارت تصویری رصــد حافظان";
	//public const String MessageBoxTitle = "رصـد حافظان";
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.NoOptimization)] private static void SetupVersionVars()
	private static void SetupVersionVars()
	{
		final String ProductBaseTitle = "سیستم نظارت تصویری";
		String productCustomTitle;
		switch (VersionTarget)
		{
			case Rasad:
				productCustomTitle = "رصد";
				AllowThemeChange = true;
				DefaultThemeName = "DeepBlue";
				//SystemTakeSnapshotDefaultText = 
				break;

			case Sepah:
				productCustomTitle = "رصد";
				AllowThemeChange = false;
				DefaultThemeName = "MetropolisDark";
				break;

			case Hafezan:
				productCustomTitle = "رصد حافظان";
				AllowThemeChange = true;
				DefaultThemeName = "DeepBlue";
				break;

			case SepidBam:
				productCustomTitle = "رصد سپید بام دماوند";
				AllowThemeChange = true;
				DefaultThemeName = "DeepBlue";
				break;

			default:
				throw new RuntimeException("Invalid version target specified");
		}

		ProductTitle = String.format("%1$s %2$s", ProductBaseTitle, productCustomTitle);
		SystemTakeSnapshotDefaultText = ProductTitle;
		ApplicationTitle = String.format("%1$s %2$s", ProductTitle, getSystemVersionTitle());
		ApplicationTitleAdministrator = "مدیریت" + " " + ApplicationTitle;
		MessageBoxTitle = productCustomTitle;
	}

	// ============================================================================ //
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 زمان زنده نگهداشتن استریمهای زنده جهت استفاده مجدد
	*/
	private static double KeepCameraStreamAliveTimeoutMilliseconds;
	public static double getKeepCameraStreamAliveTimeoutMilliseconds()
	{
		return KeepCameraStreamAliveTimeoutMilliseconds;
	}
	public static void setKeepCameraStreamAliveTimeoutMilliseconds(double value)
	{
		KeepCameraStreamAliveTimeoutMilliseconds = value;
	}

	public static final String GlobalConfigFileForServer = "ServerConfig.config";
	public static final String GlobalConfigFileForClient = "ClientConfig.config";

	//public const RasadLogLevel DefaultLogLevel = RasadLogLevel.Fatal | RasadLogLevel.Error | RasadLogLevel.Warn | RasadLogLevel.Info | RasadLogLevel.Debug;
	public static final RasadLogLevel DefaultLogLevel = RasadLogLevel.Fatal | RasadLogLevel.Error | RasadLogLevel.Warn | RasadLogLevel.Info;
	public static final boolean DefaultCentralLogEnabled = true;
	public static final int DefaultMaxDaysKeepLogFiles = 30;

	public static final String CameraEventHttpDefaultParameterName = "Message";
	public static final String CameraEventMotionOnDefaultParameterValue = "MotionON";
	public static final String CameraEventMotionOffDefaultParameterValue = "MotionOFF";

	public static final String HttpEventListenerDefaultUsername = "rasad";
	public static final String HttpEventListenerDefaultPassword = "12345";

	// max playback speeds
	public static final int MaxPlaybackSpeedForSinglePlayback = 11; // values in TPlaybackSpeedBar.xaml class TPlaybackSpeedCollection
	public static final int MaxPlaybackSpeedWhenMax4CamerasPlayback = 7;
	public static final int MaxPlaybackSpeedWhenMax6CamerasPlayback = 6;
	public static final int MaxPlaybackSpeedWhenMoreThan6CamerasPlayback = 5;

	public static final long RVSRootDriveMinDiskFreeSpaceMB = 10 * 1024;

	public static final int ExcessiveErrorLogPreventcontinuousCount = 4;
	public static final int ExcessiveErrorLogPreventMinDiffBetweenErrorsMilliseconds = 2500;

	public static final String LocalCommunicationSharedMemoryIDPrefix = "RVSLocalCommunication";

	public static final SystemEntity LocalCommunicationServerEntity = SystemEntity.MainService;
	public static final ArrayList<SystemEntity> LocalCommunicationExcludedServerEntities = new ArrayList<SystemEntity>(Arrays.asList(new SystemEntity[] {SystemEntity.LicenseService, SystemEntity.EventTriggerService, SystemEntity.FailoverService, SystemEntity.Plugin}));

	public static final int WaitForStreamerProfileReadyMilliseconds = 3000;

	public static final int ProfileMinGovLength = 1;
	public static final int ProfileGovLengthDefault = 32;
	public static final int ProfileCompressionDefault = 50; //30;

	public static final double FrameRateToGOVLengthMultiplier = 1.10;

	public static final int PTZContinuousActionTimeoutMilliseconds = 800;

	// =============================== Mobile service version ==========================

	public static final ProfileStreamerMode DefaultStreamerMode = ProfileStreamerMode.OnDemand;
	public static final Rasad.Core.Services.CameraService.CameraDesignTimeProfileSelectionPolicy DefaultCameraDesignTimeProfileSelectionPolicy = Services.CameraService.CameraDesignTimeProfileSelectionPolicy.PreferStreamer;
	public static final Rasad.Core.Services.CameraService.CameraProfileAccessPolicy DefaultCameraProfileAccessPolicy = Services.CameraService.CameraProfileAccessPolicy.ForceStreamer;

	public static final boolean CameraTooltipsDefaultVisible = false;
	public static final boolean MultiMonitorRunDefault = true;

	public static final int CameraCommandsTimeoutMilliseconds = 15000;

	public static final String UpdateSourceDirDefault = "C:\\Program Files (x86)\\Rasad\\Rasad Video Surveillance (Client)";

	public static final String[] UpdaterAgentFilesWithPriority = new String[] {"Rasad.VS.Updater.Agent2.exe", "Rasad.VS.Updater.Agent.exe"};

	public static final UUID NoFilterFrameIDForLiveView = UUID.fromString("{E75B25BE-0F9F-4DDD-BFA7-E425AF104A89}");
	public static final UUID NoFilterFrameIDForPlaybackView = UUID.fromString("{6A6F0FB3-659D-4A25-8929-541ECC9F85B5}");

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Cell Appearance
	public static final System.Windows.Media.Brush CellLockedBorderColor = System.Windows.Media.Brushes.Red;
	public static final System.Windows.Media.Brush CellSelectedUnlockedBorderColor = System.Windows.Media.Brushes.Orange;
	public static final System.Windows.Media.Brush CellUnselectedBorderColor = System.Windows.Media.Brushes.Black;
	public static final System.Windows.Media.Brush CellUnselectedBorderColorPlayback = System.Windows.Media.Brushes.White;

	public static final System.Windows.Thickness CellLockedThickness = new System.Windows.Thickness(2.3);
	public static final System.Windows.Thickness CellSelectedUnlockedThickness = new System.Windows.Thickness(2.3);
	public static final System.Windows.Thickness CellUnselectedThickness = new System.Windows.Thickness(1.0);
	public static final System.Windows.Thickness CellUnselectedThicknessLive = new System.Windows.Thickness(1.0);

	public static final System.Windows.Media.Brush SubCellLockedBorderColor = System.Windows.Media.Brushes.Brown;
	public static final System.Windows.Media.Brush SubCellSelectedUnlockedBorderColor = System.Windows.Media.Brushes.Yellow;
	public static final System.Windows.Media.Brush SubCellUnselectedBorderColor = System.Windows.Media.Brushes.Black;

	public static final System.Windows.Thickness SubCellLockedThickness = new System.Windows.Thickness(2.0);
	public static final System.Windows.Thickness SubCellSelectedUnlockedThickness = new System.Windows.Thickness(0.0);
	public static final System.Windows.Thickness SubCellUnselectedThickness = new System.Windows.Thickness(0.0);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public static final int PTZFullZoomOutZoomValue = -100;
	public static final int PTZFullZoomOutZoomSpeed = 100;

	public static final int MotionTriggerMinTimeBetweenTriggersMilliseconds = 60000;

	// ============================================
	public static boolean UseMPVInPlayback = true;

	// to use GStreamer test version in live view:
	//    1. use _GST_ in build for Rasad.Client
	//    2. Set UseMPVInLive = true, UseSingleView = false
	public static final boolean UseMPVInLive = false;
	public static final boolean UseSingleView = true;
	// ============================================

	public static final LPREngineType LPREngine = LPREngineType.ITS;

	public static final int PresetSpeed = 100;

	public static final int RecordingMaxCriticalTimeSpaceCount = 5;

	public static final int MinRecordFileLengthBytes = 1024 * 10; // 10 KB

	public static final boolean StreamerLoadProfilesAtStartup = true;

	// don't use SystemParameters.MinimumHorizontalDragDistance  in all situations,
	//   because sometimes the sensitivity is high for big drag operations
	public static final int MinimumHorizontalDragDistanceForBigDrag = 15;

	public static final Rasad.Core.Services.NotificationService.Services.ServiceType[] ServicesNotDependingOnFailoverState = new Rasad.Core.Services.NotificationService.Services.ServiceType[] {Services.NotificationService.Services.ServiceType.LicenseService, Services.NotificationService.Services.ServiceType.Log};

	public static final String SystemUserInfo = "سیستم";

	// * Caution: if change value of ServerSideMapImageAccessXYZ, also uploaded files on the server may be required to be renamed.
	public static final int ServerSideMapImageAccessXYZ = 1000;


	public static final boolean FailoverRecordFileTransferEnabledDefault = true;
	public static final Rasad.VideoSurveillance.Core.RecordFileTransferPolicy FailoverRecordFileTransferPolicyDefault = Rasad.VideoSurveillance.Core.RecordFileTransferPolicy.NewestRecordingsPriority;
	public static final Rasad.VideoSurveillance.Core.RecordFileTransferSpeed FailoverRecordFileTransferSpeedDefault = Rasad.VideoSurveillance.Core.RecordFileTransferSpeed.Low;

	public static final String GSMMessageSuffix = "\nRVSMobileService-0";

	public static final String BookmarkMiscBookmarkName = "متفرقه";
	public static final String BookmarkDefaultColor = "#FFAE3420";

	//public static readonly int MaxCameraCount = 2;
	public static final int DefaultProcessedFramesPerSecondForLPR = 10;
	public static final char LPRCorruptedChar = '?';


	public static final System.Windows.Duration D3DTryLockDuration = new System.Windows.Duration(TimeSpan.FromMilliseconds(500));

	// all in milliseconds
	public static final int DefaultStreamerLatency = 100;
	public static final int DefaultStreamerTcpTimeout = 5000;
	public static final int DefaultLiveLatency = 100;
	public static final int MaxLatency = 5000;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static readonly ushort SQLServerDefaultPort = 1433;
	public static final short SQLServerDefaultPort = 1433;

	public static final String NFSEncryptionKey = "02@iert672561010XP!";
	//public static readonly String LDAPEncryptionKey = "658*&j#POaw98g210^q";
	//public static readonly String PassNetworkEncryptionKey = "69089gh4!@36}[GH658";  -> this is no longer used for password encryption. Now we use RSA.

	public static final String MobileServiceDir = "MobileServer";
	public static final String MobileTranscoderModuleName = "Rasad.VS.Mobile.Transcoder.exe";
	public static final String MobilePlaybackExporterModuleName = "Rasad.VS.Mobile.PlaybackManager.exe";

	public static final String WebSettingsPrefix = "WEBCLIENT.";

	public static final String MulticastAddressRangeStartDefault = "232.0.2.0";
	public static final String MulticastAddressRangeEndDefault = "232.0.2.0";
	public static final int MulticastPortRangeStartDefault = 6000;
	public static final int MulticastPortRangeEndDefault = 8000;
	public static final int MulticastMTUDefault = 1500;
	public static final int MulticastTTLDefault = 32;

	public static final String MulticastStreamerModuleName = "Rasad.MulticastStreamer.exe";
	public static final int MulticastCameraUrlExpirationTimeMilliseconds = 14000;

	public static final String WebInvestigationsDownloadChannel = "channel-inv-download";
	public static final int WebLiveVideoDefaultMaxHeight = 720;

	public static final String SnapshotThumbnailSuffix = "_t";

	public static final String WebLocalProxyToMobileServiceOriginalUrlHostHeader = "_ORIGINAL_URL_HOST_";

	public static final String AccessControlServiceDir = "AccessControlServer";

	public static final Rasad.VideoSurveillance.Core.RecordingStorageMethod DefaultRecordingStorageMethod = Rasad.VideoSurveillance.Core.RecordingStorageMethod.LegacyMethod;
	public static final int DefaultRecordingStorageRetentionTimeMinutes = 0; // 0 means max
	public static final int DefaultRecordingStorageMaxSizeMB = 1000 * 1024; // 1000 GB

	public static final String MediaDatabaseFileName = "MediaInfo";

	public static final String CamListXmlPassEncryptionKey = "36o$hg@!09#$_PE-0t3";

	public static final String MotionDetectionProcessorModuleName = "Rasad.MotionDetectionProcessor.exe";

	// if changed, also change [DefaultValue(MotionDetectionMethod.Basic)] in TMotionDetectionSettingsViewModel.cs
	public static final Rasad.VideoSurveillance.Core.MotionDetectionMethod DefaultMotionDetectionMethodForH264Cameras = Rasad.VideoSurveillance.Core.MotionDetectionMethod.MotionVector;
	public static final Rasad.VideoSurveillance.Core.MotionDetectionMethod DefaultMotionDetectionMethod = Rasad.VideoSurveillance.Core.MotionDetectionMethod.Basic;

	public static final int DefaultAdminUserID = 1;
	public static final String DefaultAdminUserName = "admin";
	public static final String DefaultAdminPassword = "admin";

	public static final String DefaultAdministratorsRoleInternalName = "Administrators";
	public static final String DefaultAdministratorsRoleName = "سرپرست سیستم";
	public static final String DefaultAdministratorsRoleDescription = "کاربران سرپرست سیستم";

	public static final String StartupLoaderModuleName = "Rasad.StartupLoader.exe";

	// ************************************************************************* //
	// MAIN CONFIGS TO CHECK BEFORE ANY RELEASE
	// ************************************************************************* //
	public static final Rasad.Core.VersionTarget VersionTarget = Rasad.Core.VersionTarget.Rasad;
	// --------------------------------------------------------------------------
	/** 
	 Sample : 3.0.124
	*/
	//public static readonly string SystemVersion = "3.0.134";  // must be readonly not constant
	//public static readonly string SystemVersion = "3.0.133";  // must be readonly not constant
	public static final String SystemVersion = "3.0.156";
	public static final String SystemVersionForPlugins = "3.0.155";
	public static final String CurrentVersion = "3.0.140";
	public static final String SupportVersion = "3.0.140";
	// 3.0.128 :: Federation
	// 3.0.137 :: Failover
	// --------------------------------------------------------------------------
	// for mashhad SingleServiceMode = false
	//public static readonly bool SingleServiceModeRecStrMot = false;  // always false  --> moved to Server.config
	//public static readonly bool SingleServiceModeRecStr = true;  // for shahrdari = true  --> moved to Server.config
	// --------------------------------------------------------------------------
	public static final boolean IsFailoverVersion = false;
	// --------------------------------------------------------------------------
	//public static readonly bool MotionIsSingleProcessor = true;  --> moved to Server.config
	public static final boolean UseMotionDetectionV3 = true;
	// --------------------------------------------------------------------------
	public static boolean SoftwareRendering = false;
	// ************************************************************************* //
	// ************************************************************************* //
}