package org.michaelbel.movies.details.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import org.michaelbel.movies.details.ui.DetailsRoute
import org.michaelbel.movies.navigation.MoviesNavigationDestination

private val DETAILS_MOVIE_NAV_ARGUMENT: NamedNavArgument = navArgument(
    name = DetailsDestination.movieIdArg,
    builder = {
        type = NavType.LongType
    }
)

private val DETAILS_MOVIE_DEEP_LINK: NavDeepLink = navDeepLink {
    uriPattern = "https://www.themoviedb.org/movie/{movieId}}"
}

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
        arguments = listOf(DETAILS_MOVIE_NAV_ARGUMENT),
        deepLinks = listOf(DETAILS_MOVIE_DEEP_LINK)
    ) {
        DetailsRoute(
            onBackClick = onBackClick
        )
    }
}