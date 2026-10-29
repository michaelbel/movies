package org.michaelbel.movies.settingsweb.intent

import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.mvi.Intent
import org.michaelbel.movies.common.theme.AppTheme

sealed interface SettingsIntent: Intent {
    data object SelectTheme: SettingsIntent
    data object SelectFeedView: SettingsIntent
    data object SelectMovieList: SettingsIntent
    data object GithubClick: SettingsIntent
    data object TelegramClick: SettingsIntent
    data class SetTheme(val theme: AppTheme): SettingsIntent
    data class SetFeedView(val feedView: FeedView): SettingsIntent
    data class SetMovieList(val movieList: MovieList): SettingsIntent
}
