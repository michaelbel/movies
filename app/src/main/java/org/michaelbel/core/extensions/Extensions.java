package org.michaelbel.core.extensions;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Moviemade;

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