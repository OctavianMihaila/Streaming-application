package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Performs changes from unix time to human readable time and vice versa.
 */
public class DateAndTimeConvertor {
    public static String convertToHumanReadableDate(long unixDate) {
        Date date = new Date(unixDate * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
        String humanReadableDate = dateFormat.format(date);

        return humanReadableDate;
    }

    public static long convertToUnixDate(String humanReadableDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date;

        try {
            date = dateFormat.parse(humanReadableDate);
        } catch (ParseException e) {
            return 0;
        }

        return date.getTime() / 1000L;
    }

    public static String converTime(long timeInSeconds) {
        String humanReadableTime;
        int hours = (int) timeInSeconds / 3600;
        int minutes = (int) (timeInSeconds % 3600) / 60;
        int seconds = (int) timeInSeconds % 60;

        if (hours > 0) {
            humanReadableTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            humanReadableTime = String.format("%02d:%02d", minutes, seconds);
        }

        return humanReadableTime;
    }
}
