package org.michaelbel.movies.search

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import org.michaelbel.movies.ui.navigation.SearchDestination
import org.michaelbel.movies.search.ui.SearchScreen

fun EntryProviderScope<NavKey>.searchGraph() {
    entry<SearchDestination> {
        SearchScreen()
    }
}