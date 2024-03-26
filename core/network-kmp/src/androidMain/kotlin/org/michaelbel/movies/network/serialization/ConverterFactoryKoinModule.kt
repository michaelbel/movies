package org.michaelbel.movies.network.serialization

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val converterFactoryKoinModule = module {
    single {
        val contentType = "application/json".toMediaType()
        val format = Json { ignoreUnknownKeys = true }
        format.asConverterFactory(contentType)
    }
}