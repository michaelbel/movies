package org.michaelbel.movies.common.googleapi

import android.content.Context
import android.os.Build
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleApi @Inject constructor(
    @ApplicationContext private val context: Context,
    private val googleApiAvailability: GoogleApiAvailability
) {

    val isPlayServicesAvailable: Boolean
        get() {
            val status: Int = googleApiAvailability.isGooglePlayServicesAvailable(context)
            return status == ConnectionResult.SUCCESS
        }

    /**
     * Don't work on Android 11+
     */
    @Suppress("Deprecation")
    val isAppFromGooglePlay: Boolean
        get() {
            val validInstallers: List<String> = listOf(
                PACKAGE_ANDROID_VENDING,
                PACKAGE_ANDROID_FEEDBACK
            )
            val installer: String = if (Build.VERSION.SDK_INT >= 30) {
                context.packageManager.getInstallSourceInfo(context.packageName).originatingPackageName
            } else {
                context.packageManager.getInstallerPackageName(context.packageName)
            }.orEmpty()
            return validInstallers.contains(installer)
        }

    private companion object {
        private const val PACKAGE_ANDROID_VENDING = "com.android.vending"
        private const val PACKAGE_ANDROID_FEEDBACK = "com.google.android.feedback"
    }
}