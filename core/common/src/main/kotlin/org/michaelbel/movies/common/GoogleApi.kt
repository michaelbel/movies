package org.michaelbel.movies.common

import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleApi @Inject constructor(
    @ApplicationContext private val context: Context,
    //private val googleApiAvailability: GoogleApiAvailability
) {

    /*val isPlayServicesAvailable: Boolean
        get() {
            val status = googleApiAvailability.isGooglePlayServicesAvailable(context)
            return status == ConnectionResult.SUCCESS
        }*/

    @Suppress("Deprecation")
    val isAppFromGooglePlay: Boolean
        get() {
            val validInstallers: List<String> = listOf(
                "com.android.vending",
                "com.google.android.feedback"
            )
            val installer: String = if (Build.VERSION.SDK_INT >= 30) {
                context.packageManager.getInstallSourceInfo(context.packageName).originatingPackageName
            } else {
                context.packageManager.getInstallerPackageName(context.packageName)
            }.orEmpty()
            return validInstallers.contains(installer)
        }
}