package main.java.Rasad.Core.Interprocess.SharedMemory;

import Rasad.Core.*;

public enum SharedMemoryChannel
{
	Receiver,
	Sender;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static SharedMemoryChannel forValue(int value)
	{
		return values()[value];
	}
}