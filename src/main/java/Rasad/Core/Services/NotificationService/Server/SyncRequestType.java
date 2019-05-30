package main.java.Rasad.Core.Services.NotificationService.Server;


public enum SyncRequestType
{
	Failover,
	Federation;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static SyncRequestType forValue(int value)
	{
		return values()[value];
	}
}