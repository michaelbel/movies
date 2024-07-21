package org.michaelbel.movies.settings.di

import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.settings.SettingsViewModel

actual val settingsKoinModule = module {
    includes(
        interactorKoinModule
    )
    single { SettingsViewModel(get()) }
}