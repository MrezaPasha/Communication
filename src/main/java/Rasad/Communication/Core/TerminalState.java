package main.java.Rasad.Communication.Core;

public enum TerminalState
{
	Stopped,
	Stopping,
	Connected,
	Connecting;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static TerminalState forValue(int value)
	{
		return values()[value];
	}
}