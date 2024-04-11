package org.michaelbel.movies.analytics

import org.michaelbel.movies.analytics.model.BaseEvent

interface MoviesAnalytics {

    fun trackDestination(
        route: String?,
        arguments: HashMap<String, String>
    )

    fun logEvent(
        event: BaseEvent
    )
}