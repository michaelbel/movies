package org.michaelbel.movies.ui.preview

import org.michaelbel.movies.ui.preview.base.CollectionPreviewParameterProvider

class TitlePreviewParameterProvider: CollectionPreviewParameterProvider<String>(
    listOf(
        "How to Train Your Dragon",
        "Three Billboards Outside Ebbing, Missouri"
    )
)