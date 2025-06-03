package org.michaelbel.movies.common.list

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.exceptions.InvalidMovieListException

sealed interface MovieList: SealedString {

    data class NowPlaying(
        val name: String = "now_playing"
    ): MovieList

    data class Popular(
        val name: String = "popular"
    ): MovieList

    data class TopRated(
        val name: String = "top_rated"
    ): MovieList

    data class Upcoming(
        val name: String = "upcoming"
    ): MovieList

    companion object {
        val VALUES = listOf(
            NowPlaying(),
            Popular(),
            TopRated(),
            Upcoming()
        )

        fun transform(name: String): MovieList {
            return when (name) {
                NowPlaying().name -> NowPlaying()
                Popular().name -> Popular()
                TopRated().name -> TopRated()
                Upcoming().name -> Upcoming()
                else -> throw InvalidMovieListException
            }
        }

        fun name(movieList: MovieList): String {
            return when (movieList) {
                is NowPlaying -> movieList.name
                is Popular -> movieList.name
                is TopRated -> movieList.name
                is Upcoming -> movieList.name
            }
        }
    }
}