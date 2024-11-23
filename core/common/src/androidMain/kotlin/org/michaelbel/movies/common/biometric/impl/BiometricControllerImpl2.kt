package org.michaelbel.movies.common.biometric.impl

import android.content.Context
import androidx.biometric.BiometricManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.common.biometric.BiometricController2

internal class BiometricControllerImpl2(
    private val context: Context
): BiometricController2 {

    override val isBiometricAvailable: Flow<Boolean>
        get() {
            val biometricManager = BiometricManager.from(context)
            val authenticators = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            return flowOf(authenticators == BiometricManager.BIOMETRIC_SUCCESS)
        }
}