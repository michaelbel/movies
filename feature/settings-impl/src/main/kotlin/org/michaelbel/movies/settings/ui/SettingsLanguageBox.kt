package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings.ktx.languageText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.LanguagePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsLanguageBox(
    currentLanguage: AppLanguage,
    onLanguageSelect: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    var languageDropdown by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable { languageDropdown = true }
            .testTag("ConstraintLayout")
    ) {
        val (icon, title, value) = createRefs()

        Icon(
            imageVector = MoviesIcons.Language,
            contentDescription = stringResource(MoviesContentDescription.LanguageIcon),
            modifier = Modifier
                .constrainAs(icon) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("Icon"),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            text = stringResource(R.string.settings_language),
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(icon.end, 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("TitleText"),
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        Box(
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("ValueText"),
        ) {
            Text(
                text = currentLanguage.languageText,
                style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.primary)
            )

            DropdownMenu(
                expanded = languageDropdown,
                onDismissRequest = { languageDropdown = false },
                offset = DpOffset(x = 0.dp, y = (-48).dp),
                modifier = Modifier.widthIn(min = 112.dp, max = 280.dp)
            ) {
                AppLanguage.VALUES.forEach { appLanguage ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = appLanguage.languageText,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            onLanguageSelect(appLanguage)
                            languageDropdown = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SettingsLanguageBoxPreview(
    @PreviewParameter(LanguagePreviewParameterProvider::class) language: AppLanguage
) {
    MoviesTheme {
        SettingsLanguageBox(
            currentLanguage = language,
            onLanguageSelect = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun SettingsLanguageBoxAmoledPreview(
    @PreviewParameter(LanguagePreviewParameterProvider::class) language: AppLanguage
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingsLanguageBox(
            currentLanguage = language,
            onLanguageSelect = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}