@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.analytics.impl

import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.model.BaseEvent

internal actual class MoviesAnalyticsImpl: MoviesAnalytics {

    actual override fun trackDestination(route: String?, arguments: HashMap<String, String>) {}

    actual override fun logEvent(event: BaseEvent) {}
}