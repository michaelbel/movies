package org.michaelbel.movies.settings.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.settings.SettingsViewModel

val settingsKoinModule = module {
    includes(
        interactorKoinModule
    )
    factoryOf(::SettingsViewModel)
}