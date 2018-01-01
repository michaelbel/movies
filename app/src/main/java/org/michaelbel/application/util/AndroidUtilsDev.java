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

    public static boolean floatingToolbar() {
        SharedPreferences prefs = getContext().getSharedPreferences("devconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("floating_toolbar", false);
    }

    public static boolean scrollToTop() {
        SharedPreferences prefs = getContext().getSharedPreferences("devconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("scroll_to_top", false);
    }

    public static boolean hamburgerIcon() {
        SharedPreferences prefs = getContext().getSharedPreferences("devconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("burger", false);
    }

    public static boolean searchResultsCount() {
        SharedPreferences prefs = getContext().getSharedPreferences("devconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("search_results_count", false);
    }
}