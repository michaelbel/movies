@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.settingsweb.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.icons.SettingsReset

@Composable
fun SettingsResetDialog(
    onDismissRequest: () -> Unit,
    onResetClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onResetClick,
                shapes = ButtonDefaults.shapes()
            ) {
                Text(
                    text = "Reset",
                    style = MaterialTheme.typography.titleMediumEmphasized.copy(textAlign = TextAlign.Center)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                shapes = ButtonDefaults.shapes()
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.titleMediumEmphasized.copy(textAlign = TextAlign.Center)
                )
            }
        },
        icon = {
            Icon(
                imageVector = MoviesIcons.SettingsReset,
                contentDescription = null,
                modifier = Modifier.size(IconButtonDefaults.largeIconSize)
            )
        },
        title = {
            Text(
                text = "Reset settings",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Text(
                text = "Are you sure you want to reset app settings?",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = MaterialTheme.shapes.extraLarge,
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    )
}
