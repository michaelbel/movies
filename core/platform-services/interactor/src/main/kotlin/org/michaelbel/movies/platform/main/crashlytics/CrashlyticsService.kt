package org.michaelbel.movies.platform.main.crashlytics

interface CrashlyticsService {

    fun recordException(priority: Int, tag: String, message: String, exception: Throwable)
}