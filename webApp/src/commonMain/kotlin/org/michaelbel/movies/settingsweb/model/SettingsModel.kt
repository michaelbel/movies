package org.michaelbel.movies.settingsweb.model

import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.mvi.model.Model
import org.michaelbel.movies.common.theme.AppTheme

data class SettingsModel(
    val themeData: AppTheme = AppTheme.FollowSystem,
    val feedView: FeedView = FeedView.FeedList,
    val movieList: MovieList = MovieList.NowPlaying(),
    val versionName: String = "",
    val versionCode: Long = 0L,
    val isThemeFeatureEnabled: Boolean = false,
    val isFeedViewFeatureEnabled: Boolean = false,
    val isMovieListFeatureEnabled: Boolean = false,
    val isGithubFeatureEnabled: Boolean = false,
    val isTelegramFeatureEnabled: Boolean = false,
    val isAboutFeatureEnabled: Boolean = false
): Model
