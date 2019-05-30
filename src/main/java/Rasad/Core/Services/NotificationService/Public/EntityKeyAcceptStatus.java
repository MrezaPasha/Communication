package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;

public enum EntityKeyAcceptStatus
{
	Accepted,
	DuplicateKey;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static EntityKeyAcceptStatus forValue(int value)
	{
		return values()[value];
	}
}