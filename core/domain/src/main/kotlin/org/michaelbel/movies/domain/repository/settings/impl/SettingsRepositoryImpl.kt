package org.michaelbel.movies.domain.repository.settings.impl

import android.content.Context
import android.os.Build
import androidx.compose.ui.unit.LayoutDirection
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.domain.ktx.code
import org.michaelbel.movies.domain.ktx.packageInfo
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_DYNAMIC_COLORS_KEY
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_RTL_ENABLED_KEY
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_THEME_KEY
import org.michaelbel.movies.domain.repository.settings.SettingsRepository
import org.michaelbel.movies.ui.theme.model.AppTheme
import org.michaelbel.movies.ui.version.AppVersionData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>
): SettingsRepository {

    override val currentTheme: Flow<AppTheme> = dataStore.data.map { preferences ->
        AppTheme.transform(preferences[PREFERENCE_THEME_KEY] ?: AppTheme.FollowSystem.theme)
    }

    override val dynamicColors: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PREFERENCE_DYNAMIC_COLORS_KEY] ?: (Build.VERSION.SDK_INT >= 31)
    }

    override val layoutDirection: Flow<LayoutDirection> = dataStore.data.map { preferences ->
        val isRtl: Boolean = preferences[PREFERENCE_RTL_ENABLED_KEY] ?: false
        if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    }

    override val appVersionData: Flow<AppVersionData> = flowOf(
        AppVersionData(
            version = context.packageInfo.versionName,
            code = context.packageInfo.code
        )
    )

    override suspend fun selectTheme(theme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_THEME_KEY] = theme.theme
        }
    }

    override suspend fun setDynamicColors(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_DYNAMIC_COLORS_KEY] = value
        }
    }

    override suspend fun setRtlEnabled(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_RTL_ENABLED_KEY] = value
        }
    }
}