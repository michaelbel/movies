package org.michaelbel.movies.common.timber

import android.content.Context
import androidx.startup.Initializer
import org.michaelbel.movies.common.BuildConfig
import org.michaelbel.movies.common.crashlytics.CrashlyticsTree
import org.michaelbel.movies.common.crashlytics.FirebaseCrashlyticsInitializer
import timber.log.Timber

@Suppress("unused")
internal class TimberInitializer: Initializer<Unit> {

    override fun create(context: Context) {
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(FirebaseCrashlyticsInitializer::class.java)
    }
}