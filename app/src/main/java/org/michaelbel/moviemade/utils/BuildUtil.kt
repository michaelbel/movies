package org.michaelbel.moviemade.utils

import org.michaelbel.moviemade.BuildConfig

object BuildUtil {

    /**
     * If **TMDB_API_KEY** is empty, the application will display a message:
     * [R.string#error_empty_api_key] on [EmptyView].
     * Implemented шn the following fragments: [NowPlayingFragment], [TopRatedFragment],
     * [UpcomingFragment], [SearchMoviesFragment], [FavoriteFragment], [WatchlistFragment]
     */
    fun isEmptyApiKey(): Boolean {
        return BuildConfig.TMDB_API_KEY == "null"
    }
}