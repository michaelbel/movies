package org.michaelbel.moviemade;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import org.michaelbel.moviemade.extensions.AndroidExtensions;

import androidx.annotation.DrawableRes;

public class Theme {

    public static final int THEME_LIGHT = 0;
    private static final int THEME_NIGHT = 1;
    public static final int THEME_NIGHT_BLUE = 2;

    private static Context getContext() {
        return Moviemade.AppContext;
    }

    public static int getTheme() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getInt("theme", 0);
    }

    public static int primaryColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.primary;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.primary;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.primary;
        }

        return 0;
    }

    public static int accentColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.accent;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.accent;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.accent;
        }

        return 0;
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

    public static int backgroundColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.background;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.background;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.background;
        }

        return 0;
    }

    public static int foregroundColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.foreground;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.foreground;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.foreground;
        }

        return 0;
    }

    public static int selectedTabColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.accent;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.accent;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.accent;
        }

        return 0;
    }

    public static Drawable getIcon(@DrawableRes int resource, int colorFilter) {
        return AndroidExtensions.getIcon(getContext(), resource, colorFilter, PorterDuff.Mode.MULTIPLY);
    }
}