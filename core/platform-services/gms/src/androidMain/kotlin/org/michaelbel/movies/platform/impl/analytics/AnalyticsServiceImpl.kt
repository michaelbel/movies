package org.michaelbel.movies.platform.impl.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import org.michaelbel.movies.platform.analytics.AnalyticsService

class AnalyticsServiceImpl(
    private val firebaseAnalytics: FirebaseAnalytics
): AnalyticsService {

    override val screenView: String = FirebaseAnalytics.Event.SCREEN_VIEW

    override val screenName: String = FirebaseAnalytics.Param.SCREEN_NAME

    override fun logEvent(name: String, params: Bundle) {
        firebaseAnalytics.logEvent(name, params)
    }
}