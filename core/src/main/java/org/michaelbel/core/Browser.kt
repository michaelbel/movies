package org.michaelbel.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.graphics.BitmapFactory
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.preference.PreferenceManager

object Browser {

    private const val REQUEST_CODE = 100

    fun openUrl(context: Context, url: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val inApp = preferences.getBoolean("browser", true)

        if (inApp) {
            openInAppUrl(context, url)
        } else {
            openBrowserUrl(context, url)
        }
    }

    private fun openInAppUrl(context: Context, url: String) {
        val shareIntent = Intent(ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }

        val pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, shareIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val shareIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_share)

        val builder = CustomTabsIntent.Builder().apply {
            addDefaultShareMenuItem()
            setShowTitle(true)
            setToolbarColor(ContextCompat.getColor(context, R.color.primary))
            setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.primary))
            setActionButton(shareIcon, context.getString(R.string.share_link), pendingIntent, true)
        }

        builder.build().apply {
            launchUrl(context, url.toUri())
        }
    }

    private fun openBrowserUrl(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    }
}