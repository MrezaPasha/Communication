package Rasad.Core.Globalization;

import Rasad.Core.Extensions.*;
import Rasad.Core.*;
import java.time.*;

/** 
	 Contains methods to fix the Persian culture for Persian calendar.
*/
public class TPersianCultureHelper
{
	private static native boolean VirtualProtect(IntPtr lpAddress, int dwSize, int flNewProtect, tangible.OutObject<Integer> lpflOldProtect);
	static
	{
		System.loadLibrary("kernel32.dll");
	}

	/** 
		 Fixes the DateTimeFormatInfo for Persian resources (months and week day names), and optionally sets the calendar to
		 PersianCalendar.
	 
	 @param info The DateTimeFormatInfo to be fixed.
	 @param UsePersianCalendar If set, the calendar will be set to PersianCalendar.
	 @return The fixed DateTimeFormatInfo.
	*/
	public static DateTimeFormatInfo FixPersianDateTimeFormat(DateTimeFormatInfo info, boolean UsePersianCalendar)
	{
		java.lang.reflect.Field dateTimeFormatInfoReadOnly = DateTimeFormatInfo.class.GetField("m_isReadOnly", BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue());
		java.lang.reflect.Field dateTimeFormatInfoCalendar = DateTimeFormatInfo.class.GetField("calendar", BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue());
		;

		if (info == null)
		{
			info = new DateTimeFormatInfo();
		}
		info.Calendar = new HijriCalendar();

		boolean readOnly = (Boolean)dateTimeFormatInfoReadOnly.GetValue(info);
		if (readOnly)
		{
			dateTimeFormatInfoReadOnly.set(info, false);
		}
		if (UsePersianCalendar)
		{
			dateTimeFormatInfoCalendar.set(info, new PersianCalendar());
		}
		if (info.Calendar.getClass() == PersianCalendar.class)
		{
			info.AbbreviatedDayNames = new String[] {"ی", "د", "س", "چ", "پ", "ج", "ش"};
			info.ShortestDayNames = new String[] {"ی", "د", "س", "چ", "پ", "ج", "ش"};
			info.DayNames = new String[] {"یکشنبه", "دوشنبه", "ﺳﻪشنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه"};
			info.AbbreviatedMonthNames = new String[] {"فروردین", "ارديبهشت", "خرداد", "تير", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند", ""};
			info.MonthNames = new String[] {"فروردین", "ارديبهشت", "خرداد", "تير", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند", ""};
			info.AMDesignator = "ق.ظ";
			info.PMDesignator = "ب.ظ";
			info.FirstDayOfWeek = DayOfWeek.SATURDAY;
			info.FullDateTimePattern = "yyyy MMMM dddd, dd HH:mm:ss";
			info.LongDatePattern = "yyyy MMMM dddd, dd";
			info.ShortDatePattern = "yyyy/MM/dd";
		}
		if (readOnly)
		{
			dateTimeFormatInfoReadOnly.set(info, true);
		}
		return info;
	}

	/** 
		 Fixes CultureInfo for Persian resoures (months and day names) and also PersianCalendar.
	 
	 @param culture The CultureInfo instace to be fixed. If NULL, a new instance will be created and returned.
	 @param options Specifies what to be fixed.
	 @return A new instance of fixed Persian CultureInfo.
	*/
	public static CultureInfo FixPersianCulture(CultureInfo culture, FixCultureOptions options)
	{
		PropertyInfo calendarID = Calendar.class.GetProperty("ID", BindingFlags.NonPublic.getValue() | BindingFlags.Instance.getValue());
		java.lang.reflect.Field cultureInfoReadOnly = CultureInfo.class.GetField("m_isReadOnly", BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue());
		java.lang.reflect.Field cultureInfoCalendar = CultureInfo.class.GetField("calendar", BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue());
		//FieldInfo cultureInfoReadOnly = typeof(CultureInfo).GetField("m_", BindingFlags.NonPublic | BindingFlags.Public | BindingFlags.Instance);
		if (culture == null)
		{
			culture = new CultureInfo("fa-IR", false);
		}
		if (culture == null || culture.LCID != 1065)
		{
			return culture;
		}
		if ((options.getValue() & FixCultureOptions.foptAll.getValue()) == FixCultureOptions.foptAll.getValue())
		{
			options = Rasad.Core.Globalization.FixCultureOptions.forValue(FixCultureOptions.foptCalendarInCulture.getValue() | FixCultureOptions.foptCalendarInDateFormatInfo.getValue() | FixCultureOptions.foptOptionalCalendars.getValue());
		}
		if ((options.getValue() & FixCultureOptions.foptOptionalCalendars.getValue()) == FixCultureOptions.foptOptionalCalendars.getValue())
		{
			FixOptionalCalendars(culture, 4);
			culture = new CultureInfo("fa-IR", false);
		}

		boolean readOnly = (Boolean)cultureInfoReadOnly.GetValue(culture);
		if (readOnly)
		{
			cultureInfoReadOnly.set(culture, false);
		}
		if ((options.getValue() & FixCultureOptions.foptCalendarInDateFormatInfo.getValue()) == FixCultureOptions.foptCalendarInDateFormatInfo.getValue())
		{
			culture.DateTimeFormat = FixPersianDateTimeFormat(culture.DateTimeFormat, true);
		}
		else
		{
			FixPersianDateTimeFormat(culture.DateTimeFormat, false);
		}
		if ((options.getValue() & FixCultureOptions.foptCalendarInCulture.getValue()) == FixCultureOptions.foptCalendarInCulture.getValue())
		{
			cultureInfoCalendar.set(culture, new PersianCalendar());
		}
		if (readOnly)
		{
			cultureInfoReadOnly.set(culture, true);
		}
		return culture;
	}

	/** 
		 Creates, fixes and returns a new instance of Persian culture.
	 
	 @return A new instance of fixed Persian culture.
	*/
	public static CultureInfo GetFixedPersianCulture()
	{
		return FixPersianCulture(null, FixCultureOptions.foptAll);
	}

	/** 
		 Set the CurrentCulture of current thread to a new fixed Persian culture.
	 
	 @return The fixed Persian cultreinfo.
	*/
	public static CultureInfo FixAndSetCurrentCulture()
	{
		CultureInfo culture = FixPersianCulture(null, FixCultureOptions.foptAll);
		Thread.currentThread().CurrentCulture = culture;
		return culture;
	}

	/** 
		 Fixes the OptionalCalendars in case of .Net 4.
	*/
	private static CultureInfo _FixOptionalCalendars4(CultureInfo culture, int CalenadrIndex)
	{
		java.lang.reflect.Field cultureDataField = CultureInfo.class.GetField("m_cultureData", BindingFlags.Public.getValue() | BindingFlags.NonPublic.getValue() | BindingFlags.Instance.getValue());
		Object cultureData = cultureDataField.GetValue(culture);
		java.lang.reflect.Field waCalendarsField = cultureData.getClass().getField("waCalendars", BindingFlags.Public.getValue() | BindingFlags.NonPublic.getValue() | BindingFlags.Instance.getValue());
		//CheckAgain:
		//int[] waCalendars = (int[])waCalendarsField.GetValue(cultureData);
		int[] waCalendars = {12, 1, 2, 6, 11};
		//if (waCalendars == null)
		//{
		//    object ll = null;
		//    foreach (var item in cultureData.GetType().GetFields(BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.Instance | BindingFlags.GetField))
		//    {
		//        try
		//        {
		//            ll = item.GetValue(cultureData);
		//        }
		//        catch
		//        {
		//        }
		//    }
		//    goto CheckAgain;
		//}
		if (CalenadrIndex >= 0 && CalenadrIndex < waCalendars.length)
		{
			waCalendars[CalenadrIndex] = 0x16;
		}
		waCalendarsField.set(cultureData, waCalendars);
		return culture;
	}


	/** 
		 Sets the CalendarIndex element of OptionalCalendars of the passed caulture to PersianCalendar.
	 
	 @param culture The CultureInfo instance to be fixed.
	 @param CalenadrIndex
		 The index of element in optional calendars to be set to PersianCalendar. Note that this can
		 not add a new element so that the idex should be les than the length of the OptionalCalendars array.
	 
	 @return The fixed culture.
	*/
	public static CultureInfo FixOptionalCalendars(CultureInfo culture, int CalenadrIndex)
	{
		InvokeHelper ivCultureInfo = new InvokeHelper(culture);
		if (!ivCultureInfo.HasField("m_cultureTableRecord"))
		{
			// This is .Net 4. 
			return _FixOptionalCalendars4(culture, CalenadrIndex);
		}

		InvokeHelper ivTableRecord = new InvokeHelper(ivCultureInfo.GetField("m_cultureTableRecord"));
		// Get the m_pData pointer as *void
		Pointer m_pData = (Pointer)ivTableRecord.GetField("m_pData");
		java.lang.reflect.Constructor _intPtrCtor = IntPtr.class.GetConstructor(new Object[] {java.lang.Class.forName("System.Void*")});
		// Construct a new IntPtr
		IntPtr DataIntPtr = (IntPtr)_intPtrCtor.newInstance(new Object[] {m_pData});

		java.lang.Class TCultureTableData = java.lang.Class.forName("System.Globalization.CultureTableData");
		// Convert the Pointer class to object if type CultureTableData to work with
		// reflection API.
		Object oCultureTableData = Marshal.PtrToStructure(DataIntPtr, TCultureTableData);
		InvokeHelper ivCultureTableData = new InvokeHelper(oCultureTableData);
		// Get waCalendars pointer
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var waCalendars = (uint)ivCultureTableData.GetField("waCalendars");
		int waCalendars = (int)ivCultureTableData.GetField("waCalendars");
		Object IOPTIONALCALENDARS = ivTableRecord.GetProperty("IOPTIONALCALENDARS");

		// Get m_Pool pointer
		Pointer m_pool = (Pointer)ivTableRecord.GetField("m_pPool");

		IntPtr PoolInPtr = (IntPtr)_intPtrCtor.newInstance(new Object[] {m_pool});
		// Add the waCalendars offset to pool pointer
		IntPtr shortArrayPtr = new IntPtr((PoolInPtr.ToInt64() + waCalendars * (Short.SIZE / Byte.SIZE)));
		short[] shortArray = new short[1];
		// Now shortArray points to an arry of short integers.
		// Go to read the first value which is the number of elements.
		// Marshal array to read elements.
		Marshal.Copy(shortArrayPtr, shortArray, 0, 1);
		// shortArray[0] is the number of optional calendars.
		short[[]] calArray = new short[shortArray[0]];
		// Add one element of short type to point to array of calendars
		IntPtr calArrayPtr = new IntPtr(shortArrayPtr.ToInt64() + (Short.SIZE / Byte.SIZE));
		// Finally read the array
		Marshal.Copy(calArrayPtr, calArray, 0, shortArray[0]);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint old;
		int old;
		tangible.OutObject<Integer> tempOut_old = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: VirtualProtect(calArrayPtr, 100, 0x4, out old);
		VirtualProtect(calArrayPtr, 100, 0x4, tempOut_old);
	old = tempOut_old.argValue;
		calArray[CalenadrIndex] = 0x16;
		Marshal.Copy(calArray, 0, calArrayPtr, calArray.length);
		tangible.OutObject<Integer> tempOut_old2 = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: VirtualProtect(calArrayPtr, 100, old, out old);
		VirtualProtect(calArrayPtr, 100, old, tempOut_old2);
	old = tempOut_old2.argValue;

		return culture;
	}
	public static void ChangeNumberFormat()
	{
		try
		{
			CultureInfo culture = new CultureInfo(Thread.currentThread().CurrentCulture.LCID);

			culture.NumberFormat.NumberDecimalSeparator = ".";
			culture.NumberFormat.CurrencyDecimalSeparator = ".";
			culture.NumberFormat.CurrencySymbol = "";
			CultureInfo.DefaultThreadCurrentCulture = culture;
			Thread.currentThread().CurrentCulture = culture;
			Thread.currentThread().CurrentUICulture = culture;
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error setting number format for " + String.valueOf(Thread.currentThread().CurrentCulture.LCID), exp);
		}
	}
}