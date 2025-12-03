package org.michaelbel.movies.settings

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import org.michaelbel.movies.settings.ui.SettingsScreen
import org.michaelbel.movies.ui.navigation.SettingsDestination

fun EntryProviderScope<NavKey>.settingsGraph() {
    entry<SettingsDestination> {
        SettingsScreen()
    }
}