package org.michaelbel.movies.widget.configure

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import org.michaelbel.movies.widget_kmp.R

class AppWidgetPinnedReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, context.getString(R.string.appwidget_pinned_message), Toast.LENGTH_SHORT).show()
    }
}