package Rasad.VideoSurveillance.Core;

public enum PluginRequiredService
{
	CameraAssignmentEditor;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static PluginRequiredService forValue(int value)
	{
		return values()[value];
	}
}