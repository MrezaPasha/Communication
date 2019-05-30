package Rasad.VideoSurveillance.Core;

public enum LPREngineType
{
	Normal,
	ITS;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static LPREngineType forValue(int value)
	{
		return values()[value];
	}
}