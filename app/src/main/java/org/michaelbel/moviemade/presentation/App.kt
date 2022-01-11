package org.michaelbel.moviemade.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.facebook.stetho.Stetho
import com.singhajit.sherlock.core.Sherlock
import com.tspoon.traceur.Traceur
import dagger.hilt.android.HiltAndroidApp
import org.michaelbel.core.BuildConfig
import org.michaelbel.core.CrashlyticsTree
import timber.log.Timber

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        applyTheme()
        initStetho()
        initCrashlytics()
        initSherlock()
        initTraceur()
    }

    private fun applyTheme() {
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initCrashlytics() {
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree())
    }

    private fun initSherlock() {
        if (BuildConfig.DEBUG) {
            Sherlock.init(this)
        }
    }

    private fun initTraceur() {
        if (BuildConfig.DEBUG) {
            Traceur.enableLogging()
        }
    }
}