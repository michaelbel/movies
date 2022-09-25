package org.michaelbel.movies.domain.datasource.ktx

import retrofit2.Retrofit

inline fun <reified T> Retrofit.createService(): T {
    return create(T::class.java)
}