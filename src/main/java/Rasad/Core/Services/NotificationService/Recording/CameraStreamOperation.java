package main.java.Rasad.Core.Services.NotificationService.Recording;



//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum CameraStreamOperation : byte
public enum CameraStreamOperation 
{
	CameraIsStoppedStopAllReceiversNormally((byte)0);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, CameraStreamOperation> mappings;
	private static java.util.HashMap<Byte, CameraStreamOperation> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraStreamOperation.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, CameraStreamOperation>();
				}
			}
		}
		return mappings;
	}

	private CameraStreamOperation(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static CameraStreamOperation forValue(byte value)
	{
		return getMappings().get(value);
	}
}