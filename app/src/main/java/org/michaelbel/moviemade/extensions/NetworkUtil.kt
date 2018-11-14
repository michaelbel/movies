package org.michaelbel.moviemade.extensions

import android.content.Context
import android.net.ConnectivityManager
import org.michaelbel.moviemade.Moviemade

object NetworkUtil {

    private const val TYPE_WIFI = 1
    private const val TYPE_MOBILE = 2
    private const val TYPE_VPN = 3
    private const val TYPE_NOT_CONNECTED = 4

    fun getNetworkStatus(): Int {
        val connectivityManager = Moviemade.AppContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null) {
            when {
                networkInfo.type == ConnectivityManager.TYPE_WIFI -> return TYPE_WIFI
                networkInfo.type == ConnectivityManager.TYPE_MOBILE -> return TYPE_MOBILE
                networkInfo.type == ConnectivityManager.TYPE_VPN -> return TYPE_VPN
            }
        }

        return TYPE_NOT_CONNECTED
    }

    fun notConnected(): Boolean {
        return getNetworkStatus() == TYPE_NOT_CONNECTED
    }
}