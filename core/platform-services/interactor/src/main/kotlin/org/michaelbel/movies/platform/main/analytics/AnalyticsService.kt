package org.michaelbel.movies.platform.main.analytics

import android.os.Bundle

interface AnalyticsService {

    val screenView: String

    val screenName: String

    fun logEvent(name: String, params: Bundle)
}