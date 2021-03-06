package Rasad.Core.Globalization;

import Rasad.Core.*;
import java.io.*;
import java.time.*;

/** 
	 PersianCalendar calendar. Persian calendar, also named Jalaali calendar, was first based on Solar year by Omar
	 Khayyam, the great Iranian poet, astrologer and scientist.
	 Jalaali calendar is approximately 365 days. Each of the first six months in the Jalaali calendar has 31 days, each
	 of the next five months has 30 days, and the last month has 29 days in a common year and 30 days in a leap year. A
	 leap year is a year that, when divided by 33, has a remainder of 1, 5, 9, 13, 17, 22, 26, or 30. For example, the
	 year 1370 is a leap year because dividing it by 33 yields a remainder of 17. There are approximately 8 leap years
	 in every 33 year cycle.
*/
public final class TPersianCalendar extends Calendar implements Serializable
{
	static
	{
		PersianEra = 1;
		//       DaysToMonth = new int[] { 0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336 };
		//       LeapYears33 = new int[] { 
		//   0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 
		//   0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 
		//   0
		//};
		minDate = LocalDateTime.of(622, 3, 21);
		maxDate = LocalDateTime.MAX;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields

	public static LocalDateTime minDate = LocalDateTime.MIN;
	public static LocalDateTime maxDate = LocalDateTime.MIN;


	/** 
		 Represents the current era.
	 
	 The Persian calendar recognizes only A.P (Anno Persarum) era.
	*/
	public static int PersianEra;

	private int twoDigitYearMax = 1409;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
		 Gets and sets the last year of a 100-year range that can be represented by a 2-digit year.
	 
	 <property_value>The last year of a 100-year range that can be represented by a 2-digit year.</property_value>
	 
		 This property allows a 2-digit year to be properly translated to a 4-digit year. For example, if this property
		 is set to 1429, the 100-year range is from 1330 to 1429; therefore, a 2-digit value of 30 is interpreted as 1330,
		 while a 2-digit value of 29 is interpreted as 1429.
	 
	*/
	public int getTwoDigitYearMax()
	{
		return twoDigitYearMax;
	}
	public void setTwoDigitYearMax(int value)
	{
		if (value < 100 || 9378 < value)
		{
			throw new IllegalStateException("مقدار وارد شده را نمیتوان به سال تبدیل کرد.");
		}

		twoDigitYearMax = value;
	}

	/** 
		 Gets the list of eras in the PersianCalendar.
	 
	 The Persian calendar recognizes one era: A.P. (Latin "Anno Persarum", which means "the year of/for Persians").
	*/
	@Override
	public int[] getEras()
	{
		return new int[] {PersianEra};
	}


	@Override
	public CalendarAlgorithmType getAlgorithmType()
	{
		return CalendarAlgorithmType.SolarCalendar;
	}

	@Override
	public LocalDateTime getMaxSupportedDateTime()
	{
		return maxDate;
	}

	@Override
	public LocalDateTime getMinSupportedDateTime()
	{
		return minDate;
	}

	/** 
		 Returns a DateTime that is the specified number of months away from the specified DateTime.
	 
	 @param time The DateTime instance to add.
	 @param months The number of months to add.
	 @return The DateTime that results from adding the specified number of months to the specified DateTime.
	 
		 The year part of the resulting DateTime is affected if the resulting month is beyond the last month of the current
		 year. The day part of the resulting DateTime is also affected if the resulting day is not a valid day in the
		 resulting month of the resulting year; it is changed to the last valid day in the resulting month of the resulting
		 year. The time-of-day part of the resulting DateTime remains the same as the specified DateTime.
		 For example, if the specified month is Ordibehesht, which is the 2nd month and has 31 days, the specified day is
		 the 31th day of that month, and the value of the months parameter is -3, the resulting year is one less than the
		 specified year, the resulting month is Bahman, and the resulting day is the 30th day, which is the last day in
		 Bahman.
		 If the value of the months parameter is negative, the resulting DateTime would be earlier than the specified
		 DateTime.
	 
	*/
	@Override
	public LocalDateTime AddMonths(LocalDateTime time, int months)
	{
		return ShDate.ToDateTime(ShDate.AddMonths(time, months));
	}


	/** 
		 Returns a DateTime that is the specified number of years away from the specified DateTime.
	 
	 @param time The DateTime instance to add.
	 @param years The number of years to add.
	 @return The DateTime that results from adding the specified number of years to the specified DateTime.
	 
		 The day part of the resulting DateTime is affected if the resulting day is not a valid day in the resulting month
		 of the resulting year; it is changed to the last valid day in the resulting month of the resulting year. The
		 time-of-day part of the resulting DateTime remains the same as the specified DateTime.
		 For example, Esfand has 29 days, except during leap years when it has 30 days. If the specified Date is the 30th
		 day of Esfand in a leap year and the value of years is 1, the resulting Date will be the 29th day of Esfand in the
		 following year.
		 If years is negative, the resulting DateTime would be earlier than the specified DateTime.
	 
	*/
	@Override
	public LocalDateTime AddYears(LocalDateTime time, int years)
	{
		return ShDate.ToDateTime(ShDate.AddYears(time, years));
	}

	/** 
		 Gets the day of the month in the specified DateTime.
	 
	 @param time The DateTime instance to read.
	 @return An integer from 1 to 31 that represents the day of the month in time.
	*/
	@Override
	public int GetDayOfMonth(LocalDateTime time)
	{
		return ShDate.DayOf(time);
	}


	/** 
		 Gets the day of the week in the specified DateTime.
	 
	 @param time The DateTime instance to read.
	 @return A DayOfWeek value that represents the day of the week in time.
	 
		 The DayOfWeek values are Sunday which indicates YekShanbe', Monday which indicates DoShanbe', Tuesday which
		 indicates SeShanbe', Wednesday which indicates ChaharShanbe', Thursday which indicates PanjShanbe', Friday which
		 indicates Jom'e, and Saturday which indicates Shanbe'.
	 
	*/
	@Override
	public DayOfWeek GetDayOfWeek(LocalDateTime time)
	{
		return time.getDayOfWeek();
	}

	/** 
		 Gets the day of the year in the specified DateTime.
	 
	 @param time The DateTime instance to read.
	 @return An integer from 1 to 366 that represents the day of the year in time.
	*/
	@Override
	public int GetDayOfYear(LocalDateTime time)
	{
		return ShDate.GetDayOfYear(time);
	}

	/** 
		 Gets the number of days in the specified month.
	 
	 @param year An integer that represents the year.
	 @param month An integer that represents the month.
	 @param era An integer that represents the era.
	 @return The number of days in the specified month in the specified year in the specified era.
	 
		 For example, this method might return 29 or 30 for Esfand (month = 12), depending on whether year is a leap
		 year.
	 
	*/
	@Override
	public int GetDaysInMonth(int year, int month, int era)
	{
		return ShDate.GetDaysInMonth(year, month);
	}

	/** 
		 Gets the number of days in the year specified by the year and era parameters.
	 
	 @param year An integer that represents the year.
	 @param era An integer that represents the era.
	 @return The number of days in the specified year in the specified era.
	 For example, this method might return 365 or 366, depending on whether year is a leap year.
	*/
	@Override
	public int GetDaysInYear(int year, int era)
	{
		return ShDate.GetDaysInYear(year);
	}

	/** 
		 Gets the era in the specified DateTime.
	 
	 @param time The DateTime instance to read.
	 @return An integer that represents the era in time.
	 The Persian calendar recognizes one era: A.P. (Latin "Anno Persarum", which means "the year of/for Persians").
	*/
	@Override
	public int GetEra(LocalDateTime time)
	{
		return PersianEra;
	}

	/** 
		 Gets the month in the specified DateTime.
	 
	 @param time The DateTime instance to read.
	 @return An integer between 1 and 12 that represents the month in time.
	 
		 Month 1 indicates Farvardin, month 2 indicates Ordibehesht, month 3 indicates Khordad, month 4 indicates Tir,
		 month 5 indicates Amordad, month 6 indicates Shahrivar, month 7 indicates Mehr, month 8 indicates Aban, month 9
		 indicates Azar, month 10 indicates Dey, month 11 indicates Bahman, and month 12 indicates Esfand.
	 
	*/
	@Override
	public int GetMonth(LocalDateTime time)
	{
		return ShDate.MonthOf(time);
	}

	/** 
		 Gets the number of months in the year specified by the year and era parameters.
	 
	 @param year An integer that represents the year.
	 @param era An integer that represents the era.
	 @return The number of months in the specified year in the specified era.
	*/
	@Override
	public int GetMonthsInYear(int year, int era)
	{
		return 12;
	}

	/** 
		 Gets the year in the specified DateTime.
	 
	 @param time The DateTime instance to read.
	 @return An integer between 1 and 9378 that represents the year in time.
	*/
	@Override
	public int GetYear(LocalDateTime time)
	{
		return ShDate.YearOf(time);
	}

	/** 
		 Determines whether the Date specified by the year, month, day, and era parameters is a leap day.
	 
	 @param year An integer that represents the year.
	 @param month An integer that represents the month.
	 @param day An integer that represents the day.
	 @param era An integer that represents the era.
	 @return true if the specified day is a leap day; otherwise, false.
	 
		 In the Persian calendar leap years are applied every 4 or 5 years according to a certain pattern that iterates in a
		 2820-year cycle. A common year has 365 days and a leap year has 366 days.
		 A leap day is a day that occurs only in a leap year. In the Persian calendar, the 30th day of Esfand (month 12) is
		 the only leap day.
	 
	*/
	@Override
	public boolean IsLeapDay(int year, int month, int day, int era)
	{
		return ShDate.IsLeapDay(year, month, day);
	}

	/** 
		 Determines whether the month specified by the year, month, and era parameters is a leap month.
	 
	 @param year An integer that represents the year.
	 @param month An integer that represents the month.
	 @param era An integer that represents the era.
	 @return This method always returns false, unless overridden by a derived class.
	 
		 In the Persian calendar leap years are applied every 4 or 5 years according to a certain pattern that iterates in a
		 2820-year cycle. A common year has 365 days and a leap year has 366 days.
		 A leap month is an entire month that occurs only in a leap year. The Persian calendar does not have any leap
		 months.
	 
	*/
	@Override
	public boolean IsLeapMonth(int year, int month, int era)
	{
		return ShDate.IsLeapMonth(year, month);
	}

	/** 
		 Determines whether the year specified by the year and era parameters is a leap year.
	 
	 @param year An integer that represents the year.
	 @param era An integer that represents the era.
	 @return true if the specified year is a leap year; otherwise, false.
	 
		 In the Persian calendar leap years are applied every 4 or 5 years according to a certain pattern that iterates
		 in a 2820-year cycle. A common year has 365 days and a leap year has 366 days.
	 
	*/
	@Override
	public boolean IsLeapYear(int year, int era)
	{
		return ShDate.IsLeapYear(year);
	}

	/** 
		 Returns a DateTime that is set to the specified Date and time in the specified era.
	 
	 @param year An integer that represents the year.
	 @param month An integer that represents the month.
	 @param day An integer that represents the day.
	 @param hour An integer that represents the hour.
	 @param minute An integer that represents the minute.
	 @param second An integer that represents the second.
	 @param millisecond An integer that represents the millisecond.
	 @param era An integer that represents the era.
	 @return The DateTime instance set to the specified Date and time in the current era.
	*/
	@Override
	public LocalDateTime ToDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int era)
	{
		return ShDate.EncodeDate(year, month, day, hour, minute, second, millisecond);
	}

	/** 
		 Converts the specified two-digit year to a four-digit year by using the
		 Globalization.PersianCalendar.TwoDigitYearMax property to determine the appropriate century.
	 
	 @param year A two-digit integer that represents the year to convert.
	 @return An integer that contains the four-digit representation of year.
	 
		 TwoDigitYearMax is the last year in the 100-year range that can be represented by a two-digit year. The
		 century is determined by finding the sole occurrence of the two-digit year within that 100-year range. For example,
		 if TwoDigitYearMax is set to 1429, the 100-year range is from 1330 to 1429; therefore, a 2-digit value of 30 is
		 interpreted as 1330, while a 2-digit value of 29 is interpreted as 1429.
	 
	*/
	@Override
	public int ToFourDigitYear(int year)
	{
		return Integer.parseInt((new Integer(year)).toString("1300"));
	}
}