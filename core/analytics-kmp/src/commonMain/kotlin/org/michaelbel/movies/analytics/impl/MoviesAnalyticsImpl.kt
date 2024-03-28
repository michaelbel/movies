@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.analytics.impl

import org.michaelbel.movies.analytics.model.BaseEvent

internal expect class MoviesAnalyticsImpl {

    fun trackDestination(route: String?, arguments: HashMap<String, String>)

    fun logEvent(event: BaseEvent)
}