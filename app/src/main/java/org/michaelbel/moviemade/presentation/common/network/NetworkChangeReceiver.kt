package org.michaelbel.moviemade.presentation.common.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkChangeReceiver(private var listener: Listener): BroadcastReceiver() {

    companion object {
        const val INTENT_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }

    interface Listener {
        fun onNetworkChanged()
    }

    override fun onReceive(context: Context, intent: Intent) {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            listener.onNetworkChanged()
        }
    }
}