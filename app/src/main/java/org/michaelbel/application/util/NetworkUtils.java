package org.michaelbel.application.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.michaelbel.application.moviemade.AppLoader;

@SuppressWarnings("all")
public class NetworkUtils {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_VPN = 3;
    public static final int TYPE_NOT_CONNECTED = 0;

    public static int getNetworkStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                AppLoader.AppContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
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
}