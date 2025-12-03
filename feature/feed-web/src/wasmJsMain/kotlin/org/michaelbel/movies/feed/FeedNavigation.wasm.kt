package org.michaelbel.movies.feed

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import org.michaelbel.movies.feed.ui.FeedRoute

fun EntryProviderScope<NavKey>.feedGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDetails: (String, Int) -> Unit
) {
    entry<FeedDestination> {
        FeedRoute(
            onNavigateToSearch = navigateToSearch,
            onNavigateToAccount = navigateToAccount,
            onNavigateToAuth = navigateToAuth,
            onNavigateToSettings = navigateToSettings,
            onNavigateToDetails = navigateToDetails
        )
    }
}