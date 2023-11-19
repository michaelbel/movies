package org.michaelbel.movies.analytics.impl

import android.os.Bundle
import androidx.core.os.bundleOf
import javax.inject.Inject
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent
import org.michaelbel.movies.platform.main.analytics.AnalyticsService

internal class MoviesAnalyticsImpl @Inject constructor(
    private val analyticsService: AnalyticsService
): MoviesAnalytics {

    override fun trackDestination(route: String?, arguments: Bundle?) {
        val bundle = bundleOf(
            analyticsService.screenName to route,
            MoviesParams.PARAM_ARGUMENTS to arguments
        )
        analyticsService.logEvent(analyticsService.screenView, bundle)
    }

    override fun logEvent(event: BaseEvent) {
        analyticsService.logEvent(event.name, event.params)
    }
}