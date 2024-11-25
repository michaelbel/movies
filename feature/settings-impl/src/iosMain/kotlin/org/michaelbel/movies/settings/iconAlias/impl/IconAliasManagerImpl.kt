package org.michaelbel.movies.settings.iconAlias.impl

import org.michaelbel.movies.settings.iconAlias.IconAliasInteractor
import org.michaelbel.movies.ui.appicon.IconAlias

class IconAliasManagerImpl: IconAliasInteractor {

    override val enabledIcon: IconAlias
        get() = IconAlias.Red

    override fun setIcon(iconAlias: IconAlias) {}
}