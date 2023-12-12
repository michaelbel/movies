package org.michaelbel.movies.platform.gms.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import org.michaelbel.movies.platform.main.crashlytics.CrashlyticsService

internal class CrashlyticsServiceImpl @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics
): CrashlyticsService {

    override fun recordException(priority: Int, tag: String, message: String, exception: Throwable) {
        firebaseCrashlytics.run {
            setCustomKey(CRASHLYTICS_KEY_PRIORITY, priority)
            setCustomKey(CRASHLYTICS_KEY_TAG, tag)
            setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)
            recordException(exception)
        }
    }

    private companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"
    }
}