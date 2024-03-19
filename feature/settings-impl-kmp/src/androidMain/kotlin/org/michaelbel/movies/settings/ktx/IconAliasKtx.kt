package org.michaelbel.movies.settings.ktx

import android.content.Context
import org.michaelbel.movies.settings_impl_kmp.R
import org.michaelbel.movies.ui.appicon.IconAlias

actual fun IconAlias.iconSnackbarText(context: Context): String {
    return when (this) {
        is IconAlias.Red -> context.getString(R.string.settings_app_launcher_icon_red)
        is IconAlias.Purple -> context.getString(R.string.settings_app_launcher_icon_purple)
        is IconAlias.Brown -> context.getString(R.string.settings_app_launcher_icon_brown)
        is IconAlias.Amoled -> context.getString(R.string.settings_app_launcher_icon_amoled)
    }
}