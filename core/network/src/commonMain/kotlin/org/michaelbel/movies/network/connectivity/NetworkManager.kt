package org.michaelbel.movies.network.connectivity

import kotlinx.coroutines.flow.Flow

interface NetworkManager {
    val status: Flow<NetworkStatus>
}