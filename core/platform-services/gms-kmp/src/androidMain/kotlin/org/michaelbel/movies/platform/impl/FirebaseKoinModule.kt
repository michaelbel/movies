package org.michaelbel.movies.platform.impl

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.koin.dsl.module

private const val FETCH_INTERVAL_IN_SECONDS = 5L

val firebaseKoinModule = module {
    single { Firebase.analytics }
    single { FirebaseCrashlytics.getInstance() }
    single { FirebaseMessaging.getInstance() }
    single {
        val configSettings: FirebaseRemoteConfigSettings = remoteConfigSettings {
            fetchTimeoutInSeconds = FETCH_INTERVAL_IN_SECONDS
            minimumFetchIntervalInSeconds = FETCH_INTERVAL_IN_SECONDS
        }
        val defaults: Map<String, Any> = mapOf(
            "param_settings_icon_visible" to true
        )
        val firebaseRemoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(defaults)
        }
        firebaseRemoteConfig
    }
}