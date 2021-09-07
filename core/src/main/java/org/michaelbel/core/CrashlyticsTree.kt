package org.michaelbel.core

import android.util.Log
import timber.log.Timber

class CrashlyticsTree: Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        val exception: Throwable = throwable ?: Exception(message)

        //Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
        //Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
        //Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)
        //Crashlytics.logException(t)
    }

    private companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"
    }
}