package org.michaelbel.movies.di

import org.koin.dsl.module
import org.michaelbel.movies.settings.di.settingsKoinModule

internal val appKoinModule = module {
    includes(
        settingsKoinModule
    )
}