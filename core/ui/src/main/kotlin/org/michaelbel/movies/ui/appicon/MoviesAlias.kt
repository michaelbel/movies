package org.michaelbel.movies.ui.appicon

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

private fun Context.componentName(iconAlias: IconAlias): ComponentName {
    return ComponentName(packageName, "org.michaelbel.movies.${iconAlias.key}")
}

fun Context.isEnabled(iconAlias: IconAlias): Boolean {
    val enabledSetting = packageManager.getComponentEnabledSetting(componentName(iconAlias))
    return enabledSetting == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || enabledSetting == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT && iconAlias == IconAlias.Red
}

fun Context.setIcon(iconAlias: IconAlias) {
    IconAlias.VALUES.forEach { alias ->
        packageManager.setComponentEnabledSetting(
            componentName(alias),
            if (alias == iconAlias) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}

fun Context.installLauncherIcon() {
    IconAlias.VALUES.forEach { iconAlias ->
        if (isEnabled(iconAlias)) {
            return
        }
    }
    setIcon(IconAlias.Red)
}