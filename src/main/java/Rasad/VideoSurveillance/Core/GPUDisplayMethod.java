package Rasad.VideoSurveillance.Core;

public enum GPUDisplayMethod
{
	Method1UseSurfaces(0),
	Method2UseDevices(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, GPUDisplayMethod> mappings;
	private static java.util.HashMap<Integer, GPUDisplayMethod> getMappings()
	{
		if (mappings == null)
		{
			synchronized (GPUDisplayMethod.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, GPUDisplayMethod>();
				}
			}
		}
		return mappings;
	}

	private GPUDisplayMethod(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static GPUDisplayMethod forValue(int value)
	{
		return getMappings().get(value);
	}
}