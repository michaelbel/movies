package org.michaelbel.movies.details

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import org.michaelbel.movies.ui.navigation.DetailsDestination

fun EntryProviderScope<NavKey>.detailsGraph() {
    entry<DetailsDestination> { key ->
        DetailsScreen(destination = key)
    }
}