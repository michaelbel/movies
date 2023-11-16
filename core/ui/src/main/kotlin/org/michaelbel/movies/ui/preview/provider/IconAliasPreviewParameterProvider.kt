package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.ui.appicon.IconAlias

class IconAliasPreviewParameterProvider: PreviewParameterProvider<IconAlias> {
    override val values: Sequence<IconAlias> = IconAlias.VALUES.asSequence()
}