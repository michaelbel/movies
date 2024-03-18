@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.ui.appicon.IconAlias

actual class IconAliasPreviewParameterProvider: PreviewParameterProvider<IconAlias> {
    override val values = IconAlias.VALUES.asSequence()
}