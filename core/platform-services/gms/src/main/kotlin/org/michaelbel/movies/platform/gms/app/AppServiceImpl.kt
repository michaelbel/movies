package org.michaelbel.movies.platform.gms.app

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.FirebaseApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import org.michaelbel.movies.platform.main.app.AppService

internal class AppServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val googleApiAvailability: GoogleApiAvailability
): AppService {

    override val isPlayServicesAvailable: Boolean
        get() {
            val status: Int = googleApiAvailability.isGooglePlayServicesAvailable(context)
            return status == ConnectionResult.SUCCESS
        }

    override fun installApp() {
        FirebaseApp.initializeApp(context)
    }
}