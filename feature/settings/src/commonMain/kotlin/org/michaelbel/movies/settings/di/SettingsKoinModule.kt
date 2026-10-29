package org.michaelbel.movies.settings.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.common.biometric.di.biometricKoinModule
import org.michaelbel.movies.common.notify.di.notifyKoinModule
import org.michaelbel.movies.domain.usecase.di.useCaseKoinModule
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.settings.SettingsViewModel

val settingsKoinModule = module {
    includes(
        biometricKoinModule,
        notifyKoinModule,
        interactorKoinModule,
        useCaseKoinModule
    )
    viewModel {
        SettingsViewModel(
            uiInteractor = get(),
            aboutInteractor = get(),
            biometricController = get(),
            notifyManager = get(),
            interactor = get(),
            updateService = get(),
            appService = get(),
            selectThemeUseCase = get(),
            selectFeedViewUseCase = get(),
            selectMovieListUseCase = get(),
            selectTraktListUseCase = get(),
            setDynamicColorsUseCase = get(),
            resetSettingsUseCase = get(),
            themeDataFlowUseCase = get(),
            currentFeedViewFlowUseCase = get(),
            currentMovieListFlowUseCase = get(),
            currentTraktListFlowUseCase = get(),
            providerFlowUseCase = get(),
            biometricEnabledFlowUseCase = get(),
            screenshotBlockEnabledFlowUseCase = get(),
            setPaletteColorsUseCase = get(),
            setPaletteKeyUseCase = get(),
            setSeedColorUseCase = get(),
            setProviderUseCase = get(),
            setBiometricEnabledUseCase = get(),
            setScreenshotBlockEnabledUseCase = get()
        )
    }
}
