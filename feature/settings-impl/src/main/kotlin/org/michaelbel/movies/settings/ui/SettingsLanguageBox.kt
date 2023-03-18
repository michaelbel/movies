package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.settings.ktx.languageText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.LanguagePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsLanguageBox(
    modifier: Modifier = Modifier,
    languages: List<AppLanguage>,
    currentLanguage: AppLanguage,
    onLanguageSelect: (AppLanguage) -> Unit
) {
    var languageDialog: Boolean by remember { mutableStateOf(false) }

    if (languageDialog) {
        SettingLanguageDialog(
            languages = languages,
            currentLanguage = currentLanguage,
            onLanguageSelect = onLanguageSelect,
            onDismissRequest = {
                languageDialog = false
            }
        )
    }

    ConstraintLayout(
        modifier = modifier
            .clickable {
                languageDialog = true
            }
            .testTag("ConstraintLayout")
    ) {
        val (title, value) = createRefs()

        Text(
            text = stringResource(R.string.settings_language),
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("TitleText"),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        Text(
            text = currentLanguage.languageText,
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("ValueText"),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsLanguageBoxPreview(
    @PreviewParameter(LanguagePreviewParameterProvider::class) language: AppLanguage
) {
    MoviesTheme {
        SettingsLanguageBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            languages = listOf(AppLanguage.English, AppLanguage.Russian),
            currentLanguage = language,
            onLanguageSelect = {}
        )
    }
}