package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.common.localization.model.AppLanguage

class LanguagePreviewParameterProvider: PreviewParameterProvider<AppLanguage> {
    override val values: Sequence<AppLanguage> = AppLanguage.VALUES.asSequence()
}