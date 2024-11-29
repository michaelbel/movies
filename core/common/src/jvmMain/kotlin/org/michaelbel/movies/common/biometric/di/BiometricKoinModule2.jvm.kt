package org.michaelbel.movies.common.biometric.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.common.biometric.BiometricController2
import org.michaelbel.movies.common.biometric.impl.BiometricControllerImpl2

actual val biometricKoinModule2 = module {
    singleOf(::BiometricControllerImpl2) { bind<BiometricController2>() }
}