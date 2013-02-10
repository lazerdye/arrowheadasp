/**
 * ArrowHead ASP Server 
 * This is a source file for the ArrowHead ASP Server - an 100% Java
 * VBScript interpreter and ASP server.
 *
 * For more information, see http://www.tripi.com/arrowhead
 *
 * Copyright (C) 2002  Terence Haddock
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package com.tripi.asp;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jregex.Matcher;
import jregex.Pattern;
import jregex.RETokenizer;

import org.apache.log4j.Logger;

/**
 * The AspDate class contains the special code needed for handling ASP
 * dates. This class can process most date format, and can be configured
 * for the default month, day, year ordering.
 * 
 * @author Terence Haddock
 * @version 0.9
 */
public class AspDate
{
    /** Debugging class */
    private static final Logger DBG = Logger.getLogger(AspDate.class);

    /** Month bit field */
    static final int MONTH = 0x10;

    /** Day bit field */
    static final int DAY = 0x8;

    /** Year bit field */
    static final int YEAR = 0x4;

    /** Time bit field */
    static final int TIME = 0x2;

    /** AM/PM bit field */
    static final int AMPM = 0x1;

    /** Field contains the current default order of the month, day, year fields */
    protected static String order = "MDY";

    /** The current date/time values as a calendar object */
    protected Calendar    cal = new GregorianCalendar();

    /** Does this object hold a time value? */
    protected boolean   hasTime = false;

    /** Does this object hold a date value? */
    protected boolean   hasDate = false;

    /** Long date/time format */
    static DateFormat longDateTimeFormat =
        new SimpleDateFormat("M/d/yyyy h:mm:ss aa");

    /** Long date format */
    static DateFormat longDateFormat =
        new SimpleDateFormat("EEEE, MMMM d, yyyy");

    /** Short date format */
    static DateFormat shortDateFormat = new SimpleDateFormat("M/d/yyyy");

    /** Long time format */
    static DateFormat longTimeFormat = new SimpleDateFormat("h:mm:ss aa");

    /** Short time format */
    static DateFormat shortTimeFormat = new SimpleDateFormat("H:mm");

