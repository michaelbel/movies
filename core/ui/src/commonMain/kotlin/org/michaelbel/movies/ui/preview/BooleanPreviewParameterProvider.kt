package org.michaelbel.movies.ui.preview

import org.michaelbel.movies.ui.preview.base.CollectionPreviewParameterProvider

class BooleanPreviewParameterProvider: CollectionPreviewParameterProvider<Boolean>(
    listOf(true, false)
)