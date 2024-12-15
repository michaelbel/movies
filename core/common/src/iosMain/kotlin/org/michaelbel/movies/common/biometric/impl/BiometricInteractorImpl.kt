package org.michaelbel.movies.common.biometric.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.common.biometric.BiometricInteractor
import org.michaelbel.movies.common.biometric.BiometricListener

internal class BiometricInteractorImpl: BiometricInteractor {

    override val isBiometricAvailable: Flow<Boolean>
        get() = flowOf(false)

    override fun authenticate(activity: Any, biometricListener: BiometricListener) {}
}