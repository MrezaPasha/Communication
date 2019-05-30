package main.java.Rasad.Core.ActiveDirectory.ADPicker;

import Rasad.Core.*;

public enum ADsPathType
{
	NT4(2),
	Ldap(4),
	Sid(16); // 0x00000010

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ADsPathType> mappings;
	private static java.util.HashMap<Integer, ADsPathType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ADsPathType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ADsPathType>();
				}
			}
		}
		return mappings;
	}

	private ADsPathType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ADsPathType forValue(int value)
	{
		return getMappings().get(value);
	}
}