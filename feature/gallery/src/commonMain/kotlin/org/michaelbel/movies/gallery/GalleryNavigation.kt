package org.michaelbel.movies.gallery

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import org.michaelbel.movies.ui.navigation.GalleryDestination
import org.michaelbel.movies.gallery.ui.GalleryScreen

fun EntryProviderScope<NavKey>.galleryGraph() {
    entry<GalleryDestination> { key ->
        GalleryScreen(destination = key)
    }
}