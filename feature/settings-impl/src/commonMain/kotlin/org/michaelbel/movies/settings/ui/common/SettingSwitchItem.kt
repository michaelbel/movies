package org.michaelbel.movies.settings.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.compose.SwitchCheckIcon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.BooleanPreviewParameterProvider
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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = MoviesContentDescriptionCommon.None,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Column(
            modifier = Modifier.weight(1F)
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
            thumbContent = if (checked) { { SwitchCheckIcon() } } else null,
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.surfaceTint,
                checkedIconColor = MaterialTheme.colorScheme.surfaceTint
            )
        )
    }
}

@Preview
@Composable
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