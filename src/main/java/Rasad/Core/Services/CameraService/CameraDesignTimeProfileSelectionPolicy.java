package main.java.Rasad.Core.Services.CameraService;

import Rasad.Core.*;
import Rasad.Core.Services.*;

public enum CameraDesignTimeProfileSelectionPolicy
{
	PreferCamera,
	PreferStreamer;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static CameraDesignTimeProfileSelectionPolicy forValue(int value)
	{
		return values()[value];
	}
}