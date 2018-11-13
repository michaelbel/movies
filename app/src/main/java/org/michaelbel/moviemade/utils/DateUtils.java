package org.michaelbel.moviemade.utils;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Moviemade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtils {

    private static String formatDate(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        return simpleDateFormat.format(date != null ? date : new Date());
    }

    public static String getCurrentDateAndTime() {
        String datePattern = "dd.MM.yy";
        String timePattern = "HH:mm";

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);

        String formatDate = formatDate(datePattern, calendar.getTime());
        String formatTime = formatDate(timePattern, Calendar.getInstance().getTime());

        return Moviemade.AppContext.getString(R.string.CurrentDateAndTime, formatDate, formatTime);
    }

    public static String getCurrentDateAndTimeWithMilliseconds() {
        String datePattern = "dd.MM.yy";
        String timePattern = "HH:mm:ss";

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);

        String formatDate = formatDate(datePattern, calendar.getTime());
        String formatTime = formatDate(timePattern, Calendar.getInstance().getTime());

        return Moviemade.AppContext.getString(R.string.CurrentDateAndTime, formatDate, formatTime);
    }
}