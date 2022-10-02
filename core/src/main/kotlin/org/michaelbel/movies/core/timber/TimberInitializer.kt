package org.michaelbel.movies.core.timber

import android.content.Context
import androidx.startup.Initializer
import org.michaelbel.movies.core.BuildConfig
import org.michaelbel.movies.core.crashlytics.CrashlyticsTree
import org.michaelbel.movies.core.crashlytics.FirebaseCrashlyticsInitializer
import timber.log.Timber

@Suppress("unused")
class TimberInitializer: Initializer<Unit> {

    override fun create(context: Context) {
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(FirebaseCrashlyticsInitializer::class.java)
    }
}