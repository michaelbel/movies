package org.michaelbel.movies.common.biometric

import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.Flow

interface BiometricController {

    val isBiometricAvailable: Flow<Boolean>

    fun authenticate(activity: FragmentActivity, biometricListener: BiometricListener)
}