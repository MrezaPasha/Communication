package Rasad.Core.Globalization;

import Rasad.Core.*;
import java.time.*;

public class MIDate
{
	public static String[] LongMonthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	public static String MonthName(short Month) //{January ... December}
	{
		return LongMonthNames[Month - 1];
	}

	public static String MiladyToShamsi(String MiladiDateStr)
	{
		String[] AllParts = MiladiDateStr.split(java.util.regex.Pattern.quote(String.valueOf(ShDate.getDateSeparator())), -1);
		return MiladyToShamsi(AllParts[0], AllParts[1], AllParts[2]);
	}

	public static String MiladyToShamsi(String Year, String Month, String Day)
	{
		return MiladyToShamsi(Integer.parseInt(Year), Integer.parseInt(Month), Integer.parseInt(Day));
	}

	public static String MiladyToShamsi(int Year, int Month, int Day)
	{
		return ShDate.DateToStr(LocalDateTime.of(Year, Month, Day));
	}

	public static String ShamsiToMiladi(String ShamsiDateStr)
	{
		LocalDateTime Converted = ShDate.ToDateTime(ShamsiDateStr);
		return Converted.getYear() + String.valueOf(ShDate.getDateSeparator()) + Converted.getMonthValue() + ShDate.getDateSeparator() + Converted.getDayOfMonth();
	}

	public static LocalDateTime StrToDateTime(String MiladiDateStr)
	{
		String[] AllParts = MiladiDateStr.split(java.util.regex.Pattern.quote(String.valueOf(ShDate.getDateSeparator())), -1);
		int Year = Integer.parseInt(AllParts[0]);
		int Month = Integer.parseInt(AllParts[0]);
		int Day = Integer.parseInt(AllParts[0]);
		return LocalDateTime.of(Year, Month, Day);
	}

	public static String DateTimeToStr(LocalDateTime VDateTime)
	{
		return (new Integer(VDateTime.getYear())).toString("0000") + ShDate.getDateSeparator() + (new Integer(VDateTime.getMonthValue())).toString("00") + ShDate.getDateSeparator() + (new Integer(VDateTime.getDayOfMonth())).toString("00");
	}

	/** 
		 اين تابع يک تاريخ میلادی از کاربر دريافت نموده و اولين روز ماه میلادی آن تاريخ را بر ميگرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return اولين روز ماه تاريخ مورد نظر
	*/
	public static LocalDateTime FirstDayOfMonth(LocalDateTime date)
	{
		return date.plusDays(1 - date.getDayOfMonth());
	}

	/** 
		 اين تابع يک تاريخ میلادی از کاربر دريافت نموده و آخرین روز ماه میلادی آن تاريخ را بر ميگرداند
	 
	 @param VDateTime تاريخ مورد نظر
	 @return اولين روز ماه تاريخ مورد نظر
	*/
	public static LocalDateTime LastDayOfMonth(LocalDateTime date)
	{
		return FirstDayOfMonth(date).plusMonths(1).plusDays(-1);
	}

	public static LocalDateTime FirstDayOfWeek(LocalDateTime date)
	{
		//int date.DayOfWeek
		return date;
	}

	/** 
		 اين تابع يک تاريخ ميلادي سيستم را دريافت نموده و مشخص ميکند که در روز چندم هفته میلادی قرار دارد
	 
	 @param VDateTime تاريخ ميلادي سيستم
	 @return روز چندم هفته
	*/
	public static short DayOfWeek(LocalDateTime VDateTime) // {7...1}
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] DayArrays = {2, 3, 4, 5, 6, 7, 1};
		byte[] DayArrays = {2, 3, 4, 5, 6, 7, 1};
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short DayOfWeek = DayArrays[(byte) VDateTime.DayOfWeek];
		short DayOfWeek = DayArrays[(byte) VDateTime.getDayOfWeek().getValue()];
		return DayOfWeek;
	}
}