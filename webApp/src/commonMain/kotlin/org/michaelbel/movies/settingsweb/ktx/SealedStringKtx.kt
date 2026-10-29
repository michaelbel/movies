package org.michaelbel.movies.settingsweb.ktx

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme

val SealedString.stringText: String
    get() = when (this) {
        is AppTheme.NightNo -> "Light"
        is AppTheme.NightYes -> "Dark"
        is AppTheme.FollowSystem -> "Follow System"
        is FeedView.FeedList -> "List"
        is FeedView.FeedGrid -> "Grid"
        is MovieList.NowPlaying -> "Now Playing"
        is MovieList.Popular -> "Popular"
        is MovieList.TopRated -> "Top Rated"
        is MovieList.Upcoming -> "Upcoming"
        else -> ""
    }
