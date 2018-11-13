package org.michaelbel.moviemade.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.michaelbel.moviemade.Moviemade;

public class NetworkUtils {

    private static final int TYPE_WIFI = 1;
    private static final int TYPE_MOBILE = 2;
    private static final int TYPE_VPN = 3;
    private static final int TYPE_NOT_CONNECTED = 0;

    private static int getNetworkStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Moviemade.AppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_VPN) {
                return TYPE_VPN;
            }
        }

        return TYPE_NOT_CONNECTED;
    }

    public static boolean notConnected() {
        return getNetworkStatus() == TYPE_NOT_CONNECTED;
    }
}