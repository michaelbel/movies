package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider

class BooleanPreviewParameterProvider: CollectionPreviewParameterProvider<Boolean>(
    listOf(true, false)
)