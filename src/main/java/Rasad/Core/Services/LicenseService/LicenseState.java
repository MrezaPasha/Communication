package Rasad.Core.Services.LicenseService;

import Rasad.Core.*;
import Rasad.Core.Services.*;

public enum LicenseState
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("غیر فعال")] Inactive = 0,
	Inactive(0),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("فعال")] Active = 1,
	Active(1),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("آماده نصب")] ReadyToInstall = 2,
	ReadyToInstall(2),
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("در حال نصب")] Installing = 3
	Installing(3);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, LicenseState> mappings;
	private static java.util.HashMap<Integer, LicenseState> getMappings()
	{
		if (mappings == null)
		{
			synchronized (LicenseState.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, LicenseState>();
				}
			}
		}
		return mappings;
	}

	private LicenseState(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static LicenseState forValue(int value)
	{
		return getMappings().get(value);
	}
}