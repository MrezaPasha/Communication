package main.java.Rasad.Core;

public enum CameraStreamRenderMode
{
	GPU(0),
	CPU(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, CameraStreamRenderMode> mappings;
	private static java.util.HashMap<Integer, CameraStreamRenderMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CameraStreamRenderMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, CameraStreamRenderMode>();
				}
			}
		}
		return mappings;
	}

	private CameraStreamRenderMode(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static CameraStreamRenderMode forValue(int value)
	{
		return getMappings().get(value);
	}
}