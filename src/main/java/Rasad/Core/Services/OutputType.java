package main.java.Rasad.Core.Services;

import Rasad.Core.*;

public enum OutputType
{
	StringType,
	ImageType,
	NumberType,
	CameraType;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static OutputType forValue(int value)
	{
		return values()[value];
	}
}