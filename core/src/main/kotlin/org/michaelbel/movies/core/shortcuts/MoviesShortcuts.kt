package org.michaelbel.movies.core.shortcuts

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import org.michaelbel.movies.core.R

private const val SETTINGS_SHORTCUT_ID = "settingsShortcutId"

const val INTENT_ACTION_SETTINGS = "shortcut://settings"

fun Context.installShortcuts() {
    if (Build.VERSION.SDK_INT >= 25) {
        val settingsShortcut = ShortcutInfo.Builder(this, SETTINGS_SHORTCUT_ID)
            .setShortLabel(getString(R.string.shortcuts_settings_title))
            .setLongLabel(getString(R.string.shortcuts_settings_title))
            .setRank(1)
            .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_cog_outline))
            .setIntent(Intent(Intent.ACTION_VIEW, INTENT_ACTION_SETTINGS.toUri()))
            .build()

        val shortcutManager: ShortcutManager? = ContextCompat.getSystemService(
            this,
            ShortcutManager::class.java
        )
        shortcutManager?.dynamicShortcuts = listOf(settingsShortcut)
    }
}