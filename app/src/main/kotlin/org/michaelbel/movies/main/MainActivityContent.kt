package org.michaelbel.movies.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.michaelbel.movies.details.ui.DetailsContent
import org.michaelbel.movies.feed.FeedContent
import org.michaelbel.movies.navigation.NavGraph
import org.michaelbel.movies.settings.ui.SettingsContent

@Composable
fun MainActivityContent(
    onAppUpdateClicked: () -> Unit
) {
    val navController: NavHostController = rememberNavController()

    Scaffold {
        NavHost(
            navController = navController,
            startDestination = NavGraph.Home.route
        ) {
            composable(route = NavGraph.Home.route) {
                FeedContent(navController, onAppUpdateClicked)
            }
            composable(
                route = NavGraph.Movie.routeWithArgs,
                arguments = listOf(navArgument(NavGraph.Movie.argMovieId) {
                    type = NavType.LongType
                })
            ) { backStackEntry ->
                val movieId: Long? = backStackEntry.arguments?.getLong(NavGraph.Movie.argMovieId)
                if (movieId != null) {
                    DetailsContent(
                        navController = navController,
                        movieId = movieId
                    )
                }
            }
            composable(route = NavGraph.Settings.route) {
                SettingsContent(navController)
            }
        }
    }
}