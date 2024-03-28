package org.michaelbel.movies.details

import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object DetailsDestination: MoviesNavigationDestination {

    override val route: String = "movie?movieList={movieList}&movieId={movieId}"

    override val destination: String = "movie"
}