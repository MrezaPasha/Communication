package Rasad.Core.Globalization;

import Rasad.Core.*;
import java.time.*;

/** 
	 یک روز در تقویم شمسی میباشد ، که جهت ساخت تقویم شمسی مورد استفاده قرار میگیرد
*/
public class CalendarDay
{
	private LocalDateTime date = LocalDateTime.MIN;
	private String dayNumberText;
	private DayOfWeek dayOfWeek = DayOfWeek.values()[0];
	private boolean isFriday;
	private boolean isOtherMonth;
	private boolean isSelected;
	private boolean isToday;
	private String shamsiDate;

	public CalendarDay(LocalDateTime date, boolean isFriday, boolean isToday, boolean isSelected, boolean isOtherMonth, String dayNumberText)
	{
		this.date = date;
		this.isFriday = isFriday;
		this.isToday = isToday;
		this.isOtherMonth = isOtherMonth;
		this.isSelected = isSelected;
		this.dayNumberText = dayNumberText;
		shamsiDate = ShDate.DateToStr(date);
		dayOfWeek = date.getDayOfWeek();
	}

	public CalendarDay(LocalDateTime date, boolean isOtherMonth)
	{
		this.date = date;
		shamsiDate = ShDate.DateToStr(date);
		this.isOtherMonth = isOtherMonth;
		isToday = (date.Date.equals(LocalDateTime.Today));
		isFriday = ShDate.IsFriday(date);
		dayNumberText = String.valueOf(ShDate.DayOf(date));
		dayOfWeek = date.getDayOfWeek();
	}

	public CalendarDay(LocalDateTime date)
	{
		this.date = date;
		isToday = (date.Date.equals(LocalDateTime.Today));
		isOtherMonth = false;
		isFriday = ShDate.IsFriday(date);
		shamsiDate = ShDate.DateToStr(date);
		dayNumberText = String.valueOf(ShDate.DayOf(date));
		dayOfWeek = date.getDayOfWeek();
	}

	public final LocalDateTime getDate()
	{
		return date;
	}

	public final String getDayNumberText()
	{
		return dayNumberText;
	}

	public final boolean getIsOtherMonth()
	{
		return isOtherMonth;
	}

	private boolean IsSelectable;
	public final boolean getIsSelectable()
	{
		return IsSelectable;
	}
	public final void setIsSelectable(boolean value)
	{
		IsSelectable = value;
	}

	public final boolean getIsSelected()
	{
		return isSelected;
	}

	public final boolean getIsToday()
	{
		return isToday;
	}

	public final boolean getIsFriday()
	{
		return isFriday;
	}

	public final String getShamsiDate()
	{
		return shamsiDate;
	}

	/** 
		 روز هفته را تعیین می نماید
	*/
	public final DayOfWeek getDayOfWeek()
	{
		return dayOfWeek;
	}
}