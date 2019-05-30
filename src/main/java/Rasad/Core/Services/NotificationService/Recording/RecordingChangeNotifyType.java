package main.java.Rasad.Core.Services.NotificationService.Recording;



//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum RecordingChangeNotifyType : byte
public enum RecordingChangeNotifyType 
{
	Add((byte)0),
	Delete((byte)1);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, RecordingChangeNotifyType> mappings;
	private static java.util.HashMap<Byte, RecordingChangeNotifyType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RecordingChangeNotifyType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, RecordingChangeNotifyType>();
				}
			}
		}
		return mappings;
	}

	private RecordingChangeNotifyType(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static RecordingChangeNotifyType forValue(byte value)
	{
		return getMappings().get(value);
	}
}