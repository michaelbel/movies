package org.michaelbel.movies.platform.crashlytics

interface CrashlyticsService {

    fun recordException(priority: Int, tag: String, message: String, exception: Throwable)
}