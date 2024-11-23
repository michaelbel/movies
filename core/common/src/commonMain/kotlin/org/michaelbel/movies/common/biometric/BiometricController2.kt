package org.michaelbel.movies.common.biometric

import kotlinx.coroutines.flow.Flow

interface BiometricController2 {

    val isBiometricAvailable: Flow<Boolean>
}