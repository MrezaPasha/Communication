package main.java.Rasad.Core.Common;

import Rasad.Core.*;

public enum TaskWaitResult
{
	Successful,
	TimedOut,
	Cancelled;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static TaskWaitResult forValue(int value)
	{
		return values()[value];
	}
}