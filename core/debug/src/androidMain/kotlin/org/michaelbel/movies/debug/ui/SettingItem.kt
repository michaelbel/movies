package org.michaelbel.movies.debug.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription

@Composable
internal fun SettingItem(
    title: String,
    description: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = icon,
            contentDescription = MoviesContentDescription.None,
            modifier = Modifier
                .padding(start = 8.dp, end = 16.dp)
                .size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Column(
            modifier = Modifier.weight(1F)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )

            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}