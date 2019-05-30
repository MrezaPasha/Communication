package Rasad.Core.Services.CameraService;

import Rasad.Core.*;
import Rasad.Core.Services.*;

public enum CameraProfileAccessPolicy
{
	AsDefined,
	ForceCamera,
	ForceStreamer,
	FirstStreamerThenCamera;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static CameraProfileAccessPolicy forValue(int value)
	{
		return values()[value];
	}
}