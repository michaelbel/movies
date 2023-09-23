package org.michaelbel.movies.network.service.ktx

import retrofit2.Retrofit

internal inline fun <reified T> Retrofit.createService(): T = create(T::class.java)