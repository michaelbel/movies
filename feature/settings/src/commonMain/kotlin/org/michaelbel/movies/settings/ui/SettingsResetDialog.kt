package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.icons.SettingsReset
import org.michaelbel.movies.ui.strings.MoviesStrings

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
                    text = stringResource(MoviesStrings.settings_action_reset),
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
                    text = stringResource(MoviesStrings.settings_action_cancel),
                    style = MaterialTheme.typography.titleMediumEmphasized.copy(textAlign = TextAlign.Center)
                )
            }
        },
        icon = {
            Icon(
                imageVector = MoviesIcons.SettingsReset,
                modifier = Modifier.size(IconButtonDefaults.largeIconSize),
                contentDescription = MoviesContentDescription.None
            )
        },
        title = {
            Text(
                text = stringResource(MoviesStrings.settings_reset),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Text(
                text = stringResource(MoviesStrings.settings_reset_confirmation),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = MaterialTheme.shapes.extraLarge,
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    )
}
