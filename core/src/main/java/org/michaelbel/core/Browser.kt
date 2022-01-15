package org.michaelbel.core

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.preference.PreferenceManager

object Browser {

    fun openUrl(context: Context, url: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val inApp = preferences.getBoolean("browser", true)

        if (inApp) {
            openBrowserUrl(context, url)
        } else {
            openBrowserUrl(context, url)
        }
    }

    private fun openBrowserUrl(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    }
}