package Rasad.Core.Globalization;

import Rasad.Core.*;
import java.time.*;

/** 
	 Class to convert PersianDate into normal DateTime value and vice versa.
	 {@link PersianDate }
 
 
	 You can use <c>Rasad.Core.Globalization.FarsiDate.Now</c> property to access current Date.
 
*/
public final class TPersianDateConverter
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields

	private static double Solar = 365.25;
	private static int GYearOff = 226894;

	private static int[][] gdaytable = new int[][]
	{
		{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
		{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}
	};

	private static int[][] jdaytable = new int[][]
	{
		{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29},
		{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30}
	};

	private static String[] weekdays = new String[] {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه"};

	private static String[] weekdaysabbr = new String[] {"ش", "ی", "د", "س", "چ", "پ", "ج"};

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Props

	/** 
		 Array of Day Table for Gregorian Days.
	*/
	public static int[][] getGDayTable()
	{
		return gdaytable;
	}

	/** 
		 Array of Day Table for Jalali Days.
	*/
	public static int[][] getJDayTable()
	{
		return jdaytable;
	}

	/** 
		 Array of WeekDay names for Persian Weekdays. This array is a collection of abbreviated weekday names. The
		 abbreviation name is just the first character of normal weekday names.
	*/
	public static String[] getWeekDaysAbbr()
	{
		return weekdaysabbr;
	}

	public static String[] getWeekDays()
	{
		return weekdays;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods

	/** 
		 Checks if a specified Persian year is a leap one.
	 
	 @param iJYear
	 @return returns 1 if the year is leap, otherwise returns 0.
	*/
	private static int JLeap(int iJYear)
	{
		//Is jalali year a leap year?
		int tmp;

		tangible.OutObject<Integer> tempOut_tmp = new tangible.OutObject<Integer>();
		Math.DivRem(iJYear, 33, tempOut_tmp);
	tmp = tempOut_tmp.argValue;
		if ((tmp == 1) || (tmp == 5) || (tmp == 9) || (tmp == 13) || (tmp == 17) || (tmp == 22) || (tmp == 26) || (tmp == 30))
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	/** 
		 Checks if a specified Gregorian year is a leap one.
	 
	 @param GregYear
	 @return returns 1 if the year is leap, otherwise returns 0.
	*/
	private static int GLeap(int GregYear)
	{
		//Is gregorian year a leap year?
		int Mod4, Mod100, Mod400;

		tangible.OutObject<Integer> tempOut_Mod4 = new tangible.OutObject<Integer>();
		Math.DivRem(GregYear, 4, tempOut_Mod4);
	Mod4 = tempOut_Mod4.argValue;
		tangible.OutObject<Integer> tempOut_Mod100 = new tangible.OutObject<Integer>();
		Math.DivRem(GregYear, 100, tempOut_Mod100);
	Mod100 = tempOut_Mod100.argValue;
		tangible.OutObject<Integer> tempOut_Mod400 = new tangible.OutObject<Integer>();
		Math.DivRem(GregYear, 400, tempOut_Mod400);
	Mod400 = tempOut_Mod400.argValue;

		if (((Mod4 == 0) && (Mod100 != 0)) || (Mod400 == 0))
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	private static int GregDays(int iGYear, int iGMonth, int iGDay)
	{
		//Calculate total days of gregorian from calendar base
		int Div4, Div100, Div400;
		int iLeap;
		Div4 = (iGYear - 1) / 4;
		Div100 = (iGYear - 1) / 100;
		Div400 = (iGYear - 1) / 400;
		iLeap = GLeap(iGYear);
		for (int iCounter = 0; iCounter < iGMonth - 1; iCounter++)
		{
			iGDay = iGDay + getGDayTable()[iLeap][iCounter];
		}

		return ((iGYear - 1) * 365 + iGDay + Div4 - Div100 + Div400);
	}

	private static int JLeapYears(int iJYear)
	{
		int iLeap, iCurrentCycle, Div33;
		int iCounter;

		Div33 = iJYear / 33;
		iCurrentCycle = iJYear - (Div33 * 33);
		iLeap = (Div33 * 8);
		if (iCurrentCycle > 0)
		{
			for (iCounter = 1; iCounter <= 18; iCounter = iCounter + 4)
			{
				if (iCounter > iCurrentCycle)
				{
					break;
				}
				iLeap++;
			}
		}
		if (iCurrentCycle > 21)
		{
			for (iCounter = 22; iCounter <= 31; iCounter = iCounter + 4)
			{
				if (iCounter > iCurrentCycle)
				{
					break;
				}
				iLeap++;
			}
		}
		return iLeap;
	}

	public static int JalaliDays(int iJYear, int iJMonth, int iJDay)
	{
		//Calculate total days of jalali years from the base calendar
		int iTotalDays, iLeap;

		iLeap = JLeap(iJYear);
		for (int i = 0; i < iJMonth - 1; i++)
		{
			iJDay = iJDay + getJDayTable()[iLeap][i];
		}

		iLeap = JLeapYears(iJYear - 1);
		iTotalDays = ((iJYear - 1) * 365 + iLeap + iJDay);

		return iTotalDays;
	}

	/** <overloads>Has two overloads.</overloads>
	 Converts a Gregorian Date of type <c>System.DateTime</c> class to Persian Date.
	 @param date DateTime to evaluate
	 @return string representation of Jalali Date
	*/
	public static TDateTime ToPersianDate(String date)
	{
		return ToPersianDate(LocalDateTime.parse(date));
	}


	/** 
		 Converts a Gregorian Date of type <c>String</c> and a <c>TimeSpan</c> into a Persian Date.
	 
	 @param date
	 @param time
	 @return 
	*/
	public static TDateTime ToPersianDate(String date, TimeSpan time)
	{
		TDateTime pd = ToPersianDate(date);
		pd.setHour(time.Hours);
		pd.setMinute(time.Minutes);
		pd.setSecond(time.Seconds);

		return pd;
	}

	/** 
		 Converts a Gregorian Date of type <c>String</c> class to Persian Date.
	 
	 @param dt Date to evaluate
	 @return string representation of Jalali Date.
	*/
	public static TDateTime ToPersianDate(LocalDateTime dt)
	{
		ShDate.TShamsiDate Result = ShDate.DecodeDate(dt);
		return new TDateTime(Result.getYear(), Result.getMonth(), Result.getDay(), dt.getHour(), dt.getMinute(), dt.getSecond());
	}

	/** 
		 Converts a Persian Date of type <c>String</c> to Gregorian Date of type <c>DateTime</c> class.
	 
	 @param date Date to evaluate
	 @return Gregorian DateTime representation of evaluated Jalali Date.
	*/
	public static LocalDateTime ToGregorianDateTime(String date)
	{
		return ToGregorianDateTime(new TDateTime(date));
	}

	public static LocalDateTime ToGregorianDateTime(TDateTime date)
	{
		return ShDate.EncodeDate(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), date.getMinute(), date.getSecond(), date.getMillisecond());
	}

	/** 
		 Converts a Persian Date of type <c>String</c> to Gregorian Date of type <c>String</c>.
	 
	 @param date
	 @return Gregorian DateTime representation in string format of evaluated Jalali Date.
	*/
	public static String ToGregorianDate(TDateTime date)
	{
		int iJYear = date.getYear();
		int iJMonth = date.getMonth();
		int iJDay = date.getDay();

		//Continue
		int iTotalDays, iGYear, iGMonth, iGDay;
		int Div4, Div100, Div400;
		int iGDays;
		int i, leap;

		iTotalDays = JalaliDays(iJYear, iJMonth, iJDay);
		iTotalDays = iTotalDays + GYearOff;
		iGYear = (int)(iTotalDays / (Solar - 0.25 / 33));

		Div4 = iGYear / 4;
		Div100 = iGYear / 100;
		Div400 = iGYear / 400;

		iGDays = iTotalDays - (365 * iGYear) - (Div4 - Div100 + Div400);
		iGYear = iGYear + 1;

		if (iGDays == 0)
		{
			iGYear--;
			if (GLeap(iGYear) == 1)
			{
				iGDays = 366;
			}
			else
			{
				iGDays = 365;
			}
		}
		else
		{
			if (iGDays == 366 && GLeap(iGYear) != 1)
			{
				iGDays = 1;
				iGYear++;
			}
		}

		leap = GLeap(iGYear);
		for (i = 0; i <= 12; i++)
		{
			if (iGDays <= getGDayTable()[leap][i])
			{
				break;
			}

			iGDays = iGDays - getGDayTable()[leap][i];
		}

		iGMonth = i + 1;
		iGDay = iGDays;

		return (toDouble(iGMonth) + "/" + toDouble(iGDay) + "/" + iGYear + " " + toDouble(date.getHour()) + ":" + toDouble(date.getMinute()) + ":" + toDouble(date.getSecond()));
	}

	/** 
		 Adds to single day or months a preceding zero
	 
	 @param i
	 @return 
	*/
	private static String toDouble(int i)
	{
		if (i > 9)
		{
			return String.valueOf(i);
		}
		else
		{
			return "0" + String.valueOf(i);
		}
	}

	public static String DayOfWeek(TDateTime date)
	{
		if (!date.getIsNull())
		{
			LocalDateTime dt = ToGregorianDateTime(date);
			return DayOfWeek(dt);
		}
		else
		{
			return "";
		}
	}

	/** 
		 Gets Persian Weekday name from specified Gregorian Date.
	 
	 @param date
	 @return 
	*/
	public static String DayOfWeek(LocalDateTime date)
	{
		String DayOfWeek = date.getDayOfWeek().toString().toLowerCase();
		String day;

		switch (DayOfWeek)
		{
			case "saturday":
				day = TDateTime.PersianWeekDayNames.getDefault().Shanbeh;
				break;
			case "sunday":
				day = TDateTime.PersianWeekDayNames.getDefault().Yekshanbeh;
				break;
			case "monday":
				day = TDateTime.PersianWeekDayNames.getDefault().Doshanbeh;
				break;
			case "tuesday":
				day = TDateTime.PersianWeekDayNames.getDefault().Seshanbeh;
				break;
			case "wednesday":
				day = TDateTime.PersianWeekDayNames.getDefault().Chaharshanbeh;
				break;
			case "thursday":
				day = TDateTime.PersianWeekDayNames.getDefault().Panjshanbeh;
				break;
			case "friday":
				day = TDateTime.PersianWeekDayNames.getDefault().Jomeh;
				break;
			default:
				day = "";
				break;
		}

		return (day);
	}

	/** 
		 Returns number of days in specified month number.
	 
	 @param MonthNo Month no to evaluate in integer
	 @return number of days in the evaluated month
	*/
	public static int MonthDays(int MonthNo)
	{
		return (getJDayTable()[1][MonthNo - 1]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}