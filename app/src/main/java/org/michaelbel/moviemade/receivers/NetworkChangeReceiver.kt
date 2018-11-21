package org.michaelbel.moviemade.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkChangeReceiver(private var networkChangeListener: NetworkChangeListener?) : BroadcastReceiver() {

    companion object {
        const val INTENT_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            if (networkChangeListener != null) {
                networkChangeListener!!.onNetworkChanged()
            }
        }
    }
}