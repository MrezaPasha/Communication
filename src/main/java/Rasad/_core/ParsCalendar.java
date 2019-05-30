package main.java.Rasad._core;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import java.time.LocalTime;
import java.util.*;

/**
 * @author masoud
 * @version 1.95.05.06
 */
public class ParsCalendar {

    private static final ParsCalendar instance = new ParsCalendar();
    private static Calendar persCal;
    private static Calendar gregCal;

    ///========================================================
    ///---CONSTRACTOR
    ///========================================================
    private ParsCalendar() {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
    }

    private String getWeekName(int monthNumber) {

        switch (monthNumber) {
            case 7:
                return "شنبه";
            case 1:
                return "یکشنبه";
            case 2:
                return "دوشنبه";
            case 3:
                return "سه شنبه";
            case 4:
                return "چهارشنبه";
            case 5:
                return "پنجشنبه";
            case 6:
                return "جمعه";
        }
        return "";
    }

    ///========================================================
    ///---PUBLIC
    ///========================================================
    public void refresh() {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
    }


    /**
     * format: 2016-01-01
     *
     * @param gShortDate
     * @return
     */
    public String gregorianToJalaliDate(String gShortDate) {
        if (gShortDate == null || gShortDate.isEmpty()) {
            return "";
        }
        String[] s = gShortDate.split(" ")[0].split("-");
        if (s.length != 3) {
            return "";
        }

        gregCal.set(Calendar.YEAR, Integer.parseInt(s[0]));
        gregCal.set(Calendar.MONTH, Integer.parseInt(s[1]) - 1);
        gregCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s[2]));

        persCal.setTime(gregCal.getTime());

        return getPersDate();
    }

    ///========================================================
    ///---PRIVATE
    ///========================================================
    private String getPersDate() {
        int m = persCal.get(Calendar.MONTH) + 1;
        return (persCal.get(Calendar.YEAR) < 10 ? "0" + persCal.get(Calendar.YEAR) : persCal.get(Calendar.YEAR)) + "/"
                + (m < 10 ? "0" + m : m) + "/"
                + (persCal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + persCal.get(Calendar.DAY_OF_MONTH) : persCal.get(Calendar.DAY_OF_MONTH));
    }

    public Date JalaliDateTimeToGregorian(String shortDateTime) {
        OutLog.pl(shortDateTime);
        if (shortDateTime == null || shortDateTime.isEmpty()) {
            return null;
        }
        OutLog.pl("");
        String[] ss = shortDateTime.split(" ");
        if (ss.length != 2) {
            return null;
        }
        String[] tm = ss[1].split(":");

        int h, m, s, year, day, month;
        try {
            h = Integer.parseInt(tm[0]);
        } catch (Exception e) {
            h = 0;
        }
        try {
            m = Integer.parseInt(tm[1]);
        } catch (Exception e) {
            m = 0;
        }
        try {
            s = Integer.parseInt(tm[2]);
        } catch (Exception e) {
            s = 0;
        }
//        OutLog.pl("");
        tm = ss[0].split("/");
        try {
            year = Integer.parseInt(tm[0]);
        } catch (Exception e) {
            year = 0;
        }
        try {
            month = Integer.parseInt(tm[1]);
        } catch (Exception e) {
            month = 0;
        }
        try {
            day = Integer.parseInt(tm[2]);
        } catch (Exception e) {
            day = 0;
        }
//        OutLog.pl("");
        persCal.set(year, month - 1, day, h, m, s);
//        OutLog.pl(persCal.toString());
        return persCal.getTime();
    }

    public String getShortDateTime() {
        persCal.setTime(new Date());
        return getPersDate() + " " + getPersTime();
    }

    public String getShortDateTime(long timeInMillisecond) {
        persCal.setTimeInMillis(timeInMillisecond);
        return getPersDate() + " " + getPersTime(persCal);
    }

    private String getPersTime(Calendar time) {
        return (time.get(Calendar.HOUR) < 10 ? "0" + time.get(Calendar.HOUR) : time.get(Calendar.HOUR))
                + ":" + (time.get(Calendar.MINUTE) < 10 ? "0" + time.get(Calendar.MINUTE) : time.get(Calendar.MINUTE))
                + ":" + (time.get(Calendar.SECOND) < 10 ? "0" + time.get(Calendar.SECOND) : time.get(Calendar.SECOND));
    }

    private String getPersTime() {
        return (LocalTime.now().getHour() < 10 ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour())
                + ":" + (LocalTime.now().getMinute() < 10 ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
                + ":" + (LocalTime.now().getSecond() < 10 ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond());
    }

    public String getShortDateTime(TtCalendarItem calendarItem, int afterNow) {
        persCal.setTime(new Date());
        return getPersDate(calendarItem, afterNow) + " " + getPersTime();
    }

    // return some days after current date
    // if 'dayAfterNow' is negative, return some days befor current date
    private String getPersDate(TtCalendarItem calendarItem, int afterNow) {
        switch (calendarItem) {
            case Day:
                persCal.set(Calendar.DAY_OF_MONTH, persCal.get(Calendar.DAY_OF_MONTH) + afterNow);
                break;
            case Month:
                persCal.set(Calendar.MONTH, persCal.get(Calendar.MONTH) + afterNow);
                break;
            case Year:
                persCal.set(Calendar.YEAR, persCal.get(Calendar.YEAR) + afterNow);
                break;

        }
        int m = persCal.get(Calendar.MONTH) + 1;
        return (persCal.get(Calendar.YEAR) < 10 ? "0" + persCal.get(Calendar.YEAR) : persCal.get(Calendar.YEAR)) + "/"
                + (m < 10 ? "0" + m : m) + "/"
                + (persCal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + persCal.get(Calendar.DAY_OF_MONTH) : persCal.get(Calendar.DAY_OF_MONTH));
    }

    public String getShortDateTime(TtCalendarItem calendarItem, int after, String shortDateTime) {
        persCal.setTime(new Date());
        try {
            return getPersDate(calendarItem, after, shortDateTime) + " " + shortDateTime.split(" ")[1];
        } catch (Exception e) {
            return getPersDate(calendarItem, after, shortDateTime) + " " + getPersTime();
        }
    }

    private String getPersDate(TtCalendarItem calendarItem, int after, String shortDate) {
        if (shortDate == null) {
            return "";
        }
        String[] s = shortDate.split(" ")[0].split("/");
        if (s.length != 3) {
            //            OutLog.pl();
            return "";
        }

        switch (calendarItem) {
            case Day:
                persCal.set(Calendar.YEAR, Integer.parseInt(s[0]));
                persCal.set(Calendar.MONTH, Integer.parseInt(s[1]) - 1);
                persCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s[2]) + after);
                break;
            case Month:
                persCal.set(Calendar.YEAR, Integer.parseInt(s[0]));
                persCal.set(Calendar.MONTH, Integer.parseInt(s[1]) - 1 + after);
                persCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s[2]));
                break;
            case Year:
                persCal.set(Calendar.YEAR, Integer.parseInt(s[0]) + after);
                persCal.set(Calendar.MONTH, Integer.parseInt(s[1]) - 1);
                persCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s[2]));
                break;
            case Hour:
            case Minute:
            case Second:
                return shortDate;

        }
        int m = persCal.get(Calendar.MONTH) + 1;
        return (persCal.get(Calendar.YEAR) < 10 ? "0" + persCal.get(Calendar.YEAR) : persCal.get(Calendar.YEAR)) + "/"
                + (m < 10 ? "0" + m : m) + "/"
                + (persCal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + persCal.get(Calendar.DAY_OF_MONTH) : persCal.get(Calendar.DAY_OF_MONTH));
    }

    public String getShortTime() {
        return getPersTime();
    }

    public String getShortDate(TtCalendarItem calendarItem, int afterNow) {
        persCal.setTime(new Date());
        return getPersDate(calendarItem, afterNow);
    }

    public String getShortDate(TtCalendarItem calendarItem, int after, String shortDate) {
        return getPersDate(calendarItem, after, shortDate);
    }

    public String getDateInMonthString() {
        persCal.setTime(new Date());
        return getPersDay()
                + " " + getMonthName(persCal.get(Calendar.MONTH) + 1)
                + " " + getPersYear();
    }

    private String getPersDay() {
        return "" + (persCal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + persCal.get(Calendar.DAY_OF_MONTH) : persCal.get(Calendar.DAY_OF_MONTH));
    }

    private String getMonthName(int monthNumber) {

        switch (monthNumber) {
            case 1:
                return "فروردین";
            case 2:
                return "اردیبهشت";
            case 3:
                return "خرداد";
            case 4:
                return "تیر";
            case 5:
                return "مرداد";
            case 6:
                return "شهریور";
            case 7:
                return "مهر";
            case 8:
                return "آبان";
            case 9:
                return "آذر";
            case 10:
                return "دی";
            case 11:
                return "بهمن";
            case 12:
                return "اسفند";
        }
        return "";
    }

    private String getPersYear() {
        return "" + (persCal.get(Calendar.YEAR) < 10 ? "0" + persCal.get(Calendar.YEAR) : persCal.get(Calendar.YEAR));
    }

    public String getDateTimeInMonthString(String shortDateTime) {
        if (shortDateTime == null || shortDateTime.isEmpty()) {
            return "";
        }
        String[] dt = shortDateTime.split(" ");
        String[] s = dt[0].split("/");
        if (s.length != 3) {
            return "";
        }

        return s[2]
                + " " + getMonthName(s[1])
                + " " + s[0]
                + ((dt.length > 1) ? " " + dt[1] : "");
    }

    private String getMonthName(String monthNumber) {
        int mn = 0;
        try {
            mn = Integer.parseInt(monthNumber);
        } catch (Exception e) {
        }
        return getMonthName(mn);
    }

    public String getDateInMonthString(String shortDate) {
        if (shortDate == null || shortDate.isEmpty()) {
            return "";
        }
        String[] dt = shortDate.split(" ");
        String[] s = dt[0].split("/");
        if (s.length != 3) {
            return "";
        }

        return s[2]
                + " " + getMonthName(s[1])
                + " " + s[0];
    }

    ///========================================================
    ///---PUBLIC WEEK AND MONTH BORDER
    ///========================================================
    public String getStartWeekDate() {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.DAY_OF_MONTH, persCal.get(Calendar.DAY_OF_MONTH) - persCal.get(Calendar.DAY_OF_WEEK));
        return getPersDate();
    }

    public String getStartWeekDate(int weekAfterThisWeek) {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.DAY_OF_MONTH, persCal.get(Calendar.DAY_OF_MONTH) - persCal.get(Calendar.DAY_OF_WEEK) + 7 * weekAfterThisWeek);
        return getPersDate();
    }

    public String getStartWeekDate(String shortDate) {
        if (shortDate == null || shortDate.isEmpty()) {
            return "";
        }
        String[] s = shortDate.split(" ")[0].split("/");
        if (s.length != 3) {
            return "";
        }
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.YEAR, Integer.parseInt(s[0]));
        persCal.set(Calendar.MONTH, Integer.parseInt(s[1]) - 1);
        persCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s[2]) - persCal.get(Calendar.DAY_OF_WEEK));
        return getPersDate();
    }

    public String getEndWeekDate() {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.DAY_OF_MONTH, persCal.get(Calendar.DAY_OF_MONTH) - persCal.get(Calendar.DAY_OF_WEEK) + 6);
        return getPersDate();
    }

    public String getEndWeekDate(int weekAfterThisWeek) {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.DAY_OF_MONTH, persCal.get(Calendar.DAY_OF_MONTH) - persCal.get(Calendar.DAY_OF_WEEK) + 6 + 7 * weekAfterThisWeek);
        return getPersDate();
    }

    public String getEndWeekDate(String shortDate) {
        if (shortDate == null || shortDate.isEmpty()) {
            return "";
        }
        String[] s = shortDate.split(" ")[0].split("/");
        if (s.length != 3) {
            return "";
        }
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.YEAR, Integer.parseInt(s[0]));
        persCal.set(Calendar.MONTH, Integer.parseInt(s[1]) - 1);
        persCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s[2]) - persCal.get(Calendar.DAY_OF_WEEK) + 6);
        return getPersDate();
    }

    public String getStartMonthDate() {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.DAY_OF_MONTH, 1);
        return getPersDate();
    }

    public String getStartMonthDate(int monthAfterThisMonth) {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.MONTH, persCal.get(Calendar.MONTH) + monthAfterThisMonth);
        persCal.set(Calendar.DAY_OF_MONTH, 1);
        return getPersDate();
    }

    public String getStartMonthDate(String shortDate) {
        if (shortDate == null || shortDate.isEmpty()) {
            return "";
        }
        String[] s = shortDate.split(" ")[0].split("/");
        if (s.length != 3) {
            return "";
        }
        persCal.set(Calendar.YEAR, Integer.parseInt(s[0]));
        persCal.set(Calendar.MONTH, Integer.parseInt(s[1]) - 1);
        persCal.set(Calendar.DAY_OF_MONTH, 1);
        return getPersDate();
    }

    public String getEndMonthDate() {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.DAY_OF_MONTH, getMonthLength(Calendar.MONTH, isLeapYear(Calendar.YEAR)) - 1);
        return getPersDate();
    }

    private int getMonthLength(int month, boolean isleapyear) {
        if (month < 1 || month > 12) {
            return -1;
        }
        if (month >= 1 && month <= 6) {
            return 31;
        } else if (month >= 7 && month <= 11) {
            return 30;
        } else {
            if (isleapyear) {
                return 30;
            } else {
                return 29;
            }
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public String getEndMonthDate(int monthAfterThisMonth) {
        persCal = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
        gregCal = Calendar.getInstance();
        persCal.set(Calendar.MONTH, persCal.get(Calendar.MONTH) + monthAfterThisMonth);
        persCal.set(Calendar.DAY_OF_MONTH, getMonthLength(Calendar.MONTH, isLeapYear(Calendar.YEAR)) - 1);
        return getPersDate();
    }

    ///========================================================
    ///---PUBLIC ELEMENT DATES
    ///========================================================
    public String getYear() {
        persCal.setTime(new Date());
        return getPersYear();
    }

    public int getMonthI() {
        persCal.setTime(new Date());
        return persCal.get(Calendar.MONTH) + 1;
    }

    public int getDayI() {
        persCal.setTime(new Date());
        return persCal.get(Calendar.DAY_OF_MONTH);
    }

    public int getHoursI() {
        return LocalTime.now().getHour();
    }

    public int getMinutesI() {
        return LocalTime.now().getMinute();
    }

    public TtCompareResult compareDateTime(String firstDateTime, String secondDateTime) {
        firstDateTime = firstDateTime.trim();
        secondDateTime = secondDateTime.trim();

        if (firstDateTime.equals(secondDateTime)) {
            return TtCompareResult.Equal;
        }
        String fDate, fTime, sDate, sTime;

        String[] fSplit = firstDateTime.split(" ");
        String[] sSplit = secondDateTime.split(" ");
        try {
            fDate = fSplit[0];
            sDate = sSplit[0];
            TtCompareResult tcr = compareDate(fDate, sDate);
            if (tcr != TtCompareResult.Equal) {
                return tcr;
            }
        } catch (Exception e) {
            return TtCompareResult.Unknown;
        }
        try {
            fTime = fSplit[1];
            sTime = sSplit[1];
            return compareTime(fTime, sTime);

        } catch (Exception e) {
            return TtCompareResult.Unknown;
        }

    }

    public String getYear(String dateOrdateTime) {
        if (dateOrdateTime != null && !dateOrdateTime.isEmpty()) {
            return dateOrdateTime.split("/")[0];
        }
        return "0";
    }

    public String getMonth() {
        persCal.setTime(new Date());
        return getPersMonth();

    }

    private String getPersMonth() {
        int m = persCal.get(Calendar.MONTH) + 1;
        return "" + (m < 10 ? "0" + m : m);
    }

    public String getDay() {
        persCal.setTime(new Date());
        return getPersDay();
    }

    public String getHours() {
        return "" + (LocalTime.now().getHour() < 10 ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour());
    }

    public String getMinutes() {
        return "" + (LocalTime.now().getMinute() < 10 ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute());
    }

    public String getSeconds() {
        return "" + (LocalTime.now().getSecond() < 10 ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond());
    }

    ///========================================================
    ///---PUBLIC COPMPARE
    ///========================================================
    public TtCompareResult compareDate(String firstDate, String secondDate) {
        if (firstDate == null || firstDate.isEmpty()) {
            return TtCompareResult.Unknown;
        }
        if (firstDate.equals(secondDate)) {
            return TtCompareResult.Equal;
        }
        int startYear, startDay, startMonth,
                endDay, endMonth, endYear;

        String[] fDateSplit = firstDate.split("/");
        String[] eDateSplit = secondDate.split("/");
        try {
            startYear = Integer.valueOf(fDateSplit[0]);
            endYear = Integer.valueOf(eDateSplit[0]);
            if (startYear > endYear) {
                return TtCompareResult.FirstIsBigger;
            } else if (startYear < endYear) {
                return TtCompareResult.SecondIsBigger;
            }
        } catch (Exception e) {
            return TtCompareResult.Unknown;
        }
        try {
            startMonth = Integer.valueOf(fDateSplit[1]);
            endMonth = Integer.valueOf(eDateSplit[1]);
            if (startMonth > endMonth) {
                return TtCompareResult.FirstIsBigger;
            } else if (startMonth < endMonth) {
                return TtCompareResult.SecondIsBigger;
            }
        } catch (Exception e) {
            return TtCompareResult.Unknown;
        }
        try {
            startDay = Integer.valueOf(fDateSplit[2]);
            endDay = Integer.valueOf(eDateSplit[2]);
            if (startDay > endDay) {
                return TtCompareResult.FirstIsBigger;
            } else if (startDay < endDay) {
                return TtCompareResult.SecondIsBigger;
            }
        } catch (Exception e) {
            return TtCompareResult.Unknown;
        }
        return TtCompareResult.Equal;
    }

    public TtCompareResult compareTime(String firstTime, String secondTime) {
        if (firstTime.equals(secondTime)) {
            return TtCompareResult.Equal;
        }
        int fHour, fSec, fMin,
                eSec, eMin, eHour;

        String[] startDateSplit = firstTime.split(":");
        String[] endDateSplit = secondTime.split(":");
        try {
            fHour = Integer.valueOf(startDateSplit[0]);
            eHour = Integer.valueOf(endDateSplit[0]);
            if (fHour > eHour) {
                return TtCompareResult.FirstIsBigger;
            } else if (fHour < eHour) {
                return TtCompareResult.SecondIsBigger;
            }
        } catch (Exception e) {
            return TtCompareResult.Unknown;
        }
        try {
            fMin = Integer.valueOf(startDateSplit[1]);
            eMin = Integer.valueOf(endDateSplit[1]);
            if (fMin > eMin) {
                return TtCompareResult.FirstIsBigger;
            } else if (fMin < eMin) {
                return TtCompareResult.SecondIsBigger;
            }
        } catch (Exception e) {
            return TtCompareResult.Unknown;
        }
        try {
            fSec = Integer.valueOf(startDateSplit[2]);
            eSec = Integer.valueOf(endDateSplit[2]);
            if (fSec > eSec) {
                return TtCompareResult.FirstIsBigger;
            } else if (fSec < eSec) {
                return TtCompareResult.SecondIsBigger;
            }
        } catch (Exception e) {
            return TtCompareResult.Unknown;
        }
        return TtCompareResult.Equal;
    }

    ///========================================================
    ///---PUBLIC DURATION
    ///========================================================
    public int durationOnTheDayI(String startDate, String endDate) {
//        OutLog.pl("sd: " + startDate);
//        OutLog.pl("sd: " + endDate);
        if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
            return -1;
        }
        int diffDay = 0,
                startYear, startDay, startMonth,
                endDay, endMonth, endYear;
        boolean isNegative = false;
        //            OutLog.pl("");
        String[] startDateSplit = startDate.split(" ")[0].split("/");
        String[] endDateSplit = endDate.split(" ")[0].split("/");
        //            OutLog.pl("");
        startYear = Integer.valueOf(startDateSplit[0]);
        endYear = Integer.valueOf(endDateSplit[0]);
        try {
            if (startYear > endYear) {
                isNegative = true;
                int tmp = startYear;
                startYear = endYear;
                endYear = tmp;
                startDay = Integer.valueOf(endDateSplit[2]);
                startMonth = Integer.valueOf(endDateSplit[1]);
                endDay = Integer.valueOf(startDateSplit[2]);
                endMonth = Integer.valueOf(startDateSplit[1]);
            } else {
                startDay = Integer.valueOf(startDateSplit[2]);
                startMonth = Integer.valueOf(startDateSplit[1]);
                endDay = Integer.valueOf(endDateSplit[2]);
                endMonth = Integer.valueOf(endDateSplit[1]);

            }
        } catch (Exception e) {
            return -1;
        }
        //            OutLog.pl("");
        if (startDay > 31 || startDay < 1) {
            return -1;
        }
        if (startMonth > 12 || startMonth < 1) {
            return -1;
        }

        if (endDay > 31 || endDay < 1) {
            return -1;
        }
        if (endMonth > 12 || endMonth < 1) {
            return -1;
        }

        for (int y = startYear; y < endYear; y++) {
            diffDay += isLeapYear(y) ? 366 : 365;
        }

        for (int m = 1; m < endMonth; m++) {
            diffDay += (m < 7) ? 31
                    : (m < 12) ? 30
                    : isLeapYear(endYear) ? 30
                    : 29;
        }
        //            OutLog.pl("");
        diffDay += endDay;

        for (int m = 1; m < startMonth; m++) {
            diffDay -= (m < 7) ? 31
                    : (m < 12) ? 30
                    : isLeapYear(startYear) ? 30
                    : 29;
        }

        diffDay -= (startDay - 1);
        //            OutLog.pl(diffDay);
        if (isNegative) {
            return -1 * diffDay;
        } else {
            return diffDay;
        }
    }

    private int[] durationOnTheDateI(String startDate, String endDate) {

        if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
//         //            OutLog.pl();
            return null;
        }
        int startYear, startDay, startMonth,
                endDay, endMonth, endYear;

        String[] startDateSplit = startDate.split(" ")[0].split("/");
        String[] endDateSplit = endDate.split(" ")[0].split("/");

        //            OutLog.pl(Arrays.toString(startDateSplit));
        //            OutLog.pl(Arrays.toString(endDateSplit));
        startYear = Integer.valueOf(startDateSplit[0]);
        endYear = Integer.valueOf(endDateSplit[0]);
        try {
            if (startYear > endYear) {
                return new int[]{-1};
            } else {
                startDay = Integer.valueOf(startDateSplit[2]);
                startMonth = Integer.valueOf(startDateSplit[1]);
                endDay = Integer.valueOf(endDateSplit[2]);
                endMonth = Integer.valueOf(endDateSplit[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new int[]{-2};
        }

        if (startDay > 31 || startDay < 1) {
            return new int[]{-3};
        }
        if (startMonth > 12 || startMonth < 1) {
            return new int[]{-4};
        }

        if (endDay > 31 || endDay < 1) {
            return new int[]{-5};
        }
        if (endMonth > 12 || endMonth < 1) {
            return new int[]{-6};
        }

        boolean isLeapYear = isLeapYear(endYear);

        // for consodering current day in duration
        //end - start + 1    >    end++ - start
//        endDay++;
        if (startDay > endDay) {
            endDay += getMonthLength(endMonth, isLeapYear);
            endMonth = (endMonth == 1) ? 12 : endMonth - 1;
            endYear = (endMonth == 12) ? endYear - 1 : endYear;
            if (startYear > endYear) {
                return new int[]{-7};
            }
        }

        if (startMonth > endMonth) {
            endMonth += 12;
            endYear--;
            if (startYear > endYear) {
                return new int[]{-8};
            }
        }

        return new int[]{endDay - startDay,
                endMonth - startMonth,
                endYear - startYear};
    }

    private int[] durationOnTheTimeI_HM(String startTime, String endTime) {

        int startH, startM,
                endH, endM;

        String[] startTimeSplit = startTime.split(":");
        String[] endTimeSplit = endTime.split(":");

        startH = Integer.valueOf(startTimeSplit[0]);
        endH = Integer.valueOf(endTimeSplit[0]);
        OutLog.p(startH, endH);
        try {
            if (startH > endH) {
                OutLog.pl();
                return new int[]{-1};
            } else {
                startM = Integer.valueOf(startTimeSplit[1]);
                endM = Integer.valueOf(endTimeSplit[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new int[]{-2};
        }
        if (startH > 23 || startH < 0) {
            OutLog.pl();
            return new int[]{-3};
        }
        if (startM > 59 || startM < 0) {
            OutLog.pl();
            return new int[]{-4};
        }

        if (startM > endM) {
            endM += 60;
            endH--;
            if (startH > endH) {
                return new int[]{-8};
            }
        }
        OutLog.p(endM - startM, endH - startH);
        return new int[]{endM - startM, endH - startH};
    }

    ///========================================================
    ///---PUBLIC ARRAY
    ///========================================================
    public int[] getGregoriantYearArrayInRangeI(String fromDate, String toDate, TtSort sort) {
        if (fromDate != null && !fromDate.isEmpty()) {
            int syi, eyi;
            int[] s;
            String y = fromDate.split("/")[0];
            syi = Integer.parseInt(y);
            if (toDate != null && !toDate.isEmpty()) {
                y = toDate.split("/")[0];
                eyi = Integer.parseInt(y);
                //            OutLog.p("eyi " + eyi);
            } else {
                eyi = Calendar.getInstance().get(Calendar.YEAR);
            }
            OutLog.pl("");
            eyi -= syi;
            OutLog.pl("def  " + eyi);
            s = new int[eyi + 1];
            if (sort == TtSort.AECS) {
                for (int i = 0; i <= eyi; i++) {
                    //            OutLog.p(syi + i);
                    s[i] = syi + i;
                }
            } else {
                for (int i = eyi; i >= 0; i--) {
                    //            OutLog.p(syi + i);
                    s[eyi - i] = syi + i;
                }
            }
            return s;
        }
        //            OutLog.pl("");
        return null;
    }

    public int getYearI() {
        persCal.setTime(new Date());
        return persCal.get(Calendar.YEAR);
    }

    public static ParsCalendar getInstance() {
        return instance;
    }

    public int[] getYearArrayInRangeI(String fromDate, String toDate, TtSort sort) {
        if (fromDate != null && !fromDate.isEmpty()) {
            int syi, eyi;
            int[] s;
            String y = fromDate.split("/")[0];
            syi = Integer.parseInt(y);
            if (toDate != null && !toDate.isEmpty()) {
                y = toDate.split("/")[0];
                eyi = Integer.parseInt(y);
                //            OutLog.p("eyi " + eyi);
            } else {
                eyi = ParsCalendar.getInstance().getYearI();
            }
            //            OutLog.pl("");
            eyi -= syi;
            //            OutLog.pl("def  " + eyi);
            s = new int[eyi + 1];
            if (sort == TtSort.AECS) {
                for (int i = 0; i <= eyi; i++) {
                    //            OutLog.p(syi + i);
                    s[i] = syi + i;
                }
            } else {
                for (int i = eyi; i >= 0; i--) {
                    //            OutLog.p(syi + i);
                    s[eyi - i] = syi + i;
                }
            }
            return s;
        }
        //            OutLog.pl("");
        return null;
    }


    public String getShortDate() {
        persCal.setTime(new Date());
        return getPersDate();
    }

    ///========================================================
    ///---PUBLIC CONVERTOR
    ///========================================================
    public String toTimeString(long startTime) {
        int d = (int) ((new Date().getTime() - startTime) / 1000);
        int ds = d % 60;
        int dm = d / 60;
        return ((dm == 0) ? "" : dm + " دقیقه")
                + ((dm == 0 || ds == 0) ? "" : " و ") + (ds == 0 ? "" : ds + " ثانیه");
    }

    public String toRemindingTimeString(long startTime, int ceilingSeconds) {
        int diffSecond = (int) ((startTime + (ceilingSeconds * 1000) - new Date().getTime()) / 1000);
        int remindSec = (diffSecond % 60);
        int remindMin = diffSecond / 60;
        return ((remindMin == 0) ? "" : remindMin + " دقیقه")
                + ((remindMin == 0 || remindSec == 60) ? "" : " و ") + (remindSec == 60 ? "" : remindSec + " ثانیه");
    }

    ///========================================================
    ///---PUBLIC CONVERTOR
    ///========================================================
    private String[] getPersTime(TtCalendarItem calendarItem, int after, String shortTime) {

        String[] tm = shortTime.split(":");
        if (tm.length != 3) {
            OutLog.pl();
            return null;
        }

        int h = 0, m = 0, s = 0, d = 0;
        switch (calendarItem) {
            case Hour:
                h = Integer.parseInt(tm[0]);
                h += after;
                if (h > 23) {
                    return new String[]{(h / 24) + "", getPersTime(h % 24, Integer.parseInt(tm[1]), Integer.parseInt(tm[2]))};
                }
                return new String[]{"0", getPersTime(h, Integer.parseInt(tm[1]), Integer.parseInt(tm[2]))};

            case Minute:
                h = Integer.parseInt(tm[0]);
                m = Integer.parseInt(tm[1]);
                m += after;
                if (m > 59) {
                    h += (m / 60);
                    if (h > 23) {
                        d = h / 24;
                        h = h % 24;
                    }
                    m = m % 60;
                }
                return new String[]{d + "", getPersTime(h, m, Integer.parseInt(tm[2]))};
            case Second:
                h = Integer.parseInt(tm[0]);
                m = Integer.parseInt(tm[1]);
                s = Integer.parseInt(tm[2]);
                s += after;
                if (s > 59) {
                    m += s / 60;
                    if (m > 59) {
                        h += (m / 60);
                        if (h > 23) {
                            d = h / 24;
                            h = h % 24;
                        }
                        m = m % 60;
                    }
                    s = s % 60;
                }
                return new String[]{d + "", getPersTime(h, m, s)};
        }

        return new String[]{"0", shortTime};
    }

    private String getPersTime(int hour, int min, int sec) {
        return (hour < 10 ? "0" + hour : hour)
                + ":" + (min < 10 ? "0" + min : min)
                + ":" + (sec < 10 ? "0" + sec : sec);
    }
}
