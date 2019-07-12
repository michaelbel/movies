package org.michaelbel.moviemade.core

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.annotation.RequiresPermission

inline fun <reified T: Any> Activity.startActivity(requestCode: Int = -1, options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()

    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T: Any> Activity.startActivity(options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()

    startActivity(intent, options)
}

inline fun <reified T: Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        val netInfo = it.activeNetworkInfo
        netInfo?.let { info ->
            if (info.isConnected) return true
        }
    }
    return false
}