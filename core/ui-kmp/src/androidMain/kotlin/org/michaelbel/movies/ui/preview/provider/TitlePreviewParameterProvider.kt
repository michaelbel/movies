@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

actual class TitlePreviewParameterProvider: PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "How to Train Your Dragon",
        "Three Billboards Outside Ebbing, Missouri"
    )
}