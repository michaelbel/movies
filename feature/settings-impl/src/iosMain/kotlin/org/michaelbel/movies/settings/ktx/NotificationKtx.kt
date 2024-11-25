package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable

@Composable
actual fun rememberPostNotificationsPermissionHandler(
    areNotificationsEnabled: Boolean,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
): () -> Unit {
    return {}
}