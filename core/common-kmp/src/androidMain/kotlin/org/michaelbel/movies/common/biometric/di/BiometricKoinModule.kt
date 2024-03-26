package org.michaelbel.movies.common.biometric.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.common.biometric.BiometricController
import org.michaelbel.movies.common.biometric.impl.BiometricControllerImpl

val biometricKoinModule = module {
    singleOf(::BiometricControllerImpl) { bind<BiometricController>() }
}