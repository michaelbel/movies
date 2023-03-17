package org.michaelbel.movies.network.connectivity.ktx

import org.michaelbel.movies.network.connectivity.NetworkStatus

val NetworkStatus.isAvailable: Boolean
    get() = this == NetworkStatus.Available