package org.michaelbel.movies.common.biometric.impl

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.common.biometric.BiometricController
import org.michaelbel.movies.common.biometric.BiometricListener
import org.michaelbel.movies.common_kmp.R

internal class BiometricControllerImpl(
    private val context: Context
): BiometricController {

    override val isBiometricAvailable: Flow<Boolean>
        get() {
            val biometricManager = BiometricManager.from(context)
            val authenticators = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            return flowOf(authenticators == BiometricManager.BIOMETRIC_SUCCESS)
        }

    override fun authenticate(activity: FragmentActivity, biometricListener: BiometricListener) {
        val biometricPrompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(context),
            object: BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    biometricListener.onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    when (errorCode) {
                        BiometricPrompt.ERROR_USER_CANCELED -> biometricListener.onCancel()
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> biometricListener.onCancel()
                    }
                }
            }
        )
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.biometric_title))
            .setSubtitle(context.getString(R.string.biometric_subtitle))
            .setDescription(context.getString(R.string.biometric_description))
            .setNegativeButtonText(context.getString(R.string.biometric_cancel))
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
}