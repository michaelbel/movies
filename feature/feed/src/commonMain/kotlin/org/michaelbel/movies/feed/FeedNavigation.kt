package org.michaelbel.movies.feed

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import org.michaelbel.movies.feed.navigation.FeedDestination
import org.michaelbel.movies.feed.ui.FeedScreen

fun EntryProviderScope<NavKey>.feedGraph() {
    entry<FeedDestination> { FeedScreen() }
}