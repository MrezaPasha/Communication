package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class ODMStreamMode
{
	public static final ODMStreamMode Video = new ODMStreamMode(1);
	public static final ODMStreamMode Audio = new ODMStreamMode(2);
	//TODO MRREPLACE
	public static final ODMStreamMode VideoAudio = new ODMStreamMode(ODMStreamMode.Video.intValue  | ODMStreamMode.Audio.intValue);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ODMStreamMode> mappings;
	private static java.util.HashMap<Integer, ODMStreamMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ODMStreamMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ODMStreamMode>();
				}
			}
		}
		return mappings;
	}

	private ODMStreamMode(int value)
	{
		intValue = value;
		synchronized (ODMStreamMode.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static ODMStreamMode forValue(int value)
	{
		synchronized (ODMStreamMode.class)
		{
			ODMStreamMode enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new ODMStreamMode(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}