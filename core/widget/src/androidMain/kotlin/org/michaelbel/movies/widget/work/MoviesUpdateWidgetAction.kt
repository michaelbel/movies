package org.michaelbel.movies.widget.work

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback

internal class MoviesUpdateWidgetAction: ActionCallback {

    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        MoviesGlanceWidgetWorker.enqueue(context = context, force = true)
    }
}