package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable

@Composable
expect fun requestTileService(onSuccess: (String) -> Unit): () -> Unit