package org.michaelbel.core.extensions;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;

import org.michaelbel.moviemade.app.Moviemade;

/**
 * Date: Sun, Feb 25 2018
 * Time: 10:15 MSK
 *
 * @author Michael Bel
 */

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
}