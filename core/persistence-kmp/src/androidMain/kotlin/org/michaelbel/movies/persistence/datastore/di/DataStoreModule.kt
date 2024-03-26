package org.michaelbel.movies.persistence.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.michaelbel.movies.persistence.datastore.DATA_STORE_NAME
import org.michaelbel.movies.persistence.datastore.SHARED_PREFERENCES_NAME
import org.michaelbel.movies.persistence.datastore.ktx.dataStorePreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return dataStorePreferences(
            producePath = { context.filesDir.resolve(DATA_STORE_NAME).absolutePath },
            migrations = listOf(SharedPreferencesMigration(context, SHARED_PREFERENCES_NAME))
        )
    }
}