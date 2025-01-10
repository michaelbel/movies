@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.platform.analytics

actual interface AnalyticsService {

    val screenView: String

    val screenName: String

    fun logEvent(name: String, params: HashMap<String, String>)
}