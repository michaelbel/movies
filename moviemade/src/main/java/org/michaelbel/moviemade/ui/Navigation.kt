package org.michaelbel.moviemade.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.michaelbel.moviemade.ui.about.AboutScreen
import org.michaelbel.moviemade.ui.details.DetailsScreen
import org.michaelbel.moviemade.ui.home.HomeScreen
import org.michaelbel.moviemade.ui.settings.SettingsScreen

const val ARG_MOVIE_TITLE = "movieTitle"

const val ROUTE_HOME = "route_home"
const val ROUTE_MOVIE = "movie?$ARG_MOVIE_TITLE={$ARG_MOVIE_TITLE}"
const val ROUTE_SETTINGS = "route_settings"
const val ROUTE_ABOUT = "route_about"

@Composable
fun NavigationContent(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_HOME
    ) {
        composable(route = ROUTE_HOME) { HomeScreen(navController) }
        composable(route = ROUTE_SETTINGS) { SettingsScreen(navController) }
        composable(route = ROUTE_ABOUT) { AboutScreen(navController) }
        composable(
            route = ROUTE_MOVIE,
            arguments = listOf(navArgument(ARG_MOVIE_TITLE) { type = NavType.StringType })
        ) { backStackEntry ->
            val movieTitle: String = backStackEntry.arguments?.getString(ARG_MOVIE_TITLE).orEmpty()

            DetailsScreen(
                navController = navController,
                movieTitle = movieTitle
            )
        }
    }
}