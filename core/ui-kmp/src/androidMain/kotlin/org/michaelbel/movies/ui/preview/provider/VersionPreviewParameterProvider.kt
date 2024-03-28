package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import org.michaelbel.movies.common.version.AppVersionData

class VersionPreviewParameterProvider: CollectionPreviewParameterProvider<AppVersionData>(
    listOf(
        AppVersionData(
            flavor = "GMS"
        ),
        AppVersionData(
            flavor = "GMS"
        ),
        AppVersionData(
            flavor = "FOSS"
        )
    )
)