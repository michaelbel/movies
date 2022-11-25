package org.michaelbel.movies.ui.theme.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.ui.theme.model.AppTheme

class ThemesPreviewParameterProvider: PreviewParameterProvider<AppTheme> {
    override val values: Sequence<AppTheme> = sequenceOf(
        AppTheme.FollowSystem,
        AppTheme.NightNo,
        AppTheme.NightYes
    )
}