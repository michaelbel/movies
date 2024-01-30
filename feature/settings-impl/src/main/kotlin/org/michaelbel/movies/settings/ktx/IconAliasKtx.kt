package org.michaelbel.movies.settings.ktx

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.appicon.isEnabled

internal val IconAlias.iconText: String
    @Composable get() = when (this) {
        is IconAlias.Red -> stringResource(R.string.settings_app_launcher_icon_red)
        is IconAlias.Purple -> stringResource(R.string.settings_app_launcher_icon_purple)
        is IconAlias.Brown -> stringResource(R.string.settings_app_launcher_icon_brown)
        is IconAlias.Amoled -> stringResource(R.string.settings_app_launcher_icon_amoled)
    }

internal fun IconAlias.iconSnackbarText(context: Context): String {
    return when (this) {
        is IconAlias.Red -> context.getString(R.string.settings_app_launcher_icon_red)
        is IconAlias.Purple -> context.getString(R.string.settings_app_launcher_icon_purple)
        is IconAlias.Brown -> context.getString(R.string.settings_app_launcher_icon_brown)
        is IconAlias.Amoled -> context.getString(R.string.settings_app_launcher_icon_amoled)
    }
}

@Composable
internal fun IconAlias.backgroundColor(context: Context): Color {
    return when {
        context.isEnabled(this) -> MaterialTheme.colorScheme.inversePrimary
        else -> MaterialTheme.colorScheme.primaryContainer
    }
}