package org.michaelbel.moviemade;

import android.util.Log;

public class Logger {

    public static void e(String msg) {
        Log.e("2580", msg);
    }

    public static void e(String msg1, String msg2) {
        Log.e("2580", msg1 + "\n" + msg2);
    }

    public static void e(String msg1, String msg2, String msg3) {
        Log.e("2580", msg1 + "\n" + msg2 + "\n" + msg3);
    }
}