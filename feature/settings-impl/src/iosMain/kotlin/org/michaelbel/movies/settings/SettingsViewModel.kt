package org.michaelbel.movies.settings

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.MOVIES_GITHUB_URL
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.browser.openUrl
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
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ThemeData.Default
        )

    val currentFeedView: StateFlow<FeedView> = interactor.currentFeedView
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FeedView.FeedList
        )

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = MovieList.NowPlaying()
        )

    fun selectLanguage(language: AppLanguage) = viewModelScope.launch {
        interactor.selectLanguage(language)
    }

    fun selectTheme(theme: AppTheme) = viewModelScope.launch {
        interactor.selectTheme(theme)
    }

    fun selectFeedView(feedView: FeedView) = viewModelScope.launch {
        interactor.selectFeedView(feedView)
    }

    fun selectMovieList(movieList: MovieList) = viewModelScope.launch {
        interactor.selectMovieList(movieList)
    }

    fun setPaletteKey(paletteKey: Int) = viewModelScope.launch {
        interactor.setPaletteKey(paletteKey)
    }

    fun setSeedColor(seedColor: Int) = viewModelScope.launch {
        interactor.setSeedColor(seedColor)
    }

    fun navigateToGithubUrl() {
        openUrl(MOVIES_GITHUB_URL)
    }
}