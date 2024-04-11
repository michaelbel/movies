package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider

class TitlePreviewParameterProvider: CollectionPreviewParameterProvider<String>(
    listOf(
        "How to Train Your Dragon",
        "Three Billboards Outside Ebbing, Missouri"
    )
)