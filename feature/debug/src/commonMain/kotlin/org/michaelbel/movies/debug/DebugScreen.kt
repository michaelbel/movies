@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.debug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import movies.feature.debug.generated.resources.Res
import movies.feature.debug.generated.resources.debug_app_settings
import movies.feature.debug.generated.resources.debug_developer_settings
import movies.feature.debug.generated.resources.debug_firebase_token_copy
import movies.feature.debug.generated.resources.debug_notification_dialog_expire_reset
import movies.feature.debug.generated.resources.debug_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.debug.intent.DebugIntent
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.icons.SettingsAccountBox
import org.michaelbel.movies.ui.icons.SettingsCinematic

@Composable
fun DebugScreen(
    viewModel: DebugViewModel = koinViewModel(),
    uiInteractor: UiInteractor = koinInject()
) {
    val state by viewModel.stateFlow.collectAsStateCommon()

    val copyToClipboard = uiInteractor.rememberCopyToClipboardHandler()
    val navigateToAppSettings = uiInteractor.navigateToAppSettings()
    val navigateToDeveloperSettings = uiInteractor.navigateToDeveloperSettings()
    val sheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded)
    )
    val listItemCount = when {
        state.isFirebaseTokenFeatureEnabled -> 4
        else -> 3
    }

    ModalBottomSheet(
        onDismissRequest = { viewModel.dispatch(DebugIntent.DismissRequest) },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.debug_title),
                        modifier = Modifier.weight(1F),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )

                    FilledIconButton(
                        onClick = { viewModel.dispatch(DebugIntent.DismissRequest) },
                        shapes = IconButtonDefaults.shapes(
                            shape = IconButtonDefaults.smallRoundShape,
                            pressedShape = IconButtonDefaults.smallPressedShape
                        ),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = .08F
                            ),
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = MoviesIcons.Close,
                            contentDescription = stringResource(MoviesContentDescription.CloseIcon),
                            modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            item {
                SegmentedListItem(
                    onClick = navigateToAppSettings,
                    shapes = ListItemDefaults.segmentedShapes(
                        index = 0,
                        count = listItemCount
                    ),
                    leadingContent = {
                        Icon(
                            imageVector = MoviesIcons.SettingsCinematic,
                            contentDescription = null,
                            modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                        )
                    },
                    colors = ListItemDefaults.segmentedColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(
                        text = stringResource(Res.string.debug_app_settings),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            item {
                SegmentedListItem(
                    onClick = navigateToDeveloperSettings,
                    shapes = ListItemDefaults.segmentedShapes(
                        index = 1,
                        count = listItemCount
                    ),
                    leadingContent = {
                        Icon(
                            imageVector = MoviesIcons.SettingsAccountBox,
                            contentDescription = null,
                            modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                        )
                    },
                    colors = ListItemDefaults.segmentedColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(
                        text = stringResource(Res.string.debug_developer_settings),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            item {
                SegmentedListItem(
                    onClick = { viewModel.dispatch(DebugIntent.ResetNotificationExpireTime) },
                    shapes = ListItemDefaults.segmentedShapes(
                        index = 2,
                        count = listItemCount
                    ),
                    leadingContent = {
                        Icon(
                            imageVector = MoviesIcons.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                        )
                    },
                    colors = ListItemDefaults.segmentedColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(
                        text = stringResource(Res.string.debug_notification_dialog_expire_reset),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            if (state.isFirebaseTokenFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = { copyToClipboard(state.firebaseToken) },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = 3,
                            count = listItemCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.Token,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        colors = ListItemDefaults.segmentedColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(Res.string.debug_firebase_token_copy),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}
