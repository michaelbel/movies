package org.michaelbel.movies.common.crashlytics

import org.michaelbel.movies.platform.crashlytics.CrashlyticsService
import timber.log.Timber

class CrashlyticsTree(
    private val crashlyticsService: CrashlyticsService
): Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val exception: Throwable = t ?: Exception(message)
        crashlyticsService.recordException(priority, tag.orEmpty(), message, exception)
    }
}