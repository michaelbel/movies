package org.michaelbel.movies.main.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

expect val StartDestination: NavKey

expect fun EntryProviderScope<NavKey>.mainNavGraph()