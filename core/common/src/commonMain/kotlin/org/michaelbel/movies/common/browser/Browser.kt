package org.michaelbel.movies.common.browser

import androidx.compose.runtime.Composable

@Composable
expect fun navigateToUrl(url: String): () -> Unit