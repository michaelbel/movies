package org.michaelbel.moviemade.core.utils

import android.content.Context
import android.net.ConnectivityManager
import org.michaelbel.moviemade.presentation.App

object NetworkUtil {

    fun isNetworkConnected(): Boolean {
        val cm = App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}