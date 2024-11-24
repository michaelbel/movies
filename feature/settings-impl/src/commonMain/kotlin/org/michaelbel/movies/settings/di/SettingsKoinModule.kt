package org.michaelbel.movies.settings.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.common.biometric.di.biometricKoinModule2
import org.michaelbel.movies.common.notify.di.notifyKoinModule
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.settings.SettingsViewModel

val settingsKoinModule = module {
    includes(
        biometricKoinModule2,
        notifyKoinModule,
        interactorKoinModule
    )
    viewModel { SettingsViewModel(get(), get(), get(), get(), get(), get()) }
}