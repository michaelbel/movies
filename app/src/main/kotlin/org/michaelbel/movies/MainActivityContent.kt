package org.michaelbel.movies

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.michaelbel.movies.details.ui.DetailsScreenContent
import org.michaelbel.movies.feed.ui.FeedScreenContent
import org.michaelbel.movies.navigation.NavGraph
import org.michaelbel.movies.settings.ui.SettingsScreenContent
import org.michaelbel.movies.ui.MoviesTheme

@Composable
fun MainActivityContent() {
    val navController: NavHostController = rememberNavController()

    Scaffold { paddingValues: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavGraph.Home.route,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(
                route = NavGraph.Home.route
            ) {
                FeedScreenContent(navController)
            }
            composable(
                route = NavGraph.Movie.routeWithArgs,
                arguments = listOf(navArgument(NavGraph.Movie.argMovieId) {
                    type = NavType.LongType
                })
            ) {
                DetailsScreenContent(navController)
            }
            composable(
                route = NavGraph.Settings.route
            ) {
                SettingsScreenContent(navController)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainActivityContentPreview() {
    MoviesTheme {
        MainActivityContent()
    }
}