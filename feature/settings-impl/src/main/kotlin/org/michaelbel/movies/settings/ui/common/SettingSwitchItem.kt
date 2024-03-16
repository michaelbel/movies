package org.michaelbel.movies.settings.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionKmp
import org.michaelbel.movies.ui.compose.SwitchCheckIcon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.BooleanPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingSwitchItem(
    title: String,
    description: String,
    icon: ImageVector,
    checked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 20.dp)
    ) {
        val (iconRef, texts, switch) = createRefs()

        Icon(
            imageVector = icon,
            contentDescription = MoviesContentDescriptionKmp.None,
            modifier = Modifier.constrainAs(iconRef) {
                width = Dimension.value(24.dp)
                height = Dimension.value(24.dp)
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Column(
            modifier = Modifier.constrainAs(texts) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(iconRef.end, 16.dp)
                top.linkTo(parent.top)
                end.linkTo(switch.start, 16.dp)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.primary)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = null,
            modifier = Modifier
                .constrainAs(switch) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("Switch"),
            thumbContent = if (checked) { { SwitchCheckIcon() } } else null,
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.surfaceTint,
                checkedIconColor = MaterialTheme.colorScheme.surfaceTint
            )
        )
    }
}

@Composable
internal fun SettingSwitchItem(
    title: String,
    description: String,
    icon: Painter,
    checked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 20.dp)
    ) {
        val (iconRef, texts, switch) = createRefs()

        Icon(
            painter = icon,
            contentDescription = MoviesContentDescriptionKmp.None,
            modifier = Modifier.constrainAs(iconRef) {
                width = Dimension.value(24.dp)
                height = Dimension.value(24.dp)
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Column(
            modifier = Modifier.constrainAs(texts) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(iconRef.end, 16.dp)
                top.linkTo(parent.top)
                end.linkTo(switch.start, 16.dp)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.primary)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = null,
            modifier = Modifier
                .constrainAs(switch) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("Switch"),
            thumbContent = if (checked) { { SwitchCheckIcon() } } else null,
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.surfaceTint,
                checkedIconColor = MaterialTheme.colorScheme.surfaceTint
            )
        )
    }
}

@Composable
@DevicePreviews
private fun SettingSwitchItemPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) checked: Boolean
) {
    MoviesTheme {
        SettingSwitchItem(
            title = "Title",
            description = "Description",
            icon = MoviesIcons.Language,
            checked = checked,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun SettingSwitchItemAmoledPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) checked: Boolean
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingSwitchItem(
            title = "Title",
            description = "Description",
            icon = MoviesIcons.Language,
            checked = checked,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}