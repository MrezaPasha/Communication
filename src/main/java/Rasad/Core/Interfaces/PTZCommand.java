package Rasad.Core.Interfaces;

import Rasad.Core.*;

public enum PTZCommand
{
	None,

	ContinuesZoom,
	RelativeZoom,
	AreaZoom,
	EndContinuesZoom,

	ContinuesMove,
	EndContinuesMove,
	MoveCenter,

	GotoPreset,
	GotoHome,
	GotoAbsolutePosition,
	RelativeMove,
	SuspendServer;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static PTZCommand forValue(int value)
	{
		return values()[value];
	}
}