package org.michaelbel.application.util;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import org.michaelbel.application.moviemade.AppLoader;

@SuppressWarnings("all")
public class DeviceUtils {

    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getMacAddress(@NonNull Context context) {
        String macAddress = null;

        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            macAddress = wifiInfo.getMacAddress();
        } catch (Exception e) {
            //FirebaseCrash.logcat(Log.ERROR, "e_message", "Get macaddress error");
            //FirebaseCrash.report(e);
        }

        return macAddress;
    }

    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    public static String getBatteryLevel(@NonNull Context context) {
        Intent intent;
        int level = 0;
        int scale = 0;
        int percent;

        intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (intent != null) {
            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        }

        percent = (level * 100) / scale;
        return String.valueOf(percent) + "%";
    }

    public static void addToClipboard(CharSequence text) {
        try {
            ClipboardManager clipboardManager = (ClipboardManager)
                    AppLoader.AppContext.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("label", text);
            clipboardManager.setPrimaryClip(clipData);
        } catch (Exception e) {
            //FirebaseCrash.logcat(Log.ERROR, "e_message", "Add to clipboard error");
            //FirebaseCrash.report(e);
        }
    }
}