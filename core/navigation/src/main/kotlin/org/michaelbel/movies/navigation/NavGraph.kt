package org.michaelbel.movies.navigation

private const val ROUTE_HOME = "home"
private const val ROUTE_DETAILS = "movie"
private const val ROUTE_SETTINGS = "settings"

private const val DETAILS_ARG_MOVIE_ID = "movieId"
private const val ROUTE_DETAILS_WITH_ARGS = "$ROUTE_DETAILS/{$DETAILS_ARG_MOVIE_ID}"

sealed class NavGraph(
    val route: String
) {
    object Home: NavGraph(ROUTE_HOME)
    object Movie: NavGraph(ROUTE_DETAILS) {
        const val routeWithArgs: String = ROUTE_DETAILS_WITH_ARGS
        const val argMovieId: String = DETAILS_ARG_MOVIE_ID
    }
    object Settings: NavGraph(ROUTE_SETTINGS)
}