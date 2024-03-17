package org.michaelbel.movies.ui.shortcuts

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import org.michaelbel.movies.ui.appicon.shortcutSearchIconRes
import org.michaelbel.movies.ui.appicon.shortcutSettingsIconRes
import org.michaelbel.movies.ui_kmp.R

private const val SEARCH_SHORTCUT_ID = "searchShortcutId"
private const val SETTINGS_SHORTCUT_ID = "settingsShortcutId"

const val INTENT_ACTION_SEARCH = "movies_shortcut://search"
const val INTENT_ACTION_SETTINGS = "movies_shortcut://settings"

/**
 * See [App Shortcuts Design Guidelines](https://commondatastorage.googleapis.com/androiddevelopers/shareables/design/app-shortcuts-design-guidelines.pdf)
 */
actual fun Context.installShortcuts() {
    val searchShortcut = ShortcutInfoCompat.Builder(this, SEARCH_SHORTCUT_ID)
        .setShortLabel(getString(R.string.shortcuts_search_title))
        .setLongLabel(getString(R.string.shortcuts_search_title))
        .setRank(1)
        .setIcon(IconCompat.createWithResource(this, shortcutSearchIconRes))
        .setIntent(Intent(Intent.ACTION_VIEW, INTENT_ACTION_SEARCH.toUri()))
        .build()
    val settingsShortcut = ShortcutInfoCompat.Builder(this, SETTINGS_SHORTCUT_ID)
        .setShortLabel(getString(R.string.shortcuts_settings_title))
        .setLongLabel(getString(R.string.shortcuts_settings_title))
        .setRank(1)
        .setIcon(IconCompat.createWithResource(this, shortcutSettingsIconRes))
        .setIntent(Intent(Intent.ACTION_VIEW, INTENT_ACTION_SETTINGS.toUri()))
        .build()
    ShortcutManagerCompat.setDynamicShortcuts(this, listOf(searchShortcut, settingsShortcut))
}