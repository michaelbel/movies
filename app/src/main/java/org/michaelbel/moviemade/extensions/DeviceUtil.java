package org.michaelbel.moviemade.extensions;

import android.content.Context;
import android.content.res.Configuration;

import org.michaelbel.moviemade.R;

import androidx.annotation.NonNull;

public class DeviceUtil {

    public static boolean isTablet(@NonNull Context context) {
        return context.getResources().getBoolean(R.bool.tablet);
    }

    public static boolean isLandscape(@NonNull Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static int getStatusBarHeight(@NonNull Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        }

        return result;
    }

    public static int dp(@NonNull Context context, float value) {
        return (int) Math.ceil(context.getResources().getDisplayMetrics().density * value);
    }
}