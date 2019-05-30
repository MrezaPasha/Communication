package main.java.Rasad.Communication.Core;

public enum CommunicationEntityLifetime
{
	Temporary(0),
	Permanent(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, CommunicationEntityLifetime> mappings;
	private static java.util.HashMap<Integer, CommunicationEntityLifetime> getMappings()
	{
		if (mappings == null)
		{
			synchronized (CommunicationEntityLifetime.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, CommunicationEntityLifetime>();
				}
			}
		}
		return mappings;
	}

	private CommunicationEntityLifetime(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static CommunicationEntityLifetime forValue(int value)
	{
		return getMappings().get(value);
	}
}