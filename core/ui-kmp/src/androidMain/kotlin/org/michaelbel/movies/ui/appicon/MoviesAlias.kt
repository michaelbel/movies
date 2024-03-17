package org.michaelbel.movies.ui.appicon

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.DrawableRes
import org.michaelbel.movies.ui_kmp.R

private fun Context.componentName(iconAlias: IconAlias): ComponentName {
    return ComponentName(packageName, "org.michaelbel.movies.${iconAlias.key}")
}

actual fun Context.isEnabled(iconAlias: IconAlias): Boolean {
    val enabledSetting = packageManager.getComponentEnabledSetting(componentName(iconAlias))
    return enabledSetting == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || enabledSetting == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT && iconAlias == IconAlias.Red
}

actual fun Context.setIcon(iconAlias: IconAlias) {
    IconAlias.VALUES.forEach { alias ->
        packageManager.setComponentEnabledSetting(
            componentName(alias),
            if (alias == iconAlias) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}

actual fun Context.installLauncherIcon() {
    IconAlias.VALUES.forEach { iconAlias ->
        if (isEnabled(iconAlias)) {
            return
        }
    }
    setIcon(IconAlias.Red)
}

actual val Context.shortcutSearchIconRes: Int
    @DrawableRes get() = when {
        isEnabled(IconAlias.Red) -> R.drawable.ic_shortcut_search_red_48
        isEnabled(IconAlias.Purple) -> R.drawable.ic_shortcut_search_purple_48
        isEnabled(IconAlias.Brown) -> R.drawable.ic_shortcut_search_brown_48
        isEnabled(IconAlias.Amoled) -> R.drawable.ic_shortcut_search_amoled_48
        else -> 0
    }

actual val Context.shortcutSettingsIconRes: Int
    @DrawableRes get() = when {
        isEnabled(IconAlias.Red) -> R.drawable.ic_shortcut_settings_red_48
        isEnabled(IconAlias.Purple) -> R.drawable.ic_shortcut_settings_purple_48
        isEnabled(IconAlias.Brown) -> R.drawable.ic_shortcut_settings_brown_48
        isEnabled(IconAlias.Amoled) -> R.drawable.ic_shortcut_settings_amoled_48
        else -> 0
    }