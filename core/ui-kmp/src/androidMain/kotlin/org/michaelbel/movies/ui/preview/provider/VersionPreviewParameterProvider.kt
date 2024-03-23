package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import org.michaelbel.movies.common.version.AppVersionData

class VersionPreviewParameterProvider: CollectionPreviewParameterProvider<AppVersionData>(
    listOf(
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
)