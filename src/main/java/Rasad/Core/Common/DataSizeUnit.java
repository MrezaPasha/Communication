package main.java.Rasad.Core.Common;


public enum DataSizeUnit
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Byte,
	Byte,
	KByte,
	MByte,
	GByte;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static DataSizeUnit forValue(int value)
	{
		return values()[value];
	}
}