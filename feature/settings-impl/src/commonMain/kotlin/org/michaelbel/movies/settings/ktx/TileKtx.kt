package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable

@Composable
expect fun requestTileService(onSuccess: (String) -> Unit): () -> Unit