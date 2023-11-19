package org.michaelbel.movies.platform.gms.config

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteConfigModule {

    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
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
        return firebaseRemoteConfig
    }

    private const val FETCH_INTERVAL_IN_SECONDS = 5L
}