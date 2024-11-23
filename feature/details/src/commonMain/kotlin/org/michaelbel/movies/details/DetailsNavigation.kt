package org.michaelbel.movies.details

import androidx.navigation.NavController
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

fun NavController.navigateToDetails(pagingKey: PagingKey, movieId: MovieId) {
    navigate(DetailsDestination(pagingKey, movieId))
}