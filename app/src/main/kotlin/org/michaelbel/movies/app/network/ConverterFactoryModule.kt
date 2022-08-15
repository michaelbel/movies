package org.michaelbel.movies.app.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

@Module
@InstallIn(SingletonComponent::class)
object ConverterFactoryModule {

    @Provides
    @Singleton
    fun provideSerializationConverterFactory(): Converter.Factory {
        val contentType: MediaType = "application/json".toMediaType()
        val format = Json { ignoreUnknownKeys = true }
        return format.asConverterFactory(contentType)
    }
}