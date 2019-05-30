package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum MediaDatabaseInitializationStep : byte
public enum MediaDatabaseInitializationStep 
{
	NotInitialized((byte)0),
	StorageIDAssigned((byte)1),
	StorageReady((byte)2);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, MediaDatabaseInitializationStep> mappings;
	private static java.util.HashMap<Byte, MediaDatabaseInitializationStep> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MediaDatabaseInitializationStep.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, MediaDatabaseInitializationStep>();
				}
			}
		}
		return mappings;
	}

	private MediaDatabaseInitializationStep(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static MediaDatabaseInitializationStep forValue(byte value)
	{
		return getMappings().get(value);
	}
}