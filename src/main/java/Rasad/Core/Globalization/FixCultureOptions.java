package Rasad.Core.Globalization;

import Rasad.Core.Extensions.*;
import Rasad.Core.*;
import java.time.*;

public class FixCultureOptions
{
	/** 
		 If set Calendar property of culture will be set to PersianCalendar.
	*/
	public static final FixCultureOptions foptCalendarInCulture = new FixCultureOptions(2);

	/** 
		 If set Calendar of DateFormatInfo will be set to PersianCalendar
	*/
	public static final FixCultureOptions foptCalendarInDateFormatInfo = new FixCultureOptions(4);

	/** 
		 If set the first element of OptionalCalendars will be set to PersianCalendar
	*/
	public static final FixCultureOptions foptOptionalCalendars = new FixCultureOptions(8);

	/** 
		 All fix ups will applied.
	*/
	public static final FixCultureOptions foptAll = new FixCultureOptions(16);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, FixCultureOptions> mappings;
	private static java.util.HashMap<Integer, FixCultureOptions> getMappings()
	{
		if (mappings == null)
		{
			synchronized (FixCultureOptions.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, FixCultureOptions>();
				}
			}
		}
		return mappings;
	}

	private FixCultureOptions(int value)
	{
		intValue = value;
		synchronized (FixCultureOptions.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static FixCultureOptions forValue(int value)
	{
		synchronized (FixCultureOptions.class)
		{
			FixCultureOptions enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new FixCultureOptions(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}