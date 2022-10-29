package org.michaelbel.movies.common.config.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.michaelbel.movies.common.config.RemoteParams

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteConfigModule {

    private const val FETCH_INTERVAL_IN_SECONDS = 5L

    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val configSettings: FirebaseRemoteConfigSettings = remoteConfigSettings {
            fetchTimeoutInSeconds = FETCH_INTERVAL_IN_SECONDS
            minimumFetchIntervalInSeconds = FETCH_INTERVAL_IN_SECONDS
        }
        val defaults: Map<String, Any> = mapOf(
            RemoteParams.PARAM_SETTINGS_ICON_VISIBLE to true
        )
        val firebaseRemoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(defaults)
        }
        return firebaseRemoteConfig
    }
}