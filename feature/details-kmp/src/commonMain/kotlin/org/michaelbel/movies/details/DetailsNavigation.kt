@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import org.michaelbel.movies.details.ui.DetailsRoute

expect fun NavController.navigateToDetails(movieList: String, movieId: Int)

expect fun NavGraphBuilder.detailsGraph(
    navigateBack: () -> Unit,
    navigateToGallery: (Int) -> Unit
)