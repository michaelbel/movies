package org.michaelbel.movies.ui.version.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.ui.version.AppVersionData

class VersionPreviewParameterProvider: PreviewParameterProvider<AppVersionData> {
    override val values: Sequence<AppVersionData> = sequenceOf(
        AppVersionData(
            version = "1.0.0",
            code = 100L
        )
    )
}