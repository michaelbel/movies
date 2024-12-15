package org.michaelbel.movies.network.connectivity.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus

internal class NetworkManagerImpl: NetworkManager {
    override val status: Flow<NetworkStatus> = flowOf(NetworkStatus.Available)
}