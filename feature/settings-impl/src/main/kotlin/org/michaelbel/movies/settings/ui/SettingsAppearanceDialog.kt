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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.settings.ktx.feedViewText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.AppearancePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsAppearanceDialog(
    currentFeedView: FeedView,
    onFeedViewSelect: (FeedView) -> Unit,
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
                imageVector = MoviesIcons.GridView,
                contentDescription = stringResource(MoviesContentDescription.AppearanceIcon),
                modifier = Modifier.testTag("Icon")
            )
        },
        title = {
            Text(
                text = stringResource(R.string.settings_appearance),
                modifier = Modifier.testTag("Title"),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        text = {
            SettingAppearanceDialogContent(
                currentFeedView = currentFeedView,
                onFeedViewSelect = { feedView ->
                    onFeedViewSelect(feedView)
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
private fun SettingAppearanceDialogContent(
    currentFeedView: FeedView,
    onFeedViewSelect: (FeedView) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState: ScrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        FeedView.VALUES.forEach { feedView: FeedView ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clickable { onFeedViewSelect(feedView) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentFeedView == feedView,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )

                Text(
                    text = feedView.feedViewText,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SettingsAppearanceDialogPreview(
    @PreviewParameter(AppearancePreviewParameterProvider::class) feeView: FeedView
) {
    MoviesTheme {
        SettingsAppearanceDialog(
            currentFeedView = feeView,
            onFeedViewSelect = {},
            onDismissRequest = {}
        )
    }
}