package org.michaelbel.moviemade.utils;

import org.michaelbel.moviemade.Moviemade;

public class ScreenUtils {

    public static int dp(float value) {
        return (int) Math.ceil(Moviemade.AppContext.getResources().getDisplayMetrics().density * value);
    }
}