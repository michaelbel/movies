@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.analytics.impl

import android.os.Bundle
import androidx.core.os.bundleOf
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent
import org.michaelbel.movies.platform.analytics.AnalyticsService

internal actual class MoviesAnalyticsImpl(
    private val analyticsService: AnalyticsService
): MoviesAnalytics {

    actual override fun trackDestination(route: String?, arguments: HashMap<String, String>) {
        val args = Bundle()
        arguments.forEach { (key, value) ->
            args.putString(key, value)
        }
        val bundle = bundleOf(
            analyticsService.screenName to route,
            MoviesParams.PARAM_ARGUMENTS to args
        )
        analyticsService.logEvent(analyticsService.screenView, bundle)
    }

    actual override fun logEvent(event: BaseEvent) {
        val bundle = bundleOf()
        event.params.forEach { (key, value) ->
            bundle.putString(key, value)
        }
        analyticsService.logEvent(event.name, bundle)
    }
}