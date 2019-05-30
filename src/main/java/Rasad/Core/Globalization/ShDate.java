package Rasad.Core.Globalization;

import Rasad.Core.Extensions.*;
import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

/** 
	 اين کلاس حاوي توابع سودمند تاريخ شمسي و توابع تبديل تاريخ ميلادي به شمسي و تاريخ شمسي به ميلادي مي باشد
*/
public final class ShDate
{
	static
	{
		setFirstDayOFWeek(DayOfWeek.SATURDAY);
		setDateSeparator('/');
		setTimeSeparator(':');
		setAmDesignator("ق.ظ");
		setPmDesignator("ب.ظ");
		setMonthDaysArray(new Object[]
		{
			new Object[] {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30},
			new Object[] {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29}
		});
		setShortestWeekDayNames(new String[] {"ی", "د", "س", "چ", "پ", "ج", "ش"});
		setLongestWeekDayNames(new String[] {"یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه"});
		setWeekDaysRemained(new short[] {5, 4, 3, 2, 1, 0, 6});
		setShortMonthNames(new String[] {"فروردين", "ارديبهشت", "خرداد", "تير", "مرداد", "شهريور", "مهر", "آبان", "آذر", "دي", "بهمن", "اسفند"});
		setLongMonthNames(new String[] {"فروردين", "ارديبهشت", "خرداد", "تير", "مرداد", "شهريور", "مهر", "آبان", "آذر", "دي", "بهمن", "اسفند"});

		setDateTimeFormatInfo(new DateTimeFormatInfo());
		getDateTimeFormatInfo().AbbreviatedDayNames = new String[] {"ی", "د", "س", "چ", "پ", "ج", "ش"};
		getDateTimeFormatInfo().ShortestDayNames = new String[] {"ی", "د", "س", "چ", "پ", "ج", "ش"};
		getDateTimeFormatInfo().DayNames = new String[] {"یکشنبه", "دوشنبه", "ﺳﻪشنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه"};
		getDateTimeFormatInfo().AbbreviatedMonthNames = new String[] {"فروردین", "ارديبهشت", "خرداد", "تير", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند", ""};
		getDateTimeFormatInfo().MonthNames = new String[] {"فروردین", "ارديبهشت", "خرداد", "تير", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند", ""};
		getDateTimeFormatInfo().AMDesignator = "ق.ظ";
		getDateTimeFormatInfo().PMDesignator = "ب.ظ";
		getDateTimeFormatInfo().FirstDayOfWeek = DayOfWeek.SATURDAY;
		getDateTimeFormatInfo().FullDateTimePattern = "yyyy MMMM dddd, dd HH:mm:ss";
		getDateTimeFormatInfo().LongDatePattern = "dddd , dd MMMM yyyy";
		getDateTimeFormatInfo().ShortDatePattern = "yyyy/MM/dd";
		getDateTimeFormatInfo().YearMonthPattern = "MMMM, yyyy";
		setCalendar(new TPersianCalendar());
	}

	private static DateTimeFormatInfo DateTimeFormatInfo;
	public static DateTimeFormatInfo getDateTimeFormatInfo()
	{
		return DateTimeFormatInfo;
	}
	private static void setDateTimeFormatInfo(DateTimeFormatInfo value)
	{
		DateTimeFormatInfo = value;
	}
	private static TPersianCalendar Calendar;
	public static TPersianCalendar getCalendar()
	{
		return Calendar;
	}
	private static void setCalendar(TPersianCalendar value)
	{
		Calendar = value;
	}
	private static short[] WeekDaysRemained;
	public static short[] getWeekDaysRemained()
	{
		return WeekDaysRemained;
	}
	private static void setWeekDaysRemained(short[] value)
	{
		WeekDaysRemained = value;
	}
	private static DayOfWeek FirstDayOFWeek = DayOfWeek.values()[0];
	public static DayOfWeek getFirstDayOFWeek()
	{
		return FirstDayOFWeek;
	}
	private static void setFirstDayOFWeek(DayOfWeek value)
	{
		FirstDayOFWeek = value;
	}

	private static String[] ShortestWeekDayNames;
	public static String[] getShortestWeekDayNames()
	{
		return ShortestWeekDayNames;
	}
	private static void setShortestWeekDayNames(String[] value)
	{
		ShortestWeekDayNames = value;
	}

	/** 
		 لیست اسامی روزهای هفته
	*/
	private static String[] LongestWeekDayNames;
	public static String[] getLongestWeekDayNames()
	{
		return LongestWeekDayNames;
	}
	private static void setLongestWeekDayNames(String[] value)
	{
		LongestWeekDayNames = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region    Constants

	/** 
		 1
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerBrowsable(DebuggerBrowsableState.Never)] private const short Gregorian = 1;
	private static final short Gregorian = 1;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerBrowsable(DebuggerBrowsableState.Never)] private static string DateTimeExpression = "(?<DatePart>(?<Year>(?:\\d{4}|\\d{2}))/(?<Month>\\d{1,2})/(?<Day>\\d{1,2}))?(?<TimePart>\\s*(?<Hour>\\d{1,2})(\\s*:(?<Minute>\\d{1,2})(\\s*:(?<Second>\\d{1,2}))?)?(?<TimeType>\\s+ق.ظ|ب.ظ)?)?";
	private static String DateTimeExpression = "(?<DatePart>(?<Year>(?:\\d{4}|\\d{2}))/(?<Month>\\d{1,2})/(?<Day>\\d{1,2}))?(?<TimePart>\\s*(?<Hour>\\d{1,2})(\\s*:(?<Minute>\\d{1,2})(\\s*:(?<Second>\\d{1,2}))?)?(?<TimeType>\\s+ق.ظ|ب.ظ)?)?";

	private static String AmDesignator;
	public static String getAmDesignator()
	{
		return AmDesignator;
	}
	private static void setAmDesignator(String value)
	{
		AmDesignator = value;
	}
	private static String PmDesignator;
	public static String getPmDesignator()
	{
		return PmDesignator;
	}
	private static void setPmDesignator(String value)
	{
		PmDesignator = value;
	}

	/** 
		 لیست اسامی ماههای شمسی
	*/
	private static String[] ShortMonthNames;
	public static String[] getShortMonthNames()
	{
		return ShortMonthNames;
	}
	private static void setShortMonthNames(String[] value)
	{
		ShortMonthNames = value;
	}

	/** 
		 لیست اسامی ماههای شمسی
	*/
	private static String[] LongMonthNames;
	public static String[] getLongMonthNames()
	{
		return LongMonthNames;
	}
	private static void setLongMonthNames(String[] value)
	{
		LongMonthNames = value;
	}

	/** 
		 لیست اسامی روزهای هفته
	*/
	public static String[] getShortDayNames()
	{
		return getDateTimeFormatInfo().ShortestDayNames;
	}

	/** 
		 تعداد روزهای ماهها در سال عادی و سال کبیسه
	*/
	private static Object[] MonthDaysArray;
	public static Object[] getMonthDaysArray()
	{
		return MonthDaysArray;
	}
	private static void setMonthDaysArray(Object[] value)
	{
		MonthDaysArray = value;
	}

	/** 
		 جدا کننده تاریخ تنظیم شده در سیستم
	*/
	private static char DateSeparator;
	public static char getDateSeparator()
	{
		return DateSeparator;
	}
	private static void setDateSeparator(char value)
	{
		DateSeparator = value;
	}

	/** 
		 جدا کننده ساعت تنظیم شده در سیستم
	*/
	private static char TimeSeparator;
	public static char getTimeSeparator()
	{
		return TimeSeparator;
	}
	private static void setTimeSeparator(char value)
	{
		TimeSeparator = value;
	}

	public static String getShamsiDateFormat()
	{
		return String.format("0000%1$s00%1$s00", getDateSeparator());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Functions

	private static void IncMonthB(tangible.RefObject<Integer> year, tangible.RefObject<Integer> month, tangible.RefObject<Integer> day, int numberOfMonths)
	{
		if (numberOfMonths >= 0)
		{
			year.argValue = (int)(year.argValue + (numberOfMonths / 12));
			numberOfMonths = (int)(numberOfMonths % 12);
			month.argValue = (int)(month.argValue + numberOfMonths);

			if ((month.argValue - 1) > 11) // if Month <= 0, word(Month-1) > 11)
			{
				year.argValue = year.argValue + 1;
				month.argValue = month.argValue - 12;
			}
		}
		else
		{
			int posMonth = Math.abs(numberOfMonths);
			year.argValue = (short)(year.argValue - (posMonth / 12));
			posMonth = (short)(posMonth % 12);
			month.argValue = (short)(month.argValue - posMonth);

			if (month.argValue < 1) // if Month <= 0, word(Month-1) > 11)
			{
				year.argValue = year.argValue - 1;
				month.argValue = month.argValue + 12;
			}
		}

		Object[] monthDay = (Object[])getMonthDaysArray()[((IsLeapYear(year.argValue)) ? 0 : 1)];
		if (day.argValue > (Short)monthDay[month.argValue - 1])
		{
			day.argValue = (Short)monthDay[month.argValue - 1];
		}
	}

	private static void civil_persian(tangible.RefObject<Integer> year, tangible.RefObject<Integer> month, tangible.RefObject<Integer> day)
	{
		jdn_persian(civil_jdn(year.argValue, month.argValue, day.argValue, Gregorian), year, month, day);
	}

	private static void persian_civil(tangible.RefObject<Integer> year, tangible.RefObject<Integer> month, tangible.RefObject<Integer> day)
	{
		jdn_civil(persian_jdn(year.argValue, month.argValue, day.argValue), year, month, day);
	}

	private static int Ceil(Object objIn)
	{
		double dValue = (double)(objIn, CultureInfo.InvariantCulture.NumberFormat);
		int result = -(int)Math.signum(dValue) * Int_(-Math.abs(dValue));
		return result;
	}

	private static int Int_(Object objIn)
	{
		double dValue = (double)(objIn, CultureInfo.InvariantCulture.NumberFormat);
		if (dValue < 0)
		{
			if (Frac(dValue) != 0)
			{
				return ((int)dValue - 1);
			}
			return (int)dValue;
		}
		return (int)dValue;
	}

	private static double Frac(Object objIn)
	{
		double dValue = (double)(objIn, CultureInfo.InvariantCulture.NumberFormat);
		return dValue - (Integer)objIn;
	}


	private static int persian_jdn(int iYear, int iMonth, int iDay)
	{
		final int persianEpoch = 1948321; // The JDN of 1 Farvardin 1
		int epbase;
		int mdays;
		if (iYear >= 0)
		{
			epbase = iYear - 474;
		}
		else
		{
			epbase = iYear - 473;
		}
		int epyear = 474 + (epbase % 2820);
		if (iMonth <= 7)
		{
			mdays = ((int)iMonth - 1) * 31;
		}
		else
		{
			mdays = ((int)iMonth - 1) * 30 + 6;
		}
		int returnValue = (int)iDay + mdays + ((epyear * 682) - 110) / 2816 + (epyear - 1) * 365 + epbase / 2820 * 1029983 + (persianEpoch - 1);
		return returnValue;
	}

	private static void jdn_julian(tangible.RefObject<Integer> jdn, tangible.RefObject<Integer> iYear, tangible.RefObject<Integer> iMonth, tangible.RefObject<Integer> iDay)
	{
		int j = jdn.argValue + 1402;
		int k = ((j - 1) / 1461);
		int l = j - 1461 * k;
		int n = ((l - 1) / 365) - (l / 1461);
		int i = l - 365 * n + 30;
		j = ((80 * i) / 2447);
		iDay.argValue = i - ((2447 * j) / 80);
		i = (j / 11);
		iMonth.argValue = j + 2 - 12 * i;
		iYear.argValue = 4 * k + n + i - 4716;
	}

	private static void jdn_civil(int jdn, tangible.RefObject<Integer> year, tangible.RefObject<Integer> month, tangible.RefObject<Integer> day)
	{
		int l;
		int n;
		int i;
		int j;

		if (jdn > 2299160)
		{
			l = jdn + 68569;
			n = ((4 * l) / 146097);
			l = l - ((146097 * n + 3) / 4);
			i = ((4000 * (l + 1)) / 1461001);
			l = l - ((1461 * i) / 4) + 31;
			j = ((80 * l) / 2447);
			day.argValue = l - ((2447 * j) / 80);
			l = (j / 11);
			month.argValue = j + 2 - 12 * l;
			year.argValue = 100 * (n - 49) + i + l;
		}
		else
		{
			tangible.RefObject<Integer> tempRef_jdn = new tangible.RefObject<Integer>(jdn);
			jdn_julian(tempRef_jdn, year, month, day);
		jdn = tempRef_jdn.argValue;
		}
	}

	private static void jdn_persian(int jdn, tangible.RefObject<Integer> year, tangible.RefObject<Integer> month, tangible.RefObject<Integer> day)
	{
		Object ycycle;

		Object depoch = jdn - 2121446;

		Object cycle = (Integer)depoch / 1029983;

		Object cyear = (Integer)depoch % 1029983;
		if ((Integer)cyear == 1029982)
		{
			ycycle = 2820;
		}
		else
		{
			double aux1 = (int)(cyear) / 366;
			double aux2 = ((int)(cyear)) % 366;
			ycycle = (int)(((2134 * aux1) + (2816 * aux2) + 2815) / 1028522) + aux1 + 1;
		}
		year.argValue = (short)((Integer)ycycle + (2820 * (Integer)cycle) + 474);
		if (year.argValue <= 0)
		{
			year.argValue = year.argValue - 1;
		}

		Object yday = (jdn - persian_jdn(year.argValue, 1, 1)) + 1;
		if ((int)(yday) <= 186)
		{
			month.argValue = Ceil((double)(yday, CultureInfo.InvariantCulture.NumberFormat) / 31);
		}
		else
		{
			month.argValue = Ceil(((double)(yday, CultureInfo.InvariantCulture.NumberFormat) - 6) / 30);
		}

		day.argValue = (jdn - persian_jdn(year.argValue, month.argValue, 1)) + 1;
	}

	private static int civil_jdn(int iYear, int iMonth, int iDay, short calendarType)
	{
		if (calendarType == Gregorian & ((iYear > 1582) | ((iYear == 1582) & (iMonth > 10)) | ((iYear == 1582) & (iMonth == 10) & (iDay > 14))))
		{
			return ((1461 * (iYear + 4800 + ((iMonth - 14) / 12))) / 4) + ((367 * (iMonth - 2 - 12 * ((iMonth - 14) / 12))) / 12) - ((3 * ((iYear + 4900 + ((iMonth - 14) / 12)) / 100)) / 4) + iDay - 32075;
		}
		return 367 * iYear - ((7 * (iYear + 5001 + ((iMonth - 9) / 7))) / 4) + ((275 * iMonth) / 9) + iDay + 1729777;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Current Date Functions

	/** 
		 تابع جاری تاریخ امروز را با فرمت ####/##/## بر می گرداند
	 
	 @return تاریخ با ####/##/## فرمت
	*/
	public static String CurrentDateStr()
	{
		return NowWithCentury();
	}

	/** 
		 تابع جاری تاریخ امروز را به صورت عددی بر می گرداند مانند 13780201
	 
	 @return تاریخ عددی امروز
	*/
	public static int CurrentDateInt()
	{
		return Integer.parseInt(RemoveDateSeparator(CurrentDateStr()));
	}


	/** 
		 تابع زیر ساعت جاری را به صورت 1014 برمی گرداند که 2 رقم اول از چپ ساعت و 2 رقم سمت راست دقیقه می باشد
	 
	 @return 
	*/
	public static String CurrentTime()
	{
		return CurrentTime(false, false);
	}

	public static String TimeToString(LocalDateTime time, boolean withSeparator, boolean withSecond)
	{
		String H, M, S = "";
		LocalDateTime DT = time;
		H = (new Integer(DT.getHour())).toString("00");
		M = (new Integer(DT.getMinute())).toString("00");
		S = (new Integer(DT.getSecond())).toString("00");
		if (withSeparator)
		{
			if (withSecond)
			{
				return H + getTimeSeparator() + M + getTimeSeparator() + S;
			}
			return H + getTimeSeparator() + M;
		}
		if (withSecond)
		{
			return H + M + S;
		}
		return H + M;
	}
	public static String CurrentTime(boolean WithSeparator, boolean WithSecond)
	{
		return TimeToString(LocalDateTime.now(), WithSeparator, WithSecond);
	}

	/** 
		 تابع زیر ساعت جاری را به صورت 10:14 برمی گرداند که 2 رقم اول از چپ ساعت و 2 رقم سمت راست دقیقه می باشد
	 
	 @return 
	*/
	public static String CurrentTimeWithSeparator()
	{
		return LocalDateTime.now().getHour().toString("00") + ":" + LocalDateTime.now().getMinute().toString("00");
	}

	/** 
		 اين تابع عدد سال شمسي جاري را برميگرداند
	 
	 @return عدد سال شمسي جاري
	*/
	public static int CurrentYear()
	{
		return YearOf(LocalDateTime.now());
	}

	/** 
		 اين تابع عدد ماه شمسي جاري را بر مي گرداند
	 
	 @return عدد ماه شمسي جاري
	*/
	public static int CurrentMonth()
	{
		return MonthOf(LocalDateTime.now());
	}

	/** 
		 اين تابع عدد روز شمسي جاري را بر مي گرداند
	 
	 @return عدد روز شمسي جاري
	*/
	public static int CurrentDay()
	{
		return DayOf(LocalDateTime.now());
	}

	/** 
		 تابع جاری نام روز هفته جاری را باز می گرداند مانند شنبه ... جمعه
	 
	 @return شنبه ... جمعه
	*/
	public static String CurrentDayName() // {شنبه ... جمعه}
	{
		return getLongestWeekDayNames()[CurrentDayOfWeek().getValue()];
	}

	/** 
		 نام ماه شمسی جاری را برمی گرداند
	 
	 @return فروردين ... اسفند
	*/
	public static String CurrentMonthName() //{فروردين ... اسفند}
	{
		return getLongMonthNames()[CurrentMonth() - 1];
	}

	/** 
		 نام روز جاری در ماه جاری را برمی گرداند
	 
	 @return يکم ... سي و يکم
	*/
	public static String CurrentDayNameInMonth() //{يکم ... سي و يکم}
	{
		return Rasad.Core.Extensions.TNumberHelper.ToWord(CurrentDay(), true);
	}


	/** 
		 اين تابع مشخص مي کند که امروز ما در چندمين روز هفته قرار داريم
		 شنبه روز اول و جمعه روز هفتم ميباشد
	 
	 @return روز چندم هفته
	*/
	public static DayOfWeek CurrentDayOfWeek() //{6...0}
	{
		return LocalDateTime.now().getDayOfWeek();
	}


	/** 
		 اين تابع تاريخ جاري شمسي سيستم را بصورت استرينگ 8 کارکتري بدون قرن برميگرداند
	 
	 @return استرينگ 8 کارکتري بدون قرن
	*/
	public static String Now() //{78/02/01}
	{
		return DateToStr(LocalDateTime.now()).substring(2, 10);
	}

	/** 
		 اين تابع تاريخ جاري شمسي سيستم را بصورت استرينگ 10 کارکتري با قرن برميگرداند
	 
	 @return استرينگ 10 کارکتري با قرن
	*/
	public static String NowWithCentury() //{1378/02/01}
	{
		return DateToStr(LocalDateTime.now());
	}

	/** 
		 اين متد مشخص مي کند که در هفته چندم سال هستيم
	 
	 @return هفته چندم
	*/
	public static int CurrentWeekNo()
	{
		return WeekOf(LocalDateTime.now());
	}

	/** 
		 تابع جاری تاریخ امروز را به صورت کامل و رشته ای برمیگرداند مانند شنبه سی ام مهر ماه سال 1384
	 
	 @return تاریخ کامل به صورت رشته فارسی
	*/
	public static String CurrentCompleteDateName()
	{
		return CompleteDateName(LocalDateTime.now());
	}

	/** 
		 آخرین روز سال جاری را بر می گرداند
	 
	 @return آخرین روز سال جاری
	*/
	public static String LastDayOfCurrentYear()
	{
		return LastDayOfYear(LocalDateTime.now());
	}

	/** 
		 تاریخ آخرین روز از ماه جاری را بر می گرداند
	 
	 @return تاریخ آخرین روز از ماه جاری
	*/
	public static String LastDayOfCurrentMoon()
	{
		return DateToStr(LastDayOfMoon(LocalDateTime.now()));
	}

	/** 
		 تاریخ آخرین روز از هفته جاری را بر می گرداند
	 
	 @return تاریخ آخرین روز از هفته جاری
	*/
	public static String LastDayOfCurrentWeek()
	{
		return DateToStr(LastDayOfWeek(LocalDateTime.now()));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Date Functions

	public static LocalDateTime EncodeDate(String dateTime)
	{
		int year = 0, month = 0, day = 0, hour = 0, minute = 0, second = 0, millisecond = 0;
		String TimeType;
		Match m = GetDatePart(dateTime);
		if (!m.Success)
		{
			throw new IllegalArgumentException("تاریخ و ساعت مورد نظر ساختار معتبری ندارد");
		}
		if (m.Groups["DatePart"].Success)
		{
			if (m.Groups["Year"].Success)
			{
				String yearStr = m.Groups["Year"].Value;
				if (yearStr.length() == 2)
				{
					yearStr = CurrentDateStr().substring(0, 2) + yearStr;
				}
				year = Integer.parseInt(yearStr);
			}
			if (m.Groups["Month"].Success)
			{
				month = (int)(m.Groups["Month"].Value);
			}
			if (m.Groups["Day"].Success)
			{
				day = (int)(m.Groups["Day"].Value);
			}
		}
		if (m.Groups["TimePart"].Success)
		{
			if (m.Groups["Second"].Success)
			{
				second = (int)(m.Groups["Second"].Value);
			}
			if (m.Groups["Minute"].Success)
			{
				minute = (int)(m.Groups["Minute"].Value);
			}
			if (m.Groups["Hour"].Success)
			{
				hour = (int)(m.Groups["Hour"].Value);
				if (hour == 24)
				{
					hour = 23;
					minute = 59;
					second = 59;
					millisecond = 999;
				}
			}
			if (m.Groups["TimeType"].Success)
			{
				TimeType = m.Groups["Second"].Value;
				if (TimeType.equals(getPmDesignator(), StringComparison.CurrentCultureIgnoreCase))
				{
					hour += 12;
				}
				else if (TimeType.equals(getAmDesignator(), StringComparison.CurrentCultureIgnoreCase))
				{
					//Do Nothing , Do not remove !
				}
				else
				{
					throw new IllegalArgumentException("مقدار " + TimeType + " در تاریخ مورد نظر معتبر نیست");
				}
			}
		}
		return EncodeDate(year, month, day, hour, minute, second, millisecond);
	}

	private static Match GetDatePart(String dateTime)
	{
		return Regex.Match(dateTime, DateTimeExpression);
	}

	/** 
		 اين تابع روز،ماه و سال (شمسي) را بصورت عددي از کاربر دريافت نموده و يک تاريخ ميلادي سيستم مي سازد
	 
	 @param year سال شمسي مانند 1378
	 @param month ماه شمسي مانند 12
	 @param day روز شمسي مانند 10
	 @return تاريخ ميلادي
	*/
	public static LocalDateTime EncodeDate(int year, int month, int day)
	{
		return EncodeDate(year, month, day, 0, 0);
	}

	/** 
		 اين تابع روز،ماه و سال (شمسي) را بصورت عددي از کاربر دريافت نموده و يک تاريخ ميلادي سيستم مي سازد
	 
	 @param year سال شمسي مانند 1378
	 @param month ماه شمسي مانند 12
	 @param day روز شمسي مانند 10
	 @param hour ساعت
	 @param minute دقیقه
	 @return 
	*/
	public static LocalDateTime EncodeDate(int year, int month, int day, int hour, int minute)
	{
		return EncodeDate(year, month, day, hour, minute, 0, 0);
	}

	/** 
		 اين تابع روز،ماه و سال (شمسي) را بصورت عددي از کاربر دريافت نموده و يک تاريخ ميلادي سيستم مي سازد
	 
	 @param year سال شمسي مانند 1378
	 @param month ماه شمسي مانند 12
	 @param day روز شمسي مانند 10
	 @param hour ساعت
	 @param minute دقیقه
	 @param second ثانیه
	 @param millisecond میلی ثانیه
	 @return تاریخ میلادی
	*/

	public static java.time.LocalDateTime EncodeDate(int year, int month, int day, int hour, int minute, int second)
	{
		return EncodeDate(year, month, day, hour, minute, second, 0);
	}

	public static java.time.LocalDateTime EncodeDate(int year, int month, int day, int hour, int minute)
	{
		return EncodeDate(year, month, day, hour, minute, 0, 0);
	}

	public static java.time.LocalDateTime EncodeDate(int year, int month, int day, int hour)
	{
		return EncodeDate(year, month, day, hour, 0, 0, 0);
	}

	public static java.time.LocalDateTime EncodeDate(int year, int month, int day)
	{
		return EncodeDate(year, month, day, 0, 0, 0, 0);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static DateTime EncodeDate(int year, int month, int day, int hour = 0, int minute = 0, int second = 0, int millisecond = 0)
	public static LocalDateTime EncodeDate(int year, int month, int day, int hour, int minute, int second, int millisecond)
	{
		if (millisecond > 999)
		{
			throw new IllegalArgumentException("مقدار میلی ثانیه 0 تا 999 مجاز می باشد");
		}
		if (second > 59 || second < 0)
		{
			throw new IllegalArgumentException("مقدار ثانیه 0 تا 59 مجاز می باشد");
		}
		if (minute > 59 || minute < 0)
		{
			throw new IllegalArgumentException("مقدار دقیقه 0 تا 59 مجاز می باشد");
		}
		if (hour > 23 || hour < 0)
		{
			throw new IllegalArgumentException("مقدار ساعت 0 تا 23 مجاز می باشد");
		}
		int V_Y = year;
		int V_M = month;
		int V_D = day;
		tangible.RefObject<Integer> tempRef_V_Y = new tangible.RefObject<Integer>(V_Y);
		tangible.RefObject<Integer> tempRef_V_M = new tangible.RefObject<Integer>(V_M);
		tangible.RefObject<Integer> tempRef_V_D = new tangible.RefObject<Integer>(V_D);
		persian_civil(tempRef_V_Y, tempRef_V_M, tempRef_V_D);
	V_D = tempRef_V_D.argValue;
	V_M = tempRef_V_M.argValue;
	V_Y = tempRef_V_Y.argValue;

		return LocalDateTime.of(V_Y, V_M, V_D, hour, minute, second, millisecond);
	}

	/** 
		 اين تابع يک تاريخ ميلادي سيستم از کاربر دريافت نموده و 3 متغير ارسالي کاربر را با سال و ماه و روز شمسي استخراج شده
		 از تاريخ ارسالي پر مي نمايد
	 
	 @param VDateTime تاريخ ميلادي ارسالي
	*/
	public static TShamsiDate DecodeDate(LocalDateTime VDateTime)
	{
		return new TShamsiDate(VDateTime);
	}

	public static TShamsiDate DecodeDate(String ShamsiDate)
	{
		String[] AllParts = ShamsiDate.split(java.util.regex.Pattern.quote(String.valueOf(getDateSeparator())), -1);
		return new TShamsiDate(Integer.parseInt(AllParts[0]), Integer.parseInt(AllParts[1]), Integer.parseInt(AllParts[2]));
	}

	public static String DateToStr(LocalDateTime VDateTime)
	{
		return DateToStr(VDateTime, String.valueOf(getDateSeparator()));
	}

	/** 
		 تابع جاري يک تاريخ ميلادي سيستم از کاربر دريافت نموده و يک رشته تاريخ شمسي باز مي گرداند
	 
	 @param VDateTime تاريخ ميلادي سيستم
	 @return تاريخ شمسي معادل تاريخ ورودي
	*/
	public static String DateToStr(LocalDateTime VDateTime, String dateSeparator)
	{
		TShamsiDate Result = DecodeDate(VDateTime);
		return FormatDate(Result.getYear(), Result.getMonth(), Result.getDay(), dateSeparator);
	}

	/** 
		 این تابع تاریخ میلادی را دریافت نموده و تاریخ شمسی معادل آنرا با فرمت عددی برمی گرداند
	 
	 @param VDateTime تاریخ میلادی
	 @return تاریخ شمسی با فرمت عددی مانند 13780201
	*/
	public static int DateToInt(LocalDateTime VDateTime)
	{
		return Integer.parseInt(RemoveDateSeparator(DateToStr(VDateTime, "")));
	}

	public static LocalDateTime ToDateTime(String ShamsiDate)
	{
		return ToDateTime(ShamsiDate, String.valueOf(getDateSeparator()));
	}

	/** 
		 تابع مورد نظر يک تاريخ شمسي به شکل "1378/02/01" يا "78/02/01" را دريافت نموده و به فرمت تاريخ ميلادي سيستم تبديل
		 مينمايد
	 
	 @param VDateTimeStr تاريخ شمسي رشته اي مورد نظر با فرمت ####/##/## و یا ##/##/## فرمت .
	 @return تاريخ ميلادي سيستم
	*/
	public static LocalDateTime ToDateTime(String ShamsiDate, String dateSeparator)
	{
		String DateStr = CoerceDate(ShamsiDate, dateSeparator);
		if (!IsDateValid(ShamsiDate, dateSeparator))
		{
			throw new IllegalArgumentException("Shamsi date is not in correct format !", "ShamsiDate");
		}

		return DecodeDate(DateStr).getDateTime();
	}

	/** 
		 این تابع تاریخ شمسی با فرمت عددی را دریافت نموده و تاریخ میلادی معادل با آن را برمی گرداند
	 
	 @param VDateTimeInt تاریخ با فرمت عددی مانند 13780201
	 @return تاریخ میلادی سیستم
	*/
	public static LocalDateTime ToDateTime(int ShamsiDateInt)
	{
		return ToDateTime(String.valueOf(ShamsiDateInt));
	}

	/** 
		 این تابع روز ، ماه و سال شمسی را دریافت نموده و تاریخ میلادی سیستم معادل با آن را برمی گرداند
	 
	 @param Year سال شمسی
	 @param Month ماه شمسی
	 @param Day روز شمسی
	 @return تاریخ میلادی سیستم
	*/
	public static LocalDateTime ToDateTime(int Year, int Month, int Day)
	{
		return EncodeDate(Year, Month, Day);
	}

	/** 
		 تابع جاری تاریخ مورد نظر را به صورت کامل و رشته ای برمیگرداند مانند شنبه سی ام مهر ماه سال 1384
	 
	 @param VDateTime تاریخ مورد نظر
	 @return تاریخ کامل به صورت رشته فارسی
	*/
	public static String CompleteDateName(LocalDateTime VDateTime) //{1383 اول فروردين ماه سال}
	{
		TShamsiDate Result = DecodeDate(VDateTime);
		return DayName((short)(VDateTime.getDayOfWeek().getValue())) + " " + Rasad.Core.Extensions.TNumberHelper.ToWord(Result.getDay(), true) + " " + getLongMonthNames()[Result.getMonth() - 1] + " ماه " + " سال " + Result.getYear();
	}

	/** 
		 تابع جاری تاریخ مورد نظر را به صورت کامل و رشته ای برمیگرداند مانند شنبه سی ام مهر ماه سال 1384
	 
	 @param ShamsiDate تاریخ مورد نظر
	 @return تاریخ کامل به صورت رشته فارسی
	*/
	public static String CompleteDateName(String ShamsiDate)
	{
		return CompleteDateName(ToDateTime(ShamsiDate));
	}

	/** 
		 اين تابع يک تاريخ شمسي از جنس رشته دريافت نموده و مشخص مي نمايد که تاريخ مورد نظر معتبر است يا خير
	 
	 @param TarikhShamsi تاريخ رشته اي مورد نظر
	 @return معتبر است يا خير
	*/

	public static boolean IsDateValid(String ShamsiDate, String dateSeparator)
	{
		return IsDateValid(ShamsiDate, dateSeparator, false);
	}

	public static boolean IsDateValid(String ShamsiDate)
	{
		return IsDateValid(ShamsiDate, "/", false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static bool IsDateValid(string ShamsiDate, string dateSeparator = "/", bool CanEmpty = false)
	public static boolean IsDateValid(String ShamsiDate, String dateSeparator, boolean CanEmpty)
	{
		if (tangible.StringHelper.isNullOrEmpty(ShamsiDate))
		{
			return CanEmpty;
		}
		String[] DateParts = ShamsiDate.split(java.util.regex.Pattern.quote(dateSeparator), -1);
		if (DateParts.length != 3)
		{
			return false;
		}
		String year = DateParts[0].trim();
		String month = DateParts[1].trim();
		String day = DateParts[2].trim();

		if (day.length() < 2)
		{
			return false;
		}
		if (month.length() < 2)
		{
			return false;
		}
		if (year.length() < 4)
		{
			return false;
		}
		int YearInt;
		int MonthInt;
		int DayInt;
		tangible.OutObject<Integer> tempOut_YearInt = new tangible.OutObject<Integer>();
		if (!tangible.TryParseHelper.tryParseInt(year, tempOut_YearInt))
		{
		YearInt = tempOut_YearInt.argValue;
			return false;
		}
	else
	{
		YearInt = tempOut_YearInt.argValue;
	}
		tangible.OutObject<Integer> tempOut_MonthInt = new tangible.OutObject<Integer>();
		if (!tangible.TryParseHelper.tryParseInt(month, tempOut_MonthInt))
		{
		MonthInt = tempOut_MonthInt.argValue;
			return false;
		}
	else
	{
		MonthInt = tempOut_MonthInt.argValue;
	}
		tangible.OutObject<Integer> tempOut_DayInt = new tangible.OutObject<Integer>();
		if (!tangible.TryParseHelper.tryParseInt(day, tempOut_DayInt))
		{
		DayInt = tempOut_DayInt.argValue;
			return false;
		}
	else
	{
		DayInt = tempOut_DayInt.argValue;
	}

		if (MonthInt < 1 || MonthInt > 12)
		{
			return false;
		}
		if (DayInt < 1 || DayInt > 31)
		{
			return false;
		}
		if (DayInt > 29 && MonthInt == 12 && !IsLeapYear(YearInt))
		{
			return false;
		}
		return true;
	}


	public static String CoerceDate(String ShamsiDate)
	{
		return CoerceDate(ShamsiDate, "/");
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static string CoerceDate(string ShamsiDate, string dateSeparator = "/")
	public static String CoerceDate(String ShamsiDate, String dateSeparator)
	{
		if (tangible.StringHelper.isNullOrEmpty(ShamsiDate))
		{
			return ShamsiDate;
		}
		ShamsiDate = ShamsiDate.trim();
		Integer Year = null;
		Integer Month = null;
		Integer Day = null;
		int yearVal;
		int monthVal;
		int dayVal;
		if (ShamsiDate.IsInteger())
		{
			if (ShamsiDate.length() == 6 && !ShamsiDate.startsWith("13"))
			{
				ShamsiDate = "13" + ShamsiDate;
			}
			if (ShamsiDate.length() == 8)
			{
				tangible.OutObject<Integer> tempOut_yearVal = new tangible.OutObject<Integer>();
				if (tangible.TryParseHelper.tryParseInt(ShamsiDate.substring(0, 4), tempOut_yearVal))
				{
				yearVal = tempOut_yearVal.argValue;
					Year = yearVal;
				}
			else
			{
				yearVal = tempOut_yearVal.argValue;
			}

				tangible.OutObject<Integer> tempOut_monthVal = new tangible.OutObject<Integer>();
				if (tangible.TryParseHelper.tryParseInt(ShamsiDate.substring(4, 6), tempOut_monthVal))
				{
				monthVal = tempOut_monthVal.argValue;
					Month = monthVal;
				}
			else
			{
				monthVal = tempOut_monthVal.argValue;
			}

				tangible.OutObject<Integer> tempOut_dayVal = new tangible.OutObject<Integer>();
				if (tangible.TryParseHelper.tryParseInt(ShamsiDate.substring(6, 8), tempOut_dayVal))
				{
				dayVal = tempOut_dayVal.argValue;
					Day = dayVal;
				}
			else
			{
				dayVal = tempOut_dayVal.argValue;
			}
			}
		}
		else
		{
			if (ShamsiDate.length() == 8 && !ShamsiDate.startsWith("13"))
			{
				ShamsiDate = "13" + ShamsiDate;
			}
			String[] parts = ShamsiDate.split(java.util.regex.Pattern.quote(dateSeparator), -1);
			if (parts.length == 3)
			{
				tangible.OutObject<Integer> tempOut_yearVal2 = new tangible.OutObject<Integer>();
				if (tangible.TryParseHelper.tryParseInt(parts[0], tempOut_yearVal2))
				{
				yearVal = tempOut_yearVal2.argValue;
					Year = yearVal;
				}
			else
			{
				yearVal = tempOut_yearVal2.argValue;
			}

				tangible.OutObject<Integer> tempOut_monthVal2 = new tangible.OutObject<Integer>();
				if (tangible.TryParseHelper.tryParseInt(parts[1], tempOut_monthVal2))
				{
				monthVal = tempOut_monthVal2.argValue;
					Month = monthVal;
				}
			else
			{
				monthVal = tempOut_monthVal2.argValue;
			}

				tangible.OutObject<Integer> tempOut_dayVal2 = new tangible.OutObject<Integer>();
				if (tangible.TryParseHelper.tryParseInt(parts[2], tempOut_dayVal2))
				{
				dayVal = tempOut_dayVal2.argValue;
					Day = dayVal;
				}
			else
			{
				dayVal = tempOut_dayVal2.argValue;
			}
			}
		}
		if (Year != null && Month != null && Day != null)
		{
			ShamsiDate = String.format("%2$s%1$s%3$s%1$s%4$s", dateSeparator, (new Integer(Year.intValue())).toString("0000"), (new Integer(Month.intValue())).toString("00"), (new Integer(Day.intValue())).toString("00"));
		}
		return ShamsiDate;
	}

	/** 
		 اين تابع مشخص مي کند که يک روز خاص از يک ماه خاص شمسي تعطيل هست يا نه
	 
	 @param Month ماه شمسي
	 @param Day سال شمسي
	 @return bool
	*/
	public static boolean IsTateel(int month, int day)
	{
		boolean Result = false;
		if ((month == 1) && ((day == 1) || (day == 2) || (day == 3) || (day == 4) || (day == 12) || (day == 13)))
		{
			Result = true;
		}
		if ((month == 3) && ((day == 14) || (day == 15)))
		{
			Result = true;
		}
		if ((month == 11) && (day == 22))
		{
			Result = true;
		}
		if ((month == 12) && (day == 29))
		{
			Result = true;
		}
		return Result;
	}

	/** 
		 اين تابع مشخص مي کند که يک تاریخ خاص تعطيل هست يا نه
	 
	 @param VDateTime تاریخ مورد نظر
	 @return تعطیل هست یا نه
	*/
	public static boolean IsTateel(LocalDateTime VDateTime)
	{
		TShamsiDate Result = new TShamsiDate(VDateTime);
		return IsTateel(Result.getMonth(), Result.getDay());
	}

	/** 
		 این تابع تعیین مینماید که تاریخ مورد نظر روز جمعه میباشد یا خیر
	 
	 @param VDateTime تاریخ مورد نظر
	 @return جمه هست یا خیر
	*/
	public static boolean IsFriday(LocalDateTime VDateTime)
	{
		return VDateTime.getDayOfWeek() == DayOfWeek.FRIDAY;
	}

	public static boolean IsFriday(String VDateTime)
	{
		return IsFriday(ToDateTime(VDateTime));
	}

	/** 
		 تابع جاری تعداد روزهای مابین 2 تاریخ را محاسبه می نماید
	 
	 @param StartDate تاریخ شروع
	 @param EndDate تاریخ پایان
	 @return تعداد روز های بین 2 تاریخ
	*/
	public static int DifDays(LocalDateTime StartDate, LocalDateTime EndDate)
	{
		TimeSpan difference = StartDate - EndDate;
		return difference.Days;
	}

	/** 
		 تابع جاری تعداد روزهای مابین 2 تاریخ را محاسبه می نماید
	 
	 @param StartDateSh تاریخ شروع
	 @param EndDateSh تاریخ پایان
	 @return تعداد روز های بین 2 تاریخ
	*/
	public static int DifDays(String StartDateSh, String EndDateSh)
	{
		TimeSpan difference = ToDateTime(EndDateSh) - ToDateTime(StartDateSh);
		return difference.Days;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Year Functions

	/** 
		 (365) اين تابع مشخص مي کند که سال شمسي مورد نظر کبيسه(366) هست يا نه
	 
	 @param ShYear 1378 سال مورد نظر
	 @return کبيسه بودن آن سال
	*/
	public static boolean IsLeapYear(int year)
	{
		double V1;
		if (year > 0)
		{
			V1 = ((((year - 474) % 2820) + 474) + 38) * 682;
		}
		else
		{
			V1 = ((((year - 473) % 2820) + 474) + 38) * 682;
		}
		return ((V1 % 2816) < 682);
	}


	/** 
		 اين تابع يک تاريخ ميلادي سيستم از کاربر دريافت نموده و سال شمسي آنرا بر مي گرداند
	 
	 @param VDateTime سال ميلادي سيستم
	 @return عدد سال شمسي
	*/
	public static int YearOf(LocalDateTime VDateTime)
	{
		return (new TShamsiDate(VDateTime)).getYear();
	}

	/** 
		 اين تابع يک تاريخ ميلادي سيستم از کاربر دريافت نموده و سال شمسي آنرا بر مي گرداند
	 
	 @param ShamsiDate سال ميلادي سيستم
	 @return عدد سال شمسي
	*/
	public static int YearOf(String ShamsiDate)
	{
		return (int)(ShamsiDate.split(java.util.regex.Pattern.quote(String.valueOf(getDateSeparator())), -1)[0]);
	}


	/** 
		 اين تابع اولين روز سال شمسي را از تاريخ ارسالي استخراج نموده و معادل تاريخ ميلادي آنروز را برميگرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return تاريخ اولين روز سال بصورت ميلادي
	*/
	public static LocalDateTime FirstDayOfYear(LocalDateTime VDateTime)
	{
		return EncodeDate(YearOf(VDateTime), 1, 1);
	}

	/** 
		 اين تابع اولين روز سال شمسي را از تاريخ ارسالي استخراج نموده و معادل تاريخ ميلادي آنروز را برميگرداند
	 
	 @param ShamsiDate تاريخ مورد نظر
	 @return تاريخ اولين روز سال بصورت ميلادي
	*/
	public static LocalDateTime FirstDayOfYear(String ShamsiDate)
	{
		return FirstDayOfYear(ToDateTime(ShamsiDate));
	}


	/** 
		 آخرین روز از سال تاریخ مورد نظر را بر می گرداند
	 
	 @param VDateTime تاریخ مورد نظر
	 @return آخرین روز از سال
	*/
	public static String LastDayOfYear(LocalDateTime VDateTime)
	{
		return LastDayOfYear(YearOf(VDateTime));
	}

	/** 
		 آخرین روز از سال تاریخ مورد نظر را بر می گرداند
	 
	 @param ShamsiDate تاریخ مورد نظر
	 @return آخرین روز از سال
	*/
	public static String LastDayOfYear(String ShamsiDate)
	{
		return LastDayOfYear(ToDateTime(ShamsiDate));
	}


	/** 
		 آخرین روز از سال ، سال مورد نظر را بر می گرداند
	 
	 @param ShYear سال مورد نظر
	 @return آخرین روز از سال
	*/
	public static String LastDayOfYear(int year)
	{
		if (IsLeapYear(year))
		{
			return FormatDate(year, 12, 30);
		}
		return FormatDate(year, 12, 29);
	}

	/** 
		 اين تابع تعداد روز هاي يک سال را بر اساس کبيسه بودن يا نبودن بر ميگرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return تعداد روز هاي سال
	*/
	public static int DaysInYear(LocalDateTime VDateTime)
	{
		return (short)(IsLeapYear(YearOf(VDateTime)) ? 366 : 365);
	}

	/** 
		 اين تابع تعداد روز هاي يک سال را بر اساس کبيسه بودن يا نبودن بر ميگرداند
	 
	 @param ShamsiDate تاريخ مورد نظر
	 @return تعداد روز هاي سال
	*/
	public static int DaysInYear(String ShamsiDate)
	{
		return DaysInYear(ToDateTime(ShamsiDate));
	}

	/** 
		 اين تابع تعداد روزهاي گذشته از سال را بر مي گرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return تعداد روز هاي گذشته از سال
	*/
	public static int GetDayOfYear(LocalDateTime VDateTime)
	{
		TimeSpan VTimeSpan = new TimeSpan();
		VTimeSpan = VDateTime - FirstDayOfYear(VDateTime);
		return (short)(VTimeSpan.Days);
	}

	/** 
		 اين تابع تعداد روزهاي گذشته از سال را بر مي گرداند
	 
	 @param ShamsiDate تاريخ مورد نظر
	 @return تعداد روز هاي گذشته از سال
	*/
	public static int GetDayOfYear(String ShamsiDate)
	{
		return GetDayOfYear(ToDateTime(ShamsiDate));
	}

	/** 
		 اين تابع تعداد روزهاي مانده به انتهاي سال را محاسبه نموده و برميگرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return تعداد روزهاي مانده به انتهاي سال
	*/
	public static int DaysRemainedYear(LocalDateTime VDateTime)
	{
		return (short)(DaysInYear(VDateTime) - 1 - GetDayOfYear(VDateTime));
	}

	/** 
		 اين تابع تعداد روزهاي مانده به انتهاي سال را محاسبه نموده و برميگرداند
	 
	 @param ShamsiDate تاريخ مورد نظر
	 @return تعداد روزهاي مانده به انتهاي سال
	*/
	public static int DaysRemainedYear(String ShamsiDate)
	{
		return DaysRemainedYear(ToDateTime(ShamsiDate));
	}

	public static String AddYears(LocalDateTime VDateTime, int value)
	{
		return AddYears(DateToStr(VDateTime), value);
	}

	/** 
		 تابع جاری به یک تاریخ خاص تعدادی سال اضافه یا کم میکند
	 
	 @param ShamsiDate تاریخ شمسی مورد نظر
	 @param value تعداد سالهای مورد نظر : مثبت یا منفی
	 @return تاریخ شمسی جدید
	*/
	public static String AddYears(String ShamsiDate, int value)
	{
		String[] Allparts = ShamsiDate.split(java.util.regex.Pattern.quote(String.valueOf(getDateSeparator())), -1);
		int Year = Integer.parseInt(Allparts[0]);
		int Month = Integer.parseInt(Allparts[1]);
		int Day = Integer.parseInt(Allparts[2]);
		boolean IsSourceLeapYear = ((Month == 12) && (Day == 30));
		Year += value;
		if (IsSourceLeapYear)
		{
			if (!IsLeapYear((short)Year))
			{
				Day = 29;
			}
		}
		return (new Integer(Year)).toString("0000") + getDateSeparator() + (new Integer(Month)).toString("00") + getDateSeparator() + (new Integer(Day)).toString("00");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Month Functions

	/** 
		 اين تابع يک تاريخ ميلادي سيستم را از کاربر دريافت نموده و ماه شمسي آنرا برمي گرداند
	 
	 @param VDateTime تاريخ ميلادي سيستم
	 @return عدد ماه شمسي
	*/
	public static int MonthOf(LocalDateTime VDateTime)
	{
		return (new TShamsiDate(VDateTime)).getMonth();
	}

	public static int MonthOf(String ShamsiDate)
	{
		return (int)(ShamsiDate.split(java.util.regex.Pattern.quote(String.valueOf(getDateSeparator())), -1)[1]);
	}

	/** 
		 تابع جاری یک تاریخ شمسی را از کاربر دریافت نموده و به آن تعدادی ماه اضافه یا کم میکند
	 
	 @param ShamsiDate تاریخ شمسی مورد نظر
	 @param NumberOfMonths تعداد ماههای مورد نظر : منفی یا مثبت
	 @return تاریخ جدید
	*/
	public static String AddMonths(String ShamsiDate, int NumberOfMonths)
	{
		return AddMonths(ToDateTime(ShamsiDate), NumberOfMonths);
	}

	/** 
		 تابع جاری یک تاریخ را از کاربر دریافت نموده و به آن تعدادی ماه اضافه یا کم میکند
	 
	 @param VDateTime تاریخ مورد نظر
	 @param NumberOfMonths تعداد ماههای مورد نظر : منفی یا مثبت
	 @return تاریخ جدید
	*/
	public static String AddMonths(LocalDateTime VDateTime, int NumberOfMonths)
	{
		return DateToStr(IncMonthA(VDateTime, NumberOfMonths));
	}

	/** 
		 تعداد ماه مورد نظر را از تاريخ ورودي کسر مي نمايد يا به آن اضافه مي کند که بستگي به عدد ورودي دارد مثبت يا منفي
	 
	 @param VDateTime تاريخ ورودي
	 @param NumberOfMonths تعداد ماه (عدد مثبت يا منفي)
	 @return 
	*/
	private static LocalDateTime IncMonthA(LocalDateTime VDateTime, int NumberOfMonths)
	{
		TShamsiDate Result = new TShamsiDate(VDateTime);
		int V_Y = Result.getYear();
		int V_M = Result.getMonth();
		int V_D = Result.getDay();
		tangible.RefObject<Integer> tempRef_V_Y = new tangible.RefObject<Integer>(V_Y);
		tangible.RefObject<Integer> tempRef_V_M = new tangible.RefObject<Integer>(V_M);
		tangible.RefObject<Integer> tempRef_V_D = new tangible.RefObject<Integer>(V_D);
		IncMonthB(tempRef_V_Y, tempRef_V_M, tempRef_V_D, NumberOfMonths);
	V_D = tempRef_V_D.argValue;
	V_M = tempRef_V_M.argValue;
	V_Y = tempRef_V_Y.argValue;
		return EncodeDate(V_Y, V_M, V_D);
	}


	/** 
		 اين تابع يک تاريخ از کاربر دريافت نموده و اولين روز ماه آن تاريخ را بر ميگرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return اولين روز ماه تاريخ مورد نظر
	*/
	public static LocalDateTime FirstDayOfMoon(LocalDateTime VDateTime)
	{
		TShamsiDate Result = new TShamsiDate(VDateTime);
		return (new TShamsiDate(Result.getYear(), Result.getMonth(), 1)).getDateTime();
	}

	/** 
		 اين تابع يک تاريخ از کاربر دريافت نموده و اولين روز ماه آن تاريخ را بر ميگرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return اولين روز ماه تاريخ مورد نظر
	*/
	public static LocalDateTime FirstDayOfMoon(String ShamsiDate)
	{
		return FirstDayOfMoon(ToDateTime(ShamsiDate));
	}

	/** 
		 اين تابع يک تاريخ از کاربر دريافت نموده و اولين روز ماه آن تاريخ را بر ميگرداند
	 
	 @param ShamsiDate تاريخ مورد نظر
	 @return اولين روز ماه تاريخ مورد نظر
	*/
	public static String FirstDayOfMoonB(String ShamsiDate)
	{
		int Y = 0, M = 0, D = 0;
		String[] DateParts = ShamsiDate.split(java.util.regex.Pattern.quote(String.valueOf(getDateSeparator())), -1);
		Y = Integer.parseInt(DateParts[0]);
		M = Integer.parseInt(DateParts[1]);
		D = 1;
		return (new Integer(Y)).toString("0000") + getDateSeparator() + (new Integer(M)).toString("00") + getDateSeparator() + (new Integer(D)).toString("00");
	}


	/** 
		 اين تابع يک عدد ماه را از کاربر دريافت نموده و رشته فارسي نام آن ماه را برميگرداند
	 
	 @param Month شماره ماه
	 @return نام ماه
	*/
	public static String MonthName(short Month) //{فروردين ... اسفند}
	{
		return getLongMonthNames()[Month - 1];
	}


	/** 
		 اين تابع يک تاريخ از کاربر دريافت نموده و تاريخ آخرین روز آن ماه را برميگرداند
	 
	 @param VDateTime تاريخ ورودي
	 @return تاریخ آخرین روز ماه
	*/
	public static LocalDateTime LastDayOfMoon(LocalDateTime VDateTime)
	{
		TShamsiDate Result = new TShamsiDate(VDateTime);
		return LastDayOfMoon(Result.getYear(), Result.getMonth());
	}

	/** 
		 اين تابع يک تاريخ از کاربر دريافت نموده و تاريخ آخرین روز آن ماه را برميگرداند
	 
	 @param ShamsiDate تاريخ ورودي
	 @return تاریخ آخرین روز ماه
	*/
	public static LocalDateTime LastDayOfMoon(String ShamsiDate)
	{
		return LastDayOfMoon(ToDateTime(ShamsiDate));
	}

	/** 
		 این تابع سال و ماه را از کاربر دریافت نموده و تاریخ میلادی آخرین روز آن ماه را برمی گرداند
	 
	 @param year سال مورد نظر
	 @param month ماه مورد نظر
	 @return تاریخ میلادی
	*/
	public static LocalDateTime LastDayOfMoon(int year, int month)
	{
		short shDay = 31;
		if (month < 7)
		{
			shDay = 31;
		}
		else if (month > 6 && month < 12)
		{
			shDay = 30;
		}
		else
		{
			boolean IsLeap = IsLeapYear(year);
			shDay = (IsLeap) ? (short)30 : (short)29;
		}
		return EncodeDate(year, month, shDay);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte MonthDays(string ShamsiDateStr)
	public static byte MonthDays(String ShamsiDateStr)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte Result = 0;
		byte Result = 0;
		String[] DateParts = ShamsiDateStr.split(java.util.regex.Pattern.quote(String.valueOf(getDateSeparator())), -1);
		short YearNumber = Short.parseShort(DateParts[0]);
		short MonthNumber = Short.parseShort(DateParts[1]);
		if (MonthNumber <= 6 && MonthNumber >= 1)
		{
			Result = 31;
		}
		else if (MonthNumber >= 7 && MonthNumber <= 11)
		{
			Result = 30;
		}
		else
		{
			if (IsLeapYear(YearNumber))
			{
				Result = 30;
			}
			else
			{
				Result = 29;
			}
		}
		return Result;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte MonthDays(DateTime VDateTime)
	public static byte MonthDays(LocalDateTime VDateTime)
	{
		return MonthDays(DateToStr(VDateTime));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Week Functions

	/** 
		 اين متد يک تاريخ ميلادي از کاربر دريافت نموده و مشخص مي کند که در هفته چندم سال شمسي واقع کرديده
	 
	 @param VDateTime تاريخ ميلادي
	 @return هفته چندم
	*/
	public static int WeekOf(LocalDateTime VDateTime)
	{
		// تعريف فيلدها
		LocalDateTime VFD = LocalDateTime.MIN;
		TimeSpan PassedDays = new TimeSpan();
		// محاسبه افست اولين جمعه سال
		VFD = FirstDayOfYear(VDateTime);
		TimeSpan FirstHollyDayOfYearOffset = TimeSpan.FromDays(DaysRemainedWeek(VFD) + 1);

		//DaysPassedYear
		PassedDays = (VDateTime - VFD).Add(TimeSpan.FromDays(1));

		//
		if (PassedDays.Days > FirstHollyDayOfYearOffset.Days) //We Passed First Week Of Year
		{
			PassedDays = PassedDays.Subtract(FirstHollyDayOfYearOffset).Subtract(TimeSpan.FromDays(1));
			return ((int)(Math.floor((double)(PassedDays.Days / 7)))) + 2;
		}
		return ((int)(Math.floor((double)(PassedDays.Days / 7)))) + 1;
	}

	/** 
		 اين متد يک تاريخ شمسی از کاربر دريافت نموده و مشخص مي کند که در هفته چندم سال شمسي واقع کرديده
	 
	 @param SourceShamsiDate تاريخ شمسی
	 @return هفته چندم
	*/
	public static int WeekOf(String SourceShamsiDate)
	{
		return WeekOf(ToDateTime(SourceShamsiDate));
	}

	/** 
		 اين تابع يک تاريخ شمسی را دريافت نموده و مشخص ميکند که در روز چندم هفته شمسي قرار دارد
		 بطور مثال روز شنبه شمسي معادل عدد 0 ميباشد
	 
	 @param VDateTime تاريخ شمسی
	 @return روز چندم هفته
	*/
	public static DayOfWeek DayOfWeek(String SourceShamsiDate) // {6...0}
	{
		return ToDateTime(SourceShamsiDate).getDayOfWeek();
	}

	/** 
		 اين تابع يک تاريخ از کاربر دريافت نموده و اولين روز هفته آن تاريخ را برمي گرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return اولين روز هفته تاريخ مورد نظر
	*/
	public static LocalDateTime FirstDayOfWeek(LocalDateTime VDateTime)
	{
		TimeSpan VTimeSpan = new TimeSpan(VDateTime.getDayOfWeek().getValue(), 0, 0, 0, 0);
		return VDateTime.Subtract(VTimeSpan);
	}

	/** 
		 اين تابع يک تاريخ از کاربر دريافت نموده و اولين روز هفته آن تاريخ را برمي گرداند
	 
	 @param SourceShamsiDate تاريخ مورد نظر
	 @return اولين روز هفته تاريخ مورد نظر
	*/
	public static LocalDateTime FirstDayOfWeek(String SourceShamsiDate)
	{
		return FirstDayOfWeek(ToDateTime(SourceShamsiDate));
	}


	/** 
		 اين تابع شماره يک هفته از يک سال خاص را از کابر دريافت کرده و تاريخ ابتداوانتهاي آن هفته را بر مي گرداند
	 
	 @param ShamsiWeekNo
	 @param Year سال مورد نظر
	 @param StrStartDate تاريخ شروع هفته
	 @param StrEndDate تاريخ پايان هفته
	*/
	public static void WeekNoToRangeDate(short ShamsiWeekNo, short Year, tangible.RefObject<String> StrStartDate, tangible.RefObject<String> StrEndDate)
	{
		LocalDateTime FirstDayOfYear = EncodeDate(Year, 1, 1);
		int VDayOfWeek = FirstDayOfYear.getDayOfWeek().getValue();
		LocalDateTime StartDay = LocalDateTime.MIN;
		LocalDateTime EndDay = LocalDateTime.MIN;
		if (ShamsiWeekNo == 1)
		{
			StartDay = FirstDayOfYear.plusDays(-VDayOfWeek + 1);
		}
		else
		{
			StartDay = FirstDayOfYear.plusDays((ShamsiWeekNo - 2) * 7 + (7 - (VDayOfWeek - 1)));
		}
		EndDay = StartDay.plusDays(6);
		StrStartDate.argValue = DateToStr(StartDay);
		StrEndDate.argValue = DateToStr(EndDay);
	}


	/** 
		 اين تابع تعداد روزهاي مانده به انتهاي هفته را بر مي گرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return تعداد روزهاي مانده به انتهاي هفته
	*/
	public static short DaysRemainedWeek(LocalDateTime VDateTime)
	{
		return getWeekDaysRemained()[(short)VDateTime.getDayOfWeek().getValue()];
	}

	/** 
		 اين تابع تعداد روزهاي مانده به انتهاي هفته را بر مي گرداند
	 
	 @param SourceShamsiDate تاريخ مورد نظر
	 @return تعداد روزهاي مانده به انتهاي هفته
	*/
	public static short DaysRemainedWeek(String SourceShamsiDate)
	{
		return DaysRemainedWeek(ToDateTime(SourceShamsiDate));
	}


	/** 
		 اين تابع يک تاريخ از کاربر دريافت نموده و تاريخ روز جمعه همان هفته را برميگرداند
	 
	 @param VDateTime تاريخ ورودي
	 @return تاريخ روز جمعه
	*/
	public static LocalDateTime LastDayOfWeek(LocalDateTime VDateTime)
	{
		return VDateTime.plusDays(DaysRemainedWeek(VDateTime));
	}

	/** 
		 اين تابع يک تاريخ از کاربر دريافت نموده و تاريخ روز جمعه همان هفته را برميگرداند
	 
	 @param SourceShamsiDate تاريخ ورودي
	 @return تاريخ روز جمعه
	*/
	public static LocalDateTime LastDayOfWeek(String SourceShamsiDate)
	{
		return LastDayOfWeek(ToDateTime(SourceShamsiDate));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Day Functions

	/** 
		 تابع مورد نظر تعداد مشخصی روز را به تاریخ مورد نظر اضافه نموده و نتیجه را باز می گرداند.
	 
	 @param VDateTime تاریخ مبدا 
	 @param AddedDays تعدا روز مورد نظر جهت اضافه شدن به تاریخ مورد نظر
	 @return تاریخ جدید
	*/
	public static String AddDays(LocalDateTime VDateTime, double AddedDays)
	{
		VDateTime = VDateTime.plusDays(AddedDays);
		return DateToStr(VDateTime);
	}

	/** 
		 تابع مورد نظر تعداد مشخصی روز را به تاریخ مورد نظر اضافه نموده و نتیجه را باز می گرداند.
	 
	 @param SourceShamsiDate تاریخ مبدا با ####/##/## و یا ##/##/## فرمت
	 @param AddedDays تعدا روز مورد نظر جهت اضافه شدن به تاریخ مورد نظر
	 @return تاریخ جدید
	*/
	public static String AddDays(String SourceShamsiDate, double AddedDays)
	{
		return AddDays(ToDateTime(SourceShamsiDate), AddedDays);
	}

	/** 
		 تابع مورد نظر تعداد مشخصی روز را به تاریخ مورد نظر اضافه نموده و نتیجه را باز می گرداند.
	 
	 @param SourceShamsiDate تاریخ مبدا با فرمت عددی
	 @param AddedDays تعدا روز مورد نظر جهت اضافه شدن به تاریخ مورد نظر
	 @return تاریخ جدید
	*/
	public static int AddDays(int SourceShamsiDate, double AddedDays)
	{
		return Integer.parseInt(AddDays((new Integer(SourceShamsiDate)).toString(getShamsiDateFormat()), AddedDays).replace(String.valueOf(getDateSeparator()), ""));
	}


	/** 
		 تابع جاري يک تاريخ ميلادي سيستم از کاربر دريافت نموده و روز شمسي آنرا مشخص مي نمايد
	 
	 @param VDateTime تاريخ ميلادي سيستم
	 @return عدد روز شمسي
	*/
	public static int DayOf(LocalDateTime VDateTime)
	{
		return (new TShamsiDate(VDateTime)).getDay();
	}

	public static int DayOf(String SourceShamsiDate)
	{
		return (int)(SourceShamsiDate.split(java.util.regex.Pattern.quote(String.valueOf(getDateSeparator())), -1)[2]);
	}

	public static int DayOf(int SourceShamsiDate)
	{
		return DayOf((new Integer(SourceShamsiDate)).toString(getShamsiDateFormat()));
	}

	/** 
		 نام روز هفته مورد نظر را باز میگرداند
	 
	 @param day عدد روز هفته مورد از پایه صفر
	 @return شنبه ... جمعه
	*/
	public static String DayName(int day) // {شنبه ... جمعه}
	{
		if (day > getLongestWeekDayNames().length || day < 0)
		{
			return "؟";
		}
		return getLongestWeekDayNames()[day];
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Calendar Functions

	/** 
		 این تابع یک تاریخ میلادی از کاربر دریافت نموده و اولین روز از تقویم ماه تاریخ مورد نظر را برمی گرداند
	 
	 @param VDateTime
	 @return اولین روز از تقویم ماه تاریخ مورد نظر
	*/
	public static LocalDateTime FirstDayOfCalendar(LocalDateTime VDateTime)
	{
		return FirstDayOfWeek(FirstDayOfMoon(VDateTime));
	}

	/** 
		 این تابع یک تاریخ از کاربر دریافت نموده و اولین روز از تقویم ماه تاریخ مورد نظر را برمی گرداند
	 
	 @param SourceShamsiDate
	 @return اولین روز از تقویم ماه تاریخ مورد نظر
	*/
	public static LocalDateTime FirstDayOfCalendar(String SourceShamsiDate)
	{
		return FirstDayOfCalendar(ToDateTime(SourceShamsiDate));
	}

	/** 
		 این تابع یک تاریخ میلادی از کاربر دریافت نموده و آخرین روز از تقویم ماه تاریخ مورد نظر را برمی گرداند
	 
	 @param VDateTime تاریخ مورد نظر
	 @return آخرین روز از تقویم ماه تاریخ مورد نظر
	*/
	public static LocalDateTime LastDayOfCalendar(LocalDateTime VDateTime)
	{
		return LastDayOfWeek(LastDayOfMoon(VDateTime));
	}

	/** 
		 این تابع یک تاریخ میلادی از کاربر دریافت نموده و آخرین روز از تقویم ماه تاریخ مورد نظر را برمی گرداند
	 
	 @param SourceShamsiDate تاریخ مورد نظر
	 @return آخرین روز از تقویم ماه تاریخ مورد نظر
	*/
	public static LocalDateTime LastDayOfCalendar(String SourceShamsiDate)
	{
		return LastDayOfCalendar(ToDateTime(SourceShamsiDate));
	}

	/** 
		 این تابع روزهای یک صفحه از یک ماه از سال را برمیگرداند
	 
	 @return 
	*/
	public static CalendarDay[] GetCalendarDays(int year, int month)
	{
		LocalDateTime StartDate = EncodeDate(year, month, 1);
		LocalDateTime FirstDateOfCal = FirstDayOfCalendar(StartDate);
		LocalDateTime LastDateOfCal = LastDayOfCalendar(StartDate);
		LocalDateTime CurrentDate = FirstDateOfCal;
		List Result = new ArrayList();
		int CurrentMonth = 0;
		for (int i = 1; i <= 42; i++)
		{
			CurrentMonth = MonthOf(CurrentDate);
			boolean IsOtherMonth = (month != CurrentMonth);
			CalendarDay CDay = new CalendarDay(CurrentDate, IsOtherMonth);
			Result.add(CDay);
			CurrentDate = CurrentDate.plusDays(1);
		}
		CalendarDay[] FinalResult = new CalendarDay[Result.size()];
		Result.CopyTo(FinalResult, 0);
		return FinalResult;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Format Date Functions

	/** 
		 این تابع یک عدد را دریافت نموده و به فرمت ####/##/## در می آورد
	 
	 @param NumberDate تاریخ عددی
	 @return ####/##/## تاریخ با فرمت
	*/
	public static String FormatDate(Object NumberDate)
	{
		String Result = "";
		String SendedDate = String.valueOf(NumberDate);
		if (SendedDate.length() == 8)
		{
			Result = SendedDate.substring(0, 4) + getDateSeparator() + SendedDate.substring(4, 6) + getDateSeparator() + SendedDate.substring(6, 8);
		}
		else if (SendedDate.length() == 6)
		{
			Result = SendedDate.substring(0, 2) + getDateSeparator() + SendedDate.substring(2, 4) + getDateSeparator() + SendedDate.substring(4, 6);
		}
		else
		{
			Result = SendedDate;
		}
		return Result;
	}

	public static String FormatDate(String TarikhShamsi)
	{
		return FormatDate(TarikhShamsi, String.valueOf(getDateSeparator()));
	}

	/** 
		 اين تابع يک تاريخ شمسي بصورت 6 يا 8 رقمي دريافت نموده و به فرمت استرينگ تاريخ در مي آورد
	 
	 @param TarikhShamsi
	 @return 
	*/
	public static String FormatDate(String TarikhShamsi, String dateSeparator)
	{
		String Result = RemoveDateSeparator(TarikhShamsi, dateSeparator);
		int DateInt = Integer.parseInt(Result);
		return FormatDate(DateInt, dateSeparator);
	}

	public static String FormatDate(int TarikhShamsi)
	{
		return FormatDate(TarikhShamsi, String.valueOf(getDateSeparator()));
	}

	/** 
		 این تابع یک تاریخ شمسی عددی را دریافت نموده و به فرمت ####/##/## ویا ##/##/## در می آورد
	 
	 @param TarikhShamsi تاریخ شمسی با فرمت عددی 6 یا 8 رقمی
	 @return تاریخ رشته ای فرمت شده
	*/
	public static String FormatDate(int TarikhShamsi, String dateSeparator)
	{
		int DateLength = String.valueOf(TarikhShamsi).length();
		if (DateLength == 8)
		{
			return (new Integer(TarikhShamsi)).toString("####" + dateSeparator + "##" + dateSeparator + "##");
		}
		if (DateLength == 6)
		{
			return (new Integer(TarikhShamsi)).toString("##" + dateSeparator + "##" + dateSeparator + "##");
		}
		throw new RuntimeException("تاریخ جهت فرمت کردن معتبر نیست فقط 6 یا 8 رقمی مجاز می باشد");
	}

	public static String FormatDate(Object Year, Object Month, Object Day)
	{
		return FormatDate(Year, Month, Day, String.valueOf(getDateSeparator()));
	}

	/** 
		 اين تابع سال و ماه و روز شمسي را از کاربر به هر فرمتي دريافت کرده و يک رشته مثل 01/02/1378 برمي گرداند
	 
	 @param Year سال شمسي
	 @param Month ماه شمسي
	 @param Day روز شمسي
	 @return رشته تاريخ
	*/
	public static String FormatDate(Object Year, Object Month, Object Day, String dateSeparator)
	{
	//{1378,7,1 ==> "1378/07/01"}
		int Y_S, M_S, D_S;
		Y_S = (Integer)Year;
		M_S = (Integer)Month;
		D_S = (Integer)Day;
		String Sep = tangible.StringHelper.isNullOrEmpty(dateSeparator) ? String.valueOf(getDateSeparator()) : dateSeparator;
		return (new Integer(Y_S)).toString("0000") + Sep + (new Integer(M_S)).toString("00") + Sep + (new Integer(D_S)).toString("00");
	}

	public static String RemoveDateSeparator(LocalDateTime VDateTime)
	{
		return RemoveDateSeparator(VDateTime, String.valueOf(getDateSeparator()));
	}

	/** 
		 تابع جاري يک تاريخ سيستم از کاربر دريافت نموده و آنرا به تاريخ شمسي رشته اي بدون جداکننده تبديل مي نمايد
	 
	 @param VDateTime تاريخ ميلادي سيستم
	 @return تاريخ شمسي رشته اي بدون جداکننده
	*/
	public static String RemoveDateSeparator(LocalDateTime VDateTime, String dateSeparator)
	{
		return DateToStr(VDateTime, dateSeparator);
	}

	public static String RemoveDateSeparator(String TarikhShamsi)
	{
		return RemoveDateSeparator(TarikhShamsi, String.valueOf(getDateSeparator()));
	}

	/** 
		 تابع جاري يک تاريخ شمسي از نوع رشته دريافت نموده و "/" را از تاريخ جدا مي نمايد
	 
	 @param TarikhShamsi تاريخ رشته اي مورد نظر
	 @return تاريخ بدون جدا کننده
	*/
	public static String RemoveDateSeparator(String TarikhShamsi, String dateSeparator)
	{
		return TarikhShamsi.replace(dateSeparator, "");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region public Functions

	/** 
		 دریافت ساعت جاری تهران با محاسبه مابه التفاوت تنظیمات ساعت سرور جاری با زمان تهران
	*/
	public static LocalDateTime getTehranDateTime()
	{
		LocalDateTime CurrentLocalDateTime = LocalDateTime.now();
		TimeSpan LocalOffset = TimeZone.CurrentTimeZone.GetUtcOffset(CurrentLocalDateTime);
		TimeSpan TehranOffset = GetTehranDifferenceTime();
		TimeSpan SumOffset = TehranOffset - LocalOffset;
		LocalDateTime Result = CurrentLocalDateTime + SumOffset;
		return Result;
	}

	/** 
		 بدست آوردن علت تعطیلی
	 
	 @param month ماه شمسی
	 @param day روز شمسی
	 @return علت تعطیلی
	*/
	public static String HolidayReason(int month, int day)
	{
		String Result = "";
		if ((month == 1) && ((day == 1) || (day == 2) || (day == 3) || (day == 4) || (day == 5)))
		{
			Result = "عید نوروز";
		}
		else if ((month == 1) && (day == 12))
		{
			Result = "روز جمهوری اسلامی";
		}
		else if ((month == 1) && (day == 13))
		{
			Result = "روز طبیعت";
		}
		else if ((month == 3) && (day == 14))
		{
			Result = "رحلت امام خمینی";
		}
		else if ((month == 3) && (day == 15))
		{
			Result = "قیام پانزدهم خرداد";
		}
		else if ((month == 11) && (day == 22))
		{
			Result = "پیروزی انقلاب اسلامی ایران";
		}
		else if ((month == 12) && (day == 29))
		{
			Result = "روز ملی شدن صنعت نفت ایران";
		}
		return Result;
	}

	/** 
		 بدست آوردن علت تعطیلی
	 
	 @param VDateTime تاریخ
	 @return علت تعطیلی
	*/
	public static String HolidayReason(LocalDateTime VDateTime)
	{
		TShamsiDate Result = new TShamsiDate(VDateTime);
		return HolidayReason(Result.getMonth(), Result.getDay());
	}

	/** 
		 تابع جاری اختلاف زمانی ساعت تهران و گرینویچ لندن را بر می گرداند
	 
	 @return اختلاف زمانی ساعت تهران و گرینویچ لندن
	*/
	public static TimeSpan GetTehranDifferenceTime()
	{
		return new TimeSpan(4, 30, 0);
	}

	public static int GetDaysInMonth(int year, int month)
	{
		if ((month == 10) && (year == 9378))
		{
			return 10;
		}
		if (month == 12)
		{
			if (IsLeapYear(year))
			{
				return 30;
			}
			return 29;
		}
		if (month <= 6)
		{
			return 31;
		}
		return 30;
	}

	public static int GetDaysInYear(int year)
	{
		if (IsLeapYear(year))
		{
			return 366;
		}
		return 365;
	}

	public static boolean IsLeapDay(int year, int month, int day)
	{
		if (day == 30 && month == 12 && IsLeapYear(year))
		{
			return true;
		}
		return false;
	}

	public static boolean IsLeapMonth(int year, int month)
	{
		return month == 12 && IsLeapYear(year);
	}

	public static int GetCentury(LocalDateTime time)
	{
		return YearOf(time) / 100;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Serializable, StructLayout(LayoutKind.Sequential), ComVisible(true)] public struct TShamsiDate
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [Serializable, StructLayout(LayoutKind.Sequential), ComVisible(true)] public struct TShamsiDate
	public final static class TShamsiDate implements Serializable
	{
		public TShamsiDate()
		{
		}

		static
		{
			Empty = new TShamsiDate();
		}

		public static TShamsiDate Empty = new TShamsiDate();
		private int year;
		private int month;
		private int day;
		private LocalDateTime dateTime = LocalDateTime.MIN;

		public TShamsiDate(int year, int month, int day)
		{
			this.year = year;
			this.month = month;
			this.day = day;
			dateTime = EncodeDate(year, month, day);
		}

		public TShamsiDate(LocalDateTime dateTime)
		{
			this.dateTime = dateTime;
			year = dateTime.getYear();
			month = dateTime.getMonthValue();
			day = dateTime.getDayOfMonth();
			tangible.RefObject<Integer> tempRef_year = new tangible.RefObject<Integer>(year);
			tangible.RefObject<Integer> tempRef_month = new tangible.RefObject<Integer>(month);
			tangible.RefObject<Integer> tempRef_day = new tangible.RefObject<Integer>(day);
			civil_persian(tempRef_year, tempRef_month, tempRef_day);
		day = tempRef_day.argValue;
		month = tempRef_month.argValue;
		year = tempRef_year.argValue;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)] public bool IsEmpty
		public boolean getIsEmpty()
		{
			return ((year == 0) && (month == 0) && (day == 0));
		}

		public int getYear()
		{
			return year;
		}
		private void setYear(int value)
		{
			year = value;
		}

		public int getMonth()
		{
			return month;
		}
		private void setMonth(int value)
		{
			month = value;
		}

		public int getDay()
		{
			return day;
		}
		private void setDay(int value)
		{
			day = value;
		}

		public LocalDateTime getDateTime()
		{
			return dateTime;
		}
		private void setDateTime(LocalDateTime value)
		{
			dateTime = value;
		}

		public static TShamsiDate OpAddition(TShamsiDate pt, TimeSpan sz)
		{
			return Add(pt.clone(), sz);
		}

		public static TShamsiDate OpSubtraction(TShamsiDate pt, TimeSpan sz)
		{
			return Subtract(pt.clone(), sz);
		}


		public static boolean OpEquality(TShamsiDate left, TShamsiDate right)
		{
			return ((left.getYear() == right.getYear()) && (left.month == right.month) && (left.day == right.day));
		}

		public static boolean OpInequality(TShamsiDate left, TShamsiDate right)
		{
			return !Rasad.Core.Globalization.ShDate.TShamsiDate.OpEquality(left.clone(), right.clone());
		}

		public static TShamsiDate Add(TShamsiDate pt, TimeSpan value)
		{
			return new TShamsiDate(pt.dateTime.Add(value));
		}

		public static TShamsiDate Subtract(TShamsiDate pt, TimeSpan value)
		{
			return new TShamsiDate(pt.dateTime.Subtract(value));
		}

		@Override
		public int hashCode()
		{
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof TShamsiDate))
			{
				return false;
			}
			TShamsiDate tf = (TShamsiDate)obj;
			return (((tf.getYear() == getYear()) && (tf.month == month) && (tf.day == day)) && tf.getClass().equals(super.getClass()));
		}

		@Override
		public String toString()
		{
			return toString('/');
		}

		public String toString(char separator)
		{
			return String.format(CultureInfo.CurrentCulture, "%1$s%4$s%2$s%4$s%3$s", new Object[] {getYear(), getMonth(), getDay(), separator});
		}

		public TShamsiDate clone()
		{
			TShamsiDate varCopy = new TShamsiDate();

			varCopy.year = this.year;
			varCopy.month = this.month;
			varCopy.day = this.day;
			varCopy.dateTime = this.dateTime;

			return varCopy;
		}
	}


	public static java.time.LocalDateTime Today(int hour, int minute, int second)
	{
		return Today(hour, minute, second, 0);
	}

	public static java.time.LocalDateTime Today(int hour, int minute)
	{
		return Today(hour, minute, 0, 0);
	}

	public static java.time.LocalDateTime Today(int hour)
	{
		return Today(hour, 0, 0, 0);
	}

	public static java.time.LocalDateTime Today()
	{
		return Today(0, 0, 0, 0);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static DateTime Today(int hour = 0, int minute = 0, int second = 0, int millisecond = 0)
	public static LocalDateTime Today(int hour, int minute, int second, int millisecond)
	{
		Rasad.Core.Globalization.ShDate.TShamsiDate DT = ShDate.DecodeDate(LocalDateTime.now());
		return ShDate.EncodeDate(DT.getYear(), DT.getMonth(), DT.getDay(), hour, minute, second, millisecond);
	}
}