package Rasad.Core.Globalization;

import Rasad.Core.*;
import java.time.*;

public class HijriDate
{
	/** 
		 مشخص می کند که یک روز مشخص از یک ماه مشخص قمری تعطیل هست یا خیر
	 
	 @param VDateTime تاریخ
	 @return bool
	*/
	public static boolean IsTateel(LocalDateTime VDateTime)
	{
		HijriCalendar VHijriCalendar = new HijriCalendar();
		short Month = (short) VHijriCalendar.GetMonth(VDateTime);
		short Day = (short) VHijriCalendar.GetDayOfMonth(VDateTime);
		return IsTateel(Month, Day);
	}

	/** 
		 مشخص می کند که یک روز مشخص از یک ماه مشخص قمری تعطیل هست یا خیر
	 
	 @param Month ماه قمری
	 @param Day روز قمری
	 @return bool
	*/
	public static boolean IsTateel(short Month, short Day)
	{
		boolean Result = false;
		switch (Month)
		{
			case 1:
				Result = ((Day == 9) || (Day == 10));
				break;
			case 2:
				Result = ((Day == 20) || (Day == 28) || (Day == 29));
				break;
			case 3:
				Result = (Day == 17);
				break;
			case 6:
				Result = (Day == 3);
				break;
			case 7:
				Result = ((Day == 13) || (Day == 27));
				break;
			case 8:
				Result = (Day == 15);
				break;
			case 9:
				Result = (Day == 21);
				break;
			case 10:
				Result = ((Day == 1) || (Day == 25));
				break;
			case 12:
				Result = ((Day == 10) || (Day == 18));
				break;
		}
		return Result;
	}

	/** 
		 بدست آوردن علت تعطیلی
	 
	 @param Month ماه قمری
	 @param Day روز قمری
	 @return علت تعطیلی
	*/
	public static String HolidayReason(short Month, short Day)
	{
		String Result = "";
		if ((Month == 1) && (Day == 9))
		{
			Result = "تاسوعای حسینی";
		}
		else if ((Month == 1) && (Day == 10))
		{
			Result = "عاشورای حسینی";
		}
		else if ((Month == 2) && (Day == 20))
		{
			Result = "اربعین حسینی";
		}
		else if ((Month == 2) && (Day == 28))
		{
			Result = "رحلت حضرت رسول اکرم (ص) و شهادت امام حسن مجتبی علیه السلام";
		}
		else if ((Month == 2) && (Day == 29))
		{
			Result = "شهادت امام رضا علیه السلام";
		}
		else if ((Month == 3) && (Day == 17))
		{
			Result = "میلاد حضرت رسول اکرم (ص) و میلاد امام جعفر صادق علیه السلام";
		}
		else if ((Month == 6) && (Day == 3))
		{
			Result = "شهادت حضرت فاطمه زهرا سلام اله علیها";
		}
		else if ((Month == 7) && (Day == 13))
		{
			Result = "ولادت حضرت امام علی علیه السلام";
		}
		else if ((Month == 7) && (Day == 27))
		{
			Result = "مبعث حضرت رسول اکرم صلی الله علیه و آله";
		}
		else if ((Month == 8) && (Day == 15))
		{
			Result = "ولادت حضرت مهدی علیه السلام";
		}
		else if ((Month == 9) && (Day == 21))
		{
			Result = "شهادت حضرت علی علیه السلام";
		}
		else if ((Month == 10) && (Day == 1))
		{
			Result = "عید سعید فطر";
		}
		else if ((Month == 10) && (Day == 25))
		{
			Result = "شهادت امام جعفر صادق علیه السلام";
		}
		else if ((Month == 12) && (Day == 10))
		{
			Result = "عید سعید قربان";
		}
		else if ((Month == 12) && (Day == 18))
		{
			Result = "عید سعید غدیر خم";
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
		HijriCalendar VHijriCalendar = new HijriCalendar();
		short Month = (short) VHijriCalendar.GetMonth(VDateTime);
		short Day = (short) VHijriCalendar.GetDayOfMonth(VDateTime);
		return HolidayReason(Month, Day);
	}
}