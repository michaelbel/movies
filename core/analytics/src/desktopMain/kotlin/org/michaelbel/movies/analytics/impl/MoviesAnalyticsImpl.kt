package org.michaelbel.movies.analytics.impl

import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.model.BaseEvent

internal class MoviesAnalyticsImpl: MoviesAnalytics {

    override fun trackDestination(route: String?, arguments: HashMap<String, String>) {}

    override fun logEvent(event: BaseEvent) {}
}