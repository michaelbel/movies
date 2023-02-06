package org.michaelbel.movies.details.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class TitlePreviewParameterProvider: PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf(
        "How to Train Your Dragon",
        "Three Billboards Outside Ebbing, Missouri"
    )
}