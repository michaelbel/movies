package org.michaelbel.movies.platform.impl

import com.google.android.gms.common.GoogleApiAvailability
import org.koin.dsl.module

internal val googleApiKoinModule = module {
    single { GoogleApiAvailability.getInstance() }
}