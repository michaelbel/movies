package org.michaelbel.movies.network.connectivity.impl

import android.content.Context
import net.i2p.android.router.util.ConnectivityAndInternetAccess
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response
import org.michaelbel.movies.network.connectivity.OfflineNetworkException
import org.michaelbel.movies.network.connectivity.RemoteConnectivityPolicy

/**
 * Observes transport failures from the real backend request. It never performs a
 * pre-flight check and never changes the response/error seen by Ktor.
 */
class BackendConnectivityInterceptor(
    context: Context,
    private val connectivityMonitor: MoviesConnectivityMonitor
) : Interceptor {
    private val applicationContext = context.applicationContext ?: context

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Cheap local guard only. It does not perform DNS, TCP, TLS or HTTP probes.
        if (!RemoteConnectivityPolicy.canStartRemoteRequest(
                isConnected = ConnectivityAndInternetAccess.isConnected(applicationContext),
                hasPhysicalNetwork = ConnectivityAndInternetAccess.hasPhysicalNetwork(applicationContext)
            )
        ) {
            throw OfflineNetworkException()
        }

        return try {
            chain.proceed(request)
        } catch (failure: IOException) {
            // A response (including 4xx/5xx) is returned normally and never reaches here.
            // Therefore this callback is reserved for transport-level failures.
            connectivityMonitor.onBackendTransportFailure(request.url.host, failure)
            throw failure
        }
    }
}
