package main.java.Rasad.Core;

public enum EditorState
{
	View,
	Insert,
	Edit;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static EditorState forValue(int value)
	{
		return values()[value];
	}
}