package org.michaelbel.movies.network.connectivity.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus

class NetworkManagerImpl(
    connectivityMonitor: MoviesConnectivityMonitor
) : NetworkManager {
    override val status: Flow<NetworkStatus> = connectivityMonitor.networkState
        .map { state ->
            if (state.connected) {
                NetworkStatus.Available
            } else {
                NetworkStatus.Unavailable
            }
        }
        .distinctUntilChanged()
}
