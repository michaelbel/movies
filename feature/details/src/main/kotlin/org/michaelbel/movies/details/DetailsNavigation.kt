package org.michaelbel.movies.details

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import org.michaelbel.movies.details.ui.DetailsRoute

private val DETAILS_MOVIE_NAV_ARGUMENT: NamedNavArgument = navArgument(
    name = DetailsDestination.movieIdArg,
    builder = {
        type = NavType.LongType
    }
)

private val DETAILS_MOVIE_WEB_DEEP_LINK: NavDeepLink = navDeepLink {
    uriPattern = "https://www.themoviedb.org/movie/{movieId}"
}

private val DETAILS_MOVIE_PUSH_DEEP_LINK: NavDeepLink = navDeepLink {
    uriPattern = "movies://details/{movieId}"
}

fun NavController.navigateToDetails(movieId: Int) {
    navigate(DetailsDestination.createNavigationRoute(movieId))
}

fun NavGraphBuilder.detailsGraph(
    navigateBack: () -> Unit
) {
    composable(
        route = DetailsDestination.route,
        arguments = listOf(DETAILS_MOVIE_NAV_ARGUMENT),
        deepLinks = listOf(DETAILS_MOVIE_WEB_DEEP_LINK, DETAILS_MOVIE_PUSH_DEEP_LINK)
    ) {
        DetailsRoute(
            onBackClick = navigateBack
        )
    }
}