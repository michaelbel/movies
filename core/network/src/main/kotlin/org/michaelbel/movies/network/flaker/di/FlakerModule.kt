package org.michaelbel.movies.network.flaker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor
import io.github.rotbolt.flakerokhttpcore.dto.FlakerFailResponse

@Module
@InstallIn(SingletonComponent::class)
internal object FlakerModule {

    @Provides
    fun provideFlakerInterceptor(): FlakerInterceptor {
        val flakerFailResponse = FlakerFailResponse(
            httpCode = 500,
            message = "Flaker is enabled. This is a flaky response",
            responseBodyString = "Test Failure"
        )
        return FlakerInterceptor.Builder()
            .failResponse(flakerFailResponse)
            .build()
    }
}