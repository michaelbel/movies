package org.michaelbel.movies.platform.impl.crashlytics

import javax.inject.Inject
import org.michaelbel.movies.platform.crashlytics.CrashlyticsService

class CrashlyticsServiceImpl @Inject constructor(): CrashlyticsService {

    override fun recordException(priority: Int, tag: String, message: String, exception: Throwable) {}
}