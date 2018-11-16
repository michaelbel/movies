package org.michaelbel.moviemade;

import android.util.Log;

import androidx.annotation.NonNull;

public class log {

    private static final String TAG = "258";

    public static void e(@NonNull String message) {
        Log.e(TAG, message);
    }
}