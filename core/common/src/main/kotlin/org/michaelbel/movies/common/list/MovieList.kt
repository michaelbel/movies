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

        fun transform(className: String): MovieList {
            return when (className) {
                NowPlaying.toString() -> NowPlaying
                Popular.toString() -> Popular
                TopRated.toString() -> TopRated
                Upcoming.toString() -> Upcoming
                else -> throw InvalidMovieListException
            }
        }

        fun transformNullable(name: String?): MovieList? {
            return when (name) {
                null -> null
                else -> transformName(name)
            }
        }

        private fun transformName(name: String): MovieList {
            return when (name) {
                NowPlaying.name -> NowPlaying
                Popular.name -> Popular
                TopRated.name -> TopRated
                Upcoming.name -> Upcoming
                else -> throw InvalidMovieListException
            }
        }
    }
}