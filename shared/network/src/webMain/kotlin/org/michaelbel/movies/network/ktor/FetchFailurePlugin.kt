package org.michaelbel.movies.network.ktor

import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import kotlinx.io.IOException

/**
 * Ktor Js engine reports a failed `fetch` (no connection, blocked request, CORS) as `Error("Fail to fetch", cause)`.
 * `kotlin.Error` is not an `Exception`, so `catch (e: Exception)` in shared code does not see it.
 * Converts exactly this error into [IOException], like network failures on other platforms.
 */
val FetchFailurePlugin = createClientPlugin("FetchFailurePlugin") {
    on(Send) { request ->
        try {
            proceed(request)
        } catch (e: Error) {
            if (e::class != Error::class) {
                throw e
            }
            throw IOException(e.message, e)
        }
    }
}
