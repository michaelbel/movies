package org.michaelbel.movies.network.connectivity.impl

import android.content.Context
import android.os.SystemClock
import android.util.Log
import java.io.Closeable
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.i2p.android.router.util.ConnectivityAndInternetAccess
import org.michaelbel.movies.network.connectivity.ConnectivityFallbackPolicy
import org.michaelbel.movies.network.connectivity.ConnectivityFallbackResult
import org.michaelbel.movies.network.connectivity.ConnectivityTierResult
import org.michaelbel.movies.network.connectivity.RemoteConnectivityPolicy

private const val TAG = "MoviesConnectivity"
private const val DIAGNOSTIC_COOLDOWN_MS = 5_000L

data class MoviesConnectivityDiagnostic(
    val failedBackendHost: String,
    val passiveState: ConnectivityAndInternetAccess.NetworkState,
    val fallbackResult: ConnectivityFallbackResult?,
    val elapsedMilliseconds: Long
)

/**
 * Application-level network observer and failure diagnostic.
 *
 * Normal operation is passive: one observer follows Android's actual default network and
 * generates no DNS or HTTP traffic. Active diagnostics are started only after OkHttp/Ktor
 * reports a transport IOException. The real application request is never gated or replaced.
 *
 * The observer is deliberately fail-safe. Connectivity diagnostics are auxiliary functionality
 * and therefore must never prevent the application from starting if Android rejects a network
 * callback registration or a vendor implementation throws while querying connectivity state.
 *
 * After a transport failure reported by Ktor or a background downloader, the active generic
 * diagnostic is deliberately deferred until this point. It distinguishes a general Internet
 * outage from a failure of the original operation without adding pre-flight probes to success.
 */
