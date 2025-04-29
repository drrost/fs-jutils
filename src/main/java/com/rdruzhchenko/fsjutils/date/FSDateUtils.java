package com.rdruzhchenko.fsjutils.date;

import com.rdruzhchenko.fsjutils.exception.FSDateException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Utility class providing various date and time manipulation operations.
 * This class contains methods for date formatting, parsing, conversion between
 * different date formats, date calculations, and date/time comparisons.
 */
public class FSDateUtils {

    /** Standard date format: day.month.year (e.g., 31.12.2023) */
    public static final String DATE_FORMAT_LONG = "dd.MM.yyyy";

    /** Month and year format (e.g., 12.2023) */
    private static final String DATE_FORMAT_MIDDLE = "MM.yyyy";

    /** Year only format (e.g., 2023) */
    private static final String DATE_FORMAT_SHORT = "yyyy";

    /** Time format with hours, minutes, and seconds (e.g., 23:59:59) */
    private static final String TIME_FORMAT = "HH:mm:ss";

    /** Date and time format with seconds (e.g., 31.12.2023 23:59:59) */
    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    /** Date and time format without seconds (e.g., 31.12.2023 23:59) */
    private static final String DATE_TIME_FORMAT_NO_SECONDS = "dd.MM.yyyy HH:mm";

    /** Descending date and time format without seconds (e.g., 2023.12.31 23:59) */
    private static final String DATE_TIME_DESC_FORMAT_NO_SECONDS = "yyyy.MM.dd HH:mm";

    /** Descending date and time format with seconds (e.g., 2023.12.31 23:59:59) */
    private static final String DATE_TIME_SECONDS_DESC_FORMAT = "yyyy.MM.dd HH:mm:ss";

    /** PostgreSQL date format (e.g., 2023-12-31) */
    public static final String DATE_FORMAT_POSTGRES = "yyyy-MM-dd";

    /**
     * Converts a date string to a Date object.
     * Tries to parse the string using different formats (long, middle, short).
     *
     * @param dateString The date string to convert
     * @return The Date object, or null if the input is null or empty
     * @throws FSDateException if the date string cannot be parsed using any of the supported formats
     */
    public static Date dateFromString(String dateString) throws FSDateException {
        if (dateString == null || dateString.isEmpty())
            return null;
        Date result = dateFrom(DATE_FORMAT_LONG, dateString);
        if (result != null)
            return result;

        result = dateFrom(DATE_FORMAT_MIDDLE, dateString);
        if (result != null)
            return result;

        result = dateFrom(DATE_FORMAT_SHORT, dateString);
        if (result != null)
            return result;

        throw new FSDateException("Failed to parse date string: \"" + dateString.trim() + "\". Expected formats: " + DATE_FORMAT_LONG + ", " + DATE_FORMAT_MIDDLE + ", or " + DATE_FORMAT_SHORT);
    }

