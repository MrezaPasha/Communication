package Rasad.VideoSurveillance.Core;

public enum CameraMainOperation
{
	View,
	Playback,
	PTZ;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static CameraMainOperation forValue(int value)
	{
		return values()[value];
	}
}