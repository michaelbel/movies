package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.common.version.AppVersionData

actual class VersionPreviewParameterProvider: PreviewParameterProvider<AppVersionData> {
    override val values = sequenceOf(
        AppVersionData(
            version = "1.0.0",
            code = 100L,
            flavor = "GMS",
            isDebug = false
        ),
        AppVersionData(
            version = "1.0.0",
            code = 100L,
            flavor = "GMS",
            isDebug = true
        ),
        AppVersionData(
            version = "1.0.0",
            code = 100L,
            flavor = "HMS",
            isDebug = false
        ),
        AppVersionData(
            version = "1.0.0",
            code = 100L,
            flavor = "HMS",
            isDebug = true
        )
    )
}