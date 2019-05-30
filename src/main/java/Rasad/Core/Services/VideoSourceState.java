package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public enum VideoSourceState
{
	Stopped(0),
	Initializing(1),
	Stopping(2),
	Playing(3); // first frame must be show before setting to this play state because of wpf bindings in the code !!!

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, VideoSourceState> mappings;
	private static java.util.HashMap<Integer, VideoSourceState> getMappings()
	{
		if (mappings == null)
		{
			synchronized (VideoSourceState.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, VideoSourceState>();
				}
			}
		}
		return mappings;
	}

	private VideoSourceState(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static VideoSourceState forValue(int value)
	{
		return getMappings().get(value);
	}
}