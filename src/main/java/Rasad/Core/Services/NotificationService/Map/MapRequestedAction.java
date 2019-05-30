package main.java.Rasad.Core.Services.NotificationService.Map;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;

public class MapRequestedAction
{
	public static final MapRequestedAction None = new MapRequestedAction(0);

	public static final MapRequestedAction CleanupUploadedData = new MapRequestedAction(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, MapRequestedAction> mappings;
	private static java.util.HashMap<Integer, MapRequestedAction> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MapRequestedAction.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, MapRequestedAction>();
				}
			}
		}
		return mappings;
	}

	private MapRequestedAction(int value)
	{
		intValue = value;
		synchronized (MapRequestedAction.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static MapRequestedAction forValue(int value)
	{
		synchronized (MapRequestedAction.class)
		{
			MapRequestedAction enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new MapRequestedAction(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}