    /**
     * Converts a Date object to a string using the standard date format (dd.MM.yyyy).
     *
     * @param date The Date object to convert
     * @return The formatted date string, or null if the input is null
     */
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_LONG);
        return formatter.format(date);
    }

    /**
     * Gets the current date as a string in the standard format (dd.MM.yyyy).
     *
     * @return The current date as a string
     */
    public static String getCurrentDate() {
        return getNowWithFormat(DATE_FORMAT_LONG);
    }

    /**
     * Gets the current date as a Date object.
     *
     * @return The current date as a Date object
     */
    public static Date getCurrentDateAsDate() {
        var dateString = getNowWithFormat(DATE_FORMAT_LONG);
        return dateFromString(dateString);
    }

    /**
     * Gets the current time as a string in the format HH:mm:ss.
     *
     * @return The current time as a string
     */
    public static String getCurrentTime() {
        return getNowWithFormat(TIME_FORMAT);
    }

    /**
     * Gets the current date and time as a string in the format dd.MM.yyyy HH:mm.
     *
     * @return The current date and time as a string
     */
    public static String getCurrentDateTime() {
        return getNowWithFormat(DATE_TIME_FORMAT_NO_SECONDS);
    }

    /**
     * Converts a date string to a LocalDateTime object.
     *
     * @param dateString The date string to convert
     * @return The LocalDateTime object
     */
    public static LocalDateTime dateStringToDateTime(String dateString) {
        var date = FSDateUtils.dateFromString(dateString);
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    /**
     * Gets the current date and time in descending format (yyyy.MM.dd HH:mm).
     *
     * @return The current date and time in descending format
     */
    public static String getCurrentDateTimeDesc() {
        return getNowWithFormat(DATE_TIME_DESC_FORMAT_NO_SECONDS);
    }

    /**
     * Gets the current date and time with seconds in descending format (yyyy.MM.dd HH:mm:ss).
     *
     * @return The current date and time with seconds in descending format
     */
    public static String getCurrentDateTimeSecondsDesc() {
        return getNowWithFormat(DATE_TIME_SECONDS_DESC_FORMAT);
    }

    /**
     * Gets the current date and time with seconds (dd.MM.yyyy HH:mm:ss).
     *
     * @return The current date and time with seconds
     */
    public static String getCurrentDateTimeSeconds() {
        return getNowWithFormat(DATE_TIME_FORMAT);
    }

    /**
     * Formats a Date object as a date and time string (dd.MM.yyyy HH:mm:ss).
     *
     * @param date The Date object to format
     * @return The formatted date and time string
     */
    public static String getStringDateTimeFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * Converts a java.util.Date to a java.time.LocalDate.
     *
     * @param dateToConvert The Date object to convert
     * @return The LocalDate object
     */
    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    /**
     * Converts a java.time.LocalDate to a java.util.Date.
     *
     * @param dateToConvert The LocalDate object to convert
     * @return The Date object
     */
    public static Date localDateToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
    }

    private static Date dateFrom(String format, String dateString) {
        if (dateString == null)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date result = null;
        try {
            result = formatter.parse(dateString);
        } catch (ParseException ignore) {
        }
        return result;
    }

    private static String getNowWithFormat(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Adds a specified number of days to a date string.
     *
     * @param dateString The date string to add days to
     * @param i The number of days to add (can be negative to subtract days)
     * @return A new date string with the days added
     */
    public static String dateByAddDays(String dateString, int i) {
        Date date = dateFromString(dateString);
        return dateByAddDays(date, i);
    }

    /**
     * Adds a specified number of days to a Date object and returns the result as a string.
     *
     * @param date The Date object to add days to
     * @param i The number of days to add (can be negative to subtract days)
     * @return A new date string with the days added
     */
    public static String dateByAddDays(Date date, int i) {
        var newDate = dateByAddDaysAsDate(date, i);
        return dateToString(newDate);
    }

    /**
     * Adds a specified number of days to a Date object and returns the result as a Date.
     *
     * @param date The Date object to add days to
     * @param i The number of days to add (can be negative to subtract days)
     * @return A new Date object with the days added
     */
    public static Date dateByAddDaysAsDate(Date date, int i) {
        LocalDate localDate = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        LocalDate newLocalDate = localDate.plusDays(i);
        return Date.from(newLocalDate.atStartOfDay()
            .atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Checks if a string is a valid date in one of the supported formats.
     *
     * @param dateString The string to check
     * @return true if the string is a valid date, false otherwise
     */
    public static boolean isValidDate(String dateString) {
        if (dateString == null) {
            return false;
        }
        if (dateString.length() > 10) {
            return false;
        }
        try {
            Date date = dateFromString(dateString);
            return date != null;
        } catch (FSDateException e) {
            return false;
        }
    }

    /**
     * Calculates the number of days between two dates.
     *
     * @param dateBegin The start date
     * @param dateEnd The end date
     * @return The number of days between the dates, or -1 if either date is null
     */
    public static int daysBetween(Date dateBegin, Date dateEnd) {
        if (dateBegin == null || dateEnd == null) {
            return -1;
        }
        LocalDate date1 = dateBegin.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        LocalDate date2 = dateEnd.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        return (int) Duration.between(date1.atStartOfDay(), date2.atStartOfDay()).toDays();
    }

    /**
     * Extracts the year from a date string.
     *
     * @param date The date string in format dd.MM.yyyy
     * @return The year as an integer
     */
    public static int yearForDate(String date) {
        var items = date.split("\\.");
        return Integer.parseInt(items[2]);
    }

    /**
     * Checks if the first date is less than the second date.
     *
     * @param date1 The first date string
     * @param date2 The second date string
     * @return true if date1 is earlier than date2, false otherwise
     * @throws FSDateException if either date string cannot be parsed
     */
    public static boolean isDateLessThan(String date1, String date2) throws FSDateException {
        var date1date = dateFromString(date1);
        var date2date = dateFromString(date2);
        return date1date.compareTo(date2date) < 0;
    }

    /**
     * Checks if the first date is less than or equal to the second date.
     *
     * @param date1 The first date string
     * @param date2 The second date string
     * @return true if date1 is earlier than or equal to date2, false otherwise
     * @throws FSDateException if either date string cannot be parsed
     */
    public static boolean isDateLessOrEqualsThan(String date1, String date2) throws FSDateException {
        var date1date = dateFromString(date1);
        var date2date = dateFromString(date2);
        return date1date.compareTo(date2date) <= 0;
    }

    /**
     * Extracts the day part from a date string.
     *
     * @param dateCheckOut The date string in format dd.MM.yyyy
     * @return The day part as a string, or null if the input is null
     */
    public static String getDay(String dateCheckOut) {
        if (dateCheckOut == null) {
            return null;
        }
        return dateCheckOut.substring(0, 2);
    }

    /**
     * Returns the Ukrainian month name in genitive case for a given date.
     *
     * @param dateCheckOut The date string in format dd.MM.yyyy
     * @return The Ukrainian month name in genitive case
     */
    public static String getMonthWordInGenitiveCase(String dateCheckOut) {
        var month = dateCheckOut.substring(3, 5);
        return switch (month) {
            case "01" -> "січня";
            case "02" -> "лютого";
            case "03" -> "березня";
            case "04" -> "квітня";
            case "05" -> "травня";
            case "06" -> "червня";
            case "07" -> "липня";
            case "08" -> "серпня";
            case "09" -> "вересня";
            case "10" -> "жовтня";
            case "11" -> "листопада";
            case "12" -> "грудня";
            default -> "unknown";
        };
    }

    /**
     * Extracts the year part from a date string.
     *
     * @param date The date string in format dd.MM.yyyy
     * @return The year part as a string, or null if the input is null
     */
    public static String getYear(String date) {
        if (date == null) {
            return null;
        }
        return date.substring(6, 10);
    }

    /**
     * Gets the start of the week (Monday) for a given date.
     *
     * @param date The date string
     * @return The date string for the Monday of the week containing the given date
     */
    public static String getWeekStart(String date) {

        Date dateDate = FSDateUtils.dateFromString(date);

        // Go backward to get Monday
        LocalDate monday = dateToLocalDate(dateDate);
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }

        return dateToString(localDateToDate(monday));
    }

    /**
     * Gets the end of the week (Sunday) for a given date.
     *
     * @param date The date string
     * @return The date string for the Sunday of the week containing the given date
     */
    public static String getWeekEnd(String date) {
        Date dateDate = FSDateUtils.dateFromString(date);

        // Go forward to get Sunday
        LocalDate sunday = dateToLocalDate(dateDate);
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }

        return dateToString(localDateToDate(sunday));
    }

    /**
     * Extracts the time part from a date-time string.
     *
     * @param dateTime The date-time string in format dd.MM.yyyy HH:mm:ss or dd.MM.yyyy HH:mm
     * @return The time part of the date-time string
     * @throws FSDateException if the date-time format is invalid
     */
    public static String getTimeFromDateTime(String dateTime) {
        String[] splitedDateTime = dateTime.split(" ");
        if (splitedDateTime[1] == null) {
            throw new FSDateException("Invalid date-time format: '" + dateTime + "'. Expected format: 'dd.MM.yyyy HH:mm:ss' or 'dd.MM.yyyy HH:mm'");
        }
        return splitedDateTime[1];
    }

    /**
     * Adds a specified number of minutes to a time string.
     *
     * @param time The time string in format HH:mm:ss or HH:mm
     * @param minutesToAdd The number of minutes to add
     * @return The time string with minutes added, or the original time if minutesToAdd is not positive
     * @throws FSDateException if the time format is invalid
     */
    public static String plusMinutesToTime(String time, int minutesToAdd) {
        try {
            if (minutesToAdd > 0) {
                return LocalTime.parse(time).plusMinutes(minutesToAdd).toString();
            } else {
                return time;
            }
        } catch (DateTimeParseException e) {
            throw new FSDateException("Failed to parse time string: \"" + time.trim() + "\". Expected format: 'HH:mm:ss' or 'HH:mm'", e);
        }
    }

    /**
     * Checks if the first time is less than the second time.
     *
     * @param time1 The first time string in format HH:mm:ss or HH:mm
     * @param time2 The second time string in format HH:mm:ss or HH:mm
     * @return true if time1 is earlier than time2, false otherwise
     * @throws FSDateException if either time string cannot be parsed
     */
    public static boolean isTimeLessThan(String time1, String time2) {
        try {
            var time1Seconds = LocalTime.parse(time1).toSecondOfDay();
            var time2Seconds = LocalTime.parse(time2).toSecondOfDay();
            return time1Seconds < time2Seconds;
        } catch (DateTimeParseException e) {
            throw new FSDateException("Failed to parse time strings: \"" + time1.trim() + "\" or \"" + time2.trim() + "\". Expected format: 'HH:mm:ss' or 'HH:mm'", e);
        }
    }

    /**
     * Checks if the first time is greater than the second time.
     *
     * @param time1 The first time string in format HH:mm:ss or HH:mm
     * @param time2 The second time string in format HH:mm:ss or HH:mm
     * @return true if time1 is later than time2, false otherwise
     * @throws FSDateException if either time string cannot be parsed
     */
    public static boolean isTimeGreaterThan(String time1, String time2) {
        if (time1.equals(time2)) {
            return false;
        }
        return !isTimeLessThan(time1, time2);
    }

    /**
     * Compares two date-time strings.
     *
     * @param dateStr1 The first date-time string in format dd.MM.yyyy HH:mm:ss
     * @param dateStr2 The second date-time string in format dd.MM.yyyy HH:mm:ss
     * @return A negative integer if dateStr1 is earlier than dateStr2,
     *         zero if they are equal, or a positive integer if dateStr1 is later than dateStr2
     * @throws FSDateException if either date-time string cannot be parsed
     */
    public static int compareDates(String dateStr1, String dateStr2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

        try {
            Date date1 = sdf.parse(dateStr1);
            Date date2 = sdf.parse(dateStr2);

            return date1.compareTo(date2);
        } catch (ParseException e) {
            throw new FSDateException("Invalid date format for comparison: \"" + dateStr1 + "\" or \"" + dateStr2 + "\". Expected format: " + DATE_TIME_FORMAT, e);
        }
    }

    /**
     * Formats a duration in nanoseconds to a human-readable string in Ukrainian.
     *
     * @param nanos The duration in nanoseconds
     * @return A formatted string representing the duration in days, hours, minutes, seconds, and milliseconds
     */
    public static String formatDuration(long nanos) {
        long millis = TimeUnit.NANOSECONDS.toMillis(nanos);

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days);
        long milliseconds = millis - TimeUnit.SECONDS.toMillis(seconds) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.HOURS.toMillis(hours) - TimeUnit.DAYS.toMillis(days);

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append(" дн. ");
        }
        if (hours > 0) {
            result.append(hours).append(" г. ");
        }
        if (minutes > 0) {
            result.append(minutes).append(" хв. ");
        }
        if (seconds > 0) {
            result.append(seconds).append(" сек. ");
        }
        if (milliseconds > 0) {
            result.append(milliseconds).append(" мс.");
        }

        if (result.isEmpty()) {
            result.append("0 мс.");
        }

        return result.toString().trim();
    }

    /**
     * Calculates the elapsed time from a start time in nanoseconds to the current time.
     *
     * @param startTime The start time in nanoseconds (from System.nanoTime())
     * @return A formatted string representing the elapsed time
     */
    public static String calcElapsedTime(long startTime) {
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        return FSDateUtils.formatDuration(duration);
    }

    /**
     * Converts a date string from standard format to PostgreSQL format.
     *
     * @param s The date string in standard format (dd.MM.yyyy)
     * @return The date string in PostgreSQL format (yyyy-MM-dd)
     * @throws FSDateException if the date string cannot be parsed
     */
    public static String stringToPostgres(String s) {
        var date = dateFromString(s);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_POSTGRES);
        return formatter.format(date);
    }

    /**
     * Converts a date string from PostgreSQL format to standard format.
     *
     * @param s The date string in PostgreSQL format (yyyy-MM-dd)
     * @return The date string in standard format (dd.MM.yyyy)
     * @throws FSDateException if the date string cannot be parsed
     */
    public static String postgresToString(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_POSTGRES);
        try {
            var date = formatter.parse(s);
            formatter = new SimpleDateFormat(DATE_FORMAT_LONG);
            return formatter.format(date);
        } catch (ParseException e) {
            throw new FSDateException("Failed to parse PostgreSQL date format: \"" + s + "\". Expected format: " + DATE_FORMAT_POSTGRES, e);
        }
    }

    /**
     * Adds a specified number of months to a date string.
     *
     * @param s The date string in standard format (dd.MM.yyyy)
     * @param months The number of months to add (can be negative to subtract months)
     * @return A new date string with the months added
     * @throws FSDateException if the date string cannot be parsed
     */
    public static String plusMonth(String s, int months) {
        var date = FSDateUtils.dateFromString(s);
        Instant instant = date.toInstant();
        Instant oneMonthAgoInstant = instant
            .atZone(ZoneId.systemDefault())
            .plusMonths(months)
            .toInstant();
        date = Date.from(oneMonthAgoInstant);
        return FSDateUtils.dateToString(date);
    }
}
