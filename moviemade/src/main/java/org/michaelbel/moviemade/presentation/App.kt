package org.michaelbel.moviemade.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.app.crashlytics.CrashlyticsTree
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreate() {
        super.onCreate()
        initAppTheme()
        initFirebaseCrashlytics()
        initTimber()
        initFirebaseRemoteConfig()
    }

    private fun initAppTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun initFirebaseCrashlytics() {
        FirebaseApp.initializeApp(applicationContext)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }

    private fun initTimber() {
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree())
    }

    private fun initFirebaseRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.fetchAndActivate().addOnFailureListener(Timber::e)
    }
}