package org.michaelbel.movies.details

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        route = DetailsDestination.route
    ) {
        Text(
            text = "Details",
            modifier = Modifier.clickable { navigateBack() }
        )
    }
}