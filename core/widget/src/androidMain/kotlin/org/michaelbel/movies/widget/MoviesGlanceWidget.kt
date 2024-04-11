package org.michaelbel.movies.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import org.michaelbel.movies.widget.theme.MoviesGlanceTheme
import org.michaelbel.movies.widget.work.MoviesGlanceStateDefinition
import org.michaelbel.movies.widget.work.MoviesWidgetState

internal class MoviesGlanceWidget: GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val glanceWidgetState = currentState<MoviesWidgetState>()
            MoviesGlanceTheme {
                MoviesGlanceWidgetContent(
                    glanceWidgetState = glanceWidgetState
                )
            }
        }
    }

    override val stateDefinition = MoviesGlanceStateDefinition
}