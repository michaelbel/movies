package org.michaelbel.movies.app.initializer

import android.content.Context
import androidx.startup.Initializer
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.movies.app.crashlytics.CrashlyticsTree
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