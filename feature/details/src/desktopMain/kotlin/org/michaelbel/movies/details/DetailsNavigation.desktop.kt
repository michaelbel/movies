package org.michaelbel.movies.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.michaelbel.movies.details.ui.DetailsRoute
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

fun NavController.navigateToDetails(pagingKey: PagingKey, movieId: MovieId) {
    navigate("movie?movieList=$pagingKey&movieId=$movieId")
}

fun NavGraphBuilder.detailsGraph(
    navigateBack: () -> Unit,
    navigateToGallery: (MovieId) -> Unit
) {
    composable(
        route = DetailsDestination.route,
        arguments = listOf(
            navArgument("movieList") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("movieId") { type = NavType.LongType }
        ),
    ) {
        DetailsRoute(
            onBackClick = navigateBack,
            onNavigateToGallery = navigateToGallery
        )
    }
}