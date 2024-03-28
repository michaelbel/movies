package org.michaelbel.movies.platform.impl.crashlytics

import org.michaelbel.movies.platform.crashlytics.CrashlyticsService

class CrashlyticsServiceImpl: CrashlyticsService {

    override fun recordException(priority: Int, tag: String, message: String, exception: Throwable) {}
}