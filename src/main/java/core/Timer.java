package core;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Timer {

    public long startStamp;
    private static Date date = new Date();

    public void start() {

        startStamp = getTimeStamp();
    }

    public static long getTimeStamp() {

        return new Date().getTime();
    }

    public boolean expired(int seconds) {
        int difference = (int) ((getTimeStamp() - startStamp) / 1000);
        return difference > seconds;
    }

    public static int getDifference(long start, long end) {

        return (int) ((end - start) / 1000);
    }

    public static String setActualDateForExpiration() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", new Locale("es", "AR"));
        return dateFormat.format(LocalDateTime.now());
    }

    public String setActualDateForNubi() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es", "AR"));
        return dateFormat.format(date);
    }

    public String setActualHourForNubi() {
        String timeSetter = setAmOrPmForNubiHour();
        if (timeSetter.equals("a. m.")) {
            SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm 'a. m.'");
            return hourFormat.format(date);
        } else {
            SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm 'p. m.'");
            return hourFormat.format(date);
        }
    }

    public String setAmOrPmForNubiHour() {
        SimpleDateFormat amOrPmFormat = new SimpleDateFormat("a");
        String timeSetter;

        if (amOrPmFormat.format(date).contains("AM"))
            timeSetter = "a. m.";
        else if (amOrPmFormat.format(date).contains("PM"))
            timeSetter = "p. m.";
        else
            timeSetter = null;

        return timeSetter;
    }

}
