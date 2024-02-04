package org.michaelbel.movies.network.okhttp.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class TmdbApiInterceptor(
    private val tmdbApiKey: String
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val newHttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", tmdbApiKey)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        return chain.proceed(newRequest)
    }
}