package org.michaelbel.movies.settings.iconAlias.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.settings.iconAlias.IconAliasManager
import org.michaelbel.movies.settings.iconAlias.impl.IconAliasManagerImpl

actual val iconAliasKoinModule = module {
    singleOf(::IconAliasManagerImpl) { bind<IconAliasManager>() }
}