package org.michaelbel.movies.hms.crashlytics

import javax.inject.Inject
import org.michaelbel.movies.platform.main.crashlytics.CrashlyticsService

internal class CrashlyticsServiceImpl @Inject constructor(): CrashlyticsService {

    override fun recordException(priority: Int, tag: String, message: String, exception: Throwable) {}
}