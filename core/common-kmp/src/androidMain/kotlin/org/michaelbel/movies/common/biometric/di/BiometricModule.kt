package org.michaelbel.movies.common.biometric.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.common.biometric.BiometricController
import org.michaelbel.movies.common.biometric.impl.BiometricControllerImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface BiometricModule {

    @Binds
    @Singleton
    fun provideBiometricController(controller: BiometricControllerImpl): BiometricController
}