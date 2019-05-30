package main.java.Rasad.Core;

import Rasad.Core.SimulationOptions;

public final class RVSGlobalConfig
{
	public static final boolean ConnectionStringDebugMode = false;
	public static final String DebugModeConnectionString = "Data Source=192.168.1.200;Initial Catalog=RVS;User ID=sa;Password=123;MultipleActiveResultSets=True;Application Name=RVSClient";
	//public static readonly String DebugModeConnectionString = @"Data Source=192.168.1.70\RASADSQLEXPRESS;Initial Catalog=RVS;User ID=sa;Password=M0stafa8;MultipleActiveResultSets=True;Application Name=RVSClient";
	//public static readonly String DebugModeConnectionString = @"Data Source=192.168.1.113;Initial Catalog=RVS137Big;User ID=sa;Password=M0stafa8;MultipleActiveResultSets=True;Application Name=RVSClient";
	//public static readonly String DebugModeConnectionString = @"Data Source=192.168.1.113;Initial Catalog=RVSA15;User ID=sa;Password=M0stafa8;MultipleActiveResultSets=True;Application Name=RVSClient";

	//public static readonly bool DebugLPR = false;
	public static final boolean DebugFishEye = false;
	public static final boolean DebugFaceDetection = false;
	public static final boolean DebugVideoSynopsis = false;
	public static final boolean DebugAccessControl = false;

	public static final boolean DebugDisableFederationSync = false;

	static
	{
		///#if DEBUG
		//            ClientMultiProcessMode = false;
		///#else
		setClientMultiProcessMode(true);
		///#endif

		setSimulationOptions(new SimulationOptions(false, false));
		setDebuggingCommunicationService(false);
		setDebuggingNotifications(false);

		setOverrideLogLevelSetting(null);
		//OverrideLogLevelSetting = RasadLogLevel.All;

		setUseLocalCommunicationOnServer(true);
		setServerCPUEnhancementsActive(true);
		setMPAElementsMemoryFreeUpIntervalMilliseconds(FloatingPointToInteger.ToInt32(TimeSpan.FromHours(1).TotalMilliseconds));
	}

	private static boolean ClientMultiProcessMode;
	public static boolean getClientMultiProcessMode()
	{
		return ClientMultiProcessMode;
	}
	private static void setClientMultiProcessMode(boolean value)
	{
		ClientMultiProcessMode = value;
	}

	// simulation options
	private static Rasad.Core.SimulationOptions SimulationOptions;
	public static SimulationOptions getSimulationOptions()
	{
		return SimulationOptions;
	}
	private static void setSimulationOptions(SimulationOptions value)
	{
		SimulationOptions = value;
	}
	private static boolean DebuggingCommunicationService;
	public static boolean getDebuggingCommunicationService()
	{
		return DebuggingCommunicationService;
	}
	private static void setDebuggingCommunicationService(boolean value)
	{
		DebuggingCommunicationService = value;
	}
	private static boolean DebuggingNotifications;
	public static boolean getDebuggingNotifications()
	{
		return DebuggingNotifications;
	}
	private static void setDebuggingNotifications(boolean value)
	{
		DebuggingNotifications = value;
	}
	private static RasadLogLevel OverrideLogLevelSetting = null;
	public static RasadLogLevel getOverrideLogLevelSetting()
	{
		return OverrideLogLevelSetting;
	}
	private static void setOverrideLogLevelSetting(RasadLogLevel value)
	{
		OverrideLogLevelSetting = value;
	}

	private static boolean UseLocalCommunicationOnServer;
	public static boolean getUseLocalCommunicationOnServer()
	{
		return UseLocalCommunicationOnServer;
	}
	private static void setUseLocalCommunicationOnServer(boolean value)
	{
		UseLocalCommunicationOnServer = value;
	}
	private static boolean ServerCPUEnhancementsActive;
	public static boolean getServerCPUEnhancementsActive()
	{
		return ServerCPUEnhancementsActive;
	}
	private static void setServerCPUEnhancementsActive(boolean value)
	{
		ServerCPUEnhancementsActive = value;
	}

	private static int MPAElementsMemoryFreeUpIntervalMilliseconds;
	public static int getMPAElementsMemoryFreeUpIntervalMilliseconds()
	{
		return MPAElementsMemoryFreeUpIntervalMilliseconds;
	}
	private static void setMPAElementsMemoryFreeUpIntervalMilliseconds(int value)
	{
		MPAElementsMemoryFreeUpIntervalMilliseconds = value;
	}
}