package org.michaelbel.movies.domain.service.ktx

import retrofit2.Retrofit

inline fun <reified T> Retrofit.createService(): T = create(T::class.java)