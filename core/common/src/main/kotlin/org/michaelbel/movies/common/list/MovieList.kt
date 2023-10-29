package org.michaelbel.movies.common.list

import org.michaelbel.movies.common.list.exceptions.InvalidMovieListException

sealed class MovieList(
    val name: String
) {
    data object NowPlaying: MovieList("now_playing")

    data object Popular: MovieList("popular")

    data object TopRated: MovieList("top_rated")

    data object Upcoming: MovieList("upcoming")

    companion object {
        val VALUES: List<MovieList> = listOf(
            NowPlaying,
            Popular,
            TopRated,
            Upcoming
        )

        fun transform(name: String): MovieList {
            return when (name) {
                NowPlaying.toString() -> NowPlaying
                Popular.toString() -> Popular
                TopRated.toString() -> TopRated
                Upcoming.toString() -> Upcoming
                else -> throw InvalidMovieListException
            }
        }
    }
}