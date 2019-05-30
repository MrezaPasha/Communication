package main.java.Rasad.Core.ActiveDirectory;

import java.util.*;

public enum SIDType
{
	SidTypeUser(1),
	SidTypeGroup(2),
	SidTypeDomain(3),
	SidTypeAlias(4),
	SidTypeWellKnownGroup(5),
	SidTypeDeletedAccount(6),
	SidTypeInvalid(7),
	SidTypeUnknown(8),
	SidTypeComputer(9);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, SIDType> mappings;
	private static java.util.HashMap<Integer, SIDType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SIDType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SIDType>();
				}
			}
		}
		return mappings;
	}

	private SIDType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SIDType forValue(int value)
	{
		return getMappings().get(value);
	}
}