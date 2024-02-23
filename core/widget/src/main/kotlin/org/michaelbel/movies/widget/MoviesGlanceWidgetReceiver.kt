package org.michaelbel.movies.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import org.michaelbel.movies.widget.work.MoviesGlanceWidgetWorker

internal class MoviesGlanceWidgetReceiver: GlanceAppWidgetReceiver() {

    override val glanceAppWidget = MoviesGlanceWidget()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        if (context != null) {
            MoviesGlanceWidgetWorker.enqueue(context)
        }
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        if (context != null) {
            MoviesGlanceWidgetWorker.cancel(context)
        }
    }
}