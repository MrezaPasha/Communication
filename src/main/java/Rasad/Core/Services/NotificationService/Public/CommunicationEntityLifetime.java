package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;

public enum CommunicationEntityLifetime
{
	Temporary,
	Permanent;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static CommunicationEntityLifetime forValue(int value)
	{
		return values()[value];
	}
}