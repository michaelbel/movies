package org.michaelbel.moviemade.presentation

import android.app.Application
import android.content.Context
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.facebook.stetho.Stetho
import com.singhajit.sherlock.core.Sherlock
import com.tspoon.traceur.Traceur
import dagger.hilt.android.HiltAndroidApp
import org.michaelbel.core.BuildConfig
import org.michaelbel.core.CrashlyticsTree
import org.michaelbel.moviemade.BuildConfig.DEBUG
import timber.log.Timber

@HiltAndroidApp
class App: Application() {

    companion object {
        operator fun get(context: Context): App {
            return context as App
        }

        lateinit var appHandler: Handler
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        appHandler = Handler(applicationContext.mainLooper)
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        //Analytics.initialize(this)
        Stetho.initializeWithDefaults(this)

        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree())

        if (DEBUG) {
            Sherlock.init(this)
            Traceur.enableLogging()
            //LeakCanary.install(this)
        }
    }
}