package org.michaelbel.movies.settings.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.common.biometric.di.biometricKoinModule
import org.michaelbel.movies.common.notify.di.notifyKoinModule
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.settings.SettingsViewModel

val settingsKoinModule = module {
    includes(
        biometricKoinModule,
        notifyKoinModule,
        interactorKoinModule
    )
    viewModelOf(::SettingsViewModel)
}