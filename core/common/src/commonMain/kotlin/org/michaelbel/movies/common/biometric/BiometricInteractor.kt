package org.michaelbel.movies.common.biometric

import kotlinx.coroutines.flow.Flow

interface BiometricInteractor {

    val isBiometricAvailable: Flow<Boolean>

    fun authenticate(activity: Any, biometricListener: BiometricListener)
}