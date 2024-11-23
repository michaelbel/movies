package org.michaelbel.movies.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.search.ui.SearchRoute
import org.michaelbel.movies.ui.shortcuts.INTENT_ACTION_SEARCH

fun NavGraphBuilder.searchGraph(
    navigateBack: () -> Unit,
    navigateToDetails: (PagingKey, MovieId) -> Unit,
) {
    composable<SearchDestination>(
        deepLinks = listOf(
            navDeepLink { uriPattern = INTENT_ACTION_SEARCH }
        )
    ) {
        SearchRoute(
            onBackClick = navigateBack,
            onNavigateToDetails = navigateToDetails
        )
    }
}