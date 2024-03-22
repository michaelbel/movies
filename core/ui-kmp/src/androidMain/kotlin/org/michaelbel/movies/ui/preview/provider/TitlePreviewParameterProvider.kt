@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider

actual class TitlePreviewParameterProvider: CollectionPreviewParameterProvider<String>(
    listOf(
        "How to Train Your Dragon",
        "Three Billboards Outside Ebbing, Missouri"
    )
)