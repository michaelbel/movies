package org.michaelbel.core.extensions;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Moviemade;

import java.lang.reflect.Field;

/**
 * Date: Sun, Feb 25 2018
 * Time: 10:15 MSK
 *
 * @author Michael Bel
 */

@SuppressWarnings("all")
public class Extensions {

    public static Context getContext() {
        return Moviemade.AppContext;
    }

    public static void copyToClipboard(@NonNull CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(text, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clipData);
        }
    }

    @ColorInt
    public static int adjustAlpha(@ColorInt int color, @FloatRange(from = 0.00F, to = 1.00F) float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static double[] rgbToHsv(int r, int g, int b) {
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

        return new double[]{ h, s, max };
    }

    public static int[] hsvToRgb(double h, double s, double v) {
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static void clearCursorDrawable(EditText editText) {
        if (editText == null) {
            return;
        }

        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.setInt(editText, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}