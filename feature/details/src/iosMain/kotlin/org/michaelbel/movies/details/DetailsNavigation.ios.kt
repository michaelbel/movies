package org.michaelbel.movies.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.details.ui.DetailsRoute
import org.michaelbel.movies.persistence.database.typealiases.MovieId

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