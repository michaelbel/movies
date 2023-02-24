package org.michaelbel.movies.analytics.impl

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent

internal class MoviesAnalyticsImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
): MoviesAnalytics {

    override fun trackDestination(route: String?, arguments: Bundle?) {
        val bundle = bundleOf(
            MoviesParams.PARAM_DESTINATION to route,
            MoviesParams.PARAM_ARGUMENTS to arguments
        )
        firebaseAnalytics.logEvent(MoviesEvents.SCREEN_VIEW, bundle)
    }

    override fun logEvent(event: BaseEvent) {
        firebaseAnalytics.logEvent(event.name, event.params)
    }
}