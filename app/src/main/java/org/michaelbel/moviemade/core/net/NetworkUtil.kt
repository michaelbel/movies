package org.michaelbel.moviemade.core.net

import android.content.Context
import android.net.ConnectivityManager
import org.michaelbel.moviemade.presentation.App

object NetworkUtil {

    fun isNetworkConnected(): Boolean {
        val connectivityManager = App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}