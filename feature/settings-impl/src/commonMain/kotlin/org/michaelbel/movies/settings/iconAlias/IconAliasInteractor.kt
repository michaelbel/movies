package org.michaelbel.movies.settings.iconAlias

import org.michaelbel.movies.ui.appicon.IconAlias

interface IconAliasInteractor {

    val enabledIcon: IconAlias

    fun setIcon(iconAlias: IconAlias)
}