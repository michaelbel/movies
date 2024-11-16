package org.michaelbel.movies.details

import kotlinx.serialization.Serializable
import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object DetailsDestination: MoviesNavigationDestination {

    override val route: String = "movie?movieList={movieList}&movieId={movieId}"

    override val destination: String = "movie"
}

@Serializable
class DetailsRoute(
    val movieList: String,
    val movieId: Int
)