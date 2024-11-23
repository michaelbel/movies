package org.michaelbel.movies.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.details.ui.DetailsRoute

fun NavGraphBuilder.detailsGraph(
    navigateBack: () -> Unit,
    navigateToGallery: (Int) -> Unit
) {
    composable<DetailsDestination>(
        deepLinks = listOf(
            navDeepLink { uriPattern = "https://www.themoviedb.org/movie/{movieId}" },
            navDeepLink { uriPattern = "movies://details/{movieId}" }
        )
    ) {
        DetailsRoute(
            onBackClick = navigateBack,
            onNavigateToGallery = navigateToGallery
        )
    }
}