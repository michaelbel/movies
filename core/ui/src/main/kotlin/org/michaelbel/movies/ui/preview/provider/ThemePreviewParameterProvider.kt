package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.common.theme.AppTheme

class ThemePreviewParameterProvider: PreviewParameterProvider<AppTheme> {
    override val values = AppTheme.VALUES.asSequence()
}