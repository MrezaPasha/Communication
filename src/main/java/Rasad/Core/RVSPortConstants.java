package main.java.Rasad.Core;

public final class RVSPortConstants
{
	// communication service
	public static final int CommunicationPortNumber = 8221;

	// streamer
	public static final int StreamerStartPortNumber = 35000;
	public static final int StreamerEndPortNumber = 36000;

	// Log service
	public static final int LogServerUdpPortNumber = 38801;

	// Playback service
	public static final int PlaybackServicePortNumber = 11027;
	public static final int PlaybackServiceHttpsPortNumber = 11030;

	// Backup
	public static final int BackupServicePortNumber = 11028;

	// Camera Http Events Listener
	public static final int CameraHttpEventsListenerPortNumber = 12000;
	public static final int MapServicePortNumber = 11029;
	public static final int MapUploaderDownloadPortNumber = 11031;

	public static final int MobileServiceHttpPortNumber = 9001;
	public static final int MobileServiceResolverPort = 9002;
	public static final int MobileCommunicationPortNumber = 9000;

	public static final int MobileVideoPushStartPort = 8800;
	public static final int MobileVideoPushEndPort = 8996;

	public static final int WebClientServerPort = 8081;
	public static final int WebClientServerHttpsPort = 8082;
	public static final int WebVideoServerPort = 9025;
	public static final int WebVideoServerHttpsPort = 9027;

	public static final int RVSExternalServicePort = 9003;
	public static final int RVSInternalServicePort = 9004;
	public static final int RVSMonitoringServicePort = 9005;
	public static final int RVSDiagnosticsServicePort = 9006;
	public static final int RVSCameraMonitoringServicePort = 9007;
	public static final int RVSRecordingServicePort = 9008;
	public static final int RVSCameraServicePort = 9009;
	public static final int RVSMotionDetectionServicePort = 9011;
	public static final int RVSStreamingServicePort = 9012;
	// 9013, 9014, 9015, 9016, 9017, 9018, 9019, 9020 (all reserved)

	public static final int RVSUpdaterPort = 9010; // used in updater

	public static final int FailoverFileTransferServerPort = 9021;

	public static final int DataProxyPort = 9022;
	public static final int MobileUploadPort = 9023;

	public static final int RVSMulticastStreamerServicePort = 9026;


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Other Properties
	/** 
	 "http://{0}:" + TWebApiFileTransferServer.PlaybackServicePort + "/playback/{1}"
	*/
	public static String getPlaybackUrlTemplate()
	{
		return "http://{0}:" + PlaybackServicePortNumber + "/playback/{1}";
	}

	public static String RVSExternalWebServiceUrl(String ip)
	{
		return String.format("http://%1$s:%2$s/rvsexnet", ip, RVSExternalServicePort);
	}

	public static String RVSMonitoringWebServiceUrl(String ip)
	{
		return String.format("http://%1$s:%2$s/rvsmonitor", ip, RVSMonitoringServicePort);
	}

	public static String RVSMulticastStreamerWebServiceUrl(String ip)
	{
		return String.format("http://%1$s:%2$s/rvsmcast", ip, RVSMulticastStreamerServicePort);
	}

	public static String MobileServiceUrl(String serverAddress)
	{
		return String.format("http://%1$s:%2$s/WcfMobileService", serverAddress, RVSPortConstants.MobileServiceHttpPortNumber);
	}
	public static String WebVideoServerUrl(String serverAddress)
	{
		return String.format("http://%1$s:%2$s", serverAddress, RVSPortConstants.WebVideoServerPort);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}