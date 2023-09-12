package org.michaelbel.movies.domain.repository.settings.impl

import android.content.Context
import android.os.Build
import androidx.compose.ui.unit.LayoutDirection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.BuildConfig
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.domain.ktx.code
import org.michaelbel.movies.domain.ktx.packageInfo
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: MoviesPreferences
): SettingsRepository {

    override val currentTheme: Flow<AppTheme> = preferences.theme.map { theme ->
        AppTheme.transform(theme ?: AppTheme.FollowSystem.theme)
    }

    override val dynamicColors: Flow<Boolean> = preferences.isDynamicColors.map { isDynamicColors ->
        isDynamicColors ?: (Build.VERSION.SDK_INT >= 31)
    }

    override val layoutDirection: Flow<LayoutDirection> = preferences.isRtlEnabled.map { isRtlEnabled ->
        if (isRtlEnabled == true) LayoutDirection.Rtl else LayoutDirection.Ltr
    }

    override val appVersionData: Flow<AppVersionData> = flowOf(
        AppVersionData(
            version = context.packageInfo.versionName,
            code = context.packageInfo.code,
            isDebug = BuildConfig.DEBUG
        )
    )

    override suspend fun selectTheme(appTheme: AppTheme) {
        preferences.setTheme(appTheme.theme)
    }

    override suspend fun setDynamicColors(value: Boolean) {
        preferences.setDynamicColors(value)
    }

    override suspend fun setRtlEnabled(value: Boolean) {
        preferences.setRtlEnabled(value)
    }
}