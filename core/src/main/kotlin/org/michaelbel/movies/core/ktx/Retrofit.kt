package org.michaelbel.movies.core.ktx

import retrofit2.Retrofit

inline fun <reified T> createService(retrofit: Retrofit): T = retrofit.create(T::class.java)