package Rasad.VideoSurveillance.Core;

public enum FishEyeCameraLocation
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("دیوار")] Wall,
	Wall,
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("سقف")] Ceiling,
	Ceiling;
	//[Description("کف")]
	//Floor

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static FishEyeCameraLocation forValue(int value)
	{
		return values()[value];
	}
}