package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public enum TransportType
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Udp = 0,
	Udp(0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EnumMember] Tcp = 1,
	Tcp(1);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, TransportType> mappings;
	private static java.util.HashMap<Integer, TransportType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (TransportType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, TransportType>();
				}
			}
		}
		return mappings;
	}

	private TransportType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static TransportType forValue(int value)
	{
		return getMappings().get(value);
	}
}