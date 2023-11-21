package org.michaelbel.movies.network.service.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.network.service.account.AccountService
import org.michaelbel.movies.network.service.authentication.AuthenticationService
import org.michaelbel.movies.network.service.image.ImageService
import org.michaelbel.movies.network.service.ktx.createService
import org.michaelbel.movies.network.service.movie.MovieService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun provideMovieService(
        retrofit: Retrofit
    ): MovieService = retrofit.createService()

    @Provides
    @Singleton
    fun provideImageService(
        retrofit: Retrofit
    ): ImageService = retrofit.createService()

    @Provides
    @Singleton
    fun provideAuthenticationService(
        retrofit: Retrofit
    ): AuthenticationService = retrofit.createService()

    @Provides
    @Singleton
    fun provideAccountService(
        retrofit: Retrofit
    ): AccountService = retrofit.createService()
}