package org.michaelbel.application.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import org.michaelbel.application.moviemade.Moviemade;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("all")
public class AndroidUtils {

    private static Context getContext() {
        return Moviemade.AppContext;
    }

    public static String getProperty(String key) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = getContext().getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (Exception e) {
            // todo Error retrieving file asset
        }

        return null;
    }

    public static boolean includeAdult() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("adult", true);
    }

    public static void addToClipboard(CharSequence label, CharSequence text) {
        try {
            ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(label, text);
            if (clipboard != null) {
                clipboard.setPrimaryClip(clipData);
            }
        } catch (Exception e) {
            // todo Error
        }
    }

    public static boolean isPermissionGranted(@NonNull String permissionName) {
        int permission = ActivityCompat.checkSelfPermission(getContext(), permissionName);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(@NonNull String permission, Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[] { permission }, requestCode);
    }

    public static void createCacheDirectory() {
        File cacheDir = new File(Environment.getExternalStorageDirectory(), "Moviemade");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        File cacheDirImages = new File(Environment.getExternalStorageDirectory() + "/Moviemade", "Moviemade Images");
        if (!cacheDirImages.exists()) {
            cacheDirImages.mkdirs();
        }
    }

    public static String getCacheDirectory() {
        File directory = new File(Environment.getExternalStorageDirectory(), "Moviemade/Moviemade Images");
        if (!directory.exists()) {
            createCacheDirectory();
        }

        return directory.getPath();
    }

    public static int getColumns() {
        SharedPreferences prefs = Moviemade.AppContext.getSharedPreferences("main_config", Context.MODE_PRIVATE);
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
            Moviemade.AppHandler.post(runnable);
        } else {
            Moviemade.AppHandler.postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        Moviemade.AppHandler.removeCallbacks(runnable);
    }

    static {
        checkDisplaySize();
    }

    private static void checkDisplaySize() {
        try {
            Configuration configuration = Moviemade.AppContext.getResources().getConfiguration();
            usingHardwareInput = configuration.keyboard != Configuration.KEYBOARD_NOKEYS && configuration.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO;
            WindowManager manager = (WindowManager) Moviemade.AppContext.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    display.getMetrics(displayMetrics);
                    display.getSize(displaySize);
                }
            }
        } catch (Exception e) {
            // todo Error.
        }
    }
}