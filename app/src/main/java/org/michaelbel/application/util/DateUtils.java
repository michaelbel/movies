package org.michaelbel.application.util;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("all")
public class DateUtils {

    private static final String DEFAULT_DATE_FORMAT = "MMMM yyyy";

    public static String getYesterdayDate() {
        return getYesterdayDate(DEFAULT_DATE_FORMAT);
    }

    public static String getYesterdayDate(@NonNull String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return formatDate(format, calendar.getTime());
    }

    public static String getTodayDate() {
        return getTodayDate(DEFAULT_DATE_FORMAT);
    }

    public static String getTodayDate(@NonNull String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        return formatDate(format, calendar.getTime());
    }

    public static String getTomorrowDate() {
        return getTomorrowDate(DEFAULT_DATE_FORMAT);
    }

    public static String getTomorrowDate(@NonNull String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return formatDate(format, calendar.getTime());
    }

    private static String formatDate(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        return simpleDateFormat.format(date != null ? date : new Date());
    }
}