package org.michaelbel.moviemade.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;

import androidx.annotation.DrawableRes;

public class Theme {

    private static final int THEME_LIGHT = 0;
    private static final int THEME_NIGHT = 1;
    private static final int THEME_NIGHT_BLUE = 2;

    private static Context getContext() {
        return Moviemade.AppContext;
    }

    public static int getTheme() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getInt("theme", 0);
    }

    public static int primaryTextColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.primaryText;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.primaryText;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.primaryText;
        }

        return 0;
    }

    public static int secondaryTextColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.secondaryText;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.secondaryText;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.secondaryText;
        }

        return 0;
    }

    public static int iconActiveColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.iconActive;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.iconActive;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.iconActive;
        }

        return 0;
    }

    public static Drawable getIcon(@DrawableRes int resource, int colorFilter) {
        return DrawableUtil.INSTANCE.getIcon(getContext(), resource, colorFilter, PorterDuff.Mode.MULTIPLY);
    }
}