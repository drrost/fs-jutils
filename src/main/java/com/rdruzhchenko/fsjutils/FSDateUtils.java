package com.rdruzhchenko.fsjutils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FSDateUtils {
    private static String DATE_SEPARATOR = ".";

    public static final String DATE_FORMAT_LONG = "dd.MM.yyyy";
    private static final String DATE_FORMAT_MIDDLE = "MM.yyyy";
    private static final String DATE_FORMAT_SHORT = "yyyy";

    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private static final String DATE_TIME_FORMAT_NO_SECONDS = "dd.MM.yyyy HH:mm";
    private static final String DATE_TIME_DESC_FORMAT_NO_SECONDS = "yyyy.MM.dd HH:mm";
    private static final String DATE_TIME_SECONDS_DESC_FORMAT = "yyyy.MM.dd HH:mm:ss";

    public static final String DATE_FORMAT_POSTGRES = "yyyy-MM-dd";

    public static Date dateFromString(String dateString) throws RuntimeException {
        if (dateString == null || dateString.equals(""))
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

        throw new RuntimeException("Can't parse string \"" + dateString.trim() + "\"");
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_LONG);
        return formatter.format(date);
    }

    public static String getCurrentDate() {
        return getNowWithFormat(DATE_FORMAT_LONG);
    }

    public static Date getCurrentDateAsDate() {
        var dateString = getNowWithFormat(DATE_FORMAT_LONG);
        return dateFromString(dateString);
    }

    public static String getCurrentTime() {
        return getNowWithFormat(TIME_FORMAT);
    }

    public static String getCurrentDateTime() {
        return getNowWithFormat(DATE_TIME_FORMAT_NO_SECONDS);
    }

    public static LocalDateTime dateStringToDateTime(String dateString) {
        var date = FSDateUtils.dateFromString(dateString);
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    public static String getCurrentDateTimeDesc() {
        return getNowWithFormat(DATE_TIME_DESC_FORMAT_NO_SECONDS);
    }

    public static String getCurrentDateTimeSecondsDesc() {
        return getNowWithFormat(DATE_TIME_SECONDS_DESC_FORMAT);
    }

    public static String getCurrentDateTimeSeconds() {
        return getNowWithFormat(DATE_TIME_FORMAT);
    }

    public static String getStringDateTimeFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(date);
    }

    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

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

    public static String dateByAddDays(String dateString, int i) {
        Date date = dateFromString(dateString);
        return dateByAddDays(date, i);
    }

    public static String dateByAddDays(Date date, int i) {
        var newDate = dateByAddDaysAsDate(date, i);
        return dateToString(newDate);
    }

    public static Date dateByAddDaysAsDate(Date date, int i) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate newLocalDate = localDate.plusDays(i);
        Date newDate = Date.from(newLocalDate.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant());
        return newDate;
    }

    public static boolean isValidDate(String dateString) {
        Date date = null;
        date = dateFromString(dateString);
        return date != null;
    }

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

    public static int yearForDate(String date) {
        var items = date.split("\\.");
        return Integer.parseInt(items[2]);
    }

    // Returns true if date1 < date2
    //
    public static boolean isDateLessThan(String date1, String date2) throws RuntimeException {
        var date1date = dateFromString(date1);
        var date2date = dateFromString(date2);
        return date1date.compareTo(date2date) < 0;
    }

    public static boolean isDateLessOrEqualsThan(String date1, String date2) throws RuntimeException {
        var date1date = dateFromString(date1);
        var date2date = dateFromString(date2);
        return date1date.compareTo(date2date) <= 0;
    }

    public static String getDay(String dateCheckOut) {
        if (dateCheckOut == null) {
            return null;
        }
        return dateCheckOut.substring(0, 2);
    }

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

    public static String getYear(String date) {
        if (date == null) {
            return null;
        }
        return date.substring(6, 10);
    }

    public static String getWeekStart(String date) {

        LocalDate today = null;
        Date dateDate = FSDateUtils.dateFromString(date);
        today = dateToLocalDate(dateDate);

        // Go backward to get Monday
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }

        return dateToString(localDateToDate(monday));
    }

    public static String getWeekEnd(String date) {
        LocalDate today = null;
        Date dateDate = FSDateUtils.dateFromString(date);
        today = dateToLocalDate(dateDate);

        // Go forward to get Sunday
        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }

        return dateToString(localDateToDate(sunday));
    }

    public static String getTimeFromDateTime(String dateTime) {
        String[] splitedDateTime = dateTime.split(" ");
        if (splitedDateTime[1] == null) {
            throw new RuntimeException("DateTime wrong format");
        }
        return splitedDateTime[1];
    }

    public static String plusMinutesToTime(String time, int minutesToAdd) {
        try {
            if (minutesToAdd > 0) {
                return LocalTime.parse(time).plusMinutes(minutesToAdd).toString();
            } else {
                return time;
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Can't parse string \"" + time.trim() + "\"");
        }
    }

    public static boolean isTimeLessThan(String time1, String time2) {
        try {
            var time1Seconds = LocalTime.parse(time1).toSecondOfDay();
            var time2Seconds = LocalTime.parse(time2).toSecondOfDay();
            return time1Seconds < time2Seconds;
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Can't parse string " + time1.trim() + " or " + time2.trim());
        }
    }

    public static boolean isTimeGreaterThan(String time1, String time2) {
        if (time1.equals(time2)) { return false; }
        return !isTimeLessThan(time1, time2);
    }

    public static int compareDates(String dateStr1, String dateStr2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

        try {
            Date date1 = sdf.parse(dateStr1);
            Date date2 = sdf.parse(dateStr2);

            return date1.compareTo(date2);
        } catch (ParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
            throw new IllegalArgumentException("Invalid date format", e);
        }
    }

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

    public static String calcElapsedTime(long startTime) {
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        return FSDateUtils.formatDuration(duration);
    }

    public static String stringToPostgres(String s) {
        var date = dateFromString(s);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_POSTGRES);
        return formatter.format(date);
    }

    public static String postgresToString(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_POSTGRES);
        try {
            var date = formatter.parse(s);
            formatter = new SimpleDateFormat(DATE_FORMAT_LONG);
            return formatter.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

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
