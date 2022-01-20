package org.michaelbel.moviemade.app.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GsonModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder().apply {
            setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        }
        return builder.create()
    }
}