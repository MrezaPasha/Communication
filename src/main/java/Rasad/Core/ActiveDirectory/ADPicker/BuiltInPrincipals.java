package main.java.Rasad.Core.ActiveDirectory.ADPicker;

import Rasad.Core.*;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] public enum BuiltInPrincipals : uint
public class BuiltInPrincipals 
{
	public static final BuiltInPrincipals Users = new BuiltInPrincipals(Integer.parseInt("2147483649")); // 0x80000001
	public static final BuiltInPrincipals LocalGroups = new BuiltInPrincipals(Integer.parseInt("2147483650")); // 0x80000002
	public static final BuiltInPrincipals GlobalGroups = new BuiltInPrincipals(Integer.parseInt("2147483650")); // 0x80000004
	public static final BuiltInPrincipals Computers = new BuiltInPrincipals(Integer.parseInt("2147483650")); // 0x80000008
	public static final BuiltInPrincipals World = new BuiltInPrincipals(Integer.parseInt("2147483650")); // 0x80000010
	public static final BuiltInPrincipals AuthenticatedUser = new BuiltInPrincipals(Integer.parseInt("2147483650")); // 0x80000020
	public static final BuiltInPrincipals Anonymous = new BuiltInPrincipals(Integer.parseInt("2147483650")); // 0x80000040
	public static final BuiltInPrincipals Batch = new BuiltInPrincipals(Integer.parseInt("2147483650")); // 0x80000080
	public static final BuiltInPrincipals CreatorOwner = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80000100
	public static final BuiltInPrincipals CreatorGroup = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80000200
	public static final BuiltInPrincipals Dialup = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80000400
	public static final BuiltInPrincipals Interactive = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80000800
	public static final BuiltInPrincipals Network = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80001000
	public static final BuiltInPrincipals Service = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80002000
	public static final BuiltInPrincipals System = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80004000
	public static final BuiltInPrincipals ExcludeBuiltinGroups = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80008000
	public static final BuiltInPrincipals TerminalServer = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80010000
	public static final BuiltInPrincipals AllWellknownSids = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80020000
	public static final BuiltInPrincipals LocalService = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80040000
	public static final BuiltInPrincipals NetworkService = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80080000
	public static final BuiltInPrincipals RemoteLogon = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80100000
	public static final BuiltInPrincipals InternetUser = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80200000
	public static final BuiltInPrincipals OwnerRights = new BuiltInPrincipals(Integer.parseInt("2147483904")); // 0x80400000
	public static final BuiltInPrincipals Services = new BuiltInPrincipals(Integer.parseInt("2155872256")); // 0x80800000

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, BuiltInPrincipals> mappings;
	private static java.util.HashMap<Integer, BuiltInPrincipals> getMappings()
	{
		if (mappings == null)
		{
			synchronized (BuiltInPrincipals.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, BuiltInPrincipals>();
				}
			}
		}
		return mappings;
	}

	private BuiltInPrincipals(int value)
	{
		intValue = value;
		synchronized (BuiltInPrincipals.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static BuiltInPrincipals forValue(int value)
	{
		synchronized (BuiltInPrincipals.class)
		{
			BuiltInPrincipals enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new BuiltInPrincipals(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}