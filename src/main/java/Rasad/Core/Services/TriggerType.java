package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class TriggerType
{
	public static final TriggerType FiringTrigger = new TriggerType(1);
	public static final TriggerType StateTrigger = new TriggerType(2);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, TriggerType> mappings;
	private static java.util.HashMap<Integer, TriggerType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (TriggerType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, TriggerType>();
				}
			}
		}
		return mappings;
	}

	private TriggerType(int value)
	{
		intValue = value;
		synchronized (TriggerType.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static TriggerType forValue(int value)
	{
		synchronized (TriggerType.class)
		{
			TriggerType enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new TriggerType(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}