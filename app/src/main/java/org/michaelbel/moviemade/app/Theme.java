package org.michaelbel.moviemade.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import org.michaelbel.moviemade.R;

public class Theme {

    public static final int THEME_LIGHT = 0;
    public static final int THEME_NIGHT = 1;
    public static final int THEME_NIGHT_BLUE = 2;

    private static Context getContext() {
        return Moviemade.AppContext;
    }

    public static int getTheme() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getInt("theme", 2);
    }

    // Base

    public static int primaryColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.colorPrimary;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.colorPrimary;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.colorPrimary;
        }

        return 0;
    }

    public static int primaryDarkColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.colorPrimaryDark;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.colorPrimaryDark;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.colorPrimaryDark;
        }

        return 0;
    }

    public static int accentColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.md_orange_500;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.colorAccent;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.colorAccent;
        }

        return 0;
    }

    public static int primaryTextColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.primaryTextColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_primaryTextColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_primaryTextColor;
        }

        return 0;
    }

    public static int secondaryTextColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.secondaryTextColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_secondaryTextColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_secondaryTextColor;
        }

        return 0;
    }

    public static int hindTextColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.disabledHintTextColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_disabledHintTextColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_disabledHintTextColor;
        }

        return 0;
    }

    public static int dividerColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.dividerColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_dividerColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_dividerColor;
        }

        return 0;
    }

    public static int iconActiveColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.iconActiveColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_iconActiveColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_iconActiveColor;
        }

        return 0;
    }

    public static int iconInactiveColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.iconInactiveColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_iconInactiveColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_iconInactiveColor;
        }

        return 0;
    }

    public static int statusBarColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.statusBarColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_statusBarColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_statusBarColor;
        }

        return 0;
    }

    public static int appBarColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.appBarColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_appBarColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_appBarColor;
        }

        return 0;
    }

    public static int backgroundColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.backgroundColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_backgroundColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_backgroundColor;
        }

        return 0;
    }

    public static int foregroundColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.foregroundColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_foregroundColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_foregroundColor;
        }

        return 0;
    }

    public static int thumbOnColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.switch_thumbOn;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_switch_thumbOn;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_switch_thumbOn;
        }

        return 0;
    }

    public static int thumbOffColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.switch_thumbOff;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_switch_thumbOff;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_switch_thumbOff;
        }

        return 0;
    }

    public static int trackOnColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.switch_trackOn;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_switch_trackOn;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_switch_trackOn;
        }

        return 0;
    }

    public static int trackOffColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.switch_trackOff;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_switch_trackOff;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_switch_trackOff;
        }

        return 0;
    }

    // Custom

    public static int selectedTabColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.md_white;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.md_white;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.colorAccent;
        }

        return 0;
    }

    public static int unselectedTabColor() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.color.night_blue_secondaryTextColor;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.color.night_secondaryTextColor;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return R.color.night_blue_secondaryTextColor;
        }

        return 0;
    }

    // STYLES

    public static int popupTheme() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.style.ThemeOverlay_AppCompat_Light;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.style.ThemeOverlay_AppCompat;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return -1;
        }

        return 0;
    }

    public static int alertTheme() {
        if (Theme.getTheme() == THEME_LIGHT) {
            return R.style.AlertLight;
        } else if (Theme.getTheme() == THEME_NIGHT) {
            return R.style.AlertNight;
        } else if (Theme.getTheme() == THEME_NIGHT_BLUE) {
            return -1;
        }

        return 0;
    }

    public static Drawable getIcon(@DrawableRes int resource, int colorFilter) {
        return getIcon(resource, colorFilter, PorterDuff.Mode.MULTIPLY);
    }

    public static Drawable getIcon(@DrawableRes int resource, int colorFilter, PorterDuff.Mode mode) {
        Drawable iconDrawable = getContext().getResources().getDrawable(resource, null);

        if (iconDrawable != null) {
            iconDrawable.clearColorFilter();
            iconDrawable.mutate().setColorFilter(colorFilter, mode);
        }

        return iconDrawable;
    }

    public static int getAttrColor(@NonNull Context context, @AttrRes int colorAttr) {
        int color = 0;
        int[] attrs = new int[] {
                colorAttr
        };

        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs);
            color = typedArray.getColor(0, 0);
            typedArray.recycle();
        } catch (Exception e) {
            // todo Error.
        }

        return color;
    }

    public static int[] getColorArray(@NonNull Context context, @ArrayRes int arrayRes) {
        if (arrayRes == 0) {
            return null;
        }

        TypedArray ta = context.getResources().obtainTypedArray(arrayRes);
        int[] colors = new int[ta.length()];

        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }

        ta.recycle();
        return colors;
    }
}