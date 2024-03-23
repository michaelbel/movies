package org.michaelbel.movies.repository.impl

import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.common_kmp.BuildConfig
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.platform.Flavor
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.repository.SettingsRepository
import org.michaelbel.movies.repository.ktx.code
import org.michaelbel.movies.repository.ktx.packageInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: MoviesPreferences,
    private val appService: AppService
): SettingsRepository {

    override val isReviewFeatureEnabled: Boolean
        get() = appService.flavor == Flavor.Gms

    override val isUpdateFeatureEnabled: Boolean
        get() = appService.flavor == Flavor.Gms

    override val currentTheme: Flow<AppTheme> = preferences.themeFlow.map { name ->
        AppTheme.transform(name ?: AppTheme.FollowSystem.toString())
    }

    override val currentFeedView: Flow<FeedView> = preferences.feedViewFlow.map { name ->
        FeedView.transform(name ?: FeedView.FeedList.toString())
    }

    override val currentMovieList: Flow<MovieList> = preferences.movieListFlow.map { className ->
        MovieList.transform(className ?: MovieList.NowPlaying.toString())
    }

    override val dynamicColors: Flow<Boolean> = preferences.isDynamicColorsFlow.map { isDynamicColors ->
        isDynamicColors ?: (Build.VERSION.SDK_INT >= 31)
    }

    override val isBiometricEnabled: Flow<Boolean> = preferences.isBiometricEnabled.map { enabled ->
        enabled ?: false
    }

    override val appVersionData: Flow<AppVersionData> = flowOf(
        AppVersionData(
            version = context.packageInfo.versionName,
            code = context.packageInfo.code,
            flavor = appService.flavor.name,
            isDebug = BuildConfig.DEBUG
        )
    )

    override suspend fun isBiometricEnabledAsync(): Boolean {
        return preferences.isBiometricEnabledAsync()
    }

    override suspend fun selectTheme(appTheme: AppTheme) {
        preferences.setTheme(appTheme.toString())
    }

    override suspend fun selectFeedView(feedView: FeedView) {
        preferences.setFeedView(feedView.toString())
    }

    override suspend fun selectMovieList(movieList: MovieList) {
        preferences.setMovieList(movieList.toString())
    }

    override suspend fun setDynamicColors(value: Boolean) {
        preferences.setDynamicColors(value)
    }

    override suspend fun setBiometricEnabled(enabled: Boolean) {
        preferences.setBiometricEnabled(enabled)
    }
}