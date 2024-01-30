package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.settings.ktx.languageText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.LanguagePreviewParameterProvider
import org.michaelbel.movies.ui.theme.AmoledTheme
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingLanguageDialog(
    currentLanguage: AppLanguage,
    onLanguageSelect: (AppLanguage) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.testTag("ConfirmTextButton")
            ) {
                Text(
                    text = stringResource(R.string.settings_action_cancel),
                    modifier = Modifier.testTag("ConfirmText"),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        icon = {
            Icon(
                imageVector = MoviesIcons.Language,
                contentDescription = stringResource(MoviesContentDescription.LanguageIcon),
                modifier = Modifier.testTag("Icon")
            )
        },
        title = {
            Text(
                text = stringResource(R.string.settings_language),
                modifier = Modifier.testTag("Title"),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        text = {
            SettingLanguageDialogContent(
                currentLanguage = currentLanguage,
                onLanguageSelect = { language ->
                    onLanguageSelect(language)
                    onDismissRequest()
                },
                modifier = Modifier.testTag("Content")
            )
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun SettingLanguageDialogContent(
    currentLanguage: AppLanguage,
    onLanguageSelect: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState: ScrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        AppLanguage.VALUES.forEach { language: AppLanguage ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clickable { onLanguageSelect(language) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentLanguage == language,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6F)
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )

                Text(
                    text = language.languageText,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SettingLanguageDialogPreview(
    @PreviewParameter(LanguagePreviewParameterProvider::class) language: AppLanguage
) {
    MoviesTheme {
        SettingLanguageDialog(
            currentLanguage = language,
            onLanguageSelect = {},
            onDismissRequest = {}
        )
    }
}

@Composable
@Preview
private fun SettingLanguageDialogAmoledPreview(
    @PreviewParameter(LanguagePreviewParameterProvider::class) language: AppLanguage
) {
    AmoledTheme {
        SettingLanguageDialog(
            currentLanguage = language,
            onLanguageSelect = {},
            onDismissRequest = {}
        )
    }
}