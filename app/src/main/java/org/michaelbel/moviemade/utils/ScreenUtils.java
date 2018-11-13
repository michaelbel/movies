package org.michaelbel.moviemade.utils;

import android.content.res.Configuration;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Moviemade;

public class ScreenUtils {

    public static int dp(float value) {
        return (int) Math.ceil(Moviemade.AppContext.getResources().getDisplayMetrics().density * value);
    }

    public static boolean isTablet() {
        return Moviemade.AppContext.getResources().getBoolean(R.bool.tablet);
    }

    public static boolean isPortrait() {
        return Moviemade.AppContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}