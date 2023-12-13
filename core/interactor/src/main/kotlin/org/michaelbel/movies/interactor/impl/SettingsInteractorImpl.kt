package org.michaelbel.movies.interactor.impl

import androidx.compose.ui.unit.LayoutDirection
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.event.ChangeDynamicColorsEvent
import org.michaelbel.movies.analytics.event.ChangeRtlEnabledEvent
import org.michaelbel.movies.analytics.event.SelectFeedViewEvent
import org.michaelbel.movies.analytics.event.SelectMovieListEvent
import org.michaelbel.movies.analytics.event.SelectThemeEvent
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.config.RemoteParams
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.interactor.SettingsInteractor
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.platform.config.ConfigService
import org.michaelbel.movies.repository.SettingsRepository

@Singleton
internal class SettingsInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val settingsRepository: SettingsRepository,
    private val configService: ConfigService,
    private val analytics: MoviesAnalytics,
    appService: AppService
): SettingsInteractor {

    override val currentTheme: Flow<AppTheme> = settingsRepository.currentTheme

    override val currentFeedView: Flow<FeedView> = settingsRepository.currentFeedView

    override val currentMovieList: Flow<MovieList> = settingsRepository.currentMovieList

    override val dynamicColors: Flow<Boolean> = settingsRepository.dynamicColors

    override val layoutDirection: Flow<LayoutDirection> = settingsRepository.layoutDirection

    override val isSettingsIconVisible: Flow<Boolean> = configService.getBooleanFlow(RemoteParams.PARAM_SETTINGS_ICON_VISIBLE)

    override val isPlayServicesAvailable: Flow<Boolean> = flowOf(appService.isPlayServicesAvailable)

    override val appVersionData: Flow<AppVersionData> = settingsRepository.appVersionData

    override suspend fun selectTheme(appTheme: AppTheme) = withContext(dispatchers.main) {
        settingsRepository.selectTheme(appTheme)
        analytics.logEvent(SelectThemeEvent(appTheme.toString()))
    }

    override suspend fun selectFeedView(feedView: FeedView) = withContext(dispatchers.main) {
        settingsRepository.selectFeedView(feedView)
        analytics.logEvent(SelectFeedViewEvent(feedView.toString()))
    }

    override suspend fun selectMovieList(movieList: MovieList) = withContext(dispatchers.main) {
        settingsRepository.selectMovieList(movieList)
        analytics.logEvent(SelectMovieListEvent(movieList.toString()))
    }

    override suspend fun setDynamicColors(value: Boolean) = withContext(dispatchers.main) {
        settingsRepository.setDynamicColors(value)
        analytics.logEvent(ChangeDynamicColorsEvent(value))
    }

    override suspend fun setRtlEnabled(value: Boolean) = withContext(dispatchers.main) {
        settingsRepository.setRtlEnabled(value)
        analytics.logEvent(ChangeRtlEnabledEvent(value))
    }

    override suspend fun fetchRemoteConfig() {
        withContext(dispatchers.main) {
            configService.fetchAndActivate()
        }
    }
}