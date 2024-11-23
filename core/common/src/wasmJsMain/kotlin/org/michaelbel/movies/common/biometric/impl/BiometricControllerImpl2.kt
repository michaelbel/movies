package org.michaelbel.movies.common.biometric.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.common.biometric.BiometricController2

internal class BiometricControllerImpl2: BiometricController2 {

    override val isBiometricAvailable: Flow<Boolean>
        get() = flowOf(false)
}