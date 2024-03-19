@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.details

import org.michaelbel.movies.navigation.MoviesNavigationDestination

actual object DetailsDestination: MoviesNavigationDestination {

    override val route: String = "movie?movieList={movieList}&movieId={movieId}"

    override val destination: String = "movie"
}