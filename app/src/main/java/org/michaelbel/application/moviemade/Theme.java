package org.michaelbel.application.moviemade;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import org.michaelbel.application.R;

import java.lang.reflect.Field;

@SuppressWarnings("all")
public class Theme {

    public static final int LIGHT_THEME = 0;
    public static final int NIGHT_THEME = 1;
    public static final int NIGHT_BLUE_THEME = 2;

    private static Context getContext() {
        return Moviemade.AppContext;
    }

    public static int getAppTheme() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getInt("theme", 2);
    }

    public static int primaryColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.colorPrimary;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.colorPrimary;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.colorPrimary;
        }

        return 0;
    }

    public static int primaryDarkColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.colorPrimaryDark;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.colorPrimaryDark;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.colorPrimaryDark;
        }

        return 0;
    }

    public static int accentColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.colorAccent;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.colorAccent;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.colorAccent;
        }

        return 0;
    }

    public static int primaryTextColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.primaryTextColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_primaryTextColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_primaryTextColor;
        }

        return 0;
    }

    public static int secondaryTextColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.secondaryTextColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_secondaryTextColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_secondaryTextColor;
        }

        return 0;
    }

    public static int hindTextColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.disabledHintTextColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_disabledHintTextColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_disabledHintTextColor;
        }

        return 0;
    }

    public static int dividerColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.dividerColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_dividerColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_dividerColor;
        }

        return 0;
    }

    public static int iconActiveColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.iconActiveColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_iconActiveColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_iconActiveColor;
        }

        return 0;
    }

    public static int iconInactiveColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.iconInactiveColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_iconInactiveColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_iconInactiveColor;
        }

        return 0;
    }

    public static int statusBarColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.statusBarColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_statusBarColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_statusBarColor;
        }

        return 0;
    }

    public static int appBarColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.appBarColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_appBarColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_appBarColor;
        }

        return 0;
    }

    public static int backgroundColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.backgroundColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_backgroundColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_backgroundColor;
        }

        return 0;
    }

    public static int foregroundColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.foregroundColor;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_foregroundColor;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_foregroundColor;
        }

        return 0;
    }

    public static int thumbOnColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.switch_thumbOn;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_switch_thumbOn;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_switch_thumbOn;
        }

        return 0;
    }

    public static int thumbOffColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.switch_thumbOff;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_switch_thumbOff;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_switch_thumbOff;
        }

        return 0;
    }

    public static int trackOnColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.switch_trackOn;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_switch_trackOn;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_switch_trackOn;
        }

        return 0;
    }

    public static int trackOffColor() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.color.switch_trackOff;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.color.night_switch_trackOff;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return R.color.night_blue_switch_trackOff;
        }

        return 0;
    }

    // STYLES

    public static int popupTheme() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.style.ThemeOverlay_AppCompat_Light;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.style.ThemeOverlay_AppCompat;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return -1;
        }

        return 0;
    }

    public static int alertTheme() {
        if (Theme.getAppTheme() == LIGHT_THEME) {
            return R.style.AlertLight;
        } else if (Theme.getAppTheme() == NIGHT_THEME) {
            return R.style.AlertNight;
        } else if (Theme.getAppTheme() == NIGHT_BLUE_THEME) {
            return -1;
        }

        return 0;
    }

    public static int selectableItemBackground() {
        int[] attrs = new int[] {
                R.attr.selectableItemBackground
        };

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        typedArray.recycle();

        return backgroundResource;
    }

    public static int selectableItemBackgroundBorderless() {
        int[] attrs = new int[] {
                R.attr.selectableItemBackgroundBorderless
        };

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        typedArray.recycle();

        return backgroundResource;
    }

    public static Drawable selectableItemBackgroundDrawable() {
        int[] attrs = new int[] {
                android.R.attr.selectableItemBackground
        };

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs);
        Drawable drawableFromTheme = typedArray.getDrawable(0);
        typedArray.recycle();

        return drawableFromTheme;
    }

    public static Drawable selectableItemBackgroundBorderlessDrawable() {
        int[] attrs = new int[] {
                android.R.attr.selectableItemBackgroundBorderless
        };

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs);
        Drawable drawableFromTheme = typedArray.getDrawable(0);
        typedArray.recycle();

        return drawableFromTheme;
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static void clearCursorDrawable(EditText editText) {
        if (editText == null) {
            return;
        }
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.setInt(editText, 0);
        } catch (Exception e) {}
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
        } catch (Exception e) {}

        return color;
    }

    @ColorInt
    public static int adjustAlpha(@ColorInt int color, @FloatRange(from = 0.00F, to = 1.00F) float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
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

    private static double[] rgbToHsv(int r, int g, int b) {
        double rf = r / 255.0;
        double gf = g / 255.0;
        double bf = b / 255.0;
        double max = (rf > gf && rf > bf) ? rf : (gf > bf) ? gf : bf;
        double min = (rf < gf && rf < bf) ? rf : (gf < bf) ? gf : bf;
        double h, s;
        double d = max - min;
        s = max == 0 ? 0 : d / max;
        if (max == min) {
            h = 0;
        } else {
            if (rf > gf && rf > bf) {
                h = (gf - bf) / d + (gf < bf ? 6 : 0);
            } else if (gf > bf) {
                h = (bf - rf) / d + 2;
            } else {
                h = (rf - gf) / d + 4;
            }
            h /= 6;
        }
        return new double[]{h, s, max};
    }

    private static int[] hsvToRgb(double h, double s, double v) {
        double r = 0, g = 0, b = 0;
        double i = (int) Math.floor(h * 6);
        double f = h * 6 - i;
        double p = v * (1 - s);
        double q = v * (1 - f * s);
        double t = v * (1 - (1 - f) * s);
        switch ((int) i % 6) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            case 5:
                r = v;
                g = p;
                b = q;
                break;
        }
        return new int[]{(int) (r * 255), (int) (g * 255), (int) (b * 255)};
    }
}