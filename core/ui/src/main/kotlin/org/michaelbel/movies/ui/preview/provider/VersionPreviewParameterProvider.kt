package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.common.version.AppVersionData

class VersionPreviewParameterProvider: PreviewParameterProvider<AppVersionData> {
    override val values: Sequence<AppVersionData> = sequenceOf(
        AppVersionData(
            version = "1.0.0",
            code = 100L
        )
    )
}