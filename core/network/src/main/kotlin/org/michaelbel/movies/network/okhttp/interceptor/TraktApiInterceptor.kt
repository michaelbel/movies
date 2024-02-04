package org.michaelbel.movies.network.okhttp.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class TraktApiInterceptor(
    private val traktClientId: String
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest: Request = chain.request()
        originalRequest = originalRequest.newBuilder()
            .addHeader("trakt-api-key", traktClientId)
            .build()
        return chain.proceed(originalRequest)
    }
}