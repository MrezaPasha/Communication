package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public enum PlayerState
{
	Stopped(1),
	Downloading(2),
	Playing(3);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, PlayerState> mappings;
	private static java.util.HashMap<Integer, PlayerState> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PlayerState.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, PlayerState>();
				}
			}
		}
		return mappings;
	}

	private PlayerState(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static PlayerState forValue(int value)
	{
		return getMappings().get(value);
	}
}