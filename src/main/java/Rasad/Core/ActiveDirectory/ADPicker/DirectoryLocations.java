package main.java.Rasad.Core.ActiveDirectory.ADPicker;

import Rasad.Core.*;

public class DirectoryLocations
{
	public static final DirectoryLocations None = new DirectoryLocations(0);
	public static final DirectoryLocations LocalComputer = new DirectoryLocations(1);
	public static final DirectoryLocations JoinedDomain = new DirectoryLocations(2);
	public static final DirectoryLocations EnterpriseDomain = new DirectoryLocations(4);
	public static final DirectoryLocations GlobalCatalog = new DirectoryLocations(8);
	public static final DirectoryLocations ExternalDomain = new DirectoryLocations(16); // 0x00000010
	public static final DirectoryLocations Workgroup = new DirectoryLocations(32); // 0x00000020
	public static final DirectoryLocations UserEntered = new DirectoryLocations(64); // 0x00000040
	public static final DirectoryLocations All = new DirectoryLocations(64 | 32 | 16 | 8 | 4 | 2 | 1); // 0x0000007F

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, DirectoryLocations> mappings;
	private static java.util.HashMap<Integer, DirectoryLocations> getMappings()
	{
		if (mappings == null)
		{
			synchronized (DirectoryLocations.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, DirectoryLocations>();
				}
			}
		}
		return mappings;
	}

	private DirectoryLocations(int value)
	{
		intValue = value;
		synchronized (DirectoryLocations.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static DirectoryLocations forValue(int value)
	{
		synchronized (DirectoryLocations.class)
		{
			DirectoryLocations enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new DirectoryLocations(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}