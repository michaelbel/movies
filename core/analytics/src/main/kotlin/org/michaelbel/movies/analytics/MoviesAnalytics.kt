package org.michaelbel.movies.analytics

import android.os.Bundle
import org.michaelbel.movies.analytics.model.BaseEvent

interface MoviesAnalytics {

    fun trackDestination(route: String?, arguments: Bundle?)

    fun logEvent(event: BaseEvent)
}