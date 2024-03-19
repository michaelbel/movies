@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.search.ui.SearchRoute
import org.michaelbel.movies.ui.shortcuts.INTENT_ACTION_SEARCH

expect fun NavController.navigateToSearch()

expect fun NavGraphBuilder.searchGraph(
    navigateBack: () -> Unit,
    navigateToDetails: (String, Int) -> Unit,
)