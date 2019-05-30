package main.java.Rasad.Core.Services.NotificationService.Public;



// never change values because used in logging system
public enum SystemEntity 
{
	Other(0),

	CommunicationService(1),
	MainService(2),

	// Recorder
	RecordingService(3),
	RecorderHost(4),

	PlaybackService(5),
	PresetTourSevice(6),

	// Motion Detection
	MotionDetectionService(7),
	MotionDetectorHost(8),

	// Streamer
	StreamingService(9),
	StreamerHost(10),

	CameraMonitoringService(11),
	EventTriggerService(12),
	Client(13),
	Debugger(14),
	Plugin(15),
	LicenseService(16),

	IOService(17),
	Bootstrap(18),

	LogService(19),
	MobileService(20),

	CameraService(21),
	CameraHost(22),
	AdministratorClient(23),

	FederatedServer(24),

	MobileDevice(25),
	FailoverService(26),
	MapService(27),
	DataProxy(28),

	PluginHost(29),
	AccessControlService(30),

	MotionDetectionProcessor(31);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, SystemEntity> mappings;
	private static java.util.HashMap<Integer, SystemEntity> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SystemEntity.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SystemEntity>();
				}
			}
		}
		return mappings;
	}

	private SystemEntity(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SystemEntity forValue(int value)
	{
		return getMappings().get(value);
	}
}