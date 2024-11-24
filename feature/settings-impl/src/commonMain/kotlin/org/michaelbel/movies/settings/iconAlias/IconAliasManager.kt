package org.michaelbel.movies.settings.iconAlias

import org.michaelbel.movies.ui.appicon.IconAlias

interface IconAliasManager {

    val enabledIcon: IconAlias

    fun setIcon(iconAlias: IconAlias)
}