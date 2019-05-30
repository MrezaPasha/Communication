package main.java.Rasad.Core.Services;

import Rasad.Core.*;

public enum LoginResult
{
	Success,
	UserNotHaveRole,
	UserIsLocked,
	ActiveRoleNotSelected,
	PasswordIsInvalid,
	ConnectionFailed,
	AccessDeniedByAddress,
	AccessDeniedByTime;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static LoginResult forValue(int value)
	{
		return values()[value];
	}
}