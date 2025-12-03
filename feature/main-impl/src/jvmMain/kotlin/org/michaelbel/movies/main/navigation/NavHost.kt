package org.michaelbel.movies.main.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import org.michaelbel.movies.main.mainnav.mainGraph

actual val StartDestination: NavKey = MainDestination()

actual fun EntryProviderScope<NavKey>.mainNavGraph() {
    mainGraph()
}