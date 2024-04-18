package org.michaelbel.movies.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.entity.AppLanguage

class SettingsViewModel(
    private val interactor: Interactor
): BaseViewModel() {

    val themeData: StateFlow<ThemeData> = interactor.themeData
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = ThemeData.Default
        )

    val currentFeedView: StateFlow<FeedView> = interactor.currentFeedView
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = FeedView.FeedList
        )

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = MovieList.NowPlaying()
        )

    val isBiometricEnabled: StateFlow<Boolean> = interactor.isBiometricEnabled
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val isScreenshotBlockEnabled: StateFlow<Boolean> = interactor.isScreenshotBlockEnabled
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    var isUpdateAvailable by mutableStateOf(false)

    fun selectLanguage(language: AppLanguage) = launch {
        interactor.selectLanguage(language)
    }

    fun selectTheme(theme: AppTheme) = launch {
        interactor.selectTheme(theme)
    }

    fun selectFeedView(feedView: FeedView) = launch {
        interactor.selectFeedView(feedView)
    }

    fun selectMovieList(movieList: MovieList) = launch {
        interactor.selectMovieList(movieList)
    }

    fun setDynamicColors(value: Boolean) = launch {
        interactor.setDynamicColors(value)
    }

    fun setPaletteKey(paletteKey: Int) = launch {
        interactor.setPaletteKey(paletteKey)
    }

    fun setSeedColor(seedColor: Int) = launch {
        interactor.setSeedColor(seedColor)
    }

    fun setBiometricEnabled(enabled: Boolean) = launch {
        interactor.setBiometricEnabled(enabled)
    }

    fun setScreenshotBlockEnabled(enabled: Boolean) = launch {
        interactor.setScreenshotBlockEnabled(enabled)
    }
}