package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class BooleanPreviewParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(
        true,
        false
    )
}