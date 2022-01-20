package org.michaelbel.moviemade.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import dagger.hilt.android.HiltAndroidApp
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.app.crashlytics.CrashlyticsTree
import timber.log.Timber

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        applyTheme()
        initCrashlytics()
    }

    private fun applyTheme() {
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun initCrashlytics() {
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree())
    }
}