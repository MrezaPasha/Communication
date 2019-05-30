package main.java.Rasad.Core.Interprocess.SharedMemory;

import Rasad.Core.*;

public enum SharedMemoryMode
{
	Server,
	Client;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static SharedMemoryMode forValue(int value)
	{
		return values()[value];
	}
}