package org.michaelbel.moviemade.extensions;

import android.content.Context;
import android.content.res.Configuration;

import org.michaelbel.moviemade.R;

public class DeviceUtil {

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.tablet);
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}