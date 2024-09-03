@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.platform.impl.analytics

import org.michaelbel.movies.platform.analytics.AnalyticsService

actual class AnalyticsServiceImpl: AnalyticsService {

    override val screenView: String = ""

    override val screenName: String = ""

    override fun logEvent(name: String, params: HashMap<String, String>) {}
}