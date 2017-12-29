package org.michaelbel.application.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.michaelbel.application.moviemade.Moviemade;

@SuppressWarnings("all")
public class AndroidUtilsDev {

    private static Context getContext() {
        return Moviemade.AppContext;
    }

    public static boolean scrollbarsEnabled() {
        SharedPreferences prefs = getContext().getSharedPreferences("devconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("scrollbars", true);
    }
}