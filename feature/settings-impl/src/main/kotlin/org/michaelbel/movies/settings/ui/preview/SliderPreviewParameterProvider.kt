package org.michaelbel.movies.settings.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SliderPreviewParameterProvider: PreviewParameterProvider<Int> {
    override val values: Sequence<Int> = sequenceOf(
        0,
        5000,
        10000
    )
}