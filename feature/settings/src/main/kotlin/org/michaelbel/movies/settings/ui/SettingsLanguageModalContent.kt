package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.settings.ktx.languageTextRes
import org.michaelbel.movies.ui.language.model.AppLanguage
import org.michaelbel.movies.ui.language.preview.LanguagesPreviewParameterProvider
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsLanguageModalContent(
    modifier: Modifier = Modifier,
    languages: List<AppLanguage>,
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        languages.forEach { language: AppLanguage ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        onLanguageSelected(language)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentLanguage == language,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)
                    ),
                    modifier = Modifier
                        .padding(
                            start = 16.dp
                        )
                )

                Text(
                    text = stringResource(language.languageTextRes),
                    modifier = Modifier
                        .padding(
                            start = 8.dp
                        ),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SettingsLanguageModalContentPreview(
    @PreviewParameter(LanguagesPreviewParameterProvider::class) language: AppLanguage
) {
    MoviesTheme {
        SettingsLanguageModalContent(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            languages = listOf(
                AppLanguage.English,
                AppLanguage.Russian
            ),
            currentLanguage = language,
            onLanguageSelected = {}
        )
    }
}