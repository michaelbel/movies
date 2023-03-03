package org.michaelbel.movies.ui.language.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.ui.language.model.AppLanguage

class LanguagePreviewParameterProvider: PreviewParameterProvider<AppLanguage> {
    override val values: Sequence<AppLanguage> = sequenceOf(
        AppLanguage.English,
        AppLanguage.Russian
    )
}