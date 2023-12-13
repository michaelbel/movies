package org.michaelbel.movies.repository.impl

import android.content.Context
import android.os.Build
import androidx.compose.ui.unit.LayoutDirection
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.BuildConfig
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.repository.SettingsRepository
import org.michaelbel.movies.repository.ktx.code
import org.michaelbel.movies.repository.ktx.packageInfo

@Singleton
internal class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: MoviesPreferences,
    appService: AppService
): SettingsRepository {

    override val currentTheme: Flow<AppTheme> = preferences.themeFlow.map { name ->
        AppTheme.transform(name ?: AppTheme.FollowSystem.toString())
    }

    override val currentFeedView: Flow<FeedView> = preferences.feedViewFlow.map { name ->
        FeedView.transform(name ?: FeedView.FeedList.toString())
    }

    override val currentMovieList: Flow<MovieList> = preferences.movieListFlow.map { name ->
        MovieList.transform(name ?: MovieList.NowPlaying.toString())
    }

    override val dynamicColors: Flow<Boolean> = preferences.isDynamicColorsFlow.map { isDynamicColors ->
        isDynamicColors ?: (Build.VERSION.SDK_INT >= 31)
    }

    override val layoutDirection: Flow<LayoutDirection> = preferences.isRtlEnabledFlow.map { isRtlEnabled ->
        if (isRtlEnabled == true) LayoutDirection.Rtl else LayoutDirection.Ltr
    }

    override val appVersionData: Flow<AppVersionData> = flowOf(
        AppVersionData(
            version = context.packageInfo.versionName,
            code = context.packageInfo.code,
            flavor = appService.flavor.name,
            isDebug = BuildConfig.DEBUG
        )
    )

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

    override suspend fun setRtlEnabled(value: Boolean) {
        preferences.setRtlEnabled(value)
    }
}