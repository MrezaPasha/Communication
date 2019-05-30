package Rasad.VideoSurveillance.Core.Helpers;

import Rasad.VideoSurveillance.Core.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;

public enum ActivityMonitorItem 
{
	Alive(0);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ActivityMonitorItem> mappings;
	private static java.util.HashMap<Integer, ActivityMonitorItem> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ActivityMonitorItem.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ActivityMonitorItem>();
				}
			}
		}
		return mappings;
	}

	private ActivityMonitorItem(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ActivityMonitorItem forValue(int value)
	{
		return getMappings().get(value);
	}
}