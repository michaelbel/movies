package org.michaelbel.movies.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.repository.AccountRepository
import org.michaelbel.movies.repository.AuthenticationRepository
import org.michaelbel.movies.repository.ImageRepository
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.NotificationRepository
import org.michaelbel.movies.repository.PagingKeyRepository
import org.michaelbel.movies.repository.SearchRepository
import org.michaelbel.movies.repository.SettingsRepository
import org.michaelbel.movies.repository.impl.AccountRepositoryImpl
import org.michaelbel.movies.repository.impl.AuthenticationRepositoryImpl
import org.michaelbel.movies.repository.impl.ImageRepositoryImpl
import org.michaelbel.movies.repository.impl.MovieRepositoryImpl
import org.michaelbel.movies.repository.impl.NotificationRepositoryImpl
import org.michaelbel.movies.repository.impl.PagingKeyRepositoryImpl
import org.michaelbel.movies.repository.impl.SearchRepositoryImpl
import org.michaelbel.movies.repository.impl.SettingsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun provideAccountRepository(
        repository: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    fun provideAuthenticationRepository(
        repository: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    @Singleton
    fun provideImageRepository(
        repository: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    @Singleton
    fun provideMovieRepository(
        repository: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    fun provideNotificationRepository(
        repository: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    @Singleton
    fun providePagingKeyRepository(
        repository: PagingKeyRepositoryImpl
    ): PagingKeyRepository

    @Binds
    @Singleton
    fun provideSearchRepository(
        repository: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    fun provideSettingsRepository(
        repository: SettingsRepositoryImpl
    ): SettingsRepository
}