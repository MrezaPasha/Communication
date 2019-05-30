package main.java.Rasad.Core.ActiveDirectory.ADPicker;

import Rasad.Core.*;

public class DirectoryObjectTypes
{
	public static final DirectoryObjectTypes None = new DirectoryObjectTypes(0);
	public static final DirectoryObjectTypes Users = new DirectoryObjectTypes(1);
	public static final DirectoryObjectTypes Groups = new DirectoryObjectTypes(2);
	public static final DirectoryObjectTypes Computers = new DirectoryObjectTypes(4);
	public static final DirectoryObjectTypes Contacts = new DirectoryObjectTypes(8);
	public static final DirectoryObjectTypes BuiltInGroups = new DirectoryObjectTypes(16); // 0x00000010
	public static final DirectoryObjectTypes WellKnownPrincipals = new DirectoryObjectTypes(32); // 0x00000020
	public static final DirectoryObjectTypes All = new DirectoryObjectTypes(32 | 16 | 8 | 4 | 2 | 1); // 0x0000003F

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, DirectoryObjectTypes> mappings;
	private static java.util.HashMap<Integer, DirectoryObjectTypes> getMappings()
	{
		if (mappings == null)
		{
			synchronized (DirectoryObjectTypes.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, DirectoryObjectTypes>();
				}
			}
		}
		return mappings;
	}

	private DirectoryObjectTypes(int value)
	{
		intValue = value;
		synchronized (DirectoryObjectTypes.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static DirectoryObjectTypes forValue(int value)
	{
		synchronized (DirectoryObjectTypes.class)
		{
			DirectoryObjectTypes enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new DirectoryObjectTypes(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}