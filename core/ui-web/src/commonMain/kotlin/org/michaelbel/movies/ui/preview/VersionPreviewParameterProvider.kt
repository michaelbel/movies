package org.michaelbel.movies.ui.preview

import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.ui.preview.base.CollectionPreviewParameterProvider

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