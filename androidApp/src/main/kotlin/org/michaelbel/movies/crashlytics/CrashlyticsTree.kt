package org.michaelbel.movies.crashlytics

import org.michaelbel.movies.platform.crashlytics.CrashlyticsService
import timber.log.Timber

internal class CrashlyticsTree(
    private val crashlyticsService: CrashlyticsService
): Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val exception = t ?: Exception(message)
        crashlyticsService.recordException(priority, tag.orEmpty(), message, exception)
    }
}