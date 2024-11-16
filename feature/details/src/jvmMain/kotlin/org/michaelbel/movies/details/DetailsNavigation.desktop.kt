package org.michaelbel.movies.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.details.ui.DetailsRoute
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

fun NavController.navigateToDetails(pagingKey: PagingKey, movieId: MovieId) {
    navigate(DetailsDestination(pagingKey, movieId))
}

fun NavGraphBuilder.detailsGraph(
    navigateBack: () -> Unit,
    navigateToGallery: (MovieId) -> Unit
) {
    composable<DetailsDestination> {
        DetailsRoute(
            onBackClick = navigateBack,
            onNavigateToGallery = navigateToGallery
        )
    }
}