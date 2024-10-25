@file:SuppressLint("NewApi")

package org.michaelbel.movies.widget.ktx

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Context
import android.content.Intent
import org.michaelbel.movies.widget.configure.AppWidgetPinnedReceiver

fun AppWidgetProviderInfo.pin(context: Context) {
    val successCallback = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, AppWidgetPinnedReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    AppWidgetManager.getInstance(context).requestPinAppWidget(provider, null, successCallback)
}