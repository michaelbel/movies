@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.platform.analytics

import android.os.Bundle

actual interface AnalyticsService {

    val screenView: String

    val screenName: String

    fun logEvent(name: String, params: Bundle)
}