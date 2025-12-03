package org.michaelbel.movies.auth

import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.scene.DialogSceneStrategy
import org.michaelbel.movies.ui.navigation.AuthDestination
import org.michaelbel.movies.ui.ktx.USE_PLATFORM_DEFAULT_WIDTH

fun EntryProviderScope<NavKey>.authGraph() {
    entry<AuthDestination>(
        metadata = DialogSceneStrategy.dialog(
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = USE_PLATFORM_DEFAULT_WIDTH
            )
        )
    ) {
        AuthScreen()
    }
}