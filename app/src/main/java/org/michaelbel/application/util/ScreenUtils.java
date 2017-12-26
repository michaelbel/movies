package org.michaelbel.application.util;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import org.michaelbel.application.moviemade.AppLoader;
import org.michaelbel.application.R;

@SuppressWarnings("all")
public class ScreenUtils {

    public static int dp(float value) {
        return (int) Math.ceil(AppLoader.AppContext.getResources().getDisplayMetrics().density * value);
    }

    public static boolean isTablet() {
        return AppLoader.AppContext.getResources().getBoolean(R.bool.Tablet);
    }

    public static int getScreenWidth() {
        WindowManager windowManager = (WindowManager) AppLoader.AppContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        WindowManager windowManager = (WindowManager) AppLoader.AppContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static boolean isPortrait() {
        return AppLoader.AppContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static boolean isLandscape() {
        return AppLoader.AppContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isUndefined() {
        return AppLoader.AppContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_UNDEFINED;
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = AppLoader.AppContext.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = AppLoader.AppContext.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    public static boolean isScreenLock() {
        KeyguardManager keyguardManager = (KeyguardManager) AppLoader.AppContext.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isRTL() {
        return AppLoader.AppContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isLTR() {
        return AppLoader.AppContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR;
    }
}