package org.michaelbel.movies.domain.preferences.ktx

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES_NAME = "user_preferences"

internal val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)