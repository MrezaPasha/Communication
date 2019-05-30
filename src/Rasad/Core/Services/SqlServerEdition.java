package Rasad.Core.Services;

import Rasad.Core.*;

public enum SqlServerEdition
{
	Enterprise(1804890536),
	EnterpriseEditionCore_basedLicensing(1872460670),
	EnterpriseEvaluation(610778273),
	BusinessIntelligence(284895786),
	Developer(-2117995310),
	Express(-1592396055),
	ExpressWithAdvancedServices(-133711905),
	Standard(-1534726760),
	Web(1293598313),
	SQL_Database(1674378470);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, SqlServerEdition> mappings;
	private static java.util.HashMap<Integer, SqlServerEdition> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SqlServerEdition.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SqlServerEdition>();
				}
			}
		}
		return mappings;
	}

	private SqlServerEdition(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SqlServerEdition forValue(int value)
	{
		return getMappings().get(value);
	}
}