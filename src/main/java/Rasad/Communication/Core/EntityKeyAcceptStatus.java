package main.java.Rasad.Communication.Core;

public enum EntityKeyAcceptStatus
{
	Accepted(0),
	DuplicateKey(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, EntityKeyAcceptStatus> mappings;
	private static java.util.HashMap<Integer, EntityKeyAcceptStatus> getMappings()
	{
		if (mappings == null)
		{
			synchronized (EntityKeyAcceptStatus.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, EntityKeyAcceptStatus>();
				}
			}
		}
		return mappings;
	}

	private EntityKeyAcceptStatus(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static EntityKeyAcceptStatus forValue(int value)
	{
		return getMappings().get(value);
	}
}