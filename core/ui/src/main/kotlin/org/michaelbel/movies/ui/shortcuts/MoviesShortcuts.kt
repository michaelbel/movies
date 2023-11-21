package org.michaelbel.movies.ui.shortcuts

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import org.michaelbel.movies.ui.R
import org.michaelbel.movies.ui.appicon.shortcutSettingsIconRes

private const val SETTINGS_SHORTCUT_ID = "settingsShortcutId"

const val INTENT_ACTION_SETTINGS = "shortcut://settings"

/**
 * See [App Shortcuts Design Guidelines](https://commondatastorage.googleapis.com/androiddevelopers/shareables/design/app-shortcuts-design-guidelines.pdf)
 */
fun Context.installShortcuts() {
    val settingsShortcut = ShortcutInfoCompat.Builder(this, SETTINGS_SHORTCUT_ID)
        .setShortLabel(getString(R.string.shortcuts_settings_title))
        .setLongLabel(getString(R.string.shortcuts_settings_title))
        .setRank(1)
        .setIcon(IconCompat.createWithResource(this, shortcutSettingsIconRes))
        .setIntent(Intent(Intent.ACTION_VIEW, INTENT_ACTION_SETTINGS.toUri()))
        .build()
    ShortcutManagerCompat.pushDynamicShortcut(this, settingsShortcut)
}