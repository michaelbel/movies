package org.michaelbel.movies.common.biometric.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.common.biometric.BiometricInteractor
import org.michaelbel.movies.common.biometric.impl.BiometricInteractorImpl

actual val biometricKoinModule = module {
    singleOf(::BiometricInteractorImpl) { bind<BiometricInteractor>() }
}