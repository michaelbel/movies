package org.michaelbel.moviemade.core.net

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import org.michaelbel.moviemade.presentation.App

object NetworkUtil {

    fun isNetworkConnected(): Boolean {
        val connectivityManager = App.appContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}