class MoviesConnectivityMonitor(
    context: Context
) : Closeable {
    private val applicationContext = context.applicationContext ?: context

    private val networkStateMutable = MutableStateFlow(snapshotNetworkStateSafely())
    val networkState: StateFlow<ConnectivityAndInternetAccess.NetworkState> =
        networkStateMutable.asStateFlow()

    private val lastDiagnosticMutable = MutableStateFlow<MoviesConnectivityDiagnostic?>(null)
    val lastDiagnostic: StateFlow<MoviesConnectivityDiagnostic?> =
        lastDiagnosticMutable.asStateFlow()

    private val fallbackPolicy = ConnectivityFallbackPolicy()

    // Untouched Gist defaults: effective/system DNS -> public DNS -> TCP/NTP/TLS/HTTPS.
    private val genericConnectivity = ConnectivityAndInternetAccess.Builder().build()

    private val diagnosticExecutor = Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable, "movies-connectivity-diagnostic").apply { isDaemon = true }
    }
    private val diagnosticInFlight = AtomicBoolean(false)
    private val lastDiagnosticStartedAt = AtomicLong(Long.MIN_VALUE)
    private val closed = AtomicBoolean(false)

    private val observer: Closeable? = createObserverSafely()

    private fun snapshotNetworkStateSafely(): ConnectivityAndInternetAccess.NetworkState {
        return try {
            normalizeNetworkState(ConnectivityAndInternetAccess.snapshotNetworkState(applicationContext))
        } catch (runtime: RuntimeException) {
            Log.e(TAG, "unable to read initial network state; continuing without blocking startup", runtime)
            disconnectedFallbackState()
        }
    }

    private fun createObserverSafely(): Closeable? {
        return try {
            ConnectivityAndInternetAccess.observeNetwork(applicationContext) { state ->
                val normalizedState = normalizeNetworkState(state)
                networkStateMutable.value = normalizedState
                Log.d(
                    TAG,
                    "default-network connected=${normalizedState.connected}, " +
                        "validated=${normalizedState.internetValidated}, " +
                        "captivePortal=${normalizedState.captivePortalDetected}"
                )
            }
        } catch (runtime: RuntimeException) {
            Log.e(
                TAG,
                "unable to register default-network observer; app will continue without passive updates",
                runtime
            )
            null
        }
    }

    private fun disconnectedFallbackState() = ConnectivityAndInternetAccess.NetworkState(
        connected = false,
        internetValidated = false,
        captivePortalDetected = false,
        observedAtElapsedRealtime = SystemClock.elapsedRealtime()
    )

    private fun normalizeNetworkState(
        state: ConnectivityAndInternetAccess.NetworkState
    ): ConnectivityAndInternetAccess.NetworkState {
        val hasPhysicalNetwork = try {
            ConnectivityAndInternetAccess.hasPhysicalNetwork(applicationContext)
        } catch (runtime: RuntimeException) {
            Log.w(TAG, "unable to verify physical network; treating state as offline", runtime)
            false
        }
        return state.copy(
            connected = RemoteConnectivityPolicy.canStartRemoteRequest(
                isConnected = state.connected,
                hasPhysicalNetwork = hasPhysicalNetwork
            )
        )
    }

    fun onBackendTransportFailure(failedHost: String, failure: Throwable) {
        if (closed.get()) return

        val now = SystemClock.elapsedRealtime()
        val previous = lastDiagnosticStartedAt.get()
        if (previous != Long.MIN_VALUE && now - previous < DIAGNOSTIC_COOLDOWN_MS) {
            Log.d(TAG, "diagnostic skipped (cooldown), backend=$failedHost")
            return
        }
        if (!diagnosticInFlight.compareAndSet(false, true)) {
            Log.d(TAG, "diagnostic skipped (already running), backend=$failedHost")
            return
        }
        lastDiagnosticStartedAt.set(now)

        Log.w(
            TAG,
            "backend transport failure host=$failedHost, " +
                "type=${failure.javaClass.simpleName}; starting app-first diagnosis"
        )

        diagnosticExecutor.execute {
            try {
                runDiagnostic(failedHost)
            } catch (runtime: RuntimeException) {
                Log.e(TAG, "connectivity diagnostic failed", runtime)
            } finally {
                diagnosticInFlight.set(false)
            }
        }
    }

    private fun runDiagnostic(failedHost: String) {
        val started = SystemClock.elapsedRealtime()
        val passive = networkStateMutable.value

        if (!passive.connected) {
            val diagnostic = MoviesConnectivityDiagnostic(
                failedBackendHost = failedHost,
                passiveState = passive,
                fallbackResult = null,
                elapsedMilliseconds = SystemClock.elapsedRealtime() - started
            )
            lastDiagnosticMutable.value = diagnostic
            Log.w(TAG, "diagnosis: Android default network is offline; active probes skipped")
            return
        }

        val fallbackResult = fallbackPolicy.diagnose(
            failedTarget = failedHost,
            generalProbe = {
                genericConnectivity.checkInternetBlocking(applicationContext).toTierResult()
            }
        )

        val diagnostic = MoviesConnectivityDiagnostic(
            failedBackendHost = failedHost,
            passiveState = passive,
            fallbackResult = fallbackResult,
            elapsedMilliseconds = SystemClock.elapsedRealtime() - started
        )
        lastDiagnosticMutable.value = diagnostic

        when {
            fallbackResult.generalTier?.reachable == true -> Log.w(
                TAG,
                "diagnosis: generic Internet works via " +
                    "${fallbackResult.generalTier.reachedEndpoint}; failed backend=$failedHost " +
                    "is likely target/service-specific or transient"
            )

            else -> Log.w(
                TAG,
                "diagnosis: Movies endpoints failed and generic Internet fallback also failed"
            )
        }
    }

    override fun close() {
        if (!closed.compareAndSet(false, true)) return
        try {
            observer?.close()
        } catch (runtime: RuntimeException) {
            Log.w(TAG, "default-network observer cleanup failed", runtime)
        }
        diagnosticExecutor.shutdownNow()
    }

    private fun ConnectivityAndInternetAccess.InternetResult.toTierResult() =
        ConnectivityTierResult(
            reachable = reachable,
            reachedEndpoint = reachedHost,
            attemptedEndpoints = attemptedHosts
        )
}
