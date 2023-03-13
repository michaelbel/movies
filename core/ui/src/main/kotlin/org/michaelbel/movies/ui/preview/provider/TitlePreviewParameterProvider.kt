package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class TitlePreviewParameterProvider: PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf(
        "How to Train Your Dragon",
        "Three Billboards Outside Ebbing, Missouri"
    )
}