    /**
     * This constructor creates an AspDate object from a generic time
     * string.
     * @param datestr date/time string
     * @throws AspException on error
     */
    public AspDate(String datestr) throws AspException
    {
        /* This is the tokenizer we will use */
        final Pattern p = new Pattern("[, \t\\-\\\\/]+");
        RETokenizer tok = p.tokenizer(datestr);
        String parts[] = tok.split();
        /* match will hold the type of data this string can represent in a
           bit pattern */
        int match[] = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            if (DBG.isDebugEnabled())
                DBG.debug("parts[" + i + "] = " + parts[i]);
            if (isNumber(parts[i])) {
                int iVal = Integer.parseInt(parts[i]);
                if (iVal > 0 && iVal <= 12) {
                    match[i] = MONTH | DAY | YEAR;
                } else if (iVal > 12 && iVal <= 31) {
                    match[i] = DAY | YEAR;
                } else {
                    match[i] = YEAR;
                }
            } else if (getMonthString(parts[i]) != -1) {
                match[i] = MONTH;
            } else if (isTimeString(parts[i])) {
                match[i] = TIME;
            } else if (parts[i].equalsIgnoreCase("am") ||
                    parts[i].equalsIgnoreCase("pm")) {
                match[i] = AMPM;
            } else {
                throw new AspCastException("Cannot convert string \"" +
                    datestr + "\" to date");
            }
        }
        if (DBG.isDebugEnabled()) {
            DBG.debug("Before logical elimination: ");
            for (int i = 0; i < parts.length; i++) {
                DBG.debug("match[" + parts[i] + "] = " + match[i]);
            }
        }
        /* From the bit patterns, perform a logical elimination of the
           match state */
        int bit = 1;
        while (bit <= (MONTH|DAY|YEAR|TIME|AMPM)) {
            for (int i = 0; i < parts.length; i++) {
                if (match[i] == bit) {
                    if (DBG.isDebugEnabled()) DBG.debug("best for bit " + bit
                        + ": " + i);
                    for (int j = 0 ; j < parts.length; j++)
                        if (j != i) match[j] &= ~bit;
                    break;
                }
            }
            bit*=2;
        }
        if (DBG.isDebugEnabled()) {
            DBG.debug("After logical elimination: ");
            for (int i = 0; i < parts.length; i++) {
                DBG.debug("match[" + parts[i] + "] = " + match[i]);
            }
        }
        /* Handle the MDY ordering for the last elimination */
        for (int k = 0; k < 3; k++) {
            if (order.charAt(k)=='M') bit = MONTH;
            else if (order.charAt(k)=='D') bit = DAY;
            else if (order.charAt(k)=='Y') bit = YEAR;
            if (DBG.isDebugEnabled()) DBG.debug("Bit: " + bit);
            for (int i = 0 ; i < parts.length; i++) {
                if ((match[i] & bit) == bit) {
                    for (int j = 0; j < parts.length; j++)
                        if (j != i) match[j] &= ~bit;
                    match[i] = bit;
                    break;
                }
            }
        }
        if (DBG.isDebugEnabled()) {
            DBG.debug("After ordering: ");
            for (int i = 0; i < parts.length; i++) {
                DBG.debug("match[" + parts[i] + "] = " + match[i]);
            }
        }
        /* Get the actual data which corresponds to the month,day,year fields */
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < parts.length ; i++)
        {
            if (match[i] == MONTH) {
                int month;
                if (Character.isLetter(parts[i].charAt(0))) {
                    month = getMonthString(parts[i]);
                } else {
                    month = Integer.parseInt(parts[i]) - 1;
                }
                cal.set(Calendar.MONTH, month);
                hasDate = true;
            } else if (match[i] == DAY) {
                int day = Integer.parseInt(parts[i]);
                cal.set(Calendar.DAY_OF_MONTH, day);
                hasDate = true;
            } else if (match[i] == YEAR) {
                int year = Integer.parseInt(parts[i]);
                if (year > 50 && year < 100) {
                    year+=1900;
                } else if (year < 100) {
                    year+=2000;
                }
                cal.set(Calendar.YEAR, year);
                hasDate = true;
            } else if (match[i] == TIME) {
                /* Check if a time is included */
                if (i < (match.length - 1) && match[i+1] == AMPM) {
                    parseTime(parts[i] + parts[i + 1]);
                } else {
                    parseTime(parts[i]);
                }
            } else if (match[i] == AMPM) {
                if (i == 0 || match[i - 1] != TIME) {
                    /* AM/PM has to be after a time */
                    throw new AspCastException("Cannot convert string \"" +
                        datestr + "\" to date");
                }
            } else {
                /* A part did not match */
                throw new AspCastException("Cannot convert string \"" +
                    datestr + "\" to date");
            }
        }
        if (!hasDate)
        {
            cal.set(Calendar.MONTH, 11);
            cal.set(Calendar.DAY_OF_MONTH, 30);
            cal.set(Calendar.YEAR, 1899);
        }
    }

    /**
     * This constructor creates an AspDate object from a Java Date object.
     * @param date Java date object
     */
    public AspDate(Date date)
    {
        this(date, true, true);
    }

    /**
     * This constructor creates an AspDate object from a Java Date object,
     * optionally setting the date and time values.
     * @param javaDate Java date object
     * @param hasTime has a time value?
     * @param hasDate has a date value?
     */
    public AspDate(Date javaDate, boolean hasTime, boolean hasDate)
    {
        cal.setTime(javaDate);
        this.hasTime = hasTime;
        this.hasDate = hasDate;
        if (!hasTime) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        if (!hasDate) {
            cal.set(Calendar.MONTH, 11);
            cal.set(Calendar.DAY_OF_MONTH, 30);
            cal.set(Calendar.YEAR, 1899);
        }
    }

    /**
     * This constructor creates an AspDate object from a Java calendar object.
     * @param cal Java calendar object
     */
    public AspDate(Calendar cal)
    {
        this(cal, true, true);
    }

    /**
     * This constructor creates an AspDate object from a Java Calendar object,
     * optionally setting the date and time values.
     * @param javaCal Java calendar object
     * @param hasTime has a time value?
     * @param hasDate has a date value?
     */
    public AspDate(Calendar javaCal, boolean hasTime, boolean hasDate)
    {
        this(javaCal.getTime(), hasTime, hasDate);
    }

    /**
     * Constructor set with specific time settings.
     * @param month Month setting
     * @param day Day setting
     * @param year Year setting
     * @param hour Hour setting
     * @param minute Minute setting
     * @param second Second setting
     */
    protected AspDate(int month, int day, int year, int hour, int minute, int second)
    {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        cal.set(Calendar.YEAR, 1899);
        if (month != -1)
        {
            cal.set(Calendar.MONTH, month);
            hasDate = true;
        }
        if (day != -1)
        {
            cal.set(Calendar.DAY_OF_MONTH, day);
            hasDate = true;
        }
        if (year != -1)
        {
            cal.set(Calendar.YEAR, year);
            hasDate = true;
        }
        if (hour != -1)
        {
            cal.set(Calendar.HOUR_OF_DAY, hour);
            hasTime = true;
        }
        if (minute != -1)
        {
            cal.set(Calendar.MINUTE, minute);
            hasTime = true;
        }
        if (second != -1)
        {
            cal.set(Calendar.SECOND, second);
            hasTime = true;
        }
    }

    /**
     * Constructor set with specific date-only settings.
     * @param month Month setting
     * @param day Day setting
     * @param year Year setting
     */
    protected AspDate(int month, int day, int year)
    {
        this(month, day, year, -1, -1, -1);
    }

    /**
     * This function determines if the specified string contains a number
     * @param str String to test
     * @return true if the value is a number, false otherwise
     */
    private static boolean isNumber(String str)
    {
        char ch[] = str.toCharArray();
        for (int x = 0; x < ch.length ; x++)
        {
            if (!Character.isDigit(ch[x])) return false;
        }
        return true;
    }

    /**
     * This funtion obtains from a string, the month the string corresponds
     * to. Uses the build-in DateFormatSymbols class to match strings to
     * months.
     * @param str String to convert to month number.
     * @return number of the month from 0 to 11, or -1 if the string is
     * not recognized
     */
    private static int getMonthString(String str)
    {
        DateFormatSymbols format = new DateFormatSymbols();
        String months[] = format.getShortMonths();
        String mon = str.toLowerCase();
        for (int i = 0; i < 12; i++)
            if (mon.startsWith(months[i].toLowerCase())) return i;
        return -1;
    }

    /**
     * This function attempts to determine if the given string is a time
     * string.
     * @param str string to check for time string
     * @return true if the string is a time string, false otherwise
     * @throws AspException on error
     */
    public static boolean isTimeString(String str) throws AspException
    {
        Pattern p = new Pattern("^[0-9]{1,2}:[0-9]{1,2}" +
            "(:[0-9]{1,2}(\\.[0-9]{1,2})?)?([aApP][mM])?$");
        Matcher m = p.matcher(str);
        if (m.matches()) return true;
        return false;
    }

    /**
     * This function parses the given string into hour, minute, second,
     * and sets the internal variables accordingly.
     * @param str Time string to parse
     * @throws AspException on error
     */
    protected void parseTime(String str) throws AspException
    {
        final Pattern p = new Pattern("^([0-9]{1,2}):([0-9]{1,2})" +
            "(:([0-9]{1,2})(\\.[0-9]{1,2})?)?([aApP][mM])?$");
        Matcher m = p.matcher(str);

        if (!m.find()) {
            throw new AspCastException("Cannot convert string \"" +
                str + "\" to time");
        }

        hasTime = true;
        int hour = Integer.parseInt(m.group(1));
        int minute = Integer.parseInt(m.group(2));
        int second = 0;
        if (m.group(4) != null) {
            second = Integer.parseInt(m.group(4));
        } else {
            second = 0;
        }
        if (m.group(6) != null) {
            String apm = m.group(6).toLowerCase();
            if (apm.equals("pm")) {
                hour+=12;
            }
        }
        if (DBG.isDebugEnabled()) {
            DBG.debug("Setting hour: " + hour);
            DBG.debug("Setting minute: " + minute);
            DBG.debug("Setting second: " + second);
        }
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        if (DBG.isDebugEnabled()) {
            DBG.debug("Resulting cal: " + cal);
        }
    }
    
    /**
     * Converts this AspDate object to a Calendar object 
     * @return calendar object representing this AspDate object
     */
    public Calendar toCalendar()
    {
        return (Calendar)cal.clone();
    }

    /**
     * Converts this AspDate object to a Date object 
     * @return date object representing this AspDate object
     */
    public Date toDate()
    {
        return cal.getTime();
    }

    /**
     * Get the month value of this AspDate
     * @return month value
     */
    public int getMonth()
    {
        return cal.get(Calendar.MONTH);
    }

    /**
     * Get the day value of this AspDate
     * @return day value
     */
    public int getDay()
    {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get the year value of this AspDate
     * @return year value
     */
    public int getYear()
    {
        return cal.get(Calendar.YEAR);
    }

    /**
     * Get the hour value of this AspDate
     * @return hour value
     */
    public int getHour()
    {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Get the minute value of this AspDate
     * @return minute value
     */
    public int getMinute()
    {
        return cal.get(Calendar.MINUTE);
    }

    /**
     * Get the second value of this AspDate
     * @return second value
     */
    public int getSecond()
    {
        return cal.get(Calendar.SECOND);
    }

    /**
     * Does this object have a time value?
     * @return true if this object has a time value, false otherwise.
     */
    public boolean hasTime()
    {
        return hasTime;
    }

    /**
     * Does this object have a date value?
     * @return true if this object has a date value, false otherwise.
     */
    public boolean hasDate()
    {
        return hasDate;
    }

    /**
     * Convert this date to a string.
     * @return String representation of this date.
     */
    public String toString()
    {
        if (!hasTime)
        {
            return shortDateFormat.format(toDate());
        } else if (!hasDate) {
            return longTimeFormat.format(toDate());
        } else {
            return longDateTimeFormat.format(toDate());
        }
    }
}
