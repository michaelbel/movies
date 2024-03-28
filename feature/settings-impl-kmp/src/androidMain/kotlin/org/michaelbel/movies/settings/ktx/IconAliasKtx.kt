package org.michaelbel.movies.settings.ktx

import org.michaelbel.movies.settings_impl_kmp.R
import org.michaelbel.movies.ui.appicon.IconAlias

internal val IconAlias.iconSnackbarTextRes: Int
    get() = when (this) {
        is IconAlias.Red -> R.string.settings_app_launcher_icon_red
        is IconAlias.Purple -> R.string.settings_app_launcher_icon_purple
        is IconAlias.Brown -> R.string.settings_app_launcher_icon_brown
        is IconAlias.Amoled -> R.string.settings_app_launcher_icon_amoled
    }