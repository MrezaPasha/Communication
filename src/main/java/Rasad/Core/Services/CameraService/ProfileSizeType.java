package Rasad.Core.Services.CameraService;

import Rasad.Core.*;
import Rasad.Core.Services.*;

public enum ProfileSizeType
{
	Large,
	Medium,
	Small;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ProfileSizeType forValue(int value)
	{
		return values()[value];
	}
}