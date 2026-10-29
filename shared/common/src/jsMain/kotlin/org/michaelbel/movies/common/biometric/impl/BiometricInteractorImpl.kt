package org.michaelbel.movies.common.biometric.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.common.biometric.BiometricInteractor

class BiometricInteractorImpl: BiometricInteractor {

    override val isBiometricAvailable: Flow<Boolean> = flowOf(false)
}
