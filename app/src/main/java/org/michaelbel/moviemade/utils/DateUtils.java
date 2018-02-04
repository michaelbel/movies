package org.michaelbel.moviemade.utils;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Moviemade;

import java.text.ParseException;
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

    public static String getMovieReleaseDate(String inputDate) {
        if (inputDate == null || inputDate.isEmpty()) {
            return "";
        }

        String inputPattern = "yyyy-MM-dd";
        String newPattern = "d MMM yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat(newPattern, Locale.US);

        Date date = null;
        try {
            date = inputFormat.parse(inputDate);
        } catch (ParseException e) {
            // todo Error.
        }

        return newFormat.format(date);
    }




    //private static final String DEFAULT_DATE_FORMAT = "MMMM yyyy";

    /*public static String getYesterdayDate() {
        return getYesterdayDate(DEFAULT_DATE_FORMAT);
    }*/

    /*public static String getYesterdayDate(@NonNull String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return formatDate(format, calendar.getTime());
    }*/

    /*public static String getTodayDate() {
        return getTodayDate(DEFAULT_DATE_FORMAT);
    }*/

    /*public static String getTodayDate(@NonNull String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        return formatDate(format, calendar.getTime());
    }*/

    /*public static String getTomorrowDate() {
        return getTomorrowDate(DEFAULT_DATE_FORMAT);
    }*/

   /* public static String getTomorrowDate(@NonNull String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return formatDate(format, calendar.getTime());
    }*/
}