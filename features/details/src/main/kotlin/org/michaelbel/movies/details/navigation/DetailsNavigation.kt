package org.michaelbel.movies.details.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.michaelbel.movies.details.ui.DetailsRoute
import org.michaelbel.movies.details.ui.DetailsScreenContent
import org.michaelbel.movies.navigation.MoviesNavigationDestination

private val DETAILS_MOVIE_NAV_ARGUMENT: NamedNavArgument = navArgument(
    name = DetailsDestination.movieIdArg,
    builder = {
        type = NavType.LongType
    }
)

object DetailsDestination: MoviesNavigationDestination {

    const val movieIdArg = "movieId"

    override val route: String = "movie/{$movieIdArg}"

    override val destination: String = "movie"

    fun createNavigationRoute(movieId: Int): String {
        return "movie/$movieId"
    }
}

fun NavGraphBuilder.detailsGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = DetailsDestination.route,
        arguments = listOf(DETAILS_MOVIE_NAV_ARGUMENT)
    ) {
        DetailsRoute(
            onBackClick = onBackClick
        )
    }
}