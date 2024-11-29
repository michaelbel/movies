package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable

@Composable
actual fun requestTileService(onSuccess: (String) -> Unit): () -> Unit {
    return {}
}