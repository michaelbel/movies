package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.common.appearance.FeedView

class AppearancePreviewParameterProvider: PreviewParameterProvider<FeedView> {
    override val values = FeedView.VALUES.asSequence()
}