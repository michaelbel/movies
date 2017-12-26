package org.michaelbel.application.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import org.michaelbel.application.moviemade.AppLoader;

import java.io.InputStream;
import java.util.Properties;

public class AppUtils {

    public static String getProperty(String key) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = AppLoader.AppContext.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (Exception e) {
            //FirebaseCrash.logcat(Log.ERROR, "e_message", "Error retrieving file asset");
            //FirebaseCrash.report(e);
        }

        return null;
    }

    public static int getColumns() {
        SharedPreferences prefs = AppLoader.AppContext.getSharedPreferences("main_config", Context.MODE_PRIVATE);
        int type = prefs.getInt("view_type", 0);

        if (type == 0) {
            return ScreenUtils.isTablet() ? ScreenUtils.isPortrait() ? 6 : 8 : ScreenUtils.isPortrait() ? 3 : 5;
        } else if (type == 1) {
            return ScreenUtils.isTablet() ? ScreenUtils.isPortrait() ? 2 : 4 : ScreenUtils.isPortrait() ? 1 : 2;
        } else {
            return ScreenUtils.isTablet() ? ScreenUtils.isPortrait() ? 2 : 4 : ScreenUtils.isPortrait() ? 1 : 2;
        }
    }

    public static int getColumnsForVideos() {
        return ScreenUtils.isTablet() ? ScreenUtils.isPortrait() ? 2 : 4 : ScreenUtils.isPortrait() ? 1 : 2;
    }

    public static int getColumnsForImages() {
        return  ScreenUtils.isPortrait() ? 3 : 6;
    }

    private static Point displaySize = new Point();
    private static boolean usingHardwareInput;
    private static DisplayMetrics displayMetrics = new DisplayMetrics();

    public static float getPixelsInCM(float cm, boolean isX) {
        return (cm / 2.54f) * (isX ? displayMetrics.xdpi : displayMetrics.ydpi);
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            AppLoader.AppHandler.post(runnable);
        } else {
            AppLoader.AppHandler.postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        AppLoader.AppHandler.removeCallbacks(runnable);
    }

    static {
        checkDisplaySize();
    }

    private static void checkDisplaySize() {
        try {
            Configuration configuration = AppLoader.AppContext.getResources().getConfiguration();
            usingHardwareInput = configuration.keyboard != Configuration.KEYBOARD_NOKEYS && configuration.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO;
            WindowManager manager = (WindowManager) AppLoader.AppContext.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    display.getMetrics(displayMetrics);
                    display.getSize(displaySize);
                }
            }
        } catch (Exception e) {
            //FirebaseCrash.logcat(Log.ERROR, "e_message", "Error check display size");
            //FirebaseCrash.report(e);
        }
    }
}