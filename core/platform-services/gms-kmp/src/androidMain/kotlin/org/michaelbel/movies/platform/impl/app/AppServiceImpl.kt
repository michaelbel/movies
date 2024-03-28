package org.michaelbel.movies.platform.impl.app

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.FirebaseApp
import org.michaelbel.movies.platform.Flavor
import org.michaelbel.movies.platform.app.AppService

class AppServiceImpl(
    private val context: Context,
    private val googleApiAvailability: GoogleApiAvailability
): AppService {

    override val flavor: Flavor = Flavor.Gms

    override val isPlayServicesAvailable: Boolean
        get() {
            val status: Int = googleApiAvailability.isGooglePlayServicesAvailable(context)
            return status == ConnectionResult.SUCCESS
        }

    override fun installApp() {
        FirebaseApp.initializeApp(context)
    }
}