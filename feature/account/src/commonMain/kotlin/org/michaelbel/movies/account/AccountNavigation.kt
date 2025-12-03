package org.michaelbel.movies.account

import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.scene.DialogSceneStrategy
import org.michaelbel.movies.ui.navigation.AccountDestination
import org.michaelbel.movies.ui.ktx.USE_PLATFORM_DEFAULT_WIDTH

fun EntryProviderScope<NavKey>.accountGraph() {
    entry<AccountDestination>(
        metadata = DialogSceneStrategy.dialog(
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = USE_PLATFORM_DEFAULT_WIDTH
            )
        )
    ) {
        AccountScreen()
    }
}