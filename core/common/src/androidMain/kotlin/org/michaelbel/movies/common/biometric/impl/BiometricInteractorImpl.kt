package org.michaelbel.movies.common.biometric.impl

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.common.R
import org.michaelbel.movies.common.biometric.BiometricInteractor
import org.michaelbel.movies.common.biometric.BiometricListener

internal class BiometricInteractorImpl(
    private val context: Context
): BiometricInteractor {

    override val isBiometricAvailable: Flow<Boolean>
        get() {
            val biometricManager = BiometricManager.from(context)
            val authenticators = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            return flowOf(authenticators == BiometricManager.BIOMETRIC_SUCCESS)
        }

    override fun authenticate(activity: Any, biometricListener: BiometricListener) {
        val biometricPrompt = BiometricPrompt(
            activity as FragmentActivity,
            ContextCompat.getMainExecutor(context),
            object: BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    biometricListener.onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    when (errorCode) {
                        BiometricPrompt.ERROR_USER_CANCELED -> {
                            biometricListener.onCancel()
                        }
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                            biometricListener.onCancel()
                        }
                        else -> Unit
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