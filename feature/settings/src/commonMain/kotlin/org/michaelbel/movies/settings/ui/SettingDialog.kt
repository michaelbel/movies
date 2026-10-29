package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewWrapper
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.interactor.entity.AppLanguage
import org.michaelbel.movies.settings.ktx.stringText
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.AppearancePreviewParameterProvider
import org.michaelbel.movies.ui.preview.wrapper.ThemeWrapper
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
fun <T: SealedString> SettingsDialog(
    icon: ImageVector,
    title: String,
    items: List<T>,
    currentItem: T,
    onItemSelect: (T) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
                shapes = ButtonDefaults.shapes()
            ) {
                Text(
                    text = stringResource(MoviesStrings.settings_action_cancel),
                    style = MaterialTheme.typography.titleMediumEmphasized.copy(
                        textAlign = TextAlign.Center
                    )
                )
            }
        },
        icon = {
            Icon(
                imageVector = icon,
                modifier = Modifier.size(IconButtonDefaults.largeIconSize),
                contentDescription = MoviesContentDescription.None
            )
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
            ) {
                itemsIndexed(items) { index, item ->
                    SegmentedListItem(
                        onClick = {
                            onItemSelect(item)
                            onDismissRequest()
                        },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = index,
                            count = items.size
                        ),
                        leadingContent = {
                            RadioButton(
                                selected = currentItem == item,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                        alpha = .6F
                                    )
                                )
                            )
                        },
                        colors = ListItemDefaults.segmentedColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(
                            text = item.stringText,
                            style = MaterialTheme.typography.titleMediumEmphasized
                        )
                    }
                }
            }
        },
        shape = MaterialTheme.shapes.extraLarge,
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    )
}

@PreviewWrapper(ThemeWrapper::class)
@Preview
@Composable
private fun SettingDialogPreview(
    @PreviewParameter(AppearancePreviewParameterProvider::class) appLanguage: AppLanguage
) {
    SettingsDialog(
        icon = MoviesIcons.Language,
        title = stringResource(MoviesStrings.settings_language),
        items = AppLanguage.VALUES,
        currentItem = appLanguage,
        onItemSelect = {},
        onDismissRequest = {}
    )
}
