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

sealed class NavGraph(val route: String) {
    object Home: NavGraph("home")
    object Settings: NavGraph("settings")
    object About: NavGraph("about")
    object Movie: NavGraph("movie") {
        const val routeWithArgs: String = "movie/{movieId}"
        const val argMovieId: String = "movieId"
    }
}

@Composable
fun NavigationContent(
    navController: NavHostController,
    onAppUpdateClicked: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavGraph.Home.route
    ) {
        composable(route = NavGraph.Home.route) { HomeScreen(navController, onAppUpdateClicked) }
        composable(route = NavGraph.Settings.route) { SettingsScreen(navController) }
        composable(route = NavGraph.About.route) { AboutScreen(navController) }
        composable(
            route = NavGraph.Movie.routeWithArgs,
            arguments = listOf(navArgument(NavGraph.Movie.argMovieId) { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId: Long = backStackEntry.arguments?.getLong(NavGraph.Movie.argMovieId) ?: return@composable
            DetailsScreen(
                navController = navController,
                movieId = movieId
            )
        }
    }
}