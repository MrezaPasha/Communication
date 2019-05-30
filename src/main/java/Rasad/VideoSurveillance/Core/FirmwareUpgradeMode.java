package Rasad.VideoSurveillance.Core;

public enum FirmwareUpgradeMode
{
	Normal(0),
	FactoryDefault(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, FirmwareUpgradeMode> mappings;
	private static java.util.HashMap<Integer, FirmwareUpgradeMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (FirmwareUpgradeMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, FirmwareUpgradeMode>();
				}
			}
		}
		return mappings;
	}

	private FirmwareUpgradeMode(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static FirmwareUpgradeMode forValue(int value)
	{
		return getMappings().get(value);
	}
}