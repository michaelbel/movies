package org.michaelbel.movies.settings.iconAlias.impl

import android.content.Context
import org.michaelbel.movies.settings.iconAlias.IconAliasManager
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.appicon.enabledIcon
import org.michaelbel.movies.ui.appicon.setIcon

class IconAliasManagerImpl(
    private val context: Context
): IconAliasManager {

    override val enabledIcon: IconAlias
        get() = context.enabledIcon

    override fun setIcon(iconAlias: IconAlias) {
        context.setIcon(iconAlias)
    }
}