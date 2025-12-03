package org.michaelbel.movies.main.mainnav

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import org.michaelbel.movies.main.navigation.MainDestination

fun EntryProviderScope<NavKey>.mainGraph() {
    entry<MainDestination> { key ->
        MainNavRoute(
            requestToken = key.requestToken,
            approved = key.approved
        )
    }
}