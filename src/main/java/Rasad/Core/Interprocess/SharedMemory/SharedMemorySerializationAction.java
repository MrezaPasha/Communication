package main.java.Rasad.Core.Interprocess.SharedMemory;


public enum SharedMemorySerializationAction
{
	Serialize,
	Deserialize;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static SharedMemorySerializationAction forValue(int value)
	{
		return values()[value];
	}
}
