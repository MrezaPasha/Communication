package Rasad.Core.Globalization;

import Rasad.Core.Globalization.Exceptions.*;
import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

  </code>
		 You can alternatively create a specified date/time based on specific <c>DateTime</c> value. To do this
		 you need to use
		 <value>PersianDateConverter</value>
		 class.
	 </example>
	 {@link PersianDateConverter }
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [TypeConverter(typeof(TDateTimeTypeConverter))][Serializable] public sealed class TDateTime : IFormattable, ICloneable, IComparable, IComparable<TDateTime>, IComparer, IComparer<TDateTime>, IEquatable<TDateTime>
public final class TDateTime implements IFormattable, Cloneable, java.lang.Comparable, java.lang.Comparable<TDateTime>, Comparator, Comparator<TDateTime>, IEquatable<TDateTime>, Serializable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Month Names

	public static class PersianMonthNames
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Fields

		private static PersianMonthNames def;
		public String Aban = "آبان";
		public String Azar = "آذر";
		public String Bahman = "بهمن";
		public String Day = "دی";
		public String Esfand = "اسفند";
		public String Farvardin = "فروردین";
		public String Khordad = "خرداد";
		public String Mehr = "مهر";
		public String Mordad = "مرداد";
		public String Ordibehesht = "ارديبهشت";
		public String Shahrivar = "شهریور";
		public String Tir = "تير";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Ctor

		static
		{
			def = new PersianMonthNames();
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Indexer

		public static PersianMonthNames getDefault()
		{
			return def;
		}

		public final String get(int month)
		{
			return GetName(month);
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Methods

		private String GetName(int monthNo)
		{
			switch (monthNo)
			{
				case 1:
					return Farvardin;

				case 2:
					return Ordibehesht;

				case 3:
					return Khordad;

				case 4:
					return Tir;

				case 5:
					return Mordad;

				case 6:
					return Shahrivar;

				case 7:
					return Mehr;

				case 8:
					return Aban;

				case 9:
					return Azar;

				case 10:
					return Day;

				case 11:
					return Bahman;

				case 12:
					return Esfand;

				default:
					throw new IndexOutOfBoundsException("Month value " + monthNo + " is out of range");
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region WeekDay Names

	public static class PersianWeekDayNames
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region fields

		private static PersianWeekDayNames def;
		public String Chaharshanbeh = "چهارشنبه";
		public String Doshanbeh = "دوشنبه";
		public String Jomeh = "جمعه";
		public String Panjshanbeh = "پنجشنبه";
		public String Seshanbeh = "ﺳﻪشنبه";
		public String Shanbeh = "شنبه";
		public String Yekshanbeh = "یکشنبه";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Ctor

		static
		{
			def = new PersianWeekDayNames();
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Indexer

		public static PersianWeekDayNames getDefault()
		{
			return def;
		}

		public final String get(int day)
		{
			return GetName(day);
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Methods

		private String GetName(int WeekDayNo)
		{
			switch (WeekDayNo)
			{
				case 0:
					return Shanbeh;

				case 1:
					return Yekshanbeh;

				case 2:
					return Doshanbeh;

				case 3:
					return Seshanbeh;

				case 4:
					return Chaharshanbeh;

				case 5:
					return Panjshanbeh;

				case 6:
					return Jomeh;

				default:
					throw new IndexOutOfBoundsException();
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region WeekDay Abbreviation

	public static class PersianWeekDayAbbr
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Fields

		private static PersianWeekDayAbbr def;
		public String Chaharshanbeh = "چ";
		public String Doshanbeh = "د";
		public String Jomeh = "ج";
		public String Panjshanbeh = "پ";
		public String Seshanbeh = "س";
		public String Shanbeh = "ش";
		public String Yekshanbeh = "ی";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Ctor

		static
		{
			def = new PersianWeekDayAbbr();
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Indexer

		public static PersianWeekDayAbbr getDefault()
		{
			return def;
		}

		public final String get(int day)
		{
			return GetName(day);
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Methods

		private String GetName(int WeekDayNo)
		{
			switch (WeekDayNo)
			{
				case 0:
					return Shanbeh;

				case 1:
					return Yekshanbeh;

				case 2:
					return Doshanbeh;

				case 3:
					return Seshanbeh;

				case 4:
					return Chaharshanbeh;

				case 5:
					return Panjshanbeh;

				case 6:
					return Jomeh;

				default:
					throw new IndexOutOfBoundsException("WeekDay number is out of range");
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields

	private static TDateTime now;


	public transient static TDateTime MinValue;

	public transient static TDateTime MaxValue;

	public transient static TDateTime MinValueDateTime;

	public transient static TDateTime MaxValueDateTime;
	private final TPersianCalendar pc = new TPersianCalendar();
	private String amDesignator = "ق.ظ";
	private TDateTime date;
	private int day;
	private int hour;
	private int millisecond;
	private int minute;
	private int month;
	private String pmDesignator = "ب.ظ";
	private int second;
	private TTimeSpan time = new TTimeSpan();
	private int year;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Static Ctor

	/** 
		 Static constructor initializes Now property of PersianDate and Min/Max values.
	*/
	static
	{
		now = TPersianDateConverter.ToPersianDate(LocalDateTime.now());
		MinValue = new TDateTime(1000, 1, 1, 0, 0, 0, 0); // 12:00:00.000 AM, 22/03/0622
		MaxValue = new TDateTime(LocalDateTime.MAX);
		MinValueDateTime = ShDate.ToDateTime(1000, 3, 22);
		MaxValueDateTime = LocalDateTime.MAX;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Props

	/** 
		 AMDesignator.
	*/
	public String getAMDesignator()
	{
		return amDesignator;
	}

	/** 
		 PMDesignator.
	*/
	public String getPMDesignator()
	{
		return pmDesignator;
	}

	/** 
		 Current date/time in PersianDate format.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)][Description("Current date/time in PersianDate format")] public static TDateTime Now
	public static TDateTime getNow()
	{
		return now;
	}

	/** 
		 Year value of PersianDate.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Year value of PersianDate")][NotifyParentProperty(true)] public int Year
	public int getYear()
	{
		return year;
	}
	public void setYear(int value)
	{
		CheckYear(value);
		year = value;
	}

	/** 
		 Month value of PersianDate.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Month value of PersianDate")][NotifyParentProperty(true)] public int Month
	public int getMonth()
	{
		return month;
	}
	public void setMonth(int value)
	{
		CheckMonth(value);
		month = value;
	}

	/** 
		 Day value of PersianDate.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Day value of PersianDate")][NotifyParentProperty(true)] public int Day
	public int getDay()
	{
		return day;
	}
	public void setDay(int value)
	{
		CheckDay(getYear(), getMonth(), value);
		day = value;
	}

	/** 
		 Hour value of PersianDate.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Hour value of PersianDate")][NotifyParentProperty(true)] public int Hour
	public int getHour()
	{
		return hour;
	}
	public void setHour(int value)
	{
		CheckHour(value);
		hour = value;
	}

	/** 
		 Minute value of PersianDate.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Minute value of PersianDate")][NotifyParentProperty(true)] public int Minute
	public int getMinute()
	{
		return minute;
	}
	public void setMinute(int value)
	{
		CheckMinute(value);
		minute = value;
	}

	/** 
		 Second value of PersianDate.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Second value of PersianDate")][NotifyParentProperty(true)] public int Second
	public int getSecond()
	{
		return second;
	}
	public void setSecond(int value)
	{
		CheckSecond(value);
		second = value;
	}

	/** 
		 Millisecond value of PersianDate.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("Millisecond value of PersianDate")][NotifyParentProperty(true)] public int Millisecond
	public int getMillisecond()
	{
		return millisecond;
	}
	public void setMillisecond(int value)
	{
		CheckMillisecond(value);
		millisecond = value;
	}

	/** 
		 Time value of PersianDate in TimeSpan format.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)][Description("Time value of PersianDate in TimeSpan format.")] public TTimeSpan Time
	public TTimeSpan getTime()
	{
		if (Rasad.Core.Globalization.TTimeSpan.OpEquality(time.clone(), TTimeSpan.Zero))
		{
			time = new TTimeSpan(0, hour, minute, second, millisecond);
		}
		return time;
	}

	/** 
		 Date value of PersianDate in TDateTime format.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)][Description("Date value of PersianDate in TDateTime format.")] public TDateTime Date
	public TDateTime getDate()
	{
		if (Rasad.Core.Globalization.TDateTime.OpEquality(date, (Object) null))
		{
			date = new TDateTime(year, month, day);
		}
		return date;
	}

	/** 
		 Returns the DayOfWeek of the date instance
	*/
	public DayOfWeek getDayOfWeek()
	{
		return ((LocalDateTime) this).getDayOfWeek();
	}

	/** 
		 Localized name of PersianDate months.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)][Description("Localized name of PersianDate months")] public string LocalizedMonthName
	public String getLocalizedMonthName()
	{
		return PersianMonthNames.getDefault().get(month);
	}

	/** 
		 Weekday names of this instance in localized format.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)][Description("Weekday names of this instance in localized format.")] public string LocalizedWeekDayName
	public String getLocalizedWeekDayName()
	{
		return TPersianDateConverter.DayOfWeek(this);
	}

	/** 
		 Number of days in this month.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)][Description("Number of days in this month")] public int MonthDays
	public int getMonthDays()
	{
		return TPersianDateConverter.MonthDays(month);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)] public bool IsNull
	public boolean getIsNull()
	{
		return getYear() == MinValue.getYear() && getMonth() == MinValue.getMonth() && getDay() == MinValue.getDay();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor

	public TDateTime(LocalDateTime dt)
	{
		Assign(TPersianDateConverter.ToPersianDate(dt));
	}

	public TDateTime()
	{
		year = getNow().year;
		month = getNow().getMonth();
		day = getNow().getDay();
		hour = getNow().getHour();
		minute = getNow().getMinute();
		second = getNow().getSecond();
		millisecond = getNow().getMillisecond();
		CreateDateTime();
	}

	/** 
		 Constructs a PersianDate instance with values provided in datetime string.
		 use the format you want to parse the string with. Currently it can be either g, G, or d value.
	 
	 @param datetime
	 @param format
	*/
	public TDateTime(String datetime, String format)
	{
		Assign(Parse(datetime, format));
	}

	/** 
		 Constructs a PersianDate instance with values provided in datetime string. You should
		 include Date part only in <c>Date</c> and set the Time of the instance as a <c>TimeSpan</c>.
	 
	 @exception TInvalidPersianDateException
	 @param Date
	 @param time
	*/
	public TDateTime(String Date, TimeSpan time)
	{
		TDateTime pd = Parse(Date);

		pd.setHour(time.Hours);
		pd.setMinute(time.Minutes);
		pd.setSecond(time.Seconds);
		pd.setMillisecond(time.Milliseconds);

		Assign(pd);
	}

	/** 
		 Constructs a PersianDate instance with values provided as a string. The provided string should be in format
		 'yyyy/mm/dd'.
	 
	 @exception TInvalidPersianDateException
	 @param Date
	*/
	public TDateTime(String Date)
	{
		Assign(Parse(Date));
	}

	/** 
		 Constructs a PersianDate instance with values specified as <c>Integer</c> and default second and millisecond set to
		 zero.
	 
	 @param year
	 @param month
	 @param day
	 @param hour
	 @param minute
	*/
	public TDateTime(int year, int month, int day, int hour, int minute)
	{
		this(year, month, day, hour, minute, 0);
	}

	/** 
		 Constructs a PersianDate instance with values specified as <c>Integer</c> and default millisecond set to zero.
	 
	 @param year
	 @param month
	 @param day
	 @param hour
	 @param minute
	 @param second
	*/
	public TDateTime(int year, int month, int day, int hour, int minute, int second)
	{
		this(year, month, day, hour, minute, second, 0);
	}

	/** 
		 Constructs a PersianDate instance with values specified as <c>Integer</c>.
	 
	 @exception TInvalidPersianDateException
	 @param year
	 @param month
	 @param day
	 @param hour
	 @param minute
	 @param second
	*/
	public TDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond)
	{
		CheckYear(year);
		CheckMonth(month);
		CheckDay(year, month, day);
		CheckHour(hour);
		CheckMinute(minute);
		CheckSecond(second);
		CheckMillisecond(millisecond);

		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.millisecond = millisecond;
		CreateDateTime();
	}

	/** 
		 Constructs a PersianDate instance with values specified as <c>Integer</c>. Time value of this instance is set to
		 <c>DateTime.Now</c>.
	 
	 @param year
	 @param month
	 @param day
	*/
	public TDateTime(int year, int month, int day)
	{
		CheckYear(year);
		CheckMonth(month);
		CheckDay(year, month, day);

		this.year = year;
		this.month = month;
		this.day = day;
		hour = 12;
		minute = 0;
		second = 0;
		millisecond = 0;

		CreateDateTime();
	}

	private void CreateDateTime()
	{
		time = TTimeSpan.Zero;
		date = null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Check Methods

	private void CheckYear(int YearNo)
	{
		if (YearNo < 1 || YearNo > 9999)
		{
			throw new TInvalidPersianDateException("مقدار سال صحیح نمیباشد.");
		}
	}

	private void CheckMonth(int MonthNo)
	{
		if (MonthNo > 12 || MonthNo < 1)
		{
			throw new TInvalidPersianDateException("مقدار ماه صحیح نمیباشد.");
		}
	}

	private void CheckDay(int YearNo, int MonthNo, int DayNo)
	{
		if (MonthNo < 6 && DayNo > 31)
		{
			throw new TInvalidPersianDateException("مقدار روز صحیح نمیباشد.");
		}

		if (MonthNo > 6 && DayNo > 30)
		{
			throw new TInvalidPersianDateException("مقدار روز صحیح نمیباشد.");
		}

		if (MonthNo == 12 && DayNo > 29)
		{
			if (!pc.IsLeapDay(YearNo, MonthNo, DayNo) || DayNo > 30)
			{
				throw new TInvalidPersianDateException("مقدار روز صحیح نمیباشد.");
			}
		}
	}

	private void CheckHour(int HourNo)
	{
		if (HourNo > 24 || HourNo < 0)
		{
			throw new TInvalidPersianDateException("مقدار ساعت صحیح نمیباشد.");
		}
	}

	private void CheckMinute(int MinuteNo)
	{
		if (MinuteNo > 60 || MinuteNo < 0)
		{
			throw new TInvalidPersianDateException("مقدار دقیقه صحیح نمیباشد.");
		}
	}

	private void CheckSecond(int SecondNo)
	{
		if (SecondNo > 60 || SecondNo < 0)
		{
			throw new TInvalidPersianDateException("مقدار ثانیه صحیح نمیباشد.");
		}
	}

	private void CheckMillisecond(int MillisecondNo)
	{
		if (MillisecondNo < 0 || MillisecondNo > 1000)
		{
			throw new TInvalidPersianDateException("مقدار ميلي ثانيه صحيح نميباشد");
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods

	public static int Compare(LocalDateTime dt1, LocalDateTime dt2)
	{
		TDateTime tdt1 = new TDateTime(dt1);
		TDateTime tdt2 = new TDateTime(dt2);
		if (Rasad.Core.Globalization.TDateTime.OpEquality(tdt1, tdt2))
		{
			return 0;
		}
		if (Rasad.Core.Globalization.TDateTime.OpGreaterThan(tdt1, tdt2))
		{
			return 1;
		}
		return -1;
	}

	/** 
		 Determines whether the specified year in the current era is a leap year.
	 
	 @return 
	*/
	public boolean IsLeapYear()
	{
		return pc.IsLeapYear(year);
	}

	/** 
		 Assigns an instance of PersianDate's values to this instance.
	 
	 @param pd
	*/
	public void Assign(TDateTime pd)
	{
		setYear(pd.getYear());
		setMonth(pd.getMonth());
		setDay(pd.getDay());
		setHour(pd.getHour());
		setMinute(pd.getMinute());
		setSecond(pd.getSecond());
		setMillisecond(pd.millisecond);
		CreateDateTime();
	}

	/** 
		 Returns a string representation of current PersianDate value.
	 
	 @return 
	*/
	public String ToWritten()
	{
		return (getLocalizedWeekDayName() + " " + day + " " + getLocalizedMonthName() + " " + year);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Parse Methods

	/** 
		 Parse a string value into a PersianDate instance. Value could be either in 'yyyy/mm/dd hh:mm:ss' or 'yyyy/mm/dd'
		 formats. If you want to parse <c>Time</c> value too,
		 you should set <c>includesTime</c> to <c>true</c>.
	 
	 @exception TInvalidPersianDateException
	 @param value
	 @param includesTime
	 @return 
	*/
	public static TDateTime Parse(String value, boolean includesTime)
	{
		if (value.equals(""))
		{
			return MinValue;
		}

		if (includesTime)
		{
			if (value.length() > 19)
			{
				throw new TInvalidPersianDateFormatException("متن وارد شده برای زمان/ساعت صحیح نمیباشد.");
			}

			String[] dt = value.split(java.util.regex.Pattern.quote(" "), -1);

			if (dt.length != 2)
			{
				throw new TInvalidPersianDateFormatException("ساختار تاریخ مجاز نمیباشد.");
			}

			String _date = dt[0];
			String _time = dt[1];

			String[] dateParts = _date.split(java.util.regex.Pattern.quote("/"), -1);
			String[] timeParts = _time.split(java.util.regex.Pattern.quote(":"), -1);

			if (dateParts.length != 3)
			{
				throw new TInvalidPersianDateFormatException("ساختار تاریخ مجاز نمیباشد.");
			}

			if (timeParts.length != 3)
			{
				throw new TInvalidPersianDateFormatException("ساختار زمان صحیح نمیباشد.");
			}

			int day = Integer.parseInt(dateParts[2]);
			int month = Integer.parseInt(dateParts[1]);
			int year = Integer.parseInt(dateParts[0]);
			int hour = Integer.parseInt(timeParts[0]);
			int minute = Integer.parseInt(timeParts[1]);
			int second = Integer.parseInt(timeParts[2]);

			return new TDateTime(year, month, day, hour, minute, second);
		}
		return Parse(value);
	}

	public static TDateTime Parse(String value, String format)
	{
		switch (format)
		{
			case "G": //yyyy/mm/dd hh:mm:ss tt
				return ParseFullDateTime(value);

			case "g": //yyyy/mm/dd hh:mm tt
				return ParseDateShortTime(value);

			case "d": //yyyy/mm/dd
				return Parse(value);

			default:
				throw new IllegalArgumentException("Currently G,g,d formats are supported.");
		}
	}

	/** 
		 Parse a string value into a PersianDate instance. Value should be in 'yyyy/mm/dd hh:mm:ss tt' formats.
	 
	 @exception TInvalidPersianDateException
	 @param value
	 @return 
	*/
	private static TDateTime ParseFullDateTime(String value)
	{
		if (value.equals(""))
		{
			return MinValue;
		}

		if (value.length() > 23)
		{
			throw new TInvalidPersianDateFormatException("متن وارد شده برای زمان/ساعت صحیح نمیباشد.");
		}

		String[] dt = value.split(java.util.regex.Pattern.quote(" "), -1);

		if (dt.length != 3)
		{
			throw new TInvalidPersianDateFormatException("ساختار تاریخ مجاز نمیباشد.");
		}

		String _date = dt[0];
		String _time = dt[1];

		String[] dateParts = _date.split(java.util.regex.Pattern.quote("/"), -1);
		String[] timeParts = _time.split(java.util.regex.Pattern.quote(":"), -1);

		if (dateParts.length != 3)
		{
			throw new TInvalidPersianDateFormatException("ساختار تاریخ مجاز نمیباشد.");
		}

		if (timeParts.length != 3)
		{
			throw new TInvalidPersianDateFormatException("ساختار زمان صحیح نمیباشد.");
		}

		int day = Integer.parseInt(dateParts[2]);
		int month = Integer.parseInt(dateParts[1]);
		int year = Integer.parseInt(dateParts[0]);
		int hour = Integer.parseInt(timeParts[0]);
		int minute = Integer.parseInt(timeParts[1]);
		int second = Integer.parseInt(timeParts[2]);

		return new TDateTime(year, month, day, hour, minute, second, 0);
	}

	/** 
		 Parse a string value into a PersianDate instance. Value should be in 'yyyy/mm/dd hh:mm tt' formats.
	 
	 @exception TInvalidPersianDateException
	 @param value
	 @return 
	*/
	private static TDateTime ParseDateShortTime(String value)
	{
		if (value.equals(""))
		{
			return MinValue;
		}

		if (value.length() > 20)
		{
			throw new TInvalidPersianDateFormatException("متن وارد شده برای زمان/ساعت صحیح نمیباشد.");
		}

		String[] dt = value.split(java.util.regex.Pattern.quote(" "), -1);

		if (dt.length > 3)
		{
			throw new TInvalidPersianDateFormatException("ساختار تاریخ مجاز نمیباشد.");
		}

		String _date = dt[0];
		String _time = dt[1];

		String[] dateParts = _date.split(java.util.regex.Pattern.quote("/"), -1);
		String[] timeParts = _time.split(java.util.regex.Pattern.quote(":"), -1);

		if (dateParts.length != 3)
		{
			throw new TInvalidPersianDateFormatException("ساختار تاریخ مجاز نمیباشد.");
		}

		if (timeParts.length != 2)
		{
			throw new TInvalidPersianDateFormatException("ساختار زمان صحیح نمیباشد.");
		}

		int day = Integer.parseInt(dateParts[2]);
		int month = Integer.parseInt(dateParts[1]);
		int year = Integer.parseInt(dateParts[0]);
		int hour = Integer.parseInt(timeParts[0]);
		int minute = Integer.parseInt(timeParts[1]);

		return new TDateTime(year, month, day, hour, minute, 0, 0);
	}

	/** 
		 Parse a string value into a PersianDate instance. Value can only be in 'yyyy/mm/dd' format.
	 
	 @param value
	 @return 
	*/
	public static TDateTime Parse(String value)
	{
		if (value.length() == 10)
		{
			return ParseShortDate(value);
		}
		if (value.length() == 16)
		{
			return ParseDateShortTime(value);
		}
		if (value.length() == 20)
		{
			return ParseDateShortTime(value);
		}
		if (value.length() == 23)
		{
			return ParseFullDateTime(value);
		}

		throw new TInvalidPersianDateFormatException("Can not parse the value. Format is incorrect.");
	}

	private static TDateTime ParseShortDate(String value)
	{
		if (value.equals(""))
		{
			return MinValue;
		}

		if (value.length() > 10)
		{
			throw new TInvalidPersianDateFormatException("متن وارد شده برای زمان/ساعت صحیح نمیباشد.");
		}

		String[] dateParts = value.split(java.util.regex.Pattern.quote("/"), -1);

		if (dateParts.length != 3)
		{
			throw new TInvalidPersianDateFormatException("ساختار تاریخ مجاز نمیباشد.");
		}

		int day = Integer.parseInt(dateParts[2]);
		int month = Integer.parseInt(dateParts[1]);
		int year = Integer.parseInt(dateParts[0]);

		return new TDateTime(year, month, day);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Overrides

	/** 
		 Returns Date in 'yyyy/mm/dd' string format.
	 
	 @return string representation of evaluated Date.
	 <example>
		 An example on how to get the written form of a Date.
		 <code>
	 		class MyClass {
	 		   public static void Main()
	 		   {	
	 				Console.WriteLine(PersianDate.Now.ToString());
	 		   }
	 		}
	  </code>
	 </example>
	 {@link ToWritten }
	*/
	@Override
	public String toString()
	{
		return toString("g", null);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Operators

	public static boolean OpEquality(TDateTime date1, String date2)
	{
		return Rasad.Core.Globalization.TDateTime.OpEquality(date1, Parse(date2));
	}

	/** 
		 Compares two instance of the PersianDate for the specified operator.
	 
	 @param date1
	 @param date2
	 @return 
	*/
	public static boolean OpEquality(TDateTime date1, TDateTime date2)
	{
		if ((date1 instanceof Object ? (Object)date1 : null) == null && (date2 instanceof Object ? (Object)date2 : null) == null)
		{
			return true;
		}

		if ((date1 instanceof Object ? (Object)date1 : null) == null && (date2 instanceof Object ? (Object)date2 : null) != null)
		{
			return false;
		}

		if ((date2 instanceof Object ? (Object)date2 : null) == null && (date1 instanceof Object ? (Object)date1 : null) != null)
		{
			return false;
		}

		return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() && date1.getHour() == date2.getHour() && date1.getMinute() == date2.getMinute() && date1.getSecond() == date2.getSecond() && date1.getMillisecond() == date2.getMillisecond();
	}

	public static boolean OpInequality(TDateTime date1, String date2)
	{
		return Rasad.Core.Globalization.TDateTime.OpInequality(date1, Parse(date2));
	}

	/** 
		 Compares two instance of the PersianDate for the specified operator.
	 
	 @param date1
	 @param date2
	 @return 
	*/
	public static boolean OpInequality(TDateTime date1, TDateTime date2)
	{
		if ((date1 instanceof Object ? (Object)date1 : null) == null && (date2 instanceof Object ? (Object)date2 : null) == null)
		{
			return false;
		}

		if ((date1 instanceof Object ? (Object)date1 : null) == null && (date2 instanceof Object ? (Object)date2 : null) != null)
		{
			return true;
		}

		if ((date2 instanceof Object ? (Object)date2 : null) == null && (date1 instanceof Object ? (Object)date1 : null) != null)
		{
			return true;
		}

		return date1.getYear() != date2.getYear() || date1.getMonth() != date2.getMonth() || date1.getDay() != date2.getDay() || date1.getHour() != date2.getHour() || date1.getMinute() != date2.getMinute() || date1.getSecond() != date2.getSecond() || date1.getMillisecond() != date2.getMillisecond();
	}

	/** 
		 Compares two instance of the PersianDate for the specified operator.
	 
	 @param date1
	 @param date2
	 @return 
	*/
	public static boolean OpGreaterThan(TDateTime date1, TDateTime date2)
	{
		if ((date1 instanceof Object ? (Object)date1 : null) == null && (date2 instanceof Object ? (Object)date2 : null) == null)
		{
			return false;
		}

		if ((date1 instanceof Object ? (Object)date1 : null) == null && (date2 instanceof Object ? (Object)date2 : null) != null)
		{
			throw new NullPointerException();
		}

		if ((date2 instanceof Object ? (Object)date2 : null) == null && (date1 instanceof Object ? (Object)date1 : null) != null)
		{
			throw new NullPointerException();
		}

		if (date1.getYear() > date2.getYear())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() > date2.getMonth())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() > date2.getDay())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() && date1.getHour() > date2.getHour())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() && date1.getHour() == date2.getHour() && date1.getMinute() > date2.getMinute())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() && date1.getHour() == date2.getHour() && date1.getMinute() == date2.getMinute() && date1.getSecond() > date2.getSecond())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() && date1.getHour() == date2.getHour() && date1.getMinute() == date2.getMinute() && date1.getSecond() == date2.getSecond() && date1.getMillisecond() > date2.getMillisecond())
		{
			return true;
		}

		return false;
	}

	public static boolean OpGreaterThan(TDateTime date1, String date2)
	{
		return Rasad.Core.Globalization.TDateTime.OpGreaterThan(date1, Parse(date2));
	}

	/** 
		 Compares two instance of the PersianDate for the specified operator.
	 
	 @param date1
	 @param date2
	 @return 
	*/
	public static boolean OpLessThan(TDateTime date1, TDateTime date2)
	{
		if ((date1 instanceof Object ? (Object)date1 : null) == null && (date2 instanceof Object ? (Object)date2 : null) == null)
		{
			return false;
		}

		if ((date1 instanceof Object ? (Object)date1 : null) == null && (date2 instanceof Object ? (Object)date2 : null) != null)
		{
			throw new NullPointerException();
		}

		if ((date2 instanceof Object ? (Object)date2 : null) == null && (date1 instanceof Object ? (Object)date1 : null) != null)
		{
			throw new NullPointerException();
		}

		if (date1.getYear() < date2.getYear())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() < date2.getMonth())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() < date2.getDay())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() && date1.getHour() < date2.getHour())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() && date1.getHour() == date2.getHour() && date1.getMinute() < date2.getMinute())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() && date1.getHour() == date2.getHour() && date1.getMinute() == date2.getMinute() && date1.getSecond() < date2.getSecond())
		{
			return true;
		}

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() && date1.getHour() == date2.getHour() && date1.getMinute() == date2.getMinute() && date1.getSecond() == date2.getSecond() && date1.getMillisecond() < date2.getMillisecond())
		{
			return true;
		}

		return false;
	}

	public static boolean OpLessThan(TDateTime date1, String date2)
	{
		return Rasad.Core.Globalization.TDateTime.OpLessThan(date1, Parse(date2));
	}

	/** 
		 Compares two instance of the PersianDate for the specified operator.
	 
	 @param date1
	 @param date2
	 @return 
	*/
	public static boolean OpLessThanOrEqual(TDateTime date1, TDateTime date2)
	{
		return (Rasad.Core.Globalization.TDateTime.OpLessThan(date1, date2)) || Rasad.Core.Globalization.TDateTime.OpEquality(date1, date2);
	}

	public static boolean OpLessThanOrEqual(TDateTime date1, String date2)
	{
		return Rasad.Core.Globalization.TDateTime.OpLessThanOrEqual(date1, Parse(date2));
	}

	/** 
		 Compares two instance of the PersianDate for the specified operator.
	 
	 @param date1
	 @param date2
	 @return 
	*/
	public static boolean OpGreaterThanOrEqual(TDateTime date1, TDateTime date2)
	{
		return (Rasad.Core.Globalization.TDateTime.OpGreaterThan(date1, date2)) || Rasad.Core.Globalization.TDateTime.OpEquality(date1, date2);
	}

	public static boolean OpGreaterThanOrEqual(TDateTime date1, String date2)
	{
		return Rasad.Core.Globalization.TDateTime.OpGreaterThanOrEqual(date1, Parse(date2));
	}

	public static TDateTime OpSubtraction(TDateTime date1, TTimeSpan timeDiff)
	{
		return ((LocalDateTime) date1).Subtract(timeDiff.clone());
	}

	public static TTimeSpan OpSubtraction(TDateTime date1, TDateTime date2)
	{
		return (TPersianDateConverter.ToGregorianDateTime(date1) - TPersianDateConverter.ToGregorianDateTime(date2));
	}

	public static TTimeSpan OpSubtraction(TDateTime date1, String date2)
	{
		return (TPersianDateConverter.ToGregorianDateTime(date1) - TPersianDateConverter.ToGregorianDateTime(date2));
	}

	public static TDateTime OpSubtraction(TDateTime date1, int days)
	{
		return Rasad.Core.Globalization.TDateTime.OpSubtraction(date1, new TTimeSpan(days, 0, 0, 0));
	}

	public static TDateTime OpDecrement(TDateTime t)
	{
		return t = Rasad.Core.Globalization.TDateTime.OpSubtraction(t, 1);
	}

	public static TDateTime OpAddition(TDateTime date1, int days)
	{
		return Rasad.Core.Globalization.TDateTime.OpAddition(date1, new TTimeSpan(days, 0, 0, 0));
	}

	public static TDateTime OpAddition(TDateTime date1, TTimeSpan timeSpan)
	{
		return ((LocalDateTime) date1).Add(timeSpan.clone());
	}

	public static TDateTime OpAddition(TDateTime date1, String timeSpan)
	{
		return Rasad.Core.Globalization.TDateTime.OpAddition(date1, TimeSpan.Parse(timeSpan));
	}

	/** 
		 يک روز به روز جاري اضافه مي کند
	 
	 @param t
	 @return 
	*/
	public static TDateTime OpIncrement(TDateTime t)
	{
		return t = Rasad.Core.Globalization.TDateTime.OpAddition(t, 1);
	}


	/** 
		 Serves as a hash function for a particular type. System.Object.GetHashCode() is suitable for use in hashing
		 algorithms and data structures like a hash table.
	 
	 @return 
	*/
	@Override
	public int hashCode()
	{
		return toString("s").hashCode();
	}

	/** 
		 Determines whether the specified System.Object instances are considered equal.
	 
	 @param obj
	 @return 
	*/
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof TDateTime)
		{
			return Rasad.Core.Globalization.TDateTime.OpEquality(this, (TDateTime) obj);
		}
		return false;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Implicit Casting

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator DateTime(TDateTime pd)
	{
		return TPersianDateConverter.ToGregorianDateTime(pd);
	}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator TDateTime(LocalDateTime dt)
	{
		if (dt.equals(LocalDateTime.MIN))
		{
			return MinValue;
		}

		return TPersianDateConverter.ToPersianDate(dt);
	}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator TDateTime(String dt)
	{
		return Parse(dt);
	}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator String(TDateTime dt)
	{
		return String.format("%0f", dt);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICloneable Members

	public Object Clone()
	{
		return new TDateTime(getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond(), getMillisecond());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICompareable Interface

	/** 
		 Compares the current instance with another object of the same type.
	 
	 @return 
		 A 32-bit signed integer that indicates the relative order of the objects being compared. The return value has these
		 meanings: Value Meaning Less than zero This instance is less than obj. Zero This instance is equal to obj. Greater
		 than zero This instance is greater than obj.
	 
	 @param obj An object to compare with this instance. 
	 @exception T:System.ArgumentException obj is not the same type as this instance. 
	 <filterpriority>2</filterpriority>
	*/
	public int compareTo(Object obj)
	{
		if (!(obj instanceof TDateTime))
		{
			throw new IllegalArgumentException("Comparing value is not of type PersianDate.");
		}

		TDateTime pd = (TDateTime) obj;

		if (Rasad.Core.Globalization.TDateTime.OpLessThan(pd, this))
		{
			return 1;
		}
		if (Rasad.Core.Globalization.TDateTime.OpGreaterThan(pd, this))
		{
			return -1;
		}

		return 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IComparer

	/** 
		 Compares two objects and returns a value indicating whether one is less than, equal to, or greater than the other.
	 
	 @return 
		 Value Condition Less than zero x is less than y. Zero x equals y. Greater than zero x is greater than y.
	 
	 @param y The second object to compare. 
	 @param x The first object to compare. 
	 @exception T:System.ArgumentException
		 Neither x nor y implements the <see cref="T:System.IComparable"></see>
		 interface.-or- x and y are of different types and neither one can handle comparisons with the other.
	 
	 <filterpriority>2</filterpriority>
	 @exception T:System.ApplicationException Either x or y is a null reference
	*/
	public int compare(Object x, Object y)
	{
		if (x == null || y == null)
		{
			throw new RuntimeException("Invalid PersianDate comparer.");
		}

		if (!(x instanceof TDateTime))
		{
			throw new IllegalArgumentException("x value is not of type PersianDate.");
		}

		if (!(y instanceof TDateTime))
		{
			throw new IllegalArgumentException("y value is not of type PersianDate.");
		}

		TDateTime pd1 = (TDateTime) x;
		TDateTime pd2 = (TDateTime) y;

		if (Rasad.Core.Globalization.TDateTime.OpGreaterThan(pd1, pd2))
		{
			return 1;
		}
		if (Rasad.Core.Globalization.TDateTime.OpLessThan(pd1, pd2))
		{
			return -1;
		}

		return 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IComparer<T> Implementation

	/** 
		 Compares the current object with another object of the same type.
	 
	 @return 
		 A 32-bit signed integer that indicates the relative order of the objects being compared. The return value has the
		 following meanings: Value Meaning Less than zero This object is less than the other parameter.Zero This object is
		 equal to other. Greater than zero This object is greater than other.
	 
	 @param other An object to compare with this object.
	*/
	public int compareTo(TDateTime other)
	{
		if (Rasad.Core.Globalization.TDateTime.OpLessThan(other, this))
		{
			return 1;
		}
		if (Rasad.Core.Globalization.TDateTime.OpGreaterThan(other, this))
		{
			return -1;
		}

		return 0;
	}

	/** 
		 Compares two objects and returns a value indicating whether one is less than, equal to, or greater than the other.
	 
	 @return 
		 Value Condition Less than zerox is less than y.Zerox equals y.Greater than zero x is greater than y.
	 
	 @param y The second object to compare.
	 @param x The first object to compare.
	*/
	public int compare(TDateTime x, TDateTime y)
	{
		if (Rasad.Core.Globalization.TDateTime.OpGreaterThan(x, y))
		{
			return 1;
		}
		if (Rasad.Core.Globalization.TDateTime.OpLessThan(x, y))
		{
			return -1;
		}

		return 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IEquatable<T>

	/** 
		 Indicates whether the current object is equal to another object of the same type.
	 
	 @return 
		 true if the current object is equal to the other parameter; otherwise, false.
	 
	 @param other An object to compare with this object.
	*/
	public boolean equals(TDateTime other)
	{
		return Rasad.Core.Globalization.TDateTime.OpEquality(this, other);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IFormattable

	/** 
		 Returns string representation of this instance in desired format, or using provided <see cref="IFormatProvider" />
		 instance.
	 
	 @param format
	 @param formatProvider
	 @return 
	*/
	public String toString(String format, IFormatProvider formatProvider)
	{
		if (format == null)
		{
			format = "G";
		}
		int smallhour = (getHour() > 12) ? getHour() - 12 : getHour();
		String designator = getHour() > 12 ? getPMDesignator() : getAMDesignator();

		if (formatProvider != null)
		{
			Object tempVar = formatProvider.GetFormat(ICustomFormatter.class);
			ICustomFormatter formatter = tempVar instanceof ICustomFormatter ? (ICustomFormatter)tempVar : null;
			if (formatter != null)
			{
				return formatter.Format(format, this, formatProvider);
			}
		}
		if (format.length() == 1)
		{
			switch (format)
			{
				case "D":
					//'yyyy mm dd dddd' e.g. 'دوشنبه 20 شهریور 1384'
					return String.format("%1$s %2$s %3$s %4$s", new Object[] {getLocalizedWeekDayName(), (new Integer(getDay())).toString("00"), getLocalizedMonthName(), getYear()});

				case "f":
					//'hh:mm yyyy mmmm dd dddd' e.g. 'دوشنبه 20 شهریور 1384 21:30'
					return String.format("%1$s %2$s %3$s %4$s %5$s:%6$s", new Object[] {getLocalizedWeekDayName(), (new Integer(getDay())).toString("00"), getLocalizedMonthName(), getYear(), (new Integer(getHour())).toString("00"), (new Integer(getMinute())).toString("00")});

				case "F":
					//'tt hh:mm:ss yyyy mmmm dd dddd' e.g. 'دوشنبه 20 شهریور 1384 02:30:22 ب.ض'
					return String.format("%1$s %2$s %3$s %4$s %5$s:%6$s:%7$s %8$s", new Object[] {getLocalizedWeekDayName(), (new Integer(getDay())).toString("00"), getLocalizedMonthName(), getYear(), (new Integer(smallhour)).toString("00"), (new Integer(getMinute())).toString("00"), (new Integer(getSecond())).toString("00"), designator});

				case "g":
					//'yyyy/mm/dd hh:mm tt'
					return String.format("%1$s/%2$s/%3$s %4$s:%5$s %6$s", new Object[] {getYear(), (new Integer(getMonth())).toString("00"), (new Integer(getDay())).toString("00"), (new Integer(smallhour)).toString("00"), (new Integer(getMinute())).toString("00"), designator});

				case "G":
					//'yyyy/mm/dd hh:mm:ss tt'
					return String.format("%1$s/%2$s/%3$s %4$s:%5$s:%6$s %7$s", new Object[] {getYear(), (new Integer(getMonth())).toString("00"), (new Integer(getDay())).toString("00"), (new Integer(smallhour)).toString("00"), (new Integer(getMinute())).toString("00"), (new Integer(getSecond())).toString("00"), designator});

				case "M":
				case "m":
					//'yyyy mmmm'
					return String.format("%1$s %2$s", getYear(), getLocalizedMonthName());

				case "s":
					//'yyyy-mm-ddThh:mm:ss'
					return String.format("%1$s-%2$s-%3$sT%4$s:%5$s:%6$s", getYear(), (new Integer(getMonth())).toString("00"), (new Integer(getDay())).toString("00"), (new Integer(getHour())).toString("00"), (new Integer(getMinute())).toString("00"), (new Integer(getSecond())).toString("00"));

				case "t":
					//'hh:mm tt'
					return String.format("%1$s:%2$s %3$s", (new Integer(smallhour)).toString("00"), (new Integer(getMinute())).toString("00"), designator);

				case "T":
					//'hh:mm:ss tt'
					return String.format("%1$s:%2$s:%3$s %4$s", new Object[] {(new Integer(smallhour)).toString("00"), (new Integer(getMinute())).toString("00"), (new Integer(getSecond())).toString("00"), designator});
				case "d":
				default:
					//ShortDatePattern yyyy/mm/dd e.g. '1384/9/1'
					return String.format("%1$s/%2$s/%3$s", getYear(), (new Integer(getMonth())).toString("00"), (new Integer(getDay())).toString("00"));
			}
		}
		return CustomeFormat(format, formatProvider);
	}

	/** 
		 Returns string representation of this instance in default format.
	 
	 @param format
	 @return 
	*/
	public String toString(String format)
	{
		return toString(format, null);
	}

	/** 
		 Returns string representation of this instance and format it using the <see cref="IFormatProvider" /> instance.
	 
	 @param formatProvider
	 @return 
	*/
	public String toString(IFormatProvider formatProvider)
	{
		return toString(null, formatProvider);
	}

	private String CustomeFormat(String format, IFormatProvider formatProvider)
	{
		int numSkip = 1;
		StringBuilder outputBuffer = new StringBuilder();
		for (int i = 0; i < format.length(); i += numSkip)
		{
			char patternChar = format.charAt(i);
			switch (patternChar)
			{
				case 'F':
				case 'f':
					break;

				case 'H':
				{
					numSkip = ParseRepeatPattern(format, i, patternChar);
					FormatDigits(outputBuffer, getHour(), numSkip);
					break;
				}
				case ':':
				{
					outputBuffer.append(':');
					numSkip = 1;
					break;
				}
				case '/':
				{
					outputBuffer.append('/');
					numSkip = 1;
					break;
				}

				case '\'':
				case '"':
				{
					StringBuilder result = new StringBuilder();
					numSkip = ParseQuoteString(format, i, result);
					outputBuffer.append(result);
					break;
				}

				case 'M':
					numSkip = ParseRepeatPattern(format, i, patternChar);
					if (numSkip <= 2)
					{
						outputBuffer.append((new Integer(getMonth())).toString("00"));
					}
					else
					{
						outputBuffer.append(getLocalizedMonthName());
					}
					break;

				case '\\':
					numSkip = 1;
					break;
				case 'd':
					numSkip = ParseRepeatPattern(format, i, patternChar);
					if (numSkip > 2)
					{
						outputBuffer.append(getLocalizedWeekDayName());
					}
					else
					{
						outputBuffer.append((new Integer(getDay())).toString("00"));
					}
					break;

				case 'h':
				{
					numSkip = ParseRepeatPattern(format, i, patternChar);
					int num3 = getHour() % 12;
					if (num3 == 0)
					{
						num3 = 12;
					}
					FormatDigits(outputBuffer, num3, numSkip);
					break;
				}
				case 's':
				{
					numSkip = ParseRepeatPattern(format, i, patternChar);
					FormatDigits(outputBuffer, getSecond(), numSkip);
					break;
				}
				case 't':
				{
					numSkip = ParseRepeatPattern(format, i, patternChar);

					if (getHour() >= 12)
					{
						outputBuffer.append(getPMDesignator());
					}
					else
					{
						outputBuffer.append(getAMDesignator());
					}
					break;
				}
				case 'm':
				{
					numSkip = ParseRepeatPattern(format, i, patternChar);
					FormatDigits(outputBuffer, getMinute(), numSkip);
					break;
				}
				case 'y':
					numSkip = ParseRepeatPattern(format, i, patternChar);

					FormatDigits(outputBuffer, getYear(), numSkip);
					break;

				default:
					numSkip = 1;
					outputBuffer.append(format.charAt(i));
					break;
			}
		}
		return outputBuffer.toString();
	}

	private int ParseQuoteString(String format, int pos, StringBuilder result)
	{
		int length = format.length();
		int num2 = pos;
		char ch = format.charAt(pos++);
		boolean flag = false;
		while (pos < length)
		{
			char ch2 = format.charAt(pos++);
			if (ch2 == ch)
			{
				flag = true;
				break;
			}
			if (ch2 == '\\')
			{
				if (pos >= length)
				{
					throw new NumberFormatException("Format_InvalidString");
				}
				result.append(format.charAt(pos++));
			}
			else
			{
				result.append(ch2);
			}
		}
		if (!flag)
		{
			throw new NumberFormatException("Format_BadQuote");
		}
		return (pos - num2);
	}

	private void FormatDigits(StringBuilder outputBuffer, int digit, int numSkip)
	{
		String digitString = tangible.StringHelper.padLeft(String.valueOf(digit), numSkip, '0');
		outputBuffer.append(tangible.StringHelper.substring(digitString, digitString.length() - numSkip, numSkip));
	}

	private int ParseRepeatPattern(String format, int pos, char patternChar)
	{
		int length = format.length();
		int num2 = pos + 1;
		while ((num2 < length) && (format.charAt(num2) == patternChar))
		{
			num2++;
		}
		return (num2 - pos);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public LocalDateTime AddDays(double days)
	{
		LocalDateTime dt = ShDate.EncodeDate(getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond(), getMillisecond());
		return ShDate.ToDateTime(ShDate.AddDays(dt, days));
	}

	public LocalDateTime AddMonths(int months)
	{
		LocalDateTime dt = ShDate.EncodeDate(getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond(), getMillisecond());
		return ShDate.ToDateTime(ShDate.AddMonths(dt, months));
	}

	public LocalDateTime AddYears(int years)
	{
		LocalDateTime dt = ShDate.EncodeDate(getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond(), getMillisecond());
		return ShDate.ToDateTime(ShDate.AddYears(dt, years));
	}
}