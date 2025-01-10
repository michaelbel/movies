package org.michaelbel.movies.settings.ktx

import org.jetbrains.compose.resources.StringResource
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.strings.MoviesStrings

internal val IconAlias.iconSnackbarTextRes: StringResource
    get() = when (this) {
        is IconAlias.Red -> MoviesStrings.settings_app_launcher_icon_red
        is IconAlias.Purple -> MoviesStrings.settings_app_launcher_icon_purple
        is IconAlias.Brown -> MoviesStrings.settings_app_launcher_icon_brown
        is IconAlias.Amoled -> MoviesStrings.settings_app_launcher_icon_amoled
    }