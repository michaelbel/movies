package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class SliderPreviewParameterProvider: PreviewParameterProvider<Int> {
    override val values: Sequence<Int> = sequenceOf(
        0,
        5000,
        10000
    )
}