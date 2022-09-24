package org.michaelbel.movies.network.ktx

import retrofit2.Retrofit

inline fun <reified T> Retrofit.createService(): T {
    return create(T::class.java)
}